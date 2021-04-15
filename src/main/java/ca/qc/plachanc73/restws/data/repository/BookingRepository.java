package ca.qc.plachanc73.restws.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.qc.plachanc73.restws.data.entity.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

}