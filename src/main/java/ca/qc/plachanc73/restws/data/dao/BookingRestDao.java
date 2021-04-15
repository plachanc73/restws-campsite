package ca.qc.plachanc73.restws.data.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.repository.BookingRepository;

@Service
public class BookingRestDao implements BookingDao {

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public Optional<Booking> getBookingById(Long idBooking) {
		return bookingRepository.findById(idBooking);
	}

	@Override
	public Booking save(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public void delete(Booking booking) {
		bookingRepository.delete(booking);
	}
}