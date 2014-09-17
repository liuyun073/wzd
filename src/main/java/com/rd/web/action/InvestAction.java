package com.rd.web.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.Account;
import com.rd.domain.Attestation;
import com.rd.domain.Borrow;
import com.rd.domain.Friend;
import com.rd.domain.Repayment;
import com.rd.domain.User;
import com.rd.domain.Userinfo;
import com.rd.model.BorrowTender;
import com.rd.model.DetailUser;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UnionSearchParam;
import com.rd.model.UserAccountSummary;
import com.rd.model.UserBorrowModel;
import com.rd.model.account.AccountModel;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.AccountService;
import com.rd.service.BorrowService;
import com.rd.service.CommentService;
import com.rd.service.FriendService;
import com.rd.service.MemberBorrowService;
import com.rd.service.UserService;
import com.rd.service.UserinfoService;
import com.rd.tool.Page;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class InvestAction extends BaseAction implements ModelDriven<BorrowModel> {
	private static Logger logger = Logger.getLogger(InvestAction.class);
	
	private BorrowService borrowService;
	private UserService userService;
	private UserinfoService userinfoService;
	private CommentService commentService;
	private AccountService accountService;
	private MemberBorrowService memberBorrowService;
	private FriendService friendService;
	private PageDataList borrowList;
	BorrowModel model = new BorrowModel();

	public FriendService getFriendService() {
		return this.friendService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public BorrowModel getModel() {
		return this.model;
	}

	public String index() throws Exception {
		String webid = Global.getValue("webid");
		int pageNum = this.model.getPageNum();
		if (pageNum < 1) {
			this.model.setPageNum(Page.ROWS);
		}
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		if ((webid != null) && (webid.equals("mdw"))) {
			List<UserBorrowModel> borrowList1 = this.borrowService.getList();
			this.request.setAttribute("borrowList1", borrowList1);
		} else {
			checkParams();
			BorrowModel wrapModel = BorrowHelper.getHelper(Constant.TYPE_ALL, this.model);
			this.model.setParam(getParam());

			this.borrowList = this.borrowService.getList(wrapModel);
			this.request.setAttribute("borrowList", this.borrowList.getList());

			List<UserBorrowModel> successList = this.borrowService.getSuccessListForIndex("", 5);
			this.request.setAttribute("successList", successList);
			this.session.put("newborrowlist", this.borrowList.getList());
			this.request.setAttribute("p", this.borrowList.getPage());

			this.request.setAttribute("orderstr", wrapModel.getModel().getOrderStr());
			logger.info(wrapModel.getModel().getOrderStr());

			this.request.setAttribute("map", wrapModel.getModel().getParam().toMap());
		}
		this.request.setAttribute("searchStatus", Integer.valueOf(status));
		return SUCCESS;
	}

	private SearchParam getParam() {
		String search = StringUtils.isNull(this.request.getParameter("search"));
		String sBorrowType = StringUtils.isNull(this.request.getParameter("sType"));
		String sLilv = StringUtils.isNull(this.request.getParameter("sApr"));
		String sLimit = StringUtils.isNull(this.request.getParameter("sLimit"));
		String sAccount = StringUtils.isNull(this.request.getParameter("sAccount"));
		String sOrder = StringUtils.isNull(this.request.getParameter("order"));
		String sUse = StringUtils.isNull(this.request.getParameter("use"));
		String sKeywords = StringUtils.isNull(this.request.getParameter("keywords"));
		if (Global.getWebid().equals("wzdai")) {
			search = (StringUtils.isBlank(search)) ? "select" : search;
		}
		if (search.equals("union")) {
			UnionSearchParam param = new UnionSearchParam(sBorrowType, sLilv, sLimit, sAccount, sOrder);
			String webid = Global.getValue("webid");
			if (StringUtils.isNull(webid).equals("zrzb")){
				param.setOrder(11);
			}
			else if (StringUtils.isNull(webid).equals("hndai")) {
				if ((sOrder != null) && (!"".equals(sOrder)))
					param.setOrder(Integer.parseInt(sOrder));
				else
					param.setOrder(0);
			} else {
				param.setOrder(Constant.ORDER_BORROW_ADDTIME_DOWN);
			}
			param.setUse(sUse);
			param.setSearch(search);
			param.setKeywords(sKeywords);
			return param;
		}
		if (search.equals("select")) {
			String BorrowType = StringUtils.isNull(this.request.getParameter("type"));
			String Limit = StringUtils.isNull(this.request.getParameter("time_limit"));
			String borrow_style = StringUtils.isNull(this.request.getParameter("borrow_style"));
			String is_award = StringUtils.isNull(this.request.getParameter("is_award"));
			String account_min = StringUtils.isNull(this.request.getParameter("account_min"));
			String account_max = StringUtils.isNull(this.request.getParameter("account_max"));
			if ((Global.getWebid().equals("lhcf")) || (Global.getWebid().equals("hndai"))) {
				UnionSearchParam param = new UnionSearchParam(sBorrowType, sLilv, sLimit, sAccount, sOrder);
				param.setKeywords(sKeywords);
				if (StringUtils.isBlank(sOrder)) {
					param.setOrder(Constant.ORDER_BORROW_ADDTIME_DOWN);
				} else {
					int order = NumberUtils.getInt(sOrder);
					param.setOrder(order);
				}
				return param;
			}
			SearchParam param = new SearchParam(sUse, Limit, sKeywords);
			param.setType(BorrowType);
			param.setBorrow_style(borrow_style);
			param.setIs_award(is_award);
			param.setAccount_min(account_min);
			param.setAccount_max(account_max);
			if (StringUtils.isBlank(sOrder)) {
				if (Global.getWebid().equals("wzdai")) {
					param.setOrder(Constant.ORDER_ACCOUNT_DOWN);
				} else if (StringUtils.isBlank(sOrder)) {
					param.setOrder(Constant.ORDER_BORROW_ADDTIME_DOWN);
				} else {
					int order = NumberUtils.getInt(sOrder);
					param.setOrder(order);
				}
			} else {
				int order = NumberUtils.getInt(sOrder);
				param.setOrder(order);
			}
			return param;
		}
		SearchParam param = new SearchParam(sUse, sLimit, sKeywords);
		param.setType(sBorrowType);
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("zrzb")) {
			param.setOrder(11);
		} else if ((sOrder != null) && (!"".equals(sOrder)))
			param.setOrder(Integer.parseInt(sOrder));
		else {
			param.setOrder(0);
		}

		return param;
	}

	public String detail() throws Exception {
		Borrow b = this.borrowService.getBorrow(this.model.getBorrowid());
		if (b == null) {
			return ERROR;
		}

		DetailUser u = this.userService.getDetailUser(b.getUser_id());
		Userinfo info = this.userinfoService.getUserinfoByUserid(b.getUser_id());

		if (Global.getInt("borrow_ajaxTenderListIsOn") != 1) {
			List<BorrowTender> tenderList = this.borrowService.getTenderList(b.getId());
			this.request.setAttribute("tenderlist", tenderList);
		}
		List<Attestation> attestationList = this.userinfoService.getAttestationListByUserid(b.getUser_id(), 1);
		Map<String, Object> map = this.commentService.getCommentListByBorrowid(b.getId());
		User kf = null;
		if (isSession()) {
			User user = (User) this.session.get(Constant.SESSION_USER);
			long user_id = user.getUser_id();
			kf = this.accountService.getKf(user_id);
			this.request.setAttribute("kf", kf);

			AccountModel act = this.accountService.getAccount(getSessionUser().getUser_id());
			this.request.setAttribute("account", act);
			String webid = Global.getValue("webid");
			if (StringUtils.isNull(webid).equals("wzdai")) {
				List<Friend> list = this.friendService.getBlackList(user_id);
				int isBlackFriend = 0;
				if ((b != null) && (b.getUser_id() != 0L)) {
					for (int i = 0; i < list.size(); ++i) {
						if (b.getUser_id() == ((Friend) list.get(i)).getFriends_userid()) {
							isBlackFriend = 1;
							break;
						}
					}
				}
				this.request.setAttribute("isBlackFriend", Integer.valueOf(isBlackFriend));
			}

		}

		AccountModel borrowAccount = this.accountService.getAccount(b.getUser_id());
		UserAccountSummary uas = this.accountService.getUserAccountSummary(b.getUser_id());
		List<UserBorrowModel> repament_scuess = this.memberBorrowService.getRepayMentList("repaymentyes", u.getUser_id());
		List<UserBorrowModel> repament_failure = this.memberBorrowService.getRepayMentList("repayment", u.getUser_id());
		List<BorrowModel> borrowFlowList = this.borrowService.getBorrowFlowListByuserId(u.getUser_id());
		List<Repayment> earlyRepaymentList = this.borrowService.getRepaymentList(null, u.getUser_id());
		List<Repayment> lateRepaymentList = this.borrowService.getRepaymentList("lateRepayment", u.getUser_id());
		List<Repayment> overdueRepaymentList = this.borrowService.getRepaymentList("overdueRepayment", u.getUser_id());
		List<Repayment> overdueRepaymentsList = this.borrowService.getRepaymentList("overdueRepayments", u.getUser_id());
		List<Repayment> overdueNoRepaymentsList = this.borrowService.getRepaymentList("overdueNoRepayments", u.getUser_id());

		this.request.setAttribute("repament_scuess", Integer.valueOf(repament_scuess.size()));
		this.request.setAttribute("repament_failure", Integer.valueOf(repament_failure.size()));
		this.request.setAttribute("borrowFlowList", Integer.valueOf(borrowFlowList.size()));
		this.request.setAttribute("earlyRepaymentList", Integer.valueOf(earlyRepaymentList.size()));
		this.request.setAttribute("lateRepaymentList", Integer.valueOf(lateRepaymentList.size()));
		this.request.setAttribute("overdueRepaymentList", Integer.valueOf(overdueRepaymentList.size()));
		this.request.setAttribute("overdueRepaymentsList", Integer.valueOf(overdueRepaymentsList.size()));
		this.request.setAttribute("overdueNoRepaymentsList", Integer.valueOf(overdueNoRepaymentsList.size()));

		List<Repayment> waitRepayList = this.memberBorrowService.getRepaymentList(b.getUser_id());
		this.request.setAttribute("summary", uas);
		this.request.setAttribute("borrow", b);
		this.request.setAttribute("user", u);
		this.request.setAttribute("borrowAccount", borrowAccount);
		this.request.setAttribute("info", info);
		this.request.setAttribute("commentlist", map.get("List"));
		this.request.setAttribute("commentCount", map.get("count"));
		this.request.setAttribute("attestations", attestationList);
		this.request.setAttribute("waitRepayList", waitRepayList);
		this.request.setAttribute("nid", Constant.INVEST);
		saveToken("borrow_token");
		return SUCCESS;
	}

	private void checkParams() {
		if ((Global.getWebid().equals("wzdai")) || (Global.getWebid().equals("jlct"))) {
			String status = this.request.getParameter("status");

			this.model.setStatus(NumberUtils.getInt(status));
		}

		this.model.setPageStart(NumberUtils.getInt(this.request.getParameter("page")));
		if ((this.model.getOrder() < -4) || (this.model.getOrder() > 4)) {
			this.model.setOrder(0);
		}
		if (this.model.getPageStart() < 1) {
			this.model.setPageStart(1);
		}
		if (this.model.getStatus() < 1)
			this.model.setStatus(1);
	}

	public String detailTenderForJson() throws Exception {
		int page = paramInt("page");
		int order = paramInt("order");
		SearchParam param = new SearchParam();
		if (order == 22) {
			order = 21;
			param.setOrder(order);
		}
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("xdcf")) {
			param.setOrder(-21);
		}
		Borrow b = this.borrowService.getBorrow(this.model.getBorrowid());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (b == null) {
			jsonMap.put(MSG, ERROR);
		} else {
			PageDataList list;
			if (StringUtils.isNull(webid).equals("xdcf"))
				list = this.borrowService.getTenderList(this.model.getBorrowid(), page, param);
			else {
				list = this.borrowService.getTenderList(this.model.getBorrowid(), page, new SearchParam());
			}
			jsonMap.put(MSG, SUCCESS);
			jsonMap.put("data", list);
		}
		printJson(JSON.toJSONString(jsonMap));
		return null;
	}

	public String detailForJson() throws Exception {
		Borrow b = this.borrowService.getBorrow(this.model.getBorrowid());

		Account account = this.accountService.getAccount(1L);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		logger.debug("account=====" + account);
		logger.debug("borrow=====" + b);

		if (this.model.getBorrowid() != 0L) {
			b.setLitpic("0");
			b.setFlag("0");
			b.setVouch_award("0");
			b.setVouch_user("0");
			b.setVouch_account("0");
			b.setSource("0");
			b.setPublish("0");
			b.setCustomer("0");
			b.setNumber_id("0");
			b.setVerify_user("0");
			b.setVerify_remark("0");
			b.setMonthly_repayment("0");
			b.setRepayment_time("0");
			b.setRepayment_remark("0");
			b.setSuccess_time("0");
			b.setEach_time("0");
			b.setEnd_time("0");
			b.setIs_false("0");
			b.setPwd("0");
			b.setBorrow_time("0");
			b.setBorrow_account("0");
			b.setOpen_credit("0");
			b.setOpen_account("0");
			b.setOpen_borrow("0");
			b.setOpen_tender("0");
			b.setPayment_account("0");
			b.setBorrow_time_limit("0");
			b.setContent("0");
			b.setAddip("0");
			b.setRepayment_account("0");
			logger.debug("borrow=====" + b);
			jsonMap.put("data", b);
		} else {
			jsonMap.put("data", account);
		}
		printJson(JSON.toJSONString(jsonMap));

		return null;
	}

	public String objectToJson(Borrow b) {
		
//		JsonConfig config = new JsonConfig();
//
//		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//
//		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
//			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date d = (Date) arg1;
//				return sdf.format(d);
//			}
//
//			public Object processArrayValue(Object arg0, JsonConfig arg1) {
//				return null;
//			}
//		});
//		String s = JSONObject.fromObject(b, config).toString();
		
		String s = JSON.toJSONString(b);
		
		System.out.println(s);
		return s;
	}

	public static String toJSON(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			try {
				field.setAccessible(true);
				Object o = field.get(obj);
				if (name != null) {
					map.put("\"" + name + "\"", o);
					System.out.println(name + "==========" + o);
				} else {
					map.put("\"" + name + "\"", "");
				}
			} catch (IllegalArgumentException localIllegalArgumentException) {
			} catch (IllegalAccessException localIllegalAccessException) {
			}
		}
		String s = map.toString();
		String str = s.replaceAll("\"=", "\":");
		return str;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public UserinfoService getUserinfoService() {
		return this.userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public CommentService getCommentService() {
		return this.commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public MemberBorrowService getMemberBorrowService() {
		return this.memberBorrowService;
	}

	public void setMemberBorrowService(MemberBorrowService memberBorrowService) {
		this.memberBorrowService = memberBorrowService;
	}
}