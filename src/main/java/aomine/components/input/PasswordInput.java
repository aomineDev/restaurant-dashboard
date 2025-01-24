package aomine.components.input;

import javax.swing.JPasswordField;

public class PasswordInput extends GoatTextInput<JPasswordField> {
  private String value;

  public PasswordInput(PasswordInputBuilder builder) {
    this.lblText = builder.lblText;
    this.lblErrorText = builder.lblErrorText;
    this.value = builder.value;
    this.placeholder = builder.placeholder;

    initialize();
  }

  @Override
  protected void initialize() {
    initComponents();

    super.applyPlaceholder();

    super.initialize();
  }

  private void initComponents() {
    this.input = new JPasswordField(value);
  }

  @Override
  public String getText() {
    return String.valueOf(this.input.getPassword());
  }

  @Override
  public JPasswordField getInput() {
    return this.input;
  }

  public static class PasswordInputBuilder extends GoatTextInput.GoatTextInputBuilder<PasswordInputBuilder> {
    private String value;

    public PasswordInputBuilder setValue(String value) {
      this.value = value;
      return this;
    }

    @Override
    protected PasswordInputBuilder self() {
      return this;
    }

    public PasswordInput build() {
      return new PasswordInput(this);
    }
  }
}
