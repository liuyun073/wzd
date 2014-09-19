package com.liuyun.site.model;

import com.liuyun.site.domain.Userinfo;
import java.io.Serializable;

public class AdminInfoModel extends Userinfo implements Serializable {
	private static final long serialVersionUID = 6026604036412862478L;
	private String realname;
	private String sex;
	private String username;
	private String nation;
	private String birthday;
	private String provincetext;
	private String citytext;
	private String areatext;
	private String usertype;
	private String email;
	private String card_type;
	private String card_id;

	public String getCard_type() {
		return this.card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProvincetext() {
		return this.provincetext;
	}

	public void setProvincetext(String provincetext) {
		this.provincetext = provincetext;
	}

	public String getCitytext() {
		return this.citytext;
	}

	public void setCitytext(String citytext) {
		this.citytext = citytext;
	}

	public String getAreatext() {
		return this.areatext;
	}

	public void setAreatext(String areatext) {
		this.areatext = areatext;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return 6026604036412862478L;
	}
}