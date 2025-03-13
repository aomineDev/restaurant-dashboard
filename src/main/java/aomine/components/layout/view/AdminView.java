package aomine.components.layout.view;

import java.util.HashMap;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.components.GoatPanel;
import aomine.components.input.SelectInput;
import aomine.components.input.TextInput;
import aomine.model.EntityColumn;
import aomine.utils.GoatList;
import aomine.view.View;
import net.miginfocom.swing.MigLayout;

public abstract class AdminView extends SimpleView implements View {
  protected int rowSelected;
  private String bannerImage;
  protected GoatList<EntityColumn> columnList;

  public AdminView(String bannerImage) {
    this.bannerImage = bannerImage;

    initialize();

    setController();

    setBtnEnabled(false);

    setColumnList();
    setSiColumnItems();

    applyTableStyles();

    setTableModel();
    setTableData();
  }

  @Override
  public void initComponents() {
    banner = new GoatPanel.GoatPanelBuilder()
        .setPathFromResources("banner/" + bannerImage)
        .setArc(20)
        .build();

    container = new JPanel();

    lblTitle = new JLabel("Empleados");
    tableContainer = new JPanel();

    tiSearch = new TextInput.TextInputBuilder()
        .setPlaceholder("Nombre")
        .build();

    siColumn = new SelectInput.SelectInputBuilder<EntityColumn>()
        .build();

    btnAdd = new JButton("Nuevo");
    btnEdit = new JButton("Editar");
    btnDelete = new JButton("Eliminar");
    table = new JTable();
  }

  @Override
  public void setLayouts() {
    setLayout(new MigLayout("insets 0", "[fill, 210:30%:450]20[fill, grow]", "[fill, grow]"));

    container.setLayout(new MigLayout("insets 0, flowy", "[grow]", "[]10[grow]"));

    tableContainer.setLayout(new MigLayout("insets 16, fillx", "[][]push[][][]", "[]20[grow]"));
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
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        setBtnEnabled(true);
        rowSelected = table.rowAtPoint(evt.getPoint());
      }
    });
  }

  @Override
  public void renderComponents() {
    add(banner);
    add(container);

    container.add(lblTitle);
    container.add(tableContainer, "grow");

    tableContainer.add(tiSearch.getInput(), "w 200");
    tableContainer.add(siColumn.getInput());
    tableContainer.add(btnAdd);
    tableContainer.add(btnEdit);
    tableContainer.add(btnDelete, "wrap");
    tableContainer.add(new JScrollPane(table), "span, grow");
  }

  @Override
  public void viewRefresh() {
    tiSearch.setText(null);
    setTableData();
    setBtnEnabled(false);
  }

  private void applyTableStyles() {
    // Change scroll style
    JScrollPane scroll = (JScrollPane) table.getParent().getParent();
    scroll.setBorder(BorderFactory.createEmptyBorder());
    scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
        "background: $Table.background;" +
            "track: $Table.background;" +
            "trackArc: 999;" +
            "thumbArc: 999;" +
            "width: 8;");

    // Add styles class to the table
    JTableHeader tableHeader = table.getTableHeader();

    tableHeader.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
    table.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

    tableHeader.setReorderingAllowed(false);
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

  public void setBtnEnabled(boolean enable) {
    btnEdit.setEnabled(enable);
    btnDelete.setEnabled(enable);
  }

  public long getIdFromTable(int row) {
    return (long) table.getModel().getValueAt(row, 0);
  }

  public Object getValueFromTable(int row, int column) {
    return table.getValueAt(row, column);
  }

  protected abstract void setTableModel();

  public abstract void setTableData();

  protected abstract void setController();

  protected abstract void setColumnList();

  protected void setSiColumnItems() {
    columnList.forEach(column -> siColumn.getInput().addItem(column));
  }

  // getters
  public TextInput getTiSearch() {
    return tiSearch;
  }

  public SelectInput<EntityColumn> getSiColumn() {
    return siColumn;
  }

  public JTable getTable() {
    return table;
  }

  // layout
  protected GoatPanel banner;
  protected JPanel container;
  protected JLabel lblTitle;
  protected JPanel tableContainer;

  // table
  protected TextInput tiSearch;
  protected SelectInput<EntityColumn> siColumn;
  protected JButton btnAdd;
  protected JButton btnEdit;
  protected JButton btnDelete;
  protected JTable table;
}
