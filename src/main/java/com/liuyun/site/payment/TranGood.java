package com.liuyun.site.payment;

import java.io.Serializable;

public class TranGood implements Serializable {
	private static final long serialVersionUID = -5006584744925194986L;
	private String merOrderNum;
	private String tranDateTime;
	private String tranAmt;
	private String feeAmt;
	private String goodsName;
	private String goodsDetail;
	private String buyerName;
	private String buyerContact;
	private String merRemark1;
	private String merRemark2;
	private String remark;

	public String getTranDateTime() {
		return this.tranDateTime;
	}

	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}

	public String getMerOrderNum() {
		return this.merOrderNum;
	}

	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}

	public String getTranAmt() {
		return this.tranAmt;
	}

	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}

	public String getFeeAmt() {
		return this.feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDetail() {
		return this.goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getBuyerName() {
		return this.buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerContact() {
		return this.buyerContact;
	}

	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}

	public String getMerRemark1() {
		return this.merRemark1;
	}

	public void setMerRemark1(String merRemark1) {
		this.merRemark1 = merRemark1;
	}

	public String getMerRemark2() {
		return this.merRemark2;
	}

	public void setMerRemark2(String merRemark2) {
		this.merRemark2 = merRemark2;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}