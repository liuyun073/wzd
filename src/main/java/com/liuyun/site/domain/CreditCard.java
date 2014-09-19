package com.liuyun.site.domain;

import java.io.Serializable;

public class CreditCard implements Serializable {
	private static final long serialVersionUID = -6784728727307847252L;
	private int card_id;
	private String name;
	private String issuing_bank;
	private String issuing_nstitution;
	private String issuing_status;
	private String check_style;
	private String interest;
	private String tel;
	private String currency;
	private String grade;
	private String borrowing_limit;
	private String interest_free;
	private String integral_policy;
	private String integral_indate;
	private String credit_rules;
	private String scoring_rules;
	private String installment;
	private String amount;
	private String fee_payment;
	private String applicable_fee;
	private String prepayment;
	private String open_card;
	private String repea_card;
	private String app_condition;
	private String app_way;
	private String submit_info;
	private String supplement_num;
	private String supplement_app;
	private String report_loss;
	private String loss_protection;
	private String loss_tel;
	private String lowest_refund;
	private String allopatry_back_fee;
	private String rmb_repayment;
	private String foreign_repayment;
	private String special_repayment;
	private String card_features;
	private String add_service;
	private String joint_discount;
	private String main_card_fee;
	private String supplement_card_fee;
	private String year_cut_rules;
	private String fee_date;
	private String local_fee;
	private String local_interbank_fee;
	private String offsite_fee;
	private String offsite_interbank_fee;
	private String overseas_pay_fee;
	private String overseas_unpay_fee;
	private String overseas_meet_fee;
	private String enchashment_limit;
	private String localback_fee;
	private String local_interbank_back_fee;
	private String offsite_overflow_back_fee;
	private String offsite_interbank_back_fee;
	private String overseas_pay_back_fee;
	private String overseas_unpay_back_fee;
	private String overflow_back_rules;
	private String message_money;
	private String overseas_fee;
	private String change_card;
	private String ahead_change_card;
	private String express_fee;
	private String statement_fee;
	private String statement_free_clause;
	private String loss_fee;
	private String reset_password_fee;
	private String selfdom_card_fee;
	private String foreign_convert_fee;
	private String slip_fee;
	private String slip_fee_copy;
	private String slip_fee_foreign;
	private String slip_fee_copy_foreign;
	private String overdue_fine;
	private String transfinite_fee;
	private int type_value;
	private String litpic;

