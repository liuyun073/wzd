package com.rd.freemarker.directive;

import com.rd.dao.AttestationDao;
import com.rd.domain.AttestationType;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class AttestationDirectiveModel implements TemplateDirectiveModel {
	private static Logger logger = Logger
			.getLogger(AttestationDirectiveModel.class);
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String CLASS = "class";
	private static final String DEFAULT = "default";
	private AttestationDao dao;

	public AttestationDirectiveModel(AttestationDao dao) {
		this.dao = dao;
	}

	public AttestationDirectiveModel() {
	}

	public void execute(Environment env, Map map, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Iterator it = map.entrySet().iterator();
		String name = "";
		String id = "";
		String clazz = "";
		String defaultvalue = "";
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String paramName = entry.getKey().toString();
			TemplateModel paramValue = (TemplateModel) entry.getValue();
			logger.debug("name:" + paramName);
			logger.debug("r:" + paramValue.toString());
			if (paramName.equals("name"))
				name = paramValue.toString();
			else if (paramName.equals("id"))
				id = paramValue.toString();
			else if (paramName.equals("class"))
				clazz = paramValue.toString();
			else if (paramName.equals("default")) {
				defaultvalue = paramValue.toString();
			}
		}
		String result = html(name, id, clazz, defaultvalue);
		Writer out = env.getOut();
		out.write(result);
	}

	private String html(String name, String id, String clazz,
			String defaultvalue) {
		List<AttestationType> list = null;
		list = this.dao.getAllList();

		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(name).append(
				"\" autocomplete=\"off\"");
		if (!id.equals("")) {
			sb.append(" id=\"").append(id).append("\"");
		}
		if (!clazz.equals("")) {
			sb.append(" class=\"").append(clazz).append("\"");
		}
		sb.append(">");
		for (AttestationType l : list) {
			String value = "" + l.getType_id();
			sb.append("<option value=\"").append(value).append("\"");
			sb.append(">").append(l.getName()).append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
}