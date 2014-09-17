package com.rd.dao.jdbc;

import com.rd.dao.MemberBorrowDao;
import com.rd.dao.jdbc.mapper.UserBorrowMapper;
import com.rd.model.SearchParam;
import com.rd.model.UserBorrowModel;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class MemberBorrowDaoImpl extends BaseDaoImpl implements MemberBorrowDao {
	private static Logger logger = Logger.getLogger(MemberBorrowDaoImpl.class);

	public List<UserBorrowModel> getBorrowList(String type, long user_id, int start, int end, SearchParam param) {
		String sql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p2.realname,p2.area as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales,p1.use as usetypename from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id where p1.user_id=? ";

		String typeSql = getTypeSql(type);
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		String orderSql = " order by p1.addtime desc";
		sql = sql + typeSql + searchSql + orderSql + limitSql;

		logger.debug("SQL:" + sql);
		List<UserBorrowModel> list = new ArrayList<UserBorrowModel>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(user_id) },
					new UserBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public int getBorrowCount(String type, long user_id, SearchParam param) {
		int total = 0;
		String sql = "select count(p1.id) from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id where p1.user_id=" + user_id;
		logger.debug("SQL:" + sql);
		String typeSql = getTypeSql(type);
		String searchSql = param.getSearchParamSql();
		sql = sql + typeSql + searchSql;

		sql = sql + typeSql;
		logger.debug("SQL:" + sql);
		try {
			total = getJdbcTemplate().queryForInt(sql);
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	private String getTypeSql(String type) {
		String typeSql = " and p1.status=1";
		if ("unpublish".equals(type))
			typeSql = " and p1.status=0";
		else if ("repayment".equals(type))
			typeSql = " and (p1.status=3 or p1.status=6 or p1.status=7)";
		else if ("repaymentyes".equals(type)) {
			typeSql = " and p1.status=8";
		}
		return typeSql;
	}

	public List<UserBorrowModel> getRepamentList(String type, long user_id) {
		String typeSql = getTypeSql(type);
		String sql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p2.realname,p2.area as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales,p1.use as usetypename from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id where p1.user_id=? ";

		String orderSql = " order by p1.addtime desc";
		sql = sql + typeSql + orderSql;

		logger.debug("SQL:" + sql);
		List<UserBorrowModel> list = new ArrayList<UserBorrowModel>();
		try {
			list = getJdbcTemplate().query(sql,
					new Object[] { Long.valueOf(user_id) },
					new UserBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}