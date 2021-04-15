package ca.qc.plachanc73.restws.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ca.qc.plachanc73.restws.data.entity.ReservedDate;

@Repository
public interface ReservedDateRepository extends CrudRepository<ReservedDate, Long> {

	Optional<ReservedDate> findOneByDate(Date date);

	@Query("SELECT r FROM ReservedDate r WHERE r.date >= :dateFrom AND r.date <= :dateTo ORDER BY date ASC")
	List<ReservedDate> findAllByDateRange(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

	@Transactional
	@Modifying
	@Query("DELETE FROM ReservedDate r WHERE r.booking.id = :idBooking")
	void deleteByIdBooking(Long idBooking);
}