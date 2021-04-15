package ca.qc.plachanc73.restws.web.service;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.qc.plachanc73.restws.business.service.BookingBusinessService;
import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.mapper.BookingMapper;
import ca.qc.plachanc73.restws.exception.ConflictServiceException;
import ca.qc.plachanc73.restws.web.json.AvailableBookingDatesJson;
import ca.qc.plachanc73.restws.web.json.BookingJson;
import ca.qc.plachanc73.restws.web.json.FilterAvailableBookingDatesJson;

@RestController
@RequestMapping(value = BookingController.PATH_BOOKING, produces = "application/json")
public class BookingRestController extends AbstractRestController implements BookingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookingRestController.class);

	private static final String PATH_ID_BOOKING = "/{idBooking}";

	private static final String PATH_FILTER_AVAILABLE_DATES = "/filter/availableDates";

	private static final String BOOKING_WITH_ID = "Booking with id ";

	@Autowired
	private BookingBusinessService bookingBusinessService;

	@Autowired
	private BookingMapper bookingMapper;

	@Override
	@GetMapping(value = PATH_ID_BOOKING)
	public ResponseEntity<BookingJson> getBooking(@PathVariable Long idBooking) {
		Optional<Booking> optionalBooking = bookingBusinessService.getBookingById(idBooking);
		if (!optionalBooking.isPresent()) {
			LOGGER.error(BOOKING_WITH_ID + idBooking + TEXT_NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		BookingJson bookingJson = bookingMapper.bookingToBookingJson(optionalBooking.get());

		return new ResponseEntity<>(bookingJson, HttpStatus.OK);
	}

	@Override
	@PostMapping()
	public ResponseEntity<BookingJson> createBooking(@Valid @RequestBody BookingJson bookingJson) {
		Booking booking = bookingMapper.bookingJsonToBooking(bookingJson);
		Booking bookingSaved;

		try {
			bookingSaved = bookingBusinessService.verifyAndSave(booking);
		} catch (ConflictServiceException cse) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		BookingJson bookingSavedJson = bookingMapper.bookingToBookingJson(bookingSaved);

		return new ResponseEntity<>(bookingSavedJson, HttpStatus.CREATED);
	}

	@Override
	@PutMapping(value = PATH_ID_BOOKING)
	public ResponseEntity<BookingJson> updateBooking(@PathVariable Long idBooking,
			@Valid @RequestBody BookingJson bookingJson) {
		Optional<Booking> optionalBooking = bookingBusinessService.getBookingById(idBooking);
		if (!optionalBooking.isPresent()) {
			LOGGER.error(BOOKING_WITH_ID + idBooking + TEXT_NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Booking booking = bookingMapper.bookingJsonToBooking(bookingJson);
		booking.setId(idBooking);
		Booking bookingUpdated = bookingBusinessService.verifyAndSave(booking);
		BookingJson bookingSavedJson = bookingMapper.bookingToBookingJson(bookingUpdated);

		return new ResponseEntity<>(bookingSavedJson, HttpStatus.OK);
	}

	@Override
	@DeleteMapping(value = PATH_ID_BOOKING)
	public ResponseEntity<Void> deleteBooking(@PathVariable Long idBooking) {
		Optional<Booking> optionalBooking = bookingBusinessService.getBookingById(idBooking);
		if (!optionalBooking.isPresent()) {
			LOGGER.error(BOOKING_WITH_ID + idBooking + TEXT_NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		bookingBusinessService.delete(optionalBooking.get());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	@PostMapping(value = PATH_FILTER_AVAILABLE_DATES)
	public ResponseEntity<AvailableBookingDatesJson> filterAvailableBookingDates(
			@RequestBody FilterAvailableBookingDatesJson bookingRequestAvailableDatesJson) {
		AvailableBookingDatesJson availableBookingDatesJson = new AvailableBookingDatesJson();
		availableBookingDatesJson.setAvailableDates(bookingBusinessService.getAvailableDates(
				bookingRequestAvailableDatesJson.getFrom(), bookingRequestAvailableDatesJson.getTo()));

		return new ResponseEntity<>(availableBookingDatesJson, HttpStatus.OK);
	}
}