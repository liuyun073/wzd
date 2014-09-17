package com.rd.domain;

public class ObjAward {
	private long id;
	private String name;
	private long rule_id;
	private int level;
	private int rate;
	private long point_limit;
	private long bestow;
	private long total;
	private int award_limit;
	private String description;
	private double ratio;
	private long obj_value;
	private String pic_url;
	private String object_rule;
	private String addtime;
	private String addip;

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

	public long getRule_id() {
		return this.rule_id;
	}

	public void setRule_id(long rule_id) {
		this.rule_id = rule_id;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public long getPoint_limit() {
		return this.point_limit;
	}

	public void setPoint_limit(long point_limit) {
		this.point_limit = point_limit;
	}

	public long getBestow() {
		return this.bestow;
	}

	public void setBestow(long bestow) {
		this.bestow = bestow;
	}

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getAward_limit() {
		return this.award_limit;
	}

	public void setAward_limit(int award_limit) {
		this.award_limit = award_limit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRatio() {
		return this.ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public long getObj_value() {
		return this.obj_value;
	}

	public void setObj_value(long obj_value) {
		this.obj_value = obj_value;
	}

	public String getPic_url() {
		return this.pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getObject_rule() {
		return this.object_rule;
	}

	public void setObject_rule(String object_rule) {
		this.object_rule = object_rule;
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