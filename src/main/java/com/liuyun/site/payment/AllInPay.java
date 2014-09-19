package com.liuyun.site.payment;

import com.allinpay.ets.client.RequestOrder;
import com.allinpay.ets.client.SecurityUtil;
import com.liuyun.site.context.Global;

public class AllInPay {
	
	private String inputCharset;
	private String pickupUrl;
	private String receiveUrl;
	private String version;
	private String language;
	private String signType;
	private String merchantId;
	private String payerName;
	private String payerEmail;
	private String payerTelephone;
	private String payerIDCard;
	private String pid;
	private String orderNo;
	private String orderAmount;
	private String orderCurrency;
	private String orderDatetime;
	private String orderExpireDatetime;
	private String productName;
	private String productPrice;
	private String productNum;
	private String productId;
	private String productDesc;
	private String ext1;
	private String ext2;
	private String extTL;
	private String payType;
	private String issuerId;
	private String pan;
	private String signMsg;
	private String key;

	public String getInputCharset() {
		return this.inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getPickupUrl() {
		return this.pickupUrl;
	}

	public void setPickupUrl(String pickupUrl) {
		this.pickupUrl = pickupUrl;
	}

	public String getReceiveUrl() {
		return this.receiveUrl;
	}

	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayerName() {
		return this.payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerEmail() {
		return this.payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getPayerTelephone() {
		return this.payerTelephone;
	}

	public void setPayerTelephone(String payerTelephone) {
		this.payerTelephone = payerTelephone;
	}

	public String getPayerIDCard() {
		return this.payerIDCard;
	}

	public void setPayerIDCard(String payerIDCard) {
		this.payerIDCard = payerIDCard;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getOrderCurrency() {
		return this.orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public String getOrderDatetime() {
		return this.orderDatetime;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public String getOrderExpireDatetime() {
		return this.orderExpireDatetime;
	}

	public void setOrderExpireDatetime(String orderExpireDatetime) {
		this.orderExpireDatetime = orderExpireDatetime;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductNum() {
		return this.productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExtTL() {
		return this.extTL;
	}

	public void setExtTL(String extTL) {
		this.extTL = extTL;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getIssuerId() {
		return this.issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getPan() {
		return this.pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getSignMsg() {
		return this.signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void init() {
		this.inputCharset = "1";
		this.version = "v1.0";
		this.signType = "1";
		this.language = "1";
		this.orderCurrency = "0";
	}

	public String getSignMsg(AllInPay tlPay) {
		if ((this.payerIDCard != null) && (!"".equals(this.payerIDCard))) {
			try {
				this.payerIDCard = SecurityUtil.encryptByPublicKey(Global.getValue("tl_cert"), this.payerIDCard);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ((this.pan != null) && (!"".equals(this.pan))) {
			try {
				this.pan = SecurityUtil.encryptByPublicKey(Global.getValue("tl_cert"), this.pan);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		RequestOrder requestOrder = new RequestOrder();
		if ((this.inputCharset != null) && (!"".equals(this.inputCharset))) {
			requestOrder.setInputCharset(Integer.parseInt(this.inputCharset));
		}
		requestOrder.setPickupUrl(this.pickupUrl);
		requestOrder.setReceiveUrl(this.receiveUrl);
		requestOrder.setVersion(this.version);
		if ((this.language != null) && (!"".equals(this.language))) {
			requestOrder.setLanguage(Integer.parseInt(this.language));
		}
		requestOrder.setSignType(Integer.parseInt(this.signType));
		requestOrder.setPayType(Integer.parseInt(this.payType));
		requestOrder.setIssuerId(this.issuerId);
		requestOrder.setMerchantId(this.merchantId);
		requestOrder.setPayerName(this.payerName);
		requestOrder.setPayerEmail(this.payerEmail);
		requestOrder.setPayerTelephone(this.payerTelephone);
		requestOrder.setPayerIDCard(this.payerIDCard);
		requestOrder.setPid(this.pid);
		requestOrder.setOrderNo(this.orderNo);
		requestOrder.setOrderAmount(Long.valueOf(Long.parseLong(this.orderAmount)));
		requestOrder.setOrderCurrency(this.orderCurrency);
		requestOrder.setOrderDatetime(this.orderDatetime);
		requestOrder.setOrderExpireDatetime(this.orderExpireDatetime);
		requestOrder.setProductName(this.productName);
		if ((this.productPrice != null) && (!"".equals(this.productPrice))) {
			requestOrder.setProductPrice(Long.valueOf(Long.parseLong(this.productPrice)));
		}
		if ((this.productNum != null) && (!"".equals(this.productNum))) {
			requestOrder.setProductNum(Integer.parseInt(this.productNum));
		}
		requestOrder.setProductId(this.productId);
		requestOrder.setProductDesc(this.productDesc);
		requestOrder.setExt1(this.ext1);
		requestOrder.setExt2(this.ext2);
		requestOrder.setPan(this.pan);
		requestOrder.setKey(this.key);

		String strSignMsg = requestOrder.doSign();

		return strSignMsg;
	}
}