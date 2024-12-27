package aomine;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import aomine.components.TextInput;
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

    JPanel panel = new JPanel();
    panel.setLayout(new MigLayout("fill", "[fill]"));
    // TextInput textInput = new TextInput.TextInputBuilder()
    // .placeholder("Enter your name")
    // .errorLabel()
    // // .label("Name")
    // .build();

    // // textInput.setFieldErrorHint(true);
    // textInput.onChanged(e -> {
    // textInput.setLblErrorText("asd");

    // });

    // JButton btn = new JButton("Test");
    // btn.addActionListener(e -> {
    // textInput.setLblErrorText("");
    // // textInput.setLblErrorVisible(false);
    // });

    // panel.add(textInput);
    // panel.add(btn);
    // setContentPane(panel);
    TextInput pf = new TextInput.TextInputBuilder()
        .setPlaceholder("Enter your password")
        .setLabelText("Password")
        .withErrorLabel()
        .setPassword(true)
        .build();

    TextInput tf = new TextInput.TextInputBuilder()
        .setPlaceholder("Enter your username")
        .setLabelText("Username")
        .withErrorLabel()
        .build();
    JButton btn = new JButton("Test");
    btn.addActionListener(e -> {
      System.out.println(pf.getText());
      System.out.println(tf.getText());
      tf.setLblErrorText("error");
    });
    panel.add(pf.getInput());
    panel.add(tf.getInput());
    panel.add(btn);
    setContentPane(panel);
  }

  public static void main(String[] args) {
    FlatRobotoFont.install();
    UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

    FlatMacLightLaf.setup();

    EventQueue.invokeLater(() -> new Test().setVisible(true));
  }
}
