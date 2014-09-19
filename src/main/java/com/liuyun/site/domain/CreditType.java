package com.liuyun.site.domain;

import java.io.Serializable;

public class CreditType implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String nid;
	private long value;
	private byte cycle;
	private byte award_times;
	private long interval;
	private String remark;
	private long op_user;
	private long addtime;
	private String addip;
	private long updatetime;
	private String updateip;
	private String rule_nid;
	private byte credit_category;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public long getValue() {
		return this.value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public byte getCycle() {
		return this.cycle;
	}

	public void setCycle(byte cycle) {
		this.cycle = cycle;
	}

	public byte getAward_times() {
		return this.award_times;
	}

	public void setAward_times(byte award_times) {
		this.award_times = award_times;
	}

	public long getInterval() {
		return this.interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getOp_user() {
		return this.op_user;
	}

	public void setOp_user(long op_user) {
		this.op_user = op_user;
	}

	public long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public long getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}

	public String getUpdateip() {
		return this.updateip;
	}

	public void setUpdateip(String updateip) {
		this.updateip = updateip;
	}

	public String getRule_nid() {
		return this.rule_nid;
	}

	public void setRule_nid(String rule_nid) {
		this.rule_nid = rule_nid;
	}

	public byte getCredit_category() {
		return this.credit_category;
	}

	public void setCredit_category(byte credit_category) {
		this.credit_category = credit_category;
	}
}