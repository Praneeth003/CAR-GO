package com.cargobackend.pojo.dao.cargo;

import java.sql.Timestamp;
import java.util.List;

public class CartEntry {
	
	Integer cartId;
	Integer userId;
	Variant variant;
	Boolean isActive;
	Timestamp fromDate;
	Timestamp toDate;
	Location pickupLocation;
	Location dropLocation;
	Double price;
	CartPrice cartPrice;
	UserProfile userProfile;
	List<AddOn> addOnList;
	public Variant getVariant() {
		return variant;
	}
	public void setVariant(Variant variant) {
		this.variant = variant;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
	public Location getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(Location pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public Location getDropLocation() {
		return dropLocation;
	}
	public void setDropLocation(Location dropLocation) {
		this.dropLocation = dropLocation;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public List<AddOn> getAddOnList() {
		return addOnList;
	}
	public void setAddOnList(List<AddOn> addOnList) {
		this.addOnList = addOnList;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public CartPrice getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(CartPrice cartPrice) {
		this.cartPrice = cartPrice;
	}

	@Override
	public String toString() {
		return "CartEntry{" +
				"cartId=" + cartId +
				", userId=" + userId +
				", variant=" + variant +
				", isActive=" + isActive +
				", fromDate='" + fromDate + '\'' +
				", toDate='" + toDate + '\'' +
				", pickupLocation=" + pickupLocation +
				", dropLocation=" + dropLocation +
				", price=" + price +
				", cartPrice=" + cartPrice +
				", userProfile=" + userProfile +
				", addOnList=" + addOnList +
				'}';
	}
}
