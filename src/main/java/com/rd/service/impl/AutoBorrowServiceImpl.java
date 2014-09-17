package com.rd.service.impl;

import com.rd.common.enums.EnumIntegralTypeName;
import com.rd.common.enums.EnumRuleBorrowCategory;
import com.rd.common.enums.EnumRuleNid;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.BorrowAutoDao;
import com.rd.dao.BorrowDao;
import com.rd.dao.CollectionDao;
import com.rd.dao.MessageDao;
import com.rd.dao.ProtocolDao;
import com.rd.dao.RepaymentDao;
import com.rd.dao.RewardStatisticsDao;
import com.rd.dao.RuleDao;
import com.rd.dao.TenderDao;
import com.rd.dao.UserAmountDao;
import com.rd.dao.UserCacheDao;
import com.rd.dao.UserCreditDao;
import com.rd.dao.UserDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.AutoTenderOrder;
import com.rd.domain.Borrow;
import com.rd.domain.BorrowAuto;
import com.rd.domain.Collection;
import com.rd.domain.CreditType;
import com.rd.domain.Huikuan;
import com.rd.domain.Message;
import com.rd.domain.Notice;
import com.rd.domain.NoticeConfig;
import com.rd.domain.Protocol;
import com.rd.domain.Repayment;
import com.rd.domain.Tender;
import com.rd.domain.User;
import com.rd.domain.UserAmount;
import com.rd.domain.UserCache;
import com.rd.domain.UserCredit;
import com.rd.domain.UserCreditLog;
import com.rd.exception.BorrowException;
import com.rd.model.BorrowTender;
import com.rd.model.DetailCollection;
import com.rd.model.DetailUser;
import com.rd.model.RuleModel;
import com.rd.model.UserCreditModel;
import com.rd.model.borrow.BaseBorrowModel;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.model.borrow.protocol.BorrowProtocol;
import com.rd.quartz.notice.NoticeJobQueue;
import com.rd.quartz.notice.Sms;
import com.rd.service.AutoBorrowService;
import com.rd.tool.credit.CreditCount;
import com.rd.tool.interest.InterestCalculator;
import com.rd.tool.interest.MonthInterest;
import com.rd.tool.javamail.MailWithAttachment;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class AutoBorrowServiceImpl implements AutoBorrowService {
	private Logger logger = Logger.getLogger(AutoBorrowServiceImpl.class);

	BorrowDao borrowDao;
	TenderDao tenderDao;
	CollectionDao collectionDao;
	AccountDao accountDao;
	AccountLogDao accountLogDao;
	UserCreditDao userCreditDao;
	RepaymentDao repaymentDao;
	UserDao userDao;
	UserCacheDao userCacheDao;
	MessageDao messageDao;
	UserAmountDao userAmountDao;
	ProtocolDao protocolDao;
	BorrowAutoDao borrowAutoDao;
	RuleDao ruleDao;
	RewardStatisticsDao rewardStatisticsDao;

	public void fillAccountLog(AccountLog log, Account act, double operateValue) {
		log.setMoney(operateValue);
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money());
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
	}

	public String findServerPath() {
		String path = super.getClass().getResource("/").getPath();
		path = path.replaceAll("/WEB-INF/classes/", "/");
		return path;
	}

	private void sendMailWithAttachment(User user, long borrow_id,
			long tender_id) throws Exception {
		String to = user.getEmail();
		String attachfile = findServerPath() + "data/protocol/" + borrow_id
				+ "_" + tender_id + ".pdf";
		MailWithAttachment m = MailWithAttachment.getInstance();
		m.setTo(to);
		m.readProtocolMsg();
		m.replace(user.getUsername(), to, "/member/identify/active.html?id="
				+ m.getdecodeIdStr(user));
		m.attachfile(attachfile);
		m.sendMail();
	}

	public void autoVerifyFullSuccess(BorrowModel borrow) {
		BorrowModel model = borrow.getModel();
		BorrowModel dbModel = this.borrowDao.getBorrowById(model.getId());
		if (dbModel.getStatus() == 6) {
			this.logger.error("该借款表的状态已经处在放款状态！");
			return;
		}

		List<BorrowTender> tenderList = this.tenderDao.allTenderListBybid(model.getId());
		for (int i = 0; i < tenderList.size(); ++i) {
			Tender t = (Tender) tenderList.get(i);
			long borrow_id = t.getBorrow_id();
			long tender_id = t.getId();
			long user_id = t.getUser_id();
			User toUser = this.userDao.getUserByUserid(user_id);

			if (Global.getInt("protocol_toEmail_enable") == 1) {
				BorrowProtocol bp = new BorrowProtocol(toUser, borrow_id,
						tender_id);
				File pdfFile = new File(bp.getInPdfName());
				if (pdfFile.exists()) {
					try {
						bp.createPdf();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					sendMailWithAttachment(toUser, borrow_id, tender_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			updateUserCredit(model, t);
			this.accountDao.updateAccount(0.0D, 0.0D, -NumberUtils.getDouble(t.getAccount()), NumberUtils.getDouble(t.getAccount()), t.getUser_id());
			Account act = this.accountDao.getAccount(t.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			AccountLog log = new AccountLog(t.getUser_id(), Constant.INVEST, model.getUser_id(), DateUtils.getNowTimeStr(), "");

			log.setType(Constant.INVEST);
			log.setMoney(NumberUtils.getDouble(t.getAccount()));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark("扣除投资["
					+ logRemarkHtml(model)
					+ "]冻结资金,同时生成待收本金!"
					+ NumberUtils.format4(NumberUtils.getDouble(t.getAccount())));
			this.accountLogDao.addAccountLog(log);
			double tenderAccount = NumberUtils.getDouble(t.getAccount());

			double interest = NumberUtils.getDouble(t.getInterest());
			this.accountDao.updateAccount(interest, 0.0D, 0.0D, interest, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			log.setType(Constant.WAIT_INTEREST);
			fillAccountLog(log, act, interest);
			log.setRemark("生成[" + logRemarkHtml(model) + "]待收利息" + NumberUtils.format4(interest));
			this.accountLogDao.addAccountLog(log);

			double credit = 0.0D;
			if (model.getIsday() == 1)
				credit = tenderAccount * model.getTime_limit_day() / 3000.0D;
			else {
				credit = tenderAccount * NumberUtils.getInt(model.getTime_limit()) / 100.0D;
			}

			double awardValue = 0.0D;
			String remark = "收到标[" + model.getName() + "]投资奖励!!";
			if (model.getAward() == 1)
				awardValue = NumberUtils.getDouble(t.getAccount()) * model.getPart_account() / 100.0D;
			else if (model.getAward() == 2)
				awardValue = model.getFunds() / NumberUtils.getDouble(model.getAccount()) * NumberUtils.getDouble(t.getAccount());
			else {
				awardValue = 0.0D;
			}
			if (awardValue > 0.0D) {
				this.accountDao.updateAccount(awardValue, awardValue, 0.0D,
						0.0D, t.getUser_id());
				act = this.accountDao.getAccount(t.getUser_id());
				log.setUser_id(t.getUser_id());
				log.setTo_user(model.getUser_id());
				log.setType(Constant.AWARD_ADD);
				fillAccountLog(log, act, awardValue);
				log.setRemark(remark);
				this.accountLogDao.addAccountLog(log);

				if (Global.getWebid().equals("jsy")) {
					DetailUser user = this.userDao.getDetailUserByUserid(act
							.getUser_id());
					Protocol protocol = new Protocol(0L,
							Constant.AWARD_PROTOCOL, log.getAddtime(), log
									.getAddip(), "");
					protocol.setRemark("用户名为" + user.getUsername()
							+ "的用户投标（标名为" + model.getName() + "的借款标）奖励金额"
							+ awardValue + "元，生成资金划转委托书");
					protocol.setMoney(awardValue);
					protocol.setUser_id(t.getUser_id());
					this.protocolDao.addProtocol(protocol);
				}

			}

			if (Global.getWebid().equals("jsy")) {
				DetailUser user = this.userDao.getDetailUserByUserid(act
						.getUser_id());
				Protocol protocol = new Protocol(0L, Constant.TENDER_PROTOCOL,
						log.getAddtime(), log.getAddip(), "");
				protocol.setRemark("用户名为" + user.getUsername() + "的用户投标（标名为"
						+ model.getName() + "的借款标）金额"
						+ NumberUtils.getDouble(t.getAccount())
						+ "元,生成投资人投标资金划转委托书");
				protocol.setMoney(NumberUtils.getDouble(t.getAccount()));
				protocol.setUser_id(t.getUser_id());
				protocol.setPid(t.getId());
				protocol.setBorrow_id(t.getBorrow_id());
				this.protocolDao.addProtocol(protocol);
			}

			t.setWait_account(t.getAccount());
			t.setWait_interest(t.getInterest());
			this.tenderDao.modifyTender(t);

			List<Collection> clist = this.collectionDao
					.getCollectionListByTender(t.getId());
			for (Collection c : clist) {
				BaseBorrowModel baseBorrow = new BaseBorrowModel(model);
				c.setRepay_time(""
						+ baseBorrow.getRepayTime(c.getOrder()).getTime()
						/ 1000L);
			}
			this.collectionDao.modifyBatchCollection(clist);

			int enableHuikuan = Global.getInt("huikuan_enable");
			if ((enableHuikuan == 1) && (t.getAuto_repurchase() == 1)
					&& (checkHuikuanTender(model))) {
				autoHuikuan(t.getUser_id(), tenderAccount, log);
			}

		}

		double money = NumberUtils.getDouble(model.getAccount());
		Account act = this.accountDao.getAccount(model.getUser_id());
		this.accountDao.updateAccount(money, money, 0.0D, 0.0D, act
				.getUser_id());
		act = this.accountDao.getAccount(act.getUser_id());
		AccountLog log = new AccountLog(model.getUser_id(),
				Constant.BORROW_SUCCESS, 1L, DateUtils.getNowTimeStr(), "");
		fillAccountLog(log, act, money);
		log.setRemark("[" + logRemarkHtml(model) + "]借款入账"
				+ NumberUtils.format4(money));
		this.accountLogDao.addAccountLog(log);

		double totalAwardValue = 0.0D;
		String remark = "扣除标[" + logRemarkHtml(model) + "]投资奖励!!";
		if (model.getAward() == 1)
			totalAwardValue = NumberUtils.getDouble(model.getAccount())
					* model.getPart_account() / 100.0D;
		else if (model.getAward() == 2)
			totalAwardValue = model.getFunds();
		else {
			totalAwardValue = 0.0D;
		}
		if (totalAwardValue > 0.0D) {
			this.accountDao.updateAccount(-totalAwardValue, -totalAwardValue,
					0.0D, 0.0D, model.getUser_id());
			act = this.accountDao.getAccount(model.getUser_id());
			log.setUser_id(model.getUser_id());
			log.setTo_user(1L);
			log.setType(Constant.AWARD_DEDUCT);
			fillAccountLog(log, act, totalAwardValue);
			log.setRemark(remark);
			this.accountLogDao.addAccountLog(log);
		}

		deductFee(borrow, log);
		try {
			if (borrow.getBorrowType() != Constant.TYPE_DONATION)
				this.repaymentDao.addBatchRepayment(borrow.getRepayment());
		} catch (Exception e) {
			e.printStackTrace();
		}

		preSecondRepay(borrow);

		this.borrowDao.updateBorrowStatus(6, model.getId());
		try {
			NoticeConfig full_verify_invest = Global
					.getNoticeConfig("full_verify_invest");
			NoticeConfig full_verify_borrow = Global
					.getNoticeConfig("full_verify_borrow");
			if ((full_verify_invest != null)
					&& (full_verify_invest.getSms() == 1L)) {
				List<Notice> tenderNotices = fullSuccessTenderNotices(tenderList, dbModel);
				NoticeJobQueue.NOTICE.offer(tenderNotices);
			}
			if ((full_verify_invest != null) && (full_verify_invest.getLetters() == 1L)) {
				List<Notice> tenderNotices = fullSuccessTenderNotices(tenderList, dbModel);
				NoticeJobQueue.MESSAGE.offer(tenderNotices);
			}
			if ((full_verify_borrow != null)
					&& (full_verify_borrow.getSms() == 1L)) {
				List<Notice> borrowNotices = fullSuccessBorrowNotices(tenderList, dbModel);
				NoticeJobQueue.NOTICE.offer(borrowNotices);
			}
			if ((full_verify_borrow != null) && (full_verify_borrow.getLetters() == 1L)) {
				List<Notice> borrowNotices = fullSuccessBorrowNotices(tenderList, dbModel);
				NoticeJobQueue.MESSAGE.offer(borrowNotices);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserCredit(BorrowModel model, Tender t) {
		RuleModel rule = new RuleModel(this.ruleDao.getRuleByNid(EnumRuleNid.INTEGRAL_TENDER.getValue()));
		double check_money = rule.getValueDoubleByKey("integral_base_money").doubleValue();
		List<Integer> typeNoList = rule.getValueListByKey("borrow_type_no");

		Boolean result = Boolean.valueOf(isRewardCredits(model, typeNoList));
		if (!result.booleanValue())
			return;
		byte isDate = (byte) model.getIsday();
		int time = 0;
		if (EnumRuleBorrowCategory.BORROW_DAY.getValue() == isDate)
			time = model.getTime_limit_day();
		else if (EnumRuleBorrowCategory.BORROW_MONTH.getValue() == isDate) {
			time = Integer.parseInt(model.getTime_limit());
		}

		int value = CreditCount.getTenderValueMethodOne(isDate, time, Double.parseDouble(t.getAccount()), check_money);
		if (value <= 0)
			return;
		CreditType creditType = this.userCreditDao.getCreditTypeByNid(EnumIntegralTypeName.INTEGRAL_INVEST.getValue());

		UserCreditModel credit = this.userCreditDao.getCreditModelById(t.getUser_id());

		if ((credit != null) && (credit.getUser_id() > 0L)) {
			this.userCreditDao.editTenderValue(t.getUser_id(), value);
		} else if ((credit == null) || (credit.getUser_id() <= 0L)) {
			UserCredit uc = new UserCredit(t.getUser_id(), 10, new Date().getTime() / 1000L, "");
			uc.setValue(value);
			uc.setTender_value(value);
			this.userCreditDao.addUserCredit(uc);
		}

		UserCreditLog creditLog = new UserCreditLog();
		creditLog.setAddtime(NumberUtils.getLong(DateUtils.getNowTimeStr()));
		creditLog.setOp(Long.parseLong("1"));
		creditLog.setOp_user((int) t.getUser_id());
		creditLog.setType_id((int) creditType.getId());
		creditLog.setUser_id(t.getUser_id());
		creditLog.setValue(value);
		this.userCreditDao.addUserCreditLog(creditLog);
	}

	private boolean isRewardCredits(BorrowModel model, List<Integer> typeNoList) {
		Integer nowBorrowType = Integer.valueOf(0);
		if (model.getIs_mb() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_SECOND);
		else if (model.getIs_art() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_ART);
		else if (model.getIs_charity() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_CHARITY);
		else if (model.getIs_xin() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_CREDIT);
		else if (model.getIs_fast() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_MORTGAGE);
		else if (model.getIs_jin() == 1)
			nowBorrowType = Integer.valueOf(Constant.TYPE_PROPERTY);
		else if (model.getIs_offvouch() == 1) {
			nowBorrowType = Integer.valueOf(Constant.TYPE_OFFVOUCH);
		}

		if ((typeNoList != null) && (typeNoList.size() > 0)) {
			for (int i = 0; i < typeNoList.size(); ++i) {
				Integer borrowType = (Integer) typeNoList.get(i);
				if ((borrowType != null) && (borrowType.intValue() > 0) && (borrowType.equals(nowBorrowType))) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean checkHuikuanTender(BorrowModel model) {
		int isMonth = Global.getInt("huikuan_month");

		return (isMonth != 1) || (model.getIsday() != 1);
	}

	private double autoHuikuan(long user_id, double account, AccountLog log) {
		long start = NumberUtils.getLong(Global.getString("huikuan_start_time"));
		int isMonth = Global.getInt("huikuan_month");
		int isday = (isMonth == 1) ? 0 : 1;
		double collection = this.accountDao.getCollectionSumNoJinAndSecond(user_id, 1, isday, start);
		double huikuansum = this.accountDao.gethuikuanSum(user_id);

		if (collection > huikuansum) {
			double huikuan_money = 0.0D;
			double huikuan_award = Global.getDouble(Constant.HUIKUAN_AWARD);
			if (collection - huikuansum > account)
				huikuan_money = account;
			else {
				huikuan_money = collection - huikuansum;
			}
			if (huikuan_money >= 0.01D) {
				double huikuan_award_value = huikuan_money * huikuan_award;
				if (huikuan_award_value >= 0.01D) {
					Huikuan huikuan = new Huikuan();
					huikuan.setUser_id(user_id);
					huikuan.setHuikuan_money(NumberUtils.format2Str(huikuan_money));
					huikuan.setRemark("回款续投" + huikuan_money);
					huikuan.setStatus("0");
					huikuan.setAddtime(DateUtils.getNowTimeStr());
					if (huikuan_award_value >= 0.01D) {
						huikuan.setHuikuan_award(NumberUtils.format2Str(huikuan_money * huikuan_award));
					}
					this.accountDao.addHuikuanMoney(huikuan);
				}
			}
		}
		return account;
	}

	private void preSecondRepay(BorrowModel borrow) {
		if (borrow.getBorrowType() == Constant.TYPE_SECOND) {
			BorrowModel model = borrow.getModel();
			Repayment repayment = this.repaymentDao.getRepayment(0, model.getId());

			double freezeVal = NumberUtils.getDouble(repayment.getRepayment_account());
			this.accountDao.updateAccount(0.0D, -freezeVal, freezeVal, model.getUser_id());
			Account act = this.accountDao.getAccount(model.getUser_id());
			AccountLog log = new AccountLog(model.getUser_id(), Constant.FREEZE, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, freezeVal);
			this.accountLogDao.addAccountLog(log);
			this.repaymentDao.modifyRepaymentStatus(repayment.getId(), 0, 1);
		}
	}

	private List<Notice> fullSuccessTenderNotices(List<BorrowTender> tenders,
			BorrowModel model) {
		
		List<Notice> notices = new ArrayList<Notice>();
		for (BorrowTender tender : tenders) {
			User user = this.userDao.getUserById(tender.getUser_id());
			Notice s = new Sms();
			s.setTitle("投标[" + model.getName() + "]成功！");
			s.setReceive_userid(tender.getUser_id());
			s.setSend_userid(1L);
			s.setContent("尊敬的" + Global.getValue("webname") + "用户["
							+ user.getUsername() + "]，您于"
							+ DateUtils.dateStr5(tender.getAddtime())
							+ "进行投的标[" + model.getName() + "]已经满标审核成功，扣除冻结金额"
							+ tender.getAccount() + "元，审核时间为"
							+ DateUtils.dateStr5(model.getVerify_time())
							+ "，请登录网站查看详情");
			notices.add(s);
		}
		return notices;
	}

	private List<Notice> fullSuccessBorrowNotices(List<BorrowTender> tenders, BorrowModel model) {
		List<Notice> notices = new ArrayList<Notice>();
		User user = this.userDao.getUserById(model.getUser_id());
		Notice s = new Sms();
		s.setTitle("借款标[" + model.getName() + "]成功借款！");
		s.setReceive_userid(model.getUser_id());
		s.setSend_userid(1L);
		s.setContent("尊敬的" + Global.getValue("webname") + "用户["
				+ user.getUsername() + "]，您的借款标[" + model.getName()
				+ "]已经满标审核成功，审核时间为"
				+ DateUtils.dateStr5(model.getVerify_time()) + "，请登录网站查看详情");
		notices.add(s);
		return notices;
	}

	private List<Notice> fullFailTenderNotices(List<BorrowTender> tenders,
			BorrowModel model) {
		List<Notice> notices = new ArrayList<Notice>();
		for (BorrowTender tender : tenders) {
			User user = this.userDao.getUserById(tender.getUser_id());
			Notice s = new Sms();
			s.setReceive_userid(tender.getUser_id());
			s.setSend_userid(1L);
			s.setContent("尊敬的" + Global.getValue("webname") + "用户["
							+ user.getUsername() + "]，您于"
							+ DateUtils.dateStr5(tender.getAddtime())
							+ "进行投的标[" + model.getName() + "]满标审核没有通过，归还冻结金额"
							+ tender.getAccount() + "元，审核时间为"
							+ DateUtils.dateStr5(model.getVerify_time())
							+ "，请登录网站查看详情");
			notices.add(s);
		}
		return notices;
	}

	private List<Notice> fullFailBorrowNotices(List<BorrowTender> tenders,
			BorrowModel model) {
		List<Notice> notices = new ArrayList<Notice>();
		User user = this.userDao.getUserById(model.getUser_id());
		Notice s = new Sms();
		s.setReceive_userid(model.getUser_id());
		s.setSend_userid(1L);
		s.setContent("尊敬的" + Global.getValue("webname") + "用户["
				+ user.getUsername() + "]，您的借款标[" + model.getName()
				+ "]满标审核没有通过，审核时间为"
				+ DateUtils.dateStr5(model.getVerify_time()) + "，请登录网站查看详情");
		notices.add(s);
		return notices;
	}

	public void autoVerifyFullFail(BorrowModel borrow) {
		BorrowModel model = borrow.getModel();
		BorrowModel dbModel = this.borrowDao.getBorrowById(model.getId());
		if (dbModel.getStatus() == 49) {
			this.logger.error("该借款标满标审核未通过!");
			return;
		}
		List<BorrowTender> tenderList = this.tenderDao.allTenderListBybid(model.getId());
		for (int i = 0; i < tenderList.size(); ++i) {
			Tender t = (Tender) tenderList.get(i);
			double tenderValue = NumberUtils.getDouble(t.getAccount());
			this.accountDao.updateAccount(0.0D, tenderValue, -tenderValue, 0.0D, t.getUser_id());
			Account act = this.accountDao.getAccount(t.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			AccountLog log = new AccountLog(t.getUser_id(), Constant.UNFREEZE, model.getUser_id(), DateUtils.getNowTimeStr(), "");

			fillAccountLog(log, act, tenderValue);
			log.setRemark("[" + logRemarkHtml(model)
					+ "]审核不通过，解冻资金!"
					+ NumberUtils.format4(NumberUtils.getDouble(t.getAccount())));
			this.accountLogDao.addAccountLog(log);
		}

		this.borrowDao.updateBorrowStatus(49, model.getId());
		try {
			NoticeConfig full_verify_invest = Global.getNoticeConfig("full_verify_invest");
			NoticeConfig full_verify_borrow = Global.getNoticeConfig("full_verify_borrow");
			if ((full_verify_invest != null) && (full_verify_invest.getSms() == 1L)) {
				List<Notice> tenderNotices = fullFailTenderNotices(tenderList, dbModel);
				NoticeJobQueue.NOTICE.offer(tenderNotices);
			}
			if ((full_verify_invest != null) && (full_verify_invest.getLetters() == 1L)) {
				List<Notice> tenderNotices = fullFailTenderNotices(tenderList, dbModel);
				NoticeJobQueue.MESSAGE.offer(tenderNotices);
			}
			if ((full_verify_borrow != null) && (full_verify_borrow.getSms() == 1L)) {
				List<Notice> borrowNotices = fullFailBorrowNotices(tenderList, dbModel);
				NoticeJobQueue.NOTICE.offer(borrowNotices);
			}
			if ((full_verify_borrow != null) && (full_verify_borrow.getLetters() == 1L)) {
				List<Notice> borrowNotices = fullFailBorrowNotices(tenderList, dbModel);
				NoticeJobQueue.MESSAGE.offer(borrowNotices);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autoCancel(BorrowModel borrow) {
		BorrowModel model = borrow.getModel();
		BorrowModel dbModel = this.borrowDao.getBorrowById(model.getId());
		if (dbModel.getStatus() == 59) {
			this.logger.error("借款标的已经撤回并退款了!");
			return;
		}
		List<BorrowTender> tenderList = this.tenderDao.allTenderListBybid(model.getId());
		for (int i = 0; i < tenderList.size(); ++i) {
			Tender t = (Tender) tenderList.get(i);
			double tenderValue = NumberUtils.getDouble(t.getAccount());
			this.accountDao.updateAccount(0.0D, tenderValue, -tenderValue, 0.0D, t.getUser_id());
			Account act = this.accountDao.getAccount(t.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			AccountLog log = new AccountLog(t.getUser_id(), Constant.UNFREEZE, model.getUser_id(), DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, tenderValue);
			log.setRemark("["
					+ logRemarkHtml(model)
					+ "]项目撤回，解冻资金!"
					+ NumberUtils.format4(NumberUtils.getDouble(t.getAccount())));
			this.accountLogDao.addAccountLog(log);
		}

		this.borrowDao.updateBorrowStatus(59, model.getId());
	}

	public void autoDoRepayForSecond(BorrowModel borrow) {
	}

	public void autoRepay(Repayment repay) {
		Repayment repayment = this.repaymentDao.getRepayment(repay.getId());
		if (repayment.getStatus() == 1) {
			this.logger.error("该改还款计划已经还款");
			return;
		}
		BorrowModel model = this.borrowDao.getBorrowById(repay.getBorrow_id());
		double capital = NumberUtils.getDouble(repay.getCapital());
		double interest = NumberUtils.getDouble(repay.getInterest());

		BorrowModel borrow = BorrowHelper.getHelper(model);

		double fee = 0.0D;

		double lateInterest = 0.0D;
		boolean isWebPay = false;

		double repayLateInterest = 0.0D;
		if ((repay.getWebstatus() == 3) && (repay.getStatus() == 2) && (repay.getLate_interest() != null)) {
			isWebPay = true;
			lateInterest = Long.valueOf(repay.getLate_interest()).longValue();

			if (lateInterest > 0.0D) {
				this.accountDao.updateAccount(-lateInterest, 0.0D, -lateInterest, 0.0D, model.getUser_id());
				Account act = this.accountDao.getAccount(model.getUser_id());
				AccountLog log = new AccountLog(model.getUser_id(), Constant.REPAID, 1L, DateUtils.getNowTimeStr(), "");
				fillAccountLog(log, act, lateInterest);
				log.setRemark("[" + logRemarkHtml(model) + "]还款，网站垫付扣除罚息" + NumberUtils.format4(lateInterest));
				this.accountLogDao.addAccountLog(log);
			}
		} else {
			String lateInterestStr = repay.getLate_interest();
			if (!StringUtils.isBlank(lateInterestStr)) {
				repayLateInterest = Double.parseDouble(lateInterestStr);
			}

		}

		if (capital > 0.0D) {
			this.accountDao.updateAccount(-capital, 0.0D, -capital, 0.0D, model.getUser_id());
			Account act = this.accountDao.getAccount(model.getUser_id());
			AccountLog log = new AccountLog(model.getUser_id(), Constant.REPAID, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, capital);
			log.setRemark("[" + logRemarkHtml(model) + "]还款，扣除本金" + NumberUtils.format4(capital));
			this.accountLogDao.addAccountLog(log);
		}

		if (interest > 0.0D) {
			this.accountDao.updateAccount(-interest, 0.0D, -interest, 0.0D, model.getUser_id());
			Account act = this.accountDao.getAccount(model.getUser_id());
			AccountLog log = new AccountLog(model.getUser_id(), Constant.REPAID, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, interest);
			log.setRemark("[" + logRemarkHtml(model) + "]还款，扣除利息" + NumberUtils.format4(interest));
			this.accountLogDao.addAccountLog(log);
		}

		if (Global.getWebid().equals("jsy")) {
			Protocol protocol = new Protocol(0L, Constant.REPAYMENT_ACCOUNT_PROTOCOL, DateUtils.getNowTimeStr(), "", "");
			protocol.setRemark("用户名为" + repayment.getUsername() + "的用户进行还款（标名为"
					+ model.getName() + "的借款标）,归还本息" + capital + interest
					+ "元，生成借款人归还本息委托书");
			protocol.setUser_id(model.getUser_id());
			protocol.setMoney(capital + interest);
			protocol.setRepayment_account(capital);
			protocol.setInterest(interest);
			protocol.setBorrow_id(repay.getBorrow_id());
			protocol.setRepayment_time(repay.getRepayment_time());
			this.protocolDao.addProtocol(protocol);
		}

		if (repayLateInterest > 0.0D) {
			this.accountDao.updateAccount(-repayLateInterest, 0.0D, -repayLateInterest, 0.0D, model.getUser_id());
			Account act = this.accountDao.getAccount(model.getUser_id());
			AccountLog log = new AccountLog(model.getUser_id(), Constant.LATE_DEDUCT, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, repayLateInterest);
			log.setRemark("[" + logRemarkHtml(model) + "]还款，扣除逾期利息" + NumberUtils.format4(repayLateInterest));
			this.accountLogDao.addAccountLog(log);

			repayLateInterest /= 2.0D;
			this.accountDao.updateAccount(repayLateInterest, repayLateInterest, 0.0D, 0.0D, 1L);
			act = this.accountDao.getAccount(1L);
			log = new AccountLog(1L, Constant.LATE_REPAYMENT, model.getUser_id(), DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, repayLateInterest);
			log.setRemark("[" + logRemarkHtml(model) + "]还款，扣除逾期利息" + NumberUtils.format4(repayLateInterest));
			this.accountLogDao.addAccountLog(log);
		}

		if (fee > 0.0D) {
			Account act = this.accountDao.getAccount(model.getUser_id());
			this.accountDao.updateAccount(-fee, 0.0D, -fee, 0.0D, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			AccountLog log = new AccountLog(model.getUser_id(), Constant.BORROW_FEE, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, fee);
			log.setRemark("[" + logRemarkHtml(model) + "]借款管理费" + NumberUtils.format4(fee));
			this.accountLogDao.addAccountLog(log);
		}

		if (model.getIs_xin() == 1) {
			UserAmount amount = this.userAmountDao.getUserAmount(model.getUser_id());
			this.userAmountDao.updateCreditAmount(0.0D, capital, -capital, amount);
		}
		List<DetailCollection> collectList = null;
		List<Tender> tenderList = new ArrayList<Tender>();
		boolean lastPeriod = borrow.isLastPeriod(repay.getOrder());

		if (!isWebPay) {
			collectList = this.collectionDao.getCollectionLlistByBorrow(model.getId(), repay.getOrder());
			for (DetailCollection c : collectList) {
				double cCapital = NumberUtils.getDouble(c.getCapital());
				double cInterest = NumberUtils.getDouble(c.getInterest());
				Tender t = this.tenderDao.getTenderById(c.getTender_id());
				tenderList.add(t);

				if (cCapital > 0.0D) {
					this.accountDao.updateAccount(0.0D, cCapital, 0.0D, -cCapital, t.getUser_id());
					Account cAct = this.accountDao.getAccount(t.getUser_id());
					AccountLog cLog = new AccountLog(t.getUser_id(), Constant.CAPITAL_COLLECT, model.getUser_id(), DateUtils.getNowTimeStr(), "");
					fillAccountLog(cLog, cAct, cCapital);
					cLog.setRemark("[" + logRemarkHtml(model) + "]还款，归还本金" + NumberUtils.format4(cCapital));
					this.accountLogDao.addAccountLog(cLog);
				}

				if (cInterest > 0.0D) {
					this.accountDao.updateAccount(0.0D, cInterest, 0.0D, -cInterest, t.getUser_id());
					Account cAct = this.accountDao.getAccount(t.getUser_id());
					AccountLog cLog = new AccountLog(t.getUser_id(), Constant.INTEREST_COLLECT, model.getUser_id(), DateUtils.getNowTimeStr(), "");
					fillAccountLog(cLog, cAct, cInterest);
					cLog.setRemark("[" + logRemarkHtml(model) + "]还款，归还利息" + NumberUtils.format4(cInterest));
					fillAccountLog(cLog, cAct, cInterest);
					this.accountLogDao.addAccountLog(cLog);
					UserCache uc = this.userCacheDao.getUserCacheByUserid(t.getUser_id());
					double borrow_fee;
					if (Global.getDouble(Constant.VIP_BORROW_FEE) == 0.0D) {
						borrow_fee = Global.getDouble(Constant.BORROW_FEE);
					} else {
						if (uc.getVip_status() == 1)
							borrow_fee = Global.getDouble(Constant.VIP_BORROW_FEE);
						else {
							borrow_fee = Global.getDouble(Constant.BORROW_FEE);
						}
					}

					if (cInterest * borrow_fee > 0.0D) {
						this.accountDao.updateAccount(-cInterest * borrow_fee, -cInterest * borrow_fee, 0.0D, 0.0D, t.getUser_id());
						cAct = this.accountDao.getAccount(t.getUser_id());
						cLog = new AccountLog(t.getUser_id(), Constant.MANAGE_FEE, 1L, DateUtils.getNowTimeStr(), "");
						fillAccountLog(cLog, cAct, cInterest * borrow_fee);
						cLog.setRemark("[" + logRemarkHtml(model)
								+ "]还款，扣除利息管理费"
								+ NumberUtils.format2(cInterest * borrow_fee));
						this.accountLogDao.addAccountLog(cLog);
					}
				}

				double totalRepay = 1.0D;
				String totalRepayStr = repay.getCapital();
				if (!StringUtils.isBlank(totalRepayStr)) {
					totalRepay = Double.parseDouble(totalRepayStr);
				}
				if (repayLateInterest > 0.0D) {
					double tenderLateInterest = NumberUtils.format2(repayLateInterest * (cCapital / totalRepay));
					if (tenderLateInterest > 0.0D) {
						this.accountDao.updateAccount(tenderLateInterest, tenderLateInterest, 0.0D, 0.0D, t.getUser_id());
						Account cAct = this.accountDao.getAccount(t.getUser_id());
						AccountLog cLog = new AccountLog(t.getUser_id(), Constant.LATE_REPAYMENT, model.getUser_id(), DateUtils.getNowTimeStr(), "");
						fillAccountLog(cLog, cAct, tenderLateInterest);
						cLog.setRemark("[" + logRemarkHtml(model)
								+ "]还息，归还逾期利息"
								+ NumberUtils.format4(tenderLateInterest));
						this.accountLogDao.addAccountLog(cLog);
					}
					c.setLate_days(Long.valueOf(repay.getLate_days()).longValue());
					c.setLate_interest(String.valueOf(tenderLateInterest));
				}

				this.tenderDao.modifyRepayTender(cCapital, cInterest, t.getId());

				c.setStatus(1);
				c.setRepay_yestime(DateUtils.getNowTimeStr());
				c.setRepay_yesaccount(c.getRepay_account());
				this.collectionDao.modifyCollection(c);

				if (lastPeriod) {
					double awardValue = NumberUtils.getDouble(t.getAccount()) * model.getLate_award() / 100.0D;

					if (awardValue > 0.0D) {
						this.accountDao.updateAccount(awardValue, awardValue, 0.0D, 0.0D, t.getUser_id());
						Account act = this.accountDao.getAccount(t.getUser_id());
						AccountLog log = new AccountLog();
						log.setUser_id(t.getUser_id());
						log.setTo_user(model.getUser_id());
						log.setType(Constant.AWARD_ADD);
						fillAccountLog(log, act, awardValue);
						String remark = "收到标[" + model.getName() + "]还款奖励!!";
						log.setRemark(remark);
						Date newDate = new Date();
						log.setAddtime(DateUtils.getTimeStr(newDate));
						this.accountLogDao.addAccountLog(log);

						if (Global.getWebid().equals("jsy")) {
							DetailUser user = this.userDao.getDetailUserByUserid(act.getUser_id());
							Protocol protocol = new Protocol(0L, Constant.AWARD_PROTOCOL, log.getAddtime(), log.getAddip(), "");
							protocol.setRemark("用户名为" + user.getUsername()
									+ "的用户投标（标名为" + model.getName()
									+ "的借款标）还款奖励金额" + awardValue
									+ "元，生成资金划转委托书");
							protocol.setMoney(awardValue);
							protocol.setUser_id(t.getUser_id());
							this.protocolDao.addProtocol(protocol);
						}
					}
				}
			}

		}

		int repayStatus = 7;
		if (borrow.isLastPeriod(repay.getOrder())) {
			repayStatus = 8;

			if (model.getLate_award() > 0.0D) {
				double totalLateAwardValue = NumberUtils.getDouble(model.getAccount()) * model.getLate_award() / 100.0D;
				String remark = "扣除标[" + logRemarkHtml(model) + "]还款奖励!!";

				if (totalLateAwardValue > 0.0D) {
					this.accountDao.updateAccount(-totalLateAwardValue, -totalLateAwardValue, 0.0D, 0.0D, model.getUser_id());
					Account borrowerAct = this.accountDao.getAccount(model.getUser_id());
					AccountLog borrowerLog = new AccountLog();
					borrowerLog.setUser_id(model.getUser_id());
					borrowerLog.setTo_user(1L);
					borrowerLog.setType(Constant.AWARD_DEDUCT);
					fillAccountLog(borrowerLog, borrowerAct, totalLateAwardValue);
					borrowerLog.setRemark(remark);
					Date newDate = new Date();
					borrowerLog.setAddtime(DateUtils.getTimeStr(newDate));
					this.accountLogDao.addAccountLog(borrowerLog);
				}
			}
		}

		this.borrowDao.updateBorrowStatus(repayStatus, model.getId());
		repay.setRepayment_yestime(DateUtils.getNowTimeStr());
		repay.setStatus(1);
		repay.setRepayment_yesaccount("" + (capital + interest + lateInterest));
		this.repaymentDao.modifyRepaymentYes(repay);
		try {
			NoticeConfig repay_invest = Global.getNoticeConfig("repay_invest");
			NoticeConfig repay_borrow = Global.getNoticeConfig("repay_borrow");
			if ((repay_invest != null) && (repay_invest.getSms() == 1L)) {
				List<Notice> tenderNotices = wrapCollectTenderNotice(tenderList, collectList, model, repay);
				NoticeJobQueue.NOTICE.offer(tenderNotices);
			}
			if ((repay_invest != null) && (repay_invest.getLetters() == 1L)) {
				List<Notice> tenderNotices = wrapCollectTenderNotice(tenderList, collectList, model, repay);
				NoticeJobQueue.MESSAGE.offer(tenderNotices);
			}
			if ((repay_borrow != null) && (repay_borrow.getSms() == 1L)) {
				List<Notice> borrowNotices = wrapCollectBorrowNotice(tenderList, collectList, model, repay);
				NoticeJobQueue.NOTICE.offer(borrowNotices);
			}
			if ((repay_borrow != null) && (repay_borrow.getLetters() == 1L)) {
				List<Notice> borrowNotices = wrapCollectBorrowNotice(tenderList, collectList, model, repay);
				NoticeJobQueue.MESSAGE.offer(borrowNotices);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Notice> wrapCollectTenderNotice(List<Tender> ts,
			List<DetailCollection> cs, BorrowModel b, Repayment repay) {

		List<Notice> notices = new ArrayList<Notice>();
		for (int i = 0; i < cs.size(); ++i) {
			DetailCollection c = (DetailCollection) cs.get(i);
			Tender t = (Tender) ts.get(i);
			User user = this.userDao.getUserById(t.getUser_id());
			Notice s = new Sms();
			s.setTitle("借款标[" + b.getName() + "]的第" + (repay.getOrder() + 1) + "期还款到账");
			s.setReceive_userid(t.getUser_id());
			s.setSend_userid(b.getUser_id());
			s.setContent("尊敬的" + Global.getValue("webname") + "用户["
					+ user.getUsername() + "]，投资的借款标[" + b.getName() + "]的第"
					+ (repay.getOrder() + 1) + "期，于"
					+ DateUtils.dateStr5(c.getRepay_yestime()) + "，还款本金"
					+ c.getCapital() + "元" + "，所得利息" + c.getInterest()
					+ "元，请登录网站查看详情");
			notices.add(s);
		}
		return notices;
	}

	private List<Notice> wrapCollectBorrowNotice(List<Tender> ts,
			List<DetailCollection> cs, BorrowModel b, Repayment repay) {

		List<Notice> notices = new ArrayList<Notice>();
		User user = this.userDao.getUserById(b.getUser_id());
		Notice s = new Sms();
		s.setTitle("借款标[" + b.getName() + "]已经还款成功第" + (repay.getOrder() + 1) + "期成功还款");
		s.setReceive_userid(b.getUser_id());
		s.setSend_userid(1L);
		s.setContent("尊敬的" + Global.getValue("webname") + "用户["
				+ user.getUsername() + "]，您的借款标[" + b.getName() + "]已经还款成功第"
				+ (repay.getOrder() + 1) + "期，还款时间为"
				+ DateUtils.dateStr5(repay.getRepayment_yestime()) + "，还款金额为"
				+ repay.getRepayment_yesaccount() + "元，请登录网站查看详情");
		notices.add(s);
		return notices;
	}

	public void autoFlowRepay(DetailCollection c) {
		DetailCollection dbCollect = this.collectionDao.getCollection(c.getId());
		if (dbCollect.getStatus() != 0) {
			this.logger.error("该改待收计划已经还款:cid=" + dbCollect.getId() + ",status=" + dbCollect.getStatus());
			return;
		}
		double repay = NumberUtils.getDouble(c.getRepay_account());
		double interest = NumberUtils.getDouble(c.getInterest());
		double capital = NumberUtils.getDouble(c.getCapital());
		BorrowModel model = this.borrowDao.getBorrowById(c.getBorrow_id());
		Tender t = this.tenderDao.getTenderById(c.getTender_id());

		this.accountDao.updateAccount(-repay, -repay, 0.0D, 0.0D, model.getUser_id());
		Account act = this.accountDao.getAccount(model.getUser_id());
		AccountLog log = new AccountLog(model.getUser_id(), Constant.REPAID, t.getUser_id(), DateUtils.getNowTimeStr(), "");
		fillAccountLog(log, act, repay);
		log.setRemark("[" + logRemarkHtml(model) + "]还款，扣除还款本息" + NumberUtils.format4(repay));
		this.accountLogDao.addAccountLog(log);

		if (Global.getWebid().equals("jsy")) {
			DetailUser user = this.userDao.getDetailUserByUserid(model.getUser_id());
			Protocol protocol = new Protocol(0L, Constant.REPAYMENT_ACCOUNT_PROTOCOL, DateUtils.getNowTimeStr(), "", "");
			protocol.setRemark("用户名为" + user.getUsername() + "的用户进行还款（标名为"
							+ model.getName() + "的借款标）,归还本息" + repay
							+ "元，生成借款人归还本息委托书");
			protocol.setUser_id(model.getUser_id());
			protocol.setMoney(repay);
			protocol.setRepayment_account(capital);
			protocol.setInterest(interest);
			protocol.setBorrow_id(c.getBorrow_id());
			protocol.setRepayment_time(c.getRepay_time());
			this.protocolDao.addProtocol(protocol);
		}

		this.accountDao.updateAccount(0.0D, repay, 0.0D, -repay, t.getUser_id());
		Account cAct = this.accountDao.getAccount(t.getUser_id());
		AccountLog cLog = new AccountLog(t.getUser_id(), Constant.REPAID, model.getUser_id(), DateUtils.getNowTimeStr(), "");
		fillAccountLog(cLog, cAct, repay);
		cLog.setRemark("[" + logRemarkHtml(model) + "]还款，流转标还款本息" + NumberUtils.format4(repay));
		this.accountLogDao.addAccountLog(cLog);

		double borrow_fee_precent = Global.getDouble(Constant.BORROW_FEE);
		double borrow_fee = borrow_fee_precent * interest;
		this.accountDao.updateAccount(-borrow_fee, -borrow_fee, 0.0D, 0.0D, t.getUser_id());
		cAct = this.accountDao.getAccount(t.getUser_id());
		cLog = new AccountLog(t.getUser_id(), Constant.MANAGE_FEE, 1L, DateUtils.getNowTimeStr(), "");
		fillAccountLog(cLog, cAct, borrow_fee);
		cLog.setRemark("[" + logRemarkHtml(model) + "]还款，扣除利息管理费" + NumberUtils.format4(borrow_fee));
		this.accountLogDao.addAccountLog(cLog);

		this.tenderDao.modifyRepayTender(capital, interest, t.getId());

		c.setStatus(1);
		c.setRepay_yestime(DateUtils.getNowTimeStr());
		c.setRepay_yesaccount(c.getRepay_account());
		this.collectionDao.modifyCollection(c);

		int count = this.borrowDao.releaseFlowBorrow(NumberUtils.getDouble(c.getCapital()), model.getId());
		if (count < 1) {
			throw new BorrowException("释放流转标已经回款的金额！");
		}

		if (model.getLate_award() <= 0.0D) {
			return;
		}

		String remark = "收到 标[" + logRemarkHtml(model) + "]还款奖励!!";
		double awardValue = NumberUtils.getDouble(t.getAccount()) * model.getLate_award() / 100.0D;

		if (awardValue > 0.0D) {
			this.accountDao.updateAccount(awardValue, awardValue, 0.0D, 0.0D, t.getUser_id());
			act = this.accountDao.getAccount(t.getUser_id());
			log.setUser_id(t.getUser_id());
			log.setTo_user(model.getUser_id());
			log.setType(Constant.AWARD_ADD);
			log.setMoney(awardValue);
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark(remark);
			this.accountLogDao.addAccountLog(log);

			remark = "扣除标[" + logRemarkHtml(model) + "]还款奖励!!";

			this.accountDao.updateAccount(-awardValue, -awardValue, 0.0D, 0.0D, model.getUser_id());
			Account borrowerAct = this.accountDao.getAccount(model.getUser_id());
			AccountLog borrowerLog = new AccountLog();
			borrowerLog.setUser_id(model.getUser_id());
			borrowerLog.setTo_user(1L);
			borrowerLog.setType(Constant.AWARD_DEDUCT);
			fillAccountLog(borrowerLog, borrowerAct, awardValue);
			borrowerLog.setRemark(remark);
			Date newDate = new Date();
			borrowerLog.setAddtime(DateUtils.getTimeStr(newDate));
			this.accountLogDao.addAccountLog(borrowerLog);
		}
	}

	private Message fillSiteMessage(long receive_user, String title,
			String content, long sent_user, Message message) {
		message.setSent_user(receive_user);
		message.setReceive_user(sent_user);
		message.setStatus(0);
		message.setType(Constant.SYSTEM);
		message.setName(title);
		message.setContent(content);
		message.setAddtime(DateUtils.getNowTimeStr());
		return message;
	}

	public void batchRepayTimer() {
		long now = System.currentTimeMillis() / 1000L;
		List<Repayment> list = this.repaymentDao.getAllRepaymentList(0, now - 259200L, now);
		User u = null;
		BorrowModel model = null;
		for (Repayment r : list) {
			model = this.borrowDao.getBorrowById(r.getBorrow_id());
			if (model != null) {
				u = this.userDao.getUserById(model.getUser_id());
				if (u != null) {
					Message message = fillSiteMessage(u.getUser_id(), "还款通知", model.getName() + "将于"
									+ DateUtils.dateStr2(r.getRepayment_time())
									+ "到期，请及时还款！", 1L, new Message());
					this.messageDao.addMessage(message);
				}
			}
		}
	}

	private void deductFee(BorrowModel borrow, AccountLog log) {
		BorrowModel model = borrow.getModel();

		double fee = borrow.getManageFee();
		if (fee > 0.0D) {
			Account act = this.accountDao.getAccount(model.getUser_id());
			this.accountDao.updateAccount(-fee, -fee, 0.0D, 0.0D, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			log = new AccountLog(model.getUser_id(), Constant.BORROW_FEE, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, fee);
			log.setRemark("[" + logRemarkHtml(model) + "]交易手续费" + NumberUtils.format4(fee));
			this.accountLogDao.addAccountLog(log);
		}

		fee = borrow.getTransactionFee();
		if (fee > 0.0D) {
			Account act = this.accountDao.getAccount(model.getUser_id());
			this.accountDao.updateAccount(-fee, -fee, 0.0D, 0.0D, act.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			log = new AccountLog(model.getUser_id(), Constant.TRANSACTION_FEE, 1L, DateUtils.getNowTimeStr(), "");
			fillAccountLog(log, act, fee);
			log.setRemark("[" + logRemarkHtml(model) + "]借款管理费" + NumberUtils.format4(fee));
			this.accountLogDao.addAccountLog(log);
		}
	}

	private String logRemarkHtml(BorrowModel model) {
		String s = "<a href='" + Global.getString("weburl")
				+ "/invest/detail.html?borrowid=" + model.getId()
				+ "' target=_blank>" + model.getName() + "</a>";
		return s;
	}

	
	/**
	 * 未完成
	 */
	public void autoDealTender(BorrowModel borrow) {
		boolean isOk = true;
		String checkMsg = "";
		List<BorrowAuto> list = new ArrayList<BorrowAuto>();
		List<BorrowAuto> newBorrowAutoList = this.borrowAutoDao.getBorrowAutoListByStatus(1L);
		List<AutoTenderOrder> autoTenderOrderlist = this.borrowAutoDao.getAutoTenderOrder();
		if (autoTenderOrderlist.size() > 0) {
			for (AutoTenderOrder autoTenderOrder : autoTenderOrderlist) {
				for (BorrowAuto borrowAuto : newBorrowAutoList) {
					if (autoTenderOrder.getUser_id() == borrowAuto.getUser_id()) {
						list.add(borrowAuto);
					}
				}
			}
		} else {
			list = newBorrowAutoList;
		}
		String auto_tender_max_apr = Global.getValue("auto_tender_max_apr");
		double auto_tender_max_account = NumberUtils.getDouble(borrow.getAccount()) * NumberUtils.getDouble(auto_tender_max_apr);
		borrow.setAccount(NumberUtils.format2Str(auto_tender_max_account));
		borrow.setMoney(auto_tender_max_account);

		// label3723:
		if (list.size() > 0) {
			// label1192: label4223:
			for (BorrowAuto auto : list) {
				String pwd = StringUtils.isNull(borrow.getPwd());
				if (!StringUtils.isBlank(pwd))
					break;
				try {
					AutoTenderOrder autoTenderOrder = this.borrowAutoDao.getAutoTenderOrder(auto.getUser_id());

					int auto_times = this.tenderDao.getAutoTenderByUserid(auto.getUser_id(), 1L, borrow.getId(), 19L);
					if (auto_times > 0) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}
					//double tender_max_account = auto.getTender_account();
					long borrow_id = borrow.getId();
					User user = this.userDao.getUserByUserid(auto.getUser_id());
					this.logger.info("Begin tender! uid=" + user.getUser_id()
							+ ",bid=" + borrow_id + ",tender_money="
							+ borrow.getMoney());
					BorrowModel model = this.borrowDao.getBorrowById(borrow_id);
					if (auto_tender_max_account <= NumberUtils.getDouble(model.getAccount_yes())) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4233:
						continue;
					}
					BorrowModel wrapModel = BorrowHelper.getHelper(model);
					double tenderNum = auto.getTender_account();

					Account act = this.accountDao.getAccount(auto.getUser_id());
					double account_num = NumberUtils.getDouble(borrow.getAccount());
					double account_yes_num = NumberUtils.getDouble(borrow.getAccount_yes());

					if (account_yes_num >= account_num) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4233:
						continue;
					}

					double useMoney = act.getUse_money();
					if (tenderNum > useMoney) {
						tenderNum = useMoney;
						borrow.setMoney(tenderNum);
					}
					AccountLog log = new AccountLog(user.getUser_id(), Constant.TENDER, model.getUser_id(), DateUtils.getNowTimeStr(), "");
					log.setRemark(getLogRemark(wrapModel.getModel()));

					if (auto.getStatus() != 1) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}
					if (auto.getTender_account() == 0) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if ((auto.getBorrow_style_status() == 1) && (auto.getBorrow_style() != NumberUtils.getInt(borrow.getStyle()))) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if ((auto.getApr_status() == 1) && (((auto.getApr_first() > borrow.getApr()) || (auto.getApr_last() < borrow.getApr())))) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if (auto.getTimelimit_status() == 1) {
						if (borrow.getIsday() != 1) {
							if ((auto.getTimelimit_month_first() <= NumberUtils.getInt(borrow.getTime_limit()))
									&& (auto.getTimelimit_month_last() >= NumberUtils.getInt(borrow.getTime_limit()))) {

								// break label1192;
							}

							this.logger.info("End tender! uid="
									+ auto.getUser_id() + ",bid="
									+ borrow.getId() + ",tender_money="
									+ borrow.getMoney());

							// break label4223:
							continue;
						}

						if ((auto.getTimelimit_day_first() > borrow.getTime_limit_day())
								|| (auto.getTimelimit_day_last() < borrow.getTime_limit_day())) {
							this.logger.info("End tender! uid="
									+ auto.getUser_id() + ",bid="
									+ borrow.getId() + ",tender_money="
									+ borrow.getMoney());

							// break label4223:
							continue;
						}

					}

					if (auto.getAward_status() == 1) {
						if (borrow.getAward() == 0) {
							this.logger.info("End tender! uid="
									+ auto.getUser_id() + ",bid="
									+ borrow.getId() + ",tender_money="
									+ borrow.getMoney());

							// break label4223:
							continue;
						}
						double part_account = 0.0D;
						if (borrow.getAward() == 2)
							part_account = borrow.getFunds()
									/ NumberUtils.getDouble(borrow.getAccount());
						else {
							part_account = borrow.getPart_account();
						}
						if ((auto.getAward_first() > part_account)
								|| (auto.getAward_last() < part_account)) {
							this.logger.info("End tender! uid="
									+ auto.getUser_id() + ",bid="
									+ borrow.getId() + ",tender_money="
									+ borrow.getMoney());

							// break label4223:
							continue;
						}

					}

					if ((((borrow.getIs_xin() != 1) || (auto.getXin_status() != 1)))
							&& (((borrow.getIs_jin() != 1) || (auto.getJin_status() != 1)))
							&& (((borrow.getIs_fast() != 1) || (auto.getFast_status() != 1)))
							&& (((borrow.getIs_offvouch() != 1) || (auto.getVouch_status() != 1)))) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if (user.getIslock() == 1) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if (borrow.getUser_id() == user.getUser_id()) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if (tenderNum > useMoney) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					double lowest_account_num = NumberUtils.getDouble(borrow.getLowest_account());
					if (tenderNum < lowest_account_num) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					if (tenderNum <= 0.0D) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}

					Tender tender = new Tender();
					tender.setBorrow_id(model.getId());
					if (model.getIs_flow() == 1)
						tender.setMoney("" + (wrapModel.getFlow_count() * model.getFlow_money()));
					else {
						tender.setMoney("" + tenderNum);
					}
					tender.setAddtime("" + new Date().getTime() / 1000L);
					tender.setAddip("");
					tender.setUser_id(user.getUser_id());
					tender.setAuto_repurchase(0);
					tender.setIs_auto_tender(1);

					log.setRemark(getLogRemark(wrapModel.getModel()));

					//Protocol protocol = new Protocol(tender.getId(), Constant.TENDER_PROTOCOL, "", "", "");
					String logRemark = log.getRemark();

					tenderLog("Begin tender service!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());

					double validAccount = 0.0D;
					double tenderAccount = NumberUtils.getDouble(tender.getMoney());
					double account_val = auto_tender_max_account;
					double account_yes_val = NumberUtils.getDouble(model.getAccount_yes());
					long tender_times_val = NumberUtils.getLong(model.getTender_times());

					if (account_yes_val >= account_val) {
						tenderLog("Borrow is full!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);

						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4233:
						continue;
					}
					if (tenderAccount + account_yes_val >= account_val) {
						validAccount = account_val - account_yes_val;
						borrow.skipReview();
						if (model.getStatus() == 3) {
							if (borrow.getBorrowType() == Constant.TYPE_SECOND) {
								double interest = borrow.calculateInterest();
								double award = borrow.calculateBorrowAward();
								double freeze = interest + award;
								this.accountDao.updateAccount(0.0D, freeze, -freeze, model.getUser_id());
								Account borrowAct = this.accountDao.getAccount(model.getUser_id());
								log.setUser_id(model.getUser_id());
								log.setTo_user(1L);
								log.setType(Constant.UNFREEZE);
								log.setMoney(freeze);
								log.setTotal(borrowAct.getTotal());
								log.setUse_money(borrowAct.getUse_money());
								log.setNo_use_money(borrowAct.getNo_use_money());
								log.setCollection(borrowAct.getCollection());
								log.setAddtime(DateUtils.getNowTimeStr());
								log.setRemark(log.getRemark() + "解冻资金" + NumberUtils.format4(freeze));
								this.accountLogDao.addAccountLog(log);
							}
							model.setVerify_time(DateUtils.getNowTimeStr());
							this.borrowDao.updateJinBorrow(model);
						}
					} else {
						validAccount = tenderAccount;
					}
					tenderLog("Tender service,get validAccount!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
					model.setAccount_yes("" + (account_yes_val + validAccount));
					model.setTender_times("" + (tender_times_val + 1L));
					Account newAccount = this.accountDao.getAccount(tender.getUser_id());
					this.logger.info("Tender service,New account use_money! uid="
									+ tender.getUser_id() + ",bid="
									+ model.getId() + ",money="
									+ tender.getMoney() + ",account="
									+ newAccount.getUse_money());
					if (validAccount > newAccount.getUse_money()) {
						tenderLog("Tender fail,not enough money!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
						throw new BorrowException("投资金额大于您的可用金额，投标失败！");
					}

					if ((Global.getWebid().equals("xdcf"))
							&& (model.getIs_flow() == 1)
							&& (model.getIsday() == 1)
							&& (model.getTime_limit_day() == 7)) {
						double flow_month_tender_collection = this.accountDao.getFlowMonthTenderCollection(tender.getUser_id());
						double flow_day_tender_collection = this.accountDao.getFlowDayTenderCollection(tender.getUser_id());
						if (flow_day_tender_collection + validAccount > flow_month_tender_collection) {
							tenderLog("Tender fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
							throw new BorrowException("您的待收金额小于" + validAccount + ",不能进行投流转标，投标失败！");
						}

					}

					double mbTenderAccount = NumberUtils.getDouble(Global.getValue("mb_tender_account"));
					if ((mbTenderAccount > 0.0D)
							&& (NumberUtils.format2(newAccount.getCollection()) < mbTenderAccount)
							&& (model.getIs_mb() == 1)) {
						tenderLog("Tender fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);
						throw new BorrowException("您的待收金额小于" + mbTenderAccount + ",不能进行投秒标，投标失败！");
					}

					double collectionLimit = NumberUtils.getDouble(model.getCollection_limit());

					if ((collectionLimit > 0.0D) && (NumberUtils.format2(newAccount.getCollection()) < NumberUtils.format2(collectionLimit))) {
						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}
					tenderLog("Tender success!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);

					int count = this.borrowDao.updateBorrow(validAccount, model.getStatus(), model.getId());
					if (count < 1) {
						tenderLog("Update borrow fail!", tender.getUser_id(), model.getId(), tender.getMoney(), "" + validAccount);

						this.logger.info("End tender! uid=" + auto.getUser_id()
								+ ",bid=" + borrow.getId() + ",tender_money="
								+ borrow.getMoney());

						// break label4223:
						continue;
					}
					if (model.getBorrowType() == Constant.TYPE_FLOW) {
						this.borrowDao.updateBorrowFlow(borrow);
					}

					tender.setMoney("" + NumberUtils.format6(tenderAccount));
					tender.setAccount("" + NumberUtils.format6(validAccount));
					tender = this.tenderDao.addTender(tender);
					validAccount = NumberUtils.getDouble(tender.getAccount());
					InterestCalculator ic = wrapModel
							.interestCalculator(validAccount);
					List<MonthInterest> monthList = ic.getMonthList();

					List<Collection> collectList = new ArrayList<Collection>();
					int i = 0;
					for (MonthInterest mi : monthList) {
						Collection c = fillCollection(mi, tender, ic);
						c.setOrder(i++);
						if (borrow.getBorrowType() == Constant.TYPE_FLOW) {
							String repayTime = "";
							if (model.getIsday() == 1)
								repayTime = DateUtils.rollDay(tender
										.getAddtime(), ""
										+ model.getTime_limit_day());
							else {
								repayTime = DateUtils.rollMonth(tender
										.getAddtime(), model.getTime_limit());
							}
							c.setRepay_time(repayTime);
						}
						collectList.add(c);
					}
					this.collectionDao.addBatchCollection(collectList);

					double repayment_account = ic.getTotalAccount();
					double repayment_interest = repayment_account
							- NumberUtils.getDouble(tender.getAccount());
					tender.setRepayment_account(""
							+ NumberUtils.format6(repayment_account));
					tender.setInterest(""
							+ NumberUtils.format6(repayment_interest));
					if (model.getBorrowType() == Constant.TYPE_FLOW) {
						tender.setWait_account(""
								+ NumberUtils.format6(repayment_account));
						tender.setWait_interest(""
								+ NumberUtils.format6(repayment_interest));
					}

					tender = this.tenderDao.modifyTender(tender);

					int row = 0;
					try {
						row = this.accountDao.updateAccountNotZero(0.0D,
								-validAccount, validAccount, auto.getUser_id());
					} catch (Exception e) {
						tenderLog("freeze account fail!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
						this.logger.error(e.getMessage());

						if (row >= 1){
							// break label3723;
						}
						throw new BorrowException("投资人冻结投资款失败！请注意您的可用余额。");
					} finally {
						if (row < 1)
							throw new BorrowException("投资人冻结投资款失败！请注意您的可用余额。");
					}
					tenderLog("freeze account success!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
					act = this.accountDao.getAccount(act.getUser_id());
					log.setMoney(validAccount);
					log.setTotal(act.getTotal());
					log.setUse_money(act.getUse_money());
					log.setUser_id(act.getUser_id());
					log.setType(Constant.TENDER);
					log.setNo_use_money(act.getNo_use_money());
					log.setCollection(act.getCollection());
					log.setRemark(logRemark + "投资成功，冻结投资者的投标资金" + NumberUtils.format4(validAccount));
					this.accountLogDao.addAccountLog(log);

					if ((autoTenderOrder != null) && (autoTenderOrder.getId() != 0L)) {
						autoTenderOrder.setAuto_lasttime(NumberUtils.getInt(DateUtils.getNowTimeStr()));
						this.borrowAutoDao.updateAutoTenderOrder(autoTenderOrder);
					}
					tenderLog("Tender service end!", tender.getUser_id(), model.getId(), tender.getMoney(), tender.getAccount());
				} catch (BorrowException e) {
					isOk = false;
					checkMsg = e.getMessage();
					this.logger.error(e.getMessage(), e.getCause());
				} catch (Exception e) {
					isOk = false;
					checkMsg = "系统繁忙，投标失败,请稍后再试！";
					this.logger.error(e.getMessage());
					e.printStackTrace();
				} finally {
					this.logger.info("End tender! uid=" + auto.getUser_id()
							+ ",bid=" + borrow.getId() + ",tender_money="
							+ borrow.getMoney());
				}
			}
		}

		//label4233: 
		this.borrowDao.updateBorrowStatus(1, borrow.getId());
	}

	protected String getLogRemark(Borrow b) {
		String s = "对[<a href='" + Global.getValue("weburl")
				+ "/invest/detail.html?borrowid=" + b.getId()
				+ "' target=_blank>" + b.getName() + "</a>]";
		return s;
	}

	public void autoVerifyFullSuccessForDonation(BorrowModel borrow) {
		BorrowModel model = borrow.getModel();
		BorrowModel dbModel = this.borrowDao.getBorrowById(model.getId());
		if (dbModel.getStatus() == 6) {
			this.logger.error("该借款表的状态已经处在放款状态！");
			return;
		}
		List<BorrowTender> tenderList = this.tenderDao.allTenderListBybid(model.getId());
		for (int i = 0; i < tenderList.size(); ++i) {
			Tender t = (Tender) tenderList.get(i);
			this.accountDao.updateAccount(-NumberUtils.getDouble(t.getAccount()), 0.0D, -NumberUtils.getDouble(t.getAccount()), 0.0D, t.getUser_id());
			Account act = this.accountDao.getAccount(t.getUser_id());
			act = this.accountDao.getAccount(act.getUser_id());
			AccountLog log = new AccountLog(t.getUser_id(), Constant.INVEST, model.getUser_id(), DateUtils.getNowTimeStr(), "");
			log.setType(Constant.INVEST);
			log.setMoney(NumberUtils.getDouble(t.getAccount()));
			log.setTotal(act.getTotal());
			log.setUse_money(act.getUse_money());
			log.setNo_use_money(act.getNo_use_money());
			log.setCollection(act.getCollection());
			log.setRemark("扣除投资["
					+ logRemarkHtml(model)
					+ "]冻结资金"
					+ NumberUtils.format4(NumberUtils.getDouble(t.getAccount()))
					+ ",感谢你爱的奉献!");
			this.accountLogDao.addAccountLog(log);

			double tenderAccount = NumberUtils.getDouble(t.getAccount());
			double credit = 0.0D;
			if (model.getIsday() == 1)
				credit = tenderAccount * model.getTime_limit_day() / 3000.0D;
			else {
				credit = tenderAccount * NumberUtils.getInt(model.getTime_limit()) / 100.0D;
			}

			double awardValue = 0.0D;
			String remark = "收到标[" + model.getName() + "]投资奖励!!";

			if (model.getAward() == 1)
				awardValue = NumberUtils.getDouble(t.getAccount()) * model.getPart_account() / 100.0D;
			else if (model.getAward() == 2)
				awardValue = model.getFunds() / NumberUtils.getDouble(model.getAccount()) * NumberUtils.getDouble(t.getAccount());
			else {
				awardValue = 0.0D;
			}
			if (awardValue > 0.0D) {
				this.accountDao.updateAccount(awardValue, awardValue, 0.0D, 0.0D, t.getUser_id());
				act = this.accountDao.getAccount(t.getUser_id());
				log.setUser_id(t.getUser_id());
				log.setTo_user(model.getUser_id());
				log.setType(Constant.AWARD_ADD);
				fillAccountLog(log, act, awardValue);
				log.setRemark(remark);
				this.accountLogDao.addAccountLog(log);
			}

		}

		double money = NumberUtils.getDouble(model.getAccount());
		Account act = this.accountDao.getAccount(model.getUser_id());
		this.accountDao.updateAccount(money, money, 0.0D, 0.0D, act.getUser_id());
		act = this.accountDao.getAccount(act.getUser_id());
		AccountLog log = new AccountLog(model.getUser_id(), Constant.BORROW_SUCCESS, 1L, DateUtils.getNowTimeStr(), "");
		fillAccountLog(log, act, money);
		log.setRemark("[" + logRemarkHtml(model) + "]借款入账" + NumberUtils.format4(money));
		this.accountLogDao.addAccountLog(log);

		double totalAwardValue = 0.0D;
		String remark = "扣除标[" + logRemarkHtml(model) + "]投资奖励!!";
		if (model.getAward() == 1)
			totalAwardValue = NumberUtils.getDouble(model.getAccount()) * model.getPart_account() / 100.0D;
		else if (model.getAward() == 2)
			totalAwardValue = model.getFunds();
		else {
			totalAwardValue = 0.0D;
		}
		if (totalAwardValue > 0.0D) {
			this.accountDao.updateAccount(-totalAwardValue, -totalAwardValue, 0.0D, 0.0D, model.getUser_id());
			act = this.accountDao.getAccount(model.getUser_id());
			log.setUser_id(model.getUser_id());
			log.setTo_user(1L);
			log.setType(Constant.AWARD_DEDUCT);
			fillAccountLog(log, act, totalAwardValue);
			log.setRemark(remark);
			this.accountLogDao.addAccountLog(log);
		}

		deductFee(borrow, log);

		this.borrowDao.updateBorrowStatus(8, model.getId());
	}

	private void tenderLog(String type, long user_id, long bid, String money,
			String account) {
		this.logger.info(type + " uid=" + user_id + ",bid=" + bid + ",money=" + money + ",account=" + account);
	}

	private Collection fillCollection(MonthInterest mi, Tender t,
			InterestCalculator ic) {
		Collection c = new Collection();
		c.setTender_id(t.getId());

		c.setInterest("" + mi.getInterest());
		c.setCapital("" + mi.getAccountPerMon());
		c.setRepay_account("" + (mi.getInterest() + mi.getAccountPerMon()));
		c.setAddtime("" + new Date().getTime() / 1000L);

		c.setSite_id(0L);
		c.setStatus(0);
		c.setRepay_yesaccount("0");
		c.setLate_days(0L);
		c.setLate_interest("0");
		c.setUser_id(t.getUser_id());
		return c;
	}

	public List<AutoTenderOrder> getAutoTenderOrderList() {
		List<AutoTenderOrder> list = this.borrowAutoDao.getAutoTenderOrder();

		return list;
	}

	public AutoTenderOrder getAutoTenderOrderByUserid(long user_id) {
		return this.borrowAutoDao.getAutoTenderOrder(user_id);
	}

	public RuleDao getRuleDao() {
		return this.ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public BorrowAutoDao getBorrowAutoDao() {
		return this.borrowAutoDao;
	}

	public void setBorrowAutoDao(BorrowAutoDao borrowAutoDao) {
		this.borrowAutoDao = borrowAutoDao;
	}

	public ProtocolDao getProtocolDao() {
		return this.protocolDao;
	}

	public void setProtocolDao(ProtocolDao protocolDao) {
		this.protocolDao = protocolDao;
	}

	public BorrowDao getBorrowDao() {
		return this.borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public TenderDao getTenderDao() {
		return this.tenderDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public CollectionDao getCollectionDao() {
		return this.collectionDao;
	}

	public void setCollectionDao(CollectionDao collectionDao) {
		this.collectionDao = collectionDao;
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

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}

	public RepaymentDao getRepaymentDao() {
		return this.repaymentDao;
	}

	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserCacheDao getUserCacheDao() {
		return this.userCacheDao;
	}

	public void setUserCacheDao(UserCacheDao userCacheDao) {
		this.userCacheDao = userCacheDao;
	}

	public UserAmountDao getUserAmountDao() {
		return this.userAmountDao;
	}

	public void setUserAmountDao(UserAmountDao userAmountDao) {
		this.userAmountDao = userAmountDao;
	}

	public MessageDao getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public RewardStatisticsDao getRewardStatisticsDao() {
		return this.rewardStatisticsDao;
	}

	public void setRewardStatisticsDao(RewardStatisticsDao rewardStatisticsDao) {
		this.rewardStatisticsDao = rewardStatisticsDao;
	}
}