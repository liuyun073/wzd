package com.rd.model;

import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchParam {
	private String search = "";
	private String use;
	private String limittime;
	private String keywords;
	private String dotime1;
	private String dotime2;
	private String succtime1;
	private String succtime2;
	private String account_type;
	private String username;
	private String status;
	private String realname;
	private String email;
	private String kefu_username;
	private String invite_username;
	protected String qq;
	private String vip_status;
	private String phone;
	private String typename;
	private String invite_userid;
	private String card_id;
	private String query;
	private String url;
	private String result;
	private String paymentname;
	private String audit_user;
	private String account;
	private String limit;
	private String apr;
	private String trade_no;
	private String payment;
	private String userPhone;
	private String borrow_style;
	private String verify_user;
	private String repayment_time1;
	private String repayment_time2;
	private String user_type;
	private String protocol_type;
	private String gmt_create1;
	private String gmt_create2;
	private String user_typeid;
	private String is_award;
	private String account_min;
	private String account_max;
	private String cashTotal_min;
	private String cashTotal_max;
	private int timelimit_month_first;
	private int timelimit_month_last;
	private int timelimit_day_first;
	private int timelimit_day_last;
	private int apr_first;
	private int apr_last;
	private double award_first;
	private String verify_username;
	private String recharge_kefu_username;
	private int[] statusArray;
	private int order;
	private String type;
	Map<String, Object> map = new HashMap<String, Object>();

	public String getGmt_create1() {
		return this.gmt_create1;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setGmt_create1(String gmt_create1) {
		this.gmt_create1 = gmt_create1;
	}

	public String getGmt_create2() {
		return this.gmt_create2;
	}

	public void setGmt_create2(String gmt_create2) {
		this.gmt_create2 = gmt_create2;
	}

	public String getProtocol_type() {
		return this.protocol_type;
	}

	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}

	public int getTimelimit_month_first() {
		return this.timelimit_month_first;
	}

	public void setTimelimit_month_first(int timelimit_month_first) {
		this.timelimit_month_first = timelimit_month_first;
	}

	public int getTimelimit_month_last() {
		return this.timelimit_month_last;
	}

	public void setTimelimit_month_last(int timelimit_month_last) {
		this.timelimit_month_last = timelimit_month_last;
	}

	public int getTimelimit_day_first() {
		return this.timelimit_day_first;
	}

	public void setTimelimit_day_first(int timelimit_day_first) {
		this.timelimit_day_first = timelimit_day_first;
	}

	public int getTimelimit_day_last() {
		return this.timelimit_day_last;
	}

	public void setTimelimit_day_last(int timelimit_day_last) {
		this.timelimit_day_last = timelimit_day_last;
	}

	public int getApr_first() {
		return this.apr_first;
	}

	public void setApr_first(int apr_first) {
		this.apr_first = apr_first;
	}

	public int getApr_last() {
		return this.apr_last;
	}

	public void setApr_last(int apr_last) {
		this.apr_last = apr_last;
	}

	public double getAward_first() {
		return this.award_first;
	}

	public void setAward_first(double award_first) {
		this.award_first = award_first;
	}

	public String getCashTotal_min() {
		return this.cashTotal_min;
	}

	public void setCashTotal_min(String cashTotal_min) {
		this.cashTotal_min = cashTotal_min;
	}

	public String getCashTotal_max() {
		return this.cashTotal_max;
	}

	public void setCashTotal_max(String cashTotal_max) {
		this.cashTotal_max = cashTotal_max;
	}

	public String getUser_typeid() {
		return this.user_typeid;
	}

	public void setUser_typeid(String user_typeid) {
		this.user_typeid = user_typeid;
	}

	public String getVerify_username() {
		return this.verify_username;
	}

	public void setVerify_username(String verify_username) {
		this.verify_username = verify_username;
	}

	public String getUser_type() {
		return this.user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getRepayment_time1() {
		return this.repayment_time1;
	}

	public void setRepayment_time1(String repayment_time1) {
		this.repayment_time1 = repayment_time1;
	}

	public String getRepayment_time2() {
		return this.repayment_time2;
	}

	public void setRepayment_time2(String repayment_time2) {
		this.repayment_time2 = repayment_time2;
	}

	public String getVerify_user() {
		return this.verify_user;
	}

	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}

	public String getRecharge_kefu_username() {
		return this.recharge_kefu_username;
	}

	public void setRecharge_kefu_username(String recharge_kefu_username) {
		this.recharge_kefu_username = recharge_kefu_username;
	}

	public String getBorrow_style() {
		return this.borrow_style;
	}

	public void setBorrow_style(String borrow_style) {
		this.borrow_style = borrow_style;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLimit() {
		return this.limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getApr() {
		return this.apr;
	}

	public void setApr(String apr) {
		this.apr = apr;
	}

	public String getAudit_user() {
		return this.audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public String getPaymentname() {
		return this.paymentname;
	}

	public void setPaymentname(String paymentname) {
		this.paymentname = paymentname;
	}

	public String getAccount_min() {
		return this.account_min;
	}

	public void setAccount_min(String account_min) {
		this.account_min = account_min;
	}

	public String getAccount_max() {
		return this.account_max;
	}

	public void setAccount_max(String account_max) {
		this.account_max = account_max;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCard_id() {
		return this.card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getInvite_userid() {
		return this.invite_userid;
	}

	public void setInvite_userid(String invite_userid) {
		this.invite_userid = invite_userid;
	}

	public String getInvite_username() {
		return this.invite_username;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getVip_status() {
		return this.vip_status;
	}

	public void setVip_status(String vip_status) {
		this.vip_status = vip_status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrade_no() {
		return this.trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getIs_award() {
		return this.is_award;
	}

	public void setIs_award(String is_award) {
		this.is_award = is_award;
	}

	public SearchParam() {
	}

	public SearchParam(String use, String limittime, String keywords) {
		this.use = use;
		this.limittime = limittime;
		this.keywords = keywords;
	}

	public SearchParam(String use, String limittime, String keywords,
			long user_id) {
		this.use = use;
		this.limittime = limittime;
		this.keywords = keywords;
	}

	public String getKefu_username() {
		return this.kefu_username;
	}

	public void setKefu_username(String kefu_username) {
		this.kefu_username = kefu_username;
	}

	public String getUse() {
		return this.use;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getLimittime() {
		return this.limittime;
	}

	public void setLimittime(String limittime) {
		this.limittime = limittime;
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

	public String getAccount_type() {
		return this.account_type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setInvite_username(String invite_username) {
		this.invite_username = invite_username;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int[] getStatusArray() {
		return this.statusArray;
	}

	public void setStatusArray(int[] statusArray) {
		this.statusArray = statusArray;
	}

	public String getSucctime1() {
		return this.succtime1;
	}

	public void setSucctime1(String succtime1) {
		this.succtime1 = succtime1;
	}

	public String getSucctime2() {
		return this.succtime2;
	}

	public void setSucctime2(String succtime2) {
		this.succtime2 = succtime2;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Map<String, Object> toMap() {
		this.map.put("search", "");
		this.map.put("use", this.use);
		this.map.put("time_limit", this.limittime);
		this.map.put("keywords", this.keywords);
		this.map.put("dotime1", this.dotime1);
		this.map.put("dotime2", this.dotime2);
		this.map.put("succtime1", this.succtime1);
		this.map.put("succtime2", this.succtime2);
		this.map.put("type", this.type);
		this.map.put("trade_no", this.trade_no);
		this.map.put("payment", this.payment);
		this.map.put("order", this.order);
		this.map.put("is_award", this.is_award);
		this.map.put("cashTotal_min", this.cashTotal_min);
		this.map.put("cashTotal_max", this.cashTotal_max);
		if (!StringUtils.isBlank(this.gmt_create1)) {
			this.map.put("gmt_create1", this.gmt_create1);
		}
		if (!StringUtils.isBlank(this.gmt_create2)) {
			this.map.put("gmt_create2", this.gmt_create2);
		}
		if (this.timelimit_month_first != 0) {
			this.map.put("timelimit_month_first", Integer.valueOf(this.timelimit_month_first));
		}
		if (this.timelimit_month_last != 0) {
			this.map.put("timelimit_month_last", Integer.valueOf(this.timelimit_month_last));
		}
		if (this.timelimit_day_first != 0) {
			this.map.put("timelimit_day_first", Integer.valueOf(this.timelimit_day_first));
		}
		if (this.timelimit_day_last != 0) {
			this.map.put("timelimit_day_last", Integer.valueOf(this.timelimit_day_last));
		}
		if (this.apr_first != 0) {
			this.map.put("apr_first", Integer.valueOf(this.apr_first));
		}
		if (this.apr_last != 0) {
			this.map.put("apr_last", Integer.valueOf(this.apr_last));
		}
		if (this.award_first != 0.0D) {
			this.map.put("award_first", Double.valueOf(this.award_first));
		}
		if ((this.account_type != null) && (!this.account_type.equals("0"))) {
			this.map.put("account_type", this.account_type);
		}
		if (this.protocol_type != null) {
			this.map.put("protocol_type", this.protocol_type);
		}
		if (!StringUtils.isBlank(this.borrow_style)) {
			this.map.put("borrow_style", this.borrow_style);
		}
		if ((this.audit_user != null) && (!this.audit_user.equals("0"))) {
			this.map.put("audit_user", this.audit_user);
		}
		if ((this.paymentname != null) && (!this.paymentname.equals("0"))) {
			this.map.put("paymentname", this.paymentname);
		}
		if (!StringUtils.isBlank(this.username)) {
			this.map.put("username", this.username);
		}
		if (!StringUtils.isBlank(this.account)) {
			this.map.put("account", this.account);
		}
		if (!StringUtils.isBlank(this.apr)) {
			this.map.put("apr", this.apr);
		}
		if (!StringUtils.isBlank(this.limit)) {
			this.map.put("limit", this.limit);
		}
		if (!StringUtils.isBlank(this.repayment_time1)) {
			this.map.put("repayment_time1", this.repayment_time1);
		}
		if (!StringUtils.isBlank(this.repayment_time2)) {
			this.map.put("repayment_time2", this.repayment_time2);
		}
		if (!StringUtils.isBlank(this.status)) {
			int statusInt = NumberUtils.getInt(this.status);
			if (statusInt >= 0) {
				this.map.put("status", this.status);
			}
		}
		if (!StringUtils.isBlank(this.vip_status)) {
			int statusInt = NumberUtils.getInt(this.vip_status);
			if (statusInt >= 0) {
				this.map.put("vip_status", this.vip_status);
			}
		}
		if (!StringUtils.isBlank(this.recharge_kefu_username)) {
			this.map.put("recharge_kefu_username", this.recharge_kefu_username);
		}
		if (!StringUtils.isBlank(this.user_type)) {
			this.map.put("user_type", this.user_type);
		}
		if (!StringUtils.isBlank(this.verify_username)) {
			this.map.put("verify_username", this.verify_username);
		}
		this.map.put("realname", this.realname);
		this.map.put("email", this.email);
		this.map.put("kefu_name", this.kefu_username);
		if (!StringUtils.isBlank(this.invite_username)) {
			this.map.put("invite_username", this.invite_username);
		}
		return this.map;
	}

	public void addMap(String key, String value) {
		this.map.put(key, value);
	}

	public String getSearchParamSql() {
		StringBuffer sb = new StringBuffer();
		if (NumberUtils.getInt(getUse()) > 0) {
			sb.append(" and p1.use=" + this.use);
		}
		if (NumberUtils.getInt(this.limittime) > 0) {
			sb.append(" and p1.time_limit=" + this.limittime);
		}
		if (!StringUtils.isBlank(this.audit_user)) {
			sb.append(" and m.sent_user =" + this.audit_user);
		}
		if (!StringUtils.isBlank(this.keywords)) {
			sb.append(" and p1.name like '%" + StringUtils.isNull(this.keywords) + "%'");
		}
		if ((!StringUtils.isBlank(this.borrow_style)) && (!this.borrow_style.equals("0"))) {
			sb.append(" and p1.style=" + this.borrow_style);
		}

		if (!StringUtils.isBlank(this.recharge_kefu_username)) {
			sb.append(" and recharge_kefu.username='" + this.recharge_kefu_username + "'");
		}
		String dotimeStr1 = null;
		String dotimeStr2 = null;
		try {
			dotimeStr1 = Long.toString(DateUtils.valueOf(this.dotime1).getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.dotime2);
			d = DateUtils.rollDay(d, 1);
			dotimeStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr2 = "";
		}
		if (!StringUtils.isBlank(dotimeStr1)) {
			sb.append(" and p1.addtime>" + dotimeStr1 + " ");
		}
		if (!StringUtils.isBlank(dotimeStr2)) {
			sb.append(" and p1.addtime<" + dotimeStr2 + " ");
		}
		String succtimeStr1 = null;
		String succtimeStr2 = null;
		try {
			succtimeStr1 = Long.toString(DateUtils.valueOf(this.succtime1).getTime() / 1000L);
		} catch (Exception e) {
			succtimeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.succtime2);
			d = DateUtils.rollDay(d, 1);
			succtimeStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			succtimeStr2 = "";
		}
		if (!StringUtils.isBlank(succtimeStr1)) {
			sb.append(" and p1.verify_time>" + succtimeStr1);
		}
		if (!StringUtils.isBlank(succtimeStr2)) {
			sb.append(" and p1.verify_time<" + succtimeStr2);
		}
		String gmt_createStr1 = null;
		String gmt_createStr2 = null;
		try {
			gmt_createStr1 = Long.toString(DateUtils.valueOf(this.gmt_create1).getTime() / 1000L);
		} catch (Exception e) {
			succtimeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.gmt_create2);
			d = DateUtils.rollDay(d, 1);
			gmt_createStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			gmt_createStr2 = "";
		}
		if (!StringUtils.isBlank(gmt_createStr1)) {
			sb.append(" and p1.gmt_create>" + gmt_createStr1);
		}
		if (!StringUtils.isBlank(gmt_createStr2)) {
			sb.append(" and p1.gmt_create<" + gmt_createStr2);
		}
		String repayment_timeStr1 = null;
		String repayment_timeStr2 = null;
		try {
			repayment_timeStr1 = Long.toString(DateUtils.valueOf(this.repayment_time1).getTime() / 1000L);
		} catch (Exception e) {
			repayment_timeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.repayment_time2);
			d = DateUtils.rollDay(d, 1);
			repayment_timeStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			repayment_timeStr2 = "";
		}
		if (!StringUtils.isBlank(repayment_timeStr1)) {
			sb.append(" and p3.repayment_time>" + repayment_timeStr1);
		}
		if (!StringUtils.isBlank(repayment_timeStr2)) {
			sb.append(" and p3.repayment_time<" + repayment_timeStr2);
		}
		if ((!StringUtils.isBlank(this.account_type))
				&& (!this.account_type.equals("0"))) {
			sb.append(" and p1.type=").append("'").append(this.account_type).append("'");
		}

		if ((!StringUtils.isBlank(this.paymentname)) && (!this.paymentname.equals("0"))) {
			sb.append(" and p1.payment=").append("'").append(this.paymentname).append("'");
		}
		if (!StringUtils.isBlank(this.username)) {
			sb.append(" and p2.username like '%").append(this.username).append("%'");
		}
		if (!StringUtils.isBlank(this.verify_user)) {
			sb.append(" and p1.verify_user like '%").append(this.verify_user).append("%'");
		}
		if ((this.statusArray != null) && (this.statusArray.length > 1)) {
			sb.append(" and p1.status in (").append(StringUtils.array2Str(this.statusArray)).append(")");
		} else {
			if (!StringUtils.isBlank(this.status)) {
				int statusInt = NumberUtils.getInt(this.status);
				if (statusInt >= 0) {
					sb.append(" and p1.status=").append(statusInt);
				}
			}
			if (!StringUtils.isBlank(this.vip_status)) {
				int statusInt = NumberUtils.getInt(this.vip_status);
				if (statusInt >= 0) {
					sb.append(" and p.vip_status=").append(statusInt);
				}
			}
		}

		if (!StringUtils.isBlank(this.realname)) {
			sb.append(" and p2.realname like '%" + StringUtils.isNull(this.realname) + "%'");
		}
		if (!StringUtils.isBlank(this.email)) {
			sb.append(" and p2.email like '%" + StringUtils.isNull(this.email) + "%'");
		}
		if (!StringUtils.isBlank(this.kefu_username)) {
			sb.append(" and kf.username like '%" + StringUtils.isNull(this.kefu_username) + "%'");
		}

		if (!StringUtils.isBlank(this.invite_username)) {
			sb.append(" and invite.username like '%").append(this.invite_username).append("%'");
		}
		if (!StringUtils.isBlank(this.type)) {
			sb.append(getTypeSql(this.type));
		}
		if (!StringUtils.isBlank(this.account)) {
			sb.append(" and p1.account=" + this.account);
		}
		if (!StringUtils.isBlank(this.apr)) {
			sb.append(" and p1.apr=" + this.apr);
		}
		if (!StringUtils.isBlank(this.limit)) {
			sb.append(" and p1.time_limit=" + this.limit);
		}
		if (!StringUtils.isBlank(this.trade_no)) {
			sb.append(" and p1.trade_no like '%" + StringUtils.isNull(this.trade_no) + "%'");
		}
		if (!StringUtils.isBlank(this.payment)) {
			if (StringUtils.isNull(this.payment).equals("50"))
				sb.append("  and p1.payment = '50'");
			else if (StringUtils.isNull(this.payment).equals("-50"))
				sb.append("  and p1.payment != '50'");
			else {
				sb.append("  and p1.payment = '" + this.payment + "'");
			}
		}
		if (!StringUtils.isBlank(this.user_type)) {
			sb.append(" and p1.type='%" + StringUtils.isNull(this.user_type) + "%'");
		}

		if (!StringUtils.isBlank(this.user_typeid)) {
			sb.append(" and p2.type_id='" + StringUtils.isNull(this.user_typeid) + "'");
		}

		if (!StringUtils.isBlank(this.verify_username)) {
			sb.append(" and p2.username like '%").append(this.verify_username).append("%'");
		}

		if (!StringUtils.isBlank(this.protocol_type)) {
			sb.append("  and p1.protocol_type = '" + this.protocol_type + "'");
		}

		if (!StringUtils.isBlank(this.is_award)) {
			if ((!this.is_award.equals("0")) && (this.is_award.equals("1")))
				sb.append(" and p1.award>0");
			else if ((!this.is_award.equals("0")) && (this.is_award.equals("2"))) {
				sb.append(" and p1.award=0");
			}

		}

		if (!StringUtils.isBlank(this.account_min)) {
			sb.append(" and p1.account>=" + this.account_min);
		}
		if (!StringUtils.isBlank(this.account_max)) {
			sb.append(" and p1.account<=" + this.account_max);
		}
		if (!StringUtils.isBlank(this.cashTotal_min)) {
			sb.append(" and p1.total>=" + this.cashTotal_min);
		}
		if (!StringUtils.isBlank(this.cashTotal_max)) {
			sb.append(" and p1.total<=" + this.cashTotal_max);
		}
		return sb.toString();
	}

	private String getTypeSql(String type) {
		String sql = " ";
		if (type.equals("1"))
			sql = " and p1.is_mb=1 ";
		else if (type.equals("2"))
			sql = " and p1.is_xin=1 ";
		else if (type.equals("3"))
			sql = " and p1.is_fast=1 ";
		else if (type.equals("4"))
			sql = " and p1.is_jin=1 ";
		else if (type.equals("5"))
			sql = " and p1.is_vouch=1 ";
		else if (type.equals("6"))
			sql = " and p1.is_art=1 ";
		else if (type.equals("7"))
			sql = " and p1.is_charity=1 ";
		else if (type.equals("8"))
			sql = "  ";
		else if (type.equals("9"))
			sql = " and p1.is_project=1 ";
		else if (type.equals("10"))
			sql = " and p1.is_flow=1 ";
		else if (type.equals("11"))
			sql = " and p1.is_student=1 ";
		else if (type.equals("12"))
			sql = " and p1.is_offvouch=1 ";
		else if (type.equals("13")) {
			sql = " and p1.is_pledge=1 ";
		}
		return sql;
	}

	public String getUserLogSearchParamSql() {
		StringBuffer sb = new StringBuffer();
		String dotimeStr1 = null;
		String dotimeStr2 = null;
		if (!StringUtils.isBlank(this.query)) {
			sb.append(" and p1.query like '%" + StringUtils.isNull(this.query) + "%'");
		}

		if (!StringUtils.isBlank(this.url)) {
			sb.append(" and p1.url like '%" + StringUtils.isNull(this.url) + "%'");
		}

		if (!StringUtils.isBlank(this.result)) {
			sb.append(" and p1.result like '%" + StringUtils.isNull(this.result) + "%'");
		}
		try {
			dotimeStr1 = Long.toString(DateUtils.valueOf(this.dotime1).getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr1 = "";
		}
		try {
			Date d = DateUtils.valueOf(this.dotime2);
			d = DateUtils.rollDay(d, 1);
			dotimeStr2 = Long.toString(d.getTime() / 1000L);
		} catch (Exception e) {
			dotimeStr2 = "";
		}
		if (!StringUtils.isBlank(dotimeStr1)) {
			sb.append(" and p1.addtime>" + dotimeStr1);
		}
		if (!StringUtils.isBlank(dotimeStr2)) {
			sb.append(" and p1.addtime<" + dotimeStr2);
		}

		if (!StringUtils.isBlank(this.username)) {
			sb.append(" and p2.username like '%").append(this.username).append("%'");
		}

		if (!StringUtils.isBlank(this.realname)) {
			sb.append(" and p2.realname like '%" + StringUtils.isNull(this.realname) + "%'");
		}

		if (!StringUtils.isBlank(this.email)) {
			sb.append(" and p2.email like '%" + StringUtils.isNull(this.email) + "%'");
		}

		if (!StringUtils.isBlank(this.invite_userid)) {
			sb.append("  p2.invite_userid like '%" + StringUtils.isNull(this.invite_userid) + "%'");
		}

		if (!StringUtils.isBlank(this.card_id)) {
			sb.append("  p2.card_id like '%" + StringUtils.isNull(this.card_id) + "%'");
		}

		return sb.toString();
	}

	public String getOrderSql() {
		String orderSql = "";
		switch (this.order) {
		case 0:
			orderSql = " ";
			break;
		case 1:
			orderSql = " order by p1.account+0 asc";
			break;
		case -1:
			orderSql = " order by p1.account+0 desc";
			break;
		case 2:
			orderSql = " order by p1.apr asc";
			break;
		case -2:
			orderSql = " order by p1.apr desc";
			break;
		case 3:
			orderSql = " order by scales asc";
			break;
		case -3:
			orderSql = " order by scales desc";
			break;
		case 4:
			orderSql = " order by p3.value asc";
			break;
		case -4:
			orderSql = " order by p3.value desc";
			break;
		case 5:
			orderSql = " order by p1.addtime ";
			break;
		case -5:
			orderSql = " order by p1.addtime desc";
			break;
		case 6:
			orderSql = " order by p1.is_mb desc,p1.is_flow desc,p1.is_fast desc,p1.is_xin desc,p1.is_jin desc,p1.id desc";
			break;
		case 11:
			orderSql = " order by p1.status,p1.account_yes/p1.account ,p1.addtime desc ";
			break;
		case 21:
			orderSql = " order by t.addtime ";
			break;
		case -21:
			orderSql = " order by p.addtime desc ";
			break;
		case 7:
			orderSql = " order by p1.verify_time ";
			break;
		case -7:
			orderSql = " order by p1.verify_time desc ";
			break;
		default:
			orderSql = " ";
		}
		return orderSql;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
}