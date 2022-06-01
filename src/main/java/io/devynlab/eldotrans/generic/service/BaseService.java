package io.devynlab.eldotrans.generic.service;

import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.generic.model.SimpleModelBase;

import java.io.Serializable;

public interface BaseService<T, PK extends Serializable> {
  T saveOrUpdate(T t);

  T findById(PK id);

  void delete(PK id);

  <T extends ModelBase> void softDelete(T entity);

  <T extends ModelBase> void softDelete(PK t, Class<T> entityClass);

  <T extends ModelBase> void softDelete(PK[] t, Class<T> entityClass);

  <T extends SimpleModelBase> void softDeleteSimple(T entity);

  <T extends SimpleModelBase> void softDeleteSimple(PK t, Class<T> entityClass);

  <T extends SimpleModelBase> void softDeleteSimple(PK[] t, Class<T> entityClass);
}
