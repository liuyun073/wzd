package com.liuyun.site.domain;

import java.io.Serializable;

public class AccountRecharge implements Serializable {
	private static final long serialVersionUID = -125906918083036989L;
	private long id;
	private String trade_no;
	private long user_id;
	private int status;
	private double money;
	private String payment;
	private String returntext;
	private String type;
	private String remark;
	private String fee;
	private long verify_userid;
	private String verify_time;
	private String verify_remark;
	private int yes_no;
	private String addtime;
	private String addip;
	private String username;
	private String realname;
	private String paymentname;
	private double total;
	private String card_id;
	private long recharge_kefuid;
	private String recharge_kefu_username;

	public int getYes_no() {
		return this.yes_no;
	}

	public void setYes_no(int yes_no) {
		this.yes_no = yes_no;
	}

	public AccountRecharge(String trade_no, long user_id, String payment,
			String type, String fee, long verify_userid, String addtime,
			String addip, String paymentname) {
		this.trade_no = trade_no;
		this.user_id = user_id;
		this.payment = payment;
		this.type = type;
		this.fee = fee;
		this.verify_userid = verify_userid;
		this.addtime = addtime;
		this.addip = addip;
		this.paymentname = paymentname;
	}

	public AccountRecharge() {
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getRecharge_kefu_username() {
		return this.recharge_kefu_username;
	}

	public void setRecharge_kefu_username(String recharge_kefu_username) {
		this.recharge_kefu_username = recharge_kefu_username;
	}

	public long getRecharge_kefuid() {
		return this.recharge_kefuid;
	}

	public void setRecharge_kefuid(long recharge_kefuid) {
		this.recharge_kefuid = recharge_kefuid;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTrade_no() {
		return this.trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
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

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getReturntext() {
		return this.returntext;
	}

	public void setReturntext(String returntext) {
		this.returntext = returntext;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPaymentname() {
		return this.paymentname;
	}

	public void setPaymentname(String paymentname) {
		this.paymentname = paymentname;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}