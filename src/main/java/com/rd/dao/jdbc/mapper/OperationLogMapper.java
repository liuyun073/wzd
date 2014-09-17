package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractOperationLogMapper;
import com.rd.model.account.OperationLogModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OperationLogMapper extends AbstractOperationLogMapper implements
		RowMapper<OperationLogModel> {
	public OperationLogModel mapRow(ResultSet rs, int num) throws SQLException {
		OperationLogModel log = new OperationLogModel();
		setProperty(rs, log);
		log.setUsername(rs.getString("username"));
		log.setVerify_username(rs.getString("verify_username"));
		log.setTypename(rs.getString("typename"));
		log.setUsertypename(rs.getString("usertypename"));
		return log;
	}
}