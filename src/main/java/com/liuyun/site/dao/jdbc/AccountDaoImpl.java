package com.liuyun.site.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.AccountDao;
import com.liuyun.site.dao.jdbc.mapper.AccountReconciliationMapper;
import com.liuyun.site.dao.jdbc.mapper.CollectionMapper;
import com.liuyun.site.dao.jdbc.mapper.UserMapper;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountBank;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Collection;
import com.liuyun.site.domain.DownLineBank;
import com.liuyun.site.domain.Huikuan;
import com.liuyun.site.domain.OnlineBank;
import com.liuyun.site.domain.PaymentInterface;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.model.HongBaoModel;
import com.liuyun.site.model.HuikuanModel;
import com.liuyun.site.model.Interest;
import com.liuyun.site.model.NewCollection;
import com.liuyun.site.model.Newpay;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserAccountSummary;
import com.liuyun.site.model.WaitPayment;
import com.liuyun.site.model.account.AccountModel;
import com.liuyun.site.model.account.AccountOneDayModel;
import com.liuyun.site.model.account.AccountReconciliationModel;
import com.liuyun.site.model.account.AccountSumModel;
import com.liuyun.site.model.account.BorrowSummary;
import com.liuyun.site.model.account.CollectSummary;
import com.liuyun.site.model.account.InvestSummary;
import com.liuyun.site.model.account.OnlineBankModel;
import com.liuyun.site.model.account.RepaySummary;
import com.liuyun.site.model.account.TiChengModel;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;

