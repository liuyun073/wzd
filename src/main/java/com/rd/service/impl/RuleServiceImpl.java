package com.rd.service.impl;

import com.rd.dao.RuleDao;
import com.rd.dao.RuleKeyDao;
import com.rd.domain.Rule;
import com.rd.service.RuleService;
import java.util.List;
import org.apache.log4j.Logger;

public class RuleServiceImpl implements RuleService {
	private Logger logger = Logger.getLogger(RuleServiceImpl.class);
	private RuleDao ruleDao;
	private RuleKeyDao ruleKeyDao;

	public RuleDao getRuleDao() {
		return this.ruleDao;
	}

	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}

	public RuleKeyDao getRuleKeyDao() {
		return this.ruleKeyDao;
	}

	public void setRuleKeyDao(RuleKeyDao ruleKeyDao) {
		this.ruleKeyDao = ruleKeyDao;
	}

	public Rule getRuleById(Long id) {
		return this.ruleDao.getRuleById(id);
	}

	public Rule getRuleByNid(String nid) {
		return this.ruleDao.getRuleByNid(nid);
	}

	public List<Rule> getRuleList(int status) {
		return this.ruleDao.getRuleList(status);
	}
}