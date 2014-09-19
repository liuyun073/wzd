package com.liuyun.site.test.util;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liuyun.site.dao.CashDao;
import com.liuyun.site.dao.RechargeDao;
import com.liuyun.site.model.UserAccountSummary;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.tool.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application*.xml" })
public class AccountServiceTest {
	private static final Logger logger = Logger
			.getLogger(AccountServiceTest.class);

	@Resource
	private AccountService accountService;

	@Resource
	private CashDao cashDao;

	@Resource
	private RechargeDao rechargeDao;

	@Test
	public void testGetUserAccountSummary() {
		long user_id = 271L;
		long cashMoney = 5000L;
		UserAccountSummary uas = this.accountService
				.getUserAccountSummary(user_id);
		logger.info(Double.valueOf(uas.getAccountOwnMoney()));
		double lastSum = this.rechargeDao.getLastRechargeSum(user_id);
		logger.info("lastSum:" + lastSum);
		double cashFreeze = this.cashDao.getAccountCashSum(393L, 0);
		logger.info("cashFreeze:" + cashFreeze);
		double y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
		logger.info("y:" + y);
		double fee = Utils.getCashFeeForZRZB(cashMoney, y, 0.003D, 500000.0D);
		logger.info("fee:" + fee);
	}
}