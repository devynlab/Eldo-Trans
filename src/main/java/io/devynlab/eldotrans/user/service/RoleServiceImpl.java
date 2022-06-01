package io.devynlab.eldotrans.user.service;

import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.user.dto.RoleDTO;
import io.devynlab.eldotrans.user.model.Role;
import io.devynlab.eldotrans.user.repos.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

  private final RoleRepository roleRepo;
  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void init() {
    roleRepo.setEm(em);
    setEntityClass(Role.class);
    setGenericDao(roleRepo);
  }

  @Override
  public Role save(RoleDTO roleDTO) {
    ModelMapper mapper = new ModelMapper();
    Role role = mapper.map(roleDTO, Role.class);
    return em.merge(role);
  }

  @Override
  public List<Role> findAll() {
    return roleRepo.findAll();
  }

}
