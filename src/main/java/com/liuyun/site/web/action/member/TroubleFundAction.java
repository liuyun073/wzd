package com.liuyun.site.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.common.enums.EnumAwardType;
import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.TroubleAwardRecord;
import com.liuyun.site.domain.TroubleDonateRecord;
import com.liuyun.site.domain.TroubleFundDonateRecord;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.award.AwardResult;
import com.liuyun.site.service.AwardService;
import com.liuyun.site.service.TroubleFundService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class TroubleFundAction extends BaseAction implements
		ModelDriven<TroubleFundDonateRecord> {
	private static final Logger logger = Logger
			.getLogger(TroubleFundAction.class);
	private UserService userService;
	private TroubleFundService troubleFundService;
	private AwardService awardService;
	private TroubleFundDonateRecord t = new TroubleFundDonateRecord();
	private String valicode;

	public AwardService getAwardService() {
		return this.awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public String getValicode() {
		return this.valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public String addTroubleFund() {
		String actionType = StringUtils.isNull(this.request
				.getParameter("type"));

		double trouble_apr = getTroubleApr();

		double trouble_admin_award = getTroubleAdminAward();
		double troubleAwardExtra = this.troubleFundService
				.getTroubleSum(EnumTroubleFund.FIRST.getValue());
		if (StringUtils.isBlank(actionType)) {
			this.request.setAttribute("troubleAwardExtra", Double
					.valueOf(troubleAwardExtra + trouble_admin_award));
			return SUCCESS;
		}
		String paypassword = StringUtils.isNull(this.request
				.getParameter("paypassword"));
		User user = getSessionUser();
		this.t.setUser_id(user.getUser_id());

		if (!checkValidImg(StringUtils.isNull(this.valicode))) {
			message("验证码不正确！", "");
			return MSG;
		}

		long ruleId = this.awardService
				.getRuleIdByAwardType(EnumAwardType.AWARD_TYPE_RATIO.getValue());

		double award_apr = getTroubleAwardApr();

		double troubleAdminAward = getTroubleAdminAward();

		this.awardService.updateTotalMoney(ruleId,
				(troubleAwardExtra + troubleAdminAward) * award_apr);

		AwardResult awardResult = this.awardService.award(ruleId, user, this.t
				.getMoney());
		logger.info("awardType==============" + awardResult.getError());
		logger.info("Level_no==============" + awardResult.getLevel_no());
		logger.info("money==============" + this.t.getMoney());

		double award_money = NumberUtils.getDouble(awardResult.getMoney());
		logger.info("award_money==============" + award_money);
		this.t.setAward_money(award_money);

		TroubleFundDonateRecord troubleFundDonateRecord = this.troubleFundService
				.troubleFund(this.t, paypassword, trouble_apr);

		TroubleAwardRecord troubleAwardRecord = this.troubleFundService
				.troubleAward(troubleFundDonateRecord, trouble_apr,
						EnumTroubleFund.FIRST.getValue(), award_money);

		if (troubleFundDonateRecord.getGiving_way() == EnumTroubleFund.FIRST
				.getValue()) {
			int page = NumberUtils.getInt(this.request.getParameter("page2"));
			PageDataList plist = this.troubleFundService.getTroubleFundList(
					this.t, trouble_apr, page);
			this.request.setAttribute("page", plist.getPage());
			this.request.setAttribute("list", plist.getList());
			this.request.setAttribute("param", new SearchParam().toMap());
			return SUCCESS;
		}

		if (troubleFundDonateRecord.getGiving_way() == EnumTroubleFund.ZERO
				.getValue()) {
			this.request.setAttribute("troubleFund", troubleFundDonateRecord);

			TroubleDonateRecord donate = this.troubleFundService.troubleDonate(
					troubleFundDonateRecord, trouble_apr);

			troubleAwardExtra = this.troubleFundService
					.getTroubleSum(EnumTroubleFund.FIRST.getValue());

			double troubleDonateExtra = this.troubleFundService
					.getTroubleSum(EnumTroubleFund.ZERO.getValue());
			this.request.setAttribute("troubleAwardExtra", Double
					.valueOf(troubleAwardExtra + trouble_admin_award));
			this.request.setAttribute("troubleDonateExtra", Double
					.valueOf(troubleDonateExtra));
			this.request.setAttribute("donate", donate);

			return SUCCESS;
		}
		return SUCCESS;
	}

	private double getTroubleApr() {
		String troubleAprString = StringUtils.isNull(Global
				.getValue("trouble_apr"));
		troubleAprString = (troubleAprString == "") ? "0.5" : troubleAprString;
		double trouble_apr = NumberUtils.getDouble(troubleAprString);
		return trouble_apr;
	}

	private double getTroubleAwardApr() {
		String awardAprString = StringUtils
				.isNull(Global.getValue("award_apr"));
		awardAprString = (awardAprString == "") ? "0.8" : awardAprString;
		double award_apr = NumberUtils.getDouble(awardAprString);
		return award_apr;
	}

	private double getTroubleAdminAward() {
		String troubleAdminAwardString = StringUtils.isNull(Global
				.getValue("trouble_admin_add_money"));
		troubleAdminAwardString = (troubleAdminAwardString == "") ? "200000"
				: troubleAdminAwardString;
		double trouble_admin_award = NumberUtils
				.getDouble(troubleAdminAwardString);
		return trouble_admin_award;
	}

	public String troubleFund() {
		double trouble_apr = getTroubleApr();
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String giving_way = StringUtils.isNull(this.request
				.getParameter("giving_way"));
		String donateOutStatus = StringUtils.isNull(this.request
				.getParameter("donateOutStatus"));
		this.request.setAttribute("donateOutStatus", donateOutStatus);
		this.request.setAttribute("givingWay", giving_way);
		String zero = String.valueOf(EnumTroubleFund.ZERO.getValue());
		if (zero.equals(giving_way)) {
			this.t.setGiving_way(EnumTroubleFund.ZERO.getValue());
			PageDataList plist = this.troubleFundService.getTroubleFundList(
					this.t, trouble_apr, page);
			this.request.setAttribute("page3", plist.getPage());
			this.request.setAttribute("list", plist.getList());
			this.request.setAttribute("param3", new SearchParam().toMap());
			return SUCCESS;
		}
		String first = String.valueOf(EnumTroubleFund.FIRST.getValue());
		if (first.equals(giving_way)) {
			PageDataList plist = this.troubleFundService.getTroubleFundList(
					this.t, trouble_apr, page);
			this.request.setAttribute("page2", plist.getPage());
			this.request.setAttribute("list", plist.getList());
			this.request.setAttribute("param2", new SearchParam().toMap());
			return SUCCESS;
		}

		if (first.equals(donateOutStatus)) {
			PageDataList plist = this.troubleFundService.getTroubleDonateList(
					EnumTroubleFund.FIRST.getValue(), page);
			this.request.setAttribute("page", plist.getPage());
			this.request.setAttribute("donateOutlist", plist.getList());
			this.request.setAttribute("param", new SearchParam().toMap());
			return SUCCESS;
		}
		return SUCCESS;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public TroubleFundService getTroubleFundService() {
		return this.troubleFundService;
	}

	public void setTroubleFundService(TroubleFundService troubleFundService) {
		this.troubleFundService = troubleFundService;
	}

	public TroubleFundDonateRecord getModel() {
		return this.t;
	}
}