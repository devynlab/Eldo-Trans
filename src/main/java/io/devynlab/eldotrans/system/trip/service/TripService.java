package io.devynlab.eldotrans.system.trip.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.system.trip.dto.TripDTO;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.trip.model.Trip;

import java.util.Date;

public interface TripService extends BaseService<Trip, Long> {
  Trip save(TripDTO tripDTO);

  ObjectListWrapper<Trip> findAllPaginated(Integer page, Integer pageSize, String search);

  ObjectListWrapper<Trip> findAllFiltered(Integer page, Integer pageSize, Destinations tripFrom, Destinations tripTo, Date date);

  Trip update(Long tripId, TripDTO tripDTO);

  Trip departure(Long tripId);

  Trip arrival(Long tripId);
}
