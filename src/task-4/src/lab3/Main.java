package lab3;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Головний клас програми для тестування опорів.
 * @author Темний Ю.
 * @version 1.0
 */
public class Main {
    
    /**
     * Об'єкт, що реалізує інтерфейс View;
     * обслуговує колекцію об'єктів ResistanceResult
     */
    private View view;
    
    /**
     * Ініціалізує поле view
     * @param view об'єкт для відображення
     */
    public Main(View view) {
        this.view = view;
    }
    
    /**
     * Відображає меню команд
     */
    protected void menu() {
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            do {
                System.out.println("Оберіть команду...");
                System.out.print("'q'uit, 'v'iew, 'g'enerate, 's'ave, 'r'estore: ");
                try {
                    s = in.readLine();
                } catch(IOException e) {
                    System.out.println("Error: " + e);
                    System.exit(0);
                }
            } while (s.length() != 1);
            
            switch (s.charAt(0)) {
                case 'q':
                    System.out.println("Вихід.");
                    break;
                case 'v':
                    System.out.println("Перегляд поточних даних");
                    view.viewShow();
                    break;
                case 'g':
                    System.out.println("Випадкова генерація.");
                    view.viewInit();
                    view.viewShow();
                    break;
                case 's':
                    System.out.println("Збереження поточних даних.");
                    try {
                        view.viewSave();
                    } catch (IOException e) {
                        System.out.println("Помилка серіалізації: " + e);
                    }
                    view.viewShow();
                    break;
                case 'r':
                    System.out.println("Відновлення збережених даних.");
                    try {
                        view.viewRestore();
                    } catch (Exception e) {
                        System.out.println("Помилка серіалізації: " + e);
                    }
                    view.viewShow();
                    break;
                default:
                    System.out.println("Невірна команда.");
            }
        } while(s.charAt(0) != 'q');
    }
    
    /**
     * Точка входу в програму
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        System.out.println("=== Лабораторна робота 3: Опори послідовних 3 провідників ===");
        
        // Factory Method pattern
        Viewable factory = new ViewableResistance();
        View view = factory.getView();
        
        // Створення Main з діалоговим інтерфейсом
        Main main = new Main(view);
        main.menu();
    }
}