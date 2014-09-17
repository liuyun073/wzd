package com.rd.service.impl;

import com.rd.common.enums.EnumTroubleFund;
import com.rd.context.Constant;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.TroubleFundDao;
import com.rd.dao.UserDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.TroubleAwardRecord;
import com.rd.domain.TroubleDonateRecord;
import com.rd.domain.TroubleFundDonateRecord;
import com.rd.domain.User;
import com.rd.exception.AccountException;
import com.rd.exception.UserException;
import com.rd.model.DetailUser;
import com.rd.model.PageDataList;
import com.rd.service.TroubleFundService;
import com.rd.tool.Page;
import com.rd.tool.coder.MD5;
import com.rd.util.DateUtils;
import com.rd.util.StringUtils;
import java.util.List;

public class TroubleFundServiceImpl implements TroubleFundService {
	private UserDao userDao;
	private TroubleFundDao troubleFundDao;
	private AccountDao accountDao;
	private AccountLogDao accountLogDao;

	public TroubleFundDonateRecord troubleFund(TroubleFundDonateRecord t,
			String paypassword, double trouble_apr) {
		TroubleDonateRecord donate = new TroubleDonateRecord();
		DetailUser user = this.userDao.getDetailUserByUserid(t.getUser_id());

		Account account = this.accountDao.getAccount(t.getUser_id());
		if (account.getUse_money() - t.getMoney() > 0.0D) {
			boolean result = checkPaypassword(user, paypassword);
			if (result) {
				if (t.getGiving_way() == EnumTroubleFund.FIRST.getValue()) {
					t.setAward_money(EnumTroubleFund.ZERO.getValue());
				}
				t = this.troubleFundDao.addTroubleFund(t);

				troubleAward(t, trouble_apr, EnumTroubleFund.ZERO.getValue(), t
						.getAward_money());

				double money = t.getMoney();
				this.accountDao.updateAccount(-money, -money,
						EnumTroubleFund.ZERO.getValue(), t.getUser_id());

				account = this.accountDao.getAccount(t.getUser_id());
				AccountLog log = new AccountLog(t.getUser_id(),
						Constant.TROUBLE_DONATE, EnumTroubleFund.FIRST
								.getValue(), DateUtils.getNowTimeStr(), "");
				log.setTotal(account.getTotal());
				log.setUse_money(account.getUse_money());
				log.setNo_use_money(account.getNo_use_money());
				log.setCollection(account.getCollection());
				log.setMoney(money);
				this.accountLogDao.addAccountLog(log);
			}
		} else {
			throw new AccountException("可用余额小于捐赠金额，不能进行捐赠");
		}
		return t;
	}

	private boolean checkPaypassword(User user, String newpaypassword) {
		String paypassword = StringUtils.isNull(user.getPaypassword());
		MD5 md5 = new MD5();
		newpaypassword = md5.getMD5ofStr(newpaypassword);
		if (StringUtils.isBlank(paypassword)) {
			throw new UserException("请先设置交易密码");
		}
		if (!paypassword.equals(newpaypassword)) {
			throw new UserException("交易密码输入不正确");
		}

		return paypassword.equals(newpaypassword);
	}

