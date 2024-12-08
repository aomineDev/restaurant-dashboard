package aomine.dao;

import java.util.ArrayList;

import org.hibernate.Session;

import aomine.database.Hibernate;
import aomine.model.Employee;

public class EmployeeDAO implements DAO<Employee> {
  private Session session;

  public EmployeeDAO() {
    session = Hibernate.getInstance().getSession();
  }

  public ArrayList<Employee> getAll() {
    String query = "FROM employees";

    return new ArrayList<>(session.createQuery(query, Employee.class).list());
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
}
