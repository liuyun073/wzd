package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.model.borrow.FastExpireModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FastExpireMapper implements RowMapper<FastExpireModel> {
	public FastExpireModel mapRow(ResultSet rs, int num) throws SQLException {
		FastExpireModel fe = new FastExpireModel();
		fe.setBorrow_id(rs.getLong("borrow_id"));
		fe.setBorrow_name(rs.getString("borrow_name"));
		fe.setBorrow_user(rs.getString("borrow_user"));
		fe.setRepayment_time(rs.getString("repayment_time"));
		fe.setRepayment_account(rs.getString("repayment_account"));
		fe.setLate_days(rs.getInt("late_days"));
		fe.setForfeit(rs.getString("forfeit"));
		return fe;
	}
}