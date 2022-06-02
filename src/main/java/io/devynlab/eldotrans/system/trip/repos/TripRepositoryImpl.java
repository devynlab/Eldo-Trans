package io.devynlab.eldotrans.system.trip.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.system.trip.model.Trip;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class TripRepositoryImpl extends GenericRepositoryImpl<Trip, Long> implements TripRepository {

  @Override
  public HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search) {
    Query query = getEm().createQuery("SELECT t FROM Trip t WHERE t.deleted =: deletedStatus " + (search != null ? "AND t.description LIKE :search" : ""), Trip.class);
    query.setParameter("deletedStatus", false);
    if (search != null) {
      query.setParameter("search", "%" + search + "%");
    }
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<Trip> trips = query.getResultList();
    Query countQuery = getEm().createQuery("SELECT count(t) FROM Trip t WHERE t.deleted =: deletedStatus " + (search != null ? "AND t.description LIKE :search" : ""), Long.class);
    countQuery.setParameter("deletedStatus", false);
    if (search != null) {
      countQuery.setParameter("search", "%" + search + "%");
    }
    Long tripCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("trips", trips);
    map.put("trip_count", tripCount);
    return map;
  }

}
