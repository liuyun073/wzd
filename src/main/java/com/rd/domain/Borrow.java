package com.rd.domain;

import java.io.Serializable;

public class Borrow implements Serializable {
	private static final long serialVersionUID = 7029710509016395903L;
	private long id;
	private long site_id;
	private long user_id;
	private String name;
	private int status;
	private int order;
	private int hits;
	private String litpic;
	private String flag;
	private int is_vouch;
	private String type;
	private int view_type;
	private String vouch_award;
	private String vouch_user;
	private String vouch_account;
	private int vouch_times;
	private String source;
	private String publish;
	private String customer;
	private String number_id;
	private String verify_user;
	private String verify_time;
	private String verify_remark;
	private int repayment_user;
	private String forst_account;
	private String repayment_account;
	private String monthly_repayment;
	private String repayment_yesaccount;
	private int repayment_yesinterest;
	private String repayment_time;
	private String repayment_remark;
	private String success_time;
	private String end_time;
	private String payment_account;
	private String each_time;
	private String use;
	private String time_limit;
	private String style;
	private String account;
	private String account_yes;
	private String tender_times;
	private double apr;
	private String lowest_account;
	private String most_account;
	private String valid_time;
	private int award;
	private double part_account;
	private double funds;
	private String is_false;
	private String open_account;
	private String open_borrow;
	private String open_tender;
	private String open_credit;
	private String content;
	private String addtime;
	private String addip;
	private int is_mb;
	private int is_fast;
	private int is_pledge;
	private int is_jin;
	private int is_xin;
	private String pwd;
	private int isday;
	private int time_limit_day;
	private int is_art;
	private int is_charity;
	private int is_project;
	private int is_recommend;
	private int is_flow;
	private int flow_count;
	private int flow_yescount;
	private int flow_money;
	private int flow_status;
	private int is_student;
	private int is_offvouch;
	private String borrow_time;
	private String borrow_account;
	private String borrow_time_limit;
	private String collection_limit;
	private int is_donation;
	private String search;
	private double late_award;

