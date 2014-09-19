package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.QuickenLoans;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractQuickenLoansMapper {
	protected void setProperty(ResultSet rs, QuickenLoans b)
			throws SQLException {
		b.setLoansId(Integer.valueOf(rs.getInt("loans_id")));
		b.setName(rs.getString("name"));
		b.setPhone(rs.getString("phone"));
		b.setArea(rs.getString("area"));
		b.setRemark(rs.getString("remark"));
		b.setCreateTime(rs.getString("create_time"));
	}
}