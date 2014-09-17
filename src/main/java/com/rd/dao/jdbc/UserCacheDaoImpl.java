package com.rd.dao.jdbc;

import com.rd.dao.UserCacheDao;
import com.rd.dao.jdbc.mapper.UserCacheMapper;
import com.rd.dao.jdbc.mapper.UserVipInfoMapper;
import com.rd.domain.UserCache;
import com.rd.model.SearchParam;
import com.rd.model.UserCacheModel;
import com.rd.model.VIPStatisticModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class UserCacheDaoImpl extends BaseDaoImpl implements UserCacheDao {
	private static Logger logger = Logger.getLogger(UserCacheDaoImpl.class);

	public void addUserCache(UserCache uc) {
		String sql = "insert into dw_user_cache(user_id,kefu_userid,kefu_addtime,vip_status,vip_remark) values (?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate()
				.update(
						sql,
						new Object[] { Long.valueOf(uc.getUser_id()),
								Long.valueOf(uc.getKefu_userid()),
								uc.getKefu_addtime(),
								Integer.valueOf(uc.getVip_status()),
								uc.getVip_remark() });
	}

	public void updateUserCache(UserCache uc) {
		String sql = "update dw_user_cache set kefu_userid=?,vip_verify_time=?,vip_status=?,vip_verify_remark=?,kefu_username=?,kefu_addtime=? where user_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(uc.getKefu_userid()),
						uc.getVip_verify_time(),
						Integer.valueOf(uc.getVip_status()),
						uc.getVip_verify_remark(), uc.getKefu_username(),
						uc.getKefu_addtime(), Long.valueOf(uc.getUser_id()) });
	}

	public UserCacheModel getUserCacheByUserid(long userid) {
		String sql = "select p1.*,p3.username as kefu_name,p3.realname as kefu_realname, p4.pic as credit_pic,p2.value as credit_jifen ,p5.username as username from dw_user_cache as p1 left join dw_credit as p2 on p1.user_id=p2.user_id left join dw_user as p3 on p1.kefu_userid = p3.user_id left join dw_credit_rank as p4 on p2.value<=p4.point2 and p2.value>=p4.point1 left join dw_user as p5 on p1.user_id=p5.user_id where p1.user_id =?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + userid);
		UserCacheModel cache = null;
		try {
			cache = (UserCacheModel) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(userid) },
					new UserCacheMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return cache;
	}

	public List<UserCacheModel> getUserVipinfo(long page, int Max, int status, SearchParam p) {
		String sql = "SELECT p.user_id,p2.username,p2.type_id,kf.username as kefu_name,p.kefu_addtime,p.vip_status,p.account_waitvip,p.vip_verify_time from dw_user_cache as p LEFT JOIN dw_user as kf on p.kefu_userid=kf.user_id  LEFT JOIN dw_user as p2 on p.user_id=p2.user_id  ";
		if (status != -1) {
			sql = sql + "where vip_status=? ";
			sql = sql + p.getSearchParamSql();
			sql = sql + "   order by p2.user_id desc ";
			sql = sql + " LIMIT ?,? ";
			logger.debug(sql);
			System.out.println("if=============================" + sql);
			return getJdbcTemplate().query(
					sql,
					new Object[] { Integer.valueOf(status), Long.valueOf(page),
							Integer.valueOf(Max) }, new UserVipInfoMapper());
		}
		sql = sql + p.getSearchParamSql();
		sql = sql + "   order by p2.user_id desc ";
		sql = sql + " LIMIT ?,?";
		System.out.println("else=============================" + sql);
		return getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(page), Integer.valueOf(Max) },
				new UserVipInfoMapper());
	}

	public int getUserVipinfo(int status, SearchParam p) {
		String sql = "SELECT count(*) from dw_user_cache as p LEFT JOIN dw_user as kf on p.kefu_userid=kf.user_id  LEFT JOIN dw_user as p2 on p.user_id=p2.user_id  ";
		if (status != -1) {
			sql = sql + "where vip_status=? ";
		}
		sql = sql + p.getSearchParamSql();
		sql = sql + "   order by p2.user_id desc";
		logger.debug(sql);

		return getJdbcTemplate().queryForInt(sql,
				new Object[] { Integer.valueOf(status) });
	}

	public UserCacheModel validUserVip(long user_id) {
		String sql = "update dw_user_cache set vip_verify_time=0,vip_status=0 where user_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id) });
		return getUserCacheByUserid(user_id);
	}

	public int getVipStatistic(SearchParam param) {
		String selectSql = "select count(1) from dw_user p1 LEFT JOIN dw_user_cache p4 ON p4.user_id=p1.user_id where 1=1 ";

		String searchSql = param.getSearchParamSql();
		String querySql = selectSql + searchSql;
		logger.debug("getVipStatistic:" + querySql);
		int count = 0;
		count = count(querySql);
		return count;
	}

	public List<VIPStatisticModel> getVipStatisticList(int start, int pernum, SearchParam param) {
		String selectSql = "select p4.*,p1.username,p1.realname,p1.addtime as registertime,p3.collection,p4.kefu_username from dw_user p1 LEFT JOIN dw_user_cache p4 ON p4.user_id=p1.user_id LEFT JOIN dw_account p3 ON p3.user_id=p1.user_id where 1=1 ";

		String orderSql = "order by p1.addtime desc,p1.user_id desc ";
		String limitSql = "limit ?,?";
		String groupbySql = " GROUP BY p1.user_id ";
		String searchSql = param.getSearchParamSql();
		String querySql = selectSql + searchSql + groupbySql + orderSql + limitSql;
		logger.debug("getVipStatisticList:" + querySql);
		List<VIPStatisticModel> list = getJdbcTemplate()
				.query(
						querySql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) }, new RowMapper<VIPStatisticModel>() {
							public VIPStatisticModel mapRow(ResultSet rs,
									int num) throws SQLException {
								VIPStatisticModel vipsm = new VIPStatisticModel();
								vipsm.setUsername(rs.getString("username"));
								vipsm.setRealname(rs.getString("realname"));
								vipsm.setRegistertime(rs
										.getString("registertime"));
								vipsm.setCollection(rs.getString("collection"));
								vipsm.setKefu_username(rs
										.getString("kefu_username"));
								vipsm.setVip_verify_time(rs
										.getString("vip_verify_time"));
								return vipsm;
							}
						});
		return list;
	}

	public List<VIPStatisticModel> getVipStatisticList(SearchParam param) {
		String selectSql = "select p4.*,p1.user_id,p1.username,p1.realname,p1.addtime as registertime,p3.collection,p4.kefu_username from dw_user p1 LEFT JOIN dw_user_cache p4 ON p4.user_id=p1.user_id LEFT JOIN dw_account p3 ON p3.user_id=p1.user_id where 1=1 ";

		String searchSql = param.getSearchParamSql();
		String orderSql = "order by p1.addtime desc,p1.user_id desc ";
		String groupbySql = " GROUP BY p1.user_id ";
		String querySql = selectSql + searchSql + groupbySql + orderSql;
		logger.debug("SQL:" + querySql);
		List<VIPStatisticModel> list = new ArrayList<VIPStatisticModel>();
		list = getJdbcTemplate().query(querySql, new RowMapper<VIPStatisticModel>() {
			public VIPStatisticModel mapRow(ResultSet rs, int num)
					throws SQLException {
				VIPStatisticModel vipsm = new VIPStatisticModel();
				vipsm.setUser_id(rs.getLong("user_id"));
				vipsm.setUsername(rs.getString("username"));
				vipsm.setRealname(rs.getString("realname"));
				vipsm.setRegistertime(rs.getString("registertime"));
				vipsm.setCollection(rs.getString("collection"));
				vipsm.setKefu_username(rs.getString("kefu_username"));
				vipsm.setVip_verify_time(rs.getString("vip_verify_time"));
				return vipsm;
			}
		});
		return list;
	}

	public int getLoginFailTimes(long user_id) {
		String sql = "select login_fail_times from dw_user_cache where user_id=?";
		logger.debug("SQL:" + sql);
		return getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(user_id) });
	}

	public void cleanLoginFailTimes(long user_id) {
		String sql = "update dw_user_cache set login_fail_times=0 where user_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id) });
	}

	public void updateLoginFailTimes(long user_id) {
		String sql = "update dw_user_cache set login_fail_times=login_fail_times+1 where user_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id) });
	}
}