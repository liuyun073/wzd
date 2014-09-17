package com.rd.model;

import com.rd.domain.QuickenLoans;

public class QuickenLoansModel extends QuickenLoans {
	private static final long serialVersionUID = 6096539246733265840L;

	public String toString() {
		return "QuickenLoansModel [getName=" + getName() + ", getPhone()="
				+ getPhone() + ", getArea()=" + getArea() + ", getRemark()="
				+ getRemark() + ", getCreateTime()=" + getCreateTime()
				+ ", toString()=" + super.toString() + "]";
	}
}