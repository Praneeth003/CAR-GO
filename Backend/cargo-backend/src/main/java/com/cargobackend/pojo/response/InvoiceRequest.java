package com.cargobackend.pojo.response;

import java.util.List;

public class InvoiceRequest {
	
	public static class CustomField {
		String name;
		String value;
		@Override
		public String toString() {
			return "CustomField [name=" + name + ", value=" + value + "]";
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static class Item {
		String name;
		String quantity;
		String unit_cost;
		String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override

		public String toString() {
			return "Items [name=" + name + ", quantity=" + quantity + ", unit_cost=" + unit_cost + "]";
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getUnit_cost() {
			return unit_cost;
		}
		public void setUnit_cost(String unit_cost) {
			this.unit_cost = unit_cost;
		}
		
	}

	public static class Field {
		public String tax;
		public Boolean discounts;
		public Boolean shipping;

		public Field(String tax, Boolean discounts, Boolean shipping) {
			this.tax = tax;
			this.discounts = discounts;
			this.shipping = shipping;
		}
	}

	
	String from;
	String to;
	String date;
	String amount_paid;
	String tax;
	Double discounts;
	List<CustomField> custom_fields;
	List<Item> items;
	Field fields;

	public Double getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Double discounts) {
		this.discounts = discounts;
	}

	public Field getField() {
		return fields;
	}

	public void setField(Field field) {
		this.fields = field;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(String amount_paid) {
		this.amount_paid = amount_paid;
	}
	public List<CustomField> getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(List<CustomField> custom_fields) {
		this.custom_fields = custom_fields;
	}
	@Override
	public String toString() {
		return "InvoiceRequest [from=" + from + ", to=" + to + ", date=" + date + ", amount_paid=" + amount_paid
				+ ", custom_fields=" + custom_fields + ", items=" + items + "]";
	}
	
	
	
}
