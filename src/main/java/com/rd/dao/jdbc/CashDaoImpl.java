package com.rd.dao.jdbc;

import com.rd.context.Global;
import com.rd.dao.CashDao;
import com.rd.dao.jdbc.mapper.AccountCashMapper;
import com.rd.dao.jdbc.mapper.AdvancedMapper;
import com.rd.domain.AccountCash;
import com.rd.domain.Advanced;
import com.rd.model.SearchParam;
import com.rd.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CashDaoImpl extends BaseDaoImpl implements CashDao {
	private static Logger logger = Logger.getLogger(CashDaoImpl.class);

	String queryAllSql = " from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 ";

	public List<AccountCash> getAccountCashList(long user_id) {
		String sql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new AccountCashMapper());
		return list;
	}

	public int getAccountCashCount(long user_id, SearchParam param) {
		String sql = "select count(p1.id) from  dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 and p1.user_id=?";

		sql = sql + param.getSearchParamSql();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(user_id) });
		return count;
	}

	public List<AccountCash> getAccountCashList(long user_id, int start, int end,
			SearchParam param) {
		String sql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? ";

		String orderSql = " order by p1.addtime desc ";
		String limitSql = "limit ?,?";
		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("getAccountCashList():" + sql);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(start), Integer.valueOf(end) }, new AccountCashMapper());
		return list;
	}

	public AccountCash addCash(final AccountCash cash) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_account_cash(user_id,account,bank,branch,total,credited,fee,addtime,addip,hongbao) values(?,?,?,?,?,?,?,?,?,?)";

		logger.info("SQL:insert into dw_account_cash(user_id,account,bank,branch,total,credited,fee,addtime,addip,hongbao) values(?,?,?,?,?,?,?,?,?,?)");
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, cash.getUser_id());
				ps.setString(2, cash.getAccount());
				ps.setString(3, cash.getBank());
				ps.setString(4, cash.getBranch());
				ps.setString(5, cash.getTotal());
				ps.setString(6, cash.getCredited());
				ps.setString(7, cash.getFee());
				ps.setString(8, cash.getAddtime());
				ps.setString(9, cash.getAddip());
				ps.setDouble(10, cash.getHongbao());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		cash.setId(key);
		return cash;
	}

	public int getAllCashCount(SearchParam param) {
		String selectSql = "select count(p1.id) ";
		String searchSql = param.getSearchParamSql();
		String querySql = selectSql + this.queryAllSql + searchSql;

		int total = 0;
		total = count(querySql);
		return total;
	}

	public List<AccountCash> getAllCashList(int start, int pernum, SearchParam param) {
		String selectSql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username ";
		String searchSql = param.getSearchParamSql();
		String orderSql = " order by p1.addtime desc ";
		String limitSql = "limit ?,?";
		String querySql = selectSql + this.queryAllSql + searchSql + orderSql + limitSql;
		logger.debug("getAllCashList():" + querySql);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(querySql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new AccountCashMapper());
		return list;
	}

	public List<AccountCash> getAllCashList(SearchParam param) {
		String selectSql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username ";
		String searchSql = param.getSearchParamSql();
		String orderSql = " order by p1.addtime desc ";

		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("ssjb")) {
			orderSql = " order by p1.addtime asc";
		}

		String querySql = selectSql + this.queryAllSql + searchSql + orderSql;
		logger.debug("getAllCashList():" + querySql);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(querySql, new AccountCashMapper());
		return list;
	}

	public AccountCash getAccountCash(long id) {
		String selectSql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.queryAllSql).append(" and p1.id=?");
		AccountCash c = null;
		try {
			c = (AccountCash) getJdbcTemplate().queryForObject(sb.toString(), new Object[] { Long.valueOf(id) }, new AccountCashMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		return c;
	}

	public void updateCash(AccountCash cash) {
		String sql = "update dw_account_cash set status=?,credited=?,fee=?,verify_userid=?,verify_time=?,verify_remark=? where id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(cash.getStatus()),
						cash.getCredited(), cash.getFee(),
						Long.valueOf(cash.getVerify_userid()),
						cash.getVerify_time(), cash.getVerify_remark(),
						Long.valueOf(cash.getId()) });
	}

	public int getAccountCashNum(long user_id, int status) {
		String sql = "SELECT COUNT(1) FROM dw_account_cash AS p1  LEFT JOIN dw_user AS p2 ON p1.user_id=p2.user_id  WHERE DATE(FROM_UNIXTIME(p1.addtime)) = CURDATE() AND p1.user_id  = ?  AND p1.status = ?";

		logger.debug("SQL " + sql);
		int cashNum = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		return cashNum;
	}

	public List<AccountCash> getAccountCashList(long user_id, int status) {
		String sql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? and p1.status=?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) }, new AccountCashMapper());
		return list;
	}

	public double getAccountCashSum(long user_id, int status) {
		String sql = "select sum(p1.total) as num from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? and p1.status=?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		return total;
	}

	public List<AccountCash> getAccountCashList(long user_id, int status, long startTime) {
		String sql = "select p1.*,p4.name as bankname,p2.username,p2.realname,admin.username as verify_username from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? and p1.status=? and p1.addtime>?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<AccountCash> list = new ArrayList<AccountCash>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status), Long.valueOf(startTime) }, new AccountCashMapper());
		return list;
	}

	public double getAccountCashSum(long user_id, int status, long startTime) {
		String sql = "select sum(p1.total) as num from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? and p1.status=? and p1.verify_time>?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id),
				Integer.valueOf(status), Long.valueOf(startTime) });
		return total;
	}

	public double getAccountCashSum(long user_id, int status, long startTime,
			long endTime) {
		String sql = "select sum(p1.total) as num from dw_account_cash as p1 where 1=1 and p1.user_id=? and p1.status=? and p1.verify_time>? and p1.verify_time<?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id),
				Integer.valueOf(status), Long.valueOf(startTime),
				Long.valueOf(endTime) });
		return total;
	}

	public double getSumTotal() {
		String sql = "select sum(total) as num from dw_account";
		logger.debug("SQL:" + sql);
		double sumTotal = sum(sql, new Object[0]);
		return sumTotal;
	}

	public double getSumUseMoney() {
		String sql = "select sum(use_money) as num from dw_account";
		logger.debug("SQL:" + sql);
		double sumUserMoney = sum(sql, new Object[0]);
		return sumUserMoney;
	}

	public double getSumNoUseMoney() {
		String sql = "select sum(no_use_money) as num from dw_account";
		logger.debug("SQL:" + sql);
		double sumNoUseMoney = sum(sql, new Object[0]);
		return sumNoUseMoney;
	}

	public double getSumCollection() {
		String sql = "select sum(collection) as num from dw_account";
		logger.debug("SQL:" + sql);
		double sumCollection = sum(sql, new Object[0]);
		return sumCollection;
	}

	public void getAdvance_insert(Advanced advanced) {
		String sql = "insert into dw_advanced(advance_reserve,no_advanced_account) values(?,?)";

		logger.info("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Double.valueOf(advanced.getAdvance_reserve()),
						Double.valueOf(advanced.getNo_advanced_account()) });
	}

	public List<Advanced> getAdvanceList() {
		String sql = "select * from dw_advanced";
		logger.debug("SQL:" + sql);
		List<Advanced> list = new ArrayList<Advanced>();
		list = getJdbcTemplate().query(sql, new Object[0], new AdvancedMapper());
		return list;
	}

	public void getAdvance_update(Advanced advanced) {
		String sql = "update dw_advanced set advance_reserve=?,no_advanced_account=?,borrow_total=?,wait_total=?,borrow_day_total = ? where id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(advanced.getAdvance_reserve()),
						Double.valueOf(advanced.getNo_advanced_account()),
						Double.valueOf(advanced.getBorrow_total()),
						Double.valueOf(advanced.getWait_total()),
						Double.valueOf(advanced.getBorrow_day_total()),
						Long.valueOf(advanced.getId()) });
	}

	public double getAccountNoCashSum(long user_id, int status, long startTime) {
		String sql = "select sum(p1.total) as num from dw_account_cash as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as admin on admin.user_id=p1.verify_userid left join dw_linkage as p4 on p4.id=p1.bank and p4.type_id=25 where 1=1 and p1.user_id=? and p1.status=? and p1.addtime>?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status), Long.valueOf(startTime) });
		return total;
	}

	public AccountCash addFreeCash(final AccountCash cash) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_account_cash(user_id,account,bank,branch,total,credited,fee,addtime,addip,hongbao,free_cash) values(?,?,?,?,?,?,?,?,?,?,?)";

		logger.info("SQL:insert into dw_account_cash(user_id,account,bank,branch,total,credited,fee,addtime,addip,hongbao,free_cash) values(?,?,?,?,?,?,?,?,?,?,?)");

		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, cash.getUser_id());
				ps.setString(2, cash.getAccount());
				ps.setString(3, cash.getBank());
				ps.setString(4, cash.getBranch());
				ps.setString(5, cash.getTotal());
				ps.setString(6, cash.getCredited());
				ps.setString(7, cash.getFee());
				ps.setString(8, cash.getAddtime());
				ps.setString(9, cash.getAddip());
				ps.setDouble(10, cash.getHongbao());
				ps.setDouble(11, cash.getFreecash());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		cash.setId(key);
		return cash;
	}
}