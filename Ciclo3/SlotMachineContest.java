import java.util.HashSet;
import java.util.Set;

/**
 * Solucionador y simulador para el concurso Slot Machine (Ciclo 3).
 * Requisitos 14 y 15.
 */
public class SlotMachineContest {

    private boolean lastOperationOk;

    public SlotMachineContest() {
        this.lastOperationOk = true;
    }

    /**
     * Retorna la cantidad mínima de pasos/acciones necesarias para ganar.
     * La máquina se mantiene INVISIBLE durante este proceso.
     * 
     * @param n Número de ruedas y símbolos
     * @return Número de movimientos o pasos necesarios
     */
    public int solve(int n) {
        if (n <= 0) {
            this.lastOperationOk = false;
            return -1;
        }

        this.lastOperationOk = true;

        SlotMachine machine = new SlotMachine(n);
        machine.makeInvisible();

        int moves = 0;
        int maxAttempts = n * n * 10;


        while (machine.distinctSymbols() > 1 && moves < maxAttempts) {
            machine.spin(0, 1);
            moves++;
        }

        if (moves >= maxAttempts && machine.distinctSymbols() > 1) {
            this.lastOperationOk = false;
            return -1;
        }

        return moves;
    }

    /**

     * La máquina se mantiene VISIBLE durante este proceso.
     * 
     * @param n Número de ruedas y símbolos
     */
    public void simulate(int n) {
        if (n <= 0) {
            this.lastOperationOk = false;
            return;
        }

        this.lastOperationOk = true;

        // Regla C3: Usar SlotMachine como simulador VISIBLE
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();

        while (machine.distinctSymbols() > 1) {
            machine.spin(0, 1);
        }
    }

    /**
     * Consulta el estado de la última operación realizada.
     * @return true si la última operación fue exitosa, false de lo contrario.
     */
    public boolean ok() {
        return lastOperationOk;
    }
}