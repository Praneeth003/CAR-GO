package com.cargobackend.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	public static void closeConnection(CallableStatement cStmt, ResultSet rs, Connection connection,
			String procedureName) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			System.out.println("Exception in closing ResultSet for: procedureName "+procedureName+"e "+ e);
		}
		try {
			if (cStmt != null)
				cStmt.close();
		} catch (Exception e) {
			System.out.println("Exception in closing ResultSet for: procedureName "+procedureName+"e "+ e);
		}
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			System.out.println("Exception in closing ResultSet for: procedureName "+procedureName+"e "+ e);
		}
	}
}
