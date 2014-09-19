package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractUserinfoMapper;
import com.liuyun.site.model.UserinfoModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserAllInfoModelMapper extends AbstractUserinfoMapper implements
		RowMapper<UserinfoModel> {
	public UserinfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserinfoModel u = new UserinfoModel();
		setProperty(rs, u);
		u.setRealname(rs.getString("realname"));
		u.setUsername(rs.getString("username"));
		u.setProvincetext(rs.getString("provincetext"));
		u.setCitytext(rs.getString("citytext"));
		u.setAreatext(rs.getString("areatext"));
		u.setUsertype(rs.getString("usertype"));
		u.setSex(rs.getString("sex"));
		u.setBirthday(rs.getString("birthday"));
		u.setCard_id(rs.getString("card_id"));
		u.setCard_pic1_path(rs.getString("card_pic1"));
		u.setCard_pic2_path(rs.getString("card_pic2"));
		u.setEmail(rs.getString("email"));
		u.setVip_status(rs.getString("vip_status"));
		u.setReal_status(rs.getString("real_status"));
		u.setVideo_status(rs.getString("video_status"));
		u.setPhone_status(rs.getString("phone_status"));
		u.setScene_status(rs.getString("scene_status"));
		u.setEmail_status(rs.getString("email_status"));
		u.setVip_remark(rs.getString("vip_remark"));
		u.setCard_type(rs.getString("card_type"));
		u.setNation(rs.getString("nation"));

		return u;
	}
}