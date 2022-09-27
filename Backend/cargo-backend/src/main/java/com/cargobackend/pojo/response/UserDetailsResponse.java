package com.cargobackend.pojo.response;

import com.cargobackend.pojo.dao.user.UserDetails;
import com.cargobackend.pojo.response.common.BaseResponse;

public class UserDetailsResponse extends BaseResponse {

	UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "UserDetailsResponse [userDetails=" + userDetails + ", getUserDetails()=" + getUserDetails()
				+ ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()="
				+ getErrorId() + ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
