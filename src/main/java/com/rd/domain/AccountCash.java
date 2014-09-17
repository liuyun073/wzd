package com.rd.domain;

import java.io.Serializable;

public class AccountCash implements Serializable {
	private static final long serialVersionUID = -8321395313750326323L;
	private long id;
	private long user_id;
	private int status;
	private String account;
	private String bank;
	private String branch;
	private String total;
	private String credited;
	private String fee;
	private long verify_userid;
	private String verify_time;
	private String verify_remark;
	private String addtime;
	private String addip;
	private String bankname;
	private String username;
	private String realname;
	private String verify_username;
	private double hongbao;
	private long cash_type;
	private double freecash;

	public double getHongbao() {
		return this.hongbao;
	}

	public void setHongbao(double hongbao) {
		this.hongbao = hongbao;
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

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCredited() {
		return this.credited;
	}

	public void setCredited(String credited) {
		this.credited = credited;
	}

	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public long getVerify_userid() {
		return this.verify_userid;
	}

	public void setVerify_userid(long verify_userid) {
		this.verify_userid = verify_userid;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public String getVerify_remark() {
		return this.verify_remark;
	}

	public void setVerify_remark(String verify_remark) {
		this.verify_remark = verify_remark;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getVerify_username() {
		return this.verify_username;
	}

	public void setVerify_username(String verify_username) {
		this.verify_username = verify_username;
	}

	public double getFreecash() {
		return this.freecash;
	}

	public void setFreecash(double freecash) {
		this.freecash = freecash;
	}
}