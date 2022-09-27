package com.cargobackend.pojo.request;

import java.util.List;

public class BodyTypeRequest {

	List<Integer> bodyTypeIdList;
	List<String> bodyTypeNameList;
	public List<Integer> getBodyTypeIdList() {
		return bodyTypeIdList;
	}
	public void setBodyTypeIdList(List<Integer> bodyTypeIdList) {
		this.bodyTypeIdList = bodyTypeIdList;
	}
	public List<String> getBodyTypeNameList() {
		return bodyTypeNameList;
	}
	public void setBodyTypeNameList(List<String> bodyTypeNameList) {
		this.bodyTypeNameList = bodyTypeNameList;
	}
	@Override
	public String toString() {
		return "BodyTypeRequest [bodyTypeIdList=" + bodyTypeIdList + ", bodyTypeNameList=" + bodyTypeNameList
				+ ", getBodyTypeIdList()=" + getBodyTypeIdList() + ", getBodyTypeNameList()=" + getBodyTypeNameList()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
