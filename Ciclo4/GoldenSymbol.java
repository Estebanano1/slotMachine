public class GoldenSymbol extends Symbol {

    public GoldenSymbol(String color, String shape) {
        super(color, shape);
        this.size = 35; // Un poco más grande para distinguirse visualmente
    }

    @Override
    public void onSpin() {
        // Efecto visual al ser seleccionado
        this.color = "yellow";
        if (visible) {
            draw();
        }
    }

    @Override
    public void draw() {
        // Dibuja el símbolo resaltado
    }
}