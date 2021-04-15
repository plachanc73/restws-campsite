package ca.qc.plachanc73.restws.web.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import ca.qc.plachanc73.restws.util.DateUtil;
import ca.qc.plachanc73.restws.util.JsonUtil;
import ca.qc.plachanc73.restws.web.json.BookingJson;
import junit.framework.Test;
import junit.framework.TestResult;

public class CreateBookingTest extends AbstractUnitTest implements Test {

	private static int countBookingCreated = 0;

	private MockMvc mockMvc;

	public static int getCountBookingCreated() {
		return countBookingCreated;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public MockMvc getMockMvc() {
		return this.mockMvc == null ? super.mockMvc : this.mockMvc;
	}

	@Override
	public int countTestCases() {
		return 1;
	}

	@Override
	public void run(TestResult result) {
		try {
			post_create_booking();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void post_create_booking() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-25"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		MockHttpServletResponse mockResponse = getMockMvc()
				.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andReturn().getResponse();

		if (mockResponse.getStatus() == 201) {
			countBookingCreated++;
		}
	}
}