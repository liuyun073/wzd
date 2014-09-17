package com.rd.domain;

public class SystemConfig {
	private long id;
	private String name;
	private String nid;
	private String value;
	private int type;
	private int style;
	private String status;

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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStyle() {
		return this.style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "SystemConfig [id=" + this.id + ", name=" + this.name + ", nid="
				+ this.nid + ", value=" + this.value + ", type=" + this.type
				+ ", style=" + this.style + ", status=" + this.status + "]";
	}
}