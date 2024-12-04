package aomine.utils;

import aomine.model.Reniec;
import aomine.model.Sunat;
import aomine.services.PersonService;

public class Request {

  public static void main(String[] args) {
    try {
      PersonService.full("20378890161");
      // Sunat r = PersonService.getInfoByRUC("10715975951");
      // Reniec r = PersonService.getPersonByDNI("75450784");
      
      // System.out.println(r.getRazonSocial());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
