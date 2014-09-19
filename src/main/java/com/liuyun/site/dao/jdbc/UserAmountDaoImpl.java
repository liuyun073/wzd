package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.UserAmountDao;
import com.liuyun.site.dao.jdbc.mapper.UserAmountApplyMapper;
import com.liuyun.site.dao.jdbc.mapper.UserAmountMapper;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.domain.UserAmountApply;
import com.liuyun.site.domain.UserAmountLog;
import com.liuyun.site.model.SearchParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class UserAmountDaoImpl extends BaseDaoImpl implements UserAmountDao {
	private static Logger logger = Logger.getLogger(UserAmountDaoImpl.class);

	public void add(UserAmountApply amount) {
		String sql = "insert into dw_user_amountapply(user_id,type,account,account_new,account_old,status,addtime,content,remark,verify_remark,verify_time,verify_user,addip) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(amount.getUser_id()),
						amount.getType(), Double.valueOf(amount.getAccount()),
						Double.valueOf(amount.getAccount_new()),
						Double.valueOf(amount.getAccount_old()),
						Integer.valueOf(amount.getStatus()),
						amount.getAddtime(), amount.getContent(),
						amount.getRemark(), amount.getVerify_remark(),
						amount.getVerify_time(),
						Long.valueOf(amount.getVerify_user()),
						amount.getAddip() });
	}

	public List<UserAmountApply> getUserAmountApply(long user_id) {
		String sql = "select p1.*,p2.username from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where p2.user_id=? and p1.status in (0,2) order by p1.addtime desc";

		logger.debug("SQL:" + sql);
		logger.debug("user_id:" + user_id);
		List<UserAmountApply> list = new ArrayList<UserAmountApply>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) },
				new UserAmountApplyMapper());
		return list;
	}

	public List<UserAmountApply> getAmountApplyListByUserid(long userid, int start, int pernum,
			SearchParam param) {
		String sql = "select p1.*,p2.username from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 and p1.user_id=? order by p1.addtime desc limit ?,?";

		logger.debug("SQL:" + sql);
		List<UserAmountApply> list = new ArrayList<UserAmountApply>();
		list = getJdbcTemplate().query(
				sql,
				new Object[] { Long.valueOf(userid), Integer.valueOf(start),
						Integer.valueOf(pernum) }, new UserAmountApplyMapper());
		return list;
	}

	public int getAmountApplyCountByUserid(long user_id, SearchParam param) {
		String sql = "select count(1) from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 and p1.user_id=? ";

		int total = 0;
		total = count(sql, new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public List<UserAmountApply> getUserMountApply(int start, int pernum, SearchParam param) {
		String sql = "select p1.*,p2.username from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 and p1.status=2 order by p1.addtime desc limit ?,?";

		logger.debug("SQL:" + sql);
		List<UserAmountApply> list = new ArrayList<UserAmountApply>();
		list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new UserAmountApplyMapper());
		return list;
	}

	public int getUserMountApplyCount(SearchParam param) {
		String sql = "select count(1) from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 and p1.status=2 ";

		int total = 0;
		total = count(sql);
		return total;
	}

	public UserAmount getUserAmount(long user_id) {
		String sql = "select p1.*,p2.username from dw_user_amount as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where p2.user_id=? order by p1.id desc";
		logger.debug("SQL:" + sql);
		logger.debug("user_id:" + user_id);
		List<UserAmount> list = new ArrayList<UserAmount>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new UserAmountMapper());
		if (list.size() > 0)
			return (UserAmount) list.get(0);
		return null;
	}

	public UserAmountApply getUserAmountApplyById(long id) {
		String sql = "select p1.*,p2.username from dw_user_amountapply as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where p1.id=? order by p1.id desc";
		logger.debug("SQL:" + sql);
		logger.debug("user_id:" + id);
		List<UserAmountApply> list = new ArrayList<UserAmountApply>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(id) },
				new UserAmountApplyMapper());
		if (list.size() > 0)
			return (UserAmountApply) list.get(0);
		return null;
	}

	public void updateCreditAmount(double totalVar, double useVar,
			double nouseVar, UserAmount amount) {
		String sql = "update dw_user_amount set credit=credit+?,credit_use=credit_use+?,credit_nouse=credit_nouse+? where user_id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Double.valueOf(totalVar),
						Double.valueOf(useVar), Double.valueOf(nouseVar),
						Long.valueOf(amount.getUser_id()) });
	}

	public void updateApply(UserAmountApply apply) {
		String sql = "update dw_user_amountapply set status=?,account=?,account_new=?,account_old=?,verify_remark=?,verify_time=?,verify_user=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(apply.getStatus()),
						Double.valueOf(apply.getAccount()),
						Double.valueOf(apply.getAccount_new()),
						Double.valueOf(apply.getAccount_old()),
						apply.getVerify_remark(), apply.getVerify_time(),
						Long.valueOf(apply.getUser_id()),
						Long.valueOf(apply.getId()) });
	}

	public void addAmountLog(UserAmountLog log) {
		String sql = "insert into dw_user_amountlog(user_id,type,amount_type,account,account_all,account_use,account_nouse,remark,addtime,addip) values(?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(log.getUser_id()), log.getType(),
						log.getAmount_type(), log.getAccount(),
						log.getAccount_all(), log.getAccount_use(),
						log.getAccount_nouse(), log.getRemark(),
						log.getAddtime(), log.getAddip() });
	}

	public UserAmount getUserAmountById(long id) {
		String sql = "select p1.*,p2.username from dw_user_amount as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where p.id=?";
		logger.debug("SQL:" + sql);
		logger.debug("user_id:" + id);
		List<UserAmount> list = new ArrayList<UserAmount>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(id) },
				new UserAmountMapper());
		if (list.size() > 0)
			return (UserAmount) list.get(0);
		return null;
	}

	public void addAmount(UserAmount amount) {
		String sql = "insert  into dw_user_amount(user_id,credit,credit_use,credit_nouse,borrow_vouch,borrow_vouch_use,borrow_vouch_nouse,tender_vouch,tender_vouch_use,tender_vouch_nouse) value(?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(amount.getUser_id()),
						Double.valueOf(amount.getCredit()),
						Double.valueOf(amount.getCredit_use()),
						Double.valueOf(amount.getCredit_nouse()),
						Double.valueOf(amount.getBorrow_vouch()),
						Double.valueOf(amount.getBorrow_vouch_use()),
						Double.valueOf(amount.getBorrow_vouch_nouse()),
						Double.valueOf(amount.getTender_vouch()),
						Double.valueOf(amount.getTender_vouch_use()),
						Double.valueOf(amount.getTender_vouch_nouse()) });
	}

	public int getUserAmountCount(SearchParam param) {
		String sql = "select count(1) from dw_user_amount as  p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 ";

		int total = 0;
		total = count(sql);
		return total;
	}

	public List<UserAmount> getUserAmount(int start, int pernum, SearchParam param) {
		String sql = "select p1.*,p2.username from dw_user_amount as p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 order by p1.user_id limit ?,?";

		logger.debug("SQL:" + sql);
		List<UserAmount> list = new ArrayList<UserAmount>();
		list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new UserAmountMapper());
		return list;
	}
}