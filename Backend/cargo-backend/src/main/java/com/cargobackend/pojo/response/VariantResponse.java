package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.Variant;
import com.cargobackend.pojo.response.common.BaseResponse;

public class VariantResponse extends BaseResponse  {

	List<Variant> variantList;

	public List<Variant> getVariantList() {
		return variantList;
	}

	public void setVariantList(List<Variant> variantList) {
		this.variantList = variantList;
	}

	@Override
	public String toString() {
		return "VariantResponse [variantList=" + variantList + ", getVariantList()=" + getVariantList()
				+ ", getStatus()=" + getStatus() + ", getErrorCode()=" + getErrorCode() + ", getErrorId()="
				+ getErrorId() + ", getErrorDescription()=" + getErrorDescription() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}


}

