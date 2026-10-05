import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad y calidad para SlotMachineContest (Ciclo 3).
 */
public class SlotMachineContestTest {

    private SlotMachineContest contest;

    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    // --- PRUEBAS DE CASOS VÁLIDOS ---

    @Test
    public void shouldSolveContestForStandardN() {
        int n = 3;
        int[][] solution = contest.solve(n);

        assertNotNull("solve() no debe retornar null para entradas válidas", solution);
        
        // Verificar que cada movimiento devuelto tenga el formato {rueda, pasos}
        for (int[] move : solution) {
            assertEquals("Cada acción debe ser un par {rueda, pasos}", 2, move.length);
            assertTrue("La rueda debe estar entre 1 y n", move[0] >= 1 && move[0] <= n);
            assertTrue("Los pasos deben ser mayores a 0", move[1] > 0);
        }
    }

    @Test
    public void shouldSolveWithinAllowedActionLimit() {
        int n = 5;
        int[][] solution = contest.solve(n);

        assertNotNull(solution);
        // La maratón permite máximo 10,000 acciones
        assertTrue("La solución no debe superar las 10,000 acciones", solution.length <= 10000);
    }

    @Test
    public void shouldSimulateWithoutErrors() {
        // Verifica que la simulación se ejecute correctamente sin lanzar excepciones
        try {
            contest.simulate(3);
        } catch (Exception e) {
            fail("simulate(3) no debería lanzar excepciones: " + e.getMessage());
        }
    }

    // --- PRUEBAS DE FRONTERA Y BORDES (EDGE CASES) ---

    @Test
    public void shouldNotSolveWithZeroN() {
        int[][] solution = contest.solve(0);
        assertNotNull("solve(0) debe retornar una matriz vacía", solution);
        assertEquals("El número de movimientos debe ser 0 para n=0", 0, solution.length);
    }

    @Test
    public void shouldNotSolveWithNegativeN() {
        int[][] solution = contest.solve(-5);
        assertNotNull("solve() con n negativo debe retornar una matriz vacía", solution);
        assertEquals("El número de movimientos debe ser 0 para n negativo", 0, solution.length);
    }

    @Test
    public void shouldHandleSimulateWithInvalidN() {
        // Asegura que simulate no falle estrepitosamente si n es inválido
        try {
            contest.simulate(-1);
            contest.simulate(0);
        } catch (Exception e) {
            fail("simulate() con entradas inválidas no debe lanzar excepciones no controladas");
        }
    }
}