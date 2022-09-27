package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.Make;
import com.cargobackend.pojo.response.common.BaseResponse;

public class MakeResponse extends BaseResponse{

	List<Make> makeList;

	public List<Make> getMakeList() {
		return makeList;
	}

	public void setMakeList(List<Make> makeList) {
		this.makeList = makeList;
	}

	@Override
	public String toString() {
		return "MakeResponse [makeList=" + makeList + ", getMakeList()=" + getMakeList() + ", getStatus()="
				+ getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()=" + getErrorId()
				+ ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
