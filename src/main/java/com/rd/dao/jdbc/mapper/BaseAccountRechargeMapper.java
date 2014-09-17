package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractAccountRechargeMapper;
import com.rd.domain.AccountRecharge;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BaseAccountRechargeMapper extends AbstractAccountRechargeMapper
		implements RowMapper<AccountRecharge> {
	public AccountRecharge mapRow(ResultSet rs, int num) throws SQLException {
		AccountRecharge c = new AccountRecharge();
		setProperty(rs, c);
		return c;
	}
}