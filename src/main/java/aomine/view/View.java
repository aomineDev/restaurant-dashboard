package aomine.view;

public interface View {
  default void initialize() {
    initComponents();
    setLayouts();
    applyStyles();
    applyEvents();
    renderComponents();
  }

  void initComponents();

  void setLayouts();

  void applyStyles();

  void applyEvents();

  void renderComponents();
}
