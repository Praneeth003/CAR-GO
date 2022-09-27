package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.BodyType;
import com.cargobackend.pojo.response.common.BaseResponse;

public class BodyTypeResponse extends BaseResponse {

	List<BodyType> bodyTypeList;

	public List<BodyType> getBodyTypeList() {
		return bodyTypeList;
	}

	public void setBodyTypeList(List<BodyType> bodyTypeList) {
		this.bodyTypeList = bodyTypeList;
	}

	@Override
	public String toString() {
		return "BodyTypeResponse [bodyTypeList=" + bodyTypeList + ", getBodyTypeList()=" + getBodyTypeList()
				+ ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()="
				+ getErrorId() + ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
