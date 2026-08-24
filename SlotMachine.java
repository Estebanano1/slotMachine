import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Representa una maquina tragamonedas.
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private boolean finished;

    /**
     * Crea una maquina tragamonedas vacia.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = false;
        finished = false;
    }

    /**
     * Adiciona una rueda a la maquina.
     */
    public void addWheel()
    {
        if(finished)
        {
            showError("El simulador ya termino.");
            return;
        }

        int position = wheels.size();
        int x = 30 + (position * 70);
        int y = 100;

        Wheel wheel = new Wheel(x, y);
        wheels.add(wheel);

        if(visible)
        {
            wheel.makeVisible();
        }
    }

    /**
     * Elimina una rueda.
     *
     * @param position Posicion de la rueda.
     */
    public void removeWheel(int position)
    {
        if(finished)
        {
            showError("El simulador ya termino.");
            return;
        }

        if(position < 0 || position >= wheels.size())
        {
            showError("La rueda no existe.");
            return;
        }

        Wheel wheel = wheels.remove(position);
        wheel.makeInvisible();
        repositionWheels();
    }

    /**
     * Adiciona un simbolo a una rueda.
     *
     * @param wheelPosition Posicion de la rueda.
     * @param symbol Simbolo que se adiciona.
     */
    public void addSymbol(int wheelPosition, Symbol symbol)
    {
        if(finished)
        {
            showError("El simulador ya termino.");
            return;
        }

        if(wheelPosition < 0 || wheelPosition >= wheels.size())
        {
            showError("La rueda no existe.");
            return;
        }

        if(symbol == null)
        {
            showError("El simbolo no puede ser null.");
            return;
        }

        wheels.get(wheelPosition).addSymbol(symbol);
    }

    /**
     * Elimina un simbolo de una rueda.
     *
     * @param wheelPosition Posicion de la rueda.
     * @param symbolPosition Posicion del simbolo.
     */
    public void removeSymbol(int wheelPosition, int symbolPosition)
    {
        if(finished)
        {
            showError("El simulador ya termino.");
            return;
        }

        if(wheelPosition < 0 || wheelPosition >= wheels.size())
        {
            showError("La rueda no existe.");
            return;
        }

        Wheel wheel = wheels.get(wheelPosition);

        if(symbolPosition < 0 || symbolPosition >= wheel.size())
        {
            showError("El simbolo no existe.");
            return;
        }

        wheel.removeSymbol(symbolPosition);
    }

    /**
     * Gira todas las ruedas.
     */
    public void spin()
    {
        if(finished)
        {
            showError("El simulador ya termino.");
            return;
        }

        if(wheels.isEmpty())
        {
            showError("No hay ruedas en la maquina.");
            return;
        }

        clearJackpotAppearance();

        for(Wheel wheel : wheels)
        {
            wheel.spin();
        }

        isJackpot();
    }

    /**
     * Retorna los simbolos seleccionados actualmente.
     *
     * @return lista de simbolos seleccionados.
     */
    public ArrayList<Symbol> getSymbols()
    {
        ArrayList<Symbol> result = new ArrayList<Symbol>();

        for(Wheel wheel : wheels)
        {
            Symbol symbol = wheel.getCurrentSymbol();

            if(symbol != null)
            {
                result.add(symbol);
            }
        }

        if(visible)
        {
            showSymbols(result);
        }

        return result;
    }

    /**
     * Muestra los simbolos seleccionados actualmente.
     *
     * @param symbols simbolos seleccionados.
     */
    private void showSymbols(ArrayList<Symbol> symbols)
    {
        if(symbols.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No hay simbolos seleccionados.");
            return;
        }

        String message = "Simbolos actuales:\n";

        for(int i = 0; i < symbols.size(); i++)
        {
            message += "Rueda " + i + ": " + symbols.get(i) + "\n";
        }

        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Consulta si la configuracion actual es ganadora.
     * Una configuracion es ganadora cuando todas las ruedas tienen
     * un simbolo seleccionado y todos tienen el mismo color.
     *
     * @return true si es ganadora.
     */
    public boolean isJackpot()
    {
        if(wheels.isEmpty())
        {
            return false;
        }

        Symbol first = wheels.get(0).getCurrentSymbol();

        if(first == null)
        {
            return false;
        }

        for(Wheel wheel : wheels)
        {
            Symbol current = wheel.getCurrentSymbol();

            if(current == null || !current.equals(first))
            {
                return false;
            }
        }

        if(visible)
        {
            showJackpot();
        }

        return true;
    }

    /**
     * Hace visible o invisible la maquina.
     *
     * @param visible true para hacer visible.
     */
    public void setVisible(boolean visible)
    {
        if(finished)
        {
            return;
        }

        this.visible = visible;

        for(Wheel wheel : wheels)
        {
            if(visible)
            {
                wheel.makeVisible();
            }
            else
            {
                wheel.makeInvisible();
            }
        }
    }

    /**
     * Consulta si la maquina esta visible.
     *
     * @return true si es visible.
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Termina el simulador.
     */
    public void exit()
    {
        setVisible(false);
        finished = true;
    }

    /**
     * Consulta si el simulador termino.
     *
     * @return true si termino.
     */
    public boolean isFinished()
    {
        return finished;
    }

    /**
     * Reubica las ruedas despues de eliminar una.
     */
    private void repositionWheels()
    {
        for(int i = 0; i < wheels.size(); i++)
        {
            wheels.get(i).setPosition(30 + (i * 70), 100);
        }
    }

    /**
     * Quita cualquier apariencia especial de jackpot.
     */
    private void clearJackpotAppearance()
    {
        for(Wheel wheel : wheels)
        {
            wheel.clearJackpotAppearance();
        }
    }

    /**
     * Muestra el resultado de un jackpot.
     * Solo muestra ventanas cuando la maquina esta visible.
     */
    private void showJackpot()
    {
        if(!visible)
        {
            return;
        }

        for(Wheel wheel : wheels)
        {
            wheel.highlightJackpot();
        }

        JOptionPane.showMessageDialog(
            null,
            "¡JACKPOT!"
        );
    }

    /**
     * Muestra un error solamente cuando la maquina esta visible.
     *
     * @param message Mensaje que se mostrara.
     */
    private void showError(String message)
    {
        if(visible)
        {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
