package com.cargobackend.pojo.request;


import com.cargobackend.pojo.dao.cargo.PromoCode;

import java.sql.Timestamp;
import java.util.List;

public class GetPromoPriceRequest {

    Double totalAmount;
    PromoCode promo;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PromoCode getPromo() {
        return promo;
    }

    public void setPromo(PromoCode promoId) {
        this.promo = promoId;
    }

    @Override
    public String toString() {
        return "GetPromoPriceRequest{" +
                "totalAmount=" + totalAmount +
                ", promoId=" + promo +
                '}';
    }
}
