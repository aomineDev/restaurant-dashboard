package aomine.view.login;

import java.awt.Cursor;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.GoatPanel;
import aomine.components.input.GoatTextInput;
import aomine.components.input.PasswordInput;
import aomine.components.input.TextInput;
import aomine.controller.login.LoginController;
import aomine.utils.Form;
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
    piPassword = new PasswordInput.PasswordInputBuilder()
        .setPlaceholder("Ingrese su contraseña")
        .setLabelText("Contraseña")
        .withErrorLabel()
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
    piPassword.setLeftIcon("password.svg", 0.35f);
  }

  @Override
  public void applyEvents() {
    tiUsername.onKeyTyped(e -> Form.cleanErrorOnInput(tiUsername));

    piPassword.onKeyTyped(e -> {
      if (e.getKeyChar() == '\n')
        return;

      Form.cleanErrorOnInput(piPassword);
    });

    piPassword.onKeyPressed(e -> {
      if (e.getKeyCode() == KeyEvent.VK_ENTER)
        controller.handleFastLogin(null);
    });

    btnLogin.addActionListener(controller::handleFastLogin);
  }

  @Override
  public void renderComponents() {
    add(background, "grow");
    background.add(login);
    login.add(lblTitle, "grow 0, center");
    login.add(tiUsername.getLabel());
    login.add(tiUsername.getInput());
    login.add(tiUsername.getErrorLabel());
    login.add(piPassword.getLabel());
    login.add(piPassword.getInput());
    login.add(piPassword.getErrorLabel());
    login.add(btnLogin, "gapy 10");
  }

  public TextInput getTiUsername() {
    return tiUsername;
  }

  public PasswordInput getPiPassword() {
    return piPassword;
  }

  public JButton getBtnLogin() {
    return btnLogin;
  }

  private GoatPanel background;
  private JLabel lblTitle;
  private JPanel login;
  private TextInput tiUsername;
  private PasswordInput piPassword;
  private JButton btnLogin;
}
