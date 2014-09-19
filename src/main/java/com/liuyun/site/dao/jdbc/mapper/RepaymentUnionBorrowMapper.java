package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractRepaymentMapper;
import com.liuyun.site.domain.Repayment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RepaymentUnionBorrowMapper extends AbstractRepaymentMapper
		implements RowMapper<Repayment> {
	public Repayment mapRow(ResultSet rs, int num) throws SQLException {
		Repayment r = new Repayment();
		setProperty(rs, r);
		r.setBorrow_name(rs.getString("borrow_name"));
		r.setTime_limit(rs.getString("time_limit"));
		r.setUsername(rs.getString("username"));
		r.setTime_limit_day(rs.getInt("time_limit_day"));
		r.setIsday(rs.getInt("isday"));
		r.setVerify_time(rs.getString("verify_time"));
		r.setBorrow_style(rs.getString("borrow_style"));
		try {
			r.setIs_mb(rs.getInt("is_mb"));
			r.setIs_fast(rs.getInt("is_fast"));
			r.setIs_jin(rs.getInt("is_jin"));
			r.setIs_xin(rs.getInt("is_xin"));
			r.setIs_flow(rs.getInt("is_flow"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}