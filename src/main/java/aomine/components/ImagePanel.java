package aomine.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
  private String imagePath;
  private BufferedImage image;
  private boolean aspectRatio;

  public ImagePanel(ImagePanelBuilder builder) {
    this.imagePath = builder.imagePath;
    this.aspectRatio = builder.aspectRatio;
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

  public void setImagePath(ImagePanelBuilder builder) {
    this.imagePath = builder.imagePath;
    setImage();
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (image == null)
      return;

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

    g.drawImage(image, x, y, imgWidth, imgHeight, this);
  }

  public static class ImagePanelBuilder {
    private String imagePath;
    private boolean aspectRatio;

    public ImagePanelBuilder() {
      aspectRatio = false;
    }

    public ImagePanelBuilder setPathFromRoot(String imagePath) {
      this.imagePath = Paths.get("uploads/images", imagePath).toAbsolutePath().toString();
      // this.imagePath = new File("uploads/" + imagePath).getAbsolutePath();

      return this;
    }

    public ImagePanelBuilder setPathFromResources(String imagePath) {
      this.imagePath = getClass().getResource("/aomine/images/" + imagePath).getPath();

      return this;
    }

    public ImagePanelBuilder keepAspectRatio() {
      this.aspectRatio = true;

      return this;
    }

    public ImagePanel build() {
      return new ImagePanel(this);
    }
  }
}
