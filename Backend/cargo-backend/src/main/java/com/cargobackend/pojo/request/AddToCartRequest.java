package com.cargobackend.pojo.request;

import java.sql.Timestamp;
import java.util.List;

import com.cargobackend.pojo.dao.cargo.Location;
import com.cargobackend.pojo.dao.cargo.UserProfile;
import com.cargobackend.pojo.dao.cargo.Variant;

public class AddToCartRequest {

	Integer cartId;
	Integer userId;
	Integer variantId;
	Timestamp fromDate;
	Timestamp toDate;
	Integer pickupLocationId;
	Integer dropLocationId;
	Double price;
	List<Integer> addOnIds;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getVariantId() {
		return variantId;
	}
	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}
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
	public Integer getPickupLocationId() {
		return pickupLocationId;
	}
	public void setPickupLocationId(Integer pickupLocationId) {
		this.pickupLocationId = pickupLocationId;
	}
	public Integer getDropLocationId() {
		return dropLocationId;
	}
	public void setDropLocationId(Integer dropLocationId) {
		this.dropLocationId = dropLocationId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<Integer> getAddOnIds() {
		return addOnIds;
	}
	public void setAddOnIds(List<Integer> addOnIds) {
		this.addOnIds = addOnIds;
	}
	@Override
	public String toString() {
		return "AddToCartRequest [cartId=" + cartId + ", userId=" + userId + ", variantId=" + variantId + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", pickupLocationId=" + pickupLocationId + ", dropLocationId="
				+ dropLocationId + ", price=" + price + ", addOnIds=" + addOnIds + ", getCartId()=" + getCartId()
				+ ", getUserId()=" + getUserId() + ", getVariantId()=" + getVariantId() + ", getFromDate()="
				+ getFromDate() + ", getToDate()=" + getToDate() + ", getPickupLocationId()=" + getPickupLocationId()
				+ ", getDropLocationId()=" + getDropLocationId() + ", getPrice()=" + getPrice() + ", getAddOnIds()="
				+ getAddOnIds() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
	
}
