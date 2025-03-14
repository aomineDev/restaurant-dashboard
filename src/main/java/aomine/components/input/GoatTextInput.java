package aomine.components.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.formdev.flatlaf.FlatClientProperties;

public abstract class GoatTextInput<T extends JTextComponent> extends GoatInput<T> {
  protected String placeholder;
  private Consumer<KeyEvent> handleKeyTyped;
  private Consumer<KeyEvent> handleKeyPressed;
  private Consumer<KeyEvent> handleKeyReleased;
  private Consumer<DocumentEvent> handleChanged;

  public GoatTextInput(GoatTextInputBuilder<?, ?> builder) {
    super(builder);
    this.placeholder = builder.placeholder;
  }

  @Override
  protected void initialize() {
    addKeyListener();
    addDocumentListener();

    super.initialize();
  }

  private void addKeyListener() {
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

  private void addDocumentListener() {
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

  protected void applyPlaceholder() {
    if (this.placeholder == null)
      return;

    this.input.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, this.placeholder);
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
    applyPlaceholder();
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
    this.input.setText(str);
  }

  public String getText() {
    if (this.input.getText().equals(""))
      return null;

    return this.input.getText();
  }

  @Override
  public void clear() {
    this.input.setText(null);
  }

  protected static abstract class GoatTextInputBuilder<U, T extends JTextComponent>
      extends GoatInput.GoatInputBuilder<U, T> {
    protected String placeholder;

    public U setPlaceholder(String placeholder) {
      this.placeholder = placeholder;
      return self();
    }
  }
}
