package com.rd.dao.jdbc.mapper;

import com.rd.context.Global;
import com.rd.dao.jdbc.mapper.base.AbstractAccountRechargeMapper;
import com.rd.domain.AccountRecharge;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AccountRechargeMapper extends AbstractAccountRechargeMapper
		implements RowMapper<AccountRecharge> {
	public AccountRecharge mapRow(ResultSet rs, int num) throws SQLException {
		AccountRecharge c = new AccountRecharge();
		setProperty(rs, c);
		c.setUsername(rs.getString("username"));
		c.setRealname(rs.getString("realname"));
		c.setPaymentname(rs.getString("paymentname"));
		c.setTotal(rs.getDouble("total"));
		if (Global.getWebid().equals("jsy")) {
			c.setRecharge_kefu_username(rs.getString("recharge_kefu_username"));
		}
		return c;
	}
}