	public String toString() {
		return "CreditCard [card_id=" + this.card_id + ", name=" + this.name
				+ ", issuing_bank=" + this.issuing_bank
				+ ", issuing_nstitution=" + this.issuing_nstitution
				+ ", issuing_status=" + this.issuing_status + ", check_style="
				+ this.check_style + ", interest=" + this.interest + ", tel="
				+ this.tel + ", currency=" + this.currency + ", grade="
				+ this.grade + ", borrowing_limit=" + this.borrowing_limit
				+ ", interest_free=" + this.interest_free
				+ ", integral_policy=" + this.integral_policy
				+ ", integral_indate=" + this.integral_indate
				+ ", credit_rules=" + this.credit_rules + ", scoring_rules="
				+ this.scoring_rules + ", installment=" + this.installment
				+ ", amount=" + this.amount + ", fee_payment="
				+ this.fee_payment + ", applicable_fee=" + this.applicable_fee
				+ ", prepayment=" + this.prepayment + ", open_card="
				+ this.open_card + ", repea_card=" + this.repea_card
				+ ", app_condition=" + this.app_condition + ", app_way="
				+ this.app_way + ", submit_info=" + this.submit_info
				+ ", supplement_num=" + this.supplement_num
				+ ", supplement_app=" + this.supplement_app + ", report_loss="
				+ this.report_loss + ", loss_protection="
				+ this.loss_protection + ", loss_tel=" + this.loss_tel
				+ ", lowest_refund=" + this.lowest_refund
				+ ", allopatry_back_fee=" + this.allopatry_back_fee
				+ ", rmb_repayment=" + this.rmb_repayment
				+ ", foreign_repayment=" + this.foreign_repayment
				+ ", special_repayment=" + this.special_repayment
				+ ", card_features=" + this.card_features + ", add_service="
				+ this.add_service + ", joint_discount=" + this.joint_discount
				+ ", main_card_fee=" + this.main_card_fee
				+ ", supplement_card_fee=" + this.supplement_card_fee
				+ ", year_cut_rules=" + this.year_cut_rules + ", fee_date="
				+ this.fee_date + ", local_fee=" + this.local_fee
				+ ", local_interbank_fee=" + this.local_interbank_fee
				+ ", offsite_fee=" + this.offsite_fee
				+ ", offsite_interbank_fee=" + this.offsite_interbank_fee
				+ ", overseas_pay_fee=" + this.overseas_pay_fee
				+ ", overseas_unpay_fee=" + this.overseas_unpay_fee
				+ ", overseas_meet_fee=" + this.overseas_meet_fee
				+ ", enchashment_limit=" + this.enchashment_limit
				+ ", localback_fee=" + this.localback_fee
				+ ", local_interbank_back_fee=" + this.local_interbank_back_fee
				+ ", offsite_overflow_back_fee="
				+ this.offsite_overflow_back_fee
				+ ", offsite_interbank_back_fee="
				+ this.offsite_interbank_back_fee + ", overseas_pay_back_fee="
				+ this.overseas_pay_back_fee + ", overseas_unpay_back_fee="
				+ this.overseas_unpay_back_fee + ", overflow_back_rules="
				+ this.overflow_back_rules + ", message_money="
				+ this.message_money + ", overseas_fee=" + this.overseas_fee
				+ ", change_card=" + this.change_card + ", ahead_change_card="
				+ this.ahead_change_card + ", express_fee=" + this.express_fee
				+ ", statement_fee=" + this.statement_fee
				+ ", statement_free_clause=" + this.statement_free_clause
				+ ", loss_fee=" + this.loss_fee + ", reset_password_fee="
				+ this.reset_password_fee + ", selfdom_card_fee="
				+ this.selfdom_card_fee + ", foreign_convert_fee="
				+ this.foreign_convert_fee + ", slip_fee=" + this.slip_fee
				+ ", slip_fee_copy=" + this.slip_fee_copy
				+ ", slip_fee_foreign=" + this.slip_fee_foreign
				+ ", slip_fee_copy_foreign=" + this.slip_fee_copy_foreign
				+ ", overdue_fine=" + this.overdue_fine + ", transfinite_fee="
				+ this.transfinite_fee + ", type_value=" + this.type_value
				+ ", litpic=" + this.litpic + "]";
	}

	public int getCard_id() {
		return this.card_id;
	}

	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssuing_bank() {
		return this.issuing_bank;
	}

	public void setIssuing_bank(String issuing_bank) {
		this.issuing_bank = issuing_bank;
	}

	public String getIssuing_nstitution() {
		return this.issuing_nstitution;
	}

	public void setIssuing_nstitution(String issuing_nstitution) {
		this.issuing_nstitution = issuing_nstitution;
	}

	public String getIssuing_status() {
		return this.issuing_status;
	}

	public void setIssuing_status(String issuing_status) {
		this.issuing_status = issuing_status;
	}

	public String getCheck_style() {
		return this.check_style;
	}

