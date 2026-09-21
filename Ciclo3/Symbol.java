/**
 * Representa un simbolo de la maquina tragamonedas.
 * Un simbolo puede ser un circulo, rectangulo o triangulo.
 */
public class Symbol
{
    private String color;
    private String shape;
    private boolean visible;

    private Circle circle;
    private Rectangle rectangle;
    private Triangle triangle;

    /* Posicion logica del simbolo dentro de la maquina. */
    private int xPosition;
    private int yPosition;
    private boolean positioned;

    /**
     * Crea un simbolo.
     *
     * @param color Color del simbolo.
     * @param shape Forma del simbolo: circle, rectangle o triangle.
     */
    public Symbol(String color, String shape)
    {
        if(color == null || shape == null)
        {
            throw new IllegalArgumentException("El color y la forma no pueden ser null.");
        }

        if(!shape.equals("circle") &&
           !shape.equals("rectangle") &&
           !shape.equals("triangle"))
        {
            throw new IllegalArgumentException(
                "La forma debe ser circle, rectangle o triangle."
            );
        }

        this.color = color;
        this.shape = shape;
        visible = false;
        xPosition = 0;
        yPosition = 0;
        positioned = false;

        if(shape.equals("circle"))
        {
            circle = new Circle();
            circle.changeColor(color);
            circle.changeSize(30);
        }
        else if(shape.equals("rectangle"))
        {
            rectangle = new Rectangle();
            rectangle.changeColor(color);
            rectangle.changeSize(30, 30);
        }
        else
        {
            triangle = new Triangle();
            triangle.changeColor(color);
            triangle.changeSize(30, 30);
        }
    }

    /**
     * Hace visible el simbolo.
     */
    public void makeVisible()
    {
        visible = true;

        if(circle != null)
        {
            circle.makeVisible();
        }
        else if(rectangle != null)
        {
            rectangle.makeVisible();
        }
        else
        {
            triangle.makeVisible();
        }
    }

    /**
     * Hace invisible el simbolo.
     */
    public void makeInvisible()
    {
        visible = false;

        if(circle != null)
        {
            circle.makeInvisible();
        }
        else if(rectangle != null)
        {
            rectangle.makeInvisible();
        }
        else
        {
            triangle.makeInvisible();
        }
    }

    /**
     * Coloca el simbolo en una posicion absoluta.
     * Este metodo funciona aunque el simbolo ya haya sido movido anteriormente.
     *
     * @param x Nueva posicion horizontal.
     * @param y Nueva posicion vertical.
     */
    public void moveTo(int x, int y)
    {
        int currentX;
        int currentY;

        if(!positioned)
        {
            currentX = getBaseX();
            currentY = getBaseY();
        }
        else
        {
            currentX = xPosition;
            currentY = yPosition;
        }

        moveShapes(x - currentX, y - currentY);

        xPosition = x;
        yPosition = y;
        positioned = true;
    }

    /**
     * Cambia la posicion horizontal del simbolo.
     *
     * @param distance Distancia que se mueve.
     */
    public void moveHorizontal(int distance)
    {
        moveShapes(distance, 0);

        if(positioned)
        {
            xPosition += distance;
        }
    }

    /**
     * Cambia la posicion vertical del simbolo.
     *
     * @param distance Distancia que se mueve.
     */
    public void moveVertical(int distance)
    {
        moveShapes(0, distance);

        if(positioned)
        {
            yPosition += distance;
        }
    }

    /**
     * Mueve las figuras que componen el simbolo.
     */
    private void moveShapes(int horizontal, int vertical)
    {
        if(circle != null)
        {
            circle.moveHorizontal(horizontal);
            circle.moveVertical(vertical);
        }
        else if(rectangle != null)
        {
            rectangle.moveHorizontal(horizontal);
            rectangle.moveVertical(vertical);
        }
        else
        {
            triangle.moveHorizontal(horizontal);
            triangle.moveVertical(vertical);
        }
    }

    /**
     * Retorna la posicion horizontal inicial de cada figura de shapes.
     */
    private int getBaseX()
    {
        if(circle != null)
        {
            return 20;
        }
        else if(rectangle != null)
        {
            return 70;
        }
        else
        {
            return 140;
        }
    }

    /**
     * Retorna la posicion vertical inicial de cada figura de shapes.
     */
    private int getBaseY()
    {
        return 15;
    }

    /**
     * Cambia la apariencia del simbolo cuando hay jackpot.
     *
     * @param jackpot true para resaltar el simbolo.
     */
    public void setJackpotAppearance(boolean jackpot)
    {
        int size = jackpot ? 40 : 30;

        if(circle != null)
        {
            circle.changeSize(size);
        }
        else if(rectangle != null)
        {
            rectangle.changeSize(size, size);
        }
        else
        {
            triangle.changeSize(size, size);
        }
    }

    /**
     * Retorna el color del simbolo.
     *
     * @return color del simbolo.
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Retorna la forma del simbolo.
     *
     * @return forma del simbolo.
     */
    public String getShape()
    {
        return shape;
    }

    /**
     * Consulta si el simbolo es visible.
     *
     * @return true si es visible.
     */
    public boolean isVisible()
    {
        return visible;
    }
    /**
     * Retorna una descripcion legible del simbolo.
     *
     * @return color y forma del simbolo.
     */
    @Override
    public String toString()
    {
        return color + " " + shape;
    }
    /**
     * Compara dos simbolos por color y forma.
     *
     * @param other objeto que se desea comparar.
     * @return true si tienen el mismo color y forma.
     */
    @Override
    public boolean equals(Object other)
    {
        if(this == other)
        {
            return true;
        }

        if(!(other instanceof Symbol))
        {
            return false;
        }

        Symbol otherSymbol = (Symbol)other;
        return color.equals(otherSymbol.color) && shape.equals(otherSymbol.shape);
    }

    /**
     * Calcula el codigo hash del simbolo.
     *
     * @return codigo hash.
     */
    @Override
    public int hashCode()
    {
        return 31 * color.hashCode() + shape.hashCode();
    }

}