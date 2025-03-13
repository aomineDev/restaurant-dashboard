package aomine.dao;

import org.hibernate.Session;

import aomine.database.Hibernate;
import aomine.model.EntityColumn;
import aomine.model.Role;
import aomine.utils.GoatList;

public class RoleDAO implements DAO<Role> {
  private Session session;

  public RoleDAO() {
    session = Hibernate.getSession();
  }

  @Override
  public GoatList<Role> getAll() {
    String query = "FROM Role";

    return new GoatList<>(session.createQuery(query, Role.class).getResultList());
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

  @Override
  public GoatList<Role> findBy(EntityColumn column, String value) {
    String query = "FROM Role r where r." + column.getColumnName() + " = :name";

    return new GoatList<>(session.createQuery(query, Role.class)
        .setParameter("name", value)
        .getResultList());
  }

  @Override
  public Role findUniqueBy(EntityColumn column, String value) {
    String query = "FROM Role r where r." + column.getColumnName() + " = :name";

    return session.createQuery(query, Role.class)
        .setParameter("name", value)
        .uniqueResult();
  }
}
