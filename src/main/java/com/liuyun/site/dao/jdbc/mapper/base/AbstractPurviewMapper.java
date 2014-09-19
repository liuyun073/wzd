package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Purview;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractPurviewMapper implements RowMapper<Purview> {
	protected void setProperty(ResultSet rs, Purview p) throws SQLException {
		p.setId(rs.getLong("id"));
		p.setPid(rs.getLong("pid"));
		p.setPurview(rs.getString("purview"));
		p.setUrl(rs.getString("url"));
		p.setRemark(rs.getString("remark"));
		p.setName(rs.getString("name"));
		p.setLevel(rs.getInt("level"));
	}
}