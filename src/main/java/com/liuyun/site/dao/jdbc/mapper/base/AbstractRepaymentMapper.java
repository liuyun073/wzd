package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Repayment;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractRepaymentMapper {
	protected void setProperty(ResultSet rs, Repayment r) throws SQLException {
		r.setId(rs.getLong("id"));
		r.setSite_id(rs.getLong("site_id"));
		r.setStatus(rs.getInt("status"));
		r.setWebstatus(rs.getInt("webstatus"));
		r.setOrder(rs.getInt("order"));
		r.setBorrow_id(rs.getInt("borrow_id"));
		r.setRepayment_time(rs.getString("repayment_time"));
		r.setRepayment_yestime(rs.getString("repayment_yestime"));
		r.setRepayment_account(rs.getString("repayment_account"));
		r.setRepayment_yesaccount(rs.getString("repayment_yesaccount"));
		r.setLate_days(rs.getInt("late_days"));
		r.setLate_interest(rs.getString("late_interest"));
		r.setInterest(rs.getString("interest"));
		r.setCapital(rs.getString("capital"));
		r.setBonus(rs.getString("bonus"));
		r.setForfeit(rs.getString("forfeit"));
		r.setReminder_fee(rs.getString("reminder_fee"));
		r.setAddtime(rs.getString("addtime"));
		r.setAddip(rs.getString("addip"));
	}
}