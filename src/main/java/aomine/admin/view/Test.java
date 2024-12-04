package aomine.admin.view;

import javax.swing.JButton;
import java.awt.Desktop;

import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.File;
import java.io.IOException;

import aomine.components.layout.view.SimpleView;

public class Test extends SimpleView {
  public Test() {
    init();
  }

  private void init() {
    JButton btn = new JButton("asd");
    btn.addActionListener(e -> {
      try {
        String pdfPath = Paths.get("voucher.pdf").toString();
        File pdf = new File(pdfPath);
        System.out.println(pdfPath);
        if (pdf.exists()) {
          if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(pdf.toURI());
          } else {
            System.out.println("Desktop not supported");
          }
        } else {
          System.out.println("archivo no existe");
        }

      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    add(btn);
  }
}
