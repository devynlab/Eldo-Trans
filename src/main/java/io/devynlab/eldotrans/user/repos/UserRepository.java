package io.devynlab.eldotrans.user.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepository;
import io.devynlab.eldotrans.user.model.User;

import java.util.HashMap;

public interface UserRepository extends GenericRepository<User, Long> {
  User findByUsername(String username);

  User findByEmail(String email);

  HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search);
}
