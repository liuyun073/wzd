package com.liuyun.site.tool.credit;

import com.liuyun.site.common.enums.EnumRuleBorrowCategory;
import com.liuyun.site.util.BigDecimalUtil;



public class CreditCount {
	private static int DEF_DIV_SCALE = 0;

	public static int getTenderValueMethodOne(byte type, int time,
			double tenderMoney, double checkMoney) {
		if ((tenderMoney <= 0.0D) || (checkMoney <= 0.0D) || (time <= 0))
			return 0;

		if (EnumRuleBorrowCategory.BORROW_DAY.getValue() == type) {
			double t = BigDecimalUtil.mul(tenderMoney, time);
			double f = BigDecimalUtil.div(t, checkMoney, DEF_DIV_SCALE);
			double y = BigDecimalUtil.div(f, 30.0D, DEF_DIV_SCALE);
			return (int) y;
		}
		if (EnumRuleBorrowCategory.BORROW_MONTH.getValue() == type) {
			double t = BigDecimalUtil.mul(tenderMoney, time);
			double f = BigDecimalUtil.div(t, checkMoney, DEF_DIV_SCALE);
			return (int) f;
		}
		return 0;
	}

	public static void main(String[] args) {
		//System.out.println(getTenderValueMethodOne(1, 5, 800.0D, 1000.0D));
	}
}