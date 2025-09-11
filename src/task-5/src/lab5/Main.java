package lab5;

/**
 * Обчислення та відображення результатів;
 * містить реалізацію статичного методу main()
 * @author Темний Ю.
 * @version 5.0
 * @see Main#main
 */
public class Main {
	/**
     * Конструктор за замовчуванням
     */
    public Main() {
        // Порожній конструктор
    }
    /**
     * Виконується при запуску програми;
     * викликає метод Application.run()
     * @param args параметри запуску програми
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        app.run();
    }
}