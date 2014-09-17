package com.rd.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rd.dao.LinkageDao;
import com.rd.domain.Linkage;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

public class LinkageDirectiveModel implements TemplateDirectiveModel {
	private static Logger logger = Logger.getLogger(LinkageDirectiveModel.class);
	
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String CLASS = "class";
	private static final String TYPEID = "typeid";
	private static final String DEFAULT = "default";
	private static final String NID = "nid";
	private static final String DISABLED = "disabled";
	private static final String TYPE = "type";
	private static final String NOSELECT = "noselect";
	private static final String PLAINTEXT = "plantext";
	private LinkageDao dao;

	public LinkageDirectiveModel(LinkageDao dao) {
		this.dao = dao;
	}

	public LinkageDirectiveModel() {
	}

	public void execute(Environment env, Map map, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		Iterator<?> it = map.entrySet().iterator();
		String name = "";
		String id = "";
		String clazz = "";
		String defaultvalue = "";
		String nid = "";
		String disabled = "";
		String type = "";
		String noselect = "";
		int typeid = 0;
		boolean plantext = false;
		while (it.hasNext()) {
			Map.Entry<?,?> entry = (Map.Entry) it.next();
			String paramName = entry.getKey().toString();
			TemplateModel paramValue = (TemplateModel) entry.getValue();
			if (paramName.equals("plantext")) {
				if (!(paramValue instanceof TemplateBooleanModel)) {
					throw new TemplateModelException("The \"" + plantext + "\" parameter " + "must be a boolean.");
				}
				plantext = ((TemplateBooleanModel) paramValue).getAsBoolean();
			}
			if (paramName.equals("name")) {
				name = paramValue.toString();
			} else if (paramName.equals("id")) {
				id = paramValue.toString();
			} else if (paramName.equals("class")) {
				clazz = paramValue.toString();
			} else if (paramName.equals("default")) {
				defaultvalue = paramValue.toString();
			} else if (paramName.equals("nid")) {
				nid = paramValue.toString();
			} else if (paramName.equals("disabled")) {
				disabled = paramValue.toString();
			} else if (paramName.equals("noselect")) {
				noselect = paramValue.toString();
			} else if (paramName.equals("type")) {
				type = paramValue.toString();
			} else if (paramName.equals("typeid")) {
				if (!(paramValue instanceof TemplateNumberModel)) {
					throw new TemplateModelException("The \"typeid\" parameter must be a number.");
				}

				typeid = ((TemplateNumberModel) paramValue).getAsNumber().intValue();
			}
		}
		String result = "";
		if (plantext)
			result = plaintext(name, id, clazz, defaultvalue, typeid, nid, disabled, type, noselect);
		else {
			result = html(name, id, clazz, defaultvalue, typeid, nid, disabled, type, noselect);
		}
		Writer out = env.getOut();
		out.write(result);
	}

	private String html(String name, String id, String clazz,
			String defaultvalue, int typeid, String nid, String disabled,
			String type, String noselect) {
		
		List<Linkage> list = null;
		type = StringUtils.isNull(type);
		if (!nid.equals(""))
			list = this.dao.getLinkageByNid(nid, type);
		else {
			list = this.dao.getLinkageByTypeid(typeid, type);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\"").append(name).append(
				"\" autocomplete=\"off\"");
		if (!id.equals("")) {
			sb.append(" id=\"").append(id).append("\"");
		}
		if (!clazz.equals("")) {
			sb.append(" class=\"").append(clazz).append("\"");
		}
		if (!disabled.equals("")) {
			sb.append(" disabled=\"").append(disabled).append("\"");
		}
		sb.append(">");

		if (!noselect.equals("")) {
			sb.append("<option value=\"0\">" + noselect + "</option>");
		}
		for (Linkage l : list) {
			String value = Integer.toString(l.getId());
			if ("value".equals(type)) {
				value = l.getValue();
			}
			sb.append("<option value=\"").append(value).append("\"");
			if ((!defaultvalue.equals("")) && (defaultvalue.equals(value))) {
				sb.append(" selected=\"selected\" ");
			}
			sb.append(">").append(l.getName()).append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}

	private String plaintext(String name, String id, String clazz,
			String defaultvalue, int typeid, String nid, String disabled,
			String type, String noselect) {
		Linkage l = null;
		if (!nid.equals(""))
			l = this.dao.getLinkageByValue(nid, defaultvalue);
		else {
			l = this.dao.getLinkageById(NumberUtils.getLong(defaultvalue));
		}
		if (l == null) {
			return "";
		}
		String ret = "";
		if (type.equals("value"))
			ret = l.getValue();
		else {
			ret = l.getName();
		}
		return ret;
	}
}