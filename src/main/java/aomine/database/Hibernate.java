package aomine.database;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import aomine.model.Employee;
import aomine.model.EntityColumn;
import aomine.utils.GoatList;
import io.github.cdimascio.dotenv.Dotenv;

public class Hibernate {
  private static Hibernate instance;
  private SessionFactory factory;
  private Session session;

  private Hibernate() {
    Dotenv dotenv = Dotenv.load();

    System.setProperty("DB_URL", dotenv.get("DB_URL"));
    System.setProperty("DB_USER", dotenv.get("DB_USER"));
    System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

    startConnection();
  }

  private void startConnection() {
    factory = new Configuration().configure().buildSessionFactory();
    session = factory.openSession();
  }

  public static void install() {
    if (instance == null)
      instance = new Hibernate();
  }

  public static Hibernate getInstance() {
    if (instance == null)
      instance = new Hibernate();

    return instance;
  }

  public static Session getSession() {
    return instance.session;
  }

  public static void openSession() {
    instance.session = instance.factory.openSession();
  }

  public static void close() {
    instance.session.close();
  }

  public static <T> GoatList<T> getAll(Class<T> entity) {
    String query = "FROM " + entity.getSimpleName();

    return new GoatList<>(instance.session
        .createQuery(query, entity)
        .getResultList());
  }

  public static <T> T get(Class<T> entity, long id) {
    return instance.session.find(entity, id);
  }

  public static <T> void add(T obj) {
    instance.session.beginTransaction();
    instance.session.persist(obj);
    instance.session.flush();
    instance.session.refresh(obj);
    instance.session.getTransaction().commit();
  }

  public static <T> void update(T obj) {
    instance.session.beginTransaction();
    instance.session.merge(obj);
    instance.session.flush();
    instance.session.refresh(obj);
    instance.session.getTransaction().commit();
  }

  public static <T> void delete(Class<T> entity, long id) {
    instance.session.beginTransaction();
    T obj = get(entity, id);
    instance.session.remove(obj);
    instance.session.getTransaction().commit();
  }

  public static <T> GoatList<T> findBy(Class<T> entity, EntityColumn column, String value) {
    String query = "FROM " + entity.getSimpleName() + " e WHERE e." + column.getColumnName() + " = :value";

    return new GoatList<>(instance.session
        .createQuery(query, entity)
        .setParameter("value", value)
        .getResultList());
  }

  public static <T> T findUniqueBy(Class<T> entity, EntityColumn column, String value) {
    String query = "FROM \" +  entity.getSimpleName() + \" e WHERE e." + column.getColumnName() + " = :name";

    return instance.session
        .createQuery(query, entity)
        .setParameter("value", value)
        .uniqueResult();
  }

  public static <T> GoatList<T> search(Class<T> entity, EntityColumn column, String search) {
    String query = "FROM Employee e WHERE e." + column.getColumnName() + " ILIKE :search";

    return new GoatList<>(instance.session
        .createQuery(query, entity)
        .setParameter("search", "%" + search + "%")
        .getResultList());
  }

  public static <T> boolean isEmpty(Class<T> enitty) {
    String hql = "SELECT COUNT(*) FROM " + enitty.getSimpleName() + " e";
    return instance.session
        .createQuery(hql, Long.class)
        .uniqueResult() == 0;
  }
}
