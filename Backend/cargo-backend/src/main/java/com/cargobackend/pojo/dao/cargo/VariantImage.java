package com.cargobackend.pojo.dao.cargo;

public class VariantImage {
	Integer variantImageId;
	String variantImageData;
	String variantImageView;
	String variantImageDescription;
	Boolean variantImageStatus;
	public Integer getVariantImageId() {
		return variantImageId;
	}
	public void setVariantImageId(Integer variantImageId) {
		this.variantImageId = variantImageId;
	}
	public String getVariantImageData() {
		return variantImageData;
	}
	public void setVariantImageData(String variantImageData) {
		this.variantImageData = variantImageData;
	}
	public String getVariantImageView() {
		return variantImageView;
	}
	public void setVariantImageView(String variantImageView) {
		this.variantImageView = variantImageView;
	}
	public String getVariantImageDescription() {
		return variantImageDescription;
	}
	public void setVariantImageDescription(String variantImageDescription) {
		this.variantImageDescription = variantImageDescription;
	}
	public Boolean getVariantImageStatus() {
		return variantImageStatus;
	}
	public void setVariantImageStatus(Boolean variantImageStatus) {
		this.variantImageStatus = variantImageStatus;
	}
	@Override
	public String toString() {
		return "VariantImage [variantImageId=" + variantImageId + ", variantImageData=" + variantImageData
				+ ", variantImageView=" + variantImageView + ", variantImageDescription=" + variantImageDescription
				+ ", variantImageStatus=" + variantImageStatus + ", getVariantImageId()=" + getVariantImageId()
				+ ", getVariantImageData()=" + getVariantImageData() + ", getVariantImageView()="
				+ getVariantImageView() + ", getVariantImageDescription()=" + getVariantImageDescription()
				+ ", getVariantImageStatus()=" + getVariantImageStatus() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
