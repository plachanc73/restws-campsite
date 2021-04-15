package ca.qc.plachanc73.restws.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.qc.plachanc73.restws.data.dao.BookingDao;
import ca.qc.plachanc73.restws.data.dao.ReservedDateDao;
import ca.qc.plachanc73.restws.data.entity.Booking;
import ca.qc.plachanc73.restws.data.entity.ReservedDate;
import ca.qc.plachanc73.restws.exception.ConflictServiceException;
import ca.qc.plachanc73.restws.exception.PreconditionFailedServiceException;
import ca.qc.plachanc73.restws.util.DateUtil;

@Service
public class BookingRestBusinessService implements BookingBusinessService {

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private ReservedDateDao reservedDateDao;

	@Override
	public Optional<Booking> getBookingById(Long idBooking) {
		return bookingDao.getBookingById(idBooking);
	}

	@Override
	public synchronized Booking verifyAndSave(Booking booking) {
		verifyDatesAvailability(booking);

		return save(booking);
	}

	@Override
	@Transactional
	public void delete(Booking booking) {
		bookingDao.delete(booking);
	}

	@Override
	public List<Date> getAvailableDates(Date from, Date to) {
		DateUtil dateUtil = new DateUtil();
		Date today = dateUtil.getToday();
		Date tomorrow = dateUtil.getTomorrow();
		Date dateToMax = DateUtil.addMonths(today, 1);

		if (from == null) {
			from = tomorrow;
		} else if (from.before(tomorrow)) {
			throw new PreconditionFailedServiceException(String
					.format("The arrival date %s has to be equals or after tomorrow.", dateUtil.formatDate(from)));
		}

		if (to == null) {
			to = dateToMax;
		} else if (to.after(dateToMax)) {
			throw new PreconditionFailedServiceException(
					String.format("The departure date %s has to be in the next month.", dateUtil.formatDate(to)));
		}

		return reservedDateDao.getAvailableDates(from, to);
	}

	/**
	 * Verify Booking Dates Availability.
	 *
	 * @param booking
	 * @throws ConflictServiceException
	 */
	private void verifyDatesAvailability(Booking booking) {
		Date dateToVerify = (Date) booking.getArrivalDate().clone();
		List<Date> unavailableDates = new ArrayList<>();

		// Retrieve unavailable dates
		while (dateToVerify.before(booking.getDepartureDate())) {
			if (!reservedDateDao.isDateAvailableExcludingGivenBooking(dateToVerify, booking)) {
				unavailableDates.add(dateToVerify);
			}

			dateToVerify = DateUtil.addDays(dateToVerify, 1);
		}

		if (!unavailableDates.isEmpty()) {
			String unavailableDateMessage;
			DateUtil dateUtil = new DateUtil();

			if (unavailableDates.size() == 1) {
				unavailableDateMessage = String.format("This date is unavailable : %s.",
						dateUtil.formatDate(unavailableDates.get(0)));
			} else {
				StringBuilder stbUnavailableDates = new StringBuilder();
				Iterator<Date> itrUnavailableDates = unavailableDates.iterator();
				stbUnavailableDates.append(dateUtil.formatDate(itrUnavailableDates.next()));

				while (itrUnavailableDates.hasNext()) {
					stbUnavailableDates.append(", ");
					stbUnavailableDates.append(dateUtil.formatDate(itrUnavailableDates.next()));
				}

				unavailableDateMessage = String.format("These dates are unavailable : %s.",
						stbUnavailableDates.toString());
			}

			throw new ConflictServiceException(unavailableDateMessage.toString());
		}
	}

	@Transactional
	private Booking save(Booking booking) {
		Booking bookingSaved = bookingDao.save(booking);

		Date dateToReserve = (Date) booking.getArrivalDate().clone();
		ReservedDate newReservedDate;

		while (dateToReserve.before(booking.getDepartureDate())) {
			newReservedDate = new ReservedDate();
			newReservedDate.setDate(dateToReserve);
			newReservedDate.setBooking(bookingSaved);
			reservedDateDao.save(newReservedDate);
			dateToReserve = DateUtil.addDays(dateToReserve, 1);
		}

		return bookingSaved;
	}
}