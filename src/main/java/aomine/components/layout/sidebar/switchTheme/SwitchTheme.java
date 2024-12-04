package aomine.components.layout.sidebar.switchTheme;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import aomine.ViewManager;
import net.miginfocom.swing.MigLayout;

public class SwitchTheme extends JPanel {
  public SwitchTheme () {
    init();
  }

  private void init() {
    putClientProperty(FlatClientProperties.STYLE, "background: null");
    setLayout(new MigLayout("al center", "[fill]", "[fill]"));

    darkLigSwitchIcon = new SwitchIcon();
    btnSwitch = new JToggleButton();
    btnSwitch.setSelected(true);

    darkLigSwitchIcon.setCenterSpace(20);
    btnSwitch.setIcon(darkLigSwitchIcon);
    btnSwitch.setCursor(new Cursor(Cursor.HAND_CURSOR));

    btnSwitch.putClientProperty(FlatClientProperties.STYLE, 
      "arc: 999;" +
      "borderWidth: 0;" +
      "focusWidth: 0;" +
      "innerFocusWidth: 0;" 
      // "background:darken($Drawer.background,5%)"
    );

    btnSwitch.addActionListener(new ActionListener() {
      private final ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
      private ScheduledFuture<?> scheduledFuture;
      
      @Override
      public void actionPerformed(ActionEvent evt) {
        if (scheduledFuture != null) {
          scheduledFuture.cancel(true);
        }

        scheduledFuture = scheduled.schedule(() -> {
          toogleThemes(btnSwitch.isSelected());
        }, darkLigSwitchIcon.getAnimationDuration(), TimeUnit.MILLISECONDS);
      }
    });

    add(btnSwitch);
  }

  public void toogleThemes(boolean toDark) {
    if (FlatLaf.isLafDark() != toDark) {
      if (toDark) {
        EventQueue.invokeLater(() -> {
          FlatAnimatedLafChange.showSnapshot();
          FlatMacDarkLaf.setup();
          FlatLaf.updateUI();
          ViewManager.updateTempViewUI();
          FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
      } else {
        EventQueue.invokeLater(() -> {
          FlatAnimatedLafChange.showSnapshot();
          FlatMacLightLaf.setup();
          FlatLaf.updateUI();
          ViewManager.updateTempViewUI();
          FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
      }
    }
  }

  private SwitchIcon darkLigSwitchIcon;
  private JToggleButton btnSwitch;
}
