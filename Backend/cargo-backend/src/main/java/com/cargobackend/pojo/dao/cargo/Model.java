package com.cargobackend.pojo.dao.cargo;

public class Model {
	
	Integer modelId;
	String modelName;
	String modelDescription;
	Boolean modelStatus;
	Make make;
	BodyType bodyType;
	
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDescription() {
		return modelDescription;
	}
	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}
	public Boolean getModelStatus() {
		return modelStatus;
	}
	public void setModelStatus(Boolean modelStatus) {
		this.modelStatus = modelStatus;
	}
	public Make getMake() {
		return make;
	}
	public void setMake(Make make) {
		this.make = make;
	}
	public BodyType getBodyType() {
		return bodyType;
	}
	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}
	@Override
	public String toString() {
		return "Model [modelId=" + modelId + ", modelName=" + modelName + ", modelDescription=" + modelDescription
				+ ", modelStatus=" + modelStatus + ", make=" + make + ", bodyType=" + bodyType + ", getModelId()="
				+ getModelId() + ", getModelName()=" + getModelName() + ", getModelDescription()="
				+ getModelDescription() + ", getModelStatus()=" + getModelStatus() + ", getMake()=" + getMake()
				+ ", getBodyType()=" + getBodyType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


}
