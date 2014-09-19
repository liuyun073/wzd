package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractUserMapper {
	protected void setProperty(ResultSet rs, User u) throws SQLException {
		u.setUser_id(rs.getLong("user_id"));
		u.setType_id(rs.getInt("type_id"));
		u.setOrder(rs.getInt("order"));
		u.setPurview(rs.getString("purview"));
		u.setUsername(rs.getString("username"));
		u.setPassword(rs.getString("password"));
		u.setPaypassword(rs.getString("paypassword"));
		u.setIslock(rs.getInt("islock"));
		u.setInvite_userid(rs.getString("invite_userid"));
		u.setInvite_money(rs.getString("invite_money"));
		u.setReal_status(rs.getString("real_status"));
		u.setCard_type(rs.getString("card_type"));
		u.setCard_id(rs.getString("card_id"));
		u.setCard_pic1(rs.getString("card_pic1"));
		u.setCard_pic2(rs.getString("card_pic2"));
		u.setNation(rs.getString("nation"));
		u.setRealname(rs.getString("realname"));
		u.setIntegral(rs.getString("integral"));
		u.setStatus(rs.getInt("status"));
		u.setAvatar_status(rs.getInt("avatar_status"));
		u.setEmail_status(rs.getString("email_status"));
		u.setPhone_status(rs.getString("phone_status"));
		u.setVideo_status(rs.getInt("video_status"));
		u.setScene_status(rs.getInt("scene_status"));
		u.setEmail(rs.getString("email"));
		u.setSex(rs.getString("sex"));
		u.setLitpic(rs.getString("litpic"));
		u.setTel(rs.getString("tel"));
		u.setPhone(rs.getString("phone"));
		u.setQq(rs.getString("qq"));
		u.setWangwang(rs.getString("wangwang"));
		u.setQuestion(rs.getString("question"));
		u.setAnswer(rs.getString("answer"));
		u.setBirthday(rs.getString("birthday"));
		u.setProvince(rs.getString("province"));
		u.setCity(rs.getString("city"));
		u.setArea(rs.getString("area"));
		u.setAddress(rs.getString("address"));
		u.setRemind(rs.getString("remind"));
		u.setPrivacy(rs.getString("privacy"));
		u.setLogintime(rs.getInt("logintime"));
		u.setAddtime(rs.getString("addtime"));
		u.setAddip(rs.getString("addip"));
		u.setUptime(rs.getString("uptime"));
		u.setUpip(rs.getString("upip"));
		u.setLasttime(rs.getString("lasttime"));
		u.setLastip(rs.getString("lastip"));
		u.setIs_phone(rs.getInt("is_phone"));
		u.setMemberLevel(rs.getInt("memberLevel"));
		u.setSerial_id(rs.getString("serial_id"));
		u.setSerial_status(rs.getString("serial_status"));
	}
}