	public PageDataList getTroubleFundList(TroubleFundDonateRecord t,
			double trouble_apr, int page) {
		int total = this.troubleFundDao.getTroubleFundCount(t);
		Page p = new Page(total, page);
		List list = this.troubleFundDao.getTroubleFund(t, trouble_apr, p
				.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public TroubleAwardRecord troubleAward(TroubleFundDonateRecord r,
			double trouble_apr, long status, double award_money) {
		TroubleAwardRecord award = new TroubleAwardRecord();

		if (r.getGiving_way() == EnumTroubleFund.FIRST.getValue()) {
			return award;
		}
		double troubleAwardMoney = 0.0D;

		if (status == EnumTroubleFund.FIRST.getValue()) {
			troubleAwardMoney = award_money;
			if (award_money > 0.01D) {
				this.accountDao.updateAccount(award_money, award_money,
						EnumTroubleFund.ZERO.getValue(), r.getUser_id());
				Account account = this.accountDao.getAccount(r.getUser_id());

				AccountLog log = new AccountLog(r.getUser_id(),
						Constant.TROUBLE_AWARD, EnumTroubleFund.FIRST
								.getValue(), DateUtils.getNowTimeStr(), "");
				log.setMoney(award_money);
				log.setTotal(account.getTotal());
				log.setUse_money(account.getUse_money());
				log.setCollection(account.getCollection());
				log.setNo_use_money(account.getNo_use_money());
				this.accountLogDao.addAccountLog(log);
			}
			award.setStatus(EnumTroubleFund.FIRST.getValue());
		} else {
			troubleAwardMoney = r.getMoney()
					* (EnumTroubleFund.FIRST.getValue() - trouble_apr);
			award.setStatus(EnumTroubleFund.ZERO.getValue());
		}
		award.setMoney(troubleAwardMoney);
		award.setUser_id(r.getUser_id());
		award = this.troubleFundDao.addTroubleAward(award);
		return award;
	}

	public TroubleDonateRecord troubleDonate(TroubleFundDonateRecord r,
			double trouble_apr) {
		TroubleDonateRecord donate = new TroubleDonateRecord();
		double troubleDonateMoney = r.getMoney();
		donate.setStatus(EnumTroubleFund.ZERO.getValue());
		donate.setUser_id(r.getUser_id());
		if (r.getGiving_way() == EnumTroubleFund.FIRST.getValue()) {
			donate.setBorrow_use("无偿捐赠");
		} else {
			donate.setBorrow_use("有偿捐赠");
			troubleDonateMoney = r.getMoney() * trouble_apr;
		}
		donate.setMoney(troubleDonateMoney);
		donate = this.troubleFundDao.addTroubleDonate(donate);
		return donate;
	}

	public double getTroubleSum(long type) {
		if (type == EnumTroubleFund.FIRST.getValue()) {
			double troubleIntoAwardSum = this.troubleFundDao
					.getTroubleAwardSum(EnumTroubleFund.ZERO.getValue());

			double troubleOutAwardSum = this.troubleFundDao
					.getTroubleAwardSum(EnumTroubleFund.FIRST.getValue());
			double troubleAwardExtra = troubleIntoAwardSum - troubleOutAwardSum;
			return troubleAwardExtra;
		}

		double troubleIntoDonateSum = this.troubleFundDao
				.getTroubleDonateSum(EnumTroubleFund.ZERO.getValue());

		double troubleOutDonateSum = this.troubleFundDao
				.getTroubleDonateSum(EnumTroubleFund.FIRST.getValue());
		double troubleDonateExtra = troubleIntoDonateSum - troubleOutDonateSum;
		return troubleDonateExtra;
	}

	public TroubleDonateRecord addTroubleDonate(TroubleDonateRecord t) {
		return this.troubleFundDao.addTroubleDonate(t);
	}

	public TroubleDonateRecord updateTroubleDonate(TroubleDonateRecord t) {
		return this.troubleFundDao.updateTroubleDonate(t);
	}

	public PageDataList getTroubleDonateList(long status, int page) {
		int total = this.troubleFundDao.getTroubleDonateCount(status);
		Page p = new Page(total, page);
		List list = this.troubleFundDao.getTroubleDonateList(status, p
				.getStart(), p.getPernum());
		PageDataList plist = new PageDataList(p, list);
		return plist;
	}

	public TroubleDonateRecord getTroubleDonateById(int id) {
		return this.troubleFundDao.getTroubleDonateById(id);
	}

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public TroubleFundDao getTroubleFundDao() {
		return this.troubleFundDao;
	}

	public void setTroubleFundDao(TroubleFundDao troubleFundDao) {
		this.troubleFundDao = troubleFundDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}