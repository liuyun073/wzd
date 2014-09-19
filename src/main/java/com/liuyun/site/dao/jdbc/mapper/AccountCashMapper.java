package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractAccountCashMapper;
import com.liuyun.site.domain.AccountCash;
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