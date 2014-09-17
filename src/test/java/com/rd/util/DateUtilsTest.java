package com.rd.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DateUtilsTest {
	private Logger logger = Logger.getLogger(DateUtilsTest.class);

	@Test
	public void testValueOf() {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 8, 20, 0, 0, 0);
		cal.set(14, 0);
		Date d = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:ss:SSS");
		this.logger.debug(sdf.format(d));
	}

	@Test
	public void testRollDay() {
		String str = "2012-09-13";
		Date d = DateUtils.valueOf(str);
		d = DateUtils.rollDay(d, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.logger.debug(sdf.format(d));
	}

	@Test
	public void testRollDate() {
		String str = "2012-09-15";
		Date d = DateUtils.valueOf(str);
		d = DateUtils.rollDate(d, 0, 1, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.logger.debug(sdf.format(d));
	}

	@Test
	public void testRollMonth() {
		String repayTime = DateUtils.rollMonth("1352106884", "6");
		this.logger.info(repayTime);
	}

	@Test
	public void testTradeNo() {
		String no = StringUtils.generateTradeNO(324L, "E");
		this.logger.info(no);
		this.logger.info(Integer.valueOf("E000000032420121127214257".length()));
		this.logger.info(Integer.valueOf("00000003121354017722".length()));
	}

	@Test
	public void testGetIntegralTime() {
		Date nowDay = DateUtils.getIntegralTime();
		Date lastDay = DateUtils.rollDay(nowDay, -1);
		Date lastWeek = DateUtils.rollDay(nowDay, -7);
		Date lastMon = DateUtils.rollMon(nowDay, -1);
		this.logger.info(DateUtils.dateStr4(nowDay));
		this.logger.info(DateUtils.dateStr4(lastDay));
		this.logger.info(DateUtils.dateStr4(lastWeek));
		this.logger.info(DateUtils.dateStr4(lastMon));
	}

	@Test
	public void test15RollDay() {
		Date d = Calendar.getInstance().getTime();
		d = DateUtils.rollDay(d, -15);
		this.logger.info(Long.valueOf(d.getTime() / 1000L));
	}
}