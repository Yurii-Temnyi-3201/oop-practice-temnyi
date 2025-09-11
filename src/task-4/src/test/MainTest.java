package test;

import lab3.ResistanceResult;
import lab3.View;
import lab4.ViewTable;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

/**
 * Клас для модульного тестування {@link ViewTable}.<br>
 * Перевіряє основну функціональність, серіалізацію, перевантаження, перевизначення та динамічний поліморфізм.
 * @author Темний Ю.
 * @version 1.0
 */
public class MainTest {
	/**
     * Конструктор за замовчуванням.
     */
    public MainTest() {
        // Для JUnit
    }/**
     * Перевіряє основну функціональність {@link ViewTable}.<br>
     * Тестує створення таблиці, ініціалізацію та коректність розмірів.
     */
    @Test
    public void testCalc() {
        ViewTable tbl = new ViewTable(5, 10, 2);
        assertEquals(10, tbl.getWidth());
        assertEquals(2, tbl.getPrecision());
        assertEquals(5, tbl.getItems().size());

        tbl.viewInit();
        for (ResistanceResult item : tbl.getItems()) {
            assertNotNull(item);
        }
    }/**
     * Перевіряє серіалізацію та відновлення даних.<br>
     * Тестує збереження та відновлення колекції {@link ResistanceResult}.
     */
    @Test
    public void testRestore() {
        ViewTable tbl1 = new ViewTable(5, 10, 2);
        tbl1.viewInit();
        try {
            tbl1.viewSave();
        } catch (IOException e) {
            fail("Помилка збереження: " + e.getMessage());
        }
        ViewTable tbl2 = new ViewTable(5, 10, 2);
        try {
            tbl2.viewRestore();
        } catch (Exception e) {
            fail("Помилка відновлення: " + e.getMessage());
        }
        assertEquals(tbl1.getItems().size(), tbl2.getItems().size());
        assertTrue(tbl1.getItems().containsAll(tbl2.getItems()));
    }/**
     * Перевіряє перевантаження (overloading) методів {@link ViewTable#setFormat(int)} та {@link ViewTable#setFormat(int, int)}.
     */
    @Test
    public void testOverloading() {
        ViewTable table = new ViewTable();
        table.setFormat(20);
        assertEquals(20, table.getWidth());
        table.setFormat(25, 4);
        assertEquals(25, table.getWidth());
        assertEquals(4, table.getPrecision());
    }
    /**
     * Перевіряє динамічний поліморфізм (dynamic dispatch).<br>
     * Тестує поліморфізм через інтерфейс {@link View}.
     */
    @Test
    public void testDynamicDispatch() {
        View view = new ViewTable();
        view.viewShow();
        assertTrue(view instanceof ViewTable);
    }
}