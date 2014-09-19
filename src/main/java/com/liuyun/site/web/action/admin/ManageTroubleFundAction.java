package com.liuyun.site.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.liuyun.site.common.enums.EnumTroubleFund;
import com.liuyun.site.domain.Article;
import com.liuyun.site.domain.ScrollPic;
import com.liuyun.site.domain.TroubleDonateRecord;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.service.TroubleFundService;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import com.liuyun.site.web.action.BaseAction;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

public class ManageTroubleFundAction extends BaseAction implements
		ModelDriven<Article> {
	private static final Logger logger = Logger
			.getLogger(ManageTroubleFundAction.class);
	private TroubleFundService troubleFundService;
	ArticleService articleService;
	Article model = new Article();
	List<Integer> bid;
	List<Integer> hid;
	List<Integer> orders;
	int type;
	File files;
	String filesFileName;
	ScrollPic sp = new ScrollPic();
	File pic;
	private String filePath;
	private String sep = File.separator;

	public TroubleFundService getTroubleFundService() {
		return this.troubleFundService;
	}

	public void setTroubleFundService(TroubleFundService troubleFundService) {
		this.troubleFundService = troubleFundService;
	}

	public String getSep() {
		return this.sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public File getPic() {
		return this.pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public ScrollPic getSp() {
		return this.sp;
	}

	public void setSp(ScrollPic sp) {
		this.sp = sp;
	}

	public Article getModel() {
		return this.model;
	}

	public List<Integer> getBid() {
		return this.bid;
	}

	public void setBid(List<Integer> bid) {
		this.bid = bid;
	}

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public List<Integer> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Integer> orders) {
		this.orders = orders;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public File getFiles() {
		return this.files;
	}

	public void setFiles(File files) {
		this.files = files;
	}

	public String getFilesFileName() {
		return this.filesFileName;
	}

	public void setFilesFileName(String filesFileName) {
		this.filesFileName = filesFileName;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public String addTroubleFundArticle() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			if (this.model.getSite_id() < EnumTroubleFund.FIRST.getValue()) {
				message("请选择栏目!", "/admin/article/addArticle.html");
				return ADMINMSG;
			}
			fillArticle(this.model);

			String newUrl = "";
			if (this.files != null) {
				String newFileName = generateUploadFilename(this.filesFileName);
				try {
					newUrl = upload(this.files, "", "/data/upload", newFileName);
				} catch (Exception e) {
					logger.error("上传文件出错：" + e.getMessage());
				}
			}
			this.model.setLitpic(newUrl);
			this.articleService.addArticle(this.model, newUrl);
			message("新增文章成功！", "/admin/article/showArticle.html");
			return ADMINMSG;
		}
		return SUCCESS;
	}

	public String troubleFund() {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		String actionType = StringUtils.isNull(this.request
				.getParameter("type"));
		int id = NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("id")));
		this.request.setAttribute("actionType", actionType);
		if (StringUtils.isBlank(actionType)) {
			if (id == EnumTroubleFund.ZERO.getValue()) {
				return SUCCESS;
			}
			TroubleDonateRecord troubleDonateRecord = this.troubleFundService
					.getTroubleDonateById(id);
			this.request.setAttribute("troubleDonateRecord",
					troubleDonateRecord);
			return SUCCESS;
		}

		if ("add".equals(actionType)) {
			TroubleDonateRecord t = getParater();
			if (StringUtils.isBlank(t.getBorrow_use())) {
				message("用途不能为空",
						"/admin/troublefund/troubleFund.html?type=add");
				return ADMINMSG;
			}
			if (t.getMoney() == 0.0D) {
				message("金额不能为空",
						"/admin/troublefund/troubleFund.html?type=add");
				return ADMINMSG;
			}
			this.troubleFundService.addTroubleDonate(t);
			message("操作成功!", "/admin/troublefund/troubleFund.html?type=list");
			return ADMINMSG;
		}
		if ("update".equals(actionType)) {
			TroubleDonateRecord t = getParater();
			if (StringUtils.isBlank(t.getBorrow_use())) {
				message("用途不能为空",
						"/admin/troublefund/troubleFund.html?type=add");
				return ADMINMSG;
			}
			if (t.getMoney() == 0.0D) {
				message("金额不能为空",
						"/admin/troublefund/troubleFund.html?type=add");
				return ADMINMSG;
			}
			this.troubleFundService.updateTroubleDonate(t);
			message("操作成功!", "/admin/troublefund/troubleFund.html?type=list");
			return ADMINMSG;
		}
		if ("list".equals(actionType)) {
			PageDataList plist = this.troubleFundService.getTroubleDonateList(
					EnumTroubleFund.SECOND.getValue(), page);
			this.request.setAttribute("page", plist.getPage());
			this.request.setAttribute("troubleDonateList", plist.getList());
			this.request.setAttribute("param", new SearchParam().toMap());

			return SUCCESS;
		}
		return SUCCESS;
	}

	private TroubleDonateRecord getParater() {
		TroubleDonateRecord t = new TroubleDonateRecord();
		String use = StringUtils
				.isNull(this.request.getParameter("borrow_use"));
		String borrow_content = StringUtils.isNull(this.request
				.getParameter("borrow_content"));
		String repayment_time = StringUtils.isNull(this.request
				.getParameter("repayment_time"));
		String remark = StringUtils.isNull(this.request.getParameter("remark"));
		double money = NumberUtils.getDouble(StringUtils.isNull(this.request
				.getParameter("money")));
		long status = NumberUtils.getLong(StringUtils.isNull(this.request
				.getParameter("status")));
		int id = NumberUtils.getInt(StringUtils.isNull(this.request
				.getParameter("id")));
		t.setBorrow_use(use);
		t.setMoney(money);
		t.setStatus(status);
		t.setUser_id(EnumTroubleFund.FIRST.getValue());
		t.setBorrow_content(borrow_content);
		t.setRepayment_time(repayment_time);
		t.setRemark(remark);
		t.setId(id);
		return t;
	}

	private Article fillArticle(Article model) {
		model.setAddtime(getTimeStr());
		model.setAddip(getRequestIp());
		model.setUser_id(EnumTroubleFund.FIRST.getValue());
		model.setAddtime(getTimeStr());
		model.setAddip(getRequestIp());
		return model;
	}
}