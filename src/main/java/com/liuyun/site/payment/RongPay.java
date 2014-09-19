package com.liuyun.site.payment;

import com.liuyun.site.context.Global;
import com.liuyun.site.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class RongPay {
	private static final Logger logger = Logger.getLogger(RongPay.class);

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private String merchant_ID;
	private String key;
	private String seller_email;
	private String notify_url;
	private String return_url;
	private String mainname;
	private String service;
	private String payment_type;
	private String rongpay_url;
	private String verify_url = "http://interface.reapal.com/verify/notify?";
	private String charset;
	private String sign_type;
	private String transport;
	private String title;
	private String body;
	private String total_fee;
	private String buyer_email = "";

	private String paymethod = "";
	private String defaultbank;
	private String sign;
	private String order_no;

	public void init(RongPay rp) {
		rp.setMerchant_ID(Global.getValue("merchant_ID"));
		rp.setKey(Global.getValue("key"));
		rp.setTransport(Global.getValue("transport"));
		rp.setNotify_url(Global.getValue("weburl") + Global.getValue("notify_url"));
		rp.setReturn_url(Global.getValue("weburl") + Global.getValue("return_url"));
		rp.setSeller_email(Global.getValue("seller_email"));
		rp.setMainname(Global.getValue("webname"));
		rp.setService("online_pay");
		rp.setPayment_type("1");
		rp.setRongpay_url("http://epay.reapal.com/portal");
		rp.setVerify_url("http://interface.reapal.com/verify/notify?");
		rp.setCharset(Global.getValue("charset"));
		rp.setSign_type("MD5");
		rp.setTitle(Global.getValue("webname"));
		rp.setBody(Global.getValue("body"));
		rp.setDefaultbank("");
	}

	public String BuildForm(RongPay rp) {
		if ("No".equals(rp.getDefaultbank())) {
			this.paymethod = "bankPay";
			this.defaultbank = "";
			rp.setDefaultbank(this.defaultbank);
		} else {
			this.paymethod = "directPay";
		}
		rp.setPaymethod(this.paymethod);
		String sign = getSign(rp);
		StringBuffer url = new StringBuffer();
		url.append(this.rongpay_url).append("?");
		url.append("service=").append(rp.getService()).append("&");
		url.append("payment_type=").append(rp.getPayment_type()).append("&");
		url.append("merchant_ID=").append(rp.getMerchant_ID()).append("&");
		url.append("seller_email=").append(rp.getSeller_email()).append("&");
		url.append("return_url=").append(rp.getReturn_url()).append("&");
		url.append("notify_url=").append(rp.getNotify_url()).append("&");
		url.append("charset=").append(rp.getCharset()).append("&");
		url.append("order_no=").append(rp.getOrder_no()).append("&");
		url.append("title=").append(rp.getTitle()).append("&");
		url.append("body=").append(rp.getBody()).append("&");
		url.append("total_fee=").append(rp.getTotal_fee()).append("&");
		url.append("buyer_email=").append(rp.getBuyer_email()).append("&");
		url.append("paymethod=").append(rp.getPaymethod()).append("&");
		url.append("defaultbank=").append(rp.getDefaultbank()).append("&");
		url.append("sign=").append(sign).append("&");
		url.append("sign_type=").append(getSign_type()).append("&");
		logger.info(url.toString());
		return url.toString();
	}

	public String toString() {
		return "RongPay [merchant_ID=" + this.merchant_ID + ", key=" + this.key
				+ ", seller_email=" + this.seller_email + ", notify_url="
				+ this.notify_url + ", return_url=" + this.return_url
				+ ", mainname=" + this.mainname + ", service=" + this.service
				+ ", payment_type=" + this.payment_type + ", rongpay_url="
				+ this.rongpay_url + ", verify_url=" + this.verify_url
				+ ", charset=" + this.charset + ", sign_type=" + this.sign_type
				+ ", transport=" + this.transport + ", title=" + this.title
				+ ", body=" + this.body + ", total_fee=" + this.total_fee
				+ ", buyer_email=" + this.buyer_email + ", paymethod="
				+ this.paymethod + ", defaultbank=" + this.defaultbank
				+ ", sign=" + this.sign + ", order_no=" + this.order_no + "]";
	}

	public String BuildMysign(Map<String, String> sArray, String key) {
		if ((sArray != null) && (sArray.size() > 0)) {
			StringBuilder prestr = CreateLinkString(sArray);
			return md5(key);
		}
		return null;
	}

	public StringBuilder CreateLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder();
		String key = "";
		String value = "";
		for (int i = 0; i < keys.size(); ++i) {
			key = (String) keys.get(i);
			value = (String) params.get(key);
			if (("".equals(value)) || (value == null) || (key.equalsIgnoreCase("sign")))
				continue;
			if (key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			prestr.append(key).append("=").append(value).append("&");
		}
		return prestr.deleteCharAt(prestr.length() - 1);
	}

	public String md5(String text) {
		MessageDigest msgDigest = null;
		this.charset = StringUtils.isNull(Global.getValue("charset"));
		this.charset = ((Global.getValue("charset") == "") ? "utf-8" : this.charset);
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		try {
			msgDigest.update(text.getBytes(this.charset));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support your  EncodingException.");
		}

		byte[] bytes = msgDigest.digest();

		String md5Str = new String(encodeHex(bytes));

		return md5Str;
	}

	private static char[] encodeHex(byte[] data) {
		int l = data.length;

		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; ++i) {
			out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS[(0xF & data[i])];
		}

		return out;
	}

	public Map<String, String> transformRequestMap(Map<String, Object> requestParams) {
		Map<String, String> params = null;
		if ((requestParams != null) && (requestParams.size() > 0)) {
			params = new HashMap<String, String>();
			String name = "";
			String[] values = null;
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				name = (String) iter.next();
				values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; ++i) {
					valueStr = valueStr + values[i] + ",";
				}

				params.put(name, valueStr);
			}
		}
		return params;
	}

	public String Verify(String notify_id) {
		StringBuilder veryfy_url_builder = new StringBuilder();
		this.transport = Global.getValue("transport");
		String veryfy_url = Global.getValue("veryfy_url");
		this.merchant_ID = Global.getValue("merchant_ID");
		if (this.transport.equalsIgnoreCase("https"))
			veryfy_url_builder.append("https://interface.rongpay.com.cn/verify/notify?");
		else {
			veryfy_url_builder.append(this.verify_url);
		}
		veryfy_url_builder.append("merchant_ID=").append(this.merchant_ID).append("&notify_id=").append(notify_id);

		String responseTxt = CheckUrl(veryfy_url_builder.toString());

		return responseTxt;
	}

	private static String CheckUrl(String urlvalue) {
		String inputLine = "";
		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			if (in != null) {
				inputLine = in.readLine().toString();
			}
			in.close();
			urlConnection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputLine;
	}

	public String getMerchant_ID() {
		return this.merchant_ID;
	}

	public void setMerchant_ID(String merchant_ID) {
		this.merchant_ID = merchant_ID;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSeller_email() {
		return this.seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getNotify_url() {
		return this.notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return this.return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getMainname() {
		return this.mainname;
	}

	public void setMainname(String mainname) {
		this.mainname = mainname;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPayment_type() {
		return this.payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getRongpay_url() {
		return this.rongpay_url;
	}

	public void setRongpay_url(String rongpay_url) {
		this.rongpay_url = rongpay_url;
	}

	public String getVerify_url() {
		return this.verify_url;
	}

	public void setVerify_url(String verify_url) {
		this.verify_url = verify_url;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSign_type() {
		return this.sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getTransport() {
		return this.transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTotal_fee() {
		return this.total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getBuyer_email() {
		return this.buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getPaymethod() {
		return this.paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getDefaultbank() {
		return this.defaultbank;
	}

	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}

	public String getSign() {
		return this.sign;
	}

	public String getSign(RongPay rp) {
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("service", rp.getService());
		sPara.put("payment_type", rp.getPayment_type());
		sPara.put("merchant_ID", rp.getMerchant_ID());
		sPara.put("seller_email", rp.getSeller_email());
		sPara.put("return_url", rp.getReturn_url());
		sPara.put("notify_url", rp.getNotify_url());
		sPara.put("charset", rp.getCharset());
		sPara.put("order_no", rp.getOrder_no());
		sPara.put("title", rp.getTitle());
		sPara.put("body", rp.getBody());
		sPara.put("total_fee", rp.getTotal_fee());
		sPara.put("buyer_email", rp.getBuyer_email());
		sPara.put("paymethod", rp.getPaymethod());
		sPara.put("defaultbank", rp.getDefaultbank());
		String sign = BuildMysign(sPara, rp.getKey());
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOrder_no() {
		return this.order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}