package ca.qc.plachanc73.restws.web.json;

import java.util.Date;

public class FilterAvailableBookingDatesJson {

	private Date from;

	private Date to;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}