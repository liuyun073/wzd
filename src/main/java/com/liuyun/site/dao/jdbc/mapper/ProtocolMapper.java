package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractProtocolMapper;
import com.liuyun.site.model.ProtocolModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProtocolMapper extends AbstractProtocolMapper implements
		RowMapper<ProtocolModel> {
	public ProtocolModel mapRow(ResultSet rs, int num) throws SQLException {
		ProtocolModel protocol = new ProtocolModel();
		setProperty(rs, protocol);
		try {
			protocol.setUsername(rs.getString("username"));
			protocol.setProtocol_type_name(rs.getString("protocol_type_name"));
			protocol.setRealname(rs.getString("realname"));
			protocol.setCard_id(rs.getString("card_id"));
			protocol.setVerify_time(rs.getString("verify_time"));
			protocol.setBorrow_account(rs.getDouble("borrow_account"));
			protocol.setBorrowname(rs.getString("borrowname"));
		} catch (Exception localException) {
		}
		return protocol;
	}
}