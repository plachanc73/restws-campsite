package ca.qc.plachanc73.restws.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ca.qc.plachanc73.restws.config.AbstractUnitTest;
import ca.qc.plachanc73.restws.data.entity.ReservedDate;
import ca.qc.plachanc73.restws.util.DateUtil;

public class ReservedDateRepositoryUnitTest extends AbstractUnitTest {

	@Autowired
	private ReservedDateRepository reservedDateRepository;

	@Test
	public void testFindAllByDateRange() {
		Date today = new Date();
		Date tomorrow = DateUtil.addDays(today, 1);
		Date dateTo = DateUtil.addMonths(today, 1);
		List<ReservedDate> reservedDatesInDateRange = reservedDateRepository.findAllByDateRange(tomorrow, dateTo);
		assertEquals(reservedDatesInDateRange.size(), 6);
	}

	@Test
	public void testDeleteByIdBooking() {
		Long idBooking = 1L;
		long countBeforeDelete = reservedDateRepository.count();
		reservedDateRepository.deleteByIdBooking(idBooking);
		long countAfterDelete = reservedDateRepository.count();
		assertNotEquals(countBeforeDelete, countAfterDelete);
	}
}