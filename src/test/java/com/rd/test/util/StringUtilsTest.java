package com.rd.test.util;

import com.rd.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class StringUtilsTest {
	private Logger logger = Logger.getLogger(StringUtilsTest.class);

	@Test
	public void testIsInSplit() {
		String str = "100,101,102,103,104";

		this.logger.info(Boolean.valueOf(StringUtils.isInSplit(str, "100")));
	}

	@Test
	public void testIsCard() {
		String str = "1021199202140323";
		this.logger.info(Boolean.valueOf(StringUtils.isCard(str)));
	}

	@Test
	public void testMd5() {
		String k = "123456";
		this.logger.info("Apache:" + DigestUtils.md5Hex(k));
		cryptix.jce.provider.MD5 b = new cryptix.jce.provider.MD5();
		this.logger.info("JCE:" + b.toMD5(k));
		com.rd.tool.coder.MD5 md5 = new com.rd.tool.coder.MD5();
		this.logger.info("md5:" + md5.getMD5ofStr(k));
	}

	@Test
	public void testLength() {
		String str = "庆祝平台上线，秒标不用抢，随到随秒。";
		this.logger.info(Integer.valueOf(str.length()));
	}
}