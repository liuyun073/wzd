package com.liuyun.site.util;

import com.liuyun.site.tool.Utils;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;

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
		double a = 2999.9994999999999D;
		this.logger.info(Double.valueOf(NumberUtils.format2(a)));
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
}