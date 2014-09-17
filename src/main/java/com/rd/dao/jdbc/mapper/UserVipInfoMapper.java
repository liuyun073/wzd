package com.rd.dao.jdbc.mapper;

import com.rd.model.UserCacheModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserVipInfoMapper implements RowMapper<UserCacheModel> {
	public UserCacheModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCacheModel u = new UserCacheModel();
		u.setKefu_name(rs.getString("kefu_name"));
		u.setUser_id(rs.getLong("user_id"));
		u.setKefu_addtime(rs.getString("kefu_addtime"));
		u.setVip_status(rs.getInt("vip_status"));
		u.setAccount_waitvip(rs.getInt("account_waitvip"));
		u.setUsername(rs.getString("username"));
		u.setType_id(rs.getString("type_id"));
		return u;
	}
}