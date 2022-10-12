package com.cargobackend.pojo.dao.cargo;

public class Make {
	
	Integer makeId;
	String makeName;
	String makeDescription;
	Boolean makeStatus;
	
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}
	public String getMakeName() {
		return makeName;
	}
	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}
	public String getMakeDescription() {
		return makeDescription;
	}
	public void setMakeDescription(String makeDescription) {
		this.makeDescription = makeDescription;
	}
	public Boolean getMakeStatus() {
		return makeStatus;
	}
	public void setMakeStatus(Boolean makeStatus) {
		this.makeStatus = makeStatus;
	}
	
	@Override
	public String toString() {
		return "Make [makeId=" + makeId + ", makeName=" + makeName + ", makeDescription=" + makeDescription
				+ ", makeStatus=" + makeStatus + ", getMakeId()=" + getMakeId() + ", getMakeName()=" + getMakeName()
				+ ", getMakeDescription()=" + getMakeDescription() + ", getMakeStatus()=" + getMakeStatus()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}