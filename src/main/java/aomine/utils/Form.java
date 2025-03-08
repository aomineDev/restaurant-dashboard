package aomine.utils;

import javax.swing.JComponent;

import aomine.components.input.GoatInput;

public class Form {
  public static void clearErrorInputList(GoatList<GoatInput<? extends JComponent>> formInputList) {
    formInputList.forEach(input -> input.clearError());
  }

  public static void clearInputList(GoatList<GoatInput<? extends JComponent>> formInputList) {
    formInputList.forEach(input -> input.clear());
  }

  public static void fullClearInputList(GoatList<GoatInput<? extends JComponent>> formInputList) {
    formInputList.forEach(input -> {
      input.clear();
      input.clearError();
    });
  }
}
