package io.devynlab.eldotrans.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.devynlab.eldotrans.system.vehicle.model.Vehicle;
import io.devynlab.eldotrans.user.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
@XmlRootElement
@JsonInclude(NON_ABSENT)
@Data
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "gender", columnDefinition = "varchar(7) default 'NA'")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @ManyToMany(fetch = EAGER)
  private Collection<Role> roles = new ArrayList<>();

  @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
  private Vehicle vehicle;

  @JsonIgnore
  @Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
  private boolean deleted = false;

  public String getFullNames() {
    StringBuilder sb = new StringBuilder();
    if (getFirstName() != null)
      sb.append(getFirstName()).append(" ");
    if (getLastName() != null)
      sb.append(getLastName());
    return sb.toString();
  }

  @JsonIgnore
  @XmlTransient
  public String getPassword() {
    return password;
  }

}
