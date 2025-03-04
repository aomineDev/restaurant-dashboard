package aomine.utils.validate;

import javax.swing.text.JTextComponent;

import aomine.components.input.GoatTextInput;

public class ValError {
  private GoatTextInput<? extends JTextComponent> component;
  private String message;

  public ValError(GoatTextInput<? extends JTextComponent> component, String message) {
    this.component = component;
    this.message = message;
  }

  public GoatTextInput<? extends JTextComponent> getComponent() {
    return component;
  }

  public void setComponent(GoatTextInput<? extends JTextComponent> component) {
    this.component = component;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
