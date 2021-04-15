package ca.qc.plachanc73.restws.data.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.entity.ReservedDate;
import ca.qc.plachanc73.restws.data.repository.ReservedDateRepository;
import ca.qc.plachanc73.restws.util.DateUtil;

@Service
public class ReservedDateRestDao implements ReservedDateDao {

	@Autowired
	private ReservedDateRepository reservedDateRepository;

	@Override
	public ReservedDate save(ReservedDate reservedDate) {
		return reservedDateRepository.save(reservedDate);
	}

	@Override
	public void deleteForBooking(Booking booking) {
		reservedDateRepository.deleteByIdBooking(booking.getId());
	}

	@Override
	public boolean isDateAvailableExcludingGivenBooking(Date date, Booking booking) {
		Optional<ReservedDate> reservedDateToCheck = reservedDateRepository.findOneByDate(date);

		return reservedDateToCheck.isEmpty()
				|| (reservedDateToCheck.isPresent() && reservedDateToCheck.get().getBooking().equals(booking));
	}

	@Override
	public List<Date> getAvailableDates(Date dateFrom, Date dateTo) {
		List<Date> availableDates = new ArrayList<>();
		List<ReservedDate> reservedDates = reservedDateRepository.findAllByDateRange(dateFrom, dateTo);
		Date dateToCompare = (Date) dateFrom.clone();

		for (ReservedDate reservedDate : reservedDates) {
			while (reservedDate.getDate().after(dateToCompare)) {
				availableDates.add(dateToCompare);
				dateToCompare = DateUtil.addDays(dateToCompare, 1);
			}
			dateToCompare = DateUtil.addDays(dateToCompare, 1);
		}

		while (dateTo.after(dateToCompare) || dateTo.equals(dateToCompare)) {
			availableDates.add(dateToCompare);
			dateToCompare = DateUtil.addDays(dateToCompare, 1);
		}

		return availableDates;
	}
}