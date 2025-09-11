package lab5;

/**
 * Консольна команда Undo;
 * скасовує останню виконану команду;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public class UndoConsoleCommand implements ConsoleCommand {
    /**
     * Посилання на екземпляр Application
     */
    private Application app;
    
    /**
     * Ініціалізує поле app
     * @param app екземпляр Application
     */
    public UndoConsoleCommand(Application app) {
        this.app = app;
    }
    
    @Override
    public char getKey() {
        return 'u';
    }
    
    @Override
    public String toString() {
        return "'u'ndo";
    }
    
    @Override
    public void execute() {
        System.out.println("Скасування останньої команди.");
        app.undoLastCommand();
    }
    
    @Override
    public void undo() {
        // Не має реалізації, бо це метакоманда
        System.out.println("Неможливо скасувати команду скасування.");
    }
}