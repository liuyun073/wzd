package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.Account;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAccountMapper {
	
	protected void setProperty(ResultSet rs, Account s) throws SQLException {
		s.setId(rs.getLong("id"));
		s.setTotal(rs.getDouble("total"));
		s.setCollection(rs.getDouble("collection"));
		s.setNo_use_money(rs.getDouble("no_use_money"));
		s.setUse_money(rs.getDouble("use_money"));
		s.setUser_id(rs.getLong("user_id"));
		s.setHongbao(rs.getDouble("hongbao"));
	}
}