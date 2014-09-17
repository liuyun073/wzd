package com.rd.dao;

import com.rd.domain.Article;
import com.rd.domain.ArticleField;
import com.rd.domain.Site;
import java.util.List;

public abstract interface ArticleDao {
	public abstract List<Article> getList(String paramString, int paramInt1,
			int paramInt2);

	public abstract int count(String paramString);

	public abstract Article getArticleById(long paramLong);

	public abstract List<Site> getSiteList();

	public abstract List<Site> getSubSiteList(long paramLong);

	public abstract Site getSiteById(long paramLong);

	public abstract List<Site> getSiteByCode(String paramString);

	public abstract void updateSite(Site paramSite);

	public abstract void delSite(long paramLong);

	public abstract void addSite(Site paramSite);

	public abstract Article addArticle(Article paramArticle);

	public abstract int countArticle();

	public abstract List<Article> getArticleList(int paramInt1, int paramInt2);

	public abstract void modifyArticle(Article paramArticle);

	public abstract void delArticle(long paramLong);

	public abstract void batchDel(List<Integer> paramList);

	public abstract void batchOrder(List<Integer> paramList1, List<Integer> paramList2);

	public abstract void batchStatus(List<Integer> paramList, int paramInt);

	public abstract void modifyArticleStatus(Article paramArticle);

	public abstract void addArticleFields(long paramLong, String paramString);

	public abstract List<ArticleField> getArticleFields(long paramLong);

	public abstract void modifyArticleFields(long paramLong, String paramString);

	public abstract void delArticleFields(long paramLong);
}