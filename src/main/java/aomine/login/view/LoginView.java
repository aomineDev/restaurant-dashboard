package aomine.login.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.login.controller.LoginController;
import net.miginfocom.swing.MigLayout;

public class LoginView extends JPanel {
  private LoginController controller;

  public LoginView() {
    controller = new LoginController(this);
    init();
  }

  public void init() {
    setLayout(new MigLayout("fill, debug", "[center]"));

    container = new JPanel(new MigLayout("wrap", "[fill, 280]"));
    lblUsername = new JLabel("usuario");
    lblPassword = new JLabel("contraseña");
    tfUsername = new JTextField();
    pfPassword = new JPasswordField();
    btnLogin = new JButton("Login");

    container.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff;");

    btnLogin.addActionListener(controller::handleLoginClick);

    container.add(lblUsername);
    container.add(tfUsername, "");
    container.add(lblPassword);
    container.add(pfPassword, "");
    container.add(btnLogin);

    add(container);
  }

  private JPanel container;
  private JLabel lblUsername;
  private JLabel lblPassword;
  private JTextField tfUsername;
  private JPasswordField pfPassword;
  private JButton btnLogin;
}
