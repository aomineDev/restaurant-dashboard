package aomine.utils.validate;

import java.util.function.Predicate;

import org.hibernate.Session;

import aomine.components.input.TextComponent;
import aomine.database.Hibernate;
import aomine.utils.GoatList;

public class Validate {
  private TextComponent input;
  private String text;
  private boolean isValid;
  private GoatList<ValError> valErrorList;
  private int errorCount;
  private Session session;

  public Validate() {
    this.session = Hibernate.getSession();
    this.errorCount = 0;
  }

  public void reset() {
    this.errorCount = 0;
    this.valErrorList = new GoatList<>();
  }

  public Validate setElement(TextComponent input) {
    this.input = input;
    this.text = input.getText();
    this.isValid = true;

    return this;
  }

  public Validate isRequired(String msg) {
    if (!this.isValid)
      return this;

    if (text.equals("")) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate minLength(String msg, int min) {
    if (!this.isValid)
      return this;

    if (text.length() < min) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate maxLength(String msg, int max) {
    if (!this.isValid)
      return this;

    if (text.length() > max) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate equalLength(String msg, int equal) {
    if (!this.isValid)
      return this;

    if (text.length() != equal) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isNumber(String msg) {
    if (!this.isValid)
      return this;

    if (!text.matches("[0-9]+")) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isInteger(String msg) {
    if (!this.isValid)
      return this;

    try {
      Integer.parseInt(text);
    } catch (Exception e) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isDouble(String msg) {
    if (!this.isValid)
      return this;

    try {
      Double.parseDouble(text);
    } catch (Exception e) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isLong(String msg) {
    if (!this.isValid)
      return this;

    try {
      Long.parseLong(text);
    } catch (Exception e) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate selfValidate(String msg, Predicate<String> predicate) {
    if (!this.isValid)
      return this;

    if (predicate.test(text)) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isEmail(String msg) {
    if (!this.isValid)
      return this;

    if (!text.matches("^[a-zA-Z0-9.-_+%]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isUnique(String msg, Class<?> Entity, String column) {
    if (!this.isValid)
      return this;

    String query = String.format("FROM %s WHERE %s = :%s", Entity.getSimpleName(), column, column);

    Object obj = session.createQuery(query, Entity)
        .setParameter(column, text)
        .uniqueResult();

    System.out.println(obj);

    if (obj != null) {
      this.isValid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public boolean getIsValid() {
    return this.errorCount == 0;
  }

  public int getErrorCount() {
    return this.errorCount;
  }

  public GoatList<ValError> getValErrorList() {
    return this.valErrorList;
  }
}