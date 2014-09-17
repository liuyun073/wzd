package com.rd.service.impl;

import java.util.List;

import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.dao.AccountDao;
import com.rd.dao.AccountLogDao;
import com.rd.dao.AttestationDao;
import com.rd.dao.LinkageDao;
import com.rd.dao.UserCacheDao;
import com.rd.dao.UserCreditDao;
import com.rd.dao.UserDao;
import com.rd.dao.UserinfoDao;
import com.rd.domain.Account;
import com.rd.domain.AccountLog;
import com.rd.domain.Areainfo;
import com.rd.domain.Attestation;
import com.rd.domain.AttestationType;
import com.rd.domain.User;
import com.rd.domain.UserCache;
import com.rd.domain.UserCredit;
import com.rd.domain.UserCreditLog;
import com.rd.domain.Userinfo;
import com.rd.model.AdminInfoModel;
import com.rd.model.AttestationModel;
import com.rd.model.PageDataList;
import com.rd.model.SearchParam;
import com.rd.model.UserCreditLogModel;
import com.rd.model.UserCreditModel;
import com.rd.model.UserinfoModel;
import com.rd.service.UserinfoService;
import com.rd.tool.Page;
import com.rd.util.NumberUtils;

public class UserinfoServiceImpl implements UserinfoService {
	private UserinfoDao userinfoDao;
	private UserDao userDao;
	private LinkageDao linkageDao;
	private AttestationDao attestationDao;
	private UserCacheDao userCacheDao;
	private UserCreditDao userCreditDao;
	private AccountDao accountDao;
	private AccountLogDao accountLogDao;

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}

	public UserCacheDao getUserCacheDao() {
		return this.userCacheDao;
	}

	public void setUserCacheDao(UserCacheDao userCacheDao) {
		this.userCacheDao = userCacheDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserinfoDao getUserinfoDao() {
		return this.userinfoDao;
	}

	public void setUserinfoDao(UserinfoDao userinfoDao) {
		this.userinfoDao = userinfoDao;
	}

	public LinkageDao getLinkageDao() {
		return this.linkageDao;
	}

	public void setLinkageDao(LinkageDao linkageDao) {
		this.linkageDao = linkageDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public AttestationDao getAttestationDao() {
		return this.attestationDao;
	}

	public void setAttestationDao(AttestationDao attestationDao) {
		this.attestationDao = attestationDao;
	}

	public Userinfo getUserinfoByUserid(long user_id) {
		return this.userinfoDao.getUserinfoByUserid(user_id);
	}

	public void updateUserinfo(UserinfoModel model) {
		User user = this.userDao.getUserById(model.getUser_id());
		user.setUser_id(model.getUser_id());

		user.setSex(model.getSex());

		user.setProvince(model.getProvince());
		user.setCity(model.getCity());
		user.setArea(model.getArea());

		this.userDao.updateUser(user);

		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setMarry(model.getMarry());
		userinfo.setChild(model.getChild());
		userinfo.setEducation(model.getEducation());
		userinfo.setIncome(model.getIncome());
		userinfo.setShebao(model.getShebao());
		userinfo.setShebaoid(model.getShebaoid());
		userinfo.setHousing(model.getHousing());
		userinfo.setCar(model.getCar());
		userinfo.setLate(model.getLate());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addUserinfo(userinfo);
		else
			this.userinfoDao.updateUserinfo(userinfo);
	}

	public List<Areainfo> getAreainfoByPid(String pid) {
		return this.linkageDao.getAreainfoByPid(pid);
	}

	public void updateBuilding(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setHouse_address(model.getHouse_address());
		userinfo.setHouse_area(model.getHouse_area());
		userinfo.setHouse_year(model.getHouse_year());
		userinfo.setHouse_status(model.getHouse_status());
		userinfo.setHouse_holder1(model.getHouse_holder1());
		userinfo.setHouse_holder2(model.getHouse_holder2());
		userinfo.setHouse_right1(model.getHouse_right1());
		userinfo.setHouse_right2(model.getHouse_right2());
		userinfo.setHouse_loanyear(model.getHouse_loanyear());
		userinfo.setHouse_loanprice(model.getHouse_loanprice());
		userinfo.setHouse_balance(model.getHouse_balance());
		userinfo.setHouse_bank(model.getHouse_bank());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addBuilding(userinfo);
		else
			this.userinfoDao.updateBuilding(userinfo);
	}

	public void updateCompany(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setCompany_name(model.getCompany_name());
		userinfo.setCompany_type(model.getCompany_type());
		userinfo.setCompany_industry(model.getCompany_industry());
		userinfo.setCompany_office(model.getCompany_office());
		userinfo.setCompany_jibie(model.getCompany_jibie());
		userinfo.setCompany_worktime1(model.getCompany_worktime1());
		userinfo.setCompany_worktime2(model.getCompany_worktime2());
		userinfo.setCompany_workyear(model.getCompany_workyear());
		userinfo.setCompany_address(model.getCompany_address());
		userinfo.setCompany_weburl(model.getCompany_weburl());
		userinfo.setCompany_reamrk(model.getCompany_reamrk());
		userinfo.setCompany_tel(model.getCompany_tel());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addCompany(userinfo);
		else
			this.userinfoDao.updateCompany(userinfo);
	}

	public void updateFirm(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setPrivate_type(model.getPrivate_type());
		userinfo.setPrivate_date(model.getPrivate_date());
		userinfo.setPrivate_place(model.getPrivate_place());
		userinfo.setPrivate_rent(model.getPrivate_rent());
		userinfo.setPrivate_term(model.getPrivate_term());
		userinfo.setPrivate_taxid(model.getPrivate_taxid());
		userinfo.setPrivate_commerceid(model.getPrivate_commerceid());
		userinfo.setPrivate_income(model.getPrivate_income());
		userinfo.setPrivate_employee(model.getPrivate_employee());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addFirm(userinfo);
		else
			this.userinfoDao.updateFirm(userinfo);
	}

	public void updateFinance(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setFinance_repayment(model.getFinance_repayment());
		userinfo.setFinance_property(model.getFinance_property());
		userinfo.setFinance_amount(model.getFinance_amount());
		userinfo.setFinance_car(model.getFinance_car());
		userinfo.setFinance_caramount(model.getFinance_caramount());
		userinfo.setFinance_creditcard(model.getFinance_creditcard());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addFinance(userinfo);
		else
			this.userinfoDao.updateFinance(userinfo);
	}

	public void updateContact(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setTel(model.getTel());
		userinfo.setPhone(model.getPhone());
		userinfo.setPost(model.getPost());
		userinfo.setAddress(model.getAddress());
		userinfo.setProvince(model.getProvince());
		userinfo.setCity(model.getCity());
		userinfo.setArea(model.getArea());
		userinfo.setLinkman1(model.getLinkman1());
		userinfo.setRelation1(model.getRelation1());
		userinfo.setTel1(model.getTel1());
		userinfo.setPhone1(model.getPhone1());
		userinfo.setLinkman2(model.getLinkman2());
		userinfo.setRelation2(model.getRelation2());
		userinfo.setTel2(model.getTel2());
		userinfo.setPhone2(model.getPhone2());
		userinfo.setLinkman3(model.getLinkman3());
		userinfo.setRelation3(model.getRelation3());
		userinfo.setTel3(model.getTel3());
		userinfo.setPhone3(model.getPhone3());
		userinfo.setMsn(model.getMsn());
		userinfo.setQq(model.getQq());
		userinfo.setWangwang(model.getWangwang());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addContact(userinfo);
		else
			this.userinfoDao.updateContact(userinfo);
	}

	public void updateMate(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setMate_name(model.getMate_name());
		userinfo.setMate_salary(model.getMate_salary());
		userinfo.setMate_phone(model.getMate_phone());
		userinfo.setMate_type(model.getMate_type());
		userinfo.setMate_office(model.getMate_office());
		userinfo.setMate_address(model.getMate_address());
		userinfo.setMate_income(model.getMate_income());
		userinfo.setMate_tel(model.getMate_tel());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addMate(userinfo);
		else
			this.userinfoDao.updateMate(userinfo);
	}

	public void updateEducation(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setEducation_record(model.getEducation_record());
		userinfo.setEducation_school(model.getEducation_school());
		userinfo.setEducation_study(model.getEducation_study());
		userinfo.setEducation_time1(model.getEducation_time1());
		userinfo.setEducation_time2(model.getEducation_time2());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.addEducation(userinfo);
		else
			this.userinfoDao.updateEducation(userinfo);
	}

	public void updateOtherinfo(UserinfoModel model) {
		Userinfo userinfo = new Userinfo();
		userinfo.setUser_id(model.getUser_id());
		userinfo.setOthers(model.getOthers());
		userinfo.setAbility(model.getAbility());
		userinfo.setInterest(model.getInterest());

		Userinfo existUserinfo = this.userinfoDao.getUserinfoByUserid(model
				.getUser_id());
		if (existUserinfo == null)
			this.userinfoDao.updateOtherinfo(userinfo);
		else
			this.userinfoDao.updateOtherinfo(userinfo);
	}

	public List<Attestation> getAttestationListByUserid(long user_id) {
		return this.attestationDao.getListByUserid(user_id);
	}

	public Attestation addAttestation(Attestation att) {
		return this.attestationDao.addAttestation(att);
	}

	public List<Attestation> getAttestationListByUserid(long user_id, int status) {
		return this.attestationDao.getListByUserid(user_id, status);
	}

	public PageDataList getUserInfoListByPageNumber(int page,
			SearchParam searchParam) {
		PageDataList pageDataList = new PageDataList();
		Page pages = new Page(this.userinfoDao.getUserInfocount(searchParam), page, 10);
		pageDataList.setList(this.userinfoDao.getUserInfoBySearch(pages.getStart(), pages.getPernum(), searchParam));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public UserinfoModel getUserALLInfoModelByUserid(long user_id) {
		return this.userinfoDao.getUserAllinfoByUserId(user_id);
	}

	public PageDataList getUserVipinfo(int page, int max, int status,
			SearchParam p) {
		PageDataList pageDataList = new PageDataList();
		Page pages = new Page(this.userCacheDao.getUserVipinfo(status, p),
				page, max);
		pageDataList.setList(this.userCacheDao.getUserVipinfo(pages.getStart(),
				max, status, p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public PageDataList getSearchUserInfo(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();

		Page pages = new Page(this.userinfoDao.getSearchUserInfo(p), page, 10);
		pageDataList.setList(this.userinfoDao.getSearchUserInfo(pages
				.getStart(), pages.getPernum(), p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public PageDataList getSearchUserInfo(SearchParam p, int page) {
		PageDataList pageDataList = new PageDataList();

		Page pages = new Page(this.userinfoDao.getSearchUserInfo(p), page, 10);
		pageDataList.setList(this.userinfoDao.getUserInfoBySearch(pages.getStart(), 10, p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public User getUserinfoByUsername(String username) {
		return this.userinfoDao.getUserinfoByUsername(username);
	}

	public void updateUserCredit(UserCredit userCredit) {
		this.userCreditDao.updateUserCredit(userCredit);
	}

	public void updateCreditTenderValue(UserCredit userCredit) {
		this.userCreditDao.updateCreditTenderValue(userCredit);
	}

	public UserCredit getUserCreditByUserId(long user_id) {
		UserCredit userCredit = null;
		userCredit = this.userCreditDao.getUserCreditByUserId(user_id);
		if (userCredit == null) {
			return null;
		}
		return userCredit;
	}

	public void updateUserCache(UserCache userCache) {
		this.userCacheDao.updateUserCache(userCache);
	}

	public void VerifyVipSuccess(UserCache userCache) {
		this.userCacheDao.updateUserCache(userCache);
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));
		this.accountDao.updateAccount(-vipfee, 0.0D, -vipfee, userCache
				.getUser_id());
	}

	public void VerifyVipFail(UserCache userCache) {
		this.userCacheDao.updateUserCache(userCache);
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));
		this.accountDao.updateAccount(0.0D, vipfee, -vipfee, userCache
				.getUser_id());
	}

	public void updateAdmininfo(AdminInfoModel model) {
		User user = new User();
		user.setUser_id(model.getUser_id());
		user.setRealname(model.getRealname());
		user.setSex(model.getSex());
		user.setPhone(model.getPhone());
		user.setTel(model.getTel());
		user.setEmail(model.getEmail());
		user.setBirthday(model.getBirthday());
		user.setCard_type(model.getCard_type());
		user.setCard_id(model.getCard_id());
		user.setAddress(model.getAddress());
		user.setProvince(model.getProvince());
		user.setCity(model.getCity());
		user.setArea(model.getArea());
		user.setQq(model.getQq());
		user.setWangwang(model.getWangwang());

		this.userDao.updateUser(user);
	}

	public AdminInfoModel getAllAdminInfoModelByUserid(long user_id) {
		return this.userinfoDao.getAdminAllinfoByUserId(user_id);
	}

	public void VerifyVipSuccess(UserCache userCache, AccountLog accountLog) {
		this.userCacheDao.updateUserCache(userCache);
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));
		if (vipfee > 0.0D) {
			this.accountDao.updateAccount(-vipfee, 0.0D, -vipfee, userCache
					.getUser_id());

			Account act = this.accountDao.getAccount(userCache.getUser_id());
			accountLog.setMoney(vipfee);
			accountLog.setTotal(act.getTotal());
			accountLog.setUse_money(act.getUse_money());
			accountLog.setNo_use_money(act.getNo_use_money());
			accountLog.setCollection(act.getCollection());
			accountLog.setRemark("申请VIP成功，扣除资金" + vipfee + "元");
			this.accountLogDao.addAccountLog(accountLog);
		}
	}

	public void VerifyVipSuccess(UserCache userCache, AccountLog vipLog,
			AccountLog inviteLog) {
		this.userCacheDao.updateUserCache(userCache);
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));

		if (vipfee > 0.0D) {
			this.accountDao.updateAccount(-vipfee, 0.0D, -vipfee, userCache
					.getUser_id());
			Account act = this.accountDao.getAccount(userCache.getUser_id());
			vipLog.setMoney(vipfee);
			vipLog.setTotal(act.getTotal());
			vipLog.setUse_money(act.getUse_money());
			vipLog.setNo_use_money(act.getNo_use_money());
			vipLog.setCollection(act.getCollection());
			vipLog.setRemark("申请VIP成功，扣除资金" + vipfee + "元");
			this.accountLogDao.addAccountLog(vipLog);
		}
	}

	public void VerifyVipFail(UserCache userCache, AccountLog accountLog) {
		this.userCacheDao.updateUserCache(userCache);
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));
		if (vipfee > 0.0D) {
			this.accountDao.updateAccount(0.0D, vipfee, -vipfee, userCache
					.getUser_id());

			Account act = this.accountDao.getAccount(userCache.getUser_id());
			accountLog.setMoney(vipfee);
			accountLog.setTotal(act.getTotal());
			accountLog.setUse_money(act.getUse_money());
			accountLog.setNo_use_money(act.getNo_use_money());
			accountLog.setCollection(act.getCollection());
			accountLog.setRemark("申请VIP失败，解冻资金");
			this.accountLogDao.addAccountLog(accountLog);
		}
	}

	public AttestationModel getUserAllCertifyByUserId(long user_id) {
		return this.userinfoDao.getUserAllCertifyByUserId(user_id);
	}

	public PageDataList getSearchUserCertify(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();

		Page pages = new Page(this.userinfoDao.getSearchUserCertify(p), page,
				10);
		pageDataList.setList(this.userinfoDao.getSearchUserCertify(pages
				.getStart(), pages.getPernum(), p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public Attestation modifyUserCertifyStatus(Attestation attestation) {
		return this.userinfoDao.modifyUserCertifyStatus(attestation);
	}

	public Attestation getAttestationById(String id) {
		return this.userinfoDao.getAttestationById(id);
	}

	public AttestationType getAttestationTypeByTypeId(int typeId) {
		return this.userinfoDao.getAttestationTypeByTypeId(typeId);
	}

	public PageDataList getUserCreditList(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();

		Page pages = new Page(this.userCreditDao.getUserCreditCount(p), page,
				10);
		pageDataList.setList(this.userCreditDao.getUserCredit(pages.getStart(),
				10, p));
		pageDataList.setPage(pages);
		return pageDataList;
	}

	public void addUserCreditLog(UserCreditLog userCreditLog) {
		this.userCreditDao.addUserCreditLog(userCreditLog);
	}

	public UserCreditModel getCreditModelById(long user_id) {
		return this.userCreditDao.getCreditModelById(user_id);
	}

	public UserCreditLogModel getCreditLogByUserId(long user_id) {
		return this.userCreditDao.getCreditLogByUserId(user_id);
	}

	public PageDataList getCreditLogList(int page, SearchParam p) {
		PageDataList pageDataList = new PageDataList();

		Page pages = new Page(this.userCreditDao.getCreditLogCount(p), page, 10);
		pageDataList.setList(this.userCreditDao.getCreditLog(pages.getStart(),
				10, p));
		pageDataList.setPage(pages);
		return pageDataList;
	}
}