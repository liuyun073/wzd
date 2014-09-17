package com.rd.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.ObjAward;
import com.rd.domain.RuleAward;
import com.rd.domain.UserAward;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.AwardService;
import com.rd.tool.jxl.ExcelHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.apache.struts2.ServletActionContext;

public class ManageAwardAction extends BaseAction implements
		ModelDriven<RuleAward> {
	private RuleAward ruleAward = new RuleAward();
	private AwardService awardService;

	public RuleAward getRuleAward() {
		return this.ruleAward;
	}

	public void setRuleAward(RuleAward ruleAward) {
		this.ruleAward = ruleAward;
	}

	public AwardService getAwardService() {
		return this.awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public RuleAward getModel() {
		return this.ruleAward;
	}

	public String allRule() throws Exception {
		List list = this.awardService.getRuleAwardList();
		setMsgUrl("/admin/lottery/allRule.html");
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String addRule() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			this.ruleAward.setAddtime(DateUtils.getNowTimeStr());

			this.ruleAward.setAddip(getRequestIp());
			this.awardService.addRuleAward(this.ruleAward);
			message("增加成功！", "/admin/lottery/allRule.html");
			return ADMINMSG;
		}

		return SUCCESS;
	}

	public String modifyRule() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		long id = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("id")));
		if (!StringUtils.isBlank(actionType)) {
			this.awardService.updateRuleAward(this.ruleAward);
			message("修改成功！", "/admin/lottery/allRule.html");
			return ADMINMSG;
		}
		RuleAward ruleAward = this.awardService.getRuleAwardByRuleId(id);
		this.request.setAttribute("p", ruleAward);

		return SUCCESS;
	}

	public String allObject() throws Exception {
		long id = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("id")));
		List list = this.awardService.getObjectAwardListByRuleId(id);
		setMsgUrl("/admin/lottery/allObject.html");
		this.request.setAttribute("list", list);
		this.request.setAttribute("id", Long.valueOf(id));
		return SUCCESS;
	}

	public String addObject() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			ObjAward data = setObjAward(actionType);
			this.awardService.addObjAward(data);
			long ruleId = NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("rule_id")));
			message("增加成功！", "/admin/lottery/allObject.html?id=" + ruleId);
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public String modifyObject() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		long id = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("id")));
		if (!StringUtils.isBlank(actionType)) {
			ObjAward data = setObjAward(actionType);
			this.awardService.updateObjAward(data);
			long ruleId = NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("rule_id")));
			message("修改成功！", "/admin/lottery/allObject.html?id=" + ruleId);
			return ADMINMSG;
		}
		ObjAward objectAward = this.awardService.getObjectAwardById(id);
		this.request.setAttribute("p", objectAward);

		return SUCCESS;
	}

	private ObjAward setObjAward(String actionType) {
		ObjAward data = new ObjAward();

		data.setName(StringUtils.isNull(this.request.getParameter("name")));

		data.setRule_id(NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("rule_id"))));

		data.setLevel(NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("level"))));

		BigDecimal rate = new BigDecimal(StringUtils.isNull(this.request
				.getParameter("rate")));
		data.setRate(rate.multiply(new BigDecimal(100000000)).intValue());

		data.setAward_limit(NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("award_limit"))));

		data.setTotal(NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("total"))));

		data.setRatio(NumberUtils.getDouble(StringUtils.isNull(this.request
				.getParameter("ratio"))));

		data.setObj_value(NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("obj_value"))));

		data.setDescription(StringUtils.isNull(this.request
				.getParameter("description")));

		data.setObject_rule(StringUtils.isNull(this.request
				.getParameter("object_rule")));
		if ("add".equals(actionType)) {
			data.setAddtime(DateUtils.getNowTimeStr());

			data.setAddip(getRequestIp());
		} else {
			data.setId(NumberUtils.getLong(StringUtils.isNull(this.request
					.getParameter("id"))));
		}
		return data;
	}

	public String userAwardList() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));

		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));

		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));

		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));

		String status = StringUtils.isNull(this.request.getParameter("status"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setStatus(status);
		PageDataList plist = this.awardService.getUserAwardList(page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/lottery/userAwardList.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "award_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "user_name", "level", "award_name", "addtime",
				"status", "receive_status" };
		String[] titles = { "ID", "用户名", "奖品等级", "奖品名称", "抽奖时间", "状态", "奖励发放状态" };
		List list = this.awardService.getAllUserAwardList(param);
		ExcelHelper.writeExcel(infile, list, UserAward.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}
}