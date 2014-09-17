package com.rd.model.borrow;

import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.Borrow;
import com.rd.domain.BorrowConfig;
import com.rd.domain.Repayment;
import com.rd.domain.User;
import com.rd.exception.BorrowException;
import com.rd.model.SearchParam;
import com.rd.tool.Page;
import com.rd.tool.interest.InterestCalculator;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.util.Calendar;
import java.util.Date;

public class BorrowModel extends Borrow implements SqlQueryable {
	private static final long serialVersionUID = 6227166783859660460L;
	private String username;
	private double money;
	private String paypassword;
	private SearchParam param;
	private String searchkeywords;
	private int pageStart;
	private int pageNum = Page.ROWS;
	private Page pager;
	private long borrowid;
	protected int borrowType = Constant.TYPE_ALL;
	private BorrowConfig config;
	private double borrow_fee;
	private boolean isNeedBorrowFee;

	public double getMoney() {
		return this.money;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getPaypassword() {
		return this.paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public SearchParam getParam() {
		return this.param;
	}

	public void setParam(SearchParam param) {
		this.param = param;
	}

	public String getSearchkeywords() {
		return this.searchkeywords;
	}

	public void setSearchkeywords(String searchkeywords) {
		this.searchkeywords = searchkeywords;
	}

	public int getPageStart() {
		return this.pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Page getPager() {
		return this.pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}

	public long getBorrowid() {
		return this.borrowid;
	}

	public void setBorrowid(long borrowid) {
		this.borrowid = borrowid;
	}

	public int getBorrowType() {
		return this.borrowType;
	}

	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}

	public BorrowConfig getConfig() {
		return this.config;
	}

	public void setConfig(BorrowConfig config) {
		this.config = config;
	}

	public double getBorrow_fee() {
		return this.borrow_fee;
	}

	public void setBorrow_fee(double borrow_fee) {
		this.borrow_fee = borrow_fee;
	}

	public boolean isNeedBorrowFee() {
		return this.isNeedBorrowFee;
	}

	public void setNeedBorrowFee(boolean isNeedBorrowFee) {
		this.isNeedBorrowFee = isNeedBorrowFee;
	}

	public String getTypeSql() {
		String typeSql = "";
		switch (getBorrowType()) {
		case 100:
			typeSql = "";

			break;
		case 101:
			typeSql = " and p1.is_mb =1 ";
			break;
		case 102:
			typeSql = " and p1.is_xin=1 and p1.is_student<>1 ";
			break;
		case 103:
			typeSql = " and p1.is_fast=1 ";
			break;
		case 104:
			typeSql = " and p1.is_jin=1  ";
			break;
		case 105:
			typeSql = " and p1.is_vouch=1 ";
			break;
		case 106:
			typeSql = " and p1.is_art=1 ";
			break;
		case 107:
			typeSql = " and p1.is_charity=1 ";
			break;
		case 108:
			typeSql = " ";
			break;
		case 109:
			typeSql = " and p1.is_project=1 ";
			break;
		case 110:
			typeSql = " and p1.is_flow=1 ";
			break;
		case 111:
			typeSql = " and p1.is_student=1 ";
			break;
		case 112:
			typeSql = " and p1.is_offvouch=1 ";
			break;
		case 113:
			typeSql = " and p1.is_pledge=1 ";
			break;
		case 114:
			typeSql = " and p1.is_donation=1 and p1.status<>0";
			break;
		default:
			throw new BorrowException("不正确的Borrow类型:" + getBorrowType());
		}
		return typeSql;
	}

	public String getStatusSql() {
		String statusSql = " ";
		switch (getStatus()) {
		case 0:
			statusSql = " ";
			break;
		case 1:
			statusSql = " and  (p1.status=1 and ((p1.account>p1.account_yes+0 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr()
					+ ") or (p1.is_flow=1 and p1.account>p1.account_yes+0) ))";
			break;
		case 2:
			statusSql = " and (p1.status=1 and p1.is_flow!=1 and (p1.account=p1.account_yes+0 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr() + "))";
			break;
		case 3:
			statusSql = " and p1.status=3";
			break;
		case 4:
			statusSql = " and (p1.status=4 or p1.status=49)";
			break;
		case 5:
			statusSql = " and (p1.status=5 or p1.status=59)";
			break;
		case 6:
			statusSql = " and p1.status=6";
			break;
		case 7:
			statusSql = " and p1.status=7";
			break;
		case 8:
			statusSql = " and p1.status=8";
			break;
		case 9:
			statusSql = " and status=1 and p1.account>p1.account_yes and p1.verify_time+link.value*24*60*60 <"
					+ DateUtils.getTime(new Date());
			break;
		case 10:
			statusSql = " and ((p1.status in (3,6,7) and p1.is_flow!=1) or (p1.status in(1,3,6,7) and p1.is_flow=1)) ";
			break;
		case 11:
			statusSql = " and ((((p1.status=1 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr()
					+ ") "
					+ " or (p1.status in (3,6,7,8) and p1.is_mb!=1)) "
					+ "and p1.is_flow!=1 ) or (p1.is_flow=1 and p1.status in (1,3,6,7))) ";
			break;
		case 14:
			statusSql = " and  (p1.status in(1,3,6,7,8) and ((1=1) or (p1.is_flow=1)))";

			break;
		case 12:
			statusSql = " and ((p1.status in (3,6,7,8) and p1.is_flow!=1) or (p1.status in(1,3,6,7) and p1.is_flow=1))";
			break;
		case 13:
			statusSql = " and  (p1.status=1 and ((p1.account>p1.account_yes+0 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr()
					+ ") "
					+ "or (p1.is_flow=1 and p1.account>p1.account_yes+0)))";
			break;
		case 15:
			statusSql = " and  (p1.status=1 and ((p1.account>p1.account_yes+0 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr()
					+ ") "
					+ "or (p1.is_flow=1 and p1.account>p1.account_yes+0)))";
			break;
		case 19:
			//statusSql = " and (p1.status=1 and (p1.account=p1.account_yes+0 and link.value*24*60*60+p1.addtime < " + DateUtils.getNowTimeStr() + "))";
			statusSql = " and (p1.status=1 and (p1.account>p1.account_yes+0 and link.value*24*60*60+p1.addtime < " + DateUtils.getNowTimeStr() + "))";
			break;
		case 20:
			statusSql = " and p1.status=1 and (p1.account_yes+0)=(p1.account+0) and p1.is_flow<>1";
			break;
		case 21:
			statusSql = " and p1.repayment_time";
			break;
		case 22:
			statusSql = " and ((repay.repayment_time<"
					+ DateUtils.getNowTimeStr()
					+ " and repay.repayment_yestime is  null) or (repay.repayment_yestime is not null and repay.repayment_yestime>repay.repayment_time))";

			break;
		case 23:
			statusSql = " and p1.status=1 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr() + " and p1.is_flow!=1  ";
			break;
		case 24:
			statusSql = " and ((( (p1.status in (3,6,7,8) and p1.is_mb!=1)) and p1.is_flow!=1 ) or (p1.is_flow=1 and p1.status in (1,3,6,7))) ";

			break;
		case 25:
			statusSql = " and ((((p1.status=1 and link.value*24*60*60+p1.addtime>"
					+ DateUtils.getNowTimeStr()
					+ ") "
					+ " or (p1.status in (3,6,7,8) and p1.is_mb!=1)) "
					+ "and p1.is_flow!=1 ) or (p1.is_flow=1 and p1.status in (1,3,6,7))) and p1.is_recommend=1";
			break;
		case 16:
		case 17:
		case 18:
		default:
			throw new RuntimeException("不正确的状态:" + getStatus());
		}
		return statusSql;
	}

