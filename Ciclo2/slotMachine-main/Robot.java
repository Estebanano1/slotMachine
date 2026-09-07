
/**
 * Se crea la clase robot, para el juego RobotMaze.
 * 
 * @author Alfonso-Orduz
 * @version (a version number or a date)
 */
public class Robot{
    private int x;
    private int y; 
    private char direction; 
    private boolean ok;
    private boolean isVisible;
    private Triangle robot;
    /**
     * Crea un robot en la posicion indicada.
     */
    public Robot(int x, int y){
        this.x = x;
        this.y = y;
        this.direction = 'N';
        this.ok = true;
        this.isVisible = false;
        
        robot = new Triangle();
        robot.changeColor("red");
        robot.changeSize(20,20);
        
        robot.moveHorizontal(x);
        robot.moveVertical(y);
    }
    /**
     * Muestra las coordenadas en las que esta el robot.
     */
    public int [] coordinates () {
        return new int[] {x,y};
    }
    /**
     * Muestra la direccion en la que esta viendo el robot.
     */
    public char direction () {
        return direction; 
    }
    /**
     * Voltea el robot a cualquier direccion
     */
    public void turn (char newDirection){
            if(newDirection == 'N' || newDirection == 'S' || newDirection == 'E' || newDirection == 'W'){
                direction = newDirection;
                robot.changeDirection(this.direction);
            }
        }
    
    /**
     * Mueve el robot segun en la direccion en la que esta viendo 
     */
    public void move(int cantMovimiento){
        if (direction == 'N') {
            y = y - cantMovimiento;
            robot.moveVertical(-cantMovimiento);
        }
        else if (direction == 'S') {
            y = y + cantMovimiento;
            robot.moveVertical(cantMovimiento);
        }
        else if (direction == 'E') {
            x = x + cantMovimiento;
            robot.moveHorizontal(cantMovimiento);
        }
        else if (direction == 'W') {
            x = x - cantMovimiento;
            robot.moveHorizontal(-cantMovimiento);
        }
    }
    /**
     * Hace visible el robot
     */
    public void makeVisible (){
        isVisible = true;
        robot.makeVisible();
    }
    /**
     * Hace invisible al robot
     */
    public void makeInvisible(){
        isVisible = false;
        robot.makeInvisible();
    }
}