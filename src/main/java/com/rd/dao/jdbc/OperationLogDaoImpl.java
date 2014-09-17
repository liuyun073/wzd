package com.rd.dao.jdbc;

import com.rd.dao.OperationLogDao;
import com.rd.dao.jdbc.mapper.OperationLogMapper;
import com.rd.domain.OperationLog;
import com.rd.model.SearchParam;
import com.rd.model.account.OperationLogModel;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class OperationLogDaoImpl extends BaseDaoImpl implements OperationLogDao {
	private static Logger logger = Logger.getLogger(BorrowDaoImpl.class);
	private String orderSql = " order by p1.addtime desc ";
	private String limitSql = " limit ?,? ";

	public void addOperationLog(OperationLog log) {
		String sql = "insert into dw_operation_log(user_id,type,verify_user,addtime,addip,operationResult) values(?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(log.getUser_id()), log.getType(),
						Long.valueOf(log.getVerify_user()), log.getAddtime(),
						log.getAddip(), log.getOperationResult() });
	}

	public int getOperationLogCount(SearchParam param) {
		String newsql = "select count(1) from dw_operation_log p1 left join dw_user p2 on p2.user_id=p1.verify_user left join dw_user u on u.user_id=p1.user_id left join dw_user_type p3 on p2.type_id=p3.type_id left join dw_linkage p4 on p4.value=p1.type and p4.type_id=30 where 1=1 ";

		StringBuffer sb = new StringBuffer(newsql);
		sb.append(param.getSearchParamSql());
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		int count = 0;
		count = count(sql);
		return count;
	}

	public List<OperationLogModel> getOperationLogList(int start, int pernum, SearchParam param) {
		String newsql = "select p3.name as usertypename,p4.name as typename,p1.*,p2.username as verify_username,u.username as username from dw_operation_log p1 left join dw_user p2 on p2.user_id=p1.verify_user left join dw_user u on u.user_id=p1.user_id left join dw_user_type p3 on p2.type_id=p3.type_id left join dw_linkage p4 on p4.value=p1.type and p4.type_id=42 where 1=1 ";

		StringBuffer sb = new StringBuffer(newsql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql).append(this.orderSql).append(this.limitSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<OperationLogModel> list = new ArrayList<OperationLogModel>();
		list = getJdbcTemplate()
				.query(
						sql,
						new Object[] { Integer.valueOf(start),
								Integer.valueOf(pernum) },
						new OperationLogMapper());
		return list;
	}

	public List<OperationLogModel> getOperationLogList(SearchParam param) {
		String newsql = "select p3.name as usertypename,p4.name as typename,p1.*,p2.username as verify_username,u.username as username from dw_operation_log p1 left join dw_user p2 on p2.user_id=p1.verify_user left join dw_user u on u.user_id=p1.user_id left join dw_user_type p3 on p2.type_id=p3.type_id left join dw_linkage p4 on p4.value=p1.type and p4.type_id=42 where 1=1 ";

		StringBuffer sb = new StringBuffer(newsql);
		String searchSql = param.getSearchParamSql();
		sb.append(searchSql).append(this.orderSql);
		String sql = sb.toString();
		logger.debug("SQL:" + sql);
		List<OperationLogModel> list = new ArrayList<OperationLogModel>();
		list = getJdbcTemplate().query(sql, new Object[0],
				new OperationLogMapper());
		return list;
	}
}