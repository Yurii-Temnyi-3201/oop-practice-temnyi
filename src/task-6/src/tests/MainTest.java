package tests;

import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import lab3.ViewResistanceResult;
import lab6.CommandQueue;
import lab6.MaxCommand;
import lab6.AvgCommand;
import lab6.MinCommand;

/**
 * Клас для тестування функціональності паралельної обробки команд.
 * Використовує JUnit для перевірки роботи Worker Thread pattern.
 * @author Темний Юрій
 * @version 6.0
 * @since 1.0
 * @see CommandQueue
 * @see MaxCommand
 * @see AvgCommand
 * @see MinCommand
 */
public class MainTest {
    /**
     * Конструктор за замовчуванням.
     * Ініціалізує тестовий клас.
     */
    public MainTest() {
        // Конструктор за замовчуванням
    }
    
    /** Колекція результатів для тестування */
    private static ViewResistanceResult view = new ViewResistanceResult();
    
    /** Перша команда пошуку максимуму для прямого виконання */
    private static MaxCommand max1 = new MaxCommand(view);
    
    /** Друга команда пошуку максимуму для виконання через чергу */
    private static MaxCommand max2 = new MaxCommand(view);
    
    /** Перша команда обчислення середнього для прямого виконання */
    private static AvgCommand avg1 = new AvgCommand(view);
    
    /** Друга команда обчислення середнього для виконання через чергу */
    private static AvgCommand avg2 = new AvgCommand(view);
    
    /** Перша команда пошуку мінімуму для прямого виконання */
    private static MinCommand min1 = new MinCommand(view);
    
    /** Друга команда пошуку мінімуму для виконання через чергу */
    private static MinCommand min2 = new MinCommand(view);
    
    /** Черга команд для тестування Worker Thread */
    private CommandQueue queue = new CommandQueue();
    
    /**
     * Метод ініціалізації, який виконується перед усіма тестами.
     * Створює тестові дані та перевіряє їх коректність.
     * 
     * @throws AssertionError якщо ініціалізація не вдалася
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        view.viewInit();
        assertEquals(5, view.getItems().size());
        System.out.println("=== Тестові дані ініціалізовано ===");
    }
    
    /**
     * Метод завершення, який виконується після всіх тестів.
     * Перевіряє однаковість результатів прямого виконання та виконання через чергу.
     * 
     * @throws AssertionError якщо результати не співпадають
     */
    @AfterClass
    public static void tearDownAfterClass() {
        assertEquals("Результати MaxCommand повинні співпадати", 
                    max1.getResult(), max2.getResult());
        assertEquals("Результати AvgCommand повинні співпадати", 
                    avg1.getResult(), avg2.getResult(), .1e-10);
        assertEquals("Результати MinCommand повинні співпадати", 
                    min1.getResult(), min2.getResult());
        System.out.println("=== Всі тести завершено успішно ===");
    }
    
    /**
     * Тест прямого виконання команди пошуку максимального опору.
     * Перевіряє коректність роботи алгоритму пошуку максимуму.
     * 
     * @throws AssertionError якщо результат некоректний
     */
    @Test
    public void testMax() {
        System.out.println("Тестування MaxCommand...");
        max1.execute();
        assertTrue("Індекс максимального елемента повинен бути >= 0", 
                  max1.getResult() > -1);
        System.out.println("✓ testMax пройдено успішно");
    }
    
    /**
     * Тест прямого виконання команди обчислення середнього опору.
     * Перевіряє коректність обчислення середнього арифметичного.
     * 
     * @throws AssertionError якщо результат некоректний
     */
    @Test
    public void testAvg() {
        System.out.println("Тестування AvgCommand...");
        avg1.execute();
        assertTrue("Середнє значення не повинно дорівнювати 0", 
                  avg1.getResult() != 0.0);
        System.out.println("✓ testAvg пройдено успішно");
    }
    
    /**
     * Тест прямого виконання команди пошуку мінімального опору.
     * Перевіряє коректність роботи алгоритму пошуку мінімуму.
     * 
     * @throws AssertionError якщо результат некоректний
     */
    @Test
    public void testMin() {
        System.out.println("Тестування MinCommand...");
        min1.execute();
        assertTrue("Індекс мінімального елемента повинен бути >= 0", 
                  min1.getResult() > -1);
        System.out.println("✓ testMin пройдено успішно");
    }
    
    /**
     * Тест виконання команди MaxCommand через чергу команд.
     * Перевіряє коректність роботи Worker Thread pattern з командою пошуку максимуму.
     * 
     * @throws AssertionError якщо тест не вдався
     */
    @Test
    public void testMaxQueue() {
        System.out.println("Тестування MaxCommand через чергу...");
        queue.put(max2);
        try {
            while (max2.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            fail("Помилка очікування завершення команди: " + e.toString());
        }
        System.out.println("✓ testMaxQueue пройдено успішно");
    }
    
    /**
     * Тест виконання команди AvgCommand через чергу команд.
     * Перевіряє коректність роботи Worker Thread pattern з командою обчислення середнього.
     * 
     * @throws AssertionError якщо тест не вдався
     */
    @Test
    public void testAvgQueue() {
        System.out.println("Тестування AvgCommand через чергу...");
        CommandQueue queue2 = new CommandQueue();
        queue2.put(avg2);
        try {
            while (avg2.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue2.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            fail("Помилка очікування завершення команди: " + e.toString());
        }
        System.out.println("✓ testAvgQueue пройдено успішно");
    }
    
    /**
     * Тест виконання команди MinCommand через чергу команд.
     * Перевіряє коректність роботи Worker Thread pattern з командою пошуку мінімуму.
     * 
     * @throws AssertionError якщо тест не вдався
     */
    @Test
    public void testMinQueue() {
        System.out.println("Тестування MinCommand через чергу...");
        CommandQueue queue3 = new CommandQueue();
        queue3.put(min2);
        try {
            while (min2.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            queue3.shutdown();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            fail("Помилка очікування завершення команди: " + e.toString());
        }
        System.out.println("✓ testMinQueue пройдено успішно");
    }
}