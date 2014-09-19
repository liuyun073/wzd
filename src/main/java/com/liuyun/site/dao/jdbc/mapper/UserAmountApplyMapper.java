package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.UserAmountApply;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserAmountApplyMapper implements RowMapper<UserAmountApply> {
	public UserAmountApply mapRow(ResultSet rs, int num) throws SQLException {
		UserAmountApply amount = new UserAmountApply();
		amount.setId(rs.getLong("id"));
		amount.setUser_id(rs.getLong("user_id"));
		amount.setType(rs.getString("type"));
		amount.setAccount(rs.getDouble("account"));
		amount.setAccount_new(rs.getDouble("account_new"));
		amount.setAccount_old(rs.getDouble("account_old"));
		amount.setStatus(rs.getInt("status"));
		amount.setAddtime(rs.getString("addtime"));
		amount.setContent(rs.getString("content"));
		amount.setRemark(rs.getString("remark"));
		amount.setVerify_remark(rs.getString("verify_remark"));
		amount.setVerify_time(rs.getString("verify_time"));
		amount.setVerify_user(rs.getLong("verify_user"));
		amount.setAddip(rs.getString("addip"));

		amount.setUsername(rs.getString("username"));
		return amount;
	}
}