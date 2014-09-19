package com.liuyun.site.web.action.admin;

import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.BonusApr;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.Collection;
import com.liuyun.site.domain.OperationLog;
import com.liuyun.site.domain.Protocol;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.domain.Reservation;
import com.liuyun.site.domain.RunBorrow;
import com.liuyun.site.domain.TenderAwardYesAndNo;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.domain.UserAmountApply;
import com.liuyun.site.domain.UserAmountLog;
import com.liuyun.site.model.BorrowNTender;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.DetailCollection;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;
import com.liuyun.site.model.borrow.BorrowHelper;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.model.borrow.FastExpireModel;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.service.MemberBorrowService;
import com.liuyun.site.service.QuickenLoansService;
import com.liuyun.site.service.UserAmountService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.tool.javamail.Mail;
import com.liuyun.site.tool.jxl.ExcelHelper;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ManageBorrowAction   
 * 类描述： 后台管理--货款管理模块  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 3, 2013 11:08:17 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 3, 2013 11:08:17 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ManageBorrowAction extends BaseAction {
	private Logger logger = Logger.getLogger(ManageBorrowAction.class);
	private BorrowService borrowService;
	private UserAmountService userAmountService;
	private MemberBorrowService memberBorrowService;
	private UserService userService;
	private QuickenLoansService quickenLoansService;
	private AccountService accountService;
	private long id;
	private int status;
	String username;
	private String verify_user;
	private int type;
	private String verify_remark;

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public QuickenLoansService getQuickenLoansService() {
		return this.quickenLoansService;
	}

	public void setQuickenLoansService(QuickenLoansService quickenLoansService) {
		this.quickenLoansService = quickenLoansService;
	}

	public String getVerify_user() {
		return this.verify_user;
	}

	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public UserAmountService getUserAmountService() {
		return this.userAmountService;
	}

	public void setUserAmountService(UserAmountService userAmountService) {
		this.userAmountService = userAmountService;
	}

	public MemberBorrowService getMemberBorrowService() {
		return this.memberBorrowService;
	}

	public void setMemberBorrowService(MemberBorrowService memberBorrowService) {
		this.memberBorrowService = memberBorrowService;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVerify_remark() {
		return this.verify_remark;
	}

	public void setVerify_remark(String verify_remark) {
		this.verify_remark = verify_remark;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showAllBorrow 
	 * 功能: 所有借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showAllBorrow() throws Exception {
		String servletPath = this.request.getServletPath();
		this.logger.debug("ServletPath:" + servletPath);
		String status = StringUtils.isNull(this.request.getParameter("status"));
		showBorrowList(status);
		this.request.setAttribute("borrowType", "all");

		setMsgUrl("/admin/borrow/showAllBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showElapseBorrow 
	 * 功能: 流标的借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showElapseBorrow() throws Exception {
		BorrowModel model = new BorrowModel();
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, model);
		SearchParam param = new SearchParam();
		param.setUsername(StringUtils.isNull(this.request.getParameter("username")));
		model.setPageStart(NumberUtils.getInt(this.request.getParameter("page")));
		model.setStatus(19);
		model.setParam(param);
		try {
			showBorrowList(wrapModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setMsgUrl("/admin/borrow/showElapseBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showCancelBorrow 
	 * 功能: 撤回的借款
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showCancelBorrow() throws Exception {
		BorrowModel model = new BorrowModel();
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, model);
		SearchParam param = new SearchParam();
		param.setUsername(StringUtils.isNull(this.request.getParameter("username")));
		model.setPageStart(NumberUtils.getInt(this.request.getParameter("page")));
		model.setStatus(5);
		model.setParam(param);
		try {
			showBorrowList(wrapModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setMsgUrl("/admin/borrow/showCancelBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viewBorrow 
	 * 功能: 查看借款
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String viewBorrow() throws Exception {
		if (this.id < 0L) {
			message("非法操作！");
			return ADMINMSG;
		}
		UserBorrowModel b = this.borrowService.getUserBorrow(this.id);
		if (b == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		this.request.setAttribute("b", b);
		return SUCCESS;
	}

	public String recommend() throws Exception {
		int borrow_id = NumberUtils.getInt(this.request.getParameter("id"));
		BorrowModel b = this.borrowService.getBorrow(borrow_id);
		if ((b == null) || (b.getStatus() == 2)) {
			message("非法操作！", "");
			return ADMINMSG;
		}
		b.setIs_recommend(1);
		this.borrowService.updateRecommendBorrow(b);
		b = this.borrowService.getBorrow(borrow_id);
		message("操作成功！", "");
		showAllBorrow();
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: verifyBorrow 
	 * 功能: 审核借款
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String verifyBorrow() throws Exception {
		String actionType = StringUtils.isNull(this.request.getParameter("actionType"));
		BorrowModel borrow = this.borrowService.getBorrow(this.id);

		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		OperationLog operationLog = new OperationLog(borrow.getUser_id(), auth_user.getUser_id(), "", getTimeStr(), getRequestIp(), "");

		if (actionType.equals("verify")) {
			BorrowModel b = this.borrowService.getBorrow(this.id);
			String retMsg = checkBorrow(b);
			if (!StringUtils.isBlank(retMsg)) {
				return ADMINMSG;
			}
			b.setVerify_time(DateUtils.getNowTimeStr());
			BorrowModel wrapModel = BorrowHelper.getHelper(b);

			wrapModel.verify(wrapModel.getModel().getStatus(), this.status);

			AccountLog log = null;
			try {
				log = new AccountLog(b.getUser_id(), Constant.UNFREEZE,
						getAuthUser().getUser_id(), getTimeStr(),
						getRequestIp());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.borrowService.verifyBorrow(wrapModel.getModel(), log,
					operationLog);
			message("审核成功！");
			return ADMINMSG;
		}
		if (this.id < 0L) {
			message("非法操作！");

			return ADMINMSG;
		}
		
		UserBorrowModel b = this.borrowService.getUserBorrow(this.id);
		if ((b == null) || ((b.getStatus() != 0) && (b.getStatus() != 1))) {
			message("非法操作！");

			return ADMINMSG;
		}
		this.request.setAttribute("b", b);

		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: cancelBorrow 
	 * 功能: 撤回借款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String cancelBorrow() throws Exception {
		BorrowModel b = this.borrowService.getBorrow(this.id);
		if ((b == null) || (b.getStatus() > 4) || (b.getStatus() == 3)) {
			message("非法操作！");
			return ADMINMSG;
		}
		BorrowModel wrapModel = BorrowHelper.getHelper(b);

		wrapModel.verify(wrapModel.getModel().getStatus(), 5);

		AccountLog log = new AccountLog(wrapModel.getModel().getUser_id(),
				Constant.UNFREEZE, getAuthUser().getUser_id(), getTimeStr(),
				getRequestIp());
		this.borrowService.deleteBorrow(wrapModel.getModel(), log);
		message("撤回借款标成功！");
		return ADMINMSG;
	}

	private String checkBorrow(Borrow b) {
		if (StringUtils.isBlank(this.verify_remark)) {
			message("审核信息不能为空！");
			return ADMINMSG;
		}
		return "";
	}

	/**
	 * **************************************************************************************
	 * 方法名: showTrialBorrow 
	 * 功能: 发标待审借款
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showTrialBorrow() throws Exception {
		String status = StringUtils.isNull(this.request.getParameter("status"));
		if (StringUtils.isBlank(status)) {
			status = "0";
		}
		showBorrowList(status);
		this.request.setAttribute("borrowType", "trial");

		setMsgUrl("/admin/borrow/showTrialBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showNotTrialBorrow 
	 * 功能: 发标审核未通过
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showNotTrialBorrow() throws Exception {
		showBorrowList("2");
		this.request.setAttribute("borrowType", "trial");

		setMsgUrl("/admin/borrow/showTrialBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showReviewBorrow 
	 * 功能: 己满标待审
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showReviewBorrow() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String status = "1";
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setStatus(status);
		PageDataList plist = this.borrowService.getFullBorrowList(page, param);
		this.request.setAttribute("page", plist.getPage());
		this.request.setAttribute("list", plist.getList());
		this.request.setAttribute("param", param.toMap());
		this.request.setAttribute("borrowType", "review");

		setMsgUrl("/admin/borrow/showReviewBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viewFullBorrow 
	 * 功能: 查看满标复审
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String viewFullBorrow() throws Exception {
		if (this.id < 0L) {
			message("非法操作！");
			return ADMINMSG;
		}
		UserBorrowModel b = this.borrowService.getUserBorrow(this.id);
		if (b == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		BorrowModel wrapModel = BorrowHelper.getHelper(b);
		Repayment[] repayList = wrapModel.getRepayment();

		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList plist = this.borrowService.getTenderList(this.id, page, param);

		this.request.setAttribute("b", b);
		this.request.setAttribute("tenderlist", plist.getList());
		this.request.setAttribute("repayList", repayList);
		this.request.setAttribute("page", plist.getPage());
		this.request.setAttribute("param", param.toMap());
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: verifyFullBorrow 
	 * 功能: 满标审核
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String verifyFullBorrow() throws Exception {
		BorrowModel model = this.borrowService.getBorrow(this.id);
		User user = getSessionUser();
		String retMsg = checkBorrow(model);
		if (!StringUtils.isBlank(retMsg)) {
			return ADMINMSG;
		}
		model.setVerify_time(getTimeStr());
		model.setVerify_remark(StringUtils.isNull(this.request.getParameter("verify_time")));
		BorrowModel wrapModel = BorrowHelper.getHelper(model);

		wrapModel.verify(wrapModel.getModel().getStatus(), this.status);
		AccountLog log = new AccountLog(model.getUser_id(), Constant.UNFREEZE, model.getUser_id(), getTimeStr(), getRequestIp());
		log.setRemark(getLogRemark(wrapModel.getModel()));

		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		DetailUser detailUser = this.userService.getDetailUser(auth_user.getUser_id());
		DetailUser newdetailUser = this.userService.getDetailUser(model.getUser_id());
		OperationLog operationLog = new OperationLog(newdetailUser.getUser_id(), auth_user.getUser_id(), "", getTimeStr(), getRequestIp(), "");
		if (Global.getWebid().equals("jsy")) {
			Protocol protocol = new Protocol(0L, Constant.TENDER_PROTOCOL, getTimeStr(), getRequestIp(), "");
			this.borrowService.verifyFullBorrow(wrapModel, operationLog,
					protocol);
		} else {
			this.borrowService.verifyFullBorrow(wrapModel, operationLog);
		}

		message("审核成功！");
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showBorrowing 
	 * 功能: 正在招标款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showBorrowing() throws Exception {
		showBorrowList("1");

		setMsgUrl("/admin/borrow/showBorrowing.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showIsFullBorrow 
	 * 功能: 满标审核通过 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showIsFullBorrow() throws Exception {
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		BorrowModel model = new BorrowModel();
		SearchParam param = new SearchParam();

		param.setType(String.valueOf(this.type));
		param.setUsername(this.username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);

		param.setStatusArray(new int[] { 3, 6 });
		model.setParam(param);
		model.setPageStart(page);
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, model);
		try {
			showBorrowList(wrapModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setMsgUrl("/admin/borrow/showIsFullBorrow.html");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showNotFullBorrow 
	 * 功能: 满标审核未通过
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String showNotFullBorrow() throws Exception {
		BorrowModel model = new BorrowModel();
		SearchParam param = new SearchParam();
		param.setStatusArray(new int[] { 4, 49 });
		model.setParam(param);
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, model);
		try {
			showBorrowList(wrapModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setMsgUrl("/admin/borrow/showNotFullBorrow.html");
		return SUCCESS;
	}

	private void showBorrowList(String status) {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String verify_user = StringUtils.isNull(this.request.getParameter("verify_user"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		SearchParam param = new SearchParam();
		param.setType(String.valueOf(this.type));
		param.setUsername(username);
		param.setVerify_user(verify_user);
		param.setStatus(status);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		PageDataList plist = this.borrowService.getAllBorrowList(page, param);
		setPageAttribute(plist, param);
		double total = this.borrowService.getSumBorrowAccount(param);
		this.request.setAttribute("borrow_total", Double.valueOf(total));
	}

	private void showBorrowList(BorrowModel model) {
		PageDataList plist = this.borrowService.getList(model);
		setPageAttribute(plist, model.getModel().getParam());
		double total = this.borrowService.getSumBorrowAccount(model.getModel().getParam());
		this.request.setAttribute("borrow_total", Double.valueOf(total));
	}

	/**
	 * **************************************************************************************
	 * 方法名: amountApply 
	 * 功能: 借款额度审批 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String amountApply() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList plist = this.userAmountService.getUserAmountApply(page, param);
		setPageAttribute(plist, param);

		setMsgUrl("/admin/borrow/amountApply.html");
		return SUCCESS;
	}

	public String viewAmountApply() throws Exception {
		UserAmountApply apply = this.userAmountService
				.getUserAmountApplyById(this.id);
		if (apply == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		this.request.setAttribute("apply", apply);
		return SUCCESS;
	}

	private String checkAmountApply(UserAmountApply apply) {
		if ((this.status != 0) && (this.status != 1)) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (apply == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (apply.getStatus() == 1) {
			message("已经审核通过！");
			return ADMINMSG;
		}
		return "";
	}

	public String showAllTenderList() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		PageDataList plist = this.borrowService.getAllBorrowTenderList(page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/borrow/showAllTenderList.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "account_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "borrowname", "account", "money", "interest", "addip" };
		String[] titles = { "发标者", "标名", "金额", "操作金额", "利息", "IP" };
		List<BorrowNTender> list = this.borrowService.getAllBorrowTenderList(param);
		ExcelHelper.writeExcel(infile, list, BorrowNTender.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String verifyAmountApply() throws Exception {
		UserAmountApply apply = this.userAmountService.getUserAmountApplyById(this.id);
		String msg = checkAmountApply(apply);
		if (!StringUtils.isBlank(msg)) {
			return ADMINMSG;
		}
		UserAmount ua = this.userAmountService.getUserAmount(apply.getUser_id());
		if (ua == null)
			apply.setAccount_old(0.0D);
		else {
			apply.setAccount_old(ua.getCredit());
		}
		if (this.status == 0) {
			apply.setStatus(-1);
		} else if (this.status == 1) {
			double account = NumberUtils.getDouble(this.request
					.getParameter("account"));
			if (account <= 0.0D) {
				message("申请额度不能小于0！");
				return ADMINMSG;
			}
			apply.setStatus(1);
			apply.setAccount(account);
			apply.setAccount_new(apply.getAccount_old() + account);
		} else {
			message("非法操作！");
			return ADMINMSG;
		}

		apply.setVerify_remark(this.verify_remark);
		apply.setVerify_time(getTimeStr());
		UserAmountLog log = new UserAmountLog();
		log.setAddtime(getTimeStr());
		log.setAddip(getRequestIp());
		try {
			this.userAmountService.verifyAmountApply(apply, log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		message("审核成功！");
		return ADMINMSG;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: repaid 
	 * 功能: 已还款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String repaid() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String keywords = StringUtils.isNull(this.request
				.getParameter("keywords"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String succtime1 = StringUtils.isNull(this.request
				.getParameter("succtime1"));
		String succtime2 = StringUtils.isNull(this.request
				.getParameter("succtime2"));
		String repayment_time1 = StringUtils.isNull(this.request
				.getParameter("repayment_time1"));
		String repayment_time2 = StringUtils.isNull(this.request
				.getParameter("repayment_time2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setRepayment_time1(repayment_time1);
		param.setRepayment_time2(repayment_time2);
		param.setSucctime1(succtime1);
		param.setSucctime2(succtime2);
		param.setKeywords(keywords);
		param.setUsername(username);
		param.setStatus("8");
		param.setType(String.valueOf(type));
		PageDataList plist = this.memberBorrowService.getRepaymentList(param, page);
		param.setSucctime1(succtime1);
		param.setSucctime2(succtime2);
		setPageAttribute(plist, param);
		this.request.setAttribute("repay_type", Constant.REPAID);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "repaid_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "borrow_name", "order",
				"repayment_time", "repayment_account", "repayment_yestime" };
		String[] titles = { "借款者", "借款标名称", "期数", "到期时间", "还款金额", "还款时间" };
		List<Repayment> list = this.memberBorrowService.getRepaymentList(param);
		ExcelHelper.writeExcel(infile, list, Repayment.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: repaying 
	 * 功能: 待还款 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String repaying() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String succtime1 = StringUtils.isNull(this.request.getParameter("succtime1"));
		String succtime2 = StringUtils.isNull(this.request.getParameter("succtime2"));
		
		
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setKeywords(keywords);
		param.setUsername(username);
		param.setSucctime1(succtime1);
		param.setSucctime2(succtime2);
		param.setStatusArray(new int[] { 6, 7 });

		String repayment_time1 = StringUtils.isNull(this.request.getParameter("repayment_time1"));
		String repayment_time2 = StringUtils.isNull(this.request.getParameter("repayment_time2"));
		param.setRepayment_time1(repayment_time1);
		param.setRepayment_time2(repayment_time2);
		
		if (type.isEmpty()) {
			PageDataList plist = this.memberBorrowService.getRepaymentList(param, page);
			setPageAttribute(plist, param);
			this.request.setAttribute("repay_type", "repaying");
	
			double sum = this.memberBorrowService.getRepaymentSum(param);
			this.request.setAttribute("sum", Double.valueOf(sum));
		
			return SUCCESS;
		}
		
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "repaying_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "borrow_name", "order", "repayment_time", "repayment_account", "repayment_yestime" };
		String[] titles = { "借款者", "借款标名称", "期数", "到期时间", "还款金额", "还款时间" };
		List<Repayment> list = this.memberBorrowService.getRepaymentList(param);
		ExcelHelper.writeExcel(infile, list, Repayment.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String def() throws Exception {
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setKeywords(keywords);
		param.setUsername(username);
		PageDataList plist = this.memberBorrowService.getRepaymentList(param, page);
		setPageAttribute(plist, param);
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: late 
	 * 功能: 逾期 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception 入参   
	 * 返回类型: String     
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String late() throws Exception {
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setKeywords(keywords);
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setType(String.valueOf(this.type));
		PageDataList plist = this.memberBorrowService.getLateList(param, page);
		setPageAttribute(plist, param);
		return SUCCESS;
	}

	public String overdue() throws Exception {
		String actionType = StringUtils.isNull(this.request.getParameter("actionType"));
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setKeywords(keywords);
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setType(String.valueOf(this.type));
		PageDataList plist = this.memberBorrowService.getOverdueList(param, page);
		setPageAttribute(plist, param);

		if (actionType.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "overdue_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "borrow_name", "account", "order", "repayment_time", "repayment_account", "interest" };
		String[] titles = { "借款者", "借款标名称", "借款金额", "期数", "到期时间", "还款金额", "还款利息" };
		List<Repayment> list = this.memberBorrowService.getOverdueList(param);
		ExcelHelper.writeExcel(infile, list, Repayment.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String unFinishFlow() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String keywords = StringUtils.isNull(this.request.getParameter("keywords"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setKeywords(keywords);
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setType(String.valueOf(type));
		PageDataList plist = this.memberBorrowService.getUnFinishFlowList(param, page);
		setPageAttribute(plist, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "unFinishFlow_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "borrow_name", "repay_time", "repay_account", "interest" };
		String[] titles = { "投资人", "借款标的名称", "应还时间", "应还金额", "应还利息" };
		List<Collection> list = this.memberBorrowService.getUnFinishFlowList(param);
		ExcelHelper.writeExcel(infile, list, BorrowTender.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String bonus() throws Exception {
		List<UserBorrowModel> list = this.borrowService.getList(Constant.TYPE_PROJECT, Constant.STATUS_REPAYING);
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String bonusmodify() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		if (type.equals("json")) {
			long id = NumberUtils.getLong(this.request.getParameter("id"));
			double apr = NumberUtils.getDouble(this.request.getParameter("apr"));
			String errorMsg = "";
			try {
				this.borrowService.modifyBonusAprById(id, apr / 100.0D);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuffer sb = new StringBuffer();
			if (errorMsg.isEmpty()) {
				sb.append("{error:0,msg:100}");
			} else {
				this.logger.error(errorMsg);
				sb.append("{error:1,msg:" + errorMsg + "}");
			}
			out.flush();
			out.close();
			return null;
		}
		long borrow_id = NumberUtils.getLong(this.request.getParameter("borrow_id"));
		Borrow b = this.borrowService.getBorrow(borrow_id);
		List<BonusApr> list = this.borrowService.getBonusAprList(borrow_id);
		this.request.setAttribute("list", list);
		this.request.setAttribute("borrow", b);
		return SUCCESS;
	}

	public String allLimit() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList plist = this.userAmountService.getUserAmount(page, param);
		setPageAttribute(plist, param);

		return SUCCESS;
	}

	public String tenderList() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		long borrow_id = paramLong("borrow_id");
		Borrow b = this.borrowService.getBorrow(borrow_id);
		if (b == null) {
			message("非法操作！", "");
			return ADMINMSG;
		}
		if (type.isEmpty()) {
			int page = paramInt("page");
			SearchParam param = new SearchParam();
			PageDataList tenderList = this.borrowService.getTenderList(
					borrow_id, page, param);
			setPageAttribute(tenderList, param);
			this.request.setAttribute("borrow", b);
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "tender_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "money", "account", "repay_account", "addtime", "repay_time" };
		String[] titles = { "投标人", "投标金额", "有效金额", "回收本息", "投标时间", "回收时间" };
		List<BorrowTender> list = this.borrowService.getAllTenderList(borrow_id);

		ExcelHelper.writeExcel(infile, list, BorrowTender.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String stopflow() throws Exception {
		long borrow_id = paramLong("borrow_id");
		BorrowModel b = this.borrowService.getBorrow(borrow_id);
		if ((b == null) || (b.getStatus() != 1)) {
			message("非法炒作！", "");
			return ADMINMSG;
		}
		b.setStatus(8);
		this.borrowService.updateBorrow(b);
		message("操作成功！", "");
		return ADMINMSG;
	}

	public String collect() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		long borrow_id = paramLong("borrow_id");
		Borrow b = this.borrowService.getBorrow(borrow_id);
		if (b == null) {
			message("非法操作！", "");
			return ADMINMSG;
		}
		if (type.isEmpty()) {
			int page = paramInt("page");
			SearchParam param = new SearchParam();
			PageDataList tenderList = this.borrowService.getCollectionListByBorrow(borrow_id, page, param);
			setPageAttribute(tenderList, param);
			this.request.setAttribute("borrow", b);
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "collect" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "capital", "tendertime", "repay_account", "repay_time", "repay_yestime" };
		String[] titles = { "投标人", "投标金额", "投标时间", "本息", "待收时间", "实收时间" };
		List<DetailCollection> list = this.borrowService.getCollectionListByBorrow(borrow_id);
		try {
			ExcelHelper.writeExcel(infile, list, DetailCollection.class, Arrays.asList(names), Arrays.asList(titles));
		} catch (Exception e) {
			e.printStackTrace();
		}
		export(infile, downloadFile);
		return null;
	}

	public String showAllRunBorrow() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		PageDataList plist = this.borrowService.jk(page, param);
		List<RunBorrow> list = (List<RunBorrow>) plist.getList();
		setPageAttribute(plist, param);
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String showExpriredBorrow() {
		BorrowModel model = new BorrowModel();
		BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, model);
		SearchParam param = new SearchParam();
		param.setUsername(StringUtils.isNull(this.request.getParameter("username")));
		model.setPageStart(NumberUtils.getInt(this.request.getParameter("page")));
		model.setStatus(Constant.STATUS_EXPIRED);
		model.setParam(param);
		try {
			showBorrowList(wrapModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setMsgUrl("/admin/borrow/showAllBorrow.html");
		return SUCCESS;
	}

	public String showTenderAward() {
		String actiontype = this.request.getParameter("actiontype");
		if (StringUtils.isBlank(actiontype)) {
			return SUCCESS;
		}

		String tender_award_yes_no = this.request.getParameter("tender_award_yes_no");
		TenderAwardYesAndNo tenderAwardYesAndNo = this.borrowService.TenderAward(1);
		if (tenderAwardYesAndNo.getTender_award_yes_no() == 0) {
			tenderAwardYesAndNo.setTender_award_yes_no(NumberUtils.getInt(tender_award_yes_no));
			tenderAwardYesAndNo.setId(1);
			this.borrowService.updateTenderAward(tenderAwardYesAndNo);
		}
		TenderAwardYesAndNo tenderAwardYesAndNo2 = this.borrowService.TenderAward(1);
		if (tenderAwardYesAndNo2.getTender_award_yes_no() == 1) {
			String nowtimestring = DateUtils.getNowTimeStr();
			long nowtime = NumberUtils.getLong(nowtimestring);
			long award_start_time = NumberUtils.getLong(Global.getValue("award_start_time"));
			long award_end_time = NumberUtils.getLong(Global.getValue("award_end_time"));
			if ((nowtime < award_end_time) && (nowtime > award_start_time)) {
				String starttime = Global.getValue("tender_start_time");
				String endtime = Global.getValue("tender_end_time");
				List<User> list = this.userService.getAllUser(2);
				for (User user : list) {
					double tenderTotal = this.borrowService.hasTenderListByUserid(user.getUser_id(), starttime, endtime);
					double tender_award = tender_award(tenderTotal);
					if (tenderTotal - 0.0D > 0.0D) {
						this.accountService.updateAccount(tender_award, tender_award, 0.0D, tender_award, user.getUser_id(), 1);
						Account account = this.accountService.getAccount(user.getUser_id());
						AccountLog log = new AccountLog(user.getUser_id(),
								Constant.TENDER_AWARD, account.getTotal(),
								tender_award, account.getUse_money(), account
										.getNo_use_money(), account
										.getCollection(), 1L, "用户名为"
										+ user.getUsername() + "的用户累计投资奖励金额"
										+ tender_award + "元，已成功转入个人账户",
								getTimeStr(), getRequestIp());
						this.accountService.addAccountLog(log);
					}
				}

				tenderAwardYesAndNo.setId(1);
				tenderAwardYesAndNo.setTender_award_yes_no(2);
				this.borrowService.updateTenderAward(tenderAwardYesAndNo);
			}
		}

		return SUCCESS;
	}

	private double tender_award(double tenderTotal) {
		if (tenderTotal - 1000000.0D >= 0.0D)
			return tenderTotal * 0.005D;
		if ((tenderTotal - 500000.0D >= 0.0D) && (1000000.0D - tenderTotal > 0.0D))
			return tenderTotal * 0.004D;
		if ((tenderTotal - 200000.0D >= 0.0D) && (500000.0D - tenderTotal > 0.0D))
			return tenderTotal * 0.003D;
		if ((tenderTotal - 50000.0D >= 0.0D) && (200000.0D - tenderTotal > 0.0D)) {
			return tenderTotal * 0.002D;
		}
		return tenderTotal * 0.001D;
	}

	public String reservation_list() throws Exception {
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		PageDataList plist = this.borrowService.reservation_list(param, page);
		setPageAttribute(plist, param);
		return SUCCESS;
	}

	public String reservation_add() throws Exception {
		String actionType = StringUtils.isNull(this.request.getParameter("actionType"));
		if (StringUtils.isBlank(actionType)) {
			return SUCCESS;
		}
		Reservation reservation = new Reservation();
		String reservation_user_string = StringUtils.isNull(this.request.getParameter("reservation_user"));
		User user = this.userService.getUserByName(reservation_user_string);
		String borrow_apr = StringUtils.isNull(this.request.getParameter("borrow_apr"));
		String tender_account_string = StringUtils.isNull(this.request.getParameter("tender_account"));
		if (user != null) {
			reservation.setReservation_user(user.getUser_id());
			if (StringUtils.isBlank(tender_account_string)) {
				double tender_account = NumberUtils.getDouble(tender_account_string);
				if (tender_account - 0.0D == 0.0D) {
					message("请添加有效预约标投标金额，！", "/admin/borrow/reservation_add.html");
					return ADMINMSG;
				}
				reservation.setTender_account(tender_account);
			}
			reservation.setBorrow_apr(borrow_apr);
			reservation.setAddtime(getTimeStr());
			reservation.setAddip(getRequestIp());
			this.borrowService.reservation_add(reservation);
			message("添加预约用户成功！", "/admin/borrow/reservation_add.html");
			return ADMINMSG;
		}
		message("预约用户不存在！", "/admin/borrow/reservation_add.html");
		return ADMINMSG;
	}

	public String overdueReceivables() {
		long borrow_id = Long.parseLong(this.request.getParameter("borrowid"));
		List<Repayment> list = this.memberBorrowService.getLateRepaymentByBorrowid(borrow_id);
		String subject = Global.getValue("webname") + "-还款通知";
		User user = this.userService.getUserByName(((Repayment) list.get(0)).getUsername());
		String to = user.getEmail();
		Mail mail = Mail.getInstance();

		mail.readOverdueReceivablesMsg();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("username", user.getUsername());
		m.put("email", user.getEmail());
		m.put("borrowname", ((Repayment) list.get(0)).getBorrow_name());
		m.put("account", NumberUtils
				.format2Str(Double.valueOf(
						((Repayment) list.get(0)).getRepayment_account())
						.doubleValue()));
		m.put("order", String.valueOf(((Repayment) list.get(0)).getOrder()));
		m.put("latedays", ((Repayment) list.get(0)).getLate_days());
		m.put("lateInterest", NumberUtils.format2Str(Double.valueOf(
				((Repayment) list.get(0)).getLate_interest()).doubleValue()));
		String body = mail.bodyReplace(m);
		try {
			mail.sendMail(to, subject, body);
			message("邮件提醒成功，已发送邮件至用户提醒其还款！", "/admin/borrow/late.html");
			return ADMINMSG;
		} catch (Exception e) {
			message("邮件提醒失败，发送邮件失败！", "/admin/borrow/late.html");
		}
		return ADMINMSG;
	}

	public String goLateLog() {
		String borrowid = paramString("borrowid");
		this.request.setAttribute("borrowid", borrowid);
		return SUCCESS;
	}

	public String addLateLog() {
		try {
			long borrow_id = paramLong("borrowid");
			String phone_type = paramString("phone_type");
			String phone_num = paramString("phone_num");
			String phone_status = paramString("phone_status");
			String relation_type = paramString("relation_type");
			String relation_name = paramString("relation_name");
			String repay_time = paramString("repay_time");
			String memo = paramString("content");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("borrow_id", Long.valueOf(borrow_id));
			params.put("phone_type", phone_type);
			params.put("phone_num", phone_num);
			params.put("phone_status", phone_status);
			params.put("relation_type", relation_type);
			params.put("relation_name", relation_name);
			params.put("repay_time", repay_time);
			params.put("memo", memo);
			this.borrowService.addLateLog(params);
			message("添加成功", "/admin/borrow/late.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ADMINMSG;
	}

	public String getLateBorrowDetails() {
		try {
			String borrowid = paramString("borrowid");
			List<Map<String, Object>> list = this.borrowService.getLateLog(borrowid);
			this.request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String showAllQuickenLoans() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		PageDataList pageDataList = this.quickenLoansService.getList(page, searchParam);
		this.request.setAttribute("list", pageDataList);
		setPageAttribute(pageDataList, searchParam);
		setMsgUrl("/admin/borrow/showAllQuickenLoans.html");
		return SUCCESS;
	}

	public String delQuickenLoans() throws Exception {
		int loansId = Integer.valueOf(this.request.getParameter("loansId")).intValue();
		this.quickenLoansService.delQuickenLoans(loansId);
		message("删除信息成功！", "/admin/borrow/showAllQuickenLoans.html");
		return ADMINMSG;
	}

	public String showFastExpire() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		PageDataList plist = this.borrowService.getFastExpireList(param, page);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/borrow/showFastExpire.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "fastExpire_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "borrow_id", "borrow_user", "borrow_name", "repayment_time", "repayment_account", "late_days", "forfeit" };
		String[] titles = { "ID", "借款人", "借款标题", "应还时间", "应还金额", "逾期天数", "罚金" };
		List<FastExpireModel> fastExpireList = this.borrowService.getFastExpireList(param);
		ExcelHelper.writeExcel(infile, fastExpireList, FastExpireModel.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}
}