package com.cargobackend.dao.impl;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.sql.DataSource;

import com.cargobackend.pojo.dao.cargo.*;
import com.cargobackend.pojo.request.*;
import com.cargobackend.pojo.response.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.common.BaseResponse;
import com.cargobackend.utils.CommonConstants;
import com.cargobackend.utils.CommonUtils;
import com.google.gson.Gson;

@Repository("CargoDAOImpl")
public class CargoDAOImpl implements ICargoDAO {

	public class CartEntryHistoryResp {
		public CartEntry cartEntry;
		public Set<Integer> bookingInfoId;

		public CartEntryHistoryResp(CartEntry cartEntry) {
			this.cartEntry = cartEntry;
			this.bookingInfoId = new HashSet<>();
		}

		public void addToCartEntry (Integer bookingInfoId){
			this.bookingInfoId.add(bookingInfoId);
		}
	}

	private final JdbcTemplate jdbcTemplate;

	public CargoDAOImpl(@Qualifier("cargoDataSource") DataSource cargoDataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(cargoDataSource);
	}

	@Override
	public CarMakeResponse getCarMakes(Boolean status) {

		CarMakeResponse carMakeResponse = new CarMakeResponse();
		carMakeResponse.setFailedResponse();
		System.out.println("In getCarMakes carMakeResponse......" + carMakeResponse);
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_car_make_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?, ?, ?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			if (status != null) {
				cStmt.setBoolean(1, status);
			} else {
				cStmt.setNull(1, Types.NULL);
			}
			/* register output parameters */
			cStmt.registerOutParameter(2, Types.INTEGER);
			cStmt.registerOutParameter(3, Types.VARCHAR);
			System.out.println("In getCarMakes Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getCarMakes Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(2) == CommonConstants.DB_PROC_SUCCESS_RESPONSE) {
				List<CarMake> carMakeList = new ArrayList<>();
				CarMake carMake = null;
				while (rs.next()) {
					carMake = new CarMake();
					carMake.setMakeId(rs.getInt("c_make_id"));
					carMake.setMakeName(rs.getString("c_make_name"));
					carMake.setMakeDescription(rs.getString("c_make_description"));
					carMake.setMakeStatus(rs.getBoolean("c_make_status"));
					carMakeList.add(carMake);
				}
				carMakeResponse.setCarmakeList(carMakeList);
				carMakeResponse.setSuccessResponse();
				System.out.println("In getCarMakesException carMakeResponse:" + carMakeResponse);
			} else {
				System.out.println("In getCarMakes Error in proc :{}" + cStmt.getString(4));
			}
		} catch (Exception e) {
			System.out.println("In getCarMakesException e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return carMakeResponse;
	}

	@Override
	public MakeResponse getMake(MakeRequest makeRequest) {
		MakeResponse response = new MakeResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_make_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (makeRequest == null || makeRequest.getMakeIdList() == null || makeRequest.getMakeIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(makeRequest.getMakeIdList(),
						CommonConstants.DELIMITER));
			}

			if (makeRequest == null || makeRequest.getMakeNameList() == null
					|| makeRequest.getMakeNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(makeRequest.getMakeNameList(),
						CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getMake Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getMake Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Make> makeList = new ArrayList<>();
				Make make = null;
				while (rs.next()) {
					make = new Make();
					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));
					makeList.add(make);
				}
				response.setMakeList(makeList);
				response.setSuccessResponse();
				System.out.println("In getCarMakesException response:" + response);
			} else {
				System.out.println("In getCarMakes Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getMake e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
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

	@Override
	public ColorResponse getColor(ColorRequest colorRequest) {
		ColorResponse response = new ColorResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_color_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */

			int i = 1;

			if (colorRequest == null || colorRequest.getColorIdList() == null
					|| colorRequest.getColorIdList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(colorRequest.getColorIdList(),
						CommonConstants.DELIMITER));
			}

			if (colorRequest == null || colorRequest.getColorNameList() == null
					|| colorRequest.getColorNameList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(colorRequest.getColorNameList(),
						CommonConstants.DELIMITER));
			}
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getColor Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getColor Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Color> colorList = new ArrayList<>();
				Color color = null;
				while (rs.next()) {
					color = new Color();
					color.setColorId(rs.getInt("c_color_id"));
					color.setColorName(rs.getString("c_color_name"));
					color.setColorDescription(rs.getString("c_color_description"));
					colorList.add(color);
				}
				response.setColorList(colorList);
				response.setSuccessResponse();
				System.out.println("In getColor response:" + response);
			} else {
				System.out.println("In getColor Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getColor e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public VariantResponse getVariant(VariantRequest variantRequest) {
		VariantResponse response = new VariantResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_variant_v1dot1";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;
			List<Integer> idList = null;

			cStmt.setTimestamp(i++, variantRequest.getFromDate());
			cStmt.setTimestamp(i++, variantRequest.getToDate());

			if (variantRequest == null || variantRequest.getFromLocation() == null
					|| variantRequest.getFromLocation().getLocationId() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getFromLocation().getLocationId());
			}

			if (variantRequest == null || variantRequest.getToLocation() == null
					|| variantRequest.getToLocation().getLocationId() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getToLocation().getLocationId());
			}

