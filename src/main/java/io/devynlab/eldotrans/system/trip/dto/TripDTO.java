package io.devynlab.eldotrans.system.trip.dto;

import io.devynlab.eldotrans.system.booking.enums.Destinations;
import io.devynlab.eldotrans.system.trip.model.Trip;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TripDTO implements Serializable {
  private Long id;
  private Destinations tripFrom;
  private Destinations tripTo;
  private Date date;
  private Integer numOfPassengers;
  private Integer price;
  private Vehicle vehicle;
  private Long vehicleId;
  private Date departedAt;
  private Date arrivedAt;

  public TripDTO fromTrip(Trip trip) {
    id = trip.getId();
    tripFrom = trip.getTripFrom();
    tripTo = trip.getTripTo();
    date = trip.getDate();
    numOfPassengers = trip.getNumOfPassengers();
    price = trip.getPrice();
    vehicle = trip.getVehicle();
    departedAt = trip.getDepartedAt();
    arrivedAt = trip.getArrivedAt();
    return this;
  }
}
