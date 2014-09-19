package com.liuyun.site.test.util;

import com.liuyun.site.domain.Borrow;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.BorrowService;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application*.xml" })
public class BorrowServiceTest {

	@Resource
	private BorrowService borrowService;

	@Resource
	private AccountService accountService;

	@Test
	public void testModifyBonusAprById() {
		throw new Error("Unresolved compilation problem: \n");
	}

	@Test
	public void testExcelExport() {
		throw new Error("Unresolved compilation problem: \n");
	}

	@Test
	public void testTender() throws Throwable {
		throw new Error("Unresolved compilation problem: \n");
	}

	protected String getLogRemark(Borrow paramBorrow) {
		throw new Error("Unresolved compilation problem: \n");
	}

	protected void tender(long paramLong1, long paramLong2, double paramDouble) {
		throw new Error("Unresolved compilation problem: \n");
	}

	@Test
	public void testTenderList() throws Throwable {
		throw new Error("Unresolved compilation problem: \n");
	}

	class TenderThread {
		private long user_id;
		private long borrow_id;
		private double money;

		private TenderThread(long arg2, long arg4, double arg6) {
		}

		public void runTest() throws Throwable {
			throw new Error(
					"Unresolved compilation problem: \n\tThe method runTest() of type BorrowServiceTest.TenderThread must override or implement a supertype method\n");
		}
	}
}