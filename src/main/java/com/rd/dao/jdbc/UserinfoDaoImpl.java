package com.rd.dao.jdbc;

import com.rd.dao.UserinfoDao;
import com.rd.dao.jdbc.mapper.AdminInfoModelMapper;
import com.rd.dao.jdbc.mapper.AttestationModelMapper;
import com.rd.dao.jdbc.mapper.AttestationTypeMapper;
import com.rd.dao.jdbc.mapper.UserAllInfoModelMapper;
import com.rd.dao.jdbc.mapper.UserInfoModelMapper;
import com.rd.dao.jdbc.mapper.UserMapper;
import com.rd.dao.jdbc.mapper.UserinfoMapper;
import com.rd.dao.jdbc.mapper.VipUserMapper;
import com.rd.domain.Attestation;
import com.rd.domain.AttestationType;
import com.rd.domain.User;
import com.rd.domain.Userinfo;
import com.rd.model.AdminInfoModel;
import com.rd.model.AttestationModel;
import com.rd.model.DetailUser;
import com.rd.model.SearchParam;
import com.rd.model.UserinfoModel;
import com.rd.util.StringUtils;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

public class UserinfoDaoImpl extends BaseDaoImpl implements UserinfoDao {
	private static Logger logger = Logger.getLogger(UserinfoDaoImpl.class);

	public Userinfo getUserinfoByUserid(long userid) {
		String sql = "select p1.* ,p2.username,p2.realname,p2.email from dw_userinfo as p1 left join dw_user as p2 on p1.user_id=p2.user_id where p1.user_id=?";

		Userinfo info = null;
		try {
			info = (Userinfo) getJdbcTemplate()
					.queryForObject(sql, new Object[] { Long.valueOf(userid) },
							new UserinfoMapper());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
		return info;
	}

	public void updateUserinfo(Userinfo info) {
		String sql = "update dw_userinfo set marry=?,child=?,education=?,income=?,shebao=?,shebaoid=?,housing=?,car=?,late=?  where user_id=?";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + info.getProvince());
		logger.debug("SQL:" + info.getLate());
		getJdbcTemplate().update(
				sql,
				new Object[] { info.getMarry(), info.getChild(),
						info.getEducation(), info.getIncome(),
						info.getShebao(), info.getShebaoid(),
						info.getHousing(), info.getCar(), info.getLate(),
						Long.valueOf(info.getUser_id()) });
	}

	public void addUserinfo(Userinfo info) {
		String sql = "insert into dw_userinfo(marry,education,income,shebao,shebaoid,housing,car,late,user_id) values(?,?,?,?,?,?,?,?,?)";

		logger.debug("SQL:" + sql);
		logger.debug("SQL:" + info.getProvince());
		logger.debug("SQL:" + info.getLate());
		getJdbcTemplate().update(
				sql,
				new Object[] { info.getMarry(), info.getEducation(),
						info.getIncome(), info.getShebao(), info.getShebaoid(),
						info.getHousing(), info.getCar(), info.getLate(),
						Long.valueOf(info.getUser_id()) });
	}

