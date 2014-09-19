package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.CreditType;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCreditTypeMapper {
	protected void setProperty(ResultSet rs, CreditType item)
			throws SQLException {
		item.setName(rs.getString("name"));
		item.setValue(rs.getLong("value"));
		item.setId(rs.getLong("id"));
		item.setNid(rs.getString("nid"));
		item.setCycle(rs.getByte("cycle"));
		item.setAward_times(rs.getByte("award_times"));
		item.setInterval(rs.getLong("interval"));
		item.setRemark(rs.getString("remark"));
		item.setOp_user(rs.getLong("op_user"));
		item.setAddtime(rs.getLong("addtime"));
		item.setAddip(rs.getString("addip"));
		item.setUpdatetime(rs.getLong("updatetime"));
		item.setUpdateip(rs.getString("updateip"));
		item.setRule_nid(rs.getString("rule_nid"));
		item.setCredit_category(rs.getByte("credit_category"));
	}
}