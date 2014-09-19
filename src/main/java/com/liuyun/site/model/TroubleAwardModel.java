package com.liuyun.site.model;

import com.liuyun.site.domain.TroubleAwardRecord;
import java.io.Serializable;

public class TroubleAwardModel extends TroubleAwardRecord implements
		Serializable {
	private static final long serialVersionUID = 2737053575621240588L;
	private String username;
	private String realname;
	private String award_money;
	private String into_funds;

	public String getAward_money() {
		return this.award_money;
	}

	public void setAward_money(String award_money) {
		this.award_money = award_money;
	}

	public String getInto_funds() {
		return this.into_funds;
	}

	public void setInto_funds(String into_funds) {
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