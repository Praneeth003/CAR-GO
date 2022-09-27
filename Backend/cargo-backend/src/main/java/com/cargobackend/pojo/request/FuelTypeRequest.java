package com.cargobackend.pojo.request;

import java.util.List;

public class FuelTypeRequest {

	List<Integer> fuelTypeIdList;
	List<String> fuelTypeNameList;
	public List<Integer> getFuelTypeIdList() {
		return fuelTypeIdList;
	}
	public void setFuelTypeIdList(List<Integer> fuelTypeIdList) {
		this.fuelTypeIdList = fuelTypeIdList;
	}
	public List<String> getFuelTypeNameList() {
		return fuelTypeNameList;
	}
	public void setFuelTypeNameList(List<String> fuelTypeNameList) {
		this.fuelTypeNameList = fuelTypeNameList;
	}
	@Override
	public String toString() {
		return "FuelTypeRequest [fuelTypeIdList=" + fuelTypeIdList + ", fuelTypeNameList=" + fuelTypeNameList
				+ ", getFuelTypeIdList()=" + getFuelTypeIdList() + ", getFuelTypeNameList()=" + getFuelTypeNameList()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
