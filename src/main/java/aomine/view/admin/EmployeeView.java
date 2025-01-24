package aomine.view.admin;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.components.GoatPanel;
import aomine.components.input.MaskInput;
import aomine.components.input.PasswordInput;
import aomine.components.input.TextInput;
import aomine.components.layout.view.SimpleView;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.popup.DefaultOption;
import raven.popup.GlassPanePopup;
import raven.popup.GlassPopup;
import raven.popup.component.GlassPaneChild;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.SimplePopupBorder;
import raven.popup.component.SimplePopupBorderOption;

public class EmployeeView extends SimpleView implements View {
  public EmployeeView() {
    initialize();
    setModel();
    testData();
    applyTableStyles();
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
    form = new GlassPaneChild();

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
        .setPlaceholder("Ingrese el Apellido paterno")
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
        .setMask("########")
        .build();

    datePicker = new DatePicker();
    miBirthdate = new MaskInput.MaskInputBuilder()
        .setLabelText("Fecha de nacimiento")
        .setPlaceholder("Ingrese la fecha de nacimiento")
        .withErrorLabel()
        .build();
    datePicker.setEditor(miBirthdate.getInput());
    datePicker.setUsePanelOption(true);
    datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));

    miPhoneNumber = new MaskInput.MaskInputBuilder()
        .setLabelText("Celuluar")
        .withErrorLabel()
        .setMask("### ### ###")
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

    cbRole = new JComboBox<Object>();
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 0", "[fill, 210:30%:450]20[fill, grow]", "[fill, grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("insets 16, fillx", "[]push[][][]", "[]20[grow]"));

    form.setLayout(new MigLayout("debug"));
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

    setIcons();
  }

  @Override
  public void applyEvents() {

    btnAdd.addActionListener(e -> {
      DefaultOption popupOption = new DefaultOption();

      SimplePopupBorderOption borderOption = new SimplePopupBorderOption();

      String[] actions = { "Cancelar", "Guardar" };

      PopupCallbackAction callbackAction = (controller, action) -> {
        if (action == 0) {
          GlassPanePopup.closePopup("employeeForm");
        } else if (action == 1) {
          System.out.println("ok");
        }
      };

      GlassPanePopup.showPopup(
          new SimplePopupBorder(form, "Nuevo Empleado", borderOption, actions, callbackAction),
          popupOption,
          "employeeForm");
    });
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

    form.add(tiFirstName.getLabel());
    form.add(tiFirstName.getInput());
    form.add(tiFirstName.getErrorLabel());
    form.add(tiSecondtName.getLabel());
    form.add(tiSecondtName.getInput());
    form.add(tiSecondtName.getErrorLabel());
    form.add(tiPaternalLastName.getInput());
    form.add(tiPaternalLastName.getInput());
    form.add(tiPaternalLastName.getErrorLabel());
    form.add(tiMaternalLastName.getLabel());
    form.add(tiMaternalLastName.getInput());
    form.add(tiMaternalLastName.getErrorLabel());
    form.add(miDni.getLabel());
    form.add(miDni.getInput());
    form.add(miDni.getErrorLabel());
    form.add(miBirthdate.getLabel());
    form.add(miBirthdate.getInput());
    form.add(miBirthdate.getErrorLabel());
    form.add(miPhoneNumber.getLabel());
    form.add(miPhoneNumber.getInput());
    form.add(miPhoneNumber.getErrorLabel());
    form.add(tiAddress.getLabel());
    form.add(tiAddress.getInput());
    form.add(tiAddress.getErrorLabel());
    form.add(tiEmail.getLabel());
    form.add(tiEmail.getInput());
    form.add(tiEmail.getErrorLabel());
    form.add(tiUsername.getLabel());
    form.add(tiUsername.getInput());
    form.add(tiUsername.getErrorLabel());
    form.add(piPassword.getLabel());
    form.add(piPassword.getInput());
    form.add(piPassword.getErrorLabel());
    form.add(cbRole);
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
            "width: 10;");

    // Add styles class to the table
    JTableHeader tableHeader = tableEmployee.getTableHeader();

    tableHeader.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    tableEmployee.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
  }

  private void setModel() {
    Object[] columns = { "ID", "Nombre" };
    DefaultTableModel model = new DefaultTableModel(null, columns) {
      boolean[] canEdit = { false, false };

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    };

    tableEmployee.setModel(model);
  }

  private void testData() {
    DefaultTableModel model = (DefaultTableModel) tableEmployee.getModel();

    for (int i = 1; i < 50; i++) {
      model.addRow(new Object[] { i, "Empleado " + i });
    }
  }

  private void setIcons() {
    String basePath = "aomine/icons/";
    float scale = 0.35f;
    String[] iconNames = { "add", "edit", "delete" };
    JButton[] buttons = { btnAdd, btnEdit, btnDelete };

    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setIcon(new FlatSVGIcon(basePath + iconNames[i] + ".svg", scale));
      buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    tiSearch.setRightIcon("search.svg", scale);
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
  private GlassPaneChild form;
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
  private JComboBox<Object> cbRole;

}
