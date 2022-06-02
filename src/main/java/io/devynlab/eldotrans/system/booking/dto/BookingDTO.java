package io.devynlab.eldotrans.system.booking.dto;

import io.devynlab.eldotrans.system.booking.enums.Destinations;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookingDTO implements Serializable {
  private Long id;
  private String description;
  private Destinations departure;
  private Destinations arrival;
  private Date date;
}
