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

## Usando Flat laf

```java
panel = new JPanel();

panel.putClientProperty(FlatClientProperties.STYLE, "arc: 50;");
```

- Usara el color del fondo del padre para redondear el panel
- Si el padre es un panel con imagen no funcionara
> necesitaremos usar `panel.setOpaque(false)` para que funcione

# Obtener instancia de una clase hija en una clase padre

## Cambiando el tipo de retorno

- se tiene acceso a la instancia de la clase hija
- no se tiene acceso a la clase hija

```java
abstract class Padre {
  
  public abstract Padre self();
}

class Hijo extends Padre {
  @Override
  public Hijo self() {
    return this;
  }
}
```
## Usando genericos

- se tiene acceso a la instancia de la clase hija
- se tiene acceso a la clase hija

```java
abstract class Padre<T extends Padre<T>> {

  public abstract T self();
}

class Hijo extends Padre<Hijo> {
  @Override
  public Hijo self() {
    return this;
  }
}
```

# Rutas

## Obtener ruta a la carpeta resources

## Obtener ruta a la raiz del proyecto

## Obtener ruta a la carpeta user en el disco C

# Componentes Internos
## GoatPanel
## Inputs
### TextInput
### PasswordInput
### MaskInput
### SelectInput
## Validate