package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractQuickenLoansMapper;
import com.liuyun.site.domain.QuickenLoans;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class QuickenLoansMapper extends AbstractQuickenLoansMapper implements
		RowMapper<QuickenLoans> {
	public QuickenLoans mapRow(ResultSet rs, int rowNum) throws SQLException {
		QuickenLoans b = new QuickenLoans();
		setProperty(rs, b);
		return b;
	}
}