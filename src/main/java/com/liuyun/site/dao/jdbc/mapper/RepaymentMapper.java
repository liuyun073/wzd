package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractRepaymentMapper;
import com.liuyun.site.domain.Repayment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RepaymentMapper extends AbstractRepaymentMapper implements
		RowMapper<Repayment> {
	public Repayment mapRow(ResultSet rs, int num) throws SQLException {
		Repayment r = new Repayment();
		setProperty(rs, r);
		return r;
	}
}