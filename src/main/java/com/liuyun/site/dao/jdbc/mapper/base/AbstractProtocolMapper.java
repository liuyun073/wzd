package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Protocol;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractProtocolMapper {
	protected void setProperty(ResultSet rs, Protocol c) throws SQLException {
		c.setId(rs.getLong("id"));
		c.setUser_id(rs.getLong("user_id"));
		c.setPid(rs.getLong("pid"));
		c.setProtocol_type(rs.getString("protocol_type"));
		c.setRemark(rs.getString("remark"));
		c.setAddtime(rs.getString("addtime"));
		c.setAddip(rs.getString("addip"));
		c.setBorrow_id(rs.getLong("borrow_id"));
		c.setRepayment_account(rs.getDouble("repayment_account"));
		c.setRepayment_time(rs.getString("repayment_time"));
		c.setInterest(rs.getDouble("interest"));
		c.setMoney(rs.getDouble("money"));
		c.setBank_account(rs.getString("bank_account"));
		c.setBank_branch(rs.getString("bank_branch"));
	}
}