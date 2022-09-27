package com.cargobackend.pojo.request;

import java.util.List;

public class MakeRequest {

	List<Integer> makeIdList;
	List<String> makeNameList;
	public List<Integer> getMakeIdList() {
		return makeIdList;
	}
	public void setMakeIdList(List<Integer> makeIdList) {
		this.makeIdList = makeIdList;
	}
	public List<String> getMakeNameList() {
		return makeNameList;
	}
	public void setMakeNameList(List<String> makeNameList) {
		this.makeNameList = makeNameList;
	}
	@Override
	public String toString() {
		return "MakeRequest [makeIdList=" + makeIdList + ", makeNameList=" + makeNameList + ", getMakeIdList()="
				+ getMakeIdList() + ", getMakeNameList()=" + getMakeNameList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
