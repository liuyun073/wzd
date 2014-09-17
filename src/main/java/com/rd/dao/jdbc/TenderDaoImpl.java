package com.rd.dao.jdbc;

import com.rd.dao.TenderDao;
import com.rd.dao.jdbc.mapper.BorrowNTenderMapper;
import com.rd.dao.jdbc.mapper.BorrowNewTenderMapper;
import com.rd.dao.jdbc.mapper.BorrowTenderMapper;
import com.rd.dao.jdbc.mapper.DetailTenderMapper;
import com.rd.domain.Tender;
import com.rd.domain.TenderAwardYesAndNo;
import com.rd.model.BorrowNTender;
import com.rd.model.BorrowTender;
import com.rd.model.DetailTender;
import com.rd.model.RankModel;
import com.rd.model.SearchParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TenderDaoImpl extends BaseDaoImpl implements TenderDao {
	private static Logger logger = Logger.getLogger(TenderDaoImpl.class);

	private String queryAllTenderSql = "SELECT p1.*,p2.username,p3.name as borrowname FROM dw_borrow_tender AS p1 LEFT JOIN dw_user AS p2 ON p1.user_id=p2.user_id LEFT JOIN dw_borrow AS p3 ON p1.borrow_id=p3.id WHERE 1=1 ";

	private String queryAllTenderCountSql = "SELECT count(1) FROM dw_borrow_tender AS p1 LEFT JOIN dw_user AS p2 ON p1.user_id=p2.user_id LEFT JOIN dw_borrow AS p3 ON p1.borrow_id=p3.id WHERE 1=1 ";

	private String queryTenderByBorrowidSql = "select t.*,collection.repay_time as repay_time,collection.repay_account as repay_account,u.username,borrow.part_account AS part_account from dw_borrow_tender t left join dw_borrow_collection collection on t.id=collection.tender_id left join dw_user u on u.user_id=t.user_id left join dw_borrow borrow on t.borrow_id = borrow.id where t.borrow_id=? ";

	//private String add = "select t.*,u.username from dw_borrow_tender t left join dw_user u on u.user_id=t.user_id left join dw_borrow b on b.user_id=u.id where t.borrow_id=? ";

	String queryTenderCountByBorrowidSql = "select count(1) from dw_borrow_tender t left join dw_user u on u.user_id=t.user_id where t.borrow_id=? ";

	String secsql = "SELECT * FROM dw_borrow_tender a inner JOIN dw_user c ON a.user_id=c.user_id inner JOIN dw_borrow b ON a.borrow_id=b.id ";

	public List<BorrowTender> getTenderListByBorrowid(long id) {
		String groupSql = " GROUP BY t.id ";
		String sql = this.queryTenderByBorrowidSql + groupSql + " order by t.addtime desc";
		logger.debug("SQL:" + sql);
		List<BorrowTender> tenders = null;
		try {
			tenders = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(id) }, new BorrowTenderMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return tenders;
	}

	public double getTenderListByUserid(long user_id, String starttime,
			String endtime) {
		String queryTenderByBorrowidSql = "select sum(t.money) as money from dw_borrow_tender t left join dw_user u on u.user_id=t.user_id left join dw_borrow b on b.id=t.borrow_id where t.user_id=? and t.addtime>? and t.addtime<? and (b.is_flow=1 or b.is_fast=1 or b.is_xin=1 ) and b.isday!=1";

		String groupSql = " GROUP BY t.id ";
		String sql = queryTenderByBorrowidSql + groupSql + " order by t.addtime desc";
		logger.debug("SQL:" + sql);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id), starttime, endtime });
		if (rs.next()) {
			total = rs.getDouble("money");
		}
		return total;
	}

	public List<BorrowTender> getTenderListByBorrowid(long id, int num) {
		logger.debug("SQL:" + this.queryTenderByBorrowidSql);
		logger.debug("SQL:" + id);
		String groupSql = " GROUP BY t.id ";
		String sql = this.queryTenderByBorrowidSql + groupSql + " order by t.addtime desc" + " limit 0,?";
		List<BorrowTender> tenders = null;
		try {
			tenders = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(id), Integer.valueOf(num) },
					new BorrowTenderMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return tenders;
	}

	public List<BorrowTender> getTenderListByBorrow(long id) {
		logger.debug("SQL:" + this.queryTenderByBorrowidSql);
		logger.debug("SQL:" + id);
		String groupSql = " GROUP BY t.id ";
		List<BorrowTender> tenders = null;
		String querystring = this.queryTenderByBorrowidSql + groupSql;
		try {
			tenders = getJdbcTemplate().query(querystring, new Object[] { Long.valueOf(id) }, new BorrowTenderMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return tenders;
	}

	public List<BorrowTender> getTenderListByBorrow(long id, long user_id) {
		logger.debug("SQL:" + this.queryTenderByBorrowidSql);
		logger.debug("SQL:" + id);
		String groupSql = " GROUP BY t.id ";
		List<BorrowTender> tenders = null;
		String querystring = this.queryTenderByBorrowidSql + " and t.user_id=?" + groupSql;
		try {
			tenders = getJdbcTemplate().query(querystring,
					new Object[] { Long.valueOf(id), Long.valueOf(user_id) },
					new BorrowTenderMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return tenders;
	}

	public List<BorrowTender> getTenderListByBorrowid(long id, int start, int pernum,
			SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + pernum;
		String orderSql = param.getOrderSql();
		String groupSql = " GROUP BY t.id ";
		String sql = this.queryTenderByBorrowidSql + searchSql + groupSql + orderSql + limitSql;
		logger.debug("SQL:" + this.queryTenderByBorrowidSql);
		logger.debug("SQL:" + id);
		List<BorrowTender> tenders = null;
		try {
			tenders = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(id) }, new BorrowTenderMapper());
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
			e.printStackTrace();
		}
		return tenders;
	}

	public int getTenderCountByBorrowid(long id, SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String sql = this.queryTenderCountByBorrowidSql + searchSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + id);
		int total = 0;
		try {
			total = count(sql, new Object[] { Long.valueOf(id) });
		} catch (DataAccessException e) {
			logger.error("getTenderCountByBorrowid():" + e.getMessage());
		}
		return total;
	}

	public Tender addTender(Tender t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_borrow_tender(user_id,status,borrow_id,money,account,addtime,auto_repurchase,is_auto_tender)values(?,?,?,?,?,?,?,?)";

		final Tender tender = t;

		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, tender.getUser_id());
				ps.setInt(2, tender.getStatus());
				ps.setLong(3, tender.getBorrow_id());
				ps.setString(4, tender.getMoney());
				ps.setString(5, tender.getAccount());
				ps.setString(6, tender.getAddtime());
				ps.setInt(7, tender.getAuto_repurchase());
				ps.setInt(8, tender.getIs_auto_tender());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		t.setId(key);
		return t;
	}

	public Tender modifyTender(Tender tender) {
		String sql = "update dw_borrow_tender set repayment_account=?,wait_account =?,interest =?,wait_interest = ? where id=?";

		int ret = getJdbcTemplate().update(
						sql,
						new Object[] { tender.getRepayment_account(),
								tender.getWait_account(), tender.getInterest(),
								tender.getWait_interest(),
								Long.valueOf(tender.getId()) });
		logger.debug("sql=====" + sql);
		if (ret < 1)
			return null;
		return tender;
	}

	public List<DetailTender> getInvestTenderListByUserid(long user_id) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p3.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=? order by p1.addtime desc;";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new DetailTenderMapper());
		return list;
	}

	public List<DetailTender> getInvestTenderingListByUserid(long user_id) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p3.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=? and p3.status=1 order by p1.addtime desc;";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new DetailTenderMapper());
		return list;
	}

	public List<DetailTender> getInvestTenderListByUserid(long user_id, int start, int end,
			SearchParam param) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p1.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=?  and ((p3.status =1 and p3.is_flow=1) or p3.status in (3,6,7,8)) ";

		String orderSql = "order by p1.addtime desc";
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new DetailTenderMapper());
		return list;
	}

	public int getInvestTenderCountByUserid(long user_id, SearchParam param) {
		String sql = "select count(p1.id) from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p1.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=?  and ((p3.status =1 and p3.is_flow=1) or p3.status in (3,6,7,8)) ";

		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public List<DetailTender> getInvestTenderingListByUserid(long user_id, int start,
			int end, SearchParam param) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p3.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=? and p3.status=1 ";

		String orderSql = "order by p1.addtime desc";
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit ?,?";
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(user_id), Integer.valueOf(start),
						Integer.valueOf(end) }, new DetailTenderMapper());
		return list;
	}

	public int getInvestTenderingCountByUserid(long user_id, SearchParam param) {
		String sql = "select count(1) from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p3.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=? and p3.status=1 ";

		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public List<DetailTender> getSuccessTenderList(long user_id) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p1.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p3.status in (3,6,7) and p1.user_id=? order by p1.addtime desc;";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new DetailTenderMapper());
		return list;
	}

	public List<DetailTender> getSuccessTenderList(long user_id, int start, int end,
			SearchParam param) {
		String sql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p2.username as op_username,p4.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p3.user_id = p2.user_id left join dw_user as p4 on p4.user_id = p1.user_id left join dw_credit as p5 on p1.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p1.user_id=? ";

		String orderSql = "order by p1.addtime desc";
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new DetailTenderMapper());
		return list;
	}

	public double getTotalTenderByUserid(long userid) {
		String sql = "select sum(account) as total from dw_borrow_tender where status=1 and user_id=?";
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(userid) });
		if (rs.next()) {
			total = rs.getDouble("total");
		}
		return total;
	}

	public int getTotalTenderTimesByUserid(long userid) {
		String sql = "select count(*) from dw_borrow_tender where status=1 and user_id=?";
		int times = 0;
		try {
			times = getJdbcTemplate().queryForInt(sql,
					new Object[] { Long.valueOf(userid) });
		} catch (DataAccessException e) {
			logger.debug("getTotalTenderByUserid():" + e.getMessage());
		}
		return times;
	}

	public List<RankModel> getRankListByTime(String startTime, String endTime) {
		long s = System.currentTimeMillis();
		String selSql = "select u.username,sum(p1.account) as tenderMoney from dw_borrow_tender p1 left join dw_borrow b on b.id=p1.borrow_id left join dw_user u on u.user_id=p1.user_id where ((b.status in(3,6,7,8) and b.is_flow<>1) or (b.status in(1,3,6,7,8) and b.is_flow=1))";

		String paramSql = " group by u.username order by tenderMoney desc";
		StringBuffer sb = new StringBuffer();
		sb.append(selSql).append(" and p1.addtime>=? and p1.addtime<=? ").append(paramSql).append(" limit 0,10");
		String sql = sb.toString();
		logger.debug("getChartsList()" + sql);
		List<RankModel> list = new ArrayList<RankModel>();
		list = getJdbcTemplate().query(sql,
				new Object[] { startTime, endTime }, new RowMapper<RankModel>() {
					public RankModel mapRow(ResultSet rs, int num)
							throws SQLException {
						RankModel r = new RankModel();
						r.setUsername(rs.getString("username"));
						r.setTenderMoney(rs.getDouble("tenderMoney"));
						return r;
					}
				});
		long e = System.currentTimeMillis();
		logger.info("getRankListByTime Cost Time:" + (e - s));
		return list;
	}

	public List<RankModel> getMoreRankListByTime(String startTime, String endTime, int num) {
		long s = System.currentTimeMillis();
		String selSql = "select u.username,sum(p1.account) as tenderMoney from dw_borrow_tender p1 left join dw_borrow b on b.id=p1.borrow_id left join dw_user u on u.user_id=p1.user_id where ((b.status in(3,6,7,8) and b.is_flow<>1) or (b.status in(1,3,6,7,8) and b.is_flow=1))";

		String paramSql = " group by u.username order by tenderMoney desc";
		StringBuffer sb = new StringBuffer();
		sb.append(selSql).append(" and p1.addtime>=? and p1.addtime<=? ").append(paramSql).append(" limit 0,").append(num);
		String sql = sb.toString();
		logger.debug("getChartsList()" + sql);
		List<RankModel> list = new ArrayList<RankModel>();
		list = getJdbcTemplate().query(sql,
				new Object[] { startTime, endTime }, new RowMapper<RankModel>() {
					public RankModel mapRow(ResultSet rs, int num)
							throws SQLException {
						RankModel r = new RankModel();
						r.setUsername(rs.getString("username"));
						r.setTenderMoney(rs.getDouble("tenderMoney"));
						return r;
					}
				});
		long e = System.currentTimeMillis();
		logger.info("getRankListByTime Cost Time:" + (e - s));
		return list;
	}

	public List<RankModel> getRankList() {
		String selSql = "select p3.username ,sum(p1.account) as tenderMoney from dw_borrow_tender p1 left join dw_borrow p2 on p2.id=p1.borrow_id left join dw_user p3 on p3.user_id=p1.user_id where p2.isday<>1 and ((p2.is_fast=1 and p2.status in (3,6,7,8)) or (p2.is_flow=1 and p2.status in (1,3,6,7,8)))";

		String groupSql = " group by p3.username order by tenderMoney desc";
		StringBuffer sb = new StringBuffer();
		sb.append(selSql).append(groupSql).append(" limit 0,50");
		String sql = sb.toString();
		logger.debug("getRankList():" + sql);
		List<RankModel> list = new ArrayList<RankModel>();
		list = getJdbcTemplate().query(sql, new Object[0], new RowMapper<RankModel>() {
			public RankModel mapRow(ResultSet rs, int num) throws SQLException {
				RankModel r = new RankModel();
				r.setUsername(rs.getString("username"));
				r.setTenderMoney(rs.getDouble("tenderMoney"));
				return r;
			}
		});
		return list;
	}

	public List<RankModel> getAllRankList() {
		String selSql = "select p3.username ,sum(p1.account) as tenderMoney from dw_borrow_tender p1 left join dw_borrow p2 on p2.id=p1.borrow_id left join dw_user p3 on p3.user_id=p1.user_id where p2.isday<>1 and ((p2.is_fast=1 and p2.status in (3,6,7,8)) or (p2.is_flow=1 and p2.status in (1,3,6,7,8)))";

		String groupSql = " group by p3.username order by tenderMoney desc";
		StringBuffer sb = new StringBuffer();
		sb.append(selSql).append(groupSql);
		String sql = sb.toString();
		logger.debug("getRankList():" + sql);
		List<RankModel> list = new ArrayList<RankModel>();
		list = getJdbcTemplate().query(sql, new Object[0], new RowMapper<RankModel>() {
			public RankModel mapRow(ResultSet rs, int num) throws SQLException {
				RankModel r = new RankModel();
				r.setUsername(rs.getString("username"));
				r.setTenderMoney(rs.getDouble("tenderMoney"));
				return r;
			}
		});
		return list;
	}

	public List<BorrowTender> getTenderList() {
		long start = System.currentTimeMillis();
		String selectsql = " and ((b.status in (3,6,7,8) and b.is_flow!=1 ) or (b.status in(1,3,6,7,8) and b.is_flow=1)) ";
		StringBuffer sb = new StringBuffer(this.secsql);
		sb.append(selectsql);
		String scsql = sb.toString();
		logger.debug("getTenderList():" + scsql);
		List<BorrowTender> list = new ArrayList<BorrowTender>();
		list = getJdbcTemplate().query(scsql, new BorrowNewTenderMapper());
		long end = System.currentTimeMillis();
		logger.info("GetTenderList Cost Time:" + (end - start));
		return list;
	}

	public List<BorrowTender> getNewTenderList() {
		String selectsql = " and b.status in(1,3) order by a.addtime desc limit 0,20";
		StringBuffer sb = new StringBuffer();
		sb.append(this.secsql).append(selectsql);
		String scsql = sb.toString();
		List<BorrowTender> list = new ArrayList<BorrowTender>();
		list = getJdbcTemplate().query(scsql, new BorrowNewTenderMapper());
		return list;
	}

	public int getAllTenderCount(SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String sql = this.queryAllTenderCountSql + searchSql;
		logger.debug("SQL:" + sql);
		int total = 0;
		total = count(sql);
		return total;
	}

	public List<BorrowNTender> getAllTenderList(int start, int pernum, SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + pernum;
		String orderSql = param.getOrderSql();
		String sql = this.queryAllTenderSql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + this.queryAllTenderSql);
		List<BorrowNTender> list = getJdbcTemplate().query(sql, new BorrowNTenderMapper());
		return list;
	}

	public List<BorrowNTender> getAllTenderList(SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String orderSql = param.getOrderSql();
		String sql = this.queryAllTenderSql + searchSql + orderSql;
		logger.debug("SQL:" + this.queryAllTenderSql);
		List<BorrowNTender> list = getJdbcTemplate().query(sql, new BorrowNTenderMapper());
		return list;
	}

	public void updateTenderAwardYesAndNo(
			TenderAwardYesAndNo tenderAwardYesAndNo) {
		String sql = "update dw_tender_award_yes_no set tender_award_yes_no=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] {
						Integer.valueOf(tenderAwardYesAndNo
								.getTender_award_yes_no()),
						Integer.valueOf(tenderAwardYesAndNo.getId()) });
	}

	public TenderAwardYesAndNo tenderAwardYesAndNo(int id) {
		String sql = "select * from dw_tender_award_yes_no where id=?";
		TenderAwardYesAndNo tenderAwardYesAndNo = new TenderAwardYesAndNo();
		try {
			tenderAwardYesAndNo = (TenderAwardYesAndNo) getJdbcTemplate()
					.queryForObject(sql, new Object[] { Integer.valueOf(id) },
							new RowMapper<TenderAwardYesAndNo>() {
								public TenderAwardYesAndNo mapRow(ResultSet rs,
										int num) throws SQLException {
									TenderAwardYesAndNo r = new TenderAwardYesAndNo();
									r.setId(rs.getInt("id"));
									r.setTender_award_yes_no(rs.getInt("tender_award_yes_no"));
									return r;
								}
							});
		} catch (DataAccessException e) {
			logger.debug("tenderAwardYesAndNo============ " + e.getMessage());
		}
		return tenderAwardYesAndNo;
	}

	public List<BorrowTender> allTenderListBybid(long borrow_id) {
		String sql = "SELECT * FROM dw_borrow_tender where borrow_id=?";
		List<BorrowTender> list = new ArrayList<BorrowTender>();
		list = getJdbcTemplate().query(sql, new BorrowTenderMapper(), new Object[] { Long.valueOf(borrow_id) });
		return list;
	}

	public Tender getTenderById(long tid) {
		String sql = "SELECT * FROM dw_borrow_tender where id=?";
		Tender t = null;
		try {
			t = (Tender) getJdbcTemplate().queryForObject(sql,
					new BorrowTenderMapper(),
					new Object[] { Long.valueOf(tid) });
		} catch (DataAccessException localDataAccessException) {
		}
		return t;
	}

	public void modifyRepayTender(double capital, double interest, long id) {
		String sql = "update dw_borrow_tender set repayment_yesaccount=repayment_yesaccount+?,repayment_yesinterest =repayment_yesinterest+?,wait_account =wait_account-?,wait_interest =wait_interest-? where id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { Double.valueOf(capital),
						Double.valueOf(interest), Double.valueOf(capital),
						Double.valueOf(interest), Long.valueOf(id) });
	}

	public int getAutoTenderByUserid(long userid, long is_auto_tender,
			long borrow_id, long status) {
		String sql = "select count(*) from dw_borrow_tender p1 left join dw_borrow p2 on p2.id=p1.borrow_id where p2.status=? and p1.user_id=? and p1.borrow_id=? and p1.is_auto_tender=? ";
		int times = 0;
		try {
			times = getJdbcTemplate().queryForInt(
					sql,
					new Object[] { Long.valueOf(status), Long.valueOf(userid),
							Long.valueOf(borrow_id),
							Long.valueOf(is_auto_tender) });
		} catch (DataAccessException e) {
			logger.debug("getTotalTenderByUserid():" + e.getMessage());
		}
		return times;
	}
}