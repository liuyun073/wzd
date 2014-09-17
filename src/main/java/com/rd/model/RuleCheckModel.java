package com.rd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RuleCheckModel implements Serializable {
	private static final long serialVersionUID = 528325888832622757L;
	private List<Integer> borrow_type = new ArrayList<Integer>();
	private String borrow_category;
	private String receive_way;
	private double receive_account;
	private String receive_rate;
	private String back_type;
	private double tender_check_money;

	public List<Integer> getBorrow_type() {
		return this.borrow_type;
	}

	public void setBorrow_type(List<Integer> borrow_type) {
		this.borrow_type = borrow_type;
	}

	public String getBorrow_category() {
		return this.borrow_category;
	}

	public void setBorrow_category(String borrow_category) {
		this.borrow_category = borrow_category;
	}

	public String getReceive_way() {
		return this.receive_way;
	}

	public void setReceive_way(String receive_way) {
		this.receive_way = receive_way;
	}

	public double getReceive_account() {
		return this.receive_account;
	}

	public void setReceive_account(double receive_account) {
		this.receive_account = receive_account;
	}

	public String getReceive_rate() {
		return this.receive_rate;
	}

	public void setReceive_rate(String receive_rate) {
		this.receive_rate = receive_rate;
	}

	public String getBack_type() {
		return this.back_type;
	}

	public void setBack_type(String back_type) {
		this.back_type = back_type;
	}

	public double getTender_check_money() {
		return this.tender_check_money;
	}

	public void setTender_check_money(double tender_check_money) {
		this.tender_check_money = tender_check_money;
	}
}