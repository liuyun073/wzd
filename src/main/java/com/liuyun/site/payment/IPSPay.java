package com.liuyun.site.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cryptix.jce.provider.MD5;

public class IPSPay {
	
	private static Logger logger = LoggerFactory.getLogger(IPSPay.class);
	private String mer_code;
	private String billno;
	private String amount;
	private String date;
	private String currency_Type;
	private String gateway_Type;
	private String lang;
	private String merchanturl;
	private String failUrl;
	private String errorUrl;
	private String attach;
	private String orderEncodeType;
	private String retEncodeType;
	private String rettype;
	private String serverUrl;
	private String signMD5;
	private String mer_key;
	private String doCredit;
	private String bankco;
	private String succ;
	private String msg;
	private String ipsbillno;
	private String signature;

	public String getMer_code() {
		return this.mer_code;
	}

	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}

	public String getBillno() {
		return this.billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCurrency_Type() {
		return this.currency_Type;
	}

	public void setCurrency_Type(String currency_Type) {
		this.currency_Type = currency_Type;
	}

	public String getGateway_Type() {
		return this.gateway_Type;
	}

	public void setGateway_Type(String gateway_Type) {
		this.gateway_Type = gateway_Type;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMerchanturl() {
		return this.merchanturl;
	}

	public void setMerchanturl(String merchanturl) {
		this.merchanturl = merchanturl;
	}

	public String getFailUrl() {
		return this.failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	public String getErrorUrl() {
		return this.errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public String getAttach() {
		return this.attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOrderEncodeType() {
		return this.orderEncodeType;
	}

	public void setOrderEncodeType(String orderEncodeType) {
		this.orderEncodeType = orderEncodeType;
	}

	public String getRetEncodeType() {
		return this.retEncodeType;
	}

	public void setRetEncodeType(String retEncodeType) {
		this.retEncodeType = retEncodeType;
	}

	public String getRettype() {
		return this.rettype;
	}

	public void setRettype(String rettype) {
		this.rettype = rettype;
	}

	public String getServerUrl() {
		return this.serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getSignMD5() {
		return this.signMD5;
	}

	public void setSignMD5(String signMD5) {
		this.signMD5 = signMD5;
	}

	public String getMer_key() {
		return this.mer_key;
	}

	public void setMer_key(String mer_key) {
		this.mer_key = mer_key;
	}

	public String getDoCredit() {
		return this.doCredit;
	}

	public void setDoCredit(String doCredit) {
		this.doCredit = doCredit;
	}

	public String getBankco() {
		return this.bankco;
	}

	public void setBankco(String bankco) {
		this.bankco = bankco;
	}

	public String getSucc() {
		return this.succ;
	}

	public void setSucc(String succ) {
		this.succ = succ;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIpsbillno() {
		return this.ipsbillno;
	}

	public void setIpsbillno(String ipsbillno) {
		this.ipsbillno = ipsbillno;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String encodeSignMD5() {
		MD5 b = new MD5();

		String value = "billno" + this.billno + "currencytype"
				+ this.currency_Type + "amount" + this.amount + "date"
				+ this.date + "orderencodetype" + this.orderEncodeType
				+ this.mer_key;
		logger.info(value);
		String signMD5 = b.toMD5(value).toLowerCase();
		logger.info("IPSPay signMD5:" + signMD5);
		this.signMD5 = signMD5;
		return signMD5;
	}

	public String callbackSign() {
		String content = "billno" + this.billno + "currencytype"
				+ this.currency_Type + "amount" + this.amount + "date"
				+ this.date + "succ" + this.succ + "ipsbillno" + this.ipsbillno
				+ "retencodetype" + this.retEncodeType;
		return content;
	}

	public String toString() {
		return "IPSPay [mer_code=" + this.mer_code + ", billno=" + this.billno
				+ ", amount=" + this.amount + ", date=" + this.date
				+ ", currency_Type=" + this.currency_Type + ", gateway_Type="
				+ this.gateway_Type + ", lang=" + this.lang + ", merchanturl="
				+ this.merchanturl + ", failUrl=" + this.failUrl
				+ ", errorUrl=" + this.errorUrl + ", attach=" + this.attach
				+ ", orderEncodeType=" + this.orderEncodeType
				+ ", retEncodeType=" + this.retEncodeType + ", rettype="
				+ this.rettype + ", serverUrl=" + this.serverUrl + ", signMD5="
				+ this.signMD5 + ", mer_key=" + this.mer_key + "]";
	}
}