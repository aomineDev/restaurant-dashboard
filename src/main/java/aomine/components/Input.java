package aomine.components;

import com.formdev.flatlaf.FlatClientProperties;

public class Input {
  protected boolean errorHintState;

  protected ;
  public Input() {
    errorHintState = false;
  }

  public void setErrorHint(boolean newState) {
    if (errorHintState == newState)
      return;

    errorHintState = newState;
    String outline = newState ? FlatClientProperties.OUTLINE_ERROR : null;

    this.input.putClientProperty(FlatClientProperties.OUTLINE, outline);
  }
}
