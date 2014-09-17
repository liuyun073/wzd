package com.rd.dao;

import com.rd.domain.User;
import com.rd.domain.UserType;
import com.rd.model.DetailUser;
import com.rd.model.SearchParam;
import java.util.List;

public abstract interface UserDao {
	public abstract void addUser(User paramUser);

	public abstract User getUserByUsername(String paramString);

	public abstract User getUserById(long paramLong);

	public abstract User getUserByEmail(String paramString);

	public abstract User getUserByUsernameAndPassword(String paramString1,
			String paramString2);

	public abstract User getUserByUserid(long paramLong);

	public abstract DetailUser getDetailUserByUserid(long paramLong);

	public abstract void updateUser(User paramUser);

	public abstract User realnameIdentify(User paramUser);

	public abstract User modifyEmail(User paramUser);

	public abstract User modifyEmail_status(User paramUser);

	public abstract User modifyPhone(User paramUser);

	public abstract User modifyPhone_status(User paramUser);

	public abstract User modifyVideo_status(User paramUser);

	public abstract User modifyScene_status(User paramUser);

	public abstract User modifyRealname(User paramUser);

	public abstract User modifyReal_status(User paramUser);

	public abstract User modifyUserPwd(User paramUser);

	public abstract User modifyAnswer(User paramUser);

	public abstract User modifyPayPwd(User paramUser);

	public abstract List<User> getAllKfList();

	public abstract List<User> getKfListWithLimit(int paramInt1, int paramInt2);

	public abstract List<User> getAllVerifyUser();

	public abstract List<DetailUser> getInvitedUserByUserid(long paramLong);

	public abstract List<DetailUser> getUserList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<DetailUser> getUserList(SearchParam paramSearchParam);

	public abstract int getUserCount(SearchParam paramSearchParam);

	public abstract List<UserType> getAllUserType();

	public abstract void updateUser(User paramUser, boolean paramBoolean);

	public abstract List<DetailUser> getSearchUserOrRealName(int paramInt,
			SearchParam paramSearchParam);

	public abstract int getSearchUserOrRealName(SearchParam paramSearchParam);

	public abstract String getUserTypeByid(String paramString);

	public abstract UserType getUserTypeById(long paramLong);

	public abstract void updateUserType(List<UserType> paramList);

	public abstract void addUserType(UserType paramUserType);

	public abstract void deleteUserTypeById(long paramLong);

	public abstract boolean isRoleHasPurview(long paramLong);

	public abstract int getInviteUserCount(SearchParam paramSearchParam);

	public abstract List<DetailUser> getInviteUserList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getUserCount(SearchParam paramSearchParam, int paramInt);

	public abstract List<DetailUser> getUserList(int paramInt1, int paramInt2,
			SearchParam paramSearchParam, int paramInt3);

	public abstract DetailUser getDetailUser(long paramLong, int paramInt);

	public abstract void updateUserLastInfo(User paramUser);

	public abstract User getUserByCard(String paramString);

	public abstract List<User> getAllUser(int paramInt);

	public abstract int getUserCount();

	public abstract List<DetailUser> getInviteUserList(SearchParam paramSearchParam);

	public abstract void updateUserIsLock(long paramLong);

	public abstract List<User> getVipUser();
}