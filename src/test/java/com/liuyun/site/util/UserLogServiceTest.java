package com.liuyun.site.util;

import com.liuyun.site.service.UserLogService;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application*.xml" })
public class UserLogServiceTest {
	private static final Logger logger = Logger.getLogger(UserLogServiceTest.class);

	@Resource
	private UserLogService userLogService;

	@Test
	public void testGetLogListByUserId() {
	}
}