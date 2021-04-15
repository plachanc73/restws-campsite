package ca.qc.plachanc73.restws.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

	public DateUtil() {
		// No initialization
	}

	/**
	 * Return the formatted date from the given date.
	 *
	 * @param date
	 * @return Date
	 */
	public String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * Return the date from the given formatted date.
	 *
	 * @param formattedDate
	 * @return Date
	 * @throws ParseException
	 */
	public Date formatDate(String formattedDate) throws ParseException {
		return dateFormat.parse(formattedDate);
	}

	/**
	 * Add a number of days to the given date.
	 *
	 * @param date
	 * @param numDays
	 * @return Date
	 */
	public static Date addDays(Date date, int numDays) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, numDays);
		return calendar.getTime();
	}

	/**
	 * Add a number of months to the given date.
	 *
	 * @param date
	 * @param numMonths
	 * @return Date
	 */
	public static Date addMonths(Date date, int numMonths) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, numMonths);
		return calendar.getTime();
	}

	/**
	 * Clean date by keeping Year, Month and Day.
	 *
	 * @param date
	 * @return Date
	 */
	public Date cleanDate(Date date) {
		try {
			return formatDate(formatDate(date));
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * Returns today. The check-in hour is noon, so from noon, we add 1 day.
	 *
	 * @return Date
	 */
	public Date getToday() {
		Date today = cleanDate(new Date());
		Calendar calToday = new GregorianCalendar();
		if (calToday.get(Calendar.HOUR_OF_DAY) >= 12) {
			today = addDays(today, 1);
		}

		return today;
	}

	/**
	 * Returns tomorrow based on campsite rules. The campsite can be reserved
	 * minimum 1 day(s) ahead of arrival. The check-in hour is noon, so from noon,
	 * tomorrow is in 2 days.
	 *
	 * @return Date
	 */
	public Date getTomorrow() {
		return addDays(getToday(), 1);
	}
}