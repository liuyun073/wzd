package com.liuyun.site.model.account;

import com.liuyun.site.domain.Account;

public class AccountReconciliationModel extends Account {
	private static final long serialVersionUID = 4038060732774453500L;
	private String username;
	private double recharge_money;
	private double log_recharge_money;
	private double up_recharge_money;
	private double down_recharge_money;
	private double houtai_recharge_money;
	private double allcollection;
	private double cash_money;
	private double invest_award;
	private double invest_yeswait_interest;
	private double wait_interest;
	private double borrow_award;
	private double borrow_fee;
	private double manage_fee;
	private double system_fee;
	private double invite_money;
	private double vip_money;
	private double repayment_interest;
	private double flow_repayment_interest;
	private double repayment_principal;
	private double yes_repayment_interest;
	private double flow_yes_repayment_interest;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getFlow_repayment_interest() {
		return this.flow_repayment_interest;
	}

	public void setFlow_repayment_interest(double flow_repayment_interest) {
		this.flow_repayment_interest = flow_repayment_interest;
	}

	public double getFlow_yes_repayment_interest() {
		return this.flow_yes_repayment_interest;
	}

	public void setFlow_yes_repayment_interest(
			double flow_yes_repayment_interest) {
		this.flow_yes_repayment_interest = flow_yes_repayment_interest;
	}

	public double getYes_repayment_interest() {
		return this.yes_repayment_interest;
	}

	public void setYes_repayment_interest(double yes_repayment_interest) {
		this.yes_repayment_interest = yes_repayment_interest;
	}

	public double getRepayment_interest() {
		return this.repayment_interest;
	}

	public void setRepayment_interest(double repayment_interest) {
		this.repayment_interest = repayment_interest;
	}

	public double getRepayment_principal() {
		return this.repayment_principal;
	}

	public void setRepayment_principal(double repayment_principal) {
		this.repayment_principal = repayment_principal;
	}

	public double getRecharge_money() {
		return this.recharge_money;
	}

	public void setRecharge_money(double recharge_money) {
		this.recharge_money = recharge_money;
	}

	public double getLog_recharge_money() {
		return this.log_recharge_money;
	}

	public void setLog_recharge_money(double log_recharge_money) {
		this.log_recharge_money = log_recharge_money;
	}

	public double getUp_recharge_money() {
		return this.up_recharge_money;
	}

	public void setUp_recharge_money(double up_recharge_money) {
		this.up_recharge_money = up_recharge_money;
	}

	public double getDown_recharge_money() {
		return this.down_recharge_money;
	}

	public void setDown_recharge_money(double down_recharge_money) {
		this.down_recharge_money = down_recharge_money;
	}

	public double getHoutai_recharge_money() {
		return this.houtai_recharge_money;
	}

	public void setHoutai_recharge_money(double houtai_recharge_money) {
		this.houtai_recharge_money = houtai_recharge_money;
	}

	public double getAllcollection() {
		return this.allcollection;
	}

	public void setAllcollection(double allcollection) {
		this.allcollection = allcollection;
	}

	public double getCash_money() {
		return this.cash_money;
	}

	public void setCash_money(double cash_money) {
		this.cash_money = cash_money;
	}

	public double getInvest_award() {
		return this.invest_award;
	}

	public void setInvest_award(double invest_award) {
		this.invest_award = invest_award;
	}

	public double getInvest_yeswait_interest() {
		return this.invest_yeswait_interest;
	}

	public void setInvest_yeswait_interest(double invest_yeswait_interest) {
		this.invest_yeswait_interest = invest_yeswait_interest;
	}

	public double getWait_interest() {
		return this.wait_interest;
	}

	public void setWait_interest(double wait_interest) {
		this.wait_interest = wait_interest;
	}

	public double getBorrow_award() {
		return this.borrow_award;
	}

	public void setBorrow_award(double borrow_award) {
		this.borrow_award = borrow_award;
	}

	public double getBorrow_fee() {
		return this.borrow_fee;
	}

	public void setBorrow_fee(double borrow_fee) {
		this.borrow_fee = borrow_fee;
	}

	public double getManage_fee() {
		return this.manage_fee;
	}

	public void setManage_fee(double manage_fee) {
		this.manage_fee = manage_fee;
	}

	public double getSystem_fee() {
		return this.system_fee;
	}

	public void setSystem_fee(double system_fee) {
		this.system_fee = system_fee;
	}

	public double getInvite_money() {
		return this.invite_money;
	}

	public void setInvite_money(double invite_money) {
		this.invite_money = invite_money;
	}

	public double getVip_money() {
		return this.vip_money;
	}

	public void setVip_money(double vip_money) {
		this.vip_money = vip_money;
	}
}