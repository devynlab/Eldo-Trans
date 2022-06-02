package io.devynlab.eldotrans.system.booking.model;

import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.system.booking.enums.Destinations;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
 *TODO
 * Get rid of booking and just use filters to search for available trips
 * Trip model should have the following fields for filtering: from, to, date
 * The resulting filtered list should match the filters, showing the remaining number of seats and the prices
 */

@Entity
@Table(name = "booking")
@Data
public class Booking extends ModelBase {

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
