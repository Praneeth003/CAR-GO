package com.cargobackend.pojo.dao.cargo;

import java.sql.Blob;

public class UserProfile {
	Integer userProfileId;
	Integer userId;
	String  userProfileName;
	String  licenceNumber;
	Blob  licenceImage;
	Boolean isActive;
	Boolean isPrimary;
	public Integer getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(Integer userProfileId) {
		this.userProfileId = userProfileId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserProfileName() {
		return userProfileName;
	}
	public void setUserProfileName(String userProfileName) {
		this.userProfileName = userProfileName;
	}
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}
	public Blob getLicenceImage() {
		return licenceImage;
	}
	public void setLicenceImage(Blob licenceImage) {
		this.licenceImage = licenceImage;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	@Override
	public String toString() {
		return "UserProfile [userProfileId=" + userProfileId + ", userId=" + userId + ", userProfileName="
				+ userProfileName + ", licenceNumber=" + licenceNumber + ", licenceImage=" + licenceImage
				+ ", isActive=" + isActive + ", isPrimary=" + isPrimary + "]";
	}
	

}
