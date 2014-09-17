package com.rd.service;

import com.rd.domain.AttestationType;
import java.util.List;

public abstract interface AttestationService {
	public abstract void updateAttestationTypeByList(
			List<AttestationType> paramList);

	public abstract void addAttestationType(AttestationType paramAttestationType);

	public abstract void deleteAttestationTypeByid(long paramLong);

	public abstract List<AttestationType> getAttestationType();
}