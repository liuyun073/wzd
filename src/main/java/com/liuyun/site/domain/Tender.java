package com.liuyun.site.domain;

public class Tender {
	private long id;
	private long site_id;
	private long user_id;
	private int status = 1;
	private long borrow_id;
	private String money;
	private String account;
	private String repayment_account;
	private String interest;
	private String part_account;
	private String repayment_yesaccount;
	private String wait_account;
	private String wait_interest;
	private String repayment_yesinterest;
	private String addtime;
	private String addip;
	private int auto_repurchase;
	private int is_auto_tender;
	private int award_after_push;

	public int getIs_auto_tender() {
		return this.is_auto_tender;
	}

	public void setIs_auto_tender(int is_auto_tender) {
		this.is_auto_tender = is_auto_tender;
	}

	public int getAward_after_push() {
		return this.award_after_push;
	}

	public void setAward_after_push(int award_after_push) {
		this.award_after_push = award_after_push;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSite_id() {
		return this.site_id;
	}

	public void setSite_id(long site_id) {
		this.site_id = site_id;
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

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRepayment_account() {
		return this.repayment_account;
	}

	public void setRepayment_account(String repayment_account) {
		this.repayment_account = repayment_account;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPart_account() {
		return this.part_account;
	}

	public void setPart_account(String part_account) {
		this.part_account = part_account;
	}

	public String getRepayment_yesaccount() {
		return this.repayment_yesaccount;
	}

	public void setRepayment_yesaccount(String repayment_yesaccount) {
		this.repayment_yesaccount = repayment_yesaccount;
	}

	public String getWait_account() {
		return this.wait_account;
	}

	public void setWait_account(String wait_account) {
		this.wait_account = wait_account;
	}

	public String getWait_interest() {
		return this.wait_interest;
	}

	public void setWait_interest(String wait_interest) {
		this.wait_interest = wait_interest;
	}

	public String getRepayment_yesinterest() {
		return this.repayment_yesinterest;
	}

	public void setRepayment_yesinterest(String repayment_yesinterest) {
		this.repayment_yesinterest = repayment_yesinterest;
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

	public int getAuto_repurchase() {
		return this.auto_repurchase;
	}

	public void setAuto_repurchase(int auto_repurchase) {
		this.auto_repurchase = auto_repurchase;
	}
}