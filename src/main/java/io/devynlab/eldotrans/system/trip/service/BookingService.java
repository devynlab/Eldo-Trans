package io.devynlab.eldotrans.system.trip.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.system.trip.dto.BookingDTO;
import io.devynlab.eldotrans.system.trip.model.Booking;

public interface BookingService extends BaseService<Booking, Long> {
  Booking save(BookingDTO bookingDTO);

  ObjectListWrapper<Booking> findAllPaginated(Integer page, Integer pageSize, String search);

  Booking update(Long bookingId, BookingDTO bookingDTO);
}
