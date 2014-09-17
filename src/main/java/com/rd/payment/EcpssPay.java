package com.rd.payment;

import com.rd.tool.coder.MD5;

public class EcpssPay {
	private String md5Key;
	private String merNo;
	private String billNo;
	private String amount;
	private String returnUrl;
	private String adviceUrl;
	private String defaultBankNumber = "";

	private String orderTime = "";

	private String products = "";
	private String md5Info;

	public String getMerNo() {
		return this.merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAdviceUrl() {
		return this.adviceUrl;
	}

	public void setAdviceUrl(String adviceUrl) {
		this.adviceUrl = adviceUrl;
	}

	public String getDefaultBankNumber() {
		return this.defaultBankNumber;
	}

	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getProducts() {
		return this.products;
	}

	public String getMd5Key() {
		return this.md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getMd5Info() {
		String md5Src = this.merNo + this.billNo + this.amount + this.returnUrl
				+ this.md5Key;
		MD5 md5 = new MD5();
		this.md5Info = md5.getMD5ofStr(md5Src);
		return this.md5Info;
	}

	public void setMd5Info(String md5Info) {
		this.md5Info = md5Info;
	}
}