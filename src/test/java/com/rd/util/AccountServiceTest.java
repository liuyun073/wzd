package com.rd.util;

import com.rd.dao.CashDao;
import com.rd.dao.RechargeDao;
import com.rd.model.UserAccountSummary;
import com.rd.service.AccountService;
import com.rd.tool.Utils;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
		UserAccountSummary uas = this.accountService
				.getUserAccountSummary(393L);
		logger.info(Double.valueOf(uas.getAccountOwnMoney()));
		double lastSum = this.rechargeDao.getLastRechargeSum(393L);
		logger.info("lastSum:" + lastSum);
		double cashFreeze = this.cashDao.getAccountCashSum(393L, 0);
		logger.info("cashFreeze:" + cashFreeze);
		double y = uas.getAccountOwnMoney() - lastSum - cashFreeze;
		logger.info("y:" + y);
		double fee = Utils.getCashFeeForZRZB(50000.0D, y, 0.003D, 500000.0D);
		logger.info("fee:" + fee);
	}
}