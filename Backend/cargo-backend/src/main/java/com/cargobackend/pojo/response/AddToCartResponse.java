package com.cargobackend.pojo.response;

import java.util.List;

import com.cargobackend.pojo.dao.cargo.CartEntry;
import com.cargobackend.pojo.response.common.BaseResponse;

public class AddToCartResponse extends BaseResponse {

	CartEntry cartEntry;

	public CartEntry getCartEntry() {
		return cartEntry;
	}

	public void setCartEntry(CartEntry cartEntry) {
		this.cartEntry = cartEntry;
	}

	@Override
	public String toString() {
		return "AddToCartResponse [cartEntry=" + cartEntry + "]";
	}
	

	

	
	
}
