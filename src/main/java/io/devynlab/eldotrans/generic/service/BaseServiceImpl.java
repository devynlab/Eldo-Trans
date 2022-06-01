package io.devynlab.eldotrans.generic.service;

import io.devynlab.eldotrans.generic.exception.GeneralException;
import io.devynlab.eldotrans.generic.exception.NotFoundException;
import io.devynlab.eldotrans.generic.model.ModelBase;
import io.devynlab.eldotrans.generic.model.SimpleModelBase;
import io.devynlab.eldotrans.generic.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
@Slf4j
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
  private Class<T> entityClass;
  private GenericRepository<T, PK> genericRepository;

  public abstract void init();

  @Override
  @Transactional
  public T saveOrUpdate(T t) {
    try {
      return genericRepository.save(t);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public T findById(PK id) {
    T search = genericRepository.findById(id);
    if (search == null)
      throw new NotFoundException("Record with id " + id);
    return search;
  }

  @Override
  public void delete(PK t) {
    try {
      genericRepository.deleteById(t);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends ModelBase> void softDelete(T entity) {
    try {
      genericRepository.softRemove(entity);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends ModelBase> void softDelete(PK t, Class<T> entityClass) {
    try {
      genericRepository.softRemove(t, entityClass);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends ModelBase> void softDelete(PK[] t, Class<T> entityClass) {
    try {
      genericRepository.softRemove(t, entityClass);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends SimpleModelBase> void softDeleteSimple(T entity) {
    try {
      genericRepository.softRemoveSimple(entity);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends SimpleModelBase> void softDeleteSimple(PK t, Class<T> entityClass) {
    try {
      genericRepository.softRemoveSimple(t, entityClass);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  @Override
  public <T extends SimpleModelBase> void softDeleteSimple(PK[] t, Class<T> entityClass) {
    try {
      genericRepository.softRemoveSimple(t, entityClass);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException(e.getMessage());
    }
  }

  public Class<T> getEntityClass() {
    return entityClass;
  }

  public void setEntityClass(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  public GenericRepository<T, PK> getGenericDao() {
    return genericRepository;
  }

  public void setGenericDao(GenericRepository<T, PK> genericRepository) {
    this.genericRepository = genericRepository;
  }
}
