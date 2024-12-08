package aomine.controller.login;

import java.awt.event.ActionEvent;

import aomine.ViewManager;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.view.admin.Test;
import aomine.view.login.LoginView;
import at.favre.lib.crypto.bcrypt.BCrypt;
import raven.alerts.MessageAlerts;

public class LoginController {
  private LoginView view;
  private RoleDAO roleDAO;
  private EmployeeDAO employeeDAO;

  public LoginController(LoginView view) {
    this.view = view;
    roleDAO = new RoleDAO();
    employeeDAO = new EmployeeDAO();

  }

  public void handleLoginClick(ActionEvent evt) {
    if (employeeDAO.isEmpty()) createAdmin();

    MessageAlerts.getInstance().showMessage(
      // "Error", 
      // "Usuario y/o contraseña incorrectos.",
      "OK!", 
      "Usuario creado correctamente. " + String.valueOf(view.getPfPassword().getPassword()),
      MessageAlerts.MessageType.SUCCESS
    );

    // ViewManager.showView(new Test());
    // ViewManager.login();
  }

  private void createAdmin() {
    Role admin = new Role();
    admin.setName("administrador");
    roleDAO.add(admin);

    String password = "securepass";
    String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

    Employee user = new Employee();
    user.setRole(admin);
    user.setUsername("admin");
    user.setPassword(encryptedPassword);
    user.setName("admin");
    user.setPaternalLastname("root");
    user.setMaternalLastname("toor");
    user.setDni(13246578);

    employeeDAO.add(user);
  }
}
