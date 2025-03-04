package aomine.dao;

import org.hibernate.Session;

import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.utils.GoatList;

public class EmployeeDAO implements DAO<Employee> {
  private Session session;

  public EmployeeDAO() {
    session = Hibernate.getSession();
  }

  public GoatList<Employee> getAll() {
    String query = "FROM Employee e";

    return new GoatList<>(session.createQuery(query, Employee.class).getResultList());
  }

  public Employee get(long id) {
    return session.find(Employee.class, id);
  }

  public void add(Employee employee) {
    session.beginTransaction();
    session.persist(employee);
    session.getTransaction().commit();
  }

  public void update(Employee employee) {
    session.beginTransaction();
    session.merge(employee);
    session.getTransaction().commit();
  }

  public void delete(long id) {
    session.beginTransaction();
    Employee employee = get(id);
    session.remove(employee);
    session.getTransaction().commit();
  }

  public boolean isEmpty() {
    String hql = "SELECT COUNT(*) FROM Employee e";
    return session.createQuery(hql, Long.class).uniqueResult() == 0;
  }

  public Employee findByUsername(String username) {
    String query = "FROM Employee e WHERE e.username = :username";

    return session.createQuery(query, Employee.class)
        .setParameter("username", username)
        .uniqueResult();
  }
}
