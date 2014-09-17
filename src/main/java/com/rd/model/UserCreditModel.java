package com.rd.model;

import com.rd.domain.UserCredit;
import java.io.Serializable;

public class UserCreditModel extends UserCredit implements Serializable {
	private static final long serialVersionUID = -7898681921878574306L;
	private String creditTypeName;
	private String userName;
	private String realname;
	private String creditLogAddTime;
	private String creditLogRemark;
	private String credit_pic;

	public String getCredit_pic() {
		return this.credit_pic;
	}

	public void setCredit_pic(String credit_pic) {
		this.credit_pic = credit_pic;
	}

	public String getCreditTypeName() {
		return this.creditTypeName;
	}

	public void setCreditTypeName(String creditTypeName) {
		this.creditTypeName = creditTypeName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCreditLogAddTime() {
		return this.creditLogAddTime;
	}

	public void setCreditLogAddTime(String creditLogAddTime) {
		this.creditLogAddTime = creditLogAddTime;
	}

	public String getCreditLogRemark() {
		return this.creditLogRemark;
	}

	public void setCreditLogRemark(String creditLogRemark) {
		this.creditLogRemark = creditLogRemark;
	}
}