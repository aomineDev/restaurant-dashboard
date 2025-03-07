package aomine.view.admin;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.components.GoatPanel;
import aomine.components.input.GoatInput;
import aomine.components.input.MaskInput;
import aomine.components.input.PasswordInput;
import aomine.components.input.SelectInput;
import aomine.components.input.TextInput;
import aomine.components.layout.view.SimpleView;
import aomine.controller.admin.EmployeeController;
import aomine.model.Employee;
import aomine.model.Role;
import aomine.utils.Form;
import aomine.utils.FormAction;
import aomine.utils.GoatList;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.SimplePopupBorder;
import raven.popup.component.SimplePopupBorderOption;

public class EmployeeView extends SimpleView implements View {
  private int formWidth;
  private EmployeeController controller;
  private GoatList<GoatInput<? extends JComponent>> formInputList;
  private int rowSelected;

  public EmployeeView() {
    formWidth = 800;
    controller = new EmployeeController(this);

    initialize();

    applyTableStyles();
    setTableModel();
    setTableData();

    fillFormInputList();
  }

  @Override
  public void initComponents() {
    banner = new GoatPanel.GoatPanelBuilder()
        .setPathFromResources("banner/employee.png")
        .setArc(20)
        .build();
    container = new JPanel();

    lblTitle = new JLabel("Empleados");
    tableContainer = new JPanel();

    tiSearch = new TextInput.TextInputBuilder()
        .setPlaceholder("Nombre")
        .build();
    btnAdd = new JButton("Nuevo");
    btnEdit = new JButton("Editar");
    btnDelete = new JButton("Eliminar");
    tableEmployee = new JTable();

    // Form
    form = new JPanel();

    tiFirstName = new TextInput.TextInputBuilder()
        .setLabelText("Primer nombre")
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

    controller.setAllRoles();
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 0", "[fill, 210:30%:450]20[fill, grow]", "[fill, grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("insets 16, fillx", "[]push[][][]", "[]20[grow]"));

    applyFormLayout();
  }

  @Override
  public void applyStyles() {
    lblTitle.putClientProperty(FlatClientProperties.STYLE,
        "font: bold 32;" +
            "foreground: @primaryColor;");

    tableContainer.putClientProperty(FlatClientProperties.STYLE, "background: $Table.background;" +
        "[light]border:0,0,0,0,shade(@background,5%),,20;" +
        "[dark]border:0,0,0,0,tint(@background,5%),,20;");

    tiSearch.getInput().putClientProperty(FlatClientProperties.STYLE, "background: @background");

    btnAdd.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    btnEdit.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    btnDelete.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

    miBirthdate.getInput().putClientProperty(FlatClientProperties.OUTLINE, null);

    setIcons();
  }

