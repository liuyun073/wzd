package com.liuyun.site.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.alibaba.fastjson.JSON;
import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.Article;
import com.liuyun.site.domain.AutoTenderOrder;
import com.liuyun.site.domain.CreditCard;
import com.liuyun.site.domain.LotteryRule;
import com.liuyun.site.domain.QuickenLoans;
import com.liuyun.site.domain.RunBorrow;
import com.liuyun.site.domain.ScrollPic;
import com.liuyun.site.domain.User;
import com.liuyun.site.model.BorrowTender;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.RankModel;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.service.AutoBorrowService;
import com.liuyun.site.service.BorrowService;
import com.liuyun.site.service.CreditCardService;
import com.liuyun.site.service.LotteryService;
import com.liuyun.site.service.QuickenLoansService;
import com.liuyun.site.service.UserService;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;

public class IndexAction extends BaseAction implements ServletRequestAware {
	private static Logger logger = Logger.getLogger(IndexAction.class);
	private ServletRequest request;
	private List<UserBorrowModel> borrowList;
	private BorrowService borrowService;
	private ArticleService articleService;
	private CreditCardService creditCardService;
	private QuickenLoansService quickenLoansService;
	private AutoBorrowService autoBorrowService;
	private LotteryService lotteryService;
	private BorrowModel model = new BorrowModel();
	private RunBorrow runBorrow;
	private StringBuffer msg = new StringBuffer();
	private UserService userService;

	public LotteryService getLotteryService() {
		return this.lotteryService;
	}

