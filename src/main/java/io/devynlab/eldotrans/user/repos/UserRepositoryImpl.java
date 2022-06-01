package io.devynlab.eldotrans.user.repos;

import io.devynlab.eldotrans.generic.repository.GenericRepositoryImpl;
import io.devynlab.eldotrans.user.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User, Long> implements UserRepository {

  @Override
  public User findByUsername(String username) {
    Query query = getEm().createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
    query.setParameter("username", username);
    try {
      return (User) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public User findByEmail(String email) {
    Query query = getEm().createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
    query.setParameter("email", email);
    try {
      return (User) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public HashMap<String, Object> findAllPaged(Integer page, Integer pageSize, String search) {
    Query query = getEm().createQuery("SELECT u FROM User u WHERE u.deleted =: deletedStatus " + (search != null ? "AND (u.firstName LIKE :search OR u.lastName LIKE :search OR u.username LIKE :search)" : ""), User.class);
    query.setParameter("deletedStatus", false);
    if (search != null) {
      query.setParameter("search", "%" + search + "%");
    }
    query.setFirstResult((page - 1) * pageSize);
    query.setMaxResults(pageSize);
    List<User> users = query.getResultList();
    Query countQuery = getEm().createQuery("SELECT count(u) FROM User u WHERE u.deleted =: deletedStatus " + (search != null ? "AND (u.firstName LIKE :search OR u.lastName LIKE :search OR u.username LIKE :search)" : ""), Long.class);
    countQuery.setParameter("deletedStatus", false);
    if (search != null) {
      countQuery.setParameter("search", "%" + search + "%");
    }
    Long userCount = (Long) countQuery.getSingleResult();
    HashMap<String, Object> map = new HashMap<>();
    map.put("users", users);
    map.put("user_count", userCount);
    return map;
  }

}
