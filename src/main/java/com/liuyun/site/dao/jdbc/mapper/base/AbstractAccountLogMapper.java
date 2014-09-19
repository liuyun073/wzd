package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.AccountLog;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAccountLogMapper {
	
	protected void setProperty(ResultSet rs, AccountLog l) throws SQLException {
		l.setId(rs.getLong("id"));
		l.setUser_id(rs.getLong("user_id"));
		l.setType(rs.getString("type"));
		l.setTotal(rs.getDouble("total"));
		l.setMoney(rs.getDouble("money"));
		l.setUse_money(rs.getDouble("use_money"));
		l.setNo_use_money(rs.getDouble("no_use_money"));
		l.setCollection(rs.getDouble("collection"));
		l.setTo_user(rs.getLong("to_user"));
		l.setRemark(rs.getString("remark"));
		l.setAddtime(rs.getString("addtime"));
		l.setAddip(rs.getString("addip"));
	}
}