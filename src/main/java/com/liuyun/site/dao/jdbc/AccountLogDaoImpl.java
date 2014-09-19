package com.liuyun.site.dao.jdbc;

import com.liuyun.site.context.Global;
import com.liuyun.site.dao.AccountLogDao;
import com.liuyun.site.dao.jdbc.mapper.AccountLogMapper;
import com.liuyun.site.dao.jdbc.mapper.AccountLogSumMapper;
import com.liuyun.site.dao.jdbc.mapper.DetailTenderMapper;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.account.AccountLogModel;
import com.liuyun.site.model.account.AccountLogSumModel;
import com.liuyun.site.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AccountLogDaoImpl extends BaseDaoImpl implements AccountLogDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);

	String getAccountLogSql = "from dw_account_log as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as p3 on p3.user_id=p1.to_user left join dw_linkage as p4 on p4.value=p1.type and p4.type_id=30 where 1=1 ";

	String selectSql = "select p1.*,p2.username,p3.username as to_username,p4.name as typename ";
	String countSql = "select count(distinct p1.id) ";
	String orderSql = " order by p1.addtime desc,p1.id desc ";
	String limitSql = " limit ?,? ";
	String groupSql = " group by p1.type";
	String selectSql1 = "select sum(p1.money) as sum,p4.value as type,p4.name as typename ";
	String tenderLogSql = " from dw_borrow_tender as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_borrow as p3 on p3.id = p1.borrow_id where p3.status in (1,3,6,7,8) ";

	String tenderSelSql = "select p1.*,p1.account as tender_account,p1.money as tender_money,p2.user_id as borrow_userid,p4.username as op_username,p2.username as username,p3.apr,p3.time_limit,p3.time_limit_day,p3.isday,p3.name as borrow_name,p3.id as borrow_id,p3.account as borrow_account ,p3.account_yes as borrow_account_yes,p3.part_account as part_account,p3.verify_time,p5.value as credit_jifen,p6.pic as credit_pic from dw_borrow_tender as p1 left join dw_borrow as p3 on p1.borrow_id=p3.id left join dw_user as p2 on p2.user_id = p1.user_id left join dw_user as p4 on p4.user_id = p3.user_id left join dw_credit as p5 on p1.user_id=p5.user_id left join dw_credit_rank as p6 on p5.value<=p6.point2  and p5.value>=p6.point1 where p3.status in (1,3,6,7,8) ";

	public List<AccountLogModel> getAccountLogList(long user_id) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		sb.append(this.getAccountLogSql).append(" and p1.user_id=? ").append(this.orderSql).append(" limit 0,20");
		String sql = sb.toString();
		logger.debug("getAccountLogList()" + sql);
		List<AccountLogModel> list = new ArrayList<AccountLogModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new AccountLogMapper());
		return list;
	}

	public List<AccountLogModel> getAccountLogList(long user_id, int start, int end, SearchParam param) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		String searchSql = param.getSearchParamSql();
		sb.append(this.getAccountLogSql).append(" and p1.user_id=? ").append(searchSql).append(this.orderSql).append(this.limitSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<AccountLogModel> list = new ArrayList<AccountLogModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(start), Integer.valueOf(end) }, new AccountLogMapper());
		return list;
	}

	public int getAccountLogCount(long user_id, SearchParam param) {
		StringBuffer sb = new StringBuffer(this.countSql);
		sb.append(this.getAccountLogSql).append(" and p1.user_id=? ").append(param.getSearchParamSql());
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(user_id) });
		return count;
	}

	public double getAccountLogTotalMoney(long user_id) {
		String sql = "select sum(money) as total from dw_account_log where user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			total = rs.getDouble("total");
		}
		return total;
	}

	public double getAccountLogInterestTotalMoney(long user_id) {
		String sql = "select sum(money) as total from dw_account_log where user_id=? and type='wait_interest'";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			total = rs.getDouble("total");
		}
		return total;
	}

	public void addAccountLog(AccountLog log) {
		String sql = "insert into dw_account_log(user_id,type,total,money,use_money,no_use_money,collection,to_user,remark,addtime,addip) values(?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(log.getUser_id()), log.getType(),
						Double.valueOf(log.getTotal()),
						Double.valueOf(log.getMoney()),
						Double.valueOf(log.getUse_money()),
						Double.valueOf(log.getNo_use_money()),
						Double.valueOf(log.getCollection()),
						Long.valueOf(log.getTo_user()), log.getRemark(),
						log.getAddtime(), log.getAddip() });
	}

	public int getAccountLogCount(SearchParam param) {
		StringBuffer sb = new StringBuffer(this.countSql);
		sb.append(this.getAccountLogSql).append(param.getSearchParamSql());
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql);
		return count;
	}

	public List<AccountLogModel> getAccountLogList(int start, int pernum, SearchParam param) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		String searchSql = param.getSearchParamSql();
		sb.append(this.getAccountLogSql).append(searchSql).append(this.orderSql).append(this.limitSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<AccountLogModel> list = new ArrayList<AccountLogModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new AccountLogMapper());
		return list;
	}

	public List<AccountLogModel> getAccountLogList(SearchParam param) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		String searchSql = param.getSearchParamSql();

		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("ssjb")) {
			this.orderSql = " order by p1.addtime desc,p1.id asc ";
		}

		sb.append(this.getAccountLogSql).append(searchSql).append(this.orderSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<AccountLogModel> list = new ArrayList<AccountLogModel>();
		list = getJdbcTemplate().query(sql, new AccountLogMapper());
		return list;
	}

	public double getAwardSum(long user_id) {
		String sql = "select sum(money) as sum from dw_account_log where type='award_add' and user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			total = rs.getDouble("sum");
		}
		return total;
	}

	public double getrepayInterestSum(long user_id) {
		String sql = "select sum(money) as sum from dw_account_log where type='wait_interest' and user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			total = rs.getDouble("sum");
		}
		return total;
	}

	public double getInvestInterestSum(long user_id) {
		String sql = "select investInterest  from view_invest_sum where  user_id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			total = rs.getDouble("investInterest");
		}
		return total;
	}

	public double getAwardSum(long user_id, long starttime) {
		String sql = "select sum(money) as sum from dw_account_log where type='award_add' and user_id=? and addtime>?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		logger.debug("SQL:" + starttime);
		double total = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id), Long.valueOf(starttime) });
		if (rs.next()) {
			total = rs.getDouble("sum");
		}
		return total;
	}

	public int getTenderLogCount(SearchParam param) {
		StringBuffer sb = new StringBuffer(this.countSql);
		sb.append(this.tenderLogSql).append(param.getSearchParamSql());
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql);
		return count;
	}

	public List<DetailTender> getTenderLogList(int start, int pernum, SearchParam param) {
		StringBuffer sb = new StringBuffer(this.tenderSelSql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql).append(this.orderSql).append(this.limitSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new DetailTenderMapper());
		return list;
	}

	public List<DetailTender> getTenderLogList(SearchParam param) {
		StringBuffer sb = new StringBuffer(this.tenderSelSql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql).append(this.orderSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<DetailTender> list = new ArrayList<DetailTender>();
		list = getJdbcTemplate().query(sql, new DetailTenderMapper());
		return list;
	}

	public List<AccountLogModel> getAccountLogList(long user_id, SearchParam param) {
		StringBuffer sb = new StringBuffer(this.selectSql);
		String searchSql = param.getSearchParamSql();
		sb.append(this.getAccountLogSql).append(" and p1.user_id=? ").append(searchSql).append(this.orderSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<AccountLogModel> list = new ArrayList<AccountLogModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new AccountLogMapper());
		return list;
	}

	public double getAccountLogSum(String type) {
		String sql = "select sum(money) as sum from dw_account_log where type=?";
		double accountLogSum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { type });
		if (rs.next()) {
			accountLogSum = rs.getDouble("sum");
		}
		return accountLogSum;
	}

	public List<AccountLogSumModel> getAccountLogSumWithMonth(SearchParam param) {
		StringBuffer sb = new StringBuffer(this.selectSql1);
		String searchSql = param.getSearchParamSql();
		sb.append(this.getAccountLogSql).append(searchSql).append(this.groupSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<AccountLogSumModel> list = new ArrayList<AccountLogSumModel>();
		list = getJdbcTemplate().query(sql, new AccountLogSumMapper());
		return list;
	}

	public int getAccountLogSumCount() {
		String sql = "select count(*) from dw_linkage where type_id=30";
		logger.debug("SQL:" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}
}