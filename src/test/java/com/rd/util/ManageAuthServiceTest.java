package com.rd.util;

import com.rd.model.purview.PurviewSet;
import com.rd.service.ManageAuthService;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/application*.xml" })
public class ManageAuthServiceTest {
	private static final Logger logger = Logger
			.getLogger(ManageAuthServiceTest.class);

	@Resource
	private ManageAuthService manageAuthService;

	@Test
	public void testGetPurviewByUserid() {
		List list = this.manageAuthService.getPurviewByUserid(1L);
		logger.debug(list);
		PurviewSet ps = new PurviewSet(list);
		Set set = ps.toSet();
		logger.debug("Set:" + set);
	}
}