package com.liuyun.site.model;

import com.liuyun.site.domain.RewardStatistics;

public class RewardStatisticsSumModel extends RewardStatistics {
	private static final long serialVersionUID = -8024157626597130231L;
	private String username;
	private double account;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getAccount() {
		return this.account;
	}

	public void setAccount(double account) {
		this.account = account;
	}
}