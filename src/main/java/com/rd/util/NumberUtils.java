package com.rd.util;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {
	public static double format(double d, String format) {
		DecimalFormat df = new DecimalFormat(format);
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static double format2(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static String format2Str(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		String ds = df.format(d);
		return ds;
	}

	public static double format4(double d) {
		DecimalFormat df = new DecimalFormat("0.0000");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static double format6(double d) {
		DecimalFormat df = new DecimalFormat("0.000000");
		String ds = df.format(d);
		return Double.parseDouble(ds);
	}

	public static double getDouble(String str) {
		if ((str == null) || (str.equals("")))
			return 0.0D;
		double ret = 0.0D;
		try {
			ret = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			ret = 0.0D;
		}
		return format6(ret);
	}

	public static long getLong(String str) {
		if ((str == null) || (str.equals("")))
			return 0L;
		long ret = 0L;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			ret = 0L;
		}
		return ret;
	}

	public static Long[] getLongs(String[] str) {
		if ((str == null) || (str.length < 1))
			return new Long[] { Long.valueOf(0L) };
		Long[] ret = new Long[str.length];
		for (int i = 0; i < str.length; ++i) {
			ret[i] = Long.valueOf(getLong(str[i]));
		}
		return ret;
	}

	public static int getInt(String str) {
		if ((str == null) || (str.equals("")))
			return 0;
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	public static int compare(double x, double y) {
		BigDecimal val1 = new BigDecimal(x);
		BigDecimal val2 = new BigDecimal(y);
		return val1.compareTo(val2);
	}

	public static double ceil(double d, int len) {
		String str = Double.toString(d);
		int a = str.indexOf(".");
		if (a + 3 > str.length())
			a = str.length();
		else {
			a += 3;
		}
		str = str.substring(0, a);
		return Double.parseDouble(str);
	}

	public static double ceil(double d) {
		return ceil(d, 2);
	}

	public static void main(String[] args) {
		double ac = 4.E-005D;

		double b = new BigDecimal(ac).shortValue();
		String a = format2Str(34384.339529999997D);
		System.out.println("a==ff=====" + format4(ac));
	}
}