package com.cargobackend.pojo.dao.cargo;

public class Location {

	Integer locationId;
	String locationName;
	String locationGPS;
	String locationStateName;
	String locationCountryName;
	Boolean locationStatus;
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLocationGPS() {
		return locationGPS;
	}
	public void setLocationGPS(String locationGPS) {
		this.locationGPS = locationGPS;
	}
	public String getLocationStateName() {
		return locationStateName;
	}
	public void setLocationStateName(String locationStateName) {
		this.locationStateName = locationStateName;
	}
	public String getLocationCountryName() {
		return locationCountryName;
	}
	public void setLocationCountryName(String locationCountryName) {
		this.locationCountryName = locationCountryName;
	}
	public Boolean getLocationStatus() {
		return locationStatus;
	}
	public void setLocationStatus(Boolean locationStatus) {
		this.locationStatus = locationStatus;
	}
	
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", locationGPS=" + locationGPS
				+ ", locationStateName=" + locationStateName + ", locationCountryName=" + locationCountryName
				+ ", locationStatus=" + locationStatus + ", getLocationId()=" + getLocationId() + ", getLocationName()="
				+ getLocationName() + ", getLocationGPS()=" + getLocationGPS() + ", getLocationStateName()="
				+ getLocationStateName() + ", getLocationCountryName()=" + getLocationCountryName()
				+ ", getLocationStatus()=" + getLocationStatus() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	
}
