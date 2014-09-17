package com.rd.dao.jdbc.mapper.base;

import com.rd.context.Global;
import com.rd.domain.AccountCash;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAccountCashMapper {
	
	protected void setProperty(ResultSet rs, AccountCash c) throws SQLException {
		c.setId(rs.getLong("id"));
		c.setUser_id(rs.getLong("user_id"));
		c.setStatus(rs.getInt("status"));
		c.setAccount(rs.getString("account"));
		c.setBank(rs.getString("bank"));
		c.setBranch(rs.getString("branch"));
		c.setTotal(rs.getString("total"));
		c.setCredited(rs.getString("credited"));
		c.setFee(rs.getString("fee"));
		c.setVerify_userid(rs.getLong("verify_userid"));
		c.setVerify_time(rs.getString("verify_time"));
		c.setVerify_remark(rs.getString("verify_remark"));
		c.setAddtime(rs.getString("addtime"));
		c.setAddip(rs.getString("addip"));

		c.setBankname(rs.getString("bankname"));
		c.setUsername(rs.getString("username"));
		c.setRealname(rs.getString("realname"));
		c.setVerify_username(rs.getString("verify_username"));
		if (Global.getWebid().equals("wzdai"))
			c.setHongbao(rs.getDouble("hongbao"));
	}
}