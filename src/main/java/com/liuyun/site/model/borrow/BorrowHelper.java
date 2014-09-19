package com.liuyun.site.model.borrow;

import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.Tender;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import java.util.Date;

public class BorrowHelper {
	public static BorrowModel getHelper(int type, BorrowModel model) {
		switch (type) {
		case 100:
			return new BaseBorrowModel(model);
		case 101:
			return new SecondBorrowModel(model);
		case 102:
			return new CreditBorrowModel(model);
		case 103:
			return new MortgageBorrowModel(model);
		case 104:
			return new PropertyBorrowModel(model);
		case 105:
			return new MortgageBorrowModel(model);
		case 106:
			return new ArtBorrowModel(model);
		case 107:
			return new CharityBorrowModel(model);
		case 108:
			return new MortgageBorrowModel(model);
		case 109:
			return new ProjectBorrowModel(model);
		case 110:
			return new FlowBorrowModel(model);
		case 111:
			return new StudentBorrowModel(model);
		case 112:
			return new OffVouchBorrowModel(model);
		case 113:
			return new PledgeBorrowModel(model);
		case 114:
			return new DonationBorrowModel(model);
		}
		throw new RuntimeException("不正确的Borrow类型:" + type);
	}

	public static BorrowModel getHelper(BorrowModel model) {
		if (model.getIs_mb() == 1)
			return new SecondBorrowModel(model);
		if (model.getIs_jin() == 1)
			return new PropertyBorrowModel(model);
		if (model.getIs_fast() == 1)
			return new MortgageBorrowModel(model);
		if (model.getIs_vouch() == 1)
			return new BaseBorrowModel(model);
		if (model.getIs_xin() == 1)
			return new CreditBorrowModel(model);
		if (model.getIs_art() == 1)
			return new ArtBorrowModel(model);
		if (model.getIs_charity() == 1)
			return new CharityBorrowModel(model);
		if (model.getIs_project() == 1)
			return new ProjectBorrowModel(model);
		if (model.getIs_flow() == 1)
			return new FlowBorrowModel(model);
		if (model.getIs_student() == 1)
			return new StudentBorrowModel(model);
		if (model.getIs_offvouch() == 1)
			return new OffVouchBorrowModel(model);
		if (model.getIs_donation() == 1) {
			return new DonationBorrowModel(model);
		}
		return new BaseBorrowModel(model);
	}

	public static Date getBorrowProtocolTime(Borrow model) {
		String verifyTime = "0";
		if (model.getIs_flow() != 1) {
			if (model.getStatus() >= 3)
				verifyTime = model.getVerify_time();
		} else {
			verifyTime = model.getAddtime();
		}
		Date d = DateUtils.getDate((verifyTime == null) ? "0" : verifyTime);
		return d;
	}

	public static Date getBorrowVerifyTime(Borrow model, Tender t) {
		String verifyTime = "0";
		if (model.getIs_flow() != 1) {
			if (model.getStatus() >= 3)
				verifyTime = model.getVerify_time();
		} else {
			verifyTime = t.getAddtime();
		}
		Date d = DateUtils.getDate((verifyTime == null) ? "0" : verifyTime);
		return d;
	}

	public static Date getBorrowRepayTime(Borrow model, Tender t) {
		Date d = getBorrowVerifyTime(model, t);
		if (model.getIs_flow() != 1) {
			if (model.getStatus() < 3) {
				return DateUtils.getDate("0");
			}
			if (model.getIsday() == 1)
				return DateUtils.rollDay(d, model.getTime_limit_day());
			if (model.getIs_mb() == 1) {
				return d;
			}
			return DateUtils.rollMon(d, NumberUtils.getInt(model
					.getTime_limit()));
		}

		if (model.getIsday() == 1) {
			return DateUtils.rollDay(d, model.getTime_limit_day());
		}
		return DateUtils.rollMon(d, NumberUtils.getInt(model.getTime_limit()));
	}
}