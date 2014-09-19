package com.liuyun.site.domain;

import java.io.Serializable;

public class Repayment implements Serializable {
	private static final long serialVersionUID = 6636946067550231040L;
	private long id;
	private long site_id;
	private int status;
	private int webstatus;
	private int order;
	private long borrow_id;
	private String repayment_time;
	private String repayment_yestime;
	private String repayment_account;
	private String repayment_yesaccount;
	private int late_days;
	private String late_interest;
	private String interest;
	private String capital;
	private String bonus;
	private String forfeit;
	private String reminder_fee;
	private String addtime;
	private String addip;
	private String borrow_style;
	private String borrow_name;
	private String time_limit;
	private String username;
	private int isday;
	private int time_limit_day;
	private String verify_time;
	private int is_fast;
	private int is_xin;
	private int is_mb;
	private int is_jin;
	private int is_flow;
	private int is_offvouch;
	private int is_pledge;
	private String realname;
	private String card_id;
	private String repayment_money;
	private String unRepayTimeOverdue;
	private String OverdueTime;
	private String account;

	public String getRepayment_money() {
		return this.repayment_money;
	}

	public void setRepayment_money(String repayment_money) {
		this.repayment_money = repayment_money;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public int getIs_offvouch() {
		return this.is_offvouch;
	}

	public void setIs_offvouch(int is_offvouch) {
		this.is_offvouch = is_offvouch;
	}

	public int getIs_pledge() {
		return this.is_pledge;
	}

	public void setIs_pledge(int is_pledge) {
		this.is_pledge = is_pledge;
	}

	public String getUnRepayTimeOverdue() {
		return this.unRepayTimeOverdue;
	}

	public void setUnRepayTimeOverdue(String unRepayTimeOverdue) {
		this.unRepayTimeOverdue = unRepayTimeOverdue;
	}

	public String getOverdueTime() {
		return this.OverdueTime;
	}

	public void setOverdueTime(String overdueTime) {
		this.OverdueTime = overdueTime;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getIs_flow() {
		return this.is_flow;
	}

	public void setIs_flow(int is_flow) {
		this.is_flow = is_flow;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSite_id() {
		return this.site_id;
	}

	public void setSite_id(long site_id) {
		this.site_id = site_id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWebstatus() {
		return this.webstatus;
	}

	public void setWebstatus(int webstatus) {
		this.webstatus = webstatus;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public String getRepayment_time() {
		return this.repayment_time;
	}

	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}

	public String getRepayment_yestime() {
		return this.repayment_yestime;
	}

	public void setRepayment_yestime(String repayment_yestime) {
		this.repayment_yestime = repayment_yestime;
	}

	public String getRepayment_account() {
		return this.repayment_account;
	}

	public void setRepayment_account(String repayment_account) {
		this.repayment_account = repayment_account;
	}

	public String getRepayment_yesaccount() {
		return this.repayment_yesaccount;
	}

	public void setRepayment_yesaccount(String repayment_yesaccount) {
		this.repayment_yesaccount = repayment_yesaccount;
	}

	public int getLate_days() {
		return this.late_days;
	}

	public void setLate_days(int late_days) {
		this.late_days = late_days;
	}

	public String getLate_interest() {
		return this.late_interest;
	}

	public void setLate_interest(String late_interest) {
		this.late_interest = late_interest;
	}

	public String getInterest() {
		return this.interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getCapital() {
		return this.capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getBonus() {
		return this.bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getForfeit() {
		return this.forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getReminder_fee() {
		return this.reminder_fee;
	}

	public void setReminder_fee(String reminder_fee) {
		this.reminder_fee = reminder_fee;
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

	public String getBorrow_name() {
		return this.borrow_name;
	}

	public void setBorrow_name(String borrow_name) {
		this.borrow_name = borrow_name;
	}

	public String getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsday() {
		return this.isday;
	}

	public void setIsday(int isday) {
		this.isday = isday;
	}

	public int getTime_limit_day() {
		return this.time_limit_day;
	}

	public void setTime_limit_day(int time_limit_day) {
		this.time_limit_day = time_limit_day;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public int getIs_fast() {
		return this.is_fast;
	}

	public void setIs_fast(int is_fast) {
		this.is_fast = is_fast;
	}

	public int getIs_xin() {
		return this.is_xin;
	}

	public void setIs_xin(int is_xin) {
		this.is_xin = is_xin;
	}

	public int getIs_mb() {
		return this.is_mb;
	}

	public void setIs_mb(int is_mb) {
		this.is_mb = is_mb;
	}

	public int getIs_jin() {
		return this.is_jin;
	}

	public void setIs_jin(int is_jin) {
		this.is_jin = is_jin;
	}

	public String getBorrow_style() {
		return this.borrow_style;
	}

	public void setBorrow_style(String borrow_style) {
		this.borrow_style = borrow_style;
	}

	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Repayment)
				&& (getId() == ((Repayment) obj).getId());
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) getId();
		return result;
	}
}