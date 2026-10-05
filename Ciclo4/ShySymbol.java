public class ShySymbol extends Symbol {
    private boolean isShyHidden;

    public ShySymbol(String color, String shape) {
        super(color, shape);
        this.isShyHidden = false;
    }

    @Override
    public void onSpin() {
        // Alterna entre visible e invisible cada vez que gira la rueda
        this.isShyHidden = !this.isShyHidden;
        if (this.isShyHidden) {
            makeInvisible();
        } else {
            makeVisible();
        }
    }

    @Override
    public void draw() {
        if (!isShyHidden && visible) {
            // Dibujar figura normalmente
        }
    }
}