	public void setLotteryService(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	public AutoBorrowService getAutoBorrowService() {
		return this.autoBorrowService;
	}

	public void setAutoBorrowService(AutoBorrowService autoBorrowService) {
		this.autoBorrowService = autoBorrowService;
	}

	public RunBorrow getRunBorrow() {
		return this.runBorrow;
	}

	public void setRunBorrow(RunBorrow runBorrow) {
		this.runBorrow = runBorrow;
	}

	public BorrowModel getModel() {
		return this.model;
	}

	public void setModel(BorrowModel model) {
		this.model = model;
	}

	public String execute() throws Exception {
		long s = System.currentTimeMillis();

		int registerCount = this.userService.userCount();
		this.request.setAttribute("registerCount", Integer.valueOf(registerCount));

		this.borrowList = this.borrowService.getList();
		this.request.setAttribute("borrowList", this.borrowList);

		List<UserBorrowModel> successList = this.borrowService.getSuccessListForIndex("", 10);
		this.request.setAttribute("successList", successList);

		List<BorrowTender> newTenderList = this.borrowService.getNewTenderList();
		this.request.setAttribute("newTenderList", newTenderList);

		if (Global.getWebid().equals("zxjr")) {
			List<UserBorrowModel> recommendList = this.borrowService.getRecommendList();
			this.request.setAttribute("recommendList", recommendList);
		}
		if (Global.getWebid().equals("aidai")) {
			List<User> list = this.userService.getAllUser(2);
			this.request.setAttribute("list", list);
			int size = this.borrowList.size();

			int sumMoney = 0;
			for (int i = 0; i < size; ++i) {
				Integer account = Integer.valueOf(((UserBorrowModel) this.borrowList.get(i)).getAccount());
				sumMoney += account.intValue();
			}
			this.request.setAttribute("sumMoney", Integer.valueOf(sumMoney));

			List<CreditCard> travelCardList = this.creditCardService.getListByType(1);
			this.request.setAttribute("travelCardList", travelCardList);

			List<CreditCard> shoppingList = this.creditCardService.getListByType(2);
			this.request.setAttribute("shoppingList", shoppingList);

			List<CreditCard> femailList = this.creditCardService.getListByType(3);
			this.request.setAttribute("femailList", femailList);

			List<CreditCard> carList = this.creditCardService.getListByType(4);
			this.request.setAttribute("carList", carList);
		}

		Date nowDay = DateUtils.getLastSecIntegralTime(new Date());
		Date lastDay = DateUtils.rollDay(nowDay, -1);
		Date lastWeek = DateUtils.rollDay(nowDay, -7);
		Date lastMon = DateUtils.rollMon(nowDay, -1);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("当前日期:" + df.format(new Date()));
		String monday = StringUtils.getMonday(df.format(new Date())) + " 00:00:00";
		logger.info("星期一：" + monday);

		String sunday = StringUtils.getSunday(df.format(new Date())) + " 23:59:59";
		logger.info("星期天：" + sunday);

		List<RankModel> dayRankList = this.borrowService.getRankListByTime("" + DateUtils.getTime(lastDay), "" + DateUtils.getTime(nowDay));
		this.request.setAttribute("dayRankList", dayRankList);
		List<RankModel> weekRankList = this.borrowService.getRankListByTime("" + DateUtils.getTime(lastWeek), "" + DateUtils.getTime(nowDay));
		this.request.setAttribute("weekRankList", weekRankList);
		List<RankModel> monthRankList = this.borrowService.getRankListByTime("" + DateUtils.getTime(lastMon), "" + DateUtils.getTime(nowDay));
		this.request.setAttribute("monthRankList", monthRankList);
		List<RankModel> totalRankList = this.borrowService.getRankListByTime("0", "" + DateUtils.getTime(nowDay));
		this.request.setAttribute("totalRankList", totalRankList);

		List<RankModel> totalRankListOfMonth = this.borrowService.getRankList();
		this.request.setAttribute("totalRankListOfMonth", totalRankListOfMonth);

		List<RankModel> nowDayRankList = this.borrowService.getRankListByTime("" + DateUtils
				.getIntegralTime().getTime() / 1000L, "" + DateUtils
				.getLastIntegralTime().getTime() / 1000L);

		List<RankModel> nowWeekRankList = this.borrowService.getRankListByTime("" + DateUtils.getTime(monday), "" + DateUtils.getTime(sunday));

		Double weekrankmoney = Double.valueOf(0.0D);
		for (int i = 0; i < nowWeekRankList.size(); ++i) {
			weekrankmoney = Double.valueOf(weekrankmoney.doubleValue() + ((RankModel) nowWeekRankList.get(i)).getTenderMoney());
		}
		this.request.setAttribute("weekrankmoney", weekrankmoney);

		Double dayrankmoney = Double.valueOf(0.0D);
		for (int i = 0; i < dayRankList.size(); ++i) {
			dayrankmoney = Double.valueOf(dayrankmoney.doubleValue() + ((RankModel) nowDayRankList.get(i)).getTenderMoney());
		}
		this.request.setAttribute("dayrankmoney", dayrankmoney);

		int borrowCount = 0;
		borrowCount = this.borrowService.getBorrowCountForSuccess();
		this.request.setAttribute("borrowCount", Integer.valueOf(borrowCount));

		Double totalrankmoney = Double.valueOf(0.0D);
		for (int i = 0; i < totalRankList.size(); ++i) {
			totalrankmoney = Double.valueOf(totalrankmoney.doubleValue() + ((RankModel) totalRankList.get(i)).getTenderMoney());
		}
		this.request.setAttribute("totalrankmoney", totalrankmoney);

		int applyBorrowCount = 0;
		applyBorrowCount = this.borrowService.getApplyBorrowCount();
		this.request.setAttribute("applyBorrowCount", Integer.valueOf(applyBorrowCount));

		Double applyBorrowTotal = Double.valueOf(0.0D);
		applyBorrowTotal = Double.valueOf(this.borrowService.getApplyBorrowTotal());
		this.request.setAttribute("applyBorrowTotal", applyBorrowTotal);

		int page = NumberUtils.getInt(Global.getValue("index_other_num"));
		page = (page > 0) ? page : 5;
		int ggpage = NumberUtils.getInt(Global.getValue("index_gonggao_num"));
		ggpage = (ggpage > 0) ? ggpage : 5;

		List<Article> fbList = this.articleService.getList("132", 0, ggpage);
		this.request.setAttribute("fbList", fbList);
		List<Article> ggList = this.articleService.getList("22", 0, ggpage);
		this.request.setAttribute("ggList", ggList);

		List<Article> ygList = this.articleService.getList("132", 0, ggpage);
		this.request.setAttribute("ygList", ygList);

		List<Article> bdList = this.articleService.getList("59", 0, page);
		this.request.setAttribute("bdList", bdList);

		List<Article> tzznList = this.articleService.getList("123", 0, page);
		this.request.setAttribute("tzznList", tzznList);

		List<Article> gsxwList = this.articleService.getList("127", 0, page);
		this.request.setAttribute("gsxwList", gsxwList);

		List<Article> cjList = this.articleService.getList("98", 0, page);
		this.request.setAttribute("cjList", cjList);

		List<Article> zrcjList = this.articleService.getList("99", 0, ggpage);
		this.request.setAttribute("zrcjList", zrcjList);

		List<Article> ybrzxm = this.articleService.getList("121", 0, page);
		this.request.setAttribute("ybrzxm", ybrzxm);

		List<Article> tsrzxm = this.articleService.getList("122", 0, page);
		this.request.setAttribute("tsrzxm", tsrzxm);

		List<Article> jrzx = this.articleService.getList("126", 0, page);
		this.request.setAttribute("jrzx", jrzx);

		List<Article> wzgz = this.articleService.getList("11", 0, 7);
		this.request.setAttribute("wzgz", wzgz);

		List<ScrollPic> scrollPic = this.articleService.getScrollPicList(0, 7);
		this.request.setAttribute("scrollPic", scrollPic);

		double borrowTotal = this.borrowService.getBorrowTotal();
		this.request.setAttribute("borrowTotal", Double.valueOf(borrowTotal));

		String todayStartTime = "" + DateUtils.getIntegralTime().getTime() / 1000L;
		String todayEndTime = "" + DateUtils.getLastIntegralTime().getTime() / 1000L;
		double todayRepayAccountAndInterest = this.borrowService.getTotalRepayAccountAndInterest(todayStartTime, todayEndTime);
		this.request.setAttribute("todayRepayAccountAndInterest", Double.valueOf(todayRepayAccountAndInterest));

		List<Article> rzxm = this.articleService.getList("128", 0, page);
		this.request.setAttribute("rzxm", rzxm);

		List<AutoTenderOrder> autoTenderOrderList = this.autoBorrowService.getAutoTenderOrderList();
		this.request.setAttribute("autoTenderOrderList", autoTenderOrderList);
		long e = System.currentTimeMillis();
		logger.info("IndexAction Cost Time:" + (e - s));

		this.request.setAttribute(Constant.NORMAL_RATE, Global.getString(Constant.NORMAL_RATE));

		this.request.setAttribute(Constant.OVERDUE_RAGE, Global.getString(Constant.OVERDUE_RAGE));

		this.request.setAttribute(Constant.BAD_RATE, Global.getString(Constant.BAD_RATE));
		return SUCCESS;
	}

	public String lottery() {
		LotteryRule lotteryRule = new LotteryRule();
		List<LotteryRule> list = this.lotteryService.lotteryList();

		int usertype = 0;
		User user = getSessionUser();
		if (user == null) {
			return null;
		}
		String type = StringUtils.isNull(this.request.getParameter("type"));
		String result = StringUtils.isNull(this.request.getParameter("result"));
		String data = this.lotteryService.initLottery(type, result, user, usertype);
		
		try {
			printJson(JSON.toJSONString(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void checkParams() {
		this.model.setPageStart(NumberUtils.getInt(this.request.getParameter("page")));
		if ((this.model.getOrder() < -4) || (this.model.getOrder() > 4))
			this.model.setOrder(0);
		if (this.model.getPageStart() < 1)
			this.model.setPageStart(1);
		if (this.model.getStatus() >= 1)
			return;
		this.model.setStatus(1);
	}

	public String blank() throws Exception {
		this.borrowList = this.borrowService.getList();
		this.request.setAttribute("borrowList", this.borrowList);
		return SUCCESS;
	}

	public String viewCreditCard() throws Exception {
		int cardId = Integer.valueOf(this.request.getParameter("cardId")).intValue();
		CreditCard b = this.creditCardService.getCardById(cardId);
		this.request.setAttribute("creditCard", b);
		if (b == null) {
			message("非法操作！");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CreditCardService getCreditCardService() {
		return this.creditCardService;
	}

	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	public QuickenLoansService getQuickenLoansService() {
		return this.quickenLoansService;
	}

	public void setQuickenLoansService(QuickenLoansService quickenLoansService) {
		this.quickenLoansService = quickenLoansService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getTimeType() {
		return null;
	}

	public String addjk() throws Exception {
		String money = this.request.getParameter("money");
		String username = this.request.getParameter("username");
		String tel = this.request.getParameter("tel");
		String description = this.request.getParameter("description");
		String validcode = this.request.getParameter("validcode");
		if (StringUtils.isNull(money).equals("")) {
			String errormsg = "所需资金不能为空";
			this.request.setAttribute("errormsg", errormsg);
			return FAIL;
		}
		if (StringUtils.isNull(username).equals("")) {
			String errormsg = "称呼不能为空";
			this.request.setAttribute("errormsg", errormsg);
			return FAIL;
		}
		if (StringUtils.isNull(tel).equals("")) {
			String errormsg = "联系方式不能为空";
			this.request.setAttribute("errormsg", errormsg);
			return FAIL;
		}
		if (StringUtils.isNull(description).equals("")) {
			String errormsg = "说明不能为空";
			this.request.setAttribute("errormsg", errormsg);
			return FAIL;
		}

		if (StringUtils.isNull(validcode).equals("")) {
			this.request.setAttribute("errormsg", "验证码不能为空！");
		}

		this.runBorrow = new RunBorrow();
		this.runBorrow.setMoney(money);
		this.runBorrow.setUsername(username);
		this.runBorrow.setDescription(description);
		this.runBorrow.setTel(tel);
		this.borrowService.addJk(this.runBorrow);
		message("添加成功");
		return SUCCESS;
	}

	public String realTimeFinancial() throws Exception {
		String startTime = "" + DateUtils.getIntegralTime().getTime() / 1000L;
		String endTime = "" + DateUtils.getLastIntegralTime().getTime() / 1000L;

		Advanced advanced = new Advanced();
		advanced = this.borrowService.borrowAccountSum(startTime, endTime);
		this.request.setAttribute("advanced", advanced);
		String type = StringUtils.isNull(this.request.getParameter("type"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		if (StringUtils.isBlank(type)) {
			type = "wait_repay";
		}
		SearchParam param = new SearchParam();
		PageDataList list = this.borrowService.getBorrowList(type, page, param);
		this.request.setAttribute("borrowList", list.getList());
		this.request.setAttribute("type", type);
		this.request.setAttribute("p", list.getPage());
		this.request.setAttribute("param", param.toMap());
		return SUCCESS;
	}

	public String addQuickenLoans() throws Exception {
		String name = this.request.getParameter("name");
		String phone = this.request.getParameter("phone");
		String area = this.request.getParameter("area");
		String remark = this.request.getParameter("remark");
		QuickenLoans quickenLoans = new QuickenLoans();
		quickenLoans.setName(name);
		quickenLoans.setPhone(phone);
		quickenLoans.setArea(area);
		quickenLoans.setRemark(remark);
		quickenLoans.setCreateTime(getTimeStr());
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (actionType.equals("add")) {
			this.quickenLoansService.addQuickenLoans(quickenLoans);
			message("添加成功，等待客服确认！", "");
		}

		return SUCCESS;
	}

	public String moreRank() throws Exception {
		Date nowDay = DateUtils.getLastSecIntegralTime(new Date());
		Date lastDay = DateUtils.rollDay(nowDay, -1);
		Date lastWeek = DateUtils.rollDay(nowDay, -7);
		Date lastMon = DateUtils.rollMon(nowDay, -1);

		int rankNum = NumberUtils.getInt(this.request.getParameter("rankNum"));
		List dayMoreRankList = this.borrowService.getMoreRankListByTime(
				"" + DateUtils.getTime(lastDay), "" + DateUtils.getTime(nowDay), rankNum);
		this.request.setAttribute("dayMoreRankList", dayMoreRankList);
		List weekMoreRankList = this.borrowService
				.getMoreRankListByTime("" + DateUtils.getTime(lastWeek), "" + DateUtils
						.getTime(nowDay), rankNum);
		this.request.setAttribute("weekMoreRankList", weekMoreRankList);
		List monthMoreRankList = this.borrowService.getMoreRankListByTime(
				"" + DateUtils.getTime(lastMon), "" + DateUtils.getTime(nowDay), rankNum);
		this.request.setAttribute("monthMoreRankList", monthMoreRankList);
		List totalMoreRankList = this.borrowService.getMoreRankListByTime("0",
				"" + DateUtils.getTime(nowDay), rankNum);
		this.request.setAttribute("totalMoreRankList", totalMoreRankList);
		return SUCCESS;
	}
}