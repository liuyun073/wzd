package com.liuyun.site.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.liuyun.site.context.Constant;
import com.liuyun.site.dao.AttestationDao;
import com.liuyun.site.dao.LinkageDao;
import com.liuyun.site.freemarker.directive.AreaDirectiveModel;
import com.liuyun.site.freemarker.directive.AttestationDirectiveModel;
import com.liuyun.site.freemarker.directive.LinkageDirectiveModel;
import com.liuyun.site.freemarker.method.DateMethodModel;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.IncludePage;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： FreemarkerServlet   
 * 类描述： Freemarker servlet
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:05:15 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:05:15 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class FreemarkerServlet extends freemarker.ext.servlet.FreemarkerServlet {
	
	private static Logger logger = Logger.getLogger(FreemarkerServlet.class);
	private static final long serialVersionUID = -8234001005016304634L;
	private static final String ATTR_REQUEST_MODEL = ".freemarker.Request";
	private static final String ATTR_REQUEST_PARAMETERS_MODEL = ".freemarker.RequestParameters";
	private static final String ATTR_SESSION_MODEL = ".freemarker.Session";
	private static final String ATTR_APPLICATION_MODEL = ".freemarker.Application";
	private static final String ATTR_JSP_TAGLIBS_MODEL = ".freemarker.JspTaglibs";
	private ObjectWrapper wrapper = ObjectWrapper.DEFAULT_WRAPPER;
	private WebApplicationContext ctx;
	private LinkageDao linkageDao;
	private AttestationDao attestationDao;

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map data = new HashMap();
		String requestUri = request.getRequestURI();
		String contextUri = getServletConfig().getServletContext().getRealPath("/");
		contextUri = contextUri.replace("\\", "/");
		if (contextUri.endsWith("/")) {
			contextUri = contextUri.substring(0, contextUri.length() - 1);
		}
		String context = request.getContextPath();
		contextUri = contextUri.replaceFirst(context, "");
		String templatePath = requestUri.replaceFirst(context, "");
		String targetPath = contextUri + requestUri;
		if (!File.separator.equals("/")) {
			targetPath = targetPath.replace("/", "\\");
		}
		crateHTML(request, data, templatePath, response);
	}

	public void crateHTML(HttpServletRequest request, Map data,
			String templatePath, HttpServletResponse response) {
		Configuration freemarkerCfg = new Configuration();

		addDirectiveModel(freemarkerCfg);

		freemarkerCfg.setObjectWrapper(new DefaultObjectWrapper());

		freemarkerCfg.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		try {
			Template template = freemarkerCfg.getTemplate(templatePath, "UTF-8");
			template.setEncoding("UTF-8");

			response.setContentType("text/html;charset=UTF-8");
			Writer out = response.getWriter();

			Object o = request.getSession().getAttribute(Constant.SESSION_USER);
			logger.info("Session:" + o);

			ServletContext servletContext = getServletContext();
			TemplateModel model = createModel(this.wrapper, servletContext, request, response);

			template.process(model, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				request.setAttribute("exception", e.getMessage());
				request.setAttribute("exceptionStack", e);
				request.getRequestDispatcher("/404.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void init() throws ServletException {
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.linkageDao = ((LinkageDao) this.ctx.getBean("linkageDao"));
		this.attestationDao = ((AttestationDao) this.ctx.getBean("attestationDao"));
	}

	protected TemplateModel createModel(ObjectWrapper wrapper,
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response) throws TemplateModelException {
		try {
			AllHttpScopesHashModel params = new AllHttpScopesHashModel(wrapper, servletContext, request);

			ServletContextHashModel servletContextModel = (ServletContextHashModel) servletContext.getAttribute(".freemarker.Application");
			if (servletContextModel == null) {
				servletContextModel = new ServletContextHashModel(this, wrapper);
				servletContext.setAttribute(".freemarker.Application", servletContextModel);
				TaglibFactory taglibs = new TaglibFactory(servletContext);
				servletContext.setAttribute(".freemarker.JspTaglibs", taglibs);
				initializeServletContext(request, response);
			}

			params.putUnlistedModel("Application", servletContextModel);
			params.putUnlistedModel("__FreeMarkerServlet.Application__", servletContextModel);
			params.putUnlistedModel("JspTaglibs", (TemplateModel) servletContext.getAttribute(".freemarker.JspTaglibs"));

			HttpSession session = request.getSession(false);
			HttpSessionHashModel sessionModel;
			if (session != null) {
				sessionModel = (HttpSessionHashModel) session.getAttribute(".freemarker.Session");
				if (sessionModel == null) {
					sessionModel = new HttpSessionHashModel(session, wrapper);
					session.setAttribute(".freemarker.Session", sessionModel);
					initializeSession(request, response);
				}
			} else {
				sessionModel = new HttpSessionHashModel(this, request, response, wrapper);
			}
			params.putUnlistedModel("Session", sessionModel);

			HttpRequestHashModel requestModel = (HttpRequestHashModel) request.getAttribute(".freemarker.Request");
			if ((requestModel == null) || (requestModel.getRequest() != request)) {
				requestModel = new HttpRequestHashModel(request, response, wrapper);
				request.setAttribute(".freemarker.Request", requestModel);
				request.setAttribute(".freemarker.RequestParameters", createRequestParametersHashModel(request));
			}
			params.putUnlistedModel("Request", requestModel);
			params.putUnlistedModel("include_page", new IncludePage(request, response));
			params.putUnlistedModel("__FreeMarkerServlet.Request__", requestModel);

			HttpRequestParametersHashModel requestParametersModel = (HttpRequestParametersHashModel) request.getAttribute(".freemarker.RequestParameters");
			params.putUnlistedModel("RequestParameters", requestParametersModel);

			params.put("request", request);

			return params;
		} catch (ServletException e) {
			throw new TemplateModelException(e);
		} catch (IOException e) {
			throw new TemplateModelException(e);
		}
	}

	protected void setCustomMethodOrVariable(AllHttpScopesHashModel params) {
		params.put("dateformat", new DateMethodModel());
	}

	protected void addDirectiveModel(Configuration cfg) {
		cfg.setSharedVariable("linkage", new LinkageDirectiveModel(this.linkageDao));

		cfg.setSharedVariable("region", new AreaDirectiveModel(this.linkageDao));
		cfg.setSharedVariable("attestation", new AttestationDirectiveModel(this.attestationDao));
	}
}