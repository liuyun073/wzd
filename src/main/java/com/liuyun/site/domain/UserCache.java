package com.liuyun.site.domain;

import java.io.Serializable;

public class UserCache implements Serializable {
	private static final long serialVersionUID = -889562087705028166L;
	private long user_id;
	private long kefu_userid;
	private String kefu_username;
	private String kefu_addtime;
	private int vip_status;
	private String vip_remark;
	private String vip_money;
	private String vip_verify_remark;
	private String vip_verify_time;
	private int bbs_topics_num;
	private int bbs_posts_num;
	private int credit;
	private int account;
	private int account_use;
	private int account_nouse;
	private int account_waitin;
	private int account_waitintrest;
	private int account_intrest;
	private int account_award;
	private int account_payment;
	private int account_expired;
	private int account_waitvip;
	private int borrow_amount;
	private int vouch_amount;
	private int borrow_loan;
	private int borrow_success;
	private int borrow_wait;
	private int borrow_paymeng;
	private int friends_apply;
	private int login_fail_times;

	public int getLogin_fail_times() {
		return this.login_fail_times;
	}

	public void setLogin_fail_times(int login_fail_times) {
		this.login_fail_times = login_fail_times;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getKefu_userid() {
		return this.kefu_userid;
	}

	public void setKefu_userid(long kefu_userid) {
		this.kefu_userid = kefu_userid;
	}

	public String getKefu_username() {
		return this.kefu_username;
	}

	public void setKefu_username(String kefu_username) {
		this.kefu_username = kefu_username;
	}

	public String getKefu_addtime() {
		return this.kefu_addtime;
	}

	public void setKefu_addtime(String kefu_addtime) {
		this.kefu_addtime = kefu_addtime;
	}

	public int getVip_status() {
		return this.vip_status;
	}

	public void setVip_status(int vip_status) {
		this.vip_status = vip_status;
	}

	public String getVip_remark() {
		return this.vip_remark;
	}

	public void setVip_remark(String vip_remark) {
		this.vip_remark = vip_remark;
	}

	public String getVip_money() {
		return this.vip_money;
	}

	public void setVip_money(String vip_money) {
		this.vip_money = vip_money;
	}

	public String getVip_verify_remark() {
		return this.vip_verify_remark;
	}

	public void setVip_verify_remark(String vip_verify_remark) {
		this.vip_verify_remark = vip_verify_remark;
	}

	public String getVip_verify_time() {
		return this.vip_verify_time;
	}

	public void setVip_verify_time(String vip_verify_time) {
		this.vip_verify_time = vip_verify_time;
	}

	public int getBbs_topics_num() {
		return this.bbs_topics_num;
	}

	public void setBbs_topics_num(int bbs_topics_num) {
		this.bbs_topics_num = bbs_topics_num;
	}

	public int getBbs_posts_num() {
		return this.bbs_posts_num;
	}

	public void setBbs_posts_num(int bbs_posts_num) {
		this.bbs_posts_num = bbs_posts_num;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getAccount() {
		return this.account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getAccount_use() {
		return this.account_use;
	}

	public void setAccount_use(int account_use) {
		this.account_use = account_use;
	}

	public int getAccount_nouse() {
		return this.account_nouse;
	}

	public void setAccount_nouse(int account_nouse) {
		this.account_nouse = account_nouse;
	}

	public int getAccount_waitin() {
		return this.account_waitin;
	}

	public void setAccount_waitin(int account_waitin) {
		this.account_waitin = account_waitin;
	}

	public int getAccount_waitintrest() {
		return this.account_waitintrest;
	}

	public void setAccount_waitintrest(int account_waitintrest) {
		this.account_waitintrest = account_waitintrest;
	}

	public int getAccount_intrest() {
		return this.account_intrest;
	}

	public void setAccount_intrest(int account_intrest) {
		this.account_intrest = account_intrest;
	}

	public int getAccount_award() {
		return this.account_award;
	}

	public void setAccount_award(int account_award) {
		this.account_award = account_award;
	}

	public int getAccount_payment() {
		return this.account_payment;
	}

	public void setAccount_payment(int account_payment) {
		this.account_payment = account_payment;
	}

	public int getAccount_expired() {
		return this.account_expired;
	}

	public void setAccount_expired(int account_expired) {
		this.account_expired = account_expired;
	}

	public int getAccount_waitvip() {
		return this.account_waitvip;
	}

	public void setAccount_waitvip(int account_waitvip) {
		this.account_waitvip = account_waitvip;
	}

	public int getBorrow_amount() {
		return this.borrow_amount;
	}

	public void setBorrow_amount(int borrow_amount) {
		this.borrow_amount = borrow_amount;
	}

	public int getVouch_amount() {
		return this.vouch_amount;
	}

	public void setVouch_amount(int vouch_amount) {
		this.vouch_amount = vouch_amount;
	}

	public int getBorrow_loan() {
		return this.borrow_loan;
	}

	public void setBorrow_loan(int borrow_loan) {
		this.borrow_loan = borrow_loan;
	}

	public int getBorrow_success() {
		return this.borrow_success;
	}

	public void setBorrow_success(int borrow_success) {
		this.borrow_success = borrow_success;
	}

	public int getBorrow_wait() {
		return this.borrow_wait;
	}

	public void setBorrow_wait(int borrow_wait) {
		this.borrow_wait = borrow_wait;
	}

	public int getBorrow_paymeng() {
		return this.borrow_paymeng;
	}

	public void setBorrow_paymeng(int borrow_paymeng) {
		this.borrow_paymeng = borrow_paymeng;
	}

	public int getFriends_apply() {
		return this.friends_apply;
	}

	public void setFriends_apply(int friends_apply) {
		this.friends_apply = friends_apply;
	}
}