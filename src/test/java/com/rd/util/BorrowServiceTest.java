package com.rd.util;

import com.alibaba.fastjson.JSON;
import com.rd.context.Constant;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.Borrow;
import com.rd.domain.Tender;
import com.rd.exception.BorrowException;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.account.AccountLogModel;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.AccountService;
import com.rd.service.BorrowService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.textui.TestRunner;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application*.xml" })
public class BorrowServiceTest {
	private static final Logger logger = Logger
			.getLogger(BorrowServiceTest.class);

	@Resource
	private BorrowService borrowService;

	@Resource
	private AccountService accountService;

	@Test
	public void testModifyBonusAprById() {
		this.borrowService.modifyBonusAprById(1L, 0.1D);
	}

	@Test
	public void testExcelExport() {
		List list = this.accountService.getAccountLogList(1L);
		String[] names = { "username", "to_username", "typename", "money",
				"total", "use_money", "no_use_money", "collection", "remark",
				"addtime", "addip" };
		String[] titles = { "用户名", "交易对方", "交易类型", "操作金额", "账户总额", "可用金额",
				"冻结金额", "待收金额", "备注", "时间", "IP" };
		try {
			ExcelHelper.writeExcel("log.xls", list, AccountLogModel.class,
					Arrays.asList(names), Arrays.asList(titles));
		} catch (Exception e) {
			logger.error("导出文件失败：" + e.getMessage());
		}
	}

	@Test
	public void testTender() throws Throwable {
		tender(918L, 406L, 100.0D);
	}

	protected String getLogRemark(Borrow b) {
		String s = "对[<a href='/invest/detail.html?borrowid=" + b.getId()
				+ "' target=_blank>" + b.getName() + "</a>]";
		return s;
	}

	protected void tender(long userid, long borrow_id, double money) {
		BorrowModel model = this.borrowService.getBorrow(borrow_id);
		BorrowModel wrapModel = BorrowHelper.getHelper(model);

		AccountLog log = new AccountLog(userid, Constant.TENDER, model
				.getUser_id(), DateUtils.getNowTimeStr(), "");
		log.setRemark(getLogRemark(wrapModel.getModel()));

		Account act = this.borrowService.getAccount(userid);

		Tender tender = new Tender();
		tender.setBorrow_id(model.getId());
		tender.setMoney(money+"");
		tender.setAddtime(new Date().getTime() / 1000L+"");
		tender.setUser_id(userid);
		tender.setAuto_repurchase(1);
		try {
			this.borrowService.addTender(tender, wrapModel, act, log, null);
		} catch (BorrowException e) {
			logger.error(e.getMessage(), e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTenderList() throws Throwable {
		PageDataList pList = this.borrowService.getTenderList(410L, 1,
				new SearchParam());
		logger.info(pList.getPage());
		logger.info(pList.getList());
		logger.info(JSON.toJSONString(pList));
	}

	class TenderThread extends TestRunner {
		private long user_id;
		private long borrow_id;
		private double money;

		private TenderThread(long user_id, long borrow_id, double money) {
			this.user_id = user_id;
			this.borrow_id = borrow_id;
			this.money = money;
		}

		public void runTest() throws Throwable {
			BorrowServiceTest.logger.info("Here start....");
			for (int i = 0; i < 1; ++i)
				BorrowServiceTest.this.tender(this.user_id, this.borrow_id,
						this.money);
		}
	}
}