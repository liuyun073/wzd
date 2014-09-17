package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractPurviewMapper;
import com.rd.domain.Purview;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PurviewMapper extends AbstractPurviewMapper implements
		RowMapper<Purview> {
	public Purview mapRow(ResultSet rs, int num) throws SQLException {
		Purview p = new Purview();
		setProperty(rs, p);
		return p;
	}
}