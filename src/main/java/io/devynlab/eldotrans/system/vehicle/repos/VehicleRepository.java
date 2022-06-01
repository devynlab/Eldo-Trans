package io.devynlab.eldotrans.system.vehicle.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;

import java.util.HashMap;

public interface VehicleRepository extends GenericRepository<Vehicle, Long> {
  Vehicle findByNumPlate(String numPlate);

  HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search);
}
