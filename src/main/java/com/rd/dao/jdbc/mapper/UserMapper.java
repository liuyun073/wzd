package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserMapper;
import com.rd.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper extends AbstractUserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User();
		setProperty(rs, u);
		String invite_username = "";
		try {
			invite_username = rs.getString("invite_username");
		} catch (Exception localException) {
		}
		u.setInvite_username(invite_username);
		return u;
	}
}