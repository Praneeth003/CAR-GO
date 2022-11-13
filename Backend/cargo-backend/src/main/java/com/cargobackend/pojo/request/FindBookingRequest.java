package com.cargobackend.pojo.request;

import java.sql.Timestamp;

public class FindBookingRequest {

	Integer bookingInfoId;
	String licenseLast4Digits;

	@Override
	public String toString() {
		return "FindBookingRequest{" +
				"bookingInfoId=" + bookingInfoId +
				", licenseLast4Digits='" + licenseLast4Digits + '\'' +
				'}';
	}

	public Integer getBookingInfoId() {
		return bookingInfoId;
	}

	public void setBookingInfoId(Integer bookingInfoId) {
		this.bookingInfoId = bookingInfoId;
	}

	public String getLicenseLast4Digits() {
		return licenseLast4Digits;
	}

	public void setLicenseLast4Digits(String licenseLast4Digits) {
		this.licenseLast4Digits = licenseLast4Digits;
	}
}
