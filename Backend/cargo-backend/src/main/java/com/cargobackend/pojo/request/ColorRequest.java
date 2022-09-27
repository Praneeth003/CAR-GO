package com.cargobackend.pojo.request;

import java.util.List;

public class ColorRequest {

	List<Integer> colorIdList;
	List<String> colorNameList;
	public List<Integer> getColorIdList() {
		return colorIdList;
	}
	public void setColorIdList(List<Integer> colorIdList) {
		this.colorIdList = colorIdList;
	}
	public List<String> getColorNameList() {
		return colorNameList;
	}
	public void setColorNameList(List<String> colorNameList) {
		this.colorNameList = colorNameList;
	}
	@Override
	public String toString() {
		return "ColorRequest [colorIdList=" + colorIdList + ", colorNameList=" + colorNameList + ", getColorIdList()="
				+ getColorIdList() + ", getColorNameList()=" + getColorNameList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
