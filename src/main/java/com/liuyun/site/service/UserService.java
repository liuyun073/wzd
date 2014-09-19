package com.liuyun.site.service;

import com.liuyun.site.domain.AccountLog;
import com.liuyun.site.domain.User;
import com.liuyun.site.domain.UserCache;
import com.liuyun.site.domain.UserCredit;
import com.liuyun.site.domain.UserTrack;
import com.liuyun.site.domain.UserType;
import com.liuyun.site.model.DetailUser;
import com.liuyun.site.model.PageDataList;
import com.liuyun.site.model.SearchParam;
import com.liuyun.site.model.UserCacheModel;
import com.liuyun.site.model.UserinfoModel;
import com.liuyun.site.model.VIPStatisticModel;

import java.util.List;

public abstract interface UserService {
	public abstract User register(User paramUser);

	public abstract User login(String paramString1, String paramString2);

	public abstract boolean checkUsername(String paramString);

	public abstract boolean checkEmail(String paramString);

	public abstract void addUserTrack(UserTrack paramUserTrack);

	public abstract UserTrack getLastUserTrack(long paramLong);

	public abstract DetailUser getDetailUser(long paramLong);

	public abstract UserCacheModel getUserCacheByUserid(long paramLong);

	public abstract User getUserByName(String paramString);

	public abstract User getUserById(long paramLong);

	public abstract void addUserCredit(UserCredit paramUserCredit);

	public abstract UserCacheModel saveUserCache(UserCache paramUserCache);

	public abstract UserCacheModel applyVip(UserCache paramUserCache);

	public abstract User realnameIdentify(UserinfoModel paramUserinfoModel);

	public abstract User modifyEmail(User paramUser);

	public abstract User modifyPhone(User paramUser);

	public abstract User modifyEmail_status(User paramUser);

	public abstract User modifyPhone_status(User paramUser);

	public abstract User modifyReal_status(User paramUser);

	public abstract User modifyVideo_status(User paramUser);

	public abstract User modifyScene_status(User paramUser);

	public abstract User modifyUserPwd(User paramUser);

	public abstract User modifyPayPwd(User paramUser);

	public abstract User modifyAnswer(User paramUser);

	public abstract List<User> getKfList();

	public abstract List getKfListWithLimit(int paramInt1, int paramInt2);

	public abstract List<User> getVerifyUser();

	public abstract List<DetailUser> getInvitedUserByUserid(long paramLong);

	public abstract UserCacheModel validUserVip(long paramLong);

	public abstract PageDataList getUserList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void updateuser(User paramUser);

	public abstract List<UserType> getAllUserType();

	public abstract UserType getUserTypeById(long paramLong);

	public abstract void updateUserTypeByList(List paramList);

	public abstract void addUserType(UserType paramUserType);

	public abstract void deleteUserTypeById(long paramLong);

	public abstract PageDataList getInviteUserList(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getUserList(int paramInt1,
			SearchParam paramSearchParam, int paramInt2);

	public abstract List<DetailUser> getUserList(SearchParam paramSearchParam);

	public abstract DetailUser getDetailUser(long paramLong, int paramInt);

	public abstract UserCacheModel applyVip(UserCache paramUserCache,
			AccountLog paramAccountLog);

	public abstract int userTrackCount(long paramLong);

	public abstract void updateUserLastInfo(User paramUser);

	public abstract User getUserByCardNO(String paramString);

	public abstract List<User> getAllUser(int paramInt);

	public abstract PageDataList getVipStatistic(int paramInt,
			SearchParam paramSearchParam);

	public abstract List<VIPStatisticModel> getVipStatistic(SearchParam paramSearchParam);

	public abstract int userCount();

	public abstract List<DetailUser> getInviteUserList(SearchParam paramSearchParam);

	public abstract int getLoginFailTimes(long paramLong);

	public abstract void cleanLoginFailTimes(long paramLong);

	public abstract void updateLoginFailTimes(long paramLong);

	public abstract void updateUserIsLock(long paramLong);
}