package io.devynlab.eldotrans.system.trip.dto;

import io.devynlab.eldotrans.system.trip.enums.Destinations;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TripDTO implements Serializable {
  private Long id;
  private String description;
  private Destinations departure;
  private Destinations arrival;
  private Date date;
}
