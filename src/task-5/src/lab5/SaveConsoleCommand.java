package lab5;

import java.io.IOException;
import lab3.View;

/**
 * Консольна команда Save;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class SaveConsoleCommand implements ConsoleCommand {
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult
     */
    private View view;
    
    /**
     * Ініціалізує поле view
     * @param view об'єкт, що реалізує інтерфейс View
     */
    public SaveConsoleCommand(View view) {
        this.view = view;
    }
    
    @Override
    public char getKey() {
        return 's';
    }
    
    @Override
    public String toString() {
        return "'s'ave";
    }
    
    @Override
    public void execute() {
        System.out.println("Збереження поточних даних.");
        try {
            view.viewSave();
        } catch (IOException e) {
            System.err.println("Помилка серіалізації: " + e);
        }
        //view.viewShow();
    }
    
    @Override
    public void undo() {
        // Збереження даних не скасовується
        System.out.println("Скасування збереження неможливе.");
    }
}