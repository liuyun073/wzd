package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserinfoMapper;
import com.rd.domain.Userinfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserinfoMapper extends AbstractUserinfoMapper implements
		RowMapper<Userinfo> {
	public Userinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Userinfo info = new Userinfo();
		setProperty(rs, info);
		return info;
	}
}