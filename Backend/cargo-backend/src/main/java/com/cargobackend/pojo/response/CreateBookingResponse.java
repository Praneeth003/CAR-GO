package com.cargobackend.pojo.response;


import com.cargobackend.pojo.dao.cargo.BookingInfo;
import com.cargobackend.pojo.response.common.BaseResponse;

public class CreateBookingResponse extends BaseResponse {

	BookingInfo bookingInfo;

	public BookingInfo getBookingInfo() {
		return bookingInfo;
	}

	public void setBookingInfo(BookingInfo bookingInfo) {
		this.bookingInfo = bookingInfo;
	}

	@Override
	public String toString() {
		return "CreateBookingResponse [bookingInfo=" + bookingInfo + "]";
	}

	
	

	

	
	
}
