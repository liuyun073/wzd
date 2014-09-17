package com.rd.dao;

import com.rd.domain.Attestation;
import com.rd.domain.AttestationType;
import com.rd.domain.User;
import com.rd.domain.Userinfo;
import com.rd.model.AdminInfoModel;
import com.rd.model.AttestationModel;
import com.rd.model.DetailUser;
import com.rd.model.SearchParam;
import com.rd.model.UserinfoModel;
import java.util.List;

public abstract interface UserinfoDao {
	public abstract Userinfo getUserinfoByUserid(long paramLong);

	public abstract void updateUserinfo(Userinfo paramUserinfo);

	public abstract void addUserinfo(Userinfo paramUserinfo);

	public abstract void updateBuilding(Userinfo paramUserinfo);

	public abstract void addBuilding(Userinfo paramUserinfo);

	public abstract void updateCompany(Userinfo paramUserinfo);

	public abstract void addCompany(Userinfo paramUserinfo);

	public abstract void updateFirm(Userinfo paramUserinfo);

	public abstract void addFirm(Userinfo paramUserinfo);

	public abstract void updateFinance(Userinfo paramUserinfo);

	public abstract void addFinance(Userinfo paramUserinfo);

	public abstract void updateContact(Userinfo paramUserinfo);

	public abstract void addContact(Userinfo paramUserinfo);

	public abstract void updateMate(Userinfo paramUserinfo);

	public abstract void addMate(Userinfo paramUserinfo);

	public abstract void updateEducation(Userinfo paramUserinfo);

	public abstract void addEducation(Userinfo paramUserinfo);

	public abstract void updateOtherinfo(Userinfo paramUserinfo);

	public abstract void addOtherinfo(Userinfo paramUserinfo);

	public abstract List<UserinfoModel> getUserInfoBypageNumber(int paramInt1, int paramInt2);

	public abstract UserinfoModel getUserAllinfoByUserId(long paramLong);

	public abstract int getUserInfocount();

	public abstract int getUserInfocount(SearchParam paramSearchParam);

	public abstract List<UserinfoModel> getUserInfoBySearch(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract List<DetailUser> getSearchUserInfo(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);

	public abstract int getSearchUserInfo(SearchParam paramSearchParam);

	public abstract User getUserinfoByUsername(String paramString);

	public abstract AdminInfoModel getAdminAllinfoByUserId(long paramLong);

	public abstract Attestation modifyUserCertifyStatus(
			Attestation paramAttestation);

	public abstract AttestationType getAttestationTypeByTypeId(int paramInt);

	public abstract Attestation getAttestationById(String paramString);

	public abstract AttestationModel getUserAllCertifyByUserId(long paramLong);

	public abstract int getSearchUserCertify(SearchParam paramSearchParam);

	public abstract List<AttestationModel> getSearchUserCertify(int paramInt1, int paramInt2,
			SearchParam paramSearchParam);
}