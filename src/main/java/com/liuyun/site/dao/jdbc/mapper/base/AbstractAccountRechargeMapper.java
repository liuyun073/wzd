package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.context.Global;
import com.liuyun.site.domain.AccountRecharge;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAccountRechargeMapper {
	
	protected void setProperty(ResultSet rs, AccountRecharge c)
			throws SQLException {
		c.setId(rs.getLong("id"));
		c.setTrade_no(rs.getString("trade_no"));
		c.setUser_id(rs.getLong("user_id"));
		c.setStatus(rs.getInt("status"));
		c.setMoney(rs.getDouble("money"));
		c.setPayment(rs.getString("payment"));
		c.setReturntext(rs.getString("return"));
		c.setType(rs.getString("type"));
		c.setRemark(rs.getString("remark"));
		c.setFee(rs.getString("fee"));
		c.setVerify_userid(rs.getLong("verify_userid"));
		c.setVerify_time(rs.getString("verify_time"));
		c.setVerify_remark(rs.getString("verify_remark"));
		c.setAddtime(rs.getString("addtime"));
		c.setAddip(rs.getString("addip"));
		if (Global.getWebid().equals("ssjb"))
			c.setYes_no(rs.getInt("yes_no"));
	}
}