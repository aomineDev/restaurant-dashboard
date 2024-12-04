package aomine.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
// import com.itextpdf.text.io;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PDF {
  public static void main(String[] args) {
    generateVoucherPDF();
  }

  public static void generateVoucherPDF() {
    try {
      String qrData = "https://www.youtube.com";
      QRCodeWriter qrCodeWritter = new QRCodeWriter();
      BitMatrix bitMatrix = qrCodeWritter.encode(qrData, BarcodeFormat.QR_CODE, 150, 150);

      BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", baos);
      baos.flush();
      byte[] qrImageData = baos.toByteArray();
      baos.close();

      float margin = 10;
      Rectangle ticketSize = new Rectangle(227, 400);  
      Document document = new Document(ticketSize, margin, margin, margin, margin);
    
      PdfWriter.getInstance(document, new FileOutputStream("public/tickets/voucher.pdf"));
      document.open();

      Paragraph title = p("****** TICKET ******");
      title.setAlignment(Element.ALIGN_CENTER);
      document.add(title);

      LocalDate lc = LocalDate.now();
      DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm:ss");
      LocalTime lt = LocalTime.now(); 

      document.add(p("Fecha: " + df.format(lc)));
      document.add(p("Hora: " + tf.format(lt)));

      document.add(p("-".repeat(34)));

      document.add(p(String.format("%-15s Cant P.unit Precio", "Producto")));
      document.add(p(String.format("%-15s %4d %6.2f %6.2f", "Lomo saltado", 2, 10.0, 20.0)));
      document.add(p(String.format("%-15s %4d %6.2f %6.2f", "Coca Cola", 1, 3.0, 3.0)));
      document.add(p("-".repeat(34)));
      String price = String.format("S/. %,.2f", 23.00);
      document.add(p(String.format("Total: %27s", price)));

      document.add(p("-".repeat(34)));
      document.add(p("Gracias por su compra!"));
      document.add(p("*".repeat(34)));

      // Image
      Image qrImage = Image.getInstance(qrImageData);
      qrImage.setAlignment(Element.ALIGN_CENTER);
      document.add(qrImage);

      document.close();
    } catch (DocumentException | WriterException | IOException e) {
      e.printStackTrace();
    } 
  
  }

  public static Paragraph p(String txt) {
    Font font = new Font(Font.FontFamily.COURIER, 10);

    return new Paragraph(txt, font);
  }
}


