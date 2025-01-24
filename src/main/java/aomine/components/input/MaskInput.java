package aomine.components.input;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class MaskInput extends GoatTextInput<JFormattedTextField> {
  private String mask;
  private char maskPlaceholder;

  public MaskInput(MaskInputBuilder builder) {
    this.lblText = builder.lblText;
    this.lblErrorText = builder.lblErrorText;
    this.mask = builder.mask;
    this.maskPlaceholder = builder.maskPlaceholder;

    initialize();
  }

  @Override
  protected void initialize() {
    initComponents();

    super.initialize();
  }

  private void initComponents() {
    if (this.mask != null)
      this.input = new JFormattedTextField(createFormatter());
    else
      this.input = new JFormattedTextField();

  }

  @Override
  public String getText() {
    return this.input.getText();
  }

  @Override
  public JFormattedTextField getInput() {
    return this.input;
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

  public static class MaskInputBuilder extends GoatTextInput.GoatTextInputBuilder<MaskInputBuilder> {
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

    public MaskInput build() {
      return new MaskInput(this);
    }
  }
}
