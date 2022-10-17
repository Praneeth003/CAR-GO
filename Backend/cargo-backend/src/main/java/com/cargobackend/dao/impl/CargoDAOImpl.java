package com.cargobackend.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cargobackend.dao.ICargoDAO;
import com.cargobackend.pojo.dao.cargo.AddOn;
import com.cargobackend.pojo.dao.cargo.BodyType;
import com.cargobackend.pojo.dao.cargo.BookingInfo;
import com.cargobackend.pojo.dao.cargo.CarMake;
import com.cargobackend.pojo.dao.cargo.CardInfo;
import com.cargobackend.pojo.dao.cargo.CartEntry;
import com.cargobackend.pojo.dao.cargo.Color;
import com.cargobackend.pojo.dao.cargo.FuelType;
import com.cargobackend.pojo.dao.cargo.Location;
import com.cargobackend.pojo.dao.cargo.Make;
import com.cargobackend.pojo.dao.cargo.Model;
import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.TransmissionType;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;
import com.cargobackend.pojo.dao.cargo.VariantImage;
import com.cargobackend.pojo.dao.cargo.BookingInfo.BookingStatus;
import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.request.AddToCartRequest;
import com.cargobackend.pojo.request.BodyTypeRequest;
import com.cargobackend.pojo.request.ColorRequest;
import com.cargobackend.pojo.request.CreateBookingRequest;
import com.cargobackend.pojo.request.FuelTypeRequest;
import com.cargobackend.pojo.request.MakeRequest;
import com.cargobackend.pojo.request.ModelRequest;
import com.cargobackend.pojo.request.TransmissionTypeRequest;
import com.cargobackend.pojo.request.VariantRequest;
import com.cargobackend.pojo.response.AddOnResponse;
import com.cargobackend.pojo.response.AddToCartResponse;
import com.cargobackend.pojo.response.BodyTypeResponse;
import com.cargobackend.pojo.response.CarMakeResponse;
import com.cargobackend.pojo.response.ColorResponse;
import com.cargobackend.pojo.response.CreateBookingResponse;
import com.cargobackend.pojo.response.FuelTypeResponse;
import com.cargobackend.pojo.response.LocationResponse;
import com.cargobackend.pojo.response.MakeResponse;
import com.cargobackend.pojo.response.ModelResponse;
import com.cargobackend.pojo.response.PaymentInfoResponse;
import com.cargobackend.pojo.response.TransmissionTypeResponse;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.pojo.response.UserProfileResponse;
import com.cargobackend.pojo.response.VariantResponse;
import com.cargobackend.pojo.response.common.BaseResponse;
import com.cargobackend.utils.CommonConstants;
import com.cargobackend.utils.CommonUtils;
import com.google.gson.Gson;

