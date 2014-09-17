package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.UserLog;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractUserLogMapper {
	protected void setProperty(ResultSet rs, UserLog u) throws SQLException {
		u.setLog_id(rs.getLong("log_id"));
		u.setUser_id(rs.getLong("user_id"));
		u.setQuery(rs.getString("query"));
		u.setResult(rs.getString("result"));
		u.setUrl(rs.getString("url"));
		u.setAddip(rs.getString("addip"));
		u.setAddtime(rs.getString("addtime"));
	}
}