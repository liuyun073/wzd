package com.rd.model.borrow;

import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.BorrowConfig;
import com.rd.domain.Repayment;
import com.rd.domain.User;
import com.rd.exception.BorrowException;
import com.rd.model.SearchParam;
import com.rd.tool.Page;
import com.rd.tool.interest.EndInterestCalculator;
import com.rd.tool.interest.InterestCalculator;
import com.rd.tool.interest.MonthEqualCalculator;
import com.rd.tool.interest.MonthInterest;
import com.rd.tool.interest.MonthInterestCalculator;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.util.Date;
import java.util.List;

public class BaseBorrowModel extends BorrowModel {
	private static final long serialVersionUID = 5486891425298146179L;
	private BorrowModel model;

	public BaseBorrowModel(BorrowModel model) {
		this.model = model;
		this.borrowType = Constant.TYPE_ALL;
		init();
	}

	public void init() {
		this.model
				.setConfig(Global.getBorrowConfig(this.model.getBorrowType()));
		if (this.model.getParam() == null)
			this.model.setParam(new SearchParam(this.model.getUse(), this.model
					.getTime_limit(), this.model.getSearchkeywords()));
	}

	public int getBorrowType() {
		return this.model.getBorrowType();
	}

	public BorrowConfig getConfig() {
		return this.model.getConfig();
	}

	public boolean isTrial() {
		BorrowConfig config = this.model.getConfig();

		return config.getIs_trail() != 0;
	}

	public boolean isReview() {
		BorrowConfig config = this.model.getConfig();

		return (config != null) && (config.getIs_review() != 0);
	}

	public BorrowModel getModel() {
		return this.model;
	}

	public void setModel(BorrowModel model) {
		this.model = model;
	}

	public String getTypeSql() {
		String typeSql = "";
		typeSql = this.model.getTypeSql();
		return typeSql;
	}

	public String getStatusSql() {
		String statusSql = "";
		statusSql = this.model.getStatusSql();
		return statusSql;
	}

	public String getOrderSql() {
		String orderSql = "";
		orderSql = getModel().getOrderSql();
		return orderSql;
	}

	public String getSearchParamSql() {
		String searchSql = "";
		searchSql = this.model.getSearchParamSql();
		return searchSql;
	}

	public String getLimitSql() {
		String limitSql = "";
		limitSql = this.model.getLimitSql();
		return limitSql;
	}

	public String getPageStr(Page p) {
		String pageSql = "";
		pageSql = this.model.getPageStr(p);
		return pageSql;
	}

	public String getSerachStr() {
		String searchSql = "";
		searchSql = this.model.getSerachStr();
		return searchSql;
	}

	public String getOrderStr() {
		String orderSql = "";
		orderSql = this.model.getOrderStr();
		return orderSql;
	}

	public void skipTrial() {
		if (isTrial()) {
			int enableAutoTender = Global.getInt("enableAutoTender");
			if (enableAutoTender == 1) {
				if ((this.model.getIs_fast() == 1)
						|| (this.model.getIs_xin() == 1)
						|| (this.model.getIs_offvouch() == 1)
						|| (this.model.getIs_jin() == 1))
					this.model.setStatus(19);
				else
					this.model.setStatus(1);
			} else {
				this.model.setStatus(1);
			}
			this.model.setVerify_time(DateUtils.getNowTimeStr());
			this.model.setVerify_user("1");
		}
	}

	public void skipReview() {
		if (isReview())
			this.model.setStatus(3);
	}

	public void skipStatus() {
		this.model.setStatus(this.model.getStatus() + 1);
	}

	public void verify(int status, int verifyStatus) {
		if (verifyStatus == 2) {
			if (status != 0)
				return;
			this.model.setStatus(2);
		} else if (verifyStatus == 1) {
			if (status == 0) {
				/*
				int enableAutoTender = Global.getInt("enableAutoTender");
				if (enableAutoTender == 1) {
					if ((this.model.getIs_fast() == 1)
							|| (this.model.getIs_xin() == 1)
							|| (this.model.getIs_jin() == 1)
							|| (this.model.getIs_offvouch() == 1))
						this.model.setStatus(19);
					else
						this.model.setStatus(1);
				} else
				*/
					this.model.setStatus(1);
			}
		} else if (verifyStatus == 3) {
			if (status != 1)
				return;
			this.model.setStatus(3);
		} else if (verifyStatus == 4) {
			if (status != 1)
				return;
			this.model.setStatus(4);
		} else if (verifyStatus == 5) {
			this.model.setStatus(5);
		}
	}

	public double calculateInterest() {
		InterestCalculator ic = interestCalculator();
		double interest = ic.getMoneyPerMonth() * ic.getPeriod()
				- NumberUtils.getDouble(getModel().getAccount());
		return interest;
	}

	public double calculateInterest(double validAccount) {
		InterestCalculator ic = interestCalculator(validAccount);
		double interest = ic.getMoneyPerMonth() * ic.getPeriod() - validAccount;
		return interest;
	}

	public InterestCalculator interestCalculator() {
		BorrowModel model = getModel();
		double account = NumberUtils.getDouble(model.getAccount());
		InterestCalculator ic = interestCalculator(account);
		return ic;
	}

