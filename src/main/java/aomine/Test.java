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
import aomine.dao.EmployeeDAO;
import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import net.miginfocom.swing.MigLayout;

public class Test extends JFrame {
  public Test() {
    init();
  }

  private void init() {
    setTitle("Test");
    setSize(500, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Hibernate.install();
    Validate val = new Validate();
    EmployeeDAO dao = new EmployeeDAO();

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

    JButton btn = new JButton("validate");
    btn.addActionListener(e -> {
      if (dao.isEmpty())
        System.out.println("Vacio");
      else
        System.out.println("Hay datos en la db");
      System.out.println(Employee.class.getSimpleName());

      val.reset();
      val.setElement(tiName)
          .isRequired("Usuario es requerido")
          .isUnique("Error!!! >:D", Employee.class, "username");

      if (val.getIsValid()) {
        System.out.println("Valid");
      } else {
        for (ValError error : val.getValErrorList()) {
          System.out.println(error.getMessage());
          error.getInput().setErrorHint(true);
        }
      }
    });

    tiName.onChanged((e) -> {
      tiName.setErrorHint(false);
    });

    panel.add(tiName.getLabel());
    panel.add(tiName.getInput(), "wrap, w 200");
    panel.add(btn);
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
