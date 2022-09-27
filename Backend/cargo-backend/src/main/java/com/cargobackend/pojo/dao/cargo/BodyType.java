package com.cargobackend.pojo.dao.cargo;

public class BodyType {

	Integer bodyTypeId;
	String bodyTypeName;
	String bodyTypeDescription;
	Boolean bodyTypeStatus;
	
	public Integer getBodyTypeId() {
		return bodyTypeId;
	}
	public void setBodyTypeId(Integer bodyTypeId) {
		this.bodyTypeId = bodyTypeId;
	}
	public String getBodyTypeName() {
		return bodyTypeName;
	}
	public void setBodyTypeName(String bodyTypeName) {
		this.bodyTypeName = bodyTypeName;
	}
	public String getBodyTypeDescription() {
		return bodyTypeDescription;
	}
	public void setBodyTypeDescription(String bodyTypeDescription) {
		this.bodyTypeDescription = bodyTypeDescription;
	}
	public Boolean getBodyTypeStatus() {
		return bodyTypeStatus;
	}
	public void setBodyTypeStatus(Boolean bodyTypeStatus) {
		this.bodyTypeStatus = bodyTypeStatus;
	}
	
	@Override
	public String toString() {
		return "BodyType [bodyTypeId=" + bodyTypeId + ", bodyTypeName=" + bodyTypeName + ", bodyTypeDescription="
				+ bodyTypeDescription + ", bodyTypeStatus=" + bodyTypeStatus + ", getBodyTypeId()=" + getBodyTypeId()
				+ ", getBodyTypeName()=" + getBodyTypeName() + ", getBodyTypeDescription()=" + getBodyTypeDescription()
				+ ", getBodyTypeStatus()=" + getBodyTypeStatus() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
