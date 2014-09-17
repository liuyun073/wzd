package com.rd.dao.jdbc.mapper;

import com.rd.domain.UserAward;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserAwardMapper implements RowMapper<UserAward> {
	public UserAward mapRow(ResultSet rs, int num) throws SQLException {
		UserAward userAward = new UserAward();
		userAward.setId(rs.getLong("id"));
		userAward.setUser_id(rs.getLong("user_id"));
		userAward.setUser_name(rs.getString("user_name"));
		userAward.setLevel(rs.getInt("level"));
		userAward.setAward_id(rs.getLong("award_id"));
		userAward.setPoint_reduce(rs.getLong("point_reduce"));
		userAward.setRule_id(rs.getLong("rule_id"));
		userAward.setAward_name(rs.getString("award_name"));
		userAward.setStatus(rs.getInt("status"));
		userAward.setReceive_status(rs.getInt("receive_status"));
		userAward.setAddtime(rs.getString("addtime"));
		userAward.setAddip(rs.getString("addip"));
		return userAward;
	}
}