	public String toString() {
		return "Borrow [id=" + this.id + ", site_id=" + this.site_id
				+ ", user_id=" + this.user_id + ", name=" + this.name
				+ ", status=" + this.status + ", order=" + this.order
				+ ", hits=" + this.hits + ", litpic=" + this.litpic + ", flag="
				+ this.flag + ", is_vouch=" + this.is_vouch + ", type="
				+ this.type + ", view_type=" + this.view_type
				+ ", vouch_award=" + this.vouch_award + ", vouch_user="
				+ this.vouch_user + ", vouch_account=" + this.vouch_account
				+ ", vouch_times=" + this.vouch_times + ", source="
				+ this.source + ", publish=" + this.publish + ", customer="
				+ this.customer + ", number_id=" + this.number_id
				+ ", verify_user=" + this.verify_user + ", verify_time="
				+ this.verify_time + ", verify_remark=" + this.verify_remark
				+ ", repayment_user=" + this.repayment_user
				+ ", forst_account=" + this.forst_account
				+ ", repayment_account=" + this.repayment_account
				+ ", monthly_repayment=" + this.monthly_repayment
				+ ", repayment_yesaccount=" + this.repayment_yesaccount
				+ ", repayment_yesinterest=" + this.repayment_yesinterest
				+ ", repayment_time=" + this.repayment_time
				+ ", repayment_remark=" + this.repayment_remark
				+ ", success_time=" + this.success_time + ", end_time="
				+ this.end_time + ", payment_account=" + this.payment_account
				+ ", each_time=" + this.each_time + ", use=" + this.use
				+ ", time_limit=" + this.time_limit + ", style=" + this.style
				+ ", account=" + this.account + ", account_yes="
				+ this.account_yes + ", tender_times=" + this.tender_times
				+ ", apr=" + this.apr + ", lowest_account="
				+ this.lowest_account + ", most_account=" + this.most_account
				+ ", valid_time=" + this.valid_time + ", award=" + this.award
				+ ", part_account=" + this.part_account + ", funds="
				+ this.funds + ", is_false=" + this.is_false
				+ ", open_account=" + this.open_account + ", open_borrow="
				+ this.open_borrow + ", open_tender=" + this.open_tender
				+ ", open_credit=" + this.open_credit + ", content="
				+ this.content + ", addtime=" + this.addtime + ", addip="
				+ this.addip + ", is_mb=" + this.is_mb + ", is_fast="
				+ this.is_fast + ", is_pledge=" + this.is_pledge + ", is_jin="
				+ this.is_jin + ", is_xin=" + this.is_xin + ", pwd=" + this.pwd
				+ ", isday=" + this.isday + ", time_limit_day="
				+ this.time_limit_day + ", is_art=" + this.is_art
				+ ", is_charity=" + this.is_charity + ", is_project="
				+ this.is_project + ", is_flow=" + this.is_flow
				+ ", flow_count=" + this.flow_count + ", flow_yescount="
				+ this.flow_yescount + ", flow_money=" + this.flow_money
				+ ", flow_status=" + this.flow_status + ", is_student="
				+ this.is_student + ", is_offvouch=" + this.is_offvouch
				+ ", borrow_time=" + this.borrow_time + ", borrow_account="
				+ this.borrow_account + ", borrow_time_limit="
				+ this.borrow_time_limit + ", collection_limit="
				+ this.collection_limit + ", is_donation=" + this.is_donation
				+ ", search=" + this.search + ", getIs_pledge()="
				+ getIs_pledge() + ", getSearch()=" + getSearch()
				+ ", getBorrow_time()=" + getBorrow_time()
				+ ", getBorrow_account()=" + getBorrow_account()
				+ ", getBorrow_time_limit()=" + getBorrow_time_limit()
				+ ", getId()=" + getId() + ", getSite_id()=" + getSite_id()
				+ ", getUser_id()=" + getUser_id() + ", getName()=" + getName()
				+ ", getStatus()=" + getStatus() + ", getOrder()=" + getOrder()
				+ ", getHits()=" + getHits() + ", getLitpic()=" + getLitpic()
				+ ", getFlag()=" + getFlag() + ", getIs_vouch()="
				+ getIs_vouch() + ", getType()=" + getType()
				+ ", getView_type()=" + getView_type() + ", getVouch_award()="
				+ getVouch_award() + ", getVouch_user()=" + getVouch_user()
				+ ", getVouch_account()=" + getVouch_account()
				+ ", getVouch_times()=" + getVouch_times() + ", getSource()="
				+ getSource() + ", getPublish()=" + getPublish()
				+ ", getCustomer()=" + getCustomer() + ", getNumber_id()="
				+ getNumber_id() + ", getVerify_user()=" + getVerify_user()
				+ ", getVerify_time()=" + getVerify_time()
				+ ", getVerify_remark()=" + getVerify_remark()
				+ ", getRepayment_user()=" + getRepayment_user()
				+ ", getForst_account()=" + getForst_account()
				+ ", getRepayment_account()=" + getRepayment_account()
				+ ", getMonthly_repayment()=" + getMonthly_repayment()
				+ ", getRepayment_yesaccount()=" + getRepayment_yesaccount()
				+ ", getRepayment_yesinterest()=" + getRepayment_yesinterest()
				+ ", getRepayment_time()=" + getRepayment_time()
				+ ", getRepayment_remark()=" + getRepayment_remark()
				+ ", getSuccess_time()=" + getSuccess_time()
				+ ", getEnd_time()=" + getEnd_time()
				+ ", getPayment_account()=" + getPayment_account()
				+ ", getEach_time()=" + getEach_time() + ", getUse()="
				+ getUse() + ", getTime_limit()=" + getTime_limit()
				+ ", getStyle()=" + getStyle() + ", getAccount()="
				+ getAccount() + ", getAccount_yes()=" + getAccount_yes()
				+ ", getTender_times()=" + getTender_times() + ", getApr()="
				+ getApr() + ", getLowest_account()=" + getLowest_account()
				+ ", getMost_account()=" + getMost_account()
				+ ", getValid_time()=" + getValid_time() + ", getAward()="
				+ getAward() + ", getPart_account()=" + getPart_account()
				+ ", getFunds()=" + getFunds() + ", getIs_false()="
				+ getIs_false() + ", getOpen_account()=" + getOpen_account()
				+ ", getOpen_borrow()=" + getOpen_borrow()
				+ ", getOpen_tender()=" + getOpen_tender()
				+ ", getOpen_credit()=" + getOpen_credit() + ", getContent()="
				+ getContent() + ", getAddtime()=" + getAddtime()
				+ ", getAddip()=" + getAddip() + ", getIs_mb()=" + getIs_mb()
				+ ", getIs_fast()=" + getIs_fast() + ", getIs_jin()="
				+ getIs_jin() + ", getPwd()=" + getPwd() + ", getIsday()="
				+ getIsday() + ", getTime_limit_day()=" + getTime_limit_day()
				+ ", getIs_xin()=" + getIs_xin() + ", getIs_art()="
				+ getIs_art() + ", getIs_charity()=" + getIs_charity()
				+ ", getIs_project()=" + getIs_project() + ", getIs_flow()="
				+ getIs_flow() + ", getFlow_count()=" + getFlow_count()
				+ ", getFlow_yescount()=" + getFlow_yescount()
				+ ", getFlow_money()=" + getFlow_money()
				+ ", getFlow_status()=" + getFlow_status()
				+ ", getIs_student()=" + getIs_student()
				+ ", getIs_offvouch()=" + getIs_offvouch()
				+ ", getCollection_limit()=" + getCollection_limit()
				+ ", getIs_donation()=" + getIs_donation() + ", hashCode()="
				+ hashCode() + ", getClass()=" + super.getClass()
				+ ", toString()=" + super.toString() + "]";
	}

