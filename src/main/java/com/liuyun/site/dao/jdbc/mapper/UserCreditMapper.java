package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.UserCredit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserCreditMapper implements RowMapper<UserCredit> {
	public UserCredit mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCredit userCredit = new UserCredit();
		userCredit.setUser_id(rs.getLong("user_id"));
		userCredit.setValue(rs.getInt("value"));
		userCredit.setOp_user(rs.getInt("op_user"));
		userCredit.setAddtime(rs.getLong("addtime"));
		userCredit.setAddip(rs.getString("addip"));
		userCredit.setUpdateip(rs.getString("updateip"));
		userCredit.setUpdatetime(rs.getString("updatetime"));
		userCredit.setTender_value(rs.getInt("tender_value"));
		userCredit.setBorrow_value(rs.getInt("borrow_value"));
		userCredit.setGift_value(rs.getInt("gift_value"));
		userCredit.setExpense_value(rs.getInt("expense_value"));
		userCredit.setValid_value(rs.getInt("valid_value"));
		return userCredit;
	}
}