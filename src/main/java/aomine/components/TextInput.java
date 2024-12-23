package aomine.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.function.Consumer;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class TextInput extends JPanel {
  private String placeholder;
  private String lblText;
  private String value;
  private String lblErrorText;
  private boolean errorHintState;

  private JTextField textField;
  private JLabel lblError;
  private JLabel lbl;

  private Consumer<KeyEvent> handleKeyTyped;
  private Consumer<KeyEvent> handleKeyPressed;
  private Consumer<KeyEvent> handleKeyReleased;
  private Consumer<DocumentEvent> handleChanged;

  public TextInput(TextInputBuilder builder) {
    this.placeholder = builder.placeholder;
    this.lblText = builder.lblText;
    this.value = builder.value;
    this.lblErrorText = builder.lblErrorText;
    this.errorHintState = false;

    init();
  }

  private void init() {
    setLayout(new MigLayout("insets 0, debug, flowy, fillx", "[fill]"));

    if (this.lblText != null) {
      this.lbl = new JLabel(this.lblText);
      add(this.lbl);
    }

    this.textField = new JTextField(this.value);

    this.textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, this.placeholder);

    this.addKeyListener();

    this.addDocumentListener();

    add(this.textField);

    if (this.lblErrorText != null) {
      this.lblError = new JLabel(this.lblErrorText);
      this.lblError.putClientProperty(FlatClientProperties.STYLE,
          "font: -1;" +
              "foreground: rgb(255, 130, 130);");
      add(this.lblError);
    }
  }

  public void addKeyListener() {
    this.textField.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (handleKeyTyped != null)
          handleKeyTyped.accept(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (handleKeyPressed != null)
          handleKeyPressed.accept(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (handleKeyReleased != null)
          handleKeyReleased.accept(e);
      }
    });
  }

  public void addDocumentListener() {
    this.textField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        if (handleChanged != null)
          handleChanged.accept(e);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        if (handleChanged != null)
          handleChanged.accept(e);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        if (handleChanged != null)
          handleChanged.accept(e);
      }
    });
  }

  public void setFieldErrorHint(boolean value) {
    if (errorHintState == value)
      return;

    String outline = value ? FlatClientProperties.OUTLINE_ERROR : null;

    this.textField.putClientProperty(FlatClientProperties.OUTLINE, outline);
  }

  public void setLblErrorText(String txt) {
    if (this.lblError == null)
      return;

    this.lblError.setText(txt);
  }

  public void setLeftIcon(String iconPath) {
    this.textField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, getIcon(iconPath));
  }

  public void setRightIcon(String iconPath) {
    this.textField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, getIcon(iconPath));
  }

  private ImageIcon getIcon(String iconPath) {
    return new ImageIcon(getClass().getResource("/aomine/icons/" + iconPath));
  }

  public String getText() {
    return this.textField.getText();
  }

  public void setText(String txt) {
    this.textField.setText(txt);
  }

  public void onKeyTyped(Consumer<KeyEvent> handleKeyTyped) {
    this.handleKeyTyped = handleKeyTyped;
  }

  public void onKeyPressed(Consumer<KeyEvent> handleKeyPressed) {
    this.handleKeyPressed = handleKeyPressed;
  }

  public void onKeyReleased(Consumer<KeyEvent> handleKeyReleased) {
    this.handleKeyReleased = handleKeyReleased;
  }

  public void onChanged(Consumer<DocumentEvent> handleChanged) {
    this.handleChanged = handleChanged;
  }

  public static class TextInputBuilder {
    private String placeholder;
    private String lblText;
    private String value;
    private String lblErrorText;

    public TextInputBuilder() {
      placeholder = "";
      value = "";
    }

    public TextInputBuilder placeholder(String placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public TextInputBuilder label(String lblText) {
      this.lblText = lblText;
      return this;
    }

    public TextInputBuilder value(String value) {
      this.value = value;
      return this;
    }

    public TextInputBuilder errorLabel() {
      this.lblErrorText = "";
      return this;
    }

    public TextInput build() {
      return new TextInput(this);
    }
  }
}
