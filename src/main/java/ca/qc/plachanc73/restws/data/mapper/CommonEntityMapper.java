package ca.qc.plachanc73.restws.data.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.repository.BookingRepository;
import ca.qc.plachanc73.restws.exception.MapperException;

@Mapper(componentModel = "spring")
public class CommonEntityMapper extends EntityMapper {

	@Autowired
	protected BookingRepository bookingRepository;

	public Booking toBooking(Long idBooking) {
		if (idBooking == null) {
			return null;
		}

		Optional<Booking> optionalBooking = bookingRepository.findById(idBooking);
		if (!optionalBooking.isPresent()) {
			throw new MapperException("The booking with the id " + idBooking + DOESNT_EXIST);
		}
		return optionalBooking.get();
	}
}