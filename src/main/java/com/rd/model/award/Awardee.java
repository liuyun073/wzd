package com.rd.model.award;

import java.io.Serializable;

public class Awardee implements Serializable {
	private static final long serialVersionUID = -1299030586222972967L;
	private String time;
	private String name;
	private String award;

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}
}