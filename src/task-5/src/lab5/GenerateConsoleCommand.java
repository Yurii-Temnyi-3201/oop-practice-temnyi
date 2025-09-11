package lab5;

import lab3.View;
import lab3.ViewResistanceResult;
import lab3.ResistanceResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Консольна команда Generate;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class GenerateConsoleCommand implements ConsoleCommand {
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult
     */
    private View view;
    
    /**
     * Збережений попередній стан елементів для можливості скасування
     */
    private List<ResistanceResult> previousState;
    
    /**
     * Ініціалізує поле view
     * @param view об'єкт, що реалізує інтерфейс View
     */
    public GenerateConsoleCommand(View view) {
        this.view = view;
    }
    
    @Override
    public char getKey() {
        return 'g';
    }
    
    @Override
    public String toString() {
        return "'g'enerate";
    }
    
    @Override
    public void execute() {
        // Зберігаємо поточний стан для можливості скасування
        savePreviousState();
        
        System.out.println("Випадкова генерація даних.");
        view.viewInit();
        view.viewShow();
    }
    
    /**
     * Зберігає поточний стан колекції для скасування
     */
    private void savePreviousState() {
        previousState = new ArrayList<>();
        for (ResistanceResult item : ((ViewResistanceResult)view).getItems()) {
            // Створюємо копії всіх елементів
            ResistanceResult copy = new ResistanceResult();
            copy.setValues(item.getCurrent(), item.getVoltage1(), item.getVoltage2(), item.getVoltage3());
            previousState.add(copy);
        }
    }
    
    @Override
    public void undo() {
        if (previousState == null) {
            System.out.println("Немає попереднього стану для відновлення.");
            return;
        }
        
        System.out.println("Скасування випадкової генерації даних.");
        List<ResistanceResult> currentItems = ((ViewResistanceResult)view).getItems();
        
        // Переконуємося, що розміри списків співпадають
        int minSize = Math.min(previousState.size(), currentItems.size());
        for (int i = 0; i < minSize; i++) {
            ResistanceResult prev = previousState.get(i);
            ResistanceResult curr = currentItems.get(i);
            curr.setValues(prev.getCurrent(), prev.getVoltage1(), prev.getVoltage2(), prev.getVoltage3());
        }
        
        view.viewShow();
    }
}