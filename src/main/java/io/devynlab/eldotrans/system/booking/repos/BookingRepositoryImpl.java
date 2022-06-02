package io.devynlab.eldotrans.system.booking.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.system.booking.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class BookingRepositoryImpl extends GenericRepositoryImpl<Booking, Long> implements BookingRepository {

  @Override
  public HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search) {
    Query query = getEm().createQuery("SELECT b FROM Booking b WHERE b.deleted =: deletedStatus " + (search != null ? "AND b.description LIKE :search" : ""), Booking.class);
    query.setParameter("deletedStatus", false);
    if (search != null) {
      query.setParameter("search", "%" + search + "%");
    }
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<Booking> bookings = query.getResultList();
    Query countQuery = getEm().createQuery("SELECT count(t) FROM Booking t WHERE t.deleted =: deletedStatus " + (search != null ? "AND t.description LIKE :search" : ""), Long.class);
    countQuery.setParameter("deletedStatus", false);
    if (search != null) {
      countQuery.setParameter("search", "%" + search + "%");
    }
    Long bookingCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("bookings", bookings);
    map.put("booking_count", bookingCount);
    return map;
  }

}
