package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractAdminInfoMapper;
import com.rd.model.AdminInfoModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AdminInfoModelMapper extends AbstractAdminInfoMapper implements
		RowMapper<AdminInfoModel> {
	public AdminInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminInfoModel admin = new AdminInfoModel();
		setProperty(rs, admin);
		admin.setRealname(rs.getString("realname"));
		admin.setUsername(rs.getString("username"));
		admin.setProvincetext(rs.getString("provincetext"));
		admin.setCitytext(rs.getString("citytext"));
		admin.setAreatext(rs.getString("areatext"));
		admin.setUsertype(rs.getString("usertype"));
		admin.setSex(rs.getString("sex"));
		admin.setBirthday(rs.getString("birthday"));
		admin.setCard_id(rs.getString("card_id"));
		admin.setCard_type(rs.getString("card_type"));
		admin.setEmail(rs.getString("email"));
		return admin;
	}
}