package ca.qc.plachanc73.restws.data.dao;

import java.util.Date;
import java.util.List;

import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.entity.ReservedDate;

public interface ReservedDateDao {

	ReservedDate save(ReservedDate reservedDate);

	void deleteForBooking(Booking booking);

	boolean isDateAvailableExcludingGivenBooking(Date date, Booking booking);

	List<Date> getAvailableDates(Date dateFrom, Date dateTo);
}