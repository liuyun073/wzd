package com.rd.dao.jdbc.mapper;

import com.rd.model.invest.InvestBorrowModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class InvestBorrowMapper implements RowMapper<InvestBorrowModel> {
	
	public InvestBorrowModel mapRow(ResultSet rs, int num) throws SQLException {
		InvestBorrowModel model = new InvestBorrowModel();
		model.setRepayment_yesaccount(rs.getString("repayment_yesaccount"));
		model.setRepayment_account(rs.getString("repayment_account"));
		model.setTender_time(rs.getString("tender_time"));
		model.setAnum(rs.getString("anum"));
		model.setInter(rs.getString("inter"));
		model.setBorrow_name(rs.getString("borrow_name"));
		model.setAccount(rs.getString("account"));
		model.setTime_limit(rs.getString("time_limit"));
		model.setIsday(rs.getInt("isday"));
		model.setTime_limit_day(rs.getInt("time_limit_day"));
		model.setApr(rs.getDouble("apr"));
		model.setUsername(rs.getString("username"));
		model.setCredit(rs.getInt("credit"));
		model.setId(rs.getLong("id"));
		model.setUser_id(rs.getLong("user_id"));
		return model;
	}
}