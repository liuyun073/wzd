package com.liuyun.site.freemarker.directive;

import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.domain.Areainfo;
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

public class AreaDirectiveModel implements TemplateDirectiveModel {
	private static Logger logger = Logger.getLogger(AreaDirectiveModel.class);
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String CLASS = "class";
	private static final String PID = "pid";
	private static final String VALUE = "value";
	private LinkageDao dao;

	public AreaDirectiveModel(LinkageDao dao) {
		this.dao = dao;
	}

	public AreaDirectiveModel() {
	}

	public void execute(Environment env, Map map, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Iterator it = map.entrySet().iterator();
		String name = "";
		String id = "";
		String clazz = "";
		String value = "";
		String pid = "";
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
			else if (paramName.equals("value")) {
				value = paramValue.toString();
			}
		}
		String result = html(name, id, clazz, pid, value);
		Writer out = env.getOut();
		out.write(result);
	}

	private String html(String name, String id, String clazz, String pid,
			String value) {
		List<Areainfo> list = new ArrayList<Areainfo>();
		if (pid.equals("")) {
			pid = "-1";
			Areainfo a = new Areainfo();
			a.setId(-1);
			a.setName("请选择");
			list.add(a);
		} else {
			list = this.dao.getAreainfoByPid(pid);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(name);
		if (!id.equals("")) {
			sb.append("\" id=\"").append(id);
		}
		if (!clazz.equals("")) {
			sb.append("\" class=\"").append(clazz);
		}
		sb.append("\">");

		sb.append("<option value=\"0\">请选择</option>");
		for (Areainfo l : list) {
			sb.append("<option value=\"").append(l.getId()).append("\"");
			if (value.equals(l.getId())) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(">").append(l.getName()).append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
}