package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.BorrowFlowDao;
import com.liuyun.site.dao.jdbc.mapper.BorrowMapper;
import com.liuyun.site.domain.BorrowFlow;
import com.liuyun.site.model.borrow.BorrowModel;
import java.util.List;

public class BorrowFlowDaoImpl extends BaseDaoImpl implements BorrowFlowDao {
	public void add(BorrowFlow flow) {
		String sql = "insert into dw_borrow_flow(borrow_id,user_id,flow_count,valid_count,repayment_account,interest,buy_time,back_time,auto_repurchase,status,addip) values(?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(flow.getBorrow_id()),
						Long.valueOf(flow.getUser_id()),
						Integer.valueOf(flow.getFlow_count()),
						Integer.valueOf(flow.getValid_count()),
						flow.getRepayment_account(), flow.getInterest(),
						Long.valueOf(flow.getBuy_time()),
						Long.valueOf(flow.getBack_time()),
						Integer.valueOf(flow.getAuto_repurchase()),
						Integer.valueOf(flow.getStatus()), flow.getAddip() });
	}

	public List<BorrowModel> getBorrowFlowByUserId(long user_id) {
		String sql = "SELECT*FROM dw_borrow WHERE (STATUS=2 OR STATUS=4 OR STATUS=5) AND user_id =?";

		return getJdbcTemplate().query(sql,
				new Object[] { Long.valueOf(user_id) }, new BorrowMapper());
	}
}