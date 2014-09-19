package com.liuyun.site.model;

import com.liuyun.site.domain.Userinfo;
import java.io.File;
import java.io.Serializable;

public class UserinfoModel extends Userinfo implements Serializable {
	private static final long serialVersionUID = 6026604036412862478L;
	private String type;
	private String realname;
	private String sex;
	private String username;
	private String nation;
	private String birthday;
	private String card_type;
	private String card_id;
	private File card_pic1;
	private File card_pic2;
	private String card_pic1FileName;
	private String card_pic2FileName;
	private String card_pic1_path;
	private String card_pic2_path;
	private String provincetext;
	private String citytext;
	private String areatext;
	private String usertype;
	private String email;
	private String vip_status;
	private String email_status;
	private String real_status;
	private String video_status;
	private String phone_status;
	private String scene_status;
	private String vip_remark;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCard_type() {
		return this.card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public File getCard_pic1() {
		return this.card_pic1;
	}

	public void setCard_pic1(File card_pic1) {
		this.card_pic1 = card_pic1;
	}

	public File getCard_pic2() {
		return this.card_pic2;
	}

	public void setCard_pic2(File card_pic2) {
		this.card_pic2 = card_pic2;
	}

	public String getCard_pic1_path() {
		return this.card_pic1_path;
	}

	public void setCard_pic1_path(String card_pic1_path) {
		this.card_pic1_path = card_pic1_path;
	}

	public String getCard_pic2_path() {
		return this.card_pic2_path;
	}

	public void setCard_pic2_path(String card_pic2_path) {
		this.card_pic2_path = card_pic2_path;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getCard_pic1FileName() {
		return this.card_pic1FileName;
	}

	public void setCard_pic1FileName(String card_pic1FileName) {
		this.card_pic1FileName = card_pic1FileName;
	}

	public String getCard_pic2FileName() {
		return this.card_pic2FileName;
	}

	public void setCard_pic2FileName(String card_pic2FileName) {
		this.card_pic2FileName = card_pic2FileName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getVip_remark() {
		return this.vip_remark;
	}

	public void setVip_remark(String vip_remark) {
		this.vip_remark = vip_remark;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVip_status() {
		return this.vip_status;
	}

	public void setVip_status(String vip_status) {
		this.vip_status = vip_status;
	}

	public String getEmail_status() {
		return this.email_status;
	}

	public void setEmail_status(String email_status) {
		this.email_status = email_status;
	}

	public String getReal_status() {
		return this.real_status;
	}

	public void setReal_status(String real_status) {
		this.real_status = real_status;
	}

	public String getVideo_status() {
		return this.video_status;
	}

	public void setVideo_status(String video_status) {
		this.video_status = video_status;
	}

	public String getPhone_status() {
		return this.phone_status;
	}

	public void setPhone_status(String phone_status) {
		this.phone_status = phone_status;
	}

	public String getScene_status() {
		return this.scene_status;
	}

	public void setScene_status(String scene_status) {
		this.scene_status = scene_status;
	}
}