package io.devynlab.eldotrans.system.trip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.system.booking.enums.Destinations;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trips")
@Data
public class Trip extends ModelBase {

  @Column(name = "date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "departed_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date departedAt;

  @Column(name = "arrived_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date arrivedAt;

  @JsonIgnore
  @JoinColumn(name = "vehicle_id", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Vehicle vehicle;

  @Column(name = "no_of_passengers")
  private Integer numOfPassengers;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_from", nullable = false, columnDefinition = "varchar(20) default 'ELDORET'")
  private Destinations tripFrom;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_to", nullable = false, columnDefinition = "varchar(20) default 'NAIROBI'")
  private Destinations tripTo;

}
