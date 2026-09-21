import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static final int VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private char direction;
    
    /**
     * Mueve el triangulo en diagonal 
     */
    
    public void moveDiagonal (int distance) {
        moveHorizontal (distance);
        moveVertical (distance);
    }
    /**
     * Convierte un triangulo cualquiera en un equilatero
     */
    
    public void equilateral() {
        double areaActual = area(height, width);
        int newWidth = (int)Math.sqrt((4 * areaActual) / Math.sqrt(3));
        int newHeight = (int)(newWidth * 0.866);
        changeSize (newHeight, newWidth); 
        
    }
    
    /**
     * Mueve el triangulo las veces que dice ads(times)
     */
    public void walk(int times) {
        if (times > 0) {
            for (int i = 0; i < times; i ++) {
                moveHorizontal(20);
                moveVertical(20);
            }
        }
        else {
            for (int i = 0; i < -times; i ++) {
                moveHorizontal(-20);
                moveVertical(20);
            }
        }
    }
    /**
     * Saca el area del triangulo. 
     */
    public double area(int height, int width){
        double area = (height * width) / 2.00; 
        return area;
    }

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
        direction = 'N';
    }
    
    /**
     * Nuevo creador
     */
    public Triangle(String color, int height, int width){
        this.color = color;
        this.height = height;
        this.width = width;
        xPosition = 140;
        yPosition = 15;
        direction = 'N';
        
    }
    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        if (newHeight > 0) {
            height = newHeight;
        }
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    public void changeDirection (char newDirection) {
        erase ();
        direction = newDirection;
        draw();
    }
    /*
     * Draw the triangle with current specifications on screen.
     */
    private void draw(){
    if(isVisible) {
        Canvas canvas = Canvas.getCanvas();

        int[] xpoints;
        int[] ypoints;

        if(direction == 'N') {
            xpoints = new int[] {
                xPosition,
                xPosition + (width / 2),
                xPosition - (width / 2)
            };

            ypoints = new int[] {
                yPosition,
                yPosition + height,
                yPosition + height
            };
        }
        else if(direction == 'S') {
            xpoints = new int[] {
                xPosition,
                xPosition + (width / 2),
                xPosition - (width / 2)
            };

            ypoints = new int[] {
                yPosition + height,
                yPosition,
                yPosition
            };
        }
        else if(direction == 'E') {
            xpoints = new int[] {
                xPosition + height,
                xPosition,
                xPosition
            };

            ypoints = new int[] {
                yPosition,
                yPosition - (width / 2),
                yPosition + (width / 2)
            };
        }
        else {
            xpoints = new int[] {
                xPosition - height,
                xPosition,
                xPosition
            };

            ypoints = new int[] {
                yPosition,
                yPosition - (width / 2),
                yPosition + (width / 2)
            };
        }

        canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
        canvas.wait(10);
    }
}

    /*
     * Erase the triangle on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
