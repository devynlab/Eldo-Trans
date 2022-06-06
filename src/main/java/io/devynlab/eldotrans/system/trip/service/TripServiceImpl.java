package io.devynlab.eldotrans.system.trip.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devynlab.eldotrans.auth.service.AuthService;
import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.exception.BadRequestException;
import io.devynlab.eldotrans.generic.exception.GeneralException;
import io.devynlab.eldotrans.generic.exception.NotFoundException;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.system.trip.dto.BookingDTO;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.trip.model.Trip;
import io.devynlab.eldotrans.system.trip.model.TripHistory;
import io.devynlab.eldotrans.system.trip.repos.TripRepository;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import io.devynlab.eldotrans.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl extends BaseServiceImpl<Trip, Long> implements TripService {

  private final TripRepository tripRepo;
  private final AuthService authService;

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
    User onlineUser = authService.loggedUser();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      Trip trip = objectMapper.convertValue(tripDTO, Trip.class);
      if (onlineUser.getVehicle() != null && tripDTO.getVehicle() == null) {
        trip.setVehicle(onlineUser.getVehicle());
        trip.setNumOfPassengers(onlineUser.getVehicle().getNumOfSeats());
      } else if (onlineUser.getVehicle() == null && tripDTO.getVehicleId() == null) {
        throw new BadRequestException("Vehicle needed to successfully create a trip");
      } else {
        Vehicle vehicle = em.find(Vehicle.class, tripDTO.getVehicleId());
        if (vehicle == null) {
          throw new NotFoundException("Vehicle");
        }
        trip.setVehicle(vehicle);
        trip.setNumOfPassengers(vehicle.getNumOfSeats());
        trip.setRemainingSeats(vehicle.getNumOfSeats());
      }
      trip = em.merge(trip);
      TripHistory tripHistory = new TripHistory();
      tripHistory.setCreatedAt(new Date());
      tripHistory.setTripId(trip.getId());
      tripHistory.setRemainingSeats(trip.getNumOfPassengers());
      tripHistory.setComment("New trip created by " + onlineUser.getUsername());
      em.merge(tripHistory);
      return trip;
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
  public ObjectListWrapper<Trip> findAllFiltered(Integer page, Integer pageSize, Destinations tripFrom, Destinations tripTo, Date date) {
    ObjectListWrapper wrapper = new ObjectListWrapper();
    HashMap<String, Object> trips = tripRepo.findAllFiltered(page, pageSize, tripFrom, tripTo, date);
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

  @Override
  public Trip booking(Long tripId, BookingDTO bookingDTO) {
    if (bookingDTO.getName() == null || bookingDTO.getPhoneNumber() == null || bookingDTO.getNumOfSeats() == null)
      throw new BadRequestException("Please provide all the required information");
    Trip trip = em.find(Trip.class, tripId);
    if (trip == null)
      throw new NotFoundException("Trip");
    if (!trip.isAvailable())
      throw new BadRequestException("Trip no longer available");
    if (trip.getRemainingSeats() < 1)
      throw new BadRequestException("Trip already full");
    if (trip.getRemainingSeats() < bookingDTO.getNumOfSeats())
      throw new BadRequestException("Trip available seats are less than what you desire. " + trip.getRemainingSeats() + " seats remain");
    if (trip.getDepartedAt() != null)
      throw new BadRequestException("You can't book this trip as it has already departed. Wait for the next trip available");
    if (trip.getArrivedAt() != null)
      throw new BadRequestException("You can't book this trip as it has already arrived. Wait for the next trip available");
    trip.setRemainingSeats(trip.getRemainingSeats() - bookingDTO.getNumOfSeats());
    trip = em.merge(trip);
    TripHistory tripHistory = new TripHistory();
    tripHistory.setCreatedAt(new Date());
    tripHistory.setTripId(trip.getId());
    tripHistory.setRemainingSeats(trip.getRemainingSeats());
    tripHistory.setComment(bookingDTO.getName() + "(" + bookingDTO.getPhoneNumber() + ") booked " + bookingDTO.getNumOfSeats() + " seats at " + tripHistory.getCreatedAt());
    em.merge(tripHistory);
    return trip;
  }

  @Override
  public Trip departure(Long tripId) {
    Trip trip = em.find(Trip.class, tripId);
    if (trip == null)
      throw new NotFoundException("Trip");
    if (trip.getDepartedAt() != null)
      throw new BadRequestException("Trip already set as departed");
    trip.setDepartedAt(new Date());
    trip.setAvailable(false);
    trip = em.merge(trip);
    TripHistory tripHistory = new TripHistory();
    tripHistory.setCreatedAt(new Date());
    tripHistory.setTripId(trip.getId());
    tripHistory.setComment("Trip departure time at " + trip.getDepartedAt());
    em.merge(tripHistory);
    return trip;
  }

  @Override
  public Trip arrival(Long tripId) {
    Trip trip = em.find(Trip.class, tripId);
    if (trip == null)
      throw new NotFoundException("Trip");
    if (trip.getDepartedAt() == null)
      throw new BadRequestException("Logically, you have to first depart before you can arrive at your destination");
    if (trip.getArrivedAt() != null)
      throw new BadRequestException("Trip already set as arrived");
    trip.setArrivedAt(new Date());
    trip.setActive(false);
    trip = em.merge(trip);
    TripHistory tripHistory = new TripHistory();
    tripHistory.setCreatedAt(new Date());
    tripHistory.setTripId(trip.getId());
    tripHistory.setTrip(trip);
    tripHistory.setComment("Trip arrival time at " + trip.getArrivedAt());
    em.merge(tripHistory);
    return trip;
  }

}
