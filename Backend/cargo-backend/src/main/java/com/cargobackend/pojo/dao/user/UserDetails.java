package com.cargobackend.pojo.dao.user;

public class UserDetails {

	Integer userId;
	
	String userName;
	
	String userType;
	
	String userVerifictionType;
	
	String userDescription;
	
	String userEmail;
	
	String userMobileNumber;
	
	Boolean userStatus;
	
	Integer authId;
	
	String userPassword;
	
	String userAuthToken;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserVerifictionType() {
		return userVerifictionType;
	}

	public void setUserVerifictionType(String userVerifictionType) {
		this.userVerifictionType = userVerifictionType;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public Boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserAuthToken() {
		return userAuthToken;
	}

	public void setUserAuthToken(String userAuthToken) {
		this.userAuthToken = userAuthToken;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userType=" + userType + ", userVerifictionType="
				+ userVerifictionType + ", userDescription=" + userDescription + ", userEmail=" + userEmail
				+ ", userMobileNumber=" + userMobileNumber + ", userStatus=" + userStatus + ", authId=" + authId
				+ ", userPassword=" + userPassword + ", userAuthToken=" + userAuthToken + "]";
	}
	
}