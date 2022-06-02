package io.devynlab.eldotrans.system.trip.model;

import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.system.trip.enums.Destinations;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trips")
@Data
public class Trip extends ModelBase {

  @Column(name = "description", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_from", nullable = false, columnDefinition = "varchar(20) default 'ELDORET'")
  private Destinations departure;

  @Enumerated(EnumType.STRING)
  @Column(name = "destination_to", nullable = false, columnDefinition = "varchar(20) default 'NAIROBI'")
  private Destinations arrival;

  @Column(name = "date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date date;

}
