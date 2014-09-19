package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractCreditLogMapper;
import com.liuyun.site.domain.UserCreditLog;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserCreditLogMapper extends AbstractCreditLogMapper implements
		RowMapper<UserCreditLog> {
	public UserCreditLog mapRow(ResultSet rs, int num) throws SQLException {
		UserCreditLog ucLog = new UserCreditLog();
		setProperty(rs, ucLog);
		ucLog.setId(rs.getInt("id"));
		ucLog.setUser_id(rs.getLong("user_id"));
		ucLog.setType_id(rs.getInt("type_id"));
		ucLog.setOp(rs.getLong("op"));
		ucLog.setValue(rs.getInt("value"));
		ucLog.setRemark(rs.getString("remark"));
		ucLog.setAddip(rs.getString("addip"));
		ucLog.setAddtime(rs.getLong("addtime"));
		ucLog.setOp_user(rs.getInt("op_user"));
		return ucLog;
	}
}