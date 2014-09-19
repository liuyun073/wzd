package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.UserAmount;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserAmountMapper implements RowMapper<UserAmount> {
	public UserAmount mapRow(ResultSet rs, int num) throws SQLException {
		UserAmount amount = new UserAmount();
		amount.setId(rs.getLong("id"));
		amount.setUser_id(rs.getLong("user_id"));
		amount.setCredit(rs.getDouble("credit"));
		amount.setCredit_nouse(rs.getDouble("credit_nouse"));
		amount.setCredit_use(rs.getDouble("credit_use"));

		amount.setBorrow_vouch(rs.getDouble("borrow_vouch"));
		amount.setBorrow_vouch(rs.getDouble("borrow_vouch_use"));
		amount.setBorrow_vouch(rs.getDouble("borrow_vouch_nouse"));

		amount.setBorrow_vouch(rs.getDouble("tender_vouch"));
		amount.setBorrow_vouch_use(rs.getDouble("tender_vouch_use"));
		amount.setBorrow_vouch_nouse(rs.getDouble("tender_vouch_nouse"));

		amount.setUsername(rs.getString("username"));

		return amount;
	}
}