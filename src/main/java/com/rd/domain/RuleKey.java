package com.rd.domain;

import java.io.Serializable;

public class RuleKey implements Serializable {
	private static final long serialVersionUID = -3699904413691849255L;
	private long id;
	private String key;
	private String name;
	private String type;
	private String value;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}