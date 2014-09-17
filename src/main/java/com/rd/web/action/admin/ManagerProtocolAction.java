package com.rd.web.action.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.opensymphony.xwork2.ActionContext;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.AccountCash;
import com.rd.domain.AccountRecharge;
import com.rd.domain.User;
import com.rd.exception.BorrowException;
import com.rd.model.PageDataList;
import com.rd.model.ProtocolModel;
import com.rd.model.SearchParam;
import com.rd.service.AccountService;
import com.rd.service.BorrowService;
import com.rd.service.UserService;
import com.rd.tool.itext.PdfHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;

public class ManagerProtocolAction extends BaseAction {
	private static final Logger logger = Logger
			.getLogger(ManagerProtocolAction.class);

	private boolean isOk = true;
	private String checkMsg = "";
	private AccountService accountService;
	private UserService userService;
	private BorrowService borrowService;
	private AccountRecharge accountRecharge;
	private AccountCash accountCash;

	public AccountCash getAccountCash() {
		return this.accountCash;
	}

	public void setAccountCash(AccountCash accountCash) {
		this.accountCash = accountCash;
	}

	public AccountRecharge getAccountRecharge() {
		return this.accountRecharge;
	}

	public void setAccountRecharge(AccountRecharge accountRecharge) {
		this.accountRecharge = accountRecharge;
	}

