package com.example.currFair.api.model;

public class TradeMessage {
	
	private String userId;
	private String currencyFrom;
	private String currencyTo;
	private Double amountSell;
	private Double amountBuy;
	private Double rate;
	private String timePlaced;
	private String originatingCountry;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	public Double getAmountSell() {
		return amountSell;
	}
	public void setAmountSell(Double amountSell) {
		this.amountSell = amountSell;
	}
	public Double getAmountBuy() {
		return amountBuy;
	}
	public void setAmountBuy(Double amountBuy) {
		this.amountBuy = amountBuy;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getTimePlaced() {
		return timePlaced;
	}
	public void setTimePlaced(String timePlaced) {
		this.timePlaced = timePlaced;
	}
	public String getOriginatingCountry() {
		return originatingCountry;
	}
	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}
	
	

}
