import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas de unidad para SlotMachineContest (Ciclo 3).
 */
public class SlotMachineContestTest {

    private SlotMachineContest contest;

    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    @Test
    public void shouldSolveContestWithValidN() {
        int movements = contest.solve(3);
        assertTrue("solve() debe marcar ok() true en ejecuciones válidas", contest.ok());
        assertTrue("El número de movimientos para resolver debe ser >= 0", movements >= 0);
    }

    @Test
    public void shouldNotSolveWithInvalidN() {
        int result = contest.solve(0);
        assertFalse("solve() debe retornar ok() false si n <= 0", contest.ok());
        assertEquals(-1, result);
    }

    @Test
    public void shouldSimulateContestWithValidN() {
        contest.simulate(3);
        assertTrue("simulate() debe marcar ok() true en ejecuciones válidas", contest.ok());
    }

    @Test
    public void shouldNotSimulateWithInvalidN() {
        contest.simulate(-1);
        assertFalse("simulate() debe retornar ok() false si n es negativo", contest.ok());
    }
}