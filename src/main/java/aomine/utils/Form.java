package aomine.utils;

import javax.swing.JComponent;

import aomine.components.input.GoatInput;
import aomine.components.input.GoatTextInput;
import aomine.components.input.SelectInput;

public class Form {
  public static void cleanErrorOnInput(GoatInput<? extends JComponent> ti) {
    ti.setErrorHint(false);
    ti.setLabelErrorText("");
  }

  public static void cleanErrorOnInput(GoatList<GoatInput<? extends JComponent>> formInputList) {
    formInputList.forEach(input -> {
      input.setErrorHint(false);
      input.setLabelErrorText("");
    });
  }

  public static void cleanInputs(GoatList<GoatInput<? extends JComponent>> formInputList) {

    formInputList.forEach(input -> {
      if (input instanceof GoatTextInput) {
        ((GoatTextInput<?>) input).setText("");
      } else if (input instanceof SelectInput) {
        ((SelectInput<?>) input).getInput().setSelectedIndex(0);
      }
    });
  }
}
