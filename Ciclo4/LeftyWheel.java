public class LeftyWheel extends Wheel {

    public LeftyWheel(int x, int y) {
        super(x, y);
    }

    public void spinStepsLefty(int steps, Wheel leftWheel) {
        if (isLocked) return;

        // 1. Girar la rueda
        super.spinSteps(steps);

        // 2. Copiar el símbolo activo de la rueda izquierda
        if (leftWheel != null && leftWheel.getCurrentSymbol() != null) {
            String targetColor = leftWheel.getCurrentSymbol().getColor();

            // Si el color no existe en los símbolos de esta rueda, se añade
            boolean exists = false;
            for (Symbol s : symbols) {
                if (s.getColor().equalsIgnoreCase(targetColor)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                addSymbol(new NormalSymbol(targetColor, "circle"));
            }

            setSymbolByColor(targetColor);
        }
    }
}