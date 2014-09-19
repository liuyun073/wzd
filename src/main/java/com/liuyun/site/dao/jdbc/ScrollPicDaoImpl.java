package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.ScrollPicDao;
import com.liuyun.site.dao.jdbc.mapper.ScrollPicMapper;
import com.liuyun.site.domain.ScrollPic;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class ScrollPicDaoImpl extends BaseDaoImpl implements ScrollPicDao {
	private static Logger logger = Logger.getLogger(ScrollPicDaoImpl.class);

	public List<ScrollPic> getList(int start, int end) {
		long s = System.currentTimeMillis();
		String sql = "select * from dw_scrollpic where 1=1 order by sort limit "
				+ start + "," + end;

		logger.debug("SQL:" + sql);
		List<ScrollPic> list = new ArrayList<ScrollPic>();
		try {
			list = getJdbcTemplate().query(sql, new ScrollPicMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		long e = System.currentTimeMillis();
		logger.info("GetScrillPicList Cost Time:" + (e - s));
		return list;
	}

	public ScrollPic getScrollPicById(long id) {
		String sql = "select * from dw_scrollpic where id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + id);
		ScrollPic sp = null;
		try {
			sp = (ScrollPic) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(id) }, new ScrollPicMapper());
		} catch (DataAccessException e) {
			logger.debug("BorrowDao.getBorrowById,数据库查询结果为空！");
		}
		return sp;
	}

	public void delScrollPic(long id) {
		String sql = "delete from dw_scrollpic where id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public void modifyScrollPic(ScrollPic sp) {
		String sql = "update dw_scrollpic set site_id=?,status=?,sort=?,flag=?,type_id=?,url=?,name=?,pic=?,summary=?,hits=?,addtime=?,addip=? where id=?";

		logger.info("SQL:" + sql);
		try {
			getJdbcTemplate().update(
					sql,
					new Object[] { Long.valueOf(sp.getSite_id()),
							Integer.valueOf(sp.getStatus()),
							Integer.valueOf(sp.getSort()), sp.getFlag(),
							Long.valueOf(sp.getType_id()), sp.getUrl(),
							sp.getName(), sp.getPic(), sp.getSummary(),
							Integer.valueOf(sp.getHits()), sp.getAddtime(),
							sp.getAddip(), Long.valueOf(sp.getId()) });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ScrollPic addScrollPic(ScrollPic sp) {
		String sql = "insert into dw_scrollpic(site_id,status,sort,flag,type_id,url,name,pic,summary,hits,addtime,addip) values(?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(sp.getSite_id()),
						Integer.valueOf(sp.getStatus()),
						Integer.valueOf(sp.getSort()), sp.getFlag(),
						Long.valueOf(sp.getType_id()), sp.getUrl(),
						sp.getName(), sp.getPic(), sp.getSummary(),
						Integer.valueOf(sp.getHits()), sp.getAddtime(),
						sp.getAddip() });
		return sp;
	}
}