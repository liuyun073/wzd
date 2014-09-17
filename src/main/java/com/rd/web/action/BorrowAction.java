package com.rd.web.action;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.AccountRecharge;
import com.rd.domain.Article;
import com.rd.domain.Borrow;
import com.rd.domain.BorrowFlow;
import com.rd.domain.Protocol;
import com.rd.domain.Site;
import com.rd.domain.Tender;
import com.rd.domain.User;
import com.rd.domain.UserAmount;
import com.rd.exception.BorrowException;
import com.rd.model.BorrowTender;
import com.rd.model.DetailUser;
import com.rd.model.SearchParam;
import com.rd.model.UserAccountSummary;
import com.rd.model.account.AccountModel;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.model.borrow.protocol.BorrowProtocol;
import com.rd.service.AccountService;
import com.rd.service.ArticleService;
import com.rd.service.BorrowService;
import com.rd.service.UserAmountService;
import com.rd.service.UserService;
import com.rd.tool.coder.MD5;
import com.rd.tool.interest.InterestCalculator;
import com.rd.tool.itext.PdfHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class BorrowAction extends BaseAction implements
		ModelDriven<BorrowModel> {
	
	private static Logger logger = Logger.getLogger(BorrowAction.class);
	private BorrowService borrowService;
	private UserService userService;
	private AccountService accountService;
	private UserAmountService userAmountService;
	private ArticleService articleService;
	private String webid = Global.getValue("webid");
	private String pwd;
	private BorrowModel borrow = new BorrowModel();

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String index() throws Exception {
		int page = NumberUtils.getInt(Global.getValue("index_other_num"));
		page = (page > 0) ? page : 5;
		int ggpage = NumberUtils.getInt(Global.getValue("index_gonggao_num"));
		ggpage = (ggpage > 0) ? ggpage : 5;
		List<Article> ggList = this.articleService.getList("22", 0, ggpage);
		this.request.setAttribute("ggList", ggList);

		List<Article> bdList = this.articleService.getList("59", 0, page);
		this.request.setAttribute("bdList", bdList);

		List<Article> cjList = this.articleService.getList("11", 0, page);
		this.request.setAttribute("cjList", cjList);
		return "index";
	}

	
	public String loan() throws Exception {
		int borrowType = Constant.TYPE_ALL;
		String typeStr = this.borrow.getType();
		borrowType = Global.getBorrowType(typeStr);
		
		String id = this.request.getParameter("id");
		BorrowModel bm = StringUtils.isBlank(id)? null : this.borrowService.getBorrow(Long.parseLong(id));
		
		if(bm!=null){
			this.request.setAttribute("borrow", bm);
			BorrowModel model = BorrowHelper.getHelper(bm);
			User sessionUser = this.userService.getDetailUser(getSessionUser().getUser_id());
			model.checkIdentify(sessionUser);
			
			typeStr = Global.getBorrowTypeName(model.getBorrowType());
			borrowType = model.getBorrowType();
		}

		this.request.setAttribute("btype", Integer.valueOf(borrowType));
		this.request.setAttribute("type", typeStr);

		List<User> list = new ArrayList<User>();
		list = this.userService.getVerifyUser();
		this.request.setAttribute("VerifyUser", list);

		UserAccountSummary summary = this.accountService.getUserAccountSummary(getSessionUser().getUser_id());
		this.request.setAttribute("summary", summary);
		return "detail";
	}

	
	public String add() throws Exception {
		boolean isOk = true;
		String checkMsg = "";
		BorrowModel model = null;
		User sessionUser = this.userService.getDetailUser(getSessionUser().getUser_id());

		int btype = NumberUtils.getInt(this.request.getParameter("btype"));
		if (!StringUtils.isBlank(this.borrow.getBorrow_time())) {
			this.borrow.setBorrow_time(DateUtils.getTimeStr(this.borrow.getBorrow_time(), "yyyy-MM-dd"));
		}
		model = BorrowHelper.getHelper(btype, this.borrow);
		model.checkIdentify(sessionUser);
		model.checkModelData();
		validate();
		if (btype != Constant.TYPE_DONATION) {
			fillBorrow(model);
		} else {
			if (sessionUser.getType_id() == 3) {
				throw new BorrowException("爱心捐助标必须是由平台所发");
			}
			fillDonation(model);
		}

		AccountLog log = new AccountLog(getSessionUser().getUser_id(),
				Constant.FREEZE, getSessionUser().getUser_id(), getTimeStr(),
				getRequestIp());
		this.borrowService.addBorrow(model, log);
		message("发布成功!", "/member/index.html", "进入用户中心>>");
		return SUCCESS;
	}

	public String delete() throws Exception {
		User user = getSessionUser();
		String id = this.request.getParameter("id");
		BorrowModel borrow = this.borrowService.getBorrow(Long.parseLong(id));
		if ((borrow.getStatus() != 1) && (borrow.getStatus() != 0)) {
			logger.info("借款标的状态不允许撤回:" + borrow.getStatus());
			message("借款标的状态不允许撤回");
			return MSG;
		}
		borrow.setUser_id(user.getUser_id());
		borrow.setStatus(5);
		AccountLog log = new AccountLog(getSessionUser().getUser_id(),
				Constant.UNFREEZE, getSessionUser().getUser_id(), getTimeStr(),
				getRequestIp());
		this.borrowService.deleteBorrow(borrow, log);
		message("撤回成功！", "/member/borrow/borrow.html");
		return MSG;
	}

	public String update() throws Exception {
		boolean isOk = true;
		String checkMsg = "";
		try {
			isOk = validate();
			String borrowId = this.request.getParameter("id");
			BorrowModel b = this.borrowService.getBorrow(Long.parseLong(borrowId));
			BorrowModel wrapModel = BorrowHelper.getHelper(b);
			fillBorrow(wrapModel);
			this.borrowService.updateBorrow(wrapModel.getModel());
		} catch (Exception e) {
			isOk = false;
			checkMsg = e.getMessage();
			logger.error(e.getMessage(), e.getCause());
		}
		if (isOk)
			message("修改成功", "");
		else {
			message(checkMsg, "");
		}
		return SUCCESS;
	}

	private boolean validate() {
		if (!checkValidImg(StringUtils.isNull(this.request.getParameter("valicode")))) {
			throw new BorrowException("验证码不正确！");
		}

		if ((this.borrow.getIs_jin() == 1) && (Global.getWebid().equals(Constant.HUIZHOUDAI))) {
			UserAccountSummary uas = this.accountService.getUserAccountSummary(getSessionUser().getUser_id());
			if ((uas == null) || (uas.getAccountOwnMoney() < this.borrowService.getRepayTotalWithJin(getSessionUser().getUser_id()) + NumberUtils.getDouble(this.borrow.getAccount()))) {
				throw new BorrowException("你的净资产小于净值标待还总额,发标失败!");
			}
		}

		UserAmount amount = this.userAmountService.getUserAmount(getSessionUser().getUser_id());
		if ((((this.borrow.getIs_xin() == 1) || (this.borrow.getIs_student() == 1))) && (((amount == null) || (amount.getCredit_use() < NumberUtils.getDouble(this.borrow.getAccount()))))) {
			throw new BorrowException("可用信用额度不足!");
		}

		return true;
	}

	private boolean checkHasUnFinsh() {
		List<BorrowModel> unfinshList = this.borrowService.unfinshBorrowList(getSessionUser().getUser_id());
		if ((unfinshList != null) && (unfinshList.size() > 0)) {
			throw new BorrowException("还存在未完成的标！");
		}
		return true;
	}

	public String tender() throws Exception {
		boolean isOk = true;
		String checkMsg = "";
		User user = getSessionUser();
		long borrow_id = this.borrow.getId();

		Tender lottery_tender = new Tender();
		try {
			logger.info("Begin tender! uid=" + user.getUser_id() + ",bid=" + borrow_id + ",tender_money=" + this.borrow.getMoney());
			BorrowModel model = this.borrowService.getBorrow(borrow_id);
			BorrowModel wrapModel = BorrowHelper.getHelper(model);

			double tenderNum = this.borrow.getMoney();
			double tenderCount = this.borrow.getFlow_count();

			Account act = this.borrowService.getAccount(user.getUser_id());

			double useMoney = act.getUse_money();
			if (tenderNum > useMoney) {
				tenderNum = useMoney;
				this.borrow.setMoney(tenderNum);
			}
			
			String errormsg = checkTender(model, user, act, tenderNum, tenderCount);
			if (errormsg.equals(ERROR)) {
				return ERROR;
			}

			String tokenMsg = checkToken("borrow_token");
			if (!StringUtils.isBlank(tokenMsg)) {
				message(tokenMsg);
				return MSG;
			}

			Tender tender = fillTender(wrapModel);
			lottery_tender = tender;

			AccountLog log = new AccountLog(user.getUser_id(), Constant.TENDER, model.getUser_id(), getTimeStr(), getRequestIp());
			log.setRemark(getLogRemark(wrapModel.getModel()));

			Protocol protocol = new Protocol(tender.getId(), Constant.TENDER_PROTOCOL, getTimeStr(), getRequestIp(), "");
			this.borrowService.addTender(tender, wrapModel, act, log, protocol);

			if ((Global.getInt("protocol_toEmail_enable") == 1) && (model.getBorrowType() == Constant.TYPE_FLOW)) {
				Long tender_id = Long.valueOf(tender.getId());
				BorrowProtocol bp = new BorrowProtocol(user, borrow_id, tender_id.longValue());
				File pdfFile = new File(bp.getInPdfName());
				if (pdfFile.exists()) {
					try {
						bp.createPdf();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				sendMailWithAttachment(user, borrow_id, tender_id.longValue());
			}
		} catch (BorrowException e) {
			isOk = false;
			checkMsg = e.getMessage();
			logger.error(e.getMessage(), e.getCause());
		} catch (Exception e) {
			isOk = false;
			checkMsg = "系统繁忙，投标失败,请稍后再试！";
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			logger.info("End tender! uid=" + user.getUser_id() + ",bid=" + borrow_id + ",tender_money=" + this.borrow.getMoney());
		}
		if (isOk) {
			Borrow bl = this.borrowService.getBorrow(borrow_id);
			if ((bl.getIs_jin() == 1) && (Double.parseDouble(bl.getAccount()) == Double.parseDouble(bl.getAccount_yes()))) {
				bl.setVerify_time("" + new Date().getTime() / 1000L);
				bl.setVerify_remark("净值标满标自动审核！");
				this.borrowService.updateJinBorrow(bl);
			}

			String tender_money_is_enable = StringUtils.isNull(Global.getValue("tender_money_is_enable"));
			tender_money_is_enable = (tender_money_is_enable == "") ? "0" : tender_money_is_enable;
			if ("1".equals(tender_money_is_enable)) {
				try {
					this.borrowService.getTenderAddLotteryTimes(lottery_tender);
				} catch (Exception e) {
					checkMsg = "投标成功！";
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				double tender_money = NumberUtils.getDouble(StringUtils.isNull(Global.getValue("tender_money")));
				double tenderAccount = NumberUtils.getDouble(StringUtils.isNull(lottery_tender.getAccount()));
				if (tenderAccount >= tender_money)
					message("投标成功，且您今日已额外获得一次抽奖机会！", "/invest/detail.html?borrowid=" + this.borrow.getId());
				else
					message("投标成功！", "/invest/detail.html?borrowid=" + this.borrow.getId());
			} else {
				message("投标成功！", "/invest/detail.html?borrowid=" + this.borrow.getId());
			}
			logger.info("Show tender success msg! uid=" + user.getUser_id() + ",bid=" + borrow_id + ",tender_money=" + this.borrow.getMoney());
		} else {
			message(checkMsg, "");
			logger.info("Show tender fail msg! uid=" + user.getUser_id() + "bid=" + borrow_id + ",tender_money=" + this.borrow.getMoney());
		}

		if (Global.getWebid().equals("ssjb")) {
			SearchParam param = new SearchParam();
			param.setStatus("1");
			param.setUsername(user.getUsername());
			List<AccountRecharge> list = this.accountService.getRechargeList(param);
			if (list.size() > 0) {
				AccountRecharge accountRecharge = this.accountService.getMinRechargeMoney(user.getUser_id());
				if (accountRecharge.getYes_no() == 0) {
					double minRechargeMoney = accountRecharge.getMoney();
					String min_recharge_award_apr_string = Global.getValue("min_recharge_award_apr");
					double min_recharge_award_apr = NumberUtils.getDouble(min_recharge_award_apr_string);
					double minRechargeAward = minRechargeMoney * min_recharge_award_apr;
					String invite_userid = user.getInvite_userid();
					User newuser = this.userService.getUserById(NumberUtils.getLong(invite_userid));
					if (!StringUtils.isBlank(invite_userid)) {
						this.accountService.updateAccount(minRechargeAward, minRechargeAward, 0.0D, NumberUtils.getLong(invite_userid));
						AccountModel inviteAccount = this.accountService.getAccount(NumberUtils.getLong(invite_userid));
						AccountLog log = new AccountLog(NumberUtils.getLong(invite_userid), Constant.Invite_RECHARGE_AWARD, 1L, getTimeStr(), getRequestIp());
						log.setMoney(minRechargeAward);
						log.setRemark("邀请好友充值成功，奖励" + minRechargeAward + "元已经转到邀请人" + newuser.getUsername() + "账户");
						log.setUse_money(inviteAccount.getUse_money());
						log.setTotal(inviteAccount.getTotal());
						log.setNo_use_money(inviteAccount.getNo_use_money());
						log.setCollection(inviteAccount.getCollection());
						this.accountService.addAccountLog(log);

						accountRecharge.setYes_no(1);
						this.accountService.updateAccountRechargeYes_no(accountRecharge);
					}
				}
			}
		}
		return SUCCESS;
	}

	private int createPdf(String pdfName, Borrow b) throws Exception {
		int size = 0;
		PdfHelper pdf = PdfHelper.instance(pdfName);
		User user = getSessionUser();
		if (b == null) {
			throw new BorrowException("该借款标不存在！");
		}
		if ((this.webid != null) && (this.webid.equals("mdw")) && (b.getIs_flow() == 1)) {
			List<BorrowTender> list = this.borrowService.getTenderList(b.getId(), user.getUser_id());
			for (BorrowTender bt : list) {
				addPdfHeader(pdf, b, bt);
				addPdfContent(pdf, b, bt);
			}
		} else {
			addPdfHeader(pdf, b);
			addPdfTable(pdf, b);
			addPdfContent(pdf, b);
		}
		pdf.exportPdf();
		return size;
	}

	private void addPdfHeader(PdfHelper pdf, Borrow b) throws DocumentException {
		DetailUser u = this.userService.getDetailUser(b.getUser_id());
		User user = getSessionUser();

		String title = "借款协议书\n";
		String wztitle = "合同编号: MDW-" + DateUtils.dateStr2(b.getAddtime()) + b.getId() + "\n";
		if ((this.webid != null) && (this.webid.equals("mdw")) && (b.getIs_flow() == 1))
			pdf.addTitle(wztitle);
		else {
			pdf.addTitle(title);
		}
		String content = Html2Text(b.getContent());
		String h = "借款协议号：" + b.getId() + "     借款人：" + u.getRealname()
				+ "     出借人：详见本协议第一条 \n签订日期："
				+ DateUtils.dateStr2(BorrowHelper.getBorrowProtocolTime(b))
				+ "\n借款人通过" + Global.getValue("webname")
				+ "网站(以下简称\"本网站\")的居间,就有关借款事项与各出借人达成如下协议：\n"
				+ "第一条：借款详情如下表所示：\n";

		String p = "借款协议号："
				+ b.getId()
				+ "    借款人（以下简称乙方）："
				+ u.getRealname()
				+ "  出借人（以下简称甲方）：详见本协议第一条\n"
				+ "签订日期："
				+ DateUtils.dateStr2(BorrowHelper.getBorrowProtocolTime(b))
				+ "借款人通过"
				+ Global.getValue("webname")
				+ "网站(以下简称\"本网站\")的居间服务,就有关借款事项与各出借人达成如下协议：\n\n"
				+ "鉴于：\n "
				+ "1、丙方是一家在厦门湖里区合法成立并有效存续的有限责任公司，拥有www.youyoudai.com 网站（以下简称“该网站”）的经营权，提供信用咨询，为交易提供信息服务；"
				+ "2、乙方已在该网站注册，并承诺其提供给丙方的信息是完全真实的；"
				+ "3、甲方承诺对本协议涉及的借款具有完全的支配能力，是其自有闲散资金，为其合法所得；并承诺其提供给丙方的信息是完全真实的；"
				+ "4、乙方有借款需求，甲方亦同意借款，双方有意成立借贷关系；"
				+ "第一条 借款详细信息 （注：因计算中存在四舍五入，最后一期应收本息与之前略有不同）";

		String q = "借款协议号：" + b.getId() + "     借款人："
				+ StringUtils.hideStr(u.getRealname(), 0, 1)
				+ "     出借人：详见本协议第一条 \n签订日期："
				+ DateUtils.dateStr2(BorrowHelper.getBorrowProtocolTime(b))
				+ "\n借款人通过" + Global.getValue("webname")
				+ "网站(以下简称\"本网站\")的居间,就有关借款事项与各出借人达成如下协议：\n"
				+ "第一条：借款详情如下表所示：\n";

		if ((this.webid != null) && (this.webid.equals("yydai")))
			pdf.addText(p);
		else if ((this.webid != null) && (this.webid.equals("ssjb")))
			pdf.addText(q);
		else
			pdf.addText(h);
	}

	private void addPdfHeader(PdfHelper pdf, Borrow b, BorrowTender bt)
			throws DocumentException {
		logger.debug("borrow_time====" + b.getBorrow_time());
		logger
				.debug("borrow_time===="
						+ DateUtils.dateStr2(b.getBorrow_time()));
		DetailUser u = this.userService.getDetailUser(b.getUser_id());
		User user = getSessionUser();
		String wztitle = "合同编号: MDW-" + DateUtils.dateStr2(b.getAddtime())
				+ b.getId() + "\n";
		pdf.addTitle(wztitle);
		String content = Html2Text(b.getContent());
		if (bt.getUser_id() == user.getUser_id()) {
			String w = "债权转让及回购协议\n甲方(债权人)：沈**\n乙方(第三人)："
					+ user.getRealname()
					+ "\n"
					+ "丙方(担保人)：深圳市鼎和资产管理有限公司\n"
					+ "1.借款条款\n"
					+ "（1）"
					+ DateUtils.dateStr2(b.getBorrow_time())
					+ "，甲方和"
					+ u.getRealname()
					+ "签订借款协议（见附件一），甲方出借给"
					+ u.getRealname()
					+ "人民币"
					+ b.getBorrow_account()
					+ "元。\n"
					+ "截止本协议签订之日，"
					+ u.getRealname()
					+ "尚欠甲方人民币"
					+ b.getBorrow_account()
					+ "元未归还,借款期限"
					+ b.getBorrow_time_limit()
					+ "个月，还款方式为到期一次性返本金和利息。\n"
					+ "（2）现甲方将以上债权中的部分债权计人民币（￥"
					+ bt.getAccount()
					+ "）元（“出让债权”）转让给乙方，借款利息"
					+ b.getApr()
					+ "%，并承诺"
					+ "并保证其转让的债权系合法、有效。乙方表示愿意受让上述部分债权。\n"
					+ "（3）在本协签订之日，甲方应按照附件二的格式给"
					+ u.getRealname()
					+ "发出债权转让通知书，并确认债务人收到本债"
					+ "权转让通知书。\n"
					+ "（4）上述转让自本协议签订之日起立即生效。借款利息拆分将按天计算，本协议签订之前的利息归甲"
					+ "方所有，本协议签订之后的利息将归乙方所有。\n"
					+ "2.回购条款。\n"
					+ "自本协议签订"
					+ b.getTime_limit()
					+ "个月后，甲方承诺将无条件从乙方回购出让债权。在回购前，出让债权的利息归乙方所"
					+ "有。在甲方回购后，乙方将不在享有出让债权的任何权利。\n"
					+ "丙方保证甲方将按照本协议约定条款回购乙方受让的债权，如甲方违约，丙方将代甲方履行回购义务"
					+ "。\n"
					+ "3.陈述与承诺\n"
					+ "甲方明确声明本协议项下的债权无任何第三人主张权利，且权利不受限制，即不存在被法院保全、查"
					+ "封或强制执行的情况或已设担保。签订本协议之前，甲方没有与第三方签订过本协议项下债权转让合"
					+ "同。\n"
					+ "4.保证其提供的所有证照、资料真实、准确、完整、合法；\n"
					+ "5.违约责任\n"
					+ "各方同意，如果一方违反其在本协议中所作的承诺或任何其他义务，致使其他方遭受或发生损害、损"
					+ "失、索赔、处罚、诉讼仲裁、费用、义务和/或责任，违约方须向另一方作出全面补偿。\n"
					+ "6.其他规定\n"
					+ "（1）对本协议所作的任何修改及补充，必须采用书面形式并由各方签署。\n"
					+ "（2）在本协议履行过程中发生的纠纷，双方应友好协商解决;协商不成的，任何一方均有权向深圳市"
					+ "人民法院提请诉讼。\n"
					+ "（3）本协议自双方签字盖章后生效，一式三份，甲乙丙三方各执一份，具有同等法律效力。\n"
					+ "甲方　　　沈**\n"
					+ "乙方　　　"
					+ user.getRealname()
					+ "\n"
					+ "丙方　　　深圳市鼎和资产管理有限公司\n"
					+ "签订地点：广东省深圳市\n"
					+ "签订日期："
					+ DateUtils.dateStr2(bt.getAddtime())
					+ "\n\n\n\n"
					+ "附件一：借款协议\n"
					+ "借出人：沈**\n"
					+ "借入人："
					+ u.getRealname()
					+ "\n"
					+ "担保人：深圳市鼎和资产管理有限公司\n"
					+ "重要提示：\n"
					+ "借出人、借入人、保证人请认真阅读本协议项下的全部条款。借出人、借入人、保证人，一旦签订本协议，即认为借入人、保证人已理解并同意本协议的所有条款。\n"
					+ "1.借贷条款\n" + "（1）贷款金额：人民币￥" + b.getBorrow_account()
					+ "元。\n" + "（2）贷款期限：" + b.getBorrow_time_limit() + "个月。\n"
					+ "（3）还款方式：一次性返本息。";
			pdf.addText(w);
		}
	}

	private String Html2Text(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>";

			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");

			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");

			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}

	private void addPdfTable(PdfHelper pdf, Borrow b) throws DocumentException {
		List list = this.borrowService.getTenderList(this.borrow.getId());
		List cellList = null;
		List[] args = new List[list.size() + 1];

		cellList = new ArrayList();
		cellList.add("出借人(id)");
		cellList.add("借款金额");
		cellList.add("借款期限");
		cellList.add("年利率");
		cellList.add("借款开始日");
		cellList.add("借款到期日");
		cellList.add("截止还款日");
		cellList.add("还款本息");
		args[0] = cellList;
		for (int i = 1; i < list.size() + 1; ++i) {
			BorrowTender t = (BorrowTender) list.get(i - 1);
			cellList = new ArrayList();
			cellList.add(t.getUsername());
			cellList.add(t.getAccount() + "元");
			if (b.getIsday() == 1)
				cellList.add(b.getTime_limit_day() + "天");
			else {
				cellList.add(b.getTime_limit() + "个月");
			}
			cellList.add(b.getApr() + "%");
			cellList.add(DateUtils.dateStr2(BorrowHelper.getBorrowVerifyTime(b,
					t)));
			Date d = BorrowHelper.getBorrowRepayTime(b, t);
			cellList.add(DateUtils.dateStr2(d));
			cellList.add("每月截止" + DateUtils.getDay(d) + "日");
			cellList.add(Double.valueOf(NumberUtils.ceil(Double.valueOf(
					t.getRepayment_account()).doubleValue())));
			args[i] = cellList;
		}
		pdf.addTable(args, 80.0F, 7);
	}

	private int addPdfContent(PdfHelper pdf, Borrow b) throws DocumentException {
		int size = 0;
		Site site = this.articleService.getSiteById(32L);
		String content = site.getContent();
		pdf.addText(content);
		if ((StringUtils.isNull(this.webid).equals("xdcf"))
				|| (StringUtils.isNull(this.webid).equals("jsy"))
				|| (StringUtils.isNull(this.webid).equals("mszb"))) {
			try {
				Image image = Image.getInstance(Global.getValue("weburl")
						+ Global.getValue("theme_dir") + "/images/zhang.jpg");
				pdf.addImage(image);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		size = pdf.getPageNumber();
		return size;
	}

	private int addPdfContent(PdfHelper pdf, Borrow b, BorrowTender bt)
			throws DocumentException {
		int size = 0;
		Site site = this.articleService.getSiteById(31L);
		String content = site.getContent();
		pdf.addText(content);
		DetailUser u = this.userService.getDetailUser(b.getUser_id());
		User user = getSessionUser();
		if (bt.getUser_id() == user.getUser_id()) {
			String endtext = "借入人：" + u.getRealname() + "\n"
					+ "保证人：深圳市鼎和资产管理有限公司\n" + "协议签订地：广东省深圳市\n" + "协议签署日期："
					+ DateUtils.dateStr2(b.getBorrow_time())
					+ "\n\n附件二：债权转让通知书\n" + u.getRealname() + "：\n"
					+ "根据我方与贵方在" + DateUtils.dateStr2(b.getBorrow_time())
					+ "签订的《借款协议》，我方向贵方出借了人民币\n" + "￥" + b.getBorrow_account()
					+ "元借款，目前还在借款过程中。我方决定将上述债权中部分合计人民币（" + bt.getAccount()
					+ "）元的\n" + "债权转让给第三方自然人(" + user.getRealname()
					+ ")，上述转让立即生效。借款利息拆分将按天结算，今天之前的利息\n"
					+ "归我\t方所有，今天之后的利息将归新债权人所有。\n" + "特此告知！\n" + "沈**\n"
					+ DateUtils.dateStr2(bt.getAddtime());
			pdf.addText(endtext);
		}
		try {
			Image image = Image.getInstance(Global.getValue("weburl")
					+ Global.getValue("theme_dir") + "/images/zhang.jpg");
			pdf.addImage(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		size = pdf.getPageNumber();
		return size;
	}

	public String getWebid() {
		return this.webid;
	}

	public void setWebid(String webid) {
		this.webid = webid;
	}

	public String protocol() {
		String type = paramString("type");
		if (type.equals("new")) {
			return newprotocol();
		}
		User user = getSessionUser();
		boolean isOk = true;
		String checkMsg = "";

		double hasTenderMoney = this.borrowService
				.hasTenderTotalPerBorrowByUserid(this.borrow.getId(),
						getSessionUser().getUser_id());
		int borrowCount = this.borrowService.hasBorrowCountByUserid(this.borrow
				.getId(), getSessionUser().getUser_id());

		String webid = Global.getValue("webid");
		if ((webid != null) && (webid.equals("jsy"))) {
			if ((hasTenderMoney <= 0.0D) && (borrowCount <= 0)) {
				message("您不是投资人或借款人，无权访问该借款协议书！", "");
				return MSG;
			}

		} else if (hasTenderMoney <= 0.0D) {
			message("您不是投资人，无权访问该借款协议书！", "");
			return MSG;
		}

		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = this.borrow.getId() + ".pdf";
		String inPdfName = contextPath + "/data/protocol/"
				+ this.borrow.getId() + ".pdf";
		String image = Global.getValue("weburl") + Global.getValue("theme_dir")
				+ "/images/zhang.jpg";
		String outPdfName = contextPath + "data/protocol/"
				+ this.borrow.getId() + ".pdf";
		logger.info(inPdfName);
		BorrowModel borrowModel = this.borrowService.getBorrow(this.borrow
				.getId());

		File pdfFile = new File(inPdfName);
		if (pdfFile.exists()) {
			boolean flug = pdfFile.delete();
			if (flug) {
				try {
					int size = 0;
					size = createPdf(inPdfName, borrowModel);
				} catch (Exception e) {
					isOk = false;
					checkMsg = "生成pdf文件出错！";
				}
			}
		}
		if ((!pdfFile.exists()) || (borrowModel.getIs_flow() == 1)) {
			try {
				int size = 0;
				size = createPdf(inPdfName, borrowModel);
			} catch (Exception e) {
				isOk = false;
				checkMsg = "生成pdf文件出错！";
			}
		}
		if (!isOk) {
			message(checkMsg);
			return MSG;
		}
		InputStream ins = null;
		try {
			ins = new BufferedInputStream(new FileInputStream(inPdfName));
			byte[] buffer = new byte[ins.available()];
			ins.read(buffer);
			ins.close();
			HttpServletResponse response = (HttpServletResponse) ActionContext
					.getContext()
					.get(
							"com.opensymphony.xwork2.dispatcher.HttpServletResponse");
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(downloadFile.getBytes()));
			response.addHeader("Content-Length", "" + pdfFile.length());
			OutputStream ous = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			ous.write(buffer);
			ous.flush();
			ous.close();
		} catch (FileNotFoundException e) {
			logger.error("协议pdf文件" + downloadFile + "未找到！");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String newprotocol() {
		User user = getSessionUser();
		boolean isOk = true;
		String checkMsg = "";

		double hasTenderMoney = this.borrowService
				.hasTenderTotalPerBorrowByUserid(this.borrow.getId(),
						getSessionUser().getUser_id());
		int borrowCount = this.borrowService.hasBorrowCountByUserid(this.borrow
				.getId(), getSessionUser().getUser_id());

		String webid = Global.getValue("webid");
		if ((webid != null) && (webid.equals("jsy"))) {
			if ((hasTenderMoney <= 0.0D) && (borrowCount <= 0)) {
				message("您不是投资人或借款人，无权访问该借款协议书！", "");
				return MSG;
			}

		} else if ((hasTenderMoney <= 0.0D)
				&& (user.getUser_id() != this.borrow.getUser_id())) {
			message("您不是投资人，无权访问该借款协议书！", "");
			return MSG;
		}

		Long tender_id = Long.valueOf(paramLong("tender_id"));
		BorrowProtocol bp = new BorrowProtocol(user, this.borrow.getId(),
				tender_id.longValue());

		File pdfFile = new File(bp.getInPdfName());
		if (pdfFile.exists()) {
			try {
				bp.createPdf();
			} catch (Exception e) {
				isOk = false;
				checkMsg = "生成pdf文件出错！";
			}
		}
		if (!isOk) {
			message(checkMsg);
			return MSG;
		}
		try {
			generateDownloadFile(bp.getInPdfName(), bp.getDownloadFileName());
		} catch (FileNotFoundException e) {
			logger.error("协议pdf文件" + bp.getDownloadFileName() + "未找到！");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String checkTender(Borrow b, User u, Account act, double tenderNum,
			double tenderCount) {
		
		u = this.userService.getDetailUser(u.getUser_id());
		String url = "/invest/detail.html?borrowid=" + b.getId();
		String pwd = StringUtils.isNull(this.request.getParameter("pwd"));
		if (u.getIslock() == 1) {
			message("您账号已经被锁定，不能进行投标，请跟管理员联系! ", url);
			return ERROR;
		}
		if (b.getStatus() != 1) {
			message("不能进行投标! ", url);
			return ERROR;
		}
		if (StringUtils.isNull(Global.getValue("webid")).equals(Constant.HUIZHOUDAI)) {
			if (StringUtils.isBlank(this.request.getParameter("valicode"))) {
				message("验证码不能为空！", url);
				return ERROR;
			}
			if (!checkValidImg(StringUtils.isNull(this.request.getParameter("valicode")))) {
				message("验证码不正确！", url);
				return ERROR;
			}

		}

		if (StringUtils.isNull(this.borrow.getPaypassword()).equals("")) {
			message("支付交易密码不能为空! ", url);
			return ERROR;
		}
		if (!StringUtils.isBlank(b.getPwd())) {
			if (StringUtils.isNull(pwd).equals("")) {
				message("定向标密码不能为空! ", url);
				return ERROR;
			}
			if (!b.getPwd().equals(pwd)) {
				message("定向标密码不正确! ", url);
				return ERROR;
			}
		}
		if (StringUtils.isNull(u.getPaypassword()).equals("")) {
			message("请先设一个支付交易密码! ", url);
			return ERROR;
		}
		MD5 md5 = new MD5();
		if (!md5.getMD5ofStr(this.borrow.getPaypassword()).equals(u.getPaypassword())) {
			message("支付交易密码不正确! ", url);
			return ERROR;
		}

		if (b.getUser_id() == u.getUser_id()) {
			message("自己不能投自己发布的标！ ", url);
			return ERROR;
		}

		if (b.getIs_flow() == 1) {
			if (tenderCount < 1.0D) {
				message("投标的份数必须大于1的整数！", url);
				return ERROR;
			}
			tenderNum = b.getFlow_money() * tenderCount;
		}
		double useMoney = act.getUse_money();
		if (tenderNum > useMoney) {
			message("投资金额不能大于你的可用余额！ ", url);
			return ERROR;
		}
		double lowest_account_num = NumberUtils.getDouble(b.getLowest_account());
		if (tenderNum < lowest_account_num) {
			message("投资金额不能小于最小限制金额额度！ ", url);
			return ERROR;
		}
		if (tenderNum <= 0.0D) {
			message("投标金额必须大于0！", url);
			return ERROR;
		}
		double account_num = NumberUtils.getDouble(b.getAccount());
		double account_yes_num = NumberUtils.getDouble(b.getAccount_yes());
		if (account_yes_num >= account_num) {
			message("此标已满! ", url);
			return ERROR;
		}

		return "";
	}

	private Tender fillTender(BorrowModel b) {
		int auto_repurchase = NumberUtils.getInt(this.request.getParameter("auto_repurchase"));

		int award_after_push = NumberUtils.getInt(this.request.getParameter("award_after_push"));
		BorrowModel model = b.getModel();
		User user = getSessionUser();
		Tender tender = new Tender();
		tender.setBorrow_id(model.getId());
		if (model.getIs_flow() == 1)
			tender.setMoney("" + this.borrow.getFlow_count() * model.getFlow_money());
		else {
			tender.setMoney("" + this.borrow.getMoney());
		}
		tender.setAddtime("" + new Date().getTime() / 1000L);
		tender.setAddip(getRequestIp());
		tender.setUser_id(user.getUser_id());
		tender.setAuto_repurchase(auto_repurchase);
		tender.setAward_after_push(award_after_push);
		return tender;
	}

	private BorrowFlow fillFlow(BorrowModel b) {
		BorrowModel model = b.getModel();
		User user = getSessionUser();
		BorrowFlow flow = new BorrowFlow();
		flow.setBorrow_id(model.getId());
		Date now = Calendar.getInstance().getTime();
		Date back = DateUtils.rollDate(now, 0, NumberUtils.getInt(model.getTime_limit()), 0);
		flow.setBuy_time(now.getTime() / 1000L);
		flow.setBack_time(back.getTime() / 1000L);
		flow.setUser_id(user.getUser_id());
		flow.setFlow_count(this.borrow.getFlow_count());
		flow.setInterest("" + NumberUtils.format4(b.calculateInterest()));
		flow.setAddip(getRequestIp());
		return flow;
	}

	private Borrow fillBorrow(BorrowModel model) {
		BorrowModel b = model.getModel();
		User user = getSessionUser();
		b.setUser_id(user.getUser_id());

		b.setType(StringUtils.isNull(b.getType()));
		b.setAccount(this.borrow.getAccount());
		b.setAccount_yes("0");
		b.setName(this.borrow.getName());
		b.setContent(this.borrow.getContent());
		b.setUse(this.borrow.getUse());
		b.setLowest_account(this.borrow.getLowest_account());
		b.setMost_account(this.borrow.getMost_account());
		b.setValid_time(this.borrow.getValid_time());
		b.setPwd(StringUtils.isNull(this.borrow.getPwd()));

		b.setAward(this.borrow.getAward());
		b.setFunds(this.borrow.getFunds());
		b.setPart_account(this.borrow.getPart_account());

		b.setVouch_user(StringUtils.isNull(this.borrow.getVouch_user()));
		b.setVouch_award(StringUtils.isNull(this.borrow.getVouch_award()));
		b.setVouch_account(StringUtils.isNull(this.borrow.getVouch_account()));
		b.setRepayment_account("0");

		b.setOpen_tender(this.borrow.getOpen_tender());

		InterestCalculator ic = model.interestCalculator();
		double repayAccount = ic.getTotalAccount();
		b.setRepayment_account("" + repayAccount);

		b.setApr(this.borrow.getApr());

		b.setStyle(StringUtils.isNull(this.borrow.getStyle()));

		b.setAddip(getRequestIp());
		b.setAddtime(getTimeStr());

		b.setLate_award(this.borrow.getLate_award());
		return b;
	}

	private Borrow fillDonation(BorrowModel model) {
		BorrowModel b = model.getModel();
		User user = getSessionUser();
		b.setUser_id(user.getUser_id());

		b.setType(StringUtils.isNull(b.getType()));
		b.setAccount(this.borrow.getAccount());
		b.setAccount_yes("0");
		b.setName(this.borrow.getName());
		b.setContent(this.borrow.getContent());
		b.setUse(this.borrow.getUse());
		b.setLowest_account(this.borrow.getLowest_account());
		b.setMost_account(this.borrow.getMost_account());
		b.setValid_time(this.borrow.getValid_time());
		b.setPwd(StringUtils.isNull(this.borrow.getPwd()));

		b.setAward(this.borrow.getAward());
		b.setFunds(this.borrow.getFunds());
		b.setPart_account(this.borrow.getPart_account());

		b.setVouch_user(StringUtils.isNull(this.borrow.getVouch_user()));
		b.setVouch_award(StringUtils.isNull(this.borrow.getVouch_award()));
		b.setVouch_account(StringUtils.isNull(this.borrow.getVouch_account()));
		b.setRepayment_account("0");

		b.setOpen_tender(this.borrow.getOpen_tender());

		b.setAddip(getRequestIp());
		b.setAddtime(getTimeStr());

		b.setLate_award(this.borrow.getLate_award());

		return b;
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

	public BorrowModel getModel() {
		return this.borrow;
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

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}