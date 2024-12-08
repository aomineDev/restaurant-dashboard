package aomine.dao;

import java.util.ArrayList;

public interface DAO<T> {
  ArrayList<T> getAll();
  T get(int id);
  void add(T t);
  void update(T t);
  void delete(int id);
}
