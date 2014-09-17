package com.rd.service.impl;

import com.alibaba.fastjson.JSON;
import com.rd.common.enums.EnumRewardStatisticsBackType;
import com.rd.common.enums.EnumRewardStatisticsStatus;
import com.rd.common.enums.EnumRewardStatisticsType;
import com.rd.common.enums.EnumRuleBorrowCategory;
import com.rd.common.enums.EnumRuleNid;
import com.rd.context.Constant;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.OperationLogDao;
import com.rd.dao.RewardStatisticsDao;
import com.rd.dao.RuleDao;
import com.rd.dao.UserCacheDao;
import com.rd.dao.UserDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.OperationLog;
import com.rd.domain.RewardStatistics;
import com.rd.domain.Rule;
import com.rd.domain.User;
import com.rd.domain.UserCache;
import com.rd.model.DetailUser;
import com.rd.model.PageDataList;
import com.rd.model.RewardStatisticsModel;
import com.rd.model.RewardStatisticsSumModel;
import com.rd.model.RuleCheckModel;
import com.rd.model.RuleModel;
import com.rd.model.SearchParam;
import com.rd.service.RewardStatisticsService;
import com.rd.tool.Page;
import com.rd.util.DateUtils;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class RewardStatisticsServiceImpl implements RewardStatisticsService {
	private Logger logger = Logger.getLogger(RewardStatisticsServiceImpl.class);
	private RewardStatisticsDao rewardStatisticsDao;
	private UserDao userDao;
	private UserCacheDao userCacheDao;
	private RuleDao ruleDao;
	private AccountDao accountDao;
	private AccountLogDao accountLogDao;
	private OperationLogDao operationLogDao;

	public void addRewardStatistics(RewardStatistics rewardStatistics) {
		//Rule inviteRule = this.ruleDao.getRuleByNid(Constant.INVITE_AWARD);

		//Rule percentRuel = this.ruleDao.getRuleByNid("percent_award");

		Rule inviterRule = this.ruleDao.getRuleByNid("inviter_award");
		RuleModel model = new RuleModel();
		String rule_check = inviterRule.getRule_check();
		model.setRule_check(rule_check);

		List<?> borrowTypeList = model.getValueListByKey("borrow_type");
		String init = "";
		if (borrowTypeList != null) {
			for (int j = 0; j < borrowTypeList.size(); ++j) {
				String str1 = borrowTypeList.get(j).toString();
			}

		}

		String borrow_category = model.getValueStrByKey("borrow_category");
		if (borrow_category.equals("1")) {
			String sql = " and b.isday = 1";
			init = init + sql;
		}
		this.logger.debug("拼接sql：" + init);

		String receive_way = model.getValueStrByKey("receive_way");

		String receive_account = model.getValueStrByKey("receive_account");

		//String back_type = model.getValueStrByKey("back_type");

		List<User> userList = this.userDao.getVipUser();
		if (!userList.isEmpty())
			for (int i = 0; i < userList.size(); ++i) {
				User user = (User) userList.get(i);

				String startTime = user.getAddtime();

				String endTime = null;
				Date time = DateUtils.getDate(startTime);

				Date addTime = DateUtils.rollMon(time, 1);
				endTime = DateUtils.getTimeStr(addTime);

				Date newDate = new Date();
				RewardStatisticsSumModel sumModel = this.rewardStatisticsDao
						.getSumAccount(user.getUser_id(), startTime, endTime,
								init);
				if (sumModel.getAccount() <= 0.0D)
					continue;
				if (receive_way.equals("1")) {
					rewardStatistics.setType(Byte
							.valueOf(EnumRewardStatisticsType.INVITER_AWARD
									.getValue()));
					rewardStatistics.setReward_user_id(user.getUser_id());
					rewardStatistics.setPassive_user_id(1L);
					rewardStatistics.setReceive_time(DateUtils
							.getTimeStr(newDate));
					rewardStatistics.setReceive_yestime(DateUtils
							.getTimeStr(newDate));
					rewardStatistics.setReceive_account(Double.valueOf(
							receive_account).doubleValue());
					rewardStatistics.setReceive_yesaccount(Double.valueOf(
							receive_account).doubleValue());
					rewardStatistics.setReceive_status(Byte
							.valueOf(EnumRewardStatisticsStatus.CASH_BACK
									.getValue()));
					rewardStatistics.setBack_type(Byte
							.valueOf(EnumRewardStatisticsBackType.AUTO_BACK
									.getValue()));
				} else {
					rewardStatistics.setReceive_account(sumModel.getAccount());

					rewardStatistics.setReceive_status(Byte
							.valueOf(EnumRewardStatisticsStatus.CASH_UN_BACK
									.getValue()));
				}
				rewardStatistics.setAddtime(startTime);
				rewardStatistics.setEndtime(endTime);
				rewardStatistics.setRule_id(inviterRule.getId());

				this.rewardStatisticsDao.addRewardStatistics(rewardStatistics);
			}
	}

	public String getRule(Rule rule, String init) {
		RuleCheckModel checkModel = (RuleCheckModel) JSON.parseObject(rule
				.getRule_check(), RuleCheckModel.class);
		List<Integer> borrowTypeList = checkModel.getBorrow_type();
		if (borrowTypeList != null) {
			String sqlStr = "";
			for (int i = 0; i < borrowTypeList.size(); ++i) {
				Integer type = (Integer) borrowTypeList.get(i);
				sqlStr = typeSql(sqlStr, type.intValue());
			}
			if (sqlStr.length() > 0) {
				init = init + "and ( " + sqlStr + " ) ";
			}
		}

		if (checkModel.getBorrow_category() != null) {
			if (checkModel.getBorrow_category().equals(
					Byte.valueOf(EnumRuleBorrowCategory.BORROW_DAY.getValue()))) {
				String sql = " and b.isday = 1";
				init = init + sql;
			} else if (checkModel.getBorrow_category().equals(
					Byte
							.valueOf(EnumRuleBorrowCategory.BORROW_MONTH
									.getValue()))) {
				String sql = " and b.isday = 0";
				init = init + sql;
			}
		}
		return init;
	}

	public void addRewardStatistics(long id, String ip) {
		User user = this.userDao.getUserById(id);
		RewardStatistics rewardStatistics = new RewardStatistics();
		Rule invite_award = this.ruleDao.getRuleByNid(EnumRuleNid.INVITE_AWARD
				.getValue());
		Rule inviter_award = this.ruleDao
				.getRuleByNid(EnumRuleNid.INVITER_AWARD.getValue());
		Rule percent_award = this.ruleDao
				.getRuleByNid(EnumRuleNid.PERCENT_AWARD.getValue());
		
		Date localDate;
		if ((invite_award != null)
				&& (invite_award.getStatus().byteValue() == 1)) {
			localDate = addReward(id, user, rewardStatistics, invite_award);
		}
		if ((inviter_award != null)
				&& (inviter_award.getStatus().byteValue() == 1)) {
			localDate = addReward(id, user, rewardStatistics, inviter_award);
		}
		if ((percent_award != null)
				&& (percent_award.getStatus().byteValue() == 1))
			localDate = addReward(id, user, rewardStatistics, percent_award);
	}

	public void updateReward(String ip, UserCache userCache) {
		User user = this.userDao.getUserById(userCache.getUser_id());
		String inviteUserId = user.getInvite_userid();
		User inviteUser = this.rewardStatisticsDao.getUserByInviteId(Long
				.valueOf(inviteUserId).longValue(), user.getAddtime());
		Date newDate = new Date();
		RewardStatistics rewardStatistics = this.rewardStatisticsDao
				.getRewardByPassiveId(Long.valueOf(
						inviteUser.getInvite_userid()).longValue(), user
						.getUser_id());

		if ((userCache.getVip_status() != 1)
				|| (rewardStatistics.getBack_type().byteValue() != EnumRewardStatisticsBackType.AUTO_BACK
						.getValue()))
			return;
		rewardStatistics.setReceive_status(Byte
				.valueOf(EnumRewardStatisticsBackType.AUTO_BACK.getValue()));
		Rule rule = this.ruleDao.getRuleById(Long.valueOf(rewardStatistics
				.getRule_id()));
		RuleCheckModel checkModel = (RuleCheckModel) JSON.parseObject(rule
				.getRule_check(), RuleCheckModel.class);
		rewardStatistics.setReceive_yestime(DateUtils.getTimeStr(newDate));
		rewardStatistics.setReceive_yesaccount(checkModel.getReceive_account());
		rewardStatistics.setReceive_status(Byte
				.valueOf(EnumRewardStatisticsStatus.CASH_BACK.getValue()));
		this.rewardStatisticsDao.updateReward(rewardStatistics);
		double receive_yesaccount = rewardStatistics.getReceive_yesaccount();
		user.setInvite_money(Double.toString(receive_yesaccount));
		this.userDao.updateUser(user);
		this.accountDao.updateAccount(receive_yesaccount, receive_yesaccount,
				0.0D, rewardStatistics.getReward_user_id());
		Account act = this.accountDao.getAccount(rewardStatistics
				.getReward_user_id());

		AccountLog log = new AccountLog(rewardStatistics.getReward_user_id(),
				Constant.INVITE_AWARD, rewardStatistics.getPassive_user_id(),
				DateUtils.getTimeStr(newDate), ip);

		OperationLog operationLog = new OperationLog(rewardStatistics
				.getReward_user_id(), rewardStatistics.getPassive_user_id(),
				Constant.INVITE_AWARD, DateUtils.getTimeStr(newDate), ip, "");

		DetailUser detailUser = this.userDao.getDetailUserByUserid(operationLog
				.getVerify_user());
		Log(log, operationLog, receive_yesaccount, detailUser, user, act);
	}

	private Date addReward(long id, User user,
			RewardStatistics rewardStatistics, Rule rule) {
		String startTime = user.getAddtime();

		String endTime = null;
		Date time = DateUtils.getDate(startTime);

		Date addTime = DateUtils.rollMon(time, 1);
		endTime = DateUtils.getTimeStr(addTime);

		Date newDate = new Date();

		RuleCheckModel checkModel = new RuleCheckModel();
		String jsonCheckStr = rule.getRule_check();
		if ((jsonCheckStr != null) && (jsonCheckStr.length() > 0)) {
			checkModel = (RuleCheckModel) JSON.parseObject(jsonCheckStr,
					RuleCheckModel.class);
		}

		if (rule.getNid().equals(EnumRuleNid.PERCENT_AWARD.getValue())) {
			rewardStatistics.setType(Byte
					.valueOf(EnumRewardStatisticsType.INVITE_AWARD.getValue()));
			rewardStatistics.setReward_user_id(Long.valueOf(
					user.getInvite_userid()).longValue());
			rewardStatistics.setReceive_account(0.0D);
		} else if (rule.getNid().equals(EnumRuleNid.INVITE_AWARD.getValue())) {
			rewardStatistics.setReward_user_id(Long.valueOf(
					user.getInvite_userid()).longValue());
			rewardStatistics.setType(Byte
					.valueOf(EnumRewardStatisticsType.INVITE_AWARD.getValue()));
			rewardStatistics
					.setReceive_account(checkModel.getReceive_account());
		} else if (rule.getNid().equals(EnumRuleNid.INVITER_AWARD.getValue())) {
			rewardStatistics
					.setType(Byte
							.valueOf(EnumRewardStatisticsType.INVITER_AWARD
									.getValue()));
			rewardStatistics.setReward_user_id(id);
			rewardStatistics
					.setReceive_account(checkModel.getReceive_account());
		}
		rewardStatistics.setReceive_yesaccount(0.0D);
		rewardStatistics.setPassive_user_id(user.getUser_id());
		rewardStatistics.setReceive_time(DateUtils.getTimeStr(newDate));
		if (Byte.valueOf(checkModel.getBack_type()).byteValue() == EnumRewardStatisticsBackType.TIME_BACK
				.getValue())
			rewardStatistics
					.setBack_type(Byte
							.valueOf(EnumRewardStatisticsBackType.TIME_BACK
									.getValue()));
		else if (Byte.valueOf(checkModel.getBack_type()).byteValue() == EnumRewardStatisticsBackType.AUTO_BACK
				.getValue())
			rewardStatistics
					.setBack_type(Byte
							.valueOf(EnumRewardStatisticsBackType.AUTO_BACK
									.getValue()));
		else if (Byte.valueOf(checkModel.getBack_type()).byteValue() == EnumRewardStatisticsBackType.ARTIFICIAL_BACK
				.getValue()) {
			rewardStatistics.setBack_type(Byte
					.valueOf(EnumRewardStatisticsBackType.ARTIFICIAL_BACK
							.getValue()));
		}
		rewardStatistics.setReceive_status(Byte
				.valueOf(EnumRewardStatisticsStatus.CASH_UN_BACK.getValue()));
		rewardStatistics.setReceive_yestime(null);
		rewardStatistics.setAddtime(startTime);
		rewardStatistics.setEndtime(endTime);
		rewardStatistics.setRule_id(rule.getId());
		rewardStatistics.setType_fk_id(0L);

		this.rewardStatisticsDao.addRewardStatistics(rewardStatistics);
		return newDate;
	}

	private String typeSql(String sqlStr, int type) {
		StringBuffer sql = new StringBuffer(sqlStr);
		if (type == Constant.TYPE_SECOND)
			sql.append(" b.is_mb = 1");
		else if (type == Constant.TYPE_CREDIT) {
			if (sql.length() > 0)
				sql.append(" or b.is_xin = 1");
			else
				sql.append(" b.is_xin = 1");
		} else if (type == Constant.TYPE_MORTGAGE) {
			if (sql.length() > 0)
				sql.append(" or b.is_fast = 1");
			else
				sql.append(" b.is_fast = 1");
		} else if (type == Constant.TYPE_OFFVOUCH) {
			if (sql.length() > 0)
				sql.append(" or b.is_offvouch = 1");
			else
				sql.append(" b.is_offvouch = 1");
		} else if (type == Constant.TYPE_FLOW) {
			if (sql.length() > 0)
				sql.append(" or b.is_flow = 1");
			else
				sql.append(" b.is_flow = 1");
		} else if (type == Constant.TYPE_SECOND) {
			if (sql.length() > 0)
				sql.append(" or b.is_mb = 1");
			else
				sql.append(" b.is_mb = 1");
		} else if (type == Constant.TYPE_PROPERTY) {
			if (sql.length() > 0)
				sql.append(" or b.is_jin = 1");
			else
				sql.append(" b.is_jin = 1");
		}

		if (sql.length() > 0) {
			sqlStr = sql.toString();
			return sqlStr;
		}
		return "";
	}

	public List<RewardStatisticsModel> getRewardList() {
		return this.rewardStatisticsDao.getRewardList();
	}

	public RewardStatistics getRewardStatisticsById(long id) {
		return this.rewardStatisticsDao.getRewardStatisticsById(id);
	}

	public void verifyReward(RewardStatistics r, String status, User authUser,
			String ip) {
		UserCache userCache = this.userCacheDao.getUserCacheByUserid(r
				.getPassive_user_id());
		User user = this.userDao.getUserById(r.getPassive_user_id());
		Rule rule = this.ruleDao.getRuleById(Long.valueOf(r.getRule_id()));
		String init = getRule(rule, "");
		RewardStatisticsSumModel sumModel = sumModel(r, init);
		RuleCheckModel checkModel = (RuleCheckModel) JSON.parseObject(rule
				.getRule_check(), RuleCheckModel.class);
		if ((userCache.getVip_status() == 1)
				&& (sumModel.getAccount() >= checkModel.getTender_check_money())) {
			r.setReceive_yesaccount(r.getReceive_account());

			r.setReceive_status(Byte.valueOf(status));
			r.setReceive_yestime(DateUtils.getTimeStr(new Date()));

			AccountLog log = new AccountLog(r.getReward_user_id(),
					Constant.INVITE_AWARD, authUser.getUser_id(), DateUtils
							.getTimeStr(new Date()), ip);

			OperationLog operationLog = new OperationLog(r.getReward_user_id(),
					authUser.getUser_id(), Constant.INVITE_AWARD, DateUtils
							.getTimeStr(new Date()), ip, "");

			this.rewardStatisticsDao.updateReward(r);

			if (!r.getReceive_status().equals(
					Byte.valueOf(EnumRewardStatisticsStatus.CASH_BACK
							.getValue())))
				return;
			double receive_yesaccount = r.getReceive_yesaccount();

			DetailUser detailUser = this.userDao
					.getDetailUserByUserid(operationLog.getVerify_user());
			user.setInvite_money(Double.toString(receive_yesaccount));
			this.userDao.updateUser(user);
			this.accountDao.updateAccount(receive_yesaccount,
					receive_yesaccount, 0.0D, r.getReward_user_id());
			Account act = this.accountDao.getAccount(r.getReward_user_id());

			Log(log, operationLog, receive_yesaccount, detailUser, user, act);
		}
	}

	private RewardStatisticsSumModel sumModel(RewardStatistics r, String init) {
		RewardStatisticsSumModel sumModel = this.rewardStatisticsDao
				.getSumAccount(r.getPassive_user_id(), r.getAddtime(), r
						.getEndtime(), init);
		return sumModel;
	}

	private void Log(AccountLog log, OperationLog operationLog,
			double receive_yesaccount, DetailUser detailUser, User user,
			Account act) {
		log.setType(Constant.INVITE_AWARD);
		log.setMoney(Math.abs(receive_yesaccount));
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money());
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
		log.setRemark("好友奖励金额" + receive_yesaccount + "元");
		this.accountLogDao.addAccountLog(log);

		operationLog.setType(Constant.INVITE_AWARD);
		operationLog.setOperationResult(detailUser.getTypename() + "（"
				+ operationLog.getAddip() + "）用户名为" + detailUser.getUsername()
				+ "的操作员审核好友奖励" + user.getUsername() + receive_yesaccount
				+ "元成功");
		this.operationLogDao.addOperationLog(operationLog);
	}

	public List<RewardStatisticsModel> getRewardStatistics(SearchParam param) {
		return this.rewardStatisticsDao.getRewardStatistics(param);
	}

	public PageDataList getRewardStatisticsList(SearchParam param, int startPage) {
		PageDataList statisticsList = new PageDataList();
		int total = this.rewardStatisticsDao.getCount(param);
		Page p = new Page(total, startPage);
		List<RewardStatisticsModel> rewardStatisticsList = this.rewardStatisticsDao
				.getRewardStatisticsList(param, p.getStart(), p.getPernum());

		statisticsList.setList(rewardStatisticsList);
		statisticsList.setPage(p);
		return statisticsList;
	}

	public RewardStatisticsSumModel getSumAccount(long userId,
			String startTime, String endTime, String init) {
		return this.rewardStatisticsDao.getSumAccount(userId, startTime,
				endTime, init);
	}

	public void updateAccount(double account, long id) {
		this.rewardStatisticsDao.updateAccount(account, id);
	}

	public static void main(String[] args) {
		RewardStatisticsServiceImpl t = new RewardStatisticsServiceImpl();

		Rule rule = new Rule();
		String str = "{'borrow_type':[101,102,103,105,110],'borrow_category':2,'receive_way':2,'tender_check_money':2000,'receive_rate':'0.001','back_type':3}";
		rule.setRule_check(str);
		System.out.println(t.getRule(rule, " and aa = 1 "));
	}

	public RewardStatisticsDao getRewardStatisticsDao() {
		return this.rewardStatisticsDao;
	}

	public void setRewardStatisticsDao(RewardStatisticsDao rewardStatisticsDao) {
		this.rewardStatisticsDao = rewardStatisticsDao;
	}

	public RuleDao getRuleDao() {
		return this.ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public OperationLogDao getOperationLogDao() {
		return this.operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public UserCacheDao getUserCacheDao() {
		return this.userCacheDao;
	}

	public void setUserCacheDao(UserCacheDao userCacheDao) {
		this.userCacheDao = userCacheDao;
	}
}