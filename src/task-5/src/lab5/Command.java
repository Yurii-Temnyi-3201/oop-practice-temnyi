package lab5;
/**
 * Інтерфейс команди або задачі;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public interface Command {
    /**
     * Виконання команди; шаблон Command
     */
    void execute();
    /**
     * Скасування команди; шаблон Command
     */
    void undo();
}