@Repository("CargoDAOImpl")
public class CargoDAOImpl implements ICargoDAO {

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
//				Boolean b = false;
				while (rs.next()) {
//					b = false;
//					for (Variant v : variantList) {
//						if (v.getVariantId() == rs.getInt("c_variant_id")) {
//							b = true;
//							break;
//						}
//					}
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
					variant.setVariantImage(variantImage);

					variantList.add(variant);

				}
				response.setVariantList(variantList);
				response.setSuccessResponse();
//				System.out.println("In getVariant response:" + response);
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
//				Boolean b = false;
				while (rs.next()) {
//					b = false;
//					for (Variant v : variantList) {
//						if (v.getVariantId() == rs.getInt("c_variant_id")) {
//							b = true;
//							break;
//						}
//					}

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
					variant.setVariantImage(variantImage);

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
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(addOnIds, CommonConstants.DELIMITER));
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
//				Boolean b = false;
				while (rs.next()) {
//					b = false;
//					for (Variant v : variantList) {
//						if (v.getVariantId() == rs.getInt("c_variant_id")) {
//							b = true;
//							break;
//						}
//					}

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
//				Boolean b = false;
				while (rs.next()) {
//					b = false;
//					for (Variant v : variantList) {
//						if (v.getVariantId() == rs.getInt("c_variant_id")) {
//							b = true;
//							break;
//						}
//					}

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
			System.out.println("In AddOnResponse By Id Get DB Call Calling DB procedure cStmt Before{}" + cStmt);
			rs = cStmt.executeQuery();
			System.out.println("In AddOnResponse By Id Get DB Call Calling DB procedure cStmt After {}" + cStmt);
			if (cStmt.getInt(i - 2) == CommonConstants.HttpStatusCode.OK.getValue()) {
				List<PaymentInfo> paymentInfoList = new ArrayList<>();
				PaymentInfo paymentInfo = null;
//				Boolean b = false;
				while (rs.next()) {
//					b = false;
//					for (Variant v : variantList) {
//						if (v.getVariantId() == rs.getInt("c_variant_id")) {
//							b = true;
//							break;
//						}
//					}

					paymentInfo = new PaymentInfo();
					
					paymentInfo.setPaymentInfoId(rs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(rs.getInt("c_user_id"));
					paymentInfo.setPaymentMethodInfo(gson.fromJson(rs.getString("c_payment_method_info"), CardInfo.class));
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
	    	
			System.out.println("In addPaymentInfo paymentInfo "+paymentInfo);
		    Connection connection = null;
	        CallableStatement cStmt = null;
	        ResultSet rs = null;
	        String procedureName = "proc_create_payment_info_v1dot0";
	        final String procedureCall = "{call " + procedureName + "(?,?,?,?,?)}";
			Gson gson = new Gson();
	        try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            cStmt = connection.prepareCall(procedureCall);
	            
	            int i=1;
	            /* input parameters start */     
	            cStmt.setInt(i++,paymentInfo.getUserId());
	            if(paymentInfo.getPaymentMethodInfo() == null) {
	            	paymentInfoResponse.setErrorCode("400");
	            	paymentInfoResponse.setErrorDescription("Payment Details Can't Be Empty");
	            	return paymentInfoResponse;
	            }
	            cStmt.setString(i++,gson.toJson(paymentInfo.getPaymentMethodInfo()));
	            if(paymentInfo.getIsActive() == null) {
	            	paymentInfo.setIsActive(true);
	            }
	            cStmt.setBoolean(i++,paymentInfo.getIsActive());
	            /* input parameters start */  
	            
	            /* register output parameters start */
	            cStmt.registerOutParameter(i++, Types.INTEGER);
	            cStmt.registerOutParameter(i++, Types.VARCHAR);
	            /* register output parameters end */
	            
	            System.out.println("In addPaymentInfo Calling DB procedure cStmt Before{}"+ cStmt);
	            rs = cStmt.executeQuery();
	            System.out.println("In addPaymentInfo Calling DB procedure cStmt After {}"+ cStmt+" i "+i);
	            if (cStmt.getInt(i-2) == CommonConstants.HttpStatusCode.OK.getValue()) {
	            	rs.next();
	            	paymentInfo.setPaymentInfoId(rs.getInt(1));
	            	List<PaymentInfo> paymentList = new ArrayList<>();
	            	paymentList.add(paymentInfo);
	            	paymentInfoResponse.setPaymentInfoList(paymentList);
	            	paymentInfoResponse.setSuccessResponse();
	            } else {
	            	System.out.println("In addPaymentInfo paymentInfoResponse: Else cStmt.getInt(i-2) "+ cStmt.getInt(i-2));
	            	System.out.println("In addPaymentInfo paymentInfoResponse: Else cStmt.getInt(i-1) "+ cStmt.getInt(i-1));
	            	paymentInfoResponse.setErrorId(cStmt.getInt(i-2));
	            	paymentInfoResponse.setErrorDescription(CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i-2)).getDescription().toString());
	            }
	        } catch (Exception e) {
	        	 System.out.println("In addPaymentInfo e :"+ e);
	        } finally {
	            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
	        }
	     	System.out.println("In addUserProfile paymentInfoResponse:"+ paymentInfoResponse);
			return paymentInfoResponse;

		}

	 @Override
		public UserProfileResponse addUserProfile(UserProfile userProfile) {
		 	UserProfileResponse userProfileResponse = new UserProfileResponse();
	    	userProfileResponse.setFailedResponse();
	    	
			System.out.println("In addUserProfile userProfile "+userProfile);
		    Connection connection = null;
	        CallableStatement cStmt = null;
	        ResultSet rs = null;
	        String procedureName = "proc_create_user_profile_v1dot0";
	        final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?)}";
	        try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            cStmt = connection.prepareCall(procedureCall);
	            
