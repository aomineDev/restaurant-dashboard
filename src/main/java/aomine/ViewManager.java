package aomine;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Image;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import aomine.login.view.LoginView;
import aomine.utils.PrevNext;

import aomine.components.layout.sidebar.MyDrawerBuilder;
import aomine.components.layout.sidebar.Sidebar;
import aomine.components.layout.slider.PanelSlider;
import aomine.components.layout.slider.SimpleTransition;
import aomine.components.layout.view.MainView;
import aomine.components.layout.view.SimpleView;

public class ViewManager {
  private static ViewManager instance;
  private final JFrame frame;
  private final PanelSlider panelSlider;
  private final Sidebar sidebar;
  private final MainView mainView;
  public final PrevNext<SimpleView> viewStack = new PrevNext<>();
  private boolean sidebarShowing;
  
  public static void install(JFrame frame) {
    instance = new ViewManager(frame);
  }

  private ViewManager(JFrame frame) {
    this.frame = frame;
    panelSlider = new PanelSlider();
    sidebar = new Sidebar(new MyDrawerBuilder());
    mainView = new MainView();
    sidebarShowing = true;
  }

  public static void logout() {
    FlatAnimatedLafChange.showSnapshot();
    instance.frame.getContentPane().removeAll();
    instance.frame.getContentPane().add(new LoginView());
    instance.frame.revalidate();
    instance.frame.repaint();
    FlatAnimatedLafChange.hideSnapshotWithAnimation();
  }

  public static void login() {
    FlatAnimatedLafChange.showSnapshot();
    instance.frame.getContentPane().removeAll();
    instance.frame.getContentPane().add(instance.panelSlider);
    instance.frame.revalidate();
    instance.frame.repaint();
    FlatAnimatedLafChange.hideSnapshotWithAnimation();
  }

  public static void showView(SimpleView component) {
    if (isNewViewAble()) {
      instance.viewStack.add(component);
      
      if (instance.sidebarShowing) {
        instance.sidebarShowing = false;
        Image oldImage = instance.panelSlider.createOldImage();
        instance.mainView.setView(component);
        instance.panelSlider.addSlide(instance.mainView, SimpleTransition.getSwitchViewTransition(oldImage, instance.sidebar.getDrawerBuilder().getDrawerWidth()));
      }
    }
  }

  public static void showSidebar() {
    instance.sidebarShowing = true;
    instance.panelSlider.addSlide(instance.sidebar, SimpleTransition.getShowSidebarTransition(instance.sidebar.getDrawerBuilder().getDrawerWidth()));
  }

  public static void hideSidebar() {
    instance.sidebarShowing = false;
    instance.panelSlider.addSlide(instance.mainView, SimpleTransition.getHideSidebarTransition(instance.sidebar.getDrawerBuilder().getDrawerWidth()));
  }

  public static void prev() {
    if (isNewViewAble()) {
      if (!instance.sidebarShowing && instance.viewStack.isPrevAble()) {
        instance.mainView.showView(instance.viewStack.prev(), SimpleTransition.getDefaultTransition(true));
      }
    }
  }

  public static void next() {
    if (isNewViewAble()) {
      if (!instance.sidebarShowing && instance.viewStack.isNextAble()) {
        instance.mainView.showView(instance.viewStack.next());
        instance.viewStack.getCurrent().viewOpen();
      }
    }
  }

  public static void refresh() {
    if(!instance.sidebarShowing) {
      instance.viewStack.getCurrent().viewRefresh();
    }
  }

  public static PrevNext<SimpleView> getViewStack() {
    return instance.viewStack;
  }

  public static boolean isNewViewAble() {
    return instance.viewStack.getCurrent() == null || instance.viewStack.getCurrent().viewClose();
  }

  public static void updateTempViewUI() {
    for (SimpleView f: instance.viewStack) {
      SwingUtilities.updateComponentTreeUI(f);
    }
  }
}
