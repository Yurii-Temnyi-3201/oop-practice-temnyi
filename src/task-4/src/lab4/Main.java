package lab4;
import lab3.Viewable;
import java.io.IOException;
import lab3.View;
import java.util.Scanner;
/**
 * Містить обчислення, відображення результатів та реалізацію статичного методу main()<br>
 * Діалоговий інтерфейс для роботи з табличним представленням результатів.
 * @author Темний Ю.
 * @version 1.0
 */
public class Main extends lab3.Main {
    /**
     * Конструктор, який викликає конструктор суперкласу з параметром View.
     * @param view об'єкт для відображення результатів
     */
    public Main(View view) {
        super(view);
    }/**
     * Точка входу в програму<br>
     * Створює табличний вигляд результатів і запускає меню.
     * @param args параметри командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        System.out.println("=== Лабораторна робота: Поліморфізм та форматований вивід ===");
        
        Viewable factory = new ViewableTable();//конкретний створювач
        ViewTable view = (ViewTable) factory.getView();//поліморфне створення 
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("Оберіть команду:");
                System.out.println("'q' - Вихід");
                System.out.println("'v' - Перегляд поточних даних");
                System.out.println("'g' - Випадкова генерація");
                System.out.println("'s' - Зберегти дані");
                System.out.println("'r' - Відновити дані");
                System.out.println("'f' - Змінити формат таблиці (ширина, точність, кількість рядків)");
                System.out.print("Ваш вибір: ");
                	String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;
                char command = input.toLowerCase().charAt(0);
                switch (command) {
                    case 'q': running = false;
                    System.out.println("Вихід."); break; 
                    //поліморфні виклики
                    case 'v': view.viewShow(); break;
                    case 'g': view.viewInit(); view.viewShow(); break;
                    case 's':
                        try { view.viewSave();
                            System.out.println("Дані збережено.");
                        } catch (IOException e) {
                            System.out.println("Помилка серіалізації: " + e.getMessage());
                        } break;
                     case 'r':
                        try { view.viewRestore();
                            System.out.println("Дані відновлено.");
                            view.viewShow();
                        } catch (Exception e) {
                            System.out.println("Помилка відновлення: " + e.getMessage());
                        } break;
                     case 'f':changeTableFormat(view, scanner);
                        break;
                     	default:
                        System.out.println("Невірна команда. Спробуйте ще раз.");
                }
            }
        }
        System.out.println("Завершення роботи.");
    }

    /**
     * Зміна формату таблиці через діалог з користувачем.
     * @param view об'єкт ViewTable для налаштування
     * @param scanner сканер для введення даних
     */
    private static void changeTableFormat(ViewTable view, Scanner scanner) {
        try {
            System.out.print("Введіть ширину стовпців (мін. 6): ");
            int width = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Введіть точність (кількість знаків після коми): ");
            int precision = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Введіть кількість рядків (мін. 1): ");
            int newSize = Integer.parseInt(scanner.nextLine().trim());
            
            view.setFormat(width, precision);
            view.setSize(newSize);
            
            System.out.println("Формат та кількість рядків змінено.");
            view.viewShow();    
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть коректні числові значення!");
        } catch (Exception e) {
            System.out.println("Помилка введення параметрів: " + e.getMessage());
        }
    }
}