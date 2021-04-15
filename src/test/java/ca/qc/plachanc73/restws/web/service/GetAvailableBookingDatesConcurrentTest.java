package ca.qc.plachanc73.restws.web.service;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import junit.extensions.ActiveTestSuite;
import junit.extensions.RepeatedTest;
import junit.framework.TestSuite;

public class GetAvailableBookingDatesConcurrentTest extends AbstractUnitTest {

	@Test
	public void runRepeatedSuite() {
		TestSuite suite = new ActiveTestSuite();

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		GetAvailableBookingDatesTest getAvailableBookingDatesTest = new GetAvailableBookingDatesTest();
		getAvailableBookingDatesTest.setMockMvc(mockMvc);

		// Try to get available booking dates 10 times with 10 threads
		for (int i = 0; i < 10; i++) {
			suite.addTest(new RepeatedTest(getAvailableBookingDatesTest, 10));
		}

		junit.run(suite);
	}
}