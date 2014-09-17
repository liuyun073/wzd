package com.rd.dao.jdbc.mapper;

import com.rd.dao.jdbc.mapper.base.AbstractCreditTypeMapper;
import com.rd.domain.CreditType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CreditTypeMapper extends AbstractCreditTypeMapper implements
		RowMapper<CreditType> {
	public CreditType mapRow(ResultSet rs, int num) throws SQLException {
		CreditType creditType = new CreditType();
		creditType.setName(rs.getString("name"));
		creditType.setValue(rs.getLong("value"));
		creditType.setId(rs.getLong("id"));
		creditType.setNid(rs.getString("nid"));
		creditType.setCycle(rs.getByte("cycle"));
		creditType.setAward_times(rs.getByte("award_times"));
		creditType.setInterval(rs.getLong("interval"));
		creditType.setRemark(rs.getString("remark"));
		creditType.setOp_user(rs.getLong("op_user"));
		creditType.setAddtime(rs.getLong("addtime"));
		creditType.setAddip(rs.getString("addip"));
		creditType.setUpdatetime(rs.getLong("updatetime"));
		creditType.setUpdateip(rs.getString("updateip"));
		creditType.setRule_nid(rs.getString("rule_nid"));
		creditType.setCredit_category(rs.getByte("credit_category"));
		return creditType;
	}
}