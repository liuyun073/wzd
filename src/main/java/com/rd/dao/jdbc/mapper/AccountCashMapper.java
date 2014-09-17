package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractAccountCashMapper;
import com.rd.domain.AccountCash;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountCashMapper extends AbstractAccountCashMapper implements
		RowMapper<AccountCash> {
	public AccountCash mapRow(ResultSet rs, int num) throws SQLException {
		AccountCash c = new AccountCash();
		setProperty(rs, c);
		return c;
	}
}