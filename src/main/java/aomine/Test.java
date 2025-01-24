package aomine;

import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import aomine.components.GoatPanel;
import aomine.components.input.TextInput;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;

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
    panel.setLayout(new MigLayout("flowy, debug", ""));
    DatePicker dp = new DatePicker();
    JFormattedTextField ft = new JFormattedTextField(createFormatter("####", '#'));
    ft.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "1111");
    // dp.setEditor(ft);
    dp.setUsePanelOption(true);
    dp.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
    panel.add(ft, "w 200");
    TextInput sdf = new TextInput.TextInputBuilder()
        .setLabelText("asd")
        .setPlaceholder("xdddddd")
        .build();
    panel.add(sdf.getLabel());
    panel.add(sdf.getInput());
    setContentPane(panel);
  }

  private MaskFormatter createFormatter(String mask, char maskPlaceholder) {
    try {
      MaskFormatter formatter = new MaskFormatter(mask);
      formatter.setPlaceholderCharacter(maskPlaceholder);

      return formatter;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    FlatRobotoFont.install();

    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,
        13));

    FlatLightLaf.setup();

    EventQueue.invokeLater(() -> new Test().setVisible(true));
  }

  // private void
}
