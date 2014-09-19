package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractArticleMapper;
import com.liuyun.site.domain.Article;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ArticleMapper extends AbstractArticleMapper implements
		RowMapper<Article> {
	public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		Article b = new Article();
		setProperty(rs, b);
		return b;
	}
}