package com.rd.domain;

import java.io.Serializable;

public class Areainfo implements Serializable {
	private static final long serialVersionUID = 5401140792784741444L;
	private int id;
	private String name;
	private String nid;
	private int pid;
	private String domain;
	private int order;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}