package com.liuyun.site.test.util;

import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.Tender;
import com.liuyun.site.exception.BorrowException;
import com.liuyun.site.model.borrow.BorrowHelper;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.util.DateUtils;
import java.util.Date;
import org.apache.log4j.Logger;

public class TenderTest {
	private static final Logger logger = Logger.getLogger(TenderTest.class);
	private BorrowService borrowService;

	public void testTender() {
		for (int i = 0; i < 1000; ++i) {
			logger.info(Thread.currentThread().getName() + ":" + i);
			tender(95L, 262L, 100.0D);
		}
	}

	protected String getLogRemark(Borrow b) {
		String s = "对[<a href='/invest/detail.html?borrowid=" + b.getId()
				+ "' target=_blank>" + b.getName() + "</a>]";
		return s;
	}

	protected void tender(long userid, long borrow_id, double money) {
		BorrowModel model = this.borrowService.getBorrow(borrow_id);
		BorrowModel wrapModel = BorrowHelper.getHelper(model);

		AccountLog log = new AccountLog(userid, Constant.TENDER,
				model.getUser_id(), DateUtils.getNowTimeStr(), "");
		log.setRemark(getLogRemark(wrapModel.getModel()));

		Account act = this.borrowService.getAccount(userid);

		Tender tender = new Tender();
		tender.setBorrow_id(model.getId());
		tender.setMoney(money + "");
		tender.setAddtime((new Date().getTime() / 1000L) + "");
		tender.setUser_id(95L);
		tender.setAuto_repurchase(1);
		try {
			this.borrowService.addTender(tender, wrapModel, act, log, null);
		} catch (BorrowException e) {
			logger.error(e.getMessage(), e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class TenderThread implements Runnable {
		private long user_id;
		private long borrow_id;
		private double money;

		private TenderThread(long user_id, long borrow_id, double money) {
			this.user_id = user_id;
			this.borrow_id = borrow_id;
			this.money = money;
		}

		public void run() {
			TenderTest.logger.info("Here start....");
			for (int i = 0; i < 500; ++i) {
				TenderTest.logger.info(Thread.currentThread().getName() + ":"
						+ i);
				TenderTest.this
						.tender(this.user_id, this.borrow_id, this.money);
			}
		}
	}
}