package io.devynlab.eldotrans.user.service;

import io.devynlab.eldotrans.generic.dto.ObjectListWrapper;
import io.devynlab.eldotrans.generic.exception.ResourceExistsException;
import io.devynlab.eldotrans.generic.service.BaseServiceImpl;
import io.devynlab.eldotrans.user.dto.UserDTO;
import io.devynlab.eldotrans.user.model.User;
import io.devynlab.eldotrans.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepo;
  @PersistenceContext
  private EntityManager em;

  @PostConstruct
  public void init() {
    userRepo.setEm(em);
    setEntityClass(User.class);
    setGenericDao(userRepo);
  }

  @Override
  public User findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  @Override
  public User save(UserDTO userDTO) {
    if (userRepo.findByUsername(userDTO.getUsername()) != null) {
      throw new ResourceExistsException("User with username '" + userDTO.getUsername() + "' already exists!");
    }
    if (userRepo.findByEmail(userDTO.getEmail()) != null) {
      throw new ResourceExistsException("User with email '" + userDTO.getEmail() + "' already exists!");
    }
    ModelMapper mapper = new ModelMapper();
    User user = mapper.map(userDTO, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return em.merge(user);
  }

  @Override
  public ObjectListWrapper<User> findAllPaginated(Integer page, Integer pageSize, String search) {
    ObjectListWrapper wrapper = new ObjectListWrapper();
    HashMap<String, Object> users = userRepo.findAllPaged(page, pageSize, search);
    wrapper.setList((List) users.get("users"));
    wrapper.setTotal(Integer.parseInt(users.get("user_count").toString()));
    wrapper.setPageSize(pageSize);
    wrapper.setCurrentPage(page);
    return wrapper;
  }

  @Override
  public User update(Long userId, UserDTO userDTO) {
    User user = userRepo.findById(userId);
    if (userRepo.findByUsername(userDTO.getUsername()) != null && userRepo.findByUsername(userDTO.getUsername()) != user) {
      throw new ResourceExistsException("User with username '" + userDTO.getUsername() + "' already exists!");
    }
    if (userRepo.findByEmail(userDTO.getEmail()) != null && userRepo.findByEmail(userDTO.getEmail()) != user) {
      throw new ResourceExistsException("User with email '" + userDTO.getEmail() + "' already exists!");
    }
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
    return em.merge(user);
  }

}
