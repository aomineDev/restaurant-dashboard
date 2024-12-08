package aomine.components.layout.sidebar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.ViewManager;
import aomine.components.layout.sidebar.switchTheme.SwitchTheme;
import aomine.view.admin.Prueba;
import aomine.view.admin.Test;
import raven.drawer.component.DrawerPanel;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.header.SimpleHeaderStyle;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.drawer.component.menu.SimpleMenuStyle;
import raven.drawer.component.menu.data.Item;
import raven.drawer.component.menu.data.MenuItem;
import raven.swing.AvatarIcon;

public class MyDrawerBuilder extends SimpleDrawerBuilder {
  private final SwitchTheme switchTheme;

  public MyDrawerBuilder() {
    switchTheme = new SwitchTheme();
  }

  @Override
  public SimpleHeaderData getSimpleHeaderData() {
    AvatarIcon icon = new AvatarIcon(getClass().getResource("/aomine/images/sidebar/avatar.jpg"), 60, 60, 999);
    icon.setBorder(2);

    return new SimpleHeaderData()
      .setIcon(icon)
      .setTitle("aomine")
      .setDescription("admin")
      .setHeaderStyle(new SimpleHeaderStyle() {
        @Override
        public void styleTitle(JLabel label) {
          label.putClientProperty(FlatClientProperties.STYLE, 
            "[light]foreground:#FAFAFA"
          );
        }

        @Override
        public void styleDescription(JLabel label) {
          label.putClientProperty(FlatClientProperties.STYLE, 
            "[light]foreground:#E1E1E1"
          );
        }
      })
    ;
  }

  @Override
  public SimpleMenuOption getSimpleMenuOption() {
    MenuItem[] items = new MenuItem[]{
      new Item("Dashboard"),
      new Item("asd"),
      new Item("Prueba")
    };

    SimpleMenuOption simpleMenuOption = new SimpleMenuOption() {
      @Override
      public Icon buildMenuIcon(String path, float scale) {
        FlatSVGIcon icon = new FlatSVGIcon(path, scale);
        FlatSVGIcon.ColorFilter colorFilter = new FlatSVGIcon.ColorFilter();
        colorFilter.add(Color.decode("#969696"), Color.decode("#FAFAFA"), Color.decode("#969696"));
        icon.setColorFilter(colorFilter);

        return icon;
      }
    };

    simpleMenuOption.setMenuStyle(new SimpleMenuStyle() {
      @Override
      public void styleMenuItem(JButton menu, int[] index) {
        menu.putClientProperty(FlatClientProperties.STYLE, 
          "[light]foreground: #FAFAFA;" +
          "arc: 10"
        );
      }

      @Override
      public void styleMenu(JComponent component) {
        component.putClientProperty(FlatClientProperties.STYLE, 
          "background:$Drawer.background"
        );
      }

      @Override
      public void styleLabel(JLabel label) {
        label.putClientProperty(FlatClientProperties.STYLE, 
          "[light]foreground: darken(#FAFAFA, 15%);" +
          "[dark]foreground: darken($Label.foreground, 30%)"
        );
      }
    });

    simpleMenuOption.addMenuEvent(new MenuEvent() {
      @Override
      public void selected(MenuAction action, int[] index) {
        if (index.length == 1) {
          if (index[0] == 0) {
            ViewManager.showView(new Test());
          } else if (index[0] == 1) {
            ViewManager.showView(new Prueba());
          }
        } else if (index.length == 2) {

        }
      }
    });

    simpleMenuOption
      .setMenus(items)
      .setBaseIconPath("aomine/icons/sidebar")
      .setIconScale(0.45f);

    return simpleMenuOption;
  }

  @Override
  public SimpleFooterData getSimpleFooterData() {
    return new SimpleFooterData();
  }

  @Override
  public Component getFooter() {
    return switchTheme;
  }

  @Override
  public void build(DrawerPanel drawerPanel) {
    drawerPanel.putClientProperty(FlatClientProperties.STYLE, "background: $Drawer.background");
  }

  @Override
  public int getDrawerWidth() {
    return 270;
  }
}
