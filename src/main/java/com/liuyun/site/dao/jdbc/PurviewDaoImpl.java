package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.PurviewDao;
import com.liuyun.site.dao.jdbc.mapper.PurviewCheckedMapper;
import com.liuyun.site.dao.jdbc.mapper.PurviewMapper;
import com.liuyun.site.domain.Purview;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class PurviewDaoImpl extends BaseDaoImpl implements PurviewDao {
	private Logger logger = Logger.getLogger(PurviewDaoImpl.class);

	public List<Purview> getPurviewByPid(long pid) {
		String sql = "select * from dw_purview where pid=?";
		this.logger.debug("getPurviewByPid():" + sql);
		List<Purview> list = new ArrayList<Purview>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(pid) }, new PurviewMapper());
		} catch (DataAccessException e) {
			this.logger.debug("getPurviewByPid():" + e.getMessage());
		}
		return list;
	}

	public List<Purview> getPurviewByUserid(long user_id) {
		String sql = "select u.username,tp.user_type_id,p.* from dw_purview p inner join dw_user_typepurview tp on p.id=tp.purview_id left join dw_user u on u.type_id=tp.user_type_id where 1=1 and u.user_id=? order by p.level,p.pid";

		this.logger.debug("getPurviewByUserid():" + sql);
		List<Purview> list = new ArrayList<Purview>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new PurviewMapper());
		} catch (DataAccessException e) {
			this.logger.debug("getPurviewByUserid():" + e.getMessage());
		}
		return list;
	}

	public List<Purview> getAllPurview() {
		String sql = "select p.* from dw_purview p ";
		this.logger.debug("getPurviewByUserid():" + sql);
		List<Purview> list = new ArrayList<Purview>();
		try {
			list = getJdbcTemplate().query(sql, new Object[0], new PurviewMapper());
		} catch (DataAccessException e) {
			this.logger.debug("getPurviewByUserid():" + e.getMessage());
		}
		return list;
	}

	public List<Purview> getAllCheckedPurview(long user_typeid) {
		String sql = "select purview.*,temp.user_type_id from dw_purview purview left join (select typepurview.* from dw_user_typepurview typepurview, dw_user_type type where type.type_id=typepurview.user_type_id and type.type_id=?) temp on temp.purview_id=purview.id";

		this.logger.debug("getAllCheckedPurview():" + sql);
		List<Purview> list = new ArrayList<Purview>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(user_typeid) },
					new PurviewCheckedMapper());
		} catch (DataAccessException e) {
			this.logger.debug("getPurviewByUserid():" + e.getMessage());
		}
		return list;
	}

	public void addPurview(Purview p) {
		String sql = "insert into dw_purview(name,purview,url,pid,level,remark) values(?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { p.getName(), p.getPurview(), p.getUrl(),
						Long.valueOf(p.getPid()),
						Integer.valueOf(p.getLevel()), p.getRemark() });
	}

	public Purview getPurview(long id) {
		String sql = "select * from dw_purview where id=?";
		Purview p = null;
		try {
			p = (Purview) getJdbcTemplate().queryForObject(sql,
					new Object[] { Long.valueOf(id) }, new PurviewMapper());
		} catch (DataAccessException e) {
			this.logger.debug("getPurview():" + e.getMessage());
		}
		return p;
	}

	public void delPurview(long id) {
		String sql = "delete from dw_purview where id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(id) });
	}

	public boolean isRoleHasPurview(long id) {
		String sql = "select 1 from dw_user_typepurview tp left join dw_purview p on p.id=tp.purview_id where 1=1 and p.id=?";

		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Long.valueOf(id) });

		return rowSet.next();
	}

	public void modifyPurview(Purview p) {
		String sql = "update dw_purview set name=?,purview=?,url=?,remark=? where id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { p.getName(), p.getPurview(), p.getUrl(),
						p.getRemark(), Long.valueOf(p.getId()) });
	}

	public void addUserTypePurviews(List<Integer> purviewid, long user_type_id) {
		if(purviewid == null || purviewid.isEmpty()){
			return;
		}
		
		String sql = "insert into dw_user_typepurview(user_type_id,purview_id) values(?,?)";
		List<Object[]> argsList = new ArrayList<Object[]>();
		for (Integer id : purviewid) {
			Object[] args = { Long.valueOf(user_type_id), id };
			argsList.add(args);
		}
		getJdbcTemplate().batchUpdate(sql, argsList);
	}

	public void delUserTypePurviews(long user_type_id) {
		String sql = "delete from dw_user_typepurview where user_type_id=?";
		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(user_type_id) });
	}
}