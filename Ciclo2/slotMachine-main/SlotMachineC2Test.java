import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SlotMachineC2Test {
    private SlotMachine machine;

    @Before
    public void setUp() {
        // Inicializa la máquina de 3 ruedas por defecto en modo invisible
        machine = new SlotMachine();
    }

    @Test
    public void accordingAmOrShouldLockAndUnlockWheel() {
        machine.lock(0);
        assertTrue(machine.ok());
        
        machine.unlock(0);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingAmOrShouldSwapWheels() {
        machine.placeSymbol(0, "red");
        machine.placeSymbol(1, "blue");
        machine.swap(0, 1);
        
        assertTrue(machine.ok());
        assertEquals("blue", machine.symbols()[0]);
        assertEquals("red", machine.symbols()[1]);
    }

    @Test
    public void accordingAmOrShouldSpinGivenConfiguration() {
        // Se envía la configuración para las 3 ruedas de la máquina
        String[] config = {"red", "red", "red"};
        machine.spin(config);
        
        assertTrue(machine.ok());
        assertTrue(machine.isJackpot());
    }
}