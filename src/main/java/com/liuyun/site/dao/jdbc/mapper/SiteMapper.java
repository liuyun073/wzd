package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.domain.Site;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SiteMapper implements RowMapper<Site> {
	
	public Site mapRow(ResultSet rs, int num) throws SQLException {
		Site s = new Site();

		s.setSite_id(rs.getLong("site_id"));
		s.setCode(rs.getString("code"));
		s.setName(rs.getString("name"));
		s.setNid(rs.getString("nid"));
		s.setPid(rs.getInt("pid"));
		s.setRank(rs.getString("rank"));
		s.setUrl(rs.getString("url"));
		s.setAurl(rs.getString("aurl"));
		s.setIsurl(rs.getString("isurl"));
		s.setOrder(rs.getInt("order"));
		s.setStatus(rs.getInt("status"));
		s.setStyle(rs.getString("style"));
		s.setLitpic(rs.getString("litpic"));
		s.setContent(rs.getString("content"));
		s.setList_name(rs.getString("list_name"));
		s.setContent_name(rs.getString("content_name"));
		s.setSitedir(rs.getString("sitedir"));
		s.setVisit_type(rs.getString("visit_type"));
		s.setIndex_tpl(rs.getString("index_tpl"));
		s.setList_tpl(rs.getString("list_tpl"));
		s.setContent_tpl(rs.getString("content_tpl"));
		s.setTitle(rs.getString("title"));
		s.setKeywords(rs.getString("keywords"));
		s.setDescription(rs.getString("description"));
		s.setUser_id(rs.getString("user_id"));
		s.setAddtime(rs.getString("addtime"));
		s.setAddip(rs.getString("addip"));

		return s;
	}
}