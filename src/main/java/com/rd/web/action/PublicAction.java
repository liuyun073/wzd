package com.rd.web.action;

import com.allinpay.ets.client.PaymentResult;
import com.eitop.platform.tools.Charset;
import com.eitop.platform.tools.encrypt.MD5Digest;
import com.eitop.platform.tools.encrypt.xStrEncrypt;
import com.rd.context.Constant;
import com.rd.context.Global;
import com.rd.domain.AccountLog;
import com.rd.exception.AccountException;
import com.rd.payment.GoPay;
import com.rd.payment.IPSPay;
import com.rd.payment.RongPay;
import com.rd.payment.ShengPay;
import com.rd.payment.TranGood;
import com.rd.payment.YbPay;
import com.rd.payment.hnapay;
import com.rd.payment.tenpay.TenpayRequestHandler;
import com.rd.payment.tenpay.TenpayResponseHandler;
import com.rd.payment.tenpay.client.ClientResponseHandler;
import com.rd.payment.tenpay.client.TenpayHttpClient;
import com.rd.service.AccountService;
import com.rd.util.CharsetTypeEnum;
import com.rd.util.StringUtils;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class PublicAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(PublicAction.class);
	private AccountService accountService;

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public String gopay() throws Exception {
		String signValueFromGopay = StringUtils.isNull(this.request.getParameter("signValue"));
		HttpServletResponse response = ServletActionContext.getResponse();
		GoPay pay = gopayCallback();
		logger.debug("Callback_signValue:         " + pay.getCallbackMd5SignVal());
		logger.debug("Callback_signValueFromGopay:" + signValueFromGopay + "===");
		String s = "";
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		log.setRemark("网上充值,国付宝充值,订单号:" + pay.getGood().getMerOrderNum());
		logger.info("=========="
				+ (pay.getRespCode() != null) + ":"
				+ ((pay.getRespCode() == null) ? "" : pay.getRespCode()).equals("0000") + ":"
				+ signValueFromGopay.equals(pay.getCallbackMd5SignVal()));
		String trade_no = pay.getGood().getMerOrderNum();
		try {
			if (trade_no == null){
				s = "RespCode=9999|JumpURL="
					+ Global.getValue("weburl")
					+ "/member/account/recharge.html";
			}
			else if (Global.TRADE_NO_SET.add(trade_no)) {
				if ((pay.getRespCode() != null)
						&& (pay.getRespCode().equals("0000"))
						&& (signValueFromGopay.equals(pay.getCallbackMd5SignVal()))) {
					this.accountService.newRecharge(pay.getGood().getMerOrderNum(), pay.getCallbackSignVal(), log);
					s = "RespCode=0000|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
				} else {
					this.accountService.failRecharge(pay.getGood().getMerOrderNum(), pay.getCallbackSignVal(), log);
					s = "RespCode=9999|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + trade_no + "重复充值！**********");
				s = "RespCode=9999|JumpURL="
						+ Global.getValue("weburl")
						+ "/member/account/recharge.html";
			}
		} catch (AccountException e) {
			logger.info("**********" + e.getMessage() + "，交易流水号:" + trade_no + "重复充值！**********");
			s = "RespCode=9999|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
		} catch (Exception e) {
			logger.info("**********充值异常，交易流水号:" + trade_no + "重复充值！**********");
			s = "RespCode=9999|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
		} finally {
			Global.TRADE_NO_SET.remove(trade_no);
		}
		logger.info("Recharge Result:" + s);
		PrintWriter p = response.getWriter();
		p.print(s);
		p.flush();
		p.close();
		return null;
	}

	public String ipspay() throws Exception {
		IPSPay ips = ipspayCallback();
		String content = ips.callbackSign();
		logger.info(content);

		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		String trade_no = ips.getBillno();
		log.setRemark("网上充值,环讯IPS充值,订单号:" + trade_no);
		try {
			if (trade_no == null){
				message("充值成功！", "/member/account/recharge.html");
			}
			else if (Global.TRADE_NO_SET.add(trade_no)) {
				cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
				String SignMD5 = b.toMD5(content + ips.getMer_key()).toLowerCase();
				if ((SignMD5.equals(ips.getSignature())) && (ips.getSucc().equalsIgnoreCase("Y"))) {
					this.accountService.newRecharge(trade_no, ips.callbackSign(), log);
					message("充值成功！", "/member/account/recharge.html");
				} else {
					this.accountService.failRecharge(trade_no, ips.callbackSign(), log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + trade_no
						+ "重复充值！**********");
				message("充值失败！", "/member/account/recharge.html");
			}
		} catch (AccountException e) {
			message("充值失败！", "/member/account/recharge.html");
		} catch (Exception e) {
			message("充值失败！", "/member/account/recharge.html");
			logger.error(e.getMessage());
		} finally {
			Global.TRADE_NO_SET.remove(trade_no);
		}
		return MSG;
	}

	public String cbpPay() throws Exception {
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		String v_oid = this.request.getParameter("v_oid");
		log.setRemark("网上充值,网银在线充值,订单号:" + v_oid);
		String v_pmode = new String(this.request.getParameter("v_pmode").getBytes("utf-8"), "gbk");
		String v_pstatus = this.request.getParameter("v_pstatus");
		String v_pstring = this.request.getParameter("v_pstring");
		String v_amount = this.request.getParameter("v_amount");
		String v_moneytype = this.request.getParameter("v_moneytype");
		String v_md5str = this.request.getParameter("v_md5str");
		String remark1 = this.request.getParameter("remark1");
		String remark2 = this.request.getParameter("remark2");
		String key = Global.getValue("cbp_key");
		String text = v_oid + v_pstatus + v_amount + v_moneytype + key;
		com.rd.tool.coder.MD5 md5 = new com.rd.tool.coder.MD5();
		String v_md5 = md5.getMD5ofStr(text).toUpperCase();
		String signStr = "v_oid=" + v_oid + "&v_pmode=" + v_pmode
				+ "&v_pstatus=" + v_pstatus + "&v_pstring=" + v_pstring
				+ "&v_amount=" + v_amount + "&v_moneytype=" + v_moneytype
				+ "&v_md5str=" + v_md5str + "&remark1=" + remark1 + "&remark2="
				+ remark2 + "&key=" + key + "&v_md5=" + v_md5;
		logger.debug("MD5校验码： " + v_md5str);
		logger.debug(v_md5);
		if (v_md5str.equals(v_md5)) {
			try {
				logger.debug("校验通过...");
				PrintWriter p = this.response.getWriter();
				p.print(OK);
				p.close();
				if (v_oid == null){
					message("充值失败！", "/member/account/recharge.html");
				}
				else if (Global.TRADE_NO_SET.add(v_oid)) {
					if ((v_pstatus != null) && (v_pstatus.equals("20"))) {
						this.accountService.newRecharge(v_oid, signStr, log);
						message("充值成功！", "/member/account/recharge.html");
					} else {
						this.accountService.failRecharge(v_oid, signStr, log);
						message("充值失败！", "/member/account/recharge.html");
					}
				} else {
					this.accountService.failRecharge(v_oid, signStr, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} catch (AccountException e) {
				message("充值失败！", "/member/account/recharge.html");
			} catch (Exception e) {
				logger.info("**********充值异常，交易流水号:" + v_oid + "重复充值！**********");
				message("充值失败！", "/member/account/recharge.html");
			} finally {
				Global.TRADE_NO_SET.remove(v_oid);
			}
		} else {
			logger.info("MD5校验失败...请求重发！");
			PrintWriter p = this.response.getWriter();
			p.print(ERROR);
			p.close();
		}
		return MSG;
	}

	public String xspay() throws Exception {
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		String orderID = this.request.getParameter("orderID");
		log.setRemark("网上充值,新生充值,订单号:" + orderID);
		String resultCode = this.request.getParameter("resultCode");
		String stateCode = this.request.getParameter("stateCode");
		String orderAmount = this.request.getParameter("orderAmount");
		String payAmount = this.request.getParameter("payAmount");
		String acquiringTime = this.request.getParameter("acquiringTime");
		String completeTime = this.request.getParameter("completeTime");
		String orderNo = this.request.getParameter("orderNo");
		String partnerID = this.request.getParameter("partnerID");
		String remark = this.request.getParameter("remark");
		String charset = this.request.getParameter("charset");
		String signType = this.request.getParameter("signType");
		String signMsg = this.request.getParameter("signMsg");
		Boolean result = Boolean.valueOf(false);
		String signStr = "orderID=" + orderID + "&resultCode=" + resultCode
				+ "&stateCode=" + stateCode + "&orderAmount=" + orderAmount
				+ "&payAmount=" + payAmount + "&acquiringTime=" + acquiringTime
				+ "&completeTime=" + completeTime + "&orderNo=" + orderNo
				+ "&partnerID=" + partnerID + "&remark=" + remark + "&charset="
				+ charset + "&signType=" + signType;
		try {
			if ("2".equals(signType)) {
				String pkey = Global.getValue("pkey");
				signStr = signStr + "&pkey=" + pkey;
				result = Boolean.valueOf(hnapay.verifySignatureByMD5(signStr, signMsg, CharsetTypeEnum.UTF8));
			} else if ("1".equals(signType)) {
				result = Boolean.valueOf(hnapay.verifySignatureByRSA(signStr, signMsg, CharsetTypeEnum.UTF8));
			}
			if ((result.booleanValue()) && (orderID != null)) {
				if (Global.TRADE_NO_SET.add(orderID)) {
					if ((stateCode != null) && (stateCode.equals("2"))) {
						this.accountService.newRecharge(orderID, signStr, log);
						message("充值成功！", "/member/account/recharge.html");
					} else {
						this.accountService.failRecharge(orderID, signStr, log);
						message("充值失败！", "/member/account/recharge.html");
					}
				} else {
					this.accountService.failRecharge(orderID, signStr, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				this.accountService.failRecharge(orderID, signStr, log);
				message("充值失败！", "/member/account/recharge.html");
			}
		} catch (AccountException e) {
			message("充值失败！", "/member/account/recharge.html");
		} catch (Exception e) {
			logger.info("**********充值异常，交易流水号:" + orderID + "重复充值！**********");
			message("充值失败！", "/member/account/recharge.html");
		} finally {
			Global.TRADE_NO_SET.remove(orderID);
		}
		return MSG;
	}

	public String ybpay() throws Exception {
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		AccountLog newlog = new AccountLog(1L, Constant.RECHARGE_APR, 1L, getTimeStr(), getRequestIp());
		String keyValue = Global.getValue("ybpay_mer_key");
		String r0_Cmd = this.request.getParameter("r0_Cmd");
		String p1_MerId = this.request.getParameter("p1_MerId");
		String r1_Code = this.request.getParameter("r1_Code");
		String r2_TrxId = this.request.getParameter("r2_TrxId");
		String r3_Amt = this.request.getParameter("r3_Amt");
		String r4_Cur = this.request.getParameter("r4_Cur");
		String r5_Pid = new String(this.request.getParameter("r5_Pid").getBytes("utf-8"), "gbk");
		String r6_Order = this.request.getParameter("r6_Order");
		String r7_Uid = this.request.getParameter("r7_Uid");
		String r8_MP = this.request.getParameter("r8_MP");
		String r9_BType = this.request.getParameter("r9_BType");
		String hmac = this.request.getParameter("hmac");
		Boolean isOK = Boolean.valueOf(true);
		String signStr = "keyValue=" + keyValue + "&r0_Cmd=" + r0_Cmd
				+ "&p1_MerId=" + p1_MerId + "&r1_Code=" + r1_Code
				+ "&r2_TrxId=" + r2_TrxId + "&r3_Amt=" + r3_Amt + "&r4_Cur="
				+ r4_Cur + "&r5_Pid=" + r5_Pid + "&r6_Order=" + r6_Order
				+ "&r7_Uid=" + r7_Uid + "&r8_MP=" + r8_MP + "&r9_BType="
				+ r9_BType + "&hmac=" + hmac;
		log.setRemark("网上充值,易宝充值,订单号:" + r6_Order);
		logger.debug("callback======" + signStr);
		try {
			isOK = Boolean.valueOf(YbPay.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order,
					r7_Uid, r8_MP, r9_BType, keyValue));
			if (isOK.booleanValue()) {
				if (r1_Code.equals("1")) {
					if (r9_BType.equals("1"))
						System.out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
					else if (r9_BType.equals("2")) {
						if (Global.TRADE_NO_SET.add(r6_Order)) {
							String webid = Global.getValue("webid");
							if (StringUtils.isNull(webid).equals("xdcf"))
								this.accountService.recharge(r6_Order, signStr, log, newlog);
							else {
								this.accountService.newRecharge(r6_Order, signStr, log);
							}

							message("充值成功！", "/member/account/recharge.html");
							PrintWriter p = this.response.getWriter();
							p.print(SUCCESS);
							p.flush();
							p.close();
						} else {
							message("重复充值！", "/member/account/recharge.html");
						}
					}
				}
			} else {
				this.accountService.failRecharge(r6_Order, signStr, log);
				message("充值失败！", "/member/account/recharge.html");
			}
		} catch (AccountException e) {
			message("充值失败！", "/member/account/recharge.html");
		} catch (Exception e) {
			logger.info("**********充值异常，交易流水号:" + r6_Order + "重复充值！**********");
			message("充值失败！", "/member/account/recharge.html");
		} finally {
			Global.TRADE_NO_SET.remove(r6_Order);
		}
		return MSG;
	}

	public String baoFoopay() throws Exception {
		logger.info("宝付支付充值回调成功,已经进入方法.....");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		String MerchantID = this.request.getParameter("MerchantID");
		String TransID = this.request.getParameter("TransID");
		logger.info("回调订单号： " + TransID);
		String Result = this.request.getParameter("Result");
		String resultDesc = this.request.getParameter("resultDesc");
		logger.info("支付结果描述: " + resultDesc);
		String factMoney = this.request.getParameter("factMoney");
		logger.info("实际交易金额：" + factMoney);
		String additionalInfo = this.request.getParameter("additionalInfo");
		String SuccTime = this.request.getParameter("SuccTime");
		String Md5Sign = this.request.getParameter("Md5Sign");
		String Md5key = Global.getValue("baofoo_privateKey");
		String md5 = MerchantID + TransID + Result + resultDesc + factMoney + additionalInfo + SuccTime + Md5key;
		com.rd.tool.coder.MD5 md5Obj = new com.rd.tool.coder.MD5();
		String WaitSign = md5Obj.getMD5ofStr(md5);
		log.setRemark("网上充值,宝付充值,订单号:" + TransID);

		WaitSign = WaitSign.toLowerCase();
		try {
			if (TransID == null){
				message("充值失败！", "/member/account/recharge.html");
			}
			else if (Global.TRADE_NO_SET.add(TransID)) {
				if (WaitSign.compareTo(Md5Sign) == 0) {
					logger.info("校验通过...");

					if ("1".equals(Result)) {
						logger.info(" 订单：" + TransID + " 宝付系统扣款成功，下面开始充值...");
						this.accountService.newRecharge(TransID, WaitSign, log);
						logger.info("单号：" + TransID + "充值成功！！！");
						message("充值成功！", "/member/account/recharge.html");
					} else {
						logger.info("订单：" + TransID + " 宝付系统支付失败.. ");
						this.accountService.failRecharge(TransID, WaitSign, log);
						message("充值失败！", "/member/account/recharge.html");
					}
				} else {
					logger.info("MD5校验失败...");
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + TransID + "重复充值！**********");
				message("充值失败！", "/member/account/recharge.html");
			}
		} finally {
			Global.TRADE_NO_SET.remove(TransID);
		}
		return MSG;
	}

	public String allinpay() throws Exception {
		logger.info("通联支付充值回调成功,已经进入方法.....");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());

		String orderNo = this.request.getParameter("orderNo");
		logger.info("回调订单号： " + orderNo);
		String payAmount = this.request.getParameter("payAmount");
		logger.info("实际交易金额：" + this.request.getParameter("payAmount"));
		String payResult = this.request.getParameter("payResult");
		logger.info("支付结果描述: " + this.request.getParameter("payResult"));

		String signMsg = this.request.getParameter("signMsg");

		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setMerchantId(this.request.getParameter("merchantId"));
		paymentResult.setVersion(this.request.getParameter("version"));
		paymentResult.setLanguage(this.request.getParameter("language"));
		paymentResult.setSignType(this.request.getParameter("signType"));
		paymentResult.setPayType(this.request.getParameter("payType"));
		paymentResult.setIssuerId(this.request.getParameter("issuerId"));
		paymentResult.setPaymentOrderId(this.request.getParameter("paymentOrderId"));
		paymentResult.setOrderNo(orderNo);
		paymentResult.setOrderDatetime(this.request.getParameter("orderDatetime"));
		paymentResult.setOrderAmount(this.request.getParameter("orderAmount"));
		paymentResult.setPayDatetime(this.request.getParameter("payDatetime"));
		paymentResult.setPayAmount(payAmount);
		paymentResult.setExt1(this.request.getParameter("ext1"));
		paymentResult.setExt2(this.request.getParameter("ext2"));
		paymentResult.setPayResult(this.request.getParameter("payResult"));
		paymentResult.setErrorCode(this.request.getParameter("errorCode"));
		paymentResult.setReturnDatetime(this.request.getParameter("returnDatetime"));
		paymentResult.setSignMsg(signMsg);
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		paymentResult.setCertPath(contextPath + "/data/cert/" + Global.getValue("tl_cert"));
		System.out.println(paymentResult.getCertPath());
		logger.info("通联MD5加密key" + Global.getValue("tl_key"));
		logger.info("通联公钥证书存放路径" + Global.getValue("tl_cert"));

		boolean verifyResult = paymentResult.verify();

		log.setRemark("网上充值,通联充值,订单号:" + orderNo);
		try {
			if (orderNo == null){
				message("充值失败！", "/member/account/recharge.html");
			}
			else if (Global.TRADE_NO_SET.add(orderNo)) {
				if (verifyResult) {
					logger.info("校验通过...");

					if (payResult.equals("1")) {
						logger.info(" 订单：" + orderNo + " 通联支付系统扣款成功，下面开始充值...");
						this.accountService.newRecharge(orderNo, signMsg, log);
						logger.info("单号：" + orderNo + "充值成功！！！");
						message("充值成功！", "/member/account/recharge.html");
					} else {
						logger.info("订单：" + orderNo + " 通联支付系统支付失败.. ");
						this.accountService.failRecharge(orderNo, signMsg, log);
						message("充值失败！", "/member/account/recharge.html");
					}
				} else {
					logger.info("MD5校验失败...");
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + orderNo + "重复充值！**********");
				message("充值失败！", "/member/account/recharge.html");
			}
		} finally {
			Global.TRADE_NO_SET.remove(orderNo);
		}

		return MSG;
	}

	public String dinpay() throws Exception {
		logger.info("智付支付充值回调成功,已经进入方法.....");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());

		String OrderInfo = Charset.ISO8859_2_Gb(this.request.getParameter("OrderMessage"));
		String MOSignMsg = this.request.getParameter("Digest");

		String m_id = "";
		String m_date = "";
		String m_orderID = "";
		String m_amount = "";
		String m_currency = "";
		String m_url = "";
		String m_language = "";
		String s_name = "";
		String s_addr = "";
		String s_postcode = "";
		String s_tel = "";
		String s_eml = "";
		String r_name = "";
		String r_addr = "";
		String r_postcode = "";
		String r_tel = "";
		String r_eml = "";
		String m_ocomment = "";
		String payStatus = "";

		String key = Global.getValue("dinpay_mer_key");
		String digest = MD5Digest.encrypt(OrderInfo + key);

		if (digest.equals(MOSignMsg)) {
			logger.info("校验通过...");

			OrderInfo = xStrEncrypt.StrDecrypt(OrderInfo, key);
			System.out.println("Kson Test OrderInfo=" + OrderInfo);

			StringTokenizer OrderInfos = new StringTokenizer(OrderInfo, "|");
			String[] array = new String[OrderInfos.countTokens()];
			int i = 0;
			while (OrderInfos.hasMoreTokens()) {
				array[i] = OrderInfos.nextToken();
				++i;
			}
			m_id = array[0];
			logger.info("回调商户号:" + m_id);
			m_date = array[1];
			m_orderID = array[2];
			m_amount = array[3];
			logger.info("交易金额:" + m_amount);
			m_currency = array[4];
			m_url = array[5];
			m_language = array[6];
			s_name = array[7];
			s_addr = array[8];
			s_postcode = array[9];
			s_tel = array[10];
			s_eml = array[11];
			r_name = array[12];
			r_addr = array[13];
			r_postcode = array[14];
			r_tel = array[15];
			r_eml = array[16];
			m_ocomment = array[17];
			payStatus = array[18];

			log.setRemark("网上支付，智付充值：" + m_orderID);
			try {
				if (m_orderID != null)
					if (Global.TRADE_NO_SET.add(m_orderID)) {
						if (payStatus.equals("2")) {
							logger.info(" 订单：" + m_orderID + " 智付系统扣款成功，下面开始充值...");
							this.accountService.newRecharge(m_orderID, MOSignMsg, log);
							logger.info("单号：" + m_orderID + "充值成功！！！");
							message("充值成功！", "/member/account/recharge.html");
						} else {
							logger.info("订单：" + m_orderID + " 宝付系统支付失败.. ");
							this.accountService.failRecharge(m_orderID, MOSignMsg, log);
							message("充值失败！", "/member/account/recharge.html");
						}
					} else {
						logger.info("**********充值失败，交易流水号:" + m_orderID + "重复充值！**********");
						message("重复充值！", "/member/account/recharge.html");
					}
			} catch (AccountException localAccountException) {
			}
		} else {
			logger.info("校验失败...");
			message("充值失败！", "/member/account/recharge.html");
		}

		return MSG;
	}

	public String ecpsspay() throws Exception {
		logger.info("汇潮支付回调成功，已经进入方法...");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());

		String CharacterEncoding = "UTF-8";
		this.request.setCharacterEncoding(CharacterEncoding);
		String BillNo = this.request.getParameter("BillNo");
		String Amount = this.request.getParameter("Amount");
		String tradeOrder = this.request.getParameter("tradeOrder");
		String Succeed = this.request.getParameter("Succeed");
		String Result = this.request.getParameter("Result");
		String receiveMD5info = this.request.getParameter("MD5info");
		String MD5key = Global.getValue("ecpsspay_mer_key");
		com.rd.tool.coder.MD5 md5 = new com.rd.tool.coder.MD5();
		String md5src = BillNo + Amount + Succeed + MD5key;
		String MD5info = md5.getMD5ofStr(md5src);
		if (StringUtils.isNull(receiveMD5info).equals(MD5info)) {
			logger.info("校验通过...");
			if (Global.TRADE_NO_SET.add(BillNo)) {
				if ("88".equals(Succeed)) {
					logger.info("订单" + BillNo + "汇潮支付系统扣款成功，下面开始充值...");
					log.setRemark("网上支付,汇潮支付：" + BillNo);
					this.accountService.newRecharge(BillNo, Succeed, log);
					logger.info("单号：" + BillNo + "充值成功！！！");
				} else {
					logger.info("订单：" + BillNo + " 汇潮支付系统支付失败.. ");
					this.accountService.failRecharge(BillNo, Succeed, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + BillNo + "重复充值！**********");
				message("重复充值！", "/member/account/recharge.html");
			}
		} else {
			logger.info("校验失败...");
			message("充值失败！", "/member/account/recharge.html");
		}

		return MSG;
	}

	public String ecpsspayFast() throws Exception {
		logger.info("汇潮快捷支付回调成功，已经进入方法...");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L,
				getTimeStr(), getRequestIp());

		String CharacterEncoding = "UTF-8";
		this.request.setCharacterEncoding(CharacterEncoding);
		String BillNo = this.request.getParameter("BillNo");
		String Amount = this.request.getParameter("Amount");
		String tradeOrder = this.request.getParameter("tradeOrder");
		String Succeed = this.request.getParameter("Succeed");
		String Result = this.request.getParameter("Result");
		String receiveMD5info = this.request.getParameter("MD5info");
		String MD5key = Global.getValue("ecpsspay_fast_mer_key");
		com.rd.tool.coder.MD5 md5 = new com.rd.tool.coder.MD5();
		String md5src = BillNo + Amount + Succeed + MD5key;
		String MD5info = md5.getMD5ofStr(md5src);
		if (StringUtils.isNull(receiveMD5info).equals(MD5info)) {
			logger.info("校验通过...");
			if (Global.TRADE_NO_SET.add(BillNo)) {
				if ("88".equals(Succeed)) {
					logger.info("订单" + BillNo + "汇潮支付系统扣款成功，下面开始充值...");
					log.setRemark("网上支付,汇潮支付：" + BillNo);
					this.accountService.newRecharge(BillNo, Succeed, log);
					logger.info("单号：" + BillNo + "充值成功！！！");
				} else {
					logger.info("订单：" + BillNo + " 汇潮支付系统支付失败.. ");
					this.accountService.failRecharge(BillNo, Succeed, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + BillNo + "重复充值！**********");
				message("重复充值！", "/member/account/recharge.html");
			}
		} else {
			logger.info("校验失败...");
			message("充值失败！", "/member/account/recharge.html");
		}

		return MSG;
	}

	public String shengpay() throws Exception {
		logger.info("盛付通支付回调成功，已经进入方法...");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L,
				getTimeStr(), getRequestIp());

		String CharacterEncoding = "UTF-8";
		this.request.setCharacterEncoding(CharacterEncoding);
		ShengPay spay = shengpayCallback();
		String signKey = Global.getValue("shengpay_signKey");
		ShengPay shengpay = new ShengPay();
		String xx = spay.toSignStringResponse();
		logger.info(xx);
		shengpay = ShengPay.signFormResponse(spay, signKey);
		String signMsg1 = shengpay.getSignMsg();
		logger.info(signMsg1);
		String OrderNo = spay.getOrderNo();
		String signMsg2 = this.request.getParameter("SignMsg");
		logger.info(signMsg2);
		String TransStatus = spay.getTransStatus();
		if (StringUtils.isNull(signMsg1).equals(signMsg2)) {
			logger.info("校验通过...");
			if (Global.TRADE_NO_SET.add(OrderNo)) {
				if ("01".equals(TransStatus)) {
					logger.info("订单" + OrderNo + "盛付通支付系统扣款成功，下面开始充值...");
					log.setRemark("网上支付,盛付通支付：" + OrderNo);
					this.accountService.newRecharge(OrderNo, TransStatus, log);
					message("充值成功！", "/member/account/recharge.html");
					logger.info("单号：" + OrderNo + "充值成功！！！");
				} else {
					logger.info("订单：" + OrderNo + "盛付通支付系统支付失败.. ");
					this.accountService.failRecharge(OrderNo, TransStatus, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				logger.info("**********充值失败，交易流水号:" + OrderNo + "重复充值！**********");
				message("重复充值！", "/member/account/recharge.html");
			}
		} else {
			logger.info("校验失败...");
			message("充值失败！", "/member/account/recharge.html");
		}

		return MSG;
	}

	public String tenpay() throws Exception {
		logger.info("腾讯财付通支付回调成功，已经进入方法...");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L,
				getTimeStr(), getRequestIp());

		String merNo = Global.getValue("tenpay_merchantID");

		String key = Global.getValue("tenpay_key");

		TenpayResponseHandler resHandler = new TenpayResponseHandler(this.request, this.response);
		resHandler.setKey(key);

		if (resHandler.isTenpaySign()) {
			logger.info("校验通过...");

			String notify_id = resHandler.getParameter("notify_id");

			TenpayRequestHandler queryReq = new TenpayRequestHandler(null, null);

			TenpayHttpClient httpClient = new TenpayHttpClient();

			ClientResponseHandler queryRes = new ClientResponseHandler();

			queryReq.init();
			queryReq.setKey(key);
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
			queryReq.setParameter("partner", merNo);
			queryReq.setParameter("notify_id", notify_id);

			httpClient.setTimeOut(5);

			httpClient.setReqContent(queryReq.getRequestURL());
			logger.info("queryReq:" + queryReq.getRequestURL());

			if (httpClient.call()) {
				queryRes.setContent(httpClient.getResContent());
				logger.info("queryRes:" + httpClient.getResContent());
				queryRes.setKey(key);

				String retcode = queryRes.getParameter("retcode");
				String trade_state = queryRes.getParameter("trade_state");

				String trade_mode = queryRes.getParameter("trade_mode");

				String out_trade_no = queryRes.getParameter("out_trade_no");

				if ((queryRes.isTenpaySign()) && ("0".equals(retcode)) && ("0".equals(trade_state)) && ("1".equals(trade_mode))) {
					logger.info("订单查询成功");

					logger.info("out_trade_no:"
							+ queryRes.getParameter("out_trade_no")
							+ " transaction_id:"
							+ queryRes.getParameter("transaction_id"));
					logger.info("trade_state:"
							+ queryRes.getParameter("trade_state")
							+ " total_fee:"
							+ queryRes.getParameter("total_fee"));

					logger.info("discount:" + queryRes.getParameter("discount") + " time_end:" + queryRes.getParameter("time_end"));

					if (Global.TRADE_NO_SET.add(out_trade_no)) {
						resHandler.sendToCFT("Success");

						logger.info("订单" + out_trade_no + "财付通支付系统扣款成功，下面开始充值...");
						log.setRemark("网上支付,财付通支付：" + out_trade_no);
						this.accountService.newRecharge(out_trade_no, trade_state, log);
						logger.info("单号：" + out_trade_no + "充值成功！！！");
						message("充值成功！", "/member/account/recharge.html");
					} else {
						resHandler.sendToCFT("Fail");
						logger.info("**********充值失败，交易流水号:" + out_trade_no + "重复充值！**********");
						message("重复充值！", "/member/account/recharge.html");
					}

				} else {
					resHandler.sendToCFT("Fail");

					logger.info("查询验证签名失败或业务错误");
					logger.info("retcode:" + queryRes.getParameter("retcode") + " retmsg:" + queryRes.getParameter("retmsg"));
					logger.info("订单：" + out_trade_no + " 汇潮支付系统支付失败.. ");
					this.accountService.failRecharge(out_trade_no, trade_state, log);
					message("充值失败！", "/member/account/recharge.html");
				}
			} else {
				resHandler.sendToCFT("Fail");
				logger.info("后台调用通信失败");
				logger.info(Integer.valueOf(httpClient.getResponseCode()));
				logger.info(httpClient.getErrInfo());

				message("充值失败！", "/member/account/recharge.html");
			}
		} else {
			resHandler.sendToCFT("Fail");
			logger.info("通知签名验证失败");
			logger.info("校验失败...");
			message("充值失败！", "/member/account/recharge.html");
		}

		return MSG;
	}

	public String rongpay() throws Exception {
		logger.info("融宝支付回调成功，已经进入方法...");
		AccountLog log = new AccountLog(1L, Constant.RECHARGE, 1L, getTimeStr(), getRequestIp());
		String key = Global.getValue("key");
		RongPay rp = new RongPay();

		Map<String, String> params = rp.transformRequestMap(this.request.getParameterMap());

		String mysign = rp.BuildMysign(params, key);

		String responseTxt = rp.Verify(this.request.getParameter("notify_id"));
		logger.info(responseTxt + "******");
		String sign = this.request.getParameter("sign");

		String trade_no = this.request.getParameter("trade_no");
		String order_no = this.request.getParameter("order_no");
		String total_fee = this.request.getParameter("total_fee");
		String title = this.request.getParameter("title");
		String body = this.request.getParameter("body");
		String buyer_email = this.request.getParameter("buyer_email");
		String trade_status = this.request.getParameter("trade_status");
		String is_success = this.request.getParameter("is_success");

		String signStr = "trade_no=" + trade_no + "&order_no=" + order_no
				+ "&total_fee=" + total_fee + "&title=" + title + "&body="
				+ body + "&buyer_email=" + buyer_email + "&trade_status="
				+ trade_status + "&sign=" + sign;
		String verifyStatus = "";
		String s = "";
		String result = "";
		try {
			if ((!"T".equals(is_success)) || (order_no == null)
					|| (!Global.TRADE_NO_SET.add(order_no))){
				verifyStatus = "验证成功";
			}
			else if ((mysign.equals(sign)) && (responseTxt.equals("true"))) {
				if (trade_status.equals("TRADE_FINISHED")) {
					this.accountService.newRecharge(order_no, signStr, log);
					message("充值成功！", "/member/account/recharge.html");
					result = SUCCESS;
				} else {
					this.accountService.failRecharge(order_no, signStr, log);
					message("充值失败！", "/member/account/recharge.html");
					result = FAIL;
				}

				verifyStatus = "验证成功";
			} else {
				verifyStatus = "验证失败";
				result = FAIL;
			}

		} catch (AccountException e) {
			logger.info("**********" + e.getMessage() + "，交易流水号:" + trade_no + "重复充值！**********");
			s = "RespCode=9999|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
		} catch (Exception e) {
			logger.info("**********充值异常，交易流水号:" + trade_no + "重复充值！**********");
			s = "RespCode=9999|JumpURL=" + Global.getValue("weburl") + "/member/account/recharge.html";
		} finally {
			Global.TRADE_NO_SET.remove(order_no);
		}
		logger.info("Recharge Result:" + s);
		PrintWriter p = this.response.getWriter();
		p.print(result);
		p.flush();
		p.close();
		return MSG;
	}

	public GoPay gopayCallback() {
		GoPay pay = new GoPay();
		pay.setPrivateKey(Global.getValue("gopay_parivateKey"));
		TranGood good = createTranGood();
		pay.setGood(good);
		pay.setTranIP(this.request.getParameter("tranIP"));
		pay.setFrontMerUrl(this.request.getParameter("frontMerUrl"));
		pay.setBackgroundMerUrl(this.request.getParameter("backgroundMerUrl"));

		pay.setVersion(this.request.getParameter("version"));
		pay.setTranCode(this.request.getParameter("tranCode"));
		pay.setMerchantID(this.request.getParameter("merchantID"));
		pay.setOrderId(this.request.getParameter("orderId"));
		pay.setGopayOutOrderId(this.request.getParameter("gopayOutOrderId"));
		pay.setRespCode(this.request.getParameter("respCode"));
		pay.setServetime(this.request.getParameter("gopayServerTime"));
		String plain = pay.getSignValue();
		logger.debug(plain);
		return pay;
	}

	public IPSPay ipspayCallback() {
		String billno = this.request.getParameter("billno");
		String currency_type = this.request.getParameter("Currency_type");
		String amount = this.request.getParameter("amount");
		String date = this.request.getParameter("date");
		String succ = this.request.getParameter("succ");
		String msg = this.request.getParameter("msg");
		String attach = this.request.getParameter("attach");
		String ipsbillno = this.request.getParameter("ipsbillno");
		String retEncodeType = this.request.getParameter("retencodetype");
		String signature = this.request.getParameter("signature");
		IPSPay ips = new IPSPay();
		ips.setBillno(billno);
		ips.setCurrency_Type(currency_type);
		ips.setAmount(amount);
		ips.setDate(date);
		ips.setSucc(succ);
		ips.setMsg(msg);
		ips.setAttach(attach);
		ips.setIpsbillno(ipsbillno);
		ips.setRetEncodeType(retEncodeType);
		ips.setSignature(signature);
		ips.setMer_key(Global.getValue("ips_mer_key"));
		return ips;
	}

	public ShengPay shengpayCallback() {
		String Name = this.request.getParameter("Name");
		String Version = this.request.getParameter("Version");
		String Charset = this.request.getParameter("Charset");
		String TraceNo = this.request.getParameter("TraceNo");
		String MsgSender = this.request.getParameter("MsgSender");
		String SendTime = this.request.getParameter("SendTime");
		String MerchantNo = this.request.getParameter("MerchantNo");
		String InstCode = this.request.getParameter("InstCode");
		String OrderNo = this.request.getParameter("OrderNo");
		String OrderAmount = this.request.getParameter("OrderAmount");
		String TransNo = this.request.getParameter("TransNo");
		String TransAmount = this.request.getParameter("TransAmount");
		String TransStatus = this.request.getParameter("TransStatus");
		String TransType = this.request.getParameter("TransType");
		String TransTime = this.request.getParameter("TransTime");
		String ErrorCode = this.request.getParameter("ErrorCode");
		String ErrorMsg = this.request.getParameter("ErrorMsg");
		String Ext1 = this.request.getParameter("Ext1");
		String SignType = this.request.getParameter("SignType");
		String SignMsg = this.request.getParameter("SignMsg");
		ShengPay spay = new ShengPay();
		spay.setName(Name);
		spay.setVersion(Version);
		spay.setCharset(Charset);
		spay.setTraceNo(TraceNo);
		spay.setMsgSender(MsgSender);
		spay.setSendTime(SendTime);
		spay.setMerchantNo(MerchantNo);
		spay.setInstCode(InstCode);
		spay.setOrderNo(OrderNo);
		spay.setOrderAmount(OrderAmount);
		spay.setTransNo(TransNo);
		spay.setTransAmount(TransAmount);
		spay.setTransStatus(TransStatus);
		spay.setTransType(TransType);
		spay.setTransTime(TransTime);
		spay.setErrorCode(ErrorCode);
		spay.setErrorMsg(ErrorMsg);
		spay.setExt1(Ext1);
		spay.setSignType(SignType);
		spay.setSignMsg(SignMsg);
		return spay;
	}

	private TranGood createTranGood() {
		TranGood good = new TranGood();
		good.setTranDateTime(this.request.getParameter("tranDateTime"));
		good.setMerOrderNum(this.request.getParameter("merOrderNum"));

		good.setTranAmt(this.request.getParameter("tranAmt"));
		good.setFeeAmt(this.request.getParameter("feeAmt"));

		good.setGoodsName(Global.getValue("webname"));
		return good;
	}

	public String blank() throws Exception {
		return SUCCESS;
	}
}