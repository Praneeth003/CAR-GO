package com.cargobackend.pojo.response;


import com.cargobackend.pojo.dao.cargo.BookingInfo;
import com.cargobackend.pojo.response.common.BaseResponse;

import java.util.List;

public class BookingHistoryResponse extends BaseResponse {

	List<BookingInfo> bookingInfoList;

	public List<BookingInfo> getBookingInfoList() {
		return bookingInfoList;
	}

	public void setBookingInfoList(List<BookingInfo> bookingInfo) {
		this.bookingInfoList = bookingInfo;
	}

	@Override
	public String toString() {
		return "BookingHistoryResponse [bookingInfoList=" + bookingInfoList + "]";
	}

	
	

	

	
	
}