	public int getIs_pledge() {
		return this.is_pledge;
	}

	public void setIs_pledge(int is_pledge) {
		this.is_pledge = is_pledge;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getBorrow_time() {
		return this.borrow_time;
	}

	public String getBorrow_account() {
		return this.borrow_account;
	}

	public void setBorrow_account(String borrow_account) {
		this.borrow_account = borrow_account;
	}

	public String getBorrow_time_limit() {
		return this.borrow_time_limit;
	}

	public void setBorrow_time_limit(String borrow_time_limit) {
		this.borrow_time_limit = borrow_time_limit;
	}

	public void setBorrow_time(String borrow_time) {
		this.borrow_time = borrow_time;
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

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getHits() {
		return this.hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getLitpic() {
		return this.litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getIs_vouch() {
		return this.is_vouch;
	}

	public void setIs_vouch(int is_vouch) {
		this.is_vouch = is_vouch;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getView_type() {
		return this.view_type;
	}

	public void setView_type(int view_type) {
		this.view_type = view_type;
	}

	public String getVouch_award() {
		return this.vouch_award;
	}

	public void setVouch_award(String vouch_award) {
		this.vouch_award = vouch_award;
	}

	public String getVouch_user() {
		return this.vouch_user;
	}

	public void setVouch_user(String vouch_user) {
		this.vouch_user = vouch_user;
	}

	public String getVouch_account() {
		return this.vouch_account;
	}

	public void setVouch_account(String vouch_account) {
		this.vouch_account = vouch_account;
	}

	public int getVouch_times() {
		return this.vouch_times;
	}

	public void setVouch_times(int vouch_times) {
		this.vouch_times = vouch_times;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPublish() {
		return this.publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getNumber_id() {
		return this.number_id;
	}

	public void setNumber_id(String number_id) {
		this.number_id = number_id;
	}

	public String getVerify_user() {
		return this.verify_user;
	}

	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}

	public String getVerify_time() {
		return this.verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public String getVerify_remark() {
		return this.verify_remark;
	}

	public void setVerify_remark(String verify_remark) {
		this.verify_remark = verify_remark;
	}

	public int getRepayment_user() {
		return this.repayment_user;
	}

	public void setRepayment_user(int repayment_user) {
		this.repayment_user = repayment_user;
	}

	public String getForst_account() {
		return this.forst_account;
	}

	public void setForst_account(String forst_account) {
		this.forst_account = forst_account;
	}

	public String getRepayment_account() {
		return this.repayment_account;
	}

	public void setRepayment_account(String repayment_account) {
		this.repayment_account = repayment_account;
	}

	public String getMonthly_repayment() {
		return this.monthly_repayment;
	}

	public void setMonthly_repayment(String monthly_repayment) {
		this.monthly_repayment = monthly_repayment;
	}

	public String getRepayment_yesaccount() {
		return this.repayment_yesaccount;
	}

	public void setRepayment_yesaccount(String repayment_yesaccount) {
		this.repayment_yesaccount = repayment_yesaccount;
	}

	public int getRepayment_yesinterest() {
		return this.repayment_yesinterest;
	}

	public void setRepayment_yesinterest(int repayment_yesinterest) {
		this.repayment_yesinterest = repayment_yesinterest;
	}

	public String getRepayment_time() {
		return this.repayment_time;
	}

	public void setRepayment_time(String repayment_time) {
		this.repayment_time = repayment_time;
	}

	public String getRepayment_remark() {
		return this.repayment_remark;
	}

	public void setRepayment_remark(String repayment_remark) {
		this.repayment_remark = repayment_remark;
	}

	public String getSuccess_time() {
		return this.success_time;
	}

	public void setSuccess_time(String success_time) {
		this.success_time = success_time;
	}

	public String getEnd_time() {
		return this.end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getPayment_account() {
		return this.payment_account;
	}

	public void setPayment_account(String payment_account) {
		this.payment_account = payment_account;
	}

	public String getEach_time() {
		return this.each_time;
	}

	public void setEach_time(String each_time) {
		this.each_time = each_time;
	}

	public String getUse() {
		return this.use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getTime_limit() {
		return this.time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount_yes() {
		return this.account_yes;
	}

	public void setAccount_yes(String account_yes) {
		this.account_yes = account_yes;
	}

	public String getTender_times() {
		return this.tender_times;
	}

	public void setTender_times(String tender_times) {
		this.tender_times = tender_times;
	}

	public double getApr() {
		return this.apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public String getLowest_account() {
		return this.lowest_account;
	}

	public void setLowest_account(String lowest_account) {
		this.lowest_account = lowest_account;
	}

	public String getMost_account() {
		return this.most_account;
	}

	public void setMost_account(String most_account) {
		this.most_account = most_account;
	}

	public String getValid_time() {
		return this.valid_time;
	}

	public void setValid_time(String valid_time) {
		this.valid_time = valid_time;
	}

	public int getAward() {
		return this.award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public double getPart_account() {
		return this.part_account;
	}

	public void setPart_account(double part_account) {
		this.part_account = part_account;
	}

	public double getFunds() {
		return this.funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public String getIs_false() {
		return this.is_false;
	}

	public void setIs_false(String is_false) {
		this.is_false = is_false;
	}

	public String getOpen_account() {
		return this.open_account;
	}

	public void setOpen_account(String open_account) {
		this.open_account = open_account;
	}

	public String getOpen_borrow() {
		return this.open_borrow;
	}

	public void setOpen_borrow(String open_borrow) {
		this.open_borrow = open_borrow;
	}

	public String getOpen_tender() {
		return this.open_tender;
	}

	public void setOpen_tender(String open_tender) {
		this.open_tender = open_tender;
	}

	public String getOpen_credit() {
		return this.open_credit;
	}

	public void setOpen_credit(String open_credit) {
		this.open_credit = open_credit;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getIs_mb() {
		return this.is_mb;
	}

	public void setIs_mb(int is_mb) {
		this.is_mb = is_mb;
	}

	public int getIs_fast() {
		return this.is_fast;
	}

	public void setIs_fast(int is_fast) {
		this.is_fast = is_fast;
	}

	public int getIs_jin() {
		return this.is_jin;
	}

	public void setIs_jin(int is_jin) {
		this.is_jin = is_jin;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public int getIs_xin() {
		return this.is_xin;
	}

	public void setIs_xin(int is_xin) {
		this.is_xin = is_xin;
	}

	public int getIs_art() {
		return this.is_art;
	}

	public void setIs_art(int is_art) {
		this.is_art = is_art;
	}

	public int getIs_charity() {
		return this.is_charity;
	}

	public void setIs_charity(int is_charity) {
		this.is_charity = is_charity;
	}

	public int getIs_project() {
		return this.is_project;
	}

	public void setIs_project(int is_project) {
		this.is_project = is_project;
	}

	public int getIs_recommend() {
		return this.is_recommend;
	}

	public void setIs_recommend(int is_recommend) {
		this.is_recommend = is_recommend;
	}

	public int getIs_flow() {
		return this.is_flow;
	}

	public void setIs_flow(int is_flow) {
		this.is_flow = is_flow;
	}

	public int getFlow_count() {
		return this.flow_count;
	}

	public void setFlow_count(int flow_count) {
		this.flow_count = flow_count;
	}

	public int getFlow_yescount() {
		return this.flow_yescount;
	}

	public void setFlow_yescount(int flow_yescount) {
		this.flow_yescount = flow_yescount;
	}

	public int getFlow_money() {
		return this.flow_money;
	}

	public void setFlow_money(int flow_money) {
		this.flow_money = flow_money;
	}

	public int getFlow_status() {
		return this.flow_status;
	}

	public void setFlow_status(int flow_status) {
		this.flow_status = flow_status;
	}

	public int getIs_student() {
		return this.is_student;
	}

	public void setIs_student(int is_student) {
		this.is_student = is_student;
	}

	public int getIs_offvouch() {
		return this.is_offvouch;
	}

	public void setIs_offvouch(int is_offvouch) {
		this.is_offvouch = is_offvouch;
	}

	public String getCollection_limit() {
		return this.collection_limit;
	}

	public void setCollection_limit(String collection_limit) {
		this.collection_limit = collection_limit;
	}

	public int getIs_donation() {
		return this.is_donation;
	}

	public void setIs_donation(int is_donation) {
		this.is_donation = is_donation;
	}

	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof Borrow)
				&& (getId() == ((Borrow) obj).getId());
	}

	public int hashCode() {
		int result = 17;
		result = 31 * result + (int) getId();
		return result;
	}

	public double getLate_award() {
		return this.late_award;
	}

	public void setLate_award(double late_award) {
		this.late_award = late_award;
	}
}