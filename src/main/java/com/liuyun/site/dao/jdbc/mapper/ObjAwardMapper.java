package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.ObjAward;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ObjAwardMapper implements RowMapper<ObjAward> {
	public ObjAward mapRow(ResultSet rs, int num) throws SQLException {
		ObjAward objAward = new ObjAward();
		objAward.setName(rs.getString("name"));
		objAward.setId(rs.getLong("id"));
		objAward.setRule_id(rs.getLong("rule_id"));
		objAward.setLevel(rs.getInt("level"));
		objAward.setRate(rs.getInt("rate"));
		objAward.setPoint_limit(rs.getLong("point_limit"));
		objAward.setBestow(rs.getLong("bestow"));
		objAward.setTotal(rs.getLong("total"));
		objAward.setAward_limit(rs.getInt("award_limit"));
		objAward.setDescription(rs.getString("description"));
		objAward.setRatio(rs.getDouble("ratio"));
		objAward.setObj_value(rs.getLong("obj_value"));
		objAward.setPic_url(rs.getString("pic_url"));
		objAward.setObject_rule(rs.getString("object_rule"));
		objAward.setAddtime(rs.getString("addtime"));
		objAward.setAddip(rs.getString("addip"));
		return objAward;
	}
}