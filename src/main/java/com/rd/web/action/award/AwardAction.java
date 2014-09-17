package com.rd.web.action.award;

import com.alibaba.fastjson.JSON;
import com.rd.common.enums.EnumAwardErrorType;
import com.rd.common.enums.EnumAwardType;
import com.rd.common.enums.EnumIntegralTypeName;
import com.rd.domain.RuleAward;
import com.rd.domain.User;
import com.rd.domain.UserAward;
import com.rd.model.award.AwardResult;
import com.rd.model.award.Awardee;
import com.rd.service.AwardService;
import com.rd.service.UserCreditService;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class AwardAction extends BaseAction {
	protected final Logger logger = Logger.getLogger(super.getClass());
	private AwardService awardService;
	private UserCreditService userCreditService;

	public AwardService getAwardService() {
		return this.awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public UserCreditService getUserCreditService() {
		return this.userCreditService;
	}

	public void setUserCreditService(UserCreditService userCreditService) {
		this.userCreditService = userCreditService;
	}

	public String award() throws Exception {
		User user = getSessionUser();
		if (user == null) {
			AwardResult result = new AwardResult();
			result.setError(EnumAwardErrorType.RESULT_NO_REGISTER);
			printJson(result);
			return null;
		}

		long ruleId = NumberUtils.getLong(this.request.getParameter("id"));
		if (ruleId == 0L) {
			AwardResult result = new AwardResult();
			result.setError(EnumAwardErrorType.RESULT_PARAMETER_ERROR);
			printJson(result);
			return null;
		}

		AwardResult result = this.awardService.award(ruleId, getSessionUser(),
				0.0D);

		if (("T".equals(result.getIs_success()))
				|| (EnumAwardErrorType.RESULT_NO_AWARD
						.equals(result.getError()))
				|| (EnumAwardErrorType.RESULT_NO_AWARD_OBJ.equals(result
						.getError()))
				|| (EnumAwardErrorType.RESULT_MONEY_LIMIT.equals(result
						.getError()))) {
			RuleAward ruleAward = this.awardService
					.getRuleAwardByRuleId(ruleId);

			if (EnumAwardType.AWARD_TYPE_POINT
					.equals(ruleAward.getAward_type())) {
				this.userCreditService.updateUserCredit(user.getUser_id(),
						ruleAward.getBase_point(), new Byte("2").byteValue(),
						EnumIntegralTypeName.INTEGRAL_AWARD.getValue());
			}
		}
		printJson(result);

		return null;
	}

	public String index() throws Exception {
		int type = NumberUtils.getInt(this.request.getParameter("type"));
		RuleAward ruleAward = this.awardService.getRuleAwardByAwardType(type);
		this.request.setAttribute("rule", ruleAward);
		return SUCCESS;
	}

	public String getAwardList() throws Exception {
		long ruleId = NumberUtils.getLong(this.request.getParameter("id"));
		boolean isOrderByLevel = false;
		if (!"".equals(StringUtils.isNull(this.request.getParameter("level")))) {
			isOrderByLevel = true;
		}
		printJson(getAwardeeList(ruleId, isOrderByLevel));
		return null;
	}

	public String getMyAwardList() throws Exception {
		long ruleId = NumberUtils.getLong(this.request.getParameter("id"));
		User user = getSessionUser();
		List<UserAward> list = null;
		if (user != null) {
			list = this.awardService.getMyAwardList(ruleId, getSessionUser()
					.getUser_id());
		}
		printJson(list);
		return null;
	}

	private List<Awardee> getAwardeeList(long ruleId, boolean isOrderByLevel) {
		List<UserAward> list = this.awardService.getAwardeeList(ruleId, 12, isOrderByLevel);
		List<Awardee> result = new ArrayList<Awardee>();
		for (UserAward userAward : list) {
			Awardee awardee = new Awardee();

			awardee.setTime(DateUtils.dateStr4(DateUtils.getDate(userAward.getAddtime())));
			awardee.setName(replaceSubString(userAward.getUser_name(), 3));
			awardee.setAward(userAward.getAward_name());
			result.add(awardee);
		}
		return result;
	}

	private void printJson(Object obj) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("data", obj);
		printJson(JSON.toJSONString(jsonMap));
	}

	public static String replaceSubString(String str, int n) {
		String sub = "";
		try {
			int length = str.length();
			if (length == 1)
				return str;
			if (length == 2) {
				n = 1;
			}
			sub = str.substring(0, str.length() - n);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < n; ++i) {
				sb = sb.append("*");
			}
			sub = sub + sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sub;
	}
}