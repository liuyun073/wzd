package com.liuyun.site.domain;

public class UserType {
	private long type_id;
	private String name;
	private String purview;
	private String order;
	private int status;
	private int type;
	private String summary;
	private String remark;
	private String addtime;
	private String addip;

	public String getAddip() {
		return this.addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public long getType_id() {
		return this.type_id;
	}

	public void setType_id(long type_id) {
		this.type_id = type_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurview() {
		return this.purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String toString() {
		return "UserType [type_id=" + this.type_id + ", name=" + this.name
				+ ", purview=" + this.purview + ", order=" + this.order
				+ ", status=" + this.status + ", type=" + this.type
				+ ", summary=" + this.summary + ", remark=" + this.remark
				+ ", addtime=" + this.addtime + "]";
	}
}