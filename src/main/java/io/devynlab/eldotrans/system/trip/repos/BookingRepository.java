package io.devynlab.eldotrans.system.trip.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.system.trip.model.Booking;

import java.util.HashMap;

public interface BookingRepository extends GenericRepository<Booking, Long> {
  HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search);
}
