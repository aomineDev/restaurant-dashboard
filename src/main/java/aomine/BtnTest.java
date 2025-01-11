package aomine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BtnTest extends JPanel {
  private int arc;
  private Image image;

  public BtnTest(LayoutManager layout, int arc) {
    super(layout);
    this.arc = arc;
    setOpaque(false);

  }

  public BtnTest(int arc) {
    this.arc = arc;
    this.image = new ImageIcon(getClass().getResource("/aomine/images/banner/chef.png")).getImage();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
    super.paintComponent(g2);
    // g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    g2.dispose();
  }
}
