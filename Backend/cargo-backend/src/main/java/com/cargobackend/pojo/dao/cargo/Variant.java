package com.cargobackend.pojo.dao.cargo;

import java.sql.Date;

public class Variant {
	
	Integer variantId;
	String variantName;
	String variantDescription;
	Boolean variantStatus;
	Model model;
	Color color;
	FuelType fuelType;
	TransmissionType transmissionType;
	VariantImage variantImage;
	Long variantMileage;
	Date variantManufacturingDate;
	Long variantKilometersDriven;
	Long variantPricePerKm;
	String varaintNumberPlate ;
	
	
	public Integer getVariantId() {
		return variantId;
	}
	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}
	public String getVariantName() {
		return variantName;
	}
	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}
	public String getVariantDescription() {
		return variantDescription;
	}
	public void setVariantDescription(String variantDescription) {
		this.variantDescription = variantDescription;
	}
	public Boolean getVariantStatus() {
		return variantStatus;
	}
	public void setVariantStatus(Boolean variantStatus) {
		this.variantStatus = variantStatus;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	public TransmissionType getTransmissionType() {
		return transmissionType;
	}
	public void setTransmissionType(TransmissionType transmissionType) {
		this.transmissionType = transmissionType;
	}
	public Long getVariantMileage() {
		return variantMileage;
	}
	public void setVariantMileage(Long variantMileage) {
		this.variantMileage = variantMileage;
	}
	public Date getVariantManufacturingDate() {
		return variantManufacturingDate;
	}
	public void setVariantManufacturingDate(Date variantManufacturingDate) {
		this.variantManufacturingDate = variantManufacturingDate;
	}
	public Long getVaraintKilometersDriven() {
		return variantKilometersDriven;
	}
	public void setVaraintKilometersDriven(Long variantKilometersDriven) {
		this.variantKilometersDriven = variantKilometersDriven;
	}
	public Long getVariantPricePerKm() {
		return variantPricePerKm;
	}
	public void setVariantPricePerKm(Long variantPricePerKm) {
		this.variantPricePerKm = variantPricePerKm;
	}
	public String getVaraintNumberPlate() {
		return varaintNumberPlate;
	}
	public void setVaraintNumberPlate(String varaintNumberPlate) {
		this.varaintNumberPlate = varaintNumberPlate;
	}
	
	public VariantImage getVariantImage() {
		return variantImage;
	}
	public void setVariantImage(VariantImage variantImage) {
		this.variantImage = variantImage;
	}
	@Override
	public String toString() {
		return "Variant [variantId=" + variantId + ", variantName=" + variantName + ", variantDescription=" + variantDescription
				+ ", variantStatus=" + variantStatus + ", model=" + model + ", color=" + color + ", fuelType="
				+ fuelType + ", transmissionType=" + transmissionType + ", variantImage=" + variantImage
				+ ", variantMileage=" + variantMileage + ", variantManufacturingDate=" + variantManufacturingDate
				+ ", variantKilometersDriven=" + variantKilometersDriven + ", variantPricePerKm=" + variantPricePerKm
				+ ", varaintNumberPlate=" + varaintNumberPlate + ", getVariantId()=" + getVariantId()
				+ ", getVariantName()=" + getVariantName() + ", getVariantDescription()=" + getVariantDescription()
				+ ", getVariantStatus()=" + getVariantStatus() + ", getModel()=" + getModel() + ", getColor()="
				+ getColor() + ", getFuelType()=" + getFuelType() + ", getTransmissionType()=" + getTransmissionType()
				+ ", getVariantMileage()=" + getVariantMileage() + ", getVariantManufacturingDate()="
				+ getVariantManufacturingDate() + ", getVaraintKilometersDriven()=" + getVaraintKilometersDriven()
				+ ", getVariantPricePerKm()=" + getVariantPricePerKm() + ", getVaraintNumberPlate()="
				+ getVaraintNumberPlate() + ", getVariantImage()=" + getVariantImage() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

		
}