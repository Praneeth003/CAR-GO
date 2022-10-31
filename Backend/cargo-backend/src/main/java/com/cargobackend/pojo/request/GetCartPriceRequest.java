package com.cargobackend.pojo.request;


import java.sql.Timestamp;
import java.util.List;

public class GetCartPriceRequest{
    Integer variantId;
    List<Integer> addOnIds;
    Timestamp fromDate;
    Timestamp toDate;

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

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public List<Integer> getAddOnIds() {
        return addOnIds;
    }

    public void setAddOnIds(List<Integer> addOnIds) {
        this.addOnIds = addOnIds;
    }

    @Override
    public String toString() {
        return "GetCartPriceRequest{" +
                "variantId=" + variantId +
                ", addOnIds=" + addOnIds +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
