package com.liuyun.site.domain;

import java.io.Serializable;

public class RewardStatistics implements Serializable {
	private static final long serialVersionUID = 544257563371787345L;
	private long id;
	private Byte type;
	private long reward_user_id;
	private long passive_user_id;
	private String receive_time;
	private String receive_yestime;
	private double receive_account;
	private double receive_yesaccount;
	private Byte receive_status;
	private String addtime;
	private String endtime;
	private long rule_id;
	private Byte back_type;
	private long type_fk_id;

	public RewardStatistics() {
	}

	public RewardStatistics(long rule_id) {
		this.rule_id = rule_id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public long getReward_user_id() {
		return this.reward_user_id;
	}

	public void setReward_user_id(long reward_user_id) {
		this.reward_user_id = reward_user_id;
	}

	public long getPassive_user_id() {
		return this.passive_user_id;
	}

	public void setPassive_user_id(long passive_user_id) {
		this.passive_user_id = passive_user_id;
	}

	public String getReceive_time() {
		return this.receive_time;
	}

	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}

	public String getReceive_yestime() {
		return this.receive_yestime;
	}

	public void setReceive_yestime(String receive_yestime) {
		this.receive_yestime = receive_yestime;
	}

	public double getReceive_account() {
		return this.receive_account;
	}

	public void setReceive_account(double receive_account) {
		this.receive_account = receive_account;
	}

	public double getReceive_yesaccount() {
		return this.receive_yesaccount;
	}

	public void setReceive_yesaccount(double receive_yesaccount) {
		this.receive_yesaccount = receive_yesaccount;
	}

	public Byte getReceive_status() {
		return this.receive_status;
	}

	public void setReceive_status(Byte receive_status) {
		this.receive_status = receive_status;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public long getRule_id() {
		return this.rule_id;
	}

	public void setRule_id(long rule_id) {
		this.rule_id = rule_id;
	}

	public Byte getBack_type() {
		return this.back_type;
	}

	public void setBack_type(Byte back_type) {
		this.back_type = back_type;
	}

	public long getType_fk_id() {
		return this.type_fk_id;
	}

	public void setType_fk_id(long type_fk_id) {
		this.type_fk_id = type_fk_id;
	}
}