	public void updateBuilding(Userinfo i) {
		String sql = "update dw_userinfo set house_address=?, house_area=?, house_year=?, house_status=?, house_holder1=?, house_holder2=?, house_right1=?, house_right2=?, house_loanyear=?, house_loanprice=?, house_balance=?, house_bank=? where user_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getHouse_address(), i.getHouse_area(),
						i.getHouse_year(), i.getHouse_status(),
						i.getHouse_holder1(), i.getHouse_holder2(),
						i.getHouse_right1(), i.getHouse_right2(),
						i.getHouse_loanyear(), i.getHouse_loanprice(),
						i.getHouse_balance(), i.getHouse_bank(),
						Long.valueOf(i.getUser_id()) });
	}

	public void addBuilding(Userinfo i) {
		String sql = "insert into dw_userinfo(house_address,house_area,house_year,house_status,house_holder1,house_holder2,house_right1,house_right2,house_loanyear,house_loanprice,house_balance,house_bank,user_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getHouse_address(), i.getHouse_area(),
						i.getHouse_year(), i.getHouse_status(),
						i.getHouse_holder1(), i.getHouse_holder2(),
						i.getHouse_right1(), i.getHouse_right2(),
						i.getHouse_loanyear(), i.getHouse_loanprice(),
						i.getHouse_balance(), i.getHouse_bank(),
						Long.valueOf(i.getUser_id()) });
	}

	public void updateCompany(Userinfo i) {
		String sql = "update dw_userinfo set company_name=?, company_type=?, company_industry=?, company_office=?, company_jibie=?, company_worktime1=?, company_worktime2=?, company_workyear=?, company_tel=?, company_address=?, company_weburl=?, company_reamrk=? where user_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getCompany_name(), i.getCompany_type(),
						i.getCompany_industry(), i.getCompany_office(),
						i.getCompany_jibie(), i.getCompany_worktime1(),
						i.getCompany_worktime2(), i.getCompany_workyear(),
						i.getCompany_tel(), i.getCompany_address(),
						i.getCompany_weburl(), i.getCompany_reamrk(),
						Long.valueOf(i.getUser_id()) });
	}

	public void addCompany(Userinfo i) {
		String sql = "insert into dw_userinfo(company_name,company_type,company_industry,company_office,company_jibie,company_worktime1,company_worktime2,company_workyear,company_tel,company_address,company_weburl,company_reamrk,user_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getCompany_name(), i.getCompany_type(),
						i.getCompany_industry(), i.getCompany_office(),
						i.getCompany_jibie(), i.getCompany_worktime1(),
						i.getCompany_worktime2(), i.getCompany_workyear(),
						i.getCompany_tel(), i.getCompany_address(),
						i.getCompany_weburl(), i.getCompany_reamrk(),
						Long.valueOf(i.getUser_id()) });
	}

	public void updateFirm(Userinfo i) {
		String sql = "update dw_userinfo set  private_type=?, private_date=?, private_place=?, private_rent=?, private_term=?, private_taxid=?, private_commerceid=?, private_income=?, private_employee=? where user_id=?";

		getJdbcTemplate()
				.update(
						sql,
						new Object[] { i.getPrivate_type(),
								i.getPrivate_date(), i.getPrivate_place(),
								i.getPrivate_rent(), i.getPrivate_term(),
								i.getPrivate_taxid(),
								i.getPrivate_commerceid(),
								i.getPrivate_income(), i.getPrivate_employee(),
								Long.valueOf(i.getUser_id()) });
	}

	public void addFirm(Userinfo i) {
		String sql = "insert into dw_userinfo(private_type,private_date,private_place,private_rent,private_term,private_taxid,private_commerceid,private_income,private_employees,user_id) values(?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate()
				.update(
						sql,
						new Object[] { i.getPrivate_type(),
								i.getPrivate_date(), i.getPrivate_place(),
								i.getPrivate_rent(), i.getPrivate_term(),
								i.getPrivate_taxid(),
								i.getPrivate_commerceid(),
								i.getPrivate_income(), i.getPrivate_employee(),
								Long.valueOf(i.getUser_id()) });
	}

	public void updateFinance(Userinfo i) {
		String sql = "update dw_userinfo set finance_repayment=?, finance_property=?, finance_amount=?, finance_car=?, finance_caramount=?, finance_creditcard=? where user_id=?";

		getJdbcTemplate()
				.update(
						sql,
						new Object[] { i.getFinance_repayment(),
								i.getFinance_property(), i.getFinance_amount(),
								i.getFinance_car(), i.getFinance_caramount(),
								i.getFinance_creditcard(),
								Long.valueOf(i.getUser_id()) });
	}

	public void addFinance(Userinfo i) {
		String sql = "insert into dw_userinfo(finance_repayment,finance_property,finance_amount,finance_car,finance_caramount,finance_creditcard,user_id) values(?,?,?,?,?,?,?)";

		getJdbcTemplate()
				.update(
						sql,
						new Object[] { i.getFinance_repayment(),
								i.getFinance_property(), i.getFinance_amount(),
								i.getFinance_car(), i.getFinance_caramount(),
								i.getFinance_creditcard(),
								Long.valueOf(i.getUser_id()) });
	}

	public void updateContact(Userinfo i) {
		String sql = "update dw_userinfo set tel=?, post=?,phone=?,province=?,city=?,area=?, address=?, linkman1=?, relation1=?, tel1=?, phone1=?, linkman2=?, relation2=?, tel2=?, phone2=?, linkman3=?, relation3=?, tel3=?, phone3=?, msn=?, qq=?, wangwang=? where user_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getTel(), i.getPost(), i.getPhone(),
						i.getProvince(), i.getCity(), i.getArea(),
						i.getAddress(), i.getLinkman1(), i.getRelation1(),
						i.getTel1(), i.getPhone1(), i.getLinkman2(),
						i.getRelation2(), i.getTel2(), i.getPhone2(),
						i.getLinkman3(), i.getRelation3(), i.getTel3(),
						i.getPhone3(), i.getMsn(), i.getQq(), i.getWangwang(),
						Long.valueOf(i.getUser_id()) });
	}

	public void addContact(Userinfo i) {
		String sql = "insert into dw_userinfo(tel,post,phone,province,city,area,address,linkman1,relation1,tel1,phone1,linkman2,relation2,tel2,phone2,linkman3,relation3,tel3,phone3,msn,qq,wangwang,user_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getTel(), i.getPost(), i.getPhone(),
						i.getProvince(), i.getCity(), i.getArea(),
						i.getAddress(), i.getLinkman1(), i.getRelation1(),
						i.getTel1(), i.getPhone1(), i.getLinkman2(),
						i.getRelation2(), i.getTel2(), i.getPhone2(),
						i.getLinkman3(), i.getRelation3(), i.getTel3(),
						i.getPhone3(), i.getMsn(), i.getQq(), i.getWangwang(),
						Long.valueOf(i.getUser_id()) });
	}

	public void updateMate(Userinfo i) {
		String sql = "update dw_userinfo set mate_name=?, mate_salary=?, mate_phone=?, mate_tel=?, mate_type=?, mate_office=?, mate_address=?, mate_income=? where user_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getMate_name(), i.getMate_salary(),
						i.getMate_phone(), i.getMate_tel(), i.getMate_type(),
						i.getMate_office(), i.getMate_address(),
						i.getMate_income(), Long.valueOf(i.getUser_id()) });
	}

	public void addMate(Userinfo i) {
		String sql = "insert into dw_userinfo(mate_name,mate_salary,mate_phone,mate_tel,mate_type,mate_office,mate_address,mate_income,user_id) values(?,?,?,?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getMate_name(), i.getMate_salary(),
						i.getMate_phone(), i.getMate_tel(), i.getMate_type(),
						i.getMate_office(), i.getMate_address(),
						i.getMate_income(), Long.valueOf(i.getUser_id()) });
	}

	public void updateEducation(Userinfo i) {
		String sql = "update dw_userinfo set education_record=?, education_school=?, education_study=?, education_time1=?, education_time2=? where user_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getEducation_record(),
						i.getEducation_school(), i.getEducation_study(),
						i.getEducation_time1(), i.getEducation_time2(),
						Long.valueOf(i.getUser_id()) });
	}

	public void addEducation(Userinfo i) {
		String sql = "insert into dw_userinfo(education_record,education_school,education_study,education_time1,education_time2,user_id) values(?,?,?,?,?,?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { i.getEducation_record(),
						i.getEducation_school(), i.getEducation_study(),
						i.getEducation_time1(), i.getEducation_time2(),
						Long.valueOf(i.getUser_id()) });
	}

	public void updateOtherinfo(Userinfo i) {
		String sql = "update dw_userinfo set ability=?, interest=?, others=? where user_id=?";
		getJdbcTemplate().update(
				sql,
				new Object[] { i.getAbility(), i.getInterest(), i.getOthers(),
						Long.valueOf(i.getUser_id()) });
	}

	public void addOtherinfo(Userinfo i) {
		String sql = "insert into dw_userinfo(ability,interest,others,user_id) values(?,?,?,?)";
		getJdbcTemplate().update(
				sql,
				new Object[] { i.getAbility(), i.getInterest(), i.getOthers(),
						Long.valueOf(i.getUser_id()) });
	}

	public List<UserinfoModel> getUserInfoBypageNumber(int i, int Max) {
		String sql = "select p1.* ,p2.username,p2.realname,p2.email from dw_userinfo as p1 left join dw_user as p2 on p1.user_id=p2.user_id LIMIT ?,?";

		List<UserinfoModel> list = getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(i), Integer.valueOf(Max) },
				new UserInfoModelMapper());
		return list;
	}

	public List<UserinfoModel> getUserInfoBySearch(int page, int max, SearchParam p) {
		String sql = "select userinfo.* ,p2.username,p2.realname,p2.email from dw_userinfo as userinfo left join dw_user as p2 on p2.user_id=userinfo.user_id where 1=1 ";
		sql = sql + p.getSearchParamSql();
		sql = sql + "  limit ?,?";
		return getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new UserInfoModelMapper());
	}

	public int getUserInfocount() {
		String sql = "select count(*) from dw_userinfo as p1 left join dw_user as p2 on p1.user_id=p2.user_id";

		return getJdbcTemplate().queryForInt(sql);
	}

	public int getUserInfocount(SearchParam param) {
		String sql = "select count(*) from dw_userinfo as p1 left join dw_user as p2 on p1.user_id=p2.user_id where 1=1 ";

		sql = sql + param.getSearchParamSql();
		logger.debug("getUserInfocount(SearchParam param):" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public UserinfoModel getUserAllinfoByUserId(long id) {
		String sql = "SELECT u.*,utp.`name` as usertype,p.`name` as provincetext,p1.`name` as citytext,p2.`name` as areatext  ,p5.*,p6.*from dw_user as u LEFT JOIN dw_user_type as utp on u.type_id=utp.type_id LEFT JOIN dw_area as p on u.province=p.id   LEFT JOIN dw_area as p1 on  u.city=p1.id LEFT JOIN dw_area as p2 on u.area =p2.id LEFT JOIN dw_userinfo as p3 on u.user_id =p3.user_id LEFT JOIN dw_linkage as p7 on p3.relation1=p7.id LEFT JOIN dw_userinfo as p5 on u.user_id=p5.user_id LEFT JOIN dw_user_cache as p6 on p6.user_id=u.user_id where u.user_id=?";
		return (UserinfoModel) getJdbcTemplate()
				.queryForObject(sql, new Object[] { Long.valueOf(id) },
						new UserAllInfoModelMapper());
	}

	public int getSearchUserInfo(SearchParam p) {
		String sql = "SELECT count(*) from dw_user as p2 LEFT JOIN dw_user_cache as p on p.user_id=p2.user_id ";
		if (!StringUtils.isBlank(p.getAudit_user()))
			sql = sql
					+ " left join dw_message m on p2.user_id=m.receive_user where 1=1 and m.is_Authenticate=1";
		else {
			sql = sql + " where 1=1";
		}
		sql = sql + p.getSearchParamSql();
		logger.debug("sql=====" + sql);
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<DetailUser> getSearchUserInfo(int page, int max, SearchParam p) {
		String sql = "SELECT p2.*,p.vip_status,p.vip_verify_time,invite.username as invite_username,p.* from dw_user as p2 left join dw_user_cache as p on p.user_id=p2.user_id left join dw_user as invite on invite.user_id= p2.invite_userid ";

		if (!StringUtils.isBlank(p.getAudit_user()))
			sql = sql
					+ " left join dw_message m on p2.user_id=m.receive_user where 1=1 and m.is_Authenticate=1";
		else {
			sql = sql + " where 1=1";
		}

		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		logger.debug("sql=====" + sql);
		return getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new VipUserMapper());
	}

	public User getUserinfoByUsername(String username) {
		String sql = "select * from dw_user as p1 where p1.username=?";
		User userInfo = null;
		try {
			userInfo = (User) getJdbcTemplate().queryForObject(sql,
					new Object[] { username }, new UserMapper());
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return userInfo;
	}

	public AdminInfoModel getAdminAllinfoByUserId(long id) {
		String sql = "SELECT u.*,utp.`name` as usertype,p.`name` as provincetext,p1.`name` as citytext,p2.`name` as areatext  ,p5.*,p6.*from dw_user as u LEFT JOIN dw_user_type as utp on u.type_id=utp.type_id LEFT JOIN dw_area as p on u.province=p.id   LEFT JOIN dw_area as p1 on  u.city=p1.id LEFT JOIN dw_area as p2 on u.area =p2.id LEFT JOIN dw_userinfo as p3 on u.user_id =p3.user_id LEFT JOIN dw_linkage as p7 on p3.relation1=p7.id LEFT JOIN dw_userinfo as p5 on u.user_id=p5.user_id LEFT JOIN dw_user_cache as p6 on p6.user_id=u.user_id where u.user_id=?";
		return (AdminInfoModel) getJdbcTemplate().queryForObject(sql,
				new Object[] { Long.valueOf(id) }, new AdminInfoModelMapper());
	}

	public AttestationModel getUserAllCertifyByUserId(long id) {
		String sql = "select p1。*,p2.username,p2.realname from dw_attestation as p1 left join dw_user as p2 on p1.user_id = p2.user_id left join dw_attestation_type as p3 on p1.type_id = p3.type_id where p1.user_id=?";
		return (AttestationModel) getJdbcTemplate()
				.queryForObject(sql, new Object[] { Long.valueOf(id) },
						new AttestationModelMapper());
	}

	public int getSearchUserCertify(SearchParam p) {
		String sql = "SELECT count(*) from dw_attestation as p1 LEFT JOIN dw_attestation_type as p3 on p1.type_id = p3.type_id left join dw_user as p2 on p1.user_id=p2.user_id where 1=1";
		sql = sql + p.getSearchParamSql();
		return getJdbcTemplate().queryForInt(sql);
	}

	public List<AttestationModel> getSearchUserCertify(int page, int max, SearchParam p) {
		String sql = "SELECT p1.*,p3.`name` as type_name,p2.username ,p2.realname  from dw_attestation as p1 left join dw_attestation_type as p3 on p1.type_id=p3.type_id left join dw_user as p2 on p1.user_id = p2.user_id where p2.username is not null";

		sql = sql + p.getSearchParamSql();
		sql = sql + "  LIMIT ?,?";
		List<AttestationModel> list = getJdbcTemplate().query(sql,
				new Object[] { Integer.valueOf(page), Integer.valueOf(max) },
				new AttestationModelMapper());
		return list;
	}

	public Attestation modifyUserCertifyStatus(Attestation attestation) {
		String sql = "update dw_attestation set status=?,verify_time=?,verify_remark=? where id=?";
		int result = getJdbcTemplate().update(
				sql,
				new Object[] { Integer.valueOf(attestation.getStatus()),
						attestation.getVerify_time(),
						attestation.getVerify_remark(),
						Long.valueOf(attestation.getId()) });
		if (result < 1) {
			attestation = null;
		}
		return attestation;
	}

	public Attestation getAttestationById(String id) {
		String sql = "select p1.*,p3.`name` as type_name,p2.username,p2.realname from dw_attestation as p1 left join dw_attestation_type as p3 on p1.type_id = p3.type_id left join dw_user as p2 on p1.user_id = p2.user_id where p1.id =?";
		return (Attestation) getJdbcTemplate().queryForObject(sql,
				new Object[] { id }, new AttestationModelMapper());
	}

	public AttestationType getAttestationTypeByTypeId(int type_id) {
		String sql = "select p3.* from dw_attestation_type as p3 where p3.type_id =?";
		return (AttestationType) getJdbcTemplate().queryForObject(sql,
				new Object[] { Integer.valueOf(type_id) },
				new AttestationTypeMapper());
	}
}