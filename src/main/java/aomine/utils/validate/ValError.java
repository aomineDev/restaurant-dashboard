package aomine.utils.validate;

import aomine.components.input.TextComponent;

public class ValError {
  private TextComponent input;
  private String message;

  public ValError(TextComponent input, String message) {
    this.input = input;
    this.message = message;
  }

  public TextComponent getInput() {
    return input;
  }

  public String getMessage() {
    return message;
  }

  public void setInput(TextComponent input) {
    this.input = input;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
