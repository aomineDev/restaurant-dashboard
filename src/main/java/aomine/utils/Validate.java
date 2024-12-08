package aomine.utils;

public class Validate {
  private String el;
  private String msg;
  private boolean isValid;

  public Validate() {}

  public Validate setElement(String el) {
    this.el = el;
    this.isValid = true;
    this.msg = "";

    return this;
  }

  public Validate isRequired(String msg) {
    if (!isValid) return this;

    if (el.equals("")) {
      isValid = false;
      this.msg = msg;
    }

    return this;
  }

  public Validate minLength(String msg, int min) {
    if (!isValid) return this;

    if (el.length() < min) {
      isValid = false;
      this.msg = msg;
    }

    return this;
  }

  public boolean getIsValid() {
    return isValid;
  }

  public String getMessage() {
    return msg;
  }
}
