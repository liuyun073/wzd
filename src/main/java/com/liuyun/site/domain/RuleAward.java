package com.liuyun.site.domain;

public class RuleAward {
	private long id;
	private String name;
	private String start_date;
	private String end_date;
	private int award_type;
	private int time_limit;
	private int max_times;
	private int base_point;
	private int money_limit;
	private double total_money;
	private double bestow_money;
	private int is_absolute;
	private int msg_type;
	private String context;
	private String subject;
	private String content;
	private int back_type;
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

	public String getStart_date() {
		return this.start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return this.end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getAward_type() {
		return this.award_type;
	}

	public void setAward_type(int award_type) {
		this.award_type = award_type;
	}

	public int getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(int time_limit) {
		this.time_limit = time_limit;
	}

	public int getMax_times() {
		return this.max_times;
	}

	public void setMax_times(int max_times) {
		this.max_times = max_times;
	}

	public int getBase_point() {
		return this.base_point;
	}

	public void setBase_point(int base_point) {
		this.base_point = base_point;
	}

	public int getMoney_limit() {
		return this.money_limit;
	}

	public void setMoney_limit(int money_limit) {
		this.money_limit = money_limit;
	}

	public double getTotal_money() {
		return this.total_money;
	}

	public void setTotal_money(double total_money) {
		this.total_money = total_money;
	}

	public double getBestow_money() {
		return this.bestow_money;
	}

	public void setBestow_money(double bestow_money) {
		this.bestow_money = bestow_money;
	}

	public int getIs_absolute() {
		return this.is_absolute;
	}

	public void setIs_absolute(int is_absolute) {
		this.is_absolute = is_absolute;
	}

	public int getMsg_type() {
		return this.msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBack_type() {
		return this.back_type;
	}

	public void setBack_type(int back_type) {
		this.back_type = back_type;
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