package io.devynlab.eldotrans.system.trip.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.exception.GeneralException;
import io.devynlab.eldotrans.generic.exception.NotFoundException;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.model.Trip;
import io.devynlab.eldotrans.system.trip.repos.TripRepository;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      Trip trip = objectMapper.convertValue(tripDTO, Trip.class);
      Vehicle vehicle = em.find(Vehicle.class, tripDTO.getVehicleId());
      if (vehicle == null) {
        throw new NotFoundException("Vehicle");
      }
      trip.setVehicle(vehicle);
      trip.setNumOfPassengers(vehicle.getNumOfSeats());
      return tripRepo.save(trip);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
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
  public Trip update(Long bookingId, TripDTO tripDTO) {
    return null;
  }

}
