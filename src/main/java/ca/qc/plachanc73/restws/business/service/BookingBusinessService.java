package ca.qc.plachanc73.restws.business.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ca.qc.plachanc73.restws.data.entity.Booking;

public interface BookingBusinessService {

	Optional<Booking> getBookingById(Long idBooking);

	Booking verifyAndSave(Booking booking);

	void delete(Booking booking);

	List<Date> getAvailableDates(Date from, Date to);
}