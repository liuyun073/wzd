package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.CooperationLogin;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCooperationLoginMapper {
	
	protected void setProperty(ResultSet rs, CooperationLogin c)
			throws SQLException {
		c.setId(rs.getLong("id"));
		c.setOpen_id(rs.getString("open_id"));
		c.setOpen_key(rs.getString("open_key"));
		c.setUser_id(rs.getLong("user_id"));
		c.setType(Byte.valueOf(rs.getByte("type")));
		c.setGmt_create(rs.getString("gmt_create"));
	}
}