	            int i=1;
	            /* input parameters start */     
	            cStmt.setInt(i++,userProfile.getUserId());
	            cStmt.setString(i++,userProfile.getUserProfileName());
	            cStmt.setString(i++,userProfile.getLicenceNumber());
	            cStmt.setBlob(i++,userProfile.getLicenceImage());
	            if(userProfile.getIsActive() == null) {
	            	userProfile.setIsActive(true);
	            }
	            cStmt.setBoolean(i++,userProfile.getIsActive());
	            if(userProfile.getIsPrimary() == null) {
	            	userProfile.setIsPrimary(false);
	            }
	            cStmt.setBoolean(i++,userProfile.getIsPrimary());
	            /* input parameters start */  
	            
	            /* register output parameters start */
	            cStmt.registerOutParameter(i++, Types.INTEGER);
	            cStmt.registerOutParameter(i++, Types.VARCHAR);
	            /* register output parameters end */
	            
	            System.out.println("In addUserProfile Calling DB procedure cStmt Before{}"+ cStmt);
	            rs = cStmt.executeQuery();
	            System.out.println("In addUserProfile Calling DB procedure cStmt After {}"+ cStmt+" i "+i);
	            if (cStmt.getInt(i-2) == CommonConstants.HttpStatusCode.OK.getValue()) {
	            	rs.next();
	            	userProfile.setUserProfileId(rs.getInt(1));
	            	List<UserProfile> userProfileList = new ArrayList<>();
	            	userProfileList.add(userProfile);
	            	userProfileResponse.setUserProfileList(userProfileList);
	            	userProfileResponse.setSuccessResponse();
	            } else {
	            	System.out.println("In addUserProfile userProfileResponse: Else cStmt.getInt(i-2) "+ cStmt.getInt(i-2));
	            	System.out.println("In addUserProfile userProfileResponse: Else cStmt.getInt(i-1) "+ cStmt.getInt(i-1));
	            	userProfileResponse.setErrorId(cStmt.getInt(i-2));
	            	userProfileResponse.setErrorDescription(CommonConstants.HttpStatusCode.getByValue(cStmt.getInt(i-2)).getDescription().toString());
	            }
	        } catch (Exception e) {
	        	 System.out.println("In addUserProfile e :"+ e);
	        } finally {
	            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
	        }
	     	System.out.println("In addUserProfile userProfileResponse:"+ userProfileResponse);
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
            cStmt.setBoolean(i++,true);
            cStmt.setTimestamp(i++, addToCartRequest.getFromDate());
			cStmt.setTimestamp(i++, addToCartRequest.getToDate());
			cStmt.setInt(i++, addToCartRequest.getPickupLocationId());
			cStmt.setInt(i++, addToCartRequest.getDropLocationId());
			cStmt.setDouble(i++, addToCartRequest.getPrice());
			List<Integer> addOnIds = addToCartRequest.getAddOnIds();
			
			if (addOnIds == null || addOnIds.isEmpty()) {
				cStmt.setString(i++, CommonConstants.ALL);
			} else {
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(addOnIds, CommonConstants.DELIMITER));
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
//					Boolean b = false;
				while (rs.next()) {
//						b = false;
//						for (Variant v : variantList) {
//							if (v.getVariantId() == rs.getInt("c_variant_id")) {
//								b = true;
//								break;
//							}
//						}

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
					cartEntry.setFromDate(rs.getString("c_from_date"));
					cartEntry.setToDate(rs.getString("c_to_date"));
					cartEntry.setPrice(rs.getDouble("c_price"));
					cartEntry.setVariant(variant);
					cartEntry.setPickupLocation(pickupLocation);
					cartEntry.setDropLocation(dropLocation);

				}
				List<AddOn> addOnList = new ArrayList<>();
				if(cStmt.getMoreResults()){
					nRs = cStmt.getResultSet();
                    if(nRs !=null){
                    	while(nRs.next()) {
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
//					System.out.println("In getVariant response:" + response);
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
		final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?,?,?)}";
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
				cStmt.setString(i++, CommonUtils.getDelimitedStringFromIntegerList(userProfileIds, CommonConstants.DELIMITER));
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
					paymentInfo.setPaymentMethodInfo(gson.fromJson(pRs.getString("c_payment_method_info"), CardInfo.class));
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
//					Boolean b = false;

				Map<Integer,CartEntry> cartMap = new HashMap<Integer,CartEntry>();
				if(cStmt.getMoreResults()){
					rs = cStmt.getResultSet();
                    if(rs !=null){
						while (rs.next()) {
		//						b = false;
		//						for (Variant v : variantList) {
		//							if (v.getVariantId() == rs.getInt("c_variant_id")) {
		//								b = true;
		//								break;
		//							}
		//						}
		
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
							cartEntry.setFromDate(rs.getString("c_from_date"));
							cartEntry.setToDate(rs.getString("c_to_date"));
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
				
				
				
				if(cStmt.getMoreResults()){
					nRs = cStmt.getResultSet();
                    if(nRs !=null){
                    	while(nRs.next()) {
                    		AddOn addOn = new AddOn();

        					addOn.setAddOnId(nRs.getInt("c_add_on_id"));
        					addOn.setAddOnName(nRs.getString("c_add_on_name"));
        					addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
        					addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
        					addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));
        					
        					Integer cartId = nRs.getInt("c_cart_id");
        					
        					if(cartMap.get(cartId) != null) {
        						
        						cartMap.get(cartId).getAddOnList().add(addOn);
        					}
                    	}
                    }
                }
				
				bookingInfo.setCartList(new ArrayList<>(cartMap.values()));
				response.setBookingInfo(bookingInfo);
				response.setSuccessResponse();
//					System.out.println("In getVariant response:" + response);
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
					bookingInfo.setPaymentInfoReferenceId(pRs.getString("c_payment_info_reference_id"));;
					paymentInfo.setPaymentInfoId(pRs.getInt("c_payment_info_id"));
					paymentInfo.setUserId(pRs.getInt("c_user_id"));
					paymentInfo.setPaymentMethodInfo(gson.fromJson(pRs.getString("c_payment_method_info"), CardInfo.class));
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
//					Boolean b = false;

				Map<Integer,CartEntry> cartMap = new HashMap<Integer,CartEntry>();
				if(cStmt.getMoreResults()){
					rs = cStmt.getResultSet();
                    if(rs !=null){
						while (rs.next()) {
		//						b = false;
		//						for (Variant v : variantList) {
		//							if (v.getVariantId() == rs.getInt("c_variant_id")) {
		//								b = true;
		//								break;
		//							}
		//						}
		
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
							cartEntry.setFromDate(rs.getString("c_from_date"));
							cartEntry.setToDate(rs.getString("c_to_date"));
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
				
				
				
				if(cStmt.getMoreResults()){
					nRs = cStmt.getResultSet();
                    if(nRs !=null){
                    	while(nRs.next()) {
                    		AddOn addOn = new AddOn();

        					addOn.setAddOnId(nRs.getInt("c_add_on_id"));
        					addOn.setAddOnName(nRs.getString("c_add_on_name"));
        					addOn.setAddOnDescription(nRs.getString("c_add_on_description"));
        					addOn.setAddOnComputeStrategy(nRs.getString("c_add_on_type"));
        					addOn.setAddOnValue(nRs.getDouble("c_add_on_value"));
        					
        					Integer cartId = nRs.getInt("c_cart_id");
        					
        					if(cartMap.get(cartId) != null) {
        						
        						cartMap.get(cartId).getAddOnList().add(addOn);
        					}
                    	}
                    }
                }
				
				bookingInfo.setCartList(new ArrayList<>(cartMap.values()));
				response.setBookingInfo(bookingInfo);
				response.setSuccessResponse();
//					System.out.println("In getVariant response:" + response);
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
	public BaseResponse updateBookingStatus(Integer bookingReferenceId, String paymentReferenceId, BookingStatus status) {
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
//					System.out.println("In getVariant response:" + response);
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
	
	

}
