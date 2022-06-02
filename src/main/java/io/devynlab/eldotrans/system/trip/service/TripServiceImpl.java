package io.devynlab.eldotrans.system.trip.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.model.Trip;
import io.devynlab.eldotrans.system.trip.repos.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl extends BaseServiceImpl<Trip, Long> implements TripService {

  private final TripRepository tripRepo;

  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void init() {
    tripRepo.setEm(em);
    setEntityClass(Trip.class);
    setGenericDao(tripRepo);
  }

  @Override
  public Trip save(TripDTO tripDTO) {
    ModelMapper mapper = new ModelMapper();
    Trip trip = mapper.map(tripDTO, Trip.class);
    trip.setDescription(tripDTO.getDeparture().toString() + " - " + trip.getArrival().toString());
    return em.merge(trip);
  }

  @Override
  public ObjectListWrapper<Trip> findAllPaginated(Integer page, Integer pageSize, String search) {
    ObjectListWrapper wrapper = new ObjectListWrapper();
    HashMap<String, Object> trips = tripRepo.findAllPaged(page, pageSize, search);
    wrapper.setList((List) trips.get("trips"));
    wrapper.setTotal(Integer.parseInt(trips.get("trip_count").toString()));
    wrapper.setPageSize(pageSize);
    wrapper.setCurrentPage(page);
    return wrapper;
  }

  @Override
  public Trip update(Long tripId, TripDTO tripDTO) {
    return null;
  }
}
