package aomine.dao;

import aomine.utils.GoatList;

public interface DAO<T> {
  GoatList<T> getAll();

  T get(long id);

  void add(T t);

  void update(T t);

  void delete(long id);
}
