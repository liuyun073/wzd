package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.ArticleDao;
import com.liuyun.site.dao.jdbc.mapper.ArticleMapper;
import com.liuyun.site.dao.jdbc.mapper.ArticleSiteMapper;
import com.liuyun.site.dao.jdbc.mapper.SiteMapper;
import com.liuyun.site.domain.Article;
import com.liuyun.site.domain.ArticleField;
import com.liuyun.site.domain.Site;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao {
	private static Logger logger = Logger.getLogger(ArticleDaoImpl.class);

	String queryArticleSql = " from dw_article a  left join dw_site s on s.site_id=a.site_id where 1=1";

	public List<Article> getList(String catalog, int start, int end) {
		long s = System.currentTimeMillis();
		String sql = "select  * from dw_article where 1=1 and site_id=? order by publish desc limit " + start + "," + end;

		logger.debug("SQL:" + sql);
		List<Article> list = new ArrayList<Article>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { catalog }, new ArticleMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		long e = System.currentTimeMillis();
		logger.info("GetArticleList Cost Time:" + (e - s));
		return list;
	}

	public Article getArticleById(long id) {
		String sql = "select * from dw_article where id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + id);
		Article article = null;
		try {
			article = (Article) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new ArticleMapper());
		} catch (DataAccessException e) {
			logger.debug("BorrowDao.getBorrowById,数据库查询结果为空！");
		}
		return article;
	}

	public int count(String catalog) {
		int total = 0;
		String sql = "select count(*) from dw_article where 1=1 and site_id=?";

		logger.debug("SQL:" + sql);
		try {
			total = getJdbcTemplate().queryForInt(sql, new Object[] { catalog });
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public List<Site> getSiteList() {
		String sql = "select * from dw_site where pid=0 order by `order`";
		List<Site> list = new ArrayList<Site>();
		list = getJdbcTemplate().query(sql, new SiteMapper());
		return list;
	}

	public List<Site> getSubSiteList(long pid) {
		String sql = "select * from dw_site where pid=? order by `order`";
		List<Site> list = new ArrayList<Site>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(pid) }, new SiteMapper());
		return list;
	}

	public Site getSiteById(long id) {
		String sql = "select * from dw_site where site_id=?";
		Site s = null;
		try {
			s = (Site) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new SiteMapper());
		} catch (DataAccessException e) {
			logger.debug("getSiteById():" + e.getMessage());
		}
		return s;
	}

	public List<Site> getSiteByCode(String code) {
		String sql = "select * from dw_site where code=?";
		List<Site> list = new ArrayList<Site>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { code }, new SiteMapper());
		} catch (DataAccessException e) {
			logger.debug("getSiteById():" + e.getMessage());
		}
		return list;
	}

	public void updateSite(Site s) {
		String sql = "update dw_site as site set site.name=?,site.url=?,site.nid=?,site.status=?,site.code=?,site.order=?,site.description=?,site.content=?,site.style=? where site.site_id=?";

		getJdbcTemplate().update(sql,
				new Object[] { s.getName(), s.getUrl(), s.getNid(),
						Integer.valueOf(s.getStatus()), s.getCode(),
						Integer.valueOf(s.getOrder()), s.getDescription(),
						s.getContent(), s.getStyle(),
						Long.valueOf(s.getSite_id()) });
	}

	public void delSite(long id) {
		String sql = "delete from dw_site where site_id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public void addSite(Site s) {
		String sql = "insert into dw_site(pid,name,url,nid,status,code,description,content) values(?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(s.getPid()), s.getName(),
						s.getUrl(), s.getNid(), Integer.valueOf(s.getStatus()),
						s.getCode(), s.getDescription(), s.getContent() });
	}

	public Article addArticle(Article article) {
		final String sql = "insert into dw_article(site_id,name,littitle,status,litpic,flag,source,publish,author,summary,content,`order`,hits,user_id,addtime,addip) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		final Article a = article;

		KeyHolder keyHolder = new GeneratedKeyHolder();

		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, a.getSite_id());
				ps.setString(2, a.getName());
				ps.setString(3, a.getLittitle());
				ps.setInt(4, a.getStatus());
				ps.setString(5, a.getLitpic());
				ps.setString(6, a.getFlag());
				ps.setString(7, a.getSource());
				ps.setString(8, a.getPublish());
				ps.setString(9, a.getAuthor());
				ps.setString(10, a.getSummary());
				ps.setString(11, a.getContent());
				ps.setInt(12, a.getOrder());
				ps.setInt(13, a.getHits());
				ps.setLong(14, a.getUser_id());
				ps.setString(15, a.getAddtime());
				ps.setString(16, a.getAddip());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		a.setId(key);
		return a;
	}

	public int countArticle() {
		String selectSql = "select count(a.id) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.queryArticleSql);
		String sql = sb.toString();
		logger.debug("countArticle():" + sql);
		int total = 0;
		total = count(sql, new Object[0]);
		return total;
	}

	public List<Article> getArticleList(int start, int pernum) {
		String selectSql = "select a.*,s.name as sitename ";
		String orderSql = " order by a.publish desc,a.id desc,a.addtime desc";
		String limitSql = " limit ?,?";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.queryArticleSql).append(orderSql).append(limitSql);
		String sql = sb.toString();
		logger.debug("getArticleList():" + sql);
		List<Article> list = new ArrayList<Article>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new ArticleSiteMapper());
		return list;
	}

	public void modifyArticle(Article a) {
		String sql = "update dw_article set site_id=?,name=?,littitle=?,status=?,litpic=?,flag=?,source=?,publish=?,author=?,summary=?,content=?,`order`=?,hits=?,user_id=?,addtime=?,addip=? where id=?";

		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(a.getSite_id()), a.getName(),
						a.getLittitle(), Integer.valueOf(a.getStatus()),
						a.getLitpic(), a.getFlag(), a.getSource(),
						a.getPublish(), a.getAuthor(), a.getSummary(),
						a.getContent(), Integer.valueOf(a.getOrder()),
						Integer.valueOf(a.getHits()),
						Long.valueOf(a.getUser_id()), a.getAddtime(),
						a.getAddip(), Long.valueOf(a.getId()) });
	}

	public void delArticle(long id) {
		String sql = "delete from dw_article where id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public void batchDel(List<Integer> bid) {
		String sql = "delete from dw_article where id=?";
		List<Object[]> argList = new ArrayList<Object[]>();
		for (int i = 0; i < bid.size(); ++i) {
			Object[] args = { bid.get(i) };
			argList.add(args);
		}
		getJdbcTemplate().batchUpdate(sql, argList);
	}

	public void batchOrder(List<Integer> bid, List<Integer> order) {
		String sql = "update dw_article set `order`=? where id=?";
		List<Object[]> argList = new ArrayList<Object[]>();
		for (int i = 0; i < bid.size(); ++i) {
			Object[] args = { order.get(i), bid.get(i) };
			argList.add(args);
		}
		getJdbcTemplate().batchUpdate(sql, argList);
	}

	public void batchStatus(List<Integer> bid, int status) {
		String sql = "update dw_article set status=? where id=?";
		List<Object[]> argList = new ArrayList<Object[]>();
		for (int i = 0; i < bid.size(); ++i) {
			Object[] args = { Integer.valueOf(status), bid.get(i) };
			argList.add(args);
		}
		getJdbcTemplate().batchUpdate(sql, argList);
	}

	public void modifyArticleStatus(Article a) {
		String sql = "update dw_article set status=? where id=?";
		getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(a.getStatus()), Long.valueOf(a.getId()) });
	}

	public void addArticleFields(long aid, String files) {
		String sql = "insert into  dw_article_fields(aid,files) values(?,?)";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(aid), files });
	}

	public List<ArticleField> getArticleFields(long aid) {
		String sql = "select * from dw_article_fields where aid=?";
		List<ArticleField> list = new ArrayList<ArticleField>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(aid) },
				new RowMapper<ArticleField>() {
					public ArticleField mapRow(ResultSet rs, int num)
							throws SQLException {
						ArticleField f = new ArticleField();
						f.setAid(rs.getLong("aid"));
						f.setFiles(rs.getString("files"));
						return f;
					}
				});
		return list;
	}

	public void modifyArticleFields(long aid, String files) {
		String sql = "update dw_article_fields set files=? where aid=?";
		getJdbcTemplate().update(sql, new Object[] { files, Long.valueOf(aid) });
	}

	public void delArticleFields(long aid) {
		String sql = "delete from  dw_article_fields  where aid=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(aid) });
	}
}