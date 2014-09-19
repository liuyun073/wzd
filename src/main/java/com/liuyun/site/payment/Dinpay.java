package com.liuyun.site.payment;

public class Dinpay {
	private String m_id;
	private String m_date;
	private String m_orderID;
	private String m_amount;
	private String m_currency;
	private String m_url;
	private String m_language;
	private String s_name = "";

	private String s_address = "";

	private String s_postcode = "";

	private String s_telephone = "";

	private String s_email = "";

	private String r_name = "";

	private String r_address = "";

	private String r_postcode = "";

	private String r_telephone = "";

	private String r_email = "";

	private String m_comment = "";
	private String state;
	private String p_Bank = "";
	private String md5Sign;
	private String orderInfo;

	public String getM_id() {
		return this.m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_date() {
		return this.m_date;
	}

	public void setM_date(String m_date) {
		this.m_date = m_date;
	}

	public String getM_orderID() {
		return this.m_orderID;
	}

	public void setM_orderID(String m_orderID) {
		this.m_orderID = m_orderID;
	}

	public String getM_amount() {
		return this.m_amount;
	}

	public void setM_amount(String m_amount) {
		this.m_amount = m_amount;
	}

	public String getM_currency() {
		return this.m_currency;
	}

	public void setM_currency(String m_currency) {
		this.m_currency = m_currency;
	}

	public String getM_url() {
		return this.m_url;
	}

	public void setM_url(String m_url) {
		this.m_url = m_url;
	}

	public String getM_language() {
		return this.m_language;
	}

	public void setM_language(String m_language) {
		this.m_language = m_language;
	}

	public String getS_name() {
		return this.s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_address() {
		return this.s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public String getS_postcode() {
		return this.s_postcode;
	}

	public void setS_postcode(String s_postcode) {
		this.s_postcode = s_postcode;
	}

	public String getS_telephone() {
		return this.s_telephone;
	}

	public void setS_telephone(String s_telephone) {
		this.s_telephone = s_telephone;
	}

	public String getS_email() {
		return this.s_email;
	}

	public void setS_email(String s_email) {
		this.s_email = s_email;
	}

	public String getR_name() {
		return this.r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_address() {
		return this.r_address;
	}

	public void setR_address(String r_address) {
		this.r_address = r_address;
	}

	public String getR_postcode() {
		return this.r_postcode;
	}

	public void setR_postcode(String r_postcode) {
		this.r_postcode = r_postcode;
	}

	public String getR_telephone() {
		return this.r_telephone;
	}

	public void setR_telephone(String r_telephone) {
		this.r_telephone = r_telephone;
	}

	public String getR_email() {
		return this.r_email;
	}

	public void setR_email(String r_email) {
		this.r_email = r_email;
	}

	public String getM_comment() {
		return this.m_comment;
	}

	public void setM_comment(String m_comment) {
		this.m_comment = m_comment;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getP_Bank() {
		return this.p_Bank;
	}

	public void setP_Bank(String p_Bank) {
		this.p_Bank = p_Bank;
	}

	public String getMd5Sign() {
		return this.md5Sign;
	}

	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}

	public String getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
}