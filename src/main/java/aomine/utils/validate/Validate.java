package aomine.utils.validate;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import javax.swing.text.JTextComponent;

import org.hibernate.Session;

import aomine.components.input.GoatTextInput;
import aomine.database.Hibernate;
import aomine.utils.GoatList;

public class Validate {
  private GoatTextInput<? extends JTextComponent> input;
  private String text;
  private boolean valid;
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

  public Validate setElement(GoatTextInput<? extends JTextComponent> input) {
    this.input = input;
    this.text = input.getText();
    this.valid = true;

    return this;
  }

  public Validate isRequired(String msg) {
    if (!this.valid)
      return this;

    if (text.equals("")) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate minLength(String msg, int min) {
    if (!this.valid)
      return this;

    if (text.length() < min) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate maxLength(String msg, int max) {
    if (!this.valid)
      return this;

    if (text.length() > max) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate equalLength(String msg, int equal) {
    if (!this.valid)
      return this;

    if (text.length() != equal) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isNumber(String msg) {
    if (!this.valid)
      return this;

    if (!text.matches("[0-9]+")) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isInteger(String msg) {
    if (!this.valid)
      return this;

    try {
      Integer.parseInt(text);
    } catch (Exception e) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isDouble(String msg) {
    if (!this.valid)
      return this;

    try {
      Double.parseDouble(text);
    } catch (Exception e) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isLong(String msg) {
    if (!this.valid)
      return this;

    try {
      Long.parseLong(text);
    } catch (Exception e) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate selfValidate(String msg, Predicate<String> predicate) {
    if (!this.valid)
      return this;

    if (!predicate.test(text)) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isEmail(String msg) {
    if (!this.valid)
      return this;

    if (!text.matches("^[a-zA-Z0-9.-_+%]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isDate(String msg) {
    if (!this.valid)
      return this;

    if (!text.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$")) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isUnique(String msg, Class<?> Entity, String column) {
    if (!this.valid)
      return this;

    String query = String.format("FROM %s WHERE %s = :%s", Entity.getSimpleName(), column, column);

    Object obj = session.createQuery(query, Entity)
        .setParameter(column, text)
        .uniqueResult();

    if (obj != null) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate isUnique(String msg, Class<?> Entity, String column, long id) {
    if (!this.valid)
      return this;

    String query = String.format("SELECT id FROM %s WHERE %s = :%s", Entity.getSimpleName(), column, column);

    Object obj = session.createQuery(query, Entity)
        .setParameter(column, text)
        .uniqueResult();

    if (obj != null) {
      this.valid = false;
      this.valErrorList.add(new ValError(this.input, msg));
      this.errorCount++;
    }

    return this;
  }

  public Validate setText(UnaryOperator<String> operator) {
    this.text = operator.apply(this.text);

    return this;
  }

  public boolean isValid() {
    return this.errorCount == 0;
  }

  public int getErrorCount() {
    return this.errorCount;
  }

  public GoatList<ValError> getValErrorList() {
    return this.valErrorList;
  }

}