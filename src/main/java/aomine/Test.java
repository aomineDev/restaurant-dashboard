package aomine;

import java.awt.EventQueue;
import java.awt.Font;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import net.miginfocom.swing.MigLayout;

enum Days {
  MONDAY("lunes"), TUESDAY("martes"), WEDNESDAY("miércoles"), THURSDAY("jueves"), FRIDAY("viernes"), SATURDAY("sábado"),
  SUNDAY("domingo");

  private String name;

  Days(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}

class Padre {
  public void sayHello() {
    System.out.println("Hello Padre");
  }
}

class Hijo extends Padre {
  @Override
  public void sayHello() {
    System.out.println("Hello Hijo");
  }

  public <T> T get() {
    T valor = null;
    return valor;
  }
}

public class Test extends JFrame {
  public Test() {
    init();

    Hijo hijo = new Hijo();

    String a = hijo.get();

    System.out.println(a);
  }

  public void print(Object obj) {
    String txt = obj.getClass().getSimpleName();
    System.out.println(txt);

    if (obj instanceof Hijo)
      printf((Hijo) obj);
  }

  public void printf(Padre p) {
    p.sayHello();
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
        .setMask("## ## ## ##", '-')
        .build();

    JButton btn = new JButton("Check");
    JButton btn2 = new JButton("val");

    JPasswordField jpf = new JPasswordField();

    Days test = Days.MONDAY;

    if (test == Days.MONDAY) {
      System.out.println(test);
    }

    btn.addActionListener(e -> {
      // System.out.println("celuclar value: " + miDni.getInput().getValue());
      // System.out.println("celuclar text: " + miDni.getInput().getText());
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
