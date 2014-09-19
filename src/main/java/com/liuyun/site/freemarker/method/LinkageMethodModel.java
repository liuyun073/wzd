package com.liuyun.site.freemarker.method;

import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.domain.Linkage;
import com.liuyun.site.util.NumberUtils;
import com.liuyun.site.util.StringUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import java.util.List;

public class LinkageMethodModel implements TemplateMethodModel {
	private LinkageDao linkageDao;

	public LinkageMethodModel(LinkageDao linkageDao) {
		this.linkageDao = linkageDao;
	}

	public Object exec(List arg) throws TemplateModelException {
		String nid = "";
		String type = "";
		String value = "";
		if (arg.size() == 3) {
			nid = StringUtils.isNull(arg.get(0));
			type = StringUtils.isNull(arg.get(1));
			value = StringUtils.isNull(arg.get(2));
		} else {
			return "Illegal arguments";
		}
		Linkage l = null;
		if (type.equals("id")) {
			long id = NumberUtils.getLong(value);
			l = this.linkageDao.getLinkageById(id);
		} else {
			l = this.linkageDao.getLinkageByValue(nid, value);
		}
		return l;
	}
}