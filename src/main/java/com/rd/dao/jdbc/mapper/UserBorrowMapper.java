package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractBorrowMapper;
import com.rd.model.UserBorrowModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserBorrowMapper extends AbstractBorrowMapper implements
		RowMapper<UserBorrowModel> {
	
	public UserBorrowModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBorrowModel b = new UserBorrowModel();
		setProperty(rs, b);
		b.setIsqiye(rs.getInt("isqiye"));
		b.setFastid(rs.getLong("fastid"));
		b.setUsername(rs.getString("username"));
		b.setRealname(rs.getString("realname"));
		b.setUser_area(rs.getString("user_area"));
		b.setKefu_username(rs.getString("kefu_username"));
		b.setQq(rs.getString("qq"));
		b.setCredit_jifen(rs.getInt("credit_jifen"));
		b.setCredit_pic(rs.getString("credit_pic"));
		b.setAdd_area(rs.getString("add_area"));
		b.setScales(rs.getDouble("scales"));
		b.setUsetypename(rs.getString("usetypename"));
		return b;
	}
}