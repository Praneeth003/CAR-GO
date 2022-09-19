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

import com.cargobackend.dao.IUserDAO;
import com.cargobackend.pojo.dao.cargo.CarMake;
import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.UserDetailsResponse;
import com.cargobackend.utils.CommonConstants;
import com.cargobackend.utils.CommonUtils;

@Repository("UserDAOImpl")
public class UserDAOImpl implements IUserDAO{
	
    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(@Qualifier("cargoDataSource") DataSource cargoDataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(cargoDataSource);
    }

    @Override
	public UserDetailsResponse registerUser(UserDetails userDetails) {
      	UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
    	userDetailsResponse.setFailedResponse();
    	
		System.out.println("In regiserUser userDetailsResponse......"+userDetailsResponse+" userDetails "+userDetails);
	    Connection connection = null;
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String procedureName = "proc_create_user_v1dot0";
        final String procedureCall = "{call " + procedureName + "(?,?,?,?,?,?)}";
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            cStmt = connection.prepareCall(procedureCall);
            
            int i=1;
            /* input parameters start */     
            cStmt.setString(i++,userDetails.getUserName());
            cStmt.setString(i++,userDetails.getUserName());
            cStmt.setString(i++,userDetails.getUserName());
            cStmt.setString(i++,userDetails.getUserName());
            /* input parameters start */  
            
            /* register output parameters start */
            cStmt.registerOutParameter(i++, Types.INTEGER);
            cStmt.registerOutParameter(i++, Types.VARCHAR);
            /* register output parameters end */
            
            System.out.println("In registerUser Calling DB procedure cStmt Before{}"+ cStmt);
            rs = cStmt.executeQuery();
            System.out.println("In registerUser Calling DB procedure cStmt After {}"+ cStmt+" i "+i);
            if (cStmt.getInt(i-2) == CommonConstants.DB_PROC_SUCCESS_RESPONSE) {
             	System.out.println("In registerUser userDetailsResponse:"+ userDetailsResponse);
            	return getUserDetails(userDetails);
            } else {
            	System.out.println("In registerUser userDetailsResponse: Else cStmt.getInt(i-2) "+ cStmt.getInt(i-2));
            	userDetailsResponse.setErrorId(cStmt.getInt(i-2));
            	userDetailsResponse.setErrorDescription(cStmt.getString(i-1));
            }
        } catch (Exception e) {
        	 System.out.println("In registerUser e :"+ e);
        } finally {
            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
        }
		return userDetailsResponse;

	}

	@Override
	public UserDetailsResponse getUserDetails(UserDetails userDetails) {	
    	UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
    	userDetailsResponse.setFailedResponse();
    	
		System.out.println("In getUserDetails userDetailsResponse......"+userDetailsResponse+" userDetails "+userDetails);
	    Connection connection = null;
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String procedureName = "proc_get_user_details_v1dot0";
        final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            cStmt = connection.prepareCall(procedureCall);
            
            int i=1;
            /* input parameters start */     
            
    		if(userDetails.getUserId() != null){
				cStmt.setInt(i++, userDetails.getUserId());
			}else{
				cStmt.setNull(i++, Types.NULL);
			}
     		if(userDetails.getUserName() != null){
				cStmt.setString(i++, userDetails.getUserName());
			}else{
				cStmt.setNull(i++, Types.NULL);
			}
            /* input parameters start */  
            
            /* register output parameters start */
            cStmt.registerOutParameter(i++, Types.INTEGER);
            cStmt.registerOutParameter(i++, Types.VARCHAR);
            /* register output parameters end */
            
            System.out.println("In getUserDetails Calling DB procedure cStmt Before{}"+ cStmt);
            rs = cStmt.executeQuery();
            System.out.println("In getUserDetails Calling DB procedure cStmt After {}"+ cStmt+" i "+i);
            if (cStmt.getInt(i-2) == CommonConstants.DB_PROC_SUCCESS_RESPONSE) {
            	UserDetails userDetails1 = null; 
                while (rs.next()) {
                	userDetails1 = new UserDetails();
                	userDetails1.setUserId(rs.getInt("c_user_id"));
                	userDetails1.setUserName(rs.getString("c_user_name"));
                	userDetails1.setUserEmail(rs.getString("c_user_email"));
                	userDetails1.setUserMobileNumber(rs.getString("c_user_mobile_number"));
                }
                if(userDetails1 == null) {
               	 	userDetailsResponse.setErrorDescription("User Not found");
                  	userDetailsResponse.setErrorId(404);
                }else {
                    userDetailsResponse.setUserDetails(userDetails1);
                	userDetailsResponse.setSuccessResponse();
                }
            } else {
            	 userDetailsResponse.setErrorDescription("Failed in fecthing user Details");
            	 System.out.println("In getUserDetails Error in proc :{}"+ cStmt.getString(4));
            }
        } catch (Exception e) {
        	userDetailsResponse.setErrorDescription("Failed in fetching User Details");
        	System.out.println("In getUserDetails e :"+ e);
        } finally {
            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
        }
		return userDetailsResponse;
	}

	@Override
	public UserDetailsResponse authenticateUser(UserDetails userDetails) {
    	UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
    	userDetailsResponse.setFailedResponse();
    	
		System.out.println("In getUserDetails userDetailsResponse......"+userDetailsResponse+" userDetails "+userDetails);
	    Connection connection = null;
        CallableStatement cStmt = null;
        ResultSet rs = null;
        String procedureName = "proc_get_user_details_v1dot0";
        final String procedureCall = "{call " + procedureName + "(?,?,?,?)}";
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            cStmt = connection.prepareCall(procedureCall);
            
            int i=1;
            /* input parameters start */     
//     		if(userDetails.getUserName() != null){
//				cStmt.setString(i++, userDetails.getUserName());
//			}else{
//				cStmt.setNull(i++, Types.NULL);
//			}
//    		if(userDetails.getUserPassword() != null){
//				cStmt.setString(i++, userDetails.getUserPassword());
//			}else{
//				cStmt.setNull(i++, Types.NULL);
//			}	
			cStmt.setString(i++, userDetails.getUserName());
    		cStmt.setString(i++, userDetails.getUserPassword());
            /* input parameters start */  
            
            /* register output parameters start */
            cStmt.registerOutParameter(i++, Types.INTEGER);
            cStmt.registerOutParameter(i++, Types.VARCHAR);
            /* register output parameters end */
            
            System.out.println("In getUserDetails Calling DB procedure cStmt Before{}"+ cStmt);
            rs = cStmt.executeQuery();
            System.out.println("In getUserDetails Calling DB procedure cStmt After {}"+ cStmt+" i "+i);
            if (cStmt.getInt(i-2) == CommonConstants.DB_PROC_SUCCESS_RESPONSE) {
            	UserDetails userDetails1 = null; 
                while (rs.next()) {
                	userDetails1 = new UserDetails();
                	userDetails1.setUserId(rs.getInt("c_user_id"));
                	userDetails1.setUserName(rs.getString("c_user_name"));
                	userDetails1.setUserEmail(rs.getString("c_user_email"));
                	userDetails1.setUserMobileNumber(rs.getString("c_user_mobile_number"));
                }
                userDetailsResponse.setUserDetails(userDetails1);
            	userDetailsResponse.setSuccessResponse();
            } else {
            	 userDetailsResponse.setErrorDescription("User Name and Password are not Matching");
            	 System.out.println("In getUserDetails Error in proc :{}"+ cStmt.getString(4));
            }
        } catch (Exception e) {
        	userDetailsResponse.setErrorDescription("Failed in fetching User Details");
        	System.out.println("In getUserDetails e :"+ e);
        } finally {
            CommonUtils.closeConnection(cStmt, rs, connection, procedureName);
        }
		return userDetailsResponse;
	}
	
	
	
}
