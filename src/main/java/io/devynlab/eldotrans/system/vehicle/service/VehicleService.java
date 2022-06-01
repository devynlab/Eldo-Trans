package io.devynlab.eldotrans.system.vehicle.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.system.vehicle.dto.VehicleDTO;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;

public interface VehicleService extends BaseService<Vehicle, Long> {
  Vehicle save(VehicleDTO vehicleDTO);

  ObjectListWrapper<Vehicle> findAllPaginated(Integer page, Integer pageSize, String search);

  Vehicle update(Long vehicleId, VehicleDTO vehicleDTO);
}
