package com.cargobackend.pojo.request;

public class VariantInfo {
    public Integer varaintId;
    public Long variantKilometersDriven;

    public Integer getVaraintId() {
        return varaintId;
    }

    public void setVaraintId(Integer varaintId) {
        this.varaintId = varaintId;
    }

    public Long getVariantKilometersDriven() {
        return variantKilometersDriven;
    }

    public void setVariantKilometersDriven(Long variantKilometersDriven) {
        this.variantKilometersDriven = variantKilometersDriven;
    }

    @Override
    public String toString() {
        return "VariantInfo{" +
                "varaintId=" + varaintId +
                ", variantKilometersDriven=" + variantKilometersDriven +
                '}';
    }
}
