package aomine.dao;

import java.util.ArrayList;

import org.hibernate.Session;

import aomine.database.Hibernate;
import aomine.model.Role;

public class RoleDAO implements DAO<Role> {
  private Session session;

  public RoleDAO() {
    session = Hibernate.getInstance().getSession();
  }


  @Override
  public ArrayList<Role> getAll() {
    String query = "FROM roles";
    
    return new ArrayList<>(session.createQuery(query, Role.class).list());
  }

  @Override
  public Role get(int id) {
    return session.find(Role.class, id);
  }

  @Override
  public void add(Role role) {
    session.beginTransaction();
    session.persist(role);
    session.getTransaction().commit();
  }

  @Override
  public void update(Role role) {
    session.beginTransaction();
    session.merge(role);
    session.getTransaction().commit();
  }

  @Override
  public void delete(int id) {
    session.beginTransaction();
    Role role = get(id);
    session.remove(role);
    session.getTransaction().commit();
  }
}
