package com.rd.web.action.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.rd.domain.Site;
import com.rd.model.tree.Tree;
import com.rd.service.ArticleService;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import com.rd.web.action.BaseAction;

public class ManageSiteAction extends BaseAction implements ModelDriven<Site> {
	ArticleService articleService;
	Site model = new Site();

	public Site getModel() {
		return this.model;
	}

	public ArticleService getArticleService() {
		return this.articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public String siteTree() {
		this.response.setContentType("text/json;charset=UTF-8");

		return null;
	}

	public String showSite() throws Exception {
		Site site = this.articleService.getSiteById(this.model.getSite_id());
		if (site != null) {
			Site psite = this.articleService.getSiteById(site.getPid());
			this.request.setAttribute("site", site);
			if (psite == null) {
				psite = new Site();
				psite.setName("根目录");
				psite.setPid(0);
			}
			this.request.setAttribute("psite", psite);
		}
		return SUCCESS;
	}

	public String addSite() throws Exception {
		String actionType = this.request.getParameter("actionType");
		long pid = NumberUtils.getLong(this.request.getParameter("pid"));
		if (!StringUtils.isBlank(actionType)) {
			this.articleService.addSite(this.model);
			Tree<Site> tree = this.articleService.getSiteTree();
			this.context.setAttribute("tree", tree);
			message("新增栏目成功！", "/admin/article/showSite.html");
			return ADMINMSG;
		}

		Site psite = this.articleService.getSiteById(pid);
		if (psite == null) {
			psite = new Site();
			psite.setName("根目录");
			psite.setPid(0);
		}
		Tree<Site> tree = this.articleService.getSiteTree();
		this.context.setAttribute("tree", tree);
		this.request.setAttribute("psite", psite);
		return SUCCESS;
	}

	public String delSite() throws Exception {
		long pid = NumberUtils.getLong(this.request.getParameter("pid"));
		Site psite = this.articleService.getSiteById(pid);
		if ((psite != null) && (pid > 0L)) {
			this.articleService.delSite(psite.getSite_id());
			Tree<Site> tree = this.articleService.getSiteTree();
			this.context.setAttribute("tree", tree);
			message("删除成功", "/admin/article/showSite.html");
		} else {
			message("非法操作！", "/admin/article/showSite.html");
		}
		return SUCCESS;
	}

	public String modifySite() throws Exception {
		this.articleService.modifySite(this.model);
		Tree<Site> tree = this.articleService.getSiteTree();
		this.context.setAttribute("tree", tree);
		message("修改栏目成功！", "/admin/article/showSite.html");
		return ADMINMSG;
	}
}