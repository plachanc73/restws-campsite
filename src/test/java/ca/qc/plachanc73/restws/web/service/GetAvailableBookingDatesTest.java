package ca.qc.plachanc73.restws.web.service;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import ca.qc.plachanc73.restws.util.DateUtil;
import ca.qc.plachanc73.restws.util.JsonUtil;
import ca.qc.plachanc73.restws.web.json.FilterAvailableBookingDatesJson;
import junit.framework.Test;
import junit.framework.TestResult;

public class GetAvailableBookingDatesTest extends AbstractUnitTest implements Test {

	private MockMvc mockMvc;

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public MockMvc getMockMvc() {
		return this.mockMvc == null ? super.mockMvc : this.mockMvc;
	}

	@Override
	public int countTestCases() {
		return 2;
	}

	@Override
	public void run(TestResult result) {
		try {
			post_filter_availableBookingDates_defaultDateRange();
			post_filter_availableBookingDates_withDateRange();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void post_filter_availableBookingDates_defaultDateRange() throws Exception {
		FilterAvailableBookingDatesJson filterAvailableBookingDates = new FilterAvailableBookingDatesJson();
		String filterAvailableBookingDatesJson = JsonUtil.serialiseObject(filterAvailableBookingDates);

		getMockMvc()
				.perform(post("/service/booking/filter/availableDates").contentType(MediaType.APPLICATION_JSON)
						.content(filterAvailableBookingDatesJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.availableDates", hasSize(24)));
	}

	@org.junit.Test
	public void post_filter_availableBookingDates_withDateRange() throws Exception {
		FilterAvailableBookingDatesJson filterAvailableBookingDates = new FilterAvailableBookingDatesJson();
		DateUtil dateUtil = new DateUtil();
		filterAvailableBookingDates.setFrom(dateUtil.formatDate("2021-04-23"));
		filterAvailableBookingDates.setTo(dateUtil.formatDate("2021-05-06"));
		String filterAvailableBookingDatesJson = JsonUtil.serialiseObject(filterAvailableBookingDates);

		getMockMvc()
				.perform(post("/service/booking/filter/availableDates").contentType(MediaType.APPLICATION_JSON)
						.content(filterAvailableBookingDatesJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.availableDates", hasSize(8)));
	}
}