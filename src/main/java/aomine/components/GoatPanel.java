package aomine.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GoatPanel extends JPanel {
  private String imagePath;
  private BufferedImage image;
  private boolean aspectRatio;
  private int arc;

  public GoatPanel(GoatPanelBuilder builder) {
    this.imagePath = builder.imagePath;
    this.aspectRatio = builder.aspectRatio;
    this.arc = builder.arc;

    setImage();
  }

  private void setImage() {
    if (imagePath == null)
      return;

    try {
      image = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setImagePath(GoatPanelBuilder builder) {
    this.imagePath = builder.imagePath;
    setImage();
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), this.arc, this.arc));

    super.paintComponent(g2);

    if (image == null) {
      g2.dispose();
      return;
    }

    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int imgWidth = panelWidth;
    int imgHeight = panelHeight;
    int x = 0;
    int y = 0;

    if (aspectRatio) {
      imgWidth = image.getWidth(this);
      imgHeight = image.getHeight(this);

      double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);

      imgWidth *= scale;
      imgHeight *= scale;

      x = (panelWidth - imgWidth) / 2;
      y = (panelHeight - imgHeight) / 2;
    }

    g2.drawImage(image, x, y, imgWidth, imgHeight, this);

    g2.dispose();
  }

  public static class GoatPanelBuilder {
    private String imagePath;
    private boolean aspectRatio;
    private int arc;

    public GoatPanelBuilder() {
      aspectRatio = false;
      arc = 0;
    }

    public GoatPanelBuilder setPathFromRoot(String imagePath) {
      this.imagePath = Paths.get("uploads/images", imagePath).toAbsolutePath().toString();
      // this.imagePath = new File("uploads/images" + imagePath).getAbsolutePath();

      return this;
    }

    public GoatPanelBuilder setPathFromResources(String imagePath) {
      this.imagePath = getClass().getResource("/aomine/images/" + imagePath).getPath();

      return this;
    }

    public GoatPanelBuilder keepAspectRatio() {
      this.aspectRatio = true;

      return this;
    }

    public GoatPanelBuilder setArc(int arc) {
      this.arc = arc;

      return this;
    }

    public GoatPanel build() {
      return new GoatPanel(this);
    }
  }
}
