package com.cargobackend.pojo.response;

import com.cargobackend.pojo.dao.cargo.CartPrice;
import com.cargobackend.pojo.response.common.BaseResponse;

public class CartPriceResponse extends BaseResponse{

    CartPrice cartPrice;

    @Override
    public String toString() {
        return "CartPriceResponse{" +
                "cartPrice=" + cartPrice +
                '}';
    }

    public CartPrice getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(CartPrice cartPrice) {
        this.cartPrice = cartPrice;
    }
}
