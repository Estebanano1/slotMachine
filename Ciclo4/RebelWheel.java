/**
 * Rueda Rebelde: No se deja bloquear ni intercambiar ni eliminar.
 */
public class RebelWheel extends Wheel {

    public RebelWheel(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean lock() {
        // Rechaza el bloqueo
        this.isLocked = false;
        return false;
    }

    @Override
    public boolean isRebel() {
        return true;
    }
}