package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractCooperationLoginMapper;
import com.liuyun.site.domain.CooperationLogin;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CooperationLoginMapper extends AbstractCooperationLoginMapper
		implements RowMapper<CooperationLogin> {
	public CooperationLogin mapRow(ResultSet rs, int num) throws SQLException {
		CooperationLogin c = new CooperationLogin();
		c.setId(rs.getLong("id"));
		c.setOpen_id(rs.getString("open_id"));
		c.setOpen_key(rs.getString("open_key"));
		c.setUser_id(rs.getLong("user_id"));
		c.setType(Byte.valueOf(rs.getByte("type")));
		c.setGmt_create(rs.getString("gmt_create"));
		return c;
	}
}