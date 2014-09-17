package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractCollectionMapper;
import com.rd.model.DetailCollection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DetailCollectionMapper extends AbstractCollectionMapper implements
		RowMapper<DetailCollection> {
	public DetailCollection mapRow(ResultSet rs, int num) throws SQLException {
		DetailCollection dc = new DetailCollection();
		setProperty(rs, dc);
		try {
			dc.setBorrow_name(rs.getString("borrow_name"));
		} catch (Exception localException) {
		}
		try {
			dc.setBorrow_id(rs.getLong("borrow_id"));
		} catch (Exception localException1) {
		}
		try {
			dc.setTime_limit(rs.getString("time_limit"));
		} catch (Exception localException2) {
		}
		try {
			dc.setUsername(rs.getString("username"));
		} catch (Exception localException3) {
		}
		try {
			dc.setBorrow_style(rs.getString("borrow_style"));
		} catch (Exception localException4) {
		}
		return dc;
	}
}