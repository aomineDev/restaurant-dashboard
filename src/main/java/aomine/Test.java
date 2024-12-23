package aomine;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

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
    TextInput textInput = new TextInput.TextInputBuilder()
        .placeholder("Enter your name")
        .errorLabel()
        // .label("Name")
        .build();

    // textInput.setFieldErrorHint(true);
    textInput.onChanged(e -> {
      textInput.setLblErrorText("asd");

    });

    JButton btn = new JButton("Test");
    btn.addActionListener(e -> {
      textInput.setLblErrorText("");
      // textInput.setLblErrorVisible(false);
    });

    panel.add(textInput);
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
