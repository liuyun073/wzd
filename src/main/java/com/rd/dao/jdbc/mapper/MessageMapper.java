package com.rd.dao.jdbc.mapper;

import com.rd.domain.Message;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MessageMapper implements RowMapper<Message> {
	public Message mapRow(ResultSet rs, int num) throws SQLException {
		Message msg = new Message();
		msg.setId(rs.getLong("id"));
		msg.setSent_user(rs.getLong("sent_user"));
		msg.setReceive_user(rs.getLong("receive_user"));
		msg.setName(rs.getString("name"));
		msg.setStatus(rs.getInt("status"));
		msg.setType(rs.getString("type"));
		msg.setSented(rs.getString("sented"));
		msg.setDeltype(rs.getInt("deltype"));
		msg.setContent(rs.getString("content"));
		msg.setAddtime(rs.getString("addtime"));
		msg.setAddip(rs.getString("addip"));

		msg.setReceive_username(rs.getString("receive_username"));
		msg.setSent_username(rs.getString("sent_username"));

		return msg;
	}
}