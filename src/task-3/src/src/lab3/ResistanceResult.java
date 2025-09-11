package lab3;

import java.io.Serializable;

/**
 * Клас для представлення результатів обчислення опорів. Зберігає струм та три напруги провідників і обчислює загальний опір послідовного з'єднання.
 * @author Темний Ю.
 * @version 1.0
 */
public class ResistanceResult implements Serializable {
    
    /**
     * Версія для серіалізації
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Струм через провідники (Амперів)
     */
    private double current;
    
    /**
     * Напруга на першому провіднику (Вольтів)
     */
    private double voltage1;
    
    /**
     * Напруга на другому провіднику (Вольтів)
     */
    private double voltage2;
    
    /**
     * Напруга на третьому провіднику (Вольтів)
     */
    private double voltage3;
    
    /**
     * 8-річне представлення загального опору
     */
    private transient String octalRepresentation;
    
    /**
     * 16-річне представлення загального опору
     */
    private transient String hexRepresentation;
    
    /**
     * Конструктор за замовчуванням - створює об'єкт з нульовими значеннями
     */
    public ResistanceResult() {
        this.current = 0.0;
        this.voltage1 = 0.0;
        this.voltage2 = 0.0;
        this.voltage3 = 0.0;
        this.octalRepresentation = "0";
        this.hexRepresentation = "0";
    }
    
    /**
     * Конструктор з параметрами
     * @param current струм через провідники
     * @param voltage1 напруга на першому провіднику
     * @param voltage2 напруга на другому провіднику  
     * @param voltage3 напруга на третьому провіднику
     */
    public ResistanceResult(double current, double voltage1, double voltage2, double voltage3) {
        setValues(current, voltage1, voltage2, voltage3);
    }
    
    /**
     * Встановлення значень струму та напруг
     * @param current струм через провідники
     * @param voltage1 напруга на першому провіднику
     * @param voltage2 напруга на другому провіднику
     * @param voltage3 напруга на третьому провіднику
     */
    public void setValues(double current, double voltage1, double voltage2, double voltage3) {
        this.current = current;
        this.voltage1 = voltage1;
        this.voltage2 = voltage2;
        this.voltage3 = voltage3;
        calculateRepresentations();
    }
    
    /**
     * Обчислення 8-річного та 16-річного представлення
     */
    private void calculateRepresentations() {
        int totalResistance = (int) getTotalResistance();
        this.octalRepresentation = Integer.toOctalString(totalResistance);
        this.hexRepresentation = Integer.toHexString(totalResistance).toUpperCase();
    }
    
    /**
     * Обчислення опору першого провідника за законом Ома
     * @return опір першого провідника (Ом)
     */
    public double getR1() {
        if (current == 0.0) {
            return 0.0;
        }
        return voltage1 / current;
    }
    
    /**
     * Обчислення опору другого провідника за законом Ома
     * @return опір другого провідника (Ом)
     */
    public double getR2() {
        if (current == 0.0) {
            return 0.0;
        }
        return voltage2 / current;
    }
    
    /**
     * Обчислення опору третього провідника за законом Ома
     * @return опір третього провідника (Ом)
     */
    public double getR3() {
        if (current == 0.0) {
            return 0.0;
        }
        return voltage3 / current;
    }
    
    /**
     * Обчислення загального опору послідовного з'єднання
     * @return загальний опір (Ом)
     */
    public double getTotalResistance() {
        return getR1() + getR2() + getR3();
    }
    
    /**
     * Отримання 8-річного представлення
     * @return 8-річне представлення опору
     */
    public String getOctalRepresentation() {
        if (octalRepresentation == null) {
            calculateRepresentations();
        }
        return octalRepresentation;
    }
    
    /**
     * Отримання 16-річного представлення
     * @return 16-річне представлення опору
     */
    public String getHexRepresentation() {
        if (hexRepresentation == null) {
            calculateRepresentations();
        }
        return hexRepresentation;
    }
    
    /**
     * Отримання струму
     * @return струм через провідники
     */
    public double getCurrent() {
        return current;
    }
    
    /**
     * Отримання напруги на першому провіднику
     * @return напруга на першому провіднику
     */
    public double getVoltage1() {
        return voltage1;
    }
    
    /**
     * Отримання напруги на другому провіднику
     * @return напруга на другому провіднику
     */
    public double getVoltage2() {
        return voltage2;
    }
    
    /**
     * Отримання напруги на третьому провіднику
     * @return напруга на третьому провіднику
     */
    public double getVoltage3() {
        return voltage3;
    }
    
    /**
     * Рядкове представлення об'єкта
     * @return рядок з інформацією про струм, напруги та опори
     */
    @Override
    public String toString() {
        return String.format("I=%.2fА | U1=%.2fВ | U2=%.2fВ | U3=%.2fВ | " +
                "R_total=%.2f Ом | Oct=%s | Hex=%s",
                current, voltage1, voltage2, voltage3, getTotalResistance(),
                getOctalRepresentation(), getHexRepresentation());
    }
    
    /**
     * Порівняння об'єктів на рівність
     * @param obj об'єкт для порівняння
     * @return true якщо об'єкти рівні
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ResistanceResult that = (ResistanceResult) obj;
        return Double.compare(that.current, current) == 0 &&
               Double.compare(that.voltage1, voltage1) == 0 &&
               Double.compare(that.voltage2, voltage2) == 0 &&
               Double.compare(that.voltage3, voltage3) == 0;
    }
    
    /**
     * Обчислення хеш-коду
     * @return хеш-код об'єкта
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(current, voltage1, voltage2, voltage3);
    }
}