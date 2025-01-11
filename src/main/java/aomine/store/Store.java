package aomine.store;

import aomine.model.Employee;

public class Store {
  private static Store instance;
  private Employee user;

  private Store() {

  }

  public static void install() {
    if (instance == null)
      instance = new Store();
  }

  public static Store getInstance() {
    if (instance == null)
      instance = new Store();

    return instance;
  }

  public static void setUser(Employee user) {
    instance.user = user;
  }

  public static Employee getUser() {
    return instance.user;
  }
}
