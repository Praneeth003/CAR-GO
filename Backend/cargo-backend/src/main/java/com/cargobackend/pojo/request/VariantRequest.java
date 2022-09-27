package com.cargobackend.pojo.request;
import java.sql.Timestamp;
import java.util.List;

import com.cargobackend.pojo.dao.cargo.BodyType;
import com.cargobackend.pojo.dao.cargo.Color;
import com.cargobackend.pojo.dao.cargo.FuelType;
import com.cargobackend.pojo.dao.cargo.Location;
import com.cargobackend.pojo.dao.cargo.Make;
import com.cargobackend.pojo.dao.cargo.Model;
import com.cargobackend.pojo.dao.cargo.TransmissionType;
import com.cargobackend.pojo.dao.cargo.Variant;

public class VariantRequest {
	
	Timestamp fromDate;
	Timestamp toDate;
	Location fromLocation;
	Location toLocation;
	List<Variant> variantList;
	List<Make> makeList;
	List<Model> modelList;
	List<BodyType> bodyTypeList;
	List<FuelType> fuelTypeList;
	List<TransmissionType> transmissionTypeList;
	List<Color> colorList;
	Integer limit;
	Integer offSet;
	public Timestamp getFromDate() {
		return fromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}
	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	public Location getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}
	public Location getToLocation() {
		return toLocation;
	}
	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}
	public List<Variant> getVariantList() {
		return variantList;
	}
	public void setVariantList(List<Variant> variantList) {
		this.variantList = variantList;
	}
	public List<Make> getMakeList() {
		return makeList;
	}
	public void setMakeList(List<Make> makeList) {
		this.makeList = makeList;
	}
	public List<Model> getModelList() {
		return modelList;
	}
	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}
	public List<BodyType> getBodyTypeList() {
		return bodyTypeList;
	}
	public void setBodyTypeList(List<BodyType> bodyTypeList) {
		this.bodyTypeList = bodyTypeList;
	}
	public List<FuelType> getFuelTypeList() {
		return fuelTypeList;
	}
	public void setFuelTypeList(List<FuelType> fuelTypeList) {
		this.fuelTypeList = fuelTypeList;
	}
	public List<TransmissionType> getTransmissionTypeList() {
		return transmissionTypeList;
	}
	public void setTransmissionTypeList(List<TransmissionType> transmissionTypeList) {
		this.transmissionTypeList = transmissionTypeList;
	}
	public List<Color> getColorList() {
		return colorList;
	}
	public void setColorList(List<Color> colorList) {
		this.colorList = colorList;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffSet() {
		return offSet;
	}
	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}
	@Override
	public String toString() {
		return "VariantRequest [fromDate=" + fromDate + ", toDate=" + toDate + ", fromLocation=" + fromLocation
				+ ", toLocation=" + toLocation + ", variantList=" + variantList + ", makeList=" + makeList
				+ ", modelList=" + modelList + ", bodyTypeList=" + bodyTypeList + ", fuelTypeList=" + fuelTypeList
				+ ", transmissionTypeList=" + transmissionTypeList + ", colorList=" + colorList + ", limit=" + limit
				+ ", offSet=" + offSet + ", getFromDate()=" + getFromDate() + ", getToDate()=" + getToDate()
				+ ", getFromLocation()=" + getFromLocation() + ", getToLocation()=" + getToLocation()
				+ ", getVariantList()=" + getVariantList() + ", getMakeList()=" + getMakeList() + ", getModelList()="
				+ getModelList() + ", getBodyTypeList()=" + getBodyTypeList() + ", getFuelTypeList()="
				+ getFuelTypeList() + ", getTransmissionTypeList()=" + getTransmissionTypeList() + ", getColorList()="
				+ getColorList() + ", getLimit()=" + getLimit() + ", getOffSet()=" + getOffSet() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
