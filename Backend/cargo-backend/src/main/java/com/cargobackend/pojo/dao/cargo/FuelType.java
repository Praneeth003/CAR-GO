package com.cargobackend.pojo.dao.cargo;

public class FuelType {
	
	Integer fuelTypeId;
	String fuelTypeName;
	String fuelTypeDescription;
	Boolean fuelTypeStatus;
	
	public Integer getFuelTypeId() {
		return fuelTypeId;
	}
	public void setFuelTypeId(Integer fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}
	public String getFuelTypeName() {
		return fuelTypeName;
	}
	public void setFuelTypeName(String fuelTypeName) {
		this.fuelTypeName = fuelTypeName;
	}
	public String getFuelTypeDescription() {
		return fuelTypeDescription;
	}
	public void setFuelTypeDescription(String fuelTypeDescription) {
		this.fuelTypeDescription = fuelTypeDescription;
	}
	public Boolean getFuelTypeStatus() {
		return fuelTypeStatus;
	}
	public void setFuelTypeStatus(Boolean fuelTypeStatus) {
		this.fuelTypeStatus = fuelTypeStatus;
	}
	
	@Override
	public String toString() {
		return "FuelType [fuelTypeId=" + fuelTypeId + ", fuelTypeName=" + fuelTypeName + ", fuelTypeDescription="
				+ fuelTypeDescription + ", fuelTypeStatus=" + fuelTypeStatus + ", getFuelTypeId()=" + getFuelTypeId()
				+ ", getFuelTypeName()=" + getFuelTypeName() + ", getFuelTypeDescription()=" + getFuelTypeDescription()
				+ ", getFuelTypeStatus()=" + getFuelTypeStatus() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
