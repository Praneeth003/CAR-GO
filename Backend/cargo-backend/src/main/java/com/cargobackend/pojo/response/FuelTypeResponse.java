package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.FuelType;
import com.cargobackend.pojo.response.common.BaseResponse;

public class FuelTypeResponse extends BaseResponse{

	List<FuelType> fuelTypeList;

	public List<FuelType> getFuelTypeList() {
		return fuelTypeList;
	}

	public void setFuelTypeList(List<FuelType> fuelTypeList) {
		this.fuelTypeList = fuelTypeList;
	}

	@Override
	public String toString() {
		return "FuelTypeResponse [fuelTypeList=" + fuelTypeList + ", getFuelTypeList()=" + getFuelTypeList()
				+ ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()="
				+ getErrorId() + ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
