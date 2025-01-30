package aomine.components.input;

import javax.swing.JTextField;

public class TextInput extends GoatTextInput<JTextField> {
  private String value;

  public TextInput(TextInputBuilder builder) {
    this.lblText = builder.lblText;
    this.lblErrorText = builder.lblErrorText;
    this.value = builder.value;
    this.placeholder = builder.placeholder;

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

  @Override
  public String getText() {
    return this.input.getText();
  }

  @Override
  public JTextField getInput() {
    return this.input;
  }

  public static class TextInputBuilder extends GoatTextInput.GoatTextInputBuilder<TextInputBuilder, TextInput> {
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
