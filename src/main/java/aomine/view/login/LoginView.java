package aomine.view.login;

import java.awt.Cursor;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.GoatPanel;
import aomine.components.TextInput;
import aomine.controller.login.LoginController;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;

public class LoginView extends JPanel implements View {
  private LoginController controller;

  public LoginView() {
    controller = new LoginController(this);
    initialize();
  }

  @Override
  public void initComponents() {
    background = new GoatPanel.GoatPanelBuilder()
        .setPathFromResources("background/login.png")
        .setArc(30)
        .build();
    login = new JPanel();
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
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("fill, insets 0"));

    background.setLayout(new MigLayout("fill, insets 0", "[center]"));

    login.setLayout(new MigLayout("flowy, insets 35 45 30 45", "[fill, 280]"));
  }

  @Override
  public void applyStyles() {
    login.setOpaque(false);
    login.putClientProperty(FlatClientProperties.STYLE,
        "arc: 20;" +
            "[light]background: @background;" +
            // "[light]background: lighten(@background, 4%);" +
            "[dark]background: lighten(@background, 4%);");

    lblTitle.putClientProperty(FlatClientProperties.STYLE,
        "font: bold +10;" +
            "foreground: @primaryColor;");

    btnLogin.putClientProperty(FlatClientProperties.STYLE,
        "background: @accentColor;" +
            "foreground: @light;" +
            "font: bold;" +
            "borderWidth: 0;" +
            "focusWidth: 0;" +
            "innerFocusWidth: 0");
    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

    tiUsername.setLeftIcon("user.svg", 0.35f);
    tiPassword.setLeftIcon("password.svg", 0.35f);

  }

  @Override
  public void applyEvents() {
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
  }

  @Override
  public void renderComponents() {

    add(background, "grow");
    background.add(login);
    login.add(lblTitle, "grow 0, center");
    login.add(tiUsername.getLabel());
    login.add(tiUsername.getInput());
    login.add(tiUsername.getErrorLabel());
    login.add(tiPassword.getLabel());
    login.add(tiPassword.getInput());
    login.add(tiPassword.getErrorLabel());
    login.add(btnLogin, "gapy 10");
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

  private GoatPanel background;
  private JLabel lblTitle;
  private JPanel login;
  private TextInput tiUsername;
  private TextInput tiPassword;
  private JButton btnLogin;
}
