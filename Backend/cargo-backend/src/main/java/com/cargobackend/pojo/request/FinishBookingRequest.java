package com.cargobackend.pojo.request;

import java.sql.Timestamp;
import java.util.List;

public class FinishBookingRequest {

	Integer bookingId;
	List<VariantInfo> variantInfoList;
	Timestamp closingDate;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public List<VariantInfo> getVariantInfoList() {
		return variantInfoList;
	}

	public void setVariantInfoList(List<VariantInfo> variantInfoList) {
		this.variantInfoList = variantInfoList;
	}

	public Timestamp getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Timestamp closingDate) {
		this.closingDate = closingDate;
	}

	@Override
	public String toString() {
		return "FinishBookingRequest{" +
				"bookingId=" + bookingId +
				", variantInfoList=" + variantInfoList +
				", closingDate=" + closingDate +
				'}';
	}
}
