public class EphemeralSymbol extends Symbol {

    public EphemeralSymbol(String color, String shape) {
        super(color, shape);
    }

    @Override
    public void onSpin() {
        // En cada giro decrementa su tamaño hasta un mínimo de 4 px (un punto)
        if (this.size > 4) {
            this.size -= 6;
        } else {
            this.size = 4;
        }
        if (visible) {
            draw();
        }
    }

    @Override
    public void draw() {
        // Redibuja la figura utilizando el atributo de tamaño 'size' reducido
    }
}