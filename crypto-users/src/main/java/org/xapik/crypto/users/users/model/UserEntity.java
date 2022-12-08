package org.xapik.crypto.users.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
public class UserEntity {

  @Id
  @Getter
  @Setter
  @Column
  private String email;

  @Getter
  @Setter
  @Column
  private String name;

  @Getter
  @Setter
  @Column
  private String surname;

  @Getter
  @Setter
  @Column
  @JsonIgnore
  private String password;

  @Getter
  @Setter
  @Column
  private String role;

}
