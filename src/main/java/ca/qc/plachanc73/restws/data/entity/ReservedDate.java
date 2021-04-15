package ca.qc.plachanc73.restws.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RESERVED_DATE")
public class ReservedDate extends AbstractEntity {

	private static final long serialVersionUID = -3451772967035245410L;

	@Id
	@SequenceGenerator(name = "SEQUENCE", sequenceName = "RESERVED_DATE_SEQ", allocationSize = 1, initialValue = 10)
	@GeneratedValue(generator = "SEQUENCE")
	@Column(name = "ID_RESERVED_DATE", unique = true)
	private Long id;

	@Column(name = "DATE", nullable = false)
	private Date date;

	@OneToOne
	@JoinColumn(name = "ID_BOOKING")
	private Booking booking;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}