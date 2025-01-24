package aomine.components.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;

public abstract class GoatTextInput<T> extends GoatInput<T> implements TextComponent {
  protected String placeholder;
  private Consumer<KeyEvent> handleKeyTyped;
  private Consumer<KeyEvent> handleKeyPressed;
  private Consumer<KeyEvent> handleKeyReleased;
  private Consumer<DocumentEvent> handleChanged;

  @Override
  protected void initialize() {
    addKeyListener();
    addDocumentListener();

    super.initialize();
  }

  private void addKeyListener() {
    this.getInput().addKeyListener(new KeyListener() {
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

  private void addDocumentListener() {
    this.getInput().getDocument().addDocumentListener(new DocumentListener() {
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

  protected void applyPlaceholder() {
    this.getInput().putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, this.placeholder);
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

  public void setText(String str) {
    this.getInput().setText(str);
  }

  public abstract String getText();

  @Override
  public JTextComponent getInput() {
    return (JTextComponent) this.input;
  }

  protected static abstract class GoatTextInputBuilder<U> extends GoatInput.GoatInputBuilder<U> {
    protected String placeholder;

    public U setPlaceholder(String placeholder) {
      this.placeholder = placeholder;
      return self();
    }
  }
}
