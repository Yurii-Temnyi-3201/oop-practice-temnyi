package lab6;

import java.util.concurrent.TimeUnit;
import lab3.ViewResistanceResult;
import lab5.Command;

/**
 * Команда пошуку максимального опору;
 * Задача, використовувана обробником потока, шаблон Worker Thread.
 * @author Темний Юрій
 * @version 1.0
 */
public class MaxCommand implements Command {
    /** Результат - індекс елемента з максимальним опором */
    private int result = -1;
    /** Прогрес виконання 0-100% */
    private int progress = 0;
    /** Колекція результатів для обробки */
    private ViewResistanceResult viewResult;
    
    /**
     * Конструктор
     * @param viewResult колекція для обробки
     */
    public MaxCommand(ViewResistanceResult viewResult) {
        this.viewResult = viewResult;
    }
    
    /**
     * Отримати результат
     * @return індекс елемента з максимальним опором
     */
    public int getResult() {
        return result;
    }
    
    /**
     * Перевірка чи виконується команда
     * @return true якщо ще виконується
     */
    public boolean running() {
        return progress < 100;
    }@Override
    public void undo() {
        // Порожня реалізація
    }
    
    @Override
    public void execute() {
        progress = 0;
        System.out.println("Max resistance search started...");
        
        int size = viewResult.getItems().size();
        result = 0;
        
        for (int idx = 1; idx < size; idx++) {
            if (viewResult.getItems().get(result).getTotalResistance() < 
                viewResult.getItems().get(idx).getTotalResistance()) {
                result = idx;
            }
            
            progress = idx * 100 / size;
            if (idx % (size / 3) == 0) {
                System.out.println("Max progress: " + progress + "%");
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(3000 / size);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        System.out.println("Max done. Item #" + result + " found: " + 
            String.format("%.2f Ом", viewResult.getItems().get(result).getTotalResistance()));
        progress = 100;
    }
}