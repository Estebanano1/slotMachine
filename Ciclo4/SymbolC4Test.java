import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para verificar el comportamiento individual de las subclases de Symbol.
 */
public class SymbolC4Test {

    @Test
    public void testEphemeralSymbolReducesSizeOnSpin() {
        Symbol ephemeral = new EphemeralSymbol("red", "circle");
        int initialSize = ephemeral.getSize();

        // Al girar, debe reducir su tamaño
        ephemeral.onSpin();
        assertTrue("El tamaño del EphemeralSymbol debe ser menor tras girar", 
                   ephemeral.getSize() < initialSize);
    }

    @Test
    public void testShySymbolTogglesVisibilityOnSpin() {
        // Usamos "rectangle" que es una forma válida aceptada por la clase Symbol
        ShySymbol shy = new ShySymbol("blue", "rectangle");
        shy.makeVisible();
        assertTrue("Debe iniciar visible", shy.isVisible());

        // Primer giro: debe volverse invisible
        shy.onSpin();
        assertFalse("ShySymbol debe volverse invisible al primer giro", shy.isVisible());

        // Segundo giro: debe volver a ser visible
        shy.onSpin();
        assertTrue("ShySymbol debe volver a hacerse visible en el siguiente giro", shy.isVisible());
    }

    @Test
    public void testGoldenSymbolPointsOrBonus() {
        // Usamos "circle" que es una forma válida aceptada por la clase Symbol
        Symbol golden = new GoldenSymbol("yellow", "circle");
        
        // Verificar que mantenga sus propiedades base correctas
        assertEquals("El color del GoldenSymbol debe ser yellow", "yellow", golden.getColor());
        
        // Ejecutar onSpin para verificar que no lance excepciones
        golden.onSpin();
        assertTrue("GoldenSymbol debe ser válido tras girar", golden.getSize() > 0);
    }

    @Test
    public void testNormalSymbolBehavior() {
        Symbol normal = new NormalSymbol("green", "circle");
        int initialSize = normal.getSize();

        normal.onSpin();
        
        // NormalSymbol no debe alterar su tamaño al girar
        assertEquals("NormalSymbol debe mantener su tamaño original tras girar", 
                     initialSize, normal.getSize());
    }
}