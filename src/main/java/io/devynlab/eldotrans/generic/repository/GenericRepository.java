package io.devynlab.eldotrans.generic.repository;

import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.generic.model.SimpleModelBase;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericRepository<T, PK extends Serializable> {
  void setEm(EntityManager em);

  EntityManager getEm();

  Class<T> getEntityClass();

  T findById(final PK id);

  T findById(final PK id, String graphName);

  T getReference(final PK id);

  List<T> findByExample(final T exampleInstance);

  List<T> findByNamedQuery(final String queryName, Object... params);

  List<T> findByNamedQuery(final String queryName, String graphName, Object... params);

  List<T> findByNamedQuery(final String queryName, final Map<String, ? extends Object> params);

  List<T> findByNamedQuery(final String queryName, final String graphName, final Map<String, ? extends Object> params);

  int countByExample(final T exampleInstance);

  T save(final T entity) throws Exception;

  void delete(final T entity) throws Exception;

  void deleteById(final PK id) throws Exception;

  void delete(PK ids[]) throws Exception;

  <T extends ModelBase> void softRemove(PK ids[], Class<T> entityClass);

  <T extends ModelBase> void softRemove(PK id, Class<T> entityClass);

  <T extends ModelBase> void softRemove(T entity);

  <T extends SimpleModelBase> void softRemoveSimple(PK ids[], Class<T> entityClass);

  <T extends SimpleModelBase> void softRemoveSimple(PK id, Class<T> entityClass);

  <T extends SimpleModelBase> void softRemoveSimple(T entity);

  List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ? extends Object> params);

  Query createNativeQuery(String sql);

  Query createQuery(String sql);
}
