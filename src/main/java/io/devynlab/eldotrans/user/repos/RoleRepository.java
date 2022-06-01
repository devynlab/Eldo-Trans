package io.devynlab.eldotrans.user.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.user.model.Role;

import java.util.List;

public interface RoleRepository extends GenericRepository<Role, Long> {
  Role findByName(String name);

  List<Role> findAll();
}
