package aomine.view.admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.components.ImagePanel;
import aomine.components.layout.view.SimpleView;
import net.miginfocom.swing.MigLayout;

public class EmployeeView extends SimpleView {
  public EmployeeView() {
    viewOpen();
  }

  @Override
  public void viewOpen() {
    initComponents();
    setLayouts();
    applyStyles();
    setModel();
    renderComponents();
  }

  private void initComponents() {
    banner = new ImagePanel.ImagePanelBuilder()
        .setPathFromResources("banner/employee.png")
        .build();
    container = new JPanel();
    lblTitle = new JLabel("Empleados");
    tableContainer = new JPanel();
    btnContainer = new JPanel();
    txtSearch = new JTextField();
    btnAdd = new JButton("Nuevo");
    tableEmployee = new JTable();
  }

  private void setLayouts() {
    setLayout(new MigLayout("insets 0", "[210:30%:450]20[grow]", "[grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("debug, flowy", "[grow]", "[][grow]"));

    btnContainer.setLayout(new MigLayout("insets 0"));
  }

  private void applyTableStyles() {
    // Add styles class to the table
    JTableHeader tableHeader = tableEmployee.getTableHeader();

    tableHeader.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    tableEmployee.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
  }

  private void applyStyles() {
    applyTableStyles();
    lblTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold 32;");
    tableContainer.putClientProperty(FlatClientProperties.STYLE, "background:rgb(188, 207, 226)");
  }

  private void setModel() {
    Object[] columns = { "ID", "Nombre" };
    DefaultTableModel model = new DefaultTableModel(null, columns);

    for (int i = 1; i < 50; i++) {
      model.addRow(new Object[] { i, "Empleado " + i });
    }

    tableEmployee.setModel(model);
  }

  private void renderComponents() {
    add(banner, "grow");
    container.add(lblTitle);
    container.add(tableContainer, "grow");
    btnContainer.add(txtSearch);
    btnContainer.add(btnAdd);
    tableContainer.add(btnContainer);
    tableContainer.add(new JScrollPane(tableEmployee), "grow");
    add(container, "grow");
  }

  private ImagePanel banner;
  private JPanel container;
  private JLabel lblTitle;
  private JPanel tableContainer;
  private JPanel btnContainer;
  private JTextField txtSearch;
  private JButton btnAdd;
  private JTable tableEmployee;
}
