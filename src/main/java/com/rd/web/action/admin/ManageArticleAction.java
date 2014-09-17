package com.rd.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.Article;
import com.rd.domain.ArticleField;
import com.rd.domain.ScrollPic;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.ArticleService;
import com.rd.util.DateUtils;
import com.rd.util.ImageUtil;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： ManageArticleAction   
 * 类描述： 后台管理--栏目、文章管理模块    
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Nov 4, 2013 12:19:54 AM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Nov 4, 2013 12:19:54 AM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class ManageArticleAction extends BaseAction implements
		ModelDriven<Article> {
	private static final Logger logger = Logger
			.getLogger(ManageArticleAction.class);
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

	public String addArticle() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			if (this.model.getSite_id() < 1L) {
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

	public String addRiskReportArticle() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			if (this.model.getSite_id() < 1L) {
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

	public String addTroubleFundArticle() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			if (this.model.getSite_id() < 1L) {
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

	private Article fillArticle(Article model) {
		model.setAddtime(getTimeStr());
		model.setAddip(getRequestIp());
		model.setUser_id(1L);
		model.setAddtime(getTimeStr());
		model.setAddip(getRequestIp());

		return model;
	}

	public String batchOperateArticle() throws Exception {
		logger.debug("Param:" + this.bid);
		if ((this.bid == null) || (this.bid.size() < 1)) {
			message("请选择需要操作的文章！", "/admin/article/showArticle.html");
			return ADMINMSG;
		}
		getBatchParams();
		this.articleService.batchOperateArticle(this.type, this.bid, this.orders);
		message(returnMsg(this.type), "/admin/article/showArticle.html");
		return ADMINMSG;
	}

	private void getBatchParams() {
		List<Integer> newOrders = new ArrayList<Integer>();
		for (int i = 0; i < this.bid.size(); ++i) {
			for (int j = 0; j < this.bid.size(); ++j) {
				if (this.bid.get(j) == this.bid.get(i)) {
					newOrders.add(this.orders.get(j));
				}
			}
		}
		this.orders = newOrders;
	}

	public String showArticle() throws Exception {
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		PageDataList plist = this.articleService.getArticleList(page);
		setPageAttribute(plist, new SearchParam());
		return SUCCESS;
	}

	public String modifyArticle() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		//ScrollPic sp = this.articleService.getScrollPicListById(id);
		if (!StringUtils.isBlank(actionType)) {
			this.model.setAddtime(getTimeStr());
			this.model.setAddip(getRequestIp());
			this.model.setUser_id(1L);
			this.model.setAddtime(getTimeStr());
			this.model.setAddip(getRequestIp());

			String newFileName = generateUploadFilename(this.filesFileName);
			String newUrl = "";
			try {
				newUrl = upload(this.files, "", "/data/upload", newFileName);
			} catch (Exception e) {
				logger.error("上传文件出错：" + e.getMessage());
			}
			this.model.setLitpic(newUrl);
			this.articleService.modifyArticle(this.model, newUrl);
			message("修改文章成功！", "/admin/article/showArticle.html");
			return ADMINMSG;
		}
		Article a = this.articleService.getArticle(id);
		List<ArticleField> files = this.articleService.getArticleFileds(a.getId());
		this.request.setAttribute("a", a);
		this.request.setAttribute("files", files);
		return SUCCESS;
	}

	public String delArticle() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		this.articleService.delArticle(id);
		message("删除文章成功！", "/admin/article/showArticle.html");
		return ADMINMSG;
	}

	public String showScrollPic() throws Exception {
		List<ScrollPic> list = this.articleService.getScrollPicList(0, 7);
		this.request.setAttribute("list", list);
		return SUCCESS;
	}

	public String modifyScrollPic() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		if (!StringUtils.isBlank(actionType)) {
			fillScrollPic(this.sp);
			Boolean isImage = ImageUtil.fileIsImage(this.pic);
			if (this.pic == null) {
				ScrollPic scrollPic = this.articleService
						.getScrollPicListById(id);
				this.sp.setPic(scrollPic.getPic());
			} else {
				if (!isImage.booleanValue()) {
					message("上传图片格式不正确！请重新上传！",
							"/admin/article/showScrollPic.html");
					return ADMINMSG;
				}
				moveFile(this.sp);
				this.sp.setPic(this.filePath);
			}
			this.articleService.modifyScrollPic(this.sp);
			message("修改图片成功！", "/admin/article/showScrollPic.html");
			return ADMINMSG;
		}
		ScrollPic scrollPic = this.articleService.getScrollPicListById(id);
		this.request.setAttribute("scrollPic", scrollPic);
		return SUCCESS;
	}

	public String deleteScrollPic() throws Exception {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		this.articleService.delScrollPic(id);
		message("删除图片成功！", "/admin/article/showScrollPic.html");
		return ADMINMSG;
	}

	public String addScrollPic() throws Exception {
		String actionType = StringUtils.isNull(this.request
				.getParameter("actionType"));
		if (!StringUtils.isBlank(actionType)) {
			fillScrollPic(this.sp);
			logger.info(this.pic);
			Boolean isImage = ImageUtil.fileIsImage(this.pic);
			if (this.pic == null) {
				message("你上传的图片为空", "/admin/article/addScrollPic.html");
				return MSG;
			}
			if (!isImage.booleanValue()) {
				message("你上传的图片格式不符合要求，请重新上传",
						"/admin/article/addScrollPic.html");
				return ADMINMSG;
			}
			moveFile(this.sp);
			this.sp.setPic(this.filePath);
			ScrollPic scrollPic = this.articleService.addScrollPic(this.sp);
			this.request.setAttribute("scrollPic", scrollPic);
			message("添加图片成功！", "/admin/article/showScrollPic.html");
			return ADMINMSG;
		}

		return SUCCESS;
	}

	private ScrollPic fillScrollPic(ScrollPic sp) {
		sp.setId(NumberUtils.getLong(this.request.getParameter("id")));
		sp.setSort(NumberUtils.getInt(this.request.getParameter("sort")));
		sp.setUrl(this.request.getParameter("url"));
		sp.setPic(this.request.getParameter("pic"));
		return sp;
	}

	private String truncatUrl(String old, String truncat) {
		String url = "";
		url = old.replace(truncat, "");
		url = url.replace(this.sep, "/");
		return url;
	}

	private void moveFile(ScrollPic scrollPic) {
		String dataPath = ServletActionContext.getServletContext().getRealPath(
				"/data");
		String contextPath = ServletActionContext.getServletContext()
				.getRealPath("/");
		Date d1 = new Date();
		String upfiesDir = dataPath + this.sep + "upfiles" + this.sep
				+ "images" + this.sep;
		String destfilename1 = upfiesDir + DateUtils.dateStr2(d1) + this.sep
				+ scrollPic.getId() + "_scrollPic" + "_" + d1.getTime()
				+ ".jpg";
		this.filePath = destfilename1;
		this.filePath = truncatUrl(this.filePath, contextPath);
		logger.info(destfilename1);
		File imageFile1 = null;
		try {
			imageFile1 = new File(destfilename1);
			FileUtils.copyFile(this.pic, imageFile1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	private String returnMsg(int type) {
		String msg = "";
		switch (type) {
		case 1:
			msg = "成功执行批量排序";
			break;
		case 2:
			msg = "成功执行批量显示";
			break;
		case 3:
			msg = "成功执行批量隐藏";
			break;
		case 6:
			msg = "成功执行批量删除";
		case 4:
		case 5:
		}
		return msg;
	}

	public String modifyArticleStatus() {
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		int status = NumberUtils.getInt(this.request.getParameter("status"));
		Article article = new Article();
		article.setId(id);
		if (status == 1) {
			article.setStatus(0);
		}
		if (status == 0) {
			article.setStatus(1);
		}
		this.articleService.modifyArticleStatus(article);
		message("修改状态成功！", "/admin/article/showArticle.html");
		return ADMINMSG;
	}

	public List<Integer> getHid() {
		return this.hid;
	}

	public void setHid(List<Integer> hid) {
		this.hid = hid;
	}
}