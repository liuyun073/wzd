package com.rd.dao.jdbc.mapper;

import com.rd.domain.Areainfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AreainfoMapper implements RowMapper<Areainfo> {
	public Areainfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Areainfo a = new Areainfo();
		a.setId(rs.getInt("id"));
		a.setDomain(rs.getString("domain"));
		a.setNid(rs.getString("nid"));
		a.setOrder(rs.getInt("order"));
		a.setPid(rs.getInt("pid"));
		a.setName(rs.getString("name"));
		return a;
	}
}