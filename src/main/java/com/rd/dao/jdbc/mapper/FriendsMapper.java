package com.rd.dao.jdbc.mapper;

import com.rd.domain.Friend;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FriendsMapper implements RowMapper<Friend> {
	
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend a = new Friend();
		a.setId(rs.getInt("id"));
		a.setUser_id(rs.getLong("user_id"));
		a.setFriends_userid(rs.getLong("friends_userid"));
		a.setFriends_username(rs.getString("username"));
		a.setStatus(rs.getInt("status"));
		a.setType(rs.getInt("type"));
		a.setContent(rs.getString("content"));
		a.setAddtime(rs.getString("addtime"));
		a.setAddip(rs.getString("addip"));
		return a;
	}
}