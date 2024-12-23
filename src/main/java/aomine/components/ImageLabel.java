package aomine.components;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ImageLabel extends JLabel {
  public Image image;
  public String imagePath;
  public boolean isResizable;

  public ImageLabel(ImageLabelBuilder builder) {
    this.imagePath = builder.imagePath;
    this.isResizable = builder.isResizable;

    setImage();
  }

  private void setImage() {
    image = new ImageIcon(this.imagePath).getImage();

    SwingUtilities.invokeLater(this::setScaledImage);

    if (isResizable) {
      addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
          setScaledImage();
        }
      });
    }
  }

  public void setScaledImage() {
    Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    setIcon(new ImageIcon(scaledImage));
  }

  class ImageLabelBuilder {
    private String imagePath;
    private boolean isResizable;

    public ImageLabelBuilder() {
      this.isResizable = false;
    }

    public ImageLabelBuilder setPathFromRoot(String imagePath) {
      this.imagePath = new File("uploads/" + imagePath).getAbsolutePath();

      return this;
    }

    public ImageLabelBuilder setPathFromResources(String imagePath) {
      this.imagePath = getClass().getResource("/aomine/images/" + imagePath).getPath();

      return this;
    }

    public ImageLabelBuilder withResizable() {
      this.isResizable = true;

      return this;
    }

    public ImageLabel build() {
      return new ImageLabel(this);
    }
  }
}
