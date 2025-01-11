# Formas de redondear un panel

## Usando fill
```java
public class RoundedPanel extends JPanel {
  private int arc;

  public RoundedPanel(int arc) {
    this.arc = arc;
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(getBackground()); // Color.BLUE

    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));

    super.paintComponent(g2);

    g2.dispose();
  }
}
```

- usamos setOpaque para eliminar el fondo original (hacerlo transparente)
- creamos un rectangulo redondeado con color y lo pintamos dentro del panel (se crea debajo del fondo del panel, por lo que no se vera si no borramos el fondo oirignal)
- le damos el color original del panel (podemos cambiarlo si queremos)

## Usando setClip
```java
public class RoundedPanel extends JPanel {
  private int arc;

  public RoundedPanel(int arc) {
    this.arc = arc;
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));

    super.paintComponent(g2);

    g2.dispose();
  }
}
```

- con setClip definimos un area limite para que se pinte el contenido, en este caso el rectangulo redondeado solo define el area limite.
- el fondo se pinta respentando los limites por lo que no tenemos que eliminarlo
- no tenemos que definir un color por que no pintamos el rectangulo, solo lo usamos como area limite.