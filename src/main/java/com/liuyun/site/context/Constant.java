package com.liuyun.site.context;

public class Constant {
	
	
	public static int TYPE_ALL = 100;

	public static int TYPE_SECOND = 101;

	public static int TYPE_CREDIT = 102;

	public static int TYPE_MORTGAGE = 103;

	public static int TYPE_PROPERTY = 104;

	public static int TYPE_VOUCH = 105;

	public static int TYPE_ART = 106;

	public static int TYPE_CHARITY = 107;

	public static int TYPE_PREVIEW = 108;

	public static int TYPE_PROJECT = 109;

	public static int TYPE_FLOW = 110;

	public static int TYPE_STUDENT = 111;

	public static int TYPE_OFFVOUCH = 112;

	public static int TYPE_PLEDGE = 113;

	public static int TYPE_DONATION = 114;
	

	public static int STATUS_ALL = -1;

	public static int STATUS_NEW = 0;

	public static int STATUS_TRIAL = 1;

	public static int STATUS_REVIEW = 3;

	public static int STATUS_NOREVIEWS = 4;

	public static int STATUS_CANCEL = 5;

	public static int STATUS_BORROWSUCCESS = 6;

	public static int STATUS_PARTREPAIED = 7;

	public static int STATUS_ALLREPAIED = 8;

	public static int STATUS_EXPIRED = 9;

	public static int STATUS_REPAYING = 10;

	public static int STATUS_INDEX = 11;
	public static int STATUS_complete = 12;

	public static int STATUS_ZR_INDEX = 13;

	public static int STATUS_md_INDEX = 14;

	public static int STATUS_TENDER_INDEX = 23;

	public static int STATUS_JSY_INDEX = 24;

	public static int STATUS_INDEX_ZA = 25;
	
	

	public static int ORDER_NONE = 0;

	public static int ORDER_ACCOUNT_UP = 1;

	public static int ORDER_APR_UP = 2;

	public static int ORDER_JINDU_UP = 3;

	public static int ORDER_CREDIT_UP = 4;

	public static int ORDER_ACCOUNT_DOWN = -1;

	public static int ORDER_APR_DOWN = -2;

	public static int ORDER_JINDU_DOWN = -3;

	public static int ORDER_CREDIT_DOWN = -4;

	public static int ORDER_INDEX = 11;

	public static int ORDER_TENDER_ADDTIME_UP = 21;

	public static int ORDRE_TENDER_ADDTIME_DOWN = 21;

	public static int ORDER_BORROW_ADDTIME_UP = 5;

	public static int ORDER_BORROW_ADDTIME_DOWN = -5;

	public static int ORDER_BORROW_TYPE_UP = 6;

	public static int ORDER_BORROW_VERIFY_TIME_UP = 7;

	public static int ORDER_BORROW_VERIFY_TIME_DOWN = -7;
	

	public static String SESSION_USER = "session_user";
	public static String LOGINTIME = "logintime";
	public static String AUTH_USER = "auth_user";
	public static String AUTH_PURVIEW = "auth_purview";

	public static int BORROW_KIND = 19;

	public static String TENDER = "tender";

	public static String RECHARGE = "recharge";
	public static String RECHARGE_APR = "recharge_apr";
	public static String SYSTEM = "system";
	public static String INVEST = "invest";
	public static String RECHARGE_SUCCESS = "recharge_success";
	public static String FREEZE = "freeze";
	public static String UNFREEZE = "unfreeze";
	public static String BORROW_SUCCESS = "borrow_success";
	public static String BORROW_FEE = "borrow_fee";
	public static String VIP_BORROW_FEE = "vip_borrow_fee";
	public static String MANAGE_FEE = "manage_fee";
	public static String REPAID = "repaid";
	public static String PREREPAID = "prerepaid";
	public static String VOUCH_REPAID = "vouch_repaid";
	public static String VOUCH_WARD = "vouch_ward";
	public static String BORROW_FAIL = "borrow_fail";
	public static String VIP_FEE = "vip_fee";
	public static String INVITE_MONEY = "invite_money";
	public static String ACCOUNT_BACK = "account_back";
	public static String CASH_FROST = "cash_frost";
	public static String CASH_SUCCESS = "cash_success";
	public static String CASH_FAIL = "CASH_fail";
	public static String CASH_CANCEL = "cash_cancel";
	public static String WAIT_INTEREST = "wait_interest";
	public static String AWARD_ADD = "award_add";
	public static String AWARD_DEDUCT = "award_deduct";
	public static String HUIKUAN_AWARD = "huikuan_award";
	public static String RECHARGE_FEE = "recharge_fee";
	public static String CAPITAL_COLLECT = "capital_collect";
	public static String INTEREST_COLLECT = "interest_collect";
	public static String FEE = "fee";
	public static String LATE_DEDUCT = "late_deduct";
	public static String LATE_REPAYMENT = "late_repayment";
	public static String OFFRECHARGE_AWARD = "offrecharge_award";
	public static String TRANSACTION_FEE = "transaction_fee";
	public static String BANK_SUCCESS = "bank_success";

