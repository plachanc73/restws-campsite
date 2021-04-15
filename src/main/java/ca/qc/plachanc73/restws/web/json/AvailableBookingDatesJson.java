package ca.qc.plachanc73.restws.web.json;

import java.util.Date;
import java.util.List;

public class AvailableBookingDatesJson {

	private List<Date> availableDates;

	public List<Date> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(List<Date> availableDates) {
		this.availableDates = availableDates;
	}
}