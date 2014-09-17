package com.rd.util;

import com.rd.tool.interest.EndInterestCalculator;
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