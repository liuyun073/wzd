package com.liuyun.site.domain;

import java.io.Serializable;

public class AutoTenderOrder implements Serializable {
	private static final long serialVersionUID = -2603972601624997167L;
	private long id;
	private long user_id;
	private String username;
	private long auto_order;
	private long auto_score;
	private long user_useMoney;
	private long user_useMoneyOrder;
	private long user_autoMoney;
	private long user_autoMoneyOrder;
	private long user_jifen;
	private long user_jifenOrder;
	private long auto_lasttime;

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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getAuto_order() {
		return this.auto_order;
	}

	public void setAuto_order(long auto_order) {
		this.auto_order = auto_order;
	}

	public long getAuto_score() {
		return this.auto_score;
	}

	public void setAuto_score(long auto_score) {
		this.auto_score = auto_score;
	}

	public long getUser_useMoney() {
		return this.user_useMoney;
	}

	public void setUser_useMoney(long user_useMoney) {
		this.user_useMoney = user_useMoney;
	}

	public long getUser_useMoneyOrder() {
		return this.user_useMoneyOrder;
	}

	public void setUser_useMoneyOrder(long user_useMoneyOrder) {
		this.user_useMoneyOrder = user_useMoneyOrder;
	}

	public long getUser_autoMoney() {
		return this.user_autoMoney;
	}

	public void setUser_autoMoney(long user_autoMoney) {
		this.user_autoMoney = user_autoMoney;
	}

	public long getUser_autoMoneyOrder() {
		return this.user_autoMoneyOrder;
	}

	public void setUser_autoMoneyOrder(long user_autoMoneyOrder) {
		this.user_autoMoneyOrder = user_autoMoneyOrder;
	}

	public long getUser_jifen() {
		return this.user_jifen;
	}

	public void setUser_jifen(long user_jifen) {
		this.user_jifen = user_jifen;
	}

	public long getUser_jifenOrder() {
		return this.user_jifenOrder;
	}

	public void setUser_jifenOrder(long user_jifenOrder) {
		this.user_jifenOrder = user_jifenOrder;
	}

	public long getAuto_lasttime() {
		return this.auto_lasttime;
	}

	public void setAuto_lasttime(long auto_lasttime) {
		this.auto_lasttime = auto_lasttime;
	}
}