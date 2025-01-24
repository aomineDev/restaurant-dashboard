package aomine.components.input;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public abstract class GoatInput<T> {
  protected String lblText;
  protected String lblErrorText;

  private boolean errorHintState;

  protected JLabel lblError;
  protected JLabel lbl;
  protected T input;

  public GoatInput() {
    errorHintState = false;
  }

  protected void initialize() {
    initComponents();
    applyStyle();
  }

  private void initComponents() {
    if (this.lblText != null) {
      this.lbl = new JLabel(this.lblText);
    }

    if (this.lblErrorText != null) {
      this.lblError = new JLabel(this.lblErrorText);
    }
  }

  private void applyStyle() {
    if (this.lblErrorText != null) {
      this.lblError.putClientProperty(FlatClientProperties.STYLE,
          "font: -1;" +
              "foreground: rgb(255, 130, 130);");
    }
  }

  public void setErrorHint(boolean newState) {
    if (errorHintState == newState)
      return;

    errorHintState = newState;
    String outline = newState ? FlatClientProperties.OUTLINE_ERROR : null;

    this.getInput().putClientProperty(FlatClientProperties.OUTLINE, outline);
  }

  public void setLabelErrorText(String str) {
    if (this.lblError == null)
      return;

    if (this.lblError.getText() == str)
      return;

    this.lblError.setText(str);
  }

  public void setLabel(String str) {
    if (this.lbl == null)
      return;

    if (this.lbl.getText() == str)
      return;

    this.lbl.setText(str);
  }

  public void setLeftIcon(String iconPath, float scale) {
    this.getInput().putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, getSVGIcon(iconPath, scale));
  }

  public void setRightIcon(String iconPath, float scale) {
    this.getInput().putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, getSVGIcon(iconPath, scale));
  }

  private FlatSVGIcon getSVGIcon(String iconPath, float scale) {
    return new FlatSVGIcon("aomine/icons/" + iconPath, scale);
  }

  public JComponent getInput() {
    return (JComponent) this.input;
  }

  public JLabel getLabel() {
    return this.lbl;
  }

  public JLabel getErrorLabel() {
    return this.lblError;
  }

  protected static abstract class GoatInputBuilder<U> {
    protected String lblText;
    protected String lblErrorText;

    public U setLabelText(String lblText) {
      this.lblText = lblText;
      return self();
    }

    public U withErrorLabel() {
      this.lblErrorText = "";
      return self();
    }

    protected abstract U self();
  }
}
