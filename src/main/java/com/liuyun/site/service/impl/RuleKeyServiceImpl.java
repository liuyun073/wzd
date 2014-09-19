package com.liuyun.site.service.impl;

import com.liuyun.site.dao.RuleKeyDao;
import com.liuyun.site.domain.RuleKey;
import com.liuyun.site.service.RuleKeyService;
import org.apache.log4j.Logger;

public class RuleKeyServiceImpl implements RuleKeyService {
	private Logger logger = Logger.getLogger(RuleKeyServiceImpl.class);
	private RuleKeyDao ruleKeyDao;

	public RuleKeyDao getRuleKeyDao() {
		return this.ruleKeyDao;
	}

	public void setRuleKeyDao(RuleKeyDao ruleKeyDao) {
		this.ruleKeyDao = ruleKeyDao;
	}

	public RuleKey getRuleKeyById(Long id) {
		return this.ruleKeyDao.getRuleKeyById(id);
	}
}