package io.devynlab.eldotrans.system.trip.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookingDTO implements Serializable {
  private Long id;
  private String name;
  private String phoneNumber;
  private Integer numOfSeats;
}