	public String getOrderSql() {
		return getParam().getOrderSql();
	}

	public String getSearchParamSql() {
		return getParam().getSearchParamSql();
	}

	public String getLimitSql() {
		int borrownum = NumberUtils.getInt(Global.getValue("index_borrownum"));
		int xinborrownum = NumberUtils
				.getInt(Global.getValue("fast_borrownum"));
		if (this.pager == null) {
			if ((Global.getWebid().equals("zhcf")) && (getBorrowType() == 103)) {
				return " limit 0," + xinborrownum;
			}

			return " limit 0," + borrownum + " ";
		}
		return " limit " + getPager().getStart() + "," + getPager().getPernum();
	}

	public String getPageStr(Page p) {
		StringBuffer sb = new StringBuffer();
		int currentPage = p.getCurrentPage();
		int[] dispayPage = new int[5];
		if (p.getPages() < 5) {
			dispayPage = new int[p.getPages()];
			for (int i = 0; i < dispayPage.length; ++i) {
				dispayPage[i] = (i + 1);
			}
		} else if (currentPage < 3) {
			for (int i = 0; i < 5; ++i)
				dispayPage[i] = (i + 1);
		} else if (currentPage > p.getPages() - 2) {
			for (int i = 0; i < 5; ++i)
				dispayPage[i] = (p.getPages() - 4 + i);
		} else {
			for (int i = 0; i < 5; ++i) {
				dispayPage[i] = (currentPage - 2 + i);
			}
		}

		String statusstr = "&status=" + getStatus();
		String searchstr = getSerachStr();
		String orderstr = "&order=" + getOrder();
		String paramStr = statusstr + searchstr + orderstr;

		for (int i = 0; i < dispayPage.length; ++i) {
			if (dispayPage[i] == currentPage)
				sb
						.append(" <span class='this_page'>" + currentPage
								+ "</span>");
			else {
				sb.append(" <a href='index.html?page=" + dispayPage[i]
						+ paramStr + "'>" + dispayPage[i] + "</a>");
			}
		}
		return sb.toString();
	}

