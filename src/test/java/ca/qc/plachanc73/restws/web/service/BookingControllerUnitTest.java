package ca.qc.plachanc73.restws.web.service;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import ca.qc.plachanc73.restws.util.DateUtil;
import ca.qc.plachanc73.restws.util.JsonUtil;
import ca.qc.plachanc73.restws.web.json.BookingJson;
import ca.qc.plachanc73.restws.web.json.FilterAvailableBookingDatesJson;

public class BookingControllerUnitTest extends AbstractUnitTest {

	@Test
	public void get_booking() throws Exception {
		mockMvc.perform(get("/service/booking/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty());
	}

	@Test
	public void get_booking_notFound() throws Exception {
		mockMvc.perform(get("/service/booking/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void post_create_booking() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-25"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNotEmpty()).andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty());
	}

	@Test
	public void post_create_booking_badRequest_missDates() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_emptyName() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-23"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_blankName() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("     ");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-23"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_emptyEmailAddress() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-23"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_blankEmailAddress() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("        ");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-23"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_arrivalDateUpToOneMonth() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-05-23"));
		booking.setDepartureDate(dateUtil.formatDate("2021-05-25"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_departureDateEqualsToArrivalDate() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-23"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-23"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_departureDateBeforeArrivalDate() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-23"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-22"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void post_create_booking_badRequest_departureDateGreatherThanThreeDays() throws Exception {
		BookingJson booking = new BookingJson();
		booking.setUserFullName("Robin Hood");
		booking.setEmailAddress("robin.hood@test.com");
		DateUtil dateUtil = new DateUtil();
		booking.setArrivalDate(dateUtil.formatDate("2021-04-22"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-26"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(post("/service/booking").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void put_update_booking() throws Exception {
		String result = mockMvc.perform(get("/service/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty())
				.andReturn().getResponse().getContentAsString();

		BookingJson booking = (BookingJson) JsonUtil.deserialiseJson(result, BookingJson.class);
		booking.setUserFullName("Robinson Hood");
		booking.setEmailAddress("robin.hood@update.com");
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(put("/service/booking/1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(booking.getId().intValue()))
				.andExpect(jsonPath("$.userFullName").value("Robinson Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@update.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty());
	}

	@Test
	public void put_update_booking_dateUnavailable() throws Exception {
		String result = mockMvc.perform(get("/service/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty())
				.andReturn().getResponse().getContentAsString();

		DateUtil dateUtil = new DateUtil();
		BookingJson booking = (BookingJson) JsonUtil.deserialiseJson(result, BookingJson.class);
		booking.setUserFullName("Robinson Hood");
		booking.setEmailAddress("robin.hood@update.com");
		booking.setArrivalDate(dateUtil.formatDate("2021-04-27"));
		booking.setDepartureDate(dateUtil.formatDate("2021-04-29"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(put("/service/booking/1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isConflict()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("This date is unavailable : 2021-04-28."));
	}

	@Test
	public void put_update_booking_datesUnavailable() throws Exception {
		String result = mockMvc.perform(get("/service/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty())
				.andReturn().getResponse().getContentAsString();

		DateUtil dateUtil = new DateUtil();
		BookingJson booking = (BookingJson) JsonUtil.deserialiseJson(result, BookingJson.class);
		booking.setUserFullName("Robinson Hood");
		booking.setEmailAddress("robin.hood@update.com");
		booking.setArrivalDate(dateUtil.formatDate("2021-05-02"));
		booking.setDepartureDate(dateUtil.formatDate("2021-05-05"));
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(put("/service/booking/1").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isConflict()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("These dates are unavailable : 2021-05-03, 2021-05-04."));
	}

	@Test
	public void put_update_booking_notFound() throws Exception {
		String result = mockMvc.perform(get("/service/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.userFullName").value("Robin Hood"))
				.andExpect(jsonPath("$.emailAddress").value("robin.hood@test.com"))
				.andExpect(jsonPath("$.arrivalDate").isNotEmpty()).andExpect(jsonPath("$.departureDate").isNotEmpty())
				.andReturn().getResponse().getContentAsString();

		BookingJson booking = (BookingJson) JsonUtil.deserialiseJson(result, BookingJson.class);
		booking.setEmailAddress("robin.hood@update.com");
		String bookingJson = JsonUtil.serialiseObject(booking);

		mockMvc.perform(put("/service/booking/0").contentType(MediaType.APPLICATION_JSON).content(bookingJson))
				.andExpect(status().isNotFound());
	}

	@Test
	public void delete_booking() throws Exception {
		mockMvc.perform(delete("/service/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void delete_booking_notFound() throws Exception {
		mockMvc.perform(get("/service/booking/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void post_filter_availableBookingDates_defaultDateRange() throws Exception {
		FilterAvailableBookingDatesJson filterAvailableBookingDates = new FilterAvailableBookingDatesJson();
		String filterAvailableBookingDatesJson = JsonUtil.serialiseObject(filterAvailableBookingDates);

		mockMvc.perform(post("/service/booking/filter/availableDates").contentType(MediaType.APPLICATION_JSON)
				.content(filterAvailableBookingDatesJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.availableDates", hasSize(24)));
	}

	@Test
	public void post_filter_availableBookingDates_withDateRange() throws Exception {
		FilterAvailableBookingDatesJson filterAvailableBookingDates = new FilterAvailableBookingDatesJson();
		DateUtil dateUtil = new DateUtil();
		filterAvailableBookingDates.setFrom(dateUtil.formatDate("2021-04-23"));
		filterAvailableBookingDates.setTo(dateUtil.formatDate("2021-05-06"));
		String filterAvailableBookingDatesJson = JsonUtil.serialiseObject(filterAvailableBookingDates);

		mockMvc.perform(post("/service/booking/filter/availableDates").contentType(MediaType.APPLICATION_JSON)
				.content(filterAvailableBookingDatesJson)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.availableDates", hasSize(8)));
	}
}