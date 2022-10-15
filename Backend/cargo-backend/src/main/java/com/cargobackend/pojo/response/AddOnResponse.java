package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.AddOn;
import com.cargobackend.pojo.response.common.BaseResponse;

public class AddOnResponse extends BaseResponse {

	List<AddOn> addOnList ;

	public List<AddOn> getAddOnList() {
		return addOnList;
	}

	public void setAddOnList(List<AddOn> addOnList) {
		this.addOnList = addOnList;
	}

	@Override
	public String toString() {
		return "AddOnResponse [addOnList=" + addOnList + "]";
	}

	
	
}
