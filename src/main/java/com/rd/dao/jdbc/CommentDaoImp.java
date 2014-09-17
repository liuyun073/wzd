package com.rd.dao.jdbc;

import com.rd.dao.CommentDao;
import com.rd.dao.jdbc.mapper.CommentMapper;
import com.rd.domain.Comments;
import com.rd.model.BorrowComments;
import com.rd.model.SearchParam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class CommentDaoImp extends BaseDaoImpl implements CommentDao {
	private Logger logger = Logger.getLogger(CommentDaoImp.class);

	public List<BorrowComments> getCommentList(long borrowid) {
		List<BorrowComments> list = new ArrayList<BorrowComments>();
		String sql;
		if (borrowid > 0L)
			sql = "select cm.*,us.username,us.card_pic1 from dw_comment as cm LEFT JOIN dw_user as us on cm.user_id=us.user_id where article_id=? and cm.status=1";
		else {
			sql = "select cm.*,us.username,us.card_pic1 from dw_comment as cm LEFT JOIN dw_user as us on cm.user_id=us.user_id";
		}
		this.logger.debug("Sql:" + sql);
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(borrowid) }, new CommentMapper());
		return list;
	}

	public int getCommentCount(long borrowid) {
		String sql = "SELECT COUNT(id) from dw_comment where article_id=? and status=1";
		String sql2 = "SELECT COUNT(id) from dw_comment";
		if (borrowid > 0L) {
			return getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(borrowid) });
		}
		if (borrowid == -1L) {
			return getJdbcTemplate().queryForInt(sql2);
		}

		return -3;
	}

	public int addComment(Comments c) {
		String sql = "insert into dw_comment(pid,user_id,module_code,article_id,comment,flag,`order`,status,addtime,addip) values(?,?,?,?,?,?,?,?,?,?)";
		return getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(c.getPid()),
						Long.valueOf(c.getUser_id()), c.getModule_code(),
						Long.valueOf(c.getArticle_id()), c.getComment(),
						c.getFlag(), c.getOrder(),
						Integer.valueOf(c.getStatus()), c.getAddtime(),
						c.getAddip() });
	}

	public int deleteCommentByCommentId(long id) {
		String sql = "delete from dw_comment where id=?";
		return getJdbcTemplate().update(sql);
	}

	public int deleteCommentByBorrowId(long Borrowid) {
		String sql = "delete from dw_comment where article_id=?";
		return getJdbcTemplate().update(sql);
	}

	public int deleteCommentByUserId(long userid) {
		return 0;
	}

	public void updateCommentByID(Comments c) {
		String sql = "update dw_comment set status=? where id=?";
		getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(c.getStatus()), Integer.valueOf(c.getId()) });
	}

	public void deleteCommentID(long id) {
		String sql = "delete from dw_comment where id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public Comments getCommentByid(long id) {
		String sql = "select * from dw_comment where id=?";
		Comments comments = null;
		try {
			comments = getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(id) }, new RowMapper<Comments>() {
						public Comments mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Comments comments = new Comments();
							comments.setId(rs.getInt("id"));
							comments.setComment(rs.getString("comment"));
							return comments;
						}
					});
		} catch (DataAccessException ea) {
			this.logger.debug("数据库查询结果为空！");
		}
		return comments;
	}

	public int getCommentCount(SearchParam param) {
		String selectSql = "SELECT COUNT(p1.user_id) FROM dw_comment p1 LEFT JOIN dw_user p2 ON p1.user_id=p2.user_id WHERE 1=1 ";
		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		String querySql = selectSql + searchSql;
		this.logger.debug("getCommentCount:" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public List<BorrowComments> getCommentCounts(int start, int end, SearchParam param) {
		String selectSql = "SELECT p1.id, p1.comment, p2.user_id , p2.username ,p1.addtime ,p1.status FROM dw_comment p1 ,dw_user p2 WHERE p1.user_id=p2.user_id";
		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		String limitSql = " limit ?,?";
		String groupSql = " group by p1.id ";
		String orderSql = " order by p1.id desc ";
		String querySql = selectSql + searchSql + groupSql + orderSql + limitSql;
		List<BorrowComments> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<BorrowComments>() {
					public BorrowComments mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BorrowComments comments = new BorrowComments();
						comments.setId(rs.getInt("id"));
						comments.setUser_id(rs.getInt("user_id"));
						comments.setAddtime(rs.getString("addtime"));
						comments.setStatus(rs.getInt("Status"));
						comments.setComment(rs.getString("comment"));
						comments.setUsername(rs.getString("username"));

						return comments;
					}
				});
		return list;
	}
}