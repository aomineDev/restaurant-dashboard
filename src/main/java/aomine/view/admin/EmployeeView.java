package aomine.view.admin;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.GoatPanel;
import aomine.components.layout.view.SimpleView;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;

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
    btnContainer = new JPanel();
    txtSearch = new JTextField();
    btnAdd = new JButton("Nuevo");
    tableEmployee = new JTable();
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 0", "[210:30%:450]20[grow]", "[grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("flowy, insets 16", "[grow]", "[][grow]"));

    btnContainer.setLayout(new MigLayout("insets 0"));
  }

  @Override
  public void applyStyles() {
    lblTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold 32;");
    tableContainer.putClientProperty(FlatClientProperties.STYLE, "background: $Table.background;" +
        "[light]border:0,0,0,0,shade(@background,5%),,20;" +
        "[dark]border:0,0,0,0,tint(@background,5%),,20;");
    btnContainer.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
  }

  @Override
  public void applyEvents() {

  }

  @Override
  public void renderComponents() {
    add(banner, "grow");
    container.add(lblTitle);
    container.add(tableContainer, "grow");
    btnContainer.add(txtSearch);
    btnContainer.add(btnAdd);
    tableContainer.add(btnContainer);
    tableContainer.add(new JScrollPane(tableEmployee), "grow");
    add(container, "grow");
  }

  public void applyTableStyles() {
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
  }

  public void setModel() {
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

  private GoatPanel banner;
  private JPanel container;
  private JLabel lblTitle;
  private JPanel tableContainer;
  private JPanel btnContainer;
  private JTextField txtSearch;
  private JButton btnAdd;
  private JTable tableEmployee;
}
