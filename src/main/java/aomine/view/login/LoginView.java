package aomine.view.login;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.ImagePanel;
import aomine.components.TextInput;
import aomine.controller.login.LoginController;
import net.miginfocom.swing.MigLayout;

public class LoginView extends ImagePanel {
  private LoginController controller;

  public LoginView() {
    super(new ImagePanelBuilder().setPathFromResources("background/login.png"));
    controller = new LoginController(this);
    init();
  }

  private void init() {
    // Main Layout
    setLayout(new MigLayout("fill", "[center]"));

    // Definition of components
    login = new JPanel(new MigLayout("wrap, insets 35 45 30 45", "[fill, 280]"));
    lblTitle = new JLabel("Bienvenido!");
    tiUsername = new TextInput.TextInputBuilder()
        .setPlaceholder("Ingrese su usuario")
        .setLabelText("Usuario")
        .withErrorLabel()
        .build();
    tiPassword = new TextInput.TextInputBuilder()
        .setPlaceholder("Ingrese su contraseña")
        .setLabelText("Contraseña")
        .withErrorLabel()
        .setPassword(true)
        .build();
    btnLogin = new JButton("Login");

    // Styles
    login.putClientProperty(FlatClientProperties.STYLE,
        "arc: 20;" +
            "[light]background: @background;" +
            "[dark]background: lighten(@background, 3%);");

    lblTitle.putClientProperty(FlatClientProperties.STYLE,
        "font: bold +10;" +
            "foreground: #7E3819;");

    btnLogin.putClientProperty(FlatClientProperties.STYLE,
        "background: #7E3819;" +
            "foreground: #ffffff;" +
            "font: bold;" +
            "borderWidth: 0;" +
            "focusWidth: 0;" +
            "innerFocusWidth: 0");
    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Events
    tiUsername.onKeyTyped(e -> {
      resetField(tiUsername);
    });

    tiPassword.onKeyTyped(e -> {
      if (e.getKeyChar() == '\n')
        return;

      resetField(tiPassword);
    });

    tiPassword.onKeyPressed(e -> {
      if (e.getKeyCode() == KeyEvent.VK_ENTER)
        controller.fastLogin(null);
    });

    btnLogin.addActionListener(controller::fastLogin);

    // Constraints
    login.add(lblTitle, "grow 0, center");
    login.add(tiUsername.getLabel());
    login.add(tiUsername.getInput());
    login.add(tiUsername.getErrorLabel());
    login.add(tiPassword.getLabel());
    login.add(tiPassword.getInput());
    login.add(tiPassword.getErrorLabel());
    login.add(btnLogin, "gapy 10");

    add(login);
  }

  private void resetField(TextInput ti) {
    ti.setErrorHint(false);
    ti.setLblErrorText("");
  }

  public TextInput getTiUsername() {
    return tiUsername;
  }

  public TextInput getTiPassword() {
    return tiPassword;
  }

  private JLabel lblTitle;
  private JPanel login;
  private TextInput tiUsername;
  private TextInput tiPassword;
  private JButton btnLogin;
}
