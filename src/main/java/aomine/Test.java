package aomine;

import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.util.LoggingFacade;

import aomine.components.GoatPanel;
import aomine.components.input.MaskInput;
import aomine.components.input.TextInput;
import aomine.dao.EmployeeDAO;
import aomine.dao.RoleDAO;
import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import net.miginfocom.swing.MigLayout;

public class Test extends JFrame {

  public Test() {
    init();
  }

  public boolean asd(MaskInput field, String text) {
    try {
      field.getInput().getFormatter().stringToValue(text);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private void init() {
    setTitle("Test");
    setSize(500, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    // Hibernate.install();

    System.out.println(System.getProperty("user.home"));
    String imagePath = "react.png";
    System.out.println(Paths.get("uploads/images",
        imagePath).toAbsolutePath().toString());

    GoatPanel panel = new GoatPanel.GoatPanelBuilder()
        .build();

    panel.setLayout(new MigLayout("flowy"));

    TextInput tiName = new TextInput.TextInputBuilder()
        .setLabelText("Nombre")
        .withErrorLabel()
        .setPlaceholder("dni")
        .build();

    MaskInput miDni = new MaskInput.MaskInputBuilder()
        .setLabelText("DNI")
        .withErrorLabel()
        .setMask("##/##/##/##", '-')
        .build();

    JButton btn = new JButton("Check");
    JButton btn2 = new JButton("val");

    JPasswordField jpf = new JPasswordField();

    btn.addActionListener(e -> {
      String t = "asdasdasd";
      miDni.getInput().setText(t);

      if (asd(miDni, miDni.getInput().getText())) {
        miDni.getInput().setValue(miDni.getInput().getText());
      }

      // Validate validate = new Validate();

      // validate.setElement(tiName)
      // .isRequired("El nombre es requerido")
      // .isUnique("duplicado", Employee.class, "dni", 2);

      // if (!validate.isValid()) {
      // for (ValError error : validate.getValErrorList()) {
      // error.getComponent().setLabelErrorText(error.getMessage());
      // }
      // } else {
      // System.out.println("Valid");
      // }
    });

    btn2.addActionListener(e -> {
      System.out.println(miDni.getText());
    });

    tiName.onChanged((e) -> {
      tiName.setLabelErrorText("");
    });

    panel.add(tiName.getLabel());
    panel.add(tiName.getInput(), "w 200");
    panel.add(tiName.getErrorLabel());
    panel.add(miDni.getLabel());
    panel.add(miDni.getInput(), "w 200");
    panel.add(miDni.getErrorLabel());
    panel.add(jpf);
    panel.add(btn);
    panel.add(btn2);
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
