package com.rd.domain;

import java.io.Serializable;

public class AttestationType implements Serializable {
	private static final long serialVersionUID = -4876274026037570038L;
	private int type_id;
	private String name;
	private String order;
	private int status;
	private int jifen;
	private String summary;
	private String remark;
	private String addtime;
	private String addip;

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getJifen() {
		return this.jifen;
	}

	public void setJifen(int jifen) {
		this.jifen = jifen;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String toString() {
		return "AttestationType [type_id=" + this.type_id + ", name="
				+ this.name + ", order=" + this.order + ", status="
				+ this.status + ", jifen=" + this.jifen + ", summary="
				+ this.summary + ", remark=" + this.remark + ", addtime="
				+ this.addtime + ", addip=" + this.addip + "]";
	}
}