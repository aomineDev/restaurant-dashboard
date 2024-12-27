package aomine.controller.login;

import java.awt.event.ActionEvent;

import aomine.ViewManager;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import aomine.view.admin.EmployeeView;
import aomine.view.admin.Test;
import aomine.view.login.LoginView;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import raven.alerts.MessageAlerts;

public class LoginController {
  private LoginView view;
  private RoleDAO roleDAO;
  private EmployeeDAO employeeDAO;
  private Validate validate;

  // from vview
  private String username;
  private String password;

  public LoginController(LoginView view) {
    this.view = view;
    validate = new Validate();
    // roleDAO = new RoleDAO();
    // employeeDAO = new EmployeeDAO();
  }

  public void fastLogin(ActionEvent evt) {
    validateFields();
    // ViewManager.showView(new EmployeeView());
    // ViewManager.login();
  }

  public void handleLoginClick(ActionEvent evt) {
    // Verificacion de primer ingreso
    if (employeeDAO.isEmpty())
      createAdmin();

    // Validacion de campos
    validateFields();

    // Validacio de credenciales
    try {
      Employee user = verifyUsername(username);
      verifyPassword(password, user.getPassword());

      if (user.getRole().getName().equals(Role.ADMIN)) {
        ViewManager.showView(new Test());
      }

      ViewManager.login();
    } catch (Exception e) {
      errorMessage(e.getMessage());
      view.getTiPassword().setText("");
      // view.toggleFieldErrorHint(view.getTfUsername(), true);
      // view.toggleFieldErrorHint(view.getPfPassword(), true);
    }
  }

  private void validateFields() {
    validate.reset();

    validate.setElement(view.getTiUsername())
        .isRequired("campo requerido");

    validate.setElement(view.getTiPassword())
        .isRequired("campo requerido")
        .minLength("minimo 8 caracteres", 8);

    if (validate.getErrorCount() > 0) {
      for (ValError error : validate.getValErrorList()) {
        error.getInput().setErrorHint(true);
        error.getInput().setLblErrorText(error.getMessage());
      }
    }
  }

  private void createAdmin() {
    Role admin = new Role();
    admin.setName(Role.ADMIN);
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

  private void errorMessage(String msg) {
    MessageAlerts.getInstance().showMessage(
        "Error!",
        msg,
        MessageAlerts.MessageType.ERROR);
  }

  private Employee verifyUsername(String username) throws Exception {
    Employee user = employeeDAO.getByUsername(username);

    if (user == null)
      throw new Exception("Usurio y/o contraseña invalidos");
    else
      return user;
  }

  private void verifyPassword(String password, String encryptedPassword) throws Exception {
    Result result = BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword);

    if (!result.verified)
      throw new Exception("Usurio y/o contraseña invalidos");
  }
}
