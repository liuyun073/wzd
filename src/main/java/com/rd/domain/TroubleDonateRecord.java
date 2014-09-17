package com.rd.domain;

import com.rd.util.DateUtils;
import java.io.Serializable;

public class TroubleDonateRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private long user_id;
	private double money;
	private String borrow_time;
	private String borrow_use;
	private String repayment_time;
	private String borrow_content;
	private String remark;
	private long status;
	private String addtime;

	public TroubleDonateRecord() {
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

	public String getBorrow_time() {
		return this.borrow_time;
	}

	public void setBorrow_time(String borrow_time) {
		this.borrow_time = borrow_time;
	}

	public String getBorrow_use() {
		return this.borrow_use;
	}

	public void setBorrow_use(String borrow_use) {
		this.borrow_use = borrow_use;
	}

	public String getRepayment_time() {
		return this.repayment_time;
	}

	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}

	public String getBorrow_content() {
		return this.borrow_content;
	}

	public void setBorrow_content(String borrow_content) {
		this.borrow_content = borrow_content;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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