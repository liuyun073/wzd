package com.rd.dao.jdbc;

import com.rd.dao.UserTrackDao;
import com.rd.domain.UserTrack;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class UserTrackDaoImpl extends BaseDaoImpl implements UserTrackDao {
	private static Logger logger = Logger.getLogger(UserTrackDaoImpl.class);

	public void addUserTrack(UserTrack t) {
		String sql = "insert into  dw_usertrack(user_id,login_time,login_ip) values (?, ?, ?)";

		logger.debug("SQL:" + sql);
		try {
			getJdbcTemplate().update(
					sql,
					new Object[] { t.getUser_id(), t.getLogin_time(),
							t.getLogin_ip() });
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
	}

	public UserTrack getLastUserTrack(long userid) {
		UserTrack t = null;

		String sql = "select * from dw_usertrack where user_id = ? order by id desc limit 1,1";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userid);
		try {
			t = (UserTrack) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(userid) }, new RowMapper<UserTrack>() {
						public UserTrack mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserTrack t = new UserTrack();
							t.setUser_id(rs.getString("user_id"));
							t.setLogin_ip(rs.getString("login_ip"));
							t.setLogin_time(rs.getString("login_time"));
							return t;
						}
					});
		} catch (Exception e) {
			logger.debug("数据库查询结果为空！");
			logger.error(e.getMessage());
		}
		return t;
	}

	public int getUserTrackCount(long userid) {
		String sql = "select count(1) from dw_usertrack where user_id=?";
		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(userid) });
		return total;
	}
}