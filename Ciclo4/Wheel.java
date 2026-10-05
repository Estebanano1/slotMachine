import java.util.ArrayList;

/**
 * Representa la clase base para una rueda de la maquina tragamonedas.
 * Preparada para extensiones polimórficas (RebelWheel, LeftyWheel, etc.).
 */
public class Wheel {
    protected ArrayList<Symbol> symbols;
    protected int selectedSymbol;
    protected int xPosition;
    protected int yPosition;
    protected boolean visible;
    protected boolean isLocked;

    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        selectedSymbol = 0;
        xPosition = x;
        yPosition = y;
        visible = false;
        isLocked = false;
    }

    public boolean lock() {
        this.isLocked = true;
        return true;
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
        
        Symbol current = getCurrentSymbol();
        if(current != null) {
            current.onSpin(); // Notificar al símbolo
        }

        if(visible && current != null) {
            current.makeVisible();
        }
    }

    /**
     * Rota la rueda un numero especifico de pasos.
     */
    public void spinSteps(int steps) {
        if (symbols.isEmpty() || isLocked) return;

        for (int i = 0; i < steps; i++) {
            hideSymbols();
            selectedSymbol = (selectedSymbol + 1) % symbols.size();

            Symbol current = symbols.get(selectedSymbol);
            current.onSpin(); // Notificar al símbolo en cada paso polimórficamente

            if (visible) {
                current.makeVisible();

                try {
                    Thread.sleep(300);
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
        hideSymbols(); // Ocasiona que todos estén invisibles primero
        Symbol current = getCurrentSymbol();
        if (current != null) {
            current.makeVisible(); // Muestra únicamente el símbolo activo en el visor
        }
    }

    public void makeInvisible() {
        visible = false;
        hideSymbols();
    }

    protected void hideSymbols() {
        for(Symbol symbol : symbols) {
            symbol.makeInvisible();
        }
    }

    public void highlightJackpot() {
        Symbol current = getCurrentSymbol();
        if (current != null) {
            current.setJackpotAppearance(true);
        }
    }
    
    public void clearJackpotAppearance() {
        for (Symbol symbol : symbols) {
            symbol.setJackpotAppearance(false);
        }
    }

    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        for (Symbol symbol : symbols) {
            symbol.moveTo(x, y); // Mueve los símbolos a las coordenadas de la pantalla
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

    public boolean isRebel() {
        return false;
    }
}