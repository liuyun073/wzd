package com.rd.dao.jdbc;

import com.rd.dao.CollectionDao;
import com.rd.dao.jdbc.mapper.CollectionMapper;
import com.rd.dao.jdbc.mapper.CollectionUnionMapper;
import com.rd.dao.jdbc.mapper.DetailCollectionMapper;
import com.rd.dao.jdbc.mapper.UserCollectionMapper;
import com.rd.domain.Collection;
import com.rd.domain.Tender;
import com.rd.model.BorrowTender;
import com.rd.model.DetailCollection;
import com.rd.model.SearchParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class CollectionDaoImpl extends BaseDaoImpl implements CollectionDao {
	private Logger logger = Logger.getLogger(CollectionDaoImpl.class);

	String collectSql = " from dw_borrow_collection c left join dw_borrow_tender t on t.id=c.tender_id left join dw_borrow b on b.id=t.borrow_id left join dw_user as u on  u.user_id = t.user_id where b.id=?";

	String selectSql = "select c.*,u.username,t.addtime as tendertime ";

	String countSql = "select count(1) ";

	String getUnFinishFlowListSql = "from dw_borrow_collection as p3,dw_borrow_tender as p4,dw_user as p2 ,dw_borrow as p1 where p3.tender_id=p4.id and p1.id=p4.borrow_id and p2.user_id=p4.user_id and p4.user_id=p2.user_id and p1.status in(1,8) and p1.is_flow=1";

	String selectFlowSql = "select c.*,b.name as borrow_name,b.id as borrow_id,b.time_limit,u.username,u.user_id ";
	String allFlowSql = " from dw_borrow_collection c left join dw_borrow_tender t on t.id=c.tender_id left join dw_borrow b on b.id=t.borrow_id left join dw_user as u on  u.user_id = t.user_id ";

	String whereFlowSql = " where c.status=? and b.is_flow=1 and b.status in (1,8) and c.repay_time<=?";

	public Collection addCollection(Collection c) {
		String sql = "insert into dw_borrow_collection(addtime,addip,tender_id,order,repay_time,repay_account,interest,capital) values(?,?,?,?,?,?,?,?)";

		int ret = getJdbcTemplate().update(sql,
						new Object[] { c.getAddtime(), c.getAddip(),
								Long.valueOf(c.getTender_id()),
								Integer.valueOf(c.getOrder()),
								c.getRepay_time(), c.getRepay_account(),
								c.getInterest(), c.getCapital() });
		if (ret < 0)
			return null;
		return c;
	}

	public void addBatchCollection(List<Collection> list) {
		String sql = "insert into dw_borrow_collection(addtime,addip,tender_id,`order`,repay_time,repay_account,interest,capital) values(?,?,?,?,?,?,?,?)";

		List<Object[]> listo = new ArrayList<Object[]>();
		if (list.size() > 0) {
			for (Collection c : list) {
				Object[] args = { c.getAddtime(), c.getAddip(),
						Long.valueOf(c.getTender_id()),
						Integer.valueOf(c.getOrder()), c.getRepay_time(),
						c.getRepay_account(), c.getInterest(), c.getCapital() };
				listo.add(args);
			}
			getJdbcTemplate().batchUpdate(sql, listo);
		}
	}

	public List<DetailCollection> getDetailCollectionList(long user_id, int status) {
		String sql = "select p1.*,p3.name as borrow_name,p3.id as borrow_id,p3.time_limit,p4.username  from dw_borrow_collection as p1 left join dw_borrow_tender as p2 on  p1.tender_id = p2.id left join dw_borrow as p3 on  p3.id = p2.borrow_id left join dw_user as p4 on  p4.user_id = p3.user_id where p3.user_id=? and p1.status=? order by p1.id";

		List<DetailCollection> list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) }, new DetailCollectionMapper());
		return list;
	}

	public DetailCollection getCollection(long id) {
		String sql = "select * from dw_borrow_collection where id=?";
		DetailCollection c = (DetailCollection) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new DetailCollectionMapper());
		return c;
	}

	public List<DetailCollection> getCollectionList(long user_id, int status, int start, int end, SearchParam param) {
		String sql = "select p1.*,p3.name as borrow_name,p3.id as borrow_id,p3.time_limit,p3.style as borrow_style, p4.username  from dw_borrow_collection as p1 left join dw_borrow_tender as p2 on  p1.tender_id = p2.id left join dw_borrow as p3 on  p3.id = p2.borrow_id left join dw_user as p4 on  p4.user_id = p3.user_id where p2.user_id=?  and p1.status=?  and ((p3.status =1 and p3.is_flow=1) or p3.status in (3,6,7,8)) ";

		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		String orderSql = " order by p1.repay_yestime asc,p1.repay_time asc ";
		sql = sql + searchSql + orderSql + limitSql;
		List<DetailCollection> list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) }, new DetailCollectionMapper());
		return list;
	}

	public double getCollectionInterestSum(long user_id, int status) {
		String sql = "select sum(p1.interest-p1.interest*0.1) as num  from dw_borrow_collection as p1 left join dw_borrow_tender as p2 on  p1.tender_id = p2.id left join dw_borrow as p3 on  p3.id = p2.borrow_id left join dw_user as p4 on  p4.user_id = p3.user_id where p2.user_id=?  and p1.status=?  and ((p3.status =1 and p3.is_flow=1) or p3.status in (3,6,7,8)) ";

		this.logger.info("SQL:" + sql);
		this.logger.info("SQL:" + user_id);
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("num");
		}
		return sum;
	}

	public int getCollectionListCount(long user_id, int status,
			SearchParam param) {
		String sql = "select count(p1.id) from dw_borrow_collection as p1 left join dw_borrow_tender as p2 on  p1.tender_id = p2.id left join dw_borrow as p3 on  p3.id = p2.borrow_id left join dw_user as p4 on  p4.user_id = p3.user_id where p2.user_id=?  and p1.status=?  and ((p3.status =1 and p3.is_flow=1) or p3.status in (3,6,7,8)) ";

		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		int total = 0;
		try {
			total = count(sql, new Object[] { Long.valueOf(user_id), Integer.valueOf(status) });
		} catch (Exception e) {
			this.logger.error("getCollectionListCount():" + e.getMessage());
		}
		return total;
	}

	public void modifyCollectionBonus(int order, double apr, List<BorrowTender> tlist) {
		String sql = "update dw_borrow_collection set repay_account=repay_account+capital*?,bonus=capital*? where `order`=? and tender_id=?";
		List<Object[]> args = new ArrayList<Object[]>();
		for (int i = 0; i < tlist.size(); ++i) {
			Tender t = tlist.get(i);
			Object[] arg = { Double.valueOf(apr), Double.valueOf(apr), Integer.valueOf(order), Long.valueOf(t.getId()) };
			args.add(arg);
		}
		getJdbcTemplate().batchUpdate(sql, args);
	}

	public List<DetailCollection> getCollectionLlistByBorrow(long bid, int start, int end, SearchParam param) {
		String sql = this.selectSql + this.collectSql;
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		sql = sql + searchSql + limitSql;
		List<DetailCollection> list = new ArrayList<DetailCollection>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(bid) },
					new UserCollectionMapper());
		} catch (DataAccessException e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public List<DetailCollection> getCollectionLlistByBorrow(long bid) {
		String sql = this.selectSql + this.collectSql;
		List<DetailCollection> list = new ArrayList<DetailCollection>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(bid) },
					new UserCollectionMapper());
		} catch (DataAccessException e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public int getCollectionCountByBorrow(long bid, SearchParam param) {
		String sql = this.countSql + this.collectSql;
		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(bid) });
		return count;
	}

	public int getUnFinishFlowCount(SearchParam param, String nowTime) {
		String selectSql = "select count(1) ";
		String unFinishFlowSql = " and ((p3.repay_yestime is null)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getUnFinishFlowListSql).append(param.getSearchParamSql()).append(unFinishFlowSql);
		this.logger.debug("getUnFinishFlowCount():" + sb.toString());
		this.logger.debug("nowTime:" + nowTime);
		int total = 0;
		total = count(sb.toString());
		this.logger.debug("getUnFinishFlowCount" + total);
		return total;
	}

	public List<Collection> getUnFinishFlowList(SearchParam param, String nowTime, int start, int pernum) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.*,p4.borrow_id,p1.isday,p1.time_limit,p1.time_limit_day,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area ";
		String lateSql = " and ((p3.repay_yestime is null)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getUnFinishFlowListSql).append(param.getSearchParamSql()).append(lateSql).append(" order by p3.repay_time asc limit ?,? ");
		this.logger.debug("getUnFinishFlowList():" + sb.toString());
		this.logger.debug("nowTime:" + nowTime);
		List<Collection> list = new ArrayList<Collection>();
		list = getJdbcTemplate().query(sb.toString(),
						new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) },
						new CollectionUnionMapper());
		return list;
	}

	public List<Collection> getUnFinishFlowList(SearchParam param) {
		String selectSql = "select  p3.*,p1.name as borrow_name,p1.*,p4.borrow_id,p1.isday,p1.time_limit,p1.time_limit_day,p1.verify_time,p2.username,p2.user_id,p2.phone,p2.area ";
		String lateSql = " and ((p3.repay_yestime is null)) ";
		StringBuffer sb = new StringBuffer(selectSql);
		sb.append(this.getUnFinishFlowListSql).append(param.getSearchParamSql()).append(lateSql).append(" order by p3.repay_time asc");
		this.logger.debug("getUnFinishFlowList():" + sb.toString());
		List<Collection> list = new ArrayList<Collection>();
		try {
			list = getJdbcTemplate().query(sb.toString(),
					new Object[] { Integer.valueOf(0) },
					new CollectionUnionMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Collection> getCollectionListByTender(long tid) {
		String sql = "select * from dw_borrow_collection where tender_id=?";
		List<Collection> list = new ArrayList<Collection>();
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(tid) }, new CollectionMapper());
		} catch (DataAccessException e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public void modifyBatchCollection(List<Collection> collects) {
		String sql = "update dw_borrow_collection set status=?,repay_yestime=?,repay_yesaccount=?,repay_time=?,repay_account=?,late_interest=?,late_days=? where id=?";

		List<Object[]> listo = new ArrayList<Object[]>();
		if ((collects != null) && (collects.size() > 0)) {
			for (Collection c : collects) {
				Object[] args = { Integer.valueOf(c.getStatus()),
						c.getRepay_yestime(), c.getRepay_yesaccount(),
						c.getRepay_time(), c.getRepay_account(),
						c.getLate_interest(), Long.valueOf(c.getLate_days()),
						Long.valueOf(c.getId()) };
				listo.add(args);
			}
			getJdbcTemplate().batchUpdate(sql, listo);
		}
	}

	public List<DetailCollection> getCollectionLlistByBorrow(long bid, int order) {
		String sql = this.selectSql + this.collectSql + " and c.`order`=? ";
		List<DetailCollection> list = new ArrayList<DetailCollection>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(bid), Integer.valueOf(order) },
					new UserCollectionMapper());
		} catch (DataAccessException e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public void modifyCollection(Collection c) {
		String sql = "update dw_borrow_collection set status=?,repay_yestime=?,repay_yesaccount=?,repay_time=?,repay_account=?,late_interest=?,late_days=? where id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(c.getStatus()),
						c.getRepay_yestime(), c.getRepay_yesaccount(),
						c.getRepay_time(), c.getRepay_account(),
						c.getLate_interest(), Long.valueOf(c.getLate_days()),
						Long.valueOf(c.getId()) });
	}

	public List<DetailCollection> getAllFlowCollectList(int status) {
		return getAllFlowCollectList(status, 0);
	}

	public List<DetailCollection> getAllFlowCollectList(int status, int aheadtime) {
		String sql = this.selectFlowSql + this.allFlowSql + this.whereFlowSql;
		String repay_time = "" + (System.currentTimeMillis() / 1000L + aheadtime);
		List<DetailCollection> list = new ArrayList<DetailCollection>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Integer.valueOf(status), repay_time },
					new DetailCollectionMapper());
		} catch (DataAccessException e) {
			this.logger.error(e.getMessage());
		}
		return list;
	}

	public double getRepaymentAccount(int status) {
		String sql = "select sum(repay_account) as sum from dw_borrow_collection where status=?";
		double sum = 0.0D;
		SqlRowSet rs = getJdbcTemplate().queryForRowSet(sql,
				new Object[] { Integer.valueOf(status) });
		if (rs.next()) {
			sum = rs.getDouble("sum");
		}
		return sum;
	}
}