package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.BorrowAutoDao;
import com.liuyun.site.dao.jdbc.mapper.BorrowAutoMapper;
import com.liuyun.site.domain.AutoTenderOrder;
import com.liuyun.site.domain.BorrowAuto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

public class BorrowAutoDaoImpl extends BaseDaoImpl implements BorrowAutoDao {
	private static Logger logger = Logger.getLogger(BorrowAutoDaoImpl.class);

	public List<BorrowAuto> getBorrowAutoList(long user_id) {
		String sql = "select  p1.* from dw_borrow_auto as p1 where p1.user_id=? order by p1.`id` desc ";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		List<BorrowAuto> list = new ArrayList<BorrowAuto>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new BorrowAutoMapper());
		return list;
	}

	public void addBorrowAuto(BorrowAuto auto) {
		String sql = "insert into dw_borrow_auto(status,tender_type,tender_account,tender_scale,video_status,scene_status,my_friend,not_black,late_status,dianfu_status,black_status,late_times,dianfu_times,black_user,black_times,not_late_black,borrow_credit_status,borrow_credit_first,borrow_credit_last,borrow_style_status,borrow_style,timelimit_status,timelimit_month_first,timelimit_month_last,timelimit_day_first,timelimit_day_last,apr_status,apr_first,apr_last,award_status,award_first,award_last,vouch_status,tuijian_status,user_id,addtime,fast_status,xin_status,jin_status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(auto.getStatus()),
						Integer.valueOf(auto.getTender_type()),
						Integer.valueOf(auto.getTender_account()),
						Integer.valueOf(auto.getTender_scale()),
						Integer.valueOf(auto.getVideo_status()),
						Integer.valueOf(auto.getScene_status()),
						Integer.valueOf(auto.getMy_friend()),
						Integer.valueOf(auto.getNot_black()),
						Integer.valueOf(auto.getLate_status()),
						Integer.valueOf(auto.getDianfu_status()),
						Integer.valueOf(auto.getBlack_status()),
						Integer.valueOf(auto.getLate_times()),
						Integer.valueOf(auto.getDianfu_times()),
						Integer.valueOf(auto.getBlack_user()),
						Integer.valueOf(auto.getBlack_times()),
						Integer.valueOf(auto.getNot_late_black()),
						Integer.valueOf(auto.getBorrow_credit_status()),
						Integer.valueOf(auto.getBorrow_credit_first()),
						Integer.valueOf(auto.getBorrow_credit_last()),
						Integer.valueOf(auto.getBorrow_style_status()),
						Integer.valueOf(auto.getBorrow_style()),
						Integer.valueOf(auto.getTimelimit_status()),
						Integer.valueOf(auto.getTimelimit_month_first()),
						Integer.valueOf(auto.getTimelimit_month_last()),
						Integer.valueOf(auto.getTimelimit_day_first()),
						Integer.valueOf(auto.getTimelimit_day_last()),
						Integer.valueOf(auto.getApr_status()),
						Integer.valueOf(auto.getApr_first()),
						Integer.valueOf(auto.getApr_last()),
						Integer.valueOf(auto.getAward_status()),
						Double.valueOf(auto.getAward_first()),
						Double.valueOf(auto.getAward_last()),
						Integer.valueOf(auto.getVouch_status()),
						Integer.valueOf(auto.getTuijian_status()),
						Long.valueOf(auto.getUser_id()),
						Integer.valueOf(auto.getAddtime()),
						Integer.valueOf(auto.getFast_status()),
						Integer.valueOf(auto.getXin_status()),
						Integer.valueOf(auto.getJin_status()) });
	}

	public void updateBorrowAuto(BorrowAuto auto) {
		String sql = "update dw_borrow_auto set status=?,tender_type=?, tender_account=?, tender_scale=?, video_status=?, scene_status=?,my_friend=?, not_black=?, late_status=?, dianfu_status=?, black_status=?, late_times=?, dianfu_times=?, black_user=?, black_times=?, not_late_black=?, borrow_credit_status=?, borrow_credit_first=?,borrow_credit_last=?, borrow_style_status=?, borrow_style=?, timelimit_status=?, timelimit_month_first=?, timelimit_month_last=?,timelimit_day_first=?,timelimit_day_last=?, apr_status=?, apr_first=?, apr_last=?, award_status=?, award_first=?, award_last=?, vouch_status=?, tuijian_status=?, user_id=?, addtime=?, fast_status=?, xin_status=?, jin_status=? where id=?";

		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Integer.valueOf(auto.getStatus()),
						Integer.valueOf(auto.getTender_type()),
						Integer.valueOf(auto.getTender_account()),
						Integer.valueOf(auto.getTender_scale()),
						Integer.valueOf(auto.getVideo_status()),
						Integer.valueOf(auto.getScene_status()),
						Integer.valueOf(auto.getMy_friend()),
						Integer.valueOf(auto.getNot_black()),
						Integer.valueOf(auto.getLate_status()),
						Integer.valueOf(auto.getDianfu_status()),
						Integer.valueOf(auto.getBlack_status()),
						Integer.valueOf(auto.getLate_times()),
						Integer.valueOf(auto.getDianfu_times()),
						Integer.valueOf(auto.getBlack_user()),
						Integer.valueOf(auto.getBlack_times()),
						Integer.valueOf(auto.getNot_late_black()),
						Integer.valueOf(auto.getBorrow_credit_status()),
						Integer.valueOf(auto.getBorrow_credit_first()),
						Integer.valueOf(auto.getBorrow_credit_last()),
						Integer.valueOf(auto.getBorrow_style_status()),
						Integer.valueOf(auto.getBorrow_style()),
						Integer.valueOf(auto.getTimelimit_status()),
						Integer.valueOf(auto.getTimelimit_month_first()),
						Integer.valueOf(auto.getTimelimit_month_last()),
						Integer.valueOf(auto.getTimelimit_day_first()),
						Integer.valueOf(auto.getTimelimit_day_last()),
						Integer.valueOf(auto.getApr_status()),
						Integer.valueOf(auto.getApr_first()),
						Integer.valueOf(auto.getApr_last()),
						Integer.valueOf(auto.getAward_status()),
						Double.valueOf(auto.getAward_first()),
						Double.valueOf(auto.getAward_last()),
						Integer.valueOf(auto.getVouch_status()),
						Integer.valueOf(auto.getTuijian_status()),
						Long.valueOf(auto.getUser_id()),
						Integer.valueOf(auto.getAddtime()),
						Integer.valueOf(auto.getFast_status()),
						Integer.valueOf(auto.getXin_status()),
						Integer.valueOf(auto.getJin_status()),
						Long.valueOf(auto.getId()) });
	}

	public void deleteBorrowAuto(long user_id) {
		String sql = "delete from dw_borrow_auto where user_id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(user_id) });
	}

	public void insertAutoTender() {
	}

	public List<BorrowAuto> getBorrowAutoListByStatus(long status) {
		String sql = "select  p1.* from dw_borrow_auto as p1 where p1.status=? order by p1.addtime ";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + status);
		List<BorrowAuto> list = new ArrayList<BorrowAuto>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(status) }, new BorrowAutoMapper());
		return list;
	}

	public void updateAutoTenderOrder(AutoTenderOrder auto) {
		String sql = "update dw_autoTenderOrder set Auto_lasttime=? where User_id=?  ";
		logger.debug("SQL:" + sql);
		getJdbcTemplate().update(sql,
				new Object[] { Long.valueOf(auto.getAuto_lasttime()), Long.valueOf(auto.getUser_id()) });
	}

	public AutoTenderOrder getAutoTenderOrder(long user_id) {
		AutoTenderOrder autoTenderOrder = null;
		String sql = "select  p1.* from dw_autoTenderOrder as p1 where p1.User_id=?  order by p1.`Auto_order`  ";
		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + user_id);
		try {
			autoTenderOrder = (AutoTenderOrder) getJdbcTemplate().queryForObject(sql,
							new Object[] { Long.valueOf(user_id) },
							new RowMapper<AutoTenderOrder>() {
								public AutoTenderOrder mapRow(final ResultSet rs, final int rowNum) throws SQLException {
									final AutoTenderOrder as = new AutoTenderOrder();
									as.setId(rs.getLong("id"));
									as.setUser_id(rs.getLong("User_id"));
									as.setUsername(rs.getString("User_name"));
									as.setAuto_lasttime(rs.getLong("Auto_lasttime"));
									as.setAuto_order(rs.getLong("Auto_order"));
									as.setAuto_score(rs.getLong("Auto_score"));
									as.setUser_autoMoney(rs.getLong("User_autoMoney"));
									as.setUser_autoMoneyOrder(rs.getLong("User_autoMoneyOrder"));
									as.setUser_jifen(rs.getLong("User_jifen"));
									as.setUser_jifenOrder(rs.getLong("User_jifenOrder"));
									as.setUser_useMoney(rs.getLong("User_useMoney"));
									as.setUser_useMoneyOrder(rs.getLong("User_useMoneyOrder"));
									return as;
								}
							});
		} catch (Exception localException) {
		}
		return autoTenderOrder;
	}

	public List<AutoTenderOrder> getAutoTenderOrder() {
		List<AutoTenderOrder> autoTenderOrderList = new ArrayList<AutoTenderOrder>();
		String sql = "select  p1.* from dw_autoTenderOrder as p1  order by p1.auto_order  ";
		logger.debug("SQL:" + sql);
		try {
			autoTenderOrderList = getJdbcTemplate().query(sql, new Object[0],
					new RowMapper<AutoTenderOrder>() {
						public AutoTenderOrder mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							AutoTenderOrder as = new AutoTenderOrder();
							as.setId(rs.getLong("id"));
							as.setUser_id(rs.getLong("User_id"));
							as.setUsername(rs.getString("User_name"));
							as.setAuto_lasttime(rs.getLong("Auto_lasttime"));
							as.setAuto_order(rs.getLong("Auto_order"));
							as.setAuto_score(rs.getLong("Auto_score"));
							as.setUser_autoMoney(rs.getLong("User_autoMoney"));
							as.setUser_autoMoneyOrder(rs.getLong("User_autoMoneyOrder"));
							as.setUser_jifen(rs.getLong("User_jifen"));
							as.setUser_jifenOrder(rs.getLong("User_jifenOrder"));
							as.setUser_useMoney(rs.getLong("User_useMoney"));
							as.setUser_useMoneyOrder(rs.getLong("User_useMoneyOrder"));
							return as;
						}
					});
		} catch (Exception localException) {
		}
		return autoTenderOrderList;
	}

	public int getAutoTenderOrderCount() {
		int total = 0;
		String sql = "select  count(1) from dw_autoTenderOrder as p1 where  p1.auto_lasttime is not null order by p1.`auto_order`  ";
		logger.debug("SQL:" + sql);
		try {
			total = count(sql);
		} catch (Exception localException) {
		}
		return total;
	}
}