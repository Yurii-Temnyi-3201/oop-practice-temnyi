package lab6;

import lab3.View;
import lab4.ViewableTable;
import lab5.Menu;
import lab5.ViewConsoleCommand;
import lab5.GenerateConsoleCommand;
import lab5.ChangeConsoleCommand;
import lab5.SaveConsoleCommand;
import lab5.RestoreConsoleCommand;

/**
 * Головний клас програми для паралельної обробки;
 * Виконання та відображення результатів, містить реалізацію статичного методу main().
 * @author Темний Юрій
 * @version 6.0
 */
public class Main {
    /** Об'єкт для роботи з колекцією через Factory Method */
    private View view = new ViewableTable().getView();
    /** Меню програми (макрокоманда) */
    private Menu menu = new Menu();
    /**
     * Конструктор за замовчуванням.
     * Ініціалізує головний клас програми.
     */
    public Main() {
        // Конструктор за замовчуванням
    }
    /**
     * Ініціалізація та запуск програми
     */
    public void run() {
        System.out.println("=== Паралельна обробка опорів провідників v6.0 ===");
        
        // Додаємо команди до меню
        menu.add(new ViewConsoleCommand(view));
        menu.add(new GenerateConsoleCommand(view));
        menu.add(new ChangeConsoleCommand(view));
        menu.add(new SaveConsoleCommand(view));
        menu.add(new RestoreConsoleCommand(view));
        menu.add(new ExecuteConsoleCommand(view)); //!
        
        // Запускаємо меню
        menu.execute();
    }
    
    /**
     * Точка входу в програму
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}