package ca.qc.plachanc73.restws.data.dao;

import java.util.Optional;

import ca.qc.plachanc73.restws.data.entity.Booking;

public interface BookingDao {

	Optional<Booking> getBookingById(Long idBooking);

	Booking save(Booking booking);

	void delete(Booking booking);
}