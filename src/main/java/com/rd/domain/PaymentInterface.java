package com.rd.domain;

public class PaymentInterface {
	private long id;
	private String name;
	private String merchant_id;
	private String key;
	private String recharge_fee;
	private String return_url;
	private String notice_url;
	private long is_enable;
	private String chartset;
	private String interface_Into_account;
	private String interface_value;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchant_id() {
		return this.merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRecharge_fee() {
		return this.recharge_fee;
	}

	public void setRecharge_fee(String recharge_fee) {
		this.recharge_fee = recharge_fee;
	}

	public String getReturn_url() {
		return this.return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getNotice_url() {
		return this.notice_url;
	}

	public void setNotice_url(String notice_url) {
		this.notice_url = notice_url;
	}

	public long getIs_enable() {
		return this.is_enable;
	}

	public void setIs_enable(long is_enable) {
		this.is_enable = is_enable;
	}

	public String getChartset() {
		return this.chartset;
	}

	public void setChartset(String chartset) {
		this.chartset = chartset;
	}

	public String getInterface_Into_account() {
		return this.interface_Into_account;
	}

	public void setInterface_Into_account(String interface_Into_account) {
		this.interface_Into_account = interface_Into_account;
	}

	public String getInterface_value() {
		return this.interface_value;
	}

	public void setInterface_value(String interface_value) {
		this.interface_value = interface_value;
	}
}