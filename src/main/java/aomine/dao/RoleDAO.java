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
    String query = "FROM Role";

    return new ArrayList<>(session.createQuery(query, Role.class).getResultList());
  }

  @Override
  public Role get(long id) {
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
  public void delete(long id) {
    session.beginTransaction();
    Role role = get(id);
    session.remove(role);
    session.getTransaction().commit();
  }

  public Role findByName(Role.Types type) {
    String query = "FROM Role r where r.name = :name";

    return session.createQuery(query, Role.class)
        .setParameter("name", type.getName())
        .uniqueResult();
  }
}
