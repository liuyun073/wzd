package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserinfoMapper;
import com.rd.model.UserinfoModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserInfoModelMapper extends AbstractUserinfoMapper implements
		RowMapper<UserinfoModel> {
	public UserinfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserinfoModel u = new UserinfoModel();
		setProperty(rs, u);
		u.setRealname(rs.getString("realname"));
		u.setUsername(rs.getString("username"));
		u.setUser_id(rs.getLong("user_id"));

		return u;
	}
}