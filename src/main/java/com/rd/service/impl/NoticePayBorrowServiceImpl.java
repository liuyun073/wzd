package com.rd.service.impl;

import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.BonusDao;
import com.rd.dao.BorrowAutoDao;
import com.rd.dao.BorrowDao;
import com.rd.dao.BorrowFlowDao;
import com.rd.dao.CollectionDao;
import com.rd.dao.MessageDao;
import com.rd.dao.RepaymentDao;
import com.rd.dao.TenderDao;
import com.rd.dao.UserAmountDao;
import com.rd.dao.UserCreditDao;
import com.rd.dao.UserDao;
import com.rd.dao.UserLogDao;
import com.rd.domain.Message;
import com.rd.domain.User;
import com.rd.service.NoticePayBorrowService;
import com.rd.tool.javamail.Mail;
import com.rd.util.DateUtils;
import com.rd.util.MessageUtils;
import com.rd.util.NumberUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class NoticePayBorrowServiceImpl implements NoticePayBorrowService {
	BorrowDao borrowDao;
	TenderDao tenderDao;
	CollectionDao collectionDao;
	BorrowAutoDao borrowAutoDao;
	AccountDao accountDao;
	AccountLogDao accountLogDao;
	UserAmountDao userAmountDao;
	BonusDao bonusDao;
	RepaymentDao repaymentDao;
	BorrowFlowDao borrowFlowDao;
	MessageDao messageDao;
	UserLogDao userLogDao;
	UserCreditDao userCreditDao;
	UserDao userDao;
	private Logger logger = Logger.getLogger(NoticePayBorrowServiceImpl.class);

	public MessageDao getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
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

	public BorrowAutoDao getBorrowAutoDao() {
		return this.borrowAutoDao;
	}

	public void setBorrowAutoDao(BorrowAutoDao borrowAutoDao) {
		this.borrowAutoDao = borrowAutoDao;
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

	public UserAmountDao getUserAmountDao() {
		return this.userAmountDao;
	}

	public void setUserAmountDao(UserAmountDao userAmountDao) {
		this.userAmountDao = userAmountDao;
	}

	public BonusDao getBonusDao() {
		return this.bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}

	public RepaymentDao getRepaymentDao() {
		return this.repaymentDao;
	}

	public void setRepaymentDao(RepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public BorrowFlowDao getBorrowFlowDao() {
		return this.borrowFlowDao;
	}

	public void setBorrowFlowDao(BorrowFlowDao borrowFlowDao) {
		this.borrowFlowDao = borrowFlowDao;
	}

	public UserLogDao getUserLogDao() {
		return this.userLogDao;
	}

	public void setUserLogDao(UserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	private Message getSiteMessage(long receive_user, String title,
			String content) {
		Message message = new Message();
		message.setSent_user(1L);
		message.setReceive_user(receive_user);
		message.setStatus(0);
		message.setType(Constant.SYSTEM);
		message.setName(title);
		message.setContent(content);
		message.setAddip("");
		message.setAddtime(DateUtils.getNowTimeStr());
		return message;
	}

	public void autoNoticePayBorrow() {
		List<Map<String, Object>> list = this.repaymentDao.getToPayRepayMent();

		for (Map<String, Object> map : list) {
			Long repaymentTime = Long.valueOf(Long.parseLong(map.get("repayment_time").toString()));
			String repaymentTimeStr = DateUtils.dateStr2(new Date(repaymentTime
					.longValue() * 1000L));

			Long currTime = Long.valueOf(System.currentTimeMillis());
			int days = (int) ((repaymentTime.longValue() * 1000L - currTime
					.longValue()) / 86400000L);
			if ((days <= 0) || (days >= 7))
				continue;
			String userid = map.get("user_id").toString();
			String account = map.get("repayment_account").toString();
			int order = Integer.parseInt(map.get("order").toString()) + 1;

			String borrowName = (String) map.get("name");

			String title = "您的借款【" + borrowName + "】第 " + order + "期还款提醒";
			String content = "您的借款【" + borrowName + "】:" + "第 " + order
					+ "期    即将到期";
			Message message = getSiteMessage(Long.parseLong(userid), title,
					content);

			User user = this.userDao.getUserById(Long.valueOf(userid)
					.longValue());
			String msg = MessageUtils.MessageRemindRepay(repaymentTimeStr,
					borrowName, String.valueOf(order));
			int total = this.messageDao.getMessageByName(
					Long.parseLong(userid), title);
			int isSend = this.messageDao.getSendMessageByContent(msg);
			if (isSend != 0)
				continue;
			try {
				String subject = Global.getValue("webname") + "-还款通知";
				String to = user.getEmail();
				Mail mail = Mail.getInstance();

				mail.readPayBorrowForBorrowerMsg();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("username", user.getUsername());
				m.put("email", user.getEmail());
				m.put("borrowname", borrowName);
				m.put("account", NumberUtils.format2Str(Double.valueOf(account)
						.doubleValue()));
				m.put("order", String.valueOf(order));
				if (total == 0) {
					this.messageDao.addMessage(message);

					String body = mail.bodyReplace(m);
					this.messageDao.sendMessage("0", subject, "3", to, user
							.getUser_id(), body, Long.toString(System
							.currentTimeMillis() / 1000L), "2");

					mail.sendMail(to, subject, body);
				}
			} catch (Exception e) {
				this.logger.debug("添加还款通知邮件异常：" + e.getMessage());
			}
		}
	}

	public void CalcLateInterest() {
		List<Map<String, Object>> list = this.repaymentDao.getLateList();
		for (Map<String, Object> map : list) {
			long id = NumberUtils.getLong(map.get("id").toString());
			long repayment_time = NumberUtils.getLong(map.get("repayment_time")
					.toString());
			long borrow_id = NumberUtils.getLong(map.get("borrow_id")
					.toString());
			double sumMoney = this.repaymentDao.getSumLateMoney(borrow_id);
			long day = (NumberUtils
					.getInt(DateUtils.getNowTimeStr().toString()) - repayment_time) / 86400L;
			if (day > 0L) {
				double overdue_fee = Double.valueOf(
						Global.getValue("overdue_fee")).doubleValue();
				double late_interest = sumMoney * day * overdue_fee;
				try {
					this.repaymentDao.updateLaterepayment(day, id,
							late_interest);
				} catch (Exception e) {
					this.logger.info("更新此标的还款计划表中此条逾期记录" + e.getMessage());
				}
			}
		}
	}
}