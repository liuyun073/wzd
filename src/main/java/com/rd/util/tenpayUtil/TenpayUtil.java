package com.rd.util.tenpayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ##########################################################################################   
 * 项目名称：wzd   
 * 类名称： TenpayUtil   
 * 类描述： 财付通 工具类   
 * 创建人： 李桥文 525219246@qq.com   
 * 创建时间：Oct 26, 2013 9:15:49 PM  
 * ------------------------------------------------------ 
 * 修改人：   
 * 修改时间：Oct 26, 2013 9:15:49 PM   
 * 修改备注：   
 * @version    
 * ##########################################################################################
 */
public class TenpayUtil {
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception localException) {
		}
		return a;
	}

	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}

	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1D) {
			random += 0.1D;
		}
		for (int i = 0; i < length; ++i) {
			num *= 10;
		}
		return (int) (random * num);
	}

	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		if ((request == null) || (response == null)) {
			return "gbk";
		}

		String enc = request.getCharacterEncoding();
		if ((enc == null) || ("".equals(enc))) {
			enc = response.getCharacterEncoding();
		}

		if ((enc == null) || ("".equals(enc))) {
			enc = "gbk";
		}

		return enc;
	}

	public static long getUnixTime(Date date) {
		if (date == null) {
			return 0L;
		}

		return date.getTime() / 1000L;
	}

	public static String date2String(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);

		for (int i = 0; i < bArray.length; ++i) {
			String str = Integer.toHexString(0xFF & bArray[i]);

			if (str.length() < 2) {
				sb.append(0);
			}
			sb.append(str.toUpperCase());
		}
		return sb.toString();
	}
}