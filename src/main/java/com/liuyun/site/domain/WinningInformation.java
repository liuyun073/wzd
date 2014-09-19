package com.liuyun.site.domain;

public class WinningInformation {
	private long id;
	private long user_id;
	private String username;
	private long level;
	private long award_id;
	private long point_reduce;
	private long rule_id;
	private String award_name;
	private long status;
	private String gmt_create;
	private String gmt_modified;
	private String attributes;
	private double winning_money;

	public double getWinning_money() {
		return this.winning_money;
	}

	public void setWinning_money(double winning_money) {
		this.winning_money = winning_money;
	}

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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getLevel() {
		return this.level;
	}

	public void setLevel(long level) {
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

	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getGmt_create() {
		return this.gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_modified() {
		return this.gmt_modified;
	}

	public void setGmt_modified(String gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	public String getAttributes() {
		return this.attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
}