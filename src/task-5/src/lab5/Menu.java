package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Макрокоманда (шаблон Command);
 * Колекція об'єктів класу ConsoleCommand
 * @author Темний Ю.
 * @version 1.0
 */
public class Menu implements Command {
    /**
     * Конструктор за замовчуванням
     */
    public Menu() { // Порожній конструктор
    }
    /**
     * Колекція консольних команд
     */
    private List<ConsoleCommand> menu = new ArrayList<ConsoleCommand>();
    /**
     * Додає нову команду в колекцію
     * @param command реалізує ConsoleCommand
     * @return command
     */
    public ConsoleCommand add(ConsoleCommand command) {
        menu.add(command);
        return command;
    }
    @Override
    public String toString() {
        String s = "Введіть команду...\n";
        for (ConsoleCommand c : menu) {
            s += c + ", ";
        }	s += "'q'uit: ";
        return s;
    }
    @Override
    public void execute() {//обробка команд
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        menu: while (true) {
            do {
                System.out.print(this);
                try {
                    s = in.readLine();
                } catch (IOException e) {
                    System.err.println("Помилка: " + e);
                    System.exit(0);
                }
            } while (s.length() != 1);
            	char key = s.charAt(0);
            	if (key == 'q') {
                System.out.println("Вихід.");
                break menu;
            }
            for (ConsoleCommand c : menu) {
                if (s.charAt(0) == c.getKey()) {
                    c.execute();
                    
                    // Додаємо команду в історію після виконання (крім команди undo)
                    if (!(c instanceof UndoConsoleCommand)) {
                        Application.getInstance().addToHistory(c);
                    }
                    continue menu;
                }
            }
            System.out.println("Неправильна команда.");
            continue menu;
        }
    }
    @Override
    public void undo() {
        // Не реалізовано, бо меню є контейнером команд
        System.out.println("Скасування меню не підтримується.");
    }
}
