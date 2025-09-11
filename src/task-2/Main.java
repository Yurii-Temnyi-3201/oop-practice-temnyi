import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * Обчислення опору провідників та відображення результатів.
 * Містить реалізацію статичного методу main().
 * Демонструє серіалізацію та особливості transient полів.
 * @author Темний Юрій
 * @version 1.0
 * @see Main#main
 */
	public class Main {
    /** Об'єкт класу {@linkplain Calc}. Розв'язує задачу індивідуального завдання. */
    private Calc calc = new Calc();
    /**
     * Відображає меню програми та обробляє команди користувача.*/
    private void menu() {
        String s = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            do {
                System.out.println("\n	=== Калькулятор опору провідників ===");
                System.out.println("Введіть команду...");
                System.out.print("'q'uit, 'v'iew, 'c'alculate, 's'ave, 'r'estore: ");
                try {s = in.readLine();} 
                catch(IOException e) {
                    System.out.println("Помилка: " + e);
                    System.exit(0);
                }
            } while (s.length() != 1);
            switch (s.charAt(0)) {
                case 'q': System.out.println("Вихід з програми."); break;
                case 'v': System.out.println("Поточні результати:");
                    calc.show(); break;
                case 'c': System.out.println("Обчислення опору провідників.");
                    calculateResistance(in);
                    calc.show(); break;
                case 's': System.out.println("Збереження поточних результатів.");
                    try {
                        calc.save();
                        System.out.println("Дані успішно збережено!");
                    } catch (IOException e) {
                        System.out.println("Помилка серіалізації: " + e);
                    }
                    calc.show();break;
                case 'r': System.out.println("Відновлення останніх збережених даних.");
                    try {
                        calc.restore();
                        System.out.println("Дані успішно відновлено!");
                        System.out.println("Зверніть увагу: transient поле обнулилося!");
                    } catch (Exception e) {
                        System.out.println("Помилка серіалізації: " + e);
                    }
                    calc.show();
                    break;
                    	default:
                    System.out.print("Невірна команда. ");
            }
        } while(s.charAt(0) != 'q');
    }
    
    /**
     * Запитує у користувача параметри та обчислює опір.
     * @param in - потік для читання з клавіатури
     */
    private void calculateResistance(BufferedReader in) {
        try {
            System.out.print("Введіть силу струму (А): ");
        double current = Double.parseDouble(in.readLine());
            if (current == 0) {
                System.out.println("Помилка: струм не може дорівнювати нулю!");
                return;
            }
            System.out.print("Введіть напругу на першому провіднику (В): ");
            double voltage1 = Double.parseDouble(in.readLine());
            
            System.out.print("Введіть напругу на другому провіднику (В): ");
            double voltage2 = Double.parseDouble(in.readLine());
            
            System.out.print("Введіть напругу на третьому провіднику (В): ");
            double voltage3 = Double.parseDouble(in.readLine());
            // Обчислюємо опір
            calc.init(current, voltage1, voltage2, voltage3);
        } catch (IOException e) {
            System.out.println("Помилка читання: " + e);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть коректне число!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
    
    /**
     * Виконується при запуску програми.
     * @param args - параметри запуску програми.
     */
    public static void main(String[] args) {
        Main main = new Main();
        // Демонстрація з випадковими значеннями
       //System.out.println("=== Демонстрація ===");
       // double current = 1.0 + Math.random();
       // double v1 = 2.0 + Math.random() * 3.0;
       // double v2 = 2.0 + Math.random() * 3.0;
       // double v3 = 2.0 + Math.random() * 3.0;
        
       // main.calc.init(current, v1, v2, v3);
        main.calc.show();
        // Запуск меню
        main.menu();
    }
}