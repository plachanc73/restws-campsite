package ca.qc.plachanc73.restws.data.mapper;

import org.mapstruct.Mapper;

import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.web.json.BookingJson;

@Mapper(componentModel = "spring", uses = { CommonEntityMapper.class })
public abstract class BookingMapper {

	// Map JSON to Entity

	public abstract Booking bookingJsonToBooking(BookingJson bookingJson);

	// Map Entity to JSON

	public abstract BookingJson bookingToBookingJson(Booking booking);
}