package lab6;

import java.util.concurrent.TimeUnit;
import lab3.View;
import lab3.ViewResistanceResult;
import lab5.ConsoleCommand;

/**
 * Консольна команда Execute all threads, шаблон Command.
 * @author Темний Юрій
 * @version 1.0
 */
public class ExecuteConsoleCommand implements ConsoleCommand {
    /** Об'єкт для роботи з колекцією */
    private View view;
    
    /**
     * Конструктор
     * @param view об'єкт для роботи з колекцією
     */
    public ExecuteConsoleCommand(View view) {
        this.view = view;
    }@Override
    public void undo() {
        // Порожня реалізація
    }
    
    @Override
    public char getKey() {
        return 'e';
    }
    
    @Override
    public String toString() {
        return "'e'xecute";
    }
    
    @Override
    public void execute() {
        System.out.println("Execute all threads...");
        
        // Створюємо дві черги для паралельної обробки
        CommandQueue queue1 = new CommandQueue();
        CommandQueue queue2 = new CommandQueue();
        
        // Створюємо команди для обробки
        ViewResistanceResult viewResult = (ViewResistanceResult) view;
        MaxCommand maxCommand = new MaxCommand(viewResult);
        AvgCommand avgCommand = new AvgCommand(viewResult);
        MinMaxCommand minMaxCommand = new MinMaxCommand(viewResult);
        
        // Розподіляємо команди по чергах
        queue1.put(minMaxCommand);
        queue2.put(maxCommand);
        queue2.put(avgCommand);
        
        try {
            // Очікуємо завершення всіх команд
            while (avgCommand.running() || 
                   maxCommand.running() || 
                   minMaxCommand.running()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            
            // Зупиняємо черги
            queue1.shutdown();
            queue2.shutdown();
            
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        
        System.out.println("All threads completed!");
    }
}