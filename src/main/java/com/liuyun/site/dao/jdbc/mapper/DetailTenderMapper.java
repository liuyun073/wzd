package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractTenderMapper;
import com.liuyun.site.model.DetailTender;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DetailTenderMapper extends AbstractTenderMapper implements
		RowMapper<DetailTender> {
	private static final long serialVersionUID = 4276450498189706042L;

	public DetailTender mapRow(ResultSet rs, int num) throws SQLException {
		DetailTender dt = new DetailTender();
		setProperty(rs, dt);
		dt.setTender_account(rs.getString("tender_account"));
		dt.setTender_money(rs.getString("tender_money"));
		dt.setBorrow_userid(rs.getLong("borrow_userid"));
		dt.setOp_username(rs.getString("op_username"));
		dt.setUsername(rs.getString("username"));
		dt.setApr(rs.getDouble("apr"));
		dt.setTime_limit(rs.getString("time_limit"));
		dt.setTime_limit_day(rs.getInt("time_limit_day"));
		dt.setBorrow_name(rs.getString("borrow_name"));
		dt.setBorrow_id(rs.getLong("borrow_id"));
		dt.setBorrow_account(rs.getString("borrow_account"));
		dt.setBorrow_account_yes(rs.getString("borrow_account_yes"));
		dt.setCredit_jifen(rs.getInt("credit_jifen"));
		dt.setCredit_pic(rs.getString("credit_pic"));
		dt.setVerify_time(rs.getString("verify_time"));
		return dt;
	}
}