package com.cargobackend.pojo.request;

import java.sql.Timestamp;
import java.util.List;

public class UpdateBookingRequest {

	Integer bookingInfoId;
	Timestamp fromDate;
	Timestamp toDate;

	@Override
	public String toString() {
		return "UpdateBookingRequest{" +
				"bookingInfoId=" + bookingInfoId +
				", fromDate=" + fromDate +
				", toDate=" + toDate +
				'}';
	}

	public Integer getBookingInfoId() {
		return bookingInfoId;
	}

	public void setBookingInfoId(Integer bookingInfoId) {
		this.bookingInfoId = bookingInfoId;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
}
