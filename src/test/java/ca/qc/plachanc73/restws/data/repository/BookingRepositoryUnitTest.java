package ca.qc.plachanc73.restws.data.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import ca.qc.plachanc73.restws.data.entity.Booking;

public class BookingRepositoryUnitTest extends AbstractUnitTest {

	@Autowired
	private BookingRepository bookingRepository;

	@Test
	public void testFindAll() {
		List<Booking> booking = (List<Booking>) bookingRepository.findAll();
		assertTrue(booking.size() == 3);
	}

	@Test
	public void testFindOneById() {
		Long idBooking = 1L;
		Optional<Booking> optionalBooking = bookingRepository.findById(idBooking);
		assertTrue(optionalBooking.isPresent());
		assertTrue(optionalBooking.get().getId().equals(idBooking));
	}

	@Test
	public void testUpdate() {
		Optional<Booking> optionalBooking = bookingRepository.findById(1L);
		assertTrue(optionalBooking.isPresent());
		Booking booking = optionalBooking.get();
		booking.setUserFullName("Robinson Hood");
		bookingRepository.save(booking);
		optionalBooking = bookingRepository.findById(1L);
		assertTrue(optionalBooking.get().getUserFullName().equals("Robinson Hood"));
	}

	@Test
	public void testDelete() {
		Optional<Booking> optionalBooking = bookingRepository.findById(2L);
		assertTrue(optionalBooking.isPresent());
		Booking booking = optionalBooking.get();
		bookingRepository.delete(booking);
		optionalBooking = bookingRepository.findById(2L);
		assertTrue(!optionalBooking.isPresent());
	}
}