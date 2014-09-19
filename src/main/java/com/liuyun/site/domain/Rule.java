package com.liuyun.site.domain;

import java.io.Serializable;

public class Rule implements Serializable {
	private static final long serialVersionUID = 8971537069152829541L;
	private long id;
	private String name;
	private Byte status;
	private String addtime;
	private String nid;
	private String remark;
	private String rule_check;

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

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getNid() {
		return this.nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRule_check() {
		return this.rule_check;
	}

	public void setRule_check(String rule_check) {
		this.rule_check = rule_check;
	}
}