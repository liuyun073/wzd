package com.rd.freemarker.method;

import com.rd.util.DateUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateRollMethodModel implements TemplateMethodModel {
	String dateStr = "";
	String yStr = "";
	String mStr = "";
	String dStr = "";
	String format = "";

	public Object exec(List args) throws TemplateModelException {
		long times = 0L;
		int year = 0;
		int mon = 0;
		int day = 0;
		if (!parseArgs(args).equals("")) {
			return "Arguments error!";
		}
		times = Long.parseLong(this.dateStr);
		year = Integer.parseInt(this.yStr);
		mon = Integer.parseInt(this.mStr);
		day = Integer.parseInt(this.dStr);
		Date d = new Date(times * 1000L);
		d = DateUtils.rollDate(d, year, mon, day);
		SimpleDateFormat sdf = new SimpleDateFormat(this.format);
		String f = sdf.format(d);
		return f;
	}

	private String parseArgs(List args) {
		if (args.size() == 4) {
			this.dateStr = ((String) args.get(0));
			this.yStr = ((String) args.get(1));
			this.mStr = ((String) args.get(2));
			this.dStr = ((String) args.get(3));
			this.format = "yyyy-MM-dd HH:mm:ss";
		} else if (args.size() == 5) {
			this.dateStr = ((String) args.get(0));
			this.yStr = ((String) args.get(1));
			this.mStr = ((String) args.get(2));
			this.dStr = ((String) args.get(3));
			this.format = ((String) args.get(4));
		} else {
			return "Arguments error!";
		}
		return "";
	}
}