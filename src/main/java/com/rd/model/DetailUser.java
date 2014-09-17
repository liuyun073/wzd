package com.rd.model;

import com.rd.domain.User;
import java.io.Serializable;

public class DetailUser extends User implements Serializable {
	private static final long serialVersionUID = 5543205481644066198L;
	private int credit_jifen;
	private double use_money;
	private String credit_pic;
	private int vip_status;
	private long vip_verify_time;
	private long kefu_addtime;
	private String provincetext;
	private String citytext;
	private String areatext;
	private String typename;
	private String kefu_username;

	public String getKefu_username() {
		return this.kefu_username;
	}

	public void setKefu_username(String kefu_username) {
		this.kefu_username = kefu_username;
	}

	public double getUse_money() {
		return this.use_money;
	}

	public void setUse_money(double use_money) {
		this.use_money = use_money;
	}

	public int getCredit_jifen() {
		return this.credit_jifen;
	}

	public void setCredit_jifen(int credit_jifen) {
		this.credit_jifen = credit_jifen;
	}

	public String getCredit_pic() {
		return this.credit_pic;
	}

	public void setCredit_pic(String credit_pic) {
		this.credit_pic = credit_pic;
	}

	public int getVip_status() {
		return this.vip_status;
	}

	public void setVip_status(int vip_status) {
		this.vip_status = vip_status;
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

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public long getVip_verify_time() {
		return this.vip_verify_time;
	}

	public void setVip_verify_time(long vip_verify_time) {
		this.vip_verify_time = vip_verify_time;
	}

	public long getKefu_addtime() {
		return this.kefu_addtime;
	}

	public void setKefu_addtime(long kefu_addtime) {
		this.kefu_addtime = kefu_addtime;
	}

	public String toString() {
		return "DetailUser [credit_jifen=" + this.credit_jifen
				+ ", credit_pic=" + this.credit_pic + ", vip_status="
				+ this.vip_status + ", vip_verify_time=" + this.vip_verify_time
				+ ", kefu_addtime=" + this.kefu_addtime + ", provincetext="
				+ this.provincetext + ", citytext=" + this.citytext
				+ ", areatext=" + this.areatext + ", typename=" + this.typename
				+ ", getUser_id()=" + getUser_id() + ", getType_id()="
				+ getType_id() + ", getOrder()=" + getOrder()
				+ ", getPurview()=" + getPurview() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword()
				+ ", getPaypassword()=" + getPaypassword() + ", getIslock()="
				+ getIslock() + ", getInvite_userid()=" + getInvite_userid()
				+ ", getInvite_money()=" + getInvite_money()
				+ ", getReal_status()=" + getReal_status()
				+ ", getCard_type()=" + getCard_type() + ", getCard_id()="
				+ getCard_id() + ", getCard_pic1()=" + getCard_pic1()
				+ ", getCard_pic2()=" + getCard_pic2() + ", getNation()="
				+ getNation() + ", getRealname()=" + getRealname()
				+ ", getIntegral()=" + getIntegral() + ", getStatus()="
				+ getStatus() + ", getAvatar_status()=" + getAvatar_status()
				+ ", getEmail_status()=" + getEmail_status()
				+ ", getPhone_status()=" + getPhone_status()
				+ ", getVideo_status()=" + getVideo_status()
				+ ", getScene_status()=" + getScene_status() + ", getEmail()="
				+ getEmail() + ", getSex()=" + getSex() + ", getLitpic()="
				+ getLitpic() + ", getTel()=" + getTel() + ", getPhone()="
				+ getPhone() + ", getQq()=" + getQq() + ", getWangwang()="
				+ getWangwang() + ", getQuestion()=" + getQuestion()
				+ ", getAnswer()=" + getAnswer() + ", getBirthday()="
				+ getBirthday() + ", getProvince()=" + getProvince()
				+ ", getCity()=" + getCity() + ", getArea()=" + getArea()
				+ ", getAddress()=" + getAddress() + ", getRemind()="
				+ getRemind() + ", getPrivacy()=" + getPrivacy()
				+ ", getLogintime()=" + getLogintime() + ", getAddtime()="
				+ getAddtime() + ", getAddip()=" + getAddip()
				+ ", getUptime()=" + getUptime() + ", getUpip()=" + getUpip()
				+ ", getLasttime()=" + getLasttime() + ", getLastip()="
				+ getLastip() + ", getIs_phone()=" + getIs_phone()
				+ ", getMemberLevel()=" + getMemberLevel()
				+ ", getSerial_id()=" + getSerial_id()
				+ ", getSerial_status()=" + getSerial_status()
				+ ", getInvite_username()=" + getInvite_username()
				+ ", getClass()=" + super.getClass() + ", hashCode()="
				+ super.hashCode() + ", toString()=" + super.toString() + "]";
	}
}