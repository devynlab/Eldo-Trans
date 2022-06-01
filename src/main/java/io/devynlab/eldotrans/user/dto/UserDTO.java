package io.devynlab.eldotrans.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.devynlab.eldotrans.user.model.Role;
import io.devynlab.eldotrans.user.model.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@XmlRootElement
@JsonInclude(NON_ABSENT)
@Data
public class UserDTO implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  @Email
  private String email;
  private Collection<Role> roles;

  @XmlTransient
  public String getPassword() {
    return password;
  }

  public UserDTO fromUser(User user) {
    id = user.getId();
    firstName = user.getFirstName();
    lastName = user.getLastName();
    username = user.getUsername();
    email = user.getEmail();
    roles = user.getRoles();
    return this;
  }
}
