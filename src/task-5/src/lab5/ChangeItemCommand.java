package lab5;

import lab3.ResistanceResult;

/**
 * Команда Change item;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class ChangeItemCommand implements Command {
    
    /**
     * Конструктор за замовчуванням
     */
    public ChangeItemCommand() {
        // Порожній конструктор
    }
    
    /**
     * Оброблюваний об'єкт; шаблон Command
     */
    private ResistanceResult item;
    
    /**
     * Параметр команди; шаблон Command
     */
    private double offset;
    
    /**
     * Зберігає початковий стан для можливості скасування
     */
    private double originalCurrent;
    
    /**
     * Встановлює поле item
     * @param item значення для item
     * @return нове значення item
     */
    public ResistanceResult setItem(ResistanceResult item) {
        this.item = item;
        // Зберігаємо оригінальне значення для undo
        if (item != null) {
            this.originalCurrent = item.getCurrent();
        }
        return this.item;
    }
    
    /**
     * Повертає поле item
     * @return значення item
     */
    public ResistanceResult getItem() {
        return item;
    }
    
    /**
     * Встановлює поле offset
     * @param offset значення для offset
     * @return нове значення offset
     */
    public double setOffset(double offset) {
        return this.offset = offset;
    }
    
    /**
     * Повертає поле offset
     * @return значення offset
     */
    public double getOffset() {
        return offset;
    }
    
    @Override
    public void execute() {
        if (item == null) return;
        // Масштабуємо струм на коефіцієнт offset
        double newCurrent = item.getCurrent() * offset;
        item.setValues(newCurrent, item.getVoltage1(), item.getVoltage2(), item.getVoltage3());
    }
    
    @Override
    public void undo() {
        if (item == null) return;
        // Повертаємо оригінальне значення струму
        item.setValues(originalCurrent, item.getVoltage1(), item.getVoltage2(), item.getVoltage3());
    }
}