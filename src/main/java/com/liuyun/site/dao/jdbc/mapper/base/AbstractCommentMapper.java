package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Comments;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCommentMapper {
	
	protected void setProperty(ResultSet rs, Comments c) throws SQLException {
		c.setAddip(rs.getString("addip"));
		c.setAddtime(rs.getString("addtime"));
		c.setArticle_id(rs.getLong("article_id"));
		c.setComment(rs.getString("comment"));
		c.setFlag(rs.getString("flag"));
		c.setId(rs.getInt("id"));
		c.setPid(rs.getInt("pid"));
		c.setModule_code(rs.getString("module_code"));
		c.setOrder(rs.getString("order"));
		c.setStatus(rs.getInt("status"));
		c.setUser_id(rs.getLong("user_id"));
	}
}