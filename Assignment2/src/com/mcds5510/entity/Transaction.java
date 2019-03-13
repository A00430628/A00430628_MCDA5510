package com.mcds5510.entity;

public class Transaction{

	private String nameOnCard;
	
	private String cardNumber;
	
	private String expDate; 

	private int unitPrice;

	private int qty;

	private int totalPrice;
	
	private int ID;
	
	private String createdBy;
	
	private int cardType;
	
	private String cardName;
	
	
	public void setCardName(String cardName) {
		this.cardName =  cardName;
	}

	
	public String getCardName() {
		return cardName;
	}

	public void setCardType(int cardType) {
		this.cardType =  cardType;
	}

	
	public int getCardType() {
		return cardType;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
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



	public String getExpDate() {
		return expDate;
	}



	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}



	public int getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}



	public int getQty() {
		return qty;
	}



	public void setQty(int qty) {
		this.qty = qty;
	}



	public int getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String toString(){
		
		String results = new String();
		
		results = "[NameOnCard: " + nameOnCard +",CardNumber: " + cardNumber+",CardType: "+cardName+",Expiry Date: "+expDate+",Unit Price: "+unitPrice+",Quantity: "+qty+",Total Price: "+totalPrice+",Created By: "+createdBy+"]";
		return results;

	}

	
	
}