	public InterestCalculator interestCalculator(double validAccount) {
		BorrowModel model = getModel();
		InterestCalculator ic = null;
		double apr = model.getApr() / 100.0D;
		if (model.getIsday() == 1) {
			int time_limit_day = model.getTime_limit_day();
			ic = new EndInterestCalculator(validAccount, apr, time_limit_day);
		} else if (StringUtils.isNull(model.getStyle()).equals("2")) {
			int time_limit = NumberUtils.getInt(model.getTime_limit());
			ic = new EndInterestCalculator(validAccount, apr, time_limit, 2);
		} else if (StringUtils.isNull(model.getStyle()).equals("3")) {
			int time_limit = NumberUtils.getInt(model.getTime_limit());
			ic = new MonthInterestCalculator(validAccount, apr, time_limit);
		} else {
			int time_limit = NumberUtils.getInt(model.getTime_limit());
			ic = new MonthEqualCalculator(validAccount, apr, time_limit);
		}
		ic.each();
		return ic;
	}

	public double calculateBorrowFee() {
		if (isNeedBorrowFee()) {
			BorrowModel model = getModel();
			double account = NumberUtils.getDouble(model.getAccount());
			return account * getBorrow_fee();
		}
		return 0.0D;
	}

	public double calculateBorrowAward() {
		if (this.model.getAward() == 1) {
			double account = NumberUtils.getDouble(this.model.getAccount());
			return this.model.getPart_account() / 100.0D * account;
		}
		if (this.model.getAward() == 2) {
			return this.model.getFunds();
		}
		return 0.0D;
	}

	private boolean toBool(String identify) {
		if ((this.model.getConfig() == null)
				|| (this.model.getConfig().getIdentify() == null)) {
			throw new BorrowException("该类借款标的配置参数不对！");
		}
		String cfgIdentify = this.model.getConfig().getIdentify();
		int i1 = Integer.parseInt(identify, 2);
		int i2 = Integer.parseInt(cfgIdentify, 2);
		int ret = i1 & i2;
		return ret > 0;
	}

	public boolean isNeedRealName() {
		String is = "100000";
		return toBool(is);
	}

	public boolean isNeedVIP() {
		String is = "010000";
		return toBool(is);
	}

	public boolean isNeedEmail() {
		String is = "001000";
		return toBool(is);
	}

	public boolean isNeedPhone() {
		String is = "000100";
		return toBool(is);
	}

	public boolean isNeedVideo() {
		String is = "000010";
		return toBool(is);
	}

	public boolean isNeedScene() {
		String is = "000001";
		return toBool(is);
	}

	public boolean checkIdentify(User u) {
		if ((isNeedRealName()) && (NumberUtils.getInt(u.getReal_status()) != 1))
			throw new BorrowException("需要实名认证！");

		if ((isNeedVIP()) && (u.getVip_status() != 1))
			throw new BorrowException("需要VIP认证！");

		if ((isNeedEmail()) && (NumberUtils.getInt(u.getEmail_status()) != 1))
			throw new BorrowException("需要邮箱认证！");

		if ((isNeedPhone()) && (NumberUtils.getInt(u.getPhone_status()) != 1))
			throw new BorrowException("需要手机认证！");

		if ((isNeedVideo()) && (u.getVideo_status() != 1))
			throw new BorrowException("需要视频认证！");

		if ((isNeedScene()) && (u.getScene_status() != 1))
			throw new BorrowException("需要现场认证！");

		return true;
	}

	public boolean checkModelData() {
		BorrowModel model = getModel();
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("mdw")) {
			if (StringUtils.isNull(model.getBorrow_time()).equals("")) {
				throw new BorrowException("借款时间不能为空");
			}
			if (StringUtils.isNull(model.getBorrow_account()).equals("")) {
				throw new BorrowException("借款额度不能为空");
			}
			if (StringUtils.isNull(model.getBorrow_time_limit()).equals("")) {
				throw new BorrowException("借款周期不能为空");
			}
		}
		if (!StringUtils.isInteger(model.getAccount())) {
			throw new BorrowException("借贷总金额必须为整数");
		}

		if (StringUtils.isEmpty(model.getName())) {
			throw new BorrowException("标题不能为空");
		}

		String minErrorMsg = "最低利率不能低于1%";
		String sixMonthErrorMsg = "六个月份以内(含六个月)的贷款年利率不能高于22.4%";
		String oneYearErrorMsg = "六个月至一年(含一年)的贷款年利率不能高于24%";
		String threeYearErrorMsg = "一年至三年(含三年)的贷款年利率不能高于24.6%";
		String fiveYearErrorMsg = "三年至五年(含五年)的贷款年利率不能高于25.6%";
		String moreYearErrorMsg = "五年以上的贷款年利率不能高于26.2%";

		if ((model.getApr() < 1.0D) && (model.getIs_donation() != 1)) {
			throw new BorrowException(minErrorMsg);
		}