  @Override
  public void applyEvents() {
    btnAdd.addActionListener(evt -> {
      this.piPassword.getInput().setEnabled(true);

      showFormPopup(FormAction.ADD);
    });

    btnEdit.addActionListener(e -> {
      this.fillForm(controller.getEmployee(rowSelected));

      this.piPassword.getInput().setEnabled(false);

      this.showFormPopup(FormAction.EDIT);
    });

    // btn delete
    btnDelete.addActionListener(e -> {
      controller.handleDeleteEmployee(rowSelected);
    });

    tableEmployee.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        rowSelected = tableEmployee.rowAtPoint(evt.getPoint());
      }
    });

    // Clean Inputs after error
    tiFirstName.onKeyTyped(e -> Form.cleanErrorOnInput(tiFirstName));
    tiSecondtName.onKeyTyped(e -> Form.cleanErrorOnInput(tiSecondtName));
    tiPaternalLastName.onKeyTyped(e -> Form.cleanErrorOnInput(tiPaternalLastName));
    tiMaternalLastName.onKeyTyped(e -> Form.cleanErrorOnInput(tiMaternalLastName));
    miDni.onKeyTyped(e -> Form.cleanErrorOnInput(miDni));
    miBirthdate.onChanged(e -> Form.cleanErrorOnInput(miBirthdate));
    miPhoneNumber.onKeyTyped(e -> Form.cleanErrorOnInput(miPhoneNumber));
    tiAddress.onKeyTyped(e -> Form.cleanErrorOnInput(tiAddress));
    tiEmail.onKeyTyped(e -> Form.cleanErrorOnInput(tiEmail));
    tiUsername.onKeyTyped(e -> Form.cleanErrorOnInput(tiUsername));
    piPassword.onKeyTyped(e -> Form.cleanErrorOnInput(piPassword));
  }

  @Override
  public void renderComponents() {
    add(banner);
    add(container);

    container.add(lblTitle);
    container.add(tableContainer, "grow");

    tableContainer.add(tiSearch.getInput(), "w 200");
    tableContainer.add(btnAdd);
    tableContainer.add(btnEdit);
    tableContainer.add(btnDelete, "wrap");
    tableContainer.add(new JScrollPane(tableEmployee), "span, grow");

    form.add(tiFirstName.getLabel(), "span 3");
    form.add(tiSecondtName.getLabel(), "span 3, wrap");
    form.add(tiFirstName.getInput(), "span 3");
    form.add(tiSecondtName.getInput(), "span 3, wrap");
    form.add(tiFirstName.getErrorLabel(), "span 3");
    form.add(tiSecondtName.getErrorLabel(), "span 3, wrap");
    form.add(tiPaternalLastName.getLabel(), "span 3");
    form.add(tiMaternalLastName.getLabel(), "span 3, wrap");
    form.add(tiPaternalLastName.getInput(), "span 3");
    form.add(tiMaternalLastName.getInput(), "span 3, wrap");
    form.add(tiPaternalLastName.getErrorLabel(), "span 3");
    form.add(tiMaternalLastName.getErrorLabel(), "span 3, wrap");
    form.add(miDni.getLabel(), "span 2");
    form.add(miBirthdate.getLabel(), "span 2");
    form.add(miPhoneNumber.getLabel(), "span 2, wrap");
    form.add(miDni.getInput(), "span 2");
    form.add(miBirthdate.getInput(), "span 2");
    form.add(miPhoneNumber.getInput(), "wrap, span 2");
    form.add(miDni.getErrorLabel(), "span 2");
    form.add(miBirthdate.getErrorLabel(), "span 2");
    form.add(miPhoneNumber.getErrorLabel(), "span 2, wrap");
    form.add(tiAddress.getLabel(), "span, wrap");
    form.add(tiAddress.getInput(), "span, wrap");
    form.add(tiAddress.getErrorLabel(), "span, wrap");
    form.add(tiEmail.getLabel(), "span, wrap");
    form.add(tiEmail.getInput(), "span, wrap");
    form.add(tiEmail.getErrorLabel(), "span, wrap");
    form.add(tiUsername.getLabel(), "span 2");
    form.add(piPassword.getLabel(), "span 2");
    form.add(cbRole.getLabel(), "span 2, wrap");
    form.add(tiUsername.getInput(), "span 2");
    form.add(piPassword.getInput(), "span 2");
    form.add(cbRole.getInput(), "span 2, wrap");
    form.add(tiUsername.getErrorLabel(), "span 2");
    form.add(piPassword.getErrorLabel(), "span 2");
  }

  private void applyFormLayout() {
    int formInsets = 24;
    int formGapx = 10;
    double cellWidth = (formWidth - (formInsets * 2) - (formGapx * 5)) / 6;
    String formLayoutConstraints = String.format("ins %d, gapx %d", formInsets, formGapx);
    String formColConstraints = String.format("[fill, %f]", cellWidth).repeat(6);

    form.setLayout(new MigLayout(formLayoutConstraints, formColConstraints));
  }

  private void applyTableStyles() {
    // Change scroll style
    JScrollPane scroll = (JScrollPane) tableEmployee.getParent().getParent();
    scroll.setBorder(BorderFactory.createEmptyBorder());
    scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
        "background: $Table.background;" +
            "track: $Table.background;" +
            "trackArc: 999;" +
            "thumbArc: 999;" +
            "width: 8;");

    // Add styles class to the table
    JTableHeader tableHeader = tableEmployee.getTableHeader();

    tableHeader.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    tableEmployee.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

    tableHeader.setReorderingAllowed(false);
  }

  private void setTableModel() {
    Object[] columns = { "ID", "P. Nombre", "S. Nombre", "Apellido P.", "Apellido M.", "DNI", "Fecha de nacimiento",
        "Celular", "Dirección", "Email", "Usuario", "Rol" };

    DefaultTableModel model = new DefaultTableModel(null, columns) {
      boolean[] canEdit = { false, false, false, false, false, false, false, false, false, false, false, false };

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    };

    tableEmployee.setModel(model);

    // Ocultar la columna ID
    TableColumnModel tcm = tableEmployee.getColumnModel();
    tcm.removeColumn(tcm.getColumn(0));
  }

  public void setTableData() {
    DefaultTableModel model = (DefaultTableModel) tableEmployee.getModel();

    model.setRowCount(0);

    controller.getEmployeeList().forEach(employee -> {
      Object[] row = {
          employee.getPersonId(),
          employee.getFirstName(),
          employee.getSecondName(),
          employee.getPaternalLastname(),
          employee.getMaternalLastname(),
          employee.getDni(),
          employee.getBirthdateFomramtted(),
          employee.getPhoneNumber(),
          employee.getAddress(),
          employee.getEmail(),
          employee.getUsername(),
          employee.getRole().getName()
      };

      model.addRow(row);
    });
  }

  private void setIcons() {
    String basePath = "aomine/icons/";
    float scale = 0.35f;

    HashMap<String, JButton> buttonMap = new HashMap<>();
    buttonMap.put("add", btnAdd);
    buttonMap.put("edit", btnEdit);
    buttonMap.put("delete", btnDelete);

    buttonMap.forEach((key, btn) -> {
      btn.setIcon(new FlatSVGIcon(basePath + key + ".svg", scale));
      btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    });

    tiSearch.setRightIcon("search.svg", scale);
  }

  private void fillFormInputList() {
    formInputList = new GoatList<>();

    formInputList.add(tiFirstName);
    formInputList.add(tiSecondtName);
    formInputList.add(tiPaternalLastName);
    formInputList.add(tiMaternalLastName);
    formInputList.add(miDni);
    formInputList.add(miBirthdate);
    formInputList.add(miPhoneNumber);
    formInputList.add(tiAddress);
    formInputList.add(tiEmail);
    formInputList.add(tiUsername);
    formInputList.add(piPassword);
    formInputList.add(cbRole);
  }

  private void fillForm(Employee employee) {
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

  private void showFormPopup(FormAction action) {

    String actionBtn = switch (action) {
      case ADD -> "Agregar";
      case EDIT -> "Editar";
    };

    String title = switch (action) {
      case ADD -> "Agregar Empleado";
      case EDIT -> "Editar Empleado";
    };

    int height = 550;
    int scrollWidth = 10;
    int popupWidht = formWidth + scrollWidth;

    SimplePopupBorderOption borderOption = new SimplePopupBorderOption();

    borderOption.useScroll();
    borderOption.setWidth(popupWidht);

    String[] actions = { "Cancelar", actionBtn };

    PopupCallbackAction callbackAction = (ctrl, act) -> {
      if (act == 0) {
        GlassPanePopup.closePopup("employeeForm");
        Form.cleanInputs(formInputList);
        Form.cleanErrorOnInput(formInputList);
      } else if (act == -1) {
        Form.cleanInputs(formInputList);
        Form.cleanErrorOnInput(formInputList);
      } else if (act == 1) {
        controller.setAction(action);

        controller.handleFormAction();
      }
    };

    SwingUtilities.updateComponentTreeUI(form);

    SimplePopupBorder popup = new SimplePopupBorder(form, title, borderOption, actions, callbackAction);

    popup.setMaximumSize(new Dimension(popupWidht, height));

    DefaultOption popupOption = new DefaultOption();

    GlassPanePopup.showPopup(
        popup,
        popupOption,
        "employeeForm");
  }

  // getters
  public SelectInput<Role> getCbRole() {
    return cbRole;
  }

  public TextInput getTiSearch() {
    return tiSearch;
  }

  public JTable getTableEmployee() {
    return tableEmployee;
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

  public GoatList<GoatInput<? extends JComponent>> getFormInputList() {
    return formInputList;
  }

  // layout
  private GoatPanel banner;
  private JPanel container;
  private JLabel lblTitle;
  private JPanel tableContainer;

  // table
  private TextInput tiSearch;
  private JButton btnAdd;
  private JButton btnEdit;
  private JButton btnDelete;
  private JTable tableEmployee;

  // Form
  private JPanel form;
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
