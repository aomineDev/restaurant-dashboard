package aomine.components.input;

import javax.swing.JTextField;

public class TextInput extends GoatTextInput<JTextField> {
  private String value;

  public TextInput(TextInputBuilder builder) {
    super(builder);
    this.value = builder.value;

    this.initialize();
  }

  @Override
  protected void initialize() {
    this.initComponents();

    super.applyPlaceholder();

    super.initialize();
  }

  private void initComponents() {
    this.input = new JTextField(value);
  }

  public static class TextInputBuilder extends GoatTextInput.GoatTextInputBuilder<TextInputBuilder, JTextField> {
    private String value;

    public TextInputBuilder setValue(String value) {
      this.value = value;
      return this;
    }

    @Override
    protected TextInputBuilder self() {
      return this;
    }

    @Override
    public TextInput build() {
      return new TextInput(this);
    }
  }
}
