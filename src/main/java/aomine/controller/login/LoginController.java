package aomine.controller.login;

import java.awt.event.ActionEvent;

import aomine.ViewManager;
import aomine.dao.RoleDAO;
import aomine.model.Role;
import aomine.view.admin.Test;
import aomine.view.login.LoginView;
import raven.alerts.MessageAlerts;

public class LoginController {
  private LoginView view;
  private RoleDAO roleDAO;

  public LoginController(LoginView view) {
    this.view = view;
    roleDAO = new RoleDAO();
  }

  public void handleLoginClick(ActionEvent evt) {
    MessageAlerts.getInstance().showMessage(
      // "Error", 
      // "Usuario y/o contraseña incorrectos.",
      "OK!", 
      "ROles creados correctamente.",
      MessageAlerts.MessageType.SUCCESS
    );

    // ViewManager.showView(new Test());
    // ViewManager.login();
  }
}
