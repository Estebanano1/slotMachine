import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

public class SlotMachine {
    private ArrayList<Wheel> wheels;
    private int numberOfWheels;
    private boolean lastOperationOk;
    private boolean isVisible;

    // Componentes gráficos de la máquina
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
    
        // 1. Construir el marco/pantalla
        setupMachineFrame();
    
        // 2. Crear las ruedas
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < numberOfWheels; i++) {
            Wheel wheel = new Wheel(0, 0);
    
            for (int j = 0; j < numberOfWheels; j++) {
                String color = DEFAULT_COLORS[j % DEFAULT_COLORS.length];
                String shape = DEFAULT_SHAPES[j % DEFAULT_SHAPES.length];
                wheel.addSymbol(new NormalSymbol(color, shape));
            }
    
            int initialSteps = random.nextInt(numberOfWheels);
            wheel.spinSteps(initialSteps);
    
            this.wheels.add(wheel);
        }
    
        // 3. Ubicar todas las ruedas centradamente
        repositionWheels();
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

        // 4. Perilla roja
        leverKnob = new Circle();
        leverKnob.changeSize(24);
        leverKnob.moveHorizontal(-20 + startX + screenWidth + 30);
        leverKnob.moveVertical(-60 + startY + 10);
        leverKnob.changeColor("red");
    }

    /**
     * Posiciona las ruedas perfectamente dentro del marco blanco.
     */
    private void repositionWheels() {
        int startX = 140; 
        int startY = 80;  
        int screenWidth = (numberOfWheels * 80) + 20;
        int screenHeight = 100;
    
        // Calculamos el espacio libre para distribuir uniformemente las ruedas
        int availableSpace = screenWidth - 40; // Margen interno
        int spacing = (numberOfWheels > 1) ? availableSpace / numberOfWheels : 0;
    
        for (int i = 0; i < wheels.size(); i++) {
            // - X: Distribuye dinámicamente y centra las ruedas según la cantidad n
            // - Y: Disminuimos a startY + 15 para subir las figuras al centro vertical del visor
            int posX = startX + 35 + (i * 70); 
            int posY = startY + 15; 
    
            wheels.get(i).setPosition(posX, posY);
    
            if (isVisible) {
                wheels.get(i).makeVisible();
            }
        }
    }

    public void makeVisible() {
        isVisible = true;
    
        // 1. Dibujar chasis y pantalla
        if (body != null) body.makeVisible();
        if (screen != null) screen.makeVisible();
        if (leverStick != null) leverStick.makeVisible();
        if (leverKnob != null) leverKnob.makeVisible();
    
        // 2. Reposicionar las ruedas exactamente en las coordenadas de la pantalla
        repositionWheels();
    
        // 3. Hacer visibles las ruedas ya ubicadas
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

    private void animateLever() {
        if (isVisible && leverStick != null && leverKnob != null) {
            leverStick.moveVertical(20);
            leverKnob.moveVertical(20);
            try { Thread.sleep(120); } catch (Exception ignored) {}
            leverStick.moveVertical(-20);
            leverKnob.moveVertical(-20);
        }
    }

    // --- MÉTODOS DE GIRO (SPIN) ---

    public void spin(int wheel) {
        spin(wheel, 1);
    }

    public void spin(int wheel, int steps) {
        if (isValidIndex(wheel)) {
            animateLever();
            Wheel w = wheels.get(wheel);
    
            if (w instanceof LeftyWheel) {
                Wheel leftWheel = (wheel > 0) ? wheels.get(wheel - 1) : null;
                ((LeftyWheel) w).spinStepsLefty(steps, leftWheel);
            } else {
                w.spinSteps(steps);
            }
    
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
        for (int i = 0; i < wheels.size(); i++) {
            Wheel w = wheels.get(i);
            if (w instanceof LeftyWheel) {
                Wheel leftWheel = (i > 0) ? wheels.get(i - 1) : null;
                ((LeftyWheel) w).spinStepsLefty(1, leftWheel);
            } else {
                w.spin();
            }
        }
        checkJackpot();
        lastOperationOk = true;
    }

    // --- MÉTODOS DE RUEDAS (WHEELS) ---

    public void addWheel(int pos) {
        addWheel("normal", pos);
    }

    /**
     * Sobrecarga Requisito 16 y 17: Agrega una rueda según su tipo.
     */
    public void addWheel(String type, int pos) {
        if (pos < 0 || pos > wheels.size() || type == null) {
            lastOperationOk = false;
            return;
        }

        if (body != null) body.makeInvisible();
        if (screen != null) screen.makeInvisible();
        if (leverStick != null) leverStick.makeInvisible();
        if (leverKnob != null) leverKnob.makeInvisible();

        Wheel newWheel;
        switch (type.toLowerCase()) {
            case "rebel":
                newWheel = new RebelWheel(0, 0);
                break;
            case "lefty":
                newWheel = new LeftyWheel(0, 0);
                break;
            case "speedy":
                newWheel = new SpeedyWheel(0, 0);
                break;
            default:
                newWheel = new Wheel(0, 0);
                break;
        }

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

        // Requisito 17: RebelWheel no se deja eliminar
        if (wheels.get(pos).isRebel()) {
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
            // Requisito 17: RebelWheel no se deja intercambiar
            if (wheels.get(wheel1).isRebel() || wheels.get(wheel2).isRebel()) {
                lastOperationOk = false;
                return;
            }

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
            // lock() devuelve false en RebelWheel si no se deja bloquear
            boolean locked = wheels.get(wheel).lock();
            lastOperationOk = locked;
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

    // --- MÉTODOS DE SÍMBOLOS (SYMBOLS) ---

    public void addSymbol(int pos, String color) {
        addSymbol("normal", pos, color);
    }

    /**
     * Sobrecarga Requisito 16 y 18: Agrega un símbolo según su tipo.
     */
    public void addSymbol(String type, int pos, String color) {
        if (!isValidIndex(pos) || type == null || color == null) {
            lastOperationOk = false;
            return;
        }

        Symbol newSymbol;
        switch (type.toLowerCase()) {
            case "ephemeral":
                newSymbol = new EphemeralSymbol(color, "circle");
                break;
            case "shy":
                newSymbol = new ShySymbol(color, "circle");
                break;
            case "golden":
                newSymbol = new GoldenSymbol(color, "circle");
                break;
            default:
                newSymbol = new NormalSymbol(color, "circle");
                break;
        }

        wheels.get(pos).addSymbol(newSymbol);
        lastOperationOk = true;
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

    // --- CONSULTAS E INFORMACIÓN ---

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

    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            Symbol s = wheels.get(i).getCurrentSymbol();
            config[i] = (s != null) ? s.getColor() : "";
        }
        lastOperationOk = true;
        return config;
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