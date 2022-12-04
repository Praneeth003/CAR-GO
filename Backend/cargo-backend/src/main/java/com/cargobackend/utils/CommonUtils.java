package com.cargobackend.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

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



	public static String getDelimitedStringFromList(List<String> values, String delimiter) {
		if (values == null || values.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (String value : values) {
			if (firstTime) {
				firstTime = false;
			} else {
				builder.append(delimiter);
			}
			builder.append(value);
		}
		System.out.println("\n getDelimitedStringFromList builder "+builder.toString());
		return builder.toString();
	}
	
	public static String getDelimitedStringFromIntegerList(List<Integer> values, String delimiter) {
		if (values == null || values.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (Integer value : values) {
			if (firstTime) {
				firstTime = false;
			} else {
				builder.append(delimiter);
			}
			builder.append(value.toString());
		}
		System.out.println("\n getDelimitedStringFromIntegerList builder "+builder.toString());
		return builder.toString();
	}

	public static String getDelimitedStringFromDoubleList(List<Double> values, String delimiter) {
		if (values == null || values.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (Double value : values) {
			if (firstTime) {
				firstTime = false;
			} else {
				builder.append(delimiter);
			}
			builder.append(value.toString());
		}
		System.out.println("\n getDelimitedStringFromIntegerList builder "+builder.toString());
		return builder.toString();
	}

	public static String getDelimitedStringFromLongList(List<Long> values, String delimiter) {
		if (values == null || values.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		boolean firstTime = true;
		for (Long value : values) {
			if (firstTime) {
				firstTime = false;
			} else {
				builder.append(delimiter);
			}
			builder.append(value.toString());
		}
		System.out.println("\n getDelimitedStringFromIntegerList builder "+builder.toString());
		return builder.toString();
	}
}
