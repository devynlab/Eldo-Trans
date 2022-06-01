package io.devynlab.eldotrans.generic.repository;

import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.generic.model.SimpleModelBase;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Slf4j
public abstract class GenericRepositoryImpl<T, PK extends Serializable> implements GenericRepository<T, PK> {
  private final Class<T> persistentClass;
  @PersistenceContext
  protected EntityManager em;

  @Override
  public void setEm(EntityManager em) {
    this.em = em;
  }

  @Override
  public EntityManager getEm() {
    return this.em;
  }

  @SuppressWarnings("unchecked")
  public GenericRepositoryImpl() {
    Type genericSuperClass = getClass().getGenericSuperclass();
    ParameterizedType parametrizedType = null;
    while (parametrizedType == null) {
      if ((genericSuperClass instanceof ParameterizedType)) {
        parametrizedType = (ParameterizedType) genericSuperClass;
      } else {
        genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
      }
    }
    this.persistentClass = (Class<T>) parametrizedType.getActualTypeArguments()[0];
  }

  @Override
  public Class<T> getEntityClass() {
    return persistentClass;
  }

  @Override
  @Transactional
  public T save(T entity) {
    return em.merge(entity);
  }

  @Override
  @Transactional
  public void delete(T entity) {
    em.remove(entity);
  }

  @Override
  @Transactional
  public void deleteById(final PK id) {
    T entity = em.find(persistentClass, id);
    if (entity != null) em.remove(entity);
  }

  @Override
  @Transactional
  public void delete(PK ids[]) {
    int size = ids.length;
    for (int idx = 0; idx < size; idx++) {
      T entity = em.find(persistentClass, ids[idx]);
      if (entity != null) em.remove(entity);
    }
  }

  @Override
  @Transactional
  public <T extends ModelBase> void softRemove(PK ids[], Class<T> entityClass) {
    int size = ids.length;
    for (int idx = 0; idx < size; idx++) {
      T entity = em.find(entityClass, ids[idx]);
      softRemove(entity);
    }
  }

  @Override
  @Transactional
  public <T extends ModelBase> void softRemove(PK id, Class<T> entityClass) {
    T entity = getEm().find(entityClass, id);
    if (entity == null) {
      throw new EntityNotFoundException(" Entity with id " + id + " does not exist in the database");
    }
    log.info(" the entity to be deleted , id " + entity);
    entity.setDeleted(true);
    entity.setDeletedAt(new Date());
    em.merge(entity);
  }

  @Override
  @Transactional
  public <T extends ModelBase> void softRemove(T entity) {
    entity.setDeleted(true);
    entity.setDeletedAt(new Date());
    em.merge(entity);
  }

  @Override
  @Transactional
  public <T extends SimpleModelBase> void softRemoveSimple(PK ids[], Class<T> entityClass) {
    int size = ids.length;
    for (int idx = 0; idx < size; idx++) {
      T entity = em.find(entityClass, ids[idx]);
      softRemoveSimple(entity);
    }
  }

  @Override
  @Transactional
  public <T extends SimpleModelBase> void softRemoveSimple(PK id, Class<T> entityClass) {
    T entity = getEm().find(entityClass, id);
    if (entity == null) {
      throw new EntityNotFoundException(" Entity with id " + id + " does not exist in the database");
    }
    log.info(" the entity to be deleted , id " + entity);
    entity.setDeleted(true);
    entity.setDeletedAt(new Date());
    em.merge(entity);
  }

  @Override
  @Transactional
  public <T extends SimpleModelBase> void softRemoveSimple(T entity) {
    entity.setDeleted(true);
    entity.setDeletedAt(new Date());
    em.merge(entity);
  }

  @Override
  @Transactional
  public T findById(final PK id) {
    final T result = getEm().find(persistentClass, id);
    return result;
  }

  @Override
  @Transactional
  public T findById(final PK id, String graphName) {
    EntityGraph graph = this.em.getEntityGraph(graphName);
    Map<String, Object> hints = new HashMap();
    hints.put("javax.persistence.fetchgraph", graph);
    return getEm().find(persistentClass, id, hints);
  }

  @Override
  @Transactional
  public T getReference(final PK id) {
    return em.getReference(persistentClass, id);
  }

  @Override
  @Transactional
  public int countByExample(final T exampleInstance) {
    CriteriaBuilder qb = getEm().getCriteriaBuilder();
    CriteriaQuery<Long> cq = qb.createQuery(Long.class);
    cq.select(qb.count(cq.from(getEntityClass())));
    return em.createQuery(cq).getSingleResult().intValue();
  }

  @Override
  @Transactional
  public List<T> findByExample(final T exampleInstance) {
    CriteriaBuilder builder = getEm().getCriteriaBuilder();
    CriteriaQuery<T> query = builder.createQuery(getEntityClass());
    return em.createQuery(query).getResultList();
  }

  @Override
  @Transactional
  public List<T> findByNamedQuery(final String name, Object... params) {
    Query query = getEm().createNamedQuery(name);
    for (int i = 0; i < params.length; i++) {
      query.setParameter(i + 1, params[i]);
    }
    final List<T> result = query.getResultList();
    return result;
  }

  @Override
  @Transactional
  public List<T> findByNamedQuery(final String name, String graphName, Object... params) {
    Query query = getEm().createNamedQuery(name);
    query.setHint("javax.persistence.fetchgraph", em.getEntityGraph(graphName));
    for (int i = 0; i < params.length; i++) {
      query.setParameter(i + 1, params[i]);
    }
    final List<T> result = query.getResultList();
    return result;
  }

  @Override
  @Transactional
  public List<T> findByNamedQuery(final String name, final Map<String, ? extends Object> params) {
    Query query = getEm().createNamedQuery(name);
    for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }
    return query.getResultList();
  }

  @Override
  @Transactional
  public List<T> findByNamedQuery(final String name, String graphName, final Map<String, ? extends Object> params) {
    Query query = getEm().createNamedQuery(name);
    query.setHint("javax.persistence.fetchgraph", em.getEntityGraph(graphName));
    for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }
    final List<T> result = query.getResultList();
    return result;
  }


  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params) {
    Query query = getEm().createNamedQuery(name);
    for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }
    final List<T> result = query.getResultList();
    return result;
  }

  @Override
  public Query createNativeQuery(String sql) {
    return getEm().createNativeQuery(sql);
  }

  @Override
  public Query createQuery(String sql) {
    return getEm().createQuery(sql);
  }
}
