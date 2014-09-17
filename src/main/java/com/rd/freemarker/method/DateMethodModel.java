package com.rd.freemarker.method;

import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateMethodModel implements TemplateMethodModel {
	
	@Override
	public Object exec(List args) throws TemplateModelException {
		String arg1 = "";
		String format = "yyyy-MM-dd HH:mm:ss";
		String s = "";
		if (args == null) {
			return "";
		}
		if (1 == args.size()) {
			if ((args.get(0) == null) || (args.get(0).equals("")))
				return "";
			if (args.get(0).equals("now")) {
				return System.currentTimeMillis() / 1000L;
			}

		} else if (2 == args.size()) {
			if ((args.get(1) != null) && (!args.get(1).equals("")))
				format = (String) args.get(1);
		} else if ((3 == args.size()) && (args.get(2) != null)
				&& (args.get(2).equals("vip"))) {
			return vip((String) args.get(0), (String) args.get(1));
		}

		if (format.equals("age")) {
			return getAge((String) args.get(0));
		}
		long times = 0L;
		try {
			times = Long.parseLong((String) args.get(0));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			times = 0L;
		}
		Date d = new Date(times * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			s = sdf.format(d);
		} catch (Exception e) {
			s = "ERROR！";
		}
		return s;
	}

	private String getAge(String age) throws TemplateModelException {
		Map<String, Integer> map = DateUtils.getApartTime(age, DateUtils.dateStr2(new Date()));
		if (map == null) {
			return "-1";
		}
		return "" + map.get("YEAR");
	}

	private String vip(String begin, String end) {
		Map<String, Integer> map = DateUtils.getApartTime(DateUtils.dateStr2(new Date(
				NumberUtils.getLong(begin) * 1000L)), DateUtils.dateStr2(new Date(NumberUtils.getLong(end) * 1000L)));
		return "还有" + map.get("YEAR") + "年" + map.get("MONTH") + "月" + map.get("DAY") + "天 到期";
	}
}