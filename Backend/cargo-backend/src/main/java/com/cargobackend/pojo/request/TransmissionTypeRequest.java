package com.cargobackend.pojo.request;

import java.util.List;

public class TransmissionTypeRequest {

	List<Integer> transmissionTypeIdList;
	List<String> transmissionTypeNameList;
	public List<Integer> getTransmissionTypeIdList() {
		return transmissionTypeIdList;
	}
	public void setTransmissionTypeIdList(List<Integer> transmissionTypeIdList) {
		this.transmissionTypeIdList = transmissionTypeIdList;
	}
	public List<String> getTransmissionTypeNameList() {
		return transmissionTypeNameList;
	}
	public void setTransmissionTypeNameList(List<String> transmissionTypeNameList) {
		this.transmissionTypeNameList = transmissionTypeNameList;
	}
	@Override
	public String toString() {
		return "TransmissionTypeRequest [transmissionTypeIdList=" + transmissionTypeIdList
				+ ", transmissionTypeNameList=" + transmissionTypeNameList + ", getTransmissionTypeIdList()="
				+ getTransmissionTypeIdList() + ", getTransmissionTypeNameList()=" + getTransmissionTypeNameList()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
