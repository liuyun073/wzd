package com.rd.web.action.admin;

import com.rd.common.enums.EnumTroubleFund;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.Account;
import com.rd.domain.AccountBank;
import com.rd.domain.AccountCash;
import com.rd.domain.AccountLog;
import com.rd.domain.AccountRecharge;
import com.rd.domain.Advanced;
import com.rd.domain.CreditCard;
import com.rd.domain.HongBao;
import com.rd.domain.Message;
import com.rd.domain.Notice;
import com.rd.domain.NoticeConfig;
import com.rd.domain.OnlineBank;
import com.rd.domain.OperationLog;
import com.rd.domain.PaymentInterface;
import com.rd.domain.Protocol;
import com.rd.domain.User;
import com.rd.domain.UserAmount;
import com.rd.model.DetailTender;
import com.rd.model.DetailUser;
import com.rd.model.HongBaoModel;
import com.rd.model.HuikuanModel;
import com.rd.model.IdentifySearchParam;
import com.rd.model.PageDataList;
import com.rd.model.RankModel;
import com.rd.model.SearchParam;
import com.rd.model.UserAccountSummary;
import com.rd.model.VIPStatisticModel;
import com.rd.model.account.AccountLogModel;
import com.rd.model.account.AccountLogSumModel;
import com.rd.model.account.AccountModel;
import com.rd.model.account.AccountOneDayModel;
import com.rd.model.account.AccountSumModel;
import com.rd.model.account.OperationLogModel;
import com.rd.model.account.TiChengModel;
import com.rd.quartz.notice.NoticeJobQueue;
import com.rd.quartz.notice.Sms;
import com.rd.service.AccountService;
import com.rd.service.BorrowService;
import com.rd.service.CreditCardService;
import com.rd.service.ManageCashService;
import com.rd.service.MessageService;
import com.rd.service.UserAmountService;
import com.rd.service.UserService;
import com.rd.tool.jxl.ExcelHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ManageCashAction   
 * 类描述： 后台管理--资金管理模块  
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 12:18:15 AM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 12:18:15 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ManageCashAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ManageCashAction.class);
	private ManageCashService manageCashService;
	private UserService userService;
	private AccountService accountService;
	private UserAmountService userAmountService;
	private Message message = new Message();
	private MessageService messageService;
	private CreditCardService creditCardService;
	private BorrowService borrowService;
	private File excel;
	private String excelFileName;
	private File litpic;
	private File bank_logo;
	private String filePath;
	private String sep = File.separator;

	CreditCard model = new CreditCard();
	private PageDataList pageList;

	public File getBank_logo() {
		return this.bank_logo;
	}

	public void setBank_logo(File bank_logo) {
		this.bank_logo = bank_logo;
	}

	public PageDataList getPageList() {
		return this.pageList;
	}

	public void setPageList(PageDataList pageList) {
		this.pageList = pageList;
	}

	public CreditCard getModel() {
		return this.model;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageService getMessageService() {
		return this.messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public CreditCardService getCreditCardService() {
		return this.creditCardService;
	}

	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	public ManageCashService getManageCashService() {
		return this.manageCashService;
	}

	public void setManageCashService(ManageCashService manageCashService) {
		this.manageCashService = manageCashService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public UserAmountService getUserAmountService() {
		return this.userAmountService;
	}

	public void setUserAmountService(UserAmountService userAmountService) {
		this.userAmountService = userAmountService;
	}

	public File getExcel() {
		return this.excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return this.excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public File getLitpic() {
		return this.litpic;
	}

	public void setLitpic(File litpic) {
		this.litpic = litpic;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSep() {
		return this.sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showAllAccount 
	 * 功能: 查看所有帐户 
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
	public String showAllAccount() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		double sumTotal = this.manageCashService.getSumTotal();
		double sumUseMoney = this.manageCashService.getSumUseMoney();
		double sumNoUseMoney = this.manageCashService.getSumNoUseMoney();
		double sumCollection = this.manageCashService.getSumCollection();
		this.request.setAttribute("sumTotal", Double.valueOf(sumTotal));
		this.request.setAttribute("sumUseMoney", Double.valueOf(sumUseMoney));
		this.request.setAttribute("sumNoUseMoney", Double
				.valueOf(sumNoUseMoney));
		this.request.setAttribute("sumCollection", Double
				.valueOf(sumCollection));
		PageDataList plist = this.manageCashService.getUserAccount(page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/showAllAccount.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}

		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "account_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "realname", "total", "use_money",
				"no_use_money", "wait_collect", "wait_repay", "net_assets" };

		String[] titles = { "ID", "用户名", "真实姓名", "总余额", "可用余额", "冻结金额", "待收金额",
				"待还金额", "净资产" };
		List list = this.manageCashService.getUserAccount(param);
		ExcelHelper.writeExcel(infile, list, AccountSumModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String showOneDayAccount() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		if (type.isEmpty()) {
			PageDataList plist = this.manageCashService.getUserOneDayAcount(
					page, param);
			setPageAttribute(plist, param);
			setMsgUrl("/admin/cash/showOneDayAccount.html");
			return SUCCESS;
		}

		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "account_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "realname", "total", "use_money",
				"no_use_money", "wait_collect", "wait_repay", "jin_money" };

		String[] titles = { "ID", "用户名", "真实姓名", "总余额", "可用余额", "冻结金额", "待收金额",
				"待还金额", "净资产" };
		List list = this.manageCashService.getUserOneDayAcount(param);
		ExcelHelper.writeExcel(infile, list, AccountOneDayModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String showTiChengAccount() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		if (type.isEmpty()) {
			PageDataList plist = this.manageCashService.getTiChengAcount(page,
					param);
			setPageAttribute(plist, param);
			setMsgUrl("/admin/cash/showTiChengAccount.html");
			return SUCCESS;
		}

		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "account_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "addtimes", "username", "money" };

		String[] titles = { "时间", "用户名", "好友投资总额(月)" };
		List list = this.manageCashService.getTiChengAcount(param);
		ExcelHelper.writeExcel(infile, list, TiChengModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String showFriendTiChengAccount() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		if (type.isEmpty()) {
			PageDataList plist = this.manageCashService.getFriendTiChengAcount(
					page, param);
			setPageAttribute(plist, param);
			setMsgUrl("/admin/cash/showFriendTiChengAccount.html");
			return SUCCESS;
		}

		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "account_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "addtimes", "username", "money" };

		String[] titles = { "时间", "用户名", "好友投资总额(月)" };
		List list = this.manageCashService.getFriendTiChengAcount(param);
		ExcelHelper.writeExcel(infile, list, TiChengModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: showCash 
	 * 功能: 提现信息列表 
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
	public String showCash() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		String succtime1 = StringUtils.isNull(this.request.getParameter("succtime1"));
		String succtime2 = StringUtils.isNull(this.request.getParameter("succtime2"));
		String cashTotal_min = StringUtils.isNull(this.request.getParameter("cashTotal_min"));
		String cashTotal_max = StringUtils.isNull(this.request.getParameter("cashTotal_max"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setStatus(status);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setCashTotal_min(cashTotal_min);
		param.setCashTotal_max(cashTotal_max);
		param.setSucctime1(succtime1);
		param.setSucctime2(succtime2);
		PageDataList plist = this.manageCashService.getAllCash(page, param);
		double creditSum = this.accountService.getRechargesum(param, 4);
		double totalSum = this.accountService.getRechargesum(param, 2);
		double feeSum = this.accountService.getRechargesum(param, 3);
		this.request.setAttribute("totalSum", Double.valueOf(totalSum));
		this.request.setAttribute("creditSum", Double.valueOf(creditSum));
		this.request.setAttribute("feeSum", Double.valueOf(feeSum));
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/showCash.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "cash_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "realname", "account", "bankname", "branch", "total", "credited", "fee", "addtime", "verify_time", "status" };
		String[] titles = { "ID", "用户名称", "真实姓名", "提现账号", "提现银行", "支行", "提现总额", "到账金额", "手续费", "提现时间", "操作时间", "状态" };
		List<AccountCash> list = this.manageCashService.getAllCash(param);
		ExcelHelper.writeExcel(infile, list, AccountCash.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viewCash 
	 * 功能: 查看提现 
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
	public String viewCash() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		AccountCash cash = this.manageCashService.getAccountCash(id);
		if (cash == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		this.request.setAttribute("cash", cash);
		saveToken("verifycash_token");
		UserAccountSummary uas = this.accountService.getUserAccountSummary(cash.getUser_id());
		UserAmount amt = this.userAmountService.getUserAmount(cash.getUser_id());
		this.request.setAttribute("uas", uas);
		this.request.setAttribute("amt", amt);
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: verifyCash 
	 * 功能: 审核提现 
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
	public String verifyCash() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		String verify_remark = StringUtils.isNull(this.request.getParameter("verify_remark"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		AccountCash cash = this.manageCashService.getAccountCash(id);
		if ((cash == null) || (StringUtils.isBlank(verify_remark)) || (status < 0) || (status > 3)) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (cash.getStatus() == 1) {
			message("该提现记录已经审核，不允许重复操作！");
			return ADMINMSG;
		}
		if (cash.getStatus() == 4) {
			message("该提现记录已经取消，不允许审核！");
			return ADMINMSG;
		}
		String tokenMsg = checkToken("verifycash_token");
		if (!StringUtils.isBlank(tokenMsg)) {
			message(tokenMsg);
			return ADMINMSG;
		}
		User authUser = getAuthUser();
		long verify_userid = 1L;
		if (authUser != null)
			verify_userid = authUser.getUser_id();
		cash.setStatus(status);
		cash.setVerify_userid(verify_userid);
		cash.setVerify_remark(verify_remark);
		cash.setVerify_time(getTimeStr());

		AccountLog log = new AccountLog(cash.getUser_id(), Constant.CASH_SUCCESS, verify_userid, getTimeStr(), getRequestIp());
		OperationLog operationLog = new OperationLog(cash.getUser_id(), authUser.getUser_id(), Constant.CASH_SUCCESS, getTimeStr(), getRequestIp(), "");
		this.manageCashService.verifyCash(cash, log, operationLog);
		message("操作成功！", "/admin/cash/showCash.html");

		NoticeConfig noticeConfig = Global.getNoticeConfig("cash");
		Notice s = new Sms();
		if (noticeConfig.getSms() == 1L) {
			User user = this.userService.getUserById(cash.getUser_id());
			s.setReceive_userid(cash.getUser_id());
			s.setSend_userid(cash.getVerify_userid());
			s.setAddtime(cash.getAddtime());
			if (cash.getStatus() == 1)
				s.setContent("尊敬的" + Global.getValue("webname") + "用户["
						+ user.getUsername() + "]，您于"
						+ DateUtils.dateStr5(s.getAddtime()) + "进行提现"
						+ cash.getTotal() + "元已经审核成功，即将安排财务打款，请注意查收！");
			else {
				s.setContent("尊敬的" + Global.getValue("webname") + "用户["
						+ user.getUsername() + "]，您于"
						+ DateUtils.dateStr5(s.getAddtime()) + "进行提现"
						+ cash.getTotal() + "元审核不通过，请登录网站查看详情！");
			}
			s.setMobile(user.getPhone());
			NoticeJobQueue.NOTICE.offer(s);
		}
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: recharge 
	 * 功能: 充值 
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
	public String recharge() throws Exception {
		if (Global.getWebid().equals("jsy")) {
			List<User> list = this.userService.getKfList();
			this.request.setAttribute("kflist", list);
		}
		String actionType = this.request.getParameter("actionType");
		if (StringUtils.isBlank(actionType)) {
			setMsgUrl("/admin/cash/recharge.html");
			return SUCCESS;
		}

		String username = StringUtils.isNull(this.request.getParameter("username"));

		double money = NumberUtils.getDouble(this.request.getParameter("money"));

		String remark = StringUtils.isNull(this.request.getParameter("remark"));

		String type = StringUtils.isNull(this.request.getParameter("type"));
		if (StringUtils.isBlank(type)) {
			type = "2";
		}

		if (StringUtils.isBlank(username)) {
			message("用户名不能为空！");
			return ADMINMSG;
		}
		if (money <= 0.0D) {
			message("充值的金额必须大于0");
			return ADMINMSG;
		}
		if (money >= 100000000.0D) {
			message("你充值的金额过大,目前系统仅支持千万级别的充值");
			return ADMINMSG;
		}
		if (StringUtils.isBlank(remark)) {
			message("备注不能为空！");
			return ADMINMSG;
		}

		User u = this.userService.getUserByName(username);
		if (u == null) {
			message("用户名:" + username + "不存在！");
			return ADMINMSG;
		}
		AccountRecharge r = new AccountRecharge();
		r.setUser_id(u.getUser_id());
		r.setMoney(money);
		r.setType(type);
		r.setPayment("48");
		r.setFee("0");
		r.setRemark(remark);
		r.setAddtime(getTimeStr());
		r.setAddip(getRequestIp());
		r.setTrade_no(StringUtils.generateTradeNO(u.getUser_id(), "E"));
		if (Global.getWebid().equals("jsy")) {
			String recharge_kefuid = StringUtils.isNull(this.request.getParameter("recharge_kefuid"));
			if (!StringUtils.isBlank(recharge_kefuid)) {
				r.setRecharge_kefuid(NumberUtils.getLong(recharge_kefuid));
			} else {
				message("请填写充值所属客服！！！");
				return ADMINMSG;
			}

		}

		this.accountService.addRecharge(r);

		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		DetailUser detailUser = this.userService.getDetailUser(auth_user.getUser_id());
		OperationLog operationLog = new OperationLog(u.getUser_id(), auth_user.getUser_id(), Constant.BACKSTAGE_RECHARGE_APPLY, getTimeStr(), getRequestIp(), "");
		operationLog.setOperationResult(detailUser.getTypename() + "（"
				+ operationLog.getAddip() + "）用户名为" + detailUser.getUsername()
				+ "的操作员申请后台线下充值" + money + "元成功，等待审核");
		this.accountService.addOperationLog(operationLog);
		message("充值成功，等待审核！");
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: back 
	 * 功能: 扣款 
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
	public String back() throws Exception {
		String actionType = this.request.getParameter("actionType");
		if (StringUtils.isBlank(actionType)) {
			setMsgUrl("/admin/cash/back.html");
			this.request.setAttribute("actionType", "back");
			return SUCCESS;
		}
		String username = StringUtils.isNull(this.request.getParameter("username"));
		double money = NumberUtils.getDouble(this.request.getParameter("money"));
		String remark = StringUtils.isNull(this.request.getParameter("remark"));
		if (StringUtils.isBlank(username)) {
			message("用户名不能为空！");
			return ADMINMSG;
		}
		if (money <= 0.0D) {
			message("扣款金额必须大于0");
			return ADMINMSG;
		}
		if (money >= 100000000.0D) {
			message("你扣款的金额过大,目前系统仅支持千万级别的扣款");
			return ADMINMSG;
		}
		if (StringUtils.isBlank(remark)) {
			message("备注不能为空！");
			return ADMINMSG;
		}

		User u = this.userService.getUserByName(username);
		if (u == null) {
			message("用户名:" + username + "不存在！");
			return ADMINMSG;
		}
		Account act = this.accountService.getAccount(u.getUser_id());

		AccountRecharge r = new AccountRecharge();

		r.setUser_id(u.getUser_id());
		r.setMoney(-money);
		r.setType("2");
		r.setPayment("50");
		r.setFee("0");
		r.setAddtime(getTimeStr());
		r.setAddip(getRequestIp());
		r.setRemark(remark);
		r.setVerify_remark(remark);
		r.setTrade_no(StringUtils.generateTradeNO(u.getUser_id(), "E"));
		this.accountService.addRecharge(r);

		this.accountService.updateAccount(0.0D, -money, money, u.getUser_id());

		act = this.accountService.getAccount(u.getUser_id());

		User authUser = (User) this.session.get(Constant.AUTH_USER);
		AccountLog log = new AccountLog(u.getUser_id(), Constant.FREEZE, authUser.getUser_id(), getTimeStr(), getRequestIp());

		log.setMoney(money);
		log.setTotal(act.getTotal());
		log.setUse_money(act.getUse_money());
		log.setNo_use_money(act.getNo_use_money());
		log.setCollection(act.getCollection());
		log.setRemark("后台扣款冻结" + money + "元,备注:" + remark);
		this.accountService.addAccountLog(log);
		DetailUser detailUser = this.userService.getDetailUser(authUser.getUser_id());
		OperationLog operationLog = new OperationLog(u.getUser_id(), authUser.getUser_id(), Constant.BACKSTAGE_BACK_APPLY, getTimeStr(), getRequestIp(), "");
		operationLog.setOperationResult(detailUser.getTypename() + "（"
				+ operationLog.getAddip() + "）用户名为" + detailUser.getUsername()
				+ "的操作员申请后台扣款" + r.getUsername() + money + "元成功，等待审核");
		this.accountService.addOperationLog(operationLog);
		message("扣款成功,等待管理员审核！");
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: backlist 
	 * 功能: 扣款列表
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
	public String backlist() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setPayment("50");
		fillSearchParam(param);
		PageDataList plist = this.accountService.getRechargeList(page, param);
		double sum = this.accountService.getRechargesum(param, 1);
		this.request.setAttribute("sum", Double.valueOf(sum));
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/rechargelist.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "recharge_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "trade_no", "username", "realname", "type", "paymentname", "money", "fee", "total", "addtime", "status" };
		String[] titles = { "ID", "流水号", "用户名称", "真实姓名", "类型", "所属银行", "充值金额", "费用", "到账金额", "充值时间", "状态" };
		List<AccountRecharge> list = this.accountService.getRechargeList(param);

		ExcelHelper.writeExcel(infile, list, AccountRecharge.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: rechargelist 
	 * 功能: 充值列表 
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
	public String rechargelist() throws Exception {
		if (Global.getWebid().equals("jsy")) {
			List<User> list = this.userService.getKfList();
			this.request.setAttribute("kflist", list);
		}
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		if (Global.getWebid().equals("wzdai")) {
			param.setPayment("-50");
		}
		fillSearchParam(param);
		PageDataList plist = this.accountService.getRechargeList(page, param);
		double sum = this.accountService.getRechargesum(param, 1);
		this.request.setAttribute("sum", Double.valueOf(sum));
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/rechargelist.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "recharge_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] titles;
		String[] names;
		if (Global.getWebid().equals("jsy")) {
				names = new String[] { "id", "trade_no", "username", "realname",
					"type", "paymentname", "money", "fee", "total", "addtime",
					"status", "recharge_kefu_username" };
			titles = new String[] { "ID", "流水号", "用户名称", "真实姓名", "类型", "所属银行",
					"充值金额", "费用", "到账金额", "充值时间", "状态", "充值所属客服" };
		} else {
			if (Constant.HUIZHOUDAI.equals(Global.getWebid())) {
				names = new String[] { "id", "trade_no", "username", "realname",
						"type", "paymentname", "money", "fee", "total",
						"addtime", "status", "remark" };
				titles = new String[] { "ID", "流水号", "用户名称", "真实姓名", "类型",
						"所属银行", "充值金额", "费用", "到账金额", "充值时间", "状态", "备注" };
			} else {
				names = new String[] { "id", "trade_no", "username",
						"realname", "type", "paymentname", "money", "fee",
						"total", "addtime", "status" };
				titles = new String[] { "ID", "流水号", "用户名称", "真实姓名", "类型",
						"所属银行", "充值金额", "费用", "到账金额", "充值时间", "状态" };
			}
		}
		List<AccountRecharge> list = this.accountService.getRechargeList(param);

		ExcelHelper.writeExcel(infile, list, AccountRecharge.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viewRecharge 
	 * 功能: 查看充值信息 
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
	public String viewRecharge() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		AccountRecharge r = this.accountService.getRecharge(id);
		if (r == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		this.request.setAttribute(Constant.RECHARGE, r);
		saveToken("verifyrecharge_token");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viewBack 
	 * 功能: 查看扣款信息
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
	public String viewBack() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		AccountRecharge r = this.accountService.getRecharge(id);
		if (r == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		this.request.setAttribute(Constant.RECHARGE, r);
		saveToken("verifyback_token");
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: verifyRecharge 
	 * 功能: 审核充值信息 
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
	public String verifyRecharge() throws Exception {
		setMsgUrl("/admin/cash/rechargelist.html");
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		double money = NumberUtils.getDouble(this.request.getParameter("total"));
		String verify_remark = StringUtils.isNull(this.request.getParameter("verify_remark"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		String tokenMsg = checkToken("verifyrecharge_token");
		if (!StringUtils.isBlank(tokenMsg)) {
			message(tokenMsg);
			return ADMINMSG;
		}
		if (id <= 0L) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (money <= 0.0D) {
			message("当前充值金额为" + money + ",充值金额不能为负数！");
			return ADMINMSG;
		}
		if (StringUtils.isBlank(verify_remark)) {
			message("审核备注不能为空！");
			return ADMINMSG;
		}
		AccountRecharge r = this.accountService.getRecharge(id);
		if ((r == null) || ((status != 1) && (status != 2))) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (r.getStatus() == 1) {
			message("该记录已经审核通过，不允许重复操作！");
			return ADMINMSG;
		}

		r.setStatus(status);
		r.setVerify_time(getTimeStr());
		r.setVerify_userid(auth_user.getUser_id());
		r.setVerify_remark(verify_remark);

		AccountLog log = new AccountLog(r.getUser_id(), Constant.RECHARGE, getAuthUser().getUser_id(), getTimeStr(), getRequestIp());

		OperationLog operationLog = new OperationLog(r.getUser_id(), getAuthUser().getUser_id(), Constant.RECHARGE, getTimeStr(), getRequestIp(), "");

		Protocol protocol = new Protocol(0L, Constant.RECHARGE_PROTOCOL, getTimeStr(), getRequestIp(), "");
		String flag = "";
		if (r.getStatus() != 1) {
			log.setRemark("审核充值记录，充值" + r.getMoney() + "元失败！");
			this.message.setName("充值失败");
			flag = "失败";
		} else {
			log.setRemark("审核充值记录，充值" + r.getMoney() + "元成功！");
			this.message.setName("充值成功");
			flag = "成功";
		}
		log.setRemark(log.getRemark() + "备注信息:" + StringUtils.isNull(r.getVerify_remark()));
		if (NumberUtils.getInt(r.getType()) == 1) {
			this.message.setContent("网上充值" + flag + ",充值金额为" + r.getMoney() + ",流水号：" + r.getTrade_no());
			log.setRemark(StringUtils.isNull(log.getRemark()) + "网上充值流水号:" + r.getTrade_no());
		} else {
			this.message.setContent("线下充值" + flag + ",充值金额为" + r.getMoney() + ",流水号：" + r.getTrade_no());
		}
		if (Global.getWebid().equals("wzdai")) {
			String hongbao_apr = Global.getValue("hongbao_apr");
			double hongbao_add = NumberUtils.format2(r.getMoney() * NumberUtils.getDouble(hongbao_apr));
			HongBao hongbao = new HongBao();
			hongbao.setAddip(getRequestIp());
			hongbao.setAddtime(getTimeStr());
			hongbao.setHongbao_money(hongbao_add);
			hongbao.setRemark("");
			hongbao.setType(Constant.HONGBAO_ADD);
			hongbao.setUser_id(r.getUser_id());
			this.accountService.verifyRecharge(r, log, hongbao, operationLog);
		} else if (Global.getWebid().equals("jsy")) {
			this.accountService.verifyRecharge(r, log, operationLog, protocol);
		} else {
			this.accountService.verifyRecharge(r, log, operationLog);
		}

		this.request.setAttribute(Constant.RECHARGE, r);
		message("审核成功!", "/admin/cash/rechargelist.html");

		this.message.setSent_user(auth_user.getUser_id());
		this.message.setReceive_user(r.getUser_id());
		this.message.setStatus(0);
		this.message.setType(Constant.SYSTEM);

		this.message.setAddip(getRequestIp());
		this.message.setAddtime(getTimeStr());
		this.messageService.addMessage(this.message);

		NoticeConfig noticeConfig = Global.getNoticeConfig("down_recharge");
		Notice s = new Sms();
		if (noticeConfig.getSms() == 1L) {
			User user = this.userService.getUserById(r.getUser_id());
			s.setReceive_userid(this.message.getReceive_user());
			s.setSend_userid(this.message.getSent_user());
			s.setAddtime(r.getAddtime());
			if (r.getStatus() == 1)
				s.setContent("尊敬的" + Global.getValue("webname") + "用户["
						+ user.getUsername() + "],您于"
						+ DateUtils.dateStr5(s.getAddtime()) + "进行线下充值"
						+ r.getMoney() + "元已经审核成功!");
			else {
				s.setContent("尊敬的" + Global.getValue("webname") + "用户["
						+ user.getUsername() + "],您于"
						+ DateUtils.dateStr5(s.getAddtime()) + "进行线下充值"
						+ r.getMoney() + "元审核不通过,请登录网站查看详情!");
			}
			s.setMobile(user.getPhone());
			NoticeJobQueue.NOTICE.offer(s);
		}
		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: verifyBack 
	 * 功能: 审核扣款信息
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
	public String verifyBack() throws Exception {
		User auth_user = (User) this.session.get(Constant.AUTH_USER);
		setMsgUrl("/admin/cash/rechargelist.html");
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		double money = NumberUtils.getDouble(this.request.getParameter("total"));
		String verify_remark = StringUtils.isNull(this.request.getParameter("verify_remark"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		String tokenMsg = checkToken("verifyback_token");
		if (!StringUtils.isBlank(tokenMsg)) {
			message(tokenMsg);
			return ADMINMSG;
		}
		if (id <= 0L) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (money >= 0.0D) {
			message("当前扣款金额为" + money + ",扣款金额必须为负数！");
			return ADMINMSG;
		}
		if (StringUtils.isBlank(verify_remark)) {
			message("审核备注不能为空！");
			return ADMINMSG;
		}
		AccountRecharge r = this.accountService.getRecharge(id);
		if ((r == null) || ((status != 1) && (status != 2))) {
			message("非法操作！");
			return ADMINMSG;
		}
		if (r.getStatus() == 1) {
			message("该记录已经审核通过，不允许重复操作！");
			return ADMINMSG;
		}

		r.setStatus(status);
		r.setVerify_time(getTimeStr());
		r.setVerify_userid(auth_user.getUser_id());
		r.setVerify_remark(verify_remark);

		AccountLog log = new AccountLog(r.getUser_id(), Constant.ACCOUNT_BACK, getAuthUser().getUser_id(), getTimeStr(), getRequestIp());

		OperationLog operationLog = new OperationLog(r.getUser_id(), getAuthUser().getUser_id(), Constant.RECHARGE, getTimeStr(), getRequestIp(), "");
		if (r.getStatus() != 1)
			log.setRemark("审核扣款记录，扣款" + r.getMoney() + "元不成功！备注:" + verify_remark);
		else {
			log.setRemark("审核扣款记录，扣款" + r.getMoney() + "元成功！备注:" + verify_remark);
		}
		this.accountService.verifyRecharge(r, log, operationLog);
		this.request.setAttribute(Constant.RECHARGE, r);
		message("审核成功!");

		auth_user = (User) this.session.get(Constant.AUTH_USER);
		this.message.setSent_user(auth_user.getUser_id());
		this.message.setReceive_user(r.getUser_id());
		this.message.setStatus(0);
		this.message.setType(Constant.SYSTEM);
		this.message.setName("扣款成功");
		this.message.setContent("扣款成功,扣款金额为" + r.getMoney() + ",流水号：" + r.getTrade_no());
		this.message.setAddip(getRequestIp());
		this.message.setAddtime(getTimeStr());
		this.messageService.addMessage(this.message);
		return ADMINMSG;
	}

	private void fillSearchParam(SearchParam param) {
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		String succtime1 = StringUtils.isNull(this.request
				.getParameter("succtime1"));
		String succtime2 = StringUtils.isNull(this.request
				.getParameter("succtime2"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		String account_type = StringUtils.isNull(this.request
				.getParameter("account_type"));
		String paymentname = StringUtils.isNull(this.request
				.getParameter("paymentname"));
		String trade_no = StringUtils.isNull(this.request
				.getParameter("trade_no"));
		String recharge_kefu_username = StringUtils.isNull(this.request
				.getParameter("recharge_kefu_username"));
		param.setRecharge_kefu_username(recharge_kefu_username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setSucctime1(succtime1);
		param.setSucctime2(succtime2);
		param.setUsername(username);
		param.setStatus(status);
		param.setAccount_type(account_type);
		param.setPaymentname(paymentname);
		param.setTrade_no(trade_no);
	}

	/**
	 * **************************************************************************************
	 * 方法名: log 
	 * 功能: 资金使用记录
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
	public String log() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String account_Type = StringUtils.isNull(this.request
				.getParameter("account_type"));
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
		param.setAccount_type(account_Type);
		PageDataList plist = this.accountService.getAccountLogList(page, param);
		setPageAttribute(plist, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "log_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "to_username", "typename", "money",
				"total", "use_money", "no_use_money", "collection", "remark",
				"addtime", "addip" };
		String[] titles = { "用户名", "交易对方", "交易类型", "操作金额", "账户总额", "可用金额",
				"冻结金额", "待收金额", "备注", "时间", "IP" };
		List list = this.accountService.getAccountLogList(param);
		ExcelHelper.writeExcel(infile, list, AccountLogModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: tenderLog 
	 * 功能: 用户投标记录 
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
	public String tenderLog() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setUsername(username);
		PageDataList plist = this.accountService.getTenderLogList(page, param);
		double tenderInterestSum = this.accountService.getRechargesum(param, 5);
		this.request.setAttribute("sum", Double.valueOf(tenderInterestSum));
		setPageAttribute(plist, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "tenderLog_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "borrow_id", "borrow_name", "username",
				"tender_money", "tender_account", "repayment_account", "apr",
				"interest", "addtime" };
		String[] titles = { "借款标ID", "借款标名称", "投标人", "操作金额", "有效金额", "回款金额",
				"利率", "获得利息", "投标时间" };
		List<DetailTender> list = this.accountService.getTenderLogList(param);
		ExcelHelper.writeExcel(infile, list, DetailTender.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: viptc 
	 * 功能: 会员邀请记录
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
	public String viptc() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String invite_username = StringUtils.isNull(this.request.getParameter("invite_username"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		param.setInvite_username(invite_username);
		PageDataList plist = this.userService.getInviteUserList(page, param);
		setPageAttribute(plist, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "viptc_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "user_id", "invite_username", "username", "realname", "addtime", "vip_status" };
		String[] titles = { "用户ID", "推广者用户名", "下线用户名", "真实姓名", "注册时间", "是否VIP会员" };
		List<DetailUser> list = this.userService.getInviteUserList(param);
		ExcelHelper.writeExcel(infile, list, DetailUser.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: vipstatistic 
	 * 功能: vip客户统计 
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
	public String vipstatistic() {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String kefu_username = StringUtils.isNull(this.request
				.getParameter("kefu_username"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new IdentifySearchParam();
		param.setUsername(username);
		param.setKefu_username(kefu_username);
		PageDataList plist = this.userService.getVipStatistic(page, param);
		setPageAttribute(plist, param);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "vipstatistic_" + System.currentTimeMillis()
				+ ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "kefu_username", "user_id", "username", "realname",
				"registertime", "vip_verify_time", "collection" };
		String[] titles = { "客服名", "用户ID", "用户名", "真实姓名", "注册时间", "VIP审核时间",
				"待收资金" };
		List<VIPStatisticModel> list = this.userService.getVipStatistic(param);
		try {
			ExcelHelper.writeExcel(infile, list, VIPStatisticModel.class,
					Arrays.asList(names), Arrays.asList(titles));
			export(infile, downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * **************************************************************************************
	 * 方法名: manageUserCashBank 
	 * 功能: 提现银行卡管理
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
	public String manageUserCashBank() {
		String type = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (type.equals("show")) {
			long user_id = NumberUtils.getLong(this.request
					.getParameter("user_id"));
			String bankAccount = StringUtils.isNull(this.request
					.getParameter("bankaccount"));
			AccountModel act = null;
			if (!bankAccount.equals(""))
				act = this.accountService.getAccountByBankAccount(user_id,
						bankAccount);
			else {
				act = this.accountService.getAccount(user_id);
			}
			this.request.setAttribute("account", act);
			getAndSaveRef();
			return "show";
		}
		if (type.equals("add")) {
			long user_id = NumberUtils.getLong(this.request
					.getParameter("user_id"));
			String bankaccount = StringUtils.isNull(this.request
					.getParameter("bankaccount"));
			String bank = StringUtils.isNull(this.request.getParameter("bank"));
			String branch = StringUtils.isNull(this.request
					.getParameter("branch"));
			String account = StringUtils.isNull(this.request
					.getParameter("account"));
			if ((bank.isEmpty()) || (branch.isEmpty()) || (account.isEmpty())) {
				message("填写内容不能为空！", "");
				return ADMINMSG;
			}
			AccountBank actbank = new AccountBank();
			AccountModel detailAct = this.accountService.getAccount(user_id);
			User authUser = getAuthUser();
			if ((!account.equals("")) && (!branch.equals(""))) {
				actbank.setAccount(account);
				actbank.setBranch(branch);
				actbank.setUser_id(user_id);
				actbank.setBank(bank);
				actbank.setAddip(getRequestIp());
				actbank.setAddtime(DateUtils.getTimeStr(new Date()));
				actbank.setModify_username(authUser.getUsername());
			}
			DetailUser detailUser = this.userService.getDetailUser(authUser
					.getUser_id());
			DetailUser newdetailUser = this.userService.getDetailUser(user_id);
			OperationLog operationLog = new OperationLog(actbank.getUser_id(),
					authUser.getUser_id(), Constant.BANK_SUCCESS, getTimeStr(),
					getRequestIp(), "");
			if ((detailAct.getBank() == null)
					|| (detailAct.getBank().equals(""))) {
				this.accountService.addBank(actbank);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核用户名为"
						+ newdetailUser.getUsername() + "的用户新增银行卡信息！");
			} else {
				this.accountService.modifyBankByAccount(actbank, bankaccount);
				operationLog.setOperationResult(detailUser.getTypename() + "（"
						+ operationLog.getAddip() + "）用户名为"
						+ detailUser.getUsername() + "的操作员审核用户名为"
						+ newdetailUser.getUsername() + "的用户修改银行卡信息！");
			}
			this.manageCashService.bankLog(operationLog);
			message("操作成功", getRef());
			return ADMINMSG;
		}
		if (type.equals("export")) {
			int page = NumberUtils.getInt(this.request.getParameter("page"));
			String username = StringUtils.isNull(this.request
					.getParameter("username"));
			SearchParam param = new SearchParam();
			param.setUsername(username);
			List list = this.accountService.getAccountList(param);
			String contextPath = ServletActionContext.getServletContext()
					.getRealPath("/");
			String downloadFile = "cash_" + System.currentTimeMillis() + ".xls";
			String infile = contextPath + "data/export/" + downloadFile;

			String[] names = { "user_id", "username", "realname", "bankname",
					"branch", "bankaccount" };
			String[] titles = { "用户ID", "用户名", "用户真实姓名", "开户银行", "开户银行名称",
					"提现银行卡卡号" };
			try {
				ExcelHelper.writeExcel(infile, list, AccountModel.class, Arrays
						.asList(names), Arrays.asList(titles));
				export(infile, downloadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setUsername(username);
		PageDataList plist = this.accountService.getUserAccountModel(page, param);
		setPageAttribute(plist, param);
		return SUCCESS;
	}

	public String showAllHuikuan() {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String status = StringUtils.isNull(this.request.getParameter("status"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));

		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setStatus(status);
		param.setUsername(username);
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		PageDataList plist = this.accountService.huikuanlist(page, param);
		List<HuikuanModel> list = (List<HuikuanModel>) plist.getList();
		setPageAttribute(plist, param);
		this.request.setAttribute("list", list);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "huikuan_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "huikuan_money", "huikuan_award", "addtime", "remark" };
		String[] titles = { "ID", "用户名称", "回款金额", "回款奖励", "添加时间", "备注" };
		list = this.accountService.huikuanlist(param);
		try {
			ExcelHelper.writeExcel(infile, list, HuikuanModel.class, Arrays.asList(names), Arrays.asList(titles));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			export(infile, downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String viewHuikuan() {
		String id = this.request.getParameter("id");
		HuikuanModel huikuan = null;
		if (!StringUtils.isNull(id).equals("")) {
			huikuan = this.accountService.viewHuikuan(Integer.parseInt(id));
		}
		this.request.setAttribute("huikuan", huikuan);
		return SUCCESS;
	}

	public String verifyHuikuan() {
		User user = (User) this.session.get(Constant.AUTH_USER);
		setMsgUrl("/admin/cash/showAllHuikuan.html");
		String ids = this.request.getParameter("id");
		String user_ids = this.request.getParameter("user_id");
		String status = this.request.getParameter("status");
		long verify_userid = 1L;
		String payment = "56";
		String type = "3";
		String fee = "0";
		String paymentname = "";
		int id = 0;
		int user_id = 0;
		if (!StringUtils.isNull(user_ids).equals("")) {
			user_id = Integer.parseInt(user_ids);
		}
		if (!StringUtils.isNull(ids).equals("")) {
			id = Integer.parseInt(ids);
			if (id <= 0) {
				message("非法操作！");
				return ADMINMSG;
			}
			HuikuanModel h = this.accountService.viewHuikuan(id);
			if ((h == null) || ((!status.equals("1")) && (!status.equals("4")))) {
				message("非法操作！");
				return ADMINMSG;
			}
			if (h.getStatus().equals("1")) {
				message("该记录已经审核通过，不允许重复操作！");
				return ADMINMSG;
			}

			h.setStatus(status);
			AccountLog log = new AccountLog(user_id, Constant.HUIKUAN_AWARD, verify_userid, getTimeStr(), getRequestIp());
			AccountRecharge recharge = new AccountRecharge(StringUtils.generateTradeNO(h.getUser_id(), "K"), user_id, payment, type, fee, verify_userid, getTimeStr(), getRequestIp(), paymentname);
			this.accountService.verifyHuikuan(h, log, recharge);
			this.request.setAttribute("huikuan", h);
			message("审核成功!", "/admin/cash/showAllHuikuan.html");
		}

		return ADMINMSG;
	}

	/**
	 * **************************************************************************************
	 * 方法名: excelRecharge 
	 * 功能: 批量导入充值 
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
	public String excelRecharge() throws Exception {
		String type = paramString("type");
		if (type.equals("upload")) {
			try {
				upload(this.excel, this.excelFileName, "/data/upload", this.excelFileName);
			} catch (Exception localException1) {
			}
			String xls = ServletActionContext.getServletContext().getRealPath("/data/upload") + File.separator + this.excelFileName;
			List[] data = ExcelHelper.read(xls);
			if ((data != null) && (data.length > 0)) {
				this.request.setAttribute("excelName", this.excelFileName);
				this.request.setAttribute("data", data);
				List[] retData = null;
				try {
					retData = this.accountService.addBatchRecharge(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.request.setAttribute("retData", retData);
			}
		}
		return SUCCESS;
	}

	public String addbank() {
		String type = StringUtils.isNull(this.request.getParameter("add"));
		if (type.equals("add")) {
			String username = StringUtils.isNull(this.request.getParameter("username"));
			String branch = StringUtils.isNull(this.request.getParameter("branch"));
			String bank = StringUtils.isNull(this.request.getParameter("bank"));
			String account = StringUtils.isNull(this.request.getParameter("account"));
			User u = this.userService.getUserByName(username);
			try {
				AccountBank bk = new AccountBank();
				bk.setUser_id(u.getUser_id());
				bk.setAccount(account);
				bk.setBank(bank);
				bk.setBranch(branch);
				bk.setAddip("1.1.1.1");
				bk.setAddtime(DateUtils.getTimeStr(new Date()));

				bk = this.accountService.addBank(bk);
				System.out.println(bk.getId());
				if (bk.getId() > 0L) {
					message("添加成功", "/admin/cash/cashbank.html?search=true&username=" + username);
					return ADMINMSG;
				}
				message("添加失败，请检查输入", "/admin/cash/addbank.html");
				return ADMINMSG;
			} catch (Exception e) {
				message("添加失败，请检查输入", "/admin/cash/addbank.html");
				return ADMINMSG;
			}
		}

		return SUCCESS;
	}

	public String hongBaoList() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String account_Type = StringUtils.isNull(this.request.getParameter("account_type"));
		String username = StringUtils.isNull(this.request.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setAccount_type(account_Type);
		param.setUsername(username);
		PageDataList plist = this.accountService.getHongBaoList(page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/hongBaoList.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "hongbao_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "hongbao_money", "typename", "hongbao", "addtime" };
		String[] titles = { "ID", "用户名称", "操作金额", "类型", "该用户现有红包", "添加时间" };
		List list = this.accountService.getHongBaoList(param);

		ExcelHelper.writeExcel(infile, list, HongBaoModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String accountReconciliationList() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String account_Type = StringUtils.isNull(this.request
				.getParameter("account_type"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		SearchParam param = new SearchParam();
		param.setAccount_type(account_Type);
		param.setUsername(username);
		PageDataList plist = this.accountService.getAccountReconciliationList(
				page, param);
		setPageAttribute(plist, param);
		setMsgUrl("/admin/cash/accountReconciliationList.html");
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "hongbao_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "username", "hongbao_money", "typename",
				"hongbao", "addtime" };
		String[] titles = { "ID", "用户名称", "操作金额", "类型", "该用户现有红包", "添加时间" };
		List list = this.accountService.getHongBaoList(param);

		ExcelHelper.writeExcel(infile, list, HongBaoModel.class, Arrays
				.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String advanced_account() {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		List list = this.manageCashService.getAdvancedList();
		this.request.setAttribute("list", list);
		if (StringUtils.isBlank(actionType)) {
			return SUCCESS;
		}
		Advanced advanced = new Advanced();
		String advance_reserve = StringUtils.isNull(this.request
				.getParameter("advance_reserve"));
		String no_advanced_account = StringUtils.isNull(this.request
				.getParameter("no_advanced_account"));

		if (list.size() > 0) {
			advanced = (Advanced) list.get(0);
			advanced.setAdvance_reserve(NumberUtils.getDouble(advance_reserve));
			advanced.setNo_advanced_account(NumberUtils
					.getDouble(no_advanced_account));
			this.manageCashService.getAdvanced_update(advanced);
		} else {
			advanced.setAdvance_reserve(NumberUtils.getDouble(advance_reserve));
			advanced.setNo_advanced_account(NumberUtils
					.getDouble(no_advanced_account));
			this.manageCashService.getAdvanced_insert(advanced);
		}

		return SUCCESS;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: operationLog 
	 * 功能: 操作记录 
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
	public String operationLog() {
		String actionType = this.request.getParameter("type");
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		String verify_username = StringUtils.isNull(this.request
				.getParameter("verify_username"));
		String user_type = StringUtils.isNull(this.request
				.getParameter("user_type"));
		SearchParam param = new SearchParam();
		User user = (User) this.session.get(Constant.AUTH_USER);
		if (user.getType_id() != 1) {
			param.setUser_typeid(String.valueOf(user.getType_id()));
		}
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setType(user_type);
		param.setVerify_username(verify_username);
		PageDataList list = this.accountService.operationLog(page, param);
		setPageAttribute(list, param);
		if (StringUtils.isBlank(actionType)) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = "operationLog_" + System.currentTimeMillis()
				+ ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "id", "verify_username", "typename", "username",
				"operationResult", "addtime", "addip" };
		String[] titles = { "ID", "操作人员", "操作类型", "用户名", "操作结果", "操作时间", "操作ip" };
		List<OperationLogModel> operationList = this.accountService.operationLog(param);
		try {
			ExcelHelper.writeExcel(infile, operationList,
					OperationLogModel.class, Arrays.asList(names), Arrays
							.asList(titles));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			export(infile, downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String showAllCreditCard() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam searchParam = new SearchParam();
		PageDataList pageDataList = this.creditCardService.getList(page,
				searchParam);
		this.request.setAttribute("list", pageDataList);
		setPageAttribute(pageDataList, searchParam);
		setMsgUrl("/admin/cash/showAllCreditCard.html");
		return SUCCESS;
	}

	private String truncatUrl(String old, String truncat) {
		String url = "";
		url = old.replace(truncat, "");
		url = url.replace(this.sep, "/");
		return url;
	}

	private void moveFile(CreditCard creditCard) {
		String dataPath = ServletActionContext.getServletContext().getRealPath(
				"/data");
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		Date d1 = new Date();
		String upfiesDir = dataPath + this.sep + "upfiles" + this.sep
				+ "images" + this.sep;
		String destfilename1 = upfiesDir + DateUtils.dateStr2(d1) + this.sep
				+ creditCard.getCard_id() + "_attestation" + "_" + d1.getTime()
				+ ".jpg";
		this.filePath = destfilename1;
		this.filePath = truncatUrl(this.filePath, contextPath);
		logger.info(destfilename1);
		File imageFile1 = null;
		try {
			imageFile1 = new File(destfilename1);
			FileUtils.copyFile(this.litpic, imageFile1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	private void moveFile(OnlineBank onlineBank) {
		String dataPath = ServletActionContext.getServletContext().getRealPath(
				"/data");
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		Date d1 = new Date();
		String upfiesDir = dataPath + this.sep + "upfiles" + this.sep
				+ "images" + this.sep;
		String destfilename1 = upfiesDir + DateUtils.dateStr2(d1) + this.sep
				+ onlineBank.getId() + "_attestation" + "_" + d1.getTime()
				+ ".jpg";
		this.filePath = destfilename1;
		this.filePath = truncatUrl(this.filePath, contextPath);
		logger.info(destfilename1);
		File imageFile1 = null;
		try {
			imageFile1 = new File(destfilename1);
			FileUtils.copyFile(this.bank_logo, imageFile1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public String addCreditCard() throws Exception {
		String actionType = StringUtils
				.isNull(this.request.getParameter("add"));
		if (actionType.equals("add")) {
			fillCreditCard(this.model);
			if (this.litpic == null) {
				message("你上传的图片为空", "/admin/cash/addCreditCard.html");
				return MSG;
			}
			moveFile(this.model);
			this.model.setLitpic(this.filePath);
			CreditCard creditCard = this.creditCardService
					.addCreditCard(this.model);
			this.request.setAttribute("creditCard", creditCard);
			message("新增信用卡成功！", "/admin/cash/addCreditCard.html");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	private CreditCard fillCreditCard(CreditCard creditCard) {
		creditCard.setName(this.request.getParameter("name"));
		creditCard.setIssuing_bank(this.request.getParameter("issuing_bank"));
		creditCard.setIssuing_nstitution(this.request
				.getParameter("issuing_nstitution"));
		creditCard.setIssuing_status(this.request
				.getParameter("issuing_status"));
		creditCard.setCheck_style(this.request.getParameter("check_style"));
		creditCard.setInterest(this.request.getParameter("interest"));
		creditCard.setTel(this.request.getParameter("tel"));
		creditCard.setCurrency(this.request.getParameter("currency"));
		creditCard.setGrade(this.request.getParameter("grade"));
		creditCard.setBorrowing_limit(this.request
				.getParameter("borrowing_limit"));
		creditCard.setInterest_free(this.request.getParameter("interest_free"));
		creditCard.setIntegral_policy(this.request
				.getParameter("integral_policy"));
		creditCard.setIntegral_indate(this.request
				.getParameter("integral_indate"));
		creditCard.setCredit_rules(this.request.getParameter("credit_rules"));
		creditCard.setScoring_rules(this.request.getParameter("scoring_rules"));
		creditCard.setInstallment(this.request.getParameter("installment"));
		creditCard.setAmount(this.request.getParameter("amount"));
		creditCard.setFee_payment(this.request.getParameter("fee_payment"));
		creditCard.setApplicable_fee(this.request
				.getParameter("applicable_fee"));
		creditCard.setPrepayment(this.request.getParameter("prepayment"));
		creditCard.setOpen_card(this.request.getParameter("open_card"));
		creditCard.setRepea_card(this.request.getParameter("repea_card"));
		creditCard.setApp_condition(this.request.getParameter("app_condition"));
		creditCard.setApp_way(this.request.getParameter("app_way"));
		creditCard.setSubmit_info(this.request.getParameter("submit_info"));
		creditCard.setSupplement_num(this.request
				.getParameter("supplement_num"));
		creditCard.setSupplement_app(this.request
				.getParameter("supplement_app"));
		creditCard.setReport_loss(this.request.getParameter("report_loss"));
		creditCard.setLoss_protection(this.request
				.getParameter("loss_protection"));
		creditCard.setLoss_tel(this.request.getParameter("loss_tel"));
		creditCard.setLowest_refund(this.request.getParameter("lowest_refund"));
		creditCard.setAllopatry_back_fee(this.request
				.getParameter("allopatry_back_fee"));
		creditCard.setRmb_repayment(this.request.getParameter("rmb_repayment"));
		creditCard.setForeign_repayment(this.request
				.getParameter("foreign_repayment"));
		creditCard.setSpecial_repayment(this.request
				.getParameter("special_repayment"));
		creditCard.setCard_features(this.request.getParameter("card_features"));
		creditCard.setAdd_service(this.request.getParameter("add_service"));
		creditCard.setJoint_discount(this.request
				.getParameter("joint_discount"));
		creditCard.setMain_card_fee(this.request.getParameter("main_card_fee"));
		creditCard.setYear_cut_rules(this.request
				.getParameter("year_cut_rules"));
		creditCard.setSupplement_card_fee(this.request
				.getParameter("supplement_card_fee"));
		creditCard.setFee_date(this.request.getParameter("fee_date"));
		creditCard.setLocal_fee(this.request.getParameter("local_fee"));
		creditCard.setLocal_interbank_fee(this.request
				.getParameter("local_interbank_fee"));
		creditCard.setOffsite_fee(this.request.getParameter("offsite_fee"));
		creditCard.setOffsite_interbank_fee(this.request
				.getParameter("offsite_interbank_fee"));
		creditCard.setOverseas_pay_fee(this.request
				.getParameter("overseas_pay_fee"));
		creditCard.setOverseas_unpay_fee(this.request
				.getParameter("overseas_unpay_fee"));
		creditCard.setOverseas_meet_fee(this.request
				.getParameter("overseas_meet_fee"));
		creditCard.setEnchashment_limit(this.request
				.getParameter("enchashment_limit"));
		creditCard.setLocalback_fee(this.request.getParameter("localback_fee"));
		creditCard.setLocal_interbank_back_fee(this.request
				.getParameter("local_interbank_back_fee"));
		creditCard.setOffsite_overflow_back_fee(this.request
				.getParameter("offsite_overflow_back_fee"));
		creditCard.setOffsite_interbank_back_fee(this.request
				.getParameter("offsite_interbank_back_fee"));
		creditCard.setOverseas_pay_back_fee(this.request
				.getParameter("overseas_pay_back_fee"));
		creditCard.setOverseas_unpay_back_fee(this.request
				.getParameter("overseas_unpay_back_fee"));
		creditCard.setOverflow_back_rules(this.request
				.getParameter("overflow_back_rules"));
		creditCard.setMessage_money(this.request.getParameter("message_money"));
		creditCard.setOverseas_fee(this.request.getParameter("overseas_fee"));
		creditCard.setChange_card(this.request.getParameter("change_card"));
		creditCard.setAhead_change_card(this.request
				.getParameter("ahead_change_card"));
		creditCard.setExpress_fee(this.request.getParameter("express_fee"));
		creditCard.setStatement_fee(this.request.getParameter("statement_fee"));
		creditCard.setStatement_free_clause(this.request
				.getParameter("statement_free_clause"));
		creditCard.setLoss_fee(this.request.getParameter("loss_fee"));
		creditCard.setReset_password_fee(this.request
				.getParameter("reset_password_fee"));
		creditCard.setSelfdom_card_fee(this.request
				.getParameter("selfdom_card_fee"));
		creditCard.setForeign_convert_fee(this.request
				.getParameter("foreign_convert_fee"));
		creditCard.setSlip_fee(this.request.getParameter("slip_fee"));
		creditCard.setSlip_fee_copy(this.request.getParameter("slip_fee_copy"));
		creditCard.setSlip_fee_foreign(this.request
				.getParameter("slip_fee_foreign"));
		creditCard.setSlip_fee_copy_foreign(this.request
				.getParameter("slip_fee_copy_foreign"));
		creditCard.setOverdue_fine(this.request.getParameter("overdue_fine"));
		creditCard.setTransfinite_fee(this.request
				.getParameter("transfinite_fee"));
		creditCard.setType_value(Integer.valueOf(
				this.request.getParameter("type_value")).intValue());
		creditCard.setLitpic(StringUtils.isNull(this.request
				.getParameter("litpic")));
		return creditCard;
	}

	public String modifyCreditCard() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		int cardId = Integer.valueOf(this.request.getParameter("cardId"))
				.intValue();
		if (!StringUtils.isBlank(actionType)) {
			fillCreditCard(this.model);
			this.model.setCard_id(cardId);
			if (this.litpic == null) {
				CreditCard card = this.creditCardService.getCardById(cardId);
				this.model.setLitpic(card.getLitpic());
			} else {
				moveFile(this.model);
				this.model.setLitpic(this.filePath);
			}
			this.creditCardService.updateCreditCard(this.model);
			message("修改文章成功！", "");
			return ADMINMSG;
		}
		CreditCard creditCard = this.creditCardService.getCardById(cardId);
		this.request.setAttribute("creditCard", creditCard);
		return SUCCESS;
	}

	public String delCreditCard() throws Exception {
		int cardId = Integer.valueOf(this.request.getParameter("cardId"))
				.intValue();
		this.creditCardService.delCreditCard(cardId);
		message("删除文章成功！", "/admin/cash/showAllCreditCard.html");
		return ADMINMSG;
	}

	public String cashStatistic() throws Exception {
		this.request.setAttribute(Constant.BORROW_SUCCESS, Double.valueOf(this.accountService.getAccountLogSum("borrow_success")));

		this.request.setAttribute("allLateSum", Double.valueOf(this.accountService.getAllLateSumWithYesRepaid() + this.accountService.getAllLateSumWithNoRepaid()));

		this.request.setAttribute("yesLateSum", Double.valueOf(this.accountService.getAllLateSumWithYesRepaid()));

		this.request.setAttribute("noLateSum", Double.valueOf(this.accountService.getAllLateSumWithNoRepaid()));

		this.request.setAttribute("yesRepayment", Double.valueOf(this.accountService.getRepaymentAccount(1)));

		this.request.setAttribute("noRepayment", Double.valueOf(this.accountService.getRepaymentAccount(0)));
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String dotime1 = StringUtils.isNull(this.request.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request.getParameter("dotime2"));
		SearchParam param = new SearchParam();
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		this.request.setAttribute("param", param.toMap());
		List<AccountLogSumModel> logSumList = this.accountService.getAccountLogSumWithMonth(param);
		this.request.setAttribute("logSumList", logSumList);
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "accountLogSum_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "typename", "sum" };
		String[] titles = { "类型名称", "金额" };
		logSumList = this.accountService.getAccountLogSumWithMonth(param);
		ExcelHelper.writeExcel(infile, logSumList, AccountLogSumModel.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String exportTenderRank() throws Exception {
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "allRankList_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "tenderMoney" };
		String[] titles = { "投资人", "投资总额" };
		List AllRankList = this.borrowService.getAllRankList();
		ExcelHelper.writeExcel(infile, AllRankList, RankModel.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String rechargeDownLineBank() throws Exception {
		String actionType = StringUtils.isNull(this.request.getParameter("type"));
		String idString = StringUtils.isNull(this.request.getParameter("id"));
		AccountBank bank = new AccountBank();
		int id = 0;
		if (!StringUtils.isBlank(idString)) {
			id = Integer.parseInt(idString);
		}
		if (StringUtils.isBlank(actionType)) {
			if (id == 0) {
				return SUCCESS;
			}
			bank = this.manageCashService.getDownLineBank(id);
			this.request.setAttribute("bank", bank);
			return SUCCESS;
		}
		this.request.setAttribute("actiontype", actionType);
		if ("list".equals(actionType)) {
			int page = NumberUtils.getInt(this.request.getParameter("page"));
			this.pageList = this.manageCashService.getList(page);
			setPageAttribute(this.pageList, new SearchParam());
		} else {
			String bank_name = StringUtils.isNull(this.request.getParameter("bank"));
			String bank_account = StringUtils.isNull(this.request.getParameter("account"));
			String bank_persion = StringUtils.isNull(this.request.getParameter("bank_realname"));
			String branch = StringUtils.isNull(this.request.getParameter("branch"));
			long payment = NumberUtils.getLong(StringUtils.isNull(this.request.getParameter("payment")));
			bank.setAccount(bank_account);
			bank.setBank_realname(bank_persion);
			bank.setBank(bank_name);
			bank.setBranch(branch);
			bank.setId(id);
			bank.setPayment(payment);
			this.manageCashService.rechargeDownLineBank(bank, idString);
			message("操作成功！", "/admin/cash/downLineBank.html?type=list");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public String rechargeDownLineBankList() {
		return SUCCESS;
	}

	public String addPayInterface() {
		String actionType = StringUtils.isNull(this.request
				.getParameter("type"));
		if (StringUtils.isBlank(actionType)) {
			return SUCCESS;
		}
		PaymentInterface paymentInterface = new PaymentInterface();
		paymentInterface = getParameter(paymentInterface);
		this.manageCashService.addPayInterface(paymentInterface);
		message("操作成功！", "/admin/cash/payInterface.html");
		return ADMINMSG;
	}

	public String payInterfaceList() {
		String actionType = StringUtils.isNull(this.request
				.getParameter("type"));
		String idString = StringUtils.isNull(this.request.getParameter("id"));
		PaymentInterface paymentInterface = new PaymentInterface();
		int id = 0;
		if (!StringUtils.isBlank(idString)) {
			id = Integer.parseInt(idString);
		}
		if (StringUtils.isBlank(actionType)) {
			if (id == 0) {
				return SUCCESS;
			}
			paymentInterface = this.manageCashService.getPayInterface(id);
			this.request.setAttribute("paymentInterface", paymentInterface);
			return SUCCESS;
		}
		if ("list".equals(actionType)) {
			int page = NumberUtils.getInt(this.request.getParameter("page"));
			List list = this.manageCashService
					.getPayInterfaceList(EnumTroubleFund.ZERO.getValue());
			this.request.setAttribute("list", list);
		} else {
			paymentInterface = getParameter(paymentInterface);

			this.manageCashService.PaymemtInterface(paymentInterface, idString);
			message("操作成功！", "/admin/cash/payInterface.html?type=list");
			return ADMINMSG;
		}
		this.request.setAttribute("actiontype", actionType);
		return SUCCESS;
	}

	public PaymentInterface getParameter(PaymentInterface paymentInterface) {
		String name = StringUtils.isNull(this.request.getParameter("name"));
		String is_enable = StringUtils.isNull(this.request.getParameter("is_enable"));
		String merchant_id = StringUtils.isNull(this.request.getParameter("merchant_id"));
		String key = StringUtils.isNull(this.request.getParameter("key"));
		String recharge_fee = StringUtils.isNull(this.request.getParameter(Constant.RECHARGE_FEE));
		String return_url = StringUtils.isNull(this.request.getParameter("return_url"));
		String notice_url = StringUtils.isNull(this.request.getParameter("notice_url"));
		String chartset = StringUtils.isNull(this.request.getParameter("chartset"));
		String interface_Into_account = StringUtils.isNull(this.request.getParameter("interface_Into_account"));
		String interface_value = StringUtils.isNull(this.request.getParameter("interface_value"));

		long id = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("id")));
		paymentInterface.setName(name);
		paymentInterface.setIs_enable(NumberUtils.getLong(is_enable));
		paymentInterface.setMerchant_id(merchant_id);
		paymentInterface.setKey(key);
		paymentInterface.setRecharge_fee(recharge_fee);
		paymentInterface.setReturn_url(return_url);
		paymentInterface.setNotice_url(notice_url);
		paymentInterface.setChartset(chartset);
		paymentInterface.setInterface_Into_account(interface_Into_account);
		paymentInterface.setInterface_value(interface_value);
		paymentInterface.setId(id);
		return paymentInterface;
	}

	public String onlineBank() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("type"));
		String idString = StringUtils.isNull(this.request.getParameter("id"));
		OnlineBank onlineBank = new OnlineBank();
		List payInterfacelist = this.manageCashService
				.getPayInterfaceList(EnumTroubleFund.ZERO.getValue());
		this.request.setAttribute("payInterfacelist", payInterfacelist);
		int id = 0;
		if (!StringUtils.isBlank(idString)) {
			id = Integer.parseInt(idString);
		}
		if (StringUtils.isBlank(actionType)) {
			if (id == 0) {
				return SUCCESS;
			}
			onlineBank = this.manageCashService.getOnlineBank(id);
			this.request.setAttribute("onlineBank", onlineBank);
			return SUCCESS;
		}
		this.request.setAttribute("actiontype", actionType);
		if ("list".equals(actionType)) {
			int page = NumberUtils.getInt(this.request.getParameter("page"));
			this.pageList = this.manageCashService.getOnlineBankList(page);
			setPageAttribute(this.pageList, new SearchParam());
		} else {
			onlineBank = fillOnlineBank(onlineBank);
			moveFile(onlineBank);
			onlineBank.setBank_logo(this.filePath);
			this.manageCashService.OnLineBank(onlineBank, idString);
			message("操作成功！", "/admin/cash/onlineBank.html?type=list");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public OnlineBank fillOnlineBank(OnlineBank onlineBank) {
		String bank_name = StringUtils.isNull(this.request
				.getParameter("bank_name"));
		String bank_value = StringUtils.isNull(this.request
				.getParameter("bank_value"));
		long payment_interface_id = NumberUtils.getLong(StringUtils
				.isNull(this.request.getParameter("payment_interface_id")));
		String bank_logo = StringUtils.isNull(this.request
				.getParameter("bank_logo"));
		long id = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("id")));
		onlineBank.setBank_name(bank_name);
		onlineBank.setBank_value(bank_value);
		onlineBank.setPayment_interface_id(payment_interface_id);
		onlineBank.setBank_logo(bank_logo);
		onlineBank.setId(id);
		return onlineBank;
	}
}