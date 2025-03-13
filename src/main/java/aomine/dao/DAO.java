package aomine.dao;

import aomine.model.EntityColumn;
import aomine.utils.GoatList;

public interface DAO<T> {
  GoatList<T> getAll();

  T get(long id);

  void add(T t);

  void update(T t);

  void delete(long id);

  GoatList<T> findBy(EntityColumn column, String value);

  T findUniqueBy(EntityColumn column, String value);
}
