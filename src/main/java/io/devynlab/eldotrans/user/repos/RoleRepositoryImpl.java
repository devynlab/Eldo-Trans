package io.devynlab.eldotrans.user.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.user.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role, Long> implements RoleRepository {
  @Override
  public Role findByName(String name) {
    Query query = getEm().createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class);
    query.setParameter("name", name);
    return (Role) query.getSingleResult();
  }

  @Override
  public List<Role> findAll() {
    Query query = getEm().createQuery("SELECT r FROM Role r", Role.class);
    return (List<Role>) query.getResultList();
  }
}
