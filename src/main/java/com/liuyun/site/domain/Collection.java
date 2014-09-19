package com.liuyun.site.domain;

import java.io.Serializable;

public class Collection implements Serializable {
	private static final long serialVersionUID = -3844803595420384816L;
	private long id;
	private long site_id;
	private int status;
	private int order;
	private long tender_id;
	private String repay_time;
	private String repay_yestime;
	private String repay_account;
	private String repay_yesaccount;
	private String interest;
	private String capital;
	private String bonus;
	private long late_days;
	private String late_interest;
	private String addtime;
	private String addip;
	private long user_id;
	private String phone;
	private String area;

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getTender_id() {
		return this.tender_id;
	}

	public void setTender_id(long tender_id) {
		this.tender_id = tender_id;
	}

	public String getRepay_time() {
		return this.repay_time;
	}

	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}

	public String getRepay_yestime() {
		return this.repay_yestime;
	}

	public void setRepay_yestime(String repay_yestime) {
		this.repay_yestime = repay_yestime;
	}

	public String getRepay_account() {
		return this.repay_account;
	}

	public void setRepay_account(String repay_account) {
		this.repay_account = repay_account;
	}

	public String getRepay_yesaccount() {
		return this.repay_yesaccount;
	}

	public void setRepay_yesaccount(String repay_yesaccount) {
		this.repay_yesaccount = repay_yesaccount;
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

	public long getLate_days() {
		return this.late_days;
	}

	public void setLate_days(long late_days) {
		this.late_days = late_days;
	}

	public String getLate_interest() {
		return this.late_interest;
	}

	public void setLate_interest(String late_interest) {
		this.late_interest = late_interest;
	}

	public String getBonus() {
		return this.bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
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

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Collection)
				&& (getId() == ((Collection) obj).getId());
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) getId();
		return result;
	}
}