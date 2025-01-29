package aomine.view.admin;

import java.awt.Cursor;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import raven.popup.component.GlassPaneChild;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.SimplePopupBorder;
import raven.popup.component.SimplePopupBorderOption;

public class EmployeeView extends SimpleView implements View {
  public EmployeeView() {
    formWidth = 800;
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

    cbRole = new JComboBox<Object>();
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
    btnAdd.addActionListener(e -> {
      int height = 550;

      SimplePopupBorderOption borderOption = new SimplePopupBorderOption();

      borderOption.useScroll();
      borderOption.setWidth(formWidth);

      String[] actions = { "Cancelar", "Guardar" };

      PopupCallbackAction callbackAction = (controller, action) -> {
        if (action == 0) {
          GlassPanePopup.closePopup("employeeForm");
        } else if (action == 1) {
          System.out.println("ok");
          System.out.println(miDni.getInput().getWidth());
        }
      };

      SimplePopupBorder popup = new SimplePopupBorder(form, "Nuevo Empleado", borderOption, actions, callbackAction);

      popup.setMaximumSize(new Dimension(formWidth, height));

      DefaultOption popupOption = new DefaultOption();

      GlassPanePopup.showPopup(
          popup,
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
    form.add(new JLabel("rol"), "span 2, wrap");
    form.add(tiUsername.getInput(), "span 2");
    form.add(piPassword.getInput(), "span 2");
    form.add(cbRole, "span 2, wrap");
    form.add(tiUsername.getErrorLabel(), "span 2");
    form.add(piPassword.getErrorLabel(), "span 2");
    form.add(new JLabel(""), "span 2");
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

  private int formWidth;
}
