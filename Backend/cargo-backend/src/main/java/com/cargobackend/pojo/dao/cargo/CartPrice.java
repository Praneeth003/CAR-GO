package com.cargobackend.pojo.dao.cargo;

import java.util.List;

public class CartPrice {


    Double price;
    List<AddOnPrice> addOnPrices;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<AddOnPrice> getAddOnPrices() {
        return addOnPrices;
    }

    public void setAddOnPrices(List<AddOnPrice> addOnPrices) {
        this.addOnPrices = addOnPrices;
    }

    @Override
    public String toString() {
        return "CartPrice{" +
                "price=" + price +
                ", addOnPrices=" + addOnPrices +
                '}';
    }
}
