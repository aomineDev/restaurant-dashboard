package aomine.components.input;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class MaskInput extends GoatTextInput<JFormattedTextField> {
  private String mask;
  private char maskPlaceholder;

  public MaskInput(MaskInputBuilder builder) {
    super(builder);
    this.mask = builder.mask;
    this.maskPlaceholder = builder.maskPlaceholder;

    this.initialize();
  }

  @Override
  protected void initialize() {
    this.initComponents();

    super.initialize();
  }

  private void initComponents() {
    if (this.mask != null)
      this.input = new JFormattedTextField(createFormatter());
    else
      this.input = new JFormattedTextField();

  }

  private MaskFormatter createFormatter() {
    MaskFormatter formatter = null;

    try {
      formatter = new MaskFormatter(mask);
      formatter.setPlaceholderCharacter(maskPlaceholder);
    } catch (java.text.ParseException e) {
      e.printStackTrace();
    }

    return formatter;
  }

  @Override
  public String getText() {
    if (this.input.getValue() == null)
      return null;

    return this.input.getValue().toString();
  }

  @Override
  public void setText(String text) {
    this.input.setText(text);

    if (isValidFormat(this.input.getText())) {
      this.input.setValue(this.input.getText());
    }
  }

  public boolean isValidFormat(String text) {
    try {
      this.input.getFormatter().stringToValue(text);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void clear() {
    this.input.setValue(null);
  }

  public static class MaskInputBuilder
      extends GoatTextInput.GoatTextInputBuilder<MaskInputBuilder, JFormattedTextField> {
    private String mask;
    private char maskPlaceholder;

    public MaskInputBuilder setMask(String mask) {
      return setMask(mask, ' ');
    }

    public MaskInputBuilder setMask(String mask, char maskPlaceholder) {
      this.mask = mask;
      this.maskPlaceholder = maskPlaceholder;
      return this;
    }

    @Override
    protected MaskInputBuilder self() {
      return this;
    }

    @Override
    public MaskInput build() {
      return new MaskInput(this);
    }
  }
}
