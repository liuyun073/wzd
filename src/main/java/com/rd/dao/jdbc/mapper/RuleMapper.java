package com.rd.dao.jdbc.mapper;

import com.rd.domain.Rule;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RuleMapper implements RowMapper<Rule> {
	public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
		Rule rule = new Rule();
		rule.setId(rs.getInt("id"));
		rule.setName(rs.getString("name"));
		rule.setStatus(Byte.valueOf(rs.getByte("status")));
		rule.setAddtime(rs.getString("addtime"));
		rule.setNid(rs.getString("nid"));
		rule.setRemark(rs.getString("remark"));
		rule.setRule_check(rs.getString("rule_check"));
		return rule;
	}
}