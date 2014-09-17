package com.rd.service.impl;

import com.rd.common.enums.EnumAwardErrorType;
import com.rd.common.enums.EnumAwardType;
import com.rd.context.Constant;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.AwardDao;
import com.rd.dao.UserCreditDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.ObjAward;
import com.rd.domain.RuleAward;
import com.rd.domain.User;
import com.rd.domain.UserAward;
import com.rd.domain.UserCredit;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.award.AwardResult;
import com.rd.service.AwardService;
import com.rd.tool.Page;
import com.rd.util.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AwardServiceImpl implements AwardService {
	private AwardDao awardDao;
	private UserCreditDao userCreditDao;
	private AccountDao accountDao;
	private AccountLogDao accountLogDao;

	public AwardDao getAwardDao() {
		return this.awardDao;
	}

	public void setAwardDao(AwardDao awardDao) {
		this.awardDao = awardDao;
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
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

	public RuleAward getRuleAwardByRuleId(long ruleId) {
		return this.awardDao.getRuleAwardById(ruleId);
	}

	public void updateRuleAward(RuleAward data) {
		this.awardDao.updateRuleAwardById(data);
	}

	public void addRuleAward(RuleAward data) {
		this.awardDao.addRuleAward(data);
	}

	public List<UserAward> getAwardeeList(long ruleId, int num,
			boolean isOrderByLevel) {
		return this.awardDao.getAwardeeList(ruleId, num, isOrderByLevel);
	}

	public List<RuleAward> getRuleAwardList() {
		return this.awardDao.getRuleAwardList();
	}

	public void updateTotalMoney(long ruleId, double money) {
		this.awardDao.updateTotalMoney(ruleId, money);
	}

	public PageDataList getUserAwardList(int page, SearchParam param) {
		int total = this.awardDao.getUserAwardCount(param);
		Page p = new Page(total, page);

		List<UserAward> list = this.awardDao.getUserAwardList(p.getStart(), p.getPernum(),
				param);
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public List<UserAward> getAllUserAwardList(SearchParam param) {
		return this.awardDao.getAllUserAwardList(param);
	}

	public List<ObjAward> getObjectAwardListByRuleId(long ruleId) {
		return this.awardDao.getObjectAwardListByRuleId(ruleId);
	}

	public void addObjAward(ObjAward data) {
		this.awardDao.addObjAward(data);
	}

	public void updateObjAward(ObjAward data) {
		this.awardDao.updateObjAward(data);
	}

	public ObjAward getObjectAwardById(long awardId) {
		return this.awardDao.getObjectAwardById(awardId);
	}

	public long getRuleIdByAwardType(int awardType) {
		return this.awardDao.getRuleIdByAwardType(awardType);
	}

	public RuleAward getRuleAwardByAwardType(int awardType) {
		return this.awardDao.getRuleAwardByAwardType(awardType);
	}

	public AwardResult award(long ruleId, User user, double money) {
		AwardResult result = new AwardResult();

		RuleAward ruleAward = this.awardDao.getRuleAwardById(ruleId);

		EnumAwardErrorType awardResultType = check(ruleAward, user.getUser_id());

		if (awardResultType != null) {
			result.setError(awardResultType);
			return result;
		}

		List<ObjAward> objAwardList = this.awardDao.getObjectAwardListByRuleId(ruleAward
				.getId());
		ObjAward objAward = getObjAward(objAwardList);

		if ((objAward == null) && (ruleAward.getIs_absolute() == 0)) {
			addUserAward(user, ruleAward, objAward);
			result.setError(EnumAwardErrorType.RESULT_NO_AWARD);
			return result;
		}
		if ((objAward == null) && (ruleAward.getIs_absolute() != 0)) {
			objAward = (ObjAward) objAwardList.get(objAwardList.size() - 1);
		}

		if (objAward.getAward_limit() != 0) {
			long objAwardCnt = objAward.getTotal() - objAward.getBestow();

			if ((objAwardCnt == 0L) && (ruleAward.getIs_absolute() == 0)) {
				addUserAward(user, ruleAward, null);
				result.setError(EnumAwardErrorType.RESULT_NO_AWARD_OBJ);
				return result;
			}

			if ((objAwardCnt == 0L) && (ruleAward.getIs_absolute() != 0)) {
				objAward = (ObjAward) objAwardList.get(objAwardList.size() - 1);
			}

		}

		this.awardDao.updateBestow(ruleAward.getId(), objAward.getId());

		double awardMoney = 0.0D;
		if (ruleAward.getMoney_limit() != 0) {
			double availableMoney = ruleAward.getTotal_money()
					- ruleAward.getBestow_money();

			if (EnumAwardType.AWARD_TYPE_RATIO
					.equals(ruleAward.getAward_type())) {
				awardMoney = money * objAward.getRatio();
			} else
				awardMoney = objAward.getObj_value();

			if ((awardMoney > availableMoney)
					&& (ruleAward.getIs_absolute() == 0)) {
				addUserAward(user, ruleAward, null);
				result.setError(EnumAwardErrorType.RESULT_MONEY_LIMIT);
				return result;
			}

			if ((awardMoney > availableMoney)
					&& (ruleAward.getIs_absolute() != 0)) {
				objAward = (ObjAward) objAwardList.get(objAwardList.size() - 1);

				if (EnumAwardType.AWARD_TYPE_RATIO.equals(ruleAward
						.getAward_type())) {
					awardMoney = money * objAward.getRatio();
				} else
					awardMoney = objAward.getObj_value();

			}

		} else if (EnumAwardType.AWARD_TYPE_RATIO.equals(ruleAward
				.getAward_type())) {
			awardMoney = money * objAward.getRatio();
		} else {
			awardMoney = objAward.getObj_value();
		}

		this.awardDao.updateBestowMoney(ruleAward.getId(), awardMoney);

		result.setIs_success("T");
		result.setLevel_no(String.valueOf(objAward.getLevel()));
		result.setName(objAward.getName());
		result.setMoney(String.valueOf(awardMoney));

		if (ruleAward.getBack_type() == 2) {
			this.accountDao.updateAccount(awardMoney, awardMoney, 0.0D, user
					.getUser_id());
			Account act = this.accountDao.getAccount(user.getUser_id());
			AccountLog log = new AccountLog();
			log.setType(Constant.LOTTERY_AWARD);
			log.setUser_id(user.getUser_id());
			log.setTo_user(1L);
			log.setMoney(awardMoney);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setAddtime(DateUtils.getNowTimeStr());
			log.setRemark("抽奖抽中" + objAward.getName() + "，入账" + awardMoney);
			this.accountLogDao.addAccountLog(log);
		}

		addUserAward(user, ruleAward, objAward);

		return result;
	}

	private EnumAwardErrorType check(RuleAward ruleAward, long userId) {
		EnumAwardErrorType awardResultType = null;

		if (ruleAward == null) {
			awardResultType = EnumAwardErrorType.RESULT_INVALID_RULE_ID;
			return awardResultType;
		}

		String currentTime = DateUtils.dateStr2(new Date());
		if (currentTime.compareTo(ruleAward.getStart_date()) < 0) {
			awardResultType = EnumAwardErrorType.RESULT_BEFORE_START_TIME;
			return awardResultType;
		}

		if (currentTime.compareTo(ruleAward.getEnd_date()) > 0) {
			awardResultType = EnumAwardErrorType.RESULT_AFTER_END_TIME;
			return awardResultType;
		}

		if (ruleAward.getTime_limit() != 0) {
			int userCnt = 0;

			if (ruleAward.getTime_limit() == 1)
				userCnt = this.awardDao.getUserAwardTotalCnt(ruleAward.getId(),
						userId);
			else if (ruleAward.getTime_limit() == 2) {
				userCnt = this.awardDao.getUserAwardDayCnt(ruleAward.getId(),
						userId);
			}

			if (userCnt >= ruleAward.getMax_times()) {
				awardResultType = EnumAwardErrorType.RESULT_TIME_LIMIT;
				return awardResultType;
			}

		}

		if (EnumAwardType.AWARD_TYPE_POINT.equals(ruleAward.getAward_type())) {
			UserCredit credit = this.userCreditDao
					.getUserCreditByUserId(userId);
			int validPoint = credit.getValid_value();

			if (validPoint < ruleAward.getBase_point()) {
				awardResultType = EnumAwardErrorType.RESULT_POINT_LIMIT;
				return awardResultType;
			}
		}

		return awardResultType;
	}

	private int getRandom() {
		Random r = new Random();
		return r.nextInt(100000000);
	}

	private ObjAward getObjAward(List<ObjAward> objAwardList) {
		int start = getRandom();
		for (ObjAward objAward : objAwardList) {
			if (start < objAward.getRate()) {
				return objAward;
			}
			start -= objAward.getRate();
		}
		return null;
	}

	private void addUserAward(User user, RuleAward ruleAward, ObjAward objAward) {
		UserAward userAward = new UserAward();

		userAward.setUser_id(user.getUser_id());

		userAward.setUser_name(user.getUsername());

		userAward.setRule_id(ruleAward.getId());
		if (objAward != null) {
			userAward.setLevel(objAward.getLevel());

			userAward.setAward_id(objAward.getId());

			userAward.setPoint_reduce(ruleAward.getBase_point());

			userAward.setAward_name(objAward.getName());

			userAward.setStatus(1);
			if (ruleAward.getBack_type() == 2)
				userAward.setReceive_status(1);
			else
				userAward.setReceive_status(0);
		} else {
			userAward.setStatus(0);
		}

		userAward.setAddtime(DateUtils.getNowTimeStr());

		this.awardDao.addUserAward(userAward);
	}

	public List<UserAward> getMyAwardList(long ruleId, long userId) {
		return this.awardDao.getMyAwardList(ruleId, userId);
	}
}