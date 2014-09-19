package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractUserinfoMapper;
import com.liuyun.site.domain.Userinfo;
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