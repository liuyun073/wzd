package com.liuyun.site.domain;

public class UserAward {
	private long id;
	private long user_id;
	private String user_name;
	private int level;
	private long award_id;
	private long point_reduce;
	private long rule_id;
	private String award_name;
	private int status;
	private int receive_status;
	private String addtime;
	private String addip;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getAward_id() {
		return this.award_id;
	}

	public void setAward_id(long award_id) {
		this.award_id = award_id;
	}

	public long getPoint_reduce() {
		return this.point_reduce;
	}

	public void setPoint_reduce(long point_reduce) {
		this.point_reduce = point_reduce;
	}

	public long getRule_id() {
		return this.rule_id;
	}

	public void setRule_id(long rule_id) {
		this.rule_id = rule_id;
	}

	public String getAward_name() {
		return this.award_name;
	}

	public void setAward_name(String award_name) {
		this.award_name = award_name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReceive_status() {
		return this.receive_status;
	}

	public void setReceive_status(int receive_status) {
		this.receive_status = receive_status;
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