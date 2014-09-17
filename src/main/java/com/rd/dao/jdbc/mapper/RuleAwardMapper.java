package com.rd.dao.jdbc.mapper;

import com.rd.domain.RuleAward;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RuleAwardMapper implements RowMapper<RuleAward> {
	public RuleAward mapRow(ResultSet rs, int num) throws SQLException {
		RuleAward ruleAward = new RuleAward();
		ruleAward.setName(rs.getString("name"));
		ruleAward.setContext(rs.getString("context"));
		ruleAward.setId(rs.getLong("id"));
		ruleAward.setStart_date(rs.getString("start_date"));
		ruleAward.setEnd_date(rs.getString("end_date"));
		ruleAward.setAward_type(rs.getInt("award_type"));
		ruleAward.setTime_limit(rs.getInt("time_limit"));
		ruleAward.setMax_times(rs.getInt("max_times"));
		ruleAward.setBase_point(rs.getInt("base_point"));
		ruleAward.setMoney_limit(rs.getInt("money_limit"));
		ruleAward.setTotal_money(rs.getDouble("total_money"));
		ruleAward.setBestow_money(rs.getDouble("bestow_money"));
		ruleAward.setIs_absolute(rs.getInt("is_absolute"));
		ruleAward.setMsg_type(rs.getInt("msg_type"));
		ruleAward.setSubject(rs.getString("subject"));
		ruleAward.setContent(rs.getString("content"));
		ruleAward.setBack_type(rs.getInt("back_type"));
		ruleAward.setAddtime(rs.getString("addtime"));
		ruleAward.setAddip(rs.getString("addip"));
		return ruleAward;
	}
}