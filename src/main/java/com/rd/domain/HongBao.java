package com.rd.domain;

public class HongBao {
	private long id;
	private String type;
	private String addtime;
	private String addip;
	private String remark;
	private long user_id;
	private double hongbao_money;

	public HongBao() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HongBao(String type, String addtime, String addip, String remark,
			long user_id, double hongbao_money) {
		this.type = type;
		this.addtime = addtime;
		this.addip = addip;
		this.remark = remark;
		this.user_id = user_id;
		this.hongbao_money = hongbao_money;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public double getHongbao_money() {
		return this.hongbao_money;
	}

	public void setHongbao_money(double hongbao_money) {
		this.hongbao_money = hongbao_money;
	}
}