import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private int numberOfWheels;
    private boolean lastOperationOk;
    private boolean isVisible;

    /**
     * Constructor por defecto.
     * Ensambla automáticamente la máquina con 3 ruedas y sus símbolos.
     */
    public SlotMachine() {
        this.numberOfWheels = 3;
        this.wheels = new ArrayList<>();
        this.lastOperationOk = true;
        this.isVisible = false;
        setupDefaultMachine();
    }

    /**
     * Constructor opcional según cantidad de ruedas.
     */
    public SlotMachine(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        this.wheels = new ArrayList<>();
        this.lastOperationOk = true;
        this.isVisible = false;
        setupDefaultMachine();
    }

    private void setupDefaultMachine() {
        int xPosition = 30;
        int yPosition = 50;
        int spacing = 80;

        for (int i = 0; i < numberOfWheels; i++) {
            Wheel wheel = new Wheel(xPosition + (i * spacing), yPosition);

            wheel.addSymbol(new Symbol("red", "circle"));
            wheel.addSymbol(new Symbol("blue", "rectangle"));
            wheel.addSymbol(new Symbol("yellow", "triangle"));

            this.wheels.add(wheel);
        }
    }

    // --- REQUISITO: MANAGE WHEELS ---

    public void addWheel(int pos) {
        if (pos < 0 || pos > wheels.size()) {
            lastOperationOk = false;
            return;
        }
        int xPos = 30 + (pos * 80);
        Wheel newWheel = new Wheel(xPos, 50);
        wheels.add(pos, newWheel);
        repositionWheels();
        lastOperationOk = true;
    }

    public void delWheel(int pos) {
        if (!isValidIndex(pos)) {
            lastOperationOk = false;
            return;
        }
        wheels.get(pos).makeInvisible();
        wheels.remove(pos);
        repositionWheels();
        lastOperationOk = true;
    }

    public void swap(int wheel1, int wheel2) {
        if (isValidIndex(wheel1) && isValidIndex(wheel2)) {
            Wheel temp = wheels.get(wheel1);
            wheels.set(wheel1, wheels.get(wheel2));
            wheels.set(wheel2, temp);
            repositionWheels();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void lock(int wheel) {
        if (isValidIndex(wheel)) {
            wheels.get(wheel).lock();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void unlock(int wheel) {
        if (isValidIndex(wheel)) {
            wheels.get(wheel).unlock();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    // --- REQUISITO: MANAGE SYMBOLS ---

    public void addSymbol(int pos, String color) {
        if (isValidIndex(pos)) {
            wheels.get(pos).addSymbol(new Symbol(color, "circle"));
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void delSymbol(String symbol) {
        for (Wheel wheel : wheels) {
            ArrayList<Symbol> symbolsList = wheel.getSymbols();
            for (int i = 0; i < symbolsList.size(); i++) {
                if (symbolsList.get(i).getColor().equalsIgnoreCase(symbol)) {
                    wheel.removeSymbol(i);
                }
            }
        }
        lastOperationOk = true;
    }

    public void placeSymbol(int wheel, String symbol) {
        if (isValidIndex(wheel)) {
            wheels.get(wheel).setSymbolByColor(symbol);
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    // --- REQUISITO: SPIN WHEELS ---

    /**
     * Gira una única rueda aleatoriamente.
     */
    public void spin(int wheel) {
        if (isValidIndex(wheel)) {
            wheels.get(wheel).spin();
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    /**
     * Gira una rueda de forma animada paso a paso.
     */
    public void spin(int wheel, int steps) {
        if (isValidIndex(wheel)) {
            wheels.get(wheel).spinSteps(steps);
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    /**
     * Establece una configuración de símbolos directamente.
     */
    public void spin(String[] setSymbols) {
        if (setSymbols != null && setSymbols.length == wheels.size()) {
            for (int i = 0; i < setSymbols.length; i++) {
                wheels.get(i).setSymbolByColor(setSymbols[i]);
            }
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    /**
     * Gira todas las ruedas no bloqueadas.
     */
    public void spin() {
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        checkJackpot();
        lastOperationOk = true;
    }

    // --- REQUISITO: CONSULT SYMBOLS ---

    public String[] symbols() {
        String[] currentSymbols = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            Symbol s = wheels.get(i).getCurrentSymbol();
            currentSymbols[i] = (s != null) ? s.getColor() : null;
        }
        lastOperationOk = true;
        return currentSymbols;
    }

    public int distinctSymbols() {
        Set<String> distinct = new HashSet<>();
        for (Wheel wheel : wheels) {
            Symbol s = wheel.getCurrentSymbol();
            if (s != null) {
                distinct.add(s.getColor());
            }
        }
        lastOperationOk = true;
        return distinct.size();
    }

    public String configuration() {
        StringBuilder sb = new StringBuilder();
        for (Wheel wheel : wheels) {
            Symbol s = wheel.getCurrentSymbol();
            if (s != null) {
                sb.append(s.getColor()).append(" ");
            }
        }
        lastOperationOk = true;
        return sb.toString().trim();
    }

    // --- REQUISITO: CHECK JACKPOT ---

    public boolean isJackpot() {
        if (wheels.isEmpty()) return false;
        String firstColor = null;
        for (Wheel wheel : wheels) {
            Symbol s = wheel.getCurrentSymbol();
            if (s == null) return false;
            if (firstColor == null) {
                firstColor = s.getColor();
            } else if (!firstColor.equalsIgnoreCase(s.getColor())) {
                return false;
            }
        }
        return true;
    }

    private void checkJackpot() {
        if (isJackpot()) {
            for (Wheel wheel : wheels) {
                wheel.highlightJackpot();
            }
            if (isVisible) {
                System.out.println("¡JACKPOT! ¡HAS GANADO! ");
                JOptionPane.showMessageDialog(null, "¡JACKPOT! ¡TODOS LOS SÍMBOLOS COINCIDEN!", "¡Felicidades!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // --- VISIBILIDAD Y CONTROL ---

    public void makeVisible() {
        isVisible = true;
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
        lastOperationOk = true;
    }

    public void makeInvisible() {
        isVisible = false;
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
        lastOperationOk = true;
    }

    public void exit() {
        makeInvisible();
        System.exit(0);
    }

    public boolean ok() {
        return lastOperationOk;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < wheels.size();
    }

    private void repositionWheels() {
        int xPos = 30;
        int spacing = 80;
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPosition(xPos + (i * spacing), 50);
            if (isVisible) {
                wheels.get(i).makeVisible();
            }
        }
    }

    public ArrayList<Wheel> getWheels() {
        return wheels;
    }
}