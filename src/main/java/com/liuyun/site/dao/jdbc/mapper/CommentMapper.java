package com.liuyun.site.dao.jdbc.mapper;

import com.liuyun.site.dao.jdbc.mapper.base.AbstractCommentMapper;
import com.liuyun.site.model.BorrowComments;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CommentMapper extends AbstractCommentMapper implements
		RowMapper<BorrowComments> {
	public BorrowComments mapRow(ResultSet rs, int arg1) throws SQLException {
		BorrowComments comments = new BorrowComments();
		super.setProperty(rs, comments);
		comments.setUsername(rs.getString("username"));
		return comments;
	}
}