package io.devynlab.eldotrans.generic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Formula;
import org.json.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public abstract class ModelBase implements Serializable {

  @Id
  @GeneratedValue(strategy = AUTO, generator = "honeySequence")
  @Column(name = "id")
  protected Long id;

  @Column(name = "created_at", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt;

  @Column(name = "created_by_id")
  private Long createdByUserId;

  @Basic(fetch = FetchType.LAZY)
  @Formula("(select concat(u.first_name,' ',u.last_name) from users u where u.id = created_by_id)")
  private String createdByUserName;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedAt;

  @Column(name = "updated_by_id")
  private Long updatedByUserId;

  @JsonIgnore
  @Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
  private boolean deleted = false;

  @JsonIgnore
  @Version
  @Column(name = "version")
  @ColumnDefault("0")
  private Integer version = 0;

  @Column(name = "deleted_by_id")
  private Long deletedById;

  @JsonIgnore
  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedAt;

  @JsonIgnore
  @XmlTransient
  @Transient
  public JSONObject json = new JSONObject();

  @JsonIgnore
  @XmlTransient
  @Transient
  public StringBuilder sb = new StringBuilder();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ModelBase other = (ModelBase) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }

  @Override
  public String toString() {
    return getClass().getCanonicalName() + "{" + "id=" + id + '}';
  }
}
