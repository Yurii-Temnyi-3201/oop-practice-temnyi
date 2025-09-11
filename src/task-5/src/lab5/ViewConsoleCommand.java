package lab5;

import lab3.View;

/**
 * Консольна команда View;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class ViewConsoleCommand implements ConsoleCommand {
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult
     */
    private View view;
    
    /**
     * Ініціалізує поле view
     * @param view об'єкт, що реалізує інтерфейс View
     */
    public ViewConsoleCommand(View view) {
        this.view = view;
    }
    
    @Override
    public char getKey() {
        return 'v';
    }
    
    @Override
    public String toString() {
        return "'v'iew";
    }
    
    @Override
    public void execute() {
        System.out.println("Перегляд поточних даних.");
        view.viewShow();
    }
    
    @Override
    public void undo() {
        // Команда перегляду не потребує скасування, бо не змінює стан
        System.out.println("Скасування перегляду не потрібне.");
    }
}