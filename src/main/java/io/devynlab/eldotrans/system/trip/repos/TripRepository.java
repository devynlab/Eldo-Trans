package io.devynlab.eldotrans.system.trip.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.trip.model.Trip;

import java.util.Date;
import java.util.HashMap;

public interface TripRepository extends GenericRepository<Trip, Long> {
  HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search);

  HashMap<String, Object> findAllFiltered(Integer page, Integer pageSize, Destinations tripFrom, Destinations tripTo, Date date);
}
