package aomine.components.input;

import javax.swing.JComboBox;

public class SelectInput<T> extends GoatInput<JComboBox<T>> {
  public SelectInput(SelectInputBuilder<?> builder) {
    super(builder);
    this.initialize();
  }

  @Override
  protected void initialize() {
    this.initComponents();

    super.initialize();
  }

  private void initComponents() {
    this.input = new JComboBox<T>();
  }

  public T getSelectedItem() {
    return this.input.getItemAt(this.input.getSelectedIndex());
  }

  @Override
  public void clear() {
    this.input.setSelectedIndex(0);
  }

  public static class SelectInputBuilder<T> extends GoatInput.GoatInputBuilder<SelectInputBuilder<T>, JComboBox<T>> {
    @Override
    protected SelectInputBuilder<T> self() {
      return this;
    }

    @Override
    public SelectInput<T> build() {
      return new SelectInput<T>(this);
    }
  }
}
