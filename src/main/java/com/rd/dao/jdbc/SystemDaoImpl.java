package com.rd.dao.jdbc;

import com.rd.dao.SystemDao;
import com.rd.domain.SystemConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class SystemDaoImpl extends BaseDaoImpl implements SystemDao {
	
	public List<SystemConfig> getsystem() {
		String sql = "select * from dw_system where status=1";
		List<SystemConfig> list = new ArrayList<SystemConfig>();
		list = getJdbcTemplate().query(sql, new RowMapper<SystemConfig>() {
			public SystemConfig mapRow(ResultSet rs, int num) throws SQLException {
				SystemConfig sys = new SystemConfig();
				sys.setId(rs.getLong("id"));
				sys.setName(rs.getString("name"));
				sys.setNid(rs.getString("nid"));
				sys.setStatus(rs.getString("status"));
				sys.setStyle(rs.getInt("style"));
				sys.setType(rs.getInt("type"));
				sys.setValue(rs.getString("value"));
				return sys;
			}
		});
		return list;
	}

	public void updateSystemById(List<SystemConfig> list) {
		String sql = "update dw_system set value=? where id=?";
		List<Object[]> argList = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); ++i) {
			SystemConfig sysconfig = (SystemConfig) list.get(i);
			if (sysconfig == null) {
				System.out.println(i);
			} else {
				Object[] args = { sysconfig.getValue(),
						Long.valueOf(sysconfig.getId()) };
				argList.add(args);
			}
		}
		getJdbcTemplate().batchUpdate(sql, argList);
	}

	public List<SystemConfig> getSystemListBySytle(int i) {
		String sql = "select * from dw_system where `status`=?";
		List<SystemConfig> list = new ArrayList<SystemConfig>();
		list = getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(i) }, new RowMapper<SystemConfig>() {
					public SystemConfig mapRow(ResultSet rs, int num)
							throws SQLException {
						SystemConfig sys = new SystemConfig();
						sys.setId(rs.getLong("id"));
						sys.setName(rs.getString("name"));
						sys.setNid(rs.getString("nid"));
						sys.setStatus(rs.getString("status"));
						sys.setStyle(rs.getInt("style"));
						sys.setType(rs.getInt("type"));
						sys.setValue(rs.getString("value"));
						return sys;
					}
				});
		return list;
	}

	public void addSystemConfig(SystemConfig systemConfig) {
		String sql = "insert into dw_system(`name`,nid,`value`,`type`,`style`,`status`) VALUES(?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { systemConfig.getName(), systemConfig.getNid(),
						systemConfig.getValue(),
						Integer.valueOf(systemConfig.getType()),
						Integer.valueOf(systemConfig.getStyle()),
						systemConfig.getStatus() });
	}

	public List<String> getAllowIp() {
		String sql = "select * from dw_admin_allowip";
		List<String> list = new ArrayList<String>();
		list = getJdbcTemplate().query(sql, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int num) throws SQLException {
				return rs.getString("allowip");
			}
		});
		return list;
	}
}