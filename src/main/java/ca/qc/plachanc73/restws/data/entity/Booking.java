package ca.qc.plachanc73.restws.data.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKING")
public class Booking extends AbstractEntity {

	private static final long serialVersionUID = -3974740488221164274L;

	@Id
	@SequenceGenerator(name = "SEQUENCE", sequenceName = "BOOKING_SEQ", allocationSize = 1, initialValue = 10)
	@GeneratedValue(generator = "SEQUENCE")
	@Column(name = "ID_BOOKING", unique = true)
	private Long id;

	@Column(name = "USER_FULLNAME", nullable = false, length = 100)
	private String userFullName;

	@Column(name = "EMAIL_ADDRESS", nullable = false, length = 100)
	private String emailAddress;

	@Column(name = "ARRIVAL_DATE", nullable = false)
	private Date arrivalDate;

	@Column(name = "DEPARTURE_DATE", nullable = false)
	private Date departureDate;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ReservedDate> reservedDates = new ArrayList<>();

	@Override
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