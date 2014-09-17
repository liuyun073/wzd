package com.rd.service;

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
import java.util.List;

public abstract interface UserinfoService {
	public abstract Userinfo getUserinfoByUserid(long paramLong);

	public abstract void updateUserinfo(UserinfoModel paramUserinfoModel);

	public abstract List<Areainfo> getAreainfoByPid(String paramString);

	public abstract void updateBuilding(UserinfoModel paramUserinfoModel);

	public abstract void updateCompany(UserinfoModel paramUserinfoModel);

	public abstract void updateFirm(UserinfoModel paramUserinfoModel);

	public abstract void updateFinance(UserinfoModel paramUserinfoModel);

	public abstract void updateContact(UserinfoModel paramUserinfoModel);

	public abstract void updateMate(UserinfoModel paramUserinfoModel);

	public abstract void updateEducation(UserinfoModel paramUserinfoModel);

	public abstract void updateOtherinfo(UserinfoModel paramUserinfoModel);

	public abstract List<Attestation> getAttestationListByUserid(long paramLong);

	public abstract Attestation addAttestation(Attestation paramAttestation);

	public abstract List<Attestation> getAttestationListByUserid(
			long paramLong, int paramInt);

	public abstract PageDataList getUserInfoListByPageNumber(int paramInt,
			SearchParam paramSearchParam);

	public abstract UserinfoModel getUserALLInfoModelByUserid(long paramLong);

	public abstract AttestationModel getUserAllCertifyByUserId(long paramLong);

	public abstract PageDataList getUserVipinfo(int paramInt1, int paramInt2,
			int paramInt3, SearchParam paramSearchParam);

	public abstract PageDataList getSearchUserInfo(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getSearchUserCertify(int paramInt,
			SearchParam paramSearchParam);

	public abstract PageDataList getSearchUserInfo(
			SearchParam paramSearchParam, int paramInt);

	public abstract PageDataList getUserCreditList(int paramInt,
			SearchParam paramSearchParam);

	public abstract User getUserinfoByUsername(String paramString);

	public abstract void updateUserCredit(UserCredit paramUserCredit);

	public abstract UserCredit getUserCreditByUserId(long paramLong);

	public abstract void updateUserCache(UserCache paramUserCache);

	public abstract void VerifyVipSuccess(UserCache paramUserCache);

	public abstract void VerifyVipSuccess(UserCache paramUserCache,
			AccountLog paramAccountLog);

	public abstract void VerifyVipFail(UserCache paramUserCache);

	public abstract void VerifyVipFail(UserCache paramUserCache,
			AccountLog paramAccountLog);

	public abstract AttestationType getAttestationTypeByTypeId(int paramInt);

	public abstract Attestation getAttestationById(String paramString);

	public abstract Attestation modifyUserCertifyStatus(
			Attestation paramAttestation);

	public abstract void updateAdmininfo(AdminInfoModel paramAdminInfoModel);

	public abstract PageDataList getCreditLogList(int paramInt,
			SearchParam paramSearchParam);

	public abstract void addUserCreditLog(UserCreditLog paramUserCreditLog);

	public abstract UserCreditLogModel getCreditLogByUserId(long paramLong);

	public abstract UserCreditModel getCreditModelById(long paramLong);

	public abstract AdminInfoModel getAllAdminInfoModelByUserid(long paramLong);

	public abstract void updateCreditTenderValue(UserCredit paramUserCredit);
}