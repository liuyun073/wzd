package com.rd.dao.jdbc.mapper;

import com.rd.domain.Advanced;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AdvancedMapper implements RowMapper<Advanced> {
	
	public Advanced mapRow(ResultSet rs, int num) throws SQLException {
		Advanced c = new Advanced();
		c.setAdvance_reserve(rs.getDouble("advance_reserve"));
		c.setNo_advanced_account(rs.getDouble("no_advanced_account"));
		c.setId(rs.getInt("id"));
		c.setBorrow_total(rs.getDouble("borrow_total"));
		c.setWait_total(rs.getDouble("wait_total"));
		return c;
	}
}