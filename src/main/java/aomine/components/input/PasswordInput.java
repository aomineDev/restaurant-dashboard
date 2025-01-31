package aomine.components.input;

import javax.swing.JPasswordField;

import com.formdev.flatlaf.FlatClientProperties;

public class PasswordInput extends GoatTextInput<JPasswordField> {
  private String value;

  public PasswordInput(PasswordInputBuilder builder) {
    super(builder);
    this.value = builder.value;

    this.initialize();
  }

  @Override
  protected void initialize() {
    this.initComponents();

    this.applyStyles();

    super.applyPlaceholder();

    super.initialize();
  }

  private void initComponents() {
    this.input = new JPasswordField(value);
  }

  private void applyStyles() {
    this.input.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
  }

  @Override
  public String getText() {
    return String.valueOf(this.input.getPassword());
  }

  @Override
  public JPasswordField getInput() {
    return this.input;
  }

  public static class PasswordInputBuilder
      extends GoatTextInput.GoatTextInputBuilder<PasswordInputBuilder, JPasswordField> {
    private String value;

    public PasswordInputBuilder setValue(String value) {
      this.value = value;
      return this;
    }

    @Override
    protected PasswordInputBuilder self() {
      return this;
    }

    @Override
    public PasswordInput build() {
      return new PasswordInput(this);
    }
  }
}
