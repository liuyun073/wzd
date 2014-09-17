package com.rd.dao.jdbc;

import com.rd.dao.LinkageDao;
import com.rd.dao.jdbc.mapper.AreainfoMapper;
import com.rd.dao.jdbc.mapper.LinkageMapper;
import com.rd.domain.Areainfo;
import com.rd.domain.Linkage;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class LinkageDaoImpl extends BaseDaoImpl implements LinkageDao {
	private static Logger logger = Logger.getLogger(LinkageDaoImpl.class);

	public List<Linkage> getLinkageByTypeid(int typeid, String type) {
		String sql = "select a.* from dw_linkage a where a.type_id=? " + getOrderSql(type);
		List<Linkage> list = new ArrayList<Linkage>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(typeid) }, new LinkageMapper());
		return list;
	}

	public List<Linkage> getLinkageByNid(String nid, String type) {
		String sql = "select a.* from dw_linkage a,dw_linkage_type b where a.type_id=b.id and b.nid=? " + getOrderSql(type);
		List<Linkage> list = new ArrayList<Linkage>();
		list = getJdbcTemplate().query(sql, new Object[] { nid }, new LinkageMapper());
		return list;
	}

	public List<Areainfo> getAreainfoByPid(String pid) {
		String sql = "select p.* from dw_area p where p.pid=?";
		List<Areainfo> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[] { pid }, new AreainfoMapper());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public Linkage getLinkageById(long id) {
		String sql = "select a.* from dw_linkage a,dw_linkage_type b where a.type_id=b.id and a.id=?";
		Linkage a = null;
		try {
			a = getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new LinkageMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	public Linkage getLinkageByValue(String nid, String value) {
		String sql = "select a.* from dw_linkage a,dw_linkage_type b where a.type_id=b.id and b.nid=? and a.value=?";
		Linkage a = null;
		try {
			a = getJdbcTemplate().queryForObject(sql, new Object[] { nid, value }, new LinkageMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return a;
	}

	private String getOrderSql(String type) {
		String orderSql = "";
		if (type.equals("value"))
			orderSql = "order by value+\"\"";
		else {
			orderSql = "order by a.id";
		}
		return orderSql;
	}

	public String getLinkageNameByid(long id) {
		String sql = "SELECT `name` FROM dw_linkage where id=?";
		try {
			return (String) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, String.class);
		} catch (Exception e) {
		}
		return "-";
	}

	public String getAreaByPid(String id) {
		String sql = "select name from dw_area where id =?";
		try {
			return (String) getJdbcTemplate().queryForObject(sql, new Object[] { id }, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-";
	}
}