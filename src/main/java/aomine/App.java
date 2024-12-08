package aomine;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import aomine.components.layout.view.Background;
import raven.popup.GlassPanePopup;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

public class App extends JFrame {
  App() {
 		init();
  }

  private void init() {
    setTitle("Restaurant");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(1200, 700));
    setLocationRelativeTo(null);

    getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);

    setContentPane(new Background());
    GlassPanePopup.install(this);
    ViewManager.install(this);
    ViewManager.logout();
  }

  public static void main(String[] args) {
    FlatRobotoFont.install();
    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

    FlatLaf.registerCustomDefaultsSource("aomine.themes");

    FlatMacLightLaf.setup();
        
    EventQueue.invokeLater(() -> new App().setVisible(true));
  }
}