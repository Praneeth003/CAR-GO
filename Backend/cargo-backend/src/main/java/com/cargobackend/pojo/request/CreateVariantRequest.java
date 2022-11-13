package com.cargobackend.pojo.request;

import java.sql.Date;
import java.util.List;

public class CreateVariantRequest {

    String variantName;
    String variantDescription;
    Integer modelId;
    Integer colorId;
    Integer bodyTypeId;
    Integer fuelTypeId;
    Integer transmissionTypeId;
    Integer locationId;
    String variantMileage;
    String pricePerKilometer;
    String kilometersDriven;
    String numberPlate;
    List<VariantImageInfo> imageList;

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

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Integer getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(Integer bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public Integer getTransmissionTypeId() {
        return transmissionTypeId;
    }

    public void setTransmissionTypeId(Integer transmissionTypeId) {
        this.transmissionTypeId = transmissionTypeId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getVariantMileage() {
        return variantMileage;
    }

    public void setVariantMileage(String variantMileage) {
        this.variantMileage = variantMileage;
    }

    public String getPricePerKilometer() {
        return pricePerKilometer;
    }

    public void setPricePerKilometer(String pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public String getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(String kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public List<VariantImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<VariantImageInfo> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "CreateVariantRequest{" +
                "variantName='" + variantName + '\'' +
                ", variantDescription='" + variantDescription + '\'' +
                ", modelId=" + modelId +
                ", colorId=" + colorId +
                ", bodyTypeId=" + bodyTypeId +
                ", fuelTypeId=" + fuelTypeId +
                ", transmissionTypeId=" + transmissionTypeId +
                ", locationId=" + locationId +
                ", variantMileage='" + variantMileage + '\'' +
                ", pricePerKilometer='" + pricePerKilometer + '\'' +
                ", kilometersDriven='" + kilometersDriven + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", imageList=" + imageList +
                '}';
    }
}
