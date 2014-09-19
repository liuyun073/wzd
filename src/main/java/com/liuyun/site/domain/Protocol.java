package com.liuyun.site.domain;

public class Protocol {
	private long id;
	private long pid;
	private String protocol_type;
	private String addtime;
	private String addip;
	private long user_id;
	private double money;
	private double repayment_account;
	private String repayment_time;
	private double interest;
	private long borrow_id;
	private String bank_account;
	private String bank_branch;
	private String remark;

	public Protocol() {
	}

	public String getBank_account() {
		return this.bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBank_branch() {
		return this.bank_branch;
	}

	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}

	public String getRepayment_time() {
		return this.repayment_time;
	}

	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}

	public double getRepayment_account() {
		return this.repayment_account;
	}

	public void setRepayment_account(double repayment_account) {
		this.repayment_account = repayment_account;
	}

	public double getInterest() {
		return this.interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Protocol(long pid, String protocol_type, String addtime,
			String addip, String remark) {
		this.pid = pid;
		this.protocol_type = protocol_type;
		this.addtime = addtime;
		this.addip = addip;
		this.remark = remark;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getProtocol_type() {
		return this.protocol_type;
	}

	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}