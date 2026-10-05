import java.util.ArrayList;

/**
 * Representa una rueda de la maquina tragamonedas.
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int selectedSymbol;
    private int xPosition;
    private int yPosition;
    private boolean visible;

    /**
     * Crea una rueda en una posicion determinada.
     *
     * @param x Posicion horizontal.
     * @param y Posicion vertical.
     */
    public Wheel(int x, int y)
    {
        symbols = new ArrayList<Symbol>();
        selectedSymbol = 0;
        xPosition = x;
        yPosition = y;
        visible = false;
    }

    /**
     * Adiciona un simbolo a la rueda.
     *
     * @param symbol Simbolo que se quiere adicionar.
     */
    public void addSymbol(Symbol symbol)
    {
        if(symbol == null)
        {
            return;
        }

        symbol.moveTo(xPosition, yPosition);
        symbols.add(symbol);

        if(visible)
        {
            hideSymbols();
            symbols.get(selectedSymbol).makeVisible();
        }
        else
        {
            symbol.makeInvisible();
        }
    }

    /**
     * Elimina un simbolo.
     *
     * @param position Posicion del simbolo.
     */
    public void removeSymbol(int position)
    {
        if(position < 0 || position >= symbols.size())
        {
            return;
        }

        Symbol symbol = symbols.get(position);
        symbol.makeInvisible();
        symbol.setJackpotAppearance(false);
        symbols.remove(position);

        if(symbols.isEmpty())
        {
            selectedSymbol = 0;
        }
        else if(position < selectedSymbol)
        {
            selectedSymbol--;
        }
        else if(selectedSymbol >= symbols.size())
        {
            selectedSymbol = 0;
        }

        if(visible && !symbols.isEmpty())
        {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    /**
     * Gira la rueda.
     */
    public void spin()
    {
        if(symbols.isEmpty())
        {
            return;
        }

        clearJackpotAppearance();
        hideSymbols();

        selectedSymbol = (int)(Math.random() * symbols.size());

        if(visible)
        {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    /**
     * Hace visible la rueda.
     */
    public void makeVisible()
    {
        visible = true;
        hideSymbols();

        if(!symbols.isEmpty())
        {
            symbols.get(selectedSymbol).makeVisible();
        }
    }

    /**
     * Hace invisible la rueda.
     */
    public void makeInvisible()
    {
        visible = false;
        hideSymbols();
    }

    /**
     * Oculta todos los simbolos de la rueda.
     */
    private void hideSymbols()
    {
        for(Symbol symbol : symbols)
        {
            symbol.makeInvisible();
        }
    }

    /**
     * Resalta la rueda cuando hay jackpot.
     */
    public void highlightJackpot()
    {
        if(!symbols.isEmpty())
        {
            symbols.get(selectedSymbol).setJackpotAppearance(true);
        }
    }

    /**
     * Quita el resaltado de jackpot.
     */
    public void clearJackpotAppearance()
    {
        for(Symbol symbol : symbols)
        {
            symbol.setJackpotAppearance(false);
        }
    }

    /**
     * Cambia la posicion de la rueda y recoloca sus simbolos.
     *
     * @param x Nueva posicion horizontal.
     * @param y Nueva posicion vertical.
     */
    public void setPosition(int x, int y)
    {
        xPosition = x;
        yPosition = y;

        for(Symbol symbol : symbols)
        {
            symbol.moveTo(x, y);
        }
    }

    /**
     * Retorna el simbolo seleccionado.
     *
     * @return simbolo seleccionado o null si no hay simbolos.
     */
    public Symbol getCurrentSymbol()
    {
        if(symbols.isEmpty())
        {
            return null;
        }

        return symbols.get(selectedSymbol);
    }

    /**
     * Retorna la lista de simbolos.
     *
     * @return lista de simbolos.
     */
    public ArrayList<Symbol> getSymbols()
    {
        return new ArrayList<Symbol>(symbols);
    }

    /**
     * Retorna la cantidad de simbolos.
     *
     * @return cantidad de simbolos.
     */
    public int size()
    {
        return symbols.size();
    }
}
