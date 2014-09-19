package com.liuyun.site.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.liuyun.site.context.Constant;
import com.liuyun.site.context.Global;
import com.liuyun.site.dao.AccountDao;
import com.liuyun.site.dao.AccountLogDao;
import com.liuyun.site.dao.RewardStatisticsDao;
import com.liuyun.site.dao.UserAmountDao;
import com.liuyun.site.dao.UserCacheDao;
import com.liuyun.site.dao.UserCreditDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.dao.UserTrackDao;
import com.liuyun.site.domain.Account;
import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserAmount;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserTrack;
import com.liuyun.site.domain.UserType;
import com.liuyun.site.exception.BorrowException;
import com.liuyun.site.exception.UserServiceException;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserCacheModel;
import com.liuyun.site.model.UserinfoModel;
import com.liuyun.site.model.VIPStatisticModel;
import com.liuyun.site.service.UserService;
import com.liuyun.site.tool.Page;
import com.liuyun.site.tool.coder.MD5;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private UserTrackDao userTrackDao;
	private UserCacheDao userCacheDao;
	private UserCreditDao userCreditDao;
	private AccountDao accountDao;
	private UserAmountDao userAmountDao;
	private AccountLogDao accountLogDao;
	private RewardStatisticsDao rewardStatisticsDao;

	public AccountLogDao getAccountLogDao() {
		return this.accountLogDao;
	}

	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserTrackDao getUserTrackDao() {
		return this.userTrackDao;
	}

	public void setUserTrackDao(UserTrackDao userTrackDao) {
		this.userTrackDao = userTrackDao;
	}

	public UserCacheDao getUserCacheDao() {
		return this.userCacheDao;
	}

	public void setUserCacheDao(UserCacheDao userCacheDao) {
		this.userCacheDao = userCacheDao;
	}

	public UserCreditDao getUserCreditDao() {
		return this.userCreditDao;
	}

	public void setUserCreditDao(UserCreditDao userCreditDao) {
		this.userCreditDao = userCreditDao;
	}

	public AccountDao getAccountDao() {
		return this.accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public UserAmountDao getUserAmountDao() {
		return this.userAmountDao;
	}

	public void setUserAmountDao(UserAmountDao userAmountDao) {
		this.userAmountDao = userAmountDao;
	}

	public User register(User user) {
		User inviteUser = this.userDao.getUserByUsername(user.getInvite_username());
		if (inviteUser != null) {
			user.setInvite_userid("" + inviteUser.getUser_id());
		}

		user.setStatus(1);
		user.setType_id(2);
		this.userDao.addUser(user);
		User u = this.userDao.getUserByUsername(user.getUsername());

		Account act = new Account(u.getUser_id(), 0.0D, 0.0D, 0.0D, 0.0D);
		this.accountDao.addAccount(act);

		UserAmount amount = new UserAmount(u.getUser_id(), 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		this.userAmountDao.addAmount(amount);

		return u;
	}

	public User login(String username, String password) {
		User u = null;

		MD5 md5 = new MD5();
		u = this.userDao.getUserByUsernameAndPassword(username, md5.getMD5ofStr(password));
		return u;
	}

	public DetailUser getDetailUser(long userid) {
		return this.userDao.getDetailUserByUserid(userid);
	}

	public boolean checkUsername(String username) {
		User user = null;
		try {
			user = this.userDao.getUserByUsername(username);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return user == null;
	}

	public boolean checkEmail(String email) {
		User user = null;
		try {
			user = this.userDao.getUserByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}

		return user == null;
	}

	public User getUserByName(String username) {
		return this.userDao.getUserByUsername(username);
	}

	public void addUserTrack(UserTrack ut) {
		this.userTrackDao.addUserTrack(ut);
	}

	public UserTrack getLastUserTrack(long userid) {
		return this.userTrackDao.getLastUserTrack(userid);
	}

	public User getUserById(long user_id) {
		return this.userDao.getUserById(user_id);
	}

	public UserCacheModel getUserCacheByUserid(long userid) {
		return this.userCacheDao.getUserCacheByUserid(userid);
	}

	public void addUserCredit(UserCredit uc) {
		this.userCreditDao.addUserCredit(uc);
	}

	public UserCacheModel applyVip(UserCache uc) {
		UserCacheModel cache = this.userCacheDao.getUserCacheByUserid(uc.getUser_id());
		if (cache == null)
			this.userCacheDao.addUserCache(uc);
		else {
			this.userCacheDao.updateUserCache(uc);
		}
		cache = this.userCacheDao.getUserCacheByUserid(uc.getUser_id());
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));
		this.accountDao.updateAccount(0.0D, 0.0D - vipfee, vipfee, uc.getUser_id());

		return cache;
	}

	public UserCacheModel saveUserCache(UserCache uc) {
		UserCacheModel cache = this.userCacheDao.getUserCacheByUserid(uc.getUser_id());
		if (cache == null)
			this.userCacheDao.addUserCache(uc);
		else {
			this.userCacheDao.updateUserCache(uc);
		}
		return cache;
	}

	public User realnameIdentify(UserinfoModel model) {
		User user = new User();
		user.setUser_id(model.getUser_id());
		user.setRealname(model.getRealname());
		user.setSex(model.getSex());
		user.setNation(model.getNation());
		user.setBirthday(model.getBirthday());
		user.setCard_type(model.getCard_type());
		user.setCard_id(model.getCard_id());
		user.setProvince(model.getProvince());
		user.setCity(model.getCity());
		user.setArea(model.getArea());
		user.setCard_pic1(model.getCard_pic1_path());
		user.setCard_pic2(model.getCard_pic2_path());
		user.setReal_status(model.getReal_status());
		User newUser = this.userDao.realnameIdentify(user);
		return newUser;
	}

	public User modifyPhone(User user) {
		this.userDao.modifyPhone(user);

		return user;
	}

	public User modifyEmail_status(User user) {
		return this.userDao.modifyEmail_status(user);
	}

	public User modifyUserPwd(User user) {
		return this.userDao.modifyUserPwd(user);
	}

	public User modifyPayPwd(User user) {
		return this.userDao.modifyPayPwd(user);
	}

	public User modifyAnswer(User user) {
		return this.userDao.modifyAnswer(user);
	}

	public List<User> getKfList() {
		if (StringUtils.isNull(Global.getValue("webid")).equals("jsy")) {
			String serviceListStr = Global.getValue("service_list");
			String[] serviceListArr = new String[0];
			serviceListArr = serviceListStr.split(",");

			List<User> serviceList = new ArrayList<User>();
			if ((serviceListArr != null) && (serviceListArr.length > 0)) {
				for (String s : serviceListArr) {
					serviceList.addAll(this.userDao.getAllUser(NumberUtils.getInt(s)));
				}
			}
			return serviceList;
		}
		return this.userDao.getAllKfList();
	}

	public List<User> getVerifyUser() {
		return this.userDao.getAllVerifyUser();
	}

	public List<DetailUser> getInvitedUserByUserid(long user_id) {
		return this.userDao.getInvitedUserByUserid(user_id);
	}

	public PageDataList getUserList(int page, SearchParam param) {
		int total = this.userDao.getUserCount(param);
		Page p = new Page(total, page);
		List<DetailUser> list = this.userDao.getUserList(p.getStart(), p.getPernum(), param);
		return new PageDataList(p, list);
	}

	public List<DetailUser> getUserList(SearchParam param) {
		List<DetailUser> list = this.userDao.getUserList(param);
		return list;
	}

	public void updateuser(User user) {
		if ((user.getPassword() != null) && (user.getPassword().length() > 1))
			this.userDao.updateUser(user, true);
		else
			this.userDao.updateUser(user, false);
	}

	public List<UserType> getAllUserType() {
		return this.userDao.getAllUserType();
	}

	public void updateUserTypeByList(List list) {
		this.userDao.updateUserType(list);
	}

	public void addUserType(UserType usertype) {
		this.userDao.addUserType(usertype);
	}

	public void deleteUserTypeById(long id) {
		boolean isRoleHasPurview = this.userDao.isRoleHasPurview(id);
		if (isRoleHasPurview) {
			throw new UserServiceException("该角色没有权限,不能删除");
		}
		UserType userType = this.userDao.getUserTypeById(id);
		if (userType == null) {
			throw new UserServiceException("该角色不存在！");
		}
		this.userDao.deleteUserTypeById(id);
	}

	public PageDataList getInviteUserList(int page, SearchParam param) {
		int total = this.userDao.getInviteUserCount(param);
		Page p = new Page(total, page);
		List list = this.userDao.getInviteUserList(p.getStart(), p.getPernum(),
				param);
		return new PageDataList(p, list);
	}

	public UserType getUserTypeById(long id) {
		return this.userDao.getUserTypeById(id);
	}

	public User modifyEmail(User user) {
		return this.userDao.modifyEmail(user);
	}

	public User modifyPhone_status(User user) {
		return this.userDao.modifyPhone_status(user);
	}

	public User modifyReal_status(User user) {
		return this.userDao.modifyReal_status(user);
	}

	public User modifyVideo_status(User user) {
		return this.userDao.modifyVideo_status(user);
	}

	public User modifyScene_status(User user) {
		return this.userDao.modifyScene_status(user);
	}

	public PageDataList getUserList(int page, SearchParam param, int type) {
		int total = this.userDao.getUserCount(param, type);
		Page p = new Page(total, page);
		List list = this.userDao.getUserList(p.getStart(), p.getPernum(),
				param, type);
		return new PageDataList(p, list);
	}

	public DetailUser getDetailUser(long userid, int type) {
		return this.userDao.getDetailUser(userid, type);
	}

	public UserCacheModel applyVip(UserCache uc, AccountLog accountLog) {
		UserCacheModel cache = this.userCacheDao.getUserCacheByUserid(uc
				.getUser_id());
		Account act = this.accountDao.getAccount(uc.getUser_id());
		if (cache == null)
			this.userCacheDao.addUserCache(uc);
		else {
			this.userCacheDao.updateUserCache(uc);
		}
		cache = this.userCacheDao.getUserCacheByUserid(uc.getUser_id());
		double vipfee = NumberUtils.getDouble(Global.getValue(Constant.VIP_FEE));

		if (vipfee >= 0.0D) {
			int row = 0;
			row = this.accountDao.updateAccountNotZero(0.0D, 0.0D - vipfee,
					vipfee, uc.getUser_id());
			if (row < 1)
				throw new BorrowException("扣除冻结款出错！");
			accountLog.setMoney(vipfee);
			accountLog.setTotal(act.getTotal());
			accountLog.setUse_money(act.getUse_money() - vipfee);
			accountLog.setNo_use_money(act.getNo_use_money() + vipfee);
			accountLog.setCollection(act.getCollection());
			accountLog.setRemark("申请VIP，冻结资金" + vipfee + "元");
			this.accountLogDao.addAccountLog(accountLog);
		}

		return cache;
	}

	public void updateUserLastInfo(User user) {
		this.userDao.updateUserLastInfo(user);
	}

	public int userTrackCount(long user_id) {
		int total = this.userTrackDao.getUserTrackCount(user_id);
		return total;
	}

	public User getUserByCardNO(String card) {
		return this.userDao.getUserByCard(card);
	}

	public List<User> getAllUser(int type) {
		return this.userDao.getAllUser(type);
	}

	public UserCacheModel validUserVip(long user_id) {
		UserCacheModel uc = this.userCacheDao.validUserVip(user_id);
		return uc;
	}

	public PageDataList getVipStatistic(int page, SearchParam param) {
		int total = this.userCacheDao.getVipStatistic(param);
		Page p = new Page(total, page);
		List list = this.userCacheDao.getVipStatisticList(p.getStart(), p
				.getPernum(), param);
		return new PageDataList(p, list);
	}

	public List<VIPStatisticModel> getVipStatistic(SearchParam param) {
		List<VIPStatisticModel> list = this.userCacheDao.getVipStatisticList(param);
		return list;
	}

	public int userCount() {
		return this.userDao.getUserCount();
	}

	public List<DetailUser> getInviteUserList(SearchParam param) {
		List<DetailUser> list = this.userDao.getInviteUserList(param);
		return list;
	}

	public int getLoginFailTimes(long user_id) {
		return this.userCacheDao.getLoginFailTimes(user_id);
	}

	public void cleanLoginFailTimes(long user_id) {
		this.userCacheDao.cleanLoginFailTimes(user_id);
	}

	public void updateLoginFailTimes(long user_id) {
		this.userCacheDao.updateLoginFailTimes(user_id);
	}

	public void updateUserIsLock(long user_id) {
		this.userDao.updateUserIsLock(user_id);
	}

	public List getKfListWithLimit(int start, int end) {
		return this.userDao.getKfListWithLimit(start, end);
	}
}