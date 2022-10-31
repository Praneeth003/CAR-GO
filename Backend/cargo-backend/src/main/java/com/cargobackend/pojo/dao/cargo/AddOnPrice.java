package com.cargobackend.pojo.dao.cargo;

public class AddOnPrice {
    AddOn addOn;
    Double price;

    public AddOn getAddOn() {
        return addOn;
    }

    public void setAddOn(AddOn addOn) {
        this.addOn = addOn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AddOnPrice{" +
                "addOn=" + addOn +
                ", price=" + price +
                '}';
    }
}