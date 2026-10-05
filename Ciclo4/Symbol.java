/**
 * Clase base abstracta que representa un símbolo de la máquina tragamonedas.
 * Permite extensiones polimórficas como EphemeralSymbol, ShySymbol, etc.
 */
public abstract class Symbol {
    protected String color;
    protected String shape;
    protected boolean visible;
    protected int xPosition;
    protected int yPosition;
    protected boolean positioned;
    protected int size;

    /**
     * Constructor base de Symbol.
     * 
     * @param color Color del símbolo.
     * @param shape Forma geométrica (circle, rectangle, triangle).
     */
    public Symbol(String color, String shape) {
        if (color == null || shape == null) {
            throw new IllegalArgumentException("El color y la forma no pueden ser null.");
        }

        if (!shape.equals("circle") && !shape.equals("rectangle") && !shape.equals("triangle")) {
            throw new IllegalArgumentException("La forma debe ser circle, rectangle o triangle.");
        }

        this.color = color;
        this.shape = shape;
        this.visible = false;
        this.xPosition = 0;
        this.yPosition = 0;
        this.positioned = false;
        this.size = 30; // Tamaño inicial estándar
    }

    // --- MÉTODOS ABSTRACTOS Y POLIMÓRFICOS ---

    /**
     * Método polimórfico que define la reacción del símbolo al girar la rueda.
     */
    public abstract void onSpin();

    /**
     * Dibuja o actualiza la representación gráfica del símbolo en el Canvas.
     */
    public abstract void draw();

    // --- MÉTODOS DE VISIBILIDAD Y MOVIMIENTO ---

    public void makeVisible() {
        this.visible = true;
        draw();
    }

    public void makeInvisible() {
        this.visible = false;
        draw();
    }

    public void moveTo(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.positioned = true;
        if (visible) {
            draw();
        }
    }

    public void moveHorizontal(int distance) {
        this.xPosition += distance;
        if (visible) {
            draw();
        }
    }

    public void moveVertical(int distance) {
        this.yPosition += distance;
        if (visible) {
            draw();
        }
    }

    // --- GETTERS Y SETTERS ---

    public String getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return color + " " + shape;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Symbol)) return false;
        Symbol otherSymbol = (Symbol) other;
        return color.equals(otherSymbol.color) && shape.equals(otherSymbol.shape);
    }

    @Override
    public int hashCode() {
        return 31 * color.hashCode() + shape.hashCode();
    }
    /**
     * Cambia la apariencia del símbolo cuando hay jackpot.
     * 
     * @param jackpot true para aumentar o resaltar el símbolo.
     */
    public void setJackpotAppearance(boolean jackpot) {
        this.size = jackpot ? 40 : 30; // Aumenta de tamaño si hay jackpot
        if (visible) {
            draw(); // Redibuja el símbolo con el nuevo tamaño
        }
    }
    private int getBaseX() {
    return 0; // Origen 0 en X para que mande la coordenada de la Rueda
    }

    private int getBaseY() {
        return 0; // Origen 0 en Y para que mande la coordenada de la Rueda
    }
}