package io.devynlab.eldotrans.system.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "passengers")
@Data
public class Passenger implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "phone_number")
  private String phoneNum;

  @Column(name = "name")
  private String name;

  @Column(name = "num_of_seats")
  private Integer numOfSeats;

  @Column(name = "total_amount")
  private Integer totalAmount;

  @JsonIgnore
  @Column(name = "paid", columnDefinition = "tinyint(1) default '0'")
  private boolean paid = false;

  @JoinColumn(name = "trip_id", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Trip trip;

  @Column(name = "trip_id")
  private Long tripId;

}
