package io.devynlab.eldotrans.system.trip.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.system.trip.dto.BookingDTO;
import io.devynlab.eldotrans.system.trip.model.Booking;
import io.devynlab.eldotrans.system.trip.repos.BookingRepository;
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
public class BookingServiceImpl extends BaseServiceImpl<Booking, Long> implements BookingService {

  private final BookingRepository tripRepo;

  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void init() {
    tripRepo.setEm(em);
    setEntityClass(Booking.class);
    setGenericDao(tripRepo);
  }

  @Override
  public Booking save(BookingDTO bookingDTO) {
    ModelMapper mapper = new ModelMapper();
    Booking booking = mapper.map(bookingDTO, Booking.class);
    booking.setDescription(bookingDTO.getDeparture().toString() + " - " + bookingDTO.getArrival().toString());
    return em.merge(booking);
  }

  @Override
  public ObjectListWrapper<Booking> findAllPaginated(Integer page, Integer pageSize, String search) {
    ObjectListWrapper wrapper = new ObjectListWrapper();
    HashMap<String, Object> trips = tripRepo.findAllPaged(page, pageSize, search);
    wrapper.setList((List) trips.get("bookings"));
    wrapper.setTotal(Integer.parseInt(trips.get("booking_count").toString()));
    wrapper.setPageSize(pageSize);
    wrapper.setCurrentPage(page);
    return wrapper;
  }

  @Override
  public Booking update(Long tripId, BookingDTO bookingDTO) {
    return null;
  }
}
