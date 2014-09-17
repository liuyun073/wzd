package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractUserMapper;
import com.rd.model.DetailUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DetailUserMapper extends AbstractUserMapper implements
		RowMapper<DetailUser> {
	public DetailUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetailUser u = new DetailUser();
		setProperty(rs, u);

		u.setCredit_jifen(rs.getInt("credit_jifen"));
		u.setCredit_pic(rs.getString("credit_pic"));
		u.setVip_status(rs.getInt("vip_status"));
		u.setProvincetext(rs.getString("provincetext"));
		u.setCitytext(rs.getString("citytext"));
		u.setAreatext(rs.getString("areatext"));
		u.setTypename(rs.getString("typename"));
		u.setVip_verify_time(rs.getLong("vip_verify_time"));
		u.setKefu_addtime(rs.getLong("kefu_addtime"));
		u.setUse_money(rs.getDouble("use_money"));
		u.setKefu_username(rs.getString("kefu_username"));
		return u;
	}
}