	public String getSerachStr() {
		StringBuffer sb = new StringBuffer();
		SearchParam param = getParam();
		if (param != null) {
			if (!StringUtils.isBlank(param.getUse())) {
				sb.append("&use=" + param.getUse());
			}
			if (!StringUtils.isBlank(param.getLimittime())) {
				sb.append("&limittime=" + param.getLimittime());
			}
			if (!StringUtils.isBlank(param.getKeywords()))
				sb.append("&keywords="
						+ StringUtils.isNull(param.getKeywords()));
		} else {
			sb.append("");
		}
		return sb.toString();
	}

	public String getOrderStr() {
		StringBuffer sb = new StringBuffer("");
		sb.append(getCurOrderStr(1, "金额"));
		sb.append(getCurOrderStr(2, "利率"));
		sb.append(getCurOrderStr(3, "进度"));
		sb.append(getCurOrderStr(4, "信用"));
		return sb.toString();
	}

	public String getCurOrderStr(int ordertype, String text) {
		StringBuffer sb = new StringBuffer();
		sb.append("<span><a  class='searchbtn'' href=\"?status=" + getStatus()
				+ "&search=" + getSearch() + "&time_limit=" + getTime_limit()
				+ "&type=" + getType() + "&pageNum=" + getPageNum());
		if (Math.abs(getOrder()) == ordertype) {
			if (getOrder() > 0) {
				sb.append("&order=" + -ordertype + "\">");
				sb.append("<font color=\"#FF0000\">" + text + "↑</font>");
			} else {
				sb.append("&order=" + ordertype + "\">");
				sb.append("<font color=\"#FF0000\">" + text + "↓</font>");
			}
		} else {
			sb.append("&order=" + ordertype + "\">");
			sb.append(text);
		}
		sb.append("</a> </span>");
		return sb.toString();
	}

	public void skipTrial() {
	}

	public void skipReview() {
	}

	public void skipStatus() {
	}

	public void verify(int status, int verifyStatus) {
	}

	public BorrowModel getModel() {
		return null;
	}

	public double calculateInterest() {
		return 0.0D;
	}

	public double calculateInterest(double validAccount) {
		return 0.0D;
	}

	public InterestCalculator interestCalculator() {
		return null;
	}

	public InterestCalculator interestCalculator(double validAccount) {
		return null;
	}

	public double calculateBorrowFee() {
		return 0.0D;
	}

	public double calculateBorrowAward() {
		return 0.0D;
	}

	public Repayment[] getRepayment() {
		return null;
	}

	public Date getRepayTime(int period) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0L);
		return cal.getTime();
	}

	public boolean isNeedRealName() {
		return true;
	}

	public boolean isNeedVIP() {
		return true;
	}

	public boolean isNeedEmail() {
		return true;
	}

	public boolean isNeedPhone() {
		return true;
	}

	public boolean isNeedVideo() {
		return true;
	}

	public boolean isNeedScene() {
		return true;
	}

	public boolean checkIdentify(User u) {
		throw new BorrowException("借款标的配置出错！");
	}

	public boolean checkModelData() {
		throw new BorrowException("借款标的配置出错！");
	}

	public boolean isLastPeriod(int order) {
		return false;
	}

	public double getManageFee() {
		return 0.0D;
	}

	public double getManageFee(double account) {
		return 0.0D;
	}

	public double getTransactionFee() {
		return 0.0D;
	}

	public double calculateAward() {
		return 0.0D;
	}

	public double calculateAward(double account) {
		return 0.0D;
	}

}