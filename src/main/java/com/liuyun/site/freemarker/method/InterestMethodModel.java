package com.liuyun.site.freemarker.method;

import com.liuyun.site.tool.interest.EndInterestCalculator;
import com.liuyun.site.tool.interest.InterestCalculator;
import com.liuyun.site.tool.interest.MonthEqualCalculator;
import com.liuyun.site.tool.interest.MonthInterestCalculator;
import com.liuyun.site.util.StringUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.util.List;

public class InterestMethodModel implements TemplateMethodModel {
	public Object exec(List args) throws TemplateModelException {
		if ((args != null) && (4 == args.size())) {
			double account = 0.0D;
			double apr = 0.0D;
			int period = 0;
			String type = "";
			try {
				account = Double.parseDouble((String) args.get(0));
				apr = Double.parseDouble((String) args.get(1));
				period = Integer.parseInt((String) args.get(2));
				type = StringUtils.isNull(args.get(3));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			InterestCalculator ic = null;
			double result = 0.0D;
			if (type.equals("end"))
				ic = new EndInterestCalculator(account, apr, period);
			else if (type.equals("month"))
				ic = new MonthEqualCalculator(account, apr, period);
			else if (type.equals("monthEnd"))
				ic = new EndInterestCalculator(account, apr, period, 2);
			else if (type.equals("monthInterest"))
				ic = new MonthInterestCalculator(account, apr, period);
			else if (type.equals("monthEqual")) {
				ic = new MonthEqualCalculator(account, apr, period);
			}
			ic.each();
			result = ic.getMoneyPerMonth() * ic.getPeriod();
			return Double.valueOf(result);
		}
		return "Argument is null,or argument's length illegal.";
	}
}