public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);

	String getUserAcountSql = " from dw_user p2 left join dw_account p1 on p1.user_id = p2.user_id LEFT JOIN (select t1.user_id,sum(t2.repayment_account) as wait_repay from dw_borrow t1 left join dw_borrow_repayment t2 on t2.borrow_id=t1.id where t2.status=0 GROUP BY t1.user_id) as repay on repay.user_id =p2.user_id where 1=1 ";

	String accountBanksql = "select p2.username,p2.realname,p1.*,p3.*,p3.account as bankaccount,p4.name as bankname from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where 1=1 ";

	String accountBankCountsql = "select count(1) from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where 1=1 ";

	RowMapper<AccountModel> accountModelMapper = new RowMapper<AccountModel>() {
		
		public AccountModel mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AccountModel a = new AccountModel();
			a.setId(rs.getLong("id"));
			a.setUsername(rs.getString("username"));
			a.setUser_id(rs.getLong("user_id"));
			a.setTotal(rs.getDouble("total"));
			a.setUse_money(rs.getDouble("use_money"));
			a.setNo_use_money(rs.getDouble("no_use_money"));
			a.setCollection(rs.getDouble("collection"));
			a.setBank(rs.getString("bank"));
			a.setBankaccount(rs.getString("bankaccount"));
			a.setBankname(rs.getString("bankname"));
			a.setBranch(rs.getString("branch"));
			a.setProvince(rs.getInt("province"));
			a.setCity(rs.getInt("city"));
			a.setArea(rs.getInt("area"));
			a.setAddtime(rs.getString("addtime"));
			a.setAddip(rs.getString("addip"));
			a.setRealname(rs.getString("realname"));
			a.setModify_username(rs.getString("modify_username"));
			return a;
		}
	};

	String accountReconciliationSql = " FROM dw_user AS p1 LEFT JOIN dw_account AS p2 ON p1.user_id=p2.user_id LEFT JOIN (SELECT SUM(money) AS recharge_money,p3.user_id FROM dw_account_recharge p3 WHERE p3.status=1 GROUP BY p3.user_id) AS p4 ON p4.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS log_recharge_money,p5.user_id FROM dw_account_log p5 WHERE (TYPE='recharge' OR TYPE='recharge_success') GROUP BY p5.user_id) AS p6 ON p6.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS up_recharge_money,p7.user_id FROM dw_account_recharge p7 WHERE p7.status=1 AND p7.type=1 GROUP BY p7.user_id) AS p8 ON p8.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS down_recharge_money,p9.user_id FROM dw_account_recharge p9 WHERE p9.status=1 AND p9.type=2 GROUP BY p9.user_id) AS p10 ON p10.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS houtai_recharge_money,p11.user_id FROM dw_account_recharge p11 WHERE p11.status=1 AND p11.type=2 AND p11.payment=48 GROUP BY p11.user_id) AS p12 ON p12.user_id=p1.user_id LEFT JOIN (SELECT SUM(repayment_account) AS allcollection,p13.user_id FROM dw_borrow_tender  p13 GROUP BY p13.user_id) AS p14 ON p14.user_id=p1.user_id LEFT JOIN (SELECT SUM(credited) AS cash_money,SUM(fee) as cash_fee,p15.user_id FROM dw_account_cash p15 WHERE p15.status=1  GROUP BY p15.user_id) AS p16 ON p16.user_id=p1.user_id LEFT JOIN (SELECT SUM(p18.account*p18.part_account/100+p18.funds) AS invest_award,p17.user_id FROM dw_borrow_tender AS p17 LEFT JOIN dw_borrow AS p18 ON p17.borrow_id=p18.id GROUP BY p17.user_id) AS p19 ON p19.user_id=p1.user_id LEFT JOIN (SELECT SUM(p20.interest-p20.wait_interest) AS invest_yeswait_interest,SUM(p20.wait_interest) AS wait_interest,p20.user_id FROM dw_borrow_tender p20 GROUP BY p20.user_id) AS p21 ON p21.user_id=p1.user_id LEFT JOIN (SELECT SUM(account*part_account/100+funds) AS borrow_award,user_id FROM dw_borrow GROUP BY user_id) AS p22 ON p22.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS borrow_fee,user_id FROM dw_account_log WHERE TYPE='borrow_fee' GROUP BY user_id) AS p23 ON p23.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS manage_fee,user_id FROM dw_account_log WHERE TYPE='manage_fee' GROUP BY user_id) AS p24 ON p24.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS system_fee,user_id FROM dw_account_log WHERE (TYPE='manage_fee' OR TYPE='borrow_fee' or (type='vip_fee' AND remark LIKE '%扣除资金%')) GROUP BY user_id) AS p25 ON p25.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS invite_money,user_id FROM dw_account_log WHERE TYPE='invite_money' group by user_id) AS p26 ON p26.user_id=p1.user_id LEFT JOIN (SELECT SUM(money) AS vip_money,user_id FROM dw_account_log WHERE TYPE='vip_fee' AND remark LIKE '%扣除资金%' group by user_id) AS p27 ON p27.user_id=p1.user_id LEFT JOIN (SELECT  SUM(p28.interest)AS repayment_interest,SUM(p28.repayment_account-p28.interest)AS repayment_principal,p29.user_id FROM dw_borrow_repayment AS p28 LEFT JOIN dw_borrow AS p29 ON p28.borrow_id=p29.id where p29.is_flow!=1 and p28.repayment_yestime is null GROUP BY p29.user_id ) AS p30 ON p1.user_id=p30.user_id LEFT JOIN (SELECT  SUM(p31.interest)AS repayment_interest,SUM(p31.repay_account-p31.interest)AS repayment_principal,borrow.user_id FROM dw_borrow_collection  AS p31 LEFT JOIN dw_borrow_tender AS p32 ON p31.tender_id=p32.id left join dw_borrow as borrow on borrow.id=p32.borrow_id where borrow.is_flow=1 and p31.repay_yestime is null GROUP BY borrow.user_id) AS p33 ON p1.user_id=p33.user_id LEFT JOIN (SELECT  SUM(p34.interest)AS yes_repayment_interest,p35.user_id FROM dw_borrow_repayment AS p34 LEFT JOIN dw_borrow AS p35 ON p34.borrow_id=p35.id WHERE p35.is_flow!=1 and p34.repayment_yestime  IS NOT NULL GROUP BY p35.user_id ) AS p36 ON p1.user_id=p36.user_id LEFT JOIN (SELECT  SUM(p37.interest)AS yes_repayment_interest,borrow.user_id FROM dw_borrow_collection  AS p37 LEFT JOIN dw_borrow_tender AS p38 ON p37.tender_id=p38.id left join dw_borrow borrow on borrow.id=p38.borrow_id WHERE borrow.is_flow=1 and p37.repay_yestime  IS NOT NULL  GROUP BY borrow.user_id) AS p39 ON p1.user_id=p39.user_id  WHERE 1=1";

	public List<AccountLog> getAccountLogSummary(long user_id) {
		String sql = "select type,sum(money) as money from dw_account_log where user_id=? group by type ";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		List<AccountLog> list = new ArrayList<AccountLog>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) }, new RowMapper<AccountLog>() {
					public AccountLog mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						AccountLog as = new AccountLog();
						as.setType(rs.getString("type"));
						as.setMoney(rs.getDouble("money"));
						return as;
					}
				});
		return list;
	}

	public double getSum(String sql, long user_id) {
		double total = 0.0D;
		try {
			total = sum(sql, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public int getCount(String sql, long user_id) {
		int total = 0;
		try {
			total = count(sql, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public UserAccountSummary getUserAccountSummary(long user_id) {
		String sql = "select invest.*,borrow.*,late.*,account.total as accountTotal,account.use_money as accountUseMoney,account.no_use_money as accountNoUseMoney,account.collection as collectTotal  from  dw_account account left join view_borrow_sum  borrow on borrow.user_id=account.user_id left join view_invest_sum invest on invest.user_id=account.user_id left join view_late_sum late on late.user_id=account.user_id where account.user_id=?";

		logger.debug("getUserAccountSummary()" + sql);
		UserAccountSummary summary = null;
		try {
			summary = (UserAccountSummary) getJdbcTemplate().queryForObject(
					sql, new Object[] { Long.valueOf(user_id) },
					new RowMapper<UserAccountSummary>() {
						public UserAccountSummary mapRow(ResultSet rs, int num)
								throws SQLException {
							UserAccountSummary uas = new UserAccountSummary();
							uas.setUser_id(rs.getLong("user_id"));
							uas.setAccountTotal(AccountDaoImpl.this.getResultDouble(rs, "accountTotal"));
							uas.setAccountUseMoney(AccountDaoImpl.this.getResultDouble(rs, "accountUseMoney"));
							uas.setAccountNoUseMoney(AccountDaoImpl.this.getResultDouble(rs, "accountNoUseMoney"));

							uas.setBorrowTotal(AccountDaoImpl.this.getResultDouble(rs, "borrowTotal"));
							uas.setBorrowInterest(AccountDaoImpl.this.getResultDouble(rs, "borrowInterest"));
							uas.setBorrowTimes(AccountDaoImpl.this.getResultInt(rs, "borrowTimes"));

							uas.setInvestTotal(AccountDaoImpl.this.getResultDouble(rs, "investTotal"));
							uas.setInvestInterest(AccountDaoImpl.this.getResultDouble(rs, "investInterest"));
							uas.setInvestTimes(AccountDaoImpl.this.getResultInt(rs, "investTimes"));

							uas.setLateTotal(AccountDaoImpl.this.getResultDouble(rs, "lateTotal"));
							uas.setLateInterest(AccountDaoImpl.this.getResultDouble(rs, "lateInterest"));
							uas.setOverdueInterest(AccountDaoImpl.this.getResultDouble(rs, "overdueInterest"));
							uas.setAccountOwnMoney(uas.getAccountTotal()
									+ uas.getInvestTotal()
									+ uas.getInvestInterest()
									- uas.getBorrowTotal()
									- uas.getBorrowInterest());

							uas.setRepayTotal(AccountDaoImpl.this.getResultDouble(rs, "repayTotal"));
							uas.setRepayInterest(AccountDaoImpl.this.getResultDouble(rs, "repayInterest"));
							uas.setCollectTotal(AccountDaoImpl.this.getResultDouble(rs, "collectTotal"));

							uas.setNewestCollectDate(rs.getString("newestCollectDate"));
							uas.setNewestCollectMoney(rs.getDouble("newestCollectMoney"));
							uas.setNewestRepayDate(rs.getString("newestRepayDate"));
							uas.setNewestRepayMoney(rs.getDouble("newestRepayMoney"));

							return uas;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return summary;
	}

	private double getResultDouble(ResultSet rs, String name)
			throws SQLException {
		double result = 0.0D;
		try {
			result = rs.getDouble(name);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private int getResultInt(ResultSet rs, String name) throws SQLException {
		int result = 0;
		try {
			result = rs.getInt(name);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public double getBorrowSum(long user_id) {
		String sql = "select sum(account) as num from dw_borrow where status in (3,6,7) and user_id =? ";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public int getBorrowTimes(long user_id) {
		String sql = "select count(account) as times from dw_borrow where status=3 and user_id =? ";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		int times = 0;
		times = getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(user_id) });
		return times;
	}

	public double getBorrowAmount(long user_id) {
		String sql = "select borrow_amount from dw_user_cache where user_id = ? ";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double amount = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			amount = rs.getDouble("borrow_amount");
		}
		return amount;
	}

	public UserAmount getUserAmount(long user_id) {
		String sql = "select * from dw_user_amount where user_id=? ";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);

		UserAmount amount = null;
		try {
			amount = (UserAmount) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<UserAmount>() {
						public UserAmount mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserAmount ua = new UserAmount();
							ua.setId(rs.getLong("id"));
							ua.setUser_id(rs.getLong("user_id"));
							ua.setCredit(rs.getDouble("credit"));
							ua.setCredit_use(rs.getDouble("credit_use"));
							ua.setCredit_nouse(rs.getDouble("credit_nouse"));
							ua.setBorrow_vouch(rs.getDouble("borrow_vouch"));
							ua.setBorrow_vouch_use(rs.getDouble("borrow_vouch_use"));
							ua.setBorrow_vouch_nouse(rs.getDouble("borrow_vouch_nouse"));
							ua.setTender_vouch(rs.getDouble("tender_vouch"));
							ua.setTender_vouch_use(rs.getDouble("tender_vouch_use"));
							ua.setTender_vouch_nouse(rs.getDouble("tender_vouch_nouse"));
							return ua;
						}
					});
		} catch (DataAccessException e) {
			logger.info(e.getMessage());
		}
		return amount;
	}

	public AccountModel getAccount(long user_id) {
		String sql = "select p2.username,p2.realname,p1.*,p3.*,p3.account as bankaccount,p4.name as bankname from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where p1.user_id=? limit 0,1";

		AccountModel account = null;
		logger.debug(sql);
		try {
			account = (AccountModel) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<AccountModel>() {
						public AccountModel mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							AccountModel a = new AccountModel();
							a.setId(rs.getLong("id"));
							a.setUser_id(rs.getLong("user_id"));
							a.setTotal(rs.getDouble("total"));
							a.setUse_money(rs.getDouble("use_money"));
							a.setNo_use_money(rs.getDouble("no_use_money"));
							a.setCollection(rs.getDouble("collection"));
							a.setBank(rs.getString("bank"));
							a.setBankaccount(rs.getString("bankaccount"));
							a.setBankname(rs.getString("bankname"));
							a.setBranch(rs.getString("branch"));
							a.setProvince(rs.getInt("province"));
							a.setCity(rs.getInt("city"));
							a.setArea(rs.getInt("area"));
							a.setAddtime(rs.getString("addtime"));
							a.setAddip(rs.getString("addip"));
							a.setUsername(rs.getString("username"));
							a.setRealname(rs.getString("realname"));
							if (Global.getWebid().equals("wzdai")) {
								a.setHongbao(rs.getDouble("hongbao"));
							}
							return a;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return account;
	}

	public AccountModel getAccount_hongbao(long user_id) {
		String sql = "select p2.username,p2.realname,p1.*,p3.*,p3.account as bankaccount,p4.name as bankname from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where p1.user_id=? limit 0,1";

		AccountModel account = null;
		logger.debug(sql);
		try {
			account = (AccountModel) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<AccountModel>() {
						public AccountModel mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							AccountModel a = new AccountModel();
							a.setId(rs.getLong("id"));
							a.setUser_id(rs.getLong("user_id"));
							a.setTotal(rs.getDouble("total"));
							a.setUse_money(rs.getDouble("use_money"));
							a.setNo_use_money(rs.getDouble("no_use_money"));
							a.setCollection(rs.getDouble("collection"));
							a.setBank(rs.getString("bank"));
							a.setBankaccount(rs.getString("bankaccount"));
							a.setBankname(rs.getString("bankname"));
							a.setBranch(rs.getString("branch"));
							a.setProvince(rs.getInt("province"));
							a.setCity(rs.getInt("city"));
							a.setArea(rs.getInt("area"));
							a.setAddtime(rs.getString("addtime"));
							a.setAddip(rs.getString("addip"));
							a.setUsername(rs.getString("username"));
							a.setRealname(rs.getString("realname"));
							a.setHongbao(rs.getDouble("hongbao"));
							return a;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return account;
	}

	public List<Interest> getInterest(long user_id) {
		String sql = "select p1.status ,sum(p1.repay_account) as total_repay_account ,sum(p1.interest) as total_interest_account,sum(p1.capital) as total_capital_account  from dw_borrow_collection as p1 left join dw_borrow_tender as p2  on p1.tender_id = p2.id  where p2.status=1  and p2.user_id = ? and p2.borrow_id in (select id from dw_borrow where status=3)  group by p1.status";

		List<Interest> list = new ArrayList<Interest>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) }, new RowMapper<Interest>() {
					public Interest mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Interest i = new Interest();
						i.setStatus(rs.getInt("status"));
						i.setTotal_capital_account(rs.getDouble("total_capital_account"));
						i.setTotal_interest_account(rs.getDouble("total_interest_account"));
						i.setTotal_repay_account(rs.getDouble("total_repay_account"));
						return i;
					}
				});
		return list;
	}

	public AccountModel getAccountByBankAccount(long user_id, String bankAccount) {
		String sql = "select p2.username,p2.realname,p1.*,p3.*,p3.account as bankaccount,p4.name as bankname from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where p3.account=? and p3.user_id=? limit 0,1";

		AccountModel account = null;
		logger.debug(sql);
		try {
			account = (AccountModel) getJdbcTemplate().queryForObject(sql,
					new Object[] { bankAccount, Long.valueOf(user_id) },
					new RowMapper<AccountModel>() {
						public AccountModel mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							AccountModel a = new AccountModel();
							a.setId(rs.getLong("id"));
							a.setUser_id(rs.getLong("user_id"));
							a.setTotal(rs.getDouble("total"));
							a.setUse_money(rs.getDouble("use_money"));
							a.setNo_use_money(rs.getDouble("no_use_money"));
							a.setCollection(rs.getDouble("collection"));
							a.setBank(rs.getString("bank"));
							a.setBankaccount(rs.getString("bankaccount"));
							a.setBankname(rs.getString("bankname"));
							a.setBranch(rs.getString("branch"));
							a.setProvince(rs.getInt("province"));
							a.setCity(rs.getInt("city"));
							a.setArea(rs.getInt("area"));
							a.setAddtime(rs.getString("addtime"));
							a.setAddip(rs.getString("addip"));
							a.setUsername(rs.getString("username"));
							a.setRealname(rs.getString("realname"));
							return a;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return account;
	}

	public Newpay getNewpay(long user_id) {
		String sql = "select repayment_time,repayment_account from dw_borrow_repayment where status !=1 and borrow_id in (select id from dw_borrow where user_id = ? and status=3) order by repayment_time";

		Newpay newpay = null;
		try {
			newpay = (Newpay) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<Newpay>() {
						public Newpay mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Newpay n = new Newpay();
							n.setNew_repay_account(rs.getDouble("repayment_account"));
							n.setNew_repay_time(rs.getString("repayment_time"));
							return n;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return newpay;
	}

	public NewCollection getNewCollection(long user_id) {
		String sql = "select repay_time,repay_account  from dw_borrow_collection  where tender_id in (select p2.id from dw_borrow_tender  as p2 left join dw_borrow as p3 on p2.borrow_id=p3.id where p3.status=3 and p2.user_id = ? and p2.status=1) and repay_time > "
				+ new Date().getTime() / 1000L + " and status=0 order by repay_time asc";
		NewCollection newcollect = null;
		try {
			newcollect =  getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<NewCollection>() {
						public NewCollection mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							NewCollection n = new NewCollection();
							n.setNew_collection_time(rs.getString("repay_time"));
							n.setNew_collection_account(rs.getDouble("repay_account"));
							return n;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return newcollect;
	}

	public List<WaitPayment> getWaitpayment(long user_id) {
		String sql = "select status,count(1) as repay_num,sum(repayment_account) as borrow_num ,sum(capital) as capital_num ,sum(repayment_yesaccount) as borrow_yesnum from dw_borrow_repayment where borrow_id in (select id from dw_borrow where user_id = ? and status=3) group by status ";

		List<WaitPayment> list = new ArrayList<WaitPayment>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<WaitPayment>() {
						public WaitPayment mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							WaitPayment w = new WaitPayment();
							w.setBorrow_num(rs.getDouble("borrow_num"));
							w.setBorrow_yesnum(rs.getDouble("borrow_yesnum"));
							w.setCapital_num(rs.getDouble("capital_num"));
							w.setRepay_num(rs.getDouble("repay_num"));
							w.setStatus(rs.getInt("status"));
							return w;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	public User getKf(long user_id) {
		String sql = "select * from dw_user as u left join dw_user_cache as uca on uca.kefu_userid=u.user_id where uca.user_id=?";
		User u = null;
		try {
			u = (User) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(user_id) }, new UserMapper());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return u;
	}

	public int getAccountLogCount(long user_id) {
		String sql = "select count(p1.id) from dw_account_log as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user as p3 on p3.user_id=p1.to_user left join dw_linkage as p4 on p4.value=p1.type and p4.type_id=30 where 1=1 and p2.user_id=? ";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		int count = 0;
		count = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(user_id) });
		return count;
	}

	public double getFlowDayTenderCollection(long user_id) {
		String sql = "SELECT SUM(p2.wait_account) AS num FROM dw_borrow_tender  AS p2  LEFT JOIN dw_borrow AS p3 ON p2.borrow_id=p3.id WHERE p2.user_id=? AND p3.isday=1  and p3.time_limit_day=7 AND (p3.is_flow=1)";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getFlowMonthTenderCollection(long user_id) {
		String sql = "SELECT SUM(p2.wait_account) AS num FROM dw_borrow_tender  AS p2  LEFT JOIN dw_borrow AS p3 ON p2.borrow_id=p3.id WHERE p2.user_id=? AND p3.isday!=1  AND (p3.is_flow=1 OR p3.is_fast=1)";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public Account addAccount(Account account) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_account(user_id,total,use_money,no_use_money,collection) values(?,?,?,?,?)";
		final Account a = account;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getUser_id());
				ps.setDouble(2, a.getTotal());
				ps.setDouble(3, a.getUse_money());
				ps.setDouble(4, a.getNo_use_money());
				ps.setDouble(5, a.getCollection());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		account.setId(key);
		return account;
	}

	public AccountBank addBank(AccountBank bank) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_account_bank(user_id,account,bank,branch,addtime,addip,modify_username,payment,bank_realname) values(?,?,?,?,?,?,?,?,?)";
		final AccountBank b = bank;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, b.getUser_id());
				ps.setString(2, b.getAccount());
				ps.setString(3, b.getBank());
				ps.setString(4, b.getBranch());
				ps.setString(5, b.getAddtime());
				ps.setString(6, b.getAddip());
				ps.setString(7, b.getModify_username());
				ps.setLong(8, b.getPayment());
				ps.setString(9, b.getBank_realname());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		bank.setId(key);
		return bank;
	}

	public AccountBank updateBank(AccountBank bank) {
		String sql = "update dw_account_bank set user_id=?,account=?,bank=?,branch=?,addtime=?,addip=?,modify_username=?,bank_realname=?,payment=? where user_id=?";
		int ret = getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(bank.getUser_id()),
						bank.getAccount(), bank.getBank(), bank.getBranch(),
						bank.getAddtime(), bank.getAddip(),
						bank.getModify_username(), bank.getBank_realname(),
						Long.valueOf(bank.getPayment()),
						Long.valueOf(bank.getUser_id()) });
		if (ret == 1)
			return bank;
		return bank;
	}

	public AccountBank updateBank(AccountBank bank, int init) {
		String sql = "update dw_account_bank set user_id=?,account=?,bank=?,branch=?,addtime=?,addip=?,modify_username=?,bank_realname=?,payment=? where 1=? and id=?";
		int ret = getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(bank.getUser_id()),
						bank.getAccount(), bank.getBank(), bank.getBranch(),
						bank.getAddtime(), bank.getAddip(),
						bank.getModify_username(), bank.getBank_realname(),
						Long.valueOf(bank.getPayment()), Integer.valueOf(init),
						Long.valueOf(bank.getId()) });
		if (ret == 1)
			return bank;
		return bank;
	}

	public void updateAccount(Account act) {
		String sql = "update dw_account set total=?,use_money=?,no_use_money=?,collection=? where user_id=?";
		logger.info("SQL" + sql);
		logger.info("SQL:" + act.getUser_id());
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(act.getTotal()),
						Double.valueOf(act.getUse_money()),
						Double.valueOf(act.getNo_use_money()),
						Double.valueOf(act.getCollection()),
						Long.valueOf(act.getUser_id()) });
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar, long user_id) {
		String sql = "update dw_account set total=total+?,use_money=use_money+?,no_use_money=no_use_money+? where user_id=?";
		logger.info("SQL" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Long.valueOf(user_id) });
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar,
			long user_id, double hongbao, int aa) {
		String sql = "update dw_account set total=total+?,use_money=use_money+?,no_use_money=no_use_money+?,hongbao=hongbao+? where user_id=? and 1=? ";
		logger.info("SQL" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Double.valueOf(hongbao), Long.valueOf(user_id),
						Integer.valueOf(aa) });
	}

	public int updateAccountNotZero(double totalVar, double useVar,
			double nouseVar, long user_id) {
		String sql = "update dw_account set total=total+?,use_money=use_money+?,no_use_money=no_use_money+? where user_id=? and use_money+?>=0";
		logger.info("SQL" + sql);
		int count = 0;
		count = getJdbcTemplate().update(sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Long.valueOf(user_id), Double.valueOf(useVar) });
		return count;
	}

	public AccountBank updateBankByAccount(AccountBank bank, String bankaccount) {
		String sql = "update dw_account_bank set account=?,bank=?,branch=?,addtime=?,addip=?,modify_username=?,payment=? where account=? and user_id=? ";
		int ret = getJdbcTemplate().update(sql,
				new Object[] { bank.getAccount(), bank.getBank(),
						bank.getBranch(), bank.getAddtime(), bank.getAddip(),
						bank.getModify_username(),
						Long.valueOf(bank.getPayment()), bankaccount,
						Long.valueOf(bank.getUser_id()) });
		System.out.println(sql + "=2345==" + bank.getUser_id());
		if (ret == 1)
			return bank;
		return bank;
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar,
			double collectVar, long user_id) {
		String sql = "update dw_account set total=total+?,use_money=use_money+?,no_use_money=no_use_money+?,collection=collection+? where user_id=?";

		logger.info("SQL" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Double.valueOf(collectVar), Long.valueOf(user_id) });
	}

	public void updateAccount(double totalVar, double useVar, double nouseVar,
			double tender_award, long user_id, int a) {
		String sql = "update dw_account set total=total+?,use_money=use_money+?,no_use_money=no_use_money+?,total_tender_award=total_tender_award+? where user_id=? and 1="
				+ a;
		logger.info("SQL" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Double.valueOf(tender_award), Long.valueOf(user_id) });
	}

	public List<AccountSumModel> getUserAcount(int start, int end, SearchParam param) {
		String selectSql = "select p1.id,p2.user_id,p2.username,p2.realname,p1.total,p1.use_money,p1.no_use_money,p1.collection as wait_collect,repay.wait_repay ";

		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		System.out.println(searchSql + "===========================");

		String groupSql = "group by p1.user_id ";
		String orderSql = "order by p1.user_id desc ";
		String limitSql = "limit ?,?";
		//StringBuffer sb = new StringBuffer(selectSql);
		String querySql = selectSql + this.getUserAcountSql + searchSql + groupSql + orderSql + limitSql;
		logger.debug("getUserAcount():" + querySql);
		List<AccountSumModel> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<AccountSumModel>() {
					public AccountSumModel mapRow(ResultSet rs, int num)
							throws SQLException {
						AccountSumModel s = new AccountSumModel();
						s.setId(rs.getLong("id"));
						s.setUser_id(rs.getLong("user_id"));
						s.setUsername(rs.getString("username"));
						s.setRealname(rs.getString("realname"));
						s.setTotal(rs.getDouble("total"));
						s.setUse_money(rs.getDouble("use_money"));
						s.setNo_use_money(rs.getDouble("no_use_money"));
						s.setWait_collect(rs.getDouble("wait_collect"));
						s.setWait_repay(rs.getDouble("wait_repay"));
						return s;
					}
				});
		return list;
	}

	public List<AccountOneDayModel> getUserOneDayAcount(int start, int end, SearchParam param) {
		String selectSql = "select * from dw_account_tj p1 left join dw_user p2 on p2.user_id=p1.user_id where 1=1 ";
		String searchSql = param.getSearchParamSql();
		String limitSql = "limit ?,?";
		//StringBuffer sb = new StringBuffer(selectSql);
		String querySql = selectSql + searchSql + limitSql;
		logger.debug("getUserAcount():" + querySql);
		List<AccountOneDayModel> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<AccountOneDayModel>() {
					public AccountOneDayModel mapRow(ResultSet rs, int num)
							throws SQLException {
						AccountOneDayModel s = new AccountOneDayModel();
						s.setId(rs.getLong("id"));
						s.setUser_id(rs.getLong("user_id"));
						s.setUsername(rs.getString("username"));
						s.setRealname(rs.getString("realname"));
						s.setTotal(rs.getDouble("total"));
						s.setUse_money(rs.getDouble("use_money"));
						s.setJin_money(rs.getDouble("jin_money"));
						s.setNo_use_money(rs.getDouble("no_use_money"));
						s.setWait_collect(rs.getDouble("collection"));
						s.setWait_repay(rs.getDouble("wait_repayMoney"));
						s.setAddtime(rs.getString("addtime"));
						return s;
					}
				});
		return list;
	}

	public int getUserAccountCount(SearchParam param) {
		String selectSql = "select count(p2.user_id) from dw_user p2 where 1=1 ";
		String searchSql = param.getSearchParamSql();
		searchSql = searchSql.replace("p1", "p2");
		//StringBuffer sb = new StringBuffer(selectSql);
		String querySql = selectSql + searchSql;
		logger.debug("getUserAccountCount():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public int getUserOneDayAccountCount(SearchParam param) {
		String selectSql = "select count(1) from dw_account_tj p1 left join dw_user p2 on p2.user_id=p1.user_id where 1=1 ";
		String searchSql = param.getSearchParamSql();
		//StringBuffer sb = new StringBuffer(selectSql);
		String querySql = selectSql + searchSql;
		logger.debug("getUserOneDayAccountCount():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public List<AccountSumModel> getUserAcount(SearchParam param) {
		String selectSql = "select p1.id,p2.user_id,p2.username,p2.realname,p1.total,p1.use_money,p1.no_use_money,p1.collection as wait_collect,repay.wait_repay ";

		String searchSql = param.getSearchParamSql();
		String groupSql = "group by p1.user_id ";
		String orderSql = "order by p1.user_id desc ";
		//StringBuffer sb = new StringBuffer(selectSql);
		String querySql = selectSql + this.getUserAcountSql + searchSql + groupSql + orderSql;
		logger.debug("getUserAcount():" + querySql);
		List<AccountSumModel> list = getJdbcTemplate().query(querySql, new RowMapper<AccountSumModel>() {
			public AccountSumModel mapRow(ResultSet rs, int num)
					throws SQLException {
				AccountSumModel s = new AccountSumModel();
				s.setId(rs.getLong("id"));
				s.setUser_id(rs.getLong("user_id"));
				s.setUsername(rs.getString("username"));
				s.setRealname(rs.getString("realname"));
				s.setTotal(rs.getDouble("total"));
				s.setUse_money(rs.getDouble("use_money"));
				s.setNo_use_money(rs.getDouble("no_use_money"));
				s.setWait_collect(rs.getDouble("wait_collect"));
				s.setWait_repay(rs.getDouble("wait_repay"));
				s.setNet_assets(s.getTotal() - s.getWait_repay());
				return s;
			}
		});
		return list;
	}

	public AccountModel getAccountList(long user_id) {
		String sql = "select p2.username,p1.*,p3.*,p3.account as bankaccount,p4.name as bankname from dw_account as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_account_bank as p3 on p1.user_id=p3.user_id left join dw_linkage as p4 on p4.id=p3.bank and p4.type_id=25 where p1.user_id=?";

		AccountModel account = null;
		logger.debug(sql);
		try {
			account = getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) }, new RowMapper<AccountModel>() {
						public AccountModel mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							AccountModel a = new AccountModel();
							a.setId(rs.getLong("id"));
							a.setUser_id(rs.getLong("user_id"));
							a.setTotal(rs.getDouble("total"));
							a.setUse_money(rs.getDouble("use_money"));
							a.setNo_use_money(rs.getDouble("no_use_money"));
							a.setCollection(rs.getDouble("collection"));
							a.setBank(rs.getString("bank"));
							a.setBankaccount(rs.getString("bankaccount"));
							a.setBankname(rs.getString("bankname"));
							a.setBranch(rs.getString("branch"));
							a.setProvince(rs.getInt("province"));
							a.setCity(rs.getInt("city"));
							a.setArea(rs.getInt("area"));
							a.setAddtime(rs.getString("addtime"));
							a.setAddip(rs.getString("addip"));
							a.setUsername(rs.getString("username"));
							return a;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return account;
	}

	public List<AccountModel> getAccountList(int start, int end, SearchParam param) {
		String searchSql = param.getSearchParamSql();

		String orderSql = "order by p1.user_id desc ";
		String limitSql = "limit ?,?";
		if (!param.getUsername().equals("")) {
			searchSql = " and p2.username='" + param.getUsername() + "' ";
		}
		String querySql = this.accountBanksql + searchSql + orderSql + limitSql;
		logger.debug("getAccountList():" + querySql);
		List<AccountModel> list = new ArrayList<AccountModel>();
		try {
			list = getJdbcTemplate().query(querySql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, this.accountModelMapper);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	public List<AccountModel> getAccountList(SearchParam param) {
		String searchSql = param.getSearchParamSql();

		String orderSql = "order by p1.user_id desc ";
		if (!param.getUsername().equals("")) {
			searchSql = " and p2.username='" + param.getUsername() + "' ";
		}
		String querySql = this.accountBanksql + searchSql + orderSql;
		logger.debug("getAccountList():" + querySql);
		List<AccountModel> list = new ArrayList<AccountModel>();
		try {
			list = getJdbcTemplate().query(querySql, new Object[0], this.accountModelMapper);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	public int getAccountCount(SearchParam param) {
		int total = 0;
		String searchSql = param.getSearchParamSql();
		String querySql = this.accountBankCountsql + searchSql;

		logger.debug("getAccountList():" + querySql);
		try {
			total = count(querySql);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public void addHuikuanMoney(Huikuan huikuan) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String huikuansql = "insert into dw_huikuan(huikuan_money,huikuan_award,user_id,status,remark,addtime,cash_id) values(?,?,?,?,?,?,?)";
		final Huikuan h = huikuan;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(huikuansql, 1);
				ps.setString(1, h.getHuikuan_money());
				ps.setString(2, h.getHuikuan_award());
				ps.setLong(3, h.getUser_id());
				ps.setString(4, h.getStatus());
				ps.setString(5, h.getRemark());
				ps.setString(6, h.getAddtime());
				ps.setLong(7, h.getCash_id());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		huikuan.setId(key);
	}

	public List<HuikuanModel> huikuanlist(int start, int end, SearchParam param) {
		String sql = "select p1.*,p2.username from dw_huikuan p1 left join dw_user p2 on p1.user_id=p2.user_id where 1=1 ";
		List<HuikuanModel> list = null;
		String searchSql = param.getSearchParamSql();
		String orderSql = " order by p1.addtime desc ";
		String limitSql = "  limit ?,?";
		String querySql = sql + searchSql + orderSql + limitSql;
		try {
			list = getJdbcTemplate().query(querySql,
							new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, new RowMapper<HuikuanModel>() {
								public HuikuanModel mapRow(ResultSet rs, int num)
										throws SQLException {
									HuikuanModel s = new HuikuanModel();
									s.setId(rs.getLong("id"));
									s.setUser_id(rs.getLong("user_id"));
									s.setHuikuan_money(rs.getString("huikuan_money"));
									s.setHuikuan_award(rs.getString("huikuan_award"));
									s.setStatus(rs.getString("status"));
									s.setRemark(rs.getString("remark"));
									s.setAddtime(rs.getString("addtime"));
									s.setUsername(rs.getString("username"));
									s.setCash_id(rs.getLong("cash_id"));
									return s;
								}
							});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<HuikuanModel> huikuanlist(SearchParam param) {
		String sql = "select p1.*,p2.username from dw_huikuan p1 left join dw_user p2 on p1.user_id=p2.user_id where 1=1 ";
		List<HuikuanModel> list = null;
		String searchSql = param.getSearchParamSql();
		String orderSql = " order by p1.addtime desc ";
		String querySql = sql + searchSql + orderSql;
		try {
			list = getJdbcTemplate().query(querySql, new Object[0],
					new RowMapper<HuikuanModel>() {
						public HuikuanModel mapRow(ResultSet rs, int num)
								throws SQLException {
							HuikuanModel s = new HuikuanModel();
							s.setId(rs.getLong("id"));
							s.setUser_id(rs.getLong("user_id"));
							s.setHuikuan_money(rs.getString("huikuan_money"));
							s.setHuikuan_award(rs.getString("huikuan_award"));
							s.setStatus(rs.getString("status"));
							s.setRemark(rs.getString("remark"));
							s.setAddtime(rs.getString("addtime"));
							s.setUsername(rs.getString("username"));
							s.setCash_id(rs.getLong("cash_id"));
							return s;
						}
					});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public int gethuikuanCount(SearchParam param) {
		int total = 0;
		String countSql = "select count(1) from dw_huikuan p1 left join dw_user p2 on p1.user_id=p2.user_id where 1=1";
		String sql = countSql + param.getSearchParamSql();
		try {
			total = count(sql);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public double gethuikuanSum(long user_id, int status) {
		double total = 0.0D;
		String sql = "select sum(p1.huikuan_money) as num from dw_huikuan p1  where p1.user_id=? and p1.status=? ";
		try {
			total = sum(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public double gethuikuanSum(long user_id) {
		double total = 0.0D;
		String sql = "select sum(p1.huikuan_money) as num from dw_huikuan p1  where p1.user_id=? and (p1.status=1 or p1.status=0) ";
		try {
			total = sum(sql, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public HuikuanModel viewhuikuan(int id) {
		String sql = "select p1.*,p2.username from dw_huikuan p1 left join dw_user p2 on p1.user_id=p2.user_id where p1.id=?";
		HuikuanModel huikuan = (HuikuanModel) getJdbcTemplate().queryForObject(
				sql, new Object[] { Integer.valueOf(id) }, new RowMapper<HuikuanModel>() {
					public HuikuanModel mapRow(ResultSet rs, int num)
							throws SQLException {
						HuikuanModel s = new HuikuanModel();
						s.setId(rs.getLong("id"));
						s.setUser_id(rs.getLong("user_id"));
						s.setHuikuan_money(rs.getString("huikuan_money"));
						s.setHuikuan_award(rs.getString("huikuan_award"));
						s.setStatus(rs.getString("status"));
						s.setRemark(rs.getString("remark"));
						s.setAddtime(rs.getString("addtime"));
						s.setUsername(rs.getString("username"));
						s.setCash_id(rs.getLong("cash_id"));
						return s;
					}
				});
		return huikuan;
	}

	public void verifyhuikuan(Huikuan huikuan) {
		String sql = "update dw_huikuan set user_id=?,huikuan_money=?,huikuan_award=?,status=?,remark=?,addtime=? where id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(huikuan.getUser_id()),
						huikuan.getHuikuan_money(), huikuan.getHuikuan_award(),
						huikuan.getStatus(), huikuan.getRemark(),
						huikuan.getAddtime(), Long.valueOf(huikuan.getId()) });
	}

	public RepaySummary getRepaySummary(long user_id) {
		String sql = "select sum(t.repayment_account) as repayTotal, sum(t.interest) as repayInterest, min(t.repayment_time) as repayTime from dw_borrow_repayment t left join dw_borrow b on b.id = t.borrow_id where t.repayment_yesaccount <= 0  and b.status in (3,6,7) and t.status!=1 and b.user_id =?";

		logger.info("RepaySummary SQL:" + sql);
		double repayAccount = 0.0D;

		String sql2 = "SELECT t.repayment_account as num FROM dw_borrow_repayment t LEFT JOIN dw_borrow b ON b.id=t.borrow_id WHERE t.repayment_yesaccount<=0 AND b.status IN(3,6,7) AND t.status!=1 AND b.user_id=? AND t.repayment_time=(SELECT MIN(t.repayment_time) AS repayTime FROM dw_borrow_repayment t LEFT JOIN dw_borrow b ON b.id = t.borrow_id WHERE t.repayment_yesaccount <= 0 AND b.status IN (3,6,7) AND t.status!=1 AND b.user_id =?)";

		repayAccount = sum(sql2, new Object[] { Long.valueOf(user_id), Long.valueOf(user_id) });
		RepaySummary r = new RepaySummary();
		r.setUser_id(user_id);
		try {
			r = getJdbcTemplate().queryForObject(sql,
					new RowMapper<RepaySummary>() {
						public RepaySummary mapRow(ResultSet rs, int num)
								throws SQLException {
							RepaySummary r = new RepaySummary();
							r.setRepayTotal(rs.getDouble("repayTotal"));
							r.setRepayInterest(rs.getDouble("repayInterest"));
							r.setRepayTime(rs.getString("repayTime"));
							return r;
						}
					}, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		r.setRepayAccount(repayAccount);
		return r;
	}

	public BorrowSummary getBorrowSummary(long user_id) {
		String sql = "select sum(b.account) as borrowTotal, sum(b.repayment_account-b.account) as borrowInterest, count(1)as borrowTimes,b.user_id  from dw_borrow b where b.status in(3,6,7,8) and b.user_id=? group by b.user_id";

		BorrowSummary b = new BorrowSummary();
		b.setUser_id(user_id);
		try {
			b = (BorrowSummary) getJdbcTemplate().queryForObject(sql,
					new RowMapper<BorrowSummary>() {
						public BorrowSummary mapRow(ResultSet rs, int num)
								throws SQLException {
							BorrowSummary b = new BorrowSummary();
							b.setBorrowTotal(rs.getDouble("borrowTotal"));
							b.setBorrowInterest(rs.getDouble("borrowInterest"));
							b.setBorrowTimes(rs.getInt("borrowTimes"));
							return b;
						}
					}, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return b;
	}

	public InvestSummary getInvestSummary(long user_id) {
		String sql = "select sum(c.repay_account) as investTotal, sum(c.interest) as investInterest, count(1) as investTimes,t.user_id as user_id from dw_borrow_tender t left join dw_borrow b on b.id=t.borrow_id left join dw_borrow_collection c on c.tender_id=t.id where t.status=1 and c.status!=1 and b.status in(1,3,6,7,8) and t.user_id=?";

		InvestSummary i = new InvestSummary();
		i.setUser_id(user_id);
		try {
			i = (InvestSummary) getJdbcTemplate().queryForObject(sql,
					new RowMapper<InvestSummary>() {
						public InvestSummary mapRow(ResultSet rs, int num)
								throws SQLException {
							InvestSummary i = new InvestSummary();
							i.setInvestTotal(rs.getDouble("investTotal"));
							i.setInvestInterest(rs.getDouble("investInterest"));
							i.setInvestTimes(rs.getInt("investTimes"));
							return i;
						}
					}, new Object[] { Long.valueOf(user_id) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return i;
	}

	public CollectSummary getCollectSummary(long user_id) {
		CollectSummary c = new CollectSummary();
		c.setUser_id(user_id);

		String collectInterestTotalSql = "select sum(wait_interest) as num from dw_borrow_tender where user_id=?";
		double collectInterestTotal = getSum(collectInterestTotalSql, user_id);
		c.setCollectInterest(collectInterestTotal);

		String sql = "select * from dw_borrow_collection where id=(select c.id from dw_borrow_tender  t inner join dw_borrow b on b.id=t.borrow_id and (b.status in(3,6,7) or ((b.status=1 or b.status=8) and b.is_flow=1)) inner join dw_borrow_collection c on c.tender_id=t.id and c.status=0  where t.user_id=? group by c.id order by c.repay_time asc limit 0,1)";

		Collection tc = new Collection();
		try {
			tc = (Collection) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(user_id) },
					new CollectionMapper());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		c.setCollectMoney(NumberUtils.getDouble(tc.getRepay_account()));
		c.setCollectTime(tc.getRepay_time());
		return c;
	}

	public double getCollectionSum(long user_id, int status) {
		String sql = "SELECT SUM(a.`repay_account`) as num FROM dw_borrow_collection a LEFT JOIN dw_borrow_tender b ON a.`tender_id`=b.id WHERE b.`user_id`=?  AND a.status=?";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double gethuikuanSum(long user_id, int status, long start) {
		double total = 0.0D;
		String sql = "select sum(p1.huikuan_money) as num from dw_huikuan p1  where p1.user_id=? and p1.status=? and p1.addtime>?";
		try {
			total = sum(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status), Long.valueOf(start) });
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public HuikuanModel getHuikuanByCashid(long cash_id) {
		String sql = "select p1.*,p2.username from dw_huikuan p1 left join dw_user p2 on p1.user_id=p2.user_id where p1.cash_id=?";
		Object[] parmas = new Object[] { Long.valueOf(cash_id) };
		List<HuikuanModel> list = getJdbcTemplate().query(sql, parmas, new RowMapper<HuikuanModel>() {
					
			public HuikuanModel mapRow(ResultSet rs, int num)
							throws SQLException {
				
				HuikuanModel s = new HuikuanModel();
				s.setId(rs.getLong("id"));
				s.setUser_id(rs.getLong("user_id"));
				s.setHuikuan_money(rs.getString("huikuan_money"));
				s.setHuikuan_award(rs.getString("huikuan_award"));
				s.setStatus(rs.getString("status"));
				s.setRemark(rs.getString("remark"));
				s.setAddtime(rs.getString("addtime"));
				s.setUsername(rs.getString("username"));
				s.setCash_id(rs.getLong("cash_id"));
				return s;
			}
		});
		
		 if(list!=null &&list.size()>0){
			   return list.get(0);
		  }
		
		return null;
	}

	public double getCollectionSum(long user_id, int status, long startTime) {
		String sql = "SELECT SUM(a.`repay_account`) as num FROM dw_borrow_collection a LEFT JOIN dw_borrow_tender b ON a.`tender_id`=b.id WHERE b.`user_id`=?  AND a.status=? and a.repay_yestime>?";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		logger.info("SQL:" + startTime);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getCollectionSumNoJinAndSecond(long user_id, int status,
			long startTime) {
		String sql = "select SUM(c.`repay_account`) as num from dw_borrow_tender t left join dw_borrow_collection c on t.id=c.tender_id and c.status=? and c.repay_yestime>? left join dw_borrow b on b.id= t.borrow_id where b.is_mb!=1 and b.is_jin!=1  and t.user_id=? ";

		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		logger.info("SQL:" + startTime);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Integer.valueOf(status), Long.valueOf(startTime), Long.valueOf(user_id) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getCollectionSumNoJinAndSecond(long user_id, int status,
			int isday, long startTime) {
		double borrow_fee = Global.getDouble(Constant.BORROW_FEE);
		String sql = "select SUM(c.`repay_account`-c.interest*?) as num from dw_borrow_tender t left join dw_borrow_collection c on t.id=c.tender_id and c.status=? and c.repay_yestime>? left join dw_borrow b on b.id= t.borrow_id where b.is_mb!=1 and b.is_jin!=1  and t.user_id=? ";

		Object[] args = { Double.valueOf(borrow_fee), Integer.valueOf(status), Long.valueOf(startTime), Long.valueOf(user_id) };
		if (isday == 0) {
			sql = sql + " and b.isday=?";
			args = new Object[] { Double.valueOf(borrow_fee), Integer.valueOf(status), Long.valueOf(startTime), Long.valueOf(user_id), Integer.valueOf(isday) };
		}
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		logger.info("SQL:" + startTime);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, args);
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public List<AccountOneDayModel> getUserOneDayAcount(SearchParam param) {
		String selectSql = "select * from dw_account_tj ";
		String searchSql = param.getSearchParamSql();
		String querySql = selectSql + searchSql;
		logger.debug("getUserAcount():" + querySql);
		List<AccountOneDayModel> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<AccountOneDayModel>() {
					public AccountOneDayModel mapRow(ResultSet rs, int num)
							throws SQLException {
						AccountOneDayModel s = new AccountOneDayModel();
						s.setId(rs.getLong("id"));
						s.setUser_id(rs.getLong("user_id"));
						s.setUsername(rs.getString("username"));
						s.setRealname(rs.getString("realname"));
						s.setTotal(rs.getDouble("total"));
						s.setUse_money(rs.getDouble("use_money"));
						s.setJin_money(rs.getDouble("jin_money"));
						s.setNo_use_money(rs.getDouble("no_use_money"));
						s.setWait_collect(rs.getDouble("collection"));
						s.setWait_repay(rs.getDouble("wait_repayMoney"));
						s.setAddtime(rs.getString("addtime"));
						return s;
					}
				});
		return list;
	}

	public int getFriendTiChengAccountCount(SearchParam param) {
		String selectSql = "select count(1) from (SELECT invite.username as usernames,SUM(p2.account) as money,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m') as addtimes,p1.invite_userid as invite_userid FROM  dw_borrow_tender  AS p2 LEFT JOIN dw_user AS p1 ON p1.user_id=p2.user_id LEFT JOIN dw_borrow AS p3 ON p3.id=p2.borrow_id LEFT JOIN dw_user AS invite ON invite.user_id=p1.invite_userid WHERE p1.invite_userid IS NOT NULL AND p1.invite_userid>0  AND ((p3.isday IS NULL) OR (p3.isday<>1)) GROUP BY  p1.invite_userid ,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m')) as p where 1=1 ";

		StringBuffer sb = new StringBuffer(selectSql);

		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p.usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = sb.toString();
		logger.debug("getTiChengAccountCount():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public int getTiChengAccountCount(SearchParam param) {
		String selectSql = "select count(1) from view_tc_backend where 1=1 ";
		StringBuffer sb = new StringBuffer(selectSql);

		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = sb.toString();
		logger.debug("getTiChengAccountCount():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public List<TiChengModel> getTiChengAcount(SearchParam param) {
		String selectSql = "select * from view_tc_backend ";

		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = sb.toString();
		logger.debug("getUserAcount():" + querySql);
		List<TiChengModel> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<TiChengModel>() {
					public TiChengModel mapRow(ResultSet rs, int num)
							throws SQLException {
						TiChengModel s = new TiChengModel();
						s.setMoney(rs.getString("money"));
						s.setUsername(rs.getString("usernames"));
						s.setAddtimes(rs.getString("addtimes"));
						return s;
					}
				});
		return list;
	}

	public List<TiChengModel> getFriendTiChengAcount(SearchParam param) {
		String selectSql = "select  p.* from (SELECT invite.username as usernames,SUM(p2.account) as money,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m') as addtimes,p1.invite_userid as invite_userid FROM  dw_borrow_tender  AS p2 LEFT JOIN dw_user AS p1 ON p1.user_id=p2.user_id LEFT JOIN dw_borrow AS p3 ON p3.id=p2.borrow_id LEFT JOIN dw_user AS invite ON invite.user_id=p1.invite_userid WHERE p1.invite_userid IS NOT NULL AND p1.invite_userid>0  AND ((p3.isday IS NULL) OR (p3.isday<>1)) GROUP BY  p1.invite_userid ,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m')) as p where 1=1 ";

		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p.usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = sb.toString();
		logger.debug("getUserAcount():" + querySql);
		List<TiChengModel> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<TiChengModel>() {
					public TiChengModel mapRow(ResultSet rs, int num)
							throws SQLException {
						TiChengModel s = new TiChengModel();
						s.setMoney(rs.getString("money"));
						s.setUsername(rs.getString("usernames"));
						s.setAddtimes(rs.getString("addtimes"));
						return s;
					}
				});
		return list;
	}

	public List<TiChengModel> getFriendTiChengAccountList(int start, int end,
			SearchParam param) {
		String selectSql = "select  p.* from (SELECT invite.username as usernames,SUM(p2.account) as money,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m') as addtimes,p1.invite_userid as invite_userid FROM  dw_borrow_tender  AS p2 LEFT JOIN dw_user AS p1 ON p1.user_id=p2.user_id LEFT JOIN dw_borrow AS p3 ON p3.id=p2.borrow_id LEFT JOIN dw_user AS invite ON invite.user_id=p1.invite_userid WHERE p1.invite_userid IS NOT NULL AND p1.invite_userid>0  AND ((p3.isday IS NULL) OR (p3.isday<>1)) GROUP BY  p1.invite_userid ,DATE_FORMAT(FROM_UNIXTIME(p2.addtime),'%Y-%m')) as p  where 1=1 ";

		String limitSql = " limit ?,?";
		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p.usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = limitSql;
		logger.debug("getTiChengAccountCount():" + querySql);
		List<TiChengModel> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<TiChengModel>() {
					public TiChengModel mapRow(ResultSet rs, int num)
							throws SQLException {
						TiChengModel s = new TiChengModel();
						s.setMoney(rs.getString("money"));
						s.setUsername(rs.getString("usernames"));
						s.setAddtimes(rs.getString("addtimes"));
						return s;
					}
				});
		return list;
	}

	public List<TiChengModel> getTiChengAccountList(int start, int end, SearchParam param) {
		String selectSql = "select * from view_tc_backend where 1=1 ";
		String limitSql = "limit ?,?";
		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and usernames like '%").append(param.getUsername()).append("%'");
		}
		String querySql = limitSql;
		logger.debug("getTiChengAccountCount():" + querySql);
		List<TiChengModel> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<TiChengModel>() {
					public TiChengModel mapRow(ResultSet rs, int num)
							throws SQLException {
						TiChengModel s = new TiChengModel();
						s.setMoney(rs.getString("money"));
						s.setUsername(rs.getString("usernames"));
						s.setAddtimes(rs.getString("addtimes"));
						return s;
					}
				});
		return list;
	}

	public List<HongBaoModel> getHongBaoList(int start, int end, SearchParam param) {
		String selectSql = "select p.*,p1.username,p4.name as typename,p5.hongbao as hongbao from dw_hongbao as p left join dw_user as p1 on p.user_id=p1.user_id left join dw_linkage as p4  on p.type=p4.value left join dw_account as p5 on p1.user_id=p5.user_id where 1=1 ";
		String limitSql = " limit ?,?";
		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p1.username='" + param.getUsername() + "'");
		}
		String orderSql = " order by p.addtime desc ";
		sb.append(orderSql);
		String querySql = limitSql;
		logger.debug("getHongBaoList():" + querySql);
		List<HongBaoModel> list = getJdbcTemplate().query(querySql,
				new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<HongBaoModel>() {
					public HongBaoModel mapRow(ResultSet rs, int num)
							throws SQLException {
						HongBaoModel s = new HongBaoModel();
						s.setId(rs.getLong("id"));
						s.setAddtime(rs.getString("addtime"));
						s.setRemark(rs.getString("remark"));
						s.setHongbao_money(rs.getDouble("hongbao_money"));
						s.setUsername(rs.getString("username"));
						s.setTypename(rs.getString("typename"));
						s.setHongbao(rs.getString("hongbao"));
						return s;
					}
				});
		return list;
	}

	public int getHongBaoListCount(SearchParam param) {
		String selectSql = "select count(1) from dw_hongbao as p left join dw_user as p1 on p.user_id=p1.user_id left join dw_linkage as p4 on p.type=p4.value left join dw_account as p5 on p1.user_id=p5.user_id where 1=1  ";
		StringBuffer sb = new StringBuffer(selectSql);

		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p1.username='" + param.getUsername() + "'");
		}
		String querySql = sb.toString();
		logger.debug("getHongBaoCount():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public int getAccountReconciliationListCount(SearchParam param) {
		String selectSql = "select count(1)";
		StringBuffer sb = new StringBuffer(selectSql);

		sb.append(" from dw_user as p1 where 1=1 ");
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p1.username='" + param.getUsername() + "'");
		}
		String querySql = sb.toString();
		logger.debug("getAccountReconciliationSql():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public List<AccountReconciliationModel> getAccountReconciliationList(int start, int end,
			SearchParam param) {
		String selectSql = "SELECT p1.username,p2.*,p4.recharge_money,p6.log_recharge_money,p8.up_recharge_money,p10.down_recharge_money,p12.houtai_recharge_money,p14.allcollection,p16.cash_money,p19.invest_award,p21.invest_yeswait_interest,p21.wait_interest,p22.borrow_award,p23.borrow_fee,p24.manage_fee,(p25.system_fee+p16.cash_fee) as system_fee,p26.invite_money,p27.vip_money,p30.repayment_interest as repayment_interest,p33.repayment_interest AS flow_repayment_interest,p30.repayment_principal as repayment_principal,p33.repayment_principal AS flow_repayment_principal,p36.yes_repayment_interest as yes_repayment_interest, p39.yes_repayment_interest AS flow_yes_repayment_interest";
		String limitSql = " limit ?,?";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.accountReconciliationSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p1.username='" + param.getUsername() + "'");
		}
		String querySql = limitSql;
		logger.debug("getAccountReconciliationSql():" + querySql);
		List<AccountReconciliationModel> list = getJdbcTemplate().query(querySql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, new AccountReconciliationMapper());
		return list;
	}

	public List<HongBaoModel> getHongBaoList(SearchParam param) {
		String selectSql = "select p.*,p1.username,p4.name as typename,p5.hongbao as hongbao from dw_hongbao as p left join dw_user as p1 on p.user_id=p1.user_id left join dw_linkage as p4  on p.type=p4.value left join dw_account as p5 on p1.user_id=p5.user_id where 1=1 ";

		StringBuffer sb = new StringBuffer(selectSql);
		if (!StringUtils.isBlank(param.getUsername())) {
			sb.append(" and p1.username='" + param.getUsername() + "'");
		}
		String orderSql = " order by p.addtime desc ";
		sb.append(orderSql);
		String querySql = sb.toString();
		logger.debug("getUserAcount():" + querySql);
		List<HongBaoModel> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<HongBaoModel>() {
					public HongBaoModel mapRow(ResultSet rs, int num)
							throws SQLException {
						HongBaoModel s = new HongBaoModel();
						s.setId(rs.getLong("id"));
						s.setAddtime(rs.getString("addtime"));
						s.setRemark(rs.getString("remark"));
						s.setHongbao_money(rs.getDouble("hongbao_money"));
						s.setUsername(rs.getString("username"));
						s.setTypename(rs.getString("typename"));
						s.setHongbao(rs.getString("hongbao"));
						return s;
					}
				});
		return list;
	}

	public void addFreeCash(double free_cash, long user_id) {
		String sql = "select id from dw_account_cash where user_id=? order by id desc limit 1";
		int id = 0;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			id = rs.getInt("id");
		}
		String sql2 = "update dw_account_cash set free_cash=? where id=" + id;
		getJdbcTemplate().update(sql2,
				new Object[] { Double.valueOf(free_cash) });
	}

	public double getFreeCash(long user_id) {
		String sql = "select free_cash from dw_account_cash where user_id=? order by id desc limit 1";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		double free_cash = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id) });
		if (rs.next()) {
			free_cash = rs.getDouble("free_cash");
		}
		return free_cash;
	}

	public double getFlowDayTenderCollection(long user_id, int day) {
		String sql = "SELECT SUM(p2.wait_account) AS num FROM dw_borrow_tender  AS p2  LEFT JOIN dw_borrow AS p3 ON p2.borrow_id=p3.id WHERE (p3.is_flow=1) and p2.user_id=? AND p3.isday=1  and p3.time_limit_day=? ";
		logger.info("SQL:" + sql);
		logger.info("SQL:" + user_id);
		logger.info("SQL:" + day);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(user_id), Integer.valueOf(day) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public double getFreeCashSum(long user_id) {
		String sql = "select sum(p1.free_cash) as num from dw_account_cash as p1 where p1.status=1 and p1.user_id=? ";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public int getAccountBankCount(long user_id) {
		String sql = "select count(*) from dw_account_bank where user_id=?";
		logger.debug("SQL:" + sql);
		int total = 0;
		total = getJdbcTemplate().queryForInt(sql,
				new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public DownLineBank addDownRechargeBank(DownLineBank bank) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_downline_bank(bank_name,bank,account) values(?,?,?)";
		final DownLineBank b = bank;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setString(1, b.getBank_name());
				ps.setString(2, b.getBank_account());
				ps.setString(3, b.getBank_persion());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		bank.setId(key);
		return bank;
	}

	public DownLineBank updateDownRechargeBank(DownLineBank bank) {
		String sql = "update dw_downline_bank set bank_name=?,bank_account=?,bank_persion=? where id=?";
		int ret = getJdbcTemplate().update(sql,
				new Object[] { bank.getBank_name(), bank.getBank_account(), bank.getBank_persion(), Long.valueOf(bank.getId()) });
		if (ret == 1)
			return bank;
		return bank;
	}

	public List<AccountBank> getDownRechargeBankList(int start, int end) {
		String selectSql = "select p1.id,p1.account,p1.branch,p1.bank_realname,p1.payment,p2.name as bank from dw_account_bank as p1 left join dw_linkage as p2 on p2.id=p1.bank and p2.type_id=25 where p1.user_id=0 ";
		String querySql = "";
		StringBuffer sb = new StringBuffer(selectSql);
		if (end < EnumTroubleFund.FIRST.getValue()) {
			querySql = sb.toString();
			logger.debug("getDownRechargeBankListsql():" + querySql);
			List<AccountBank> list = getJdbcTemplate().query(querySql, new Object[0],
					new RowMapper<AccountBank>() {
						public AccountBank mapRow(ResultSet rs, int num)
								throws SQLException {
							AccountBank s = new AccountBank();
							s.setId(rs.getLong("id"));
							s.setAccount(rs.getString("account"));
							s.setBank(rs.getString("bank"));
							s.setBank_realname(rs.getString("bank_realname"));
							s.setBranch(rs.getString("branch"));
							s.setPayment(rs.getLong("payment"));
							return s;
						}
					});
			return list;
		}
		String limitSql = " limit ?,?";
		querySql = limitSql;

		logger.debug("getDownRechargeBankListsql():" + querySql);
		List<AccountBank> list = getJdbcTemplate().query(querySql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) },
				new RowMapper<AccountBank>() {
					public AccountBank mapRow(ResultSet rs, int num)
							throws SQLException {
						AccountBank s = new AccountBank();
						s.setId(rs.getLong("id"));
						s.setAccount(rs.getString("account"));
						s.setBank(rs.getString("bank"));
						s.setBank_realname(rs.getString("bank_realname"));
						s.setBranch(rs.getString("branch"));
						return s;
					}
				});
		return list;
	}

	public AccountBank getDownRechargeBank(int id) {
		String selectSql = "select * from dw_account_bank where 1=1 ";
		if (id != 0) {
			selectSql = selectSql + " and id=? ";
		}
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getDownRechargeBanksql():" + querySql);
		AccountBank accountBank = (AccountBank) getJdbcTemplate()
				.queryForObject(querySql, new Object[] { Integer.valueOf(id) },
						new RowMapper<AccountBank>() {
							public AccountBank mapRow(ResultSet rs, int num)
									throws SQLException {
								AccountBank s = new AccountBank();
								s.setId(rs.getLong("id"));
								s.setAccount(rs.getString("account"));
								s.setBank(rs.getString("bank"));
								s.setBank_realname(rs.getString("bank_realname"));
								s.setBranch(rs.getString("branch"));
								s.setPayment(rs.getLong("payment"));
								return s;
							}
						});
		return accountBank;
	}

	public int getDownRechargeBankCount() {
		String selectSql = "select count(1) from dw_account_bank where user_id=0 ";
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getDownRechargeBankCountsql():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public PaymentInterface addPayInterface(PaymentInterface paymentInterface) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_payment_interface(name,merchant_id,`key`,recharge_fee,return_url,notice_url,is_enable,chartset,interface_Into_account,interface_value) values(?,?,?,?,?,?,?,?,?,?)";
		final PaymentInterface p = paymentInterface;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setString(1, p.getName());
				ps.setString(2, p.getMerchant_id());
				ps.setString(3, p.getKey());
				ps.setString(4, p.getRecharge_fee());
				ps.setString(5, p.getReturn_url());
				ps.setString(6, p.getNotice_url());
				ps.setLong(7, p.getIs_enable());
				ps.setString(8, p.getChartset());
				ps.setString(9, p.getInterface_Into_account());
				ps.setString(10, p.getInterface_value());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		paymentInterface.setId(key);
		return paymentInterface;
	}

	public List<PaymentInterface> getPayInterfaceList(int init) {
		String selectSql = "select * from dw_payment_interface  where 1=1 ";
		if (init != EnumTroubleFund.ZERO.getValue()) {
			selectSql = selectSql + " and is_enable=? ";
		}
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getDownRechargeBankListsql():" + querySql);
		if (init != EnumTroubleFund.ZERO.getValue()) {
			List<PaymentInterface> list = getJdbcTemplate().query(querySql,
					new Object[] { Integer.valueOf(init) }, new RowMapper<PaymentInterface>() {
						public PaymentInterface mapRow(ResultSet rs, int num)
								throws SQLException {
							PaymentInterface s = new PaymentInterface();
							s.setId(rs.getLong("id"));
							s.setName(rs.getString("name"));
							s.setMerchant_id(rs.getString("merchant_id"));
							s.setKey(rs.getString("key"));
							s.setNotice_url(rs.getString("notice_url"));
							s.setRecharge_fee(rs.getString("recharge_fee"));
							s.setReturn_url(rs.getString("return_url"));
							s.setChartset(rs.getString("chartset"));
							s.setInterface_Into_account(rs.getString("interface_Into_account"));
							s.setIs_enable(rs.getLong("is_enable"));
							s.setInterface_value(rs.getString("interface_value"));
							return s;
						}
					});
			return list;
		}
		List<PaymentInterface> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<PaymentInterface>() {
					public PaymentInterface mapRow(ResultSet rs, int num)
							throws SQLException {
						PaymentInterface s = new PaymentInterface();
						s.setId(rs.getLong("id"));
						s.setName(rs.getString("name"));
						s.setMerchant_id(rs.getString("merchant_id"));
						s.setKey(rs.getString("key"));
						s.setNotice_url(rs.getString("notice_url"));
						s.setRecharge_fee(rs.getString("recharge_fee"));
						s.setReturn_url(rs.getString("return_url"));
						s.setChartset(rs.getString("chartset"));
						s.setInterface_Into_account(rs.getString("interface_Into_account"));
						s.setIs_enable(rs.getLong("is_enable"));
						s.setInterface_value(rs.getString("interface_value"));
						return s;
					}
				});
		return list;
	}

	public int getPayInterfaceCount() {
		String selectSql = "select count(1) from dw_payment_interface  ";
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getPayInterfaceCountsql():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public PaymentInterface updatePayInterface(PaymentInterface p) {
		String sql = "update dw_payment_interface set name=?,merchant_id=?,`key`=?,recharge_fee=?,notice_url=?,return_url=?,is_enable=?,chartset=?,interface_Into_account=?,interface_value=? where id=?";
		int ret = getJdbcTemplate().update(
				sql,
				new Object[] { p.getName(), p.getMerchant_id(), p.getKey(),
						p.getRecharge_fee(), p.getNotice_url(),
						p.getReturn_url(), Long.valueOf(p.getIs_enable()),
						p.getChartset(), p.getInterface_Into_account(),
						p.getInterface_value(), Long.valueOf(p.getId()) });
		if (ret == 1)
			return p;
		return p;
	}

	public OnlineBank addOnlineBank(OnlineBank onlineBank) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "insert into dw_online_bank(bank_name,bank_logo,bank_value,payment_interface_id) values(?,?,?,?)";
		final OnlineBank p = onlineBank;
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setString(1, p.getBank_name());
				ps.setString(2, p.getBank_logo());
				ps.setString(3, p.getBank_value());
				ps.setLong(4, p.getPayment_interface_id());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		onlineBank.setId(key);
		return onlineBank;
	}

	public List<OnlineBankModel> getOnlineBankList(long payment_interface_id) {
		String selectSql = "select a.*,b.name as name from dw_online_bank as a left join dw_payment_interface as b on a.payment_interface_id=b.id where 1=1 ";
		if (payment_interface_id != EnumTroubleFund.ZERO.getValue()) {
			selectSql = selectSql + " and a.payment_interface_id=? and b.is_enable=? ";
			StringBuffer sb = new StringBuffer(selectSql);
			String querySql = sb.toString();
			logger.debug("getOnlineBankListsql():" + querySql);
			List<OnlineBankModel> list = getJdbcTemplate().query(querySql,
					new Object[] { Long.valueOf(payment_interface_id), Byte.valueOf(EnumTroubleFund.FIRST.getValue()) },
					new RowMapper<OnlineBankModel>() {
						public OnlineBankModel mapRow(ResultSet rs, int num)
								throws SQLException {
							OnlineBankModel s = new OnlineBankModel();
							s.setId(rs.getLong("id"));
							s.setBank_logo(rs.getString("bank_logo"));
							s.setBank_name(rs.getString("bank_name"));
							s.setBank_value(rs.getString("bank_value"));
							s.setPayment_interface_id(rs.getLong("payment_interface_id"));
							s.setName(rs.getString("name"));
							return s;
						}
					});
			return list;
		}
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getOnlineBankListsql():" + querySql);
		List<OnlineBankModel> list = getJdbcTemplate().query(querySql, new Object[0],
				new RowMapper<OnlineBankModel>() {
					public OnlineBankModel mapRow(ResultSet rs, int num)
							throws SQLException {
						OnlineBankModel s = new OnlineBankModel();
						s.setId(rs.getLong("id"));
						s.setBank_logo(rs.getString("bank_logo"));
						s.setBank_name(rs.getString("bank_name"));
						s.setBank_value(rs.getString("bank_value"));
						s.setPayment_interface_id(rs.getLong("payment_interface_id"));
						s.setName(rs.getString("name"));
						return s;
					}
				});
		return list;
	}

	public int getOnlineBankCount() {
		String selectSql = "select count(1) from dw_online_bank  ";
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getOnlineBankCountsql():" + querySql);
		int total = 0;
		total = count(querySql);
		return total;
	}

	public OnlineBank updateOnlineBank(OnlineBank p) {
		String sql = "update dw_online_bank set bank_name=?,bank_logo=?,`bank_value`=?,payment_interface_id=? where id=?";
		int ret = getJdbcTemplate().update(sql, new Object[] { p.getBank_name(), p.getBank_logo(), p.getBank_value(), Long.valueOf(p.getPayment_interface_id()), Long.valueOf(p.getId()) });
		if (ret == 1)
			return p;
		return p;
	}

	public OnlineBank getOnlineBank(int id) {
		String selectSql = "select a.*,b.name as name from dw_online_bank as a left join dw_payment_interface as b on a.payment_interface_id=b.id where 1=1 ";
		if (id != EnumTroubleFund.ZERO.getValue()) {
			selectSql = selectSql + " and a.id=? ";
		}
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getOnlineBanksql():" + querySql);
		OnlineBankModel onlineBank = getJdbcTemplate().queryForObject(querySql, new Object[] { Integer.valueOf(id) },
						new RowMapper<OnlineBankModel>() {
							public OnlineBankModel mapRow(ResultSet rs, int num)
									throws SQLException {
								OnlineBankModel s = new OnlineBankModel();
								s.setId(rs.getLong("id"));
								s.setBank_logo(rs.getString("bank_logo"));
								s.setBank_name(rs.getString("bank_name"));
								s.setBank_value(rs.getString("bank_value"));
								s.setPayment_interface_id(rs.getLong("payment_interface_id"));
								s.setName(rs.getString("name"));
								return s;
							}
						});
		return onlineBank;
	}

	public PaymentInterface getPaymentInterface(int id) {
		String selectSql = "select * from dw_payment_interface where 1=1 ";
		if (id != EnumTroubleFund.ZERO.getValue()) {
			selectSql = selectSql + " and id=? ";
		}
		StringBuffer sb = new StringBuffer(selectSql);
		String querySql = sb.toString();
		logger.debug("getPaymentInterfacesql():" + querySql);
		PaymentInterface paymentInterface = getJdbcTemplate()
				.queryForObject(querySql, new Object[] { Integer.valueOf(id) },
						new RowMapper<PaymentInterface>() {
							public PaymentInterface mapRow(ResultSet rs, int num)
									throws SQLException {
								PaymentInterface s = new PaymentInterface();
								s.setId(rs.getLong("id"));
								s.setName(rs.getString("name"));
								s.setMerchant_id(rs.getString("merchant_id"));
								s.setKey(rs.getString("key"));
								s.setNotice_url(rs.getString("notice_url"));
								s.setRecharge_fee(rs.getString("recharge_fee"));
								s.setReturn_url(rs.getString("return_url"));
								s.setChartset(rs.getString("chartset"));
								s.setInterface_Into_account(rs.getString("interface_Into_account"));
								s.setIs_enable(rs.getLong("is_enable"));
								s.setInterface_value(rs.getString("interface_value"));
								return s;
							}
						});
		return paymentInterface;
	}
}