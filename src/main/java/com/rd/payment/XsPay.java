package com.rd.payment;

import com.rd.context.Global;
import com.rd.util.CharsetTypeEnum;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： XsPay   
 * 类描述： 新生支付   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 30, 2013 3:22:07 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 30, 2013 3:22:07 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class XsPay {
	private static final Logger logger = Logger.getLogger(XsPay.class);
	private String version;
	private String url = "http://www.hnapay.com/website/pay.htm";
	private String serialID;
	private String submitTime;
	private String failureTime;
	private String customerIP;
	private String orderDetails;
	private String totalAmount;
	private String type;
	private String buyerMarked;
	private String payType;
	private String orgCode;
	private String currencyCode;
	private String directFlag;
	private String borrowingMarked;
	private String couponFlag;
	private String platformID;
	private String returnUrl;
	private String noticeUrl;
	private String partnerID;
	private String remark;
	private String charset;
	private String signType;
	private String signMsg;
	private String orderID;
	private String orderAmount;
	private String displayName;
	private String goodsName;
	private String goodsCount;

	public String getOrderID() {
		return this.orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}

	public void init() {
		this.version = "2.6";
		this.payType = "ALL";
		this.currencyCode = "1";
		this.borrowingMarked = "0";
		this.couponFlag = "0";
		this.type = "1000";
		this.currencyCode = "1";
		this.directFlag = "0";
		this.couponFlag = "1";
		this.charset = "1";
		this.platformID = "";
		this.buyerMarked = "";
		this.orgCode = "";
		this.goodsName = Global.getValue("webname");
		Date date = new Date();
		long newdate = date.getTime();
		String stringdate = String.valueOf(newdate);
		this.serialID = stringdate;

		Calendar cal = Calendar.getInstance();
		int day = cal.get(5);
		int month = cal.get(2) + 1;
		int year = cal.get(1);
		int hour = cal.get(11);
		int minute = cal.get(12);
		int second = cal.get(13);
		String x = "" + year;
		String y = "" + (year + 1);
		if (month + 1 < 10) {
			x = x + "0" + month;
			y = y + "0" + month;
		} else {
			x = x + month;
			y = y + month;
		}
		if (day < 10) {
			x = x + "0" + day;
			y = y + "0" + day;
		} else {
			x = x + day;
			y = y + day;
		}

		if (hour < 10) {
			x = x + "0" + hour;
			y = y + "0" + hour;
		} else {
			x = x + hour;
			y = y + hour;
		}

		if (minute < 10) {
			x = x + "0" + minute;
			y = y + "0" + minute;
		} else {
			x = x + minute;
			y = y + minute;
		}

		if (second < 10) {
			x = x + "0" + second;
			y = y + "0" + second;
		} else {
			x = x + second;
			y = y + second;
		}
		if (x.length() == 15) {
			x = x.substring(0, 14);
		}
		if (x.length() == 15) {
			y = y.substring(0, 14);
		}
		this.submitTime = x;
		this.failureTime = y;
		String key1 = "";
		String[] fieldArr = { this.orderID, this.orderAmount, this.displayName,
				this.goodsName, this.goodsCount };
		for (int i = 0; i < fieldArr.length; ++i) {
			if (i == fieldArr.length - 1) {
				key1 = key1 + fieldArr[i];
				break;
			}
			key1 = key1 + fieldArr[i] + ",";
		}

		this.orderDetails = key1;
		this.signMsg = submitGet();
		try {
			if ("2".equals(this.signType)) {
				String pkey = Global.getValue("pkey");
				this.signMsg = (this.signMsg + "&pkey=" + pkey);
				this.signMsg = hnapay.genSignByMD5(this.signMsg,
						CharsetTypeEnum.UTF8);
				logger.debug("signMsg=========" + this.signMsg);
			}
			if ("1".equals(this.signType))
				this.signMsg = hnapay.genSignByRSA(this.signMsg,
						CharsetTypeEnum.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("SignMsg:" + this.signMsg);
		String _v = getSignVal();
		logger.debug("Sign明文:" + _v);
	}

	private String getSignVal() {
		StringBuffer sb = new StringBuffer();
		sb.append("version=[").append(getVersion()).append("]");
		sb.append("serialID=[").append(getSerialID()).append("]");
		sb.append("submitTime=[").append(getSubmitTime()).append("]");
		sb.append("failureTime=[").append(getFailureTime()).append("]");
		sb.append("customerIP=[").append(getCustomerIP()).append("]");
		sb.append("orderDetails=[").append(getOrderDetails()).append("]");
		sb.append("totalAmount=[").append(getTotalAmount()).append("]");
		sb.append("type=[").append(getType()).append("]");
		sb.append("buyerMarked=[").append(getBuyerMarked()).append("]");
		sb.append("payType=[").append(getPayType()).append("]");
		sb.append("orgCode=[").append(getOrgCode()).append("]");
		sb.append("currencyCode=[").append(getCurrencyCode()).append("]");
		sb.append("directFlag=[").append(getDirectFlag()).append("]");
		sb.append("borrowingMarked=[").append(getBorrowingMarked()).append("]");
		sb.append("couponFlag=[").append(getCouponFlag()).append("]");
		sb.append("platformID=[").append(getPlatformID()).append("]");
		sb.append("returnUrl=[").append(getReturnUrl()).append("]");
		sb.append("noticeUrl=[").append(getNoticeUrl()).append("]");
		sb.append("partnerID=[").append(getPartnerID()).append("]");
		sb.append("remark=[").append(getRemark()).append("]");
		sb.append("charset=[").append(getCharset()).append("]");
		sb.append("signType=[").append(getSignType()).append("]");
		sb.append("signMsg=[").append(getSignMsg()).append("]");

		return sb.toString();
	}

	public String submitGet() {
		StringBuffer url = new StringBuffer();

		url.append("version=").append(getVersion()).append("&");
		url.append("serialID=").append(getSerialID()).append("&");
		url.append("submitTime=").append(getSubmitTime()).append("&");
		url.append("failureTime=").append(getFailureTime()).append("&");
		url.append("customerIP=").append(getCustomerIP()).append("&");
		url.append("orderDetails=").append(getOrderDetails()).append("&");
		url.append("totalAmount=").append(getTotalAmount()).append("&");
		url.append("type=").append(getType()).append("&");
		url.append("buyerMarked=").append(getBuyerMarked()).append("&");

		url.append("payType=").append(getPayType()).append("&");
		url.append("orgCode=").append(getOrgCode()).append("&");
		url.append("currencyCode=").append(getCurrencyCode()).append("&");

		url.append("directFlag=").append(getDirectFlag()).append("&");
		url.append("borrowingMarked=").append(getBorrowingMarked()).append("&");
		url.append("couponFlag=").append(getCouponFlag()).append("&");
		url.append("platformID=").append(getPlatformID()).append("&");

		url.append("returnUrl=").append(getReturnUrl()).append("&");
		url.append("noticeUrl=").append(getNoticeUrl()).append("&");
		url.append("partnerID=").append(getPartnerID()).append("&");
		url.append("remark=").append(getRemark()).append("&");
		url.append("charset=").append(getCharset()).append("&");
		url.append("signType=").append(getSignType());
		logger.info(url.toString());
		return url.toString();
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSerialID() {
		return this.serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public String getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getFailureTime() {
		return this.failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getCustomerIP() {
		return this.customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public String getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyerMarked() {
		return this.buyerMarked;
	}

	public void setBuyerMarked(String buyerMarked) {
		this.buyerMarked = buyerMarked;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPlatformID() {
		return this.platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNoticeUrl() {
		return this.noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getPartnerID() {
		return this.partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDirectFlag() {
		return this.directFlag;
	}

	public void setDirectFlag(String directFlag) {
		this.directFlag = directFlag;
	}

	public String getBorrowingMarked() {
		return this.borrowingMarked;
	}

	public void setBorrowingMarked(String borrowingMarked) {
		this.borrowingMarked = borrowingMarked;
	}

	public String getCouponFlag() {
		return this.couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
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

	public static Logger getLogger() {
		return logger;
	}

	public String callbackSign() {
		String content = "version" + this.version + "url" + this.url
				+ " serialID" + this.serialID + "submitTime" + this.submitTime
				+ " failureTime" + this.failureTime + " customerIP"
				+ this.customerIP + " orderDetails" + this.orderDetails
				+ " totalAmount" + this.totalAmount + " type" + this.type
				+ " buyerMarked" + this.buyerMarked + " payType" + this.payType
				+ " orgCode" + this.orgCode + "currencyCode"
				+ this.currencyCode + " directFlag" + this.directFlag
				+ " borrowingMarked" + this.borrowingMarked + " couponFlag"
				+ this.couponFlag + " platformID" + this.platformID
				+ " returnUrl" + this.returnUrl + " noticeUrl" + this.noticeUrl
				+ " partnerID" + this.partnerID + " remark" + this.remark
				+ " charset" + this.charset + " signType" + this.signType
				+ " signMsg" + this.signMsg + " orderID" + this.orderID
				+ " orderAmount" + this.orderAmount + " displayName"
				+ this.displayName + " goodsName" + this.goodsName
				+ " goodsCount" + this.goodsCount;
		return content;
	}

	public String encodeSignMD5() {
		String content = "version" + this.version + "url" + this.url
				+ " serialID" + this.serialID + "submitTime" + this.submitTime
				+ " failureTime" + this.failureTime + " customerIP"
				+ this.customerIP + " orderDetails" + this.orderDetails
				+ " totalAmount" + this.totalAmount + " type" + this.type
				+ " buyerMarked" + this.buyerMarked + " payType" + this.payType
				+ " orgCode" + this.orgCode + "currencyCode"
				+ this.currencyCode + " directFlag" + this.directFlag
				+ " borrowingMarked" + this.borrowingMarked + " couponFlag"
				+ this.couponFlag + " platformID" + this.platformID
				+ " returnUrl" + this.returnUrl + " noticeUrl" + this.noticeUrl
				+ " partnerID" + this.partnerID + " remark" + this.remark
				+ " charset" + this.charset + " signType" + this.signType
				+ " signMsg" + this.signMsg + " orderID" + this.orderID
				+ " orderAmount" + this.orderAmount + " displayName"
				+ this.displayName + " goodsName" + this.goodsName
				+ " goodsCount" + this.goodsCount;
		String signMD5 = "";
		try {
			signMD5 = hnapay.genSignByMD5(content, CharsetTypeEnum.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signMD5;
	}

	public String toString() {
		return "XsPay [version=" + this.version + ", url=" + this.url
				+ ", serialID=" + this.serialID + ", submitTime="
				+ this.submitTime + ", failureTime=" + this.failureTime
				+ ", customerIP=" + this.customerIP + ", orderDetails="
				+ this.orderDetails + ", totalAmount=" + this.totalAmount
				+ ", type=" + this.type + ", buyerMarked=" + this.buyerMarked
				+ ", payType=" + this.payType + ", orgCode=" + this.orgCode
				+ ", currencyCode=" + this.currencyCode + ", directFlag="
				+ this.directFlag + ", borrowingMarked=" + this.borrowingMarked
				+ ", couponFlag=" + this.couponFlag + ", platformID="
				+ this.platformID + ", returnUrl=" + this.returnUrl
				+ ", noticeUrl=" + this.noticeUrl + ", partnerID="
				+ this.partnerID + ", remark=" + this.remark + ", charset="
				+ this.charset + ", signType=" + this.signType + ", signMsg="
				+ this.signMsg + ", orderID=" + this.orderID + ", orderAmount="
				+ this.orderAmount + ", displayName=" + this.displayName
				+ ", goodsName=" + this.goodsName + ", goodsCount="
				+ this.goodsCount + "]";
	}
}