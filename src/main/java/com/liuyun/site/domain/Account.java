package com.liuyun.site.domain;

import java.io.Serializable;

public class Account implements Serializable {
	private static final long serialVersionUID = -2603972601624997167L;
	private long id;
	private long user_id;
	private double total;
	private double use_money;
	private double no_use_money;
	private double collection;
	private double hongbao;
	private double total_tender_award;

	public double getTotal_tender_award() {
		return this.total_tender_award;
	}

	public void setTotal_tender_award(double total_tender_award) {
		this.total_tender_award = total_tender_award;
	}

	public double getHongbao() {
		return this.hongbao;
	}

	public void setHongbao(double hongbao) {
		this.hongbao = hongbao;
	}

	public String toString() {
		return "Account [id=" + this.id + ", user_id=" + this.user_id
				+ ", total=" + this.total + ", use_money=" + this.use_money
				+ ", no_use_money=" + this.no_use_money + ", collection="
				+ this.collection + "]";
	}

	public Account(long user_id, double total, double use_money,
			double no_use_money, double collection) {
		this.user_id = user_id;
		this.total = total;
		this.use_money = use_money;
		this.no_use_money = no_use_money;
		this.collection = collection;
	}

	public Account() {
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

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getUse_money() {
		return this.use_money;
	}

	public void setUse_money(double use_money) {
		this.use_money = use_money;
	}

	public double getNo_use_money() {
		return this.no_use_money;
	}

	public void setNo_use_money(double no_use_money) {
		this.no_use_money = no_use_money;
	}

	public double getCollection() {
		return this.collection;
	}

	public void setCollection(double collection) {
		this.collection = collection;
	}
}