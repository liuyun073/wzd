package com.liuyun.site.test.util;

import com.liuyun.site.tool.interest.EndInterestCalculator;
import java.io.PrintStream;
import org.junit.Test;

public class ToolTest {
	@Test
	public void testEndDayInterest() {
		EndInterestCalculator i = new EndInterestCalculator(10000.0D, 0.12D, 7);
		i.each();
		System.out.print(i.getEachString());
	}
}