package aomine.utils.validate;

import aomine.components.TextInput;

public class ValError {
  private TextInput input;
  private String message;

  public ValError(TextInput input, String message) {
    this.input = input;
    this.message = message;
  }

  public TextInput getInput() {
    return input;
  }

  public String getMessage() {
    return message;
  }

  public void setInput(TextInput input) {
    this.input = input;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
