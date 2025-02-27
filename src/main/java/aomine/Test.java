package aomine;

import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.util.LoggingFacade;

import aomine.components.GoatPanel;
import aomine.components.input.TextInput;
import net.miginfocom.swing.MigLayout;

abstract class Padre {
  public abstract Padre self();
}

class Hijo extends Padre {
  @Override
  public Hijo self() {
    return this;
  }
}

public class Test extends JFrame {
  public Test() {
    // init();
    Hijo hijo = new Hijo();

  }

  private void init() {
    setTitle("Test");
    setSize(500, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    System.out.println(System.getProperty("user.home"));
    String imagePath = "react.png";
    System.out.println(Paths.get("uploads/images",
        imagePath).toAbsolutePath().toString());

    GoatPanel panel = new GoatPanel.GoatPanelBuilder()
        .build();

    panel.setLayout(new MigLayout());

    TextInput tiName = new TextInput.TextInputBuilder()
        .setLabelText("Nombre")
        .setPlaceholder("Ingrese su nombre")
        .build();

    JButton btn = new JButton("switch");
    btn.addActionListener(e -> switchTheme());

    JButton btn2 = new JButton("add panel");

    container = new JPanel(new MigLayout());
    tfAge = new JTextField();
    container.add(new JLabel("Age"));
    container.add(tfAge);

    btn2.addActionListener(e -> {
      // SwingUtilities.updateComponentTreeUI(container);
      panel.add(container);
      panel.repaint();
      panel.revalidate();
    });

    panel.add(btn, "wrap");
    panel.add(tiName.getLabel());
    panel.add(tiName.getInput(), "wrap");
    panel.add(btn2, "wrap");
    setContentPane(panel);
  }

  private void switchTheme() {
    String lafClassName = FlatLaf.isLafDark() ? FlatLightLaf.class.getName() : FlatDarkLaf.class.getName();
    FlatAnimatedLafChange.showSnapshot();
    try {
      UIManager.setLookAndFeel(lafClassName);
    } catch (Exception e) {
      LoggingFacade.INSTANCE.logSevere(null, e);
    }
    FlatLaf.updateUI();
    FlatAnimatedLafChange.hideSnapshotWithAnimation();

  }

  public static void main(String[] args) {
    FlatRobotoFont.install();

    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,
        13));

    FlatLightLaf.setup();

    EventQueue.invokeLater(() -> new Test().setVisible(true));
  }

  private JPanel container;
  private JTextField tfAge;
}
