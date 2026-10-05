import java.util.ArrayList;
import java.util.List;

/**
 * Clase SlotMachineContest para resolver y simular el problema de la maratón (Ciclo 3).
 */
public class SlotMachineContest {

    private SlotMachine slotMachine;

    public SlotMachineContest() {
    }

    /**
     * Resuelve el problema respetando estrictamente las restricciones del enunciado:
     * - Permanece invisible.
     * - Solo utiliza SlotMachine(n), spin(wheel, steps) y distinctSymbols().
     * 
     * @param n Número de ruedas y símbolos (debe ser > 0).
     * @return Matriz int[][] con las acciones {rueda, pasos} o una matriz vacía si n es inválido.
     */
    public int[][] solve(int n) {
    if (n <= 0) {
        return new int[0][0];
    }

    slotMachine = new SlotMachine(n);
    slotMachine.makeInvisible();

    List<int[]> moves = new ArrayList<>();

    if (slotMachine.distinctSymbols() == 1) {
        System.out.println("¡Ya está en Jackpot desde el inicio!");
        return moves.toArray(new int[0][0]);
    }

    for (int wheel = 1; wheel <= n; wheel++) {
        int currentDistinct = slotMachine.distinctSymbols();

        if (currentDistinct == 1) {
            break;
        }

        int bestSteps = 0;
        int minDistinct = currentDistinct;

        for (int steps = 1; steps < n; steps++) {
            slotMachine.spin(wheel, 1);
            int distinct = slotMachine.distinctSymbols();

            if (distinct < minDistinct) {
                minDistinct = distinct;
                bestSteps = steps;
            }
        }

        int remainingToBest = (bestSteps - (n - 1)) % n;
        if (remainingToBest < 0) {
            remainingToBest += n;
        }

        if (remainingToBest > 0) {
            slotMachine.spin(wheel, remainingToBest);
        }

        if (bestSteps > 0) {
            moves.add(new int[]{wheel, bestSteps});
            // IMprimir en la consola de BlueJ:
            System.out.println("Movimiento registrado: Rueda " + wheel + " -> " + bestSteps + " pasos.");
        }
    }

    int[][] result = moves.toArray(new int[moves.size()][2]);
    
    // Imprimir resumen de la solución
    System.out.println("--- SOLUCIÓN ENCONTRADA ---");
    System.out.println("Total de movimientos: " + result.length);
    for (int i = 0; i < result.length; i++) {
        System.out.println("Paso " + (i + 1) + ": Rueda " + result[i][0] + " giró " + result[i][1] + " posiciones.");
    }
    
    return result;
}

    /**
     * Simula las acciones calculadas por solve(n) haciendo visible la interfaz gráfica.
     * 
     * @param n Número de ruedas y símbolos.
     */
    public void simulate(int n) {
        if (n <= 0) {
            return;
        }

        int[][] solution = solve(n);

        if (slotMachine != null) {
            slotMachine.makeVisible();

            for (int[] move : solution) {
                int wheel = move[0];
                int steps = move[1];
                slotMachine.spin(wheel, steps);
            }
        }
    }
}