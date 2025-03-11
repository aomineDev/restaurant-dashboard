package aomine.view.admin.employee;

import java.time.LocalDate;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.input.MaskInput;
import aomine.components.input.PasswordInput;
import aomine.components.input.SelectInput;
import aomine.components.input.TextInput;
import aomine.controller.admin.EmployeeController;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.FormAction;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;

public class EmployeeForm extends JPanel implements View {
  private int formWidth;
  private FormAction action;

  public EmployeeForm(int formWidth, FormAction action, EmployeeController controller) {
    this.formWidth = formWidth;
    this.action = action;

    controller.setForm(this);

    initialize();

    controller.getRoleList().forEach(role -> cbRole.getInput().addItem(role));
  }

  @Override
  public void initComponents() {
    tiFirstName = new TextInput.TextInputBuilder()
        .setLabelText("Primer Nombre")
        .setPlaceholder("Inngrese el primer nombre")
        .withErrorLabel()
        .build();

    tiSecondtName = new TextInput.TextInputBuilder()
        .setLabelText("Segundo nombre")
        .setPlaceholder("Ingrese el segundo nombre")
        .withErrorLabel()
        .build();

    tiPaternalLastName = new TextInput.TextInputBuilder()
        .setLabelText("Apellido paterno")
        .setPlaceholder("Ingrese el apellido paterno")
        .withErrorLabel()
        .build();

    tiMaternalLastName = new TextInput.TextInputBuilder()
        .setLabelText("Apellido materno")
        .setPlaceholder("Ingrese el apellido materno")
        .withErrorLabel()
        .build();

    miDni = new MaskInput.MaskInputBuilder()
        .setLabelText("DNI")
        .withErrorLabel()
        .setMask("########", '-')
        .build();

    datePicker = new DatePicker();

    miBirthdate = new MaskInput.MaskInputBuilder()
        .setLabelText("Fecha de nacimiento")
        .withErrorLabel()
        .build();

    datePicker.setEditor(miBirthdate.getInput());
    datePicker.setUsePanelOption(true);
    datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
    datePicker.setCloseAfterSelected(true);

    miPhoneNumber = new MaskInput.MaskInputBuilder()
        .setLabelText("Celuluar")
        .withErrorLabel()
        .setMask("### ### ###", '-')
        .build();

    tiAddress = new TextInput.TextInputBuilder()
        .setLabelText("Dirección")
        .setPlaceholder("Ingrese la dirección")
        .withErrorLabel()
        .build();

    tiEmail = new TextInput.TextInputBuilder()
        .setLabelText("Email")
        .setPlaceholder("Ingrese el email")
        .withErrorLabel()
        .build();

    tiUsername = new TextInput.TextInputBuilder()
        .setLabelText("Usuario")
        .setPlaceholder("Ingrese el usuario")
        .withErrorLabel()
        .build();

    piPassword = new PasswordInput.PasswordInputBuilder()
        .setLabelText("Contraseña")
        .setPlaceholder("Ingrese la contraseña")
        .withErrorLabel()
        .build();

    cbRole = new SelectInput.SelectInputBuilder<Role>()
        .setLabelText("Rol")
        .build();

  }

  @Override
  public void setLayouts() {
    int formInsets = 24;
    int formGapx = 10;
    double cellWidth = (formWidth - (formInsets * 2) - (formGapx * 5)) / 6;
    String formLayoutConstraints = String.format("ins %d, gapx %d", formInsets, formGapx);
    String formColConstraints = String.format("[fill, %f]", cellWidth).repeat(6);

    setLayout(new MigLayout(formLayoutConstraints, formColConstraints));
  }

  @Override
  public void applyStyles() {
    miBirthdate.getInput().putClientProperty(FlatClientProperties.OUTLINE, null);

  }

  @Override
  public void applyEvents() {

    tiFirstName.onKeyTyped(e -> tiFirstName.clearError());
    tiSecondtName.onKeyTyped(e -> tiSecondtName.clearError());
    tiPaternalLastName.onKeyTyped(e -> tiPaternalLastName.clearError());
    tiMaternalLastName.onKeyTyped(e -> tiMaternalLastName.clearError());
    miDni.onKeyTyped(e -> miDni.clearError());
    miBirthdate.onChanged(e -> miBirthdate.clearError());
    miPhoneNumber.onKeyTyped(e -> miPhoneNumber.clearError());
    tiAddress.onKeyTyped(e -> tiAddress.clearError());
    tiEmail.onKeyTyped(e -> tiEmail.clearError());
    tiUsername.onKeyTyped(e -> tiUsername.clearError());
    piPassword.onKeyTyped(e -> piPassword.clearError());
  }

