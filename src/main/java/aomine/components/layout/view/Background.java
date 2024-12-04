package aomine.components.layout.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

public class Background extends JPanel {
  public Background() {
    init();
  }

  private void init() {
    setOpaque(true);
    setLayout(new BorderLayout());
    putClientProperty(FlatClientProperties.STYLE,
      "border:5,5,5,5;" +
      "background: $Drawer.background"
    );
  }
}
