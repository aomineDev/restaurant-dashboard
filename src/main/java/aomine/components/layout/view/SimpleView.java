package aomine.components.layout.view;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

public class SimpleView extends JPanel {
  public SimpleView() {
    init();
  }

  private void init() {
    putClientProperty(FlatClientProperties.STYLE,
        "background: null");
  }

  public void viewInitAndOpen() {

  }

  public void viewOpen() {

  }

  public void viewRefresh() {

  }

  public boolean viewClose() {
    return true;
  }
}
