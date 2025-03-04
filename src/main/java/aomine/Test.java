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
import aomine.components.input.MaskInput;
import aomine.components.input.TextInput;
import aomine.dao.EmployeeDAO;
import aomine.database.Hibernate;
import aomine.model.Employee;
import aomine.utils.validate.ValError;
import aomine.utils.validate.Validate;
import net.miginfocom.swing.MigLayout;

abstract class Padre<T> {
  protected T t;

  public abstract void print();

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }
}

class Hijo1 extends Padre<String> {
  Hijo1() {
    this.t = "Hijo1";
  }

  @Override
  public void print() {
    System.out.println("Hola: " + t);
  }
}

class Hijo2 extends Padre<Integer> {
  Hijo2() {
    this.t = 2;
  }

  @Override
  public void print() {
    System.out.println("Hola: Hijo" + t);
  }
}

public class Test extends JFrame {
  public Test() {
    init();
  }

  public void print(Padre<? extends String> p) {
    p.print();
  }

  private void init() {
    setTitle("Test");
    setSize(500, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Hibernate.install();

    System.out.println(System.getProperty("user.home"));
    String imagePath = "react.png";
    System.out.println(Paths.get("uploads/images",
        imagePath).toAbsolutePath().toString());

    GoatPanel panel = new GoatPanel.GoatPanelBuilder()
        .build();

    panel.setLayout(new MigLayout());

    MaskInput tiName = new MaskInput.MaskInputBuilder()
        .setLabelText("Nombre")
        .setMask("###", '-')
        .build();

    JButton btn = new JButton("Reset");
    btn.addActionListener(e -> {
      tiName.setText("");
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
