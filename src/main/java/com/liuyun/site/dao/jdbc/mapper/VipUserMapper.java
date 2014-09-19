package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractUserMapper;
import com.liuyun.site.model.DetailUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class VipUserMapper extends AbstractUserMapper implements
		RowMapper<DetailUser> {
	public DetailUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetailUser u = new DetailUser();
		setProperty(rs, u);
		u.setVip_status(rs.getInt("vip_status"));
		u.setVip_verify_time(rs.getLong("vip_verify_time"));
		u.setInvite_username(rs.getString("invite_username"));
		return u;
	}
}