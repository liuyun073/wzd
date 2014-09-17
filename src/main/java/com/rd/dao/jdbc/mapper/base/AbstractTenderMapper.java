package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.Tender;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractTenderMapper {
	protected void setProperty(ResultSet rs, Tender t) throws SQLException {
		t.setId(rs.getInt("id"));
		t.setSite_id(rs.getInt("site_id"));
		t.setUser_id(rs.getInt("user_id"));
		t.setStatus(rs.getInt("status"));
		t.setBorrow_id(rs.getInt("borrow_id"));
		t.setMoney(rs.getString("money"));
		t.setAccount(rs.getString("account"));
		t.setRepayment_account(rs.getString("repayment_account"));
		t.setInterest(rs.getString("interest"));
		t.setRepayment_yesaccount(rs.getString("repayment_yesaccount"));
		t.setWait_account(rs.getString("wait_account"));
		t.setWait_interest(rs.getString("wait_interest"));
		t.setRepayment_yesinterest(rs.getString("repayment_yesinterest"));
		t.setAddtime(rs.getString("addtime"));
		t.setAddip(rs.getString("addip"));
		t.setAuto_repurchase(rs.getInt("auto_repurchase"));
	}
}