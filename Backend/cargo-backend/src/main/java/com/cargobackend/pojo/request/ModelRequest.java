package com.cargobackend.pojo.request;

import java.util.List;

public class ModelRequest {
	
	List<Integer> modelIdList;
	List<String> modelNameList;
	List<Integer> makeIdList;
	List<Integer> bodyTypeIdList;
	public List<Integer> getModelIdList() {
		return modelIdList;
	}
	public void setModelIdList(List<Integer> modelIdList) {
		this.modelIdList = modelIdList;
	}
	public List<String> getModelNameList() {
		return modelNameList;
	}
	public void setModelNameList(List<String> modelNameList) {
		this.modelNameList = modelNameList;
	}
	public List<Integer> getMakeIdList() {
		return makeIdList;
	}
	public void setMakeIdList(List<Integer> makeIdList) {
		this.makeIdList = makeIdList;
	}
	public List<Integer> getBodyTypeIdList() {
		return bodyTypeIdList;
	}
	public void setBodyTypeIdList(List<Integer> bodyTypeIdList) {
		this.bodyTypeIdList = bodyTypeIdList;
	}
	@Override
	public String toString() {
		return "ModelRequest [modelIdList=" + modelIdList + ", modelNameList=" + modelNameList + ", makeIdList="
				+ makeIdList + ", bodyTypeIdList=" + bodyTypeIdList + ", getModelIdList()=" + getModelIdList()
				+ ", getModelNameList()=" + getModelNameList() + ", getMakeIdList()=" + getMakeIdList()
				+ ", getBodyTypeIdList()=" + getBodyTypeIdList() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
