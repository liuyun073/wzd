package com.rd.web.action;

import com.rd.context.Global;
import com.rd.domain.Article;
import com.rd.domain.Site;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.service.ArticleService;
import com.rd.service.UserService;
import com.rd.tool.Page;
import com.rd.util.NumberUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ArticleAction extends BaseAction {
	private ArticleService articleService;
	private UserService userService;

	public String list() throws Exception {
		String catalog = this.request.getParameter("catalog");
		int page = NumberUtils.getInt(this.request.getParameter("page"));
		PageDataList plist = this.articleService.getList(catalog, page);
		long cid = NumberUtils.getLong(catalog);
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", catalog);
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());
		setPageAttribute(plist, new SearchParam());
		return "list";
	}

	public String detail() throws Exception {
		String catalog = this.request.getParameter("catalog");
		long id = NumberUtils.getLong(this.request.getParameter("id"));
		Article article = this.articleService.getArticle(id);
		if (article == null) {
			return NOTFOUND;
		}
		long cid = NumberUtils.getLong(catalog);
		Site site = this.articleService.getSiteById(cid);
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("article", article);
		this.request.setAttribute("catalog", catalog);
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());
		return "detail";
	}

	private String getPageStr(String catalog, Page p) {
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

		String typestr = "&catalog=" + catalog;

		for (int i = 0; i < dispayPage.length; ++i) {
			if (dispayPage[i] == currentPage)
				sb
						.append(" <span class='this_page'>" + currentPage
								+ "</span>");
			else {
				sb.append(" <a href='list.html?page=" + dispayPage[i] + typestr
						+ "'>" + dispayPage[i] + "</a>");
			}
		}
		return sb.toString();
	}

	public String onlinekefu() throws Exception {
		List list = this.userService.getKfList();
		this.request.setAttribute("kflist", list);

		long cid = 98L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());
		return SUCCESS;
	}

	public String jin_culture() throws Exception {
		int pageKf = NumberUtils.getInt(Global.getValue("index_kf_num"));
		pageKf = (pageKf > 0) ? pageKf : 8;
		List list = this.userService.getKfListWithLimit(0, pageKf);
		this.request.setAttribute("kflist", list);

		long cid = 126L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		int page1 = NumberUtils.getInt(Global.getValue("index_jinculture_num"));
		page1 = (page1 > 0) ? page1 : 15;
		List jswh = this.articleService.getList("126", 0, page1);
		this.request.setAttribute("jswh", jswh);

		int page2 = NumberUtils.getInt(Global.getValue("index_xsrm_num"));
		List xsrm = this.articleService.getList("131", 0, page2);
		this.request.setAttribute("xsrm", xsrm);
		this.request.setAttribute("nid", "jin_culture");
		return SUCCESS;
	}

	public String jin_culture_content() throws Exception {
		long cid = 126L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		return SUCCESS;
	}

	public String pledge_introduction() throws Exception {
		long cid = 127L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		return SUCCESS;
	}

	public String pledge_introduction_content() throws Exception {
		long cid = 127L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		return SUCCESS;
	}

	public String security() throws Exception {
		long cid = Global.getInt("security");
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(cid);
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		return SUCCESS;
	}

	public String guolin_special() throws Exception {
		long cid = 124L;
		Site site = this.articleService.getSiteById(cid);
		if (site == null) {
			return NOTFOUND;
		}
		Site psite = this.articleService.getSiteById(site.getPid());
		if (psite == null) {
			psite = new Site();
			psite.setSite_id(0L);
			psite.setName("首页");
		}
		List sublist = this.articleService.getSubSiteList(site.getPid());
		this.request.setAttribute("catalog", Long.valueOf(cid));
		this.request.setAttribute("psite", psite);
		this.request.setAttribute("site", site);
		this.request.setAttribute("sublist", sublist);
		this.request.setAttribute("nid", psite.getNid());

		return SUCCESS;
	}

	public String getCatalogName(String catalog) {
		String name = "";
		if ("59".equals(catalog))
			name = "媒体报道";
		else if ("22".equals(catalog))
			name = "最新公告";
		else if ("63".equals(catalog))
			name = "商业模式";
		else if ("2".equals(catalog))
			name = "关于我们";
		else if ("57".equals(catalog))
			name = "借贷费用";
		else if ("3".equals(catalog))
			name = "政策法规";
		else if ("64".equals(catalog))
			name = "工具箱";
		else if ("71".equals(catalog))
			name = "下载中心";
		else if ("12".equals(catalog))
			name = "网站规则";
		else if ("9".equals(catalog))
			name = "新手上路";
		else if ("69".equals(catalog))
			name = "关于我们";
		else if ("11".equals(catalog))
			name = "常见问题";
		else if ("6".equals(catalog))
			name = "如何投资";
		else if ("67".equals(catalog)) {
			name = "如何贷款";
		}
		return name;
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
}