	public boolean isOk() {
		return this.isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public String getCheckMsg() {
		return this.checkMsg;
	}

	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
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

	public BorrowService getBorrowService() {
		return this.borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public String protocol() {
		String idString = this.request.getParameter("id");
		String type = this.request.getParameter("type");
		long id = 0L;
		if (!StringUtils.isBlank(idString)) {
			id = Integer.parseInt(idString);
		}
		ProtocolModel protocol = this.borrowService.getProtocolByid(id);
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		String downloadFile = null;
		String inPdfName = null;
		String outPdfName = null;
		downloadFile = protocol.getId() + ".pdf";
		inPdfName = contextPath + "/data/protocol/" + protocol.getId() + ".pdf";
		outPdfName = contextPath + "data/protocol/" + protocol.getId() + ".pdf";

		logger.info(inPdfName);

		File pdfFile = new File(inPdfName);
		if (pdfFile.exists()) {
			boolean flug = pdfFile.delete();
			if (flug) {
				try {
					int size = 0;
					size = createPdf(inPdfName, type, protocol);
				} catch (Exception e) {
					this.isOk = false;
					this.checkMsg = "生成pdf文件出错！";
				}
			}
		}
		if (!pdfFile.exists()) {
			try {
				int size = 0;
				size = createPdf(inPdfName, type, protocol);
			} catch (Exception e) {
				this.isOk = false;
				this.checkMsg = "生成pdf文件出错！";
			}
		}
		if (!this.isOk) {
			message(this.checkMsg);
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

	private int createPdf(String pdfName, String type, ProtocolModel protocol)
			throws Exception {
		int size = 0;
		PdfHelper pdf = PdfHelper.instance(pdfName);
		User user = getSessionUser();

		if (protocol == null) {
			throw new BorrowException("该记录不存在！");
		}
		addPdfHeader(pdf, type);
		addPdfContent(pdf, type, protocol);
		pdf.exportPdf();
		return size;
	}

	private void addPdfHeader(PdfHelper pdf, String type)
			throws DocumentException {
		String title = "";
		if (type.equals(Constant.RECHARGE_PROTOCOL))
			title = "投资人投标保证金托管委托书\n";
		else if (type.equals(Constant.CASH_PROTOCOL))
			title = "借款人提现委托书\n";
		else if (type.equals(Constant.TENDER_PROTOCOL))
			title = "投资人投标资金划转委托书\n";
		else if (type.equals(Constant.REPAYMENT_ACCOUNT_PROTOCOL))
			title = "借款人归还本息委托书\n";
		else if (type.equals(Constant.AWARD_PROTOCOL)) {
			title = "资金划转委托书\n";
		}
		pdf.addTitle(title);
	}

	private int addPdfContent(PdfHelper pdf, String type, ProtocolModel protocol)
			throws DocumentException {
		int size = 0;
		if (type.equals(Constant.RECHARGE_PROTOCOL)) {
			String rechargeContent = "怀化金宝汇资产管理有限公司：\n       本人拟在怀化及时雨网络平台管理顾问有限公司的网贷平台投标（招标），为投标便利，现转入投标保证金"
					+ protocol.getMoney()
					+ "元，"
					+ "委托贵公司保管。请按我方指令支付。\n"
					+ "                                                                                                                                      委托人："
					+ protocol.getRealname()
					+ "\n"
					+ "                                                                                                                                      公民身份号码："
					+ protocol.getCard_id()
					+ "\n"
					+ "                                                                                                                                      用户名："
					+ protocol.getUsername()
					+ "\n"
					+ "                                                                                                                                      "
					+ DateUtils.dateStr(protocol.getAddtime())
					+ "\n"
					+ "审核情况：属实。收到。\n"
					+ "                                                                                                                                      怀化金宝汇资产管理有限公司\n"
					+ "                                                                                             "
					+ DateUtils.dateStr(protocol.getAddtime());

			pdf.addText(rechargeContent);
		} else if (type.equals(Constant.CASH_PROTOCOL)) {
			String cashContent = "怀化金宝汇资产管理有限公司：\n       委托人拟向贵公司提取投资款或借款|（详见《借款合同》）人民币  "
					+ protocol.getMoney()
					+ " 元，请贵公司将笔款项划转至本委托人指定的下列银行账户中：\n"
					+ "账号："
					+ protocol.getBank_account()
					+ " \n"
					+ "户名：  "
					+ protocol.getRealname()
					+ "\n"
					+ "开户行："
					+ protocol.getBank_branch()
					+ "\n"
					+ "                                                                                                                                      委托人："
					+ protocol.getRealname()
					+ "\n"
					+ "                                                                                                                                      公民身份号码："
					+ protocol.getCard_id()
					+ "\n"
					+ "                                                                                                                                      用户名："
					+ protocol.getUsername()
					+ "\n"
					+ "                                                                                                                                      "
					+ DateUtils.dateStr(protocol.getAddtime())
					+ "\n"
					+ "                                                                                                                                      怀化金宝汇资产管理有限公司\n"
					+ "                                                                                             "
					+ DateUtils.dateStr(protocol.getAddtime());

			pdf.addText(cashContent);
		} else if (type.equals(Constant.TENDER_PROTOCOL)) {
			String tenderContent = "怀化金宝汇资产管理有限公司：\n       本人投标用户名"
					+ protocol.getUsername()
					+ "的"
					+ protocol.getBorrowname()
					+ "借款"
					+ protocol.getBorrow_account()
					+ "元的项目，请贵公司立即从本人投标保证金账户中划转"
					+ protocol.getMoney()
					+ "元至对方账户。\n"
					+ "         特此委托\n"
					+ "                                                                                                                                      委托人："
					+ protocol.getRealname()
					+ "\n"
					+ "                                                                                                                                      公民身份号码："
					+ protocol.getCard_id()
					+ "\n"
					+ "                                                                                                                                      用户名："
					+ protocol.getUsername()
					+ "\n"
					+ "                                                                                                                                      "
					+ DateUtils.dateStr(protocol.getAddtime()) + "\n";

			pdf.addText(tenderContent);
		} else if (type.equals(Constant.REPAYMENT_ACCOUNT_PROTOCOL)) {
			String repayment_accountContent = "怀化金宝汇资产管理有限公司：\n       会员本人   "
					+ protocol.getUsername()
					+ DateUtils.dateStr(protocol.getVerify_time())
					+ "借款"
					+ protocol.getBorrow_account()
					+ "元（见年月日《借款协议书》**号），约定"
					+ DateUtils.dateStr(protocol.getRepayment_time())
					+ "归还本金"
					+ protocol.getRepayment_account()
					+ "元，利息"
					+ protocol.getInterest()
					+ "元，其它0元，共计"
					+ protocol.getMoney()
					+ "元。现本人转入"
					+ protocol.getMoney()
					+ "元至贵公司，委托贵公司代本人逐一归还至出借会员账户中。\n"
					+ "         特此委托\n"
					+ "                                                                                                                                      委托人："
					+ protocol.getRealname()
					+ "\n"
					+ "                                                                                                                                      公民身份号码："
					+ protocol.getCard_id()
					+ "\n"
					+ "                                                                                                                                      用户名："
					+ protocol.getUsername()
					+ "\n"
					+ "                                                                                                                                      "
					+ DateUtils.dateStr(protocol.getAddtime()) + "\n";

			pdf.addText(repayment_accountContent);
		} else if (type.equals(Constant.AWARD_PROTOCOL)) {
			String awardContent = "（"
					+ protocol.getUsername()
					+ "会员）：\n       现转来借款标"
					+ protocol.getBorrowname()
					+ "投资奖励 "
					+ protocol.getMoney()
					+ "元， 其它  0元，共     "
					+ protocol.getMoney()
					+ "  元，己一次性打入了你的账户之中。\n"
					+ "         特此委托\n"
					+ "                                                                                                                                      委托人："
					+ protocol.getRealname()
					+ "\n"
					+ "                                                                                                                                      公民身份号码："
					+ protocol.getCard_id()
					+ "\n"
					+ "                                                                                                                                      用户名："
					+ protocol.getUsername()
					+ "\n"
					+ "                                                                                                                                      "
					+ DateUtils.dateStr(protocol.getAddtime()) + "\n";

			pdf.addText(awardContent);
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

	public String protocolList() {
		String dotime1 = StringUtils.isNull(this.request
				.getParameter("dotime1"));
		String dotime2 = StringUtils.isNull(this.request
				.getParameter("dotime2"));
		String protocol_type = StringUtils.isNull(this.request
				.getParameter("protocol_type"));
		String username = StringUtils.isNull(this.request
				.getParameter("username"));
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		SearchParam param = new SearchParam();
		param.setDotime1(dotime1);
		param.setDotime2(dotime2);
		param.setUsername(username);
		param.setProtocol_type(protocol_type);
		PageDataList plist = this.borrowService.protocolList(param, page);
		setPageAttribute(plist, param);
		return SUCCESS;
	}
}