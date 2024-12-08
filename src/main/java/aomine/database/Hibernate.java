package aomine.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

import org.hibernate.Session;

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

  public Session getSession() {
    return session;
  }

  public static Hibernate getInstance() {
    if (instance == null) instance = new Hibernate();

    return instance;
  }
}
