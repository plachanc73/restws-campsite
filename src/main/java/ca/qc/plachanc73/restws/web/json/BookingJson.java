package ca.qc.plachanc73.restws.web.json;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ca.qc.plachanc73.restws.web.validation.check.CheckBookingArrivalDate;
import ca.qc.plachanc73.restws.web.validation.check.CheckBookingDepartureDate;

@CheckBookingArrivalDate
@CheckBookingDepartureDate
public class BookingJson {

	private Long id;

	@NotBlank
	@Size(max = 100)
	private String userFullName;

	@NotBlank
	@Size(max = 100)
	private String emailAddress;

	@NotNull
	private Date arrivalDate;

	@NotNull
	private Date departureDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
}