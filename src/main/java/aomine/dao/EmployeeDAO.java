package aomine.dao;

import org.hibernate.Session;

import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.model.EntityColumn;
import aomine.utils.GoatList;

public class EmployeeDAO implements DAO<Employee> {
  private Session session;

  public EmployeeDAO() {
    session = Hibernate.getSession();
  }

  @Override
  public GoatList<Employee> getAll() {
    String query = "FROM Employee e";

    return new GoatList<>(session.createQuery(query, Employee.class).getResultList());
  }

  @Override
  public Employee get(long id) {
    return session.find(Employee.class, id);
  }

  @Override
  public void add(Employee employee) {
    session.beginTransaction();
    session.persist(employee);
    session.flush();
    session.refresh(employee);
    session.getTransaction().commit();
  }

  @Override
  public void update(Employee employee) {
    session.beginTransaction();
    session.merge(employee);
    session.flush();
    session.refresh(employee);
    session.getTransaction().commit();
  }

  @Override
  public void delete(long id) {
    session.beginTransaction();
    Employee employee = get(id);
    session.remove(employee);
    session.getTransaction().commit();
  }

  @Override
  public GoatList<Employee> findBy(EntityColumn column, String value) {
    String query = "FROM Employee e WHERE e." + column.getColumnName() + " = :value";

    return new GoatList<>(session.createQuery(query, Employee.class)
        .setParameter("value", value)
        .getResultList());
  }

  @Override
  public Employee findUniqueBy(EntityColumn column, String value) {
    String query = "FROM Employee e WHERE e." + column.getColumnName() + " = :name";

    return session.createQuery(query, Employee.class)
        .setParameter("value", value)
        .uniqueResult();
  }

  public GoatList<Employee> search(EntityColumn column, String search) {
    String query = "FROM Employee e WHERE e." + column.getColumnName() + " ILIKE :search";

    return new GoatList<>(session.createQuery(query, Employee.class)
        .setParameter("search", "%" + search + "%")
        .getResultList());
  }

  public boolean isEmpty() {
    String hql = "SELECT COUNT(*) FROM Employee e";
    return session.createQuery(hql, Long.class).uniqueResult() == 0;
  }
}
