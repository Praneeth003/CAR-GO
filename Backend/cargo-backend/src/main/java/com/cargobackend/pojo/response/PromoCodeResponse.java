package com.cargobackend.pojo.response;

import com.cargobackend.pojo.dao.cargo.PromoCode;
import com.cargobackend.pojo.response.common.BaseResponse;

import java.util.List;

public class PromoCodeResponse extends BaseResponse {
    List<PromoCode> promoCodeList ;

    public List<PromoCode> getPromoCodeList() {
        return promoCodeList;
    }

    public void setPromoCodeList(List<PromoCode> promoCodeList) {
        this.promoCodeList = promoCodeList;
    }

    @Override
    public String toString() {
        return "PromoCodeResponse [promoCodeList=" + promoCodeList + "]";
    }
}
