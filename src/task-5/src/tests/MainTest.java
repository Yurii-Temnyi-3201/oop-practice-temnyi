package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import lab3.ResistanceResult;
import lab3.ViewResistanceResult;
import lab5.ChangeItemCommand;
import lab5.ChangeConsoleCommand;

/**
 * Тестування класу ChangeItemCommand
 * @author Темний Ю.
 * @version 5.0
 * @see ChangeItemCommand
 */
public class MainTest {
	/**
     * Конструктор за замовчуванням
     */
    public MainTest() {// Порожній конструктор
    }
    /**
     * Перевірка методу ChangeItemCommand.execute()
     */
    @Test
    public void testExecute() {
        ChangeItemCommand cmd = new ChangeItemCommand();
        cmd.setItem(new ResistanceResult());
        double current, voltage1, voltage2, voltage3, offset;
        
        for (int ctr = 0; ctr < 100; ctr++) {
            current = Math.random() * 10.0 + 1.0;
            voltage1 = Math.random() * 100.0;
            voltage2 = Math.random() * 100.0;
            voltage3 = Math.random() * 100.0;
            offset = Math.random() * 2.0 + 0.5;
            
            cmd.getItem().setValues(current, voltage1, voltage2, voltage3);
            cmd.setOffset(offset);
            cmd.execute();
            
            assertEquals(current * offset, cmd.getItem().getCurrent(), 1e-10);
            assertEquals(voltage1, cmd.getItem().getVoltage1(), 1e-10);
            assertEquals(voltage2, cmd.getItem().getVoltage2(), 1e-10);
            assertEquals(voltage3, cmd.getItem().getVoltage3(), 1e-10);
        }
    }
    /**
     * Перевірка класу ChangeConsoleCommand
     */
    @Test
    public void testChangeConsoleCommand() {
        ChangeConsoleCommand cmd = new ChangeConsoleCommand(new ViewResistanceResult());
        cmd.getView().viewInit();
        cmd.execute();
        assertEquals("'c'hange", cmd.toString());
        assertEquals('c', cmd.getKey());
    }}