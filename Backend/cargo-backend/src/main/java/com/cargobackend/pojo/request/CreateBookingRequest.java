package com.cargobackend.pojo.request;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.BookingInfo;

public class CreateBookingRequest {

	Integer userId;
	Integer paymentMethodInfoId;
	List<Integer> cartIds;
	List<Integer> userProfileIds;
	List<Integer> promoCodeIds;
	BookingInfo.BookingStatus bookingStatus;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPaymentMethodInfoId() {
		return paymentMethodInfoId;
	}
	public void setPaymentMethodInfoId(Integer paymentMethodInfoId) {
		this.paymentMethodInfoId = paymentMethodInfoId;
	}
	public List<Integer> getCartIds() {
		return cartIds;
	}
	public void setCartIds(List<Integer> cartIds) {
		this.cartIds = cartIds;
	}
	public List<Integer> getUserProfileIds() {
		return userProfileIds;
	}
	public void setUserProfileIds(List<Integer> userProfileIds) {
		this.userProfileIds = userProfileIds;
	}
	public BookingInfo.BookingStatus getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(BookingInfo.BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public List<Integer> getPromoCodeIds() {
		return promoCodeIds;
	}

	public void setPromoCodeIds(List<Integer> promoCodeIds) {
		this.promoCodeIds = promoCodeIds;
	}

	@Override
	public String toString() {
		return "CreateBooking [userId=" + userId + ", paymentMethodInfoId=" + paymentMethodInfoId
				+ ", cartIds=" + cartIds + ", userProfileIds="
				+ userProfileIds + ", bookingStatus=" + bookingStatus +  ", promoCodeIds=" + promoCodeIds + "]";
	}
	
	
	
	
}
