package io.devynlab.eldotrans.system.booking.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.system.booking.model.Booking;

import java.util.HashMap;

public interface BookingRepository extends GenericRepository<Booking, Long> {
  HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search);
}
