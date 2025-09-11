package lab3.test;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import lab3.*;

/**
 * Виконує тестування розроблюваних класів.
 * Перевіряє коректність обчислень опорів та серіалізації.
 * @author Темний Ю.
 * @version 2.0
 */
public class MainTest {
    
    /**
     * Конструктор за замовчуванням для тестового класу
     */
    public MainTest() {
        // конструктор за замовчуванням
    }
    
    /**
     * Перевірка основної функціональності класу ViewResistanceResult.
     * Тестує обчислення опорів за законом Ома та послідовне з'єднання
     */
    @Test
    public void testCalc() {
        ViewResistanceResult view = new ViewResistanceResult(3);
        
        // Тест 1: I=2А, U1=10В, U2=6В, U3=4В
        view.getItems().get(0).setValues(2.0, 10.0, 6.0, 4.0);
        // R1=10/2=5 Ом, R2=6/2=3 Ом, R3=4/2=2 Ом, R_total=5+3+2=10 Ом
        assertEquals(5.0, view.getItems().get(0).getR1(), 0.01);
        assertEquals(3.0, view.getItems().get(0).getR2(), 0.01);
        assertEquals(2.0, view.getItems().get(0).getR3(), 0.01);
        assertEquals(10.0, view.getItems().get(0).getTotalResistance(), 0.01);
        
        // Тест 2: I=1А, U1=3В, U2=5В, U3=4В  
        view.getItems().get(1).setValues(1.0, 3.0, 5.0, 4.0);
        // R1=3/1=3 Ом, R2=5/1=5 Ом, R3=4/1=4 Ом, R_total=3+5+4=12 Ом
        assertEquals(3.0, view.getItems().get(1).getR1(), 0.01);
        assertEquals(5.0, view.getItems().get(1).getR2(), 0.01);
        assertEquals(4.0, view.getItems().get(1).getR3(), 0.01);
        assertEquals(12.0, view.getItems().get(1).getTotalResistance(), 0.01);
        
        // Тест 3: I=4А, U1=16В, U2=8В, U3=12В
        view.getItems().get(2).setValues(4.0, 16.0, 8.0, 12.0);
        // R1=16/4=4 Ом, R2=8/4=2 Ом, R3=12/4=3 Ом, R_total=4+2+3=9 Ом
        assertEquals(4.0, view.getItems().get(2).getR1(), 0.01);
        assertEquals(2.0, view.getItems().get(2).getR2(), 0.01);
        assertEquals(3.0, view.getItems().get(2).getR3(), 0.01);
        assertEquals(9.0, view.getItems().get(2).getTotalResistance(), 0.01);
        
        // Перевірка 8-річних представлень
        assertEquals("12", view.getItems().get(0).getOctalRepresentation());
        assertEquals("14", view.getItems().get(1).getOctalRepresentation());
        assertEquals("11", view.getItems().get(2).getOctalRepresentation());
        
        // Перевірка 16-річних представлень
        assertEquals("A", view.getItems().get(0).getHexRepresentation());
        assertEquals("C", view.getItems().get(1).getHexRepresentation());
        assertEquals("9", view.getItems().get(2).getHexRepresentation());
    }
    
    /**
     * Перевірка серіалізації. Коректність відновлення даних.
     */
    @Test
    public void testRestore() {
        ViewResistanceResult view1 = new ViewResistanceResult(100);
        ViewResistanceResult view2 = new ViewResistanceResult();
        
        // Обчислимо значення опорів з випадковим кроком приросту струму
        view1.init(Math.random() * 2.0 + 0.5);
        
        // Збережемо колекцію view1.items
        try {
            view1.viewSave();
        } catch (IOException e) {
            fail("Помилка збереження: " + e.getMessage());
        }
        
        // Завантажимо колекцію view2.items
        try {
            view2.viewRestore();
        } catch (Exception e) {
            fail("Помилка відновлення: " + e.getMessage());
        }
        
        // Повинні завантажити стільки ж елементів, скільки зберегли
        assertEquals("Кількість елементів має співпадати", 
                view1.getItems().size(), view2.getItems().size());
        
        // Причому ці елементи повинні бути рівні
        assertTrue("Колекції мають містити однакові елементи", 
                view1.getItems().containsAll(view2.getItems()));
    }
    
    /**
     * Перевірка роботи закону Ома
     */
    @Test
    public void testZakonOma() {
        ResistanceResult resistance = new ResistanceResult(5.0, 15.0, 10.0, 25.0);
        
        // Перевірка закону Ома для кожного провідника
        assertEquals("R1 = U1/I", 3.0, resistance.getR1(), 0.01);
        assertEquals("R2 = U2/I", 2.0, resistance.getR2(), 0.01);
        assertEquals("R3 = U3/I", 5.0, resistance.getR3(), 0.01);
        
        // Перевірка послідовного з'єднання
        assertEquals("R_total = R1+R2+R3", 10.0, resistance.getTotalResistance(), 0.01);
    }
}