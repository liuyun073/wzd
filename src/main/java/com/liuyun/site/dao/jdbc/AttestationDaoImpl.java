package com.liuyun.site.dao.jdbc;

import com.liuyun.site.dao.AttestationDao;
import com.liuyun.site.dao.jdbc.mapper.AttestationMapper;
import com.liuyun.site.domain.Attestation;
import com.liuyun.site.domain.AttestationType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class AttestationDaoImpl extends BaseDaoImpl implements AttestationDao {
	
	public List<AttestationType> getAllList() {
		String sql = "select * from dw_attestation_type order by type_id desc";
		List<AttestationType> list = new ArrayList<AttestationType>();
		list = getJdbcTemplate().query(sql, new RowMapper<AttestationType>() {
			public AttestationType mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AttestationType at = new AttestationType();
				at.setType_id(rs.getInt("type_id"));
				at.setName(rs.getString("name"));
				at.setOrder(rs.getString("order"));
				at.setStatus(rs.getInt("status"));
				at.setJifen(rs.getInt("jifen"));
				at.setSummary(rs.getString("summary"));
				at.setRemark(rs.getString("remark"));
				at.setAddtime(rs.getString("addtime"));
				at.setAddip(rs.getString("addip"));
				return at;
			}
		});
		return list;
	}

	public List<Attestation> getListByUserid(long user_id) {
		String sql = "select p1.*,p2.name as type_name,p2.jifen as jifen_val from dw_attestation as p1 left join dw_attestation_type as p2 on p1.type_id=p2.type_id left join dw_user as p3 on p1.user_id=p3.user_id  where p3.user_id=?";

		List<Attestation> list = new ArrayList<Attestation>();
		list = getJdbcTemplate().query(sql, new Object[] { Long.valueOf(user_id) }, new AttestationMapper());
		return list;
	}

	public Attestation addAttestation(Attestation att) {
		int result = 0;
		if (att.getStatus() == 1) {
			String sql = "insert into dw_attestation(user_id,type_id,name,litpic,addtime,jifen,verify_time,verify_remark) values(?,?,?,?,?,?,?,?)";

			result = getJdbcTemplate().update(sql,
					new Object[] { Long.valueOf(att.getUser_id()),
							Integer.valueOf(att.getType_id()), att.getName(),
							att.getLitpic(), att.getAddtime(),
							Integer.valueOf(att.getJifen()),
							att.getVerify_time(), att.getVerify_remark() });
		} else {
			String sql = "insert into dw_attestation(user_id,type_id,name,litpic,addtime,jifen) values(?,?,?,?,?,?)";

			result = getJdbcTemplate().update(
					sql,
					new Object[] { Long.valueOf(att.getUser_id()),
							Integer.valueOf(att.getType_id()), att.getName(),
							att.getLitpic(), att.getAddtime(),
							Integer.valueOf(att.getJifen()) });
		}
		if (result < 1) {
			return null;
		}
		return att;
	}

	public List<Attestation> getListByUserid(long user_id, int status) {
		String sql = "select p1.*,p2.name as type_name,p2.jifen as jifen_val from dw_attestation as p1 left join dw_attestation_type as p2 on p1.type_id=p2.type_id left join dw_user as p3 on p1.user_id=p3.user_id  where p1.status=? and p3.user_id=?";

		List<Attestation> list = new ArrayList<Attestation>();
		list = getJdbcTemplate().query(sql, new Object[] { Integer.valueOf(status), Long.valueOf(user_id) }, new AttestationMapper());
		return list;
	}

	public List<Attestation> getListByUserName(String name, int status) {
		String sql = "SELECT p.user_id,p.username,p.realname,p1.jifen,p1.addtime from dw_user as p LEFT JOIN dw_attestation as p1 on p.user_id=p1.user_id where  p1.`status`=? and p.username=?";
		List<Attestation> list = new ArrayList<Attestation>();
		list = getJdbcTemplate().query(sql,
				new Object[] { name, Integer.valueOf(status) },
				new AttestationMapper());
		return list;
	}

	public void addAttestationType(AttestationType attestationType) {
		String sql = "insert into dw_attestation_type(`name`,`order`,STATUS,jifen,summary,remark,addtime,addip)VALUES(?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { attestationType.getName(),
						attestationType.getOrder(),
						Integer.valueOf(attestationType.getStatus()),
						Integer.valueOf(attestationType.getJifen()),
						attestationType.getSummary(),
						attestationType.getRemark(),
						attestationType.getAddtime(),
						attestationType.getAddip() });
	}

	public void deleteAttestationType(long type_id) {
		String sql = "delete from dw_attestation_type where type_id=?";
		getJdbcTemplate().update(sql, new Object[] { Long.valueOf(type_id) });
	}

	public void updateAttestationTypeByList(List<AttestationType> list) {
		String sql = "update dw_attestation_type set `name`=?,`order`=?,status=?,jifen=?,summary=?,remark=?,addtime=?,addip=? where type_id=?";
		for (AttestationType o : list) {
			if (o == null)
				continue;
			getJdbcTemplate().update(
					sql,
					new Object[] { o.getName(), o.getOrder(),
							Integer.valueOf(o.getStatus()),
							Integer.valueOf(o.getJifen()), o.getSummary(),
							o.getRemark(), o.getAddtime(), o.getAddip(),
							Integer.valueOf(o.getType_id()) });
		}
	}
}