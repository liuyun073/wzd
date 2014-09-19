package com.liuyun.site.domain;

public class NoticeConfig {
	private long id;
	private String type;
	private long sms;
	private long email;
	private long letters;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSms() {
		return this.sms;
	}

	public void setSms(long sms) {
		this.sms = sms;
	}

	public long getEmail() {
		return this.email;
	}

	public void setEmail(long email) {
		this.email = email;
	}

	public long getLetters() {
		return this.letters;
	}

	public void setLetters(long letters) {
		this.letters = letters;
	}
}