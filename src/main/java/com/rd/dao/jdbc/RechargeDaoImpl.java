package com.rd.dao.jdbc;

import com.rd.context.Global;
import com.rd.dao.RechargeDao;
import com.rd.dao.jdbc.mapper.AccountMinInviteRechargeMapper;
import com.rd.dao.jdbc.mapper.AccountRechargeMapper;
import com.rd.dao.jdbc.mapper.BaseAccountRechargeMapper;
import com.rd.domain.AccountRecharge;
import com.rd.model.SearchParam;
import com.rd.util.DateUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class RechargeDaoImpl extends BaseDaoImpl implements RechargeDao {
	private Logger logger = Logger.getLogger(RechargeDaoImpl.class);

	String queryRechargeSql = " from dw_account_recharge as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_payment as p3 on p1.payment=p3.id where 1=1 ";

	String newqueryRechargeSql = " from dw_account_recharge as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as recharge_kefu on p1.recharge_kefuid=recharge_kefu.user_id left join dw_payment as p3 on p1.payment=p3.id where 1=1 ";

	String queryCashSql = " from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id  where 1=1 ";

	String queryTenderInterestSql = " from dw_borrow_tender as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_borrow as p3 on p1.borrow_id=p3.id where 1=1 and p3.status in (1,3,6,7,8) ";

	public List<AccountRecharge> getList(long user_id) {
		String sql = "select p1.*,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  from dw_account_recharge as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_payment as p3 on p1.payment=p3.id where p1.user_id=?";

		List<AccountRecharge> list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new AccountRechargeMapper());
		return list;
	}

	public int getCount(long user_id, SearchParam param) {
		StringBuffer sb = new StringBuffer("select count(p1.id) ");
		sb.append(this.queryRechargeSql).append(" and p1.user_id=? ").append(
				param.getSearchParamSql());
		String sql = sb.toString();
		this.logger.debug("getCount():" + sql);
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(user_id) });
		return count;
	}

	public List<AccountRecharge> getList(long user_id, int start, int end, SearchParam param) {
		StringBuffer sb = new StringBuffer();
		if (Global.getWebid().equals("jsy")) {
			sb = new StringBuffer("select p1.*,recharge_kefu.username as recharge_kefu_username,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
			String orderSql = " order by p1.addtime desc ";
			String limitSql = "limit ?,?";
			String searchSql = param.getSearchParamSql();
			sb.append(this.newqueryRechargeSql).append(" and p1.user_id=? ").append(searchSql).append(orderSql).append(limitSql);
		} else {
			sb = new StringBuffer("select p1.*,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
			String orderSql = " order by p1.addtime desc ";
			String limitSql = "limit ?,?";
			String searchSql = param.getSearchParamSql();
			sb.append(this.queryRechargeSql).append(" and p1.user_id=? ").append(searchSql).append(orderSql).append(limitSql);
		}
		String sql = sb.toString();
		this.logger.debug("getList():" + sql);
		System.out.println("================" + sql);
		List<AccountRecharge> list = new ArrayList<AccountRecharge>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(user_id), Integer.valueOf(start),
						Integer.valueOf(end) }, new AccountRechargeMapper());
		return list;
	}

	public void addRecharge(AccountRecharge r) {
		if (Global.getWebid().equals("jsy")) {
			String sql = "insert into dw_account_recharge(trade_no,user_id,status,money,payment,`return`,type,remark,fee,verify_userid,verify_time,verify_remark,addtime,addip,recharge_kefuid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			getJdbcTemplate().update(
					sql,
					new Object[] { r.getTrade_no(),
							Long.valueOf(r.getUser_id()),
							Integer.valueOf(r.getStatus()),
							Double.valueOf(r.getMoney()), r.getPayment(),
							r.getReturntext(), r.getType(), r.getRemark(),
							r.getFee(), Long.valueOf(r.getVerify_userid()),
							r.getVerify_time(), r.getVerify_remark(),
							r.getAddtime(), r.getAddip(),
							Long.valueOf(r.getRecharge_kefuid()) });
		} else {
			String sql = "insert into dw_account_recharge(trade_no,user_id,status,money,payment,`return`,type,remark,fee,verify_userid,verify_time,verify_remark,addtime,addip) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			getJdbcTemplate().update(
					sql,
					new Object[] { r.getTrade_no(),
							Long.valueOf(r.getUser_id()),
							Integer.valueOf(r.getStatus()),
							Double.valueOf(r.getMoney()), r.getPayment(),
							r.getReturntext(), r.getType(), r.getRemark(),
							r.getFee(), Long.valueOf(r.getVerify_userid()),
							r.getVerify_time(), r.getVerify_remark(),
							r.getAddtime(), r.getAddip() });
		}
	}

	public AccountRecharge getRechargeByTradeno(String trade_no) {
		String sql = "select * from dw_account_recharge where trade_no=?";
		AccountRecharge r = null;
		try {
			r = (AccountRecharge) getJdbcTemplate().queryForObject(sql,
					new Object[] { trade_no }, new BaseAccountRechargeMapper());
		} catch (DataAccessException e) {
			this.logger.debug(e.getMessage());
		}
		return r;
	}

	public int updateRecharge(int status, String returnText, String trade_no) {
		String sql = "update dw_account_recharge set status=?,`return`=?,verify_time=? where trade_no=? ";
		int count = 0;
		try {
			count = getJdbcTemplate().update(
					sql,
					new Object[] { Integer.valueOf(status), returnText,
							DateUtils.getNowTimeStr(), trade_no });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateRechargeByStatus(int status, String returnText,
			String trade_no) {
		String sql = "update dw_account_recharge set status=?,`return`=?,verify_time=? where trade_no=? and status=0 ";
		int count = 0;
		try {
			count = getJdbcTemplate().update(
					sql,
					new Object[] { Integer.valueOf(status), returnText,
							DateUtils.getNowTimeStr(), trade_no });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public int getAllCount(SearchParam param) {
		StringBuffer sb = new StringBuffer("select count(p1.id) ");
		if (Global.getWebid().equals("jsy"))
			sb.append(this.newqueryRechargeSql).append(param.getSearchParamSql());
		else {
			sb.append(this.queryRechargeSql).append(param.getSearchParamSql());
		}
		String sql = sb.toString();
		this.logger.debug("countsql======" + sql);
		int count = 0;
		count = count(sql);
		return count;
	}

	public List<AccountRecharge> getAllList(int start, int end, SearchParam param) {
		StringBuffer sb = new StringBuffer();
		if (Global.getWebid().equals("jsy"))
			sb = new StringBuffer("select p1.*,recharge_kefu.username as recharge_kefu_username,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
		else {
			sb = new StringBuffer("select p1.*,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
		}
		String orderSql = " order by p1.addtime desc ";
		String limitSql = "limit ?,?";
		String searchSql = param.getSearchParamSql();
		if (Global.getWebid().equals("jsy"))
			sb.append(this.newqueryRechargeSql).append(searchSql).append(orderSql).append(limitSql);
		else {
			sb.append(this.queryRechargeSql).append(searchSql).append(orderSql).append(limitSql);
		}

		String sql = sb.toString();
		this.logger.debug("RechargeDao.getList():" + sql);
		List<AccountRecharge> list = new ArrayList<AccountRecharge>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new AccountRechargeMapper());
		return list;
	}

	public List<AccountRecharge> getAllList(SearchParam param) {
		StringBuffer sb = new StringBuffer();
		if (Global.getWebid().equals("jsy"))
			sb = new StringBuffer("select p1.*,recharge_kefu.username as recharge_kefu_username,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
		else {
			sb = new StringBuffer("select p1.*,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
		}
		String orderSql = " order by p1.addtime desc ";
		String searchSql = param.getSearchParamSql();
		if (Global.getWebid().equals("jsy"))
			sb.append(this.newqueryRechargeSql).append(searchSql).append(orderSql);
		else {
			sb.append(this.queryRechargeSql).append(searchSql).append(orderSql);
		}

		String sql = sb.toString();
		this.logger.debug("RechargeDao.getList():" + sql);
		List<AccountRecharge> list = new ArrayList<AccountRecharge>();
		list = getJdbcTemplate().query(sql, new AccountRechargeMapper());
		return list;
	}

	public AccountRecharge getRecharge(long id) {
		StringBuffer sb = new StringBuffer();
		if (Global.getWebid().equals("jsy")) {
			sb = new StringBuffer("select p1.*,recharge_kefu.username as recharge_kefu_username,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
			sb.append(this.newqueryRechargeSql).append(" and p1.id=? ");
		} else {
			sb = new StringBuffer("select p1.*,p1.money,p1.money-p1.fee as total,p2.username,p2.realname,p3.name as paymentname  ");
			sb.append(this.queryRechargeSql).append(" and p1.id=? ");
		}

		String sql = sb.toString();
		AccountRecharge r = null;
		r = (AccountRecharge) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new AccountRechargeMapper());
		return r;
	}

	public void updateRecharge(AccountRecharge r) {
		String sql = "update dw_account_recharge set status=?,`return`=?,verify_userid=?,verify_time=?,verify_remark=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(r.getStatus()),
						r.getReturntext(), Long.valueOf(r.getVerify_userid()),
						r.getVerify_time(), r.getVerify_remark(),
						Long.valueOf(r.getId()) });
	}

	public void updateRechargeFee(double fee, long id) {
		String sql = "update dw_account_recharge set fee=? where id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(fee), Long.valueOf(id) });
	}

	public double getLastRechargeSum(long user_id) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -15);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and verify_time>"
				+ d.getTime() / 1000L;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getLastRechargeSum(long user_id, int day) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -day);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and verify_time>"
				+ d.getTime() / 1000L;
		this.logger.debug("sql=========" + sql);
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getLastRechargeSum(long user_id, int type, long start) {
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and  and verify_time>?";
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getLastOnlineRechargeSum(long user_id) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -15);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and type=1 and verify_time>" + d.getTime() / 1000L;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public List<AccountRecharge> getLastOfflineRechargeList(long user_id) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -15);
		String sql = "select * from dw_account_recharge r left join dw_payment p on p.id=r.payment where p.nid='offline' and r.status=1 and r.user_id=? and r.verify_time>?";

		List<AccountRecharge> list = new ArrayList<AccountRecharge>();
		try {
			list = getJdbcTemplate().query(
					sql,
					new Object[] { Long.valueOf(user_id), Long.valueOf(d.getTime() / 1000L) },
					new BaseAccountRechargeMapper());
		} catch (DataAccessException localDataAccessException) {
		}
		return list;
	}

	public double getAccount_sum(SearchParam param, int ids) {
		StringBuffer sb = new StringBuffer("select sum(p1.money) as sum");
		String searchSql = param.getSearchParamSql();
		String sql = "";
		if (ids == 1) {
			if (Global.getWebid().equals("jsy"))
				sb.append(this.newqueryRechargeSql).append(searchSql);
			else {
				sb.append(this.queryRechargeSql).append(searchSql);
			}

			sql = sb.toString();
		} else if (ids == 2) {
			StringBuffer newsb = new StringBuffer("select sum(p1.total) as sum");
			newsb.append(this.queryCashSql).append(searchSql);
			sql = newsb.toString();
		} else if (ids == 3) {
			StringBuffer newsb = new StringBuffer("select sum(p1.fee) as sum");
			newsb.append(this.queryCashSql).append(searchSql);
			sql = newsb.toString();
		} else if (ids == 4) {
			StringBuffer newsb = new StringBuffer(
					"select sum(p1.credited) as sum");
			newsb.append(this.queryCashSql).append(searchSql);
			sql = newsb.toString();
		} else if (ids == 5) {
			StringBuffer newsb = new StringBuffer(
					"select sum(p1.interest) as sum");
			newsb.append(this.queryTenderInterestSql).append(searchSql);
			sql = newsb.toString();
		}

		this.logger.debug("RechargeDao.getAccount_sum:" + sql);
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql);
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("sum");
		}
		return sum;
	}

	public double getRechargeSumWithNoAdmin(long user_id, int day) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -day);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and payment!=48 and verify_time>"
				+ d.getTime() / 1000L;
		this.logger.debug("sql=========" + sql);
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public AccountRecharge getMinRecharge(long user_id, String status) {
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer(
				"SELECT * FROM dw_account_recharge WHERE id IN (SELECT  MIN(id) FROM dw_account_recharge WHERE STATUS=? AND user_id=?) AND STATUS=? AND user_id=? ");
		String sql = sb.toString();
		AccountRecharge r = null;
		r = (AccountRecharge) getJdbcTemplate().queryForObject(
				sql,
				new Object[] { status, Long.valueOf(user_id), status,
						Long.valueOf(user_id) },
				new AccountMinInviteRechargeMapper());
		return r;
	}

	public void updateAccountRechargeYes_no(AccountRecharge accountRecharge) {
		String sql = "update dw_account_recharge set yes_no=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(accountRecharge.getYes_no()),
						Long.valueOf(accountRecharge.getId()) });
	}

	public double getTodayOnlineRechargeTotal(long user_id, int day) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -day);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and type=1 and verify_time>"
				+ d.getTime()
				/ 1000L
				+ " and verify_time>"
				+ DateUtils.getIntegralTime().getTime()
				/ 1000L
				+ " and verify_time<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L;
		this.logger.debug("sql=========" + sql);
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getTodayRechargeTotal(long user_id, int day) {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -day);
		String sql = "select sum(money) as num from dw_account_recharge where user_id = ? and status=1 and verify_time>"
				+ d.getTime()
				/ 1000L
				+ " and verify_time>"
				+ DateUtils.getIntegralTime().getTime()
				/ 1000L
				+ " and verify_time<"
				+ DateUtils.getLastIntegralTime().getTime() / 1000L;
		this.logger.debug("sql=========" + sql);
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		double sum = 0.0D;
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}
}