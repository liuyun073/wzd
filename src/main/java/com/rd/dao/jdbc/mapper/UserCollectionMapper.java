package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractCollectionMapper;
import com.rd.model.DetailCollection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserCollectionMapper extends AbstractCollectionMapper implements
		RowMapper<DetailCollection> {
	public DetailCollection mapRow(ResultSet rs, int num) throws SQLException {
		DetailCollection dc = new DetailCollection();
		setProperty(rs, dc);
		dc.setUsername(rs.getString("username"));
		dc.setTendertime(rs.getString("tendertime"));
		return dc;
	}
}