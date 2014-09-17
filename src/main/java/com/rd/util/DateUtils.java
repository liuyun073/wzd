package com.rd.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
	public static String dateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 hh:mm");
		String str = format.format(date);
		return str;
	}

	public static String dateStr(Date date, String f) {
		SimpleDateFormat format = new SimpleDateFormat(f);
		String str = format.format(date);
		return str;
	}

	public static String dateStr2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	public static String dateStr5(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒");
		String str = format.format(date);
		return str;
	}

	public static String dateStr3(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(date);
		return str;
	}

	public static String dateStr4(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	public static String dateStr6(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String str = format.format(date);
		return str;
	}

	public static Date getDate(String times) {
		long time = Long.parseLong(times);
		return new Date(time * 1000L);
	}

	public static String dateStr(String times) {
		return dateStr(getDate(times));
	}

	public static String dateStr2(String times) {
		return dateStr2(getDate(times));
	}

	public static String dateStr3(String times) {
		return dateStr3(getDate(times));
	}

	public static String dateStr4(String times) {
		return dateStr4(getDate(times));
	}

	public static String dateStr5(String times) {
		return dateStr5(getDate(times));
	}

	public static long getTime(Date date) {
		return date.getTime() / 1000L;
	}

	public static int getDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(5);
	}

	public static Date valueOf(String s) {
		int YEAR_LENGTH = 4;
		int MONTH_LENGTH = 2;
		int DAY_LENGTH = 2;
		int MAX_MONTH = 12;
		int MAX_DAY = 31;

		Date d = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}

		int firstDash = s.indexOf('-');
		int secondDash = s.indexOf('-', firstDash + 1);
		if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
			String yyyy = s.substring(0, firstDash);
			String mm = s.substring(firstDash + 1, secondDash);
			String dd = s.substring(secondDash + 1);
			if ((yyyy.length() == 4) && (mm.length() == 2) && (dd.length() == 2)) {
				int year = Integer.parseInt(yyyy);
				int month = Integer.parseInt(mm);
				int day = Integer.parseInt(dd);
				if ((month >= 1) && (month <= 12)) {
					int maxDays = 31;
					switch (month) {
					case 2:
						if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
							maxDays = 29;
						else {
							maxDays = 28;
						}
						break;
					case 4:
					case 6:
					case 9:
					case 11:
						maxDays = 30;
					case 3:
					case 5:
					case 7:
					case 8:
					case 10:
					}
					if ((day >= 1) && (day <= maxDays)) {
						Calendar cal = Calendar.getInstance();
						cal.set(year, month - 1, day, 0, 0, 0);
						cal.set(14, 0);
						d = cal.getTime();
					}
				}
			}
		}
		if (d == null) {
			throw new IllegalArgumentException();
		}
		return d;
	}

	public static Map<String, Integer> getApartTime(String Begin, String end) {
		String[] temp = Begin.split("-");
		String[] temp2 = end.split("-");
		if ((temp.length > 1) && (temp2.length > 1)) {
			Calendar ends = Calendar.getInstance();
			Calendar begin = Calendar.getInstance();

			begin.set(NumberUtils.getInt(temp[0]), NumberUtils.getInt(temp[1]), NumberUtils.getInt(temp[2]));
			ends.set(NumberUtils.getInt(temp2[0]), NumberUtils.getInt(temp2[1]), NumberUtils.getInt(temp2[2]));
			if (begin.compareTo(ends) < 0) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				ends.add(1, -NumberUtils.getInt(temp[0]));
				ends.add(2, -NumberUtils.getInt(temp[1]));
				ends.add(5, -NumberUtils.getInt(temp[2]));
				map.put("YEAR", Integer.valueOf(ends.get(1)));
				map.put("MONTH", Integer.valueOf(ends.get(2) + 1));
				map.put("DAY", Integer.valueOf(ends.get(5)));
				return map;
			}
		}
		return null;
	}

	public static Date rollDay(Date d, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(5, day);
		return cal.getTime();
	}

	public static Date rollMon(Date d, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(2, mon);
		return cal.getTime();
	}

	public static Date rollYear(Date d, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(1, year);
		return cal.getTime();
	}

	public static Date rollDate(Date d, int year, int mon, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(1, year);
		cal.add(2, mon);
		cal.add(5, day);
		return cal.getTime();
	}

	public static String getNowTimeStr() {
		String str = Long.toString(System.currentTimeMillis() / 1000L);
		return str;
	}

	public static String getTimeStr(Date time) {
		long date = time.getTime();
		String str = Long.toString(date / 1000L);
		return str;
	}

	public static String getTimeStr(String dateStr, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = sdf.parse(dateStr);
		String str = getTimeStr(date);
		return str;
	}

	public static String rollMonth(String addtime, String time_limit) {
		Date t = rollDate(getDate(addtime), 0, NumberUtils.getInt(time_limit), 0);
		return "" + t.getTime() / 1000L;
	}

	public static String rollDay(String addtime, String time_limit_day) {
		Date t = rollDate(getDate(addtime), 0, 0, NumberUtils.getInt(time_limit_day));
		return "" + t.getTime() / 1000L;
	}

	public static Date getIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(13, 0);
		cal.set(12, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getLastIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 23);
		cal.set(13, 59);
		cal.set(12, 59);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getLastSecIntegralTime(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d.getTime());
		cal.set(11, 23);
		cal.set(13, 59);
		cal.set(12, 59);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static long getTime(String format) {
		long t = 0L;
		if (StringUtils.isBlank(format))
			return t;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(format);
			t = date.getTime() / 1000L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static String getCurrentWeekday() {
		int weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();

		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}
		return 1 - dayOfWeek;
	}

	public static String getMondayOFWeek() {
		int weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public static String getFirstDayOfMonth(String first) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(2, 0);
		c.set(5, 1);
		first = format.format(c.getTime());
		return first;
	}

	public static String getLastDayOfMonth(String last) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(5, ca.getActualMaximum(5));
		last = format.format(ca.getTime());
		return last;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse("2013-01-30");
			Date t = rollMon(date, 1);
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println(f.format(t));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}