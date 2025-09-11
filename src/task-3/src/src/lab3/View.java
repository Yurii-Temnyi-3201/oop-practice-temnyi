package lab3;

import java.io.IOException;

/**
 * Product (шаблон проектування Factory Method). Інтерфейс для представлення даних про опори. Оголошує методи відображення об'єктів.
 * @author Темний Ю.
 * @version 1.0
 */
public interface View {
    /**
     * Відображення
     */
    void viewHeader();
    void viewBody();
    void viewFooter();
    void viewShow();
    /**
     * Виконує ініціалізацію
     */
    void viewInit();
    /**
     * Зберігає дані для подальшого відновлення
     * @throws IOException помилка запису файлу
     */
    void viewSave() throws IOException;
    /**
     * Відновлює раніше збережені дані
     * @throws Exception помилка при відновленні
     */
    void viewRestore() throws Exception;
}