	public void setCheck_style(String check_style) {
		this.check_style = check_style;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getBorrowing_limit() {
		return this.borrowing_limit;
	}

	public void setBorrowing_limit(String borrowing_limit) {
		this.borrowing_limit = borrowing_limit;
	}

	public String getInterest_free() {
		return this.interest_free;
	}

	public void setInterest_free(String interest_free) {
		this.interest_free = interest_free;
	}

	public String getIntegral_policy() {
		return this.integral_policy;
	}

	public void setIntegral_policy(String integral_policy) {
		this.integral_policy = integral_policy;
	}

	public String getIntegral_indate() {
		return this.integral_indate;
	}

	public void setIntegral_indate(String integral_indate) {
		this.integral_indate = integral_indate;
	}

	public String getCredit_rules() {
		return this.credit_rules;
	}

	public void setCredit_rules(String credit_rules) {
		this.credit_rules = credit_rules;
	}

	public String getScoring_rules() {
		return this.scoring_rules;
	}

	public void setScoring_rules(String scoring_rules) {
		this.scoring_rules = scoring_rules;
	}

	public String getInstallment() {
		return this.installment;
	}

	public void setInstallment(String installment) {
		this.installment = installment;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee_payment() {
		return this.fee_payment;
	}

	public void setFee_payment(String fee_payment) {
		this.fee_payment = fee_payment;
	}

	public String getApplicable_fee() {
		return this.applicable_fee;
	}

	public void setApplicable_fee(String applicable_fee) {
		this.applicable_fee = applicable_fee;
	}

	public String getPrepayment() {
		return this.prepayment;
	}

	public void setPrepayment(String prepayment) {
		this.prepayment = prepayment;
	}

	public String getOpen_card() {
		return this.open_card;
	}

	public void setOpen_card(String open_card) {
		this.open_card = open_card;
	}

	public String getRepea_card() {
		return this.repea_card;
	}

	public void setRepea_card(String repea_card) {
		this.repea_card = repea_card;
	}

	public String getApp_condition() {
		return this.app_condition;
	}

	public void setApp_condition(String app_condition) {
		this.app_condition = app_condition;
	}

	public String getApp_way() {
		return this.app_way;
	}

	public void setApp_way(String app_way) {
		this.app_way = app_way;
	}

	public String getSubmit_info() {
		return this.submit_info;
	}

	public void setSubmit_info(String submit_info) {
		this.submit_info = submit_info;
	}

	public String getSupplement_num() {
		return this.supplement_num;
	}

	public void setSupplement_num(String supplement_num) {
		this.supplement_num = supplement_num;
	}

	public String getSupplement_app() {
		return this.supplement_app;
	}

	public void setSupplement_app(String supplement_app) {
		this.supplement_app = supplement_app;
	}

	public String getReport_loss() {
		return this.report_loss;
	}

	public void setReport_loss(String report_loss) {
		this.report_loss = report_loss;
	}

	public String getLoss_protection() {
		return this.loss_protection;
	}

	public void setLoss_protection(String loss_protection) {
		this.loss_protection = loss_protection;
	}

	public String getLoss_tel() {
		return this.loss_tel;
	}

	public void setLoss_tel(String loss_tel) {
		this.loss_tel = loss_tel;
	}

	public String getLowest_refund() {
		return this.lowest_refund;
	}

	public void setLowest_refund(String lowest_refund) {
		this.lowest_refund = lowest_refund;
	}

	public String getAllopatry_back_fee() {
		return this.allopatry_back_fee;
	}

	public void setAllopatry_back_fee(String allopatry_back_fee) {
		this.allopatry_back_fee = allopatry_back_fee;
	}

	public String getRmb_repayment() {
		return this.rmb_repayment;
	}

	public void setRmb_repayment(String rmb_repayment) {
		this.rmb_repayment = rmb_repayment;
	}

	public String getForeign_repayment() {
		return this.foreign_repayment;
	}

	public void setForeign_repayment(String foreign_repayment) {
		this.foreign_repayment = foreign_repayment;
	}

	public String getSpecial_repayment() {
		return this.special_repayment;
	}

	public void setSpecial_repayment(String special_repayment) {
		this.special_repayment = special_repayment;
	}

	public String getCard_features() {
		return this.card_features;
	}

	public void setCard_features(String card_features) {
		this.card_features = card_features;
	}

	public String getAdd_service() {
		return this.add_service;
	}

	public void setAdd_service(String add_service) {
		this.add_service = add_service;
	}

	public String getJoint_discount() {
		return this.joint_discount;
	}

	public void setJoint_discount(String joint_discount) {
		this.joint_discount = joint_discount;
	}

	public String getMain_card_fee() {
		return this.main_card_fee;
	}

	public void setMain_card_fee(String main_card_fee) {
		this.main_card_fee = main_card_fee;
	}

	public String getSupplement_card_fee() {
		return this.supplement_card_fee;
	}

	public void setSupplement_card_fee(String supplement_card_fee) {
		this.supplement_card_fee = supplement_card_fee;
	}

	public String getYear_cut_rules() {
		return this.year_cut_rules;
	}

	public void setYear_cut_rules(String year_cut_rules) {
		this.year_cut_rules = year_cut_rules;
	}

	public String getFee_date() {
		return this.fee_date;
	}

	public void setFee_date(String fee_date) {
		this.fee_date = fee_date;
	}

	public String getLocal_fee() {
		return this.local_fee;
	}

	public void setLocal_fee(String local_fee) {
		this.local_fee = local_fee;
	}

	public String getLocal_interbank_fee() {
		return this.local_interbank_fee;
	}

	public void setLocal_interbank_fee(String local_interbank_fee) {
		this.local_interbank_fee = local_interbank_fee;
	}

	public String getOffsite_fee() {
		return this.offsite_fee;
	}

	public void setOffsite_fee(String offsite_fee) {
		this.offsite_fee = offsite_fee;
	}

	public String getOffsite_interbank_fee() {
		return this.offsite_interbank_fee;
	}

	public void setOffsite_interbank_fee(String offsite_interbank_fee) {
		this.offsite_interbank_fee = offsite_interbank_fee;
	}

	public String getOverseas_pay_fee() {
		return this.overseas_pay_fee;
	}

	public void setOverseas_pay_fee(String overseas_pay_fee) {
		this.overseas_pay_fee = overseas_pay_fee;
	}

	public String getOverseas_unpay_fee() {
		return this.overseas_unpay_fee;
	}

	public void setOverseas_unpay_fee(String overseas_unpay_fee) {
		this.overseas_unpay_fee = overseas_unpay_fee;
	}

	public String getOverseas_meet_fee() {
		return this.overseas_meet_fee;
	}

	public void setOverseas_meet_fee(String overseas_meet_fee) {
		this.overseas_meet_fee = overseas_meet_fee;
	}

	public String getEnchashment_limit() {
		return this.enchashment_limit;
	}

	public void setEnchashment_limit(String enchashment_limit) {
		this.enchashment_limit = enchashment_limit;
	}

	public String getLocalback_fee() {
		return this.localback_fee;
	}

	public void setLocalback_fee(String localback_fee) {
		this.localback_fee = localback_fee;
	}

	public String getLocal_interbank_back_fee() {
		return this.local_interbank_back_fee;
	}

	public void setLocal_interbank_back_fee(String local_interbank_back_fee) {
		this.local_interbank_back_fee = local_interbank_back_fee;
	}

	public String getOffsite_overflow_back_fee() {
		return this.offsite_overflow_back_fee;
	}

	public void setOffsite_overflow_back_fee(String offsite_overflow_back_fee) {
		this.offsite_overflow_back_fee = offsite_overflow_back_fee;
	}

	public String getOffsite_interbank_back_fee() {
		return this.offsite_interbank_back_fee;
	}

	public void setOffsite_interbank_back_fee(String offsite_interbank_back_fee) {
		this.offsite_interbank_back_fee = offsite_interbank_back_fee;
	}

	public String getOverseas_pay_back_fee() {
		return this.overseas_pay_back_fee;
	}

	public void setOverseas_pay_back_fee(String overseas_pay_back_fee) {
		this.overseas_pay_back_fee = overseas_pay_back_fee;
	}

	public String getOverseas_unpay_back_fee() {
		return this.overseas_unpay_back_fee;
	}

	public void setOverseas_unpay_back_fee(String overseas_unpay_back_fee) {
		this.overseas_unpay_back_fee = overseas_unpay_back_fee;
	}

	public String getOverflow_back_rules() {
		return this.overflow_back_rules;
	}

	public void setOverflow_back_rules(String overflow_back_rules) {
		this.overflow_back_rules = overflow_back_rules;
	}

	public String getMessage_money() {
		return this.message_money;
	}

	public void setMessage_money(String message_money) {
		this.message_money = message_money;
	}

	public String getOverseas_fee() {
		return this.overseas_fee;
	}

	public void setOverseas_fee(String overseas_fee) {
		this.overseas_fee = overseas_fee;
	}

	public String getChange_card() {
		return this.change_card;
	}

	public void setChange_card(String change_card) {
		this.change_card = change_card;
	}

	public String getAhead_change_card() {
		return this.ahead_change_card;
	}

	public void setAhead_change_card(String ahead_change_card) {
		this.ahead_change_card = ahead_change_card;
	}

	public String getExpress_fee() {
		return this.express_fee;
	}

	public void setExpress_fee(String express_fee) {
		this.express_fee = express_fee;
	}

	public String getStatement_fee() {
		return this.statement_fee;
	}

	public void setStatement_fee(String statement_fee) {
		this.statement_fee = statement_fee;
	}

	public String getStatement_free_clause() {
		return this.statement_free_clause;
	}

	public void setStatement_free_clause(String statement_free_clause) {
		this.statement_free_clause = statement_free_clause;
	}

	public String getLoss_fee() {
		return this.loss_fee;
	}

	public void setLoss_fee(String loss_fee) {
		this.loss_fee = loss_fee;
	}

	public String getReset_password_fee() {
		return this.reset_password_fee;
	}

	public void setReset_password_fee(String reset_password_fee) {
		this.reset_password_fee = reset_password_fee;
	}

	public String getSelfdom_card_fee() {
		return this.selfdom_card_fee;
	}

	public void setSelfdom_card_fee(String selfdom_card_fee) {
		this.selfdom_card_fee = selfdom_card_fee;
	}

	public String getForeign_convert_fee() {
		return this.foreign_convert_fee;
	}

	public void setForeign_convert_fee(String foreign_convert_fee) {
		this.foreign_convert_fee = foreign_convert_fee;
	}

	public String getSlip_fee() {
		return this.slip_fee;
	}

	public void setSlip_fee(String slip_fee) {
		this.slip_fee = slip_fee;
	}

	public String getSlip_fee_copy() {
		return this.slip_fee_copy;
	}

	public void setSlip_fee_copy(String slip_fee_copy) {
		this.slip_fee_copy = slip_fee_copy;
	}

	public String getSlip_fee_foreign() {
		return this.slip_fee_foreign;
	}

	public void setSlip_fee_foreign(String slip_fee_foreign) {
		this.slip_fee_foreign = slip_fee_foreign;
	}

	public String getSlip_fee_copy_foreign() {
		return this.slip_fee_copy_foreign;
	}

	public void setSlip_fee_copy_foreign(String slip_fee_copy_foreign) {
		this.slip_fee_copy_foreign = slip_fee_copy_foreign;
	}

	public String getOverdue_fine() {
		return this.overdue_fine;
	}

	public void setOverdue_fine(String overdue_fine) {
		this.overdue_fine = overdue_fine;
	}

	public String getTransfinite_fee() {
		return this.transfinite_fee;
	}

	public void setTransfinite_fee(String transfinite_fee) {
		this.transfinite_fee = transfinite_fee;
	}

	public int getType_value() {
		return this.type_value;
	}

	public void setType_value(int type_value) {
		this.type_value = type_value;
	}

	public String getLitpic() {
		return this.litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}
}