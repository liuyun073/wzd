package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.UserType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserTypeMapper implements RowMapper<UserType> {
	public UserType mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserType u = new UserType();
		u.setAddtime(rs.getString("addtime"));
		u.setName(rs.getString("name"));
		u.setOrder(rs.getString("order"));
		u.setPurview(rs.getString("purview"));
		u.setRemark(rs.getString("remark"));
		u.setStatus(rs.getInt("status"));
		u.setSummary(rs.getString("summary"));
		u.setType(rs.getInt("type"));
		u.setType_id(rs.getLong("type_id"));
		return u;
	}
}