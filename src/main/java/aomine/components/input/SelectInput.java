package aomine.components.input;

import javax.swing.JComboBox;

public class SelectInput<T> extends GoatInput<JComboBox<T>> {
  public SelectInput(SelectInputBuilder<T> builder) {
    this.lblText = builder.lblText;
    this.lblErrorText = builder.lblErrorText;

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
  public JComboBox<T> getInput() {
    return this.input;
  }

  public static class SelectInputBuilder<T> extends GoatInput.GoatInputBuilder<SelectInputBuilder<T>, SelectInput<T>> {
    @Override
    protected SelectInputBuilder<T> self() {
      return this;
    }

    @Override
    public SelectInput<T> build() {
      return new SelectInput<>(this);
    }

  }
}
