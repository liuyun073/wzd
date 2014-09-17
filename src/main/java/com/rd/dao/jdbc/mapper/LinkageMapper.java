package com.rd.dao.jdbc.mapper;

import com.rd.domain.Linkage;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class LinkageMapper implements RowMapper<Linkage> {
	public Linkage mapRow(ResultSet rs, int rowNum) throws SQLException {
		Linkage l = new Linkage();
		l.setId(rs.getInt("id"));
		l.setStatus(rs.getInt("status"));
		l.setOrder(rs.getInt("order"));
		l.setType_id(rs.getInt("type_id"));
		l.setPid(rs.getInt("pid"));
		l.setName(rs.getString("name"));
		l.setValue(rs.getString("value"));
		l.setAddtime(rs.getString("addtime"));
		l.setAddip(rs.getString("addip"));
		return l;
	}
}