package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserCreditMapper;
import com.rd.model.UserCreditModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserCreditModelMapper extends AbstractUserCreditMapper implements
		RowMapper<UserCreditModel> {
	public UserCreditModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCreditModel u = new UserCreditModel();
		super.setProperty(rs, u);
		u.setUserName(rs.getString("userName"));
		u.setRealname(rs.getString("realname"));
		u.setCredit_pic(rs.getString("credit_pic"));

		return u;
	}
}