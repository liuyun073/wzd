package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserLogMapper;
import com.rd.model.UserLogModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserLogModelMapper extends AbstractUserLogMapper implements
		RowMapper<UserLogModel> {
	public UserLogModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserLogModel u = new UserLogModel();
		setProperty(rs, u);
		u.setUserName(rs.getString("username"));
		u.setRealName(rs.getString("realname"));
		return u;
	}
}