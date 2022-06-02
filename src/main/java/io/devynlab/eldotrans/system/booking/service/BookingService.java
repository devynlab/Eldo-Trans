package io.devynlab.eldotrans.system.booking.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.system.booking.dto.BookingDTO;
import io.devynlab.eldotrans.system.booking.model.Booking;

public interface BookingService extends BaseService<Booking, Long> {
  Booking save(BookingDTO bookingDTO);

  ObjectListWrapper<Booking> findAllPaginated(Integer page, Integer pageSize, String search);

  Booking update(Long bookingId, BookingDTO bookingDTO);
}
