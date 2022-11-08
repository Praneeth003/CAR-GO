package com.cargobackend.pojo.dao.cargo;

public class PromoCode {

    public enum PromoCodeType {
        PERCENTAGE,
        FLAT
    }

    Integer promoCodeId;
    String promoCodeName;
    String promoCodeDescription;
    PromoCodeType promoCodeType;
    Double promoValue;
    Double promoPrice;

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(Integer promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public String getPromoCodeName() {
        return promoCodeName;
    }

    public void setPromoCodeName(String promoCodeName) {
        this.promoCodeName = promoCodeName;
    }

    public String getPromoCodeDescription() {
        return promoCodeDescription;
    }

    public void setPromoCodeDescription(String promoCodeDescription) {
        this.promoCodeDescription = promoCodeDescription;
    }

    public PromoCodeType getPromoCodeType() {
        return promoCodeType;
    }

    public void setPromoCodeType(PromoCodeType promoCodeType) {
        this.promoCodeType = promoCodeType;
    }

    public Double getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(Double promoValue) {
        this.promoValue = promoValue;
    }

    public void setPromoCodeType(String promoCodeType) {
        PromoCodeType promoCodeTypeEnum = null;
        for (PromoCodeType b : PromoCodeType.values()) {
            if (b.toString().equalsIgnoreCase(promoCodeType)) {
                promoCodeTypeEnum = b;
            }
        }
        this.promoCodeType = promoCodeTypeEnum;
    }

    @Override
    public String toString() {
        return "PromoCode{" +
                "promoCodeId=" + promoCodeId +
                ", promoCodeName='" + promoCodeName + '\'' +
                ", promoCodeDescription='" + promoCodeDescription + '\'' +
                ", promoCodeType=" + promoCodeType +
                ", promoValue=" + promoValue +
                ", promoPrice=" + promoPrice +
                '}';
    }
}
