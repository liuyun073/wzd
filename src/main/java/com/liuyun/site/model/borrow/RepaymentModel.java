package com.liuyun.site.model.borrow;

import com.liuyun.site.domain.Repayment;

public class RepaymentModel extends Repayment {
	private static final long serialVersionUID = 1L;
	
	private String repay_time;
	private String repay_account;
	private String repay_yestime;
	private long flow_borrow_id;

	public long getFlow_borrow_id() {
		return this.flow_borrow_id;
	}

	public void setFlow_borrow_id(long flow_borrow_id) {
		this.flow_borrow_id = flow_borrow_id;
	}

	public String getRepay_time() {
		return this.repay_time;
	}

	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}

	public String getRepay_account() {
		return this.repay_account;
	}

	public void setRepay_account(String repay_account) {
		this.repay_account = repay_account;
	}

	public String getRepay_yestime() {
		return this.repay_yestime;
	}

	public void setRepay_yestime(String repay_yestime) {
		this.repay_yestime = repay_yestime;
	}
}