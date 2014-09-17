package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserCacheMapper;
import com.rd.model.UserCacheModel;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserCacheMapper extends AbstractUserCacheMapper implements
		Serializable, RowMapper<UserCacheModel> {
	private static final long serialVersionUID = 7531936094043719154L;

	public UserCacheModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCacheModel u = new UserCacheModel();
		setProperty(rs, u);
		u.setKefu_name(rs.getString("kefu_name"));
		u.setKefu_realname(rs.getString("kefu_realname"));
		u.setCredit_pic(rs.getString("credit_pic"));
		u.setCredit_jifen(rs.getString("credit_jifen"));
		u.setUsername(rs.getString("username"));
		return u;
	}
}