package com.cargobackend.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.pojo.dao.CarMake;
import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.utils.CommonConstants;
import com.cargobackend.utils.CommonUtils;

@Repository("CargoDAOImpl")
public class CargoDAOImpl implements ICargoDAO{
	

    private final JdbcTemplate jdbcTemplate;

    public CargoDAOImpl(@Qualifier("cargoDataSource") DataSource fundDataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(fundDataSource);
    }

	@Override
	public CarMakeResponse getCarMakes(Boolean status) {
		
		CarMakeResponse carMakeResponse = new CarMakeResponse();
		carMakeResponse.setFailedResponse();
		System.out.println("In getCarMakes carMakeResponse......"+carMakeResponse);
	    Connection connection = null;
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String procedureName = "proc_get_car_make_v1dot0";
        final String procedureCall = "{call " + procedureName + "(?, ?, ?)}";
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            cStmt = connection.prepareCall(procedureCall);
            /* input parameters */            
    		if(status != null){
				cStmt.setBoolean(1, status);
			}else{
				cStmt.setNull(1, Types.NULL);
			}
            /* register output parameters */
            cStmt.registerOutParameter(2, Types.INTEGER);
            cStmt.registerOutParameter(3, Types.VARCHAR);
            System.out.println("In getCarMakes Calling DB procedure cStmt Before{}"+ cStmt);
            rs = cStmt.executeQuery();
            System.out.println("In getCarMakes Calling DB procedure cStmt After {}"+ cStmt);
            if (cStmt.getInt(2) == CommonConstants.DB_PROC_SUCCESS_RESPONSE) {
            	List<CarMake> carMakeList = new ArrayList<>();
            	CarMake carMake = null;
                while (rs.next()) {
                	carMake = new CarMake();
                	carMake.setMakeId(rs.getInt("c_make_id"));
                	carMake.setMakeName(rs.getString("c_make_name"));
                	carMake.setMakeDescription(rs.getString("c_make_description"));
                	carMake.setMakeStatus(rs.getBoolean("c_status"));
                	carMakeList.add(carMake);
                }
                carMakeResponse.setCarmakeList(carMakeList);
                carMakeResponse.setSuccessResponse();
             	System.out.println("In getCarMakesException carMakeResponse:"+ carMakeResponse);
            } else {
            	 System.out.println("In getCarMakes Error in proc :{}"+ cStmt.getString(4));
            }
        } catch (Exception e) {
        	 System.out.println("In getCarMakesException e :"+ e);
        } finally {
            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
        }
		return carMakeResponse;
	}

























































































	@Override
	public ModelResponse getModel(ModelRequest modelRequest) {

		ModelResponse response = new ModelResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_model_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (modelRequest == null || modelRequest.getModelIdList() == null
					|| modelRequest.getModelIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(modelRequest.getModelIdList(),
						CommonConstants.DELIMITER));
			}

			if (modelRequest == null || modelRequest.getModelNameList() == null
					|| modelRequest.getModelNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(modelRequest.getModelNameList(),
						CommonConstants.DELIMITER));
			}

			if (modelRequest == null || modelRequest.getMakeIdList() == null
					|| modelRequest.getMakeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(modelRequest.getMakeIdList(),
						CommonConstants.DELIMITER));
			}

			if (modelRequest == null || modelRequest.getBodyTypeIdList() == null
					|| modelRequest.getBodyTypeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(modelRequest.getBodyTypeIdList(),
						CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getModel Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getModel Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Model> modelList = new ArrayList<>();
				Model model = null;
				Make make = null;
				BodyType bodyType = null;
				while (rs.next()) {

					model = new Model();
					make = new Make();
					bodyType = new BodyType();
					model.setModelId(rs.getInt("c_model_id"));
					model.setModelName(rs.getString("c_model_name"));
					model.setModelDescription(rs.getString("c_model_description"));
					model.setModelStatus(rs.getBoolean("c_model_status"));
					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));
					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					model.setMake(make);
					model.setBodyType(bodyType);
					modelList.add(model);
				}
				response.setModelList(modelList);
				response.setSuccessResponse();
				System.out.println("In getModel response:" + response);
			} else {
				System.out.println("In getModel Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getModel e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public BodyTypeResponse getBodyType(BodyTypeRequest bodyTypeRequest) {
		BodyTypeResponse response = new BodyTypeResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_body_type_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (bodyTypeRequest == null || bodyTypeRequest.getBodyTypeIdList() == null
					|| bodyTypeRequest.getBodyTypeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(bodyTypeRequest.getBodyTypeIdList(),
						CommonConstants.DELIMITER));
			}

			if (bodyTypeRequest == null || bodyTypeRequest.getBodyTypeNameList() == null
					|| bodyTypeRequest.getBodyTypeNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(bodyTypeRequest.getBodyTypeNameList(),
						CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getBodyType Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getBodyType Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<BodyType> bodyTypeList = new ArrayList<>();
				BodyType bodyType = null;
				while (rs.next()) {
					bodyType = new BodyType();
					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					bodyTypeList.add(bodyType);
				}
				response.setBodyTypeList(bodyTypeList);
				response.setSuccessResponse();
				System.out.println("In getBodyType response:" + response);
			} else {
				System.out.println("In getBodyType Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getBodyType e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public FuelTypeResponse getFuelType(FuelTypeRequest fuelTypeRequest) {
		FuelTypeResponse response = new FuelTypeResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_fuel_type_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (fuelTypeRequest == null || fuelTypeRequest.getFuelTypeIdList() == null
					|| fuelTypeRequest.getFuelTypeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(fuelTypeRequest.getFuelTypeIdList(),
						CommonConstants.DELIMITER));
			}

			if (fuelTypeRequest == null || fuelTypeRequest.getFuelTypeNameList() == null
					|| fuelTypeRequest.getFuelTypeNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(fuelTypeRequest.getFuelTypeNameList(),
						CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getFuelType Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getFuelType Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<FuelType> FuelTypeList = new ArrayList<>();
				FuelType FuelType = null;
				while (rs.next()) {
					FuelType = new FuelType();
					FuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
					FuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
					FuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
					FuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
					FuelTypeList.add(FuelType);
				}
				response.setFuelTypeList(FuelTypeList);
				response.setSuccessResponse();
				System.out.println("In getFuelType response:" + response);
			} else {
				System.out.println("In getFuelType Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getFuelType e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public TransmissionTypeResponse getTransmissionType(TransmissionTypeRequest transmissionTypeRequest) {
		TransmissionTypeResponse response = new TransmissionTypeResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_transmission_type_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (transmissionTypeRequest == null || transmissionTypeRequest.getTransmissionTypeIdList() == null
					|| transmissionTypeRequest.getTransmissionTypeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(
						transmissionTypeRequest.getTransmissionTypeIdList(), CommonConstants.DELIMITER));
			}

			if (transmissionTypeRequest == null || transmissionTypeRequest.getTransmissionTypeNameList() == null
					|| transmissionTypeRequest.getTransmissionTypeNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(
						transmissionTypeRequest.getTransmissionTypeNameList(), CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getTransmissionType Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getTransmissionType Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<TransmissionType> TransmissionTypeList = new ArrayList<>();
				TransmissionType TransmissionType = null;
				while (rs.next()) {
					TransmissionType = new TransmissionType();
					TransmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
					TransmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
					TransmissionType.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
					TransmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
					TransmissionTypeList.add(TransmissionType);
				}
				response.setTransmissionTypeList(TransmissionTypeList);
				response.setSuccessResponse();
				System.out.println("In getTransmissionType response:" + response);
			} else {
				System.out.println("In getTransmissionType Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getTransmissionType e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}



}