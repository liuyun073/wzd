package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.RewardStatisticsDao;
import com.liuyun.site.dao.jdbc.mapper.RewardStatisticsMapper;
import com.liuyun.site.dao.jdbc.mapper.RewardStatisticsModelMapper;
import com.liuyun.site.dao.jdbc.mapper.RewardStatisticsSumMapper;
import com.liuyun.site.dao.jdbc.mapper.UserMapper;
import com.liuyun.site.domain.RewardStatistics;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.RewardStatisticsModel;
import com.liuyun.site.model.RewardStatisticsSumModel;
import com.liuyun.site.model.SearchParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class RewardStatisticsDaoImpl extends BaseDaoImpl implements
		RewardStatisticsDao {
	private static Logger logger = Logger
			.getLogger(RewardStatisticsDaoImpl.class);

	public String orderSql = "order by p1.addtime limit ?, ?;";

	public void addRewardStatistics(RewardStatistics rewardStatistics) {
		String sql = "insert into dw_reward_statistics(type, reward_user_id, passive_user_id, receive_time, receive_yestime, receive_account, receive_yesaccount, receive_status,addtime, endtime, rule_id, back_type, type_fk_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		logger.debug("SQL: " + sql);
		getJdbcTemplate()
				.update(
						sql,
						new Object[] {
								rewardStatistics.getType(),
								Long.valueOf(rewardStatistics
										.getReward_user_id()),
								Long.valueOf(rewardStatistics
										.getPassive_user_id()),
								rewardStatistics.getReceive_time(),
								rewardStatistics.getReceive_yestime(),
								Double.valueOf(rewardStatistics
										.getReceive_account()),
								Double.valueOf(rewardStatistics
										.getReceive_yesaccount()),
								rewardStatistics.getReceive_status(),
								rewardStatistics.getAddtime(),
								rewardStatistics.getEndtime(),
								Long.valueOf(rewardStatistics.getRule_id()),
								rewardStatistics.getBack_type(),
								Long.valueOf(rewardStatistics.getType_fk_id()) });
	}

	public void updateReward(RewardStatistics r) {
		String sql = "update dw_reward_statistics set receive_yestime=?, receive_yesaccount=?,receive_status=? where id=?";

		logger.debug("SQL: " + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { r.getReceive_yestime(),
						Double.valueOf(r.getReceive_yesaccount()),
						r.getReceive_status(), Long.valueOf(r.getId()) });
	}

	public void updateAccount(double account, long id) {
		String sql = "update dw_reward_statistics set receive_account=? where id=?";
		logger.debug("SQL: " + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(account), Long.valueOf(id) });
	}

	public List<RewardStatisticsModel> getRewardStatistics(SearchParam param) {
		String sql = "select *, p2.username, us.username as passive_username from dw_reward_statistics as p1 left join dw_user as p2 on p2.user_id = p1.reward_user_id left join dw_user as us on us.user_id = p1.passive_user_id where 1=1 ";

		String searchSql = param.getSearchParamSql();
		String selectSql = sql + searchSql;
		logger.debug(" SQL: " + selectSql);
		List<RewardStatisticsModel> list = new ArrayList<RewardStatisticsModel>();
		list = getJdbcTemplate().query(selectSql,
				new RewardStatisticsModelMapper());
		return list;
	}

	public List<RewardStatisticsModel> getRewardList() {
		String sql = "select * from dw_reward_statistics;";
		logger.debug(" SQL: " + sql);
		List<RewardStatisticsModel> list = new ArrayList<RewardStatisticsModel>();
		list = getJdbcTemplate().query(sql, new RewardStatisticsModelMapper());
		return list;
	}

	public List<RewardStatisticsModel> getRewardStatisticsList(SearchParam param, int start, int end) {
		String sql = "select *, p2.username, us.username as passive_username from dw_reward_statistics as p1 left join dw_user as p2 on p2.user_id = p1.reward_user_id left join dw_user as us on us.user_id = p1.passive_user_id where 1=1 ";

		StringBuffer sb = new StringBuffer(sql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql).append(this.orderSql);
		String selectSql = sb.toString();
		logger.debug("SQL:" + selectSql);
		List<RewardStatisticsModel> list = new ArrayList<RewardStatisticsModel>();
		list = getJdbcTemplate().query(selectSql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RewardStatisticsModelMapper());
		return list;
	}

	public int getCount(SearchParam param) {
		int total = 0;
		String sql = "select count(*) from dw_reward_statistics";
		StringBuffer sb = new StringBuffer(sql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql);
		String selectSql = sb.toString();
		logger.debug("SQL:" + selectSql);
		try {
			total = getJdbcTemplate().queryForInt(selectSql);
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public RewardStatisticsSumModel getSumAccount(long userId,
			String startTime, String endTime, String init) {
		String selectSql = "select u.username,sum(bt.account) as account from dw_borrow_tender as bt left join dw_borrow as b on bt.borrow_id = b.id left join dw_user as u on bt.user_id = u.user_id where bt.user_id = ? and (b.verify_time > ? and b.verify_time < ?) and b.status = 6 ";

		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(init);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		RewardStatisticsSumModel rewardStatistics = new RewardStatisticsSumModel();
		try {
			rewardStatistics = (RewardStatisticsSumModel) getJdbcTemplate()
					.queryForObject(
							sql,
							new Object[] { Long.valueOf(userId), startTime,
									endTime }, new RewardStatisticsSumMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return rewardStatistics;
	}

	public RewardStatistics getRewardStatisticsById(long id) {
		String sql = "select *, u.username, us.username as passive_username from dw_reward_statistics as rs left join dw_user as u on u.user_id = rs.reward_user_id left join dw_user as us on us.user_id = rs.passive_user_id where rs.id = ?";

		logger.debug("SQL: " + sql);
		RewardStatistics r = null;
		try {
			r = (RewardStatistics) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(id) },
					new RewardStatisticsMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return r;
	}

	public User getUserByInviteId(long inviteId, String addTime) {
		String sql = "select * from dw_user where invite_userid = ? and addtime = ?";
		logger.debug("SQL: " + sql);
		User user = null;
		try {
			user = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(inviteId), addTime },
					new UserMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return user;
	}

	public RewardStatistics getByTypePkId(long typePkId) {
		String sql = "select *, u.username, us.username as passive_username from dw_reward_statistics as rs left join dw_user as u on u.user_id = rs.reward_user_id left join dw_user as us on us.user_id = rs.passive_user_id where rs.type_pk_id = ?";

		logger.debug("SQL: " + sql);
		RewardStatistics r = null;
		try {
			r = (RewardStatistics) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(typePkId) },
					new RewardStatisticsMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return r;
	}

	public RewardStatistics getRewardByPassiveId(long rewardUserId,
			long passiveUserId) {
		String sql = "select * from dw_reward_statistics where reward_user_id = ? and passive_user_id = ?";
		logger.debug("SQL: " + sql);
		RewardStatistics r = null;
		try {
			r = (RewardStatistics) getJdbcTemplate().queryForObject(
					sql,
					new Object[] { Long.valueOf(rewardUserId),
							Long.valueOf(passiveUserId) },
					new RewardStatisticsMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return r;
	}

	public RewardStatistics getRewardByTypeFkId(byte type, long typeFkId) {
		String sql = "select * from dw_reward_statistics where type = ?, type_fk_id = ?";
		logger.debug("SQL: " + sql);
		RewardStatistics r = null;
		try {
			r = (RewardStatistics) getJdbcTemplate()
					.queryForObject(
							sql,
							new Object[] { Byte.valueOf(type),
									Long.valueOf(typeFkId) },
							new RewardStatisticsMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return r;
	}
}