package com.rd.model.borrow;

import com.rd.context.Constant;
import com.rd.tool.interest.EndInterestCalculator;
import com.rd.tool.interest.InterestCalculator;
import com.rd.util.NumberUtils;

public class FlowBorrowModel extends BaseBorrowModel {
	private static final long serialVersionUID = 7375703874958748525L;
	private BorrowModel model;

	public FlowBorrowModel(BorrowModel model) {
		super(model);
		this.model = model;
		this.model.setIs_flow(1);
		this.model.setFlow_status(0);
		this.model.setBorrowType(Constant.TYPE_FLOW);
		this.model.setNeedBorrowFee(false);
		init();
	}

	public double calculateInterest() {
		InterestCalculator ic = interestCalculator();
		double interest = ic.getMoneyPerMonth() * ic.getPeriod()
				- getModel().getFlow_money() * getModel().getFlow_count();
		return interest;
	}

	public InterestCalculator interestCalculator() {
		BorrowModel model = getModel();
		double account = model.getFlow_money() * model.getFlow_count();
		InterestCalculator ic = interestCalculator(account);
		return ic;
	}

	public InterestCalculator interestCalculator(double validAccount) {
		BorrowModel model = getModel();
		InterestCalculator ic = null;
		double apr = model.getApr() / 100.0D;
		if (model.getIsday() == 1) {
			int time_limit_day = model.getTime_limit_day();
			ic = new EndInterestCalculator(validAccount, apr, time_limit_day);
		} else {
			int period = NumberUtils.getInt(model.getTime_limit());
			ic = new EndInterestCalculator(validAccount, apr, period, 2);
		}
		ic.each();
		return ic;
	}
}