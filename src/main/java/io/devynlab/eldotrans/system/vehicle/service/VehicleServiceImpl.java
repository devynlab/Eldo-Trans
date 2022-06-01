package io.devynlab.eldotrans.system.vehicle.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.exception.ResourceExistsException;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.system.vehicle.dto.VehicleDTO;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import io.devynlab.eldotrans.system.vehicle.repos.VehicleRepository;
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
public class VehicleServiceImpl extends BaseServiceImpl<Vehicle, Long> implements VehicleService {

  private final VehicleRepository vehicleRepo;

  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void init() {
    vehicleRepo.setEm(em);
    setEntityClass(Vehicle.class);
    setGenericDao(vehicleRepo);
  }

  @Override
  public Vehicle save(VehicleDTO vehicleDTO) {
    if (vehicleRepo.findByNumPlate(vehicleDTO.getNumPlate()) != null) {
      throw new ResourceExistsException("Vehicle with number plate '" + vehicleDTO.getNumPlate() + "' already exists!");
    }
    ModelMapper mapper = new ModelMapper();
    Vehicle vehicle = mapper.map(vehicleDTO, Vehicle.class);
    return em.merge(vehicle);
  }

  @Override
  public ObjectListWrapper<Vehicle> findAllPaginated(Integer page, Integer pageSize, String search) {
    ObjectListWrapper wrapper = new ObjectListWrapper();
    HashMap<String, Object> vehicles = vehicleRepo.findAllPaged(page, pageSize, search);
    wrapper.setList((List) vehicles.get("vehicles"));
    wrapper.setTotal(Integer.parseInt(vehicles.get("vehicle_count").toString()));
    wrapper.setPageSize(pageSize);
    wrapper.setCurrentPage(page);
    return wrapper;
  }

  @Override
  public Vehicle update(Long vehicleId, VehicleDTO vehicleDTO) {
    Vehicle vehicle = vehicleRepo.findById(vehicleId);
    if (vehicleRepo.findByNumPlate(vehicleDTO.getNumPlate()) != null && vehicleRepo.findByNumPlate(vehicleDTO.getNumPlate()) != vehicle) {
      throw new ResourceExistsException("Vehicle with number plate '" + vehicleDTO.getNumPlate() + "' already exists!");
    }
    vehicle.setNumOfSeats(vehicleDTO.getNumOfSeats());
    vehicle.setColor(vehicleDTO.getColor());
    return em.merge(vehicle);
  }

}
