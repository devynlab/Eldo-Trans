package io.devynlab.eldotrans.system.vehicle.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
@Transactional
public class VehicleRepositoryImpl extends GenericRepositoryImpl<Vehicle, Long> implements VehicleRepository {
  @Override
  public Vehicle findByNumPlate(String numPlate) {
    Query query = getEm().createQuery("SELECT v FROM Vehicle v WHERE v.numPlate = :numPlate", Vehicle.class);
    query.setParameter("numPlate", numPlate);
    try {
      return (Vehicle) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search) {
    Query query = getEm().createQuery("SELECT v FROM Vehicle v WHERE v.available =: availableStatus " + (search != null ? "AND v.numPlate LIKE :search" : ""), Vehicle.class);
    query.setParameter("availableStatus", true);
    if (search != null) {
      query.setParameter("search", "%" + search + "%");
    }
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<Vehicle> vehicles = query.getResultList();
    Query countQuery = getEm().createQuery("SELECT count(v) FROM Vehicle v WHERE v.available =: availableStatus " + (search != null ? "AND v.numPlate LIKE :search" : ""), Long.class);
    countQuery.setParameter("availableStatus", true);
    if (search != null) {
      countQuery.setParameter("search", "%" + search + "%");
    }
    Long vehicleCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("vehicles", vehicles);
    map.put("vehicle_count", vehicleCount);
    return map;
  }

}
