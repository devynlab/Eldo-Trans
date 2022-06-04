package io.devynlab.eldotrans.system.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trips")
@Data
public class Trip implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_from", nullable = false, columnDefinition = "varchar(20) default 'ELDORET'")
  private Destinations tripFrom;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_to", nullable = false, columnDefinition = "varchar(20) default 'NAIROBI'")
  private Destinations tripTo;

  @Column(name = "date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "no_of_passengers")
  private Integer numOfPassengers;

  @Column(name = "price", nullable = false)
  private Integer price;

  @JsonIgnore
  @JoinColumn(name = "vehicle_id", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Vehicle vehicle;

  @Column(name = "vehicle_id", nullable = false)
  private Integer vehicleId;

  @Column(name = "departed_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date departedAt;

  @Column(name = "arrived_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date arrivedAt;

  @JsonIgnore
  @Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
  private boolean deleted = false;

  @JsonIgnore
  @Column(name = "active", columnDefinition = "tinyint(1) default '0'")
  private boolean active = true;

}
