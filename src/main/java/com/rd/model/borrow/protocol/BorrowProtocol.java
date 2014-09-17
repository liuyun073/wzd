package com.rd.model.borrow.protocol;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;
import com.rd.context.Global;
import com.rd.dao.TenderDao;
import com.rd.domain.Borrow;
import com.rd.domain.Site;
import com.rd.domain.Tender;
import com.rd.domain.User;
import com.rd.domain.Userinfo;
import com.rd.exception.BorrowException;
import com.rd.model.BorrowTender;
import com.rd.model.borrow.BorrowHelper;
import com.rd.model.borrow.BorrowModel;
import com.rd.service.ArticleService;
import com.rd.service.BorrowService;
import com.rd.service.UserService;
import com.rd.service.UserinfoService;
import com.rd.tool.itext.PdfHelper;
import com.rd.util.DateUtils;
import com.rd.util.NumberUtils;
import com.rd.web.action.BorrowAction;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class BorrowProtocol {
	private static Logger logger = Logger.getLogger(BorrowAction.class);
	
	private String siteName;
	private long borrow_id;
	private long tender_id;
	private BorrowService borrowService;
	private UserService userService;
	private ArticleService articleService;
	private String pdfName;
	private String inPdfName;
	private String outPdfName;
	private String downloadFileName;
	private String imageFileName;
	private ServletContext context;
	private BorrowModel borrow;
	private Tender tender;
	private User tenderUser;
	private User borrowUser;
	private PdfHelper pdf;
	private int borrowType;
	private int templateType;
	private TemplateReader templateReader;
	private Map<String, Object> data = new HashMap<String, Object>();

	public BorrowProtocol(User user, long borrow_id, long tender_id,
			int borrowType, int templateType) {
		init(user, borrow_id, tender_id, borrowType, templateType);
	}

	public BorrowProtocol(User user, long borrow_id, long tender_id) {
		init(user, borrow_id, tender_id, 100, 1);
	}

	public String findServerPath() {
		String path = super.getClass().getResource("/").getPath();
		path = path.replaceAll("/WEB-INF/classes/", "/");
		return path;
	}

	private void init(User tenderUser, long borrow_id, long tender_id,
			int borrowType, int templateType) {
		this.borrow_id = borrow_id;
		this.tender_id = tender_id;
		this.tenderUser = tenderUser;
		String contextPath = findServerPath();
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.borrowService = ((BorrowService) ctx.getBean("borrowService"));
		this.userService = ((UserService) ctx.getBean("userService"));
		this.articleService = ((ArticleService) ctx.getBean("articleService"));
		TenderDao tenderDao = (TenderDao) ctx.getBean("tenderDao");
		this.borrow = this.borrowService.getBorrow(borrow_id);
		this.tender = tenderDao.getTenderById(tender_id);
		this.borrowUser = this.userService.getUserById(this.borrow.getUser_id());
		this.downloadFileName = (this.borrow.getId() + ".pdf");
		String timeStr = DateUtils.dateStr(new Date(), "yyyyMMdd");

		this.inPdfName = (contextPath + "data/protocol/" + borrow_id + "_" + tender_id + ".pdf");
		this.imageFileName = (Global.getValue("weburl") + Global.getValue("theme_dir") + "/images/zhang.jpg");

		this.outPdfName = (contextPath + "data/protocol/" + borrow_id + "_" + tender_id + ".pdf");
		this.pdf = PdfHelper.instance(this.inPdfName);
		initBorrowType();
		templateType = 1;
	}

	protected void initBorrowType() {
		this.borrowType = 100;
	}

	protected Map<String, Object> fillProtoclData() {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		UserinfoService userinfoService = (UserinfoService) ctx.getBean("userinfoService");
		Userinfo borrowUserinfo = userinfoService.getUserinfoByUserid(getBorrowUser().getUser_id());
		Userinfo tenderUserinfo = userinfoService.getUserinfoByUserid(getTenderUser().getUser_id());
		this.data.put("webname", Global.getString("webname"));
		this.data.put("borrowProtocolTime", DateUtils.dateStr2(BorrowHelper.getBorrowProtocolTime(this.borrow)));
		this.data.put("borrow", this.borrow);
		this.borrowUser.hideChar();
		this.data.put("borrowUser", this.borrowUser);
		this.data.put("tenderUser", this.tenderUser);
		this.data.put("tender", this.tender);
		this.data.put("borrowUserinfo", borrowUserinfo);
		this.data.put("tenderUserinfo", tenderUserinfo);
		this.data.put("borrowNO", getBorrow().getId());
		Calendar cal = Calendar.getInstance();
		Date date = BorrowHelper.getBorrowRepayTime(getBorrow(), getTender());
		cal.setTime(date);
		this.data.put("payment", Integer.valueOf(cal.get(5)));
		this.data.put("verifyTime", DateUtils.dateStr6(BorrowHelper.getBorrowProtocolTime(this.borrow)));
		this.data.put("repayTime", DateUtils.dateStr6(date));
		this.data.put("roundAccount", Double.valueOf(NumberUtils.format2(NumberUtils.getDouble(this.borrow.getAccount()))));
		cal = null;
		cal = Calendar.getInstance();
		this.data.put("nowYear", Integer.valueOf(cal.get(1)));
		this.data.put("nowMonth", Integer.valueOf(cal.get(2) + 1));
		this.data.put("nowDay", Integer.valueOf(cal.get(5)));
		String stamp = findServerPath() + "data/images/stamp/stamp.jpg";
		this.data.put("stamp", stamp);
		return this.data;
	}

	public static BorrowProtocol getInstance(User user, long borrow_id,
			long tender_id, int borrowType) {
		BorrowProtocol p = null;

		p = new BaseBorrowProtocol(user, borrow_id, tender_id, borrowType, 1);
		return p;
	}

	public int createPdf() throws Exception {
		int size = 0;
		if (this.borrow == null)
			throw new BorrowException("该借款标不存在！");
		readTemplate();
		doTemplate();
		this.pdf.exportPdf();
		return size;
	}

	protected void addPdfTable(PdfHelper pdf, Borrow b)
			throws DocumentException {
		
		List<BorrowTender> list = this.borrowService.getTenderList(this.borrow.getId());
		List<String> cellList = null;
		List[] args = new List[list.size() + 1];

		cellList = new ArrayList<String>();
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
			cellList = new ArrayList<String>();
			cellList.add(t.getUsername());
			cellList.add(t.getAccount());
			cellList.add(b.getTime_limit());
			cellList.add("" + b.getApr());
			cellList.add(DateUtils.dateStr2(BorrowHelper.getBorrowVerifyTime(b,t)));
			Date d = BorrowHelper.getBorrowRepayTime(b, t);
			cellList.add(DateUtils.dateStr2(d));
			cellList.add("每月截止" + DateUtils.getDay(d) + "日");
			cellList.add(t.getRepayment_account());
			args[i] = cellList;
		}
		pdf.addTable(args, 80.0F, 7);
	}

	protected void readTemplate() throws IOException {
		if (getTemplateType() == 1) {
			Site site = this.articleService.getSiteById(32L);
			if ((site == null) || (site.getContent() == null))
				throw new BorrowException("协议模板有误！");

			BufferedReader in = new BufferedReader(new StringReader(site
					.getContent()));
			this.templateReader = new TemplateReader(in);
		} else {
			String file = findServerPath() + "/WEB-INF/classes/"
					+ getBorrowType() + ".html";
			this.templateReader = new TemplateReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
		}
	}

	protected void doTemplate() throws IOException, DocumentException {
		if (this.templateReader.read() == -1)
			;
		this.templateReader.close();
		List<String> elements = this.templateReader.getLines();
		ProtocolValue pv = new ProtocolValue();
		pv.setData(fillProtoclData());
		StringBuffer sb = new StringBuffer();
		for (String e : elements) {
			if ((e != null) && (e.startsWith("${")) && (e.endsWith("}"))) {
				e = e.substring(2, e.length() - 1);
				if (e.equals("tenderTable")) {
					templateHtml(sb.toString());
					sb.setLength(0);
					addPdfTable(this.pdf, this.borrow);
				} else {
					sb.append(pv.printProtocol(e));
				}
			} else {
				sb.append(e);
			}
		}
		templateHtml(sb.toString());
	}

	protected String templateHtml(String str) throws IOException,
			DocumentException {
		
		final List<Element> pdfeleList = new ArrayList<Element>();
		ElementHandler elemH = new ElementHandler() {
			public void add(Writable w) {
				if (w instanceof WritableElement)
					pdfeleList.addAll(((WritableElement) w).elements());
			}
		};
		InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(str.getBytes("UTF-8")), "UTF-8");
		XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);
		List<Element> list = new ArrayList<Element>();
		for (Element ele : pdfeleList)
			if (!(ele instanceof LineSeparator)) {
				if (ele instanceof WritableDirectElement) {
					continue;
				}
				list.add(ele);
			}
		this.pdf.addHtmlList(list);
		return "";
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public long getBorrow_id() {
		return this.borrow_id;
	}

	public void setBorrow_id(long borrow_id) {
		this.borrow_id = borrow_id;
	}

	public long getTender_id() {
		return this.tender_id;
	}

	public void setTender_id(long tender_id) {
		this.tender_id = tender_id;
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

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public String getPdfName() {
		return this.pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getInPdfName() {
		return this.inPdfName;
	}

	public void setInPdfName(String inPdfName) {
		this.inPdfName = inPdfName;
	}

	public String getOutPdfName() {
		return this.outPdfName;
	}

	public void setOutPdfName(String outPdfName) {
		this.outPdfName = outPdfName;
	}

	public String getDownloadFileName() {
		return this.downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public ServletContext getContext() {
		return this.context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public BorrowModel getBorrow() {
		return this.borrow;
	}

	public void setBorrow(BorrowModel borrow) {
		this.borrow = borrow;
	}

	public User getTenderUser() {
		return this.tenderUser;
	}

	public void setTenderUser(User tenderUser) {
		this.tenderUser = tenderUser;
	}

	public User getBorrowUser() {
		return this.borrowUser;
	}

	public void setBorrowUser(User borrowUser) {
		this.borrowUser = borrowUser;
	}

	public PdfHelper getPdf() {
		return this.pdf;
	}

	public void setPdf(PdfHelper pdf) {
		this.pdf = pdf;
	}

	public Tender getTender() {
		return this.tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}

	public int getBorrowType() {
		return this.borrowType;
	}

	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}

	public int getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}