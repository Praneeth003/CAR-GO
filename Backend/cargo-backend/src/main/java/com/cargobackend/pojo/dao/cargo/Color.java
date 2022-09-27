package com.cargobackend.pojo.dao.cargo;

public class Color {
	
	Integer colorId;
	String colorName;
	String colorDescription;
	
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getColorDescription() {
		return colorDescription;
	}
	public void setColorDescription(String colorDescription) {
		this.colorDescription = colorDescription;
	}
	
	@Override
	public String toString() {
		return "Color [colorId=" + colorId + ", colorName=" + colorName + ", colorDescription=" + colorDescription
				+ ", getColorId()=" + getColorId() + ", getColorName()=" + getColorName() + ", getColorDescription()="
				+ getColorDescription() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
