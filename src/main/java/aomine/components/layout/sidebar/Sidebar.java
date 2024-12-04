package aomine.components.layout.sidebar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatClientProperties;

import aomine.ViewManager;
import net.miginfocom.swing.MigLayout;
import raven.drawer.component.DrawerBuilder;
import raven.drawer.component.DrawerPanel;

public class Sidebar extends JPanel {
  private final DrawerBuilder drawerBuilder;

  public Sidebar(DrawerBuilder drawerBuilder) {
    this.drawerBuilder = drawerBuilder;
    init();
  }
  
  public DrawerBuilder getDrawerBuilder() {
    return drawerBuilder;
  }

  private void init() {
    putClientProperty(FlatClientProperties.STYLE, 
      "background: $Drawer.background"
    );
    
    setLayout(new MigLayout("wrap, fill", "[fill, " + drawerBuilder.getDrawerWidth() +"!]", "[fill]"));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
          ViewManager.hideSidebar();
        }
      }
    });

    DrawerPanel drawerPanel = new DrawerPanel(drawerBuilder);
    drawerPanel.addMouseListener(new MouseAdapter() {});
    drawerBuilder.build(drawerPanel);
    add(drawerPanel);
  }
}