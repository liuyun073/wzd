package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractCreditLogMapper;
import com.rd.model.UserCreditLogModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CreditLogMapper extends AbstractCreditLogMapper implements
		RowMapper<UserCreditLogModel> {
	public UserCreditLogModel mapRow(ResultSet rs, int num) throws SQLException {
		UserCreditLogModel ucLog = new UserCreditLogModel();
		ucLog.setRealname(rs.getString("realname"));
		ucLog.setUsername(rs.getString("username"));
		ucLog.setTypeName(rs.getString("typeName"));
		setProperty(rs, ucLog);
		return ucLog;
	}
}