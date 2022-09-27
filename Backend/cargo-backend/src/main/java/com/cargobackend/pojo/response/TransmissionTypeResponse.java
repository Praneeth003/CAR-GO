package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.TransmissionType;
import com.cargobackend.pojo.response.common.BaseResponse;

public class TransmissionTypeResponse extends BaseResponse{

	List<TransmissionType> transmissionTypeList;

	public List<TransmissionType> getTransmissionTypeList() {
		return transmissionTypeList;
	}

	public void setTransmissionTypeList(List<TransmissionType> transmissionTypeList) {
		this.transmissionTypeList = transmissionTypeList;
	}

	@Override
	public String toString() {
		return "TransmissionTypeResponse [transmissionTypeList=" + transmissionTypeList + ", getTransmissionTypeList()="
				+ getTransmissionTypeList() + ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode()
				+ ", getErrorId()=" + getErrorId() + ", getErrorDescription()=" + getErrorDescription()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
	
	
}
