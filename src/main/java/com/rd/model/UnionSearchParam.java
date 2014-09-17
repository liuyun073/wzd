package com.rd.model;

import java.util.HashMap;
import java.util.Map;

public class UnionSearchParam extends SearchParam {
	private String type;
	private String apr;
	private String limit_time;
	private String account;
	private String order;
	Map map = new HashMap();

	public UnionSearchParam(String type, String apr, String limit_time,
			String account, String order) {
		this.type = type;
		this.apr = apr;
		this.limit_time = limit_time;
		this.account = account;
		this.order = order;
	}

	public String getType() {
		return this.type;
	}

	public String getApr() {
		return this.apr;
	}

	public String getLimit_time() {
		return this.limit_time;
	}

	public String getAccount() {
		return this.account;
	}

	public Map getMap() {
		return this.map;
	}

	public Map<String, Object> toMap() {
		this.map.put("search", "union");
		this.map.put("sType", this.type);
		this.map.put("sApr", this.apr);
		this.map.put("sLimit", this.limit_time);
		this.map.put("sAccount", this.account);
		return this.map;
	}

	public String getSearchParamSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTypeSql(this.type));
		sb.append(getAprSql(this.apr));
		sb.append(getLimitTimeSql(this.limit_time));
		sb.append(getAccountSql(this.account));
		return sb.toString();
	}

	public String getOrderSql() {
		return getOrderSql(this.order);
	}

	private String getTypeSql(String type) {
		String sql = " ";
		if (type.equals("1"))
			sql = " and p1.is_mb=1 ";
		else if (type.equals("2"))
			sql = " and p1.is_xin=1 ";
		else if (type.equals("3"))
			sql = " and p1.is_fast=1 ";
		else if (type.equals("4"))
			sql = " and p1.is_jin=1 ";
		else if (type.equals("5"))
			sql = " and p1.is_vouch=1 ";
		else if (type.equals("6"))
			sql = " and p1.is_art=1 ";
		else if (type.equals("7"))
			sql = " and p1.is_charity=1 ";
		else if (type.equals("8"))
			sql = "  ";
		else if (type.equals("9"))
			sql = " and p1.is_project=1 ";
		else if (type.equals("10"))
			sql = " and p1.is_flow=1 ";
		else if (type.equals("11")) {
			sql = " and p1.is_donation=1";
		}
		return sql;
	}

	private String getAprSql(String apr) {
		String sql = " ";
		if (apr.equals("0-12"))
			sql = " and p1.apr<=12 ";
		else if (apr.equals("12-18"))
			sql = " and p1.apr>=12 and p1.apr<=18 ";
		else if (apr.equals("18")) {
			sql = " and p1.apr>=18 ";
		}
		return sql;
	}

	private String getLimitTimeSql(String time) {
		String sql = " ";
		if (time.equals("1-3"))
			sql = " and p1.time_limit<=3 ";
		else if (time.equals("3-6"))
			sql = " and p1.time_limit>=3 and p1.time_limit<=6 ";
		else if (time.equals("6-9"))
			sql = " and p1.time_limit>=6 and p1.time_limit<=9 ";
		else if (time.equals("9-12"))
			sql = " and p1.time_limit>=9 and p1.time_limit<=12 ";
		else if (time.equals("12")) {
			sql = " and p1.time_limit>=12 ";
		}
		return sql;
	}

	private String getAccountSql(String account) {
		String sql = " ";
		if (account.equals("5"))
			sql = " and p1.account<=50000 ";
		else if (account.equals("5-10"))
			sql = " and p1.account>=50000 and p1.account<=100000 ";
		else if (account.equals("10-20"))
			sql = " and p1.account>=100000 and p1.account<=200000 ";
		else if (account.equals("20-30"))
			sql = " and p1.account>=200000 and p1.account<=300000 ";
		else if (account.equals("30")) {
			sql = " and p1.account>=300000 ";
		}
		return sql;
	}

	private String getOrderSql(String order) {
		String sql = " ";
		if ((!order.equals("1")) && (!order.equals("-1"))
				&& (!order.equals("2")) && (!order.equals("-2"))
				&& (!order.equals("3")) && (!order.equals("-3"))
				&& (!order.equals("4"))) {
			order.equals("-4");
		}

		return sql;
	}
}