package aomine.view.login;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.controller.login.LoginController;
import net.miginfocom.swing.MigLayout;

public class LoginView extends JPanel {
  private BufferedImage backgroundImage;
  private LoginController controller;

  public LoginView() {
    controller = new LoginController(this);
    setBackgrondImage(getClass().getResource("/aomine/images/background/login.png").getPath());
    init();
  }

  public void init() {
    // Main Layout
    setLayout(new MigLayout("fill", "[center]"));

    // Definition of components
    container = new JPanel(new MigLayout("wrap, insets 35 45 30 45", "[fill, 280]"));
    lblTitle = new JLabel("Bienvenido!");
    tfUsername = new JTextField();
    pfPassword = new JPasswordField();
    btnLogin = new JButton("Login");

    // Styles
    container.putClientProperty(FlatClientProperties.STYLE, 
      "arc: 20;" +
      "[light]background: @background;" +
      "[dark]background: lighten(@background, 3%);"
    );

    lblTitle.putClientProperty(FlatClientProperties.STYLE, 
      "font: bold +10;" +
      "foreground: #7E3819;"
    );

    tfUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingrese su usuario");
    pfPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Ingrese su contraseña");
    pfPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
    btnLogin.putClientProperty(FlatClientProperties.STYLE, 
      "background: #7E3819;" +
      "foreground: #ffffff;" +
      "font: bold;" +
      "borderWidth: 0;" +
      "focusWidth: 0;" +
      "innerFocusWidth: 0"
    );
    btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Events
    btnLogin.addActionListener(controller::handleLoginClick);

    // Constraints
    container.add(lblTitle, "grow 0, center");
    container.add(new JLabel("Usuario"), "gapy 10");
    container.add(tfUsername, "");
    container.add(new JLabel("Contraseña"), "gapy 10");
    container.add(pfPassword, "");
    container.add(btnLogin, "gapy 15");

    add(container);
  }

  public void setBackgrondImage(String imagePath) {
    try {
      backgroundImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      System.err.println("Error al cargar la imagen: " + imagePath);
        e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if(backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
  }

  public JTextField getTfUsername() {
    return tfUsername;
  }

  public JPasswordField getPfPassword() {
    return pfPassword;
  }

  private JLabel lblTitle;
  private JPanel container;
  private JTextField tfUsername;
  private JPasswordField pfPassword;
  private JButton btnLogin;

  
}
