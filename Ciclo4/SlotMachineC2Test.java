import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Casos de prueba de unidad para SlotMachine (Ciclo 2).
 * Valida cambios de estado reales y evita excepciones en tiempo de ejecución.
 */
public class SlotMachineC2Test {

    private SlotMachine machine;

    @Before
    public void setUp() {
        // Inicializamos una máquina por defecto antes de cada prueba
        machine = new SlotMachine(3);
    }

    // ==========================================
    // 1. PRUEBAS DE LOCK Y UNLOCK
    // ==========================================
    @Test
    public void shouldNotSpinLockedWheelButShouldSpinWhenUnlocked() {
        String initialSymbolWheel0 = machine.symbols()[0];

        machine.lock(0);
        assertTrue("El método lock(0) debe retornar ok() true", machine.ok());

        machine.spin();

        assertEquals("La rueda bloqueada debe mantener su símbolo original tras spin()",
                     initialSymbolWheel0, machine.symbols()[0]);

        machine.unlock(0);
        assertTrue("El método unlock(0) debe retornar ok() true", machine.ok());

        machine.spin(0, 1);

        assertTrue("La operación de giro sobre rueda desbloqueada debe ser exitosa", machine.ok());
    }

    // ==========================================
    // 2. PRUEBAS DE SPIN Y JACKPOT
    // ==========================================
    @Test
    public void shouldSetSpecificConfigurationAndCheckJackpotCorrectly() {
        String[] targetConfig = new String[]{"red", "blue", "yellow"};

        machine.spin(targetConfig);
        assertTrue("El giro con configuración personalizada debe retornar ok() true", machine.ok());

        assertArrayEquals("Los símbolos de la máquina deben coincidir con la configuración asignada",
                         targetConfig, machine.symbols());

        assertFalse("No debe ser jackpot cuando los símbolos son distintos", machine.isJackpot());
    }

    @Test
    public void shouldDetectJackpotWhenAllSymbolsAreEquals() {
        String[] jackpotConfig = new String[]{"red", "red", "red"};

        machine.spin(jackpotConfig);
        assertTrue(machine.ok());

        assertArrayEquals(jackpotConfig, machine.symbols());
        assertTrue("Debe retornar true en isJackpot() cuando todos los símbolos son iguales", machine.isJackpot());
    }

    // ==========================================
    // 3. PRUEBAS DE SWAP
    // ==========================================
    @Test
    public void shouldSwapTwoWheelsWithoutAffectingOthers() {
        String[] initialConfig = new String[]{"red", "blue", "yellow"};
        machine.spin(initialConfig);

        String symbol0Before = machine.symbols()[0]; // "red"
        String symbol1Before = machine.symbols()[1]; // "blue"
        String symbol2Before = machine.symbols()[2]; // "yellow"

        machine.swap(0, 1);
        assertTrue("El método swap(0, 1) debe retornar ok() true", machine.ok());

        String[] currentSymbols = machine.symbols();

        assertEquals("La posición 0 debe contener el símbolo anterior de la posición 1",
                     symbol1Before, currentSymbols[0]);

        assertEquals("La posición 1 debe contener el símbolo anterior de la posición 0",
                     symbol0Before, currentSymbols[1]);

        assertEquals("Las ruedas no involucradas en el swap no deben sufrir cambios",
                     symbol2Before, currentSymbols[2]);
    }

    // ==========================================
    // 4. PRUEBAS DE GESTIÓN DE RUEDAS (ADD / DEL)
    // ==========================================
    @Test
    public void shouldAddWheelAndIncreaseTotalWheels() {
        int initialCount = machine.symbols().length;

        machine.addWheel(0);
        assertTrue("addWheel(0) debe retornar ok() true", machine.ok());

        assertEquals("La cantidad de ruedas debe incrementarse en 1",
                     initialCount + 1, machine.symbols().length);
    }

    @Test
    public void shouldDeleteWheelAndDecreaseTotalWheels() {
        int initialCount = machine.symbols().length;

        machine.delWheel(0);
        assertTrue("delWheel(0) debe retornar ok() true", machine.ok());

        assertEquals("La cantidad de ruedas debe reducirse en 1",
                     initialCount - 1, machine.symbols().length);
    }

    // ==========================================
    // 5. PRUEBAS DE GESTIÓN DE SÍMBOLOS
    // ==========================================
    @Test
    public void shouldAddSymbolToSpecificWheel() {
        machine.addSymbol(0, "purple");
        assertTrue("addSymbol(0, 'purple') debe retornar ok() true", machine.ok());
    }

    @Test
    public void shouldDeleteSymbolFromAllWheels() {
        machine.delSymbol("red");
        assertTrue("delSymbol('red') debe retornar ok() true", machine.ok());
    }

    // ==========================================
    // 6. PRUEBAS DE MANEJO DE ERRORES E ÍNDICES
    // ==========================================
    @Test
    public void shouldNotPerformOperationsWithInvalidWheelIndex() {
        machine.lock(99);
        assertFalse("lock() debe establecer ok() en false si el índice es inválido", machine.ok());

        machine.swap(-1, 0);
        assertFalse("swap() debe establecer ok() en false si un índice es negativo", machine.ok());

        machine.addWheel(-1);
        assertFalse("addWheel() debe establecer ok() en false para posición negativa", machine.ok());

        machine.delWheel(99);
        assertFalse("delWheel() debe establecer ok() en false para índice fuera de rango", machine.ok());
    }
}