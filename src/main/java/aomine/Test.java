package aomine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import aomine.components.GoatPanel;
import aomine.components.input.SelectInput;
import aomine.components.input.TextInput;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;

public class Test extends JFrame {
  public Test() {
    init();
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

    JComboBox<String> s = new JComboBox<>();
    JTextField t = new JTextField();
    t.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "asd");
    panel.setLayout(new MigLayout());

    panel.add(s);
    panel.add(t);

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
