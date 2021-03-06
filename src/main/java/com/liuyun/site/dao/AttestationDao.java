package com.liuyun.site.dao;

import com.liuyun.site.domain.Attestation;
import com.liuyun.site.domain.AttestationType;
import java.util.List;

public abstract interface AttestationDao {
	public abstract List<AttestationType> getAllList();

	public abstract List<Attestation> getListByUserid(long paramLong);

	public abstract Attestation addAttestation(Attestation paramAttestation);

	public abstract List<Attestation> getListByUserid(long paramLong,
			int paramInt);

	public abstract List<Attestation> getListByUserName(String paramString,
			int paramInt);

	public abstract void addAttestationType(AttestationType paramAttestationType);

	public abstract void deleteAttestationType(long paramLong);

	public abstract void updateAttestationTypeByList(
			List<AttestationType> paramList);
}