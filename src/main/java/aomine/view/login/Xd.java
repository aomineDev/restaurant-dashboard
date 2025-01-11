package aomine.view.login;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Xd extends JPanel {
  public Xd(LayoutManager layout) {
    super(layout);
    // setContentAreaFilled(false);
    // setBorder(new EmptyBorder(10, 10, 10, 10));
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(getBackground());
    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    g2.dispose();
    super.paintComponent(g);
  }
}