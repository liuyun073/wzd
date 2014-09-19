package com.liuyun.site.domain;

import com.liuyun.site.util.StringUtils;

public class User {
	private long user_id;
	private int type_id;
	private long order;
	private String purview;
	private String username;
	private String password;
	private String paypassword;
	private int islock;
	private String invite_userid;
	private String invite_money;
	private String real_status;
	private String card_type;
	private String card_id;
	private String card_pic1;
	private String card_pic2;
	private String nation;
	private String realname;
	private String integral;
	private int status;
	private int avatar_status;
	private String email_status;
	private String phone_status;
	private int video_status;
	private int scene_status;
	private String email;
	private String sex;
	private String litpic;
	private String tel;
	private String phone;
	private String qq;
	private String wangwang;
	private String question;
	private String answer;
	private String birthday;
	private String province;
	private String city;
	private String area;
	private String address;
	private String remind;
	private String privacy;
	private long logintime;
	private String addtime;
	private String addip;
	private String uptime;
	private String upip;
	private String lasttime;
	private String lastip;
	private long is_phone;
	private long memberLevel;
	private String serial_id;
	private String serial_status;
	private String invite_username;
	private int vip_status;

	public long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(long userid) {
		this.user_id = userid;
	}

	public int getType_id() {
		return this.type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public long getOrder() {
		return this.order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getPurview() {
		return this.purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaypassword() {
		return this.paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public int getIslock() {
		return this.islock;
	}

	public void setIslock(int islock) {
		this.islock = islock;
	}

	public String getInvite_userid() {
		return this.invite_userid;
	}

	public void setInvite_userid(String invite_userid) {
		this.invite_userid = invite_userid;
	}

	public String getInvite_money() {
		return this.invite_money;
	}

	public void setInvite_money(String invite_money) {
		this.invite_money = invite_money;
	}

	public String getReal_status() {
		return this.real_status;
	}

	public void setReal_status(String real_status) {
		this.real_status = real_status;
	}

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

	public String getCard_pic1() {
		return this.card_pic1;
	}

	public void setCard_pic1(String card_pic1) {
		this.card_pic1 = card_pic1;
	}

	public String getCard_pic2() {
		return this.card_pic2;
	}

	public void setCard_pic2(String card_pic2) {
		this.card_pic2 = card_pic2;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIntegral() {
		return this.integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAvatar_status() {
		return this.avatar_status;
	}

	public void setAvatar_status(int avatar_status) {
		this.avatar_status = avatar_status;
	}

	public String getEmail_status() {
		return this.email_status;
	}

	public void setEmail_status(String email_status) {
		this.email_status = email_status;
	}

	public String getPhone_status() {
		return this.phone_status;
	}

	public void setPhone_status(String phone_status) {
		this.phone_status = phone_status;
	}

	public int getVideo_status() {
		return this.video_status;
	}

	public void setVideo_status(int video_status) {
		this.video_status = video_status;
	}

	public int getScene_status() {
		return this.scene_status;
	}

	public void setScene_status(int scene_status) {
		this.scene_status = scene_status;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLitpic() {
		return this.litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWangwang() {
		return this.wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemind() {
		return this.remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getPrivacy() {
		return this.privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public long getLogintime() {
		return this.logintime;
	}

	public void setLogintime(long logintime) {
		this.logintime = logintime;
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

	public String getUptime() {
		return this.uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getUpip() {
		return this.upip;
	}

	public void setUpip(String upip) {
		this.upip = upip;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public String getLastip() {
		return this.lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public long getIs_phone() {
		return this.is_phone;
	}

	public void setIs_phone(long is_phone) {
		this.is_phone = is_phone;
	}

	public long getMemberLevel() {
		return this.memberLevel;
	}

	public void setMemberLevel(long memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getSerial_id() {
		return this.serial_id;
	}

	public void setSerial_id(String serial_id) {
		this.serial_id = serial_id;
	}

	public String getSerial_status() {
		return this.serial_status;
	}

	public void setSerial_status(String serial_status) {
		this.serial_status = serial_status;
	}

	public String getInvite_username() {
		return this.invite_username;
	}

	public void setInvite_username(String invite_username) {
		this.invite_username = invite_username;
	}

	public int getVip_status() {
		return this.vip_status;
	}

	public void setVip_status(int vip_status) {
		this.vip_status = vip_status;
	}

	public void hideChar() {
		if (StringUtils.isNull(getReal_status()).equals("1")) {
			if (getCard_id() != null) {
				setCard_id(StringUtils.hideLastChar(getCard_id(), 4));
			}
			if (getRealname() != null) {
				int len = 2;
				if (getRealname().length() < 3)
					len = 1;
				setRealname(StringUtils.hideFirstChar(getRealname(), len));
			}
		}
		if ((StringUtils.isNull(getPhone_status()).equals("1"))
				&& (getPhone() != null)) {
			setPhone(StringUtils.hideLastChar(getPhone(), 4));
		}

		if ((!StringUtils.isNull(getEmail_status()).equals("1"))
				|| (getEmail() == null))
			return;
		String[] temp = getEmail().split("@");
		if (temp.length > 1) {
			String newEmail = StringUtils.hideChar(temp[0], temp[0].length())
					+ "@" + temp[1];
			setEmail(newEmail);
		}
	}
}