			if (variantRequest == null || variantRequest.getVariantList() == null
					|| variantRequest.getVariantList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Variant v : variantRequest.getVariantList()) {
					idList.add(v.getVariantId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			cStmt.setString(i++, CommonConstants.ALL);

			if (variantRequest == null || variantRequest.getMakeList() == null
					|| variantRequest.getMakeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Make v : variantRequest.getMakeList()) {
					idList.add(v.getMakeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getModelList() == null
					|| variantRequest.getModelList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Model v : variantRequest.getModelList()) {
					idList.add(v.getModelId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getBodyTypeList() == null
					|| variantRequest.getBodyTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (BodyType v : variantRequest.getBodyTypeList()) {
					idList.add(v.getBodyTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getFuelTypeList() == null
					|| variantRequest.getFuelTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (FuelType v : variantRequest.getFuelTypeList()) {
					idList.add(v.getFuelTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getTransmissionTypeList() == null
					|| variantRequest.getTransmissionTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (TransmissionType v : variantRequest.getTransmissionTypeList()) {
					idList.add(v.getTransmissionTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getColorList() == null
					|| variantRequest.getColorList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Color v : variantRequest.getColorList()) {
					idList.add(v.getColorId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getOffSet() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getOffSet());
			}

			if (variantRequest == null || variantRequest.getLimit() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getLimit());
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getVariant Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getVariant Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Variant> variantList = new ArrayList<>();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				List<VariantImage> variantImageList =null;
				 Boolean b = false;
				while (rs.next()) {
//					System.out.println("\n In getVariant " + rs.getInt("c_variant_id")+" name "+rs.getString("c_variant_name"));
					b = false;
					for (Variant v : variantList) {
						 if (v.getVariantId() == rs.getInt("c_variant_id")) {
							 b = true;
							 variantImage = new VariantImage();
							 variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							 variantImage.setVariantImageData(rs.getString("c_variant_image"));
							 variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							 variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							 variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							 variantImageList = v.getVariantImageList();
							 variantImageList.add(variantImage);
							 v.setVariantImageList(variantImageList);
							 break;
						 }
					 }
					 
//					 System.out.println("\n In getVariant " + rs.getInt("c_variant_id")+" name "+rs.getString("c_variant_name")+" \n b "+b);
					 if(b){
						 continue;
					 }
					 
				    variantImageList = new ArrayList<>();
					variant = new Variant();
					make = new Make();
					model = new Model();
					bodyType = new BodyType();
					fuelType = new FuelType();
					color = new Color();
					variantImage = new VariantImage();
					transmissionType = new TransmissionType();
					variant.setVariantId(rs.getInt("c_variant_id"));
					variant.setVariantName(rs.getString("c_variant_name"));
					variant.setVariantDescription(rs.getString("c_variant_description"));
					variant.setVariantStatus(rs.getBoolean("c_variant_status"));
					variant.setVariantMileage(rs.getLong("c_mileage"));
					variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
					variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
					variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
					variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));

					model.setModelId(rs.getInt("c_model_id"));
					model.setModelName(rs.getString("c_model_name"));
					model.setModelDescription(rs.getString("c_model_description"));
					model.setModelStatus(rs.getBoolean("c_model_status"));
					model.setMake(make);

					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					model.setBodyType(bodyType);
					variant.setModel(model);

					fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
					fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
					fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
					fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
					variant.setFuelType(fuelType);

					transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
					transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
					transmissionType.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
					transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
					variant.setTransmissionType(transmissionType);

					color.setColorId(rs.getInt("c_color_id"));
					color.setColorName(rs.getString("c_color_name"));
					color.setColorDescription(rs.getString("c_color_description"));
					variant.setColor(color);

					variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
					variantImage.setVariantImageData(rs.getString("c_variant_image"));
					variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
					variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
					variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
					variantImageList.add(variantImage);
					
					variant.setVariantImage(variantImage);
					variant.setVariantImageList(variantImageList);

					variantList.add(variant);

				}
				response.setVariantList(variantList);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In getVariant Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getVariant e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public LocationResponse getLocation() {
		LocationResponse response = new LocationResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_location_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			int i = 1;
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getLocation Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getLocation Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Location> locationList = new ArrayList<>();
				Location location = null;
				while (rs.next()) {
					location = new Location();
					location.setLocationId(rs.getInt("c_location_id"));
					location.setLocationName(rs.getString("c_location_name"));
					location.setLocationGPS(rs.getString("c_location_gps"));
					location.setLocationStateName(rs.getString("c_location_state_name"));
					location.setLocationCountryName(rs.getString("c_location_country_name"));
					location.setLocationStatus(rs.getBoolean("c_location_status"));
					locationList.add(location);
				}
				response.setLocationList(locationList);
				response.setSuccessResponse();
				System.out.println("In getLocation response:" + response);
			} else {
				System.out.println("In getLocation Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getLocation e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public VariantResponse getVariantById(Integer variantId) {
		VariantResponse response = new VariantResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_variant_by_id_v1dot0";
		Variant variant = null;
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (variantId == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantId);
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getVariant By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getVariant By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Variant> variantList = new ArrayList<>();
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				List<VariantImage> variantImageList =null;
				Boolean b = false;
				while (rs.next()) {
					 b = false;
					 for (Variant v : variantList) {
						 if (v.getVariantId() == rs.getInt("c_variant_id")) {
							 b = true;
							 variantImage = new VariantImage();
							 variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							 variantImage.setVariantImageData(rs.getString("c_variant_image"));
							 variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							 variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							 variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							 variantImageList = v.getVariantImageList();
							 variantImageList.add(variantImage);
							 v.setVariantImageList(variantImageList);
							 break;
						 }
					 }
					 
					 if(b){
						 continue;
					 }

					variantImageList = new ArrayList<>();
					variant = new Variant();
					make = new Make();
					model = new Model();
					bodyType = new BodyType();
					fuelType = new FuelType();
					color = new Color();
					variantImage = new VariantImage();
					transmissionType = new TransmissionType();

					variant.setVariantId(rs.getInt("c_variant_id"));
					variant.setVariantName(rs.getString("c_variant_name"));
					variant.setVariantDescription(rs.getString("c_variant_description"));
					variant.setVariantStatus(rs.getBoolean("c_variant_status"));
					variant.setVariantMileage(rs.getLong("c_mileage"));
					variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
					variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
					variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
					variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));

					model.setModelId(rs.getInt("c_model_id"));
					model.setModelName(rs.getString("c_model_name"));
					model.setModelDescription(rs.getString("c_model_description"));
					model.setModelStatus(rs.getBoolean("c_model_status"));
					model.setMake(make);

					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					model.setBodyType(bodyType);
					variant.setModel(model);

					fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
					fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
					fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
					fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
					variant.setFuelType(fuelType);

					transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
					transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
					transmissionType.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
					transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
					variant.setTransmissionType(transmissionType);

					color.setColorId(rs.getInt("c_color_id"));
					color.setColorName(rs.getString("c_color_name"));
					color.setColorDescription(rs.getString("c_color_description"));
					variant.setColor(color);

					variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
					variantImage.setVariantImageData(rs.getString("c_variant_image"));
					variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
					variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
					variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
				
					variantImageList.add(variantImage);
					
					variant.setVariantImage(variantImage);
					variant.setVariantImageList(variantImageList);
					
					variantList.add(variant);

				}
				response.setVariantList(variantList);
				response.setSuccessResponse();
				System.out.println("In getVariantById response:" + variantList);
			} else {
				System.out.println("In getVariantById Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In getVariant Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getVariantById e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public AddOnResponse getAddOns() {
		return getAddOns(new ArrayList<Integer>());
	}

	@Override
	public AddOnResponse getAddOns(List<Integer> addOnIds) {
		AddOnResponse response = new AddOnResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_add_on_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (addOnIds == null || addOnIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(addOnIds, CommonConstants.DELIMITER));
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);

			System.out.println("In AddOnResponse By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In AddOnResponse By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<AddOn> addOnList = new ArrayList<>();
				AddOn addOn = null;
				// Boolean b = false;
				while (rs.next()) {
					// b = false;
					// for (Variant v : variantList) {
					// if (v.getVariantId() == rs.getInt("c_variant_id")) {
					// b = true;
					// break;
					// }
					// }

					addOn = new AddOn();

					addOn.setAddOnId(rs.getInt("c_add_on_id"));
					addOn.setAddOnName(rs.getString("c_add_on_name"));
					addOn.setAddOnDescription(rs.getString("c_add_on_description"));
					addOn.setAddOnComputeStrategy(rs.getString("c_add_on_type"));
					addOn.setAddOnValue(rs.getDouble("c_add_on_value"));

					addOnList.add(addOn);

				}
				response.setAddOnList(addOnList);
				response.setSuccessResponse();
				System.out.println("In AddOnResponse response:" + addOnList);
			} else {
				System.out.println("In AddOnResponse Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In AddOnResponse e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public UserProfileResponse getUserProfiles(Integer userId) {
		UserProfileResponse response = new UserProfileResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_user_profile_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (userId == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, userId);
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getUserProfiles By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getUserProfiles By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<UserProfile> userProfileList = new ArrayList<>();
				UserProfile userProfile = null;
				// Boolean b = false;
				while (rs.next()) {
					// b = false;
					// for (Variant v : variantList) {
					// if (v.getVariantId() == rs.getInt("c_variant_id")) {
					// b = true;
					// break;
					// }
					// }

					userProfile = new UserProfile();

					userProfile.setUserProfileId(rs.getInt("c_user_profile_id"));
					userProfile.setUserId(rs.getInt("c_user_id"));
					userProfile.setUserProfileName(rs.getString("c_user_profile_name"));
					userProfile.setLicenceNumber(rs.getString("c_licence_number"));
					userProfile.setLicenceImage(rs.getBlob("c_licence_image"));
					userProfile.setIsActive(rs.getBoolean("c_is_active"));
					userProfile.setIsPrimary(rs.getBoolean("c_is_primary"));

					userProfileList.add(userProfile);

				}
				response.setUserProfileList(userProfileList);
				response.setSuccessResponse();
				System.out.println("In getUserProfiles response:" + userProfileList);
			} else {
				System.out.println("In getUserProfiles Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In AddOnResponse e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public PaymentInfoResponse getPaymentInfo(Integer userId) {
		PaymentInfoResponse response = new PaymentInfoResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_payment_info_v1dot0";
		Gson gson = new Gson();
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (userId == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, userId);
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getPaymentInfo By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getPaymentInfo By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<PaymentInfo> paymentInfoList = new ArrayList<>();
				PaymentInfo paymentInfo = null;
				// Boolean b = false;
				while (rs.next()) {
					// b = false;
					// for (Variant v : variantList) {
					// if (v.getVariantId() == rs.getInt("c_variant_id")) {
					// b = true;
					// break;
					// }
					// }

					paymentInfo = new PaymentInfo();

					paymentInfo.setPaymentInfoId(rs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(rs.getInt("c_user_id"));
					paymentInfo
							.setPaymentMethodInfo(gson.fromJson(rs.getString("c_payment_method_info"), CardInfo.class));
					paymentInfo.setIsActive(rs.getBoolean("c_is_active"));

					paymentInfoList.add(paymentInfo);

				}
				response.setPaymentInfoList(paymentInfoList);
				response.setSuccessResponse();
				System.out.println("In AddOnResponse response:" + paymentInfoList);
			} else {
				System.out.println("In AddOnResponse Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In AddOnResponse e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public PaymentInfoResponse addPaymentInfo(PaymentInfo paymentInfo) {
		PaymentInfoResponse paymentInfoResponse = new PaymentInfoResponse();
		paymentInfoResponse.setFailedResponse();

		System.out.println("In addPaymentInfo paymentInfo " + paymentInfo);
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_create_payment_info_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?)}";
		Gson gson = new Gson();
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);

			int i = 1;
			/* input parameters start */
			cStmt.setInt(i++, paymentInfo.getUserId());
			if (paymentInfo.getPaymentMethodInfo() == null) {
				paymentInfoResponse.setErrorCode("400");
				paymentInfoResponse.setErrorDescription("Payment Details Can't Be Empty");
				return paymentInfoResponse;
			}
			cStmt.setString(i++, gson.toJson(paymentInfo.getPaymentMethodInfo()));
			if (paymentInfo.getIsActive() == null) {
				paymentInfo.setIsActive(true);
			}
			cStmt.setBoolean(i++, paymentInfo.getIsActive());
			/* input parameters start */

			/* register output parameters start */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			/* register output parameters end */

			System.out.println("In addPaymentInfo Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In addPaymentInfo Calling DB procedure cStmt After {}" + cStmt + " i " + i);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				rs.next();
				paymentInfo.setPaymentInfoId(rs.getInt(1));
				List<PaymentInfo> paymentList = new ArrayList<>();
				paymentList.add(paymentInfo);
				paymentInfoResponse.setPaymentInfoList(paymentList);
				paymentInfoResponse.setSuccessResponse();
			} else {
				System.out.println(
						"In addPaymentInfo paymentInfoResponse: Else cStmt.getInt(i-2) " + cStmt.getInt(i - 2));
				System.out.println(
						"In addPaymentInfo paymentInfoResponse: Else cStmt.getInt(i-1) " + cStmt.getInt(i - 1));
				paymentInfoResponse.setErrorId(cStmt.getInt(i - 2));
				paymentInfoResponse.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In addPaymentInfo e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		System.out.println("In addUserProfile paymentInfoResponse:" + paymentInfoResponse);
		return paymentInfoResponse;

	}

	@Override
	public UserProfileResponse addUserProfile(UserProfile userProfile) {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		userProfileResponse.setFailedResponse();

		System.out.println("In addUserProfile userProfile " + userProfile);
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_create_user_profile_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);

			int i = 1;
			/* input parameters start */
			cStmt.setInt(i++, userProfile.getUserId());
			cStmt.setString(i++, userProfile.getUserProfileName());
			cStmt.setString(i++, userProfile.getLicenceNumber());
			cStmt.setBlob(i++, userProfile.getLicenceImage());
			if (userProfile.getIsActive() == null) {
				userProfile.setIsActive(true);
			}
			cStmt.setBoolean(i++, userProfile.getIsActive());
			if (userProfile.getIsPrimary() == null) {
				userProfile.setIsPrimary(false);
			}
			cStmt.setBoolean(i++, userProfile.getIsPrimary());
			/* input parameters start */

			/* register output parameters start */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			/* register output parameters end */

			System.out.println("In addUserProfile Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In addUserProfile Calling DB procedure cStmt After {}" + cStmt + " i " + i);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				rs.next();
				userProfile.setUserProfileId(rs.getInt(1));
				List<UserProfile> userProfileList = new ArrayList<>();
				userProfileList.add(userProfile);
				userProfileResponse.setUserProfileList(userProfileList);
				userProfileResponse.setSuccessResponse();
			} else {
				System.out.println(
						"In addUserProfile userProfileResponse: Else cStmt.getInt(i-2) " + cStmt.getInt(i - 2));
				System.out.println(
						"In addUserProfile userProfileResponse: Else cStmt.getInt(i-1) " + cStmt.getInt(i - 1));
				userProfileResponse.setErrorId(cStmt.getInt(i - 2));
				userProfileResponse.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In addUserProfile e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		System.out.println("In addUserProfile userProfileResponse:" + userProfileResponse);
		return userProfileResponse;

	}

	@Override
	public AddToCartResponse addToCart(AddToCartRequest addToCartRequest) {
		AddToCartResponse response = new AddToCartResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		ResultSet nRs = null;
		String procedureName = "proc_create_cart_entry_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, addToCartRequest.getUserId());
			cStmt.setInt(i++, addToCartRequest.getVariantId());
			cStmt.setBoolean(i++, true);
			cStmt.setTimestamp(i++, addToCartRequest.getFromDate());
			cStmt.setTimestamp(i++, addToCartRequest.getToDate());
			cStmt.setInt(i++, addToCartRequest.getPickupLocationId());
			cStmt.setInt(i++, addToCartRequest.getDropLocationId());
			cStmt.setDouble(i++, addToCartRequest.getPrice());
			List<Integer> addOnIds = addToCartRequest.getAddOnIds();

			if (addOnIds == null || addOnIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(addOnIds, CommonConstants.DELIMITER));
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getVariant Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getVariant Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Variant> variantList = new ArrayList<>();
				CartEntry cartEntry = new CartEntry();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				Location pickupLocation = null;
				Location dropLocation = null;
				// Boolean b = false;
				while (rs.next()) {
					// b = false;
					// for (Variant v : variantList) {
					// if (v.getVariantId() == rs.getInt("c_variant_id")) {
					// b = true;
					// break;
					// }
					// }

					cartEntry = new CartEntry();
					variant = new Variant();
					make = new Make();
					model = new Model();
					bodyType = new BodyType();
					fuelType = new FuelType();
					color = new Color();
					variantImage = new VariantImage();
					transmissionType = new TransmissionType();
					pickupLocation = new Location();
					dropLocation = new Location();

					variant.setVariantId(rs.getInt("c_variant_id"));
					variant.setVariantName(rs.getString("c_variant_name"));
					variant.setVariantDescription(rs.getString("c_variant_description"));
					variant.setVariantStatus(rs.getBoolean("c_variant_status"));
					variant.setVariantMileage(rs.getLong("c_mileage"));
					variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
					variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
					variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
					variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));

					model.setModelId(rs.getInt("c_model_id"));
					model.setModelName(rs.getString("c_model_name"));
					model.setModelDescription(rs.getString("c_model_description"));
					model.setModelStatus(rs.getBoolean("c_model_status"));
					model.setMake(make);

					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					model.setBodyType(bodyType);
					variant.setModel(model);

					fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
					fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
					fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
					fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
					variant.setFuelType(fuelType);

					transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
					transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
					transmissionType.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
					transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
					variant.setTransmissionType(transmissionType);

					color.setColorId(rs.getInt("c_color_id"));
					color.setColorName(rs.getString("c_color_name"));
					color.setColorDescription(rs.getString("c_color_description"));
					variant.setColor(color);

					variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
					variantImage.setVariantImageData(rs.getString("c_variant_image"));
					variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
					variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
					variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
					variant.setVariantImage(variantImage);

					pickupLocation.setLocationId(rs.getInt("pickup_location_id"));
					pickupLocation.setLocationName(rs.getString("pickup_location_name"));
					pickupLocation.setLocationGPS(rs.getString("pickup_location_gps"));
					pickupLocation.setLocationStateName(rs.getString("pickup_location_state_name"));
					pickupLocation.setLocationCountryName(rs.getString("pickup_location_country_name"));
					pickupLocation.setLocationStatus(rs.getBoolean("pickup_location_status"));

					dropLocation.setLocationId(rs.getInt("drop_location_id"));
					dropLocation.setLocationName(rs.getString("drop_location_name"));
					dropLocation.setLocationGPS(rs.getString("drop_location_gps"));
					dropLocation.setLocationStateName(rs.getString("drop_location_state_name"));
					dropLocation.setLocationCountryName(rs.getString("drop_location_country_name"));
					dropLocation.setLocationStatus(rs.getBoolean("drop_location_status"));

					cartEntry.setCartId(rs.getInt("c_cart_id"));
					cartEntry.setUserId(rs.getInt("c_user_id"));
					cartEntry.setIsActive(rs.getBoolean("c_is_active"));
					cartEntry.setFromDate(rs.getTimestamp("c_from_date"));
					cartEntry.setToDate(rs.getTimestamp("c_to_date"));
					cartEntry.setPrice(rs.getDouble("c_price"));
					cartEntry.setVariant(variant);
					cartEntry.setPickupLocation(pickupLocation);
					cartEntry.setDropLocation(dropLocation);

				}
				List<AddOn> addOnList = new ArrayList<>();
				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							AddOn addOn = new AddOn();

							addOn.setAddOnId(nRs.getInt("c_add_on_id"));
							addOn.setAddOnName(nRs.getString("c_add_on_name"));
							addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
							addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
							addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));

							addOnList.add(addOn);
						}
					}
				}
				cartEntry.setAddOnList(addOnList);
				response.setCartEntry(cartEntry);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In getVariant Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In getVariant Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getVariant e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	public CreateBookingResponse createBooking(CreateBookingRequest createBookingRequest) {
		CreateBookingResponse response = new CreateBookingResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		ResultSet nRs = null;
		ResultSet pRs = null;
		String procedureName = "proc_create_booking_info_v1dot0";
		Gson gson = new Gson();
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, createBookingRequest.getUserId());
			cStmt.setInt(i++, createBookingRequest.getPaymentMethodInfoId());
			cStmt.setNull(i++, Types.NULL);

			List<Integer> cartIds = createBookingRequest.getCartIds();
			if (cartIds == null || cartIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(cartIds, CommonConstants.DELIMITER));
			}

			List<Integer> userProfileIds = createBookingRequest.getUserProfileIds();
			if (userProfileIds == null || userProfileIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(userProfileIds, CommonConstants.DELIMITER));
			}

