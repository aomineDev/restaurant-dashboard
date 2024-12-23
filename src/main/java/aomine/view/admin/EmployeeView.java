package aomine.view.admin;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import aomine.components.ImagePanel;
import aomine.components.layout.view.SimpleView;
import net.miginfocom.swing.MigLayout;

public class EmployeeView extends SimpleView {
  public EmployeeView() {
    viewOpen();
  }

  @Override
  public void viewOpen() {
    setLayout(new MigLayout("debug", "[210:30%:450][grow]", "[grow]"));

    ImagePanel banner = new ImagePanel.ImagePanelBuilder()
        .setPathFromResources("banner/employee.png")
        .build();
    // ImageLabel banner = new ImageLabel.ImageLabelBuilder()
    // .setPathFromResources("banner/employee.png")
    // .withResizable()
    // .build();

    add(banner, "grow");
    // add(banner, "grow, hmin 0, hmin 0");
    add(new JLabel("tabla"));
  }
}
