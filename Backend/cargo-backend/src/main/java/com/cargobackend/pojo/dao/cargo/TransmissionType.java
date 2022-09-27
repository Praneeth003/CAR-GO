package com.cargobackend.pojo.dao.cargo;

public class TransmissionType {
	
	Integer transmissionTypeId;
	String transmissionTypeName;
	String transmissionTypeDescription;
	Boolean transmissionTypeStatus;
	
	public Integer getTransmissionTypeId() {
		return transmissionTypeId;
	}
	public void setTransmissionTypeId(Integer transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}
	public String getTransmissionTypeName() {
		return transmissionTypeName;
	}
	public void setTransmissionTypeName(String transmissionTypeName) {
		this.transmissionTypeName = transmissionTypeName;
	}
	public String getTransmissionTypeDescription() {
		return transmissionTypeDescription;
	}
	public void setTransmissionTypeDescription(String transmissionTypeDescription) {
		this.transmissionTypeDescription = transmissionTypeDescription;
	}
	public Boolean getTransmissionTypeStatus() {
		return transmissionTypeStatus;
	}
	public void setTransmissionTypeStatus(Boolean transmissionTypeStatus) {
		this.transmissionTypeStatus = transmissionTypeStatus;
	}
	
	@Override
	public String toString() {
		return "TransmissionType [transmissionTypeId=" + transmissionTypeId + ", transmissionTypeName="
				+ transmissionTypeName + ", transmissionTypeDescription=" + transmissionTypeDescription
				+ ", transmissionTypeStatus=" + transmissionTypeStatus + ", getTransmissionTypeId()="
				+ getTransmissionTypeId() + ", getTransmissionTypeName()=" + getTransmissionTypeName()
				+ ", getTransmissionTypeDescription()=" + getTransmissionTypeDescription()
				+ ", getTransmissionTypeStatus()=" + getTransmissionTypeStatus() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