			List<Integer> promoCodeIds = createBookingRequest.getPromoCodeIds();
			if (promoCodeIds == null || promoCodeIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(promoCodeIds, CommonConstants.DELIMITER));
			}

			cStmt.setString(i++, BookingStatus.INITIATED.name());

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In createBooking Calling DB procedure cStmt Before{}" + cStmt);
			pRs = cStmt.executeQuery();
			System.out.println("In createBooking Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				BookingInfo bookingInfo = new BookingInfo();
				PaymentInfo paymentInfo = new PaymentInfo();
				UserDetails userInfo = new UserDetails();
				while (pRs.next()) {
					bookingInfo.setBookingInfoId(pRs.getInt("c_booking_info_id"));
					paymentInfo.setPaymentInfoId(pRs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(pRs.getInt("c_user_id"));
					paymentInfo.setPaymentMethodInfo(
							gson.fromJson(pRs.getString("c_payment_method_info"), CardInfo.class));
					paymentInfo.setIsActive(pRs.getBoolean("c_is_active"));
					userInfo.setUserId(pRs.getInt("c_user_id"));
					userInfo.setUserName(pRs.getString("c_user_name"));
					userInfo.setUserEmail(pRs.getString("c_user_email"));
					userInfo.setUserMobileNumber(pRs.getString("c_user_mobile_number"));
					bookingInfo.setStatus(pRs.getString("c_status"));
					bookingInfo.setPaymentInfo(paymentInfo);
					bookingInfo.setUser(userInfo);
				}

				CartEntry cartEntry = new CartEntry();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				Location pickupLocation = null;
				Location dropLocation = null;
				UserProfile userProfile = null;
				// Boolean b = false;

				Map<Integer, CartEntry> cartMap = new HashMap<Integer, CartEntry>();
				if (cStmt.getMoreResults()) {
					rs = cStmt.getResultSet();
					if (rs != null) {
						while (rs.next()) {
							// b = false;
							// for (Variant v : variantList) {
							// if (v.getVariantId() == rs.getInt("c_variant_id")) {
							// b = true;
							// break;
							// }
							// }

							cartEntry = new CartEntry();
							variant = new Variant();
							make = new Make();
							model = new Model();
							bodyType = new BodyType();
							fuelType = new FuelType();
							color = new Color();
							variantImage = new VariantImage();
							transmissionType = new TransmissionType();
							pickupLocation = new Location();
							dropLocation = new Location();
							userProfile = new UserProfile();

							variant.setVariantId(rs.getInt("c_variant_id"));
							variant.setVariantName(rs.getString("c_variant_name"));
							variant.setVariantDescription(rs.getString("c_variant_description"));
							variant.setVariantStatus(rs.getBoolean("c_variant_status"));
							variant.setVariantMileage(rs.getLong("c_mileage"));
							variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
							variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
							variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
							variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

							make.setMakeId(rs.getInt("c_make_id"));
							make.setMakeName(rs.getString("c_make_name"));
							make.setMakeDescription(rs.getString("c_make_description"));
							make.setMakeStatus(rs.getBoolean("c_make_status"));

							model.setModelId(rs.getInt("c_model_id"));
							model.setModelName(rs.getString("c_model_name"));
							model.setModelDescription(rs.getString("c_model_description"));
							model.setModelStatus(rs.getBoolean("c_model_status"));
							model.setMake(make);

							bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
							bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
							bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
							bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
							model.setBodyType(bodyType);
							variant.setModel(model);

							fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
							fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
							fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
							fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
							variant.setFuelType(fuelType);

							transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
							transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
							transmissionType
									.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
							transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
							variant.setTransmissionType(transmissionType);

							color.setColorId(rs.getInt("c_color_id"));
							color.setColorName(rs.getString("c_color_name"));
							color.setColorDescription(rs.getString("c_color_description"));
							variant.setColor(color);

							variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							variantImage.setVariantImageData(rs.getString("c_variant_image"));
							variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							variant.setVariantImage(variantImage);

							pickupLocation.setLocationId(rs.getInt("pickup_location_id"));
							pickupLocation.setLocationName(rs.getString("pickup_location_name"));
							pickupLocation.setLocationGPS(rs.getString("pickup_location_gps"));
							pickupLocation.setLocationStateName(rs.getString("pickup_location_state_name"));
							pickupLocation.setLocationCountryName(rs.getString("pickup_location_country_name"));
							pickupLocation.setLocationStatus(rs.getBoolean("pickup_location_status"));

							dropLocation.setLocationId(rs.getInt("drop_location_id"));
							dropLocation.setLocationName(rs.getString("drop_location_name"));
							dropLocation.setLocationGPS(rs.getString("drop_location_gps"));
							dropLocation.setLocationStateName(rs.getString("drop_location_state_name"));
							dropLocation.setLocationCountryName(rs.getString("drop_location_country_name"));
							dropLocation.setLocationStatus(rs.getBoolean("drop_location_status"));

							userProfile.setUserProfileId(rs.getInt("c_user_profile_id"));
							userProfile.setUserId(rs.getInt("c_user_id"));
							userProfile.setUserProfileName(rs.getString("c_user_profile_name"));
							userProfile.setLicenceNumber(rs.getString("c_licence_number"));
							userProfile.setLicenceImage(rs.getBlob("c_licence_image"));
							userProfile.setIsActive(rs.getBoolean("c_is_active"));
							userProfile.setIsPrimary(rs.getBoolean("c_is_primary"));

							cartEntry.setCartId(rs.getInt("c_cart_id"));
							cartEntry.setUserId(rs.getInt("c_user_id"));
							cartEntry.setIsActive(rs.getBoolean("c_is_active"));
							cartEntry.setFromDate(rs.getTimestamp("c_from_date"));
							cartEntry.setToDate(rs.getTimestamp("c_to_date"));
							cartEntry.setPrice(rs.getDouble("c_price"));
							cartEntry.setVariant(variant);
							cartEntry.setPickupLocation(pickupLocation);
							cartEntry.setDropLocation(dropLocation);
							cartEntry.setUserProfile(userProfile);
							cartEntry.setAddOnList(new ArrayList<>());

							cartMap.put(cartEntry.getCartId(), cartEntry);

						}
					}
				}

				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							AddOn addOn = new AddOn();

							addOn.setAddOnId(nRs.getInt("c_add_on_id"));
							addOn.setAddOnName(nRs.getString("c_add_on_name"));
							addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
							addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
							addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));

							Integer cartId = nRs.getInt("c_cart_id");

							if (cartMap.get(cartId) != null) {

								cartMap.get(cartId).getAddOnList().add(addOn);
							}
						}
						nRs.close();
						nRs = null;
					}
				}

				List<PromoCode> promoCodeList = new ArrayList<>();

				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							PromoCode promoCode = new PromoCode();

							promoCode.setPromoCodeId(nRs.getInt("c_promo_code_id"));
							promoCode.setPromoCodeName(nRs.getString("c_promo_code_name"));
							promoCode.setPromoCodeDescription(nRs.getString("c_promo_code_description"));
							promoCode.setPromoCodeType(nRs.getString("c_promo_code_type"));
							promoCode.setPromoValue(nRs.getDouble("c_promo_code_value"));

							promoCodeList.add(promoCode);
						}
					}
				}

				bookingInfo.setCartList(new ArrayList<>(cartMap.values()));
				bookingInfo.setPromoCodeList(promoCodeList);
				response.setBookingInfo(bookingInfo);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In createBooking e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, pRs, connection, procedureName);
		}
		return response;
	}

	@Override
	public CreateBookingResponse getBooking(Integer bookingId) {
		CreateBookingResponse response = new CreateBookingResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		ResultSet nRs = null;
		ResultSet pRs = null;
		String procedureName = "proc_get_booking_info_v1dot0";
		Gson gson = new Gson();
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, bookingId);

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In createBooking Calling DB procedure cStmt Before{}" + cStmt);
			pRs = cStmt.executeQuery();
			System.out.println("In createBooking Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				BookingInfo bookingInfo = new BookingInfo();
				PaymentInfo paymentInfo = new PaymentInfo();
				UserDetails userInfo = new UserDetails();
				while (pRs.next()) {
					bookingInfo.setBookingInfoId(pRs.getInt("c_booking_info_id"));
					bookingInfo.setPaymentInfoReferenceId(pRs.getString("c_payment_info_reference_id"));
					;
					paymentInfo.setPaymentInfoId(pRs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(pRs.getInt("c_user_id"));
					paymentInfo.setPaymentMethodInfo(
							gson.fromJson(pRs.getString("c_payment_method_info"), CardInfo.class));
					paymentInfo.setIsActive(pRs.getBoolean("c_is_active"));
					userInfo.setUserId(pRs.getInt("c_user_id"));
					userInfo.setUserName(pRs.getString("c_user_name"));
					userInfo.setUserEmail(pRs.getString("c_user_email"));
					userInfo.setUserType(pRs.getString("c_user_type"));
					userInfo.setUserMobileNumber(pRs.getString("c_user_mobile_number"));
					bookingInfo.setStatus(pRs.getString("c_status"));
					bookingInfo.setPaymentInfo(paymentInfo);
					bookingInfo.setUser(userInfo);
				}

				CartEntry cartEntry = new CartEntry();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				Location pickupLocation = null;
				Location dropLocation = null;
				UserProfile userProfile = null;
				// Boolean b = false;

				Map<Integer, CartEntry> cartMap = new HashMap<Integer, CartEntry>();
				if (cStmt.getMoreResults()) {
					rs = cStmt.getResultSet();
					if (rs != null) {
						while (rs.next()) {
							// b = false;
							// for (Variant v : variantList) {
							// if (v.getVariantId() == rs.getInt("c_variant_id")) {
							// b = true;
							// break;
							// }
							// }

							cartEntry = new CartEntry();
							variant = new Variant();
							make = new Make();
							model = new Model();
							bodyType = new BodyType();
							fuelType = new FuelType();
							color = new Color();
							variantImage = new VariantImage();
							transmissionType = new TransmissionType();
							pickupLocation = new Location();
							dropLocation = new Location();
							userProfile = new UserProfile();

							variant.setVariantId(rs.getInt("c_variant_id"));
							variant.setVariantName(rs.getString("c_variant_name"));
							variant.setVariantDescription(rs.getString("c_variant_description"));
							variant.setVariantStatus(rs.getBoolean("c_variant_status"));
							variant.setVariantMileage(rs.getLong("c_mileage"));
							variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
							variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
							variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
							variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

							make.setMakeId(rs.getInt("c_make_id"));
							make.setMakeName(rs.getString("c_make_name"));
							make.setMakeDescription(rs.getString("c_make_description"));
							make.setMakeStatus(rs.getBoolean("c_make_status"));

							model.setModelId(rs.getInt("c_model_id"));
							model.setModelName(rs.getString("c_model_name"));
							model.setModelDescription(rs.getString("c_model_description"));
							model.setModelStatus(rs.getBoolean("c_model_status"));
							model.setMake(make);

							bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
							bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
							bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
							bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
							model.setBodyType(bodyType);
							variant.setModel(model);

							fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
							fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
							fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
							fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
							variant.setFuelType(fuelType);

							transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
							transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
							transmissionType
									.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
							transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
							variant.setTransmissionType(transmissionType);

							color.setColorId(rs.getInt("c_color_id"));
							color.setColorName(rs.getString("c_color_name"));
							color.setColorDescription(rs.getString("c_color_description"));
							variant.setColor(color);

							variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							variantImage.setVariantImageData(rs.getString("c_variant_image"));
							variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							variant.setVariantImage(variantImage);

							pickupLocation.setLocationId(rs.getInt("pickup_location_id"));
							pickupLocation.setLocationName(rs.getString("pickup_location_name"));
							pickupLocation.setLocationGPS(rs.getString("pickup_location_gps"));
							pickupLocation.setLocationStateName(rs.getString("pickup_location_state_name"));
							pickupLocation.setLocationCountryName(rs.getString("pickup_location_country_name"));
							pickupLocation.setLocationStatus(rs.getBoolean("pickup_location_status"));

							dropLocation.setLocationId(rs.getInt("drop_location_id"));
							dropLocation.setLocationName(rs.getString("drop_location_name"));
							dropLocation.setLocationGPS(rs.getString("drop_location_gps"));
							dropLocation.setLocationStateName(rs.getString("drop_location_state_name"));
							dropLocation.setLocationCountryName(rs.getString("drop_location_country_name"));
							dropLocation.setLocationStatus(rs.getBoolean("drop_location_status"));

							userProfile.setUserProfileId(rs.getInt("c_user_profile_id"));
							userProfile.setUserId(rs.getInt("c_user_id"));
							userProfile.setUserProfileName(rs.getString("c_user_profile_name"));
							userProfile.setLicenceNumber(rs.getString("c_licence_number"));
							userProfile.setLicenceImage(rs.getBlob("c_licence_image"));
							userProfile.setIsActive(rs.getBoolean("c_is_active"));
							userProfile.setIsPrimary(rs.getBoolean("c_is_primary"));

							cartEntry.setCartId(rs.getInt("c_cart_id"));
							cartEntry.setUserId(rs.getInt("c_user_id"));
							cartEntry.setIsActive(rs.getBoolean("c_is_active"));
							cartEntry.setFromDate(rs.getTimestamp("c_from_date"));
							cartEntry.setToDate(rs.getTimestamp("c_to_date"));
							cartEntry.setPrice(rs.getDouble("c_price"));
							cartEntry.setVariant(variant);
							cartEntry.setPickupLocation(pickupLocation);
							cartEntry.setDropLocation(dropLocation);
							cartEntry.setUserProfile(userProfile);
							cartEntry.setAddOnList(new ArrayList<>());

							cartMap.put(cartEntry.getCartId(), cartEntry);

						}
					}
				}

				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							AddOn addOn = new AddOn();

							addOn.setAddOnId(nRs.getInt("c_add_on_id"));
							addOn.setAddOnName(nRs.getString("c_add_on_name"));
							addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
							addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
							addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));

							Integer cartId = nRs.getInt("c_cart_id");

							if (cartMap.get(cartId) != null) {

								cartMap.get(cartId).getAddOnList().add(addOn);
							}
						}
						nRs.close();
						nRs = null;
					}
				}

				List<PromoCode> promoCodeList = new ArrayList<>();

				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							PromoCode promoCode = new PromoCode();

							promoCode.setPromoCodeId(nRs.getInt("c_promo_code_id"));
							promoCode.setPromoCodeName(nRs.getString("c_promo_code_name"));
							promoCode.setPromoCodeDescription(nRs.getString("c_promo_code_description"));
							promoCode.setPromoCodeType(nRs.getString("c_promo_code_type"));
							promoCode.setPromoValue(nRs.getDouble("c_promo_code_value"));

							promoCodeList.add(promoCode);
						}
					}
				}

				bookingInfo.setCartList(new ArrayList<>(cartMap.values()));
				bookingInfo.setPromoCodeList(promoCodeList);
				response.setBookingInfo(bookingInfo);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In createBooking e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, pRs, connection, procedureName);
		}
		return response;
	}

	@Override
	public BaseResponse updateBookingStatus(Integer bookingReferenceId, String paymentReferenceId,
			BookingStatus status) {
		CreateBookingResponse response = new CreateBookingResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_update_booking_info_v1dot";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, bookingReferenceId);
			cStmt.setString(i++, paymentReferenceId);
			cStmt.setString(i++, status.name());

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In createBooking Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In createBooking Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In createBooking Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In createBooking e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public PromoCodeResponse getPromoCodes() {
		PromoCodeResponse response = new PromoCodeResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_promo_code_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);

			System.out.println("In PromoCodeResponse By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In PromoCodeResponse By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<PromoCode> promoCodeList = new ArrayList<>();
				// Boolean b = false;
				while (rs.next()) {
					// b = false;
					// for (Variant v : variantList) {
					// if (v.getVariantId() == rs.getInt("c_variant_id")) {
					// b = true;
					// break;
					// }
					// }

					PromoCode promoCode = new PromoCode();

					promoCode.setPromoCodeId(rs.getInt("c_promo_code_id"));
					promoCode.setPromoCodeName(rs.getString("c_promo_code_name"));
					promoCode.setPromoCodeDescription(rs.getString("c_promo_code_description"));
					promoCode.setPromoCodeType(rs.getString("c_promo_code_type"));
					promoCode.setPromoValue(rs.getDouble("c_promo_code_value"));

					promoCodeList.add(promoCode);

				}
				response.setPromoCodeList(promoCodeList);
				response.setSuccessResponse();
				System.out.println("In PromoCodeResponse response:" + promoCodeList);
			} else {
				System.out.println("In PromoCodeResponse Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In PromoCodeResponse e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}


	public BookingHistoryResponse getBookingHistory(Integer userId){
		BookingHistoryResponse response = new BookingHistoryResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		ResultSet nRs = null;
		ResultSet nRs2 = null;
		ResultSet pRs = null;
		String procedureName = "proc_get_booking_history_v1dot0";
		Gson gson = new Gson();
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, userId);

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getBookingHistory Calling DB procedure cStmt Before{}" + cStmt);
			pRs = cStmt.executeQuery();
			System.out.println("In getBookingHistory Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				Map<Integer,BookingInfo> bookingInfoMap = new HashMap<>();
				Map<Integer,List<PromoCode>> promoCodeList = new HashMap<>();

				while (pRs.next()) {
					BookingInfo bookingInfo = new BookingInfo();
					PaymentInfo paymentInfo = new PaymentInfo();
					UserDetails userInfo = new UserDetails();

					bookingInfo.setBookingInfoId(pRs.getInt("c_booking_info_id"));
					bookingInfo.setPaymentInfoReferenceId(pRs.getString("c_payment_info_reference_id"));
					;
					paymentInfo.setPaymentInfoId(pRs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(pRs.getInt("c_user_id"));
					paymentInfo.setPaymentMethodInfo(
							gson.fromJson(pRs.getString("c_payment_method_info"), CardInfo.class));
					paymentInfo.setIsActive(pRs.getBoolean("c_is_active"));
					userInfo.setUserId(pRs.getInt("c_user_id"));
					userInfo.setUserName(pRs.getString("c_user_name"));
					userInfo.setUserEmail(pRs.getString("c_user_email"));
					userInfo.setUserMobileNumber(pRs.getString("c_user_mobile_number"));
					bookingInfo.setStatus(pRs.getString("c_status"));
					bookingInfo.setPaymentInfo(paymentInfo);
					bookingInfo.setUser(userInfo);
					bookingInfoMap.putIfAbsent(bookingInfo.getBookingInfoId(),bookingInfo);
					promoCodeList.putIfAbsent(bookingInfo.getBookingInfoId(),new ArrayList<>());
				}
				pRs.close();

				CartEntry cartEntry = new CartEntry();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				Location pickupLocation = null;
				Location dropLocation = null;
				UserProfile userProfile = null;
				CartEntryHistoryResp cartHistoryResp = null;
				// Boolean b = false;

				Map<Integer, CartEntryHistoryResp> cartMap = new HashMap<Integer, CartEntryHistoryResp>();
				if (cStmt.getMoreResults()) {
					rs = cStmt.getResultSet();
					if (rs != null) {
						while (rs.next()) {
							// b = false;
							// for (Variant v : variantList) {
							// if (v.getVariantId() == rs.getInt("c_variant_id")) {
							// b = true;
							// break;
							// }
							// }

							cartEntry = new CartEntry();
							variant = new Variant();
							make = new Make();
							model = new Model();
							bodyType = new BodyType();
							fuelType = new FuelType();
							color = new Color();
							variantImage = new VariantImage();
							transmissionType = new TransmissionType();
							pickupLocation = new Location();
							dropLocation = new Location();
							userProfile = new UserProfile();

							Integer booking_info_id = rs.getInt("c_booking_info_id");
							variant.setVariantId(rs.getInt("c_variant_id"));
							variant.setVariantName(rs.getString("c_variant_name"));
							variant.setVariantDescription(rs.getString("c_variant_description"));
							variant.setVariantStatus(rs.getBoolean("c_variant_status"));
							variant.setVariantMileage(rs.getLong("c_mileage"));
							variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
							variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
							variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
							variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

							make.setMakeId(rs.getInt("c_make_id"));
							make.setMakeName(rs.getString("c_make_name"));
							make.setMakeDescription(rs.getString("c_make_description"));
							make.setMakeStatus(rs.getBoolean("c_make_status"));

							model.setModelId(rs.getInt("c_model_id"));
							model.setModelName(rs.getString("c_model_name"));
							model.setModelDescription(rs.getString("c_model_description"));
							model.setModelStatus(rs.getBoolean("c_model_status"));
							model.setMake(make);

							bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
							bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
							bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
							bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
							model.setBodyType(bodyType);
							variant.setModel(model);

							fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
							fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
							fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
							fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
							variant.setFuelType(fuelType);

							transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
							transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
							transmissionType
									.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
							transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
							variant.setTransmissionType(transmissionType);

							color.setColorId(rs.getInt("c_color_id"));
							color.setColorName(rs.getString("c_color_name"));
							color.setColorDescription(rs.getString("c_color_description"));
							variant.setColor(color);

							variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							variantImage.setVariantImageData(rs.getString("c_variant_image"));
							variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							variant.setVariantImage(variantImage);

							pickupLocation.setLocationId(rs.getInt("pickup_location_id"));
							pickupLocation.setLocationName(rs.getString("pickup_location_name"));
							pickupLocation.setLocationGPS(rs.getString("pickup_location_gps"));
							pickupLocation.setLocationStateName(rs.getString("pickup_location_state_name"));
							pickupLocation.setLocationCountryName(rs.getString("pickup_location_country_name"));
							pickupLocation.setLocationStatus(rs.getBoolean("pickup_location_status"));

							dropLocation.setLocationId(rs.getInt("drop_location_id"));
							dropLocation.setLocationName(rs.getString("drop_location_name"));
							dropLocation.setLocationGPS(rs.getString("drop_location_gps"));
							dropLocation.setLocationStateName(rs.getString("drop_location_state_name"));
							dropLocation.setLocationCountryName(rs.getString("drop_location_country_name"));
							dropLocation.setLocationStatus(rs.getBoolean("drop_location_status"));

							userProfile.setUserProfileId(rs.getInt("c_user_profile_id"));
							userProfile.setUserId(rs.getInt("c_user_id"));
							userProfile.setUserProfileName(rs.getString("c_user_profile_name"));
							userProfile.setLicenceNumber(rs.getString("c_licence_number"));
							userProfile.setLicenceImage(rs.getBlob("c_licence_image"));
							userProfile.setIsActive(rs.getBoolean("c_is_active"));
							userProfile.setIsPrimary(rs.getBoolean("c_is_primary"));

							cartEntry.setCartId(rs.getInt("c_cart_id"));
							cartEntry.setUserId(rs.getInt("c_user_id"));
							cartEntry.setIsActive(rs.getBoolean("c_is_active"));
							cartEntry.setFromDate(rs.getTimestamp("c_from_date"));
							cartEntry.setToDate(rs.getTimestamp("c_to_date"));
							cartEntry.setPrice(rs.getDouble("c_price"));
							cartEntry.setVariant(variant);
							cartEntry.setPickupLocation(pickupLocation);
							cartEntry.setDropLocation(dropLocation);
							cartEntry.setUserProfile(userProfile);
							cartEntry.setAddOnList(new ArrayList<>());

							cartMap.putIfAbsent(cartEntry.getCartId(), new CartEntryHistoryResp(cartEntry));
							cartMap.get(cartEntry.getCartId()).addToCartEntry(booking_info_id);

						}
					}
				}

				if (cStmt.getMoreResults()) {
					nRs = cStmt.getResultSet();
					if (nRs != null) {
						while (nRs.next()) {
							AddOn addOn = new AddOn();

							addOn.setAddOnId(nRs.getInt("c_add_on_id"));
							addOn.setAddOnName(nRs.getString("c_add_on_name"));
							addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
							addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
							addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));

							Integer cartId = nRs.getInt("c_cart_id");

							if (cartMap.get(cartId) != null) {

								cartMap.get(cartId).cartEntry.getAddOnList().add(addOn);
							}
						}
					}
				}


				if (cStmt.getMoreResults()) {
					nRs2 = cStmt.getResultSet();
					if (nRs2 != null) {
						while (nRs2.next()) {
							PromoCode promoCode = new PromoCode();

							promoCode.setPromoCodeId(nRs2.getInt("c_promo_code_id"));
							promoCode.setPromoCodeName(nRs2.getString("c_promo_code_name"));
							promoCode.setPromoCodeDescription(nRs2.getString("c_promo_code_description"));
							promoCode.setPromoCodeType(nRs2.getString("c_promo_code_type"));
							promoCode.setPromoValue(nRs2.getDouble("c_promo_code_value"));


							Integer bookingInfoId = nRs2.getInt("c_booking_info_id");

							if (promoCodeList.get(bookingInfoId) != null) {

								promoCodeList.get(bookingInfoId).add(promoCode);
							}
						}
					}
				}
				List<BookingInfo> bookingInfoList = new ArrayList<BookingInfo>();
				for(Integer bookingInfoId : bookingInfoMap.keySet()){
					BookingInfo bookingInfo = bookingInfoMap.get(bookingInfoId);
					List<CartEntry> cartEntryList = new ArrayList<>();
					for(Integer cartId : cartMap.keySet()){
						CartEntryHistoryResp cartEntryHistoryResp = cartMap.get(cartId);
						if(cartEntryHistoryResp.bookingInfoId.contains(bookingInfo.getBookingInfoId())){
							cartEntryList.add(cartEntryHistoryResp.cartEntry);
						}
					}
					bookingInfo.setCartList(cartEntryList);
					if (promoCodeList.get(bookingInfoId) != null) {
						bookingInfo.setPromoCodeList(promoCodeList.get(bookingInfoId));
					}
					else {
						bookingInfo.setPromoCodeList(new ArrayList<>());
					}
					bookingInfoList.add(bookingInfo);
				}
				response.setBookingInfoList(bookingInfoList);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In getBookingHistory Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In getBookingHistory Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getBookingHistory e :" + e + " :: ");
			e.printStackTrace();
		} finally {
			CommonUtils.closeConnection(cStmt, pRs, connection, procedureName);
		}
		return response;
	}

	@Override
	public BaseResponse updateCartDateInfo(List<Integer> cartIdList, List<Double> priceList,
										   Timestamp fromDate, Timestamp toDate) {
		BaseResponse response = new BaseResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_update_cart_date_info_v1dot";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (cartIdList == null || cartIdList.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(cartIdList, CommonConstants.DELIMITER));
			}

			if (priceList == null || priceList.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromDoubleList(priceList, CommonConstants.DELIMITER));
			}

			cStmt.setTimestamp(i++, fromDate);
			cStmt.setTimestamp(i++, toDate);

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In updateCartDateInfo Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In updateCartDateInfo Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In updateCartDateInfo Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In updateCartDateInfo Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In updateCartDateInfo e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	public BaseResponse finishBooking(FinishBookingRequest finishBookingRequest){
		BaseResponse response = new BaseResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_finish_booking_v1dot";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, finishBookingRequest.getBookingId());

			List<Integer> variantIdList = finishBookingRequest.getVariantInfoList().stream().map(variant -> variant.varaintId).toList();
			List<Long> kmDrivenList = finishBookingRequest.getVariantInfoList().stream().map(variant -> variant.variantKilometersDriven).toList();

			if (variantIdList == null || variantIdList.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromIntegerList(variantIdList, CommonConstants.DELIMITER));
			}

			if (kmDrivenList == null || kmDrivenList.isEmpty()) {
				cStmt.setString(i++, CommonConstants.EMPTY);
			} else {
				cStmt.setString(i++,
						CommonUtils.getDelimitedStringFromLongList(kmDrivenList, CommonConstants.DELIMITER));
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In finishBooking Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In finishBooking Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In finishBooking Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In finishBooking Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In finishBooking e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	public BaseResponse deleteVariant(Integer variantId){
		BaseResponse response = new BaseResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_delete_variant_v1dot";
		final String procedureCall = "{call " + procedureName + "(?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			cStmt.setInt(i++, variantId);

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In deleteVariant Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In deleteVariant Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In deleteVariant Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In deleteVariant Error in proc :{}" + cStmt.getInt(i - 1));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In deleteVariant e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}

	@Override
	public VariantResponse getVariantS(VariantRequest variantRequest) {
		VariantResponse response = new VariantResponse();
		response.setFailedResponse();
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_get_variant_s_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;
			List<Integer> idList = null;

			if (variantRequest == null || variantRequest.getVariantList() == null
					|| variantRequest.getVariantList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Variant v : variantRequest.getVariantList()) {
					idList.add(v.getVariantId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			cStmt.setString(i++, CommonConstants.ALL);

			if (variantRequest == null || variantRequest.getMakeList() == null
					|| variantRequest.getMakeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Make v : variantRequest.getMakeList()) {
					idList.add(v.getMakeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getModelList() == null
					|| variantRequest.getModelList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Model v : variantRequest.getModelList()) {
					idList.add(v.getModelId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getBodyTypeList() == null
					|| variantRequest.getBodyTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (BodyType v : variantRequest.getBodyTypeList()) {
					idList.add(v.getBodyTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getFuelTypeList() == null
					|| variantRequest.getFuelTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (FuelType v : variantRequest.getFuelTypeList()) {
					idList.add(v.getFuelTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getTransmissionTypeList() == null
					|| variantRequest.getTransmissionTypeList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (TransmissionType v : variantRequest.getTransmissionTypeList()) {
					idList.add(v.getTransmissionTypeId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getColorList() == null
					|| variantRequest.getColorList().isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				idList = new ArrayList<>();
				for (Color v : variantRequest.getColorList()) {
					idList.add(v.getColorId());
				}
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(idList, CommonConstants.DELIMITER));
			}

			if (variantRequest == null || variantRequest.getOffSet() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getOffSet());
			}

			if (variantRequest == null || variantRequest.getLimit() == null) {
				cStmt.setNull(i++, Types.NULL);
			} else {
				cStmt.setInt(i++, variantRequest.getLimit());
			}

			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In getVariant Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In getVariant Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<Variant> variantList = new ArrayList<>();
				Variant variant = null;
				Make make = null;
				Model model = null;
				BodyType bodyType = null;
				Color color = null;
				TransmissionType transmissionType = null;
				FuelType fuelType = null;
				VariantImage variantImage = null;
				Boolean b = false;
				List<VariantImage> variantImageList =null;
				while (rs.next()) {
					 b = false;
					 for (Variant v : variantList) {
						 if (v.getVariantId() == rs.getInt("c_variant_id")) {
							 b = true;
							 variantImage = new VariantImage();
							 variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
							 variantImage.setVariantImageData(rs.getString("c_variant_image"));
							 variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
							 variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
							 variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
							 variantImageList = v.getVariantImageList();
							 variantImageList.add(variantImage);
							 v.setVariantImageList(variantImageList);
							 break;
						 }
					 }
					 
					 if(b){
						 continue;
					 }
					variant = new Variant();
					make = new Make();
					model = new Model();
					bodyType = new BodyType();
					fuelType = new FuelType();
					color = new Color();
					variantImage = new VariantImage();
					variantImageList = new ArrayList();
					transmissionType = new TransmissionType();
					variant.setVariantId(rs.getInt("c_variant_id"));
					variant.setVariantName(rs.getString("c_variant_name"));
					variant.setVariantDescription(rs.getString("c_variant_description"));
					variant.setVariantStatus(rs.getBoolean("c_variant_status"));
					variant.setVariantMileage(rs.getLong("c_mileage"));
					variant.setVariantManufacturingDate(rs.getDate("c_manufacturing_date"));
					variant.setVariantPricePerKm(rs.getLong("c_price_per_kilometer"));
					variant.setVaraintKilometersDriven(rs.getLong("c_kilometers_driven"));
					variant.setVaraintNumberPlate(rs.getString("c_number_plate"));

					make.setMakeId(rs.getInt("c_make_id"));
					make.setMakeName(rs.getString("c_make_name"));
					make.setMakeDescription(rs.getString("c_make_description"));
					make.setMakeStatus(rs.getBoolean("c_make_status"));

					model.setModelId(rs.getInt("c_model_id"));
					model.setModelName(rs.getString("c_model_name"));
					model.setModelDescription(rs.getString("c_model_description"));
					model.setModelStatus(rs.getBoolean("c_model_status"));
					model.setMake(make);

					bodyType.setBodyTypeId(rs.getInt("c_body_type_id"));
					bodyType.setBodyTypeName(rs.getString("c_body_type_name"));
					bodyType.setBodyTypeDescription(rs.getString("c_body_type_description"));
					bodyType.setBodyTypeStatus(rs.getBoolean("c_body_type_status"));
					model.setBodyType(bodyType);
					variant.setModel(model);

					fuelType.setFuelTypeId(rs.getInt("c_fuel_type_id"));
					fuelType.setFuelTypeName(rs.getString("c_fuel_type_name"));
					fuelType.setFuelTypeDescription(rs.getString("c_fuel_type_description"));
					fuelType.setFuelTypeStatus(rs.getBoolean("c_fuel_type_status"));
					variant.setFuelType(fuelType);

					transmissionType.setTransmissionTypeId(rs.getInt("c_transmission_type_id"));
					transmissionType.setTransmissionTypeName(rs.getString("c_transmission_type_name"));
					transmissionType.setTransmissionTypeDescription(rs.getString("c_transmission_type_description"));
					transmissionType.setTransmissionTypeStatus(rs.getBoolean("c_transmission_type_status"));
					variant.setTransmissionType(transmissionType);

					color.setColorId(rs.getInt("c_color_id"));
					color.setColorName(rs.getString("c_color_name"));
					color.setColorDescription(rs.getString("c_color_description"));
					variant.setColor(color);

					variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
					variantImage.setVariantImageData(rs.getString("c_variant_image"));
					variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
					variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
					variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
					
					variantImage.setVariantImageId(rs.getInt("c_variant_image_id"));
					variantImage.setVariantImageData(rs.getString("c_variant_image"));
					variantImage.setVariantImageView(rs.getString("c_variant_image_view"));
					variantImage.setVariantImageDescription(rs.getString("c_variant_image_description"));
					variantImage.setVariantImageStatus(rs.getBoolean("c_variant_image_status"));
				
					variantImageList.add(variantImage);
					
					variant.setVariantImage(variantImage);
					variant.setVariantImageList(variantImageList);

					variantList.add(variant);

				}
				response.setVariantList(variantList);
				response.setSuccessResponse();
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In getVariant Error in proc :{}" + cStmt.getInt(i - 2));
				response.setErrorId(cStmt.getInt(i - 2));
				response.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In getVariant e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		return response;
	}


	@Override
	public VariantResponse createVariant(CreateVariantRequest createVariantRequest) {
		System.out.println("\n createVariant createVariantRequest *******************************************"+createVariantRequest);
		VariantResponse variantResponse= new VariantResponse();
		variantResponse.setFailedResponse();
		   
		Connection connection = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		String procedureName = "proc_create_variant_v1dot0";
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		try {
			
			LocalDate dateObj = LocalDate.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    String date = dateObj.format(formatter);
		    
			System.out.println("\n createVariant date *******************************************"+date); 
			
			
			connection = jdbcTemplate.getDataSource().getConnection();
			cStmt = connection.prepareCall(procedureCall);
			/* input parameters */
			int i = 1;

			if (createVariantRequest.getVariantName() !=null) {
				cStmt.setString(i++, createVariantRequest.getVariantName());
			} else {
				return variantResponse;
			}
			
			if (createVariantRequest.getVariantDescription() !=null) {
				cStmt.setString(i++, createVariantRequest.getVariantDescription());
			} else {
				cStmt.setNull(i++, Types.NULL);
			}
			
			cStmt.setString(i++, "1");
			

			System.out.println("\n createVariant Active *******************************************"+cStmt); 
			
			if (createVariantRequest.getModelId() !=null) {
				cStmt.setString(i++, createVariantRequest.getModelId().toString());
			} else {
				return variantResponse;
			}
			
			if (createVariantRequest.getColorId() !=null) {
				cStmt.setString(i++, createVariantRequest.getColorId().toString());
			} else {
				return variantResponse;
			}
			
			System.out.println("\n createVariant getColorId *******************************************"+cStmt); 

			if (createVariantRequest.getFuelTypeId() !=null) {
				cStmt.setString(i++, createVariantRequest.getFuelTypeId().toString());
			} else {
				return variantResponse;
			}
			
			if (createVariantRequest.getTransmissionTypeId() !=null) {
				cStmt.setString(i++, createVariantRequest.getTransmissionTypeId().toString());
			} else {
				return variantResponse;
			}
			
			System.out.println("\n createVariant getTransmissionTypeId *******************************************"+cStmt); 
			
			if (createVariantRequest.getVariantMileage() !=null) {
				cStmt.setString(i++, createVariantRequest.getVariantMileage().toString());
			} else {
				return variantResponse;
			}
			
			System.out.println("\n createVariant getVariantMileage *******************************************"+cStmt); 
			
			
			cStmt.setString(i++, date);
			
			System.out.println("\n createVariant date *******************************************"+cStmt); 
			
			
			if (createVariantRequest.getPricePerKilometer() !=null) {
				cStmt.setString(i++, createVariantRequest.getPricePerKilometer().toString());
			} else {
				return variantResponse;
			}
		
			if (createVariantRequest.getKilometersDriven() !=null) {
				cStmt.setString(i++, createVariantRequest.getKilometersDriven().toString());
			} else {
				return variantResponse;
			}
			
			if (createVariantRequest.getNumberPlate() !=null) {
				cStmt.setString(i++, createVariantRequest.getNumberPlate().toString());
			} else {
				return variantResponse;
			}

			
			if (createVariantRequest.getLocationId() !=null) {
				cStmt.setString(i++, createVariantRequest.getLocationId().toString());
			} else {
				return variantResponse;
			}

			List<String> imageUrlList = new ArrayList<>();
			List<String> imageTypeList = new ArrayList<>();
			
			System.out.println("In createVariant  imageUrlList" + imageUrlList.toString());
			System.out.println("In createVariant  imageTypeList" + imageTypeList.toString());

			if(createVariantRequest.getImageList() != null && !createVariantRequest.getImageList().isEmpty()) {
				for(VariantImageInfo imageObj:createVariantRequest.getImageList()) {
					imageUrlList.add(imageObj.getImageUri());
					imageTypeList.add(imageObj.getImageType());
				}
				System.out.println("In createVariant  imageUrlList" + imageUrlList.toString());
				System.out.println("In createVariant  imageTypeList" + imageTypeList.toString());
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(imageUrlList, CommonConstants.DELIMITER));
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromList(imageTypeList, CommonConstants.DELIMITER));
			}else {
				return variantResponse;
			}
			
			System.out.println("In createVariant  imageUrlList" + imageUrlList.toString());
			System.out.println("In createVariant  imageTypeList" + imageTypeList.toString());
			/* register output parameters */
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.INTEGER);
			cStmt.registerOutParameter(i++, Types.VARCHAR);
			System.out.println("In createVariant Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In createVariant Calling DB procedure cStmt After {}" + cStmt);

			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				variantResponse.setSuccessResponse();
				Integer vId = cStmt.getInt(i - 3);
				System.out.println("In createVariant vId" + vId);
				
				// System.out.println("In getVariant response:" + response);
			} else {
				System.out.println("In createVariant Error in proc :{}" + cStmt.getInt(i - 2));
				System.out.println("In createVariant Error in proc :{}" + cStmt.getInt(i - 1));
				variantResponse.setErrorId(cStmt.getInt(i - 2));
				variantResponse.setErrorDescription(
						CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i - 2)).getDescription().toString());
			}
		} catch (Exception e) {
			System.out.println("In createVariant e :" + e);
		} finally {
			CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
		}
		
		System.out.println("In createVariant variantResponse :" + variantResponse);
		return variantResponse;
	}
}
