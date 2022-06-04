package io.devynlab.eldotrans.system.vehicle.dto;

import io.devynlab.eldotrans.system.vehicle.enums.CarModels;
import lombok.Data;

import java.io.Serializable;

@Data
public class VehicleDTO implements Serializable {
  private Long id;
  private String numPlate;
  private CarModels carModel;
  private Integer numOfSeats;
  private String color;
  private Long driverId;
}
