import java.io.Serializable;

/**
 * Зберігає параметри і результати обчислення опору провідників.
 * @author Темний Юрій
 * @version 1.0
 */
public class ResistanceCalculationResult implements Serializable {
    /** Автоматично згенерована константа для серіалізації */
    private static final long serialVersionUID = 1L;
    
    /** Сила струму (А) */
    private double current;
    
    /** Напруга на першому провіднику (В) */
    private double voltage1;
    
    /** Напруга на другому провіднику (В) */
    private double voltage2;
    
    /** Напруга на третьому провіднику (В) */
    private double voltage3;
    
    /** Загальний опір (Ом) */
    private double totalResistance;
    
    /** 8-річне представлення загального опору */
    private String octalRepresentation;
    
    /** 16-річне представлення загального опору */
    private String hexRepresentation;
    
    /** Transient поле для демонстрації особливостей серіалізації */
    private transient double temporaryValue;
    /**
     * Ініціалізує всі поля нульовими значеннями.
     */
    public ResistanceCalculationResult() {
        current = 0.0;
        voltage1 = 0.0;
        voltage2 = 0.0;
        voltage3 = 0.0;
        totalResistance = 0.0;
        octalRepresentation = "";
        hexRepresentation = "";
        temporaryValue = 0.0;
    }
    /**
     * Встановлює значення струму.
     * @param current - значення для поля current
     * @return Значення current
     */
    public double setCurrent(double current) {
        this.temporaryValue = current * 1000; // Для демонстрації transient
        return this.current = current;
    }
    /**
     * Отримання значення струму.
     * @return Значення current
     */
    public double getCurrent() {
        return current;
    }
    /**
     * Встановлює значення напруги на першому провіднику.
     * @param voltage1 - значення для поля voltage1
     * @return Значення voltage1
     */
    public double setVoltage1(double voltage1) {
        return this.voltage1 = voltage1;
    }

    /**
     * Отримання значення напруги на першому провіднику.
     * @return Значення voltage1
     */
    public double getVoltage1() {
        return voltage1;
    }
    /**
     * Встановлює значення напруги на другому провіднику.
     * @param voltage2 - значення для поля voltage2
     * @return Значення voltage2
     */
    public double setVoltage2(double voltage2) {
        return this.voltage2 = voltage2;
    }

    /**
     * Отримання значення напруги на другому провіднику.
     * @return Значення voltage2
     */
    public double getVoltage2() {
        return voltage2;
    }

    /**
     * Встановлює значення напруги на третьому провіднику.
     * @param voltage3 - значення для поля voltage3
     * @return Значення voltage3
     */
    public double setVoltage3(double voltage3) {
        return this.voltage3 = voltage3;
    }

    /**
     * Отримання значення напруги на третьому провіднику.
     * @return Значення voltage3
     */
    public double getVoltage3() {
        return voltage3;
    }

    /**
     * Встановлює значення загального опору.
     * @param totalResistance - значення для поля totalResistance
     * @return Значення totalResistance
     */
    public double setTotalResistance(double totalResistance) {
        return this.totalResistance = totalResistance;
    }

    /**
     * Отримання значення загального опору.
     * @return Значення totalResistance
     */
    public double getTotalResistance() {
        return totalResistance;
    }

    /**
     * Встановлює 8-річне представлення.
     * @param octalRepresentation - значення для поля octalRepresentation
     * @return Значення octalRepresentation
     */
    public String setOctalRepresentation(String octalRepresentation) {
        return this.octalRepresentation = octalRepresentation;
    }

    /**
     * Отримання 8-річного представлення.
     * @return Значення octalRepresentation
     */
    public String getOctalRepresentation() {
        return octalRepresentation;
    }

    /**
     * Встановлює 16-річне представлення.
     * @param hexRepresentation - значення для поля hexRepresentation
     * @return Значення hexRepresentation
     */
    public String setHexRepresentation(String hexRepresentation) {
        return this.hexRepresentation = hexRepresentation;
    }

    /**
     * Отримання 16-річного представлення.
     * @return Значення hexRepresentation
     */
    public String getHexRepresentation() {
        return hexRepresentation;
    }

    /**
     * Отримання transient поля.
     * @return Значення temporaryValue
     */
    public double getTemporaryValue() {
        return temporaryValue;
    }

    /**
     * Встановлює всі параметри одночасно.
     * @param current - сила струму
     * @param voltage1 - напруга на першому провіднику
     * @param voltage2 - напруга на другому провіднику
     * @param voltage3 - напруга на третьому провіднику
     * @return this
     */
    public ResistanceCalculationResult setParameters(double current, double voltage1, double voltage2, double voltage3) {
        this.current = current;
        this.voltage1 = voltage1;
        this.voltage2 = voltage2;
        this.voltage3 = voltage3;
        this.temporaryValue = current * 1000;
        return this;
    }

    /**
     * Представляє результат обчислень у вигляді рядка.
     * @return Рядкове представлення об'єкта
     */
    @Override
    public String toString() {
        return String.format(
            "Струм: %.1f А, Напруги: U1=%.1f В, U2=%.1f В, U3=%.1f В\n" +
            "Загальний опір: %.1f Ом\n" +
            "Вісімкова: %s, Шістнадцяткова: %s\n" +
            "Transient поле: %.1f",
            current, voltage1, voltage2, voltage3, 
            totalResistance, octalRepresentation, hexRepresentation, temporaryValue
        );
    }

    /**
     * Автоматично згенерований метод для порівняння об'єктів.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        ResistanceCalculationResult other = (ResistanceCalculationResult) obj;
        
        if (Math.abs(current - other.current) > 1e-10) return false;
        if (Math.abs(voltage1 - other.voltage1) > 1e-10) return false;
        if (Math.abs(voltage2 - other.voltage2) > 1e-10) return false;
        if (Math.abs(voltage3 - other.voltage3) > 1e-10) return false;
        if (Math.abs(totalResistance - other.totalResistance) > 1e-10) return false;
        
        return true;
    }
}