package com.rd.util.cooperation;

import com.rd.domain.QqGetUserInfoParamBean;
import com.rd.domain.QqGetUserInfoResultBean;
import com.rd.util.json.JSONException;
import com.rd.util.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

public class OpenQqUtils {
	private static Logger log = Logger.getLogger(OpenQqUtils.class);

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(OpenQqUtils.class.getClassLoader().getResourceAsStream("cooperation-login.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("读取配置文件出错，请确认message/config.properties文件已经放到src目录下。");
		}
	}

	public String getConfigValue(String configKey) {
		String configValue = null;
		configValue = properties.getProperty(configKey);
		if ((configValue == null) || ("".equals(configValue))) {
			log.info("没有获取指定key的值，请确认资源文件中是否存在【" + configKey + "】");
		}
		return configValue;
	}

	public String doGet(String interfaceUrl) throws IOException {
		log.info("doGet:" + interfaceUrl);

		String interfaceData = "";

		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(interfaceUrl);

		HttpResponse response = httpclient.execute(httpGet);

		log.info("doGet请求状态Code:" + response.getStatusLine().getStatusCode());

		if (200 == response.getStatusLine().getStatusCode()) {
			HttpEntity resEntity = response.getEntity();

			BufferedReader input = new BufferedReader(new InputStreamReader(resEntity.getContent(), "UTF-8"));
			String tempStr = "";

			while ((tempStr = input.readLine()) != null) {
				interfaceData = interfaceData + tempStr.replace("\t", "");
			}
		}

		httpGet.abort();

		log.info("doGet返回的json数据：" + interfaceData);

		return interfaceData;
	}

	public String doPost(String url, MultipartEntity reqEntity)
			throws IOException {
		log.info("doPost:" + url);

		String interfaceData = "";

		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(reqEntity);

		HttpResponse response = httpclient.execute(httpPost);

		log.info("doPost请求状态Code:" + response.getStatusLine().getStatusCode());

		if (200 == response.getStatusLine().getStatusCode()) {
			HttpEntity resEntity = response.getEntity();

			BufferedReader input = new BufferedReader(new InputStreamReader(
					resEntity.getContent(), "UTF-8"));
			String tempStr = "";

			while ((tempStr = input.readLine()) != null) {
				interfaceData = interfaceData + tempStr.replace("\t", "");
			}
		}

		httpPost.abort();

		log.info("doPost返回的json数据：" + interfaceData);

		return interfaceData;
	}

	public boolean isNotNull(String pStr) {
		return (pStr != null) && (!"".equals(pStr));
	}

	public String timeStampToDate(String timeStamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		return df.format(Long.valueOf(timeStamp + "000"));
	}

	public String getAccessToken(String accessTokenUrl) throws IOException {
		String accessToken = "";

		String tempStr = "";

		tempStr = doGet(accessTokenUrl);

		if (tempStr.indexOf("access_token") >= 0) {
			accessToken = tempStr.split("&")[0].split("=")[1];
			log.info("access_token:" + accessToken);
			return accessToken;
		}
		log.info("access_token获取失败。返回值：" + tempStr);
		return "";
	}

	public String getOpenId(String accessToken) throws IOException {
		String openId = null;

		String interfaceData = doGet(getOpenIdUrl(accessToken));
		try {
			String jsonStr = interfaceData.substring(
					interfaceData.indexOf("{"), interfaceData.indexOf("}") + 1);

			JSONObject jsonObjRoot = new JSONObject(jsonStr);

			openId = jsonObjRoot.get("openid").toString();

			log.info("openid:" + openId);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("获取OpenId失败。返回数据：" + interfaceData);
		}

		return openId;
	}

	public String getAccessTokenUrl(String authorizationCode) {
		StringBuilder accessTokenUrl = new StringBuilder();

		accessTokenUrl.append("https://graph.qq.com/oauth2.0/token");

		accessTokenUrl.append("?grant_type=authorization_code");

		accessTokenUrl.append("&client_id=" + getConfigValue("qq.appid"));

		accessTokenUrl.append("&client_secret=" + getConfigValue("qq.appkey"));

		accessTokenUrl.append("&code=" + authorizationCode);

		accessTokenUrl.append("&state=javaSdk-token");

		accessTokenUrl.append("&redirect_uri=" + getConfigValue("qq.callback"));

		log.info("获取AccessToken的url：" + accessTokenUrl.toString());

		return accessTokenUrl.toString();
	}

	private String getOpenIdUrl(String accessToken) {
		StringBuilder openIdUrl = new StringBuilder();

		openIdUrl.append("https://graph.qq.com/oauth2.0/me");

		openIdUrl.append("?access_token=" + accessToken);

		log.info("获取OpenId的url：" + openIdUrl.toString());

		return openIdUrl.toString();
	}

	public String getQqLoginUrl() {
		StringBuilder qqLoginUrl = new StringBuilder();

		qqLoginUrl.append("https://graph.qq.com/oauth2.0/authorize");

		qqLoginUrl.append("?response_type=code");

		qqLoginUrl.append("&client_id=" + getConfigValue("qq.appid"));

		qqLoginUrl.append("&redirect_uri=" + getConfigValue("qq.callback"));

		qqLoginUrl.append(getConfigValue("kffw.callback"));

		qqLoginUrl.append("&scope=" + getConfigValue("qq.scope"));

		qqLoginUrl.append("&state=javaSdk-code");

		qqLoginUrl.append("&display=");

		return qqLoginUrl.toString();
	}

	private String getUserInfoUrl(QqGetUserInfoParamBean paramBean) {
		StringBuilder userInfoUrl = new StringBuilder();

		userInfoUrl.append("https://graph.qq.com/user/get_user_info");

		userInfoUrl.append("?oauth_consumer_key=" + getConfigValue("qq.appid"));

		userInfoUrl.append("&access_token=" + paramBean.getAccessToken());

		userInfoUrl.append("&openid=" + paramBean.getOpenId());

		return userInfoUrl.toString();
	}

	public QqGetUserInfoResultBean getUserInfo(QqGetUserInfoParamBean paramBean)
			throws IOException {
		OpenQqUtils oqu = new OpenQqUtils();

		String interfaceData = oqu.doGet(getUserInfoUrl(paramBean));
		log.error(interfaceData);
		return jsonToBean(interfaceData);
	}

	private QqGetUserInfoResultBean jsonToBean(String jsonData) {
		QqGetUserInfoResultBean resultBean = new QqGetUserInfoResultBean();
		try {
			JSONObject jsonObjRoot = new JSONObject(jsonData);

			if (jsonObjRoot.getInt("ret") != 0) {
				resultBean.setErrorFlg(true);

				resultBean.setErrorCode(jsonObjRoot.get("ret").toString());

				resultBean.setErrorMes(jsonObjRoot.getString("msg"));

				log.error("获取用户信息出错。错误编号：" + jsonObjRoot.get("ret").toString());
			} else {
				resultBean.setNickName(jsonObjRoot.getString("nickname"));

				resultBean.setFigureUrl(jsonObjRoot.getString("figureurl"));

				resultBean.setFigureUrl1(jsonObjRoot.getString("figureurl_1"));

				resultBean.setFigureUrl2(jsonObjRoot.getString("figureurl_2"));

				resultBean.setGender(jsonObjRoot.getString("gender"));

				resultBean.setIsVip(jsonObjRoot.getString("vip"));

				resultBean.setLevel(jsonObjRoot.getString("level"));
			}
		} catch (JSONException e) {
			e.printStackTrace();

			log.error("获取用户信息出错。接口返回数据：" + jsonData);
		}

		return resultBean;
	}
}