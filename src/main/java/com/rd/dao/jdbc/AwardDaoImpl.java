package com.rd.dao.jdbc;

import com.rd.dao.AwardDao;
import com.rd.dao.jdbc.mapper.ObjAwardMapper;
import com.rd.dao.jdbc.mapper.RuleAwardMapper;
import com.rd.dao.jdbc.mapper.UserAwardMapper;
import com.rd.domain.ObjAward;
import com.rd.domain.RuleAward;
import com.rd.domain.UserAward;
import com.rd.model.SearchParam;
import com.rd.util.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class AwardDaoImpl extends BaseDaoImpl implements AwardDao {
	private static Logger logger = Logger.getLogger(AwardDaoImpl.class);

	public RuleAward getRuleAwardById(long ruleId) {
		String sql = "select * from dw_rule_award where id=?";
		logger.debug("SQL:" + sql);
		logger.debug("ruleId:" + ruleId);
		RuleAward ruleAward = null;
		try {
			ruleAward = (RuleAward) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(ruleId) },
					new RuleAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return ruleAward;
	}

	public long getRuleIdByAwardType(int awardType) {
		String currentTime = DateUtils.dateStr2(new Date());
		String sql = "select t.id from dw_rule_award t where t.award_type=? and ? between t.start_date and t.end_date";
		return getJdbcTemplate().queryForLong(sql,
				new Object[] { Integer.valueOf(awardType), currentTime });
	}

	public RuleAward getRuleAwardByAwardType(int awardType) {
		String sql = "select * from dw_rule_award t where t.award_type=? ";
		logger.debug("SQL:" + sql);
		logger.debug("awardType:" + awardType);
		RuleAward ruleAward = null;
		try {
			ruleAward = (RuleAward) getJdbcTemplate().queryForObject(sql,
					new Object[] { Integer.valueOf(awardType) },
					new RuleAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return ruleAward;
	}

	public List<RuleAward> getRuleAwardList() {
		String sql = "select * from dw_rule_award";
		List<RuleAward> list = new ArrayList<RuleAward>();
		try {
			list = getJdbcTemplate().query(sql, new RuleAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return list;
	}

	public void updateRuleAwardById(RuleAward ruleAward) {
		String sql = "update dw_rule_award set name=?,start_date=?,end_date=?,award_type=?,time_limit=?,max_times=?,base_point=?,money_limit=?,total_money=?,bestow_money=?,is_absolute=?,msg_type=?,context=?,subject=?,content=?,back_type=? where id=?";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { ruleAward.getName(), ruleAward.getStart_date(),
						ruleAward.getEnd_date(),
						Integer.valueOf(ruleAward.getAward_type()),
						Integer.valueOf(ruleAward.getTime_limit()),
						Integer.valueOf(ruleAward.getMax_times()),
						Integer.valueOf(ruleAward.getBase_point()),
						Integer.valueOf(ruleAward.getMoney_limit()),
						Double.valueOf(ruleAward.getTotal_money()),
						Double.valueOf(ruleAward.getBestow_money()),
						Integer.valueOf(ruleAward.getIs_absolute()),
						Integer.valueOf(ruleAward.getMsg_type()),
						ruleAward.getContext(), ruleAward.getSubject(),
						ruleAward.getContent(),
						Integer.valueOf(ruleAward.getBack_type()),
						Long.valueOf(ruleAward.getId()) });
	}

	public void updateBestowMoney(long ruleId, double money) {
		String sql = "update dw_rule_award set bestow_money=ifnull(bestow_money,0)+? where total_money>bestow_money and id=?";
		logger.debug("SQL:" + sql);
		logger.debug("ruleId:" + ruleId);
		logger.debug("bestow_money:" + money);
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(money), Long.valueOf(ruleId) });
	}

	public void updateTotalMoney(long ruleId, double money) {
		String sql = "update dw_rule_award set total_money=? where id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(money), Long.valueOf(ruleId) });
	}

	public void addRuleAward(RuleAward ruleAward) {
		String sql = "insert into dw_rule_award(id,name,start_date,end_date,award_type,time_limit,max_times,base_point,money_limit,total_money,bestow_money,is_absolute,msg_type,context,subject,content,back_type,addtime,addip)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(ruleAward.getId()),
						ruleAward.getName(), ruleAward.getStart_date(),
						ruleAward.getEnd_date(),
						Integer.valueOf(ruleAward.getAward_type()),
						Integer.valueOf(ruleAward.getTime_limit()),
						Integer.valueOf(ruleAward.getMax_times()),
						Integer.valueOf(ruleAward.getBase_point()),
						Integer.valueOf(ruleAward.getMoney_limit()),
						Double.valueOf(ruleAward.getTotal_money()),
						Double.valueOf(ruleAward.getBestow_money()),
						Integer.valueOf(ruleAward.getIs_absolute()),
						Integer.valueOf(ruleAward.getMsg_type()),
						ruleAward.getContext(), ruleAward.getSubject(),
						ruleAward.getContent(),
						Integer.valueOf(ruleAward.getBack_type()),
						ruleAward.getAddtime(), ruleAward.getAddip() });
	}

	public List<ObjAward> getObjectAwardListByRuleId(long ruleId) {
		String sql = "select * from dw_obj_award where rule_id=? order by rate";
		List<ObjAward> list = new ArrayList<ObjAward>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(ruleId) }, new ObjAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return list;
	}

	public ObjAward getObjectAwardById(long awardId) {
		String sql = "select * from dw_obj_award where id=?";
		logger.debug("SQL:" + sql);
		logger.debug("id:" + awardId);
		ObjAward objAward = null;
		try {
			objAward = (ObjAward) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(awardId) },
					new ObjAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return objAward;
	}

	public void updateObjAward(ObjAward data) {
		String sql = "update dw_obj_award set name=?,level=?,rate=?,point_limit=?,total=?,award_limit=?,description=?,ratio=?,obj_value=?,pic_url=?,object_rule=? where id=?";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { data.getName(),
						Integer.valueOf(data.getLevel()),
						Integer.valueOf(data.getRate()),
						Long.valueOf(data.getPoint_limit()),
						Long.valueOf(data.getTotal()),
						Integer.valueOf(data.getAward_limit()),
						data.getDescription(), Double.valueOf(data.getRatio()),
						Long.valueOf(data.getObj_value()), data.getPic_url(),
						data.getObject_rule(), Long.valueOf(data.getId()) });
	}

	public void updateBestow(long ruleId, long awardId) {
		String sql = "update dw_obj_award set bestow=ifnull(bestow,0)+1 where total>bestow and id=? and rule_id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(awardId), Long.valueOf(ruleId) });
	}

	public void addObjAward(ObjAward data) {
		String sql = "insert into dw_obj_award(id,name,rule_id,level,rate,point_limit,bestow,total,award_limit,description,ratio,obj_value,pic_url,object_rule,addtime,addip)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(data.getId()), data.getName(),
						Long.valueOf(data.getRule_id()),
						Integer.valueOf(data.getLevel()),
						Integer.valueOf(data.getRate()),
						Long.valueOf(data.getPoint_limit()),
						Long.valueOf(data.getBestow()),
						Long.valueOf(data.getTotal()),
						Integer.valueOf(data.getAward_limit()),
						data.getDescription(), Double.valueOf(data.getRatio()),
						Long.valueOf(data.getObj_value()), data.getPic_url(),
						data.getObject_rule(), data.getAddtime(),
						data.getAddip() });
	}

	public List<UserAward> getAwardeeList(long ruleId, int num,
			boolean isOrderByLevel) {
		String sql = "";
		if (isOrderByLevel)
			sql = "select * from dw_user_award where status=1 and rule_id=? order by level asc,addtime desc limit ?";
		else {
			sql = "select * from dw_user_award where status=1 and rule_id=? order by addtime desc limit ?";
		}
		List<UserAward> list = new ArrayList<UserAward>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(ruleId), Integer.valueOf(num) }, new UserAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return list;
	}

	public int getUserAwardDayCnt(long ruleId, long userId) {
		String sql = "select count(1) from dw_user_award where rule_id=? and user_id=? and addtime>"
				+ DateUtils.getIntegralTime().getTime() / 1000L
				+ " and addtime<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L;
		int count = getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(ruleId), Long.valueOf(userId) });
		return count;
	}

	public int getUserAwardTotalCnt(long ruleId, long userId) {
		String sql = "select count(1) from dw_user_award where rule_id=? and user_id=?";
		int count = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(ruleId), Long.valueOf(userId) });
		return count;
	}

	public void addUserAward(UserAward data) {
		String sql = "insert into dw_user_award(id,user_id,user_name,level,award_id,point_reduce,rule_id,award_name,status,receive_status,addtime,addip)values(?,?,?,?,?,?,?,?,?,?,?,null)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(data.getId()),
						Long.valueOf(data.getUser_id()), data.getUser_name(),
						Integer.valueOf(data.getLevel()),
						Long.valueOf(data.getAward_id()),
						Long.valueOf(data.getPoint_reduce()),
						Long.valueOf(data.getRule_id()), data.getAward_name(),
						Integer.valueOf(data.getStatus()),
						Integer.valueOf(data.getReceive_status()),
						data.getAddtime() });
	}

	public List<UserAward> getUserAwardList(int start, int end,
			SearchParam param) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from dw_user_award p1 where 1=1 ");
		sbSql.append(param.getSearchParamSql());
		sbSql.append(" order by p1.addtime desc ");
		sbSql.append("limit ?,?");
		logger.debug("SQL:" + sbSql.toString());
		List<UserAward> list = new ArrayList<UserAward>();
		list = getJdbcTemplate().query(sbSql.toString(),
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new UserAwardMapper());
		return list;
	}

	public List<UserAward> getAllUserAwardList(SearchParam param) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from dw_user_award p1 where 1=1 ");
		sbSql.append(param.getSearchParamSql());
		sbSql.append(" order by p1.addtime desc ");
		logger.debug("SQL:" + sbSql.toString());
		List<UserAward> list = new ArrayList<UserAward>();
		list = getJdbcTemplate().query(sbSql.toString(), new Object[0],
				new UserAwardMapper());
		return list;
	}

	public int getUserAwardCount(SearchParam param) {
		String sql = "select count(1) from dw_user_award p1 where 1=1 ";
		sql = sql + param.getSearchParamSql();
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql, new Object[0]);
		return count;
	}

	public List<UserAward> getMyAwardList(long ruleId, long userId) {
		String sql = "select * from dw_user_award where status=1 and rule_id=? and user_id=? order by addtime desc";
		List<UserAward> list = new ArrayList<UserAward>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(ruleId), Long.valueOf(userId) }, new UserAwardMapper());
		} catch (Exception e) {
			logger.info("查询结果为空！");
		}
		return list;
	}
}