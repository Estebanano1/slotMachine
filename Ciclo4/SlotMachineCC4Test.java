import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineCC4Test {

    @Test
    public void sharedTestRebelWheelProtection() {
        SlotMachine sm = new SlotMachine(2);
        sm.addWheel("rebel", 0);
        sm.delWheel(0);
        assertFalse("Prueba compartida: RebelWheel debe protegerse contra eliminación", sm.ok());
    }

    @Test
    public void sharedTestLeftyWheelCopy() {
        SlotMachine sm = new SlotMachine(2);
        
        // 1. Reemplazar la rueda 1 por una LeftyWheel
        sm.addWheel("lefty", 1);
        
        // 2. Girar la rueda Lefty para que copie el estado actual de la rueda 0
        sm.spin(1, 1);
        
        // 3. Verificar que la rueda 1 tenga el mismo símbolo que la rueda 0
        String[] currentSymbols = sm.symbols();
        assertEquals("LeftyWheel debe tener exactamente el mismo símbolo que la rueda izquierda", 
                     currentSymbols[0], currentSymbols[1]);
    }
}