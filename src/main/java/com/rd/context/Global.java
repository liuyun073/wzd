package com.rd.context;

import com.rd.domain.BorrowConfig;
import com.rd.domain.NoticeConfig;
import com.rd.domain.Rule;
import com.rd.model.SystemInfo;
import com.rd.tool.Utils;
import com.rd.util.NumberUtils;
import com.rd.util.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Global {
	public static SystemInfo SYSTEMINFO;
	public static Map<Long, BorrowConfig> BORROWCONFIG;
	public static Map<String, NoticeConfig> NOTICECONFIG;
	public static Map<String, Rule> RULES;
	public static Set<String> TRADE_NO_SET = Collections.synchronizedSet(new HashSet<String>());
	//public static Set TENDER_SET = Collections.synchronizedSet(new HashSet());
	public static Map<String, Object> TENDER_MAP = Collections.synchronizedMap(new HashMap<String, Object>());
	public static Map<String, Object> SESSION_MAP = Collections.synchronizedMap(new HashMap<String, Object>());

	public static String[] SYSTEMNAME = { "webname", "meta_keywords",
			"meta_description", "beian", "copyright", "fuwutel", "address",
			"weburl", "vip_fee", "most_cash", "theme_dir", "version",
			"normal_rate", "overdue_rate", "bad_rate" };

	public static ThreadLocal<String> ipThreadLocal = new ThreadLocal<String>();

	public static String VERSION = "v1.6.4.1";

	public static BorrowConfig getBorrowConfig(int borrowType) {
		BorrowConfig config = null;
		if (BORROWCONFIG == null) {
			return null;
		}
		config = BORROWCONFIG.get(new Long(borrowType));
		if (config == null)
			return null;
		return config;
	}

	public static NoticeConfig getNoticeConfig(String noticeType) {
		NoticeConfig config = new NoticeConfig();
		if (NOTICECONFIG == null) {
			return config;
		}
		config = NOTICECONFIG.get(noticeType);
		return config;
	}

	public static String getValue(String key) {
		String o = null;
		if (SYSTEMINFO != null) {
			o = SYSTEMINFO.getValue(key);
		}
		if (o == null) {
			return "";
		}
		return o;
	}

	public static String getString(String key) {
		String s = StringUtils.isNull(getValue(key));
		return s;
	}

	public static int getInt(String key) {
		int i = NumberUtils.getInt(getValue(key));
		return i;
	}

	public static double getDouble(String key) {
		double i = NumberUtils.getDouble(getValue(key));
		return i;
	}

	public static String getBorrowTypeName(int type) {
		switch (type) {
		case 100:
			return "month";
		case 101:
			return "miaobiao";
		case 102:
			return "month";
		case 103:
			return "fast";
		case 104:
			return "jin";
		case 105:
			return "vouch";
		case 106:
			return "art";
		case 107:
			return "charity";
		case 108:
			return "preview";
		case 109:
			return "project";
		case 110:
			return "flow";
		case 111:
			return "student";
		case 112:
			return "offvouch";
		case 113:
			return "pledge";
		}
		return "";
	}

	public static int getBorrowType(String type) {
		if (("month".equals(type)) || ("".equals(type)))
			return 102;
		if ("miaobiao".equals(type))
			return 101;
		if ("xin".equals(type))
			return 102;
		if ("fast".equals(type))
			return 103;
		if ("jin".equals(type))
			return 104;
		if ("charity".equals(type))
			return 107;
		if ("project".equals(type))
			return 109;
		if ("flow".equals(type))
			return 110;
		if ("student".equals(type))
			return 111;
		if ("offvouch".equals(type))
			return 112;
		if ("pledge".equals(type))
			return 113;
		if ("donation".equals(type)) {
			return 114;
		}
		return 102;
	}

	public static synchronized Object getTenderLock(String borrowId) {
		return TENDER_MAP.get(borrowId);
	}

	public static double getCash(double x, double r, double money,
			double maxCash) {
		String site_id = StringUtils.isNull(getValue("webid"));
		double fee = 0.0D;
		if ((Constant.ZRZB.equals(site_id)) || ("zdvci".equals(site_id))
				|| ("xdcf".equals(site_id)) || ("lhcf".equals(site_id))
				|| ("bfct".equals(site_id)))
			fee = Utils.getCashFeeForZrzbZero(x, r, money);
		else if (Constant.HUIZHOUDAI.equals(site_id))
			fee = Utils.getCashFeeForHuidai(x, money, r);
		else if (("lhdai".equals(site_id)) || ("glct".equals(site_id))
				|| ("baoducf".equals(site_id)) || ("hycf".equals(site_id))
				|| ("zhiyacf".equals(site_id)))
			fee = Utils.getCashFeeForlhd(x, money, r, maxCash);
		else if ("rydai".equals(site_id))
			fee = Utils.getCashFeeForRYD(x, r, money);
		else {
			fee = Utils.getCashFeeForZRZB(x, money, r, maxCash);
		}
		return fee;
	}

	public static double getCash(double x, double r, double money,
			double maxCash, double tender_award) {
		double fee = 0.0D;
		fee = Utils.getCashFeeForSSJB(x, money, r, maxCash, tender_award);
		return fee;
	}

	public static double getCashForWzdai(double use_money, double ownmoney,
			double x, double r, double money, double maxCash) {
		String site_id = StringUtils.isNull(getValue("webid"));
		double fee = 0.0D;
		if (site_id.equals("jsy"))
			fee = Utils.getCashFeeForJJY(x, r, money, maxCash);
		else if ((site_id.equals("zsdai")) || (site_id.equals("aidai"))) {
			fee = Utils.GetZsdCashFee(x, r, money, maxCash);
		} else if ((use_money >= 200000.0D) && (ownmoney >= 200000.0D)
				&& (x >= 200000.0D))
			fee = Utils.GetLargeCashFee(x, r, money, maxCash);
		else {
			fee = Utils.GetCashFee(x, r, money, maxCash);
		}

		return fee;
	}

	public static String getWebid() {
		return StringUtils.isNull(getValue("webid"));
	}

	public static double getCashForHndai(double x, double r, double money,
			double maxCash, double o, double t, double or) {
		double fee = 0.0D;
		fee = Utils.getCashFeeForHndai(x, money, r, maxCash, o, t, or);
		return fee;
	}

	public static double getCashForJlct(double x, double r, double money,
			double maxCash, double lastSum) {
		double fee = 0.0D;
		fee = Utils.getCashFeeForJlct(x, r, money, lastSum);
		return fee;
	}

	public static String getIP() {
		String retObj = ipThreadLocal.get();
		return (retObj == null) ? "" : retObj;
	}

	public static String getVersion() {
		return getString("version");
	}

	public static Rule getRule(String nid) {
		return (Rule) RULES.get(nid);
	}
}