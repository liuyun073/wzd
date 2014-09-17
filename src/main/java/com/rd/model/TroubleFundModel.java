package com.rd.model;

import com.rd.domain.TroubleFundDonateRecord;
import java.io.Serializable;

public class TroubleFundModel extends TroubleFundDonateRecord implements
		Serializable {
	private static final long serialVersionUID = 2737053575621240588L;
	private String username;
	private String realname;
	private double into_funds;

	public double getInto_funds() {
		return this.into_funds;
	}

	public void setInto_funds(double into_funds) {
		this.into_funds = into_funds;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}