package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractAccountLogMapper;
import com.liuyun.site.model.account.AccountLogModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountLogMapper extends AbstractAccountLogMapper implements
		RowMapper<AccountLogModel> {
	public AccountLogModel mapRow(ResultSet rs, int num) throws SQLException {
		AccountLogModel log = new AccountLogModel();
		setProperty(rs, log);
		log.setUsername(rs.getString("username"));
		log.setTo_username(rs.getString("to_username"));
		log.setTypename(rs.getString("typename"));
		return log;
	}
}