package aomine;

import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import aomine.components.GoatPanel;
import aomine.components.textInput.TextInput;
import net.miginfocom.swing.MigLayout;

public class Test extends JFrame {
  public Test() {
    init();
  }

  private void init() {
    setTitle("Test");
    setSize(400, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    System.out.println(System.getProperty("user.home"));
    String imagePath = "react.png";
    System.out.println(Paths.get("uploads/images",
        imagePath).toAbsolutePath().toString());

    GoatPanel panel = new GoatPanel.GoatPanelBuilder()
        .setPathFromResources("background/react.png")
        .build();
    panel.setLayout(new MigLayout("fill", "[center]"));
    TextInput input = new TextInput.TextInputBuilder()
        .setMask("########")
        .setLabelText("mask")
        .build();
    panel.add(input.getInput());
    input.onChanged(e -> {
      System.out.println(input.getText());
    });
    setContentPane(panel);
  }

  public static void main(String[] args) {
    FlatRobotoFont.install();

    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,
        13));

    FlatLightLaf.setup();

    EventQueue.invokeLater(() -> new Test().setVisible(true));
  }
}
