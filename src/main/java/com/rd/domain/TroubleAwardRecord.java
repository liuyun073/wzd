package com.rd.domain;

import com.rd.util.DateUtils;
import java.io.Serializable;

public class TroubleAwardRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private long user_id;
	private double money;
	private long status;
	private String addtime;

	public TroubleAwardRecord() {
		setAddtime(DateUtils.getNowTimeStr());
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

	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}