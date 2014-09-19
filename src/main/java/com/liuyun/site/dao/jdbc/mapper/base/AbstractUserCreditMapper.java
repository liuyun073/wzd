package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.UserCredit;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractUserCreditMapper {
	protected void setProperty(ResultSet rs, UserCredit u) throws SQLException {
		u.setUser_id(rs.getLong("user_id"));
		u.setAddip(rs.getString("addip"));
		u.setAddtime(rs.getLong("addtime"));
		u.setOp_user(rs.getInt("op_user"));
		u.setValue(rs.getInt("value"));
		u.setUpdatetime(rs.getString("updatetime"));
		u.setUpdateip(rs.getString("updateip"));
		u.setTender_value(rs.getInt("tender_value"));
		u.setBorrow_value(rs.getInt("borrow_value"));
		u.setGift_value(rs.getInt("gift_value"));
		u.setExpense_value(rs.getInt("expense_value"));
		u.setValid_value(rs.getInt("valid_value"));
	}
}