package com.liuyun.site.domain;

public class Huikuan {
	private long id;
	private String huikuan_money;
	private String huikuan_award;
	private long user_id;
	private String status;
	private String addtime;
	private long cash_id;
	private long is_day;
	private String remark;

	public long getIs_day() {
		return this.is_day;
	}

	public void setIs_day(long is_day) {
		this.is_day = is_day;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHuikuan_money() {
		return this.huikuan_money;
	}

	public void setHuikuan_money(String huikuan_money) {
		this.huikuan_money = huikuan_money;
	}

	public String getHuikuan_award() {
		return this.huikuan_award;
	}

	public void setHuikuan_award(String huikuan_award) {
		this.huikuan_award = huikuan_award;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCash_id() {
		return this.cash_id;
	}

	public void setCash_id(long cash_id) {
		this.cash_id = cash_id;
	}
}