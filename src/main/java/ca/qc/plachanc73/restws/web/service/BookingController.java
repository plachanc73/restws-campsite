package ca.qc.plachanc73.restws.web.service;

import org.springframework.http.ResponseEntity;

import ca.qc.plachanc73.restws.web.json.AvailableBookingDatesJson;
import ca.qc.plachanc73.restws.web.json.BookingJson;
import ca.qc.plachanc73.restws.web.json.FilterAvailableBookingDatesJson;

public interface BookingController extends Controller {

	public static final String PATH_BOOKING = PATH_SERVICE + "/booking";

	/**
	 * REST Web Service to get a booking depending on the given id.
	 *
	 * @param idBooking
	 * @return {@link BookingJson}
	 */
	ResponseEntity<BookingJson> getBooking(Long idBooking);

	/**
	 * REST Web Service to create a booking.
	 *
	 * @param bookingJson
	 * @return {@link BookingJson}
	 */
	ResponseEntity<BookingJson> createBooking(BookingJson bookingJson);

	/**
	 * REST Web Service to update a booking.
	 *
	 * @param idBooking
	 * @param bookingJson
	 * @return {@link BookingJson}
	 */
	ResponseEntity<BookingJson> updateBooking(Long idBooking, BookingJson bookingJson);

	/**
	 * REST Web Service to delete a booking.
	 *
	 * @param idBooking
	 * @return {@link BookingJson}
	 */
	ResponseEntity<Void> deleteBooking(Long idBooking);

	/**
	 * REST Web Service to filter available booking dates.
	 *
	 * @param filterAvailableBookingDatesJson
	 * @return {@link FilterAvailableBookingDatesJson}
	 */
	ResponseEntity<AvailableBookingDatesJson> filterAvailableBookingDates(
			FilterAvailableBookingDatesJson filterAvailableBookingDatesJson);
}