package com.liuyun.site.dao.jdbc.mapper.base;

import com.liuyun.site.domain.Userinfo;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractAdminInfoMapper {
	
	protected void setProperty(ResultSet rs, Userinfo info) throws SQLException {
		info.setId(rs.getLong("id"));
		info.setSite_id(rs.getLong("site_id"));
		info.setUser_id(rs.getLong("user_id"));
		info.setName(rs.getString("name"));
		info.setStatus(rs.getInt("status"));
		info.setOrder(rs.getInt("order"));
		info.setHits(rs.getInt("hits"));
		info.setLitpic(rs.getString("litpic"));
		info.setFlag(rs.getString("flag"));
		info.setSource(rs.getString("source"));
		info.setPublish(rs.getString("publish"));
		info.setMarry(rs.getString("marry"));
		info.setChild(rs.getString("child"));
		info.setEducation(rs.getString("education"));
		info.setIncome(rs.getString("income"));
		info.setShebao(rs.getString("shebao"));
		info.setShebaoid(rs.getString("shebaoid"));
		info.setHousing(rs.getString("housing"));
		info.setCar(rs.getString("car"));
		info.setLate(rs.getString("late"));
		info.setHouse_address(rs.getString("house_address"));
		info.setHouse_area(rs.getString("house_area"));
		info.setHouse_year(rs.getString("house_year"));
		info.setHouse_status(rs.getString("house_status"));
		info.setHouse_holder1(rs.getString("house_holder1"));
		info.setHouse_holder2(rs.getString("house_holder2"));
		info.setHouse_right1(rs.getString("house_right1"));
		info.setHouse_right2(rs.getString("house_right2"));
		info.setHouse_loanyear(rs.getString("house_loanyear"));
		info.setHouse_loanprice(rs.getString("house_loanprice"));
		info.setHouse_balance(rs.getString("house_balance"));
		info.setHouse_bank(rs.getString("house_bank"));
		info.setCompany_name(rs.getString("company_name"));
		info.setCompany_type(rs.getString("company_type"));
		info.setCompany_industry(rs.getString("company_industry"));
		info.setCompany_office(rs.getString("company_office"));
		info.setCompany_jibie(rs.getString("company_jibie"));
		info.setCompany_worktime1(rs.getString("company_worktime1"));
		info.setCompany_worktime2(rs.getString("company_worktime2"));
		info.setCompany_workyear(rs.getString("company_workyear"));
		info.setCompany_tel(rs.getString("company_tel"));
		info.setCompany_address(rs.getString("company_address"));
		info.setCompany_weburl(rs.getString("company_weburl"));
		info.setCompany_reamrk(rs.getString("company_reamrk"));
		info.setPrivate_type(rs.getString("private_type"));
		info.setPrivate_date(rs.getString("private_date"));
		info.setPrivate_place(rs.getString("private_place"));
		info.setPrivate_rent(rs.getString("private_rent"));
		info.setPrivate_term(rs.getString("private_term"));
		info.setPrivate_taxid(rs.getString("private_taxid"));
		info.setPrivate_commerceid(rs.getString("private_commerceid"));
		info.setPrivate_income(rs.getString("private_income"));
		info.setPrivate_employee(rs.getString("private_employee"));
		info.setFinance_repayment(rs.getString("finance_repayment"));
		info.setFinance_property(rs.getString("finance_property"));
		info.setFinance_amount(rs.getString("finance_amount"));
		info.setFinance_car(rs.getString("finance_car"));
		info.setFinance_caramount(rs.getString("finance_caramount"));
		info.setFinance_creditcard(rs.getString("finance_creditcard"));
		info.setMate_name(rs.getString("mate_name"));
		info.setMate_salary(rs.getString("mate_salary"));
		info.setMate_phone(rs.getString("mate_phone"));
		info.setMate_tel(rs.getString("mate_tel"));
		info.setMate_type(rs.getString("mate_type"));
		info.setMate_office(rs.getString("mate_office"));
		info.setMate_address(rs.getString("mate_address"));
		info.setMate_income(rs.getString("mate_income"));
		info.setEducation_record(rs.getString("education_record"));
		info.setEducation_school(rs.getString("education_school"));
		info.setEducation_study(rs.getString("education_study"));
		info.setEducation_time1(rs.getString("education_time1"));
		info.setEducation_time2(rs.getString("education_time2"));
		info.setTel(rs.getString("tel"));
		info.setPhone(rs.getString("phone"));
		info.setPost(rs.getString("post"));
		info.setAddress(rs.getString("address"));
		info.setProvince(rs.getString("province"));
		info.setCity(rs.getString("city"));
		info.setArea(rs.getString("area"));
		info.setLinkman1(rs.getString("linkman1"));
		info.setRelation1(rs.getString("relation1"));
		info.setTel1(rs.getString("tel1"));
		info.setPhone1(rs.getString("phone1"));
		info.setLinkman2(rs.getString("linkman2"));
		info.setRelation2(rs.getString("relation2"));
		info.setTel2(rs.getString("tel2"));
		info.setPhone2(rs.getString("phone2"));
		info.setLinkman3(rs.getString("linkman3"));
		info.setRelation3(rs.getString("relation3"));
		info.setTel3(rs.getString("tel3"));
		info.setPhone3(rs.getString("phone3"));
		info.setMsn(rs.getString("msn"));
		info.setQq(rs.getString("qq"));
		info.setWangwang(rs.getString("wangwang"));
		info.setAbility(rs.getString("ability"));
		info.setInterest(rs.getString("interest"));
		info.setOthers(rs.getString("others"));
		info.setExperience(rs.getString("experience"));
		info.setAddtime(rs.getString("addtime"));
		info.setAddip(rs.getString("addip"));
		info.setUpdatetime(rs.getString("updatetime"));
		info.setUpdateip(rs.getString("updateip"));
	}
}