package io.devynlab.eldotrans.system.trip.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.model.Trip;

public interface TripService extends BaseService<Trip, Long> {
  Trip save(TripDTO tripDTO);

  ObjectListWrapper<Trip> findAllPaginated(Integer page, Integer pageSize, String search);

  Trip update(Long tripId, TripDTO tripDTO);
}
