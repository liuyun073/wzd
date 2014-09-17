package com.rd.domain;

public class Linkage {
	private int id;
	private int status;
	private int order;
	private int type_id;
	private int pid;
	private String name;
	private String value;
	private String addtime;
	private String addip;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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
}