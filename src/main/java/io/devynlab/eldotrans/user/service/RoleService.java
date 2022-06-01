package io.devynlab.eldotrans.user.service;

import io.devynlab.eldotrans.generic.service.BaseService;
import io.devynlab.eldotrans.user.dto.RoleDTO;
import io.devynlab.eldotrans.user.model.Role;

import java.util.List;

public interface RoleService extends BaseService<Role, Long> {
  Role save(RoleDTO roleDTO);

  List<Role> findAll();
}
