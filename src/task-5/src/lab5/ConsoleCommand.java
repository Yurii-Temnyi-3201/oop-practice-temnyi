package lab5;

/**
 * Інтерфейс консольної команди;
 * шаблон Command
 * @author Темний Ю.
 * @version 1.0
 */
public interface ConsoleCommand extends Command {
    /**
     * Гаряча клавіша команди;
     * шаблон Command
     * @return символ гарячої клавіші
     */
    char getKey();
}