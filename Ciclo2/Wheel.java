import java.util.ArrayList;

/**
 * Representa una rueda de la maquina tragamonedas.
 */
public class Wheel {
    private ArrayList<Symbol> symbols;
    private int selectedSymbol;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean isLocked;

    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        selectedSymbol = 0;
        xPosition = x;
        yPosition = y;
        visible = false;
        isLocked = false;
    }

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void addSymbol(Symbol symbol) {
        if(symbol == null) return;
        symbol.moveTo(xPosition, yPosition);
        symbols.add(symbol);

        if(visible) {
            hideSymbols();
            symbols.get(selectedSymbol).makeVisible();
        } else {
            symbol.makeInvisible();
        }
    }

    public void removeSymbol(int position) {
        if(position < 0 || position >= symbols.size()) return;
        Symbol symbol = symbols.get(position);
        symbol.makeInvisible();
        symbol.setJackpotAppearance(false);
        symbols.remove(position);

        if(symbols.isEmpty()) {
            selectedSymbol = 0;
        } else if(position < selectedSymbol) {
            selectedSymbol--;
        } else if(selectedSymbol >= symbols.size()) {
            selectedSymbol = 0;
        }

        if(visible && !symbols.isEmpty()) {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    public void spin() {
        if(symbols.isEmpty() || isLocked) return;
        clearJackpotAppearance();
        hideSymbols();
        selectedSymbol = (int)(Math.random() * symbols.size());
        if(visible) {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    /**
     * Rota la rueda un número específico de pasos.
     * Si la máquina está visible, anima el paso a paso en el Canvas.
     */
/**
     * Rota la rueda un número específico de pasos.
     * Si la máquina está visible, anima el paso a paso en el Canvas.
     */
    public void spinSteps(int steps) {
        if (symbols.isEmpty() || isLocked) return;

        for (int i = 0; i < steps; i++) {
            hideSymbols();
            selectedSymbol = (selectedSymbol + 1) % symbols.size();

            if (visible) {
                symbols.get(selectedSymbol).makeVisible();

                // Pausa para dar tiempo a que se dibuje en pantalla
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void setSymbolByColor(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                hideSymbols();
                selectedSymbol = i;
                if (visible) {
                    symbols.get(selectedSymbol).makeVisible();
                }
                break;
            }
        }
    }

    public void makeVisible() {
        visible = true;
        hideSymbols();
        if(!symbols.isEmpty()) {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    public void makeInvisible() {
        visible = false;
        hideSymbols();
    }

    private void hideSymbols() {
        for(Symbol symbol : symbols) {
            symbol.makeInvisible();
        }
    }

    public void highlightJackpot() {
        if(!symbols.isEmpty()) {
            symbols.get(selectedSymbol).setJackpotAppearance(true);
        }
    }

    public void clearJackpotAppearance() {
        for(Symbol symbol : symbols) {
            symbol.setJackpotAppearance(false);
        }
    }

    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
        for(Symbol symbol : symbols) {
            symbol.moveTo(x, y);
        }
    }

    public Symbol getCurrentSymbol() {
        if(symbols.isEmpty()) return null;
        return symbols.get(selectedSymbol);
    }

    public ArrayList<Symbol> getSymbols() {
        return new ArrayList<Symbol>(symbols);
    }

    public int size() {
        return symbols.size();
    }
}