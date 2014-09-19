package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.UserLogDao;
import com.liuyun.site.dao.jdbc.mapper.UserLogModelMapper;
import com.liuyun.site.domain.UserLog;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserLogModel;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class UserLogDaoImpl extends BaseDaoImpl implements UserLogDao {
	private static Logger logger = Logger.getLogger(UserLogDaoImpl.class);

	public void addUserLog(UserLog userLog) {
		String sql = "insert into dw_user_log(user_id,query,url,result,addtime,addip)values(?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(userLog.getUser_id()),
						userLog.getQuery(), userLog.getUrl(),
						userLog.getResult(), userLog.getAddtime(),
						userLog.getAddip() });
	}

	public int getLogCountByUserId(long userId, SearchParam param) {
		int logCount = 0;
		String searchParamSql = param.getUserLogSearchParamSql();
		String sql = "select count(1) from dw_user_log as p1 left join dw_user as p2 on p1.user_id = p2.user_id where p2.user_id = ?"
				+ searchParamSql + "order by p1.addtime desc";
		logger.debug("user_id:" + userId);
		logger.debug("SQL:" + sql);
		logCount = count(sql, new Object[] { Long.valueOf(userId) });

		return logCount;
	}

	public List<UserLogModel> getLogListByUserId(long userId, int start, int end,
			SearchParam param) {
		String searchParamSql = param.getUserLogSearchParamSql();
		int pernum = end - start - 1;
		String sql = "select p1.*,p2.username,p2.realname from dw_user_log as p1 left join dw_user as p2 on p1.user_id = p2.user_id where p2.user_id = ?"
				+ searchParamSql + "order by p1.addtime desc limit ?,?";
		logger.debug("user_id:" + userId);
		logger.debug("pernum:" + pernum);
		logger.debug("SQL:" + sql);
		List<UserLogModel> list = new ArrayList<UserLogModel>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(userId), Integer.valueOf(start),
						Integer.valueOf(pernum) }, new UserLogModelMapper());

		return list;
	}

	public int getLogCountByParam(SearchParam param) {
		int logCount = 0;
		String searchParamSql2Admin = param.getUserLogSearchParamSql();
		String sql = "select count(1) from dw_user_log as p1 left join dw_user as p2 on p1.user_id = p2.user_id where p2.user_id = ?"
				+ searchParamSql2Admin + "order by p1.addtime desc";
		logger.debug("SQL:" + sql);
		logCount = count(sql);

		return logCount;
	}

	public List<UserLogModel> getLogListByParams(int start, int end, SearchParam param) {
		String searchParamSql2Admin = param.getUserLogSearchParamSql();
		int pernum = end - start - 1;
		String sql = "select p1.*,p2.username,p2.realname from dw_user_log as p1 left join dw_user as p2 on p1.user_id = p2.user_id where p2.user_id = ?"
				+ searchParamSql2Admin + "order by p1.addtime desc limit ?,?";
		logger.debug("pernum:" + pernum);
		logger.debug("SQL:" + sql);
		List<UserLogModel> list = new ArrayList<UserLogModel>();
		list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new UserLogModelMapper());

		return list;
	}
}