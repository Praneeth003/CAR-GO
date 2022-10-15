package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.response.common.BaseResponse;

public class UserProfileResponse extends BaseResponse {

	List<UserProfile> userProfileList ;

	public List<UserProfile> getUserProfileList() {
		return userProfileList;
	}

	public void setUserProfileList(List<UserProfile> userProfileList) {
		this.userProfileList = userProfileList;
	}

	@Override
	public String toString() {
		return "UserProfileResponse [userProfileList=" + userProfileList + "]";
	}

	
	
}
