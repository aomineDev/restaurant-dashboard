package aomine.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import java.awt.TextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.function.Consumer;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class TextInput {
  private String placeholder;
  private String lblText;
  private String value;
  private String lblErrorText;
  private boolean isPassword;
  private boolean errorHintState;

  private JTextComponent input;
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
    this.isPassword = builder.isPassword;
    this.errorHintState = false;

    init();
  }

  private void init() {
    if (this.lblText != null) {
      this.lbl = new JLabel(this.lblText);
    }

    if (isPassword) {
      this.input = new JPasswordField(this.value) {
        @Override
        public String getText() {
          return String.valueOf(getPassword());
        }
      };

      this.input.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
    } else
      this.input = new JTextField(this.value);

    this.input.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, this.placeholder);

    addKeyListener();
    addDocumentListener();

    if (this.lblErrorText != null) {
      this.lblError = new JLabel(this.lblErrorText);
      this.lblError.putClientProperty(FlatClientProperties.STYLE,
          "font: -1;" +
              "foreground: rgb(255, 130, 130);");
    }
  }

  public void addKeyListener() {
    this.input.addKeyListener(new KeyListener() {
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
    this.input.getDocument().addDocumentListener(new DocumentListener() {
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

  public void setErrorHint(boolean newState) {
    if (errorHintState == newState)
      return;

    errorHintState = newState;
    String outline = newState ? FlatClientProperties.OUTLINE_ERROR : null;

    this.input.putClientProperty(FlatClientProperties.OUTLINE, outline);
  }

  public void setLblErrorText(String txt) {
    if (this.lblError == null)
      return;

    if (this.lblError.getText() == txt)
      return;

    this.lblError.setText(txt);
  }

  public void setLeftIcon(String iconPath) {
    this.input.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, getIcon(iconPath));
  }

  public void setRightIcon(String iconPath) {
    this.input.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, getIcon(iconPath));
  }

  private ImageIcon getIcon(String iconPath) {
    return new ImageIcon(getClass().getResource("/aomine/icons/" + iconPath));
  }

  public String getText() {
    return this.input.getText();
  }

  public void setText(String txt) {
    this.input.setText(txt);
  }

  public JLabel getLabel() {
    return this.lbl;
  }

  public JTextComponent getInput() {
    return this.input;
  }

  public JLabel getErrorLabel() {
    return this.lblError;
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
    private boolean isPassword;

    public TextInputBuilder() {
      this.placeholder = "";
      this.isPassword = false;
    }

    public TextInputBuilder setPlaceholder(String placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public TextInputBuilder setLabelText(String lblText) {
      this.lblText = lblText;
      return this;
    }

    public TextInputBuilder setValue(String value) {
      this.value = value;
      return this;
    }

    public TextInputBuilder withErrorLabel() {
      this.lblErrorText = "";
      return this;
    }

    public TextInputBuilder setPassword(boolean value) {
      this.isPassword = value;
      return this;
    }

    public TextInput build() {
      return new TextInput(this);
    }
  }
}
