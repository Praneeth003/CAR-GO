package com.cargobackend.pojo.dao.cargo;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.AddOn.AddOnComputeStrategy;
import com.cargobackend.pojo.dao.user.UserDetails;

public class BookingInfo {
	
	public enum BookingStatus {
		PENDING,
		SUCCESS,
		FAILURE,
		CANCELLED,
		INITIATED
	}
	
	Integer bookingInfoId;
	UserDetails user;
	PaymentInfo paymentInfo;
	String paymentInfoReferenceId;
	BookingStatus status;
	List<CartEntry> cartList;
	
	public Integer getBookingInfoId() {
		return bookingInfoId;
	}
	public void setBookingInfoId(Integer bookingInfoId) {
		this.bookingInfoId = bookingInfoId;
	}
	public UserDetails getUser() {
		return user;
	}
	public void setUser(UserDetails user) {
		this.user = user;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public List<CartEntry> getCartList() {
		return cartList;
	}
	public void setCartList(List<CartEntry> cartList) {
		this.cartList = cartList;
	}
	public String getPaymentInfoReferenceId() {
		return paymentInfoReferenceId;
	}
	public void setPaymentInfoReferenceId(String paymentInfoReferenceId) {
		this.paymentInfoReferenceId = paymentInfoReferenceId;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	public void setStatus(String status) {
		BookingStatus bookingStatusEnum = null;
		for (BookingStatus b : BookingStatus.values()) {
            if (b.toString().equalsIgnoreCase(status)) {
            	bookingStatusEnum = b;
            }
        }
		this.status = bookingStatusEnum;
	}
	@Override
	public String toString() {
		return "BookingInfo [bookingInfoId=" + bookingInfoId + ", user=" + user + ", paymentInfo=" + paymentInfo
				+ ", paymentInfoReferenceId=" + paymentInfoReferenceId + ", status=" + status + ", cartList=" + cartList
				+ "]";
	}
	
	

}