	public static String LOTTERY_AWARD = "lottery_award";

	public static String LOGIN_TIME = "login_time";

	public static String NORMAL_RATE = "normal_rate";

	public static String OVERDUE_RAGE = "overdue_rate";

	public static String BAD_RATE = "bad_rate";
	public static final String DEL_RECEIVE_MSG = "1";
	public static final String DEL_SENT_MSG = "2";
	public static final String SET_UNREAD__MSG = "3";
	public static final String SET_READ_MSG = "4";
	public static final String DB_PREFIX = "dw_";
	public static final long ADMIN_ID = 1L;
	public static final String OP_ADD = "1";
	public static final String OP_REDUCE = "2";
	public static String ZRZB = "zrzb";
	public static String MDW = "mdw";
	public static String HUIZHOUDAI = "huidai";

	public static String HONGBAO_ADD = "hongbao_add";

	public static String HONGBAO_LESS = "hongbao_less";

	public static String HONGBAO_LESS_FREEZE = "hongbao_less_freeze";

	public static String HONGBAO_LESS_FAIL = "hongbao_less_fail";

	public static String Invite_RECHARGE_AWARD = "invite_recharge_award";

	public static String TENDER_AWARD = "tender_award";

	public static String BACKSTAGE_BACK_APPLY = "backstage_back_apply";

	public static String BACKSTAGE_BACK_SUCCESS = "backstage_back_success";

	public static String BACKSTAGE_BACK_FAIL = "backstage_back_fail";

	public static String BACKSTAGE_RECHARGE_APPLY = "backstage_recharge_apply";

	public static String BACKSTAGE_RECHARGE_SUCCESS = "backstage_recharge_success";

	public static String BACKSTAGE_RECHARGE_FAIL = "backstage_recharge_fail";

	public static String ONLINE_RECHARGE_SUCCESS = "online_recharge_success";

	public static String ONLINE_RECHARGE_FAIL = "online_recharge_fail";

	public static String LINE_RECHARGE_SUCCESS = "line_recharge_success";

	public static String LINE_RECHARGE_FAIL = "line_recharge_fail";

	public static String VIP_SUCCESS = "vip_success";

	public static String VIP_FAIL = "vip_fail";

	public static String REALNAME_SUCCESS = "realname_success";

	public static String REALNAME_FAIL = "realname_fail";

	public static String PHONE_SUCCESS = "phone_success";

	public static String PHONE_FAIL = "phone_fail";

	public static String BORROW_FIRST_VERIFY_SUCCESS = "borrow_first_verify_success";

	public static String BORROW_FIRST_VERIFY_FAIL = "borrow_first_verify_fail";

	public static String BORROW_FULL_VERIFY_SUCCESS = "borrow_full_verify_success";

	public static String BORROW_FULL_VERIFY_FAIL = "borrow_full_verify_fail";

	public static String INVITE_AWARD = "invite_award";

	public static String RECHARGE_PROTOCOL = "recharge_protocol";
	public static String CASH_PROTOCOL = "cash_protocol";
	public static String TENDER_PROTOCOL = "tender_protocol";
	public static String REPAYMENT_ACCOUNT_PROTOCOL = "repayment_account_protocol";
	public static String AWARD_PROTOCOL = "award_protocol";

	public static String TROUBLE_AWARD = "trouble_award";

	public static String TROUBLE_DONATE = "trouble_donate";
}