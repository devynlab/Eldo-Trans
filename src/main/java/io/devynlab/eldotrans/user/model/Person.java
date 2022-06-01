package io.devynlab.eldotrans.user.model;

import io.devynlab.eldotrans.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(max = 400)
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Size(max = 400)
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Size(max = 20)
  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "gender", columnDefinition = "varchar(20) default 'NA'")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  public String getFullNames() {

    StringBuilder sb = new StringBuilder();

    if (getFirstName() != null)
      sb.append(getFirstName()).append(" ");

    if (getLastName() != null)
      sb.append(getLastName());


    return sb.toString();
  }


  public void setFirstName(String firstName) {
    if (firstName != null)
      firstName = firstName.trim().toUpperCase();

    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    if (lastName != null)
      lastName = lastName.trim().toUpperCase();

    this.lastName = lastName;
  }

}