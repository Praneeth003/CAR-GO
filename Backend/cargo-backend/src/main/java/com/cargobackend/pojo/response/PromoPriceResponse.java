package com.cargobackend.pojo.response;

import com.cargobackend.pojo.dao.cargo.CartPrice;
import com.cargobackend.pojo.response.common.BaseResponse;

public class PromoPriceResponse extends BaseResponse{

    Double promoPrice;

    @Override
    public String toString() {
        return "PromoPriceResponse{" +
                "promoPrice=" + promoPrice +
                '}';
    }

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }
}
