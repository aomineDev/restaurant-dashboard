package aomine.view;

public interface View {
  void initialize();

  void initComponents();

  void setLayouts();

  void applyStyles();

  void applyEvents();

  void renderComponents();
}
