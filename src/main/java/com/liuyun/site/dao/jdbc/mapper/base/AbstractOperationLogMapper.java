package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.OperationLog;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractOperationLogMapper {
	protected void setProperty(ResultSet rs, OperationLog l)
			throws SQLException {
		l.setId(rs.getLong("id"));
		l.setUser_id(rs.getLong("user_id"));
		l.setType(rs.getString("type"));
		l.setVerify_user(rs.getLong("verify_user"));
		l.setAddtime(rs.getString("addtime"));
		l.setAddip(rs.getString("addip"));
		l.setOperationResult(rs.getString("operationResult"));
	}
}