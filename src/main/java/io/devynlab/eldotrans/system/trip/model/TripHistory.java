package io.devynlab.eldotrans.system.trip.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trip_history")
@Data
public class TripHistory implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @JoinColumn(name = "trip_id", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Trip trip;

  @Column(name = "trip_id")
  private Long tripId;

  @Column(name = "remaining_seats")
  private Integer remainingSeats;

  @Column(name = "comment")
  private String comment;

}
