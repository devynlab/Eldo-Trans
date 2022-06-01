package io.devynlab.eldotrans.user.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.user.dto.UserDTO;
import io.devynlab.eldotrans.user.model.User;

public interface UserService extends BaseService<User, Long> {
  User findByUsername(String username);

  User save(UserDTO userDTO);

  ObjectListWrapper<User> findAllPaginated(Integer page, Integer pageSize, String search);

  User update(Long userId, UserDTO userDTO);
}
