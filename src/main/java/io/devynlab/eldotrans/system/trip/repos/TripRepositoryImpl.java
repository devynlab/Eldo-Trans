package io.devynlab.eldotrans.system.trip.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.trip.model.Trip;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class TripRepositoryImpl extends GenericRepositoryImpl<Trip, Long> implements TripRepository {

  @Override
  public HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search) {
    Query query = getEm().createQuery("SELECT t FROM Trip t WHERE t.deleted =: deletedStatus " + (search != null ? "AND t.date.toString() LIKE :search" : ""), Trip.class);
    query.setParameter("deletedStatus", false);
    if (search != null) {
      query.setParameter("search", "%" + search + "%");
    }
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<Trip> trips = query.getResultList();
    Query countQuery = getEm().createQuery("SELECT count(t) FROM Trip t WHERE t.deleted =: deletedStatus " + (search != null ? "AND t.date.toString() LIKE :search" : ""), Long.class);
    countQuery.setParameter("deletedStatus", false);
    if (search != null) {
      countQuery.setParameter("search", "%" + search + "%");
    }
    Long bookingCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("trips", trips);
    map.put("trip_count", bookingCount);
    return map;
  }

  @Override
  public HashMap<String, Object> findAllFiltered(Integer page, Integer pageSize, Destinations tripFrom, Destinations tripTo, Date date) {
    Query query = getEm().createQuery(
        "SELECT t FROM Trip t WHERE t.deleted =: deletedStatus AND t.tripFrom =:tripFrom AND t.tripTo =:tripTo AND t.date =:date ORDER BY t.id DESC", Trip.class
    );
    query.setParameter("deletedStatus", false);
    query.setParameter("tripFrom", tripFrom);
    query.setParameter("tripTo", tripTo);
    query.setParameter("date", date);
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<Trip> trips = query.getResultList();
    Query countQuery = getEm().createQuery(
        "SELECT count(t) FROM Trip t WHERE t.deleted =: deletedStatus AND t.tripFrom =:tripFrom AND t.tripTo =:tripTo AND t.date =:date", Long.class
    );
    countQuery.setParameter("deletedStatus", false);
    countQuery.setParameter("tripFrom", tripFrom);
    countQuery.setParameter("tripTo", tripTo);
    countQuery.setParameter("date", date);
    Long bookingCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("trips", trips);
    map.put("trip_count", bookingCount);
    return map;
  }

}
