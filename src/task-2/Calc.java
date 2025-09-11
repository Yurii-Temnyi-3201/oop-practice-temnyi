import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Містить реалізацію методів для обчислення опору провідників та відображення результатів.
 * Використовує агрегування з класом ResistanceCalculationResult.
 * @author Темний Юрій
 * @version 1.0
 */
public class Calc {
    
    /** Ім'я файлу, що використовується при серіалізації. */
    private static final String FNAME = "ResistanceData.bin";
    
    /** Зберігає результат обчислень. Об'єкт класу {@linkplain ResistanceCalculationResult} */
    private ResistanceCalculationResult result;
    
    /**
     * Ініціалізує {@linkplain Calc#result}
     */
    public Calc() {
        result = new ResistanceCalculationResult();
    }
    
    /**
     * Встановити значення {@linkplain Calc#result}
     * @param result - нове значення посилання на об'єкт {@linkplain ResistanceCalculationResult}
     */
    public void setResult(ResistanceCalculationResult result) {
        this.result = result;
    }
    
    /**
     * Отримати значення {@linkplain Calc#result}
     * @return поточне значення посилання на об'єкт {@linkplain ResistanceCalculationResult}
     */
    public ResistanceCalculationResult getResult() {
        return result;
    }
    
    /**
     * Обчислює опір одного провідника за законом Ома.
     * @param voltage - напруга на провіднику (В)
     * @param current - сила струму (А)
     * @return опір провідника (Ом)
     */
    private double calculateSingleResistance(double voltage, double current) {
        if (current == 0) {
            throw new IllegalArgumentException("Струм не може дорівнювати нулю!");
        }
        return voltage / current;
    }
    /**
     * Обчислює загальний опір трьох послідовно з'єднаних провідників.
     * @param voltage1 - напруга на першому провіднику (В)
     * @param voltage2 - напруга на другому провіднику (В)
     * @param voltage3 - напруга на третьому провіднику (В)
     * @param current - сила струму (А)
     * @return загальний опір (Ом)
     */
    private double calculateTotalResistance(double voltage1, double voltage2, double voltage3, double current) {
        double r1 = calculateSingleResistance(voltage1, current);
        double r2 = calculateSingleResistance(voltage2, current);
        double r3 = calculateSingleResistance(voltage3, current);
        return r1 + r2 + r3; // При послідовному з'єднанні опори додаються
    }
    /**
     * Конвертує число у 8-річну систему числення.
     * @param value - число для конвертації
     * @return 8-річне представлення
     */
    private String convertToOctal(double value) {
        int intValue = (int) Math.round(value);
        return Integer.toOctalString(intValue);
    }
    /**
     * Конвертує число у 16-річну систему числення.
     * @param value - число для конвертації
     * @return 16-річне представлення
     */
    private String convertToHex(double value) {
        int intValue = (int) Math.round(value);
        return Integer.toHexString(intValue).toUpperCase();
    }
    
    /**
     * Обчислює загальний опір та зберігає результат в об'єкті {@linkplain Calc#result}
     * @param current - сила струму (А)
     * @param voltage1 - напруга на першому провіднику (В)
     * @param voltage2 - напруга на другому провіднику (В)
     * @param voltage3 - напруга на третьому провіднику (В)
     * @return загальний опір (Ом)
     */
    public double init(double current, double voltage1, double voltage2, double voltage3) {
        // Встановлюємо вхідні параметри
        result.setCurrent(current);
        result.setVoltage1(voltage1);
        result.setVoltage2(voltage2);
        result.setVoltage3(voltage3);
        // Обчислюємо загальний опір
        double totalResistance = calculateTotalResistance(voltage1, voltage2, voltage3, current);
        result.setTotalResistance(totalResistance); 
        // Конвертуємо у різні системи числення
        result.setOctalRepresentation(convertToOctal(totalResistance));
        result.setHexRepresentation(convertToHex(totalResistance));
        return totalResistance;
    }
    /**
     * Виводить результат обчислень.
     */
    public void show() {
        System.out.println(result);
    }
    /**
     * Зберігає {@linkplain Calc#result} у файлі {@linkplain Calc#FNAME}
     * @throws IOException якщо виникла помилка при записі у файл
     */
    public void save() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FNAME));
        os.writeObject(result);
        os.flush();
        os.close();
    }
    /**
     * Відновлює {@linkplain Calc#result} з файлу {@linkplain Calc#FNAME}
     * @throws Exception якщо виникла помилка при читанні з файлу
     */
    public void restore() throws Exception {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(FNAME));
        result = (ResistanceCalculationResult) is.readObject();
        is.close();
    }
}