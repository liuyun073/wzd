package com.rd.web.action.admin;

import com.alibaba.fastjson.JSON;
import com.rd.context.Constant;
import com.rd.domain.RewardStatistics;
import com.rd.domain.Rule;
import com.rd.domain.User;
import com.rd.model.PageDataList;
import com.rd.model.RewardStatisticsModel;
import com.rd.model.RewardStatisticsSumModel;
import com.rd.model.RuleCheckModel;
import com.rd.model.SearchParam;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.RewardStatisticsService;
import com.rd.service.RuleService;
import com.rd.service.UserService;
import com.rd.tool.jxl.ExcelHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ManageRewardAction extends BaseAction {
	private static final Logger logger = Logger
			.getLogger(ManageRewardAction.class);
	private RewardStatisticsService rewardStatisticsService;
	private UserService userService;
	private RuleService ruleService;
	BorrowModel model = new BorrowModel();

	public BorrowModel getModel() {
		return this.model;
	}

	public String showAllReward() throws Exception {
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL,
				this.model);
		String choiceType = StringUtils.isNull(this.request
				.getParameter("choiceType"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setUsername(username);
		PageDataList plist = this.rewardStatisticsService
				.getRewardStatisticsList(param, page);
		setPageAttribute(plist, param);
		this.request.setAttribute("newDate", DateUtils.getTimeStr(new Date()));
		this.request.setAttribute("map", wrapModel.getModel().getParam()
				.toMap());
		if (choiceType.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "log_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "passive_username", "receive_account",
				"receive_yesaccount" };
		String[] titles = { "收到奖励者", "发放奖励者", "应收金额", "实收金额" };
		List list = this.rewardStatisticsService.getRewardStatistics(param);
		ExcelHelper.writeExcel(infile, list, RewardStatisticsModel.class,
				Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String viewReward() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		RewardStatistics r = this.rewardStatisticsService
				.getRewardStatisticsById(id);
		if (r == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		User user = this.userService.getUserById(r.getReward_user_id());
		User passiveName = this.userService.getDetailUser(r
				.getPassive_user_id());
		int vipStatus = 0;
		if (passiveName.getVip_status() == 1) {
			vipStatus = 1;
		}
		Rule rule = this.ruleService.getRuleById(Long.valueOf(r.getRule_id()));
		String init = this.rewardStatisticsService.getRule(rule, "");
		RewardStatisticsSumModel sumModel = this.rewardStatisticsService
				.getSumAccount(r.getPassive_user_id(), r.getAddtime(), r
						.getEndtime(), init);
		RuleCheckModel checkModel = (RuleCheckModel) JSON.parseObject(rule
				.getRule_check(), RuleCheckModel.class);
		String receive_rate = checkModel.getReceive_rate();
		if ((receive_rate != null) && (receive_rate.length() > 0)) {
			r.setReceive_account(Double.valueOf(receive_rate).doubleValue()
					* sumModel.getAccount());
			this.rewardStatisticsService.updateAccount(r.getReceive_account(),
					r.getId());
		}
		this.request.setAttribute("tenderCheckMoney", Double.valueOf(checkModel
				.getTender_check_money()));
		this.request.setAttribute("account", Double.valueOf(sumModel
				.getAccount()));
		this.request.setAttribute("vipStatus", Integer.valueOf(vipStatus));
		this.request.setAttribute("reward", r);
		this.request.setAttribute("username", user.getUsername());
		this.request.setAttribute("passiveName", passiveName.getUsername());
		saveToken("verifyReward_token");
		return SUCCESS;
	}

	public String verifyReward() throws Exception {
		setMsgUrl("/admin/reward/showAllReward.html");
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		double receiveYesAccount = NumberUtils.getDouble(this.request
				.getParameter("receiveYesAccount"));
		String status = this.request.getParameter("status");
		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		String tokenMsg = checkToken("verifyReward_token");
		if (!StringUtils.isBlank(tokenMsg)) {
			message(tokenMsg);
			return ADMINMSG;
		}
		if (id <= 0L) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (receiveYesAccount <= 0.0D) {
			message("发放奖励金额为" + receiveYesAccount + ",发放奖励不能为负数！");
			return ADMINMSG;
		}
		RewardStatistics r = this.rewardStatisticsService
				.getRewardStatisticsById(id);
		if (r.getReceive_status().byteValue() == 2) {
			message("该记录已经审核通过，不允许重复操作！");
			return ADMINMSG;
		}
		try {
			this.rewardStatisticsService.verifyReward(r, status, auth_user,
					getRequestIp());
			message("审核成功!", "/admin/reward/showAllReward.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ADMINMSG;
	}

	public RewardStatisticsService getRewardStatisticsService() {
		return this.rewardStatisticsService;
	}

	public void setRewardStatisticsService(
			RewardStatisticsService rewardStatisticsService) {
		this.rewardStatisticsService = rewardStatisticsService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RuleService getRuleService() {
		return this.ruleService;
	}

	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
}