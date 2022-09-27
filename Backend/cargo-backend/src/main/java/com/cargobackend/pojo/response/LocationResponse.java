package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.Location;
import com.cargobackend.pojo.response.common.BaseResponse;

public class LocationResponse extends BaseResponse {

	List<Location> locationList;

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	@Override
	public String toString() {
		return "LocationResponse [locationList=" + locationList + ", getLocationList()=" + getLocationList()
				+ ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()="
				+ getErrorId() + ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
