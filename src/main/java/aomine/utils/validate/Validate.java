package aomine.utils.validate;

import java.util.ArrayList;

import aomine.components.input.TextComponent;

public class Validate {
  private TextComponent input;
  private String text;
  private boolean isValid;
  private ArrayList<ValError> valErrorList;
  private int errorCount;

  public Validate() {
    this.errorCount = 0;
  }

  public void reset() {
    this.errorCount = 0;
    this.valErrorList = new ArrayList<>();
  }

  public Validate setElement(TextComponent input) {
    this.input = input;
    this.text = input.getText();
    this.isValid = true;

    return this;
  }

  public Validate isRequired(String msg) {
    if (!isValid)
      return this;

    if (text.equals("")) {
      isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      errorCount++;
    }

    return this;
  }

  public Validate minLength(String msg, int min) {
    if (!isValid)
      return this;

    if (text.length() < min) {
      isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      errorCount++;
    }

    return this;
  }

  public boolean getIsValid() {
    return isValid;
  }

  public int getErrorCount() {
    return errorCount;
  }

  public ArrayList<ValError> getValErrorList() {
    return valErrorList;
  }
}