package aomine.components.layout.view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import aomine.ViewManager;
import aomine.components.layout.slider.PanelSlider;
import aomine.components.layout.slider.SimpleTransition;
import aomine.components.layout.slider.SliderTransition;
import net.miginfocom.swing.MigLayout;

public class MainView extends JPanel {
  public MainView() {
    init();
  }

  private void init() {
    putClientProperty(FlatClientProperties.STYLE, 
      "border: 5, 5, 5, 5;" +
      "arc: 30"
    );

    setLayout(new MigLayout("wrap, fillx", "[fill]", "[]"));

    header = createHeader();
    panelSlider = new PanelSlider();
    JScrollPane scroll = new JScrollPane(panelSlider);

    scroll.putClientProperty(FlatClientProperties.STYLE, 
      "border: 0, 0, 0, 0"
    );

    scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, 
      "trackArc: 999;" +
      "width: 10"
    );

    scroll.getVerticalScrollBar().setUnitIncrement(10);

    add(header);
    add(scroll);
  }

  private JPanel createHeader() {
    JPanel header = new JPanel(new MigLayout("insets 3"));
    header.putClientProperty(FlatClientProperties.STYLE, 
      "background: null"
    );
    btnSidebar = createButton(new FlatSVGIcon(getClass().getResource("/aomine/icons/header/menu.svg")));
    btnPrev = createButton(new FlatSVGIcon(getClass().getResource("/aomine/icons/header/prev.svg")));
    btnNext = createButton(new FlatSVGIcon(getClass().getResource("/aomine/icons/header/next.svg")));
    btnRefresh = createButton(new FlatSVGIcon(getClass().getResource("/aomine/icons/header/refresh.svg")));

    btnSidebar.addActionListener(e -> ViewManager.showSidebar());
    btnPrev.addActionListener(e -> ViewManager.prev());
    btnNext.addActionListener(e -> ViewManager.next());
    btnRefresh.addActionListener(e -> ViewManager.refresh());

    header.add(btnSidebar);
    header.add(btnPrev);
    header.add(btnNext);
    header.add(btnRefresh);

    return header;
  }

  private JButton createButton(Icon icon) {
    JButton button = new JButton(icon);
    button.putClientProperty(FlatClientProperties.STYLE, 
      "background: $Button.toolbar.background;" +
      "arc: 10;" +
      "margin: 3, 3, 3, 3;" +
      "borderWidth: 0;" +
      "focusWidth: 0;" +
      "innerFocusWidth: 0" 
    );

    return button;
  }

  public void showView(Component component, SliderTransition transition) {
    checkButton();
    panelSlider.addSlide(component, transition);
    revalidate();
  }

  public void showView(Component component) {
    showView(component, SimpleTransition.getDefaultTransition(false));
  }

  public void setView(Component component) {
    checkButton();
    panelSlider.addSlide(component, null);
  }

  private void checkButton() {
    btnPrev.setEnabled(ViewManager.getViewStack().isPrevAble());
    btnNext.setEnabled(ViewManager.getViewStack().isNextAble());
    btnRefresh.setEnabled(ViewManager.getViewStack().getCurrent() != null);
  }

  private JPanel header;
  private JButton btnSidebar;
  private JButton btnPrev;
  private JButton btnNext;
  private JButton btnRefresh;
  private PanelSlider panelSlider;
}
