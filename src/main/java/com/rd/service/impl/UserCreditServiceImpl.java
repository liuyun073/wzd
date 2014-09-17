package com.rd.service.impl;

import com.rd.common.enums.EnumIntegralCategory;
import com.rd.dao.UserCreditDao;
import com.rd.dao.jdbc.BorrowDaoImpl;
import com.rd.domain.CreditType;
import com.rd.domain.UserCredit;
import com.rd.domain.UserCreditLog;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UserCreditModel;
import com.rd.service.UserCreditService;
import com.rd.tool.Page;
import com.rd.util.DateUtils;
import java.util.List;
import org.apache.log4j.Logger;

public class UserCreditServiceImpl implements UserCreditService {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);
	private UserCreditDao userCreditDao;

	public PageDataList getUserCreditPage(int pageNo, int pageSize,
			SearchParam param) {
		PageDataList pageDataList = new PageDataList();

		if (pageSize == 0) {
			pageSize = 10;
		}
		Page pages = new Page(this.userCreditDao.getUserCreditCount(param),
				pageNo, pageSize);
		pageDataList.setList(this.userCreditDao.getUserCredit(pages.getStart(),
				pages.getPernum(), param));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public Boolean updateUserCredit(long user_id, int value, byte status,
			String nid) {
		if ((user_id == 0L) || (nid == null) || (nid.length() <= 0)) {
			logger.info("user_id or nid is null!");
			return Boolean.valueOf(false);
		}

		if (value <= 0) {
			logger.info("操作积分必须为正整数！");
			return Boolean.valueOf(false);
		}

		CreditType creditType = this.userCreditDao.getCreditTypeByNid(nid);

		if ((creditType == null) || (creditType.getCredit_category() <= 0)) {
			logger.info("积分类型为空！");
			return Boolean.valueOf(false);
		}

		UserCreditModel credit = this.userCreditDao.getCreditModelById(user_id);
		int opValue = value;
		byte creditCaregory = creditType.getCredit_category();
		byte reduce = new Byte("2").byteValue();
		byte add = new Byte("1").byteValue();
		Boolean isAddCreditLog = Boolean.valueOf(false);
		if ((credit != null) && (credit.getUser_id() > 0L)) {
			int usefulValue = this.userCreditDao.getValidValueByUserId(user_id);

			if (status == reduce) {
				if (usefulValue < value) {
					logger.info("有效积分不足！");
					return Boolean.valueOf(false);
				}
				opValue = -opValue;
			}

			if (EnumIntegralCategory.INTEGRAL_BORROW.getValue() == creditCaregory)
				this.userCreditDao.editBorrowValue(user_id, opValue);
			else if (EnumIntegralCategory.INTEGRAL_TENDER.getValue() == creditCaregory)
				this.userCreditDao.editTenderValue(user_id, opValue);
			else if (EnumIntegralCategory.INTEGRAL_GIFT.getValue() == creditCaregory)
				this.userCreditDao.editExpenseValue(user_id, opValue);
			else if (EnumIntegralCategory.INTEGRAL_EXPENSE.getValue() == creditCaregory)
				this.userCreditDao.editExpenseValue(user_id, value);
			else if (EnumIntegralCategory.INTEGRAL_VALID.getValue() == creditCaregory) {
				this.userCreditDao.editCreditValue(user_id, value);
			}
			isAddCreditLog = Boolean.valueOf(true);
		} else if ((status == add)
				&& (((credit == null) || (credit.getUser_id() <= 0L)))) {
			UserCredit uc = new UserCredit(user_id, 10, Long
					.parseLong(DateUtils.getNowTimeStr()), "");
			uc.setValue(value);
			if (EnumIntegralCategory.INTEGRAL_BORROW.getValue() == creditCaregory) {
				uc.setValid_value(value);
				uc.setBorrow_value(value);
			} else if (EnumIntegralCategory.INTEGRAL_TENDER.getValue() == creditCaregory) {
				uc.setValid_value(value);
				uc.setTender_value(value);
			} else if (EnumIntegralCategory.INTEGRAL_GIFT.getValue() == creditCaregory) {
				uc.setValid_value(value);
				uc.setGift_value(value);
			} else if (EnumIntegralCategory.INTEGRAL_EXPENSE.getValue() == creditCaregory) {
				uc.setExpense_value(value);
			}

			this.userCreditDao.addUserCredit(uc);
			isAddCreditLog = Boolean.valueOf(true);
		}

		if (isAddCreditLog.booleanValue()) {
			UserCreditLog creditLog = new UserCreditLog();
			creditLog.setAddtime(Long.parseLong(DateUtils.getNowTimeStr()));
			creditLog.setOp(status);
			creditLog.setOp_user((int) user_id);
			creditLog.setType_id((int) creditType.getId());
			creditLog.setUser_id(user_id);
			creditLog.setValue(value);
			this.userCreditDao.addUserCreditLog(creditLog);
		}
		return Boolean.valueOf(true);
	}

	public UserCredit getUserCreditByUserId(long user_id) {
		return this.userCreditDao.getUserCreditByUserId(user_id);
	}

	public int getCreditValueByUserId(long user_id, Byte type) {
		UserCredit credit = this.userCreditDao.getUserCreditByUserId(user_id);
		if (EnumIntegralCategory.INTEGRAL_BORROW.getValue() == type.byteValue())
			return credit.getBorrow_value();
		if (EnumIntegralCategory.INTEGRAL_TENDER.getValue() == type.byteValue())
			return credit.getTender_value();
		if (EnumIntegralCategory.INTEGRAL_GIFT.getValue() == type.byteValue())
			return credit.getGift_value();
		if (EnumIntegralCategory.INTEGRAL_EXPENSE.getValue() == type
				.byteValue())
			return credit.getExpense_value();
		if (EnumIntegralCategory.INTEGRAL_VALID.getValue() == type.byteValue())
			return credit.getValid_value();
		if (EnumIntegralCategory.INTEGRAL_VALUE.getValue() == type.byteValue()) {
			return credit.getValue();
		}
		return 0;
	}

	public PageDataList getCreditLogPage(int page, SearchParam param,
			long type_id, long user_id) {
		PageDataList pageDataList = new PageDataList();
		Page pages = new Page(this.userCreditDao.getUserCreditLogCount(param,
				type_id, user_id), page);
		pageDataList.setList(this.userCreditDao.getCreditLogPage(pages
				.getStart(), pages.getPernum(), param, type_id, user_id));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public List<CreditType> getCreditTypeByUserId(long user_id) {
		return this.userCreditDao.getCreditTypeByUserId(user_id);
	}

	public List<CreditType> getCreditTypeAll() {
		return this.userCreditDao.getCreditTypeAll();
	}

	public CreditType getCreditTypeByNid(String nid) {
		return this.userCreditDao.getCreditTypeByNid(nid);
	}

	public List<UserCreditLog> getCreditLogList(long user_id, long type_id) {
		return this.userCreditDao.getCreditLogList(user_id, type_id);
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}
}