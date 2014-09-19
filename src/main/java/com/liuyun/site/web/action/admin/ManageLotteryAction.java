package com.liuyun.site.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.domain.LotteryRule;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.LotteryService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.util.List;

public class ManageLotteryAction extends BaseAction implements
		ModelDriven<LotteryRule> {
	private LotteryRule lotteryRule = new LotteryRule();
	private LotteryService lotteryService;

	public LotteryService getLotteryService() {
		return this.lotteryService;
	}

	public void setLotteryService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	public String lotteryRule() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("action_type"));
		List list = this.lotteryService.lotteryList();
		if (StringUtils.isBlank(actionType)) {
			if (list.size() > 0) {
				this.lotteryRule = ((LotteryRule) list.get(0));
				this.request.setAttribute("lotteryRule", this.lotteryRule);
			}
			return SUCCESS;
		}
		if (list.size() <= 0) {
			this.lotteryService.add_lottery(this.lotteryRule);
			list = this.lotteryService.lotteryList();
			this.lotteryRule = ((LotteryRule) list.get(0));
			this.lotteryRule = this.lotteryService
					.getLotteryById(this.lotteryRule.getId());
		} else {
			this.lotteryService.modify_lottery(this.lotteryRule);
		}
		this.request.setAttribute("lotteryRule", this.lotteryRule);
		return SUCCESS;
	}

	public String winningInformation() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String gmt_create1 = StringUtils.isNull(this.request
				.getParameter("gmt_create1"));
		String gmt_create2 = StringUtils.isNull(this.request
				.getParameter("gmt_create2"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setGmt_create1(gmt_create1);
		param.setGmt_create2(gmt_create2);
		param.setStatus(status);
		PageDataList plist = this.lotteryService.getWinningInfortionList(page,
				param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/lottery/winningInformation.html");
		return SUCCESS;
	}

	public LotteryRule getLotteryRule() {
		return this.lotteryRule;
	}

	public void setLotteryRule(LotteryRule lotteryRule) {
		this.lotteryRule = lotteryRule;
	}

	public LotteryRule getModel() {
		return this.lotteryRule;
	}
}