/**
 * Rueda Speedy: Duplica la cantidad de pasos en cada giro.
 */
public class SpeedyWheel extends Wheel {

    public SpeedyWheel(int x, int y) {
        super(x, y);
    }

    @Override
    public void spinSteps(int steps) {
        super.spinSteps(steps * 2);
    }
}