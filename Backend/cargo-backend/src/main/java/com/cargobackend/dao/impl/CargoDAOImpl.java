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

}
