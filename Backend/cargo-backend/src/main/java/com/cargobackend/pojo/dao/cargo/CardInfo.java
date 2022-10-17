package com.cargobackend.pojo.dao.cargo;

public class CardInfo {
	String cardNumber;
	String cardExpMonth;
	String cardExpYear;
	String cvv;
	String nameOnCard;
	
	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getCardExpMonth() {
		return cardExpMonth;
	}
	
	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}
	
	public String getCardExpYear() {
		return cardExpYear;
	}
	
	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}
	
	public String getCvv() {
		return cvv;
	}
	
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	@Override
	public String toString() {
		return "CardInfo [cardNumber=" + cardNumber + ", cardExpMonth=" + cardExpMonth + ", cardExpYear=" + cardExpYear
				+ ", cvv=" + cvv + ", nameOnCard=" + nameOnCard + "]";
	}
	
}
