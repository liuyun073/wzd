package com.rd.payment;

public class BaoFooPay {
	private String payID;
	private String merchantID;
	private String tradeDate;
	private String transID;
	private String orderMoney;
	private String productName = "";

	private String amount = "1";

	private String productLogo = "";

	private String username = "";

	private String email = "";

	private String mobile = "";

	private String additionalInfo = "";
	private String merchant_url;
	private String return_url;
	private String noticeType;
	private String md5Sign;

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMd5Sign() {
		return this.md5Sign;
	}

	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}

	public String getPayID() {
		return this.payID;
	}

	public void setPayID(String payID) {
		this.payID = payID;
	}

	public String getMerchantID() {
		return this.merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(String TradeDate) {
		this.tradeDate = TradeDate;
	}

	public String getTransID() {
		return this.transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getOrderMoney() {
		return this.orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLogo() {
		return this.productLogo;
	}

	public void setProductLogo(String productLogo) {
		this.productLogo = productLogo;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAdditionalInfo() {
		return this.additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getMerchant_url() {
		return this.merchant_url;
	}

	public void setMerchant_url(String merchant_url) {
		this.merchant_url = merchant_url;
	}

	public String getReturn_url() {
		return this.return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getNoticeType() {
		return this.noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
}