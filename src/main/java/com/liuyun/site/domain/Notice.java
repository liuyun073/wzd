package com.liuyun.site.domain;

public class Notice {
	private long receive_userid;
	private long send_userid;
	private String title;
	private String content;
	private String name = "";
	private String addtime;
	private int status;
	private String mobile;
	private String result;

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public long getReceive_userid() {
		return this.receive_userid;
	}

	public void setReceive_userid(long receive_userid) {
		this.receive_userid = receive_userid;
	}

	public long getSend_userid() {
		return this.send_userid;
	}

	public void setSend_userid(long send_userid) {
		this.send_userid = send_userid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String warpContent() {
		return getContent();
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}