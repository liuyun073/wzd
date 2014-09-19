package com.liuyun.site.web.action.member;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.context.Constant;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmountApply;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.DetailTender;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.service.MemberBorrowService;
import com.liuyun.site.service.UserAmountService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import com.liuyun.site.web.action.BorrowAction;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;


public class MemberBorrowAction extends BaseAction implements
		ModelDriven<UserAmountApply> {
	private static Logger logger = Logger.getLogger(BorrowAction.class);
	private MemberBorrowService memberBorrowService;
	private UserService userService;
	private AccountService accountService;
	private UserAmountService userAmountService;
	private BorrowService borrowService;
	private UserAmountApply amount = new UserAmountApply();

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	/**
	 * **************************************************************************************
	 * 方法名: borrow 
	 * 功能: 查看项目信息列表（正在招标，尚未发布，正在还款，已还款的） 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String borrow() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		SearchParam param = new SearchParam();
		String type = StringUtils.isNull(this.request.getParameter("type"));
		if (StringUtils.isBlank(type)) {
			type = "publish";
		}

		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));

		param.setKeywords(keywords);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.addMap("type", type);

		PageDataList list = this.memberBorrowService.getList(type, user.getUser_id(), page, param);

		this.request.setAttribute("borrowList", list.getList());
		this.request.setAttribute("type", type);
		this.request.setAttribute("p", list.getPage());
		this.request.setAttribute("param", param.toMap());

		return SUCCESS;
	}

	public String limitapp() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		String action_type = this.request.getParameter("action_type");
		int page = NumberUtils.getInt(StringUtils.isNull(this.request.getAttribute("page")));

		List<UserAmountApply> amountlist = this.userAmountService.getUserAmountApply(user_id);
		UserCache userCache = this.userService.getUserCacheByUserid(user_id);
		User newUser = this.userService.getUserById(user.getUser_id());
		this.session.put(Constant.SESSION_USER, newUser);

		if (amountlist.size() > 0) {
			this.request.setAttribute("amountlist", amountlist);
			this.request.setAttribute("cache", userCache);
		} else {
			if (StringUtils.isNull(action_type).equals("add")) {
				this.amount.setUser_id(user_id);
				this.amount.setAddtime("" + new Date().getTime() / 1000L);
				this.amount.setAddip("");
				this.amount.setStatus(2);
				this.userAmountService.add(this.amount);
			}
			amountlist = this.userAmountService.getUserAmountApply(user_id);
			this.request.setAttribute("amountlist", amountlist);
			this.request.setAttribute("cache", userCache);
		}
		PageDataList hasApplyList = this.userAmountService.getAmountApplyByUserid(user_id, page, new SearchParam());
		this.request.setAttribute("list", hasApplyList.getList());
		this.request.setAttribute("param", hasApplyList.getPage());
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: repaymentdetail 
	 * 功能: 还款明细账
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String repaymentdetail() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		long borrowId = NumberUtils.getLong(this.request.getParameter("borrowId"));
		List<Repayment> repay = null;
		if (borrowId > 0L)
			repay = this.memberBorrowService.getRepaymentList(user_id, borrowId);
		else {
			repay = this.memberBorrowService.getRepaymentList(user_id);
		}

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.request.setAttribute("currentTime", sdf.format(date));
		this.request.setAttribute("today", DateUtils.getTimeStr(date));
		this.request.setAttribute("repay", repay);
		return SUCCESS;
	}

	public String borrowdetail() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		long borrowId = NumberUtils.getLong(this.request.getParameter("borrowId"));
		List<DetailTender> list = this.memberBorrowService.getBorrowTenderListByUserid(user_id);
		this.request.setAttribute(Constant.TENDER, list);
		return SUCCESS;
	}

	public String repayment() {
		String cidstr = this.request.getParameter("");
		int cid = NumberUtils.getInt(cidstr);
		DetailCollection dc = this.memberBorrowService.getCollection(cid);
		if ((cid == 0) || (dc == null)) {
			return NOTFOUND;
		}

		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: repay 
	 * 功能: 还款
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String repay() {
		boolean isOk = true;
		String checkMsg = "";
		long repayid = NumberUtils.getLong(this.request.getParameter("id"));
		long user_id = getSessionUser().getUser_id();
		Repayment repay = this.memberBorrowService.getRepayment(repayid);
		Account act = this.accountService.getAccount(user_id);
		if (repay == null) {
			message("非法操作！", "/member/borrow/borrow.html?type=repayment");
			return MSG;
		}
		if (NumberUtils.getDouble(repay.getRepayment_account()) > act.getUse_money()) {
			message("可用金额不足，未完成还款！",
					"/member/borrow/borrow.html?type=repayment");
			return MSG;
		}
		AccountLog log = new AccountLog(user_id, Constant.FREEZE, user_id,
				getTimeStr(), getRequestIp());
		try {
			this.memberBorrowService.doRepay(repay, act, log);
		} catch (Exception e) {
			isOk = false;
			checkMsg = e.getMessage();
			logger.error(e.getMessage(), e.getCause());
		}
		if (isOk) {
			message("还款成功！", "/member/borrow/borrow.html?type=repayment");
		} else {
			logger.debug("还款失败！");
			message(checkMsg, "");
		}
		return MSG;
	}

	public MemberBorrowService getMemberBorrowService() {
		return this.memberBorrowService;
	}

	public void setMemberBorrowService(MemberBorrowService memberBorrowService) {
		this.memberBorrowService = memberBorrowService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserAmountApply getModel() {
		return this.amount;
	}

	public UserAmountService getUserAmountService() {
		return this.userAmountService;
	}

	public void setUserAmountService(UserAmountService userAmountService) {
		this.userAmountService = userAmountService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}