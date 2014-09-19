package com.liuyun.site.model;

import com.liuyun.site.domain.Tender;
import java.io.Serializable;

public class DetailTender extends Tender implements Serializable {
	private static final long serialVersionUID = 8876350238707212045L;
	private String tender_account;
	private String tender_money;
	private long borrow_userid;
	private String op_username;
	private String username;
	private double apr;
	private String time_limit;
	private int time_limit_day;
	private String borrow_name;
	private long borrow_id;
	private String borrow_account;
	private String borrow_account_yes;
	private int credit_jifen;
	private String credit_pic;
	private String verify_time;

	public String getTender_account() {
		return this.tender_account;
	}

	public void setTender_account(String tender_account) {
		this.tender_account = tender_account;
	}

	public String getTender_money() {
		return this.tender_money;
	}

	public void setTender_money(String tender_money) {
		this.tender_money = tender_money;
	}

	public long getBorrow_userid() {
		return this.borrow_userid;
	}

	public void setBorrow_userid(long borrow_userid) {
		this.borrow_userid = borrow_userid;
	}

	public String getOp_username() {
		return this.op_username;
	}

	public void setOp_username(String op_username) {
		this.op_username = op_username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public String getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public int getTime_limit_day() {
		return this.time_limit_day;
	}

	public void setTime_limit_day(int time_limit_day) {
		this.time_limit_day = time_limit_day;
	}

	public String getBorrow_name() {
		return this.borrow_name;
	}

	public void setBorrow_name(String borrow_name) {
		this.borrow_name = borrow_name;
	}

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public String getBorrow_account() {
		return this.borrow_account;
	}

	public void setBorrow_account(String borrow_account) {
		this.borrow_account = borrow_account;
	}

	public String getBorrow_account_yes() {
		return this.borrow_account_yes;
	}

	public void setBorrow_account_yes(String borrow_account_yes) {
		this.borrow_account_yes = borrow_account_yes;
	}

	public int getCredit_jifen() {
		return this.credit_jifen;
	}

	public void setCredit_jifen(int credit_jifen) {
		this.credit_jifen = credit_jifen;
	}

	public String getCredit_pic() {
		return this.credit_pic;
	}

	public void setCredit_pic(String credit_pic) {
		this.credit_pic = credit_pic;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}
}