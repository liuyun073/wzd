package com.rd.service;

import com.rd.domain.Article;
import com.rd.domain.ArticleField;
import com.rd.domain.ScrollPic;
import com.rd.domain.Site;
import com.rd.model.PageDataList;
import com.rd.model.tree.SiteTree;
import java.util.List;

public abstract interface ArticleService {
	public abstract PageDataList getList(String paramString, int paramInt);

	public abstract int count(String paramString);

	public abstract Article getArticle(long paramLong);

	public abstract SiteTree getSiteTree();

	public abstract Site getSiteById(long paramLong);

	public abstract Site getSiteByCode(String paramString);

	public abstract List getSubSiteList(long paramLong);

	public abstract void modifySite(Site paramSite);

	public abstract void delSite(long paramLong);

	public abstract void addSite(Site paramSite);

	public abstract void addArticle(Article paramArticle, String paramString);

	public abstract List<Article> getList(String paramString, int paramInt1,
			int paramInt2);

	public abstract PageDataList getArticleList(int paramInt);

	public abstract void modifyArticle(Article paramArticle, String paramString);

	public abstract void delArticle(long paramLong);

	public abstract void modifyArticleStatus(Article paramArticle);

	public abstract void batchOperateArticle(int paramInt, List<Integer> paramList1,
			List<Integer> paramList2);

	public abstract List<ArticleField> getArticleFileds(long paramLong);

	public abstract List<ScrollPic> getScrollPicList(int paramInt1,
			int paramInt2);

	public abstract void delScrollPic(long paramLong);

	public abstract void modifyScrollPic(ScrollPic paramScrollPic);

	public abstract ScrollPic getScrollPicListById(long paramLong);

	public abstract ScrollPic addScrollPic(ScrollPic paramScrollPic);
}