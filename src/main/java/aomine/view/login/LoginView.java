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
        .placeholder("Ingrese su usuario")
        .label("Usuario")
        .errorLabel()
        .build();
    pfPassword = new JPasswordField();
    btnLogin = new JButton("Login");

    // Styles
    login.putClientProperty(FlatClientProperties.STYLE,
        "arc: 20;" +
            "[light]background: @background;" +
            "[dark]background: lighten(@background, 3%);");

    lblTitle.putClientProperty(FlatClientProperties.STYLE,
        "font: bold +10;" +
            "foreground: #7E3819;");

    pfPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingrese su contraseña");
    pfPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
    btnLogin.putClientProperty(FlatClientProperties.STYLE,
        "background: #7E3819;" +
            "foreground: #ffffff;" +
            "font: bold;" +
            "borderWidth: 0;" +
            "focusWidth: 0;" +
            "innerFocusWidth: 0");
    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Events
    // tiUsername.addKeyListener(new KeyListener() {
    // @Override
    // public void keyTyped(KeyEvent e) {
    // toggleFieldErrorHint(tiUsername, false);
    // toggleFieldErrorHint(pfPassword, false);
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    // }
    // });

    // pfPassword.addKeyListener(new KeyListener() {
    // @Override
    // public void keyTyped(KeyEvent e) {
    // toggleFieldErrorHint(tiUsername, false);
    // toggleFieldErrorHint(pfPassword, false);
    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    // if (e.getKeyCode() == KeyEvent.VK_ENTER)
    // controller.fastLogin(null);
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    // }
    // });

    btnLogin.addActionListener(controller::fastLogin);

    // Constraints
    login.add(lblTitle, "grow 0, center");
    // login.add(new JLabel("Usuario"), "gapy 10");
    login.add(tiUsername, "");
    login.add(new JLabel("Contraseña"), "");
    login.add(pfPassword, "");
    login.add(btnLogin, "gapy 15");

    add(login);
  }

  public void toggleFieldErrorHint(JTextComponent component, boolean toggle) {
    // String outline = toggle ? FlatClientProperties.OUTLINE_ERROR : null;

    // component.putClientProperty(FlatClientProperties.OUTLINE, outline);
  }

  public TextInput getTiUsername() {
    return tiUsername;
  }

  public JPasswordField getPfPassword() {
    return pfPassword;
  }

  private JLabel lblTitle;
  private JPanel login;
  private TextInput tiUsername;
  private JPasswordField pfPassword;
  private JButton btnLogin;
}
