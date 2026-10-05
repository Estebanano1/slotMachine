public class NormalSymbol extends Symbol {

    private Circle circle;
    private Rectangle rectangle;
    private Triangle triangle;

    public NormalSymbol(String color, String shape) {
        super(color, shape);
        
        // Crear las figuras PERO NO HACERLAS VISIBLES AÚN
        if (shape.equalsIgnoreCase("circle")) {
            circle = new Circle();
            circle.changeColor(color);
            circle.changeSize(size);
        } else if (shape.equalsIgnoreCase("rectangle")) {
            rectangle = new Rectangle();
            rectangle.changeColor(color);
            rectangle.changeSize(size, size);
        } else if (shape.equalsIgnoreCase("triangle")) {
            triangle = new Triangle();
            triangle.changeColor(color);
            triangle.changeSize(size, size);
        }
    }

    @Override
    public void moveTo(int x, int y) {
        // Calcular la distancia relativa que debe moverse la figura desde su origen actual
        int currentX = positioned ? xPosition : getBaseX();
        int currentY = positioned ? yPosition : getBaseY();

        int deltaX = x - currentX;
        int deltaY = y - currentY;

        moveShapes(deltaX, deltaY);

        this.xPosition = x;
        this.yPosition = y;
        this.positioned = true;
    }

    private void moveShapes(int horizontal, int vertical) {
        if (circle != null) {
            circle.moveHorizontal(horizontal);
            circle.moveVertical(vertical);
        } else if (rectangle != null) {
            rectangle.moveHorizontal(horizontal);
            rectangle.moveVertical(vertical);
        } else if (triangle != null) {
            triangle.moveHorizontal(horizontal);
            triangle.moveVertical(vertical);
        }
    }

    private int getBaseX() {
        if (circle != null) return 20;
        if (rectangle != null) return 70;
        return 140;
    }

    private int getBaseY() {
        return 15;
    }

    @Override
    public void onSpin() {
        // Comportamiento normal
    }

    @Override
    public void draw() {
        if (visible) {
            if (circle != null) circle.makeVisible();
            else if (rectangle != null) rectangle.makeVisible();
            else if (triangle != null) triangle.makeVisible();
        } else {
            makeInvisibleShapes();
        }
    }

    @Override
    public void makeInvisible() {
        super.makeInvisible();
        makeInvisibleShapes();
    }

    private void makeInvisibleShapes() {
        if (circle != null) circle.makeInvisible();
        if (rectangle != null) rectangle.makeInvisible();
        if (triangle != null) triangle.makeInvisible();
    }
}