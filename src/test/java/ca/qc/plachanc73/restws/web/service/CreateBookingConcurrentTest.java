package ca.qc.plachanc73.restws.web.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import junit.extensions.ActiveTestSuite;
import junit.extensions.RepeatedTest;
import junit.framework.TestSuite;

public class CreateBookingConcurrentTest extends AbstractUnitTest {

	@Test
	public void runRepeatedSuite() {
		TestSuite suite = new ActiveTestSuite();

		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		CreateBookingTest createBookingTest = new CreateBookingTest();
		createBookingTest.setMockMvc(mockMvc);

		// Try to create the same booking 10 times with 10 threads
		for (int i = 0; i < 10; i++) {
			suite.addTest(new RepeatedTest(createBookingTest, 10));
		}

		junit.run(suite);

		// Verify if only 1 booking was created
		assertEquals("Verify if only 1 booking was created", 1, CreateBookingTest.getCountBookingCreated());
	}
}