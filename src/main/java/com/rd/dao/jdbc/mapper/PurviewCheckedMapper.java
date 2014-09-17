package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractPurviewMapper;
import com.rd.domain.Purview;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PurviewCheckedMapper extends AbstractPurviewMapper implements
		RowMapper<Purview> {
	public Purview mapRow(ResultSet rs, int num) throws SQLException {
		Purview p = new Purview();
		setProperty(rs, p);
		p.setUser_type_id(rs.getLong("user_type_id"));
		if (p.getUser_type_id() > 0L)
			p.setChecked(true);
		else {
			p.setChecked(false);
		}
		return p;
	}
}