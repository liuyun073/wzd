package com.liuyun.site.freemarker.method;

import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.dao.UserDao;
import com.liuyun.site.util.NumberUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.util.List;

public class AttestationTypeNameModel implements TemplateMethodModel {
	private LinkageDao linkageDao;
	private UserDao userDao;

	public AttestationTypeNameModel(LinkageDao linkageDao, UserDao userdao) {
		this.linkageDao = linkageDao;
		this.userDao = userdao;
	}

	@SuppressWarnings("unchecked")
	public Object exec(List arg0) throws TemplateModelException {
		if (arg0.size() > 1) {
			if ((arg0.get(0).equals("list")) && (arg0.get(1).equals("usertype"))) {
				return this.userDao.getAllUserType();
			}

			if (arg0.get(1).equals("name")) {
				return this.linkageDao.getLinkageNameByid(NumberUtils.getLong(arg0.get(0).toString()));
			}
			if (arg0.get(1).equals("area")) {
				return this.linkageDao.getAreaByPid(arg0.get(0).toString());
			}
			if (arg0.get(1).equals("usertype")) {
				return this.userDao.getUserTypeByid(arg0.get(0).toString());
			}
		}
		return "-";
	}
}