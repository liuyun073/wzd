package com.liuyun.site.domain;

import java.io.Serializable;

public class Purview implements Serializable, Comparable<Purview> {
	private static final long serialVersionUID = -540832868245774144L;
	private long id;
	private long pid;
	private String name;
	private int level;
	private String purview;
	private String url;
	private String remark;
	private boolean checked;
	private long user_type_id;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getPurview() {
		return this.purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public long getUser_type_id() {
		return this.user_type_id;
	}

	public void setUser_type_id(long user_type_id) {
		this.user_type_id = user_type_id;
	}

	public String toString() {
		return "Purview [id=" + this.id + ", pid=" + this.pid + ", name="
				+ this.name + ", level=" + this.level + ", purview="
				+ this.purview + ", url=" + this.url + ", remark="
				+ this.remark + "]";
	}

	public int compareTo(Purview p) {
		if (p.getId() == getId())
			return 0;
		if (p.getId() > getId()) {
			return 1;
		}
		return -1;
	}
}