package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.CarMake;
import com.cargobackend.pojo.response.common.BaseResponse;

public class CarMakeResponse extends BaseResponse{

	List<CarMake> carmakeList;

	public List<CarMake> getCarmakeList() {
		return carmakeList;
	}

	public void setCarmakeList(List<CarMake> carmakeList) {
		this.carmakeList = carmakeList;
	}

	@Override
	public String toString() {
		return "CarMakeResponse [carmakeList=" + carmakeList + "]";
	}

}
