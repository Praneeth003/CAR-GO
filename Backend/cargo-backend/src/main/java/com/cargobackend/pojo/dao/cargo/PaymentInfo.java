package com.cargobackend.pojo.dao.cargo;


public class PaymentInfo {
	Integer paymentInfoId;
	Integer userId;
	CardInfo  paymentMethodInfo;
	Boolean isActive;
	
	
	public Integer getPaymentInfoId() {
		return paymentInfoId;
	}
	public void setPaymentInfoId(Integer paymentInfoId) {
		this.paymentInfoId = paymentInfoId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public CardInfo getPaymentMethodInfo() {
		return paymentMethodInfo;
	}
	public void setPaymentMethodInfo(CardInfo paymentMethodInfo) {
		this.paymentMethodInfo = paymentMethodInfo;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "PaymentInfo [paymentInfoId=" + paymentInfoId + ", userId=" + userId + ", paymentMethod=" + paymentMethodInfo
				+ ", isActive=" + isActive + "]";
	}
	

}
