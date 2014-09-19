package com.liuyun.site.util.cooperation;

import com.liuyun.site.util.json.JSONException;
import com.liuyun.site.util.json.JSONObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class QqCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 2673913230227418920L;
	private Logger log = Logger.getLogger(QqCallbackServlet.class);

	private OpenQqUtils oqu = new OpenQqUtils();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accessTokenUrl = getAccessTokenUrl(req.getParameter("code"));

		String accessToken = getAccessToken(accessTokenUrl);

		String openId = getOpenId(accessToken);

		HttpSession session = req.getSession();

		session.setAttribute("accessToken", accessToken);

		session.setAttribute("openId", openId);

		resp.sendRedirect(this.oqu.getConfigValue("kffw.callback"));
	}

	private String getAccessToken(String accessTokenUrl) throws IOException {
		String accessToken = "";

		OpenQqUtils oqu = new OpenQqUtils();

		String tempStr = "";

		tempStr = oqu.doGet(accessTokenUrl);

		if (tempStr.indexOf("access_token") >= 0) {
			accessToken = tempStr.split("&")[0].split("=")[1];
			this.log.info("access_token:" + accessToken);
			return accessToken;
		}
		this.log.info("access_token获取失败。返回值：" + tempStr);
		return "";
	}

	private String getOpenId(String accessToken) throws IOException {
		String openId = null;

		OpenQqUtils oqu = new OpenQqUtils();

		String interfaceData = oqu.doGet(getOpenIdUrl(accessToken));
		try {
			String jsonStr = interfaceData.substring(
					interfaceData.indexOf("{"), interfaceData.indexOf("}") + 1);

			JSONObject jsonObjRoot = new JSONObject(jsonStr);

			openId = jsonObjRoot.get("openid").toString();

			this.log.info("openid:" + openId);
		} catch (JSONException e) {
			e.printStackTrace();
			this.log.error("获取OpenId失败。返回数据：" + interfaceData);
		}

		return openId;
	}

	private String getAccessTokenUrl(String authorizationCode) {
		StringBuilder accessTokenUrl = new StringBuilder();

		OpenQqUtils oqu = new OpenQqUtils();

		accessTokenUrl.append("https://graph.qq.com/oauth2.0/token");

		accessTokenUrl.append("?grant_type=authorization_code");

		accessTokenUrl.append("&client_id=" + oqu.getConfigValue("qq.appid"));

		accessTokenUrl.append("&client_secret="
				+ oqu.getConfigValue("qq.appkey"));

		accessTokenUrl.append("&code=" + authorizationCode);

		accessTokenUrl.append("&state=javaSdk-token");

		accessTokenUrl.append("&redirect_uri="
				+ oqu.getConfigValue("qq.callback"));

		this.log.info("获取AccessToken的url：" + accessTokenUrl.toString());

		return accessTokenUrl.toString();
	}

	private String getOpenIdUrl(String accessToken) {
		StringBuilder openIdUrl = new StringBuilder();

		openIdUrl.append("https://graph.qq.com/oauth2.0/me");

		openIdUrl.append("?access_token=" + accessToken);

		this.log.info("获取OpenId的url：" + openIdUrl.toString());

		return openIdUrl.toString();
	}
}