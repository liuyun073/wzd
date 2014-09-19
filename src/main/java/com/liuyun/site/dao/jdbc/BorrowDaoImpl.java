package com.liuyun.site.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.liuyun.site.context.Global;
import com.liuyun.site.dao.BorrowDao;
import com.liuyun.site.dao.jdbc.mapper.AdvancedMapper;
import com.liuyun.site.dao.jdbc.mapper.BorrowMapper;
import com.liuyun.site.dao.jdbc.mapper.InvestBorrowMapper;
import com.liuyun.site.dao.jdbc.mapper.ReservationMapper;
import com.liuyun.site.dao.jdbc.mapper.UserBorrowMapper;
import com.liuyun.site.domain.Advanced;
import com.liuyun.site.domain.Borrow;
import com.liuyun.site.domain.Repayment;
import com.liuyun.site.domain.Reservation;
import com.liuyun.site.domain.RunBorrow;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserBorrowModel;
import com.liuyun.site.model.borrow.BorrowModel;
import com.liuyun.site.model.borrow.ReservationModel;
import com.liuyun.site.model.invest.InvestBorrowModel;
import com.liuyun.site.util.DateUtils;
import com.liuyun.site.util.StringUtils;

public class BorrowDaoImpl extends BaseDaoImpl implements BorrowDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);

	private String queryAllBorrowSql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p8.name as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales,p7.name as usetypename,p2.realname,p1.verify_user from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id where 1=1 ";

	private String queryBorrowCountSql = "select count(1) from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p2.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id where 1=1 ";

	private String queryBorrowSumSql = "select sum(p1.account) as num from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p2.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id where 1=1 ";

	String fullSql = " and (p1.account_yes+0)=(p1.account+0) and p1.is_flow<>1 ";
	String queryFullBorrowSql = this.queryAllBorrowSql + this.fullSql;
	String queryFullBorrowCountSql = this.queryBorrowCountSql + this.fullSql;

	public List<UserBorrowModel> getList(BorrowModel model) {
		String sql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p8.name as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales,p7.name as usetypename,p2.realname from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id left join dw_linkage link on link.id=p1.valid_time where 1=1 ";

		String connectSql = connectSql(model);
		sql = sql + connectSql;

		System.out.println(connectSql + "=======123==========" + model.getVerify_time());
		logger.debug("SQL:" + sql);
		List<UserBorrowModel> list = new ArrayList<UserBorrowModel>();
		try {
			list = getJdbcTemplate().query(sql, new UserBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public int count(BorrowModel model) {
		int total = 0;
		String sql = "select count(*) from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id left join dw_linkage link on link.id=p1.valid_time where 1=1 ";

		logger.debug("SQL:" + sql);
		String connectSql = connectCountSql(model);

		sql = sql + connectSql;
		logger.debug("SQL:" + sql);
		try {
			total = getJdbcTemplate().queryForInt(sql);
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	public List<Repayment> getBorrowList(String type, int start, int end, SearchParam param) {
		String sql = "SELECT p3.username,p2.repayment_account AS repayment_account,p2.repayment_time AS repayment_time,p2.repayment_yestime AS repayment_yestime ,p2.borrow_id AS borrow_id FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id LEFT JOIN dw_user AS p3 ON p1.user_id=p3.user_id WHERE 1=1 ";

		String newsql = "SELECT p3.username,p2.*,p1.* FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id left join dw_user as p3 on p1.user_id=p3.user_id  where 1=1  ";

		String flowsql = "SELECT collection.repay_account AS repayment_account,collection.repay_time AS repayment_time,collection.repay_yestime AS repayment_yestime,tender.borrow_id AS borrow_id  FROM dw_borrow_tender AS tender LEFT JOIN dw_borrow_collection AS collection ON tender.id=collection.tender_id LEFT JOIN dw_borrow AS b ON tender.borrow_id=b.id WHERE 1=1 ";

		String typeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 0);
		String flowTypeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 1);
		String searchSql = param.getSearchParamSql();

		String limitSql = " limit " + start + "," + end;
		sql = typeSql + searchSql + limitSql;
		flowsql = flowTypeSql + searchSql + limitSql;

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + flowsql);
		List<Repayment> list = new ArrayList<Repayment>();
		try {
			if ("wait_repay".equals(type)) {
				list = getJdbcTemplate().query(sql, new Object[0],
						new RowMapper<Repayment>() {
							public Repayment mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Repayment ua1 = new Repayment();
								ua1.setRepayment_account(rs.getString("repayment_account"));
								ua1.setRepayment_time(rs.getString("repayment_time"));
								ua1.setRepayment_yestime(rs.getString("repayment_yestime"));
								ua1.setUsername(rs.getString("username"));
								ua1.setBorrow_id(rs.getLong("borrow_id"));
								return ua1;
							}
						});
			} else
				list = getJdbcTemplate().query(sql, new Object[0],
						new RowMapper<Repayment>() {
							public Repayment mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Repayment ua = new Repayment();
								ua.setRepayment_account(rs.getString("repayment_account"));
								ua.setRepayment_time(rs.getString("repayment_time"));
								ua.setRepayment_yestime(rs.getString("repayment_yestime"));
								ua.setUsername(rs.getString("username"));
								ua.setBorrow_id(rs.getLong("borrow_id"));
								return ua;
							}
						});
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<Repayment> getFlowBorrowList(String type, int start, int end, SearchParam param) {
		String sql = "SELECT p3.username,p2.repayment_account AS repayment_account,p2.repayment_time AS repayment_time,p2.repayment_yestime AS repayment_yestime ,p2.borrow_id AS borrow_id FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id LEFT JOIN dw_user AS p3 ON p1.user_id=p3.user_id WHERE 1=1 ";

		String newsql = "SELECT p3.username,p2.*,p1.* FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id left join dw_user as p3 on p1.user_id=p3.user_id  where 1=1  ";

		String flowsql = "SELECT u.username,collection.repay_account AS repayment_account,collection.repay_time AS repayment_time,collection.repay_yestime AS repayment_yestime,tender.borrow_id AS borrow_id  FROM dw_borrow_tender AS tender LEFT JOIN dw_borrow_collection AS collection ON tender.id=collection.tender_id LEFT JOIN dw_borrow AS b ON tender.borrow_id=b.id left join dw_user as u on u.user_id=b.user_id WHERE 1=1 ";

		String typeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 0);
		String flowTypeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 1);
		String searchSql = param.getSearchParamSql();

		String limitSql = " limit " + start + "," + end;
		sql = typeSql + searchSql + limitSql;
		flowsql = flowTypeSql + searchSql + limitSql;

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + flowsql);
		List<Repayment> list = new ArrayList<Repayment>();
		//List flowlist = new ArrayList();
		//List newlist = new ArrayList();
		try {
			if ("wait_repay".equals(type))
				list = getJdbcTemplate().query(flowsql, new Object[0],
						new RowMapper<Repayment>() {
							public Repayment mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								Repayment ua = new Repayment();
								ua.setRepayment_account(rs.getString("repayment_account"));
								ua.setRepayment_time(rs.getString("repayment_time"));
								ua.setRepayment_yestime(rs.getString("repayment_yestime"));
								ua.setUsername(rs.getString("username"));
								ua.setBorrow_id(rs.getLong("borrow_id"));
								return ua;
							}
						});
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public int getBorrowCount(String type, SearchParam param) {
		int newtotal = 0;
		int flowTotal = 0;
		int total = 0;
		String sql = "SELECT count(1) FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id LEFT JOIN dw_user AS p3 ON p1.user_id=p3.user_id WHERE 1=1 ";

		String newsql = "SELECT count(1) FROM dw_borrow p1 LEFT JOIN dw_borrow_repayment AS p2 ON p1.id=p2.borrow_id left join dw_user as p3 on p1.user_id=p3.user_id  where 1=1  ";

		String flowsql = "SELECT count(1)  FROM dw_borrow_tender AS tender LEFT JOIN dw_borrow_collection AS collection ON tender.id=collection.tender_id LEFT JOIN dw_borrow AS b ON tender.borrow_id=b.id WHERE 1=1 ";

		String typeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 0);
		String flowTypeSql = getBorrowTypeSql(type, sql, newsql, flowsql, 1);
		String searchSql = param.getSearchParamSql();
		sql = typeSql + searchSql;
		flowsql = flowTypeSql + searchSql;
		logger.debug("SQL:" + sql);
		try {
			if ("wait_repay".equals(type)) {
				newtotal = getJdbcTemplate().queryForInt(sql);
				flowTotal = getJdbcTemplate().queryForInt(flowsql);
				total = newtotal + flowTotal;
			} else {
				total = getJdbcTemplate().queryForInt(sql);
			}
		} catch (DataAccessException e) {
			logger.debug("数据库查询结果为空！");
		}
		return total;
	}

	private String getBorrowTypeSql(String type, String sql, String newsql,
			String flowsql, int i) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("当前时间:" + df.format(new Date()));
		String mondayString = StringUtils.getMonday(df.format(new Date()))
				+ " 00:00:00";
		String sumdayString = StringUtils.getSunday(df.format(new Date()))
				+ " 23:59:59";
		String monday = "" + DateUtils.getTime(mondayString);
		String sumday = "" + DateUtils.getTime(sumdayString);
		logger.debug("本周一时间为" + monday);
		logger.debug("本周日时间为" + sumday);

		String typeSql = "";
		if (i == 0) {
			typeSql = sql + " and p2.repayment_time<" + sumday
					+ " AND p2.repayment_time>" + monday
					+ "  and p1.is_flow!=1  ";
		}
		if (i == 1) {
			typeSql = flowsql + " and collection.repay_time<" + sumday
					+ " AND collection.repay_time>" + monday
					+ "  and b.is_flow=1";
		}
		if ("overdue_10_day_down_unrepay".equals(type))
			typeSql = newsql + " and p2.repayment_time+10*24*60*60>"
					+ DateUtils.getNowTimeStr() + " AND p2.repayment_time<"
					+ DateUtils.getNowTimeStr()
					+ " AND p2.repayment_yestime IS NULL and p1.is_flow!=1 ";
		else if ("overdue_10_day_up_unrepay".equals(type))
			typeSql = newsql + " and 10*24*60*60+p2.repayment_time<"
					+ DateUtils.getNowTimeStr()
					+ " AND p2.repayment_yestime IS NULL and p1.is_flow!=1 ";
		else if ("overdue_yesrepay".equals(type)) {
			typeSql = newsql
					+ " and p2.repayment_time<"
					+ DateUtils.getNowTimeStr()
					+ " AND p2.repayment_yestime IS NOT  NULL and p2.repayment_yestime > p2.repayment_time+30*60 and p1.is_flow!=1 ";
		}
		return typeSql;
	}

	public BorrowModel getBorrowById(long id) {
		String sql = "select p1.* from dw_borrow  p1 where id=?";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + id);
		BorrowModel b = null;
		try {
			b = (BorrowModel) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new BorrowMapper());
			logger.debug("borrowto======" + b.getBorrow_time());
		} catch (DataAccessException e) {
			logger.debug("getBorrowById()" + e.getMessage());
		}
		return b;
	}

	public UserBorrowModel getUserBorrowById(long id) {
		String sql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p8.name as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales,p7.name as usetypename,p2.realname from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id left join dw_linkage p7 on p1.use=p7.id and p7.type_id=19 left join dw_area p8 on p2.province=p8.id where 1=1 and p1.id=?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + id);
		UserBorrowModel b = null;
		try {
			b = (UserBorrowModel) getJdbcTemplate().queryForObject(sql, new Object[] { Long.valueOf(id) }, new UserBorrowMapper());
		} catch (DataAccessException e) {
			logger.error("getBorrowById()" + e.getMessage());
		}
		return b;
	}

	public void addBorrow(Borrow borrow) {
		String webid = Global.getValue("webid");
		if (StringUtils.isNull(webid).equals("mdw")) {
			String sql = "insert into  dw_borrow(user_id,name,status,flag,is_vouch,type,view_type,vouch_award,vouch_user,vouch_account,repayment_yesinterest,addip,is_mb,is_fast,is_jin,pwd,time_limit,style,account,account_yes,addtime,apr,lowest_account,most_account,valid_time,award,open_account,open_borrow,open_tender,open_credit,content,`use`,time_limit_day,isday,part_account,funds,repayment_account,is_art,is_charity,is_xin,is_project,is_flow,flow_money,flow_count,flow_yescount,flow_status,is_student,is_offvouch,borrow_time,borrow_account,borrow_time_limit,collection_limit,verify_time,late_award) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			logger.debug("SQL:" + sql);
			getJdbcTemplate().update(sql,
					new Object[] { Long.valueOf(borrow.getUser_id()),
							borrow.getName(),
							Integer.valueOf(borrow.getStatus()),
							borrow.getFlag(),
							Integer.valueOf(borrow.getIs_vouch()),
							borrow.getType(),
							Integer.valueOf(borrow.getView_type()),
							borrow.getVouch_award(), borrow.getVouch_user(),
							borrow.getVouch_account(),
							Integer.valueOf(borrow.getRepayment_yesinterest()),
							borrow.getAddip(),
							Integer.valueOf(borrow.getIs_mb()),
							Integer.valueOf(borrow.getIs_fast()),
							Integer.valueOf(borrow.getIs_jin()),
							borrow.getPwd(), borrow.getTime_limit(),
							borrow.getStyle(), borrow.getAccount(),
							borrow.getAccount_yes(), borrow.getAddtime(),
							Double.valueOf(borrow.getApr()),
							borrow.getLowest_account(),
							borrow.getMost_account(), borrow.getValid_time(),
							Integer.valueOf(borrow.getAward()),
							borrow.getOpen_account(), borrow.getOpen_borrow(),
							borrow.getOpen_tender(), borrow.getOpen_credit(),
							borrow.getContent(), borrow.getUse(),
							Integer.valueOf(borrow.getTime_limit_day()),
							Integer.valueOf(borrow.getIsday()),
							Double.valueOf(borrow.getPart_account()),
							Double.valueOf(borrow.getFunds()),
							borrow.getRepayment_account(),
							Integer.valueOf(borrow.getIs_art()),
							Integer.valueOf(borrow.getIs_charity()),
							Integer.valueOf(borrow.getIs_xin()),
							Integer.valueOf(borrow.getIs_project()),
							Integer.valueOf(borrow.getIs_flow()),
							Integer.valueOf(borrow.getFlow_money()),
							Integer.valueOf(borrow.getFlow_count()),
							Integer.valueOf(borrow.getFlow_yescount()),
							Integer.valueOf(borrow.getFlow_status()),
							Integer.valueOf(borrow.getIs_student()),
							Integer.valueOf(borrow.getIs_offvouch()),
							borrow.getBorrow_time(),
							borrow.getBorrow_account(),
							borrow.getBorrow_time_limit(),
							borrow.getCollection_limit(),
							borrow.getVerify_time(),
							Double.valueOf(borrow.getLate_award()) });
		} else if (StringUtils.isNull(webid).equals("jsy")) {
			String sql = "insert into  dw_borrow(user_id,name,status,flag,is_vouch,type,view_type,vouch_award,vouch_user,vouch_account,repayment_yesinterest,addip,is_mb,is_fast,is_jin,pwd,time_limit,style,account,account_yes,addtime,apr,lowest_account,most_account,valid_time,award,open_account,open_borrow,open_tender,open_credit,content,`use`,time_limit_day,isday,part_account,funds,repayment_account,is_art,is_charity,is_xin,is_project,is_flow,flow_money,flow_count,flow_yescount,flow_status,is_student,is_offvouch,is_pledge,verify_user,late_award) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			logger.debug("SQL:" + sql);
			getJdbcTemplate().update(sql,
					new Object[] { Long.valueOf(borrow.getUser_id()),
							borrow.getName(),
							Integer.valueOf(borrow.getStatus()),
							borrow.getFlag(),
							Integer.valueOf(borrow.getIs_vouch()),
							borrow.getType(),
							Integer.valueOf(borrow.getView_type()),
							borrow.getVouch_award(), borrow.getVouch_user(),
							borrow.getVouch_account(),
							Integer.valueOf(borrow.getRepayment_yesinterest()),
							borrow.getAddip(),
							Integer.valueOf(borrow.getIs_mb()),
							Integer.valueOf(borrow.getIs_fast()),
							Integer.valueOf(borrow.getIs_jin()),
							borrow.getPwd(), borrow.getTime_limit(),
							borrow.getStyle(), borrow.getAccount(),
							borrow.getAccount_yes(), borrow.getAddtime(),
							Double.valueOf(borrow.getApr()),
							borrow.getLowest_account(),
							borrow.getMost_account(), borrow.getValid_time(),
							Integer.valueOf(borrow.getAward()),
							borrow.getOpen_account(), borrow.getOpen_borrow(),
							borrow.getOpen_tender(), borrow.getOpen_credit(),
							borrow.getContent(), borrow.getUse(),
							Integer.valueOf(borrow.getTime_limit_day()),
							Integer.valueOf(borrow.getIsday()),
							Double.valueOf(borrow.getPart_account()),
							Double.valueOf(borrow.getFunds()),
							borrow.getRepayment_account(),
							Integer.valueOf(borrow.getIs_art()),
							Integer.valueOf(borrow.getIs_charity()),
							Integer.valueOf(borrow.getIs_xin()),
							Integer.valueOf(borrow.getIs_project()),
							Integer.valueOf(borrow.getIs_flow()),
							Integer.valueOf(borrow.getFlow_money()),
							Integer.valueOf(borrow.getFlow_count()),
							Integer.valueOf(borrow.getFlow_yescount()),
							Integer.valueOf(borrow.getFlow_status()),
							Integer.valueOf(borrow.getIs_student()),
							Integer.valueOf(borrow.getIs_offvouch()),
							Integer.valueOf(borrow.getIs_pledge()),
							borrow.getVerify_user(),
							Double.valueOf(borrow.getLate_award()) });
		} else if ((StringUtils.isNull(webid).equals("jlct")) || (StringUtils.isNull(webid).equals("aidai"))) {
			String sql = "insert into  dw_borrow(user_id,name,status,flag,is_vouch,type,view_type,vouch_award,vouch_user,vouch_account,repayment_yesinterest,addip,is_mb,is_fast,is_jin,pwd,time_limit,style,account,account_yes,addtime,apr,lowest_account,most_account,valid_time,award,open_account,open_borrow,open_tender,open_credit,content,`use`,time_limit_day,isday,part_account,funds,repayment_account,is_art,is_charity,is_xin,is_project,is_flow,flow_money,flow_count,flow_yescount,flow_status,is_student,is_offvouch,is_donation,late_award) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			logger.debug("SQL:" + sql);
			getJdbcTemplate().update(sql,
					new Object[] { Long.valueOf(borrow.getUser_id()),
							borrow.getName(),
							Integer.valueOf(borrow.getStatus()),
							borrow.getFlag(),
							Integer.valueOf(borrow.getIs_vouch()),
							borrow.getType(),
							Integer.valueOf(borrow.getView_type()),
							borrow.getVouch_award(), borrow.getVouch_user(),
							borrow.getVouch_account(),
							Integer.valueOf(borrow.getRepayment_yesinterest()),
							borrow.getAddip(),
							Integer.valueOf(borrow.getIs_mb()),
							Integer.valueOf(borrow.getIs_fast()),
							Integer.valueOf(borrow.getIs_jin()),
							borrow.getPwd(), borrow.getTime_limit(),
							borrow.getStyle(), borrow.getAccount(),
							borrow.getAccount_yes(), borrow.getAddtime(),
							Double.valueOf(borrow.getApr()),
							borrow.getLowest_account(),
							borrow.getMost_account(), borrow.getValid_time(),
							Integer.valueOf(borrow.getAward()),
							borrow.getOpen_account(), borrow.getOpen_borrow(),
							borrow.getOpen_tender(), borrow.getOpen_credit(),
							borrow.getContent(), borrow.getUse(),
							Integer.valueOf(borrow.getTime_limit_day()),
							Integer.valueOf(borrow.getIsday()),
							Double.valueOf(borrow.getPart_account()),
							Double.valueOf(borrow.getFunds()),
							borrow.getRepayment_account(),
							Integer.valueOf(borrow.getIs_art()),
							Integer.valueOf(borrow.getIs_charity()),
							Integer.valueOf(borrow.getIs_xin()),
							Integer.valueOf(borrow.getIs_project()),
							Integer.valueOf(borrow.getIs_flow()),
							Integer.valueOf(borrow.getFlow_money()),
							Integer.valueOf(borrow.getFlow_count()),
							Integer.valueOf(borrow.getFlow_yescount()),
							Integer.valueOf(borrow.getFlow_status()),
							Integer.valueOf(borrow.getIs_student()),
							Integer.valueOf(borrow.getIs_offvouch()),
							Integer.valueOf(borrow.getIs_donation()),
							Double.valueOf(borrow.getLate_award()) });
		} else {
			String sql = "insert into  dw_borrow(user_id,name,status,flag,is_vouch,type,view_type,vouch_award,vouch_user,vouch_account,repayment_yesinterest,addip,is_mb,is_fast,is_jin,pwd,time_limit,style,account,account_yes,addtime,apr,lowest_account,most_account,valid_time,award,open_account,open_borrow,open_tender,open_credit,content,`use`,time_limit_day,isday,part_account,funds,repayment_account,is_art,is_charity,is_xin,is_project,is_flow,flow_money,flow_count,flow_yescount,flow_status,is_student,is_offvouch,verify_time,late_award) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			logger.debug("SQL:" + sql);
			getJdbcTemplate().update(
					sql,
					new Object[] { Long.valueOf(borrow.getUser_id()),
							borrow.getName(),
							Integer.valueOf(borrow.getStatus()),
							borrow.getFlag(),
							Integer.valueOf(borrow.getIs_vouch()),
							borrow.getType(),
							Integer.valueOf(borrow.getView_type()),
							borrow.getVouch_award(), borrow.getVouch_user(),
							borrow.getVouch_account(),
							Integer.valueOf(borrow.getRepayment_yesinterest()),
							borrow.getAddip(),
							Integer.valueOf(borrow.getIs_mb()),
							Integer.valueOf(borrow.getIs_fast()),
							Integer.valueOf(borrow.getIs_jin()),
							borrow.getPwd(), borrow.getTime_limit(),
							borrow.getStyle(), borrow.getAccount(),
							borrow.getAccount_yes(), borrow.getAddtime(),
							Double.valueOf(borrow.getApr()),
							borrow.getLowest_account(),
							borrow.getMost_account(), borrow.getValid_time(),
							Integer.valueOf(borrow.getAward()),
							borrow.getOpen_account(), borrow.getOpen_borrow(),
							borrow.getOpen_tender(), borrow.getOpen_credit(),
							borrow.getContent(), borrow.getUse(),
							Integer.valueOf(borrow.getTime_limit_day()),
							Integer.valueOf(borrow.getIsday()),
							Double.valueOf(borrow.getPart_account()),
							Double.valueOf(borrow.getFunds()),
							borrow.getRepayment_account(),
							Integer.valueOf(borrow.getIs_art()),
							Integer.valueOf(borrow.getIs_charity()),
							Integer.valueOf(borrow.getIs_xin()),
							Integer.valueOf(borrow.getIs_project()),
							Integer.valueOf(borrow.getIs_flow()),
							Integer.valueOf(borrow.getFlow_money()),
							Integer.valueOf(borrow.getFlow_count()),
							Integer.valueOf(borrow.getFlow_yescount()),
							Integer.valueOf(borrow.getFlow_status()),
							Integer.valueOf(borrow.getIs_student()),
							Integer.valueOf(borrow.getIs_offvouch()),
							borrow.getVerify_time(),
							Double.valueOf(borrow.getLate_award()) });
		}
	}

	public void updateBorrow(Borrow borrow) {
		String sql = "update dw_borrow t set t.account=?,t.tender_times=?,t.apr=?,style=?,t.use=?,t.time_limit=?,t.lowest_account=?,t.most_account=?,t.valid_time=?,t.name=?,t.content=?,t.pwd=?,t.award=?,t.funds=?,t.part_account=?,t.vouch_award=?,t.vouch_user=?,t.open_tender=?,t.status=?,t.verify_time=?,t.verify_remark=?  where t.id=? and t.user_id=? ";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(
				sql,
				new Object[] { borrow.getAccount(), borrow.getTender_times(),
						Double.valueOf(borrow.getApr()), borrow.getStyle(),
						borrow.getUse(), borrow.getTime_limit(),
						borrow.getLowest_account(), borrow.getMost_account(),
						borrow.getValid_time(), borrow.getName(),
						borrow.getContent(), borrow.getPwd(),
						Integer.valueOf(borrow.getAward()),
						Double.valueOf(borrow.getFunds()),
						Double.valueOf(borrow.getPart_account()),
						borrow.getVouch_award(), borrow.getVouch_user(),
						borrow.getOpen_tender(),
						Integer.valueOf(borrow.getStatus()),
						borrow.getVerify_time(), borrow.getVerify_remark(),
						Long.valueOf(borrow.getId()),
						Long.valueOf(borrow.getUser_id()) });
	}

	public void updateJinBorrow(Borrow borrow) {
		String sql = "update dw_borrow set verify_time=? where id=?";
		try {
			getJdbcTemplate().update(
					sql,
					new Object[] { borrow.getVerify_time(),
							Long.valueOf(borrow.getId()) });
		} catch (Exception localException) {
		}
	}

	public int updateBorrow(double account, int status, long id) {
		String sql = "update dw_borrow set tender_times=tender_times+1,account_yes=round(account_yes+?,2),status=? where id=? and round(account_yes+?)<=account+0";

		logger.debug("updateBorrow():" + sql);
		logger.debug("updateBorrow() params:" + account + "," + status + ","
				+ id);
		int count = 0;
		try {
			count = getJdbcTemplate().update(
					sql,
					new Object[] { Double.valueOf(account),
							Integer.valueOf(status), Long.valueOf(id),
							Double.valueOf(account) });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("updateBorrow() result:" + count);
		return count;
	}

	public void deleteBorrow(Borrow borrow) {
		String sql = "update dw_borrow set status=5 where borrow_id=?";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(borrow.getId()) });
	}

	public List<InvestBorrowModel> getSuccessListByUserid(long user_id, String type) {
		String sql = "select bt.repayment_yesaccount, bt.repayment_account, bt.addtime as tender_time, bt.account as anum, bt.repayment_account - bt.account as inter, bo.name as borrow_name, bo.account, bo.time_limit, bo.isday, bo.time_limit_day, bo.apr, u.username, cr.value as credit, bo.id,u.user_id from dw_borrow_tender as bt, dw_borrow as bo, dw_user as u, dw_credit as cr where bt.user_id = ? and bo.user_id = u.user_id and cr.user_id = bo.user_id and bt.borrow_id = bo.id and bo.status =1";

		String _sql = "";
		if ((type != null) && (type.equals("1"))) {
			_sql = " and bt.repayment_yesaccount!=bt.repayment_account ";
			sql = sql + _sql;
		} else if ((type != null) && (type.equals("2"))) {
			_sql = " and bt.repayment_yesaccount=bt.repayment_account ";
			sql = sql + _sql;
		}
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		logger.debug("SQL:" + type);
		List<InvestBorrowModel> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new InvestBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List getSuccessBorrowList(int start, int end, SearchParam param) {
		return null;
	}

	public List<InvestBorrowModel> getSuccessBorrowList(String type, int start, int end, SearchParam param) {
		String sql = "select bt.repayment_yesaccount, bt.repayment_account, bt.addtime as tender_time, bt.account as anum, bt.repayment_account - bt.account as inter, bo.name as borrow_name, bo.account, bo.time_limit, bo.isday, bo.time_limit_day, bo.apr, u.username, cr.value as credit, bo.id,u.user_id from dw_borrow_tender as bt, dw_borrow as bo, dw_user as u, dw_credit as cr where 1=1 and bo.user_id = u.user_id and cr.user_id = bo.user_id and bt.borrow_id = bo.id and bo.status =1";

		String _sql = "";
		if ((type != null) && (type.equals("1"))) {
			_sql = " and bt.repayment_yesaccount!=bt.repayment_account ";
			sql = sql + _sql;
		} else if ((type != null) && (type.equals("2"))) {
			_sql = " and bt.repayment_yesaccount=bt.repayment_account ";
			sql = sql + _sql;
		}
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + type);
		List<InvestBorrowModel> list = null;
		try {
			list = getJdbcTemplate().query(sql, new InvestBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<InvestBorrowModel> getSuccessBorrowList(long user_id, String type, int start, int end, SearchParam param) {
		String sql = "select bt.repayment_yesaccount, bt.repayment_account, bt.addtime as tender_time, bt.account as anum, bt.repayment_account - bt.account as inter, bo.name as borrow_name, bo.account, bo.time_limit, bo.isday, bo.time_limit_day, bo.apr, u.username, cr.value as credit, bo.id,u.user_id from dw_borrow_tender as bt, dw_borrow as bo, dw_user as u, dw_credit as cr where bt.user_id = ? and bo.user_id = u.user_id and cr.user_id = bo.user_id and bt.borrow_id = bo.id and ((bo.status =1 and bo.is_flow=1) or bo.status in (3,6,7,8)) ";

		String _sql = "";
		if ((type != null) && (type.equals("1"))) {
			_sql = " and bt.repayment_yesaccount<>bt.repayment_account+0 ";
			sql = sql + _sql;
		} else if ((type != null) && (type.equals("2"))) {
			_sql = " and bt.repayment_yesaccount=bt.repayment_account+0 ";
			sql = sql + _sql;
		}
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		String orderSql = " order by bt.addtime desc ";
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		List<InvestBorrowModel> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new InvestBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug("getSuccessBorrowList():" + e.getMessage());
		}
		return list;
	}

	public int getSuccessBorrowCount(long user_id, String type, SearchParam param) {
		String sql = "select count(1) from dw_borrow_tender as bt, dw_borrow as bo, dw_user as u, dw_credit as cr where bt.user_id = ? and bo.user_id = u.user_id and cr.user_id = bo.user_id and bt.borrow_id = bo.id and ((bo.status =1 and bo.is_flow=1) or bo.status in (3,6,7,8)) ";

		String _sql = "";
		if ((type != null) && (type.equals("1"))) {
			_sql = " and bt.repayment_yesaccount<>bt.repayment_account+0 ";
			sql = sql + _sql;
		} else if ((type != null) && (type.equals("2"))) {
			_sql = " and bt.repayment_yesaccount=bt.repayment_account+0 ";
			sql = sql + _sql;
		}
		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		logger.debug("SQL:" + sql);
		int total = 0;
		try {
			total = count(sql, new Object[] { Long.valueOf(user_id) });
		} catch (DataAccessException e) {
			logger.debug("getSuccessBorrowCount():" + e.getMessage());
		}
		return total;
	}

	public List<UserBorrowModel> getListByUserid(long user_id) {
		String sql = "select  p1.*,p6.isqiye,p6.id as fastid,p2.username,p2.realname,p2.area as user_area ,u.username as kefu_username,p2.qq,p3.value as credit_jifen,p4.pic as credit_pic,p5.area as add_area,p1.account_yes/p1.account as scales from dw_borrow as p1 left join dw_user as p2 on p1.user_id=p2.user_id left join dw_user_cache as uca on uca.user_id=p1.user_id left join dw_user as u on u.user_id=uca.kefu_userid left join dw_credit as p3 on p1.user_id=p3.user_id left join dw_credit_rank as p4 on p3.value<=p4.point2  and p3.value>=p4.point1 left join dw_userinfo as p5 on p1.user_id=p5.user_id left join dw_daizi as p6 on p1.id=p6.borrow_id where 1=1 and user_id=?";

		List<UserBorrowModel> list = null;
		try {
			list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new UserBorrowMapper());
		} catch (DataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<BorrowModel> getBorrowList(long user_id) {
		String sql = "select * from dw_borrow where user_id=? and status in (3,6,7)";
		List<BorrowModel> list = new ArrayList<BorrowModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new BorrowMapper());
		return list;
	}

	public List<BorrowModel> getBorrowList(int start, int end, SearchParam param) {
		String sql = "select * from dw_borrow p1 where 1=1 ";
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit ?,? ";
		String orderSql = " order by p1.addtime desc";
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		List<BorrowModel> list = new ArrayList<BorrowModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, new BorrowMapper());
		return list;
	}

	public int getBorrowCount(SearchParam param) {
		String sql = "select * from dw_borrow p1 where 1=1 ";
		String searchSql = param.getSearchParamSql();
		sql = sql + searchSql;
		logger.debug("SQL:" + sql);
		int total = 0;
		total = count(sql);
		return total;
	}

	public List<BorrowModel> getBorrowList(long user_id, int start, int end, SearchParam param) {
		String sql = "select * from dw_borrow p1 where p1.user_id=? and p1.status in (3,6,7)";
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit " + start + "," + end;
		String orderSql = "order by p1.addtime desc";
		sql = sql + searchSql + orderSql + limitSql;
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<BorrowModel> list = new ArrayList<BorrowModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new BorrowMapper());
		return list;
	}

	public int getBorrowCount(long user_id) {
		String sql = "select count(id) from dw_borrow where user_id=? and status>=3";
		int count = 0;
		count = getJdbcTemplate().queryForInt(sql, new Object[] { Long.valueOf(user_id) });
		return count;
	}

	public List<UserBorrowModel> getAllBorrowList(int start, int pernum, SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String limitSql = "order by p1.addtime desc limit ?,?";
		String sql = this.queryAllBorrowSql + searchSql + limitSql;
		logger.debug("getAllBorrowList():" + sql);
		List<UserBorrowModel> list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new UserBorrowMapper());
		return list;
	}

	public int getAllBorrowCount(SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String sql = this.queryBorrowCountSql + searchSql;
		logger.debug("getAllBorrowCount():" + sql);
		int total = 0;
		total = count(sql);
		return total;
	}

	public List<UserBorrowModel> getFullBorrowList(int start, int pernum, SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String limitSql = " limit ?,?";
		String sql = this.queryFullBorrowSql + searchSql + limitSql;
		logger.debug("getAllBorrowList():" + sql);
		List<UserBorrowModel> list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(start), Integer.valueOf(pernum) }, new UserBorrowMapper());
		return list;
	}

	public int getFullBorrowCount(SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String sql = this.queryFullBorrowCountSql + searchSql;
		logger.debug("getAllBorrowCount():" + sql);
		int total = 0;
		total = count(sql);
		return total;
	}

	public List<BorrowModel> unfinshBorrowList(long user_id) {
		String sql = "select * from dw_borrow where user_id=? and status in (0,1) and is_flow<>1";
		List<BorrowModel> list = new ArrayList<BorrowModel>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new BorrowMapper());
		return list;
	}

	public void updateBorrowFlow(Borrow b) {
		String sql = "update dw_borrow set flow_yescount=?";
		getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(b.getFlow_yescount()) });
	}

	public double hasTenderTotalPerBorrowByUserid(long borrow_id, long user_id) {
		String sql = "select sum(account) as num from dw_borrow_tender where borrow_id=? and user_id=? for update";
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(borrow_id),
				Long.valueOf(user_id) });
		return total;
	}

	public double getBorrowAccountSum() {
		String sql = "select sum(money) as num from dw_account_log where type='borrow_success'";
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}

	public double getWaitAccountSum() {
		String sql = "SELECT SUM(repayment_account-repayment_yesaccount) AS num FROM dw_borrow_tender ";
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}

	public double getDayBorrowAccountSum(String startTime, String endTime) {
		String sql = "select sum(money) as num from dw_account_log where type='borrow_success' and addtime>=? and addtime<=? ";
		double total = 0.0D;
		total = sum(sql, new Object[] { startTime, endTime });
		return total;
	}

	public void addjk(RunBorrow runBorrow) {
		final String sql = "insert into dw_run_borrow(money,username,tel,description) values(?,?,?,?)";
		final RunBorrow r = runBorrow;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setString(1, r.getMoney());
				ps.setString(2, r.getUsername());
				ps.setString(3, r.getTel());
				ps.setString(4, r.getDescription());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		runBorrow.setId(key);
	}

	public List<RunBorrow> jklist(int start, int end, SearchParam param) {
		String sql = "select * from dw_run_borrow ";
		List<RunBorrow> list = null;
		String limitSql = "  limit ?,?";
		String querySql = sql + limitSql;
		try {
			list = getJdbcTemplate().query(querySql,
							new Object[] { Integer.valueOf(start), Integer.valueOf(end) }, new RowMapper<RunBorrow>() {
								public RunBorrow mapRow(ResultSet rs, int num)
										throws SQLException {
									RunBorrow s = new RunBorrow();
									s.setId(rs.getLong("id"));
									s.setMoney(rs.getString("money"));
									s.setDescription(rs.getString("description"));
									s.setTel(rs.getString("tel"));
									s.setUsername(rs.getString("username"));
									return s;
								}
							});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public int getjkCount(SearchParam param) {
		int total = 0;
		String sql = "select count(1) from dw_run_borrow";
		try {
			total = count(sql);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return total;
	}

	public double getRepayTotalWithJin(long user_id) {
		String sql = "select sum(account) as num from dw_borrow where is_jin=1 and status in (1,3,6,7) and user_id=? ";
		double total = 0.0D;
		total = sum(sql, new Object[] { Long.valueOf(user_id) });
		return total;
	}

	public List<Advanced> advancedList() {
		String sql = "select * from dw_advanced";
		List<Advanced> list = getJdbcTemplate().query(sql, new Object[0], new AdvancedMapper());
		return list;
	}

	public double getBorrowSum() {
		String sql = "SELECT SUM(tender.account) AS num FROM  dw_borrow_tender AS tender LEFT JOIN dw_borrow AS b ON tender.borrow_id=b.id WHERE  ((b.status IN(3,6,7,8) AND b.is_flow!=1) OR ( b.is_flow=1)) ";
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}

	public double getTotalRepayAccountAndInterest(String todayStartTime,
			String todayEndTime) {
		String sql = "select sum(p1.repay_account) as num from dw_borrow_collection as p1 where p1.repay_time>" + todayStartTime + " and p1.repay_time<" + todayEndTime + " and p1.repay_yestime is null";
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}

	public double getSumBorrowAccount(SearchParam param) {
		String searchSql = param.getSearchParamSql();
		String sql = this.queryBorrowSumSql + searchSql;
		logger.debug("getAllBorrowSum():" + sql);
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}

	public int hasBorrowCountByUserid(long borrow_id, long user_id) {
		String sql = "SELECT count(1) AS num FROM dw_borrow  WHERE id=? AND user_id=? FOR UPDATE";
		int count = 0;
		count = count(sql, new Object[] { Long.valueOf(borrow_id), Long.valueOf(user_id) });
		return count;
	}

	public void reservation_add(Reservation reservation) {
		final String sql = "insert into dw_reservation(reservation_user,tender_account,borrow_apr,addtime,addip) values(?,?,?,?,?)";
		final Reservation r = reservation;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, 1);
				ps.setLong(1, r.getReservation_user());
				ps.setDouble(2, r.getTender_account());
				ps.setString(3, r.getBorrow_apr());
				ps.setString(4, r.getAddtime());
				ps.setString(5, r.getAddip());
				return ps;
			}
		}, keyHolder);
		long key = keyHolder.getKey().longValue();
		reservation.setId(key);
	}

	public List<ReservationModel> getReservation_list(int start, int end, SearchParam param) {
		String sql = "select p.*,p1.username from dw_reservation as p left join dw_user as p1 on p1.user_id=p.reservation_user";
		List<ReservationModel> list = getJdbcTemplate().query(sql, new Object[0], new ReservationMapper());
		return list;
	}

	public int getReservation_list_count(SearchParam parm) {
		String sql = "select count(*) as num from dw_reservation";
		int total = 0;
		total = count(sql);
		return total;
	}

	public int updateBorrowStatus(int status, long id) {
		String sql = "update dw_borrow set status=? where id=? ";
		logger.debug("updateBorrow():" + sql);
		logger.debug("updateBorrow() params:," + status + "," + id);
		int count = 0;
		try {
			count = getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(status), Long.valueOf(id) });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("updateBorrow() result:" + count);
		return count;
	}

	public List<BorrowModel> getBorrowListByStatus(int status) {
		String sql = "select * from dw_borrow where status=?";
		List<BorrowModel> list = new ArrayList<BorrowModel>();
		list = getJdbcTemplate().query(sql, new BorrowMapper(), new Object[] { Integer.valueOf(status) });
		return list;
	}

	public int releaseFlowBorrow(double account, long id) {
		String sql = "update dw_borrow set account_yes=account_yes-? where id=? and account_yes-?>=0";

		logger.debug("releaseFlowBorrow():" + sql);
		logger.debug("releaseFlowBorrow() params:" + account);
		int count = 0;
		try {
			count = getJdbcTemplate().update(sql, new Object[] { Double.valueOf(account), Long.valueOf(id), Double.valueOf(account) });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("releaseFlowBorrow() result:" + count);
		return count;
	}

	public int getBorrowCountForSuccess() {
		String sql = "SELECT COUNT(*) FROM dw_borrow WHERE (STATUS IN(3,6,7,8) AND is_flow<>1) OR (STATUS IN(1,3,6,7,8) AND is_flow=1)";
		logger.debug("BorrowCountForSuccess:" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public void updateRecommendBorrow(Borrow borrow) {
		String sql = "update dw_borrow set is_recommend=? where id=?";
		try {
			getJdbcTemplate().update(sql, new Object[] { Integer.valueOf(borrow.getIs_recommend()), Long.valueOf(borrow.getId()) });
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public int getApplyBorrowCount() {
		String sql = "select count(1) from dw_borrow where is_mb<>1 ";
		logger.debug("aplyBorrowCount:" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public double getApplyBorrowTotal() {
		String sql = "select sum(account) as num from dw_borrow where is_mb<>1 and is_jin<>1 ";
		double total = 0.0D;
		total = sum(sql, new Object[0]);
		return total;
	}
}