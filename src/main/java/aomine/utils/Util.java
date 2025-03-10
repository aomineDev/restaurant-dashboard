package aomine.utils;

/*
  SIN USO
 */

public class Util {
  public static Integer parseInt(String str) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
