package io.devynlab.eldotrans.generic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@Data
public abstract class SimpleModelBase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "honeySequence")
  @Column(name = "id")
  protected Long id;

  @Column(name = "created_at", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedAt;

  @JsonIgnore
  @Column(name = "deleted", columnDefinition = "tinyint(1) default '0'")
  private boolean deleted = false;

  @JsonIgnore
  @Column(name = "deleted_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedAt;

  @Column(name = "created_by_id")
  private Long createdByUserId;

  @Column(name = "updated_by_id")
  private Long updatedByUserId;

  @Column(name = "deleted_by_id")
  private Long deletedById;

  @JsonIgnore
  @Version
  @Column(name = "version")
  private int version = 0;

  @JsonIgnore
  @XmlTransient
  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @JsonIgnore
  @XmlTransient
  public Date getUpdatedAt() {
    return updatedAt;
  }

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
    SimpleModelBase other = (SimpleModelBase) obj;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
  }

  @Override
  public String toString() {
    return getClass().getCanonicalName() + "{" + "id=" + id + '}';
  }
}


