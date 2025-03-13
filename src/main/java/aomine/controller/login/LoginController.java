package aomine.controller.login;

import java.awt.event.ActionEvent;

import aomine.ViewManager;
import aomine.controller.Controller;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.model.Employee.EmployeeColumn;
import aomine.model.Role.RoleColumn;
import aomine.model.Role.RoleTypes;
import aomine.store.Store;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import aomine.view.admin.employee.EmployeeView;
import aomine.view.login.LoginView;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import io.github.cdimascio.dotenv.Dotenv;
import raven.alerts.MessageAlerts;

public class LoginController implements Controller {
  private LoginView view;
  private RoleDAO roleDAO;
  private EmployeeDAO employeeDAO;
  private Validate validate;

  // from view
  private String username;
  private String password;

  public LoginController(LoginView view) {
    this.view = view;
    this.validate = new Validate();
    this.roleDAO = new RoleDAO();
    this.employeeDAO = new EmployeeDAO();

    this.handleEmptyDB();
  }

  private void handleEmptyDB() {
    if (employeeDAO.isEmpty()) {
      createRoles();
      createAdmin();
    }
  }

  public void handleFastLogin(ActionEvent evt) {
    ViewManager.showView(new EmployeeView());
    ViewManager.login();
  }

  public void handleLogin(ActionEvent evt) {
    if (!validateFormFields())
      return;

    username = view.getTiUsername().getText();
    password = view.getPiPassword().getText();

    // Validacio de credenciales
    try {
      Employee user = verifyUsername(username);
      verifyPassword(password, user.getPassword());

      // Save User
      Store.setUser(user);

      // Router
      if (user.getRole().getName().equals(RoleTypes.ADMIN.getName())) {
        ViewManager.showView(new EmployeeView());
      }

      ViewManager.login();
    } catch (Exception e) {
      errorMessage(e.getMessage());
      view.getPiPassword().clear();
    }
  }

  @Override
  public boolean validateFormFields() {
    validate.reset();

    validate.setElement(view.getTiUsername())
        .isRequired("campo requerido");

    validate.setElement(view.getPiPassword())
        .isRequired("campo requerido");

    if (!validate.isValid()) {
      for (ValError error : validate.getValErrorList()) {
        error.getComponent().setError(error.getMessage());
      }
    }

    return validate.isValid();
  }

  private Employee verifyUsername(String username) throws Exception {
    Employee user = employeeDAO.findUniqueBy(EmployeeColumn.USERNAME, username);

    if (user == null)
      throw new Exception("Usuario y/o contraseña invalidos");
    else
      return user;
  }

  private void verifyPassword(String password, String encryptedPassword) throws Exception {
    Result result = BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword);

    if (!result.verified)
      throw new Exception("Usuario y/o contraseña invalidos");
  }

  private void createRoles() {
    for (RoleTypes role : RoleTypes.values()) {
      roleDAO.add(new Role(role.getName()));
    }
  }

  private void createAdmin() {
    Dotenv dotenv = Dotenv.load();

    String password = dotenv.get("DEFAULT_PASSWORD");
    String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

    Role admin = roleDAO.findUniqueBy(RoleColumn.NAME, RoleTypes.ADMIN.getName());

    Employee user = new Employee();
    user.setRole(admin);
    user.setUsername("admin");
    user.setPassword(encryptedPassword);
    user.setFirstName("admin");
    user.setPaternalLastname("root");
    user.setMaternalLastname("toor");
    user.setDni("12345678");

    employeeDAO.add(user);
  }

  private void errorMessage(String msg) {
    MessageAlerts.getInstance().showMessage(
        "Error!",
        msg,
        MessageAlerts.MessageType.ERROR);
  }
}
