package com.rd.freemarker.directive;

import com.rd.dao.ArticleDao;
import com.rd.domain.Site;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SiteDirectiveModel implements TemplateDirectiveModel {
	private static Logger logger = Logger.getLogger(SiteDirectiveModel.class);
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String CLASS = "class";
	private static final String PID = "pid";
	private static final String DEFUALT = "default";
	private ArticleDao dao;

	public SiteDirectiveModel(ArticleDao dao) {
		this.dao = dao;
	}

	public SiteDirectiveModel() {
	}

	public void execute(Environment env, Map map, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Iterator it = map.entrySet().iterator();
		String name = "";
		String id = "";
		String clazz = "";
		String value = "";
		String pid = "";
		String defaultValue = "";
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String paramName = entry.getKey().toString();
			TemplateModel paramValue = (TemplateModel) entry.getValue();
			logger.debug("name:" + paramName);
			logger.debug("r:" + paramValue);
			if (paramName.equals("name"))
				name = paramValue.toString();
			else if (paramName.equals("id"))
				id = paramValue.toString();
			else if (paramName.equals("class"))
				clazz = paramValue.toString();
			else if (paramName.equals("pid"))
				pid = paramValue.toString();
			else if (paramName.equals("default")) {
				defaultValue = paramValue.toString();
			}
		}
		String result = html(name, defaultValue);
		Writer out = env.getOut();
		out.write(result);
	}

	private String html(String name, String value) {
		List<Site> list = new ArrayList<Site>();

		list = this.dao.getSiteList();
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(name);
		sb.append("\">");

		sb.append("<option value=\"0\">请选择</option>");
		for (Site s : list) {
			sb.append(option(s.getSite_id(), s.getName(), value));
			List<Site> subList = this.dao.getSubSiteList(s.getSite_id());
			for (Site ss : subList) {
				sb.append(option(ss.getSite_id(), "--" + ss.getName(), value));
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	private String option(long id, String name, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=\"").append(id).append("\"");
		if (value.equals(id)) {
			sb.append(" selected=\"selected\" ");
		}
		sb.append(">").append(name).append("</option>");
		return sb.toString();
	}
}