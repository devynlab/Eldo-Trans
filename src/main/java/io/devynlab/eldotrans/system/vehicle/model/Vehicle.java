package io.devynlab.eldotrans.system.vehicle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.devynlab.eldotrans.system.trip.model.Trip;
import io.devynlab.eldotrans.system.vehicle.enums.CarModels;
import io.devynlab.eldotrans.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
public class Vehicle implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "num_plate", nullable = false, unique = true)
  private String numPlate;

  @Enumerated(EnumType.STRING)
  @Column(name = "model", columnDefinition = "varchar(20) default 'VOXY'")
  private CarModels carModel;

  @Column(name = "num_of_seats", nullable = false)
  private Integer numOfSeats;

  @Column(name = "color")
  private String color;

  @JsonIgnore
  @JoinColumn(name = "driver_id", referencedColumnName = "id", insertable = false, updatable = false)
  @OneToOne(fetch = FetchType.LAZY)
  private User driver;

  @Column(name = "driver_id", nullable = false)
  private Long driverId;

  @JsonIgnore
  @Column(name = "available", columnDefinition = "tinyint(1) default '1'")
  private boolean available = true;

  @JsonIgnore
  @OneToMany(mappedBy = "vehicle")
  private List<Trip> tripList = new ArrayList<>();

  @JsonIgnore
  @Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
  private boolean deleted = false;

}
