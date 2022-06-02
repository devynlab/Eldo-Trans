package io.devynlab.eldotrans.system.trip.dto;

import io.devynlab.eldotrans.system.booking.enums.Destinations;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TripDTO implements Serializable {
  private Long id;
  private Date date;
  private Date departedAt;
  private Date arrivedAt;
  private Vehicle vehicle;
  private Integer numOfPassengers;
  private Destinations tripFrom;
  private Destinations tripTo;
}
