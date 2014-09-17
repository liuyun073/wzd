package com.rd.payment;

import java.security.MessageDigest;
import java.util.Random;

public class ShengPay {
	
	private String name;
	private String version;
	private String charset;
	private String msgSender;
	private String sendTime;
	private String orderNo;
	private String orderAmount;
	private String orderTime;
	private String payType;
	private String instCode;
	private String pageUrl;
	private String notifyUrl;
	private String productName;
	private String buyerContact;
	private String buyerIp;
	private String ext1;
	private String signType;
	private String signMsg;
	private String signKey;
	private String payChannel;
	private String backUrl;
	private String transNo;
	private String transAmount;
	private String transStatus;
	private String transType;
	private String traceNo;
	private String ext2;
	private String transTime;
	private String merchantNo;
	private String errorCode;
	private String errorMsg;

	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getTraceNo() {
		return this.traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTransStatus() {
		return this.transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransTime() {
		return this.transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getMerchantNo() {
		return this.merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTransAmount() {
		return this.transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransNo() {
		return this.transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getBackUrl() {
		return this.backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getSignKey() {
		return this.signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public ShengPay() {
		this.name = "B2CPayment";
		this.version = "V4.1.1.1.1";
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMsgSender() {
		return this.msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public String getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getInstCode() {
		return this.instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBuyerContact() {
		return this.buyerContact;
	}

	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}

	public String getBuyerIp() {
		return this.buyerIp;
	}

	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}

	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return this.signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String toSignStringRequest() {
		StringBuffer buf = new StringBuffer();
		buf.append((this.name != null) ? this.name : "");
		buf.append((this.version != null) ? this.version : "");
		buf.append((this.charset != null) ? this.charset : "");
		buf.append((this.msgSender != null) ? this.msgSender : "");
		buf.append((this.sendTime != null) ? this.sendTime : "");
		buf.append((this.orderNo != null) ? this.orderNo : "");
		buf.append((this.orderAmount != null) ? this.orderAmount : "");
		buf.append((this.orderTime != null) ? this.orderTime : "");
		buf.append((this.payType != null) ? this.payType : "");
		buf.append((this.payChannel != null) ? this.payChannel : "");
		buf.append((this.instCode != null) ? this.instCode : "");
		buf.append((this.pageUrl != null) ? this.pageUrl : "");
		buf.append((this.backUrl != null) ? this.backUrl : "");
		buf.append((this.notifyUrl != null) ? this.notifyUrl : "");
		buf.append((this.productName != null) ? this.productName : "");
		buf.append((this.buyerContact != null) ? this.buyerContact : "");
		buf.append((this.buyerIp != null) ? this.buyerIp : "");
		buf.append((this.ext1 != null) ? this.ext1 : "");
		buf.append((this.signType != null) ? this.signType : "");
		return buf.toString();
	}

	public String toSignStringResponse() {
		StringBuffer buf = new StringBuffer();
		buf.append((this.name != null) ? this.name : "");
		buf.append((this.version != null) ? this.version : "");
		buf.append((this.charset != null) ? this.charset : "");
		buf.append((this.traceNo != null) ? this.traceNo : "");
		buf.append((this.msgSender != null) ? this.msgSender : "");
		buf.append((this.sendTime != null) ? this.sendTime : "");
		buf.append((this.instCode != null) ? this.instCode : "");
		buf.append((this.orderNo != null) ? this.orderNo : "");
		buf.append((this.orderAmount != null) ? this.orderAmount : "");
		buf.append((this.transNo != null) ? this.transNo : "");
		buf.append((this.transAmount != null) ? this.transAmount : "");
		buf.append((this.transStatus != null) ? this.transStatus : "");
		buf.append((this.transType != null) ? this.transType : "");
		buf.append((this.transTime != null) ? this.transTime : "");
		buf.append((this.merchantNo != null) ? this.merchantNo : "");
		buf.append((this.errorCode != null) ? this.errorCode : "");
		buf.append((this.errorMsg != null) ? this.errorMsg : "");
		buf.append((this.ext1 != null) ? this.ext1 : "");
		buf.append((this.signType != null) ? this.signType : "");
		return buf.toString();
	}

	public void setDirectPay(String payType, String instCode) {
		this.payType = payType;
		this.instCode = instCode;
	}

	public static ShengPay signFormRequest(ShengPay form, String key) {
		String text = form.toSignStringRequest();
		String mac = sign(text, key, form.getCharset());
		form.setSignMsg(mac);
		return form;
	}

	public static ShengPay signFormResponse(ShengPay form, String key) {
		String text = form.toSignStringResponse();
		String mac = sign(text, key, form.getCharset());
		form.setSignMsg(mac);
		return form;
	}

	private static String sign(String data, String key, String charset) {
		MessageDigest msgDigest = null;
		data = data + key;
		byte[] enbyte = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(data.getBytes(charset));
			enbyte = msgDigest.digest();
		} catch (Exception localException) {
		}
		return bin2hex(enbyte);
	}

	private static String createRandomNumber(int length) {
		StringBuffer buffer = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < length; ++i) {
			int tempvalue = rand.nextInt(10);
			buffer.append(tempvalue);
		}
		return buffer.toString();
	}

	public static String bin2hex(byte[] bs) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");

		for (int i = 0; i < bs.length; ++i) {
			int bit = (bs[i] & 0xF0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0xF;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}
}