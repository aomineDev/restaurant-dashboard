package aomine.view.admin;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.components.GoatPanel;
import aomine.components.TextInput;
import aomine.components.layout.view.SimpleView;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;
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
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 0", "[fill, 210:30%:450]20[fill, grow]", "[fill, grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("insets 16, fillx", "[]push[][][]", "[]20[grow]"));
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

      String[] actions = { "Cancelar", "Guardar" };

      SimplePopupBorderOption borderOption = new SimplePopupBorderOption();

      PopupCallbackAction callbackAction = (controller, action) -> {
        if (action == 0) {
          GlassPanePopup.closePopup("employeeForm");
        } else if (action == 1) {
          System.out.println("ok");
        }
      };

      GlassPanePopup.showPopup(
          new SimplePopupBorder(getForm(), "Nuevo Empleado", borderOption, actions, callbackAction),
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

  private GlassPaneChild getForm() {
    GlassPaneChild form = new GlassPaneChild();
    form.setLayout(new MigLayout("debug"));

    form.add(new JLabel("aqui irian un text input"));

    return form;
  }

  private GoatPanel banner;
  private JPanel container;
  private JLabel lblTitle;
  private JPanel tableContainer;
  private TextInput tiSearch;
  private JButton btnAdd;
  private JButton btnEdit;
  private JButton btnDelete;
  private JTable tableEmployee;

}
