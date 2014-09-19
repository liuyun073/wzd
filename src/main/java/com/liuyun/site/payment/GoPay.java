package com.liuyun.site.payment;

import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： GoPay   
 * 类描述： 国付宝支付  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 30, 2013 3:19:22 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 30, 2013 3:19:22 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class GoPay extends Pay {
	private static final Logger logger = Logger.getLogger(GoPay.class);
	private String version;
	private String charset;
	private String language;
	private String signType;
	private String isRepeatSubmit;
	private String signValue;
	private String currencyType;
	private String virCardNoIn;
	private String merchantID;
	private String tranCode;
	private String tranIP;
	private String bankCode;
	private String userType;
	private String servetime;
	private String respCode;
	private String submitUrl = "https://www.gopay.com.cn/PGServer/Trans/WebClientAction.do";
	private String serverTimeUrl = "https://www.gopay.com.cn/PGServer/time";
	private String privateKey;
	private String orderId;
	private String gopayOutOrderId;

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

	public String getIsRepeatSubmit() {
		return this.isRepeatSubmit;
	}

	public void setIsRepeatSubmit(String isRepeatSubmit) {
		this.isRepeatSubmit = isRepeatSubmit;
	}

	public String getSignValue() {
		return this.signValue;
	}

	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}

	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getTranCode() {
		return this.tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getVirCardNoIn() {
		return this.virCardNoIn;
	}

	public void setVirCardNoIn(String virCardNoIn) {
		this.virCardNoIn = virCardNoIn;
	}

	public String getMerchantID() {
		return this.merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getSubmitUrl() {
		return this.submitUrl;
	}

	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}

	public String getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getGopayOutOrderId() {
		return this.gopayOutOrderId;
	}

	public void setGopayOutOrderId(String gopayOutOrderId) {
		this.gopayOutOrderId = gopayOutOrderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTranIP() {
		return this.tranIP;
	}

	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}

	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getRespCode() {
		return this.respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getServetime() {
		return this.servetime;
	}

	public void setServetime(String servetime) {
		this.servetime = servetime;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void init() {
		this.version = "2.1";
		this.charset = "2";
		this.language = "1";
		this.signType = "1";
		this.currencyType = "156";
		this.isRepeatSubmit = "1";

		this.tranCode = "8888";

		this.servetime = getGopayServerTime();

		String _v = getSignVal();
		logger.debug("Sign明文:" + _v);
		this.signValue = getGopaySignValueByMD5(_v);
		logger.debug("Sign密文:" + this.signValue);
	}

	private String getSignVal() {
		StringBuffer sb = new StringBuffer();
		sb.append("version=[").append(getVersion()).append("]");
		sb.append("tranCode=[").append(getTranCode()).append("]");
		sb.append("merchantID=[").append(getMerchantID()).append("]");
		sb.append("merOrderNum=[").append(getGood().getMerOrderNum()).append("]");
		sb.append("tranAmt=[").append(getGood().getTranAmt()).append("]");
		if (com.liuyun.site.util.StringUtils.isBlank(getGood().getFeeAmt()))
			sb.append("feeAmt=[]");
		else {
			sb.append("feeAmt=[").append(getGood().getFeeAmt()).append("]");
		}
		sb.append("tranDateTime=[").append(getGood().getTranDateTime()).append("]");
		sb.append("frontMerUrl=[").append(getFrontMerUrl()).append("]");
		sb.append("backgroundMerUrl=[").append(getBackgroundMerUrl()).append("]");
		sb.append("orderId=[]");
		sb.append("gopayOutOrderId=[]");
		sb.append("tranIP=[").append(getTranIP()).append("]");
		sb.append("respCode=[]");
		sb.append("gopayServerTime=[").append(com.liuyun.site.util.StringUtils.isNull(this.servetime)).append("]");
		sb.append("VerficationCode=[").append(getPrivateKey()).append("]");
		return sb.toString();
	}

	public String getCallbackSignVal() {
		StringBuffer sb = new StringBuffer();
		sb.append("version=[").append(getVersion()).append("]");
		sb.append("tranCode=[").append(getTranCode()).append("]");
		sb.append("merchantID=[").append(getMerchantID()).append("]");
		sb.append("merOrderNum=[").append(getGood().getMerOrderNum()).append("]");
		sb.append("tranAmt=[").append(getGood().getTranAmt()).append("]");
		sb.append("feeAmt=[").append(getGood().getFeeAmt()).append("]");
		sb.append("tranDateTime=[").append(getGood().getTranDateTime()).append("]");
		sb.append("frontMerUrl=[").append(getFrontMerUrl()).append("]");
		sb.append("backgroundMerUrl=[").append(getBackgroundMerUrl()).append("]");
		sb.append("orderId=[").append(getOrderId()).append("]");
		sb.append("gopayOutOrderId=[").append(getGopayOutOrderId()).append("]");
		sb.append("tranIP=[").append(getTranIP()).append("]");
		sb.append("respCode=[").append(this.respCode).append("]");
		sb.append("gopayServerTime=[]");
		sb.append("VerficationCode=[").append(getPrivateKey()).append("]");
		return sb.toString();
	}

	public String getCallbackMd5SignVal() {
		String callbackSignVal = getCallbackSignVal();
		logger.debug("Callback Sign明文:" + getCallbackSignVal());
		String callbackMd5SignVal = getGopaySignValueByMD5(callbackSignVal);
		logger.debug("Callback Sign密文:" + callbackMd5SignVal);
		return callbackMd5SignVal;
	}

	public String submitGet() {
		StringBuffer url = new StringBuffer();
		url.append(getSubmitUrl()).append("?");
		url.append("version=").append(getVersion()).append("&");
		url.append("charset=").append(getCharset()).append("&");
		url.append("language=").append(getLanguage()).append("&");
		url.append("signType=").append(getSignType()).append("&");
		url.append("tranCode=").append(getTranCode()).append("&");
		url.append("merchantID=").append(getMerchantID()).append("&");
		url.append("merOrderNum=").append(getGood().getMerOrderNum()).append("&");
		url.append("tranAmt=").append(getGood().getTranAmt()).append("&");
		url.append("feeAmt=&");

		url.append("currencyType=").append(getCurrencyType()).append("&");
		url.append("frontMerUrl=").append(getFrontMerUrl()).append("&");
		url.append("backgroundMerUrl=").append(getBackgroundMerUrl()).append("&");

		url.append("tranDateTime=").append(getGood().getTranDateTime()).append("&");
		url.append("virCardNoIn=").append(getVirCardNoIn()).append("&");
		url.append("tranIP=").append(getTranIP()).append("&");
		url.append("isRepeatSubmit=").append(getIsRepeatSubmit()).append("&");

		url.append("goodsName=").append(getGood().getGoodsName()).append("&");
		url.append("buyerName=").append(getGood().getBuyerName()).append("&");
		url.append("signValue=").append(getSignValue()).append("&");
		url.append("userType=").append(com.liuyun.site.util.StringUtils.isNull(getUserType())).append("&");
		url.append("bankCode=").append(com.liuyun.site.util.StringUtils.isNull(getBankCode())).append("&");
		url.append("gopayServerTime=").append(this.servetime).append("&");
		logger.info(url.toString());
		return url.toString();
	}

	public String getGopayServerTime() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy("rfc2109");
		httpClient.getParams().setIntParameter("http.socket.timeout", 10000);
		GetMethod getMethod = new GetMethod(this.serverTimeUrl);
		getMethod.getParams().setParameter("http.protocol.content-charset", "UTF-8");

		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != 200){
				//getMethod.releaseConnection();
				return null;
			}
			String respString = org.apache.commons.lang.StringUtils.trim(new String(getMethod.getResponseBody(), "UTF-8"));
			return respString;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}

		return null;
	}

	private String getGopaySignValueByMD5(String signvalue) {
		String val = "";
		try {
			val = DigestUtils.md5Hex(signvalue);
		} catch (Exception e) {
			logger.error("getGopaySignValueByMD5() has error");
			e.printStackTrace();
		}
		return val;
	}
}