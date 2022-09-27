package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.Model;
import com.cargobackend.pojo.response.common.BaseResponse;

public class ModelResponse extends BaseResponse{

	List<Model> modelList;

	public List<Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "ModelResponse [modelList=" + modelList + ", getModelList()=" + getModelList() + ", getStatus()="
				+ getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()=" + getErrorId()
				+ ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
