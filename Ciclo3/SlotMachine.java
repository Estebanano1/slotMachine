import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private int numberOfWheels;
    private boolean lastOperationOk;
    private boolean isVisible;

    //Componentes de la maquina 
    private Rectangle body;      
    private Rectangle screen;    
    private Rectangle leverStick; 
    private Circle leverKnob; 
    
    private static final String[] DEFAULT_COLORS = {
        "red", "blue", "yellow", "green"
    };

    private static final String[] DEFAULT_SHAPES = {
        "circle", "rectangle", "triangle"
    };

    /**
     * Constructor por defecto (3 ruedas).
     */
    public SlotMachine() {
        this(3);
    }

    /**
     * Constructor ajustado para centrar el chasis y las ruedas en el Canvas.
     */
    public SlotMachine(int numberOfWheels) {
        if (numberOfWheels <= 0) {
            this.numberOfWheels = 0;
            this.wheels = new ArrayList<>();
            this.lastOperationOk = false;
            this.isVisible = false;
            return;
        }

        this.numberOfWheels = numberOfWheels;
        this.wheels = new ArrayList<>();
        this.lastOperationOk = true;
        this.isVisible = false;

        // 1. Primero se construye y ubica la estructura visual externa
        setupMachineFrame();

        // 2. Coordenadas fijas de las ruedas alineadas con el visor interior
        int startX = 70;
        int startY = 60;
        int spacing = 70;

        java.util.Random random = new java.util.Random();

        for (int i = 0; i < numberOfWheels; i++) {
            // Posición exacta de cada rueda dentro de la pantalla blanca
            Wheel wheel = new Wheel(startX + 110 + (i * 70), startY + 25);

            for (int j = 0; j < numberOfWheels; j++) {
                String color = DEFAULT_COLORS[j % DEFAULT_COLORS.length];
                String shape = DEFAULT_SHAPES[j % DEFAULT_SHAPES.length]; 
                
                wheel.addSymbol(new Symbol(color, shape));
            }

            int initialSteps = random.nextInt(numberOfWheels);
            wheel.spinSteps(initialSteps);

            this.wheels.add(wheel);
        }
    }

    /**
     * Construye el chasis y la palanca ajustados exactamente a la derecha.
     */
    private void setupMachineFrame() {
        int startX = 140;
        int startY = 80;
        int screenWidth = (numberOfWheels * 80) + 20;
        int screenHeight = 100;

        // 1. Carcasa exterior (negra)
        body = new Rectangle();
        body.changeSize(screenHeight + 40, screenWidth + 40);
        body.moveHorizontal(-60 + startX - 20);
        body.moveVertical(-50 + startY - 20);
        body.changeColor("black");

        // 2. Visor/Pantalla interior (blanca)
        screen = new Rectangle();
        screen.changeSize(screenHeight, screenWidth);
        screen.moveHorizontal(-60 + startX);
        screen.moveVertical(-50 + startY);
        screen.changeColor("white");

        // 3. Palo de la palanca (negro, acoplado al borde derecho)
        leverStick = new Rectangle();
        leverStick.changeSize(60, 10);
        leverStick.moveHorizontal(-60 + startX + screenWidth + 25);
        leverStick.moveVertical(-50 + startY + 20);
        leverStick.changeColor("black");

        // 4. Perilla roja (Ajustada para situarse justo encima de la barra negra)
        leverKnob = new Circle();
        leverKnob.changeSize(24);
        leverKnob.moveHorizontal(-20 + startX + screenWidth + 30); // Se mueve a la derecha de la barra
        leverKnob.moveVertical(-60 + startY + 10);                 // Se baja para alinearse con la punta
        leverKnob.changeColor("red");
    }

        /**
     * Posiciona las ruedas perfectamente centraditas dentro del marco blanco.
     */
    private void repositionWheels() {
        int startX = 140;
        int startY = 80;

        for (int i = 0; i < wheels.size(); i++) {
            // startX + 110 acomoda la primera rueda dentro del visor blanco
            // y 70 de espacio las distribuye uniformemente
            wheels.get(i).setPosition(startX + 110 + (i * 70), startY + 25);
            
            if (isVisible) {
                wheels.get(i).makeVisible();
            }
        }
    }

    public void makeVisible() {
        isVisible = true;

        if (body != null) body.makeVisible();
        if (screen != null) screen.makeVisible();
        if (leverStick != null) leverStick.makeVisible();
        if (leverKnob != null) leverKnob.makeVisible();

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

        if (leverKnob != null) leverKnob.makeInvisible();
        if (leverStick != null) leverStick.makeInvisible();
        if (screen != null) screen.makeInvisible();
        if (body != null) body.makeInvisible();

        lastOperationOk = true;
    }

    /**
     * Anima el movimiento de la palanca al girar.
     */
    private void animateLever() {
        if (isVisible && leverStick != null && leverKnob != null) {
            leverStick.moveVertical(20);
            leverKnob.moveVertical(20);
            try { Thread.sleep(120); } catch (Exception ignored) {}
            leverStick.moveVertical(-20);
            leverKnob.moveVertical(-20);
        }
    }

    public void spin(int wheel) {
        if (isValidIndex(wheel)) {
            animateLever();
            wheels.get(wheel).spin();
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void spin(int wheel, int steps) {
        if (isValidIndex(wheel)) {
            animateLever();
            wheels.get(wheel).spinSteps(steps);
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void spin(String[] setSymbols) {
        if (setSymbols != null && setSymbols.length == wheels.size()) {
            animateLever();
            for (int i = 0; i < setSymbols.length; i++) {
                wheels.get(i).setSymbolByColor(setSymbols[i]);
            }
            checkJackpot();
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }

    public void spin() {
        animateLever();
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        checkJackpot();
        lastOperationOk = true;
    }

    public void addWheel(int pos) {
            if (pos < 0 || pos > wheels.size()) {
                lastOperationOk = false;
                return;
            }
    
            if (body != null) body.makeInvisible();
            if (screen != null) screen.makeInvisible();
            if (leverStick != null) leverStick.makeInvisible();
            if (leverKnob != null) leverKnob.makeInvisible();
    
            Wheel newWheel = new Wheel(0, 0);
            wheels.add(pos, newWheel);
            numberOfWheels = wheels.size();
    
            setupMachineFrame();
            repositionWheels();
    
            if (isVisible) {
                makeVisible();
            }
    
            lastOperationOk = true;
        }

    public void delWheel(int pos) {
            if (!isValidIndex(pos)) {
                lastOperationOk = false;
                return;
            }
    
            if (body != null) body.makeInvisible();
            if (screen != null) screen.makeInvisible();
            if (leverStick != null) leverStick.makeInvisible();
            if (leverKnob != null) leverKnob.makeInvisible();
    
            wheels.get(pos).makeInvisible();
            wheels.remove(pos);
            numberOfWheels = wheels.size();
    
            setupMachineFrame();
            repositionWheels();
    
            if (isVisible) {
                makeVisible();
            }
    
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
                System.out.println("¡JACKPOT! ¡HAS GANADO!");
                JOptionPane.showMessageDialog(null, "¡JACKPOT! ¡TODOS LOS SÍMBOLOS COINCIDEN!", "¡Felicidades!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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


    public ArrayList<Wheel> getWheels() {
        return wheels;
    }
}