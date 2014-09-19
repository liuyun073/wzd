package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.PasswordToken;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PasswordTokenMapper implements RowMapper<PasswordToken> {
	public PasswordToken mapRow(ResultSet rs, int rowNum) throws SQLException {
		PasswordToken pt = new PasswordToken();
		pt.setId(rs.getLong("id"));
		pt.setUser_id(rs.getLong("user_id"));
		pt.setQuestion(rs.getString("question"));
		pt.setAnswer(rs.getString("answer"));
		return pt;
	}
}