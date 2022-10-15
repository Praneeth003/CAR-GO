package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.PaymentInfo;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.response.common.BaseResponse;

public class PaymentInfoResponse extends BaseResponse {

	List<PaymentInfo> paymentInfoList ;

	public List<PaymentInfo> getPaymentInfoList() {
		return paymentInfoList;
	}

	public void setPaymentInfoList(List<PaymentInfo> paymentInfoList) {
		this.paymentInfoList = paymentInfoList;
	}

	@Override
	public String toString() {
		return "PaymentInfoResponse [paymentInfoList=" + paymentInfoList + "]";
	}

	

	
	
}
