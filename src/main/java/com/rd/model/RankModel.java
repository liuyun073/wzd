package com.rd.model;

import java.io.Serializable;

public class RankModel implements Serializable {
	private static final long serialVersionUID = -6632945396689637373L;
	private String username;
	private double tenderMoney;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getTenderMoney() {
		return this.tenderMoney;
	}

	public void setTenderMoney(double tenderMoney) {
		this.tenderMoney = tenderMoney;
	}
}