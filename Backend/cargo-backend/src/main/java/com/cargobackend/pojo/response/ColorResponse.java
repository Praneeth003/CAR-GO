package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.Color;
import com.cargobackend.pojo.response.common.BaseResponse;

public class ColorResponse extends BaseResponse{

	List<Color> colorList;

	public List<Color> getColorList() {
		return colorList;
	}

	public void setColorList(List<Color> colorList) {
		this.colorList = colorList;
	}

	@Override
	public String toString() {
		return "ColorResponse [colorList=" + colorList + ", getColorList()=" + getColorList() + ", getStatus()="
				+ getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()=" + getErrorId()
				+ ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
