package com.liuyun.site.service.impl;

import com.liuyun.site.dao.ArticleDao;
import com.liuyun.site.dao.ScrollPicDao;
import com.liuyun.site.domain.Article;
import com.liuyun.site.domain.ArticleField;
import com.liuyun.site.domain.ScrollPic;
import com.liuyun.site.domain.Site;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.tree.SiteTree;
import com.liuyun.site.model.tree.Tree;
import com.liuyun.site.service.ArticleService;
import com.liuyun.site.tool.Page;
import java.util.ArrayList;
import java.util.List;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class ArticleServiceImpl implements ArticleService {
	ArticleDao articleDao;
	ScrollPicDao scrollPicDao;

	public ScrollPicDao getScrollPicDao() {
		return this.scrollPicDao;
	}

	public void setScrollPicDao(ScrollPicDao scrollPicDao) {
		this.scrollPicDao = scrollPicDao;
	}

	public Article getArticle(long id) {
		Article b = this.articleDao.getArticleById(id);
		return b;
	}

	public ArticleDao getArticleDao() {
		return this.articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public int count(String catalog) {
		return this.articleDao.count(catalog);
	}

	public List<Article> getList(String catalog, int start, int end) {
		List<Article> mbList = this.articleDao.getList(catalog, start, end);
		return mbList;
	}

	public PageDataList getList(String catalog, int page) {
		int total = this.articleDao.count(catalog);
		Page p = new Page(total, page);
		List<Article> mbList = this.articleDao.getList(catalog, p.getStart(), p.getEnd());
		PageDataList plist = new PageDataList(p, mbList);
		return plist;
	}

	public SiteTree getSiteTree() {
		List<Site> list = this.articleDao.getSiteList();
		SiteTree tree = new SiteTree(null, new ArrayList<Tree<Site>>());
		for (Site s : list) {
			SiteTree secTree = new SiteTree(s, new ArrayList<Tree<Site>>());
			List<Site> sublist = this.articleDao.getSubSiteList(s.getSite_id());
			for (Site ss : sublist) {
				SiteTree subTree = new SiteTree(ss, null);
				secTree.addChild(subTree);
			}
			tree.addChild(secTree);
		}
		return tree;
	}

	public List<Site> getSubSiteList(long id) {
		return this.articleDao.getSubSiteList(id);
	}

	public Site getSiteById(long id) {
		return this.articleDao.getSiteById(id);
	}

	public Site getSiteByCode(String code) {
		List<Site> list = this.articleDao.getSiteByCode(code);
		if ((list != null) && (list.size() > 0))
			return list.get(0);
		return null;
	}

	public void modifySite(Site s) {
		this.articleDao.updateSite(s);
	}

	public void delSite(long id) {
		this.articleDao.delSite(id);
	}

	public void addSite(Site s) {
		this.articleDao.addSite(s);
	}

	public void addArticle(Article a, String files) {
		Article newArticle = this.articleDao.addArticle(a);
		if (!files.isEmpty())
			this.articleDao.addArticleFields(newArticle.getId(), files);
	}

	public PageDataList getArticleList(int page) {
		int total = this.articleDao.countArticle();
		Page p = new Page(total, page);
		List<Article> list = this.articleDao.getArticleList(p.getStart(), p.getPernum());
		return new PageDataList(p, list);
	}

	public void modifyArticle(Article a, String files) {
		this.articleDao.modifyArticle(a);
		if (!StringUtils.isBlank(files)) {
			List<ArticleField> list = this.articleDao.getArticleFields(a.getId());
			if ((list != null) && (list.size() > 0))
				this.articleDao.modifyArticleFields(a.getId(), files);
			else
				this.articleDao.addArticleFields(a.getId(), files);
		}
	}

	public void delArticle(long id) {
		this.articleDao.delArticle(id);
	}

	public void batchOperateArticle(int type, List<Integer> bid, List<Integer> order) {
		switch (type) {
		case 1:
			this.articleDao.batchOrder(bid, order);
			break;
		case 2:
			this.articleDao.batchStatus(bid, 1);
			break;
		case 3:
			this.articleDao.batchStatus(bid, 0);
			break;
		case 6:
			this.articleDao.batchDel(bid);
		case 4:
		case 5:
		}
	}

	public void modifyArticleStatus(Article a) {
		this.articleDao.modifyArticleStatus(a);
	}

	public List<ArticleField> getArticleFileds(long aid) {
		return this.articleDao.getArticleFields(aid);
	}

	public List<ScrollPic> getScrollPicList(int start, int end) {
		return this.scrollPicDao.getList(start, end);
	}

	public void delScrollPic(long id) {
		this.scrollPicDao.delScrollPic(id);
	}

	public void modifyScrollPic(ScrollPic sp) {
		this.scrollPicDao.modifyScrollPic(sp);
	}

	public ScrollPic getScrollPicListById(long id) {
		return this.scrollPicDao.getScrollPicById(id);
	}

	public ScrollPic addScrollPic(ScrollPic sp) {
		return this.scrollPicDao.addScrollPic(sp);
	}
}