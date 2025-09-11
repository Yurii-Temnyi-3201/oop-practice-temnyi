import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

/**
 * Виконує тестування розроблених класів.
 * Перевіряє коректність обчислень опору та серіалізації/десеріалізації.
 * @author Темний Юрій
 * @version 1.0
 */
public class MainTest {
    
    /**
     * Перевірка основної функціональності класу {@linkplain Calc}
     */
    @Test
    public void testCalc() {
        System.out.println("=== Тестування обчислень ===");
        Calc calc = new Calc();
        // Тест 1: При струмі 1А та напругах 2В, 3В, 5В
        // Опори: 2Ом, 3Ом, 5Ом. Загальний = 10 Ом
        calc.init(1.0, 2.0, 3.0, 5.0);
        
        assertEquals(1.0, calc.getResult().getCurrent(), 1e-10);
        assertEquals(2.0, calc.getResult().getVoltage1(), 1e-10);
        assertEquals(3.0, calc.getResult().getVoltage2(), 1e-10);
        assertEquals(5.0, calc.getResult().getVoltage3(), 1e-10);
        assertEquals(10.0, calc.getResult().getTotalResistance(), 1e-10);
        System.out.println("✓ Тест 1 пройшов успішно");
        
        // Тест 2: При струмі 2А та напругах 4В, 6В, 10В
        // Опори: 2Ом, 3Ом, 5Ом. Загальний = 10 Ом
        calc.init(2.0, 4.0, 6.0, 10.0);
        assertEquals(10.0, calc.getResult().getTotalResistance(), 1e-10);
        System.out.println("✓ Тест 2 пройшов успішно");
        
        // Тест 3: Перевірка обробки нульового струму
        try {
            calc.init(0.0, 2.0, 3.0, 5.0);
            fail("Має бути виняток при нульовому струмі");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Тест 3 пройшов успішно");
        } System.out.println("✓ Тест обчислень: ПРОЙДЕНО");
    }
    
    /**
     * Перевірка 8-річного та 16-річного представлення
     */
    @Test
    public void testNumberRepresentation() {
        System.out.println("=== Тестування представлення чисел ===");
        Calc calc = new Calc();
        // Тест: опір = 15 Ом
        // 15 в octal = 17, в hex = F
        calc.init(1.0, 5.0, 4.0, 6.0); // 5+4+6 = 15 Ом
        
        ResistanceCalculationResult result = calc.getResult();
        assertEquals(15.0, result.getTotalResistance(), 1e-10);
        
        // Перевіряємо представлення цілої частини (15)
        assertEquals("17", result.getOctalRepresentation());
        assertEquals("f", result.getHexRepresentation().toLowerCase());
        
        System.out.println("✓ 8-річне представлення: " + result.getOctalRepresentation());
        System.out.println("✓ 16-річне представлення: " + result.getHexRepresentation());
        System.out.println("✓ Тест представлення чисел: ПРОЙДЕНО");
    }
    /**
     * Перевірка серіалізації та відновлення даних
     */
    @Test
    public void testSerialization() {
        System.out.println("=== Тестування серіалізації ===");
        Calc calc = new Calc();
        
        // Встановлюємо початкові дані
        double current = 2.5;
        double voltage1 = 5.0;
        double voltage2 = 7.5;
        double voltage3 = 12.5;
        double expectedResistance = (voltage1 + voltage2 + voltage3) / current; // 10.0 Ом
        
        calc.init(current, voltage1, voltage2, voltage3);
        
        // Перевіряємо розрахунки
        assertEquals(expectedResistance, calc.getResult().getTotalResistance(), 1e-10);
        
        try {
            // Зберігаємо стан
            calc.save();
            System.out.println("✓ Серіалізація виконана");
            
            // Змінюємо дані
            calc.init(1.0, 1.0, 1.0, 1.0);
            assertEquals(3.0, calc.getResult().getTotalResistance(), 1e-10);
            
            // Відновлюємо попередній стан
            calc.restore();
            System.out.println("✓ Десеріалізація виконана");
            
            // Перевіряємо відновлені дані
            ResistanceCalculationResult restored = calc.getResult();
            assertEquals(current, restored.getCurrent(), 1e-10);
            assertEquals(voltage1, restored.getVoltage1(), 1e-10);
            assertEquals(voltage2, restored.getVoltage2(), 1e-10);
            assertEquals(voltage3, restored.getVoltage3(), 1e-10);
            assertEquals(expectedResistance, restored.getTotalResistance(), 1e-10);
            
            System.out.println("✓ Тест серіалізації: ПРОЙДЕНО");
            
        } catch (IOException e) {
            fail("Помилка серіалізації: " + e.getMessage());
        } catch (Exception e) {
            fail("Помилка десеріалізації: " + e.getMessage());
        }
    }
    
    /**
     * Перевірка особливостей transient полів
     */
    @Test
    public void testTransientFields() {
        System.out.println("=== Тестування transient полів ===");
        Calc calc = new Calc();
        
        // Встановлюємо дані, що дають опір 255 Ом
        // 255 в octal = 377, в hex = FF
        calc.init(1.0, 100.0, 75.0, 80.0); // 100+75+80 = 255 Ом
        
        ResistanceCalculationResult result = calc.getResult();
        
        // Перевіряємо, що transient поля обчислюються правильно
        assertNotNull("Octal representation should not be null", 
                     result.getOctalRepresentation());
        assertNotNull("Hex representation should not be null", 
                     result.getHexRepresentation());
        
        assertEquals("377", result.getOctalRepresentation());
        assertEquals("ff", result.getHexRepresentation().toLowerCase());
        
        try {
            // Зберігаємо та відновлюємо
            calc.save();
            calc.restore();
            
            // Після десеріалізації transient поля мають бути відновлені
            ResistanceCalculationResult restoredResult = calc.getResult();
            assertNotNull("Octal representation should be restored after deserialization", 
                         restoredResult.getOctalRepresentation());
            assertNotNull("Hex representation should be restored after deserialization", 
                         restoredResult.getHexRepresentation());
            
            assertEquals("377", restoredResult.getOctalRepresentation());
            assertEquals("ff", restoredResult.getHexRepresentation().toLowerCase());
            
            System.out.println("✓ Transient поля правильно відновлені після серіалізації");
            System.out.println("✓ Тест transient полів: ПРОЙДЕНО");
            
        } catch (Exception e) {
            fail("Помилка при тестуванні transient полів: " + e.getMessage());
        }
    }
    
    /**
     * Комплексний тест з випадковими даними
     */
    @Test
    public void testRandomData() {
        System.out.println("=== Тестування з випадковими даними ===");
        Calc calc = new Calc();
        
        for (int i = 0; i < 10; i++) {
            double current = 0.5 + Math.random() * 4.5; // 0.5 - 5.0 А
            double voltage1 = 1.0 + Math.random() * 9.0; // 1.0 - 10.0 В
            double voltage2 = 1.0 + Math.random() * 9.0;
            double voltage3 = 1.0 + Math.random() * 9.0;
            
            calc.init(current, voltage1, voltage2, voltage3);
            
            // Перевіряємо, що результат логічний
            double expectedResistance = (voltage1 + voltage2 + voltage3) / current;
            assertEquals(expectedResistance, calc.getResult().getTotalResistance(), 1e-10);
            
            // Перевіряємо, що представлення не null
            assertNotNull(calc.getResult().getOctalRepresentation());
            assertNotNull(calc.getResult().getHexRepresentation());
        }
        
        System.out.println("✓ Тест з випадковими даними: ПРОЙДЕНО");
    }
}