package aomine.components.input;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;

public interface TextComponent {
  // GoatInput
  void setErrorHint(boolean val);

  void setLabelErrorText(String str);

  public JLabel getErrorLabel();

  public JLabel getLabel();

  void setLabel(String str);

  void setLeftIcon(String iconPath, float scale);

  void setRightIcon(String iconPath, float scale);

  JComponent getInput();

  // GoatTextInput
  void onKeyTyped(Consumer<KeyEvent> handleKeyTyped);

  void onKeyPressed(Consumer<KeyEvent> handleKeyPressed);

  void onKeyReleased(Consumer<KeyEvent> handleKeyReleased);

  public void onChanged(Consumer<DocumentEvent> handleChanged);

  String getText();

  void setText(String str);
}
