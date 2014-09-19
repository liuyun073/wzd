package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.UserCreditLog;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCreditLogMapper {
	protected void setProperty(ResultSet rs, UserCreditLog ucLog)
			throws SQLException {
		ucLog.setId(rs.getInt("id"));
		ucLog.setUser_id(rs.getLong("user_id"));
		ucLog.setType_id(rs.getInt("type_id"));
		ucLog.setOp(rs.getLong("op"));
		ucLog.setValue(rs.getInt("value"));
		ucLog.setRemark(rs.getString("remark"));
		ucLog.setAddip(rs.getString("addip"));
		ucLog.setAddtime(rs.getLong("addtime"));
		ucLog.setOp_user(rs.getInt("op_user"));
	}
}