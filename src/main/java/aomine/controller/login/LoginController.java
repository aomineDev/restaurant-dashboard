package aomine.controller.login;

import java.awt.event.ActionEvent;

import org.postgresql.translation.messages_pt_BR;

import aomine.ViewManager;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.Validate;
import aomine.view.admin.Test;
import aomine.view.login.LoginView;
import at.favre.lib.crypto.bcrypt.BCrypt;
import raven.alerts.MessageAlerts;

public class LoginController {
  private LoginView view;
  private RoleDAO roleDAO;
  private EmployeeDAO employeeDAO;
  private Validate validate;

  public LoginController(LoginView view) {
    this.view = view;
    validate = new Validate();
    roleDAO = new RoleDAO();
    employeeDAO = new EmployeeDAO();

  }

  public void handleLoginClick(ActionEvent evt) {
    if (employeeDAO.isEmpty()) createAdmin();

    String username = view.getTfUsername().getText();

    validate.setElement(username)
      .isRequired("El usuario es requerido");

    if (!validate.getIsValid()) {
      errorMessage(validate.getMessage());
      return;
    }

    String password = String.valueOf(view.getPfPassword().getPassword());

    validate.setElement(password)
      .isRequired("La contraseña es requedia")
      .minLength("La contraseña tiene que tener mas de 8 cara cteres", 8);

    if (!validate.getIsValid()) {
      errorMessage(validate.getMessage());
      return;
    }

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

  public void errorMessage(String msg) {
    MessageAlerts.getInstance().showMessage(
      "Error!", 
      msg,
      MessageAlerts.MessageType.ERROR
    );
  }
}