		if (model.getIs_mb() == 1) {
			if (model.getApr() > 22.399999999999999D)
				throw new BorrowException(sixMonthErrorMsg);
		} else if (model.getIsday() != 1) {
			int time_limit = NumberUtils.getInt(model.getTime_limit());
			double apr = model.getApr();
			if ((time_limit <= 6) && (apr > 22.399999999999999D)) {
				throw new BorrowException(sixMonthErrorMsg);
			}
			if ((time_limit <= 12) && (time_limit > 6) && (apr > 24.0D)) {
				throw new BorrowException(oneYearErrorMsg);
			}
			if ((time_limit <= 36) && (time_limit > 12)
					&& (apr > 24.600000000000001D)) {
				throw new BorrowException(threeYearErrorMsg);
			}
			if ((time_limit <= 60) && (time_limit > 36)
					&& (apr > 25.600000000000001D)) {
				throw new BorrowException(fiveYearErrorMsg);
			}
			if ((time_limit > 60) && (apr > 26.199999999999999D)) {
				throw new BorrowException(moreYearErrorMsg);
			}
		}
		BorrowConfig config = Global.getBorrowConfig(model.getBorrowType());
		if (config != null) {
			double lowest_account = 0.0D;
			if (config.getLowest_account() != 0.0D)
				lowest_account = config.getLowest_account();
			else {
				lowest_account = 500.0D;
			}
			double most_account = 0.0D;
			if (config.getMost_account() != 0.0D)
				most_account = config.getMost_account();
			else {
				most_account = 5000000.0D;
			}
			double account = NumberUtils.getDouble(model.getAccount());
			if (account < lowest_account) {
				throw new BorrowException("借款金额不能低于" + lowest_account + "元");
			}
			if (account > most_account) {
				throw new BorrowException("借款金额不能高于" + most_account + "元");
			}

		}

		return true;
	}

	public Repayment[] getRepayment() {
		InterestCalculator ic = interestCalculator();
		List<MonthInterest> monthList = ic.getMonthList();
		Repayment[] repays = new Repayment[ic.getPeriod()];
		int i = 0;
		for (MonthInterest mi : monthList) {
			Repayment repay = new Repayment();
			repay.setBorrow_id(this.model.getId());
			repay.setOrder(i++);
			repay.setRepayment_time("" + getRepayTime(repay.getOrder()).getTime() / 1000L);
			double repayment_account = mi.getAccountPerMon() + mi.getInterest();

			repay.setRepayment_account("" + NumberUtils.format6(repayment_account));
			double repaymeng_interest = mi.getInterest();
			repay.setInterest("" + NumberUtils.format6(repaymeng_interest));
			double repaymeng_accountPerMon = mi.getAccountPerMon();
			repay.setCapital("" + NumberUtils.format6(repaymeng_accountPerMon));
			repay.setAddtime(DateUtils.getNowTimeStr());
			repays[(i - 1)] = repay;
		}
		return repays;
	}

	public Date getRepayTime(int period) {
		Date d = DateUtils.getDate(this.model.getVerify_time());
		Date repayDate = DateUtils.getLastSecIntegralTime(d);
		if (this.model.getIs_mb() == 1)
			return d;
		if (this.model.getIsday() == 1) {
			repayDate = DateUtils.rollDay(repayDate, this.model.getTime_limit_day());
			return repayDate;
		}

		if (StringUtils.isNull(this.model.getStyle()).equals("2"))
			repayDate = DateUtils.rollMon(repayDate, NumberUtils.getInt(this.model.getTime_limit()));
		else {
			repayDate = DateUtils.rollMon(repayDate, period + 1);
		}
		return repayDate;
	}

	public boolean isLastPeriod(int order) {
		if ((this.model.getIs_mb() == 1) || (this.model.getIsday() == 1) || (StringUtils.isNull(this.model.getStyle()).equals("2"))) {
			return true;
		}
		int time_limit = NumberUtils.getInt(this.model.getTime_limit());
		return time_limit == order + 1;
	}

	public double getManageFee() {
		double fee = 0.0D;
		double account = NumberUtils.getDouble(this.model.getAccount());
		fee = getManageFee(account);
		return fee;
	}

	public double getManageFee(double account) {
		double fee = 0.0D;
		BorrowConfig cfg = this.model.getConfig();
		if (cfg != null) {
			if (this.model.getIsday() == 1)
				fee = account * cfg.getDaymanagefee() * this.model.getTime_limit_day() / 3000.0D;
			else {
				fee = account * cfg.getManagefee() * NumberUtils.getInt(this.model.getTime_limit()) / 100.0D;
			}
		}
		return fee;
	}

	public double getTransactionFee() {
		return 0.0D;
	}

	public double calculateAward() {
		return calculateAward(NumberUtils.getDouble(this.model.getAccount()));
	}

	public double calculateAward(double account) {
		double awardValue = 0.0D;
		if (this.model.getAward() == 1)
			awardValue = account * this.model.getPart_account() / 100.0D;
		else if (this.model.getAward() == 2)
			awardValue = this.model.getFunds() / NumberUtils.getDouble(this.model.getAccount()) * account;
		else {
			awardValue = 0.0D;
		}
		return awardValue;
	}
}