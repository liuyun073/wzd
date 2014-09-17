package com.rd.dao.jdbc.mapper.base;

import com.rd.domain.Article;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractArticleMapper {
	
	protected void setProperty(ResultSet rs, Article b) throws SQLException {
		b.setId(rs.getLong("id"));
		b.setSite_id(rs.getLong("site_id"));
		b.setUser_id(rs.getLong("user_id"));
		b.setName(rs.getString("name"));
		b.setAuthor(rs.getString("author"));
		b.setStatus(rs.getInt("status"));
		b.setHits(rs.getInt("hits"));
		b.setLitpic(rs.getString("litpic"));
		b.setFlag(rs.getString("flag"));
		b.setOrder(rs.getInt("order"));
		b.setSource(rs.getString("source"));
		b.setPublish(rs.getString("publish"));
		b.setSummary(rs.getString("summary"));
		b.setContent(rs.getString("content"));
		b.setAddtime(rs.getString("addtime"));
		b.setAddip(rs.getString("addip"));
	}
}