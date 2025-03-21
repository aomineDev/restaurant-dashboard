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
    String text = String.valueOf(this.input.getPassword());

    if (text.equals(""))
      return null;

    return String.valueOf(this.input.getPassword());
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
