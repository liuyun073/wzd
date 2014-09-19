package com.liuyun.site.model.invest;

import java.io.Serializable;

public class InvestBorrowModel implements Serializable {
	private static final long serialVersionUID = -6010522210993109924L;
	private String repayment_yesaccount;
	private String repayment_account;
	private String tender_time;
	private String anum;
	private String inter;
	private String borrow_name;
	private String account;
	private String time_limit;
	private int isday;
	private int time_limit_day;
	private double apr;
	private String username;
	private int credit;
	private long id;
	private long user_id;

	public String getRepayment_yesaccount() {
		return this.repayment_yesaccount;
	}

	public void setRepayment_yesaccount(String repayment_yesaccount) {
		this.repayment_yesaccount = repayment_yesaccount;
	}

	public String getRepayment_account() {
		return this.repayment_account;
	}

	public void setRepayment_account(String repayment_account) {
		this.repayment_account = repayment_account;
	}

	public String getTender_time() {
		return this.tender_time;
	}

	public void setTender_time(String tender_time) {
		this.tender_time = tender_time;
	}

	public String getAnum() {
		return this.anum;
	}

	public void setAnum(String anum) {
		this.anum = anum;
	}

	public String getInter() {
		return this.inter;
	}

	public void setInter(String inter) {
		this.inter = inter;
	}

	public String getBorrow_name() {
		return this.borrow_name;
	}

	public void setBorrow_name(String borrow_name) {
		this.borrow_name = borrow_name;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public int getIsday() {
		return this.isday;
	}

	public void setIsday(int isday) {
		this.isday = isday;
	}

	public int getTime_limit_day() {
		return this.time_limit_day;
	}

	public void setTime_limit_day(int time_limit_day) {
		this.time_limit_day = time_limit_day;
	}

	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
}