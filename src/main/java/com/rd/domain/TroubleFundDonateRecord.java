package com.rd.domain;

import com.rd.util.DateUtils;

public class TroubleFundDonateRecord {
	private long id;
	private long user_id;
	private double money;
	private long giving_way;
	private long display_way;
	private double award_money;
	private String remark;
	private String addtime;

	public TroubleFundDonateRecord() {
		setAddtime(DateUtils.getNowTimeStr());
	}

	public double getAward_money() {
		return this.award_money;
	}

	public void setAward_money(double award_money) {
		this.award_money = award_money;
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

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public long getGiving_way() {
		return this.giving_way;
	}

	public void setGiving_way(long giving_way) {
		this.giving_way = giving_way;
	}

	public long getDisplay_way() {
		return this.display_way;
	}

	public void setDisplay_way(long display_way) {
		this.display_way = display_way;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}