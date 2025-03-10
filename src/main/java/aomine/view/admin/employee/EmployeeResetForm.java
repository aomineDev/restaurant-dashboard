package aomine.view.admin.employee;

import javax.swing.JPanel;

import aomine.components.input.PasswordInput;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;

public class EmployeeResetForm extends JPanel implements View {
  public EmployeeResetForm() {
    initialize();
  }

  @Override
  public void initComponents() {
    piNewPassword = new PasswordInput.PasswordInputBuilder()
        .setLabelText("Nueva contraseña")
        .setPlaceholder("Ingrese la nueva contraseña")
        .withErrorLabel()
        .build();
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 24, flowy, fillx", "[fill]"));
  }

  @Override
  public void applyStyles() {

  }

  @Override
  public void applyEvents() {
    piNewPassword.onKeyTyped(e -> piNewPassword.clearError());
  }

  @Override
  public void renderComponents() {
    add(piNewPassword.getLabel());
    add(piNewPassword.getInput());
    add(piNewPassword.getErrorLabel());
  }

  // getters
  public PasswordInput getPiNewPassword() {
    return piNewPassword;
  }

  private PasswordInput piNewPassword;
}
