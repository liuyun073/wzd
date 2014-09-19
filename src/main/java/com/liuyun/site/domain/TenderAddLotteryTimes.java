package com.liuyun.site.domain;

public class TenderAddLotteryTimes {
	private long id;
	private long user_id;
	private long tender_id;
	private long lottery_times;
	private String addtime;

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getTender_id() {
		return this.tender_id;
	}

	public void setTender_id(long tender_id) {
		this.tender_id = tender_id;
	}

	public long getLottery_times() {
		return this.lottery_times;
	}

	public void setLottery_times(long lottery_times) {
		this.lottery_times = lottery_times;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}