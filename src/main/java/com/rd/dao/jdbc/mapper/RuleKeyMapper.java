package com.rd.dao.jdbc.mapper;

import com.rd.domain.RuleKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RuleKeyMapper implements RowMapper<RuleKey> {
	public RuleKey mapRow(ResultSet rs, int rowNum) throws SQLException {
		RuleKey ruleKey = new RuleKey();
		ruleKey.setId(rs.getInt("id"));
		ruleKey.setKey(rs.getString("key"));
		ruleKey.setName(rs.getString("name"));
		ruleKey.setType(rs.getString("type"));
		ruleKey.setValue(rs.getString("value"));
		return ruleKey;
	}
}