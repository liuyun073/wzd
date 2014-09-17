package com.rd.test.util;

import com.rd.tool.Utils;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.junit.Test;

public class NumberUtilsTest {
	private Logger logger = Logger.getLogger(NumberUtilsTest.class);

	@Test
	public void testCompare() {
		double a = 1.0E-012D;
		double b = 2.0E-012D;
		for (int i = 0; i < 50; ++i) {
			this.logger.info("BigDecimal:" + NumberUtils.compare(b, a));
		}
		for (int i = 0; i < 50; ++i)
			this.logger.info("Double:" + (b - a > 0.0D));
	}

	@Test
	public void testFormat2() {
		double d = 3343.5554999999999D;
		double f2 = NumberUtils.ceil(d);
		this.logger.info(Double.valueOf(f2));
		this.logger.info(Double.valueOf(Math.ceil(d)));
	}

	@Test
	public void testCash() {
		double cashfee = Utils.getCashFeeForZRZB(12110.0D, -32030.0D, 0.003D,
				500000.0D);
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -15);
		this.logger.info(Long.valueOf(d.getTime() / 1000L));
		this.logger.info(Double.valueOf(cashfee));
	}

	@Test
	public void testDateRoll() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 11, 20, 23, 59, 59);
		Date d = cal.getTime();
		this.logger.info(DateUtils.dateStr4(d));
		d = DateUtils.rollDay(d, 12);
		this.logger.info(DateUtils.dateStr4(d));
		this.logger.info(Long.valueOf(d.getTime() / 1000L));
	}
}