package com.liuyun.site.web.action.member;

import com.alibaba.fastjson.JSON;
import com.eitop.platform.tools.encrypt.MD5Digest;
import com.eitop.platform.tools.encrypt.xStrEncrypt;
import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountBank;
import com.liuyun.site.domain.AccountCash;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.AccountRecharge;
import com.liuyun.site.domain.Huikuan;
import com.liuyun.site.domain.Notice;
import com.liuyun.site.domain.NoticeConfig;
import com.liuyun.site.domain.PaymentInterface;
import com.liuyun.site.domain.User;
import com.liuyun.site.exception.AccountException;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserAccountSummary;
import com.liuyun.site.model.account.AccountCashList;
import com.liuyun.site.model.account.AccountLogList;
import com.liuyun.site.model.account.AccountLogModel;
import com.liuyun.site.model.account.AccountModel;
import com.liuyun.site.model.account.AccountRechargeList;
import com.liuyun.site.model.account.OnlineBankModel;
import com.liuyun.site.payment.AllInPay;
import com.liuyun.site.payment.BaoFooPay;
import com.liuyun.site.payment.ChinaBankPay;
import com.liuyun.site.payment.Dinpay;
import com.liuyun.site.payment.EcpssPay;
import com.liuyun.site.payment.GoPay;
import com.liuyun.site.payment.IPSPay;
import com.liuyun.site.payment.RongPay;
import com.liuyun.site.payment.ShengPay;
import com.liuyun.site.payment.TranGood;
import com.liuyun.site.payment.XsPay;
import com.liuyun.site.payment.YbPay;
import com.liuyun.site.payment.tenpay.TenpayRequestHandler;
import com.liuyun.site.quartz.notice.NoticeJobQueue;
import com.liuyun.site.quartz.notice.Sms;
import com.liuyun.site.service.AccountService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.tool.coder.MD5;
import com.liuyun.site.tool.jxl.ExcelHelper;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class AccountAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(AccountAction.class);
	private AccountService accountService;
	private UserService userService;
	private String valicode;
	private String money;
	private String paypassword;
	private String type;
	private String account;
	private String branch;
	private String bank;
	private String dotime1;
	private String dotime2;
	private int page;
	private String account_type;
	private int token;
	private String status;
	private String huikuan_money;
	private String huikuan_award;
	private String remark;
	private long is_day;

	public String list() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();

		this.request.setAttribute("dotime2", DateUtils.dateStr2(new Date()));
		String dotime1 = DateUtils.dateStr2(new Date(System.currentTimeMillis() - 604800000L));
		this.request.setAttribute("dotime1", dotime1);

		UserAccountSummary summary = this.accountService.getUserAccountSummary(user_id);

		this.request.setAttribute("summary", summary);

		return SUCCESS;
	}

	private void fillSearchParam(SearchParam param) {
		param.setDotime1(this.dotime1);
		param.setDotime2(this.dotime2);
		param.setAccount_type(this.account_type);
		param.setStatus(this.status);
	}

	public String log() throws Exception {
		String type = StringUtils.isNull(this.request.getParameter("type"));
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		SearchParam param = new SearchParam();
		fillSearchParam(param);
		AccountLogList accountLogList = this.accountService.getAccountLogList(user_id, this.page, param);
		double total = this.accountService.getAccountLogTotalMoney(user_id);
		this.request.setAttribute("log", accountLogList.getList());
		this.request.setAttribute("p", accountLogList.getPage());

		this.request.setAttribute("total", Double.valueOf(total));
		this.request.setAttribute("param", param.toMap());
		if (type.isEmpty()) {
			return SUCCESS;
		}
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String downloadFile = "log_" + System.currentTimeMillis() + ".xls";
		String infile = contextPath + "data/export/" + downloadFile;
		String[] names = { "username", "to_username", "typename", "money",
				"total", "use_money", "no_use_money", "collection", "remark",
				"addtime" };
		String[] titles = { "用户名", "交易对方", "交易类型", "操作金额", "账户总额", "可用金额",
				"冻结金额", "待收金额", "备注", "时间" };
		List<AccountLogModel> list = this.accountService.getAccountLogList(user_id, param);
		ExcelHelper.writeExcel(infile, list, AccountLogModel.class, Arrays.asList(names), Arrays.asList(titles));
		export(infile, downloadFile);
		return null;
	}

	public String cash() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		SearchParam param = new SearchParam();
		fillSearchParam(param);
		int start = NumberUtils.getInt(this.request.getParameter("page"));
		AccountCashList cash = this.accountService.getAccountCashList(user_id, start, param);
		UserAccountSummary summary = this.accountService.getUserAccountSummary(user_id);
		this.request.setAttribute("cash", cash.getList());
		this.request.setAttribute("p", cash.getPage());
		this.request.setAttribute("param", param.toMap());
		this.request.setAttribute("summary", summary);
		return SUCCESS;
	}

	public String getAvailableCashMoney() throws Exception {
		long user_id = getSessionUser().getUser_id();
		AccountCash cash = new AccountCash();
		cash.setUser_id(user_id);
		cash.setTotal(this.money);
		AccountModel detailAct = this.accountService.getAccount(user_id);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (detailAct == null) {
			jsonMap.put(MSG, "用户id=" + user_id + "不存在！");
		} else {
			this.accountService.newCash(cash, detailAct, NumberUtils.getDouble(this.money), false);
			jsonMap.put(MSG, SUCCESS);
			jsonMap.put("data", cash);
		}
		printJson(JSON.toJSONString(jsonMap));
		return null;
	}

	public boolean phoneSms() throws Exception {
		User user = (User) this.session.get(Constant.SESSION_USER);
		String valicode = StringUtils.isNull(this.request.getParameter("valicode"));
		String code_number = (String) this.session.get("code_number");
		String errormsg = "";
		String phoneUrl = "/member/identify/phone.html";
		String phone = StringUtils.isNull(user.getPhone());
		if ((phone == null) || (phone.equals(""))) {
			errormsg = "手机号码为空,不能进行验证！";
			return false;
		}
		long date = DateUtils.getTime(new Date());
		long preDate = 0L;
		Object object = this.session.get("nowdate");
		if (object == null) {
			object = "";
		}
		if (!"".equals(object)) {
			preDate = Long.parseLong(object.toString());
		}

		if (!StringUtils.isBlank(code_number)) {
			if (!code_number.equals(valicode)) {
				errormsg = "短信验证码不正确！,请查看是否过期或者输入错误";
				return false;
			}
			return true;
		}

		return false;
	}

	public void json(String returnmessage) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("data", returnmessage);
		try {
			printJson(JSON.toJSONString(jsonMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String mobileaccess() {
		String code_number = code();

		long date = DateUtils.getTime(new Date());
		String returnmessage = "";
		if (this.session.get("nowdate") != null) {
			logger.info("当前系统时间" + date + "上一次获取时间" + this.session.get("nowdate"));
			long preDate = Long.parseLong(this.session.get("nowdate").toString());
			if ((this.session.get("nowdate") != null) && (date - preDate < 60L)) {
				returnmessage = "本次短信验证码已发出，请60秒后重试。如果超过60秒还没有输该验证码，请重新获取";
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("data", returnmessage);
				try {
					printJson(JSON.toJSONString(jsonMap));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

		}

		User user = (User) this.session.get(Constant.SESSION_USER);

		this.session.put("code_number", code_number);
		date = DateUtils.getTime(new Date());
		this.session.put("nowdate", Long.valueOf(date));
		NoticeConfig noticeConfig = Global.getNoticeConfig("phone_code");
		String mobile = this.request.getParameter("mobile");
		Notice s = new Sms();
		if (noticeConfig.getSms() == 1L) {
			s.setReceive_userid(user.getUser_id());
			s.setSend_userid(1L);
			s.setContent("尊敬的" + Global.getValue("webname") + "用户["
					+ user.getUsername() + "]，您于"
					+ DateUtils.dateStr5(s.getAddtime()) + "所获取手机验证码为"
					+ code_number);
			s.setMobile(mobile);
			NoticeJobQueue.NOTICE.offer(s);
		}
		return null;
	}

	public static String code() {
		Set<Integer> set = GetRandomNumber();

		Iterator<Integer> iterator = set.iterator();

		String temp = "";
		while (iterator.hasNext()) {
			temp = temp + iterator.next();
		}

		return temp;
	}

	public static Set<Integer> GetRandomNumber() {
		Set<Integer> set = new HashSet<Integer>();

		Random random = new Random();

		while (set.size() < 4) {
			set.add(Integer.valueOf(random.nextInt(10)));
		}
		return set;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: newcash 
	 * 功能: 前台提现申请 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return    设定文件 
	 * 返回类型: String    返回类型 
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String newcash() {
		boolean isOk = true;
		String checkMsg = "";
		long user_id = getSessionUser().getUser_id();
		User user = this.userService.getUserById(user_id);

		String account = this.request.getParameter("account");
		AccountModel detailAct = null;
		if (StringUtils.isBlank(account))
			detailAct = this.accountService.getAccount(user_id);
		else {
			detailAct = this.accountService.getAccountByBankAccount(user_id, account);
		}

		String errormsg = "";
		if ((detailAct == null) || (StringUtils.isNull(detailAct.getBankaccount()).equals(""))) {
			errormsg = "您的银行账号还没填写，请先<a href=\"bank.html\"><font color='red'><strong>填写</strong></font></a>";
		}
		if ((user.getReal_status() == null) || (!user.getReal_status().equals("1"))) {
			errormsg = "您还未通过实名认证，请先<a href=\"" + this.request.getContextPath() + "/member/identify/realname.html\"><font color='red'><strong>填写</strong></font></a>";
		}
		if ((user.getPhone_status() == null) || (!user.getPhone_status().equals("1"))) {
			errormsg = "您还未通过手机认证，请先<a href=\"" + this.request.getContextPath() + "/member/identify/phone.html\"><font color='red'><strong>填写</strong></font></a>";
		}

		if (!errormsg.equals("")) {
			this.request.setAttribute("account", detailAct);
			this.request.setAttribute("errormsg", errormsg);
			return SUCCESS;
		}
		try {
			boolean result = phoneSms();
			if (!result)
				errormsg = "短信验证码不正确！,请查看是否过期或者输入错误";
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (StringUtils.isNull(this.type).equals("newcash")) {
			errormsg = checkNewCash(user, detailAct);
			if (errormsg.equals("")) {
				AccountCash cash = new AccountCash();
				if (Global.getWebid().equals("wzdai")) {
					String bankAccountString = this.request
							.getParameter("account");
					if (StringUtils.isBlank(bankAccountString)) {
						String[] bank = bankAccountString.split(",");
						String bankAccount = bank[2];
						detailAct = this.accountService.getAccountByBankAccount(user.getUser_id(), bankAccount);
					}
				}
				if (StringUtils.isNull(Global.getWebid()).equals("jsy")) {
					int cash_Num = Global.getInt("cash_Num");
					int cashNum = this.accountService.getAccountCashList(user_id, 1);
					if (cashNum >= cash_Num) {
						message("今日已成功提现" + cash_Num + "次，请明天再来提现", "/member/cash.html");
						return MSG;
					}
				}

				cash.setUser_id(user_id);
				cash.setBank(detailAct.getBank());
				cash.setAccount(detailAct.getBankaccount());
				cash.setBranch(detailAct.getBranch());
				cash.setAddtime("" + new Date().getTime() / 1000L);
				cash.setAddip(getRequestIp());
				cash.setTotal(this.money);
				double moneyd = NumberUtils.getDouble(this.money);
				AccountLog log = new AccountLog(user_id, Constant.CASH_FROST, 1L, getTimeStr(), getRequestIp());

				log.setRemark("提现冻结" + this.money + "元");
				try {
					this.accountService.newCash(cash, detailAct, moneyd, log);
				} catch (AccountException e) {
					isOk = false;
					checkMsg = e.getMessage();
					logger.error(e.getMessage(), e.getCause());
				} catch (Exception e) {
					isOk = false;
					checkMsg = "系统繁忙，提现失败,请稍后再试！";
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				if (!isOk) {
					logger.debug("提现失败！");
					message(checkMsg, "/member/cash.html");
					return MSG;
				}

				String msg = "申请提现成功，请等待管理员审核！<a href=\"cash.html\" >查看提现记录</a>&nbsp;&nbsp;<a href=\"newcash.action\">继续提现</a>";
				this.request.setAttribute(MSG, msg);
			} else {
				this.request.setAttribute("errormsg", errormsg);
			}
		} else {
			saveToken("newcash_token");
		}
		if ((detailAct != null) && (detailAct.getBankaccount() != null)) {
			detailAct.setBankaccount(StringUtils.hideLastChar(detailAct.getBankaccount(), 4));
		}
		UserAccountSummary uas = this.accountService.getUserAccountSummary(user_id);
		SearchParam param = new SearchParam();
		param.setUsername(user.getUsername());
		PageDataList plist = this.accountService.getUserAccountModel(1, param);
		this.request.setAttribute("banklist", plist.getList());
		this.request.setAttribute("uas", uas);
		this.request.setAttribute("account", detailAct);
		this.request.setAttribute("lowest_cash", Global.getValue("lowest_cash"));
		return SUCCESS;
	}

	/**
	 * **************************************************************************************
	 * 方法名: cancelcash 
	 * 功能: 取消提现 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return
	 * 参数: @throws Exception    设定文件 
	 * 返回类型: String    返回类型 
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String cancelcash() throws Exception {
		String checkMsg = "";
		boolean isOk = true;
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		AccountLog log = new AccountLog(getSessionUser().getUser_id(), Constant.CASH_CANCEL, getSessionUser().getUser_id(), getTimeStr(), getRequestIp());
		try {
			this.accountService.cancelCash(id, log);
		} catch (AccountException e) {
			isOk = false;
			checkMsg = e.getMessage();
		} catch (Exception e) {
			isOk = false;
			checkMsg = "系统异常";
		}
		if (isOk)
			message("取消提现成功！", "/member/account/cash.html");
		else {
			message(checkMsg, "/member/account/cash.html");
		}
		return MSG;
	}

	public String bank() {
		String wzlx = this.request.getParameter("wzlx");
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		AccountModel detailAct = this.accountService.getAccount(user_id);

		String typeStr = StringUtils.isNull(this.type);
		if (typeStr.equals("add")) {
			if (detailAct == null) {
				Account act = new Account();
				act.setUser_id(user_id);
				this.accountService.addAccount(act);
			}
			AccountBank actbank = new AccountBank();
			if (StringUtils.isNull(this.branch).equals("")) {
				this.request.setAttribute("errormsg", "开户行名称不能为空");
				message("开户行名称不能为空", "/member/account/bank.html");
				return ERROR;
			}
			if (StringUtils.isNull(this.account).equals("")) {
				this.request.setAttribute("errormsg", "银行账号不能为空");
				message("银行账号不能为空", "/member/account/bank.html");
				return ERROR;
			}
			if (StringUtils.isNull(wzlx).equals("")) {
				actbank.setAccount(this.account);
			} else if (StringUtils.isNull(wzlx).equals("hxdai")) {
				String d = "";
				String a = "";
				int j = this.account.length();
				int k = j / 4 * 4;
				for (int i = 0; i < k; ++i) {
					if (i % 4 == 0) {
						if (j > i + 4) {
							d = this.account.substring(i, i + 4);
						}
						a = a + d + " ";
					}
				}

				if (j - k == 3) {
					String e = this.account.substring(k, j);
					a = a + e;
				} else if (j - k == 2) {
					String e = this.account.substring(k, j);
					a = a + e;
				} else if (j - k == 1) {
					String e = this.account.substring(k, j);
					a = a + e;
				}
				logger.debug("a=====" + a);
				actbank.setAccount(a);
			}
			actbank.setBranch(this.branch);
			actbank.setUser_id(user_id);
			actbank.setBank(this.bank);
			if ((detailAct.getBank() == null) || (detailAct.getBank().equals(""))) {
				this.accountService.addBank(actbank);
				message("修改成功！", "/member/account/bank.html");
				return MSG;
			}
			this.accountService.modifyBank(actbank);

			detailAct = this.accountService.getAccount(user_id);
			if (detailAct != null) {
				if (detailAct.getBank() != null) {
					detailAct.setBankaccount(StringUtils.hideLastChar(detailAct.getBankaccount(), 4));
				}
				this.request.setAttribute("act", detailAct);
			}
		} else if (detailAct != null) {
			SearchParam param = new SearchParam();
			param.setUsername(user.getUsername());
			PageDataList plist = this.accountService.getUserAccountModel(1,
					param);
			this.request.setAttribute("banklist", plist.getList());
			if (detailAct.getBank() != null) {
				detailAct.setBankaccount(StringUtils.hideLastChar(detailAct.getBankaccount(), 4));
			}
			this.request.setAttribute("act", detailAct);
		}

		return SUCCESS;
	}

	public String addbank() {
		String wzlx = this.request.getParameter("wzlx");
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		String typeStr = StringUtils.isNull(this.type);
		int countTotal = this.accountService.getAccountBankCount(user_id);
		int defaultTotal = Global.getInt("bank_num");
		if (countTotal >= defaultTotal) {
			this.request.setAttribute("errormsg", "添加银行不能超过最大数");
			message("添加银行不能超过最大数", "/member/account/bank.html");
			return ERROR;
		}
		if ("add".equals(typeStr)) {
			AccountBank actbank = new AccountBank();
			if (StringUtils.isNull(this.branch).equals("")) {
				this.request.setAttribute("errormsg", "开户行名称不能为空");
				message("开户行名称不能为空", "/member/account/bank.html");
				return ERROR;
			}
			if (StringUtils.isNull(this.account).equals("")) {
				this.request.setAttribute("errormsg", "银行账号不能为空");
				message("银行账号不能为空", "/member/account/bank.html");
				return ERROR;
			}
			actbank.setAccount(this.account);
			actbank.setBranch(this.branch);
			actbank.setUser_id(user_id);
			actbank.setBank(this.bank);
			this.accountService.addBank(actbank);
			message("添加成功！", "/member/account/bank.html");
			return MSG;
		}
		return SUCCESS;
	}

	public String recharge() {
		User user = (User) this.session.get(Constant.SESSION_USER);
		long user_id = user.getUser_id();
		SearchParam param = new SearchParam();
		fillSearchParam(param);
		AccountRechargeList accountRechargeList = this.accountService.getRechargeList(user_id, this.page, param);
		UserAccountSummary summary = this.accountService.getUserAccountSummary(user_id);
		double rechargeSum = this.accountService.getRechargesum(param, 1);
		this.request.setAttribute(Constant.RECHARGE, accountRechargeList.getList());

		this.request.setAttribute("p", accountRechargeList.getPage());
		this.request.setAttribute("param", param.toMap());
		this.request.setAttribute("rechargeSum", Double.valueOf(rechargeSum));
		this.request.setAttribute("summary", summary);
		return SUCCESS;
	}

	
	/**
	 * **************************************************************************************
	 * 方法名: newrecharge 
	 * 功能: 用户充值 
	 * 用途: TODO(这里用一句话描述这个方法的作用) 
	 * 参数: @return    设定文件 
	 * 返回类型: String    返回类型 
	 * 异常: 
	 * -------------------------------------------------- 
	 * 修改人：   
	 * 修改时间：Oct 26, 2013 7:38:20 PM   
	 * 修改备注：   
	 * @version 
	 * **************************************************************************************
	 */
	public String newrecharge() {
		
		if (Global.getWebid().equals("jsy")) {
			User user = (User) this.session.get(Constant.SESSION_USER);
			long user_id = user.getUser_id();
			DetailUser detailUser = this.userService.getDetailUser(user_id);

			List<User> list = this.userService.getKfList();
			this.request.setAttribute("kflist", list);
			this.request.setAttribute("detailUser", detailUser);
		}
		
		if (StringUtils.isBlank(this.type)) {
			List<AccountBank> downBankList = this.accountService.downBank();
			List<PaymentInterface> interfaceList = this.accountService.paymentInterface(EnumTroubleFund.FIRST.getValue());
			long interface_id = NumberUtils.getLong(StringUtils.isNull(Global.getValue("interface_id")));
			List<OnlineBankModel> onlineBankList = this.accountService.onlineBank(interface_id);
			if (onlineBankList.size() > 0) {
				this.request.setAttribute("onlineBankList", onlineBankList);
			}
			if (interfaceList.size() > 0) {
				this.request.setAttribute("interfaceList", interfaceList);
			}
			if (downBankList.size() > 0) {
				this.request.setAttribute("downBankList", downBankList);
			}
			return SUCCESS;
		}
		
		String payment = StringUtils.isNull(this.request.getParameter("payment1"));
		String payment2 = StringUtils.isNull(this.request.getParameter("payment2"));
		if ((StringUtils.isNull(this.type).equals("1")) && (StringUtils.isBlank(payment))) {
			message("网上充值的充值方式不能为空！", "");
			return MSG;
		}
		if ((StringUtils.isNull(this.type).equals("2")) && (StringUtils.isBlank(payment2))) {
			message("线下充值的充值银行不能为空！", "");
			return MSG;
		}
		if (NumberUtils.getDouble(this.money) <= 0.0D) {
			message("充值的金额必须大于0！", "");
			return MSG;
		}
		if ((!Global.getWebid().equals("wzdai")) && (!checkValidImg(StringUtils.isNull(this.valicode)))) {
			message("验证码不正确！", "");
			return MSG;
		}
		

		AccountRecharge r = new AccountRecharge();
		User sessionUser = getSessionUser();
		r.setUser_id(sessionUser.getUser_id());
		r.setMoney(NumberUtils.getDouble(this.money));
		r.setType(this.type);
		
		if (Global.getWebid().equals("jsy")) {
			String recharge_kefu_id = this.request.getParameter("recharge_kefuid");
			if (!StringUtils.isBlank(recharge_kefu_id)) {
				r.setRecharge_kefuid(NumberUtils.getLong(recharge_kefu_id));
			} else {
				message("请填写充值专属客服！", "");
				return MSG;
			}
		}
		
		this.request.setAttribute("money", this.money);
		r.setAddtime(getTimeStr());
		r.setAddip(getRequestIp());

		AccountLog log = new AccountLog(sessionUser.getUser_id(), Constant.RECHARGE, sessionUser.getUser_id(), getTimeStr(), getRequestIp());
		double rechargefee = 0.0D;

		
		//网上充值
		if (StringUtils.isNull(this.type).equals("1")) {
			r.setRemark("用户网上充值" + r.getMoney() + "元");
			rechargefee = NumberUtils.getDouble(Global.getValue("online_rechargefee"));
			r.setFee("" + NumberUtils.format2(r.getMoney() * rechargefee));
			r.setStatus(0);
			log.setRemark("用户网上充值" + r.getMoney() + "元");

			r.setPayment(payment);
			
			if (payment.equals("32")) {
				logger.info("用户进入国宝支付...");
				r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "E"));
				gopayment("1", "", r);
			} else if (payment.endsWith("_t")) {
				logger.info("用户进入网银支付...");
				r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "E"));
				r.setPayment("32");
				String backcode = payment.replace("_t", "");
				gopayment("1", backcode, r);
				logger.info(backcode);
			} else {
				if (payment.equals("10")) {
					logger.info("用户进入环讯IPS支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "I"));
					ipspayment("", r);
					this.accountService.addRecharge(r);
					return "ips";
				}
				if (payment.endsWith("_s")) {
					logger.info("用户进入环讯IPS支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "I"));
					r.setPayment("10");
					String backcode = payment.replace("_s", "");
					ipspayment(backcode, r);
					this.accountService.addRecharge(r);
					return "ips";
				}
				if (payment.equals("11")) {
					logger.info("用户进入xspay支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "H"));
					xspayment(payment, r);
					this.accountService.addRecharge(r);
					return "xsp";
				}
				if (payment.equals("12")) {
					logger.info("用户进入ybpay支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "Y"));
					ybpayment(payment, r);
					this.accountService.addRecharge(r);
					return "ybp";
				}
				if (payment.endsWith("_baofoo")) {
					logger.info("用户进入宝付直连...");

					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "B"));

					String bankCode = payment.split("_")[0];
					baoFooPayMent(bankCode, r);
					r.setPayment("13");
					this.accountService.addRecharge(r);
					return "baofoo";
				}
				if ("13".equals(payment)) {
					logger.info("用户进入宝付支付...");

					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "B"));

					String bankCode = "1000";
					baoFooPayMent(bankCode, r);
					this.accountService.addRecharge(r);
					return "baofoo";
				}
				if (payment.equals("57")) {
					logger.info("用户进入中国银行在线支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "W"));
					chinaBankPayment("", r);
					this.accountService.addRecharge(r);
					return "cbpay";
				}
				if ("51".equals(payment)) {
					logger.info("用户进入通联支付...");

					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "T"));
					allInpayment("1", r);
					r.setPayment("51");
					this.accountService.addRecharge(r);
					return "allinpay";
				}
				if ("56".equals(payment)) {
					logger.info("用户进入盛付通支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "S"));
					ShengPayment("", r);
					this.accountService.addRecharge(r);
					return "shengpay";
				}
				if (payment.endsWith("_shengpay")) {
					logger.info("用户进入盛付通直连支付...");
					String bankCode = payment.replace("_shengpay", "");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "S"));
					r.setPayment("56");
					logger.info(r.getTrade_no());
					ShengPayment(bankCode, r);
					this.accountService.addRecharge(r);
					return "shengpay";
				}
				if ("55".equals(payment)) {
					logger.info("用户进入智付支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "D"));

					Dinpayment(r);
					this.accountService.addRecharge(r);
					return "dinpay";
				}
				if ("54".equals(payment)) {
					logger.info("用户进入汇潮支付...");

					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "C"));
					Ecpsspayment("", r);
					this.accountService.addRecharge(r);
					return "ecpssPay";
				}
				if (payment.endsWith("_c")) {
					logger.info("用户进入汇潮直连支付...");
					String bankCode = payment.replace("_c", "");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "C"));
					r.setPayment("54");
					logger.info(r.getTrade_no());
					Ecpsspayment(bankCode, r);
					this.accountService.addRecharge(r);
					return "ecpssPay";
				}
				if ("9".equals(payment)) {
					logger.info("用户进入腾讯财付通支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "Q"));
				}
			}
		}
		
		
		//线下充值
		logger.info("线下充值...");
		
		r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "E"));
		this.accountService.addRecharge(r);
		
		message("充值成功，等待管理审核！", "/member/account/newrecharge.html");
		
		/*
		try {
			//Tenpay("DEFAULT", r);

			message("充值成功，等待管理审核！", "/member/account/newrecharge.html");
			// label1946: label2083:
			return "tenpay";
		} catch (UnsupportedEncodingException ex) {
			// ex.printStackTrace(); break label1946:
			
			String bankCode;
			if (payment.endsWith("_q")) {
				logger.info("用户进入财付通直连支付...");
				bankCode = payment.replace("_q", "");
				r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "Q"));
				r.setPayment("9");
				logger.info(r.getTrade_no());
			}
			
			try {
				Tenpay(bankCode, r);
				this.accountService.addRecharge(r);
				return "tenpay";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				// break label1946:

				if ("14".equals(payment)) {
					logger.info("欢迎进入融宝支付...");
					r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "R"));
				}
				try {
					rongpayment("", r);
					this.accountService.addRecharge(r);
					return "rongpay";
				} catch (UnsupportedEncodingException ex2) {
					ex2.printStackTrace();
					// break label1946:
					// String bankCode;
					if (payment.endsWith("_r")) {
						logger.info("欢迎进入融宝支付...");
						bankCode = payment.replace("_r", "");
						r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "R"));
						r.setPayment("14");
						logger.info(r.getTrade_no());
					}
					try {
						rongpayment(bankCode, r);
						this.accountService.addRecharge(r);
						return "rongpay";
					} catch (UnsupportedEncodingException ex3) {
						ex3.printStackTrace();
						// break label1946:

						if ("58".equals(payment)) {
							logger.info("用户进入汇潮支付快捷支付...");
							r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "A"));
							EcpsspaymentFast("", r);
							this.accountService.addRecharge(r);
							return "ecpssPayFast";
						}

						this.accountService.addRecharge(r);
						// break label2083:
						if (StringUtils.isNull(this.type).equals("2")) {
							r.setRemark(StringUtils.isNull(this.request.getParameter("remark")));
							r.setTrade_no(StringUtils.generateTradeNO(sessionUser.getUser_id(), "E"));
							r.setPayment(payment2);
							r.setStatus(0);
							rechargefee = NumberUtils.getDouble(Global.getValue("online_rechargefee"));
							r.setFee("" + NumberUtils.format2(r.getMoney() * rechargefee));

							this.accountService.addRecharge(r);
						} else {
							message("暂不支持该充值方式！", "");
							return MSG;
						}
						message("充值成功，等待管理审核！", "/member/account/newrecharge.html");
					}
				}
			}
			
		}
		*/
		
		return MSG;
	}

	private void gopayment(String type, String bankCode, AccountRecharge r) {
		TranGood good = createTranGood(r);
		GoPay pay = new GoPay();
		pay.setPrivateKey(Global.getValue("gopay_parivateKey"));
		pay.setMerchantID(Global.getValue("gopay_merchantID"));
		pay.setVirCardNoIn(Global.getValue("gopay_virCardNoIn"));
		pay.setGood(good);
		pay.setTranIP(getRequestIp());
		String weburl = Global.getValue("weburl");
		String callback = weburl + Global.getValue("gopay_callback");
		logger.debug("CallbacK:" + callback);
		pay.setFrontMerUrl(callback);
		pay.setBackgroundMerUrl(callback);
		pay.setUserType(type);
		if (!StringUtils.isBlank(bankCode)) {
			pay.setBankCode(bankCode);
		}
		pay.init();
		String url = pay.submitGet();
		logger.debug("Submit Url:" + url);
		try {
			this.response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void xspayment(String type, AccountRecharge r) {
		XsPay xspay = new XsPay();
		xspay.setOrderID(r.getTrade_no());
		String money = "" + r.getMoney() * 100.0D;

		xspay.setTotalAmount(money);
		xspay.setOrderAmount(money);
		xspay.setGoodsCount("1");
		xspay.setCustomerIP(getRequestIp());
		String weburl = Global.getValue("weburl");
		String returnurl = weburl + Global.getValue("xs_returnurl");
		logger.debug("returnurl:" + returnurl);
		xspay.setReturnUrl(returnurl);
		xspay.setNoticeUrl(returnurl);
		xspay.setType(type);
		xspay.setRemark(r.getRemark());
		xspay.setPartnerID(Global.getValue("partnerID"));
		xspay.setSignType(Global.getValue("signType"));
		xspay.init();
		this.request.setAttribute("xspay", xspay);
		String url = xspay.submitGet();
		logger.debug("Submit Url:" + url);
	}

	private void ybpayment(String type, AccountRecharge r) {
		YbPay ybpay = new YbPay();
		String money = "" + r.getMoney();

		ybpay.setKeyValue(Global.getValue("ybpay_mer_key"));
		ybpay.setP0_Cmd("Buy");
		ybpay.setP1_MerId(Global.getValue("ybpay_merchantID"));
		ybpay.setP2_Order(r.getTrade_no());
		ybpay.setP3_Amt(money);
		ybpay.setP4_Cur("CNY");
		ybpay.setP5_Pid(Global.getValue("webid"));
		ybpay.setP6_Pcat(Global.getValue("webid"));
		ybpay.setP7_Pdesc(r.getRemark());
		ybpay.setP8_Url(Global.getValue("weburl") + Global.getValue("ybpay_callback"));
		ybpay.setP9_SAF("1");
		ybpay.setPa_MP("");
		ybpay.setPd_FrpId("");
		ybpay.setPr_NeedResponse("1");
		ybpay.setNodeAuthorizationURL(YbPay.getInstance().getValue("yeepayCommonReqURL"));

		String hmac = "";
		try {
			hmac = YbPay.getReqMd5HmacForOnlinePayment(ybpay.getP0_Cmd(), ybpay
					.getP1_MerId(), ybpay.getP2_Order(), ybpay.getP3_Amt(),
					ybpay.getP4_Cur(), new String(ybpay.getP5_Pid().getBytes(
							"utf-8"), "gbk"), new String(ybpay.getP6_Pcat()
							.getBytes("utf-8"), "gbk"), new String(ybpay
							.getP7_Pdesc().getBytes("utf-8"), "gbk"), ybpay
							.getP8_Url(), ybpay.getP9_SAF(), ybpay.getPa_MP(),
					ybpay.getPd_FrpId().toUpperCase(), ybpay
							.getPr_NeedResponse(), ybpay.getKeyValue());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ybpay.setHmac(hmac);
		this.request.setAttribute("ybpay", ybpay);
		logger.debug("ybpay=======" + ybpay);
	}

	private void ipspayment(String bankco, AccountRecharge r) {
		String tranAamt = NumberUtils.format2Str(NumberUtils
				.getDouble(this.money));
		String weburl = Global.getValue("weburl");
		IPSPay ips = new IPSPay();
		ips.setMer_code(Global.getValue("ips_mer_code"));
		ips.setMer_key(Global.getValue("ips_mer_key"));
		ips.setBillno(r.getTrade_no());
		ips.setAmount(tranAamt);
		ips.setDate(DateUtils.dateStr(new Date(), "yyyyMMdd"));
		ips.setGateway_Type("01");
		ips.setCurrency_Type("RMB");
		ips.setLang("GB");
		ips.setMerchanturl(weburl + Global.getValue("ips_success_callback"));
		ips.setFailUrl(weburl + Global.getValue("ips_fail_callback"));
		ips.setErrorUrl("");
		ips.setAttach("");
		ips.setOrderEncodeType("5");
		ips.setRetEncodeType("17");
		ips.setRettype("1");
		ips.setServerUrl(weburl + Global.getValue("ips_callback"));
		if (!StringUtils.isBlank(bankco)) {
			ips.setDoCredit("1");
			ips.setBankco(bankco);
		}
		ips.encodeSignMD5();
		logger.info("封装IPSPay对象:" + ips);
		this.request.setAttribute("ips", ips);
	}

	private void rongpayment(String bankco, AccountRecharge r)
			throws UnsupportedEncodingException {
		String tranAamt = NumberUtils.format2Str(NumberUtils.getDouble(this.money));
		String weburl = Global.getValue("weburl");
		RongPay rp = new RongPay();
		rp.init(rp);
		if (!StringUtils.isBlank(bankco)) {
			rp.setPaymethod("directPay");
			rp.setDefaultbank(bankco);
		} else {
			rp.setPaymethod("bankPay");
			rp.setDefaultbank("");
		}
		rp.setBuyer_email("");
		rp.setOrder_no(r.getTrade_no());
		rp.setTotal_fee(tranAamt);
		rp.setSign(rp.getSign(rp));
		this.request.setAttribute("rp", rp);
		logger.info("封装RongPay对象:" + rp);
	}

	private void chinaBankPayment(String bankco, AccountRecharge r) {
		String tranAamt = NumberUtils.format2Str(NumberUtils
				.getDouble(this.money));
		ChinaBankPay cbp = new ChinaBankPay();
		String v_mid = Global.getValue("cbp_mid_code");
		String key = Global.getValue("cbp_key");
		String weburl = Global.getValue("weburl");
		String returnurl = weburl + Global.getValue("cbp_url");
		logger.debug("回调地址：" + returnurl);
		String v_oid = r.getTrade_no();
		cbp.setV_mid(v_mid);
		cbp.setV_url(returnurl);
		if ((v_oid != null) && (!v_oid.equals(""))) {
			cbp.setV_oid(v_oid);
		} else {
			Date currTime = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-"
					+ cbp.getV_mid() + "-hhmmss", Locale.US);
			v_oid = sf.format(currTime);
		}
		cbp.setV_oid(v_oid);
		cbp.setV_amount(tranAamt);
		String v_moneytype = "CNY";
		cbp.setV_moneytype(v_moneytype);
		String v_md5info = tranAamt + v_moneytype + v_oid + v_mid + returnurl
				+ key;
		MD5 md5 = new MD5();
		cbp.setV_md5info(md5.getMD5ofStr(v_md5info));
		cbp.setRemark1(r.getRemark());
		cbp.setRemark2(r.getRemark());
		logger.info("封装ChinaBankPay对象:" + cbp);
		this.request.setAttribute("cbp", cbp);
	}

	private void ShengPayment(String bankCode, AccountRecharge r) {
		String weburl = Global.getValue("weburl");
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String amount = NumberUtils.format2Str(NumberUtils
				.getDouble(this.money));
		ShengPay spay = new ShengPay();
		spay.setName("B2CPayment");
		spay.setVersion("V4.1.1.1.1");
		spay.setCharset("UTF-8");

		spay.setMsgSender(Global.getValue("shengpay_merchantID"));
		spay.setSendTime(df.format(new Date()));
		spay.setOrderNo(r.getTrade_no());
		spay.setOrderAmount(amount);
		spay.setOrderTime(df.format(new Date()));
		spay.setPayType("");
		spay.setInstCode(bankCode);

		spay.setPageUrl(weburl + Global.getValue("shengpay_callback"));
		spay.setNotifyUrl(weburl + Global.getValue("shengpay_notify"));
		spay.setProductName("用户充值" + amount + "元");
		spay.setBuyerContact("");
		spay.setBuyerIp(this.request.getRemoteAddr());
		spay.setExt1("");
		spay.setSignType("MD5");

		String signKey = Global.getValue("shengpay_signKey");
		spay.setSignKey(signKey);
		ShengPay paySignMsg = ShengPay.signFormRequest(spay, signKey);
		spay.setSignMsg(paySignMsg.getSignMsg());
		this.request.setAttribute("spay", spay);
	}

	private void baoFooPayMent(String bankCode, AccountRecharge r) {
		BaoFooPay baoFoo = new BaoFooPay();

		String weburl = Global.getValue("weburl");

		baoFoo.setMerchant_url(weburl + Global.getValue("baofoo_callback"));

		baoFoo.setMerchantID(Global.getValue("baofoo_merchantID"));

		baoFoo.setNoticeType("1");
		String orderMoney;
		if (!"".equals(this.money)) {
			double a = Double.parseDouble(this.money) * 100.0D;
			orderMoney = String.valueOf(a);
		} else {
			orderMoney = "0";
		}
		baoFoo.setOrderMoney(orderMoney);

		baoFoo.setPayID(bankCode);

		baoFoo.setReturn_url(weburl + Global.getValue("baofoo_callback"));

		baoFoo.setTradeDate(DateUtils.dateStr3(new Date()));

		baoFoo.setTransID(r.getTrade_no());

		String Md5key = Global.getValue("baofoo_privateKey");
		String md5Sign = baoFoo.getMerchantID() + baoFoo.getPayID()
				+ baoFoo.getTradeDate() + baoFoo.getTransID()
				+ baoFoo.getOrderMoney() + baoFoo.getMerchant_url()
				+ baoFoo.getReturn_url() + baoFoo.getNoticeType() + Md5key;
		MD5 md5 = new MD5();
		String Md5Sign = md5.getMD5ofStr(md5Sign);
		baoFoo.setMd5Sign(Md5Sign);

		this.request.setAttribute("baoFoo", baoFoo);
	}

	private void allInpayment(String type, AccountRecharge r) {
		String weburl = Global.getValue("weburl");
		AllInPay tpay = new AllInPay();
		tpay.init();
		String payType = this.request.getParameter("payType");
		tpay.setKey(Global.getValue("tl_key"));
		tpay.setPickupUrl(weburl + Global.getValue("tl_returnUrl"));
		tpay.setReceiveUrl(weburl + Global.getValue("tl_returnUrl"));
		tpay.setMerchantId(Global.getValue("tl_merchantId"));
		tpay.setPayerEmail(this.request.getParameter("payerEmail"));
		if ((payType != null) && (payType.equals("3"))) {
			String payerIDCard = this.request.getParameter("payerIDCard");
			String payerName = this.request.getParameter("payerName");
			String payerTelephone = this.request.getParameter("payerTelephone");
			String pan = this.request.getParameter("pan");
			if ((payerIDCard == null) || (payerIDCard.equals(""))
					|| (payerName == null) || (payerName.equals(""))
					|| (payerTelephone == null) || (payerTelephone.equals(""))
					|| (pan == null) || (pan.equals(""))) {
				logger.info("电话支付中 ,支付卡号、支付姓名、和支付电话不能为空");
			} else {
				tpay.setPayerIDCard(payerIDCard);
				tpay.setPayerName(payerName);
				tpay.setPayerTelephone(payerTelephone);
				tpay.setPan(pan);
			}
			tpay.setPayType(payType);
		} else {
			tpay.setPayType("0");
		}
		logger.info("tpay.getPayType()" + tpay.getPayType());
		tpay.setOrderNo(r.getTrade_no());
		tpay.setOrderAmount("" + r.getMoney() * 100.0D);
		tpay.setOrderDatetime(DateUtils.dateStr3(new Date()));
		tpay.setOrderExpireDatetime(this.request
				.getParameter("orderExpireDatetime"));
		tpay.setProductName(Global.getValue("webname"));
		tpay.setProductDesc("用户充值：" + this.money + "RMB");
		tpay.setProductPrice("" + r.getMoney() * 100.0D);
		tpay.setIssuerId(this.request.getParameter("issuerId"));
		String signMsg = tpay.getSignMsg(tpay);
		tpay.setSignMsg(signMsg);
		this.request.setAttribute("tpay", tpay);
	}

	private void Dinpayment(AccountRecharge r) {
		String tranAamt = NumberUtils.format2Str(NumberUtils
				.getDouble(this.money));
		String weburl = Global.getValue("weburl");

		Dinpay dinpay = new Dinpay();

		dinpay.setM_id(Global.getValue("dinpay_merchantID"));
		dinpay.setM_orderID(r.getTrade_no());
		dinpay.setM_amount(tranAamt);
		dinpay.setM_currency("1");
		dinpay.setM_url(weburl + Global.getValue("dinpay_callback"));
		dinpay.setM_language("1");
		dinpay.setM_date(DateUtils.dateStr3(new Date()));
		dinpay.setState("0");

		String m_info = dinpay.getM_id() + "|" + dinpay.getM_orderID() + "|"
				+ dinpay.getM_amount() + "|" + dinpay.getM_currency() + "|"
				+ dinpay.getM_url() + "|" + dinpay.getM_language();
		String s_info = dinpay.getS_name() + "|" + dinpay.getS_address() + "|"
				+ dinpay.getS_postcode() + "|" + dinpay.getS_telephone() + "|"
				+ dinpay.getS_email() + "|" + dinpay.getR_name();
		String r_info = dinpay.getR_address() + "|" + dinpay.getR_postcode()
				+ "|" + dinpay.getR_telephone() + "|" + dinpay.getR_email()
				+ "|" + dinpay.getM_comment() + "|" + dinpay.getState() + "|"
				+ dinpay.getM_date();
		String orderInfo = m_info + "|" + s_info + "|" + r_info;

		System.out.println(orderInfo);

		String key = Global.getValue("dinpay_mer_key");

		orderInfo = xStrEncrypt.StrEncrypt(orderInfo, key);

		dinpay.setOrderInfo(orderInfo);

		dinpay.setMd5Sign(MD5Digest.encrypt(orderInfo + key));

		this.request.setAttribute("dinpay", dinpay);
	}

	private void Ecpsspayment(String bankCode, AccountRecharge r) {
		String returnUrl = Global.getValue("ecpsspay_returnUrl");

		String adviceUrl = Global.getValue("ecpsspay_adviceUrl");

		String md5Key = Global.getValue("ecpsspay_mer_key");

		String merNo = Global.getValue("ecpsspay_merchantID");

		String weburl = Global.getValue("weburl");

		EcpssPay ecpssPay = new EcpssPay();
		ecpssPay.setMd5Key(md5Key);
		ecpssPay.setMerNo(merNo);
		ecpssPay.setBillNo(r.getTrade_no());
		ecpssPay.setAmount(this.money);
		ecpssPay.setReturnUrl(weburl + returnUrl);
		ecpssPay.setAdviceUrl(weburl + adviceUrl);
		ecpssPay.setDefaultBankNumber(bankCode);
		ecpssPay.setOrderTime(DateUtils.dateStr3(new Date()));
		ecpssPay.setProducts("用户充值" + this.money + "RMB");

		this.request.setAttribute("ecpssPay", ecpssPay);
	}

	private void EcpsspaymentFast(String bankCode, AccountRecharge r) {
		String returnUrl = Global.getValue("ecpsspay_fast_returnUrl");

		String adviceUrl = Global.getValue("ecpsspay_fast_adviceUrl");

		String md5Key = Global.getValue("ecpsspay_fast_mer_key");

		String merNo = Global.getValue("ecpsspay_fast_merchantID");

		String weburl = Global.getValue("weburl");

		EcpssPay ecpssPay = new EcpssPay();
		ecpssPay.setMd5Key(md5Key);
		ecpssPay.setMerNo(merNo);
		ecpssPay.setBillNo(r.getTrade_no());
		ecpssPay.setAmount(this.money);
		ecpssPay.setReturnUrl(weburl + returnUrl);
		ecpssPay.setAdviceUrl(weburl + adviceUrl);
		ecpssPay.setDefaultBankNumber(bankCode);
		ecpssPay.setOrderTime(DateUtils.dateStr3(new Date()));
		ecpssPay.setProducts("用户充值" + this.money + "RMB");

		this.request.setAttribute("ecpssPay", ecpssPay);
	}

	/**
	 * 财付通
	 * @param bankCode
	 * @param r
	 * @throws UnsupportedEncodingException
	 */
	private void Tenpay(String bankCode, AccountRecharge r)
			throws UnsupportedEncodingException {
		
		String weburl = Global.getValue("weburl");

		String merNo = Global.getValue("tenpay_merchantID");

		String returnUrl = Global.getValue("tenpay_returnUrl");

		String notifyUrl = Global.getValue("tenpay_notifyUrl");

		String key = Global.getValue("tenpay_key");

		TenpayRequestHandler tenpay = new TenpayRequestHandler(this.request, this.response);
		tenpay.init();
		tenpay.setParameter("partner", merNo);
		tenpay.setKey(key);
		tenpay.setParameter("out_trade_no", r.getTrade_no());

		tenpay.setParameter("total_fee", "" + (Double.valueOf(this.money).doubleValue() * 100.0D));
		tenpay.setParameter("return_url", weburl + returnUrl);
		tenpay.setParameter("notify_url", weburl + notifyUrl);
		tenpay.setParameter("body", "用户充值" + this.money + "元");

		tenpay.setParameter("bank_type", bankCode);

		tenpay.setParameter("spbill_create_ip", this.request.getRemoteAddr());
		tenpay.setParameter("fee_type", "1");

		tenpay.setParameter("input_charset", "UTF-8");
		String debuginfo = tenpay.getDebugInfo();
		logger.info("requestUrl：" + tenpay.getRequestURL());

		this.request.setAttribute("tenpay", tenpay);
	}

	private String checkNewCash(User user, AccountModel detailAct) {
		String errormsg = "";
		String paypwdStr = StringUtils.isNull(this.paypassword);
		String mypaypwd = StringUtils.isNull(user.getPaypassword());
		String site_id = StringUtils.isNull(Global.getValue("webid"));

		UserAccountSummary uas = this.accountService.getUserAccountSummary(user.getUser_id());

		if (paypwdStr.equals("")) {
			errormsg = "请输入支付密码，请先<a href="
					+ this.request.getContextPath()
					+ "'/memberSecurity/paypwd.html'><font color='red'><strong>填写</strong></font></a>";
			return errormsg;
		}
		MD5 md5 = new MD5();
		paypwdStr = md5.getMD5ofStr(paypwdStr);
		if (!mypaypwd.equals(paypwdStr)) {
			errormsg = "支付密码不正确，请先<a href=\""
					+ this.request.getContextPath()
					+ "/memberSecurity/paypwd.html\"><font color='red'><strong>填写</strong></font></a>";
			return errormsg;
		}
		double lowest_cash = NumberUtils.getDouble(Global.getValue("lowest_cash"));
		double cash_money = NumberUtils.getDouble(this.money);
		if (this.money == null) {
			errormsg = "您的提现金额不能为空！";
			return errormsg;
		}
		if (cash_money > NumberUtils.format2(detailAct.getUse_money())) {
			errormsg = "您的提现金额大于你所有的可用余额";
			return errormsg;
		}
		if ((cash_money > uas.getAccountOwnMoney()) && (((site_id.equals("zrzb")) || (site_id.equals("xdcf"))))) {
			errormsg = "您的提现金额大于你的净资产";
			return errormsg;
		}
		if ((Math.floor(cash_money) != cash_money) && (site_id.equals(Constant.HUIZHOUDAI))) {
			errormsg = "提现金额只能为整数！";
			return errormsg;
		}
		if (cash_money < lowest_cash) {
			errormsg = "不能小于最低提现金额";
			return errormsg;
		}
		String tokenMsg = checkToken("newcash_token");
		if (!StringUtils.isBlank(tokenMsg)) {
			return tokenMsg;
		}

		return errormsg;
	}

	private TranGood createTranGood(AccountRecharge r) {
		TranGood good = new TranGood();

		Date d = new Date();
		good.setTranDateTime(DateUtils.dateStr3(d));

		User user = (User) this.session.get(Constant.SESSION_USER);
		good.setMerOrderNum(r.getTrade_no());

		double tranAamt = NumberUtils.format4(NumberUtils.getDouble(this.money));
		double feeAmt = tranAamt * NumberUtils.getDouble(Global.getValue("online_rechargefee"));
		good.setTranAmt("" + NumberUtils.format2(tranAamt));
		if (feeAmt > 0.01D) {
			good.setFeeAmt("" + NumberUtils.format2(feeAmt));
		}

		good.setGoodsName(Global.getValue("webname"));
		good.setGoodsDetail("用户充值：" + this.money + "RMB");
		good.setBuyerName(Global.getValue("webname"));
		good.setBuyerContact(user.getProvince() + user.getCity() + user.getArea());

		return good;
	}

	public String huikuan() {
		String webid = Global.getValue("webid");
		User user = (User) this.session.get(Constant.SESSION_USER);
		this.request.setAttribute(Constant.SESSION_USER, user);
		String huikuan_award = Global.getValue(Constant.HUIKUAN_AWARD);
		this.request.setAttribute(Constant.HUIKUAN_AWARD, huikuan_award);
		this.type = this.request.getParameter("type");
		if ((!StringUtils.isNull(this.type).equals("")) && (this.type.equals("huikuan"))) {
			if ((StringUtils.isNull(webid).equals("zrzb")) && (!StringUtils.isNull(this.huikuan_money).equals(""))) {
				double money = NumberUtils.getDouble(this.huikuan_money);
				if (money < 5000.0D) {
					this.request.setAttribute("errormsg", "回款金额小于5000元,不能进行申请");
					return SUCCESS;
				}
			}

			Huikuan huikuan = new Huikuan();
			huikuan.setUser_id(user.getUser_id());
			huikuan.setHuikuan_money(this.huikuan_money);
			if ((Global.getWebid().equals("zrzb")) || (Global.getWebid().equals("xdcf"))) {
				String huikuan_award_day = Global.getValue("huikuan_award_day");
				String huikuan_award_month = Global.getValue("huikuan_award_month");
				if (this.is_day == 1L)
					huikuan.setHuikuan_award(huikuan_award_day);
				else
					huikuan.setHuikuan_award(huikuan_award_month);
			} else {
				huikuan.setHuikuan_award(huikuan_award);
			}
			huikuan.setRemark(this.remark);
			huikuan.setStatus("0");
			huikuan.setAddtime(getTimeStr());
			this.accountService.huikuan(huikuan);
			this.request.setAttribute(MSG, "申请回款成功");
		}

		return SUCCESS;
	}

	public String hongBaoList() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String account_type = "hongbao";
		String username = getSessionUser().getUsername();
		SearchParam param = new SearchParam();
		param.setAccount_type(account_type);
		param.setUsername(username);
		PageDataList plist = this.accountService.getHongBaoList(page, param);
		this.request.setAttribute("list", plist.getList());
		this.request.setAttribute("p", plist.getPage());
		this.request.setAttribute("param", param.toMap());
		setMsgUrl("/member/account/hongbao.html");
		return SUCCESS;
	}

	public long getIs_day() {
		return this.is_day;
	}

	public void setIs_day(long is_day) {
		this.is_day = is_day;
	}

	public String getHuikuan_money() {
		return this.huikuan_money;
	}

	public void setHuikuan_money(String huikuan_money) {
		this.huikuan_money = huikuan_money;
	}

	public String getHuikuan_award() {
		return this.huikuan_award;
	}

	public void setHuikuan_award(String huikuan_award) {
		this.huikuan_award = huikuan_award;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getToken() {
		return this.token;
	}

	public void setToken(int token) {
		this.token = token;
	}

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

	public String getValicode() {
		return this.valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPaypassword() {
		return this.paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getDotime1() {
		return this.dotime1;
	}

	public void setDotime1(String dotime1) {
		this.dotime1 = dotime1;
	}

	public String getDotime2() {
		return this.dotime2;
	}

	public void setDotime2(String dotime2) {
		this.dotime2 = dotime2;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAccount_type() {
		return this.account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
}