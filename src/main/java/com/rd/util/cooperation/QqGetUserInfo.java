package com.rd.util.cooperation;

import com.rd.domain.QqGetUserInfoParamBean;
import com.rd.domain.QqGetUserInfoResultBean;
import com.rd.util.json.JSONException;
import com.rd.util.json.JSONObject;
import java.io.IOException;
import org.apache.log4j.Logger;

public class QqGetUserInfo {
	private Logger log = Logger.getLogger(QqGetUserInfo.class);

	private OpenQqUtils oqu = new OpenQqUtils();

	public QqGetUserInfoResultBean getUserInfo(QqGetUserInfoParamBean paramBean)
			throws IOException {
		OpenQqUtils oqu = new OpenQqUtils();

		String interfaceData = oqu.doGet(getUserInfoUrl(paramBean));

		return jsonToBean(interfaceData);
	}

	private String getUserInfoUrl(QqGetUserInfoParamBean paramBean) {
		StringBuilder userInfoUrl = new StringBuilder();

		userInfoUrl.append("https://graph.qq.com/user/get_user_info");

		userInfoUrl.append("?oauth_consumer_key="
				+ this.oqu.getConfigValue("qq.appid"));

		userInfoUrl.append("&access_token=" + paramBean.getAccessToken());

		userInfoUrl.append("&openid=" + paramBean.getOpenId());

		return userInfoUrl.toString();
	}

	private QqGetUserInfoResultBean jsonToBean(String jsonData) {
		QqGetUserInfoResultBean resultBean = new QqGetUserInfoResultBean();
		try {
			JSONObject jsonObjRoot = new JSONObject(jsonData);

			if (jsonObjRoot.getInt("ret") != 0) {
				resultBean.setErrorFlg(true);

				resultBean.setErrorCode(jsonObjRoot.get("ret").toString());

				resultBean.setErrorMes(jsonObjRoot.getString("msg"));

				this.log.error("获取用户信息出错。错误编号："
						+ jsonObjRoot.get("ret").toString());
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

			this.log.error("获取用户信息出错。接口返回数据：" + jsonData);
		}

		return resultBean;
	}
}