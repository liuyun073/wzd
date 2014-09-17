package com.rd.service.impl;

import com.rd.dao.AttestationDao;
import com.rd.domain.AttestationType;
import com.rd.service.AttestationService;
import java.util.List;

public class AttestationServiceImp implements AttestationService {
	private AttestationDao attestationDao;

	public AttestationDao getAttestationDao() {
		return this.attestationDao;
	}

	public void setAttestationDao(AttestationDao attestationDao) {
		this.attestationDao = attestationDao;
	}

	public void addAttestationType(AttestationType attestationType) {
		this.attestationDao.addAttestationType(attestationType);
	}

	public void deleteAttestationTypeByid(long id) {
		this.attestationDao.deleteAttestationType(id);
	}

	public List<AttestationType> getAttestationType() {
		return this.attestationDao.getAllList();
	}

	public void updateAttestationTypeByList(List<AttestationType> list) {
		this.attestationDao.updateAttestationTypeByList(list);
	}
}