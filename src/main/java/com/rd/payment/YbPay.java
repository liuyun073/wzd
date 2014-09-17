package com.rd.payment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class YbPay {
	private static final Logger logger = Logger.getLogger(YbPay.class);
	private String p0_Cmd;
	private String p1_MerId;
	private String p2_Order;
	private String p3_Amt;
	private String p4_Cur;
	private String p5_Pid;
	private String nodeAuthorizationURL;
	private String keyValue;
	private static Object lock = new Object();
	private static YbPay ybpay = null;
	private static ResourceBundle rb = null;
	private static final String CONFIG_FILE = "merchantInfo";
	private static String encodingCharset = "UTF-8";
	private String p6_Pcat;
	private String p7_Pdesc;
	private String p8_Url;
	private String p9_SAF;
	private String pa_MP;
	private String pd_FrpId;
	private String pr_NeedResponse;
	private String hmac;

	public String getKeyValue() {
		return this.keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getNodeAuthorizationURL() {
		return this.nodeAuthorizationURL;
	}

	public void setNodeAuthorizationURL(String nodeAuthorizationURL) {
		this.nodeAuthorizationURL = nodeAuthorizationURL;
	}

	public YbPay() {
		rb = ResourceBundle.getBundle("merchantInfo");
	}

	public static YbPay getInstance() {
		synchronized (lock) {
			if (ybpay == null) {
				ybpay = new YbPay();
			}
		}
		return ybpay;
	}

	public String getValue(String key) {
		return rb.getString(key);
	}

	public static String getReqMd5HmacForOnlinePayment(String p0_Cmd,
			String p1_MerId, String p2_Order, String p3_Amt, String p4_Cur,
			String p5_Pid, String p6_Pcat, String p7_Pdesc, String p8_Url,
			String p9_SAF, String pa_MP, String pd_FrpId,
			String pr_NeedResponse, String keyValue) {
		StringBuffer sValue = new StringBuffer();

		sValue.append(p0_Cmd);

		sValue.append(p1_MerId);

		sValue.append(p2_Order);

		sValue.append(p3_Amt);

		sValue.append(p4_Cur);

		sValue.append(p5_Pid);

		sValue.append(p6_Pcat);

		sValue.append(p7_Pdesc);

		sValue.append(p8_Url);

		sValue.append(p9_SAF);

		sValue.append(pa_MP);

		sValue.append(pd_FrpId);

		sValue.append(pr_NeedResponse);

		String sNewString = null;

		sNewString = hmacSign(sValue.toString(), keyValue);
		return sNewString;
	}

	public static String hmacSign(String aValue, String aKey) {
		byte[] k_ipad = new byte[64];
		byte[] k_opad = new byte[64];
		byte[] value;
		byte[] keyb;
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

//		Arrays.fill(k_ipad, keyb.length, 64, 54);
//		Arrays.fill(k_opad, keyb.length, 64, 92);
		
		for (int i = 0; i < keyb.length; ++i) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5C);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte[] dg = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte[] input) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; ++i) {
			int current = input[i] & 0xFF;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	public static boolean verifyCallback(String hmac, String p1_MerId,
			String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
			String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
			String r8_MP, String r9_BType, String keyValue) {
		StringBuffer sValue = new StringBuffer();

		sValue.append(p1_MerId);

		sValue.append(r0_Cmd);

		sValue.append(r1_Code);

		sValue.append(r2_TrxId);

		sValue.append(r3_Amt);

		sValue.append(r4_Cur);

		sValue.append(r5_Pid);

		sValue.append(r6_Order);

		sValue.append(r7_Uid);

		sValue.append(r8_MP);

		sValue.append(r9_BType);
		String sNewString = null;
		sNewString = hmacSign(sValue.toString(), keyValue);
		logger.debug("sNewString=======" + sNewString);
		logger.debug("hmac=======" + hmac);

		return hmac.equals(sNewString);
	}

	public String getP0_Cmd() {
		return this.p0_Cmd;
	}

	public void setP0_Cmd(String p0_Cmd) {
		this.p0_Cmd = p0_Cmd;
	}

	public String getP1_MerId() {
		return this.p1_MerId;
	}

	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}

	public String getP2_Order() {
		return this.p2_Order;
	}

	public void setP2_Order(String p2_Order) {
		this.p2_Order = p2_Order;
	}

	public String getP3_Amt() {
		return this.p3_Amt;
	}

	public void setP3_Amt(String p3_Amt) {
		this.p3_Amt = p3_Amt;
	}

	public String getP4_Cur() {
		return this.p4_Cur;
	}

	public void setP4_Cur(String p4_Cur) {
		this.p4_Cur = p4_Cur;
	}

	public String getP5_Pid() {
		return this.p5_Pid;
	}

	public void setP5_Pid(String p5_Pid) {
		this.p5_Pid = p5_Pid;
	}

	public String getP6_Pcat() {
		return this.p6_Pcat;
	}

	public void setP6_Pcat(String p6_Pcat) {
		this.p6_Pcat = p6_Pcat;
	}

	public String getP7_Pdesc() {
		return this.p7_Pdesc;
	}

	public void setP7_Pdesc(String p7_Pdesc) {
		this.p7_Pdesc = p7_Pdesc;
	}

	public String getP8_Url() {
		return this.p8_Url;
	}

	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
	}

	public String getP9_SAF() {
		return this.p9_SAF;
	}

	public void setP9_SAF(String p9_SAF) {
		this.p9_SAF = p9_SAF;
	}

	public String getPa_MP() {
		return this.pa_MP;
	}

	public void setPa_MP(String pa_MP) {
		this.pa_MP = pa_MP;
	}

	public String toString() {
		return "YbPay [p0_Cmd=" + this.p0_Cmd + ", p1_MerId=" + this.p1_MerId
				+ ", p2_Order=" + this.p2_Order + ", p3_Amt=" + this.p3_Amt
				+ ", p4_Cur=" + this.p4_Cur + ", p5_Pid=" + this.p5_Pid
				+ ", nodeAuthorizationURL=" + this.nodeAuthorizationURL
				+ ", keyValue=" + this.keyValue + ", p6_Pcat=" + this.p6_Pcat
				+ ", p7_Pdesc=" + this.p7_Pdesc + ", p8_Url=" + this.p8_Url
				+ ", p9_SAF=" + this.p9_SAF + ", pa_MP=" + this.pa_MP
				+ ", pd_FrpId=" + this.pd_FrpId + ", pr_NeedResponse="
				+ this.pr_NeedResponse + ", hmac=" + this.hmac + "]";
	}

	public String getPd_FrpId() {
		return this.pd_FrpId;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	public String getPr_NeedResponse() {
		return this.pr_NeedResponse;
	}

	public void setPr_NeedResponse(String pr_NeedResponse) {
		this.pr_NeedResponse = pr_NeedResponse;
	}

	public String getHmac() {
		return this.hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}