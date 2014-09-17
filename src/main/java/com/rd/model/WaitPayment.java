package com.rd.model;

import java.io.Serializable;

public class WaitPayment implements Serializable {
	private static final long serialVersionUID = -7993686327275841354L;
	private int status;
	private double repay_num;
	private double borrow_num;
	private double capital_num;
	private double borrow_yesnum;

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getRepay_num() {
		return this.repay_num;
	}

	public void setRepay_num(double repay_num) {
		this.repay_num = repay_num;
	}

	public double getBorrow_num() {
		return this.borrow_num;
	}

	public void setBorrow_num(double borrow_num) {
		this.borrow_num = borrow_num;
	}

	public double getCapital_num() {
		return this.capital_num;
	}

	public void setCapital_num(double capital_num) {
		this.capital_num = capital_num;
	}

	public double getBorrow_yesnum() {
		return this.borrow_yesnum;
	}

	public void setBorrow_yesnum(double borrow_yesnum) {
		this.borrow_yesnum = borrow_yesnum;
	}
}