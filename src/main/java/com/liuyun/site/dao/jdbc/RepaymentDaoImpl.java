package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.RepaymentDao;
import com.liuyun.site.dao.jdbc.mapper.FastExpireMapper;
import com.liuyun.site.dao.jdbc.mapper.RepaymentBorrowMapper;
import com.liuyun.site.dao.jdbc.mapper.RepaymentMapper;
import com.liuyun.site.dao.jdbc.mapper.RepaymentUnionBorrowMapper;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.borrow.FastExpireModel;
import com.liuyun.site.util.DateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class RepaymentDaoImpl extends BaseDaoImpl implements RepaymentDao {
	private static Logger logger = Logger.getLogger(RepaymentDaoImpl.class);

	String getRepaymentListSql = "from dw_borrow_repayment as p3,dw_borrow as p1 ,dw_user as p2 where p3.borrow_id=p1.id and p1.user_id=p2.user_id ";

	String getLateListSql = "from dw_borrow_repayment as p3,dw_borrow as p1 ,dw_user as p2 where p3.borrow_id=p1.id and p1.user_id=p2.user_id ";

	String getOverdueListSql = "from dw_borrow_repayment as p3,dw_borrow as p1 ,dw_user as p2 where p3.borrow_id=p1.id and p1.user_id=p2.user_id and p1.status in(3,6,7,8) ";

	public List<Repayment> getRepaymentList(long user_id) {
		String sql = "select  p1.*,p2.name as borrow_name,p2.verify_time,p2.isday,p2.time_limit_day,p2.style as borrow_style, p2.is_mb,p2.is_jin,p2.is_xin,p2.is_flow,p2.is_fast,p2.time_limit,p3.username,p3.user_id,p3.phone,p3.area from dw_borrow_repayment as p1,dw_borrow as p2 ,dw_user as p3 where p1.borrow_id=p2.id and p2.user_id=p3.user_id and (p2.status=3 or p2.status=6 or p2.status=7) and p2.user_id=? order by p1.repayment_time asc";

		logger.debug("SQl:" + sql);
		logger.debug("SQL:" + user_id);
		List<Repayment> list = new ArrayList<Repayment>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new RepaymentUnionBorrowMapper());
		return list;
	}

	public List<Repayment> getRepaymentList(long user_id, long borrowId) {
		String sql = "select  p1.*,p2.name as borrow_name,p2.verify_time,p2.isday,p2.time_limit_day,p2.is_fast,p2.style as borrow_style, p2.time_limit,p3.username,p3.user_id,p3.phone,p3.area from dw_borrow_repayment as p1,dw_borrow as p2 ,dw_user as p3 where p1.borrow_id=p2.id and p2.user_id=p3.user_id and (p2.status=3 or p2.status=6 or p2.status=7 or p2.status=8) and p2.user_id=? and p2.id=?";

		logger.debug("SQl:" + sql);
		logger.debug("SQL:" + user_id);
		logger.debug("SQL:" + borrowId);
		List<Repayment> list = new ArrayList<Repayment>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id), Long.valueOf(borrowId) },
				new RepaymentUnionBorrowMapper());
		return list;
	}

	public Repayment getRepayment(long repay_id) {
		String sql = "select * from dw_borrow_repayment where id=?";
		logger.debug("getRepayment():" + sql);
		logger.debug("id:" + repay_id);
		Repayment r = null;
		try {
			r = (Repayment) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(repay_id) },
					new RepaymentMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
		}
		return r;
	}

	public Repayment getRepayment(int order, long borrow_id) {
		String sql = "select * from dw_borrow_repayment where `order`=? and borrow_id=?";
		logger.debug("getRepayment():" + sql);
		logger.debug("order:" + order);
		logger.debug("borrow_id:" + borrow_id);
		Repayment r = null;
		try {
			r = (Repayment) getJdbcTemplate().queryForObject(
					sql,
					new Object[] { Integer.valueOf(order), Long.valueOf(borrow_id) }, new RepaymentMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
		}
		return r;
	}

	public void addBatchRepayment(Repayment[] repays) {
		String sql = "insert into dw_borrow_repayment(borrow_id,`order`,status,repayment_time,repayment_account,interest,capital,addtime) values(?,?,?,?,?,?,?,?)";

		List<Object[]> list = new ArrayList<Object[]>();
		Object[] arg = null;
		for (int i = 0; i < repays.length; ++i) {
			arg = new Object[] { Long.valueOf(repays[i].getBorrow_id()),
					Integer.valueOf(repays[i].getOrder()),
					Integer.valueOf(repays[i].getStatus()),
					repays[i].getRepayment_time(),
					repays[i].getRepayment_account(), repays[i].getInterest(),
					repays[i].getCapital(), repays[i].getAddtime() };
			list.add(arg);
		}
		getJdbcTemplate().batchUpdate(sql, list);
	}

	public void modifyRepayment(Repayment repay) {
		String sql = "update dw_borrow_repayment set webstatus=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(repay.getWebstatus()),
						Long.valueOf(repay.getId()) });
	}

	public void modifyRepaymentYes(Repayment repay) {
		String sql = "update dw_borrow_repayment set status=?,webstatus=?,repayment_yestime=?,repayment_yesaccount=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(repay.getStatus()),
						Integer.valueOf(repay.getWebstatus()),
						repay.getRepayment_yestime(),
						repay.getRepayment_yesaccount(),
						Long.valueOf(repay.getId()) });
	}

	public void modifyRepaymentBonus(Repayment repay) {
		String sql = "update dw_borrow_repayment set repayment_account=?,bonus=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { repay.getRepayment_account(), repay.getBonus(),
						Long.valueOf(repay.getId()) });
	}

	public List<Repayment> getRepaymentList(SearchParam param, int start, int pernum) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.*,p1.style as borrow_style,p2.username,p2.user_id,p2.phone,p2.area ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getRepaymentListSql).append(param.getSearchParamSql()).append(" order by p3.repayment_time  ").append(" limit ?,?");
		logger.debug("getRepaymentList() SQL:" + sb.toString());
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(
					sb.toString(),
					new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) },
					new RepaymentUnionBorrowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getRepaymentCount(SearchParam param) {
		String selectSql = "select count(1)";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getRepaymentListSql).append(param.getSearchParamSql());
		logger.debug("getRepaymentCount() SQL:" + sb.toString());
		int total = 0;
		total = count(sb.toString());
		return total;
	}

	public List<Repayment> getLateList(SearchParam param, String nowTime, int start, int pernum) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.is_fast,p1.style AS borrow_style,p1.is_xin,p1.is_mb,p1.is_flow,p1.is_jin,p1.time_limit,p1.time_limit_day,p1.isday,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area ";
		String lateSql = " and p3.repayment_time+0<? AND p1.is_mb<>1 AND (p3.repayment_yestime>p3.repayment_time OR p3.repayment_yestime IS NULL)";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(param.getSearchParamSql()).append(lateSql).append(" limit ?,?");
		logger.debug("getLateList():" + sb.toString());
		logger.debug("nowTime:" + nowTime);
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(
					sb.toString(),
					new Object[] { nowTime, Integer.valueOf(start), Integer.valueOf(pernum) },
					new RepaymentUnionBorrowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public int getLateCount(SearchParam param, String nowTime) {
		String selectSql = "select count(1) ";
		String lateSql = " and p3.repayment_time+0<? AND (p3.repayment_yestime>p3.repayment_time OR p3.repayment_yestime IS NULL)";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(param.getSearchParamSql())
				.append(lateSql);
		logger.debug("getLateCount():" + sb.toString());
		logger.debug("nowTime:" + nowTime);
		int total = 0;
		total = count(sb.toString(), new Object[] { nowTime });
		return total;
	}

	public boolean hasRepaymentAhead(int orderid, long borrowId) {
		String sql = "select * from dw_borrow_repayment where `order`<? and borrow_id=? and status=0";
		List<Repayment> list = null;
		try {
			list = getJdbcTemplate().query(
					sql,
					new Object[] { Integer.valueOf(orderid),
							Long.valueOf(borrowId) }, new RepaymentMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return (list != null) && (list.size() >= 1);
	}

	public List<Repayment> getRepaymentList(String search, long user_id) {
		String searchsql = getSearchSql(search);
		String sql = "SELECT * FROM dw_borrow_repayment WHERE borrow_id IN(SELECT id FROM dw_borrow WHERE user_id=?)";

		sql = sql + searchsql;
		logger.debug("SQL:" + sql);
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(user_id) },
					new RepaymentMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	private String getSearchSql(String search) {
		String searchSql = " AND repayment_time > repayment_yestime AND STATUS=1";
		if ("lateRepayment".equals(search)) {
			searchSql = " AND repayment_time < repayment_yestime AND STATUS=1";
		} else if ("overdueRepayment".equals(search)) {
			searchSql = " AND late_days <=30 AND late_days>0 AND STATUS=1";
		} else if ("overdueRepayments".equals(search)) {
			searchSql = " AND late_days>30 AND STATUS=1";
		} else if ("overdueNoRepayments".equals(search)) {
			String nowTime = Long.toString(System.currentTimeMillis() / 1000L);
			searchSql = " AND " + nowTime + ">repayment_time AND STATUS=0";
		}
		return searchSql;
	}

	public int getCountRepaid(int user_id) {
		String sql = "select count(*) from dw_borrow where status=8 and user_id="
				+ user_id;
		return count(sql);
	}

	public int getCountExprire(int user_id) {
		return 0;
	}

	public int getOverdueCount(SearchParam param, String nowTime) {
		String selectSql = "select count(1) ";

		String overdueSql = " and ((p3.repayment_yestime is null and p3.repayment_time<"
				+ nowTime
				+ ") or (p3.repayment_yestime is not null and p3.repayment_yestime>p3.repayment_time)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getOverdueListSql).append(param.getSearchParamSql())
				.append(overdueSql);
		logger.debug("getOverdueCount():" + sb.toString());
		logger.debug("nowTime:" + nowTime);
		int total = 0;
		total = count(sb.toString());
		logger.debug("getOverdueCount" + total);
		return total;
	}

	public List<Repayment> getOverdueList(SearchParam param, String nowTime, int start,
			int pernum) {
		String selectSql = "select  p3.*,p1.is_mb,p1.is_jin,p1.is_fast,p1.is_flow,p1.is_xin,p1.is_offvouch,p1.is_pledge,p1.style as borrow_style,p1.name as borrow_name,p1.isday,p1.time_limit,p1.time_limit_day,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area,(("
				+ nowTime
				+ "-p3.repayment_time)/(24*60*60)) as unRepayTimeOverdue,((p3.repayment_yestime-p3.repayment_time)/(24*60*60)) as OverdueTime,p1.account ";

		String overdueSql = " and ((p3.repayment_yestime is null and p3.repayment_time<"
				+ nowTime
				+ ") or (p3.repayment_yestime is not null and p3.repayment_yestime>p3.repayment_time)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getOverdueListSql).append(param.getSearchParamSql())
				.append(overdueSql).append(
						" order by p3.repayment_time asc limit ?,? ");
		logger.debug("getOverdueList():" + sb.toString());
		logger.debug("nowTime:" + nowTime);
		List<Repayment> list = new ArrayList<Repayment>();
		list = getJdbcTemplate()
				.query(
						sb.toString(),
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new RepaymentBorrowMapper());
		return list;
	}

	public List<Repayment> getOverdueList(SearchParam param, String nowTime) {
		String selectSql = "select  p3.*,p1.is_mb,p1.is_jin,p1.is_fast,p1.is_flow,p1.is_xin,p1.is_offvouch,p1.is_pledge,p1.style as borrow_style,p1.name as borrow_name,p1.isday,p1.time_limit,p1.time_limit_day,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area,(("
				+ nowTime
				+ "-p3.repayment_time)/(24*60*60)) as unRepayTimeOverdue,((p3.repayment_yestime-p3.repayment_time)/(24*60*60)) as OverdueTime,p1.account ";

		String overdueSql = " and ((p3.repayment_yestime is null and p3.repayment_time<"
				+ nowTime
				+ ") or (p3.repayment_yestime is not null and p3.repayment_yestime>p3.repayment_time)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getOverdueListSql).append(param.getSearchParamSql())
				.append(overdueSql).append(" order by p3.repayment_time asc");
		logger.debug("getOverdueList():" + sb.toString());
		logger.debug("nowTime:" + nowTime);
		List<Repayment> list = new ArrayList<Repayment>();
		list = getJdbcTemplate().query(sb.toString(), new Object[0],
				new RepaymentBorrowMapper());
		return list;
	}

	public List<Repayment> getRepaymentList(SearchParam param) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.*,p1.style as borrow_style,p2.username,p2.user_id,p2.phone,p2.area ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getRepaymentListSql).append(param.getSearchParamSql()).append(" order by p3.repayment_time  ");
		logger.debug("getRepaymentList() SQL:" + sb.toString());
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(sb.toString(), new Object[0], new RepaymentUnionBorrowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public double getRepaymentSum(SearchParam param) {
		String selectSql = "select sum(p3.repayment_account) as sum ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getRepaymentListSql).append(param.getSearchParamSql());
		logger.debug("getRepaymentSum() SQL:" + sb.toString());
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sb.toString());
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("sum");
		}
		return sum;
	}

	public List<Repayment> getAllRepaymentList(int status) {
		String sql = "select * from dw_borrow_repayment where webstatus=? and status!=1";
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Integer.valueOf(status) },
					new RepaymentMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Repayment> getAllRepaymentList(int webstatus, long start, long end) {
		String sql = "select * from dw_borrow_repayment where webstatus=? and status!=1 and repayment_time>? and repayment_time<?";
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(
					sql,
					new Object[] { Integer.valueOf(webstatus),
							Long.valueOf(start), Long.valueOf(end) },
					new RepaymentMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void modifyRepaymentStatus(long id, int status, int webstatus) {
		String sql = "update dw_borrow_repayment set status=?,webstatus=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(status),
						Integer.valueOf(webstatus), Long.valueOf(id) });
	}

	public int modifyRepaymentStatusWithCheck(long id, int status, int webstatus) {
		String sql = "update dw_borrow_repayment set status=?,webstatus=? where id=? and webstatus=0";
		int c = 0;
		c = getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(status),
						Integer.valueOf(webstatus), Long.valueOf(id) });
		return c;
	}

	public List<Map<String, Object>> getToPayRepayMent() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MIN(a.repayment_time) repayment_time, a.borrow_id, b.user_id,  b.name,a.order,a.repayment_account  FROM dw_borrow_repayment a ");
		sql.append(" INNER JOIN dw_borrow b on a.borrow_id = b.id ");
		sql.append(" where  a.status = 0 AND a.webstatus = 0  AND a.repayment_yestime is NULL GROUP BY a.borrow_id ");
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
		return list;
	}

	public List<Map<String, Object>> getLateList() {
		String sql = "select id,repayment_time,borrow_id from dw_borrow_repayment where status = 0 and  repayment_yestime is null and  repayment_time+0<?";
		logger.info("查找所有逾期未还的标" + sql);
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, new Object[] { DateUtils.getNowTimeStr() });
		return list;
	}

	public double getSumLateMoney(long id) {
		String sql = "select sum(capital) capital from dw_borrow_repayment  where borrow_id  =?  and status = 0 ";
		logger.info("根据标的id查找还款计划表中逾期信息" + sql);
		double sumMoney = ((Double) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, Double.class)).doubleValue();

		return sumMoney;
	}

	public void updateLaterepayment(long lateDay, long id, double late_interest) {
		String sql = "update dw_borrow_repayment set late_days=?,late_interest=? where id=? ";
		logger.info("更改还款计划表中逾期信息" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(lateDay),
						Double.valueOf(late_interest), Long.valueOf(id) });
	}

	public List<Repayment> getLateRepaymentByBorrowid(long borrow_id) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.is_fast,p1.style AS borrow_style,p1.is_xin,p1.is_mb,p1.is_flow,p1.is_jin,p1.time_limit,p1.time_limit_day,p1.isday,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area ";
		String lateSql = " and p3.repayment_time+0<? AND p3.repayment_yestime IS NULL AND p3.borrow_id=?";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(lateSql);
		logger.debug("getLateList():" + sb.toString());
		logger.debug("nowTime:" + DateUtils.getNowTimeStr());
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			list = getJdbcTemplate().query(
					sb.toString(),
					new Object[] { DateUtils.getNowTimeStr(), Long.valueOf(borrow_id) },
					new RepaymentUnionBorrowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public double getAllSumLateMoney(int status) {
		String sql = "select sum(capital) as sum from dw_borrow_repayment where status=?";
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Integer.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("sum");
		}
		return sum;
	}

	public List<Map<String, Object>> getFastExpireList(SearchParam param, int start, int pernum) {
		String selectSql = "select p3.*,p1.id as borrow_id,p2.username as borrow_user,p1.name as borrow_name ";
		String fastSql = " and p1.is_mb<>1 and p1.is_flow<>1 and ((p3.repayment_time>=? and p3.repayment_time>p3.repayment_yestime) or ((p3.repayment_yestime is null and p3.repayment_time<?) or (p3.repayment_yestime is not null and p3.repayment_time<p3.repayment_yestime)))";

		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(fastSql).append(param.getSearchParamSql()).append(" limit ?,?");
		logger.debug("getFastExpireList:" + sb.toString());
		logger.debug("nowTime:" + DateUtils.getNowTimeStr());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = getJdbcTemplate().queryForList(
					sb.toString(),
					new Object[] { DateUtils.getNowTimeStr(),
							DateUtils.getNowTimeStr(), Integer.valueOf(start),
							Integer.valueOf(pernum) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getFastExpireCount(SearchParam param) {
		String selectSql = "select count(1)";
		String fastSql = " and p1.is_mb<>1 and p1.is_flow<>1 and ((p3.repayment_time>=? and p3.repayment_time>p3.repayment_yestime) or ((p3.repayment_yestime is null and p3.repayment_time<?) or (p3.repayment_yestime is not null and p3.repayment_time<p3.repayment_yestime)))";

		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(fastSql).append(
				param.getSearchParamSql());
		logger.debug("getFastExpireCount:" + sb.toString());
		int count = 0;
		try {
			count = count(sb.toString(), new Object[] {
					DateUtils.getNowTimeStr(), DateUtils.getNowTimeStr() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<FastExpireModel> getFastExpireList(SearchParam param) {
		String selectSql = "select p3.*,p1.id as borrow_id,p2.username as borrow_user,p1.name as borrow_name ";
		String fastSql = " and p1.is_mb<>1 and p1.is_flow<>1 and ((p3.repayment_time>=? and p3.repayment_time>p3.repayment_yestime) or ((p3.repayment_yestime is null and p3.repayment_time<?) or (p3.repayment_yestime is not null and p3.repayment_time<p3.repayment_yestime)))";

		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(fastSql).append(
				param.getSearchParamSql());
		logger.debug("getFastExpireList:" + sb.toString());
		logger.debug("nowTime:" + DateUtils.getNowTimeStr());
		List<FastExpireModel> list = new ArrayList<FastExpireModel>();
		try {
			list = getJdbcTemplate()
					.query(
							sb.toString(),
							new Object[] { DateUtils.getNowTimeStr(),
									DateUtils.getNowTimeStr() },
							new FastExpireMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public double getAllLateSumWithNoRepaid() {
		String selectSql = "select sum(p3.repayment_account) as sum ";
		String lateSql = " and p1.is_mb<>1 and p3.status=0 and p3.repayment_yestime is null and p3.repayment_time<?";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(lateSql);
		logger.debug("getAllLateSumWithNoRepaid:" + sb.toString());
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sb.toString(),
				new Object[] { DateUtils.getNowTimeStr() });
		if (rs.next()) {
			total = rs.getDouble("sum");
		}
		return total;
	}

	public double getAllLateSumWithYesRepaid() {
		String selectSql = "select sum(p3.repayment_account) as sum ";
		String lateSql = " and p1.is_mb<>1 and p3.status=1 and p3.repayment_yestime is not null and p3.repayment_time<p3.repayment_yestime";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getLateListSql).append(lateSql);
		logger.debug("getAllLateSumWithYesRepaid:" + sb.toString());
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sb.toString(),
				new Object[0]);
		if (rs.next()) {
			total = rs.getDouble("sum");
		}
		return total;
	}
}