  @Override
  public void renderComponents() {
    add(tiFirstName.getLabel(), "span 3");
    add(tiSecondtName.getLabel(), "span 3, wrap");
    add(tiFirstName.getInput(), "span 3");
    add(tiSecondtName.getInput(), "span 3, wrap");
    add(tiFirstName.getErrorLabel(), "span 3");
    add(tiSecondtName.getErrorLabel(), "span 3, wrap");
    add(tiPaternalLastName.getLabel(), "span 3");
    add(tiMaternalLastName.getLabel(), "span 3, wrap");
    add(tiPaternalLastName.getInput(), "span 3");
    add(tiMaternalLastName.getInput(), "span 3, wrap");
    add(tiPaternalLastName.getErrorLabel(), "span 3");
    add(tiMaternalLastName.getErrorLabel(), "span 3, wrap");
    add(miDni.getLabel(), "span 2");
    add(miBirthdate.getLabel(), "span 2");
    add(miPhoneNumber.getLabel(), "span 2, wrap");
    add(miDni.getInput(), "span 2");
    add(miBirthdate.getInput(), "span 2");
    add(miPhoneNumber.getInput(), "wrap, span 2");
    add(miDni.getErrorLabel(), "span 2");
    add(miBirthdate.getErrorLabel(), "span 2");
    add(miPhoneNumber.getErrorLabel(), "span 2, wrap");
    add(tiAddress.getLabel(), "span, wrap");
    add(tiAddress.getInput(), "span, wrap");
    add(tiAddress.getErrorLabel(), "span, wrap");
    add(tiEmail.getLabel(), "span, wrap");
    add(tiEmail.getInput(), "span, wrap");
    add(tiEmail.getErrorLabel(), "span, wrap");
    add(tiUsername.getLabel(), "span 2");
    if (action == FormAction.ADD)
      add(piPassword.getLabel(), "span 2");
    add(cbRole.getLabel(), "span 2, wrap");
    add(tiUsername.getInput(), "span 2");
    if (action == FormAction.ADD)
      add(piPassword.getInput(), "span 2");
    add(cbRole.getInput(), "span 2, wrap");
    add(tiUsername.getErrorLabel(), "span 2");
    if (action == FormAction.ADD)
      add(piPassword.getErrorLabel(), "span 2");
  }

  public void fillForm(Employee employee) {
    tiFirstName.setText(employee.getFirstName());
    tiSecondtName.setText(employee.getSecondName());
    tiPaternalLastName.setText(employee.getPaternalLastname());
    tiMaternalLastName.setText(employee.getMaternalLastname());
    miDni.setText(employee.getDni() + "");
    miBirthdate.setText(employee.getBirthdateFomramtted());
    miPhoneNumber.setText(employee.getPhoneNumber() + "");
    tiAddress.setText(employee.getAddress());
    tiEmail.setText(employee.getEmail());
    tiUsername.setText(employee.getUsername());
    cbRole.getInput().setSelectedItem(employee.getRole());
  }

  public SelectInput<Role> getCbRole() {
    return cbRole;
  }

  public TextInput getTiFirstName() {
    return tiFirstName;
  }

  public TextInput getTiSecondtName() {
    return tiSecondtName;
  }

  public TextInput getTiPaternalLastName() {
    return tiPaternalLastName;
  }

  public TextInput getTiMaternalLastName() {
    return tiMaternalLastName;
  }

  public MaskInput getMiDni() {
    return miDni;
  }

  public DatePicker getDatePicker() {
    return datePicker;
  }

  public MaskInput getMiBirthdate() {
    return miBirthdate;
  }

  public MaskInput getMiPhoneNumber() {
    return miPhoneNumber;
  }

  public TextInput getTiAddress() {
    return tiAddress;
  }

  public TextInput getTiEmail() {
    return tiEmail;
  }

  public TextInput getTiUsername() {
    return tiUsername;
  }

  public PasswordInput getPiPassword() {
    return piPassword;
  }

  private TextInput tiFirstName;
  private TextInput tiSecondtName;
  private TextInput tiPaternalLastName;
  private TextInput tiMaternalLastName;
  private MaskInput miDni;
  private DatePicker datePicker;
  private MaskInput miBirthdate;
  private MaskInput miPhoneNumber;
  private TextInput tiAddress;
  private TextInput tiEmail;
  private TextInput tiUsername;
  private PasswordInput piPassword;
  private SelectInput<Role> cbRole;
}
